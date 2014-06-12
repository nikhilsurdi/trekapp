package com.trekapp.Controllers;

import com.trekapp.R;
import com.trekapp.R.layout;
import com.trekapp.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	// Nik : Stores the Username
	public String the_user = "";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Nik : to check username when back from sign in page
		final TextView textView_user = (TextView) findViewById(R.id.textViewUser);
		Intent intent = getIntent();
	    final String message_user = intent.getStringExtra(SignInActivity.SIGN_IN_USER);
	    
	    // Nik : To get the username if stored before closing the app
	    SharedPreferences shared = getSharedPreferences("user",MODE_PRIVATE);
	    final String stored_user = shared.getString("key_user","");
	    
		// Nik : Function for sign in button click
		final Button button_sing_in = (Button) findViewById(R.id.buttonSignin);
		button_sing_in.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent_sign_in = new Intent(getApplicationContext(), SignInActivity.class);
				startActivity(intent_sign_in);
			}
		});
	    
	    
	    // Nik : To store User when app closes
	    final SharedPreferences pref;
	    pref = getSharedPreferences("user", MODE_PRIVATE);
	    Editor editor = pref.edit();
	    
	    if (("".equals(stored_user) || stored_user == null) && ("".equals(message_user) || message_user == null))
	    	textView_user.setText("");
	    else
	    {	
	    	if (!stored_user.isEmpty() && stored_user != null)
	    	{
	    		textView_user.setText("Welcome " + stored_user);
	    		the_user = stored_user;
	    		editor.putString("key_user",stored_user);
	    	}
	    	else if (!message_user.isEmpty() || message_user != null)
	    	{
	    		textView_user.setText("Welcome " + message_user);
	    		the_user = message_user;
	    		editor.putString("key_user",message_user);
	    	}
	    	button_sing_in.setVisibility(View.INVISIBLE);
	    }
	    editor.commit();
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
