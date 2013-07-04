package com.example.quasit;

import com.example.quasit.R;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MenuHandler {
	
	Activity mActivity;
	
	public MenuHandler(Activity activity){
		mActivity = activity;
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		mActivity.getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent prefsIntent = new Intent(mActivity, PrefsActivity.class);
		Intent refreshIntent = new Intent(mActivity, RefreshService.class);
		Log.d("YO", "item delivered is:::" + item.getItemId());
		
		switch (item.getItemId()) {
		case R.id.item_refreshTimeLine:
			mActivity.startService(refreshIntent);
			return true;
		case R.id.item_prefs:
			mActivity.startActivity(prefsIntent);
			return true;
		default:
			return false;
		}
		
	}

}
