package com.olcap.lang;

import java.util.Stack;

/**
 * 
 * 词法解析器
 * 
 * @author tangdou
 *
 */
public class LangLexer extends Lexer {
		
	private Stack<Integer> stack = new Stack<Integer>();
	
	public LangLexer (String input){
		super(input);
	}

	@Override
	public Token nextToken() {
		while (c != EOF) {
			
			try {
				
				if(c==' '||c=='\t'||c=='\r'||c=='\n'){
					WS();
					continue;
				}
				
				if(c=='/'){
					
					if(speculateComment()){
						comment();
						continue;
					}
					
					match('/');
					return buildNewToken(Tokens.DivOperator, "/");
				}
				
				if(c=='+') {
					match('+');
					return buildNewToken(Tokens.AddOperator, "+");
				}
				
				if(c=='-') {
					
					if(speculateExprOperator()) {
						return exprOperator();
					}
					
					match('-');
					return buildNewToken(Tokens.SubOperator, "-");
				}
				
				if(c=='*') {
					match('*');
					return buildNewToken(Tokens.MulOperator, "*");
				}
				
				if(c=='%') {
					match('%');
					return buildNewToken(Tokens.ModOperator, "%");
				}
				
				if(c=='=') {
					
					if(speculateEqualOperator()){
						return equalOperator();
					}
					
					match('=');
					return buildNewToken(Tokens.AssignOperator, "=");
				}
				
				if(c=='!') {
					
					if(speculateNotEqualOperator()){
						return notEqualOperator();
					}
					
					match('!');
					return buildNewToken(Tokens.NotOperator, "!");
				}
				
				if(c=='>') {
					
					if(speculateGTE()){
						return gte();
					}
					
					match('>');
					return buildNewToken(Tokens.GreaterThanOperator, ">");
				}
				
				if(c=='<') {
					
					if(speculateLTE()){
						return lte();
					}
					
					match('<');
					return buildNewToken(Tokens.LessThanOperator, "<");
				}
				
				if(c=='&') {
					
					match('&');
					match('&');
					
					return buildNewToken(Tokens.AndOperator, "&&");
				}
				
				if(c=='|') {
					
					match('|');
					match('|');
					
					return buildNewToken(Tokens.OrOperator, "||");
				}
				
				
				
				if(isNumber()) {
					return number();
				}
				
				if(c=='"'){
					return string();
				}
				
				if(c=='_'||isLetter()) {
					return symbol();
				}
				
				if(c=='{') {
					match('{');
					return buildNewToken(Tokens.LeftCurly, "{");
				}
				if(c=='}'){
					match('}');
					return buildNewToken(Tokens.RightCurly, "}");
				}
				
				if(c=='[') {
					match('[');
					return buildNewToken(Tokens.LeftSquare, "[");
				}
				if(c==']'){
					match(']');
					return buildNewToken(Tokens.RightSquare, "]");
				}

				if(c=='(') {
					match('(');
					return buildNewToken(Tokens.LeftParenthesis, "(");
				}
				if(c==')'){
					match(')');
					return buildNewToken(Tokens.RightParenthesis, ")");
				}
				
				if(c==',') {
					match(',');
					return buildNewToken(Tokens.Comma, ",");
				}
				if(c==':'){
					match(':');
					return buildNewToken(Tokens.Colon, ":");
				}
				
			} catch (NoMatchException e) {
				throw new InvalidException(errorMsg("invalid char: "+c, e), e);
			} catch (IncorrectUseKeywordsException e) {
				throw new InvalidException(errorMsg(null, e), e);
			} catch(Exception e) {
				throw new InvalidException(errorMsg(null, null), e);
			}
			
			throw new InvalidException(errorMsg("invalid char: "+c, null));
		}
		
		return EOF_TOKEN;
	}

	private Token lte() {
		match('<');
		match('=');
		
		return buildNewToken(Tokens.LessThanEqualOperator, "<=");
	}

	private boolean speculateLTE() {
		boolean success = true;
		mark();
		try{
			
			match('<');
			match('=');
			
		} catch(RecognitionException|
				NoMatchException e) {
			success =false;
		}
		release();
		return success;
	}

