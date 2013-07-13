package com.example.idost.activity;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.idost.GetCurrentAddrLocClass;
import com.example.idost.GetLocationClass;
import com.example.idost.R;
import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AppCommonBean;
import com.example.idost.pojo.ContactBean;
import com.example.idost.pojo.NearestPoliceInfoBean;
import com.example.idost.receiver.ResponseCurrentAddReceiver;
import com.example.idost.receiver.ResponsePoliceInfoReceiver;
import com.example.idost.receiver.SmsDeliverIdostReceiver;
import com.example.idost.receiver.SmsSendIdostReceiver;
import com.example.idost.util.AppCallServiceUtilityClass;
import com.example.idost.util.AppCommonExceptionClass;
import com.example.idost.util.AppReflectUtilityClass;
import com.example.idost.util.PreferUtilityClass;
import com.example.idost.util.ShowAlertUtilityClass;



public class MainActivity extends Activity{

    
	private ResponseCurrentAddReceiver currAddreceiver;
	private ResponsePoliceInfoReceiver policeReceiver;
	private SmsSendIdostReceiver smssendreceiver;
	private SmsDeliverIdostReceiver smsdeliverreceiver;
	
	
	private static final int PICK_CONTACT =1;
    GetCurrentAddrLocClass mGetCurrentAddress;
    boolean mBounded;
    Intent mIntent;
    //private TextView viewObj;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
       Button buttonSms = (Button)findViewById(R.id.btnSMS);
       ResponseCurrentAddReceiver.msgBtn = buttonSms;
       buttonSms.setText(AppCommonConstantsClass.FETCH_CURR_ADDR);
       buttonSms.setEnabled(false);
       buttonSms.setOnClickListener(startSmsListener);
       
