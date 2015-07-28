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
	//����������
	InputStream is=new FileInputStream(excel_address);
	//��ȡExcel�ļ�����
	Workbook wb=Workbook.getWorkbook(is);
	//��ȡ�ļ���ָ�������� Ĭ�ϵĵ�һ��
	Sheet sheet=wb.getSheet(0);
	//��һ�д�0��ʼ
	for(int i=0;i<sheet.getRows();i++){
		//��ȡ��i�е�j��
		//��excel�е����ݴ洢��hashmap��,���ǿ�Ⱥ�ǿ����"_"�ֿ�
		map.put(sheet.getCell(0,i).getContents(), sheet.getCell(1,i).getContents()+"_"+sheet.getCell(2,i).getContents());
//		System.out.println( "��ֵ="+sheet.getCell(1,i).getContents()+"_"+sheet.getCell(2,i).getContents());
	}
	return map;
	/*
	 * ����
	for(Entry<String,String> entry:	map.entrySet()){
		System.out.println(entry.getKey()+"--->"+entry.getValue());
	}
	*/
}
}
