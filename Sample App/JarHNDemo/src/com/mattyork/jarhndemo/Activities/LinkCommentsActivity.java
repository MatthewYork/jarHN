package com.mattyork.jarhndemo.Activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

import com.mattyork.jarhndemo.R;
import com.mattyork.jarhndemo.Fragments.CommentsFragment;
import com.mattyork.jarhndemo.Fragments.LinkFragment;
import com.mattyork.jarhndemo.Helpers.SettingsManager;

public class LinkCommentsActivity extends FragmentActivity {

	public static String selectedLinkUrlString;
	
	ViewPager mLinkCommentsViewPager;
	LinkCommentPagerAdapter mPageAdapter;
	
	public static String selectedLinkUrl = "";

	private ShareActionProvider mShareActionProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_link);

		// Setup actionbar
		final ActionBar actionBar = getActionBar();
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		// Setup view pager and tabs
		setupViewPager();
		setupTabs(actionBar);

		// Get link from extra
		selectedLinkUrlString = this.getIntent().getStringExtra("url");
		if (SettingsManager.getInstance().usingReadability) {
			selectedLinkUrlString = "http://www.readability.com/m?url=" + selectedLinkUrlString;
		}

	}
	
	private void setupViewPager() {
		mLinkCommentsViewPager = (ViewPager) findViewById(R.id.linkCommentViewPager);
		mPageAdapter = new LinkCommentPagerAdapter(getSupportFragmentManager());
		mLinkCommentsViewPager.setAdapter(mPageAdapter);
		mLinkCommentsViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
			@Override
            public void onPageSelected(int position) {
                // When swiping between pages, select the
                // corresponding tab.
                getActionBar().setSelectedNavigationItem(position);
            }
		});
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case android.R.id.home:
			// Toggle sliding layer
			this.finish();
			break;
		case R.id.menu_item_share:
			// Trigger share menu
			break;
		default:
			break;
		}

		return super.onMenuItemSelected(featureId, item);
	}

	private void setupTabs(ActionBar actionBar) {
		// Specify that tabs should be displayed in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create a tab listener that is called when the user changes tabs.
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabReselected(Tab tab,
					android.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(Tab tab,
					android.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub
				mLinkCommentsViewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(Tab tab,
					android.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		};

		actionBar.addTab(actionBar.newTab().setText("Link")
				.setTabListener(tabListener));

		actionBar.addTab(actionBar.newTab().setText("Comments")
				.setTabListener(tabListener));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.article_comments_menu, menu);

		// Locate MenuItem with ShareActionProvider
		MenuItem item = menu.findItem(R.id.menu_item_share);

		// Fetch and store ShareActionProvider
		mShareActionProvider = (ShareActionProvider) item.getActionProvider();

		// Return true to display menu
		return true;
	}

	// Call to update the share intent
	private void setShareIntent(Intent shareIntent) {
		if (mShareActionProvider != null) {
			mShareActionProvider.setShareIntent(shareIntent);
		}
	}

	public static class LinkCommentPagerAdapter extends FragmentPagerAdapter {
		private LinkFragment linkFragment;
		private CommentsFragment commentsFragment;
		
		public LinkCommentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0) {
				if (linkFragment == null) {
					linkFragment = new LinkFragment();
				}
				return linkFragment;
			} else {
				if (commentsFragment == null) {
					commentsFragment = new CommentsFragment();
				}
				return commentsFragment;
			}

		}
	}
}
