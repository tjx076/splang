package com.olcap.cache;

import java.util.function.Supplier;


public abstract class Cacheable<TData> {
	
	protected abstract TData fromCache(String key);
	
	public TData orElseExecute(String key, Supplier<TData> func){
		TData obj = fromCache(key);
		if(obj==null){
			obj = func.get();
			toCache(key, obj);
		}
		
		return obj;
	}
	
	protected abstract void toCache(String key, TData obj);
}
