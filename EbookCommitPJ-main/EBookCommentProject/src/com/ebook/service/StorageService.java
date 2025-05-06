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
}

