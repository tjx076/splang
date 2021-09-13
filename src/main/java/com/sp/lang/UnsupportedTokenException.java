package com.sp.lang;

public class UnsupportedTokenException extends RuntimeException {
	private static final long serialVersionUID = -5505057096234398819L;

	public UnsupportedTokenException() {
        super();
    }

    public UnsupportedTokenException(String s) {
        super(s);
    }
}
