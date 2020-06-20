package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @ClassName: TestDerby
 * @Description: 
 * @author 
 * @date 
 */
public class TestDerby {
	public static Connection conn;
	public static void main(String[] args) {
		 
		try {
			//Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			//Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver").newInstance();
			 
			  conn = null;
			 //Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			  String URL = "jdbc:derby:db_train;create=true";
		 
			//conn = DriverManager.getConnection("jdbc:derby://localhost:1527/db_train","train","123456");
			conn = DriverManager.getConnection(URL,"train","123456");
			System.out.println("create and connect to db_train");
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT m.\"username\",\"password\" FROM  TRAIN.\"manager\" as m ");
			System.out.println("name\t\tscore");
			while (rs.next()) {
				StringBuilder builder = new StringBuilder(rs.getString(1));
				builder.append("\t");
				builder.append(rs.getString(1));
				System.out.println(builder.toString());
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 
}