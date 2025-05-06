package com.ebook.vo;

import java.io.Serializable;

public class MemberVO implements Serializable {

    private int no; // PK 역할을 하는 회원 번호
    private String email;
    private String password;
    private String nickname;
    private String regDate; // 등록일

    public MemberVO() {}

    public MemberVO(int no, String email, String password, String nickname) {
        this.no = no;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.regDate = regDate;
    }

    // Getter 및 Setter 메서드
    public int getNo() { return no; }
    public void setNo(int no) { this.no = no; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getRegDate() { return regDate; }
    public void setRegDate(String regDate) { this.regDate = regDate; }

    @Override
    public String toString() {
        return "MemberVO [no=" + no + ", email=" + email + ", nickname=" + nickname + ", regDate=" + regDate + "]";
    }
}
