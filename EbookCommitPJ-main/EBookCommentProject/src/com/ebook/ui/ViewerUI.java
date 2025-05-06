package com.ebook.ui;

import com.ebook.service.BookService;

import java.util.List;
import java.util.Scanner;

public class ViewerUI {

    private BookService bookService = new BookService();

    public void viewBook(int bookId) {
        // 책 페이지를 DB에서 가져옴
        List<String> bookPages = bookService.getBookPages(bookId);
        Scanner scanner = new Scanner(System.in);

        // 페이지 번호로 책 내용 출력
        for (int i = 0; i < bookPages.size(); i++) {
            System.out.println("페이지 " + (i + 1));
            System.out.println(bookPages.get(i));
            System.out.println("\n[다음 페이지를 보려면 Enter]");

            scanner.nextLine();

            // 마지막 페이지를 본 후 자동으로 댓글 UI로 전환
            if (i == bookPages.size() - 1) {
                System.out.println("\n책을 모두 읽었습니다! 댓글을 작성하시겠습니까?");
                // 댓글 UI로 이동
                bookService.goToCommentSection();
                return;  // 책을 다 보고 난 후 종료
            }
        }
    }
}

