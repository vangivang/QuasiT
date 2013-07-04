package com.example.quasit;

import com.example.quasit.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class OpenningActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.openning_screen);
		
		new Thread() {
			public void run() {
				try {
					Thread.sleep(3000);
					runOnUiThread(new Runnable() {
						public void run() {
							Intent newIntent = new Intent(OpenningActivity.this, MenuScreenActivity.class);
							startActivity(newIntent);
							overridePendingTransition(R.anim.splashfadein, R.anim.splashfadeout);
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

}
