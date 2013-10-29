package com.mattyork.jarhndemo.Fragments;

import java.util.ArrayList;

import com.mattyork.jarhn.HNWebService.PostFilterType;
import com.mattyork.jarhndemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LeftMenuFragment extends Fragment {

	private Button TopFilterButton;
	private Button AskFilterButton;
	private Button NewFilterButton;
	private Button JobsFilterButton;
	private Button BestFilterButton;
	private ArrayList<Button> buttonsArrayList = new ArrayList<Button>(); // Makes
																			// changing
																			// them
																			// easier

	OnLeftMenuSettingChangedListener mCallbackLeftMenuSettingChangedListener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		try {
			mCallbackLeftMenuSettingChangedListener = (OnLeftMenuSettingChangedListener)activity;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_left_menu, container);

		setupFilterButtons(view);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

	private void setupFilterButtons(View view) {
		TopFilterButton = (Button) view
				.findViewById(R.id.leftMenuTopFilterButton);
		AskFilterButton = (Button) view
				.findViewById(R.id.leftMenuAskFilterButton);
		NewFilterButton = (Button) view
				.findViewById(R.id.leftMenuNewFilterButton);
		JobsFilterButton = (Button) view
				.findViewById(R.id.leftMenuJobsFilterButton);
		BestFilterButton = (Button) view
				.findViewById(R.id.leftMenuBestFilterButton);

		// Set onclick listeners

		// Add buttons to list
		buttonsArrayList.add(TopFilterButton);
		buttonsArrayList.add(AskFilterButton);
		buttonsArrayList.add(NewFilterButton);
		buttonsArrayList.add(JobsFilterButton);
		buttonsArrayList.add(BestFilterButton);

		// Default to the "Top" filter button being selected
		setButtonSelected(TopFilterButton);
	}

	private void setButtonSelected(View selectedButton) {
		for (Button button : buttonsArrayList) {
			if (button.getId() == selectedButton.getId()) {
				button.setTextColor(getResources().getColor(R.color.HNOrange));
			} else {
				button.setTextColor(getResources().getColor(
						R.color.LeftMenuTextColor));
			}
		}
	}

	public void didSelectFilterButton(View v) {
		setButtonSelected(v);

		switch (v.getId()) {
		case R.id.leftMenuTopFilterButton:
			this.mCallbackLeftMenuSettingChangedListener.didSelectFilterPosts(PostFilterType.PostFilterTypeTop);
			getActivity().getActionBar().setTitle(R.string.content_top);
			break;
		case R.id.leftMenuAskFilterButton:
			this.mCallbackLeftMenuSettingChangedListener.didSelectFilterPosts(PostFilterType.PostFilterTypeAsk);
			getActivity().getActionBar().setTitle(R.string.content_ask);
			break;
		case R.id.leftMenuNewFilterButton:
			this.mCallbackLeftMenuSettingChangedListener.didSelectFilterPosts(PostFilterType.PostFilterTypeNew);
			getActivity().getActionBar().setTitle(R.string.content_new);
			break;
		case R.id.leftMenuJobsFilterButton:
			this.mCallbackLeftMenuSettingChangedListener.didSelectFilterPosts(PostFilterType.PostFilterTypeJobs);
			getActivity().getActionBar().setTitle(R.string.content_jobs);
			break;
		case R.id.leftMenuBestFilterButton:
			this.mCallbackLeftMenuSettingChangedListener.didSelectFilterPosts(PostFilterType.PostFilterTypeBest);
			getActivity().getActionBar().setTitle(R.string.content_best);
			break;
		default:
			break;
		}
	}
	
	public interface OnLeftMenuSettingChangedListener {
		public void didSelectFilterPosts(PostFilterType type);
	}
}
