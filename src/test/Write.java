package test;

import java.io.*;
public class Write{//�ļ�д��,trueд��ĩβ
	public void write(String str,String address,String TxtName,String postfix,boolean append){//��ַ  �ļ��� ��׺
		File file=new File(address);
//		System.out.println("sssssssssss");
		if(!file.exists())//����ļ��в�����,�򴴽��ļ���
			file.mkdirs();
			FileWriter fw = null;
			try {
				fw = new FileWriter(address+TxtName+postfix,append);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		BufferedWriter bw=new BufferedWriter(fw);
		try {
			bw.write(str.toString());
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}