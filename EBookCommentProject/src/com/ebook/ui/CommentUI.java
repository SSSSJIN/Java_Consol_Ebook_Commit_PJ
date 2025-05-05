package com.ebook.ui;

import java.util.List;
import com.ebook.service.CommentService;
import com.ebook.service.MemberService;
import com.ebook.vo.CommentVO;
import com.ebook.vo.MemberVO;

public class CommentUI extends BaseUI implements EbookUI {

    private CommentService commentService;
    private MemberVO loginUser;
    private int bookId;

    public CommentUI(MemberVO loginUser, int bookId, MemberService memberService) {
        super(memberService);
        this.commentService = new CommentService();
        this.loginUser = loginUser;
        this.bookId = bookId;
    }

    @Override
    public void execute() throws Exception {
        showCommentMenu();
    }

    // 댓글 메뉴
    private void showCommentMenu() {
        while (true) {
            System.out.println("\n====== 댓글 메뉴 ======");
            System.out.println("1. 댓글 목록 보기");
            System.out.println("2. 댓글 작성");
            System.out.println("0. 뒤로가기");

            String choice = scanStr("선택 >> ");

            switch (choice) {
                case "1":
                    viewComments();
                    break;
                case "2":
                    writeComment();
                    break;
                case "0":
                    System.out.println("이전 메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println("⚠️ 잘못된 입력입니다.");
            }
        }
    }

    // 댓글 목록 보기
    private void viewComments() {
        System.out.println("\n====== 댓글 목록 ======");
        List<CommentVO> comments = commentService.getComments(bookId);

        if (comments.isEmpty()) {
            System.out.println("댓글이 없습니다. 첫 댓글을 남겨보세요!");
        } else {
            for (CommentVO comment : comments) {
                System.out.printf("- %s: %s\n", comment.getUserId(), comment.getContent());
            }
        }
    }

    // 댓글 작성
    private void writeComment() {
        System.out.println("\n====== 댓글 작성 ======");
        String content = scanStr("댓글을 입력하세요: ");

        // 댓글 작성
        boolean success = commentService.writeComment(bookId, loginUser.getEmail(), content);

        if (success) {
            System.out.println("✅ 댓글이 등록되었습니다.");
        } else {
            System.out.println("❌ 댓글 등록에 실패했습니다.");
        }
    }
}
