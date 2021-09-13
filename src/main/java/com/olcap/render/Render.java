package com.olcap.render;

public interface Render<TSource, TResult> {
	
	public TResult render(TSource obj);
}
