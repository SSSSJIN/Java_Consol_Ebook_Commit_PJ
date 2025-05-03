package com.ebook.ui;

import com.ebook.service.MemberService;
import com.ebook.vo.MemberVO;

public class MemberLoginUI extends BaseUI {

    public MemberLoginUI(MemberService memberService) {
        super(memberService);  // BaseUI의 생성자에 service 전달
    }

    public void execute() throws Exception {
        System.out.println("\n [ 로그인 화면 ]");
        
        System.out.println("이메일 입력 : ");        
        System.out.println("비밀번호 입력 : ");
        
        String email = scanStr("");
        String pw = scanStr("");
        
        while(true) {
        	email = scanStr("");
        	if(email == null || email.trim().isEmpty()) {
        		System.out.println("이메일은 공백일 수 없습니다. 다시 입력해주세요.");
        		System.out.print("이메일 입력 : ");
        	} else {
        		break;
        	}
        }
        
        while(true) {
        	pw = scanStr("");
        	if(pw == null || pw.trim().isEmpty()) {
        		System.out.println("비밀번호는 공백일 수 없습니다. 다시 입력해주세요.");
        		System.out.print("비밀번호 입력 : ");
        	} else {
        		break;
        	}
        }
        
        System.out.println("이메일 입력 : " + email);
        System.out.println("비밀번호 입력 : " + pw);
        
        
        MemberVO member = memberService.login(email, pw);
        
        if (member == null) {
            System.out.println("❌ 로그인 실패! 아이디 또는 비밀번호가 올바르지 않습니다.");
        } else {
            System.out.println("✅ 로그인 성공! 환영합니다, " + member.getNickname() + "님!");
        }

       // return member;
        MainMenuUI mainUI = new MainMenuUI(memberService); //return 받지말고 바로 mainUI로 들어가기
        mainUI.execute();
    }
}
