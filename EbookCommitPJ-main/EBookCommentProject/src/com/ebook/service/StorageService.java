package com.ebook.service;

import java.util.List;

import com.ebook.dao.StorageDAO;
import com.ebook.vo.StorageVO;
import com.ebook.vo.BookVO; 

public class StorageService {

    private StorageDAO storageDAO = new StorageDAO();

    // 책 저장
    public boolean saveBook(StorageVO vo) {
        return storageDAO.insert(vo) > 0; 
    }
    
    public boolean saveBook(String userId, int bookId) {
        BookVO book = storageDAO.selectBookDetail(bookId); 
        if (book == null) return false; 

        // StorageVO 객체를 만들어서
        StorageVO vo = new StorageVO();
        vo.setUserId(userId);  // 사용자 ID
        vo.setBookId(bookId);   // 책 ID
        vo.setTitle(book.getTitle());  
        vo.setAuthor(book.getAuthor()); 
        vo.setCurrentEpisode(0); 
        vo.setSaveDate(new java.util.Date()); 

        // 기존의 saveBook(StorageVO vo) 메서드를 호출
        return saveBook(vo); // 저장
    }


    // 사용자의 보관함 목록 조회
    public List<StorageVO> getStorageList(String userId) {
        return storageDAO.selectByUser(userId); 
    }

    // 보관함에서 책 삭제
    public boolean deleteBook(String userId, int bookId) {
        return storageDAO.delete(userId, bookId) > 0; 
    }

    // 책이 보관함에 저장되어 있는지 여부 확인
    public boolean isBookSaved(String userId, int bookId) {
        return storageDAO.isBookSaved(userId, bookId);
    }

    // 책 상세 정보 조회
    public BookVO getBookDetails(int bookId) {
        return storageDAO.selectBookDetail(bookId); 
    }
    
    // 현재 읽고 있는 회차 조회
    public int getCurrentEpisode(String userId, int bookId) {
        return storageDAO.getCurrentEpisode(userId, bookId);
    }

    // 현재 읽고 있는 회차 업데이트
    public boolean updateCurrentEpisode(String userId, int bookId, int currentEpisode) {
        return storageDAO.updateCurrentEpisode(userId, bookId, currentEpisode);
    }
}
