package com.example.quasit;

import com.example.quasit.R;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


public class StatusActivity extends Activity implements LocationListener {
	
	static final String TAG = "StatusActivity";
	static final String PROVIDER = LocationManager.GPS_PROVIDER;
	
	LocationManager lManager;
	Location location;
	MenuHandler menuHandler;
	EditText editTextStatus;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.status);
		menuHandler = new MenuHandler(this);
		editTextStatus = (EditText) findViewById(R.id.editText_status);
		lManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		location  = lManager.getLastKnownLocation(PROVIDER);
		Log.d(TAG, "onCreate()::: " + location);
		
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		lManager.requestLocationUpdates(PROVIDER, 30000, 10, this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		lManager.removeUpdates(this);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return menuHandler.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return menuHandler.onOptionsItemSelected(item);
    }
	
	public void onClick(View v){
		final String statusText = editTextStatus.getText().toString();
		//AsyncTask
		new PostToQuasit(this, this.location).execute(statusText);
		Log.d("YO", "text: " +statusText);
		editTextStatus.setText("");
		
		// Hide keyboard on post
		InputMethodManager imm =
		(InputMethodManager)getSystemService(StatusActivity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editTextStatus.getWindowToken(), 0);

	}

	@Override
	public void onLocationChanged(Location location) {
		this.location = location;
		Log.d(TAG, "onLocationChanged()::: " + location);
	}

	@Override
	public void onProviderDisabled(String arg0) {
		Toast.makeText(this, "Just know that your location is turned of...", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onProviderEnabled(String arg0) {}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}

}
