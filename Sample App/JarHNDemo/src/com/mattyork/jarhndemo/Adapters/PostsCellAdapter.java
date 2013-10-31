package com.mattyork.jarhndemo.Adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mattyork.jarhn.HNObjects.HNPost;
import com.mattyork.jarhn.HNObjects.HNPost.PostType;
import com.mattyork.jarhndemo.R;
import com.mattyork.jarhndemo.Helpers.SettingsManager;
import com.mattyork.jarhndemo.R.id;

public class PostsCellAdapter extends ArrayAdapter<HNPost> {

	ArrayList<HNPost> posts;
	int layoutResourceId;
	private Context context;
	LayoutInflater inflater;

	TextView mPostTitleTextView;
	TextView mPointsTextView;
	TextView mDateCreatedTextView;
	TextView mCommentCountTextView;

	public PostsCellAdapter(Context context, int resource,
			ArrayList<HNPost> posts) {
		super(context, resource, posts);
		// TODO Auto-generated constructor stub

		// Make assignments
		this.posts = posts;
		this.layoutResourceId = resource;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return this.posts.size();
	}

	public HNPost getItem(int position) throws IndexOutOfBoundsException {
		return this.posts.get(position);
	}

	public long getItemId(int position) throws IndexOutOfBoundsException {
		if (position < getCount() && position >= 0) {
			return position;
		}

		return 0;
	}

	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) { // If the View is not cached
			// Inflates the Common View from XML file
			convertView = this.inflater
					.inflate(layoutResourceId, parent, false);

			// convertView = this.inflater.inflate(R.id.custom_cell, null);
		}

		// Set title
		mPostTitleTextView = (TextView) convertView
				.findViewById(R.id.postCellTitleTextView);
		mPostTitleTextView.setText(posts.get(position).Title);

		// Set points
		mPointsTextView = (TextView) convertView
				.findViewById(R.id.postCellPointsTextView);
		mPointsTextView.setText(posts.get(position).Points + " Points");

		// Set date created
		mDateCreatedTextView = (TextView) convertView
				.findViewById(R.id.postCellDateCreatedTextView);
		mDateCreatedTextView.setText(posts.get(position).TimeCreatedString);

		//Set comment count
		mCommentCountTextView = (TextView)convertView.findViewById(R.id.postCellCommentCountTextView);
		mCommentCountTextView.setText(""+posts.get(position).CommentCount);
		
		//Set background color
		if (posts.get(position).Type == PostType.PostTypeShowHN) {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.ShowHNOrange));
		} else if (posts.get(position).Type == PostType.PostTypeJobs) {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.JobsHNGreen));
		} else if (posts.get(position).Type == PostType.PostTypeDefault) {
			if (SettingsManager.getInstance().usingNightMode) {
				convertView.setBackgroundColor(context.getResources().getColor(R.color.BackgroundDarkGrey));
			}
			else {
				convertView.setBackgroundColor(context.getResources().getColor(R.color.PostCellDayBackground));
			}
		}

		return convertView;
	}
}
