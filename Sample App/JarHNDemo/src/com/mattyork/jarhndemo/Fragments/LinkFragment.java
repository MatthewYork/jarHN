package com.mattyork.jarhndemo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mattyork.jarhndemo.R;
import com.mattyork.jarhndemo.Activities.LinkCommentsActivity;

public class LinkFragment extends Fragment {

	WebView linkWebView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.fragment_link, null);
		
		 // Setup link web view
		 setupWebView(view);
		
		return view;
	}
	
	public void setupWebView(View view) {
		linkWebView = (WebView) view.findViewById(R.id.linkWebView);
		 linkWebView.getSettings().setJavaScriptEnabled(true);
		 linkWebView.loadUrl(LinkCommentsActivity.selectedLinkUrlString);
		 linkWebView.setWebViewClient(new WebViewClient());
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
	
	public void setLinkUrl(String linkUrl) {
		
	}

}
