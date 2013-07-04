package com.example.quasit;

import com.example.quasit.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

public class MenuScreenActivity extends FragmentActivity{

	private MenuHandler menuHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.menu_openning_screen);
		
		if(((QuasitApp)getApplication()).prefs.getString("username", "").length() == 0  || 
				((QuasitApp)getApplication()).prefs.getString("password", "").length() == 0 || 
				((QuasitApp)getApplication()).prefs.getString("server", "").length() == 0) {
			DialogFragment newFragment = new FirstUpAppDialog();
			newFragment.show(getSupportFragmentManager(), "firstUpApp");
		}
		
		menuHandler = new MenuHandler(this);
		
		findViewById(R.id.left_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MenuScreenActivity.this, StatusActivity.class));
			}
		});
		
		findViewById(R.id.right_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MenuScreenActivity.this, TimeLineActivity.class));
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return menuHandler.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return menuHandler.onOptionsItemSelected(item);
	}
	
}
