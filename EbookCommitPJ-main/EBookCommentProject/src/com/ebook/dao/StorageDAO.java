package com.ebook.dao;

import java.sql.*;
import java.util.*;
import com.ebook.vo.StorageVO;
import com.ebook.vo.BookVO;
import com.ebook.util.ConnectionFactory;

public class StorageDAO {

    // 책 저장
    public int insert(StorageVO vo) {
        String sql = "INSERT INTO storage (user_id, book_id, title, author, current_episode) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, vo.getUserId());
            pstmt.setInt(2, vo.getBookId());
            pstmt.setString(3, vo.getTitle());
            pstmt.setString(4, vo.getAuthor());
            pstmt.setInt(5, vo.getCurrentEpisode());
            return pstmt.executeUpdate(); // 저장된 행의 수 반환
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 사용자의 보관함 목록 조회
    public List<StorageVO> selectByUser(String userId) {
        String sql = "SELECT * FROM storage WHERE user_id = ?";
        List<StorageVO> storageList = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int bookId = rs.getInt("book_id");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    int currentEpisode = rs.getInt("current_episode");
                    StorageVO storage = new StorageVO(bookId, userId, title, author, currentEpisode);
                    storageList.add(storage);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storageList;
    }

    // 보관함에서 책 삭제
    public int delete(String userId, int bookId) {
        String sql = "DELETE FROM storage WHERE user_id = ? AND book_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setInt(2, bookId);
            return pstmt.executeUpdate(); // 삭제된 행의 수 반환
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 책이 보관함에 저장되어 있는지 확인
    public boolean isBookSaved(String userId, int bookId) {
        String sql = "SELECT COUNT(*) FROM storage WHERE user_id = ? AND book_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setInt(2, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // 저장된 책이 있으면 true 반환
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 책 상세 정보 조회
    public BookVO selectBookDetail(int bookId) {
        String sql = "SELECT * FROM book WHERE book_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String description = rs.getString("description");
                    return new BookVO(bookId, title, author, description); // 책 상세 정보 반환
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 현재 읽고 있는 회차 조회
    public int getCurrentEpisode(String userId, int bookId) {
        String sql = "SELECT current_episode FROM storage WHERE user_id = ? AND book_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setInt(2, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("current_episode"); // 현재 에피소드 반환
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // 기본값 0 반환
    }

    // 현재 읽고 있는 회차 업데이트
    public boolean updateCurrentEpisode(String userId, int bookId, int currentEpisode) {
        String sql = "UPDATE storage SET current_episode = ? WHERE user_id = ? AND book_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, currentEpisode);
            pstmt.setString(2, userId);
            pstmt.setInt(3, bookId);
            return pstmt.executeUpdate() > 0; // 업데이트 성공 여부 반환
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
