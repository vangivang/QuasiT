package com.example.quasit;

import com.example.quasit.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

public class TimeLineActivity extends FragmentActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {

	private static final String[] from = { StatusProvider.C_USER,
		StatusProvider.C_TEXT, StatusProvider.C_CREATED_AT };
	private static final int[] to = { R.id.tv_user, R.id.tv_text, R.id.tv_created_at };

	private MenuHandler menuHandler;
	private TimeLineUpdateReceiver receiver;
	private ListView statusList;
	private SimpleCursorAdapter mAdapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.timeline);

		menuHandler = new MenuHandler(this);
		statusList = (ListView) findViewById(R.id.textList);
		mAdapter = new SimpleCursorAdapter(this, R.layout.custom_single_item,
				null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		getSupportLoaderManager().initLoader(0, null, this);
		
		// Inject some logic to the returned View.
		mAdapter.setViewBinder(VIEW_BINDER);
		statusList.setAdapter(mAdapter);
		
	//	startService(new Intent(getBaseContext(), RefreshService.class));

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (receiver == null) {
			receiver = new TimeLineUpdateReceiver();
		}
		registerReceiver(receiver, new IntentFilter(
				QuasitApp.TIMELINE_RECEIVER_INTENT_FILTER));
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return menuHandler.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("YO", "item pressed is:::" + item.getItemId());
		return menuHandler.onOptionsItemSelected(item);
	}

	static final ViewBinder VIEW_BINDER = new ViewBinder() {

		@Override
		public boolean setViewValue(View view, Cursor c, int columnIndex) {
			if (view.getId() != R.id.tv_created_at) {
				return false;
			}
			Long milliTime = c.getLong(c
					.getColumnIndex(StatusProvider.C_CREATED_AT));
			// Calculate how much time has passed since milliTime until now
			CharSequence time = DateUtils.getRelativeTimeSpanString(milliTime);
			((TextView) view).setText(time);

			return true;
		}

	};

	class TimeLineUpdateReceiver extends BroadcastReceiver {

		// When a broadcast is received, DB is queried and adapter gets updated.
		// quasitApp calls for this update using an intent filter. The intent
		// filter is registerd here and not in manifest
		@Override
		public void onReceive(Context context, Intent intent) {
			getSupportLoaderManager().restartLoader(0, null, TimeLineActivity.this);
		}

	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(this, StatusProvider.CONTENT_URI, null, null,
				null, StatusProvider.C_CREATED_AT + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		mAdapter.swapCursor(cursor);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);

	}

}
