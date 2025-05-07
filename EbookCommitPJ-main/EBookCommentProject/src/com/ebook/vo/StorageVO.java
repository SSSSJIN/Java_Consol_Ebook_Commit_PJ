package com.ebook.vo;

import java.util.Date;

public class StorageVO {
    private int bookId;
    private String userId;
    private String title;
    private String author;
    private int currentEpisode;
    private Date saveDate;

    // 기본 생성자
    public StorageVO() {
    }

    // 전체 필드용 생성자
    public StorageVO(int bookId, String userId, String title, String author, int currentEpisode, Date saveDate) {
        this.bookId = bookId;
        this.userId = userId;
        this.title = title;
        this.author = author;
        this.currentEpisode = currentEpisode;
        this.saveDate = saveDate;
    }

    // 📌 selectByUser 전용 생성자 (saveDate 제외)
    public StorageVO(int bookId, String userId, String title, String author, int currentEpisode) {
        this.bookId = bookId;
        this.userId = userId;
        this.title = title;
        this.author = author;
        this.currentEpisode = currentEpisode;
    }

    // Getter / Setter
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

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    // ✅ toString() 오버라이드 (출력용)
    @Override
    public String toString() {
        return String.format("📘 책 ID: %d | 제목: %s | 작가: %s | 현재 에피소드: %d",
                bookId, title, author, currentEpisode);
    }
}
