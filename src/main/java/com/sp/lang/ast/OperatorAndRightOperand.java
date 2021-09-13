package com.sp.lang.ast;

import com.sp.lang.Token;

public class OperatorAndRightOperand<T> {
	
	Token operator;
	T rightOperand;
	
	
	
	public OperatorAndRightOperand(Token operator, T rightOperand) {
		super();
		this.operator = operator;
		this.rightOperand = rightOperand;
	}
	public Token getOperator() {
		return operator;
	}
	public void setOperator(Token operator) {
		this.operator = operator;
	}
	public T getRightOperand() {
		return rightOperand;
	}
	public void setRightOperand(T rightOperand) {
		this.rightOperand = rightOperand;
	}
	@Override
	public String toString() {
		return "OperatorAndRightOperand [operator=" + operator + ", rightOperand=" + rightOperand + "]";
	}
}
