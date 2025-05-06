package com.ebook.ui;

import com.ebook.service.MemberService;
import com.ebook.vo.MemberVO;

public class MainMenuUI extends BaseUI implements EbookUI {

    private MemberVO loginUser;

    public MainMenuUI(MemberService memberService, MemberVO loginUser) {
        super(memberService);
        this.loginUser = loginUser;
    }

    @Override
    public void execute() throws Exception {
        while (true) {
            System.out.println("\n=== 📚 메인 메뉴 ===");
            System.out.println("1. 전체 게시글 조회");
            System.out.println("2. 보관함 진입");
            System.out.println("0. 로그아웃");
            String choice = scanStr(">> 메뉴를 선택하세요 : ");

            switch (choice) {
                case "1":
                    // 전체 게시글 조회 UI 연결
                    new SearchAllUI(memberService).execute();
                    break;
                case "2":
                    // ✅ 보관함 진입 시 loginUser 전달
                    new StorageUI(memberService, loginUser).execute();
                    break;
                case "0":
                    System.out.println("👋 로그아웃합니다.");
                    return;
                default:
                    System.out.println("⚠️ 잘못된 입력입니다.");
            }
        }
    }
}
