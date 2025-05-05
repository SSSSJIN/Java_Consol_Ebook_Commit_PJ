package com.ebook.dao;

import com.ebook.util.ConnectionFactory;
import com.ebook.util.JDBCClose;
import com.ebook.vo.BookVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// DB에서 책 정보를 조회하는 클래스
public class BookDAO {
	public List<BookVO> getAllBooks() {
		List<BookVO> books = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = new ConnectionFactory().getConnection();
			String sql = "SELECT book_id, title, author, description FROM ebook_books ORDER BY book_id";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BookVO book = new BookVO(
					rs.getInt("book_id"),
					rs.getString("title"),
					rs.getString("author"),
					rs.getString("description")
				);
				books.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(conn, pstmt, rs);
		}
		return books;
	}

	// 특정 책 ID로 상세정보 조회
	public BookVO getBookById(int bookId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BookVO book = null;
		try {
			conn = new ConnectionFactory().getConnection();
			String sql = "SELECT book_id, title, author, description FROM ebook_books WHERE book_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				book = new BookVO(
					rs.getInt("book_id"),
					rs.getString("title"),
					rs.getString("author"),
					rs.getString("description")
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(conn, pstmt, rs);
		}
		return book;
	}
}