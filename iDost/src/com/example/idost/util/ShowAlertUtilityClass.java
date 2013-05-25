package com.example.idost.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AppCommonBean;

public class ShowAlertUtilityClass {
	
	
	public static void showSettingsAlert(String alertClassification){
		AlertDialog.Builder alertDialog = null;
		if(AppCommonConstantsClass.NET_GPS_NOT_ENABLED.equalsIgnoreCase(alertClassification))
		{
			alertDialog = new AlertDialog.Builder(AppCommonBean.mContext);
			 
	        // Setting Dialog Title
	        alertDialog.setTitle(AppCommonConstantsClass.ENB_PRO_SETTING);
	 
	        // Setting Dialog Message
	        alertDialog.setMessage(AppCommonConstantsClass.USR_SETTING_MSG);
	 
	        // On pressing Settings button
	        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog,int which) {
	                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                AppCommonBean.mContext.startActivity(intent);
	            }
	            
	        });
		
	 	 // on pressing cancel button
	    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        dialog.cancel();
	        ((Activity) AppCommonBean.mContext).finish();
	        }
	    });

		}
		
	// Showing Alert Message
    alertDialog.show();
	}

}
