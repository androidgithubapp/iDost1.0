package com.example.idost;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.idost.pojo.AllPoliceStationInfoBean;
import com.example.idost.pojo.AppCommonBean;
import com.example.idost.pojo.NearestPoliceInfoBean;
import com.example.idost.util.AppCommonExceptionClass;
import com.example.idost.util.AppReflectUtilityClass;
import com.example.idost.util.GetUrlUtilityClass;

public class NearestPoliceStationInfoClass {
	
		
	public void findNearPoliceStationInfo(AllPoliceStationInfoBean allPoliceStationInfoBean) throws AppCommonExceptionClass 
	    {
		 
		     try {
	        	this.getNearestPoliceInfo(allPoliceStationInfoBean);
	        	/*if(false)
	        	{
	        		while(policeInfo.getPoliceFrmattedPhNo()==null || ("").equalsIgnoreCase(policeInfo.getPoliceFrmattedPhNo()))
	        		{
	        			policeInfo = this.getNearestPoliceInfo(placeList.get(++index));	
	        		}
	        	}*/	        	
	            
	       } catch (AppCommonExceptionClass e) {
	        	throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
		 	       
			}
	      
	    }
	 
	 private void getNearestPoliceInfo(AllPoliceStationInfoBean allPoliceStationInfoBean) throws AppCommonExceptionClass
	 {
		 
		 try{
			 String urlString = this.makeUrl(allPoliceStationInfoBean);
		  
			 AppReflectUtilityClass.invokeMethod("com.example.idost.util.GetUrlUtilityClass", "getUrlConnContents",new Class[] {String.class}, new Object[] {urlString});
			 String jsonNearestPoliceStationInfo = GetUrlUtilityClass.urlContent;

		  
			 JSONObject jsonObj = new JSONObject(jsonNearestPoliceStationInfo);
			 JSONObject jsonObjResult = jsonObj.getJSONObject("result");
			 this.jsonToNearestPoliceInfoBean(jsonObjResult);
          
		 }catch(JSONException jse)
		 {
			 throw new AppCommonExceptionClass(AppCommonBean.mContext, jse);
		 }
		 catch(Exception e)
		 {
			 throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
		 }
         
         
     }
	 
	   private String makeUrl(AllPoliceStationInfoBean allPoliceStationInfoBean) throws AppCommonExceptionClass {
	        String retUrl;
	    	try{
	    	String url = "https://maps.googleapis.com/maps/api/place/details/json?";
	    	
	    	ArrayList<String> params = new ArrayList<String>();
	    	params.add("reference="+allPoliceStationInfoBean.reference);
	    	params.add("&sensor=true");
	    	params.add("&key=" + AppCommonBean.API_KEY);
	    	
	    	
	    	AppReflectUtilityClass.invokeMethod("com.example.idost.util.GetUrlUtilityClass", "getUrl",new Class[] {ArrayList.class,String.class}, new Object[] {params, url});
	    	retUrl = GetUrlUtilityClass.urlStr;
	    	
	    	}catch(Exception e)
	          {
	    		throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
	        	
	          }
	    	return retUrl;
	    }

	 
	   private void jsonToNearestPoliceInfoBean(JSONObject jsonObj) throws AppCommonExceptionClass {
	    	try {
	        	
	        	Iterator<?> iter = jsonObj.keys();
	            while(iter.hasNext()){
	                String jsonkey = (String)iter.next();
	                String value = jsonObj.getString(jsonkey);
	                if(jsonkey.equalsIgnoreCase("id"))
	                {
	                	NearestPoliceInfoBean.policeId = value;
	                }
	                if(jsonkey.equalsIgnoreCase("name"))
	                {
	                	if(value.contains("Police Station"))
	                	{
	                		value = value.replace("Police Station", "Police");
	                	}
	                	NearestPoliceInfoBean.policeNm = value;
	  		          
	                }
	                if(jsonkey.equalsIgnoreCase("formatted_address"))
	                {
	                	NearestPoliceInfoBean.policeFrmattedAdd = value;
		  		          
	                }
	                if(jsonkey.equalsIgnoreCase("international_phone_number"))
	                {
	                	NearestPoliceInfoBean.policeIntFrmattedPhNo = value;
		  		               
	                }
	                if(jsonkey.equalsIgnoreCase("formatted_phone_number"))
	                {
	                	NearestPoliceInfoBean.policeFrmattedPhNo = value;
		  		     
	                }
	                if(jsonkey.equalsIgnoreCase("vicinity"))
	                {
	                	NearestPoliceInfoBean.policeVicinity = value;
			  		     
	                }
	                
	             }
	        	
	        } catch (JSONException ex) {
	        	throw new AppCommonExceptionClass(AppCommonBean.mContext, ex);
	 	        }
	    
	    }
		
}
