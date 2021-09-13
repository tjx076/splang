package com.olcap;

import java.util.Iterator;

public class EmptyEnumerable<TElement> implements Enumerable<TElement>{

	@Override
	public Iterator<TElement> iterator() {
		return new EmptyIterator<TElement>();
	}
}
