package com.mattyork.jarhn;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

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
