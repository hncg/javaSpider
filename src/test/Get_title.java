package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Get_title{
	public  String parse(String s) {
		Pattern p=Pattern.compile("(<a[\\S\\s]*?</a>)+");//匹配a标签及其内容
		Matcher m=p.matcher(s);
		while(m.find()){
			if(m.group().indexOf("target")!=-1){
					return m.group().replaceAll("<[\\S\\s]*?>|(&\\w*?;)","");
			}	
		}	
					return ""; 
}
}