	private Token gte() {
		match('>');
		match('=');
		
		return buildNewToken(Tokens.GreaterThanEqualOperator, ">=");
	}

	private boolean speculateGTE() {
		boolean success = true;
		mark();
		try{
			
			match('>');
			match('=');
			
		} catch(RecognitionException|
				NoMatchException e) {
			success =false;
		}
		release();
		return success;
	}

	private Token notEqualOperator() {
		match('!');
		match('=');
		
		return buildNewToken(Tokens.NotEqualOperator, "!=");
	}

	private boolean speculateNotEqualOperator() {
		boolean success = true;
		mark();
		try{
			
			match('!');
			match('=');
			
		} catch(RecognitionException|
				NoMatchException e) {
			success =false;
		}
		release();
		return success;
	}

	private Token equalOperator() {
		match('=');
		match('=');
		
		return buildNewToken(Tokens.EqualOperator, "==");
	}

	private boolean speculateEqualOperator() {
		boolean success = true;
		mark();
		try{
			
			match('=');
			match('=');
			
		} catch(RecognitionException|
				NoMatchException e) {
			success =false;
		}
		release();
		return success;
	}

	private Token exprOperator() {
		
		match('-');
		match('>');
		
		
		return buildNewToken(Tokens.ExprOperator, "->");
	}

	private boolean speculateExprOperator() {
		boolean success = true;
		mark();
		try{
			
			match('-');
			match('>');
			
		} catch(RecognitionException|
				NoMatchException e) {
			success =false;
		}
		release();
		return success;
	}

	private boolean speculateComment() {
		boolean success = true;
		mark();
		try{
			
			match('/');
			match('/');
			
		} catch(RecognitionException|
				NoMatchException e) {
			success =false;
		}
		release();
		return success;
	}

	private Token symbol() {
		if(speculateRefIdentifier()) {
			return refIdentifier();
		}
		if(speculateIndexIdentifier()) {
			return indexIdentifier();
		}
//		if(speculateID()) {
			return ID();
//		}
	}

	private Token indexIdentifier() {
		Token var = null;
		Token index = null;
		
		var = ID();
		match('[');
		
		if(isNumber()){
			index = integer();
		} else if(isLetter()) {
			index = ID();
		}
		match(']');
		
		StringBuilder sb = new StringBuilder();
		sb.append(var.getText()).append('[').append(index.getText()).append("]");
		String tokenText = sb.toString();
		
		if(var.isKeyword()){
			throw new IncorrectUseKeywordsException("Can not use Keyword(" + var.getText() + ") in IndexIdentifier("+tokenText+")");
		}
		if(index.isKeyword()){
			throw new IncorrectUseKeywordsException("Can not use Keyword(" + index.getText() + ") in IndexIdentifier("+tokenText+")");
		}
		
		return buildNewToken(Tokens.IndexIdentifier, tokenText);	
	}

	private boolean speculateIndexIdentifier() {
		boolean success = true;
		mark();
		try{
			ID();
			match('[');
			if(isNumber()){
				integer();
			} else if(isLetter()) {
				ID();
			} else {
				throw new RecognitionException("Need Integer or Identifier in the [] of IndexIdentifier");
			}
			match(']');
		} catch(RecognitionException|
				NoMatchException e) {
			success =false;
		}
		release();
		return success;
	}
	
	

	private Token refIdentifier() {
		Token bef = null;
		Token aft = null;
		bef = ID();
		match('.');
		aft = ID();
		
		StringBuilder sb = new StringBuilder();
		sb.append(bef.getText()).append('.').append(aft.getText());
		String tokenText = sb.toString();
		
		if(bef.isKeyword()){
			throw new IncorrectUseKeywordsException("Can not use Keyword(" + bef.getText() + ") in RefIdentifier("+tokenText+")");
		}
		if(aft.isKeyword()){
			throw new IncorrectUseKeywordsException("Can not use Keyword(" + aft.getText() + ") in RefIdentifier("+tokenText+")");
		}
		
		return buildNewToken(Tokens.RefIdentifier, tokenText);
	}

