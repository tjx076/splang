package com.olcap;

public class OutOfRangeException extends RuntimeException {

	private static final long serialVersionUID = 2887129364402904140L;

	public OutOfRangeException() {
        super();
    }

    public OutOfRangeException(String s) {
        super(s);
    }
}