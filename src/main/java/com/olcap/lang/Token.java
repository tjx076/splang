package com.olcap.lang;

public class Token {
	public static Token True = new Token(Tokens.True.getType(), "true", Tokens.True.getName(), -1 , -1);
	public static Token False = new Token(Tokens.False.getType(), "false", Tokens.False.getName(), -1, -1);
	public static Token Null = new Token(Tokens.Null.getType(), "Null", Tokens.Null.getName(), -1 , -1);
	public static Token Obj = new Token(Tokens.Obj.getType(), "Obj", Tokens.Obj.getName(), -1, -1);
	public static Token Func = new Token(Tokens.Func.getType(), "Func", Tokens.Func.getName(), -1, -1);
	
	public int type;
	public String text;
	public String name;
	public int startRowNo;
	public int startColNo;
	
	public Token(int type, String text, String name, int startRowNo, int startColNo) {
		super();
		this.type = type;
		this.text = text;
		this.name = name;
		this.startRowNo = startRowNo;
		this.startColNo = startColNo;
	}
	
	public boolean isKeyword() {
		return Keywords.contains(this);
	}
	
	public Keywords getKeyword() {
		return Keywords.from(this.getText());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + type;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Token [type=" + type + ", text=" + text + ", name=" + name + ", startRowNo=" + startRowNo
				+ ", startColNo=" + startColNo + "]";
	}

	public int getType() {
		return type;
	}

	public String getText() {
		return text;
	}

	public String getName() {
		return name;
	}

	public int getStartRowNo() {
		return startRowNo;
	}

	public int getStartColNo() {
		return startColNo;
	}
}
