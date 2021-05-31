package cn.t.common.mybatis.keygenerator;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.data.redis.core.ValueOperations;

import java.sql.Statement;

public class RedisKeyGenerator implements KeyGenerator {

    private static final String ID_KEY_PREFIX = "GENERATE_ID:";

    private final ValueOperations<String, String> valueOperations;
    private final long step = 2;
    private long current = 0;
    private long last = -1;

    @Override
    public void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {

    }

    @Override
    public void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {

    }

    synchronized long generateId(String db, String table) {
        String key = ID_KEY_PREFIX.concat(db.concat(":").concat(table).toUpperCase());
        if(current > last) {
            Long result = valueOperations.increment(key, step);
            if(result != null) {
                last = result;
                current = last - step + 1;
            }
        }
        return current++;
    }

    public RedisKeyGenerator(ValueOperations<String, String> valueOperations) {
        this.valueOperations = valueOperations;
    }
}
