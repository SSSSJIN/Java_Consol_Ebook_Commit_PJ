package com.ebook.vo;

public class StorageVO {
	
	private int bookId; // 책 고유 번호 (PK)
	private String userId;
	private String title;
	private String author;
	private int currentEpisode;

	public StorageVO() {
	}

	public StorageVO(int bookId, String userId, String title, String author, int currentEpisode) {
		this.bookId = bookId;
		this.userId = userId;
		this.title = title;
		this.author = author;
		this.currentEpisode = currentEpisode;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public int getCurrentEpisode() {
		return currentEpisode;
	}

	public void setCurrentEpisode(int currentEpisode) {
		this.currentEpisode = currentEpisode;
	}

	@Override
	public String toString() {
		return "[" + bookId + "] " + title + " by " + author + " (현재 " + currentEpisode + "화까지 읽음)";
	}
}
