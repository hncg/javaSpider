package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Get_replies{
	Collection<String> data = new ArrayList<String>();
	public  void parse(String s) {
		String str="";
		Pattern p=Pattern.compile("(class=\"xi2\">[\\S\\s]*?</a>)+");//匹配a标签及其内容
	//	Pattern p=Pattern.compile("[\\u4e00-\\u9fa5]+");
		Matcher m=p.matcher(s);
		while(m.find()){
				str=str+m.group();
		}
		str=str.replace("xi2", "xi");
		p=Pattern.compile("\\d+");//匹配a标签及其内容
		m=p.matcher(str);
		while(m.find()){
					data.add(m.group());
			}
}
}

