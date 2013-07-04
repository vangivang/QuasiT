package com.example.quasit;

import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


public class PostToQuasit extends AsyncTask<String, Void, String>{
	
	private Activity ctx;
	private ProgressDialog mDialog;
	private Location location;

	public PostToQuasit(Activity ctx, Location location){
		this.ctx = ctx;
		this.location = location;
		mDialog = new ProgressDialog(this.ctx);
	}
	
	@Override
	protected void onPreExecute() {
		mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mDialog.setCancelable(true);
		mDialog.setMessage("Posting...!");
		mDialog.show();
	}
	
	@Override
	protected String doInBackground(String... params) {
		try {
			if(location != null){
				double[] latitudeLongitude = {location.getLatitude(), location.getLongitude() };
				((QuasitApp)ctx.getApplication()).twitter.setMyLocation(latitudeLongitude);
			}
			((QuasitApp)ctx.getApplication()).twitter.setStatus(params[0]);
			return "Successfuly posted";
		} catch (TwitterException e) {
			Log.e("YO", "failed to post:", e);
			return "Failed to post";
		}
	}
	
	@Override
	protected void onPostExecute(String result) {
		if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
		Toast.makeText(ctx.getApplicationContext(), result, Toast.LENGTH_LONG).show();
		super.onPostExecute(result);
	}

}
