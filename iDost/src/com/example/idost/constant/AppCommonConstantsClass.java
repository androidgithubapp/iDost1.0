package com.example.idost.constant;

public class AppCommonConstantsClass {
	
	public static String LOC_MNG_NULL = "location manager is null";
	public static String USR_LOC_MNG_NULL = "location not available";
	public static String LOC_PROVIDER_NULL = "location provider is null";
	public static String LOC_NET_PROVIDER_NULL = "network provider is null";
	public static String LOC_GPS_PROVIDER_NULL = "gps provider is null";
	public static String LOC_NULL = "location is null";
	public static String ADDR_NULL = "address is null";
	public static String CNTC_ADD = "Contact added successfully";
	public static String CNTC_EXIST="Contact already exits";
	
	public static String COMMON_ERR_MSG = "iDost has encountered an error Pls try after some time";
	
	public static String CURR_ADDR = "Current add is not available";
	
	
	public static String ENB_PRO_SETTING = "Enable provider setting";
	
	public static String USR_SETTING_MSG = "GPS/Network is not enabled. Do you want to go to settings menu ?";
	public static String USR_SETTING = "Settings";
	
	public static String NET_GPS_NOT_ENABLED =  "netOrGpsEnable";
	
	public static String PROVIDER_GPS = "gps";
	public static String PROVIDER_NETWORK = "network";
	
	public static int SEARCH_PLC_DIST = 5000;
	public static int INCR_PLC_DIST = 5000;
	
	public static String ALL_POL_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
	public static String NEAR_POL_URL = "https://maps.googleapis.com/maps/api/place/details/json?";
	public static String URL_LOC = "&location=";
	public static String URL_RAD = "&radius=";
	public static String URL_RANK = "&rankBy=distance";
	public static String URL_TYPES = "&types=";
	public static String URL_SENSOR = "&sensor=true";
	public static String URl_KEY = "&key=";
	public static String URL_REF = "reference=";
	
	public static String JSON_RESULTS = "results";
	public static String JSON_RESULT = "result";
	
	public static String ALL_POL_ATTR_ICON = "icon";
	public static String ALL_POL_ATTR_NM = "name";
	public static String ALL_POL_ATTR_VICINITY = "vicinity";
	public static String ALL_POL_ATTR_ID = "id";
	public static String ALL_POL_ATTR_REF = "reference";
	
	public static String NEAR_POL_ATTR_ID = "id";
	public static String NEAR_POL_ATTR_NM = "name";
	public static String NEAR_POL_ATTR_FRMADD = "formatted_address";
	public static String NEAR_POL_ATTR_INPHNO =	"international_phone_number";
	public static String NEAR_POL_ATTR_PHNO	= "formatted_phone_number";
	public static String NEAR_POL_ATTR_VICINITY = "vicinity";
	
	
	public static String CONCT_DEL_MSG = "Please Select a Contact To Delete";
	public static String NO_CONCT_ADDED ="No Contact Added Yet!";
	public static String CONCT_ADD_ERR = "Cannot Add Contact!";
	
	public static String POL_CALL_ERR_MSG = "Extremely Sorry! the information is not available yet. Please try after a few seconds";
	public static String POL_CALL_TEL = "tel:";
	
	public static String SND_MSG = "Send SMS";
	public static String CALL_POL = "Call Police";
	public static String SMS_DLV_Y = "SMS Delivered";
	public static String SMS_DLV_N = "SMS not Delivered";
	public static String SMS_SEND_Y = "SMS Sending";
	public static String SMS_SEND_GEN_ERR = "Sms failed due to generic failure";
	public static String SMS_SERV_NOT_AVAIL =  "could not send the message due to unavailability of service.";
	public static String SMS_NO_PDU = "Null PDU";
	public static String SMS_NOT_SENT = "Message could not be sent";
	public static String SERVICE_EXP= "Exception in Service";
	
	public static String ALERT_SETTING = "Settings";
	public static String ALERT_CANCEL ="Cancel";
	
	public static String MSG_CONT = "Please Help! I am in Danger, My current Location is ";
	
	public static String GET_URL_UTL_CLS = "com.example.idost.util.GetUrlUtilityClass";
	public static String GET_URL_UTL_MTH = "getUrlConnContents";
	public static String GET_URL_MTH = "getUrl";
	
	
	public static String NEAR_POL_CLS = "com.example.idost.NearestPoliceStationInfoClass";
	public static String NEAR_POL_MTH = "findNearPoliceStationInfo";
	
	public static String ALL_POL_CLS = "com.example.idost.GetAllPoliceStationInfoClass";
	public static String ALL_POL_MTH = "findAllPoliceStationInfo";
	
	public static String CURR_ADDR__CLS = "com.example.idost.GetCurrentAddrLocClass";
	public static String CURR_ADDR_MTH =  "getGeoLocation";
	
	
	
}
