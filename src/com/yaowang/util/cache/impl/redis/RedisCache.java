package com.yaowang.util.cache.impl.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.yaowang.util.ResourcesLoad;
import com.yaowang.util.cache.CacheService;
/**
 * redis api
 * @author shenl
 *
 */
public class RedisCache implements CacheService{
    private static JedisPool pool = null;
    private static Map<String, String> dataMap = ResourcesLoad.load("classpath*:/conf/redis.properties");
    
	static{
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
		}else {
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
	
	@Override
	public Boolean put(String key, Object o) throws Exception {
		if (o == null) {
			return true;
		}
		Jedis jedis = getJedis();
		try {
			byte[] data = serialize(o);
			String str = jedis.set(serialize(key), data);
			return isOk(str);
		} finally{
			close(jedis);
		}
	}

	@Override
	public Object get(String key) throws Exception {
		Jedis jedis = getJedis();
		try {
			Object object = deserialize(jedis.get(serialize(key)));
			return object;
		} finally{
			close(jedis);
		}
	}

	@Override
	public Boolean remove(String key) throws Exception {
		Jedis jedis = getJedis();
		try {
			long n = jedis.del(serialize(key));
			return true;
		} finally{
			close(jedis);
		}
	}

	@Override
	public Boolean contains(String key) throws Exception {
		Jedis jedis = getJedis();
		try {
			Boolean b = jedis.exists(serialize(key));
			return b;
		} finally{
			close(jedis);
		}
	}

	@Override
	public Boolean putList(String key, Object o) throws Exception {
		if (o == null) {
			return true;
		}
		Jedis jedis = getJedis();
		try {
			byte[] data = serialize(o);
			long n = jedis.lpush(serialize(key), data);
			return true;
		} finally{
			close(jedis);
		}
	}
	
	@Override
	public List<Object> getList(String key) throws Exception {
		Jedis jedis = getJedis();
		try {
			byte[] c = serialize(key);
			long len = jedis.llen(c);
			if (len <= 0) {
				return new ArrayList<Object>();
			}
			List<byte[]> list = jedis.lrange(c, 0, len);
			
			List<Object> array = new ArrayList<Object>();
			for (byte[] b : list) {
				array.add(deserialize(b));
			}
			return array;
		} finally{
			close(jedis);
		}
	}

	@Override
	public Boolean clearAll() throws Exception {
		Jedis jedis = getJedis();
		try {
			String str = jedis.flushAll();
			return isOk(str);
		} finally{
			close(jedis);
		}
	}
	/**
	 * 连接池中获取连接
	 * @return
	 */
	private Jedis getJedis(){
		Jedis jedis = pool.getResource();
		return jedis;
	}
	/**
	 * 返回连接池
	 * @param jedis
	 */
	private void close(Jedis jedis){
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
	
	private static void close(InputStream is){
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void close(OutputStream is){
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static boolean isOk(String str){
		return "OK".equals(str);
	}
}
