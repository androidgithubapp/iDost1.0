package com.example.idost.service;

import java.util.ArrayList;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;

import com.example.idost.pojo.AppCommonBean;
import com.example.idost.pojo.ContactBean;
import com.example.idost.pojo.CurrentAddressBean;
import com.example.idost.pojo.NearestPoliceInfoBean;
import com.example.idost.receiver.SmsDeliverIdostReceiver;
import com.example.idost.receiver.SmsSendIdostReceiver;

public class MessagingService extends IntentService {

	
	public MessagingService() {
		super("MessagingService");
	}


	boolean running = false;
	
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		

		String currentAddress = CurrentAddressBean.addressLine + "," +  
				CurrentAddressBean.locality;
		
		String nearestPolInfo = "CONTACT:" + NearestPoliceInfoBean.policeNm + "," +
				NearestPoliceInfoBean.policeVicinity; 
				if(NearestPoliceInfoBean.policeIntFrmattedPhNo!=null)
				{
					nearestPolInfo = nearestPolInfo + "PH:" +
							NearestPoliceInfoBean.policeIntFrmattedPhNo;
				}
		
		final String msgContent = "HELP! I am in great danger" + 
									"now I am at:"+currentAddress + " - " +" please contact the police "+  
									nearestPolInfo;
				
		
		SmsManager smsManager = SmsManager.getDefault();
		ArrayList<String> smsParts =smsManager.divideMessage(msgContent);
		int numSmsParts = smsParts.size();
		
		ArrayList<PendingIntent> piSent = new ArrayList<PendingIntent>();
		ArrayList<PendingIntent> piDeliver = new ArrayList<PendingIntent>();
		
		for (int index = 0; index < numSmsParts; index++) {
			piSent.add(PendingIntent.getBroadcast(AppCommonBean.mContext, 0, new Intent(SmsSendIdostReceiver.SMS_SEND_RESP), 0));
			piDeliver.add(PendingIntent.getBroadcast(AppCommonBean.mContext, 0, new Intent(SmsDeliverIdostReceiver.SMS_DELIVER_RESP), 0));
			}
		
		
		if(ContactBean.ContactMap!=null)
		{
			for(String phoneNo : ContactBean.ContactMap.keySet())
			{
				try{
					smsManager.sendMultipartTextMessage(phoneNo, null, smsParts, piSent, piDeliver);
					
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