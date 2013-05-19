package com.example.idost.service;

import java.util.ArrayList;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;

import com.example.idost.pojo.AppCommonBean;
import com.example.idost.pojo.ContactBean;
import com.example.idost.receiver.SmsDeliverIdostReceiver;
import com.example.idost.receiver.SmsSendIdostReceiver;

public class MessagingService extends IntentService {

	
	public MessagingService() {
		super("MessagingService");
	}


	private static final int DELAY = 30000; //time in milli sec
	private static final String TAG = "MessagingService";
	boolean running = false;
	
	

	
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		

		String currentAddress = CurAddPolAddiDostService.appCommonBean.currentAddressBean.addressLine + "," +  
				CurAddPolAddiDostService.appCommonBean.currentAddressBean.locality;
		
		String nearestPolInfo = "CONTACT:" + CurAddPolAddiDostService.appCommonBean.nearestPoliceInfoBean.policeNm + "," +
				CurAddPolAddiDostService.appCommonBean.nearestPoliceInfoBean.policeVicinity; 
				if(CurAddPolAddiDostService.appCommonBean.nearestPoliceInfoBean.policeIntFrmattedPhNo!=null)
				{
					nearestPolInfo = nearestPolInfo + "PH:" +
							CurAddPolAddiDostService.appCommonBean.nearestPoliceInfoBean.policeIntFrmattedPhNo;
				}
		
		final String msgContent = "HELP!" + 
									"I am at:"+currentAddress + "/" +  
									nearestPolInfo;
		//final String msgContent = "Pls help,I am at : ";

		PendingIntent piSent = PendingIntent.getBroadcast(AppCommonBean.mContext, 0, new Intent(SmsSendIdostReceiver.SMS_SEND_RESP), 0);
		PendingIntent piDeliver = PendingIntent.getBroadcast(AppCommonBean.mContext, 0, new Intent(SmsDeliverIdostReceiver.SMS_DELIVER_RESP), 0);
		SmsManager smsManager = SmsManager.getDefault();
		if(ContactBean.ContactMap!=null)
		{
			for(String phoneNo : ContactBean.ContactMap.keySet())
			{
				try{
					smsManager.sendTextMessage(phoneNo, null, msgContent, piSent, piDeliver);
					//ArrayList<String> parts = smsManager.divideMessage(msgContent);
					//smsManager.sendMultipartTextMessage(phoneNo, null, parts, null, null);
					Thread.sleep(2000);
					
					}catch(Exception e)
					{
						e.printStackTrace();
					}catch(Error err)
					{
						err.printStackTrace();
					}
			}
		}
		
		
		
		stopSelf();
		stopService(intent);
		}
	
	@Override
	  public void onDestroy() {
	    super.onDestroy();
	  }

}
