package com.mattyork.jarhn.AsyncTasks;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.mattyork.jarhn.HNManager;
import com.mattyork.jarhn.HNObjects.HNComment;
import com.mattyork.jarhn.HNObjects.HNPost;

import android.os.AsyncTask;

public class LoadCommentsFromPostAsyncTask extends
		AsyncTask<HNPost, Void, ArrayList<HNComment>> {
	
	String url;
	HNPost mHnPost;

	public LoadCommentsFromPostAsyncTask(HNPost mHnPost) {
		super();
		this.mHnPost = mHnPost;
		
		this.url = "http://news.ycombinator.com/item?id="+mHnPost.PostId;
	}



	@Override
	protected ArrayList<HNComment> doInBackground(HNPost... params) {
		// TODO Auto-generated method stub
		
		HttpGet httpGet = new HttpGet(url);

		String htmlResponseString = "";

		HttpResponse response;
		try {
			response = HNManager.getInstance().Service.client.execute(httpGet);
			htmlResponseString = EntityUtils.toString(response.getEntity());

			ArrayList<HNComment> comments = HNComment
					.parsedCommentsFromHTML(htmlResponseString, mHnPost);

			return comments;

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
