package test;

import java.io.*;
public class Write{//文件写入,true写入末尾
	public void write(String str,String address,String TxtName,String postfix,boolean append){//地址  文件名 后缀
		File file=new File(address);
//		System.out.println("sssssssssss");
		if(!file.exists())//如果文件夹不存在,则创建文件夹
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