package com.yaowang.util.cache.impl.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yaowang.util.cache.CacheService;
/**
 * 本地缓存
 * @author shenl
 *
 */
public class LocalCache implements CacheService {
	/**
	 * 缓存
	 */
	private static final Map<String, Object> MAP_DATA = new HashMap<String, Object>();//存放对应属性值
	
	@Override
	public Boolean put(String key, Object o) {
		MAP_DATA.put(key, o);
		return true;
	}

	@Override
	public Object get(String key) {
		return MAP_DATA.get(key);
	}

	@Override
	public Boolean remove(String key) {
		Object o = MAP_DATA.remove(key);
		return o != null;
	}

	@Override
	public Boolean contains(String key) {
		return MAP_DATA.containsKey(key);
	}

	@Override
	public synchronized Boolean putList(String key, Object data) {
		List<Object> obj = (List<Object>)MAP_DATA.get(key);
		if(obj == null) {
			obj = new ArrayList<Object>();
			MAP_DATA.put(key, obj);
		}
		return obj.add(data);
	}
	
	@Override
	public List<Object> getList(String key) throws Exception {
		return (List<Object>)MAP_DATA.get(key);
	}
	
	@Override
	public Boolean clearAll() {
		MAP_DATA.clear();
		return true;
	}
}
