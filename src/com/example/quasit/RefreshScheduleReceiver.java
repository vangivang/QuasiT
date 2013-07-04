package com.example.quasit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

public class RefreshScheduleReceiver extends BroadcastReceiver {

	
	static PendingIntent lastOp;
	// Starts the Refresh service when phone finishes its boot up
	// The actions that trigger it are BOOT_COMPLETED in manifest and BOOT_FILTER in YambaApp
	// It then uses preferences to set an interval on when to fire this service again
	// 
	@Override
	public void onReceive(Context context, Intent intent) {

		long intervalMillis = Long.parseLong(PreferenceManager
				.getDefaultSharedPreferences(context).getString("update_delay",
						String.valueOf(AlarmManager.INTERVAL_FIFTEEN_MINUTES)));

		// Use this PendingIntent with each fire up of the Refresh service
		// When time comes to fire service, it will use the intent filter to start the service.
		PendingIntent operation = PendingIntent.getService(context, -1,
				new Intent(QuasitApp.REFRESH_SERVICE_INTENT_FILTER),
				PendingIntent.FLAG_UPDATE_CURRENT);

		// This alarm will start at the appropriate times and launch the refresh service 
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		
		// Cancel any scheduled alarm operation before negotiating a new one
		alarmManager.cancel(lastOp);
		
		if(intervalMillis != -1){
			alarmManager.setInexactRepeating(AlarmManager.RTC,
					System.currentTimeMillis(), intervalMillis, operation);
		}
		
		lastOp = operation;
		Log.d("YO", "BootReceiver onReceive() + delay = " + intervalMillis);

	}

}
