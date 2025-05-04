package com.ebook.ui;

import java.util.Scanner;

import com.ebook.service.MemberService;
import com.ebook.vo.MemberVO;

public abstract class BaseUI implements EbookUI {

    protected Scanner sc;
    protected MemberService memberService;

    public BaseUI(MemberService memberService) {
        this.sc = new Scanner(System.in);
        this.memberService = memberService;
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


