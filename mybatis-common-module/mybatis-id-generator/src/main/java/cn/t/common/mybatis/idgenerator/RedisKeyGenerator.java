package cn.t.common.mybatis.idgenerator;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.sql.Statement;

public class RedisKeyGenerator implements KeyGenerator {

    private StringRedisTemplate stringRedisTemplate;
    private long step = 2;
    private long current = 0;
    private long last = -1;

    @Override
    public void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {

    }

    @Override
    public void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {

    }

    synchronized long generateId(String db, String table) {
        String key = "GENERATE_ID:".concat(db.concat(":").concat(table).toUpperCase());
        if(current > last) {
            last = stringRedisTemplate.opsForValue().increment(key, step);
            current = last - step + 1;
        }
        return current++;
    }

    public RedisKeyGenerator(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
}
