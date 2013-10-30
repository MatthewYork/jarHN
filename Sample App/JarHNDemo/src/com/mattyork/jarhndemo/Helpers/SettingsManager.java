package com.mattyork.jarhndemo.Helpers;

import android.R.bool;

import com.mattyork.jarhn.HNWebService.PostFilterType;

public class SettingsManager {
	
	//Singleton instance for settings
	private static SettingsManager settingsManager;
	
	public PostFilterType currentPostFilterType = PostFilterType.PostFilterTypeTop;
	public Boolean usingReadability = false;
	public Boolean usingMarkAsRead = false;
	public Boolean usingNightMode = false;
	
	protected SettingsManager() {
		
	}
	
	
	public static SettingsManager getInstance() {
		if (settingsManager == null) {
			settingsManager =  new SettingsManager();
		}
		
		return settingsManager;
	}
	
	public void loadSettingsFromSharedPreferences() {
		this.usingReadability = true;
	}
}
