package com.sp.lang;

public class RecognitionException extends RuntimeException {
	private static final long serialVersionUID = -7990093090016827696L;

	public RecognitionException() {
        super();
    }

    public RecognitionException(String s) {
        super(s);
    }
    
    public RecognitionException(String s, Throwable cause) {
        super(s, cause);
    }
}
