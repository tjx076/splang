package com.olcap.cache;


/**
 * Usage:
 * <br/>
 * 
 * <pre>
 * new FromEhcache<String>().orElseExecute(key, () -> {
 * 
 * 		// execute if the data do not exist in cache
 * 
 * });
 * </pre>
 * 
 * @author tangdou
 *
 * @param <TData>
 */
public class FromEhcache<TData> extends Cacheable<TData> {
	
	
	public FromEhcache() {
	}
	
	
	@Override
	protected TData fromCache(String key) {
		return null;
	}

	@Override
	protected void toCache(String key, TData obj) {
	}
}
