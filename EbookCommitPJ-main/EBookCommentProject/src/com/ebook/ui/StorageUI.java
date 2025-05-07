package com.ebook.ui;

import java.util.List;

import com.ebook.service.BookService;
import com.ebook.service.MemberService;
import com.ebook.service.StorageService;
import com.ebook.vo.BookVO;
import com.ebook.vo.MemberVO;
import com.ebook.vo.StorageVO;

public class StorageUI extends BaseUI implements EbookUI {

	private StorageService storageService = new StorageService();
	private MemberVO loginUser;

	// 생성자 초기화
	public StorageUI(MemberService memberService, MemberVO loginUser) {
		super(memberService);
		this.loginUser = loginUser;
	}

	// 보관함 메인 메뉴
	@Override
	public void execute() throws Exception {
		// 로그인 상태 확인
		if (loginUser == null) {
			System.out.println("\n❌ 보관함 접근 실패! 로그인이 필요한 서비스입니다.");
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		showStorageMenu(loginUser.getEmail());
	}

	// 보관함 메뉴 출력 및 선택
	public void showStorageMenu(String userId) {
		while (true) {
			System.out.println("\n====== 📚 보관함 메뉴 ======");
			System.out.println("1. 보관함 목록 보기");
			System.out.println("2. 책 저장하기");
			System.out.println("3. 책 삭제하기");
			System.out.println("0. 뒤로가기");
			String choice = scanStr("선택 >> ");

			switch (choice) {
				case "1":
					showStorageList(userId);
					break;
				case "2":
					saveBookToStorage(userId);
					break;
				case "3":
					deleteBookFromStorage(userId);
					break;
				case "0":
					System.out.println("이전 메뉴로 돌아갑니다.");
					return;
				default:
					System.out.println("⚠️ 잘못된 입력입니다.");
			}
		}
	}

	// 보관함 책 리스트
	private void showStorageList(String userId) {
	    try {
	        List<StorageVO> list = storageService.getStorageList(userId);
	        System.out.println("\n====== 📖 저장된 책 목록 ======");
	        if (list == null || list.isEmpty()) {
	            System.out.println("보관함에 저장된 책이 없습니다.");
	        } else {
	            for (StorageVO book : list) {
	                System.out.println(book);
	            }

	            // 책 선택 옵션 추가
	            int bookId = scanInt("\n읽을 책 ID를 입력하세요 (0: 취소): ");
	            if (bookId > 0) {
	                viewBook(userId, bookId);
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("보관함 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
	    }
	}

	// 책 뷰어 실행 메서드
	private void viewBook(String userId, int bookId) {
	    try {
	        // 책 뷰어로 이동
	        BookViewerUI bookViewer = new BookViewerUI(loginUser, bookId, memberService);
	        bookViewer.execute();
	    } catch (Exception e) {
	        System.out.println("책 뷰어 실행 중 오류가 발생했습니다: " + e.getMessage());
	    }
	}

	// 책 저장
	private void saveBookToStorage(String userId) {
	    try {
	        // 먼저 사용 가능한 책 목록 표시
	        System.out.println("\n[ 저장 가능한 책 목록 ]");
	        BookService bookService = new BookService();
	        List<BookVO> allBooks = bookService.getAllBooks();

	        if (allBooks.isEmpty()) {
	            System.out.println("저장할 수 있는 책이 없습니다.");
	            return;
	        }

	        // 책 목록 표시
	        for (BookVO book : allBooks) {
	            System.out.println(book);
	        }

	        int bookId = scanInt("\n저장할 책 ID를 입력하세요: ");

	        // 중복 저장 여부 확인
	     // StorageUI.java
	        if (storageService.isBookSaved(userId, bookId)) {
	            System.out.println("⚠️ 이미 보관함에 저장된 책입니다.");
	            return;
	        }

	        // 저장 시도
	        boolean success = storageService.saveBook(userId, bookId);
	        if (success) {
	            System.out.println("✅ 책이 보관함에 저장되었습니다.");
	        } else {
	            System.out.println("❌ 저장 실패. 존재하지 않는 책일 수 있습니다.");
	        }

	    } catch (NumberFormatException e) {
	        System.out.println("⚠️ 유효한 숫자를 입력해주세요.");
	    } catch (Exception e) {
	        System.out.println("책 저장 중 오류가 발생했습니다: " + e.getMessage());
	    }
	}

	// 책 삭제
	private void deleteBookFromStorage(String userId) {
		try {
			int bookId = scanInt("삭제할 책 ID를 입력하세요: ");
			boolean success = storageService.deleteBook(userId, bookId);

			if (success) {
				System.out.println("✅ 책이 삭제되었습니다.");
			} else {
				System.out.println("❌ 삭제 실패. 해당 책이 없을 수도 있습니다.");
			}
		} catch (NumberFormatException e) {
			System.out.println("⚠️ 유효한 숫자를 입력해주세요.");
		} catch (Exception e) {
			System.out.println("책 삭제 중 오류가 발생했습니다: " + e.getMessage());
		}
	}
}