       Button buttonPhone = (Button)findViewById(R.id.btnCall);
       ResponsePoliceInfoReceiver.callPlcBtn = buttonPhone;
       buttonPhone.setText(AppCommonConstantsClass.FETCH_POL_INF);
       buttonPhone.setEnabled(false);
       buttonPhone.setOnClickListener(startCallListener);
       
       
    }

	//@Override
	
	  protected void onStart() {
	    	super.onStart();
	    	 currAddreceiver = new ResponseCurrentAddReceiver();
	         IntentFilter intFltrCurrAdd = new IntentFilter();
	         intFltrCurrAdd.addAction(ResponseCurrentAddReceiver.ACTION_COMM_ADD_RESP);
	         registerReceiver(currAddreceiver,intFltrCurrAdd);
	         
	         policeReceiver = new ResponsePoliceInfoReceiver();
	         IntentFilter intFltrPolice = new IntentFilter();
	         intFltrPolice.addAction(ResponsePoliceInfoReceiver.ACTION_POL_ADD_RESP);
	         registerReceiver(policeReceiver,intFltrPolice);
	        
	    	
	         
	         smssendreceiver = new SmsSendIdostReceiver();
	         IntentFilter intFltrSmsSend = new IntentFilter(SmsSendIdostReceiver.SMS_SEND_RESP);
	         registerReceiver(smssendreceiver,intFltrSmsSend);

	         smsdeliverreceiver = new SmsDeliverIdostReceiver();
	         IntentFilter intFltrSmsDelivered = new IntentFilter(SmsDeliverIdostReceiver.SMS_DELIVER_RESP);
	         registerReceiver(smsdeliverreceiver,intFltrSmsDelivered);


	    	
	    	 try {
	      	   
	    		 AppCommonBean.mContext = MainActivity.this; 
	    		 PreferUtilityClass.PopulateMap(AppCommonBean.mContext);
	    		 
	      	   	 AppReflectUtilityClass.invokeMethod(AppCommonConstantsClass.GET_LOC_CLS,AppCommonConstantsClass.GET_LOC_MTH,null, null);
	      	   	 
	      	   	 AppCallServiceUtilityClass.getService(AppCommonBean.mContext, AppCommonConstantsClass.CURR_ADD_SERVICE);
		      	 
	      	   	 AppCallServiceUtilityClass.getService(AppCommonBean.mContext, AppCommonConstantsClass.POL_ADD_SERVICE);
		      	 
	      	   	   
	         	}catch(Exception e)
	  			{
	  				Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();
	  				if(AppCommonBean.commonErrMsg.equalsIgnoreCase(AppCommonConstantsClass.LOC_PROVIDER_NULL))
	  				{
	  					ShowAlertUtilityClass.showSettingsAlert(AppCommonConstantsClass.NET_GPS_NOT_ENABLED);
	  				}
	  			}
	    	 
	    	 
	  	}
	    
	    protected void onRestart() {
	    	super.onRestart();
		}

	    protected void onResume() {
	    	super.onResume();
		}

	    protected void onPause() {
	    	super.onPause();
		}

	    protected void onStop() {
	    	super.onStop();
		}

	   
		@Override
	    protected void onDestroy() {
			super.onDestroy();
				
			
			unregisterReceiver(policeReceiver);
	        unregisterReceiver(currAddreceiver);
	        unregisterReceiver(smsdeliverreceiver);
	        unregisterReceiver(smssendreceiver);
	        

	        try {
				AppCallServiceUtilityClass.stopService(MainActivity.this);
			} catch (AppCommonExceptionClass e) {
				
				Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();
  			}
	        
	        AppCommonBean.msgBtnClicked = true;
	        GetLocationClass.locationManager.removeUpdates(GetLocationClass.networkLocationListener);
    		GetLocationClass.locationManager.removeUpdates(GetLocationClass.gpsLocationListener);
    		
	        
	    }

	
		
	/**
	 * code to send SMS
	 */
	private OnClickListener startSmsListener = new OnClickListener() {
		public void onClick(View v)
		{
			
			if(ContactBean.ContactMap!=null && ContactBean.ContactMap.size()>0)
			{
				AppCommonBean.msgBtnClicked = true;
				
				try{

					AppCallServiceUtilityClass.getService(MainActivity.this, AppCommonConstantsClass.MSG_SERVICE);
					}
					catch(Exception e)
					{
						Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();
		  			}
			}else{
				Toast.makeText(AppCommonBean.mContext, AppCommonConstantsClass.NO_CONCT_ADDED, Toast.LENGTH_SHORT).show();
			}
		}
	};

	
	
	/**
	 * code to call police
	 */
	private OnClickListener startCallListener = new OnClickListener() {
		public void onClick(View v)
		{
			
			try{
				String nearestPolPhn = NearestPoliceInfoBean.policeIntFrmattedPhNo;
				if(nearestPolPhn!=null && !("".equalsIgnoreCase(nearestPolPhn)))
				{
					String uriString = AppCommonConstantsClass.POL_CALL_TEL+nearestPolPhn;
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse(uriString));
					startActivity(intent);
				}
				else
				{
					Toast.makeText(AppCommonBean.mContext, AppCommonConstantsClass.POL_CALL_ERR_MSG, Toast.LENGTH_LONG).show();
				}
			}
			catch(Exception e)
			{
				Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();

			}
		}
	};
		

	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId()){
    	
    	case R.id.item_add_contact:
    		if(ContactBean.ContactMap != null && ContactBean.ContactMap.size()<5)
    		{
    		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
    		startActivityForResult(intent,PICK_CONTACT);
    		}else{
    			ContactBean.showMsg=AppCommonConstantsClass.CONT_CANT_ADD;
    			Toast.makeText(AppCommonBean.mContext, ContactBean.showMsg, Toast.LENGTH_LONG).show();
    		}
    		return true;
    	case R.id.item_show_contact:
    		startActivity(new Intent(MainActivity.this,ContactActivity.class));
    		return true;
    	case R.id.stop_sms_service:
    		AppCommonBean.msgBtnClicked = false;
    		GetLocationClass.locationManager.removeUpdates(GetLocationClass.networkLocationListener);
    		GetLocationClass.locationManager.removeUpdates(GetLocationClass.gpsLocationListener);
    		return true;
    	case R.id.about_app:
    		startActivity(new Intent(MainActivity.this,AboutUsActivity.class));
    		return true;
    	default:
    		return false;
    	}
    }


    
    /* (non-Javadoc)
	 * stores the contacts in Preference
	 */
        protected void onActivityResult(int requestCode, int resultCode, Intent data)
        {  
            if (resultCode == RESULT_OK) 
            {  
            	try
            	{
            		Uri contactData = data.getData();  
                	ContentResolver cr=getContentResolver();
                	if(cr!= null)
                	{
                		Cursor cur=cr.query(contactData, null, null, null, null);
                		
                			while(cur.moveToNext())
                        	{
                        		String cid=cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        		String name = cur.getString(cur.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                        		if(Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))>0)
                        		{
                        			Cursor pcur=cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ cid,null, null);
                        			while (pcur.moveToNext())
                        			 {
                        				 String phoneNumber= pcur.getString(pcur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        				 PreferUtilityClass.StoreContact(MainActivity.this, name, phoneNumber);
                        			 }
                        		}
                        		else
                        		{
                        			ContactBean.showMsg=AppCommonConstantsClass.CONCT_ADD_ERR;
                        		}
                        	}
                		
                    	Toast.makeText(AppCommonBean.mContext, ContactBean.showMsg, Toast.LENGTH_LONG).show();
                		
                	}else
                	{
                		Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();
                	}
                	
            	}
            	catch(Exception ex)
            	{
            		Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();            	}
            
            	}
        }

	           
}
