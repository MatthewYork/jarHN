package com.mattyork.jarhndemo.Activities;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import android.R.menu;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.mattyork.jarhn.HNObjects.HNComment.CommentType;
import com.mattyork.jarhn.HNObjects.HNPost.PostType;
import com.mattyork.jarhndemo.R;
import com.mattyork.jarhndemo.Fragments.CommentsFragment;
import com.mattyork.jarhndemo.Fragments.LinkFragment;
import com.mattyork.jarhndemo.Helpers.HackerNewsViewPager;
import com.mattyork.jarhndemo.Helpers.SettingsManager;

public class LinkCommentsActivity extends FragmentActivity implements
		OnClickListener {

	public static String selectedLinkUrlString;
	private int selectedTabIndex;
	private RelativeLayout mMasterRelativeLayout;
	private LinearLayout mTabbarContainerLinearLayout;
	private View mLinkLineView, mCommentLineView;
	private TextView mLinkTextView, mCommentTextView;
	private FrameLayout mLinkFrameLayout, mCommentsFrameLayout;
	PullToRefreshAttacher mPullToRefreshAttacher;

	HackerNewsViewPager mLinkCommentsViewPager;
	LinkCommentPagerAdapter mPageAdapter;

	public static String selectedLinkUrl = "";
	private static int selectedPostType = PostType.PostTypeDefault.ordinal();
	public String formattedUrl = "";
	

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

		mMasterRelativeLayout = (RelativeLayout) findViewById(R.id.LinkCommentActivityMasterRelativeLayout);
		if (SettingsManager.getInstance().usingNightMode) {
			mMasterRelativeLayout.setBackgroundColor(getResources().getColor(
					R.color.BackgroundDarkGrey));
		} else {
			mMasterRelativeLayout.setBackgroundColor(getResources().getColor(
					R.color.PostCellDayBackground));
		}

		// Setup view pager and tabs
		setupViewPager();
		setupTabs();

		// Create a PullToRefreshAttacher instance
		mPullToRefreshAttacher = PullToRefreshAttacher.get(this);

		// Get link from extra
		selectedLinkUrlString = this.getIntent().getStringExtra("url");
		if (selectedLinkUrlString.contains("http")) { // External link
			formattedUrl = selectedLinkUrlString;

			if (SettingsManager.getInstance().usingReadability) {
				selectedLinkUrlString = "http://www.readability.com/m?url="
						+ selectedLinkUrlString;
			}

			selectedPostType = this.getIntent().getIntExtra("selectedPostType",
					0);
			if (selectedPostType == PostType.PostTypeJobs.ordinal()) {
				// Remove top tab bar
				mTabbarContainerLinearLayout.setVisibility(View.GONE);

				// Move to comments page
				mLinkCommentsViewPager.setCurrentItem(0, false);

				// Disable paging
				mLinkCommentsViewPager.setEnabled(false);
			}
		} else { // Internal HN Link
			formattedUrl = "http://news.ycombinator.com/"
					+ selectedLinkUrlString;

			// Remove top tab bar
			mTabbarContainerLinearLayout.setVisibility(View.GONE);

			// Move to comments page
			mLinkCommentsViewPager.setCurrentItem(1, false);

			// Disable paging
			mLinkCommentsViewPager.setPagingEnabled(false);
		}

	}

	private void setupViewPager() {
		mLinkCommentsViewPager = (HackerNewsViewPager) findViewById(R.id.linkCommentViewPager);
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
						if(position==1){
							
						}
					}
				});
	}

	private void setupTabs() {
		// Setup Views
		mTabbarContainerLinearLayout = (LinearLayout) findViewById(R.id.TabBarContainerLinearLayout);
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
		// mShareActionProvider = (ShareActionProvider)
		// item.getActionProvider();
		mShareActionProvider = (ShareActionProvider) (ShareActionProvider) menu
				.findItem(R.id.menu_item_share).getActionProvider();
		setShareIntent(getDefaultShareIntent());

		// Return true to display menu
		return true;
	}

	// Call to update the share intent
	private void setShareIntent(Intent shareIntent) {
		if (mShareActionProvider != null) {
			mShareActionProvider.setShareIntent(shareIntent);
		}
	}

	private Intent getDefaultShareIntent() {
		Intent mShareIntent = new Intent(Intent.ACTION_SEND);
		mShareIntent.setType("text/plain");
		mShareIntent.putExtra(Intent.EXTRA_SUBJECT, "News Y/C");
		mShareIntent.putExtra(Intent.EXTRA_TEXT,
				"Check out this article from hacker news: " + formattedUrl);
		return mShareIntent;
	}

	public static class LinkCommentPagerAdapter extends FragmentPagerAdapter {
		public LinkFragment linkFragment;
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
