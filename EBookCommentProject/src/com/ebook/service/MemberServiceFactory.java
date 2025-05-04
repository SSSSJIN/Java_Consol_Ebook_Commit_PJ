package com.ebook.service;

public class MemberServiceFactory {

	private static MemberService service = new MemberService();  // 또는 new MemberServiceImpl();

	public static MemberService getInstance() {
		return service;
    }
}
