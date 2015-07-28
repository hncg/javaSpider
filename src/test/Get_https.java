package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Get_https{
	public String get_https(String str){
		Pattern p=Pattern.compile("<div class=\"bm_c\">[\\S|\\s]*<div class=\"bm_c\">");//Æ¥ÅäÖĞÎÄ£¬°üº¬×Ö·û
		Matcher m=p.matcher(str);
	//	System.out.println(m.group());
		if(m.find()){
			 	return m.group();
		}	
		else	return "";
	}  
}