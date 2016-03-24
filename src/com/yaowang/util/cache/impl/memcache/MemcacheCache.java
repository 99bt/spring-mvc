package com.yaowang.util.cache.impl.memcache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import com.yaowang.util.cache.CacheService;
/**
 * memecache api
 * @author shenl
 *
 */
public class MemcacheCache implements CacheService{
	private static MemcachedClient memcachedClient = null;
	static{
		try {
			MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("localhost:11211"));
			//设置连接池大小，即客户端个数  
	        builder.setConnectionPoolSize(3);  
	        //宕机报警  
	        builder.setFailureMode(true);
			memcachedClient = builder.build();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Boolean put(String key, Object o) throws Exception {
		return memcachedClient.set(key, 0, o);
	}

	@Override
	public Object get(String key) throws Exception {
		return memcachedClient.get(key);
	}

	@Override
	public Boolean remove(String key) throws Exception {
		return memcachedClient.delete(key);
	}

	@Override
	public Boolean contains(String key) throws Exception {
		return memcachedClient.get(key) != null;
	}

	@Override
	public Boolean putList(String key, Object data) throws Exception {
		//原子性更新，为了在并发情况下更新失败
		GetsResponse<List<Object>> result = memcachedClient.gets(key);
		Boolean b = null;
		if (result == null) {
			List<Object> list = new ArrayList<Object>();
			list.add(data);
			b = memcachedClient.add(key, 0, list);
		}else {
			List<Object> list = result.getValue();
			list.add(data);
			b = memcachedClient.cas(key, 0, list, result.getCas());
		}
		if (!b) {
			//更新冲突，重新发起添加
			putList(key, data);
		}
		return true;
	}
	
	@Override
	public List<Object> getList(String key) throws Exception {
		return memcachedClient.get(key);
	}

	@Override
	public Boolean clearAll() throws Exception {
		memcachedClient.flushAll();
		return true;
	}
}
