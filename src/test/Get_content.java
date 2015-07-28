package test;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTMLEditorKit.Parser;
public class Get_content{
	StringBuffer content;
	String END_FILE="\r\n";
	SimpleDateFormat df=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss:SSSSSS");
	public StringBuffer get_content(String url)  {
		//-----------------------------------抓取跳转之前的页面---------------------------------------------------
		content=new StringBuffer();
		String HOST=null;String GET=null;
		Pattern p=Pattern.compile("(//){1}");
		Matcher m=p.matcher(url);
				if(m.find()){
				url = url.substring(m.end());
				p=Pattern.compile("(/){1}");
				m=p.matcher(url);
				if(m.find()){
				GET = url.substring(m.end()-1);
				HOST = url.substring(0,m.end()-1);
				}
				}
		Socket socket;
		socket=null;
		try {
		    socket=new Socket(HOST,80);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		try {
			socket.setSoTimeout(50000);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		InputStream in;
		in=null;
		try {
		    in=socket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("11111111");
			//e.printStackTrace();
		}
		InputStreamReader ir;ir=new InputStreamReader(in);
		BufferedReader br;br=new BufferedReader(ir);
		OutputStreamWriter or;or=null;
				try {
					or=new OutputStreamWriter(socket.getOutputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
		StringBuilder get = new StringBuilder();		
		get.append("GET "+GET+" HTTP/1.1\r\n");
		get.append("Host: "+HOST+"\r\n");
		get.append("Connection: Keep-Alive\r\n");
		get.append(END_FILE);
		try {
			or.write(get.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		try {
			or.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			//e.printStackTrace();
		}
		try {
			String line="";
			while((line=br.readLine())!=null){
				content.append(line+END_FILE);
			}
		} catch (IOException e) {
			content.setLength(0);
			// TODO Auto-generated catch block
//			//e.printStackTrace();
			return content;
		}
		try {
			ir.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			//e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			//e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return content;
	
//-----------------------------------抓取跳转之后的页面---------------------------------------------------
		/*
	String COOKIE1=null;String COOKIE2=null;String COOKIE3=null;
	if(content.toString().indexOf("Set-Cookie")!=-1){
	int begin = content.toString().indexOf("Set-Cookie");
	int end = content.toString().indexOf("httponly");
	int begin1 = content.toString().lastIndexOf("Set-Cookie");
	
	int end1 = content.toString().lastIndexOf("httponly");
//	int end2 = content.toString().lastIndexOf("flydragon");
	System.out.println("content="+content.toString());
	COOKIE1=content.toString().substring(begin+11,end+8);
	COOKIE2 = content.toString().substring(begin1+12,end1+8);

//	System.out.println("COOKIE1="+COOKIE1);
//	System.out.println("COOKIE2="+COOKIE2);
	
//	COOKIE3 = content.toString().substring(end2,end2+14);
//	System.out.println("COOKIE3="+COOKIE3);
	
//	COOKIE2 = COOKIE2;
	COOKIE2=COOKIE1+";"+COOKIE2;
	}//System.out.print("COOKIE2="+COOKIE2);
	content=new StringBuffer();
	try {
	    socket=new Socket(HOST,80);
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
//		//e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
//		//e.printStackTrace();
	}
	try {
		socket.setSoTimeout(50000);
	} catch (SocketException e1) {
		// TODO Auto-generated catch block
//		e1.printStackTrace();
	}
	try {
	    in=socket.getInputStream();
	} catch (IOException e) {
		// TODO Auto-generated catch block
//		//e.printStackTrace();
	}
	 ir=new InputStreamReader(in);
	 br=new BufferedReader(ir);
			try {
				or=new OutputStreamWriter(socket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				//e.printStackTrace();
			}
get = new StringBuilder();
get.append("GET "+GET+" HTTP/1.1\r\n");
get.append("Host: "+HOST+"\r\n");
get.append("Connection: Keep-Alive\r\n");
get.append("Cookie: "+COOKIE2+"\r\n");
get.append("\r\n");
try {
	or.write(get.toString());
} catch (IOException e) {
	// TODO Auto-generated catch block
//	//e.printStackTrace();
}
try {
	or.flush();
} catch (IOException e) {
	// TODO Auto-generated catch block
//	//e.printStackTrace();
}
try {
//	System.out.println("大量字符连接前时间="+df.format(new Date()));
	String line="";
	while((line=br.readLine())!=null){
		content.append(line+END_FILE);
	}
//	System.out.println("大量字符连接后时间="+df.format(new Date()));
} catch (IOException e) {
	content.setLength(0);
	// TODO Auto-generated catch block
	//e.printStackTrace();
}
try {
	ir.close();
} catch (IOException e) {
	// TODO Auto-generated catch block
	//e.printStackTrace();
}
try {
	br.close();
} catch (IOException e) {
	// TODO Auto-generated catch block
	//e.printStackTrace();
}
try {
	socket.close();
} catch (IOException e) {
	// TODO Auto-generated catch block
	//e.printStackTrace();
}
try {
	in.close();
} catch (IOException e) {
	// TODO Auto-generated catch block
	//e.printStackTrace();
}
	return content;
		*/
		}
	
}
