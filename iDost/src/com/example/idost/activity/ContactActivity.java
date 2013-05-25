package com.example.idost.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idost.R;
import com.example.idost.pojo.ContactBean;
import com.example.idost.util.PreferUtilityClass;

public class ContactActivity extends Activity {

	private TextView txtvw;
	private ListView contactView=null;
	private ArrayList<String> ConList=null;
	private Button btndel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		this.contactView=(ListView)findViewById(R.id.listView1);
		this.txtvw=(TextView)findViewById(R.id.txtViewContact);
		
		btndel=(Button)findViewById(R.id.btnDelete);
		LoadContactList();
		btndel.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View ConListView)
			{
				 DeleteSelections();
			}
		});

	}
	 private void DeleteSelections() 
	 {
		int chkItemCount=this.contactView.getCheckedItemCount();
		 if(chkItemCount==0)
			Toast.makeText(ContactActivity.this,"Please Select a Contact To Delete",Toast.LENGTH_SHORT).show();
		else
		{
			//ConList=new ArrayList<String>();
			for(int i=0;i<=this.contactView.getCount();i++)
			{
				if(this.contactView.isItemChecked(i))
				{
					ContactBean.ContactMap.remove(this.contactView.getItemAtPosition(i).toString().split(":")[1]);
				}
			}
			PreferUtilityClass.delData(ContactActivity.this);
			PreferUtilityClass.UpdateContactDetails(ContactActivity.this);
			LoadContactList();
		}
		
	}
	public void ShowContactData(String condata)
	 {
		 String[] strarr=condata.split(";");
		 txtvw.setText("");
		 for(int i=0;i<strarr.length;i++)
			 txtvw.append(strarr[i]+"\n");
	 }
	 
	 private ArrayList<String> GetContactList(String condata)
	 {
		 
		 ConList=new ArrayList<String>();
		 if(condata.length()!=0)
		 {
		 String[] strarr=condata.split(";");

		 for(int i=0;i<strarr.length;i++)
		 {
			ConList.add(strarr[i]);
		 }
		 }
		 return ConList;
		 
	 }
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact, menu);
		return true;
	}

	private void LoadContactList()
	{
	
		try {
			ArrayList<String> condata=new ArrayList<String>();
			condata=GetContactList(PreferUtilityClass.GetContact(ContactActivity.this));
			if(condata.size()!=0)
			{
			this.txtvw.setText("");
			this.btndel.setEnabled(true);
			
			contactView.setAdapter(new ArrayAdapter<String>(ContactActivity.this,
					android.R.layout.simple_list_item_multiple_choice,condata));
			
			contactView.setItemsCanFocus(false);
			contactView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			}
			else
			{
				 txtvw.setText("No Contact Added Yet!");
				 this.contactView.setAdapter(null);
				 this.btndel.setEnabled(false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}