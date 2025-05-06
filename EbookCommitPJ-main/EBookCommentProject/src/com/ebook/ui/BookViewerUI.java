package com.ebook.ui;

import java.util.List;

import com.ebook.service.BookService;
import com.ebook.service.CommentService;
import com.ebook.service.MemberService;
import com.ebook.vo.BookVO;
import com.ebook.vo.MemberVO;
import com.ebook.vo.CommentVO;

public class BookViewerUI extends BaseUI implements EbookUI {

    private BookService bookService = new BookService();
    private CommentService commentService = new CommentService();

    private MemberVO loginUser;
    private int bookId;

    public BookViewerUI(MemberVO loginUser, int bookId, MemberService memberService) {
        super(memberService);
        this.loginUser = loginUser;
        this.bookId = bookId;
    }

    @Override
    public void execute() throws Exception {
        List<String> bookPages = bookService.getBookPages(bookId);  // 책 내용을 리스트로 받는다고 가정
        int page = 0;
        while (page < bookPages.size()) {
            System.out.println("\n📖 [페이지 " + (page + 1) + "] -----------------------------------");
            System.out.println(bookPages.get(page));
            System.out.println("\nEnter 키를 누르면 다음 페이지로 넘어갑니다...");
            scanStr(""); // 대기

            page++;
        }

        // 마지막 페이지까지 다 보면 자동으로 댓글 UI로 이동
        System.out.println("\n✅ 책을 모두 읽었습니다. 댓글 작성 화면으로 이동합니다...");
        Thread.sleep(1000);  // 자연스러운 전환을 위해 잠깐 대기

        // 댓글 UI 실행
        EbookUI commentUI = new CommentUI(loginUser, bookId, memberService);
        commentUI.execute();
    }
}
