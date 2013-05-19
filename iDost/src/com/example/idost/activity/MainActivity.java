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
import android.widget.TextView;
import android.widget.Toast;

import com.example.idost.GetCurrentAddrLocClass;
import com.example.idost.R;
import com.example.idost.pojo.AppCommonBean;
import com.example.idost.receiver.ResponseIdostReceiver;
import com.example.idost.receiver.SmsDeliverIdostReceiver;
import com.example.idost.receiver.SmsSendIdostReceiver;
import com.example.idost.service.CurAddPolAddiDostService;
import com.example.idost.util.AppCallServiceUtilityClass;
import com.example.idost.util.AppCommonExceptionClass;
import com.example.idost.util.AppReflectUtilityClass;
import com.example.idost.util.PreferUtilityClass;



public class MainActivity extends Activity{

    
	private ResponseIdostReceiver receiver;
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
       buttonSms.setOnClickListener(startSmsListener);
       
       Button buttonPhone = (Button)findViewById(R.id.btnCall);
       buttonPhone.setOnClickListener(startCallListener);
       
       TextView input = (TextView) findViewById(R.id.txtView);
       ResponseIdostReceiver.input = input;
       input.setText("Service is Running...");
    }

	//@Override
	
	  protected void onStart() {
	    	super.onStart();
	    	 try {
	      	   
	    		 AppCommonBean.mContext = MainActivity.this; 
	    		 PreferUtilityClass.PopulateMap(AppCommonBean.mContext);
	      	   	 AppReflectUtilityClass.invokeMethod("com.example.idost.GetLocationClass", "getLocation",null, null);
	      	   	 AppCallServiceUtilityClass.getService(MainActivity.this, "com.example.idost.service.CurAddPolAddiDostService");
		      	   
	      	   	   
	         	}catch(Exception e)
	  			{
	  				e.printStackTrace();
	  			}
	    	 
	    	 receiver = new ResponseIdostReceiver();
	         IntentFilter intFltr = new IntentFilter();
	         intFltr.addAction(ResponseIdostReceiver.ACTION_RESP);
	         registerReceiver(receiver,intFltr);
	         
	         
	         
	         smssendreceiver = new SmsSendIdostReceiver();
	         IntentFilter intFltrSmsSend = new IntentFilter(SmsSendIdostReceiver.SMS_SEND_RESP);
	         registerReceiver(smssendreceiver,intFltrSmsSend);

	         smsdeliverreceiver = new SmsDeliverIdostReceiver();
	         IntentFilter intFltrSmsDelivered = new IntentFilter(SmsDeliverIdostReceiver.SMS_DELIVER_RESP);
	         registerReceiver(smsdeliverreceiver,intFltrSmsDelivered);

		       
	    	 
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
	        this.unregisterReceiver(receiver);
	        this.unregisterReceiver(smssendreceiver);
	        this.unregisterReceiver(smsdeliverreceiver);

	        try {
				AppCallServiceUtilityClass.stopService(MainActivity.this);
			} catch (AppCommonExceptionClass e) {
				
				e.printStackTrace();
			}
	        super.onDestroy();
	    }

	
	/**
	 * code to send SMS
	 */
	private OnClickListener startSmsListener = new OnClickListener() {
		public void onClick(View v)
		{
			try{

				CurAddPolAddiDostService.fireSMSService = true;
				AppCallServiceUtilityClass.getService(MainActivity.this, "com.example.idost.service.MessagingService");
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}catch(Error er)
				{
					er.printStackTrace();
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
				String nearestPolPhn = CurAddPolAddiDostService.appCommonBean.nearestPoliceInfoBean.policeIntFrmattedPhNo;
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
				e.printStackTrace();
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
                	}
            	}
            	catch(Exception ex)
            	{
            		ex.printStackTrace();
            	}
            
            }
        }

	           
}
