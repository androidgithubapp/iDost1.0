package com.example.idost.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.idost.R;

public class AboutUsActivity extends Activity
{
	
protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        EditText editText = (EditText)findViewById(R.id.editText1);
    
        String text = "\t\t\t\tDesigned & developed by:\n\n" +
        "\t\t\t\t\tJoydeep Mitra \n" +
        "\t\t\t\t\tNeeldeep Roy \n" +
        "\t\t\t\t\tJaydeep Chakraborty \n";

        editText.setText(text);
        
 	}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    // Respond to the action bar's Up/Home button
    case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
}


}
