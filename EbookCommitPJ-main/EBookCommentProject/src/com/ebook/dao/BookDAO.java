package com.ebook.dao;

import com.ebook.util.ConnectionFactory;
import com.ebook.util.JDBCClose;
import com.ebook.vo.BookVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// DB에서 책 정보 조회
public class BookDAO {

    // 전체 책 목록 조회
    public List<BookVO> getAllBooks() {
        List<BookVO> books = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = new ConnectionFactory().getConnection();
            String sql = "SELECT book_id, title, author, genre, description FROM ebook_books ORDER BY book_id";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BookVO book = new BookVO(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),  // genre 추가
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
            String sql = "SELECT book_id, title, author, genre, description FROM ebook_books WHERE book_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                book = new BookVO(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),  // genre 추가
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

    // 제목으로 책 검색
    public List<BookVO> searchBooksByTitle(String keyword) {
        List<BookVO> books = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = new ConnectionFactory().getConnection();
            String sql = "SELECT book_id, title, author, genre, description FROM ebook_books WHERE title LIKE ? ORDER BY book_id";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BookVO book = new BookVO(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),  // genre 추가
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

    // 특정 book_id의 에피소드 전체 조회
    public List<String> selectEpisodesByBookId(int bookId) {
        List<String> episodes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = new ConnectionFactory().getConnection();
            String sql = "SELECT episode_number, content FROM ebook_episodes WHERE book_id = ? ORDER BY episode_number";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int episodeNumber = rs.getInt("episode_number");
                String content = rs.getString("content");
                episodes.add("Episode " + episodeNumber + "\n" + content);
            }
        } catch (Exception e) {
            System.out.println("에피소드 조회 실패: " + e.getMessage());
        } finally {
            JDBCClose.close(conn, pstmt, rs);
        }
        return episodes;
    }
    
    public BookVO getBookDetail(int bookId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BookVO book = null;

		try {
			conn = new ConnectionFactory().getConnection();
			String sql = "SELECT book_id, title, author, genre, description FROM ebook_books WHERE book_id = ?";
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
			System.out.println("책 상세 조회 실패: " + e.getMessage());
		} finally {
			JDBCClose.close(conn, pstmt, rs);
		}
		return book;
	}
}

