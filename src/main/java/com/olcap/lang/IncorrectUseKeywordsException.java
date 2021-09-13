package com.olcap.lang;

public class IncorrectUseKeywordsException extends RuntimeException {
	private static final long serialVersionUID = 3565454778740819221L;
	
	
	public IncorrectUseKeywordsException() {
        super();
    }

    public IncorrectUseKeywordsException(String s) {
        super(s);
    }
    
    public IncorrectUseKeywordsException(String s, Throwable cause) {
        super(s, cause);
    }
}
