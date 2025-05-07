package com.ebook.vo;

public class EpisodeVO {
	private int episodeId;
	private int bookId;
	private int episodeNumber;
	private String title;
	private String content;
	
	// 생성자
	public EpisodeVO() {}
	
	public EpisodeVO(int episodeId, int bookId, int episodeNumber, String title, String content) {
		this.episodeId = episodeId;
		this.bookId = bookId;
		this.episodeNumber = episodeNumber;
		this.title = title;
		this.content = content;
	}
	
	// Getter, Setter
	public int getEpisodeId() {
		return episodeId;
	}
	
	public void setEpisodeId(int episodeId) {
		this.episodeId = episodeId;
	}
	
	public int getBookId() {
		return bookId;
	}
	
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	public int getEpisodeNumber() {
		return episodeNumber;
	}
	
	public void setEpisodeNumber(int episodeNumber) {
		this.episodeNumber = episodeNumber;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "에피소드 #" + episodeNumber + ": " + title;
	}
}
