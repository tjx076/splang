package com.olcap.lang.ast;

import com.olcap.lang.Token;

public class OLRefIdentifier extends OLPrimitive{

	private String var;
	private String field;
	
	public OLRefIdentifier(Token token) {
		super(token);
		
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		
		boolean b = false;
		String text = token.getText();
		for(int i = 0;i<text.length();i++){
			char c = text.charAt(i);
			
			if(c=='.'){
				b= true;
				continue;
			}
			
			if(b==false){
				sb1.append(c);
			} else {
				sb2.append(c);
			}
		}
		
		var = sb1.toString();
		field = sb2.toString();
			
	}

	public String getVar() {
		return var;
	}

	public String getField() {
		return field;
	}

	@Override
	public String toString() {
		return "OLRefIdentifier [var=" + var + ", field=" + field + "]";
	}
}
