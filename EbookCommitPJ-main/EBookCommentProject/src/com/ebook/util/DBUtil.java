package com.ebook.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "ebook"; // 본인의 DB 사용자명으로 변경
	private static final String PASSWORD = "ebook1234"; // 본인의 DB 비밀번호로 변경

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD); // DB 연결
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로딩 실패: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("DB 연결 실패: " + e.getMessage());
		}
		return conn;
	}
}
