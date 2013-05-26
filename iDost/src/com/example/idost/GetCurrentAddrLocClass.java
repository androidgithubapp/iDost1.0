package com.example.idost;

import java.util.List;

import android.location.Address;
import android.location.Geocoder;

import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AppCommonBean;
import com.example.idost.pojo.CurrentAddressBean;
import com.example.idost.util.AppCommonExceptionClass;

public class GetCurrentAddrLocClass {

	double latitude;
	double longitude;

	public void getGeoLocation(double latitude, double longitude)
			throws AppCommonExceptionClass {
		try {

			Geocoder geocoder = new Geocoder(AppCommonBean.mContext);

			if (geocoder != null) {

				List<Address> addresses = geocoder.getFromLocation(latitude,longitude, 1);

				if (addresses != null && addresses.size() > 0 && addresses.get(0).getAddressLine(0) != null) {
						CurrentAddressBean.curraddressLine = addresses.get(0).getAddressLine(0);
						CurrentAddressBean.locality = addresses.get(0).getLocality();
						CurrentAddressBean.adminArea = addresses.get(0).getAdminArea();
						CurrentAddressBean.countryCode = addresses.get(0).getCountryCode();
						CurrentAddressBean.countryNm = addresses.get(0).getCountryName();
						CurrentAddressBean.postalCode = addresses.get(0).getPostalCode();
						CurrentAddressBean.postalCode = addresses.get(0).getPhone();
				} else {
					AppCommonBean.commonErrMsg = AppCommonConstantsClass.CURR_ADDR;
					throw new Exception(AppCommonConstantsClass.ADDR_NULL);
				}

			}
		} catch (Exception e) {
			throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
		}

	}

}
