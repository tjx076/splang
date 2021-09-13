package com.olcap.lang.ast;

import com.olcap.lang.Token;

public class OLIndexIdentifier extends OLPrimitive{

	private String var;
	private String index;
	
	public OLIndexIdentifier(Token token) {
		super(token);
		
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		
		boolean b = false;
		String text = token.getText();
		for(int i = 0;i<text.length();i++){
			char c = text.charAt(i);
			
			if(c=='['){
				b= true;
				continue;
			}
			
			if(c==']'){
				break;
			}
			
			if(b==false){
				sb1.append(c);
			} else {
				sb2.append(c);
			}
		}
		
		var = sb1.toString();
		index = sb2.toString();
			
	}

	public String getVar() {
		return var;
	}

	public String getIndex() {
		return index;
	}
	
	public boolean indexIsString() {
		for(int i=0; i<index.length(); i++) {
			char c = index.charAt(i);
			if(!isLetter(c)){
				return false;
			}
		}
		
		return true;
	}
	
	
	public boolean indexIsInteger() {
		for(int i=0; i<index.length(); i++) {
			char c = index.charAt(i);
			if(!isNumber(c)){
				return false;
			}
		}
		
		return true;
	}
	
	boolean isLetter(char c) {
		return c>='a'&&c<='z' || c>='A'&&c<='Z';
	}
	
	boolean isNumber(char c) {
		return c>='0'&&c<='9';
	}

	@Override
	public String toString() {
		return "OLIndexIdentifier [var=" + var + ", index=" + index + "]";
	}
}
