package com.ebook.ui;

import com.ebook.service.StorageService;
import com.ebook.vo.BookVO;

//예시: 책 상세 보기 UI
public class BookDetailUI {
 private StorageService storageService;

 public BookDetailUI(StorageService storageService) {
     this.storageService = storageService;
 }

 // 책 ID로 상세보기
 public void displayBookDetails(int bookId) {
     BookVO book = storageService.getBookDetails(bookId);
     if (book != null) {
         System.out.println("책 제목: " + book.getTitle());
         System.out.println("저자: " + book.getAuthor());
         System.out.println("설명: " + book.getDescription());
     } else {
         System.out.println("책을 찾을 수 없습니다.");
     }
 }
}
