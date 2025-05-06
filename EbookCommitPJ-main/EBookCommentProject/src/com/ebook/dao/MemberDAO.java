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

    // 사용자가 입력한 이메일 기반 조회 메서드
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

    // 이메일 중복 확인 메서드 추가
    public boolean isEmailDuplicate(String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isDuplicate = false;

        try {
            conn = new ConnectionFactory().getConnection();

            String sql = "SELECT COUNT(*) FROM T_MEMBER WHERE email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                isDuplicate = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCClose.close(conn, pstmt, rs); // DB 자원 정리
        }

        return isDuplicate;
    }

    // 회원 정보 입력
    public boolean insertMember(MemberVO member) {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	boolean isSuccess = false;

    	try {
    		conn = new ConnectionFactory().getConnection();

    		// no 컬럼을 시퀀스로 생성, PK 컬럼명을 반환받도록 지정
    		String sql = "INSERT INTO T_MEMBER (no, email, password, nickname, regdate) VALUES (T_MEMBER_SEQ.NEXTVAL, ?, ?, ?, SYSDATE)";
    		pstmt = conn.prepareStatement(sql, new String[] {"no"});
    		pstmt.setString(1, member.getEmail());
    		pstmt.setString(2, member.getPassword());
    		pstmt.setString(3, member.getNickname());

    		int insertResult = pstmt.executeUpdate();

    		if (insertResult > 0) {
    			rs = pstmt.getGeneratedKeys();
    			if (rs.next()) {
    				int newNo = rs.getInt(1); // PK 값을 int로 직접 받음
    				member.setNo(newNo);
    				// regdate는 DB에서 SYSDATE로 입력되므로, 다시 SELECT해서 가져오는 것이 정확함
    				String regdateSql = "SELECT regdate FROM T_MEMBER WHERE no = ?";
    				try (PreparedStatement regPstmt = conn.prepareStatement(regdateSql)) {
    					regPstmt.setInt(1, newNo);
    					try (ResultSet regRs = regPstmt.executeQuery()) {
    						if (regRs.next()) {
    							member.setRegDate(regRs.getString("regdate"));
    						}
    					}
    				}
    				isSuccess = true; // 회원가입 성공
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		JDBCClose.close(conn, pstmt, rs);
    	}

    	return isSuccess;
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
