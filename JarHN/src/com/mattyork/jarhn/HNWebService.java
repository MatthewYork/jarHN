package com.mattyork.jarhn;

import org.apache.http.impl.client.DefaultHttpClient;

public class HNWebService {
	
	public enum PostFilterType{
		PostFilterTypeTop,
	    PostFilterTypeAsk,
	    PostFilterTypeNew,
	    PostFilterTypeJobs,
	    PostFilterTypeBest
	}
	
	/***
	 * Default constructor
	 */
	public HNWebService(){
		client = new DefaultHttpClient();
	}
	
	public DefaultHttpClient client;
	
	
}
