package com.ebook.ui;

import java.util.List;

import com.ebook.service.BookService;
import com.ebook.service.CommentService;
import com.ebook.service.MemberService;
import com.ebook.service.StorageService;
import com.ebook.vo.BookVO;
import com.ebook.vo.MemberVO;
import com.ebook.vo.CommentVO;
import com.ebook.vo.EpisodeVO;

public class BookViewerUI extends BaseUI implements EbookUI {

	private BookService bookService = new BookService();
	private CommentService commentService = new CommentService();
	private StorageService storageService = new StorageService();

	private MemberVO loginUser;
	private int bookId;
	private int currentEpisode = 1;
	private int totalEpisodes;
	private String bookTitle;

	public BookViewerUI(MemberVO loginUser, int bookId, MemberService memberService) {
		super(memberService);
		this.loginUser = loginUser;
		this.bookId = bookId;
		
		// 책 정보 초기화
		try {
			BookVO book = bookService.getBookById(bookId);
			if (book != null) {
				this.bookTitle = book.getTitle();
				this.totalEpisodes = bookService.getTotalEpisodes(bookId);
				
				// 현재 사용자의 마지막 읽은 회차 가져오기
				int savedEpisode = storageService.getCurrentEpisode(loginUser.getEmail(), bookId);
				if (savedEpisode > 0) {
					this.currentEpisode = savedEpisode;
				}
			}
		} catch (Exception e) {
			System.out.println("책 정보를 불러오는 중 오류가 발생했습니다: " + e.getMessage());
		}
	}

	@Override
	public void execute() throws Exception {
		showViewerMenu();
	}
	
	private void showViewerMenu() {
		try {
			while (true) {
				System.out.println("\n====== 📖 " + bookTitle + " (" + currentEpisode + "/" + totalEpisodes + "화) ======");
				System.out.println("1. 회차 리스트 보기");
				System.out.println("2. 현재 회차 보기");
				System.out.println("3. 다음 회차로 이동");
				System.out.println("4. 이전 회차로 이동");
				System.out.println("5. 현재 회차 댓글 보기");
				System.out.println("0. 보관함으로 돌아가기");
				
				String choice = scanStr("선택 >> ");
				
				switch (choice) {
					case "1":
						showEpisodeList();
						break;
					case "2":
						readCurrentEpisode();
						break;
					case "3":
						goToNextEpisode();
						break;
					case "4":
						goToPreviousEpisode();
						break;
					case "5":
						showComments();
						break;
					case "0":
						// 현재 읽은 회차 정보 저장
						storageService.updateCurrentEpisode(loginUser.getEmail(), bookId, currentEpisode);
						System.out.println("보관함으로 돌아갑니다.");
						return;
					default:
						System.out.println("⚠️ 잘못된 입력입니다.");
				}
			}
		} catch (Exception e) {
			System.out.println("책 뷰어 실행 중 오류가 발생했습니다: " + e.getMessage());
		}
	}
	
	// 회차 리스트 보기
	private void showEpisodeList() {
		System.out.println("\n======= 📚 회차 리스트 =======");
		for (int i = 1; i <= totalEpisodes; i++) {
			String marker = (i == currentEpisode) ? "▶️ " : "  ";
			System.out.println(marker + i + "화: " + bookService.getEpisodeTitle(bookId, i));
		}
		
		int selectedEpisode = scanInt("\n이동할 회차 입력 (0: 취소): ");
		if (selectedEpisode > 0 && selectedEpisode <= totalEpisodes) {
			currentEpisode = selectedEpisode;
			readCurrentEpisode();
		}
	}
	
	// 현재 회차 읽기
	private void readCurrentEpisode() {
		try {
			EpisodeVO episode = bookService.getEpisode(bookId, currentEpisode);
			
			if (episode == null) {
				System.out.println("⚠️ 해당 회차를 찾을 수 없습니다.");
				return;
			}
			
			System.out.println("\n======= 📖 " + episode.getTitle() + " =======\n");
			
			// 내용을 적절한 길이로 나누어 표시
			String content = episode.getContent();
			int chunkSize = 300; // 한 번에 보여줄 텍스트 길이
			
			for (int i = 0; i < content.length(); i += chunkSize) {
				int end = Math.min(i + chunkSize, content.length());
				System.out.println(content.substring(i, end));
				
				if (end < content.length()) {
					System.out.println("\n계속 읽으려면 Enter 키를 누르세요...");
					scanStr("");
				}
			}
			
			System.out.println("\n======= 회차 종료 =======");
			
			// 회차를 다 읽은 후 자동으로 댓글 표시
			showComments();
			
			// Storage DB에 현재 회차 업데이트
			storageService.updateCurrentEpisode(loginUser.getEmail(), bookId, currentEpisode);
			
		} catch (Exception e) {
			System.out.println("회차 내용을 불러오는 중 오류가 발생했습니다: " + e.getMessage());
		}
	}
	
