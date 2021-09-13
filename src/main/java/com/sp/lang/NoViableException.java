package com.sp.lang;

public class NoViableException extends RuntimeException {
	private static final long serialVersionUID = 4574803222826268732L;

	public NoViableException() {
        super();
    }

    public NoViableException(String s) {
        super(s);
    }
}
