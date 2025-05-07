package com.ebook.service;

import com.ebook.dao.CommentDAO;
import com.ebook.vo.CommentVO;
import java.util.List;

public class CommentService {
	private CommentDAO commentDAO;

	public CommentService() {
		this.commentDAO = new CommentDAO();
	}

	// 책별 댓글 목록 조회 (기존 메서드)
	public List<CommentVO> getComments(int bookId) {
		return commentDAO.selectByBookId(bookId);
	}

	// 댓글 작성 (기존 메서드)
	public boolean writeComment(int bookId, String userId, String content) {
		CommentVO comment = new CommentVO();
		comment.setBookId(bookId);
		comment.setUserId(userId);
		comment.setContent(content);

		return commentDAO.insert(comment) > 0;
	}
	
	// 에피소드별 댓글 목록 조회 (추가된 메서드)
	public List<CommentVO> getCommentsByEpisode(int bookId, int episode) {
		return commentDAO.getCommentsByEpisode(bookId, episode);
	}
	
	// 댓글 추가 (추가된 메서드)
	public boolean addComment(CommentVO comment) {
		return commentDAO.addComment(comment);
	}
	
	// 댓글 좋아요 (추가된 메서드)
	public boolean likeComment(int commentId) {
		return commentDAO.likeComment(commentId);
	}
	
	// 댓글 삭제 (추가된 메서드)
	public boolean deleteComment(int commentId) {
		return commentDAO.deleteComment(commentId);
	}
}
