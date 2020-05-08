package cn.t.common.mybatis.idgenerator;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;

import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yj
 * @since 2020-05-08 12:48
 **/
public class InMemoryKeyGenerator implements KeyGenerator {

    private AtomicInteger id = new AtomicInteger(1);

    @Override
    public void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
        processGeneratedKeys(ms, parameter);
    }

    @Override
    public void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {

    }

    private void processGeneratedKeys(MappedStatement ms, Object parameter) {
        try {
                final String[] keyProperties = ms.getKeyProperties();
                final Configuration configuration = ms.getConfiguration();
                final MetaObject metaParam = configuration.newMetaObject(parameter);
                if (keyProperties != null) {
                    MetaObject metaResult = configuration.newMetaObject(id.getAndIncrement());
                    setValue(metaParam, keyProperties[0], metaResult.getValue(keyProperties[0]));
                }
        } catch (ExecutorException e) {
            throw e;
        } catch (Exception e) {
            throw new ExecutorException("Error selecting key or setting result to parameter object. Cause: " + e, e);
        }
    }

    private void setValue(MetaObject metaParam, String property, Object value) {
        if (metaParam.hasSetter(property)) {
            metaParam.setValue(property, value);
        } else {
            throw new ExecutorException("No setter found for the keyProperty '" + property + "' in " + metaParam.getOriginalObject().getClass().getName() + ".");
        }
    }
}
