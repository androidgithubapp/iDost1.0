package com.example.idost.service;

import android.app.IntentService;
import android.content.Intent;

import com.example.idost.GetLocationClass;
import com.example.idost.receiver.ResponsePoliceInfoReceiver;
import com.example.idost.util.AppReflectUtilityClass;

public class PoliceAddService extends IntentService{

	public static boolean isPoliceExceptionOccured;
	
	public PoliceAddService() {
		super("PoliceAddService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		try{
			AppReflectUtilityClass.invokeMethod("com.example.idost.GetAllPoliceStationInfoClass", "findAllPoliceStationInfo",new Class[] {Double.TYPE,Double.TYPE,String.class}, new Object[] {GetLocationClass.location.getLatitude(),GetLocationClass.location.getLongitude(),"police"});
			
		}catch(Exception ex)
		{
			isPoliceExceptionOccured = true;
		}
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ResponsePoliceInfoReceiver.ACTION_RESP);
		sendBroadcast(broadcastIntent);
		
		stopSelf();
		stopService(intent);
				
	}
	
	@Override
	  public void onDestroy() {
	    super.onDestroy();
	  }

}
