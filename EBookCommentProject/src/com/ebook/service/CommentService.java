package com.ebook.service;

import com.ebook.dao.CommentDAO;
import com.ebook.vo.CommentVO;
import java.util.List;

public class CommentService {

    private CommentDAO commentDAO;

    public CommentService() {
        this.commentDAO = new CommentDAO();
    }

    // 댓글 목록 조회
    public List<CommentVO> getComments(int bookId) {
        return commentDAO.selectByBookId(bookId);
    }

    // 댓글 작성
    public boolean writeComment(int bookId, String userId, String content) {
        CommentVO comment = new CommentVO();
        comment.setBookId(bookId);
        comment.setUserId(userId);
        comment.setContent(content);

        return commentDAO.insert(comment) > 0;
    }
}
