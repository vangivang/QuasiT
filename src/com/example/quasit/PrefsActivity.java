package com.example.quasit;

import com.example.quasit.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class PrefsActivity extends PreferenceActivity{
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		
	}

}
