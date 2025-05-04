package com.ebook.main;

import com.ebook.ui.MainMenuUI;
import com.ebook.ui.MemberLoginMenuUI;
import com.ebook.ui.MemberLoginUI;

public class main {

	public static void main(String[] args) throws Exception {
		//MainMenuUI ui = new MainMenuUI(null);
		MemberLoginMenuUI ui = new MemberLoginMenuUI(null);
		ui.execute();

	}

}
