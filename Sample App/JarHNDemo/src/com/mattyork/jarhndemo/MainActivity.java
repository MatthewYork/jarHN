package com.mattyork.jarhndemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.mattyork.jarhn.HNWebService.PostFilterType;
import com.mattyork.jarhn.AsyncTasks.LoadPostsWithFilterAsyncTask;
import com.mattyork.jarhn.HNObjects.HNPost;
import com.slidinglayer.SlidingLayer;
import com.slidinglayer.SlidingLayer.OnInteractListener;
//import com.mattyork.jarhn.*;

public class MainActivity extends Activity implements OnInteractListener, OnItemClickListener {
	
	SlidingLayer slidingLayer;
	ListView postsListView;
	ArrayList<HNPost> posts = new ArrayList<HNPost>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Customize action bar
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setTitle(R.string.content_top);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		//Setup left menu
		setupLeftMenu();
		
		//Setup postsTable
		setupPostsListView();
		
		//Fetch top posts
		getPostsWithFilterType(PostFilterType.PostFilterTypeTop);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void getPostsWithFilterType(PostFilterType type) {
		
		LoadPostsWithFilterAsyncTask task = new LoadPostsWithFilterAsyncTask(type){
			
			@Override
			protected void onPostExecute(java.util.ArrayList<HNPost> result) {
				posts = result;
				
				if (posts != null) {
					postsListView.setAdapter(new PostsCellAdapter(MainActivity.this, R.layout.post_cell, posts));
				}
				
				 return;
			};
		};
		task.execute();
	}
	
	public void setupPostsListView() {
		postsListView = (ListView)findViewById(R.id.PostsListView);
		postsListView.setOnItemClickListener(this);
	}
	
	public void setupLeftMenu() {
		slidingLayer = (SlidingLayer) findViewById(R.id.slidingLayer1);

        slidingLayer.setShadowWidthRes(R.dimen.shadow_width);
        slidingLayer.setShadowDrawable(R.drawable.sidebar_shadow);
        slidingLayer.setStickTo(SlidingLayer.STICK_TO_LEFT);
        slidingLayer.setCloseOnTapEnabled(false);
        slidingLayer.setOnInteractListener(this);
        
        LayoutParams rlp = (LayoutParams) slidingLayer.getLayoutParams();
		rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		slidingLayer.setLayoutParams(rlp);
        
        slidingLayer.openLayer(true);
	}
	
	@Override
	public void onOpen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpened() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClosed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case android.R.id.home:
			//Toggle sliding layer
			if (slidingLayer.isOpened()) {
				slidingLayer.closeLayer(true);
			}
			else {
				slidingLayer.openLayer(true);
			}
			break;

		default:
			break;
		}
		
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, LinkActivity.class);
		i.putExtra("url", posts.get(position).UrlString);
		this.startActivity(i);
	}
	
	
}
