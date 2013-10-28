package com.mattyork.jarhn;

import java.util.ArrayList;

import org.apache.http.impl.client.DefaultHttpClient;

import com.mattyork.jarhn.AsyncTasks.LoadPostsWithFilterAsyncTask;
import com.mattyork.jarhn.Dtos.DtoLoginRequest;
import com.mattyork.jarhn.Dtos.DtoLoginResponse;
import com.mattyork.jarhn.HNObjects.HNPost;

import android.content.Context;
import android.os.AsyncTask;

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
