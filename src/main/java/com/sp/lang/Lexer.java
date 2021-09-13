package com.sp.lang;

public abstract class Lexer {
	
	public static final char EOF = (char)-1;
	public static final int EOF_TYPE = 1;
	public static final Token EOF_TOKEN = new Token(EOF_TYPE, "<EOF>", "EOF", -1, -1);
	
	String input;
	int p = 0;
	char c;
	String[] rows;
	
	public Lexer(String input) {
		this.input = input;
		if(StringUtil.isEmpty(this.input)){
			c = EOF;
		} else {
			c = this.input.charAt(p);
			rows = input.split("\n|\r");
		}
	}
	
	public abstract Token nextToken();
	public abstract String getTokenName(int tokenType);
	
	public void consume() {
		p++;
		setTo(p);
	}
	
	public void match(char x) {
		if (c == x) {
			consume();
		} else {
			throw new NoMatchException("expecting char: " + x);
		}
		
	}
	
	public int[] getRowColumn(int p) {
		int rowNo = 1;
		int colNo = 1;
		
		int charNum = 0;
		for(int i=0; i<rows.length; i++) {
			String row = rows[i];
			
			charNum += row.length()+1;
			
			if(p+1 <= charNum) {
				rowNo = i+1;
				
				colNo = (p+1) - (charNum - (row.length()+1));
				break;
			}
		}
		
		return new int[]{rowNo, colNo};
	}
	
	public String printErrorCode() {
		int[] rc = getRowColumn(p);
		int rowNo = rc[0];
		int colNo = rc[1];
		
		int prevRow = rowNo - 1;
		int currRow = rowNo;
		int nextRow = rowNo + 1;
		
		StringBuffer sb = new StringBuffer();
		sb.append("\n\n");
		sb.append( printRowNo(prevRow) ).append(prevRow<1?"": rows[prevRow-1]).append("\n");
		sb.append( printRowNo(currRow) ).append(rows[currRow-1]).append("\n");
		sb.append( printRowNo(currRow) ).append(printCursor(rows[currRow-1], colNo)).append("\n");
		sb.append( printRowNo(nextRow) ).append(nextRow>rows.length?"": rows[nextRow-1]).append("\n");
		
		return sb.toString();
	}
	
	public String printRowNo(int rowNo) {
		if(rowNo<1 || rowNo>rows.length) {
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
	
	public String printCursor(String row, int colNo) {
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i<colNo; i++) {
			if(i==colNo-1) {
				sb.append("^");
			} else {
				char c = row.charAt(i);
				if(c=='\t') {
					sb.append("\t");
				} else {
					sb.append(" ");
				}
			}
		}
		
		return sb.toString();
	}
	
	public void setTo(int p) {
		this.p = p;
		if(p>=input.length()){
			c = EOF;
		} else {
			c = this.input.charAt(p);
		}
	}
}
