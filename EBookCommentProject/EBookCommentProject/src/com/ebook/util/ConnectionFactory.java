package com.ebook.util;

/*
 * DB T_MEMBER 테이블 쿼리 - 생성하기
CREATE TABLE T_MEMBER (
    no INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    regDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

 */

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
