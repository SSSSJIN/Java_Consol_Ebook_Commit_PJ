package com.ebook.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ebook.util.DBUtil;
import com.ebook.util.JDBCClose;
import com.ebook.vo.StorageVO;

public class StorageDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	// 보관함 책 삽입
	public int insert(StorageVO vo) {
		int result = 0;
		try {
			conn = DBUtil.getConnection(); // DB 연결
			String sql = "INSERT INTO storage(book_id, user_id, title, author, current_episode) VALUES (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getBookId());
			pstmt.setString(2, vo.getUserId());
			pstmt.setString(3, vo.getTitle());
			pstmt.setString(4, vo.getAuthor());
			pstmt.setInt(5, vo.getCurrentEpisode());
			result = pstmt.executeUpdate(); // insert 실행
		} catch (SQLException e) {
			System.out.println("보관함 insert 실패: " + e.getMessage());
		} finally {
			JDBCClose.close(conn, pstmt, rs);
		}
		return result; // 성공하면 1, 실패하면 0
	}

	// 사용자 보관함 목록 조회
	public List<StorageVO> selectByUser(String userId) {
		List<StorageVO> list = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM storage WHERE user_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				StorageVO vo = new StorageVO(
					rs.getInt("book_id"),
					rs.getString("user_id"),
					rs.getString("title"),
					rs.getString("author"),
					rs.getInt("current_episode")
				);
				list.add(vo); // 리스트에 추가
			}
		} catch (SQLException e) {
			System.out.println("보관함 조회 실패: " + e.getMessage());
		} finally {
			JDBCClose.close(conn, pstmt, rs);
		}
		return list;
	}

	// 책 삭제
	public int delete(String userId, int bookId) {
		int result = 0;
		try {
			conn = DBUtil.getConnection();
			String sql = "DELETE FROM storage WHERE user_id = ? AND book_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, bookId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("보관함 삭제 실패: " + e.getMessage());
		} finally {
			JDBCClose.close(conn, pstmt, rs);
		}
		return result;
	}

	// 저장된 책 여부 확인
	public boolean isBookSaved(String userId, int bookId) {
		boolean result = false;
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT COUNT(*) FROM storage WHERE user_id = ? AND book_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, bookId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			System.out.println("책 저장 여부 확인 실패: " + e.getMessage());
		} finally {
			JDBCClose.close(conn, pstmt, rs);
		}
		return result;
	}
}

