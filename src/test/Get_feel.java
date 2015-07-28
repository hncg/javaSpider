package test;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ansj.splitWord.analysis.ToAnalysis;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import jxl.read.biff.BiffException;
import test.Read;
import test.Write;
public class Get_feel {
	//连接数据库所需要的数据
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://127.0.0.1:3306/online_opinion";
	String user = "root";
	String password = "123456";
	HashMap<String, String> map=new HashMap<String, String>();//存放情感词汇和轻钢强度
	HashMap<String, Integer> map2=new HashMap<String,Integer>();//统计词的次数
	LinkedHashMap<String, Integer> map3=new LinkedHashMap<String,Integer>();//统计词的次数
	LinkedHashMap<String, Integer> map_max_article=new LinkedHashMap<String,Integer>();//排序热门帖子
	HashMap<Integer, Integer> map4=new HashMap<Integer, Integer>();//标记最大的那个月的最大的天数
	int year;int month;int day;int feel=-1;String class_code;//月  日 情感 (0-6)   类别代号
	int DAY=0;//判断日期是否改变
	int YEAR=0;
	int MONTH=0;
 	Map_sort map_sort=new Map_sort();
	public void get_feel(String excel_address,String article_address,String author_address,String time_address,String title_address,String replies_address,String url_address,String postfix,String A_number) throws BiffException, IOException, ClassNotFoundException, SQLException{
		String A_NUMBER=A_number;
		map4.put(0, 0);
		// 加载驱动程序
		Class.forName(driver);
		// 连续数据库
		Connection con=DriverManager.getConnection(url, user, password);
		Statement sq_st=(Statement) con.createStatement();
		double []Feel=new double[7];//情感
		File File_address=new File(article_address);
 		File Author_address=new File(author_address);
		File Time_address=new File(time_address);
		File Title_address=new File(title_address);
		File Replies_address=new File(replies_address);
		File Url_address=new File(url_address);
		Get_excel get_excel=new Get_excel();
		map =get_excel.get_excel(excel_address);

	//	SegTag st = new SegTag(1);//分词类
	//	Read read=new Read();//读取文件类
	//	Write write=new Write();
		Read read =new Read();
		//判断文件夹是否存在
		if((File_address.exists()&&Replies_address.exists()&&Url_address.exists()&&Author_address.exists()&&Time_address.exists()&&Title_address.exists())){
//			int article_jdge=0;
			for(int i=1;i<=100;i++){
//				System.out.println("i="+i);
				int j;
				for(j=1;j<=70;j++){
					if(A_number.equals("1")){//是长沙地区的页面
						if(i==1&&j<=13){//在首页
							A_NUMBER="15";//前面13个帖子作为第十五个地区出现
							
						}else{//否则就是当前地区
							A_NUMBER=A_number;
						}
					}else{//不是在长沙页面
						if(i==1&&j<14) j=14;//从第十四个页面开始抓取
					}
					String article_file_name=article_address+i+"_"+j+postfix;
					String author_file_name=author_address+i+"_"+j+postfix;
					String time_file_name=time_address+i+"_"+j+postfix;
					String title_file_name=title_address+i+"_"+j+postfix;
					String replies_file_name=replies_address+i+"_"+j+postfix;
					String url_file_name=url_address+i+"_"+j+postfix;
					
					if(!(new File(url_file_name).exists()&&new File(replies_file_name).exists()&&new File(article_file_name).exists()&&new File(author_file_name).exists() &&new File(time_file_name).exists() &&new File(title_file_name).exists())){//文件不存在,结束此次循环
						break;
					}
					else{ //文件存在，读取文件中的内容，并且进行分析
//						System.out.println("5555555555");
						//文章内容
						String article=read.read(article_address, Integer.toString(i)+"_"+Integer.toString(j), postfix);
						//作者
						String author=read.read(author_address, Integer.toString(i)+"_"+Integer.toString(j), postfix);
						if(author.equals(""))//为空
							author="none";
						//时间
						String 	time=read.read(time_address,Integer.toString(i)+"_"+Integer.toString(j), postfix);						
						//标题
						String title=read.read(title_address, Integer.toString(i)+"_"+Integer.toString(j), postfix);
						title=title.replaceAll("'|\\/|\\.", "");
						if(title.equals(""))//为空
							title="none";
						//回复数
						int replies=Integer.parseInt(read.read(replies_address, Integer.toString(i)+"_"+Integer.toString(j), postfix)) ;
						//帖子url
						String url=read.read(url_address, Integer.toString(i)+"_"+Integer.toString(j), postfix);
						if(url.length()>100){
							url=url.substring(0,100);
						}				
						ResultSet sq_rs_sure;
						if(url.length()<1 || url.equals("")){//url为空
							url="none";	
							
							continue;
							
						}
						url=url.replaceAll("'","");
						sq_rs_sure=(ResultSet) sq_st.executeQuery("select * from article_index where title='"+title+"' ");			
//						System.out.println("insert into article_index(title,url) values('"+title+"','"+url+"') ");
//						sq_st.executeUpdate("insert into article_index(title,url) values('"+title+"','"+url+"') ");						
						if(sq_rs_sure.first()) {//记录已经存在
//							System.out.println(A_number+"_"+i+"_"+j+"记录已经存在");							
							continue;
						}else{//不存在就插入  
//							System.out.println(A_number+"_"+i+"_"+j+"记录不存在");
							sq_st.executeUpdate("insert into article_index(title,url) values('"+title+"','"+url+"') ");
							
						}
						//存储到hash表中
						//对文章进行分词
						String  rs = ToAnalysis.parse(article).toString().replaceAll("[a-z]|[0-9]|,|'|\\[|\\]|\\/|\\.|——|…","");
						String[]	words=rs.split("\\s");//文章分词之后存储在数组words中
						//得到月份
						if(time.substring(0, 4).matches("\\d*"))
						year=Integer.parseInt(time.substring(0, 4)); 
						else continue;
						month=get_month(time);
						day=get_day(time);
						if(month!=0 && day!=0){//此if语句用来存储某月某天的某种情感
							if((DAY!=day || YEAR!=year||MONTH!=month )&&(!map_max_article.isEmpty())){//天数改变
								map_sort.map_sort(map_max_article);//一天热帖排序
								int max_control=1;
								for (Entry<String, Integer> entry: map_max_article.entrySet()){
									if(max_control>=3) break;
									String[] maxs=new String[4];
									maxs=entry.getKey().split("_");
//									System.out.println("insert into max_article(author,title,url,replies,year,month,day,A_NUMBER) values('"+maxs[0]+"','"+maxs[1]+"','"+maxs[2]+"','"+entry.getValue()+"','"+YEAR+"','"+MONTH+"','"+DAY+"','"+A_NUMBER+"')");
									if(maxs[0].length()>50) maxs[0]=maxs[0].substring(0,49);
									sq_st.executeUpdate("insert into max_article(author,title,url,replies,year,month,day,A_NUMBER) values('"+maxs[0].replaceAll("'","")+"','"+maxs[1].replaceAll("'","")+"','"+maxs[2]+"','"+entry.getValue()+"','"+YEAR+"','"+MONTH+"','"+DAY+"','"+A_NUMBER+"')");			
									max_control++;
//									System.exit(0);
								}
								map_max_article.clear();
								
							}
								map_max_article.put(author+"_"+title+"_"+url,replies);
								for(int k=0;k<words.length;k++){
								if((DAY!=day || YEAR!=year||MONTH!=month )&&!map2.isEmpty()){//天数改变，将数据存入，然后清空图
									
									double []Feel2=new double[7];
									Feel2 =Feel.clone();
									Arrays.sort(Feel2);
									double totle_strong=Feel2[0]+Feel2[1]+Feel2[2]+Feel2[3]+Feel2[4]+Feel2[5]+Feel2[6];
									double max_strong=Feel2[6];
									double synthesize=4*Feel[0]+3*Feel[1]-4*Feel[5]-3*Feel[2]-2*Feel[3]-1*Feel[4]+1*Feel[6];
//																		double synthesize=4*Feel[0]+3*Feel[1]-4*Feel[5]-3*Feel[2]-2*Feel[3]-1*Feel[4]+1*Feel[6];
									ResultSet sq_rs;
									sq_rs=(ResultSet) sq_st.executeQuery("select * from feel where year='"+YEAR+"' and month='"+MONTH+"'and day='"+DAY+"' and A_number='"+A_NUMBER+"'");
//									System.out.println("A_NUMBER="+A_NUMBER);
									int sq_up_st=0;
									if(!sq_rs.first()){//不存在就插入
										sq_st.executeUpdate("insert into feel(happy,good,anger,sorrow,fear,evil,surprise,total_strong,max_strong,year,month,day,A_NUMBER,synthesize) "
													+ "values('"+Feel[0]+"','"+Feel[1]+"','"+Feel[2]+"','"+Feel[3]+"','"+Feel[4]+"','"+Feel[5]+"','"+Feel[6]+"','"+totle_strong+"','"+max_strong+"','"+YEAR+"','"+MONTH+"','"+DAY+"','"+A_NUMBER+"','"+synthesize+"')");
										//+ "values('"+Feel[0]+"','"+Feel[1]+"','"+Feel[2]+"','"+Feel[3]+"','"+Feel[4]+"','"+Feel[5]+"','"+Feel[6]+"','"+totle_strong+"','"+max_strong+"','"+YEAR+"','"+MONTH+"','"+DAY+"','"+A_NUMBER+"','"+synthesize+"')");
//										if(YEAR==2014&&MONTH==9&&DAY==22)
//										System.out.println(article_address+"不存在"+Feel[0]+"','"+Feel[1]+"','"+Feel[2]+"','"+Feel[3]+"','"+Feel[4]+"','"+Feel[5]+"','"+Feel[6]+"','");
//										System.out.println("存在 year='"+YEAR+"' and month='"+MONTH+"'and day='"+DAY+"'");
									}
									else//否则存在就更新
									{
//										System.out.println(" 不存在'"+YEAR+"' and month='"+MONTH+"'and day='"+DAY+"'");
										sq_up_st=sq_st.executeUpdate("update feel set  happy="+(Feel[0]+Double.parseDouble(sq_rs.getString(1)))+",good="+(Feel[1]+Double.parseDouble(sq_rs.getString(2)))+",anger="+(Feel[2]+Double.parseDouble(sq_rs.getString(3)))+
												",sorrow="+(Feel[3]+Double.parseDouble(sq_rs.getString(4)))+",fear="+(Feel[4]+Double.parseDouble(sq_rs.getString(5)))+",evil="+(Feel[5]+Double.parseDouble(sq_rs.getString(6)))+",surprise="+(Feel[6]+Double.parseDouble(sq_rs.getString(7)))+
												",total_strong="+(totle_strong+Double.parseDouble(sq_rs.getString(8)))+",max_strong="+(max_strong+Double.parseDouble(sq_rs.getString(9)))+",synthesize="+( (synthesize+Double.parseDouble(sq_rs.getString(14)))/2)+"  where year='"+YEAR+"' and month='"+MONTH+"'and day='"+DAY+"' and A_number='"+A_NUMBER+"'");
//										if(YEAR==2014&&MONTH==9&&DAY==22)
//										System.out.println(article_address+"存在"+Feel[0]+"','"+Feel[1]+"','"+Feel[2]+"','"+Feel[3]+"','"+Feel[4]+"','"+Feel[5]+"','"+Feel[6]+"','");
									}
										
									for(int feel=0;feel<7;feel++)
									Feel[feel]=0;
									map3=map_sort.map_sort(map2);
									int control=0;
									for (Entry<String, Integer> entry: map3.entrySet()) {
//							          System.out.println(entry.getKey()+"		----->		"+entry.getValue());
									 sq_rs=(ResultSet) sq_st.executeQuery("select * from max_words where year='"+YEAR+"' and month='"+MONTH+"'and day='"+DAY+"' and word='"+entry.getKey()+"' and A_NUMBER='"+A_NUMBER+"'");
									if(!sq_rs.first()){//不存在就插入
										sq_st.executeUpdate("insert into max_words(word,rate,year,month,day,A_NUMBER) values('"+entry.getKey()+"','"+entry.getValue()+"','"+YEAR+"','"+MONTH+"','"+DAY+"','"+A_NUMBER+"')");
									}else{//否则就更新
										int rate_pre=Integer.parseInt( sq_rs.getString(2));
										int rate2=rate_pre+entry.getValue();
											sq_up_st=sq_st.executeUpdate("update max_words set rate='"+rate2 +"' where year='"+YEAR+"' and month='"+MONTH+"'and day='"+DAY+"' and word='"+entry.getKey()+"'");
//										System.out.println("rate_pre="+rate_pre);
//										System.out.println("entry.getValue()="+entry.getValue());
//										System.out.println("rate2="+rate2);
									}
										control++;
								        if(control==5){
								        		map2.clear();
								        		map3.clear();
								        		break;
								        }
								        
									}
								}
								//存在这个词就加一次
								if(map2.containsKey(words[k])&&words[k].getBytes().length>=4){
									map2.put(words[k], map2.get(words[k])+1);
								}
								else{
									if(words[k].getBytes().length>=4)
									map2.put(words[k],1);
								}
								DAY=day;
								YEAR=year;
								MONTH=month;
								if(map.get(words[k])!=null){//映射关系存在
								class_code=map.get(words[k]).substring(0, 2).toString();
								if(class_code.equals("PA")||class_code.equals("PE")){//英文部分归类
										feel=0;
										}
								if(class_code.equals("PD")||class_code.equals("PH")||class_code.equals("PG")||class_code.equals("PB")||class_code.equals("PK")){//英文部分归类
										feel=1;
										}
								if(class_code.equals("NA")){//英文部分归类
										feel=2;
										}
								if(class_code.equals("NB")||class_code.equals("NJ")||class_code.equals("NH")||class_code.equals("PF")){//英文部分归类
										feel=3;
										}
								if(class_code.equals("NI")||class_code.equals("NC")||class_code.equals("NG")){//英文部分归类
										feel=4;
										}
								if(class_code.equals("NE")||class_code.equals("ND")||class_code.equals("NN")||class_code.equals("NK")||class_code.equals("NL")){//英文部分归类
										feel=5;
										}
								if(class_code.equals("PC")){//英文部分归类
										feel=6;
										}
								if(feel!=-1){
									 Feel[feel]+=Double.parseDouble(map.get(words[k]).substring(3, 4).toString());//一天的同一类情感强度加起来										
									 feel=-1;
								}
							
									
							}
								
							else{//excel中没有这个词  继续统计下一个词
								continue;
							}
							
						}
						words=null;

						}
						else{
						//	System.out.println("时间获取错误");
							continue;
						}
						
					}
					
			System.out.println(article_address+"("+A_number+")"+i+"_"+j+"文章执行完毕");
				
				}
			}
		}
		
		sq_st.close();
		con.close();
	}
	public int get_month(String time){//得到月份
		Pattern p=Pattern.compile("-\\d*?-");
		Matcher m=p.matcher(time);
		if(m.find()){
			return Integer.parseInt(m.group().replace("-", ""));
		}
		else{
			return 0;
		}	
	}
	public int get_day(String time){//得到天数
		Pattern p=Pattern.compile("-\\d*?\\s");
		Matcher m=p.matcher(time);
		if(m.find()){
			return  Integer.parseInt(m.group().replaceAll("-|\\s", ""));
		}
		else{
			return 0;
		}
	}
	
}
