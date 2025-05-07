package com.ebook.dao;

import com.ebook.util.ConnectionFactory;
import com.ebook.util.JDBCClose;
import com.ebook.vo.CommentVO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	// 댓글 저장 (기존 메서드)
	public int insert(CommentVO comment) {
		int result = 0;
		try {
			conn = new ConnectionFactory().getConnection();            
			String sql = "INSERT INTO comments(book_id, user_id, content) VALUES(?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, comment.getBookId());
			pstmt.setString(2, comment.getUserId());
			pstmt.setString(3, comment.getContent());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("댓글 저장 실패: " + e.getMessage());
		} finally {
			JDBCClose.close(conn, pstmt, null);
		}
		return result;
	}

	// 책 ID로 댓글 조회 (기존 메서드)
	public List<CommentVO> selectByBookId(int bookId) {
		List<CommentVO> comments = new ArrayList<>();
		try {
			conn = new ConnectionFactory().getConnection();
			String sql = "SELECT * FROM comments WHERE book_id = ? ORDER BY comment_id DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CommentVO comment = new CommentVO();
				comment.setCommentId(rs.getInt("comment_id"));
				comment.setBookId(rs.getInt("book_id"));
				comment.setUserId(rs.getString("user_id"));
				comment.setContent(rs.getString("content"));
				comments.add(comment);
			}
		} catch (SQLException e) {
			System.out.println("책별 댓글 조회 실패: " + e.getMessage());
		} finally {
			JDBCClose.close(conn, pstmt, rs);
		}
		return comments;
	}

	// 댓글 수정 (기존 메서드)
	public int updateComment(CommentVO comment) {
		int result = 0;
		try {
			conn = new ConnectionFactory().getConnection();
			String sql = "UPDATE comments SET content = ? WHERE comment_id = ? AND user_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getContent());
			pstmt.setInt(2, comment.getCommentId());
			pstmt.setString(3, comment.getUserId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("댓글 수정 실패: " + e.getMessage());
		} finally {
			JDBCClose.close(conn, pstmt, null);
		}
		return result;
	}

	// 댓글 삭제 (기존 메서드)
	public int deleteComment(int commentId, String userId) {
		int result = 0;
		try {
			conn = new ConnectionFactory().getConnection();
			String sql = "DELETE FROM comments WHERE comment_id = ? AND user_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentId);
			pstmt.setString(2, userId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("댓글 삭제 실패: " + e.getMessage());
		} finally {
			JDBCClose.close(conn, pstmt, null);
		}
		return result;
	}
	
	// 에피소드별 댓글 목록 조회 (추가된 메서드)
	public List<CommentVO> getCommentsByEpisode(int bookId, int episode) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CommentVO> commentList = new ArrayList<>();
		
		try {
			conn = new ConnectionFactory().getConnection();
			String sql = "SELECT c.*, m.nickname FROM ebook_comments c "
					+ "JOIN t_member m ON c.user_id = m.email "
					+ "WHERE c.book_id = ? AND c.episode = ? "
					+ "ORDER BY c.reg_date DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookId);
			pstmt.setInt(2, episode);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CommentVO comment = new CommentVO();
				comment.setCommentId(rs.getInt("comment_id"));
				comment.setUserId(rs.getString("user_id"));
				comment.setNickname(rs.getString("nickname"));
				comment.setBookId(rs.getInt("book_id"));
				comment.setEpisode(rs.getInt("episode"));
				comment.setContent(rs.getString("content"));
				comment.setLikes(rs.getInt("likes"));
				comment.setRegDate(rs.getTimestamp("reg_date"));
				commentList.add(comment);
			}
		} catch (SQLException e) {
			System.out.println("댓글 목록 조회 실패: " + e.getMessage());
		} finally {
			JDBCClose.close(conn, pstmt, rs);
		}
		
		return commentList;
	}
	
	// 댓글 추가 (추가된 메서드)
	public boolean addComment(CommentVO comment) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean success = false;
		
		try {
			conn = new ConnectionFactory().getConnection();
			String sql = "INSERT INTO ebook_comments (comment_id, user_id, book_id, episode, content, likes, reg_date) "
					+ "VALUES (ebook_comment_seq.NEXTVAL, ?, ?, ?, ?, 0, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getUserId());
			pstmt.setInt(2, comment.getBookId());
			pstmt.setInt(3, comment.getEpisode());
			pstmt.setString(4, comment.getContent());
			
			int result = pstmt.executeUpdate();
			if (result > 0) {
				success = true;
			}
		} catch (SQLException e) {
			System.out.println("댓글 추가 실패: " + e.getMessage());
		} finally {
			JDBCClose.close(conn, pstmt, null);
		}
		
		return success;
	}
	
	// 댓글 좋아요 증가 (추가된 메서드)
	public boolean likeComment(int commentId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean success = false;
		
		try {
			conn = new ConnectionFactory().getConnection();
			String sql = "UPDATE ebook_comments SET likes = likes + 1 WHERE comment_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentId);
			
			int result = pstmt.executeUpdate();
			if (result > 0) {
				success = true;
			}
		} catch (SQLException e) {
			System.out.println("좋아요 증가 실패: " + e.getMessage());
		} finally {
			JDBCClose.close(conn, pstmt, null);
		}
		
		return success;
	}
	
	// 댓글 ID로 삭제 (추가된 메서드)
	public boolean deleteComment(int commentId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean success = false;
		
		try {
			conn = new ConnectionFactory().getConnection();
			String sql = "DELETE FROM ebook_comments WHERE comment_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentId);
			
			int result = pstmt.executeUpdate();
			if (result > 0) {
				success = true;
			}
		} catch (SQLException e) {
			System.out.println("댓글 삭제 실패: " + e.getMessage());
		} finally {
			JDBCClose.close(conn, pstmt, null);
		}
		
		return success;
	}
}
