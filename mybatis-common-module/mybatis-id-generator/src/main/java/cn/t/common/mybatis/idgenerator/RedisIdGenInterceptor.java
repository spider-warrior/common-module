package cn.t.common.mybatis.idgenerator;


import cn.t.util.common.reflect.ClassUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
)})
public class RedisIdGenInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(RedisIdGenInterceptor.class);
    private static final Pattern INSERT_SQL_TABLE_NAME_REG = Pattern.compile("into\\s+([1-9_a-z]+)");

    private final Properties properties = new Properties();
    private final RedisKeyGenerator redisKeyGenerator;

    @Override
    public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException, SQLException {
        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
        Object arg = invocation.getArgs()[1];
        if(SqlCommandType.INSERT == mappedStatement.getSqlCommandType() && StatementType.PREPARED == mappedStatement.getStatementType()) {
            if(mappedStatement.getKeyGenerator() instanceof NoKeyGenerator) {
                String database;
                String tableName;
                Connection connection = ((Executor)invocation.getTarget()).getTransaction().getConnection();
                if("MySQL".equals(connection.getMetaData().getDatabaseProductName())) {
                    database = connection.getCatalog().toUpperCase();
                } else {
                    throw new UnSupportedDatabase(connection.getCatalog());
                }
                tableName = getTableName(mappedStatement.getBoundSql(arg).getSql()).toUpperCase();
                logger.debug("获取到数据库名: {}, 表名: {}", database, tableName);
                if(arg instanceof DefaultSqlSession.StrictMap) {
                    @SuppressWarnings("unchecked")
                    DefaultSqlSession.StrictMap<Object> paramMap = (DefaultSqlSession.StrictMap<Object>)arg;
                    Object param = paramMap.get("collection");
                    if(param != null) {
                        if(param instanceof Collection) {
                            @SuppressWarnings("unchecked")
                            Collection<Object> paramCollection = (Collection<Object>)param;
                            if(paramCollection.size() > 0) {
                                Iterator<Object> it = paramCollection.iterator();
                                long id = redisKeyGenerator.generateId(database, tableName);
                                while (true) {
                                    Object obj = it.next();
                                    boolean success = tryToSetId(obj, id);
                                    if(!it.hasNext()) {
                                        break;
                                    }
                                    if(success) {
                                        id = redisKeyGenerator.generateId(database, tableName);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    tryToSetId(arg, redisKeyGenerator.generateId(database, tableName));
                }
            }
        }
        return invocation.proceed();
    }

    private boolean tryToSetId(Object obj, long id) throws InvocationTargetException, IllegalAccessException {
        Method method = ClassUtil.findMethod(obj.getClass(), "setId", Long.class);
        if(method == null) {
            ClassUtil.findMethod(obj.getClass(), "setId", long.class);
        }
        if(method != null) {
            method.invoke(obj, id);
            return true;
        }
        return false;
    }


    @Override
    public Object plugin(Object target) {
        if(target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties.putAll(properties);
    }

    private String getTableName(String sql) {
        Matcher matcher = INSERT_SQL_TABLE_NAME_REG.matcher(sql);
        if(matcher.find()) {
            return matcher.group(1);
        } else {
            throw new GenerateIdException("获取不到表名: " + sql);
        }
    }

    public RedisIdGenInterceptor(RedisKeyGenerator redisKeyGenerator) {
        this.redisKeyGenerator = redisKeyGenerator;
    }
}
