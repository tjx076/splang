package com.sp.lang;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.sp.lang.file.FileUtil;

/**
 * 
 * 语法 Helper
 * 
 *
 */
@Deprecated
public class SyntaxHelper {
	
	static String syntaxText = null;
	
	static {
		try {
			syntaxText = new String(FileUtil.readResource("/splang.md"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public static enum SyntaxShortName {
		
		ObjDecl("### 对象声明(Object Declare)");

		String syntaxHead;
		
		SyntaxShortName(String syntaxHead) {
			this.syntaxHead = syntaxHead;
		}

		public String getSyntaxHead() {
			return syntaxHead;
		}
	}
	
	public static String getSyntax(SyntaxShortName syntaxShortName) {
		if(StringUtil.isEmpty(syntaxText)) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer("\n");
		boolean b = false;
		int hl =  getHeadLen(syntaxShortName.getSyntaxHead());
		
		String[] syntaxs = syntaxText.split("\n");
		for(String s : syntaxs) {
			if(syntaxShortName.getSyntaxHead().equals(s)) {
				sb.append(s).append("\n");
				b = true;
				continue;
			}
			
			if(b) {
				if(s.startsWith("#")) {
					int currHL = getHeadLen(s);
					if(currHL<=hl) {
						break;
					}
				}
				
				sb.append(s).append("\n");
			}
		}
		
		return sb.toString();
	}

	private static int getHeadLen(String s) {
		int i =0;
		for( ;i<s.length();i++) {
			char c = s.charAt(i);
			if('#' != c) {
				break;
			}
		}
		return i+1;
	}
}
