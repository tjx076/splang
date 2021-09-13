package com.olcap.test;


import com.olcap.lang.Expr2;
import com.olcap.lang.LangLexer;
import com.olcap.lang.LangParser;
import com.olcap.lang.LangInterpreter;
import com.olcap.lang.Tuple;
import com.olcap.lang.file.FileUtil;

public class InterpreterTest {
	
	public static void main(String[] args) throws Exception{
		String input = new String(FileUtil.readInputStream(
				InterpreterTest.class.getResourceAsStream("/interpreter_test.ol")),
				"UTF-8");
		
		LangLexer lexer = new LangLexer(input);
		LangParser parser = new LangParser(lexer);
		LangInterpreter interpreter = new LangInterpreter(parser);
		System.out.println(interpreter.interpret());
		
		
 	}
	
	public static Double AnyUDFCall(Expr2<Integer, Integer, Double> expr2) {
		return expr2.apply(3, 4);
	}
	
	public static Tuple MultiValue(Object v1, Object v2, 
			Object v3, Object v4, Object v5, Object v6, Object v7, Object v8, Object v9, Object v10) {
		return new Tuple(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10);
	}
	
}
