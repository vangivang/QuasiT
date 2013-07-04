package com.example.quasit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class FirstUpAppDialog extends DialogFragment{
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the builder class for convenient dialog construction
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Please set connection preferences")
		.setPositiveButton("Set data", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				startActivity(new Intent(getActivity(), PrefsActivity.class));
				
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// Create this object and return it to the caller
		return builder.create();
		
		
	}

}
