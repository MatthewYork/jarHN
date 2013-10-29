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
import com.mattyork.jarhndemo.R.id;

public class PostsCellAdapter extends ArrayAdapter<HNPost> {

	ArrayList<HNPost> posts;
	int layoutResourceId;
	private Context context;
	LayoutInflater inflater;

	TextView postTitleTextView;
	TextView pointsTextView;
	TextView dateCreatedTextView;

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
		postTitleTextView = (TextView) convertView
				.findViewById(R.id.postCellTitleTextView);
		postTitleTextView.setText(posts.get(position).Title);

		// Set points
		pointsTextView = (TextView) convertView
				.findViewById(R.id.postCellPointsTextView);
		pointsTextView.setText(posts.get(position).Points + " Points");

		// Set date created
		dateCreatedTextView = (TextView) convertView
				.findViewById(R.id.postCellDateCreatedTextView);
		dateCreatedTextView.setText(posts.get(position).TimeCreatedString);

		if (posts.get(position).Type == PostType.PostTypeShowHN) {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.ShowHNOrange));
		} else if (posts.get(position).Type == PostType.PostTypeJobs) {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.JobsHNGreen));
		} else if (posts.get(position).Type == PostType.PostTypeDefault) {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.BackgroundDarkGrey));
		}

		return convertView;
	}
}
