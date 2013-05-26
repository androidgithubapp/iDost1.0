package com.example.idost.service;

import android.app.IntentService;
import android.content.Intent;

import com.example.idost.GetLocationClass;
import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.receiver.ResponseCurrentAddReceiver;
import com.example.idost.util.AppReflectUtilityClass;

public class CurrentAddressService extends IntentService{

	public static boolean isExceptionOccured;
	
	public CurrentAddressService() {
		super("CurAddPolAddiDostService");
	}
	
	@Override
	  public void onCreate() {
	    super.onCreate();
	  }
	
	@Override
	protected void onHandleIntent(Intent intent) {
		isExceptionOccured = false;
		try{
			
			AppReflectUtilityClass.invokeMethod(AppCommonConstantsClass.CURR_ADDR__CLS,AppCommonConstantsClass.CURR_ADDR_MTH,new Class[] {Double.TYPE,Double.TYPE}, new Object[] {GetLocationClass.location.getLatitude(),GetLocationClass.location.getLongitude()});
		
		}catch(Exception ex)
		{
			isExceptionOccured = true;
		}
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ResponseCurrentAddReceiver.ACTION_COMM_ADD_RESP);
		sendBroadcast(broadcastIntent);
		
		stopSelf();
		stopService(intent);
				
	}
	
	@Override
	  public void onDestroy() {
	    super.onDestroy();
	  }

}
