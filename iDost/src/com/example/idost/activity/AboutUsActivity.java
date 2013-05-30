package com.example.idost.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.idost.R;

public class AboutUsActivity extends Activity
{
	
protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        
        startActivity(new Intent(AboutUsActivity.this,AppInfoActivity.class));
	}


}
