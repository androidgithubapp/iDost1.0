package com.example.idost.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.example.idost.service.CurrentAddressService;

public class ResponseCurrentAddReceiver extends BroadcastReceiver{

	public static Button msgBtn;
	public static String ACTION_RESP = "com.example.idost.service.CURRADDRESPONSE";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(!CurrentAddressService.isExceptionOccured)
		{
			msgBtn.setText("Service loaded everyThing");
		}
		else{
			msgBtn.setText("there is an exception in service");
		}
		
	}

}
