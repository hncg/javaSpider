package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Get_time{
	Collection<String> data = new ArrayList<String>();
	public  void parse(String s) { 
		String str="";			   
		Pattern p=Pattern.compile("(<td class=\"by\">[\\S\\s]*?</td>)+");//ƥ��a��ǩ��������
		Matcher m=p.matcher(s);
		while(m.find()){
				str = str+m.group();
		}
		 p=Pattern.compile("(<em[\\S|\\s]*?</em>)+");//ƥ�����ģ������ַ�
		 m=p.matcher(str);
		 while(m.find()){
				str=m.group().replaceAll("<[\\S|\\s]*?>","");
				data.add(str);
				m.find();
		}
}
}
