package com.ebook.ui;

import java.util.Scanner;

import com.ebook.service.MemberService;

public abstract class BaseUI implements EbookUI {

    protected Scanner sc;
    protected MemberService boardService;

    public BaseUI(MemberService boardService) {
        this.sc = new Scanner(System.in);
        this.boardService = boardService;
    }

    protected String scanStr(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }

    protected int scanInt(String msg) {
        System.out.print(msg);
        return Integer.parseInt(sc.nextLine());
    }

    // Scanner 자원 관리는 각 UI 클래스에서 책임지도록 한다.
}


