package com.ebook.dao;

public class MemberDAOFactory {

	private static MemberDAO instance = null;
	
	public MemberDAO getInstance() {
		if(instance == null)
			instance = new MemberDAO();
		return instance;
	}
}
