package com.sp.lang;

public enum Keywords {
	
	TRUE(Token.True),FALSE(Token.False),NULL(Token.Null),OBJ(Token.Obj),FUNC(Token.Func);
	
	Token t;
	
	private Keywords(Token t) {
		this.t = t;
	}
	
	public Token getToken() {
		return t;
	}

	public static Keywords from(String s) {
		for(Keywords kw : Keywords.values()){
			if(kw.toString().equalsIgnoreCase(s)){
				return kw;
			}
		}
		
		return null;
	}
	
	public static boolean contains(Token t) {
		for(Keywords kw : Keywords.values()){
			if(kw.toString().equalsIgnoreCase(t.getText())){
				return true;
			}
		}
		
		return false;
	}
}
