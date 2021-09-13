package com.olcap;

public class NoElementsException extends RuntimeException{
	private static final long serialVersionUID = -6913476816048371388L;

	public NoElementsException() {
        super();
    }

    public NoElementsException(String s) {
        super(s);
    }
}
