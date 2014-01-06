package com.eeg_project.Main;
import com.eeg_project.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SetPreference extends PreferenceActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);
	}
	

}
