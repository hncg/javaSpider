package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Get_excel{
public	HashMap<String,String>	get_excel(String excel_address) throws IOException, BiffException{
	HashMap<String, String> map=new HashMap<String, String>();
	//创建输入流
	InputStream is=new FileInputStream(excel_address);
	//获取Excel文件对象
	Workbook wb=Workbook.getWorkbook(is);
	//获取文件的指定工作表 默认的第一个
	Sheet sheet=wb.getSheet(0);
	//第一行从0开始
	for(int i=0;i<sheet.getRows();i++){
		//获取第i行第j列
		//将excel中的内容存储到hashmap中,情感强度和强度用"_"分开
		map.put(sheet.getCell(0,i).getContents(), sheet.getCell(1,i).getContents()+"_"+sheet.getCell(2,i).getContents());
//		System.out.println( "键值="+sheet.getCell(1,i).getContents()+"_"+sheet.getCell(2,i).getContents());
	}
	return map;
	/*
	 * 遍历
	for(Entry<String,String> entry:	map.entrySet()){
		System.out.println(entry.getKey()+"--->"+entry.getValue());
	}
	*/
}
}
