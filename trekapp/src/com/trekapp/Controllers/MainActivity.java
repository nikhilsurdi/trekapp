package com.trekapp.Controllers;

import com.trekapp.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.trekapp.Controllers.library.DatabaseHandler;
import com.trekapp.Controllers.library.UserFunctionsOther;

public class MainActivity extends Activity {
	
	UserFunctionsOther userFunctionsOther;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final TextView textView_user = (TextView) findViewById(R.id.textViewUser);
		final Button button_sing_in = (Button) findViewById(R.id.buttonSignin);
		
		// Nik : Check login status in database
        userFunctionsOther = new UserFunctionsOther();
        if(userFunctionsOther.isUserLoggedIn(getApplicationContext())){
        	DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());
        	String user = (String) dbh.getUserDetails().get("name");
        	textView_user.setText("Welcome "+user);
        	button_sing_in.setText("Sign out");
    		
        }else{
        	textView_user.setText("");
        	button_sing_in.setText("Sign in");
        }
		
		// Nik : Function for sign in button click
		button_sing_in.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(userFunctionsOther.isUserLoggedIn(getApplicationContext())) {
					userFunctionsOther.logoutUser(getApplicationContext());
					textView_user.setText("");
					button_sing_in.setText("Sign in");
				}
				else {
					Intent intent_sign_in = new Intent(getApplicationContext(), SignInActivity.class);
					startActivity(intent_sign_in);
				}
			}
		});
		
	}

}
