package com.sp.lang.test;

import java.util.List;

import com.sp.lang.LangLexer;
import com.sp.lang.LangParser;
import com.sp.lang.ast.AST;
import com.sp.lang.file.FileUtil;

public class ParserFriendlyErrorMsgTest {
	
	public static void main(String[] args) throws Exception{
		String input = new String(FileUtil.readInputStream(
				ParserFriendlyErrorMsgTest.class.getResourceAsStream("/parser_friendly_error_msg_test.spl")),
				"UTF-8");
		
		LangLexer lexer = new LangLexer(input);
		
		LangParser parser = new LangParser(lexer);
		parser.parse();
		
		List<AST> asts = parser.getASTList();
		
		System.out.println(asts.size());
		
		for(AST ast : asts){
			System.out.println(ast);
		}
 	}
	
}
