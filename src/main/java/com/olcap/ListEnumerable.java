package com.olcap;

import java.util.Iterator;
import java.util.List;


public class ListEnumerable<E> implements Enumerable<E> {

	private List<E> list;
	
	public ListEnumerable(List<E> list){
		this.list = list;
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}
}
