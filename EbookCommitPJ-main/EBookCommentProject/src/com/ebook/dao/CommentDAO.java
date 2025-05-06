package com.ebook.dao;

import com.ebook.util.ConnectionFactory;
import com.ebook.util.JDBCClose;
import com.ebook.vo.CommentVO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    // 댓글 저장
    public int insert(CommentVO comment) {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = new ConnectionFactory().getConnection();            
            String sql = "INSERT INTO comments(book_id, user_id, content) VALUES(?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt	(1, comment.getBookId());
            pstmt.setString	(2, comment.getUserId());
            pstmt.setString	(3, comment.getContent());
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCClose.close(conn, pstmt, null);
        }
        return result;
    }

    // 책 ID로 댓글 조회
    public List<CommentVO> selectByBookId(int bookId) {
        List<CommentVO> comments = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

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
            e.printStackTrace();
        } finally {
            JDBCClose.close(conn, pstmt, rs);
        }
        return comments;
    }

    // 댓글 수정
    public int updateComment(CommentVO comment) {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = new ConnectionFactory().getConnection();
            String sql = "UPDATE comments SET content = ? WHERE comment_id = ? AND user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, comment.getContent());
            pstmt.setInt(2, comment.getCommentId());
            pstmt.setString(3, comment.getUserId());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCClose.close(conn, pstmt, null);
        }
        return result;
    }

    // 댓글 삭제
    public int deleteComment(int commentId, String userId) {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = new ConnectionFactory().getConnection();
            String sql = "DELETE FROM comments WHERE comment_id = ? AND user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, commentId);
            pstmt.setString(2, userId);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCClose.close(conn, pstmt, null);
        }
        return result;
    }
}
