package com.ebook.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCClose {

    // Statement, ResultSet, Connection을 닫는 메서드
    public static void close(Connection conn, Statement pstmt, ResultSet rs) {
        // ResultSet 먼저 닫기
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Statement 닫기
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Connection 닫기
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
