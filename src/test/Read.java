package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class Read{//�����ļ�
	public String read(String address,String TxtName,String postfix){//��ַ  �ļ���  ��׺
		File file=new File(address);
		if(!file.exists())//����ļ��в�����,�򴴽��ļ���
			file.mkdirs();
		String str = "";
		FileReader fr=null; 
		try {
			fr= new FileReader(address+TxtName+postfix);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		BufferedReader in=new BufferedReader(fr);
	//	String line = null;
		String line;
		try {
			while((line = in.readLine()) !=null){
				str = str + line;	
			}
			in.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return str;
	
	}

}