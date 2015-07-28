package test;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import jxl.read.biff.BiffException;
public class test 
{ 
	static final int WIDTH=500;
	static final int HEIGHT=400;
	static  int Page=1;
	public static void main(String []args){
		JFrame fm=new JFrame("红网网络情绪监控系统");
		JButton bt_start=new JButton("开始");
		JButton bt_stop=new JButton("结束");
		JTextPane jtp =new JTextPane();
		JScrollPane jp=new JScrollPane(jtp);
		JLabel jl=new JLabel("请输出要抓取的页数");
		JTextField jf=new JTextField("1");
		fm.setBounds(200, 100,WIDTH, HEIGHT);
		fm.setResizable(false);
		fm.setLayout(null);
		bt_start.setBounds(150, 100, 70, 30);
		bt_stop.setBounds(250, 100, 70, 30);
		jf.setBounds(230,10, 100,30);
		jl.setBounds(100, 10, 150, 30);
//		jtp.setBounds(0, 200, 500,400);此语句报错
		jp.setBounds(0, 200, 500,200);
		fm.add(bt_start);
		fm.add(bt_stop);
		fm.add(jp);
		fm.add(jf);
		fm.add(jl);
		fm.setLocationRelativeTo(null);
		fm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bt_start.addActionListener(new start(jf,jtp));
		bt_stop.addActionListener(new stop());
		fm.setVisible(true);	
		//	new UI(WIDTH,HEIGHT);
		
	}
}
class UI extends Frame{
	private static final long serialVersionUID = 1L;
	
}

class stop implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);
		
	}

	
}

class start implements ActionListener{
	private JTextPane jtp;
	JTextField jf;
	
	public start(JTextField jf,JTextPane jtp){
		this.jtp=jtp;
		this.jf=jf;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Thread1 td=new Thread1(jf,jtp);
		new Thread(td).start();
	}

	
}
class Thread1 implements Runnable{
	private int pages;
	private JTextPane jtp;
	JTextField jf;
	
