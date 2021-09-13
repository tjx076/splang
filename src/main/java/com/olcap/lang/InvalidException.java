package com.olcap.lang;

public class InvalidException extends RuntimeException {
	private static final long serialVersionUID = 7250032371899942118L;

	public InvalidException() {
        super();
    }

    public InvalidException(String s) {
        super(s);
    }
    
    public InvalidException(String s, Throwable cause) {
        super(s, cause);
    }
}
