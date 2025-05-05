package com.ebook.ui;

import com.ebook.service.BookService;
import com.ebook.vo.BookVO;

import java.util.List;

// 전체 책 목록을 보여주는 화면 클래스
public class SearchAllUI extends BaseUI {
	private BookService bookService = new BookService();

	public SearchAllUI() {
		super(null); // memberService는 사용하지 않음
	}

	@Override
	public void execute() throws Exception {
		System.out.println("\n[ 전체 책 목록 조회 ]");
		List<BookVO> books = bookService.getAllBooks();

		if (books.isEmpty()) {
			System.out.println("등록된 책이 없습니다.");
		} else {
			for (BookVO book : books) {
				System.out.println(book);
			}

			int bookId = scanInt("상세 보기할 책 ID 입력 (0: 취소) : ");
			if (bookId != 0) {
				BookVO selectedBook = bookService.getBookById(bookId);
				if (selectedBook != null) {
					System.out.println("\n[ 책 상세 정보 ]");
					System.out.println("제목 : " + selectedBook.getTitle());
					System.out.println("저자 : " + selectedBook.getAuthor());
					System.out.println("설명 : " + selectedBook.getDescription());
					// 여기에 댓글 조회 및 작성 UI 연결 예정
				} else {
					System.out.println("해당 책이 존재하지 않습니다.");
				}
			}
		}
	}
}