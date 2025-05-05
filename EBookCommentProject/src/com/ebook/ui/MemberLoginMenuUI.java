package com.ebook.ui;
import com.ebook.service.MemberService;
import com.ebook.vo.MemberVO;

public class MemberLoginMenuUI extends BaseUI {
	private MemberService memberService = new MemberService();

	public MemberLoginMenuUI(MemberService memberService) {
		super(memberService);
	}

	public void execute() throws Exception {
		while (true) {
			System.out.println("\n[ 로그인 / 회원가입 메뉴 ]");
			System.out.println("1. 로그인");
			System.out.println("2. 회원가입");
			System.out.println("0. 종료");
			
			int choice = scanInt("선택 : ");
			
			switch (choice) {
				case 1:
					login(); // 아래에 정의된 로그인 메서드 호출
					return;
				case 2:
					goToSignupUI(); // 아래에 정의된 회원가입 이동 메서드 호출
					return;
				case 0:
					System.out.println("프로그램을 종료합니다.");
					System.exit(0);
				default:
					System.out.println("⚠️ 잘못된 입력입니다. 다시 선택해주세요.");
			}
		}
	}

	private void login() throws Exception {
		String email = null;
		String pw = null;

		while(true) {
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
			MainMenuUI mainUI = new MainMenuUI(memberService);
			mainUI.execute();
		}
	}

	private void goToSignupUI() throws Exception {
		MemberSignupUI signupUI = new MemberSignupUI(memberService);
		signupUI.execute();
	}
}
