package com.ebook.service;

import com.ebook.dao.BookDAO;
import com.ebook.vo.BookVO;

import java.util.List;

public class BookService {
    private BookDAO bookDAO;

    public BookService() {
        bookDAO = new BookDAO();
    }

    public List<BookVO> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public BookVO getBookById(int id) {
        return bookDAO.getBookById(id);
    }

    public List<BookVO> searchBooksByTitle(String keyword) {
        return bookDAO.searchBooksByTitle(keyword);
    }

    // 책의 페이지(에피소드)목록을 DB에서 가져옴
    public List<String> getBookPages(int bookId) {
        return bookDAO.selectEpisodesByBookId(bookId);
    }

    // 댓글 UI로 이동하는 메서드 (실제 댓글 작성 로직은 이곳에서 구현될 수 있음)
    public void goToCommentSection() {
        // 댓글 UI로 이동하는 로직을 여기에 작성
        System.out.println("댓글을 작성하는 UI로 이동합니다...");
        // 여기에 실제로 댓글 UI를 연결하는 로직을 넣을 수 있어
    }
}
