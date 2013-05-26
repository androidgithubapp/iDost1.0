package com.example.idost.service;

import java.util.ArrayList;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;

import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AppCommonBean;
import com.example.idost.pojo.ContactBean;
import com.example.idost.pojo.CurrentAddressBean;
import com.example.idost.receiver.SmsDeliverIdostReceiver;
import com.example.idost.receiver.SmsSendIdostReceiver;

public class MessagingService extends IntentService {

	
	public MessagingService() {
		super("MessagingService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		
		try{
				
		String msgContent = AppCommonConstantsClass.MSG_CONT1;
		if(CurrentAddressBean.curraddressLine !=null)
		{
			msgContent = msgContent+AppCommonConstantsClass.MSG_CONT2+CurrentAddressBean.curraddressLine + "," +  CurrentAddressBean.locality; ;
		}
				
		
		SmsManager smsManager = SmsManager.getDefault();
		ArrayList<String> smsParts =smsManager.divideMessage(msgContent);
		
		ArrayList<PendingIntent> piSent = new ArrayList<PendingIntent>();
		ArrayList<PendingIntent> piDeliver = new ArrayList<PendingIntent>();
		
		for (int index = 0; index < smsParts.size(); index++) {
			piSent.add(PendingIntent.getBroadcast(AppCommonBean.mContext, 0, new Intent(SmsSendIdostReceiver.SMS_SEND_RESP), 0));
			piDeliver.add(PendingIntent.getBroadcast(AppCommonBean.mContext, 0, new Intent(SmsDeliverIdostReceiver.SMS_DELIVER_RESP), 0));
			}
		
		
		if(ContactBean.ContactMap!=null && ContactBean.ContactMap.size()>0)
		{
			for(String phoneNo : ContactBean.ContactMap.keySet())
			{
					smsManager.sendMultipartTextMessage(phoneNo, null, smsParts, piSent, piDeliver);
					Thread.sleep(2000);
			}
		}
		else{
			AppCommonBean.commonErrMsg = AppCommonConstantsClass.NO_CONCT_ADDED;
		}
		
		}catch(Exception e)
		{
			AppCommonBean.commonErrMsg = AppCommonConstantsClass.COMMON_ERR_MSG;
		}
		
		stopSelf();
		stopService(intent);
		}
	
	@Override
	  public void onDestroy() {
	    super.onDestroy();
	  }

}
