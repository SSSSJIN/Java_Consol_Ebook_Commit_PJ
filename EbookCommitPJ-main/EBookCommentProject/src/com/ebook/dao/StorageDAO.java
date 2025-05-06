package com.ebook.dao;

import java.sql.*;
import java.util.*;

import com.ebook.util.DBUtil;
import com.ebook.util.JDBCClose;
import com.ebook.vo.StorageVO;
import com.ebook.vo.BookVO;  // BookVO 추가

public class StorageDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    // 사용자 보관함 목록 조회
    public List<StorageVO> selectByUser(String userId) {
        List<StorageVO> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            
            // 조인 쿼리로 책 정보와 함께 저장된 책 목록 조회
            String sql = "SELECT s.book_id, s.user_id, b.title, b.author, s.current_episode " +
                         	"FROM storage s " +
                         	"JOIN ebook_books b ON s.book_id = b.book_id " +
                         	"WHERE s.user_id = ?";
            
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

    // 책 저장
    public int insert(StorageVO vo) {
        int result = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO storage (user_id, book_id, current_episode) " +
                         "VALUES (?, ?, 1)";  // 기본적으로 첫 회차는 1로 저장
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vo.getUserId());
            pstmt.setInt(2, vo.getBookId());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("책 저장 실패: " + e.getMessage());
        } finally {
            JDBCClose.close(conn, pstmt, rs);
        }
        return result;
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
            System.out.println("책 삭제 실패: " + e.getMessage());
        } finally {
            JDBCClose.close(conn, pstmt, rs);
        }
        return result;
    }

    // 책 이어보기 (현재 회차 + 1)
    public boolean continueReading(String userId, int bookId) {
        boolean isSuccess = false;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE storage SET current_episode = current_episode + 1 " +
                         "WHERE user_id = ? AND book_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setInt(2, bookId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                isSuccess = true;  // 이어보기 성공
            }
        } catch (SQLException e) {
            System.out.println("책 이어보기 실패: " + e.getMessage());
        } finally {
            JDBCClose.close(conn, pstmt, rs);
        }
        return isSuccess;
    }

    // 책 다음화 보기
    public boolean nextEpisode(String userId, int bookId) {
        boolean isSuccess = false;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE storage SET current_episode = current_episode + 1 " +
                         "WHERE user_id = ? AND book_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setInt(2, bookId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                isSuccess = true;  // 다음화 보기 성공
            }
        } catch (SQLException e) {
            System.out.println("다음화 보기 실패: " + e.getMessage());
        } finally {
            JDBCClose.close(conn, pstmt, rs);
        }
        return isSuccess;
    }

    // 책 상세 정보 조회
    public BookVO selectBookDetail(int bookId) {
        BookVO book = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT b.book_id, b.title, b.author, b.genre, b.description " +
                         "FROM ebook_books b " +
                         "WHERE b.book_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                book = new BookVO(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),
                    rs.getString("description")
                );
            }
        } catch (SQLException e) {
            System.out.println("책 상세 정보 조회 실패: " + e.getMessage());
        } finally {
            JDBCClose.close(conn, pstmt, rs);
        }
        return book;
    }

    // 책이 보관함에 저장되어 있는지 여부 확인
    public boolean isBookSaved(String userId, int bookId) {
        boolean exists = false;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM storage WHERE user_id = ? AND book_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setInt(2, bookId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("책 저장 여부 확인 실패: " + e.getMessage());
        } finally {
            JDBCClose.close(conn, pstmt, rs);
        }
        return exists;
    }
}
