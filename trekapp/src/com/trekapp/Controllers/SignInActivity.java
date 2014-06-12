package com.trekapp.Controllers;

import com.trekapp.R;
import com.trekapp.R.id;
import com.trekapp.R.layout;
import com.trekapp.R.menu;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class SignInActivity extends Activity implements OnClickListener {
	// Nik : Keys for passing the Intent
	public final static String SIGN_IN_USER = "com.nikhil.trekapp.sign_in_user";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		// Nik : Function for click on Sign in and sign up button
		Button button_sign_in = (Button) findViewById(R.id.buttonSignIn1);
		button_sign_in.setOnClickListener(this);
		Button button_sign_up = (Button) findViewById(R.id.buttonSignUp);
		button_sign_up.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_in, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_sign_in,
					container, false);
			return rootView;
		}
	}

	
	// Nik : Function called to check the validity of information
		public boolean ValidateUser(String uname, String pass) {
			return true;
		}

	@Override
	public void onClick(View arg0) {
		// Nik : Get username and password
		// Nik : variables for sign in
		TextView textView_error1 = (TextView) findViewById(R.id.textViewError1);
		EditText editText_SigninUsername = (EditText) findViewById(R.id.editTextSigninUsername);
		EditText editText_SigninPassword = (EditText) findViewById(R.id.editTextSigninPassword);
		String signin_username = editText_SigninUsername.getText().toString();
		String signin_password = editText_SigninPassword.getText().toString();
		textView_error1.setText("");
		// Nik : variables for sign up
		TextView textView_error2 = (TextView) findViewById(R.id.textViewError2);
		EditText editText_SignupUsername = (EditText) findViewById(R.id.editTextSignupUsername);
		EditText editText_SignupPassword = (EditText) findViewById(R.id.editTextSignupPassword);
		EditText editText_SignupRepassword = (EditText) findViewById(R.id.editTextSignupRepassword);
		String signup_username = editText_SignupUsername.getText().toString();
		String signup_password = editText_SignupPassword.getText().toString();
		String signup_repassword = editText_SignupRepassword.getText().toString();
		textView_error2.setText("");
		switch (arg0.getId()) {
		case R.id.buttonSignIn1:
			if (signin_username.length() != 0 && signin_password.length() != 0 )
			{
				if (ValidateUser(signin_username, signin_password) == true)
				{
					// Nik : For valid user pass username to Main Activity
					Intent intent_done = new Intent(getApplicationContext(), MainActivity.class);
					intent_done.putExtra(SIGN_IN_USER, signin_username);
					intent_done.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Kill current activity and go back to previous
					startActivity(intent_done);
					
					/* Nik : useful in future for passing results to parent activity
					setResult(RESULT_OK,intent_done);
					finish();
					*/
				}
				else
					textView_error1.setText("Invalid Username or Password");
			}
			else
				textView_error1.setText("Username or password cannot be empty");
			break;
		case R.id.buttonSignUp:
			if (signup_password.length() != 0 && signup_repassword.length() != 0 && signup_username.length() != 0 )
			{
				if (signup_password.equals(signup_repassword))
				{
					textView_error2.setText("Done");
				}
				else
					textView_error2.setText("Passwords do not match");
			}
			else
				textView_error2.setText("Fields cannot be empty");
			break;
		default:
			break;
		}
	
	}

}
