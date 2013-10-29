package com.mattyork.jarhndemo.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.webkit.WebView;

import com.mattyork.jarhndemo.R;
import com.mattyork.jarhndemo.Helpers.SettingsManager;

public class LinkActivity extends FragmentActivity {

	String linkUrlString;
	WebView linkWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.link_activity);
		
		//Setup actionbar
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//Get link from extra
		linkUrlString = this.getIntent().getStringExtra("url");
		if (SettingsManager.getInstance().usingReadability) {
			linkUrlString = "http://www.readability.com/m?url="+linkUrlString;
		}
		
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
