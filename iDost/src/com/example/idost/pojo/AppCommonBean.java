package com.example.idost.pojo;

import android.content.Context;

import com.example.idost.receiver.ResponseCurrentAddReceiver;
import com.example.idost.receiver.SmsDeliverIdostReceiver;
import com.example.idost.receiver.SmsSendIdostReceiver;

public class AppCommonBean {
	public static Context mContext;
	public static Context cContext;
	public static String API_KEY = "AIzaSyASgX9DKn-wabZjEZiMigQRoCAH4QIttKE";
	
	
	public static ResponseCurrentAddReceiver receiver;
	public static SmsSendIdostReceiver smssendreceiver;
	public static SmsDeliverIdostReceiver smsdeliverreceiver;
	
	public static String commonErrMsg;
	
	
}
