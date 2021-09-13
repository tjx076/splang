package com.olcap.test;

import com.olcap.lang.LangLexer;
import com.olcap.lang.Lexer;
import com.olcap.lang.Token;
import com.olcap.lang.file.FileUtil;

public class LexerFriendlyErrorMsgTest {

	public static void main(String[] args) throws Exception {
		String input = new String(FileUtil.readInputStream(
				LexerFriendlyErrorMsgTest.class.getResourceAsStream("/lexer_friendly_error_msg_test.ol")),
				"UTF-8");
		
		LangLexer lexer = new LangLexer(input);
		
		Token t ;
		while(Lexer.EOF_TYPE != (t = lexer.nextToken()).getType() ){
			System.out.println(t);
		}
		
		// Output:
		


	}
}
