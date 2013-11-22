package com.mattyork.jarhndemo.Activities;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.mattyork.jarhndemo.R;
import com.mattyork.jarhndemo.Fragments.CommentsFragment;
import com.mattyork.jarhndemo.Fragments.LinkFragment;
import com.mattyork.jarhndemo.Helpers.SettingsManager;

public class LinkCommentsActivity extends FragmentActivity implements
		OnClickListener {

	public static String selectedLinkUrlString;
	private int selectedTabIndex;
	private RelativeLayout mMasterRelativeLayout;
	private View mLinkLineView, mCommentLineView;
	private TextView mLinkTextView, mCommentTextView;
	private FrameLayout mLinkFrameLayout, mCommentsFrameLayout;
	PullToRefreshAttacher mPullToRefreshAttacher;
	
	ViewPager mLinkCommentsViewPager;
	LinkCommentPagerAdapter mPageAdapter;

	public static String selectedLinkUrl = "";

	private ShareActionProvider mShareActionProvider;
	
	public PullToRefreshAttacher getmPullToRefreshAttacher() {
		return mPullToRefreshAttacher;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_link);

		// Setup actionbar
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mMasterRelativeLayout = (RelativeLayout)findViewById(R.id.LinkCommentActivityMasterRelativeLayout);
		if (SettingsManager.getInstance().usingNightMode) {
			mMasterRelativeLayout.setBackgroundColor(getResources().getColor(R.color.BackgroundDarkGrey));
		}
		else {
			mMasterRelativeLayout.setBackgroundColor(getResources().getColor(R.color.PostCellDayBackground));
		}

		// Setup view pager and tabs
		setupViewPager();
		setupTabs();
		
		// Create a PullToRefreshAttacher instance
	    mPullToRefreshAttacher = PullToRefreshAttacher.get(this);

		// Get link from extra
		selectedLinkUrlString = this.getIntent().getStringExtra("url");
		if (SettingsManager.getInstance().usingReadability) {
			selectedLinkUrlString = "http://www.readability.com/m?url="
					+ selectedLinkUrlString;
		}
		
		//Get/set starting tab
		int startingTabIndex = this.getIntent().getIntExtra("selectedContent", 0);
		mLinkCommentsViewPager.setCurrentItem(startingTabIndex);
	}

	private void setupViewPager() {
		mLinkCommentsViewPager = (ViewPager) findViewById(R.id.linkCommentViewPager);
		mPageAdapter = new LinkCommentPagerAdapter(getSupportFragmentManager());
		mLinkCommentsViewPager.setAdapter(mPageAdapter);
		mLinkCommentsViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between pages, select the
						// corresponding tab.
						selectedTabIndex = position;
						updateTabUI();
					}
				});
	}

	private void setupTabs() {
		// Setup Views
		mLinkFrameLayout = (FrameLayout) findViewById(R.id.LinkFrameLayout);
		mCommentsFrameLayout = (FrameLayout) findViewById(R.id.CommentsFrameLayout);
		mLinkTextView = (TextView) findViewById(R.id.LinkTextView);
		mCommentTextView = (TextView) findViewById(R.id.CommentsTextView);
		mLinkLineView = (View) findViewById(R.id.LinkLine);
		mCommentLineView = (View) findViewById(R.id.CommentsLine);

		mLinkFrameLayout.setOnClickListener(this);
		mCommentsFrameLayout.setOnClickListener(this);

		selectedTabIndex = 0;
		updateTabUI();
	}

	private void updateTabUI() {
		switch (selectedTabIndex) {
		case 0:
			mLinkLineView.setVisibility(View.VISIBLE);
			mCommentLineView.setVisibility(View.INVISIBLE);
			break;
		case 1:
			mCommentLineView.setVisibility(View.VISIBLE);
			mLinkLineView.setVisibility(View.INVISIBLE);
			break;
		default:
			break;
		}
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.LinkFrameLayout:
			selectedTabIndex = 0;
			break;
		case R.id.CommentsFrameLayout:
			selectedTabIndex = 1;
			break;
		default:
			break;
		}

		mLinkCommentsViewPager.setCurrentItem(selectedTabIndex, true);
		updateTabUI();
	}
}
