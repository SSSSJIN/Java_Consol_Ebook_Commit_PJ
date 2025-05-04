package com.ebook.ui;


import com.ebook.exception.ChoiceOutOfBoundException;
import com.ebook.service.MemberService;

public class MainMenuUI extends BaseUI {
	
	public MainMenuUI(MemberService memberService) {
        super(memberService); // ✅ BaseUI에 service 전달
    }
	
    @Override
    public void execute() throws Exception {
    	
		while (true) {
			try {
				  // ✅ 메뉴 출력 추가
				System.out.println("\n === 메인 메뉴 ===");
                System.out.println("1. 전체 게시글 조회");
                System.out.println("2. 보관함 진입");
                System.out.println("0. 프로그램 종료");

                String choice = scanStr(">> 메뉴를 선택하세요 : ");
                
				EbookUI ui = null;

				switch (choice) {
					case "1":
						ui = new SearchAllUI();    // 전체 게시글 조회
						break;
					case "2":
						ui = new StorageUI();      // 보관함 진입
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
