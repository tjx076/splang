package com.sp.lang.buildinfuncs;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class ArrayEnumerable<E> implements Enumerable<E> {

	private List<E> list;
	
	public ArrayEnumerable(E[] arr){
		this.list= Arrays.asList(arr);
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}
}
