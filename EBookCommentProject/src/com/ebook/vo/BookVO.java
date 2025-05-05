package com.ebook.vo;

import java.io.Serializable;

// 책 정보를 담는 클래스 (VO: Value Object)
public class BookVO implements Serializable {
	private int bookId;          // 책 고유 ID = PK키
	private String title;        // 책 제목
	private String author;       // 저자
	private String description;  // 책 설명

	public BookVO() {}

	public BookVO(int bookId, String title, String author, String description) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.description = description;
	}

	// Getter/Setter
	public int getBookId() { return bookId; }
	public void setBookId(int bookId) { this.bookId = bookId; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	@Override
	public String toString() {
		return "[" + bookId + "] " + title + " - " + author;
	}
}