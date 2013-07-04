package com.example.quasit;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class RefreshService extends IntentService{

	final static String TAG = "YO";
	
	// Intent service requires this constructor
	public RefreshService() {
		super(TAG);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "RefreshService: onCreated");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		((QuasitApp)getApplication()).pullDataFromTwitterAndInsertIntoDB();
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "UpdateService: onDestroy");
		
	}

}
