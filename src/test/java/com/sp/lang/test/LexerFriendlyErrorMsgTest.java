package com.sp.lang.test;

import com.sp.lang.LangLexer;
import com.sp.lang.Lexer;
import com.sp.lang.Token;
import com.sp.lang.file.FileUtil;

public class LexerFriendlyErrorMsgTest {

	public static void main(String[] args) throws Exception {
		String input = new String(FileUtil.readInputStream(
				LexerFriendlyErrorMsgTest.class.getResourceAsStream("/lexer_friendly_error_msg_test.spl")),
				"UTF-8");
		
		LangLexer lexer = new LangLexer(input);
		
		Token t ;
		while(Lexer.EOF_TYPE != (t = lexer.nextToken()).getType() ){
			System.out.println(t);
		}
		
		// Output:
		


	}
}
