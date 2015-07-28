package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Get_article{
	public  String  parse(String s,int i) {
		Pattern p=Pattern.compile("(<td class=\"t_f\"[\\S|\\s]*?</td>){1}");
		Matcher m=p.matcher(s);
		String str="";
		int k=0;
		while(m.find()&&k<i){
				str=str+m.group().replaceAll("<[\\S|\\s]*?>|[&\\w*;]","");
				k++;
		}
		return str;
}
}
