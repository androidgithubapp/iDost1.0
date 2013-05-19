package com.example.idost.util;


import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.example.idost.R;
import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.ContactBean;

public class PreferUtilityClass {SharedPreferences shrpref;

public static void StoreContact(Context context, String name,String Phone) 
{
	ContactBean.showMsg="";
	if ((ContactBean.ContactMap == null)
			|| (!ContactBean.ContactMap.containsKey(name))) 
	{
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor edit = prefs.edit();
		String phndata = getData(context);
		if (phndata==null) {
			edit.putString(context.getString(R.string.CONVAL),
					name + ":" + Phone + ";");
		} else 
		{
			edit.putString(context.getString(R.string.CONVAL), phndata
					+ name + ":" + Phone + ";");
			
			
		}
		edit.commit();
		ContactBean.ContactMap.put(Phone, name);
		ContactBean.showMsg=AppCommonConstantsClass.CNTC_ADD;
	}
	else
		ContactBean.showMsg=AppCommonConstantsClass.CNTC_EXIST;
	
}



public static void UpdateContact(Context context, ContactBean conobj) 
{
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	Editor edit = prefs.edit();
	String phndata=getData(context);
	if(phndata==null)
	{
		edit.putString(context.getString(R.string.CONVAL),conobj.getName()+":"+conobj.getPhn());
	}
	else
	edit.putString(context.getString(R.string.CONVAL),phndata+";"+conobj.getName()+":"+conobj.getPhn());
	edit.commit();
}

public static void UpdateContactDetails(Context context,ArrayList<String> contactList)
{
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	Editor edit = prefs.edit();
	edit.putString(context.getString(R.string.CONVAL),StoreContactData(contactList));
	edit.commit();
}

private static String StoreContactData(ArrayList<String> contactList)
{
	String conListData="";
	for(int i=0;i<contactList.size();i++)
	{
		conListData+=contactList.get(i).split(":")[1]+":"+contactList.get(i).split(":")[0]+";";
	}
	return conListData;
}

public static String GetContact(Context context) {
	SharedPreferences prefs = PreferenceManager
			.getDefaultSharedPreferences(context);
	return prefs.getString(context.getString(R.string.CONVAL), "");
}

public static String getData(Context context)
{
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	String data = prefs.getString(context.getString(R.string.CONVAL), null);
	
	if(data==null)
		return null;
	else
	return data;
}

public static void delData(Context context) 
{
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	Editor edit = prefs.edit();
	edit.putString(context.getString(R.string.CONVAL),null);
	edit.commit();
}

public static void PopulateMap(Context context) 
{
	ContactBean.ContactMap=new HashMap<String, String>();
	String conTactData=getData(context);
	if(conTactData!=null)
	{
		String[] conTactListArr=conTactData.split(";");
		for(int i=0;i<conTactListArr.length;i++)
		{
			ContactBean.ContactMap.put(conTactListArr[i].split(":")[1], conTactListArr[i].split(":")[0]);
		}
	}
}}
