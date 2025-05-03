package com.ebook.dao;

import com.ebook.vo.MemberVO;
import com.ebook.util.ConnectionFactory;
import com.ebook.util.JDBCClose;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberDAO {

	//사용자가 입력한 이메일 기반 조회 메서드
	public MemberVO getMemberByEmail(String email) {
		MemberVO member = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = new ConnectionFactory().getConnection();

			String sql = "SELECT no, email, password, nickname, regdate FROM T_MEMBER WHERE email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				member = new MemberVO();
				member.setNo(rs.getInt("no"));
				member.setEmail(rs.getString("email"));
				member.setPassword(rs.getString("password"));
				member.setNickname(rs.getString("nickname"));
				member.setRegDate(rs.getString("regdate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(conn, pstmt, rs);
		}

		return member;
	}

	//회원 정보 입력
	public MemberVO insertMember(MemberVO member) {
		MemberVO newMember = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = new ConnectionFactory().getConnection();

			String sql = "INSERT INTO T_MEMBER (no, email, password, nickname, regdate) VALUES (?, ?, ?, SYSDATE)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getNickname());

			int insertResult = pstmt.executeUpdate();

			if (insertResult > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					int newNo = rs.getInt(1);

					newMember = new MemberVO();
					newMember.setNo(newNo);
					newMember.setEmail(member.getEmail());
					newMember.setPassword(member.getPassword());
					newMember.setNickname(member.getNickname());
					newMember.setRegDate(java.time.LocalDate.now().toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(conn, pstmt, rs);
		}

		return newMember;
	}


	// 회원 조회 (PK: no 기준)
	public MemberVO getMemberByNo(int no) {
		MemberVO member = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = new ConnectionFactory().getConnection();

			String sql = "SELECT no, email, password, nickname FROM T_MEMBER WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				member = new MemberVO();
				member.setNo(rs.getInt("no"));
				member.setEmail(rs.getString("email"));
				member.setPassword(rs.getString("password"));
				member.setNickname(rs.getString("nickname"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(conn, pstmt, rs);
		}

		return member;
	}

	// 비밀번호 변경
	public boolean updatePassword(int no, String newPwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean isUpdated = false;

		try {
			conn = new ConnectionFactory().getConnection();

			String sql = "UPDATE T_MEMBER SET password = ? WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPwd);
			pstmt.setInt(2, no);

			isUpdated = pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(conn, pstmt, null);
		}

		return isUpdated;
	}

	// 회원 삭제
	public boolean deleteMember(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean isDeleted = false;

		try {
			conn = new ConnectionFactory().getConnection();

			String sql = "DELETE FROM T_MEMBER WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);

			isDeleted = pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(conn, pstmt, null);
		}

		return isDeleted;
	}
}
