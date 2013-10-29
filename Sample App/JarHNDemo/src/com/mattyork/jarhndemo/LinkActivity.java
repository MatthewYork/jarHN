package com.mattyork.jarhndemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

public class LinkActivity extends Activity {

	String linkUrlString;
	WebView linkWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Setup actionbar
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//Get link from extra
		linkUrlString = this.getIntent().getStringExtra("url");
		
		//Setup link web view
		linkWebView = (WebView)findViewById(R.id.linkWebView);
		//linkWebView.getSettings().setJavaScriptEnabled(true);
		linkWebView.loadUrl(linkUrlString);
		
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case android.R.id.home:
			//Toggle sliding layer
			this.finish();
			break;

		default:
			break;
		}
		
		return super.onMenuItemSelected(featureId, item);
	}

	
}
