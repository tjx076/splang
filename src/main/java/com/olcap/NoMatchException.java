package com.olcap;

public class NoMatchException extends RuntimeException {
	private static final long serialVersionUID = 8733321768505927859L;

	public NoMatchException() {
        super();
    }

    public NoMatchException(String s) {
        super(s);
    }
}
