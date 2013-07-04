package com.example.quasit;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.Application;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class QuasitApp extends Application implements
		OnSharedPreferenceChangeListener {

	static final String TAG = "YO";
	static final String TIMELINE_RECEIVER_INTENT_FILTER = "com.example.quasit.UPDATE_STATUS";
	static final String REFRESH_SERVICE_INTENT_FILTER = "com.example.quasit.RefreshService";
	static final String BOOT_INTENT_FILTER = "com.example.quasit.BootFilter";
	public Twitter twitter;
	SharedPreferences prefs;
	private Handler handler;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "quasitApp: onCreate()");
		prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		prefs.registerOnSharedPreferenceChangeListener(this);
		handler = new Handler();
		initTwitter(setPrefrenecsData());
	}

	// pulls data from SharedPreferences into string array
	private String[] setPrefrenecsData() {
		String[] prefData = new String[3];
		prefData[0] = prefs.getString("username", "");
		prefData[1] = prefs.getString("password", "");
		prefData[2] = prefs.getString("server", "");
		return prefData;
	}

	// Injects SharedPreferences data from String array into twitter object
	private void initTwitter(String... args) {
		this.twitter = new Twitter(args[0], args[1]);
		this.twitter.setAPIRootUrl(args[2]);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		Log.d(TAG, "onSharedPrefrenceChanged fired + key");
		initTwitter(setPrefrenecsData());
		sendBroadcast(new Intent(BOOT_INTENT_FILTER));
	}

	public int pullDataFromTwitterAndInsertIntoDB() {
		int count = 0;
		try {
			List<Status> timeLine = twitter.getPublicTimeline();
			for (Status status : timeLine) {
				getContentResolver().insert(StatusProvider.CONTENT_URI,
						statusToContextValues(status));
				Log.d(TAG, status.user.name + "::: " + status.text);
			}
		} catch (TwitterException e) {
			Log.e(TAG, "Failed to pull data", e);
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					Toast.makeText(getApplicationContext(), "Failed to connect to Service. Check preferences!", Toast.LENGTH_LONG).show();
				}
			});
			/*activity.runOnUiThread(new Runnable() {
			    public void run() {
			        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
			    }
			});*/
		}

		// When DB finished inserting new status
		// it sends a broadcast to kick start the TimeLineActivity receiver and
		// update the time line view
		sendBroadcast(new Intent(TIMELINE_RECEIVER_INTENT_FILTER));
		return count;
	}

	private ContentValues statusToContextValues(Status status) {
		ContentValues values = new ContentValues();
		values.put(StatusProvider.C_ID, status.id);
		values.put(StatusProvider.C_CREATED_AT, status.createdAt.getTime());
		values.put(StatusProvider.C_USER, status.user.name);
		values.put(StatusProvider.C_TEXT, status.text);
		return values;
	}


}
