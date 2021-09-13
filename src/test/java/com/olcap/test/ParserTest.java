package com.olcap.test;

import java.util.List;

import com.olcap.lang.LangLexer;
import com.olcap.lang.LangParser;
import com.olcap.lang.ast.AST;
import com.olcap.lang.file.FileUtil;

public class ParserTest {
	
	public static void main(String[] args) throws Exception{
		String input = new String(FileUtil.readInputStream(
				ParserTest.class.getResourceAsStream("/parser_test.ol")),
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
