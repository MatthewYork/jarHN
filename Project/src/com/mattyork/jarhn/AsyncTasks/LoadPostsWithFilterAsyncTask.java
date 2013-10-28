package com.mattyork.jarhn.AsyncTasks;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;

import com.mattyork.jarhn.HNObjects.HNPost;
import com.mattyork.jarhn.HNWebService.PostFilterType;
import com.mattyork.jarhn.HNManager;
import com.mattyork.jarhn.R;

public class LoadPostsWithFilterAsyncTask extends
		AsyncTask<String, Void, ArrayList<HNPost>> {

	Context context;
	String filter;
	String url;
	
	public LoadPostsWithFilterAsyncTask(Context context, PostFilterType filter) {
		// TODO Auto-generated constructor stub
		this.context = context;
		String pathAddition = "";
		
		switch (filter) {
        case PostFilterTypeTop:
            pathAddition = "";
            break;
        case PostFilterTypeAsk:
            pathAddition = "ask";
            break;
        case PostFilterTypeBest:
            pathAddition = "best";
            break;
        case PostFilterTypeJobs:
            pathAddition = "jobs";
            break;
        case PostFilterTypeNew:
            pathAddition = "newest";
            break;
        default:
            break;
    }
		this.url = this.context.getString(R.string.kBaseURLAddress) + pathAddition;
	}
	
	@Override
	protected ArrayList<HNPost> doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		HttpGet httpGet = new HttpGet(url);
		
		String htmlResponseString = "";
		
		HttpResponse response;
		try {
			response = HNManager.getInstance().Service.client.execute(httpGet);
			htmlResponseString = EntityUtils.toString(response.getEntity());
			
			ArrayList<HNPost> posts = HNPost.parsedPostsFromHTML(htmlResponseString);
			
			return posts;
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
