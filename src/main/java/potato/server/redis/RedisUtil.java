package potato.server.redis;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * redis CRUD 메소드
 *
 * @author 정순원
 * @since 2023-08-19
 */
@Repository
public class RedisUtil {

    private RedisTemplate redisTemplate;

    public RedisUtil(final RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //리프레쉬토큰 관련 redis 명령어
    public void save(String key, String value, Long expiration) {
        redisTemplate.opsForValue().set(key, value, expiration, TimeUnit.MILLISECONDS);
    }
    public void update(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }
    public void deleteByKey(String key) {
        redisTemplate.delete(String.valueOf(key));
    }
    public Object findByKey(String key) {
        return redisTemplate.opsForValue().get(key).toString();
    }
}