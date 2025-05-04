package com.ebook.ui;

import com.ebook.service.MemberService;
import com.ebook.vo.MemberVO;

public class MemberSignupUI extends BaseUI {

    public MemberSignupUI(MemberService memberService) {
        super(memberService);  // BaseUI의 생성자에 service 전달
    }

    public void execute() throws Exception {
        System.out.println("[ 회원가입 화면 ]");

        String email = null;
        String pw = null;
        String nickname = null;

        // 이메일 입력받기
        while (true) {
            email = scanStr("이메일 입력 : ");
            if (email == null || email.trim().isEmpty()) {
                System.out.println("이메일은 공백일 수 없습니다. 다시 입력해주세요.");
            } else {
                break;
            }
        }

        // 비밀번호 입력받기
        while (true) {
            pw = scanStr("비밀번호 입력 : ");
            if (pw == null || pw.trim().isEmpty()) {
                System.out.println("비밀번호는 공백일 수 없습니다. 다시 입력해주세요.");
            } else {
                break;
            }
        }

        // 닉네임 입력받기
        while (true) {
            nickname = scanStr("닉네임 입력 : ");
            if (nickname == null || nickname.trim().isEmpty()) {
                System.out.println("닉네임은 공백일 수 없습니다. 다시 입력해주세요.");
            } else {
                break;
            }
        }

        // 회원가입 시도
        boolean isSuccess = memberService.RegisterMember(email, pw, nickname);

        if (isSuccess) {
        	System.out.println("✅ 회원가입 성공! 로그인 화면으로 돌아갑니다.");
        	MemberLoginUI loginUI = new MemberLoginUI(memberService);  // 로그인 화면으로 돌아가기
        	loginUI.execute();
        } else {
        	System.out.println("❌ 회원가입 실패! 다시 시도해주세요.");
        }
    }
}
