package com.yaowang.util.cache;

import java.util.List;

/**
 * 缓存接口 
 * @author Administrator
 *
 */
public interface CacheService {
	/**
	 * 保存缓存
	 * @param key
	 * @param o
	 * @return
	 */
	public Boolean put(String key, Object o) throws Exception;
	/**
	 * 获取缓存
	 * @return
	 */
	public Object get(String key) throws Exception;
	/**
	 * 删除缓存
	 * @param key
	 * @return
	 */
	public Boolean remove(String key) throws Exception;
	/**
	 * 键是否存在
	 * @param key
	 * @return
	 */
	public Boolean contains(String key) throws Exception;
	/**
	 * 添加到list中
	 * @param key
	 * @param data
	 * @return
	 */
	public Boolean putList(String key, Object data) throws Exception;
	/**
	 * 获取list
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List<Object> getList(String key) throws Exception;
	/**
	 * 删除全部
	 * @return
	 */
	public Boolean clearAll() throws Exception;
}
