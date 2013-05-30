package com.example.idost.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.idost.R;

public class AboutUsActivity extends Activity
{
	
protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        
        EditText editText = (EditText)findViewById(R.id.editText1);
    
        String text = "\t\t\t\tDesigned & developed by:\n\n" +
        "\t\t\t\t\tJoydeep Mitra \n" +
        "\t\t\t\t\tNeeldeep Roy \n" +
        "\t\t\t\t\tJaydeep Chakraborty \n";

        editText.setText(text);
        
        startActivity(new Intent(AboutUsActivity.this,AppInfoActivity.class));
	}


}
