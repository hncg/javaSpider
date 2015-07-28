package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class mysql {
		public void connet_mysql(String driver,String url,String user,String password) throws ClassNotFoundException, SQLException{
			String str="12345";
			// 加载驱动程序
			Class.forName(driver);
			// 连续数据库
			Connection con=DriverManager.getConnection(url, user, password);
			Statement st=(Statement) con.createStatement();
		}
}
