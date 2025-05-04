package com.ebook.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	public Connection getConnection()  {
	
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url		= "jdbc:oracle:thin:@localhost:1521:xe";
			String user 	= "hr";
			String password = "hr";
			
			conn = DriverManager.getConnection(url, user, password);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}

public class DBTest {
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        Connection conn = factory.getConnection();
        if (conn != null) {
            System.out.println("DB 연결 성공!");
        } else {
            System.out.println("DB 연결 실패...");
        }
    }
}
