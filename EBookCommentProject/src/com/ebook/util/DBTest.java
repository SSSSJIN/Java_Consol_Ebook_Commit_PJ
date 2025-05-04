package com.ebook.util;

import java.sql.Connection;

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
