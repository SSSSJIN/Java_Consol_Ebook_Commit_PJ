package com.ebook.ui;

import com.ebook.service.MemberService;
import com.ebook.service.MemberServiceFactory;
import com.ebook.vo.MemberVO;

public class AppUI {

    public void start() throws Exception {
        MemberService service = MemberServiceFactory.getInstance();

        while (true) {
            MemberVO loginUser = null;

            while (loginUser == null) {
                MemberLoginUI loginUI = new MemberLoginUI(service);
                loginUI.execute(); // 로그인 결과 받아서 사용
            }           
        }
    }
}
