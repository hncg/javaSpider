package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Get_http{
	public  String parse(String s) {
		Pattern p=Pattern.compile("(<a[\\S\\s]*?</a>)+");//匹配a标签及其内容
		Matcher m=p.matcher(s);
		while(m.find()){
			if(m.group().indexOf("target")!=-1){
				 p=Pattern.compile("http://(\\S)*|(\\s)*(.html|.php|.asp|.cn|.com)");//匹配中文，包含字符
				 m=p.matcher(m.group());	
				 if(m.find())
					 	return m.group().replace("\"","");
				 else 	return "";
			}	
		}	
					return ""; 
}
}