package com.mattyork.jarhn.HNObjects;

import android.R.integer;

public class HNUser {
	String Username;
	int Karma;
	int Age;
	String AboutInfo;

	/***
	 * Creates a user from a given HTML string.
	 * @param htmlString
	 * @return HNUser
	 */
	public static HNUser userFromHTML(String htmlString) {
		return new HNUser();
	}

}
