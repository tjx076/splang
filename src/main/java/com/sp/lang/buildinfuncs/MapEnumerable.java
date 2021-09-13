package com.sp.lang.buildinfuncs;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MapEnumerable<K, V> implements Enumerable<Map.Entry<K, V>> {

	private Map<K, V> map;
	
	public MapEnumerable(Map<K, V> map){
		this.map = map;
	}
	
	@Override
	public Iterator<Entry<K, V>> iterator() {
		return map.entrySet().iterator();
	}
}
