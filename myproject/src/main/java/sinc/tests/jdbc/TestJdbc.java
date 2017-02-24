package sinc.tests.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TestJdbc {

	public static void main(String[] args) {
		String url = "jdbc:mysql://10.102.34.247:3306/test?characterEncoding=UTF-8";
		String sql = "insert into arcsight_data values(?,?,?,?,?)";
		String username = "root";
		String password = "123456";
		try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url, username, password);
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, "test");
		statement.setString(2, "test");
		statement.setString(3, "test");
		statement.setString(4, "test");
		statement.setString(5, "test");
		statement.executeUpdate();
		conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
