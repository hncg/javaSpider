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
	//�������ݿ�����Ҫ������
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://127.0.0.1:3306/online_opinion";
	String user = "root";
	String password = "123456";
	HashMap<String, String> map=new HashMap<String, String>();//�����дʻ�����ǿ��
	HashMap<String, Integer> map2=new HashMap<String,Integer>();//ͳ�ƴʵĴ���
	LinkedHashMap<String, Integer> map3=new LinkedHashMap<String,Integer>();//ͳ�ƴʵĴ���
	LinkedHashMap<String, Integer> map_max_article=new LinkedHashMap<String,Integer>();//������������
	HashMap<Integer, Integer> map4=new HashMap<Integer, Integer>();//��������Ǹ��µ���������
	int year;int month;int day;int feel=-1;String class_code;//��  �� ��� (0-6)   ������
	int DAY=0;//�ж������Ƿ�ı�
	int YEAR=0;
	int MONTH=0;
 	Map_sort map_sort=new Map_sort();
	public void get_feel(String excel_address,String article_address,String author_address,String time_address,String title_address,String replies_address,String url_address,String postfix,String A_number) throws BiffException, IOException, ClassNotFoundException, SQLException{
		String A_NUMBER=A_number;
		map4.put(0, 0);
		// ������������
		Class.forName(driver);
		// �������ݿ�
		Connection con=DriverManager.getConnection(url, user, password);
		Statement sq_st=(Statement) con.createStatement();
		double []Feel=new double[7];//���
		File File_address=new File(article_address);
 		File Author_address=new File(author_address);
		File Time_address=new File(time_address);
		File Title_address=new File(title_address);
		File Replies_address=new File(replies_address);
		File Url_address=new File(url_address);
		Get_excel get_excel=new Get_excel();
		map =get_excel.get_excel(excel_address);

	//	SegTag st = new SegTag(1);//�ִ���
	//	Read read=new Read();//��ȡ�ļ���
	//	Write write=new Write();
		Read read =new Read();
		//�ж��ļ����Ƿ����
		if((File_address.exists()&&Replies_address.exists()&&Url_address.exists()&&Author_address.exists()&&Time_address.exists()&&Title_address.exists())){
//			int article_jdge=0;
			for(int i=1;i<=100;i++){
//				System.out.println("i="+i);
				int j;
				for(j=1;j<=70;j++){
					if(A_number.equals("1")){//�ǳ�ɳ������ҳ��
						if(i==1&&j<=13){//����ҳ
							A_NUMBER="15";//ǰ��13��������Ϊ��ʮ�����������
							
						}else{//������ǵ�ǰ����
							A_NUMBER=A_number;
						}
					}else{//�����ڳ�ɳҳ��
						if(i==1&&j<14) j=14;//�ӵ�ʮ�ĸ�ҳ�濪ʼץȡ
					}
					String article_file_name=article_address+i+"_"+j+postfix;
					String author_file_name=author_address+i+"_"+j+postfix;
					String time_file_name=time_address+i+"_"+j+postfix;
					String title_file_name=title_address+i+"_"+j+postfix;
					String replies_file_name=replies_address+i+"_"+j+postfix;
					String url_file_name=url_address+i+"_"+j+postfix;
					
					if(!(new File(url_file_name).exists()&&new File(replies_file_name).exists()&&new File(article_file_name).exists()&&new File(author_file_name).exists() &&new File(time_file_name).exists() &&new File(title_file_name).exists())){//�ļ�������,�����˴�ѭ��
						break;
					}
					else{ //�ļ����ڣ���ȡ�ļ��е����ݣ����ҽ��з���
//						System.out.println("5555555555");
						//��������
						String article=read.read(article_address, Integer.toString(i)+"_"+Integer.toString(j), postfix);
						//����
						String author=read.read(author_address, Integer.toString(i)+"_"+Integer.toString(j), postfix);
						if(author.equals(""))//Ϊ��
							author="none";
						//ʱ��
						String 	time=read.read(time_address,Integer.toString(i)+"_"+Integer.toString(j), postfix);						
						//����
						String title=read.read(title_address, Integer.toString(i)+"_"+Integer.toString(j), postfix);
						title=title.replaceAll("'|\\/|\\.", "");
						if(title.equals(""))//Ϊ��
							title="none";
						//�ظ���
						int replies=Integer.parseInt(read.read(replies_address, Integer.toString(i)+"_"+Integer.toString(j), postfix)) ;
						//����url
						String url=read.read(url_address, Integer.toString(i)+"_"+Integer.toString(j), postfix);
						if(url.length()>100){
							url=url.substring(0,100);
						}				
						ResultSet sq_rs_sure;
						if(url.length()<1 || url.equals("")){//urlΪ��
							url="none";	
							
							continue;
							
						}
						url=url.replaceAll("'","");
						sq_rs_sure=(ResultSet) sq_st.executeQuery("select * from article_index where title='"+title+"' ");			
//						System.out.println("insert into article_index(title,url) values('"+title+"','"+url+"') ");
//						sq_st.executeUpdate("insert into article_index(title,url) values('"+title+"','"+url+"') ");						
						if(sq_rs_sure.first()) {//��¼�Ѿ�����
//							System.out.println(A_number+"_"+i+"_"+j+"��¼�Ѿ�����");							
							continue;
						}else{//�����ھͲ���  
//							System.out.println(A_number+"_"+i+"_"+j+"��¼������");
							sq_st.executeUpdate("insert into article_index(title,url) values('"+title+"','"+url+"') ");
							
						}
						//�洢��hash����
						//�����½��зִ�
						String  rs = ToAnalysis.parse(article).toString().replaceAll("[a-z]|[0-9]|,|'|\\[|\\]|\\/|\\.|����|��","");
						String[]	words=rs.split("\\s");//���·ִ�֮��洢������words��
						//�õ��·�
						if(time.substring(0, 4).matches("\\d*"))
						year=Integer.parseInt(time.substring(0, 4)); 
						else continue;
						month=get_month(time);
						day=get_day(time);
						if(month!=0 && day!=0){//��if��������洢ĳ��ĳ���ĳ�����
							if((DAY!=day || YEAR!=year||MONTH!=month )&&(!map_max_article.isEmpty())){//�����ı�
								map_sort.map_sort(map_max_article);//һ����������
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
								if((DAY!=day || YEAR!=year||MONTH!=month )&&!map2.isEmpty()){//�����ı䣬�����ݴ��룬Ȼ�����ͼ
									
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
									if(!sq_rs.first()){//�����ھͲ���
										sq_st.executeUpdate("insert into feel(happy,good,anger,sorrow,fear,evil,surprise,total_strong,max_strong,year,month,day,A_NUMBER,synthesize) "
													+ "values('"+Feel[0]+"','"+Feel[1]+"','"+Feel[2]+"','"+Feel[3]+"','"+Feel[4]+"','"+Feel[5]+"','"+Feel[6]+"','"+totle_strong+"','"+max_strong+"','"+YEAR+"','"+MONTH+"','"+DAY+"','"+A_NUMBER+"','"+synthesize+"')");
										//+ "values('"+Feel[0]+"','"+Feel[1]+"','"+Feel[2]+"','"+Feel[3]+"','"+Feel[4]+"','"+Feel[5]+"','"+Feel[6]+"','"+totle_strong+"','"+max_strong+"','"+YEAR+"','"+MONTH+"','"+DAY+"','"+A_NUMBER+"','"+synthesize+"')");
//										if(YEAR==2014&&MONTH==9&&DAY==22)
//										System.out.println(article_address+"������"+Feel[0]+"','"+Feel[1]+"','"+Feel[2]+"','"+Feel[3]+"','"+Feel[4]+"','"+Feel[5]+"','"+Feel[6]+"','");
//										System.out.println("���� year='"+YEAR+"' and month='"+MONTH+"'and day='"+DAY+"'");
									}
									else//������ھ͸���
									{
//										System.out.println(" ������'"+YEAR+"' and month='"+MONTH+"'and day='"+DAY+"'");
										sq_up_st=sq_st.executeUpdate("update feel set  happy="+(Feel[0]+Double.parseDouble(sq_rs.getString(1)))+",good="+(Feel[1]+Double.parseDouble(sq_rs.getString(2)))+",anger="+(Feel[2]+Double.parseDouble(sq_rs.getString(3)))+
												",sorrow="+(Feel[3]+Double.parseDouble(sq_rs.getString(4)))+",fear="+(Feel[4]+Double.parseDouble(sq_rs.getString(5)))+",evil="+(Feel[5]+Double.parseDouble(sq_rs.getString(6)))+",surprise="+(Feel[6]+Double.parseDouble(sq_rs.getString(7)))+
												",total_strong="+(totle_strong+Double.parseDouble(sq_rs.getString(8)))+",max_strong="+(max_strong+Double.parseDouble(sq_rs.getString(9)))+",synthesize="+( (synthesize+Double.parseDouble(sq_rs.getString(14)))/2)+"  where year='"+YEAR+"' and month='"+MONTH+"'and day='"+DAY+"' and A_number='"+A_NUMBER+"'");
//										if(YEAR==2014&&MONTH==9&&DAY==22)
//										System.out.println(article_address+"����"+Feel[0]+"','"+Feel[1]+"','"+Feel[2]+"','"+Feel[3]+"','"+Feel[4]+"','"+Feel[5]+"','"+Feel[6]+"','");
									}
										
									for(int feel=0;feel<7;feel++)
									Feel[feel]=0;
									map3=map_sort.map_sort(map2);
									int control=0;
									for (Entry<String, Integer> entry: map3.entrySet()) {
//							          System.out.println(entry.getKey()+"		----->		"+entry.getValue());
									 sq_rs=(ResultSet) sq_st.executeQuery("select * from max_words where year='"+YEAR+"' and month='"+MONTH+"'and day='"+DAY+"' and word='"+entry.getKey()+"' and A_NUMBER='"+A_NUMBER+"'");
									if(!sq_rs.first()){//�����ھͲ���
										sq_st.executeUpdate("insert into max_words(word,rate,year,month,day,A_NUMBER) values('"+entry.getKey()+"','"+entry.getValue()+"','"+YEAR+"','"+MONTH+"','"+DAY+"','"+A_NUMBER+"')");
									}else{//����͸���
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
								//��������ʾͼ�һ��
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
								if(map.get(words[k])!=null){//ӳ���ϵ����
								class_code=map.get(words[k]).substring(0, 2).toString();
								if(class_code.equals("PA")||class_code.equals("PE")){//Ӣ�Ĳ��ֹ���
										feel=0;
										}
								if(class_code.equals("PD")||class_code.equals("PH")||class_code.equals("PG")||class_code.equals("PB")||class_code.equals("PK")){//Ӣ�Ĳ��ֹ���
										feel=1;
										}
								if(class_code.equals("NA")){//Ӣ�Ĳ��ֹ���
										feel=2;
										}
								if(class_code.equals("NB")||class_code.equals("NJ")||class_code.equals("NH")||class_code.equals("PF")){//Ӣ�Ĳ��ֹ���
										feel=3;
										}
								if(class_code.equals("NI")||class_code.equals("NC")||class_code.equals("NG")){//Ӣ�Ĳ��ֹ���
										feel=4;
										}
								if(class_code.equals("NE")||class_code.equals("ND")||class_code.equals("NN")||class_code.equals("NK")||class_code.equals("NL")){//Ӣ�Ĳ��ֹ���
										feel=5;
										}
								if(class_code.equals("PC")){//Ӣ�Ĳ��ֹ���
										feel=6;
										}
								if(feel!=-1){
									 Feel[feel]+=Double.parseDouble(map.get(words[k]).substring(3, 4).toString());//һ���ͬһ�����ǿ�ȼ�����										
									 feel=-1;
								}
							
									
							}
								
							else{//excel��û�������  ����ͳ����һ����
								continue;
							}
							
						}
						words=null;

						}
						else{
						//	System.out.println("ʱ���ȡ����");
							continue;
						}
						
					}
					
			System.out.println(article_address+"("+A_number+")"+i+"_"+j+"����ִ�����");
				
				}
			}
		}
		
		sq_st.close();
		con.close();
	}
	public int get_month(String time){//�õ��·�
		Pattern p=Pattern.compile("-\\d*?-");
		Matcher m=p.matcher(time);
		if(m.find()){
			return Integer.parseInt(m.group().replace("-", ""));
		}
		else{
			return 0;
		}	
	}
	public int get_day(String time){//�õ�����
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
