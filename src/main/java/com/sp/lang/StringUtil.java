package com.sp.lang;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s.trim());
	}
	
	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}
	
	
	public static String[] splitWith(String s, char sep) {
		
		List<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i <s.length(); i++) {
			
			char c = s.charAt(i);
			
			if(c==sep){
				
				list.add(sb.toString());
				sb.setLength(0);
				
				continue;
			}
			
			sb.append(c);
		}
		
		if(sb.length()>0){
			list.add(sb.toString());
		}
		
		return list.toArray(new String[]{});
	}
}