	private boolean speculateRefIdentifier() {
		boolean success = true;
		mark();
		try{
			ID();
			match('.');
			ID();
		} catch(RecognitionException|
				NoMatchException e) {
			success =false;
		}
		release();
		return success;
	}

	private Token ID() {
		StringBuilder sb = new StringBuilder();
		
		while(isLetter()||c=='_'){
			sb.append(c);
			consume();
		}
		
		String text = sb.toString();
		
		Keywords kw = Keywords.from(text);
		if(kw!=null){
			return copyNewToken(kw.getToken(), text);
		}
		
		return buildNewToken(Tokens.Identifier, text);
	}

//	private boolean speculateID() {
//		boolean success = true;
//		mark();
//		try{
//			ID();
//		} catch(RecognitionException|
//				NoMatchException e) {
//			success =false;
//		}
//		release();
//		return success;
//	}

	private Token string() {
		StringBuilder sb = new StringBuilder();
		match('"');
		while(c!='"'){
			if(c==EOF){
				return EOF_TOKEN;
			}
			sb.append(c);
			consume();
		}
		match('"');
		
		String text = "\"" + sb.toString() + "\"";
		return buildNewToken(Tokens.String, text);
	}

	Token number() {
		if(speculateDecimal()) {
			return decimal();
		}
//		if(speculateInteger()) {
			return integer();
//		}
	}
	
	private Token decimal() {
		StringBuilder sb = new StringBuilder();
		
		Token intPart = integer();
		match('.');
		Token decPart = integer();
		
		sb.append(intPart.getText()).append('.').append(decPart.getText());
		
		return buildNewToken(Tokens.Decimal, sb.toString());
	}

	private boolean speculateDecimal() {
		boolean success = true;
		mark();
		try{
			integer();
			match('.');
			integer();
		} catch(RecognitionException
				|NoMatchException e) {
			success =false;
		}
		release();
		return success;
	}

	private Token integer() {
		StringBuilder sb = new StringBuilder();

		while(isNumber()){
			sb.append(c);
			consume();
		}
		
		String text = sb.toString();
		return buildNewToken(Tokens.Integer, text);
	}

//	private boolean speculateInteger() {
//		boolean success = true;
//		mark();
//		try{
//			integer();
//		} catch(RecognitionException
//				|NoMatchException e) {
//			success =false;
//		}
//		release();
//		return success;
//	}
	
	
	
	void mark() {
		stack.push(p);
	}
	
	void release() {
		p = stack.pop();
		setTo(p);
	}
	
	void comment() {
		match('/');
		match('/');
		while(c != '\n'){
			if(c == EOF){
				return;
			}
			
			consume();
		}
		match('\n');
	}
	
	void WS() {
		while(c==' ' ||
				c == '\n' ||
				c == '\r' ||
				c == '\t') {
			consume();
		}
	}
	
	boolean isLetter() {
		return c>='a'&&c<='z' || c>='A'&&c<='Z';
	}
	
	boolean isNumber() {
		return c>='0'&&c<='9';
	}

	public Token buildNewToken(Tokens t, String text) {
		int p = this.p - text.length();
		int[] rc = getRowColumn(p);
		return new Token(t.getType(), text, t.getName(), rc[0], rc[1]);
	}
	
	public Token copyNewToken(Token t, String text) {
		int p = this.p - text.length();
		int[] rc = getRowColumn(p);
		return new Token(t.getType(), text, t.getName(), rc[0], rc[1]);
	}
	
	@Override
	public String getTokenName(int tokenType) {
		return Tokens.fromType(tokenType).getName();
	}

	public String errorMsg(String headMsg, Exception e) {
		StringBuffer sb = new StringBuffer();
		if(headMsg!=null) {
			sb.append(headMsg);
			sb.append(", ");
		}
		if(e!=null) {
			sb.append(e.getMessage());
			sb.append(", ");
		}
		sb.append(printErrorCode());
		
		return sb.toString();
	}
}
