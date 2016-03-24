package com.yaowang.util.cache;

public interface CacheUtil<T> {
	/**
	 * 
	 * @return
	 */
	public T get();
	
	public String key();
}
