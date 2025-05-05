package com.ebook.service;

import com.ebook.dao.BookDAO;
import com.ebook.vo.BookVO;

import java.util.List;

// BookDAO를 이용해서 책 정보를 가져오는 클래스
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
}