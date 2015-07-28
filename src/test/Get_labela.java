package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Get_labela{
	Collection<String> data = new ArrayList<String>();
	public  void parse(String s) { 
		Pattern p=Pattern.compile("(((<th class=\"common\">)|(<th class=\"new\">))[\\S\\s]*?</th>)+");//匹配a标签及其内容
	//	Pattern p=Pattern.compile("[\\u4e00-\\u9fa5]+");
		Matcher m=p.matcher(s);
		while(m.find()){
				data.add(m.group());
		}
}
}