package com.ebook.service;

import java.util.List;

import com.ebook.dao.StorageDAO;
import com.ebook.vo.StorageVO;

public class StorageService {
	
	private StorageDAO storageDAO = new StorageDAO();

	public boolean saveBook(StorageVO vo) {
		int result = storageDAO.insert(vo); // insert 실행 결과 반환
		return result == 1; // 성공하면 true, 실패하면 false
	}

	// 사용자의 보관함 목록 조회
	public List<StorageVO> getStorageList(String userId) {
		return storageDAO.selectByUser(userId); // 해당 userId의 책 리스트 반환
	}

	// 보관함에서 책 삭제
	public boolean deleteBook(String userId, int bookId) {
		int result = storageDAO.delete(userId, bookId); // delete 실행 결과 반환
		return result == 1;
	}
	
	public boolean isBookSaved(String userId, int bookId) {
	    return storageDAO.isBookSaved(userId, bookId);
	}
}
