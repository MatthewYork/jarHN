package com.mattyork.jarhndemo.Fragments;

import java.util.ArrayList;

import com.mattyork.jarhn.AsyncTasks.LoadCommentsFromPostAsyncTask;
import com.mattyork.jarhn.HNObjects.HNComment;
import com.mattyork.jarhndemo.R;
import com.mattyork.jarhndemo.Activities.LinkCommentsActivity;
import com.mattyork.jarhndemo.Activities.MainActivity;
import com.mattyork.jarhndemo.Adapters.CommentCellAdapter;
import com.mattyork.jarhndemo.Helpers.SettingsManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CommentsFragment extends Fragment implements OnItemClickListener{
	
	ArrayList<HNComment> comments = new ArrayList<HNComment>();
	ListView mCommentsListView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_comments, null);
		
		//Setup listview
		mCommentsListView = (ListView)view.findViewById(R.id.CommentsListView);
		mCommentsListView.setOnItemClickListener(this);
		
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		//Fetch comments
		getComments();
	}
	
	private void getComments() {
		LoadCommentsFromPostAsyncTask task = new LoadCommentsFromPostAsyncTask(MainActivity.selectedPost){

			@Override
			protected void onPostExecute(ArrayList<HNComment> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				
				if(result != null){
					mCommentsListView.setAdapter(new CommentCellAdapter(getActivity(), getThemedCellLayoutId(), result));
				}
			}
		};
		task.execute();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		//Set selected post
		
	}
	
	private int getThemedCellLayoutId() {
		if (SettingsManager.getInstance().usingNightMode) {
			return R.layout.comment_night_cell;
		}
		else {
			return R.layout.comment_day_cell;
		}
	}
}
