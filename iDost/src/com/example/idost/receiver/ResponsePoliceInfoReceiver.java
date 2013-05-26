package com.example.idost.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.example.idost.service.PoliceAddService;

public class ResponsePoliceInfoReceiver extends BroadcastReceiver{
	
	public static final String ACTION_POL_ADD_RESP = "com.example.idost.service.POLICERESPONSE";
	public static Button callPlcBtn;;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
				if(!PoliceAddService.isPoliceExceptionOccured)
				{
					callPlcBtn.setText("Service loaded everyThing");
					callPlcBtn.setEnabled(true);
				}
				else{
					callPlcBtn.setText("there is an exception in service");
					callPlcBtn.setEnabled(false);
				}
	}

}
