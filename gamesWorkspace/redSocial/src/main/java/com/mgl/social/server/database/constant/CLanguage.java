package com.mgl.social.server.database.constant;

import java.util.ArrayList;

import com.mgl.social.server.database.Language;

public class CLanguage {

	public static final Long ENGLISH = 1L;
	public static final Long SPANISH = 2L;
	public static final Long FRENCH = 3L;
	
	public static ArrayList<Language> getLanguageList() {
		try {
			
			ArrayList<Language > languageList = new ArrayList<Language>();
			Language it = new Language(CLanguage.ENGLISH, "flag_en.jpg");
			languageList.add(it);
			it = new Language(CLanguage.SPANISH, "flag_es.jpg");
			languageList.add(it);
			
			return languageList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
