package com.olcap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DataSource {
	private final static Logger LOGGER = LoggerFactory.getLogger(DataSource.class);
	
	public static <T> Enumerable<T> FromRpc(Callable<Enumerable<T>> callable) {
		try {
			return callable.call();
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return Enumerable2.Empty();
	}
	
	public static <T extends Serializable> Enumerable<T> FromMysql(String sql, Class<T> clazz) {
		throw new UnsupportedOperationException();
	}

	public static <T extends Serializable> Enumerable<T> FromMysql(String sql, Map<String, Serializable> params, Class<T> clazz) {
		throw new UnsupportedOperationException();
	}
	
	public static <T extends Serializable> Enumerable<T> FromHbase(String sql, Class<T> clazz) {
		throw new UnsupportedOperationException();
	}

	public static <T extends Serializable> Enumerable<T> FromHbase(String sql, Map<String, Serializable> params, Class<T> clazz) {
		throw new UnsupportedOperationException();
	}

	public static <T extends Serializable> Enumerable<T> FromPgsql(String sql, Class<T> clazz) {
		throw new UnsupportedOperationException();
	}

	public static <T extends Serializable> Enumerable<T> FromPgsql(String sql, Map<String, Serializable> params, Class<T> clazz) {
		throw new UnsupportedOperationException();
	}

	
	public static <TSource> Enumerable<TSource> FromArray(TSource[] source) {
		return new ArrayEnumerable<TSource>(source);
	}

	
	public static <TSource> Enumerable<TSource> FromEnumerable(Enumerable<TSource> source) {
		return source;
	}

	
	public static <TSource> Enumerable<TSource> FromIterable(Iterable<TSource> source) {
		List<TSource> list = new ArrayList<TSource>();
		
		for(TSource t : source){
			list.add(t);
		}
		
		return FromList(list);
	}
	
	public static <TSource> Enumerable<TSource> FromIterator(Iterator<TSource> source) {
		List<TSource> list = new ArrayList<TSource>();
		
		while(source.hasNext()) {
			list.add(source.next());
		}
		
		return FromList(list);
	}

	public static <TSource> Enumerable<TSource> FromList(List<TSource> source) {
		return new ListEnumerable<TSource>(source);
	}

	public static <K, V> Enumerable<Entry<K, V>> FromMap(Map<K, V> source) {
		return new MapEnumerable<K, V>(source);
	}
	

	public static <TSource> Enumerable<TSource> FromSet(Set<TSource> source) {
		return new SetEnumerable<TSource>(source);
	}
}
