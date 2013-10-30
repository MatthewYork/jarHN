package com.mattyork.jarhndemo.Fragments;

import java.util.ArrayList;

import com.mattyork.jarhn.HNWebService.PostFilterType;
import com.mattyork.jarhndemo.R;
import com.mattyork.jarhndemo.Helpers.SettingsManager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LeftMenuFragment extends Fragment implements OnClickListener {

	// Top Filter Buttons
	private Button mTopFilterButton;
	private Button mAskFilterButton;
	private Button mNewFilterButton;
	private Button mJobsFilterButton;
	private Button mBestFilterButton;
	private ArrayList<Button> buttonsArrayList = new ArrayList<Button>(); // Makes
																			// changing
																			// them
																			// easier

	// Settings Linear Layouts
	private LinearLayout mReadabilityLinearLayout;
	private LinearLayout mMarkAsReadLinearLayout;
	private LinearLayout mThemeLinearLayout;
	private ImageView mReadabilityImageView;
	private ImageView mMarkAsReadImageView;
	private ImageView mThemeImageView;
	private TextView mReadabilityTextView;
	private TextView mMarkAsReadTextView;
	private TextView mThemeTextView;

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
			mCallbackLeftMenuSettingChangedListener = (OnLeftMenuSettingChangedListener) activity;
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
		setupSettings(view);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

	private void setupFilterButtons(View view) {
		mTopFilterButton = (Button) view
				.findViewById(R.id.leftMenuTopFilterButton);
		mAskFilterButton = (Button) view
				.findViewById(R.id.leftMenuAskFilterButton);
		mNewFilterButton = (Button) view
				.findViewById(R.id.leftMenuNewFilterButton);
		mJobsFilterButton = (Button) view
				.findViewById(R.id.leftMenuJobsFilterButton);
		mBestFilterButton = (Button) view
				.findViewById(R.id.leftMenuBestFilterButton);

		// Set onclick listeners

		// Add buttons to list
		buttonsArrayList.add(mTopFilterButton);
		buttonsArrayList.add(mAskFilterButton);
		buttonsArrayList.add(mNewFilterButton);
		buttonsArrayList.add(mJobsFilterButton);
		buttonsArrayList.add(mBestFilterButton);

		// Default to the "Top" filter button being selected
		setButtonSelected(mTopFilterButton);
	}

	private void setupSettings(View view) {
		// Setup Linear Layouts
		mReadabilityLinearLayout = (LinearLayout) view
				.findViewById(R.id.leftMenuSettingsReadabilityLinearLayout);
		mMarkAsReadLinearLayout = (LinearLayout) view
				.findViewById(R.id.leftMenuSettingsMarkAsReadLinearLayout);
		mThemeLinearLayout = (LinearLayout) view
				.findViewById(R.id.leftMenuSettingsThemeLinearLayout);

		// Setup ImageViews
		mReadabilityImageView = (ImageView) view
				.findViewById(R.id.leftMenuSettingsReadabilityImageView);
		mMarkAsReadImageView = (ImageView) view
				.findViewById(R.id.leftMenuSettingsMarkAsReadImageView);
		mThemeImageView = (ImageView) view
				.findViewById(R.id.leftMenuSettingsThemeImageView);

		// Setup TextViews
		mReadabilityTextView = (TextView) view
				.findViewById(R.id.leftMenuSettingsReadabilityTextView);
		mMarkAsReadTextView = (TextView) view
				.findViewById(R.id.leftMenuSettingsMarkAsReadTextView);
		mThemeTextView = (TextView) view
				.findViewById(R.id.leftMenuSettingsThemeTextView);

		// Add onClickListeners
		mReadabilityLinearLayout.setOnClickListener(this);
		mMarkAsReadLinearLayout.setOnClickListener(this);
		mThemeLinearLayout.setOnClickListener(this);
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
			this.mCallbackLeftMenuSettingChangedListener
					.didSelectFilterPosts(PostFilterType.PostFilterTypeTop);
			getActivity().getActionBar().setTitle(R.string.content_top);
			break;
		case R.id.leftMenuAskFilterButton:
			this.mCallbackLeftMenuSettingChangedListener
					.didSelectFilterPosts(PostFilterType.PostFilterTypeAsk);
			getActivity().getActionBar().setTitle(R.string.content_ask);
			break;
		case R.id.leftMenuNewFilterButton:
			this.mCallbackLeftMenuSettingChangedListener
					.didSelectFilterPosts(PostFilterType.PostFilterTypeNew);
			getActivity().getActionBar().setTitle(R.string.content_new);
			break;
		case R.id.leftMenuJobsFilterButton:
			this.mCallbackLeftMenuSettingChangedListener
					.didSelectFilterPosts(PostFilterType.PostFilterTypeJobs);
			getActivity().getActionBar().setTitle(R.string.content_jobs);
			break;
		case R.id.leftMenuBestFilterButton:
			this.mCallbackLeftMenuSettingChangedListener
					.didSelectFilterPosts(PostFilterType.PostFilterTypeBest);
			getActivity().getActionBar().setTitle(R.string.content_best);
			break;
		default:
			break;
		}
	}

	public interface OnLeftMenuSettingChangedListener {
		public void didSelectFilterPosts(PostFilterType type);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == mReadabilityLinearLayout.getId()) {
			SettingsManager.getInstance().usingReadability = !SettingsManager
					.getInstance().usingReadability;

			if (SettingsManager.getInstance().usingReadability) {
				mReadabilityImageView.setImageResource(R.drawable.nav_readability_on);
			} else {
				mReadabilityImageView.setImageResource(R.drawable.nav_readability_off);
			}
		} else if (v.getId() == mMarkAsReadLinearLayout.getId()) {
			SettingsManager.getInstance().usingMarkAsRead = !SettingsManager
					.getInstance().usingMarkAsRead;

			if (SettingsManager.getInstance().usingMarkAsRead) {
				mMarkAsReadImageView.setImageResource(R.drawable.nav_markasread_on);
			} else {
				mMarkAsReadImageView.setImageResource(R.drawable.nav_markasread_off);
			}
		} else if (v.getId() == mThemeLinearLayout.getId()) {
			SettingsManager.getInstance().usingNightMode = !SettingsManager
					.getInstance().usingNightMode;

			if (SettingsManager.getInstance().usingNightMode) {
				mThemeImageView.setImageResource(R.drawable.nav_nightmode_on);
			} else {
				mThemeImageView.setImageResource(R.drawable.nav_nightmode_off);
			}
		}
	}
}
