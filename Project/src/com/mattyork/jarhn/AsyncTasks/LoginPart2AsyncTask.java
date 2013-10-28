package com.mattyork.jarhn.AsyncTasks;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;

import android.content.Context;
import android.os.AsyncTask;

import com.mattyork.jarhn.R;
import com.mattyork.jarhn.Dtos.DtoLoginRequest;
import com.mattyork.jarhn.Dtos.DtoLoginResponse;

public class LoginPart2AsyncTask extends AsyncTask<DtoLoginRequest, Void, DtoLoginResponse> {
	
	Context context;
	DtoLoginRequest request;
	String url;
	
	public LoginPart2AsyncTask(Context context, DtoLoginRequest request) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.request = request;
		this.url = this.context.getString(R.string.kBaseURLAddress) + "newslogin?whence=%6e%65%77%73";
	}
	
	@Override
	protected DtoLoginResponse doInBackground(
			DtoLoginRequest... arg0) {
		
		HttpGet httpGet = new HttpGet(url);/*
		try 
			//Make the request
			HttpResponse response = Service.client.execute(httpGet);
			
			//Get cookie
			List<Cookie> cookies = Service.client.getCookieStore().getCookies();
			if (cookies.size() > 0) {
				@SuppressWarnings("unused")
				Cookie cookie = cookies.get(0);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return null;
	}
	
}
