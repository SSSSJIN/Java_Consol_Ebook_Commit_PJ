package com.ebook.ui;

import com.ebook.service.BookService;
import com.ebook.service.MemberService;
import com.ebook.vo.BookVO;

import java.util.List;

public class SearchAllUI extends BaseUI {
	private BookService bookService = new BookService();

	public SearchAllUI(MemberService memberService) {
		super(memberService);
	}

	@Override
	public void execute() throws Exception {
		System.out.println("\n[ 📖 책 목록 조회 메뉴 ]");
		System.out.println("1. 전체 목록 보기");
		System.out.println("2. 제목으로 검색");
		System.out.println("0. 뒤로 가기");
		String choice = scanStr("선택 >> ");

		switch (choice) {
			case "1":
				showAllBooks();
				break;
			case "2":
				searchBooksByTitle();
				break;
			case "0":
				System.out.println("이전 메뉴로 돌아갑니다.");
				break;
			default:
				System.out.println("⚠️ 잘못된 입력입니다.");
		}
	}

	private void showAllBooks() {
		System.out.println("\n[ 전체 책 목록 ]");
		List<BookVO> books = bookService.getAllBooks();

		if (books.isEmpty()) {
			System.out.println("등록된 책이 없습니다.");
		} else {
			printBookList(books);
			selectBookDetail();
		}
	}

	private void searchBooksByTitle() {
		String keyword = scanStr("검색할 책 제목 키워드 입력: ");
		List<BookVO> books = bookService.searchBooksByTitle(keyword);

		System.out.println("\n[ 검색 결과 ]");
		if (books.isEmpty()) {
			System.out.println("검색 결과가 없습니다.");
		} else {
			printBookList(books);
			selectBookDetail();
		}
	}

	private void printBookList(List<BookVO> books) {
		for (BookVO book : books) {
			System.out.println(book);
		}
	}

	private void selectBookDetail() {
		String input = scanStr("상세 보기할 책 ID 입력 (0: 취소): ").replaceAll("[^0-9]", "");  // 숫자만 추출
		if (input.isEmpty()) {
			System.out.println("⚠️ 숫자만 입력해주세요.");
			return;
		}

		int bookId = Integer.parseInt(input);
		if (bookId == 0) return;

		BookVO selectedBook = bookService.getBookById(bookId);
		if (selectedBook != null) {
			System.out.println("\n[ 책 상세 정보 ]");
			System.out.println("제목: " + selectedBook.getTitle());
			System.out.println("저자: " + selectedBook.getAuthor());
			System.out.println("설명: " + selectedBook.getDescription());
			// TODO: 댓글 기능 연결 가능 (CommentUI 등)
		} else {
			System.out.println("해당 책이 존재하지 않습니다.");
		}
	}
}
