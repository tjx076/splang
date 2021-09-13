package com.sp.lang;

public class NoMatchException extends RuntimeException {
	private static final long serialVersionUID = 849990387021618271L;

	public NoMatchException() {
        super();
    }

    public NoMatchException(String s) {
        super(s);
    }
}
