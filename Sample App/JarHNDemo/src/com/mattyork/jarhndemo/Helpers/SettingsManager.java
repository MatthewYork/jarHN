package com.mattyork.jarhndemo.Helpers;

import android.R.bool;

import com.mattyork.jarhn.HNWebService.PostFilterType;

public class SettingsManager {
	
	//Singleton instance for settings
	private static SettingsManager settingsManager;
	
	public PostFilterType currentPostFilterType;
	public Boolean usingReadability;
	public Boolean usingMarkAsRead;
	public Boolean usingNightMode;
	
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
