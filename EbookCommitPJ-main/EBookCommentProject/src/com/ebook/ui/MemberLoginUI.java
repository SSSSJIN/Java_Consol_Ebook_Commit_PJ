package com.ebook.ui;

import com.ebook.service.MemberService;
import com.ebook.vo.MemberVO;

public class MemberLoginUI extends BaseUI {

	public MemberLoginUI(MemberService memberService) {
		super(memberService);
	}

	public void execute() throws Exception {
		System.out.println("[ 로그인 화면 ]");

		String email = null;
		String pw = null;

		while (true) {
			email = scanStr("이메일 입력 : ");
			if (email == null || email.trim().isEmpty()) {
				System.out.println("이메일은 공백일 수 없습니다. 다시 입력해주세요.");
			} else {
				break;
			}
		}

		while (true) {
			pw = scanStr("비밀번호 입력 : ");
			if (pw == null || pw.trim().isEmpty()) {
				System.out.println("비밀번호는 공백일 수 없습니다. 다시 입력해주세요.");
			} else {
				break;
			}
		}

		MemberVO member = memberService.login(email, pw);

		if (member == null) {
			System.out.println("❌ 로그인 실패! 아이디 또는 비밀번호가 올바르지 않습니다.");
		} else {
			System.out.println("✅ 로그인 성공! 환영합니다, " + member.getNickname() + "님!");
			MainMenuUI mainUI = new MainMenuUI(memberService, member);
			mainUI.execute();
		}
	}
}