	Thread1(JTextField jf,JTextPane jtp){
		this.jtp=jtp;
		this.jf=jf;
	}
	public void run() {
		// TODO Auto-generated method stub
		this.pages=Integer.parseInt(jf.getText());
		// TODO Auto-generated method stub
		int deep_control=1;//控制抓取文章的楼层,1表示一层 
		// pages=20;//默认设置抓取100页
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String day_file=df.format(new Date())+'/';
		String []areas1={"长沙","株洲","湘潭","衡阳","岳阳","益阳","常德","邵阳","娄底","永州","郴州","怀化","湘西","张家界"};
//		String day_file="";

		String [][]areas={
				  {
						"1","2","3","4","5","6","7","8","9","10","11","12","13","14"
				  },
				  {day_file+"长沙",day_file+"株洲",day_file+"湘潭",day_file+"衡阳",day_file+"岳阳",day_file+"益阳",day_file+"常德",day_file+"邵阳",day_file+"娄底",day_file+"永州",day_file+"郴州",day_file+"怀化",day_file+"湘西",day_file+"张家界"				  
				  },
				  {
					"http://bbs.rednet.cn/forum-1757-1.html","http://bbs.rednet.cn/forum-68-1.html","http://bbs.rednet.cn/forum-69-1.html",
					"http://bbs.rednet.cn/forum-70-1.html","http://bbs.rednet.cn/forum-71-1.html","http://bbs.rednet.cn/forum-72-1.html",
					"http://bbs.rednet.cn/forum-73-1.html","http://bbs.rednet.cn/forum-74-1.html","http://bbs.rednet.cn/forum-75-1.html",
					"http://bbs.rednet.cn/forum-76-1.html","http://bbs.rednet.cn/forum-77-1.html","http://bbs.rednet.cn/forum-78-1.html",
					"http://bbs.rednet.cn/forum-79-1.html","http://bbs.rednet.cn/forum-80-1.html"
				  }
		  };	
		Creat_folder creat_folder=new Creat_folder();
		
		for(int i=2;i<3;i++){
	  	 	for(int j=0;j<14;j++){	
	  	 		String url=areas[i][j];
	  	 		String home_page_address="./"+areas[i-1][j]+"/home_page/";//存放各个帖子首页地址
//	  	 		System.out.println(home_page_address);System.exit(0);
	  	 		creat_folder.creat_folder(home_page_address,jtp);
	  	 		String author_address="./"+areas[i-1][j]+"/author/";//存放作者名称地址
	  	 		creat_folder.creat_folder(author_address,jtp);
	  			String title_address="./"+areas[i-1][j]+"/title/";//存放标题地址
	  			creat_folder.creat_folder(title_address,jtp);
	  			String time_address="./"+areas[i-1][j]+"/time/";//存放时间地址
	  			creat_folder.creat_folder(time_address,jtp);
	  			String article_address="./"+areas[i-1][j] +"/article/";//存放帖子里面的文章地址
	  			creat_folder.creat_folder(article_address,jtp);
	  			String replies_address="./"+areas[i-1][j]+"/replies/";//存放回复数
	  			creat_folder.creat_folder(replies_address,jtp);
	  			String url_address="./"+areas[i-1][j]+"/url/";//存放url
	  			creat_folder.creat_folder(url_address,jtp);
	  			String postfix=".html";//保存的文件的后缀名
	  			
	  			//	SimpleDateFormat df=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss:SSSSSS");
	  				//只需一个线程抓取首页
//	  			   System.out.println(home_page_address);System.exit(0);
	  				Get_homepage get_homepage=new Get_homepage(url, home_page_address, postfix,pages,jtp);
	  				Gets gets=new Gets(home_page_address, postfix, article_address, replies_address, time_address, author_address, title_address,url_address,deep_control,pages,jtp);
	  				Thread  test=new Thread(get_homepage);
	  				test.start();
	   				//四个线程抓取文章
	  				Thread test1=	new Thread(gets);
	  				Thread test2=	new Thread(gets);
		  			test1.start();
		  			test2.start();
	  				if(j==3){
//	  					System.out.println(j);
	  					try {
	  						test1.join();
							test2.join();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
//							e1.printStackTrace();
						}
	  				} 
		  			if(j==7){
//		  				System.out.println(j);
	  					try {
	  						test1.join();
							test2.join();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
//							e1.printStackTrace();
						}
	  				} 
		  			if(j==11){
//		  				System.out.println(j);
	  					try {
	  						test1.join();
							test2.join();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
//							e1.printStackTrace();
						}
	  				} 
		  			if(j==13){
//		  				System.out.println(j);
	  					try {
	  						test1.join();
							test2.join();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
//							e1.printStackTrace();
						}
	  				} 
		  			if(j==14){
	  					try {
							test2.join();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
//							e1.printStackTrace();
						}
	  				} 
	  				
	  	 	}
	  	}
	  	
		for(int j=0 ;j<14;j++){ 
 	 		Get_feel get_feel=new Get_feel();
 	 		try {
				get_feel.get_feel("./Test.xls", "./"+day_file+areas1[j] +"/article/", "./"+day_file+areas1[j]+"/author/", "./"+day_file+areas1[j]+"/time/", "./"+day_file+areas1[j]+"/title/", "./"+day_file+areas1[j]+"/replies/","./"+day_file+areas1[j]+"/url/",".html",""+(j+1)+"");
			} catch (BiffException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 	 	}
		
		
		
		
	}
	
	
}

class Creat_folder{
	StringBuffer sb =new StringBuffer();
    public void creat_folder(String filepath,JTextPane jtp){
	File fp=new File(filepath);
	if(!fp.exists()){
		fp.mkdirs();//目录不存在，创建目录
		sb.append("目录不存在,自动创建"+"\n");
		jtp.setText(sb.toString());
//		System.out.println("目录不存在,自动创建");
	}
}
}



class Get_homepage implements Runnable  {//存储跳转前的页面
	StringBuffer sb =new StringBuffer();
	private JTextPane jtp;
	private String url;
	public volatile int page;
	private String home_page_address;
	private String postfix; 
	private int pages;
	SimpleDateFormat df=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss:SSSSSS");
	public Get_homepage(String url,String home_page_address,String postfix,int pages,JTextPane jtp){
		this.jtp=jtp;
		this.home_page_address=home_page_address;
		this.url=url;
		this.postfix=postfix;
		this.pages=pages;
		this.jtp=jtp;
		Pattern p=Pattern.compile("(\\d)+.html");
		Matcher m=p.matcher(url);
		if(m.find()){
		page = Integer.parseInt((m.group().replace(".html","")));
//		page=1;
		}
		else{
			page=1;
//			System.out.println("未找到当前页面是第几页,程序将退出");
			System.exit(0);
		}
	}
	public void st(){
		
	}
	public void run(){
		int page_local;
		String url_local; 
		while(page<=this.pages){
			synchronized (this){
				url_local=url.replaceAll("[\\d]*?.html",(page_local=page)+".html");
				page++;
			}
			Write write=new Write();
			Get_content get_content=new Get_content();
		//	System.out.println(Integer.toString(page_local)+"="+get_content.get_content(url_local).toString() );
//			System.out.println("正在抓取页为第"+page_local+"页,"+"由线程"+Thread.currentThread().getName()+"抓取");
			write.write(get_content.get_content(url_local).toString() , this.home_page_address , Integer.toString(page_local) , this.postfix,false);
			//page换成了公共的volatile变量，加入synchronized代码块，保证page每次只能单线递增；然后在run()里面加入局部变量，就能保
			//证每个线程得到的page为下一个未下载的page了,之前直接多线程会重复或跳页
				this.sb.append("抓取总页面完毕,抓取页为第"+page_local+"页,"+"由线程"+Thread.currentThread().getName()+"抓取"+"\n");
				jtp.setText(sb.toString());	
				jtp.setCaretPosition(jtp.getText().length());
//			System.out.println("抓取页面完毕,抓取页为第"+page_local+"页,"+"由线程"+Thread.currentThread().getName()+"抓取");
			}
	}
}
class Gets implements Runnable{
	private  StringBuffer sb1 =new StringBuffer();
	private JTextPane jtp;
	public volatile int page;
	int deep_control=1; 
	private String home_page_address;
	private String postfix; 
	private String article_address;
	private String replies_address;
	private String time_address;
	private String author_address;
	private String title_address;
	private String url_address;
	private int pages;
	
