package com.olcap.lang;

public class InterpretException extends RuntimeException{
	private static final long serialVersionUID = -9110873303697910981L;
	
	public InterpretException() {
        super();
    }

    public InterpretException(String s) {
        super(s);
    }
	
    public InterpretException(String s, Throwable cause) {
        super(s, cause);
    }
    
    public InterpretException(Throwable cause) {
        super(cause);
    }
}
