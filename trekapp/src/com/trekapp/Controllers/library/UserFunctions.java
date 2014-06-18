// User funcitons for login and register

package com.trekapp.Controllers.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.trekapp.Controllers.SignInActivity;
import com.trekapp.Controllers.library.JSONParser;

public class UserFunctions {
	
private JSONParser jsonParser;
	
	private static String login_tag = "login";
	private static String register_tag = "register";
	
	public SignInActivity singinActivity;
	
	// constructor
	public UserFunctions(SignInActivity a){
		jsonParser = new JSONParser(a);
	}
	
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * */
	public void loginUser(String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		
		jsonParser.execute(params);
	}
	
	/**
	 * function make register Request
	 * @param name
	 * @param email
	 * @param password
	 * */
	public void registerUser(String name, String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		
		jsonParser.execute(params);
		
	}
	
}
