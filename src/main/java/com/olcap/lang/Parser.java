package com.olcap.lang;

import java.util.ArrayList;
import java.util.List;

public abstract class Parser {
	
	List<Token> tokens;
	int p = 0;
	Token t;
	List<List<Token>> rows;
	
	public Parser (Lexer input) {
		this.tokens = new ArrayList<Token>();
		this.rows = new ArrayList<List<Token>>();
		
		Token c;
		while((c=input.nextToken()).getType() != Lexer.EOF_TOKEN.getType()) {
			tokens.add(c);
		}
		tokens.add(Lexer.EOF_TOKEN);
		
		t = tokens.get(p);
		
		int prevRowNo = Integer.MIN_VALUE;
		List<Token> row = null;
		for(Token t : tokens) {
			int rowNo = t.getStartRowNo();
			
			if(prevRowNo!=rowNo) {
				prevRowNo = rowNo;
				row = new ArrayList<Token>();
				this.rows.add(row);
			}
			
			row.add(t);
		}

	}
	
	public Token match(int x) {
		if(t.getType() == x) {
			Token c = t;
			consume();
			return c;
		} else {
			throw new NoMatchException("expecting " + Tokens.fromType(x));
		}	
	}
	
	public void consume() {
		p++;
		setTo(p);
	}
	
	public void setTo(int p) {
		this.p=p;
		if(p>=tokens.size()){
			t = Lexer.EOF_TOKEN;
		} else {
			t = this.tokens.get(p);
		}
	}

	public String printErrorCode() {
		int rowNo = t.getStartRowNo();
		int rowIndex = getRowIndex(rowNo);
		
		int prevRow = rowIndex-1;
		int currRow = rowIndex;
		int nextRow = rowIndex+1;
		
		int prevRowNo = prevRow<0? Integer.MAX_VALUE : rows.get(prevRow).get(0).getStartRowNo();
		int currRowNo = rowNo;
		int nextRowNo = nextRow>rows.size()-1? Integer.MAX_VALUE : rows.get(nextRow).get(0).getStartRowNo();
		
		StringBuffer sb = new StringBuffer();
		sb.append("\n\n");
		sb.append( printRowNo(prevRowNo) ).append(printRow(prevRow));
		for(int i=prevRowNo+1; i<currRowNo; i++) {
			sb.append( printRowNo(i) ).append("\n");
		}
		sb.append( printRowNo(currRowNo) ).append(printRow(currRow));
		sb.append( printRowNo(currRowNo) ).append(printCursor(t.getStartColNo()));
		for(int i=currRowNo+1; i<nextRowNo; i++) {
			sb.append( printRowNo(i) ).append("\n");
		}
		sb.append( printRowNo(nextRowNo) ).append(printRow(nextRow));
		
		return sb.toString();
	}
	
	public String printRowNo(int rowNo) {
		if(rowNo<0 || rowNo>rows.size()-1) {
			return "";
		}
		
		String rowNoStr = rowNo+"";
		
		StringBuffer sb = new StringBuffer();
		sb.append(rowNoStr);
		for(int i=0;i< 5-rowNoStr.length(); i++) {
			sb.append(" ");
		}
		sb.append("|");
		sb.append(" ");
		
		return sb.toString();
	}
	
	public String printCursor(int colNo) {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<colNo; i++) {
			if(i==colNo-1) {
				sb.append("^");
			} else {
				sb.append(" ");
			}
		}
		sb.append("\n");
		return sb.toString();
	}
	
	public String printRow(int rowIndex) {
		if(rowIndex<0 || rowIndex>rows.size()-1) {
			return "\n";
		}
		
		StringBuffer sb = new StringBuffer();
		int prevEndColNo = 1;
		
		List<Token> row = rows.get(rowIndex);
		for(Token t : row) {
			int startColNo = t.getStartColNo();
			
			for(int i=0; i<startColNo-prevEndColNo; i++) {
				sb.append(" ");
			}
			
			sb.append(t.getText());
			prevEndColNo = startColNo + t.getText().length();
		}
		sb.append("\n");
		
		return sb.toString();
	}
	
	public int getRowIndex(int rowNo) {
		int i = -1;
		for(List<Token> row : rows) {
			i++;
			Token token = row.get(0);
			if(rowNo == token.getStartRowNo()) {
				return i;
			}
		}
		return -1;
	}
}
