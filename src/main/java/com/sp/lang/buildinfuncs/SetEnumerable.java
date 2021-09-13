package com.sp.lang.buildinfuncs;

import java.util.Iterator;
import java.util.Set;


public class SetEnumerable<E> implements Enumerable<E> {

	private Set<E> set;
	
	public SetEnumerable(Set<E> set){
		this.set = set;
	}

	@Override
	public Iterator<E> iterator() {
		return set.iterator();
	}
}
