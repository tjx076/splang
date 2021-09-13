package com.sp.lang.except;

public class ObjDeclException  extends RuntimeException {

	private static final long serialVersionUID = -623849772245503932L;
	public ObjDeclException() {
        super();
    }

    public ObjDeclException(String s) {
        super(s);
    }
    
    public ObjDeclException(String s, Throwable cause) {
        super(s, cause);
    }
}