	public Gets(String home_page_address,String postfix,String article_address,String replies_address,String time_address,String author_address,String title_address,String url_address,int deep_control,int pages,JTextPane jtp){
		this.home_page_address=home_page_address;
		this.postfix=postfix;
		this.article_address=article_address;
		this.deep_control=deep_control;
		this.replies_address=replies_address;
		this.time_address=time_address;
		this.author_address=author_address;
		this.pages=pages;
		this.title_address=title_address;
		this.url_address=url_address;
		this.jtp=jtp;
		page=1;
		
	}
	public void run(){
		
		while(page<=this.pages){
			int local_i;
				synchronized (this){
					local_i=page;
					page++;
				}
			if(parse(local_i,this.deep_control)==2){	
//				System.out.println("文件还未存在，此线程将休眠10s等待生产文件");
				synchronized (this){
					page--;
				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//休眠十秒
		}
		}
	}
	public int parse(int TxtName,int deep_control){
		Write write =new Write();
		Read read=new Read();
		Get_https	get_https=new Get_https(); 
		Get_labela	get_labela=new Get_labela();
		Get_content get_content =new Get_content();
		Get_http get_http=new Get_http();
		Get_replies get_replies=new Get_replies();
		Get_author get_author=new Get_author();
		Get_time get_time=new Get_time();
		Get_title get_title = new Get_title();
		StringBuffer sb;String title="";
		String str="";String str2;String url_min;//每个文章进去时的超链接
		File file=new File(this.home_page_address+Integer.toString(TxtName)+this.postfix);
		if(!file.exists())//文件不存在,2表示文件不存在
			return 2;
	//	if(file.lastModified()>df.format(new Date())
	//	return 2;
		//得到各个首页的内容
		str2=read.read(this.home_page_address,Integer.toString(TxtName),this.postfix);
		//包含 帖子标题 作者 时间 回复数量的内容
		str = get_https.get_https(str2);//包含 帖子标题 作者 时间 回复数量的内容
		if(str==""){
//			System.out.println("抓取第"+TxtName+"个页面时出现服务器关闭异常");
		return 0;//异常退出
		}
		get_replies.parse(str);//得到回复数
		get_author.parse(str);//得到作者
		get_time.parse(str); //得到时间
		get_labela.parse(str);//筛选出 含有 :帖子标题 到帖子内容的超链接	
		for(int i=0;i<=get_labela.data.size()-1;i++){
	//		System.out.println("for执行前时间="+df.format(new Date()));
			title = get_title.parse(((ArrayList<String>)get_labela.data).get(i));
			url_min=get_http.parse(((ArrayList<String>) get_labela.data).get(i));
			write.write(url_min, this.url_address, Integer.toString(TxtName)+"_"+Integer.toString(i+1), postfix,false);
			sb=get_content.get_content(url_min);
			write.write(title, this.title_address, Integer.toString(TxtName)+"_"+Integer.toString(i+1), postfix,false);
			write.write(((ArrayList<String>) get_replies.data).get(i), this.replies_address, Integer.toString(TxtName)+"_"+Integer.toString(i+1), postfix,false);
			write.write(((ArrayList<String>) get_time.data).get(i), this.time_address, Integer.toString(TxtName)+"_"+Integer.toString(i+1), postfix,false);
			write.write(((ArrayList<String>) get_author.data).get(i), this.author_address, Integer.toString(TxtName)+"_"+Integer.toString(i+1), postfix,false);
			write.write(filtrate_article(sb.toString(),deep_control), this.article_address, Integer.toString(TxtName)+"_"+Integer.toString(i+1),this.postfix,false);
				this.sb1.append("当前抓取的的第"+TxtName+"页的第"+(i+1)+"个帖子，当前是线程"+Thread.currentThread().getName()+"\n");
				jtp.setText(sb1.toString());
				jtp.setCaretPosition(jtp.getText().length());
			//			System.out.println("当前抓取的的第"+TxtName+"页的第"+(i+1)+"个帖子，当前是线程"+Thread.currentThread().getName());
	//		System.out.println("for执行后的时间="+df.format(new Date()));
		}
		get_labela.data.clear();
		get_replies.data.clear();
		get_time.data.clear();
		get_author.data.clear();
		return 1;
	}
	public  String  filtrate_article(String s,int deep_control) {
		Pattern p=Pattern.compile("(<td class=\"t_f\"[\\S|\\s]*?</td>){1}");
		Matcher m=p.matcher(s);
		String str="";
		int k=0;
		while(m.find()&&k<deep_control){
				str=str+m.group().replaceAll("<[\\S|\\s]*?>|(&\\w*;)|(\n|\r)","");
				str = str.replaceAll("上传|下载附件","");
				k++;
		}
		return str;
}
} 