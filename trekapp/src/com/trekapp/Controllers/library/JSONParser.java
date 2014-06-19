// To communicate with server and get the data in JSON format

package com.trekapp.Controllers.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.trekapp.Controllers.SignInActivity;

import android.os.AsyncTask;
import android.util.Log;


public class JSONParser extends AsyncTask<List<NameValuePair>, Void, JSONObject> {
	
	static InputStream is = null;
	static JSONObject jObj = null;
	String json = "";
	
	public SignInActivity signinActivity;
	
	private static String URL = "http://192.168.0.100/android_login_api/";
	private static String TAG = "tag";
	private static String LOGIN_TAG = "login";
	private static String REGISTER_TAG = "register";

	// constructor
	public JSONParser(SignInActivity a) {
		this.signinActivity = a;
	}

	@Override
	protected JSONObject doInBackground(List<NameValuePair>... arg0) {
		// Making HTTP request
				try {
					// defaultHttpClient
					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(URL);
					httpPost.setEntity(new UrlEncodedFormEntity(arg0[0]));

					HttpResponse httpResponse = httpClient.execute(httpPost);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(
							is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					json = sb.toString();
					Log.e("JSON", json);
				} catch (Exception e) {
					Log.e("Buffer Error", "Error converting result " + e.toString());
				}

				// try parse the string to a JSON object
				try {
					jObj = new JSONObject(json);			
				} catch (JSONException e) {
					Log.e("JSON Parser", "Error parsing data " + e.toString());
					String error = "{\"tag\":\"login\",\"success\":0,\"error\":1,\"error_msg\":\"No Internet Access!\"}";
					try {
						Log.e("ERROR", error);
						jObj = new JSONObject(error);
					} catch (JSONException f){
						Log.e("JSON Parser", "Final Error" + f.toString());
					}
					
				}
				return jObj;
	}
	
	@Override
	protected void onPostExecute(JSONObject result) {
		try
		{
			if (result.getString(TAG) != LOGIN_TAG) 
				signinActivity.ProcessLoginData(result);
			else if (result.getString(TAG) != REGISTER_TAG){
				Log.d("JSON", "Register");
				signinActivity.ProcessRegisterData(result);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}


}
