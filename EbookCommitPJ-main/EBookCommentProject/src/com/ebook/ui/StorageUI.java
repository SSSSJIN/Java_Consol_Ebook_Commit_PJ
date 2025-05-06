package com.ebook.ui;

import java.util.List;

import com.ebook.service.MemberService;
import com.ebook.service.StorageService;
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
        showStorageMenu(loginUser.getEmail()); // NPE 발생X
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
		List<StorageVO> list = storageService.getStorageList(userId);
		System.out.println("\n====== 📖 저장된 책 목록 ======");
		if (list.isEmpty()) {
			System.out.println("보관함에 저장된 책이 없습니다.");
		} else {
			for (StorageVO book : list) {
				System.out.println(book);
			}
		}
	}

	// 책 저장
	private void saveBookToStorage(String userId) {
		int bookId = scanInt("책 ID를 입력하세요: ");
		StorageVO vo = new StorageVO();
		vo.setUserId(userId);
		vo.setBookId(bookId);

		boolean isAlreadySaved = storageService.isBookSaved(userId, bookId);

		if (isAlreadySaved) {
			boolean deleted = storageService.deleteBook(userId, bookId);
			if (!deleted) {
				System.out.println("❌ 기존 책 삭제 중 오류가 발생했습니다.");
				return;
			}
		}

		boolean success = storageService.saveBook(vo);

		if (success) {
			System.out.println("✅ 책이 보관함에 저장되었습니다.");
		} else {
			System.out.println("❌ 저장 실패. 다시 시도해주세요.");
		}
	}

	// 책 삭제
	private void deleteBookFromStorage(String userId) {
		int bookId = scanInt("삭제할 책 ID를 입력하세요: ");
		boolean success = storageService.deleteBook(userId, bookId);

		if (success) {
			System.out.println("✅ 책이 삭제되었습니다.");
		} else {
			System.out.println("❌ 삭제 실패. 해당 책이 없을 수도 있습니다.");
		}
	}
}
