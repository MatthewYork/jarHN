package com.mattyork.jarhndemo.Adapters;

import java.util.ArrayList;

import android.content.Context;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.mattyork.jarhn.HNObjects.HNComment;
import com.mattyork.jarhndemo.R;

public class CommentCellAdapter extends ArrayAdapter<HNComment> {
	ArrayList<HNComment> comments;
	int layoutResourceId;
	private Context context;
	LayoutInflater inflater;
	
	RelativeLayout mMasterRelativeLayout;
	RelativeLayout mHeaderRelativeLayout;
	TextView mUsernameTextView;
	TextView mDateTextView;
	TextView mTextTextView;
	RelativeLayout mTextRelativeLayout;
	
	public CommentCellAdapter(Context context, int resource,
			ArrayList<HNComment> comments) {
		super(context, resource, comments);
		// TODO Auto-generated constructor stub

		// Make assignments
		this.comments = comments;
		this.layoutResourceId = resource;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return this.comments.size();
	}

	public HNComment getItem(int position) throws IndexOutOfBoundsException {
		return this.comments.get(position);
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

		//Set header
		mHeaderRelativeLayout = (RelativeLayout)convertView.findViewById(R.id.CommentCellHeaderRelativeLayout);
		
		//LayoutParams headerLayoutParams = new LayoutParams(mHeaderRelativeLayout.getLayoutParams());
		//headerLayoutParams.setMargins((comments.get(position).Level)*15, 0, 0, 0);
		//mHeaderRelativeLayout.setLayoutParams(headerLayoutParams);
		
		// Set username
		mUsernameTextView = (TextView) convertView
				.findViewById(R.id.CommentCellUsernameTextView);
		mUsernameTextView.setText(comments.get(position).Username);
		
		//Set Date
		mDateTextView = (TextView)convertView.findViewById(R.id.CommentCellDateTextView);
		mDateTextView.setText(comments.get(position).TimeCreatedString);

		//Set Text
		mTextTextView = (TextView)convertView.findViewById(R.id.CommentCellTextTextView);
		mTextTextView.setText(comments.get(position).Text);
		mTextTextView.setLinkTextColor(context.getResources().getColor(R.color.HNOrange));
		Linkify.addLinks(mTextTextView, Linkify.WEB_URLS);
		
		//Set Level Padding
		mMasterRelativeLayout = (RelativeLayout)convertView.findViewById(R.id.CommentCellMasterRelativeLayout);
		mMasterRelativeLayout.setPadding((comments.get(position).Level - 1)*15 + 15, 0, 0, 0);
		
		//Set textRelativelayout
		
		/*
		//Draw lines
		for (int i = 0; i < comments.get(position).Level; i++) {
			//Create line
			
			
			
		}
		
		final View lineView = new View(context);
		LayoutParams lineLayoutParams = new LayoutParams(5, mTextTextView.getHeight());
		lineLayoutParams.setMargins((comments.get(position).Level)*15, 0, 0, 0);
		lineView.setLayoutParams(lineLayoutParams);
		lineView.setBackgroundColor(context.getResources().getColor(R.color.HNOrange));
		
		//Add line
		mMasterRelativeLayout.addView(lineView);*/
		
		
		return convertView;
	}
}
