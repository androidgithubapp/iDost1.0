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
import com.example.idost.R;
import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AppCommonBean;
import com.example.idost.pojo.ContactBean;
import com.example.idost.pojo.NearestPoliceInfoBean;
import com.example.idost.receiver.ResponseCurrentAddReceiver;
import com.example.idost.receiver.ResponsePoliceInfoReceiver;
import com.example.idost.receiver.SmsDeliverIdostReceiver;
import com.example.idost.receiver.SmsSendIdostReceiver;
import com.example.idost.service.CurrentAddressService;
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
       buttonSms.setText("Service is loading");
       buttonSms.setEnabled(false);
       buttonSms.setOnClickListener(startSmsListener);
       
       Button buttonPhone = (Button)findViewById(R.id.btnCall);
       ResponsePoliceInfoReceiver.callPlcBtn = buttonPhone;
       buttonPhone.setText("Service is loading");
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
	        
	    	
	         AppCommonBean.smsdeliverreceiver = smsdeliverreceiver;
	         AppCommonBean.smssendreceiver = smssendreceiver;
	         
	         AppCommonBean.smssendreceiver = new SmsSendIdostReceiver();
	         IntentFilter intFltrSmsSend = new IntentFilter(SmsSendIdostReceiver.SMS_SEND_RESP);
	         registerReceiver(AppCommonBean.smssendreceiver,intFltrSmsSend);

	         AppCommonBean.smsdeliverreceiver = new SmsDeliverIdostReceiver();
	         IntentFilter intFltrSmsDelivered = new IntentFilter(SmsDeliverIdostReceiver.SMS_DELIVER_RESP);
	         registerReceiver(AppCommonBean.smsdeliverreceiver,intFltrSmsDelivered);


	    	
	    	 try {
	      	   
	    		 AppCommonBean.mContext = MainActivity.this; 
	    		 PreferUtilityClass.PopulateMap(AppCommonBean.mContext);
	    		 
	      	   	 AppReflectUtilityClass.invokeMethod("com.example.idost.GetLocationClass", "getLocation",null, null);
	      	   	 
	      	   	 AppCallServiceUtilityClass.getService(AppCommonBean.mContext, "com.example.idost.service.CurrentAddressService");
		      	 
	      	   	 AppCallServiceUtilityClass.getService(AppCommonBean.mContext, "com.example.idost.service.PoliceAddService");
		      	 
	      	   	   
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
	        this.unregisterReceiver(currAddreceiver);
	        this.unregisterReceiver(policeReceiver);
	        this.unregisterReceiver(smssendreceiver);
	        this.unregisterReceiver(smsdeliverreceiver);

	        try {
				AppCallServiceUtilityClass.stopService(MainActivity.this);
			} catch (AppCommonExceptionClass e) {
				
				e.printStackTrace();
			}
	        
	    }

	
		
	/**
	 * code to send SMS
	 */
	private OnClickListener startSmsListener = new OnClickListener() {
		public void onClick(View v)
		{
			try{

				CurrentAddressService.fireSMSService = true;
				AppCallServiceUtilityClass.getService(MainActivity.this, "com.example.idost.service.MessagingService");
				}
				catch(Exception e)
				{
					Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();
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
					String uriString = "tel:"+nearestPolPhn;
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse(uriString));
					startActivity(intent);
				}
				else
				{
					Toast.makeText(AppCommonBean.mContext, "Extremely Sorry! the information is not available" +
							"yet. Please try after a few seconds", Toast.LENGTH_LONG).show();
				}
			}
			catch(Exception e)
			{
				Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();

			}
		}
	};
		

	
	
	/* (non-Javadoc)
	 * this portion holds the menu
	 */
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId()){
    	
    	case R.id.item_add_contact:
    		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
    		startActivityForResult(intent,PICK_CONTACT);
    		return true;
    	case R.id.item_show_contact:
    		startActivity(new Intent(MainActivity.this,ContactActivity.class));
    		return true;
    	case R.id.stop_sms_service:
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
                			ContactBean.showMsg="Cannot Add Contact!";
                	}
                	Toast.makeText(AppCommonBean.mContext, ContactBean.showMsg, Toast.LENGTH_LONG).show();
            	}
            	catch(Exception ex)
            	{
            		ex.printStackTrace();
            	}
            
            }
        }

	           
}
