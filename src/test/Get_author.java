package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Get_author{
	Collection<String> data = new ArrayList<String>();
	public  void parse(String s) { 
		String str="";			   
		Pattern p=Pattern.compile("(<cite>[\\S\\s]*?</cite>)+");//ƥ��a��ǩ��������
	//	Pattern p=Pattern.compile("[\\u4e00-\\u9fa5]+");
		Matcher m=p.matcher(s);
		while(m.find()){
				str = str+m.group();
		}
		 p=Pattern.compile("(c=\"1\">[\\S|\\s]*?</a>)+");//ƥ�����ģ������ַ�
		 m=p.matcher(str);
		 while(m.find()){
				str=m.group().replace("</a>","");
				str = str.replace("c=\"1\">","");
				data.add(str);
			 	m.find();
		}
}
}