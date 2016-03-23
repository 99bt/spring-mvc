package com.lansha.test;

import com.yaowang.util.DateUtils;
import com.yaowang.util.ResourcesLoad;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-3-4
 * Time: 下午6:19
 * To change this template use File | Settings | File Templates.
 */
@Service("redisCacheService")
public class RedisCacheService {
    private static JedisPool pool = null;
    private static Map<String, String> dataMap = ResourcesLoad.load("classpath*:/conf/redis.properties");

    static {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(Integer.valueOf(dataMap.get("redis.maxTotal")));
        config.setMaxIdle(Integer.valueOf(dataMap.get("redis.maxIdle")));
        config.setMaxWaitMillis(10 * 1000);
        config.setTestOnBorrow(true);

        String url = dataMap.get("redis.url");
        Integer port = Integer.valueOf(dataMap.get("redis.port"));
        //密码
        String password = dataMap.get("redis.password");
        if (StringUtils.isNotBlank(password)) {
            pool = new JedisPool(config, url, port, 10 * 1000, password);
        } else {
            pool = new JedisPool(config, url, port);
        }

//		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
//        JedisShardInfo si = new JedisShardInfo(dataMap.get("redis.url"), Integer.valueOf(dataMap.get("redis.port")));
//        if (StringUtils.isNotBlank(password)) {
//        	si.setPassword(password);
//		}
//        shards.add(si);
//        pool = new ShardedJedisPool(config, shards);
    }


    public Boolean put(String key, String field, String value) {
        if (StringUtils.isEmpty(field)) {
            return false;
        }
        Jedis jedis = getJedis();
        try {
            if (jedis.exists(key)) {
                long n = jedis.hset(key, field, value);
            } else {
                long n = jedis.hset(key, field, value);
                jedis.expire(key, DateUtils.diffSeconds());
            }
            return true;
        } finally {
            close(jedis);
        }
    }

    public Boolean hincrBy(String key, String field) {
        if (StringUtils.isEmpty(field)) {
            return false;
        }
        Jedis jedis = getJedis();
        try {

            long n = jedis.hincrBy(key, field, 1);

            return true;
        } finally {
            close(jedis);
        }
    }

    public Boolean existsKey(String key) {

        Jedis jedis = getJedis();
        try {
            return jedis.exists(key);
        } finally {
            close(jedis);
        }
    }

    /**
     * SortedSet zincrby
     *
     * @param key
     * @param field
     * @return
     */
    public Boolean zincrby(String key, String field) {
        if (StringUtils.isEmpty(field)) {
            return false;
        }
        Jedis jedis = getJedis();
        try {
            if (jedis.exists(key)) {
                Double n = jedis.zincrby(key, 1, field);
            } else {
                Double n = jedis.zincrby(key, 1, field);
                jedis.expire(key, DateUtils.diffSeconds() - 2000);
            }

            return true;
        } finally {
            close(jedis);
        }
    }

    public Boolean zadd(String key, Integer value, String field) {
        if (StringUtils.isEmpty(field)) {
            return false;
        }
        Jedis jedis = getJedis();
        try {
            if (jedis.exists(key)) {
                Long n = jedis.zadd(key, value, field);
            } else {
                Long n = jedis.zadd(key, value, field);
                jedis.expire(key, DateUtils.diffSeconds());
            }

            return true;
        } finally {
            close(jedis);
        }
    }

    /**
     * 从小到大
     *
     * @param key
     * @param field
     * @return
     */
    public Long zrank(String key, String field) {
        Jedis jedis = getJedis();
        try {
            Long n = jedis.zrank(key, field);
            return n;
        } finally {
            close(jedis);
        }
    }

    /**
     * 从大到小
     *
     * @param key
     * @param field
     * @return
     */
    public Long zrevrank(String key, String field) {
        Jedis jedis = getJedis();
        try {
            Long n = jedis.zrank(key, field);
            return n;
        } finally {
            close(jedis);
        }
    }

    /**
     * 获取值
     *
     * @param key
     * @param field
     * @return
     */
    public Double zscore(String key, String field) {
        Jedis jedis = getJedis();
        try {
            Double n = jedis.zscore(key, field);
            return n;
        } finally {
            close(jedis);
        }
    }


    public Boolean getByhlen(String key) throws Exception {
        Jedis jedis = getJedis();
        try {
            return jedis.hlen(key) > 0;
        } finally {
            close(jedis);
        }
    }

    /**
     * 获取key的所有的值
     *
     * @param key
     * @return
     * @throws Exception
     */
    public Map<String, String> get(String key) throws Exception {
        Jedis jedis = getJedis();
        try {
            return jedis.hgetAll(key);
        } finally {
            close(jedis);
        }
    }

    /**
     * 获取map中某一列的值
     *
     * @param key
     * @return
     * @throws Exception
     */
    public String get(String key, String field) throws Exception {
        Jedis jedis = getJedis();
        try {
            return jedis.hget(key, field);
        } finally {
            close(jedis);
        }
    }


    /**
     * 连接池中获取连接
     *
     * @return
     */
    private Jedis getJedis() {
        Jedis jedis = pool.getResource();
        return jedis;
    }

    /**
     * 返回连接池
     *
     * @param jedis
     */
    private void close(Jedis jedis) {
        pool.returnResourceObject(jedis);
    }

    private static byte[] serialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] result = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            result = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            close(os);
            close(bos);
        }
        return result;
    }

    private static Object deserialize(byte[] in) {
        Object result = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                result = (Object) is.readObject();
                is.close();
                bis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(is);
            close(bis);
        }
        return result;
    }

    private static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void close(OutputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isOk(String str) {
        return "OK".equals(str);
    }
}

