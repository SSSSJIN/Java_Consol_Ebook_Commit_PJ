package com.ebook.service;

import com.ebook.dao.MemberDAO;
import com.ebook.vo.MemberVO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MemberService {

	private MemberDAO memberDAO;

	public MemberService() {
		memberDAO = new MemberDAO();
	}	
	
	// 회원 가입
	public boolean RegisterMember(String email, String password, String nickname) {
		MemberVO member = new MemberVO();
		member.setEmail(email);
		member.setPassword(password);
		member.setNickname(nickname);

		// 현재 날짜 및 시간 설정
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		member.setRegDate(now.format(formatter));
		
		memberDAO.insertMember(member);

		return memberDAO.insertMember(member)!=null;
	}

	// 로그인 처리
	public MemberVO login(String email, String password) {
		MemberVO member = memberDAO.getMemberByEmail(email);
		if (member != null && member.getPassword().equals(password)) {
			return member;
		}
		return null;
	}

	// 회원 정보 조회
	public MemberVO getMemberByNo(int no) {
		return memberDAO.getMemberByNo(no);
	}

	// 비밀번호 변경
	public boolean updatePassword(int no, String newPassword) {
		return memberDAO.updatePassword(no, newPassword);
	}

	// 회원 탈퇴
	public boolean deleteMember(int no) {
		return memberDAO.deleteMember(no);
	}

}

