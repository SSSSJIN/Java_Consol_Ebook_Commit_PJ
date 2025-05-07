package com.ebook.vo;

public class BookVO {
	private int bookId;
	private String title;
	private String author;
	private String genre;  // genre 필드 추가
	private String description;

	// 생성자
	public BookVO(int bookId, String title, String author, String genre, String description) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.description = description;
	}

	// getter, setter methods
	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	// toString 메서드 추가
	@Override
	public String toString() {
		return String.format("[ID: %d] 제목: %s | 저자: %s | 장르: %s",
				bookId, title, author, genre);
	}
}

