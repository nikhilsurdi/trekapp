package com.trekapp.Controllers;

import org.json.JSONException;
import org.json.JSONObject;

import com.trekapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.trekapp.Controllers.library.DatabaseHandler;
import com.trekapp.Controllers.library.UserFunctions;
import com.trekapp.Controllers.library.UserFunctionsOther;

public class SignInActivity extends Activity {
	// Nik : Keys for passing the Intent
	public final static String SIGN_IN_USER = "com.nikhil.trekapp.sign_in_user";

	Button buttonSignin;
	Button buttonSignup;
	EditText signinEmail;
	EditText signinPassword;
	EditText signupEmail;
	EditText signupUsername;
	EditText signupPassword;
	EditText signupRepassword;
	TextView textviewErrorsignin;
	TextView textviewErrorsignup;

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
//	private static String KEY_ERROR = "error";
//	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		
		// Nik : Get all bottons, edittexts and textviews
		buttonSignin = (Button) findViewById(R.id.buttonSignIn1);
		buttonSignup = (Button) findViewById(R.id.buttonSignUp);
		signinEmail = (EditText) findViewById(R.id.editTextSigninEmail);
		signinPassword = (EditText) findViewById(R.id.editTextSigninPassword);
		signupEmail = (EditText) findViewById(R.id.editTextSignupEmail);
		signupUsername = (EditText) findViewById(R.id.editTextSignupUsername);
		signupPassword = (EditText) findViewById(R.id.editTextSignupPassword);
		signupRepassword = (EditText) findViewById(R.id.editTextSignupRepassword);
		textviewErrorsignin = (TextView) findViewById(R.id.textViewErrorSignin);
		textviewErrorsignup = (TextView) findViewById(R.id.textViewErrorSignup);
		
		// Nik : Function for sign in button click
		buttonSignin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String email = signinEmail.getText().toString();
				String password = signinPassword.getText().toString();
				
				UserFunctions userFunction = new UserFunctions(SignInActivity.this);
				userFunction.loginUser(email, password);
			}
			
		});	
		
		// Nik : Function for sign in button click
		buttonSignup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				textviewErrorsignin.setText("");
				String email = signupEmail.getText().toString();
				String password = signupPassword.getText().toString();
				String name = signupUsername.getText().toString();
				String repassword = signupRepassword.getText().toString();
				
				if (password.equals(repassword)) {
					UserFunctions userFunction = new UserFunctions(SignInActivity.this);
					userFunction.registerUser(name, email, password);
				}
				else
					textviewErrorsignup.setText("Passwords do not match");
				
				
			}
		});
		

	}
	
	// Nik : Function to process after login function thread
		public void ProcessLoginData(JSONObject json){
			textviewErrorsignup.setText("");
			UserFunctionsOther userFunctionOther = new UserFunctionsOther();
			try {
				if (json.getString(KEY_SUCCESS) != null) {
					this.textviewErrorsignin.setText("");
					String res = json.getString(KEY_SUCCESS);
					if(Integer.parseInt(res) == 1){
						// user successfully logged in
						// Store user details in SQLite Database
						DatabaseHandler db = new DatabaseHandler(getApplicationContext());
						JSONObject json_user = json.getJSONObject("user");
						
						// Clear all previous data in database
						userFunctionOther.logoutUser(getApplicationContext());
						db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));						
						
						// Launch Dashboard Screen
						Intent mainactivity = new Intent(getApplicationContext(), MainActivity.class);
						
						// Close all views before launching Dashboard
						mainactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(mainactivity);
						
						// Close Login Screen
						finish();
					}else{
						// Error in login
						this.textviewErrorsignin.setText("Incorrect username/password");
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		
		// Function to process register data
		public void ProcessRegisterData(JSONObject json){
			// check for login response
			UserFunctionsOther userFunctionsOther = new UserFunctionsOther();
			try {
				if (json.getString(KEY_SUCCESS) != null) {
					textviewErrorsignup.setText("");
					String res = json.getString(KEY_SUCCESS); 
					if(Integer.parseInt(res) == 1){
						// user successfully registred
						// Store user details in SQLite Database
						DatabaseHandler db = new DatabaseHandler(getApplicationContext());
						JSONObject json_user = json.getJSONObject("user");
						
						// Clear all previous data in database
						userFunctionsOther.logoutUser(getApplicationContext());
						db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));						
						// Launch Dashboard Screen
						Intent mainactivity = new Intent(getApplicationContext(), MainActivity.class);
						// Close all views before launching Dashboard
						mainactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(mainactivity);
						// Close Registration Screen
						finish();
					}else{
						// Error in registration
						textviewErrorsignup.setText("Error occured in registration");
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}


}
