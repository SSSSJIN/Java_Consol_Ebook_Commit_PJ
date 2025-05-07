package com.ebook.vo;

import java.sql.Timestamp;

public class CommentVO {
	private int commentId;
	private int bookId;
	private String userId;
	private String nickname;    // 추가: 사용자 닉네임
	private int episode;        // 추가: 에피소드 번호
	private String content;
	private int likes;          // 추가: 좋아요 수
	private Timestamp regDate;  // 추가: 등록일자

	// 기본 생성자
	public CommentVO() {}

	// Getter, Setter
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getEpisode() {
		return episode;
	}

	public void setEpisode(int episode) {
		this.episode = episode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
}
