package com.ebook.ui;


import com.ebook.exception.ChoiceOutOfBoundException;
import com.ebook.service.MemberService;

public class MainMenuUI extends BaseUI {
	
	static String choice;

    public MainMenuUI() {
    	choice = "1";
    }
    
    @Override
    public void execute() throws Exception {

		while (true) {
			try {
				EbookUI ui = null;

				switch (choice) {
					case "1":
						ui = new SearchAllUI();    // 전체 게시글 조회
						break;
					case "2":
						ui = new StorageUI();      // 보관함 진입
						break;
					case "3":
						ui = new MemberAccessUI(); // 로그인 또는 회원가입
						break;
					case "0":
						ui = new ExitUI();         // 프로그램 종료
						break;
					default:
						throw new ChoiceOutOfBoundException("\n\t존재하지 않는 메뉴입니다. 다시 선택해주세요.");
				}

				ui.execute();

			} catch (ChoiceOutOfBoundException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
