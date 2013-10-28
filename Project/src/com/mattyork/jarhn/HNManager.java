package com.mattyork.jarhn;

import java.util.HashMap;

import org.apache.http.cookie.Cookie;

import com.mattyork.jarhn.HNObjects.HNUser;

public class HNManager {

	/***
	 * HN Manager is a singleton, and as such, needs a static reference.
	 */
	private static HNManager instance = null;
	
	public HNWebService Service;
	public String postFNID;
	String userSubmissionFNID;
	Cookie SessionCookie;
	HNUser SessionUser;
	HashMap<String, Integer> MarkAsReadDictionary;
	
	
	protected HNManager(){
		Service = new HNWebService();
	}
	
	public static HNManager getInstance() {
	      if(instance == null) {
	         instance = new HNManager();
	      }
	      return instance;
	   }
}
