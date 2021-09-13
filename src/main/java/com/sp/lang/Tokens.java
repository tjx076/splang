package com.sp.lang;

public enum Tokens {
	
	Integer(2, "Integer"),
	Decimal(3, "Decimal"),
	
	String(4, "String"),
	
	Identifier(5, "Identifier"),
	RefIdentifier(6, "RefIdentifier"),
	IndexIdentifier(7, "IndexIdentifier"),
	
	LeftCurly(8, "LeftCurly"),
	RightCurly(9, "RightCurly"),
	
	LeftSquare(10, "LeftSquare"),
	RightSquare(11, "RightSquare"),
	
//	LEFT_ANGLE(12, "LeftAngle"),
//	RIGHT_ANGLE(13, "RightAngle"),
	
	LeftParenthesis(14, "LeftParenthesis"),
	RightParenthesis(15, "RightParenthesis"),
	
	Comma(16, "Comma"),
	Colon(17, "Colon"),
	
	AddOperator(19, "AddOperator"),
	SubOperator(20, "SubOperator"),
	MulOperator(21, "MulOperator"),
	DivOperator(18, "DivOperator"),
	ModOperator(22, "ModOperator"),
	
	ExprOperator(23, "ExprOperator"),
	
	AssignOperator(24, "AssignOperator"),
	
	EqualOperator(25, "EqualOperator"),
	NotEqualOperator(27, "NotEqualOperator"),
	GreaterThanOperator(28, "GreaterThanOperator"),
	GreaterThanEqualOperator(29, "GreaterThanEqualOperator"),
	LessThanOperator(30, "LessThanOperator"),
	LessThanEqualOperator(31, "LessThanEqualOperator"),
	
	NotOperator(26, "NotOperator"),
	AndOperator(32, "AndOperator"),
	OrOperator(33, "OrOperator"),
	
	Null(34, "Keywords Null"),
	True(35, "Keywords Boolean True"),
	False(36, "Keywords Boolean False"),
	Obj(37, "Keywords Obj"),
	Func(38, "Keywords Func");
	
	int type;
	String name;
	
	Tokens(int type, String name) {
		this.type = type;
		this.name = name;
	}

	
	public static Tokens fromType(int type) {
		for(Tokens t : Tokens.values()){
			if(t.getType() == type){
				return t;
			}
		}
		
		throw new UnsupportedTokenException("type " + type);
	}
	
	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}
}
