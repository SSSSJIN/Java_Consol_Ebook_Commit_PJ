package com.ebook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ebook.util.ConnectionFactory;
import com.ebook.util.JDBCClose;
import com.ebook.vo.EpisodeVO;

public class EpisodeDAO {
	
	// 책의 전체 회차 수 반환
	public int getTotalEpisodes(int bookId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalEpisodes = 0;
		
		try {
			conn = new ConnectionFactory().getConnection();
			String sql = "SELECT COUNT(*) FROM ebook_episodes WHERE book_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				totalEpisodes = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("회차 수 조회 실패: " + e.getMessage());
		} finally {
			JDBCClose.close(conn, pstmt, rs);
		}
		
		return totalEpisodes;
	}
	
	// 에피소드 제목 반환
	public String getEpisodeTitle(int bookId, int episodeNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String title = "제목 없음";
		
		try {
			conn = new ConnectionFactory().getConnection();
			String sql = "SELECT title FROM ebook_episodes WHERE book_id = ? AND episode_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookId);
			pstmt.setInt(2, episodeNumber);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				title = rs.getString("title");
			}
		} catch (SQLException e) {
			System.out.println("에피소드 제목 조회 실패: " + e.getMessage());
		} finally {
			JDBCClose.close(conn, pstmt, rs);
		}
		
		return title;
	}
	
	// 특정 회차 정보 반환
	public EpisodeVO getEpisode(int bookId, int episodeNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EpisodeVO episode = null;
		
		try {
			conn = new ConnectionFactory().getConnection();
			String sql = "SELECT * FROM ebook_episodes WHERE book_id = ? AND episode_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookId);
			pstmt.setInt(2, episodeNumber);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				episode = new EpisodeVO();
				episode.setEpisodeId(rs.getInt("episode_id"));
				episode.setBookId(rs.getInt("book_id"));
				episode.setEpisodeNumber(rs.getInt("episode_number"));
				episode.setTitle(rs.getString("title"));
				episode.setContent(rs.getString("content"));
			}
		} catch (SQLException e) {
			System.out.println("에피소드 조회 실패: " + e.getMessage());
		} finally {
			JDBCClose.close(conn, pstmt, rs);
		}
		
		return episode;
	}
}
