package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Get_time{
	Collection<String> data = new ArrayList<String>();
	public  void parse(String s) { 
		String str="";			   
		Pattern p=Pattern.compile("(<td class=\"by\">[\\S\\s]*?</td>)+");//匹配a标签及其内容
		Matcher m=p.matcher(s);
		while(m.find()){
				str = str+m.group();
		}
		 p=Pattern.compile("(<em[\\S|\\s]*?</em>)+");//匹配中文，包含字符
		 m=p.matcher(str);
		 while(m.find()){
				str=m.group().replaceAll("<[\\S|\\s]*?>","");
				data.add(str);
				m.find();
		}
}
}