	// 다음 회차로 이동
	private void goToNextEpisode() {
		if (currentEpisode < totalEpisodes) {
			currentEpisode++;
			readCurrentEpisode();
		} else {
			System.out.println("⚠️ 이미 마지막 회차입니다.");
		}
	}
	
	// 이전 회차로 이동
	private void goToPreviousEpisode() {
		if (currentEpisode > 1) {
			currentEpisode--;
			readCurrentEpisode();
		} else {
			System.out.println("⚠️ 이미 첫 회차입니다.");
		}
	}
	
	// 댓글 보기 및 기능
	private void showComments() {
		try {
			while (true) {
				// 댓글 목록 조회
				List<CommentVO> comments = commentService.getCommentsByEpisode(bookId, currentEpisode);
				
				System.out.println("\n======= 💬 댓글 목록 =======");
				if (comments.isEmpty()) {
					System.out.println("아직 댓글이 없습니다.");
				} else {
					for (int i = 0; i < comments.size(); i++) {
						CommentVO comment = comments.get(i);
						System.out.println((i+1) + ". " + comment.getNickname() + ": " + comment.getContent() + 
								" [좋아요: " + comment.getLikes() + "]");
					}
				}
				
				System.out.println("\n======= 댓글 메뉴 =======");
				System.out.println("1. 댓글 작성하기");
				System.out.println("2. 댓글에 좋아요 누르기");
				System.out.println("3. 내 댓글 삭제하기");
				System.out.println("0. 뒤로 가기");
				
				String choice = scanStr("선택 >> ");
				
				switch (choice) {
					case "1":
						writeComment();
						break;
					case "2":
						likeComment(comments);
						break;
					case "3":
						deleteMyComment(comments);
						break;
					case "0":
						return;
					default:
						System.out.println("⚠️ 잘못된 입력입니다.");
				}
			}
		} catch (Exception e) {
			System.out.println("댓글 기능 실행 중 오류가 발생했습니다: " + e.getMessage());
		}
	}
	
	// 댓글 작성
	private void writeComment() {
		String content = scanStr("댓글 내용 입력: ");
		
		if (content.trim().isEmpty()) {
			System.out.println("⚠️ 댓글 내용을 입력해주세요.");
			return;
		}
		
		CommentVO comment = new CommentVO();
		comment.setUserId(loginUser.getEmail());
		comment.setNickname(loginUser.getNickname());
		comment.setBookId(bookId);
		comment.setEpisode(currentEpisode);
		comment.setContent(content);
		
		boolean success = commentService.addComment(comment);
		
		if (success) {
			System.out.println("✅ 댓글이 등록되었습니다.");
		} else {
			System.out.println("❌ 댓글 등록에 실패했습니다.");
		}
	}
	
	// 댓글 좋아요
	private void likeComment(List<CommentVO> comments) {
		if (comments.isEmpty()) {
			System.out.println("좋아요할 댓글이 없습니다.");
			return;
		}
		
		int commentIndex = scanInt("좋아요할 댓글 번호: ");
		
		if (commentIndex < 1 || commentIndex > comments.size()) {
			System.out.println("⚠️ 잘못된 댓글 번호입니다.");
			return;
		}
		
		CommentVO comment = comments.get(commentIndex - 1);
		boolean success = commentService.likeComment(comment.getCommentId());
		
		if (success) {
			System.out.println("👍 좋아요를 눌렀습니다.");
		} else {
			System.out.println("❌ 좋아요 실패했습니다.");
		}
	}
	
	// 내 댓글 삭제
	private void deleteMyComment(List<CommentVO> comments) {
		if (comments.isEmpty()) {
			System.out.println("삭제할 댓글이 없습니다.");
			return;
		}
		
		// 내 댓글만 필터링
		System.out.println("\n======= 🗑️ 내 댓글 목록 =======");
		int count = 0;
		for (int i = 0; i < comments.size(); i++) {
			CommentVO comment = comments.get(i);
			if (comment.getUserId().equals(loginUser.getEmail())) {
				System.out.println((i+1) + ". " + comment.getContent());
				count++;
			}
		}
		
		if (count == 0) {
			System.out.println("삭제할 내 댓글이 없습니다.");
			return;
		}
		
		int commentIndex = scanInt("삭제할 댓글 번호: ");
		
		if (commentIndex < 1 || commentIndex > comments.size()) {
			System.out.println("⚠️ 잘못된 댓글 번호입니다.");
			return;
		}
		
		CommentVO comment = comments.get(commentIndex - 1);
		
		// 내 댓글인지 확인
		if (!comment.getUserId().equals(loginUser.getEmail())) {
			System.out.println("⚠️ 본인이 작성한 댓글만 삭제할 수 있습니다.");
			return;
		}
		
		boolean success = commentService.deleteComment(comment.getCommentId());
		
		if (success) {
			System.out.println("✅ 댓글이 삭제되었습니다.");
		} else {
			System.out.println("❌ 댓글 삭제에 실패했습니다.");
		}
	}
}
