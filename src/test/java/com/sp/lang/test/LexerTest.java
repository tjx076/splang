package com.sp.lang.test;

import com.sp.lang.LangLexer;
import com.sp.lang.Lexer;
import com.sp.lang.Token;
import com.sp.lang.file.FileUtil;

public class LexerTest {

	public static void main(String[] args) throws Exception {
		String input = new String(FileUtil.readInputStream(
				LexerTest.class.getResourceAsStream("/lexer_test.spl")),
				"UTF-8");
		
		LangLexer lexer = new LangLexer(input);
		
		Token t ;
		while(Lexer.EOF_TYPE != (t = lexer.nextToken()).getType() ){
			System.out.println(t);
		}
		
		// Output:
		


	}
}
