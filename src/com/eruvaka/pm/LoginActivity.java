package com.eruvaka.pm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 

public class LoginActivity extends ActionBarActivity implements View.OnClickListener{
	
	SharedPreferences ApplicationDetails;
	SharedPreferences.Editor ApplicationDetailsEdit;
    EditText LoginUserName, LoginUserPassword;
 	android.support.v7.app.ActionBar actionBar;
	public static String name;
	public static String email;
	private static final int TIMEOUT_MILLISEC = 0;
	AlertDialog.Builder build;
	/*
	 * (non-Javadoc)
	 * Pond Mother Latest App Version B.M.1.0
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try{
	 	ApplicationDetails = getApplicationContext().getSharedPreferences("com.pm",MODE_PRIVATE);
		boolean isLogged = ApplicationDetails.getBoolean("isLogged", false);
		if (isLogged == true) {
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		} else {
				actionBar = getSupportActionBar();
				actionBar.hide();
				setContentView(R.layout.login);
				 build = new AlertDialog.Builder(LoginActivity.this);
		LoginUserName = (EditText) findViewById(R.id.loginusername);
		LoginUserPassword = (EditText) findViewById(R.id.loginpasswd);
		Button signup = (Button) findViewById(R.id.textview_signup11);
		TextView ac = (TextView) findViewById(R.id.cantac);
		Button loginButton = (Button) findViewById(R.id.login_loginbutton);
		 signup.setOnClickListener(this);
		 ac.setOnClickListener(this);
		 loginButton.setOnClickListener(this);
		 
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.login_loginbutton:
        	if(InternetAviavable()){

				String LoginUserNameString = LoginUserName
						.getText().toString().trim();
				String LoginUserPasswordString = LoginUserPassword
						.getText().toString().trim();
				if (LoginUserNameString.isEmpty()) {
					Toast.makeText(LoginActivity.this,R.string.userid,
							Toast.LENGTH_SHORT).show();
				} else if(LoginUserPasswordString.isEmpty()){
					Toast.makeText(LoginActivity.this,
							R.string.passwd,
							Toast.LENGTH_SHORT).show();
				}else {
					 new OnLoginButton().execute();
				}
				
			}
        	break;
        case R.id.textview_signup11:
        	try {
				Intent inte = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(inte);
			} catch (Exception e) {
				e.printStackTrace();
			}
        	break;
        case R.id.cantac:
        	try{
				Intent intent = new Intent(LoginActivity.this,ForgotActivity.class);
    			startActivity(intent);
           		}catch(Exception e){
           			e.printStackTrace();
           		}
        	break;
              
        }
    }
		protected boolean InternetAviavable() {
			// TODO Auto-generated method stub
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnected()) {
				 return true;
			} else {
				Toast.makeText(LoginActivity.this, R.string.internet,Toast.LENGTH_SHORT).show();
			}
			return false;
		}
		public class OnLoginButton extends AsyncTask<String, Void, Void> {
			ProgressDialog progressdialog;

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.AsyncTask#onPreExecute()
			 */
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				progressdialog = new ProgressDialog(LoginActivity.this);
				progressdialog.setMessage("Loading. please wait...");
				progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressdialog.show();
				progressdialog.setCanceledOnTouchOutside(false);
				super.onPreExecute();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
					progressdialog.dismiss();
					super.onPostExecute(result);
					 
					try {
					
					build.setTitle(getResources().getString(R.string.login));
					String json_response = ApplicationData.getregentity().toString().trim();
					String no_response = "nodata".toString().trim();
					if (no_response.equals(json_response)) {
						build.setMessage(R.string.unablegetdata);
						build.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,int id) {
										dialog.cancel();
									}
								});
						build.show();
					}else{
						try {
							JSONObject jsn = new JSONObject(json_response);
							String response = jsn.getString("status");
							String zero = "0".toString().trim();
							if (response.equals(zero)) {
								String error = jsn.getString("error");
								build.setMessage(error);
								build.setNegativeButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int which) {

												dialog.cancel();
											}
										});
								build.show();
							} else {
								try{
				 			
	                              try{
	                            	 
									 String user_id = jsn.getString("user_id");
									 String user_data = jsn.getString("data");
		      						 JSONObject jsn2 = new JSONObject(user_data);
		      						 String FirstName = jsn2.getString("firstname");
		      						 String lastname = jsn2.getString("lastname");
		      						 String mobilenumber = jsn2.getString("mobilenumber");
		      						 String emailid = jsn2.getString("emailid");
		      						 String timezone = jsn2.getString("timezone");	
		      						 
		      						 if(timezone.isEmpty()){
		      							AlertDialog.Builder alertDialog = new AlertDialog.Builder(
												LoginActivity.this);
										alertDialog.setMessage("Time Zone is Empty Please Contact Admin");
										alertDialog.setPositiveButton("OK",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {

														dialog.cancel();
													}
												});
										alertDialog.show();
		      						 }else{
		      							 try{
		      						    ApplicationDetailsEdit = ApplicationDetails.edit();
										ApplicationDetailsEdit.putBoolean("isLogged", true);
										ApplicationDetailsEdit.putString("user_id",user_id);
										ApplicationDetailsEdit.putString("FirstName",FirstName);
										ApplicationDetailsEdit.putString("lastname",lastname);
										ApplicationDetailsEdit.putString("mobilenumber",mobilenumber);
										ApplicationDetailsEdit.putString("emailid",emailid);
										ApplicationDetailsEdit.putString("timezone",timezone);
										ApplicationDetailsEdit.commit();
										 
										Intent devices = new Intent(LoginActivity.this,MainActivity.class);
										devices.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										startActivity(devices);
										finish();
										
		      							 }catch(Exception e){
		      								 e.printStackTrace();
		      							 }
		      						 }
		      						      						
	                              }catch(Exception e){
	                            	  e.printStackTrace();
	                            	  Toast.makeText(LoginActivity.this, R.string.unablegetdata,Toast.LENGTH_SHORT).show();
	                              }
								}catch(Exception e){
									e.printStackTrace();
									Toast.makeText(LoginActivity.this, R.string.unablegetdata,Toast.LENGTH_SHORT).show();
								}
							}

						} catch (Exception e) {
							e.printStackTrace();
							Toast.makeText(LoginActivity.this, R.string.unablegetdata,Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
			
					Toast.makeText(LoginActivity.this, R.string.noresponse,Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			protected Void doInBackground(String... params) {
				// TODO Auto-generated method stub
				try{
					String LoginUserNameString = LoginUserName.getText().toString().trim();
					String LoginUserPasswordString = LoginUserPassword.getText().toString().trim();
				 
					Log.i(getClass().getSimpleName(),"sending  task - started login");
					JSONObject loginJson = new JSONObject();
					loginJson.put("username", LoginUserNameString);
					loginJson.put("password", LoginUserPasswordString);
				 

					HttpParams httpParams = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT_MILLISEC);
					HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);

					DefaultHttpClient httpclient = new MyHttpsClient(httpParams,getApplicationContext());
					HttpPost httppost = new HttpPost(Config.URL_KEY_LOGIN);
					httppost.setEntity(new ByteArrayEntity(loginJson.toString().getBytes("UTF8")));
					httppost.setHeader("eruv", loginJson.toString());
					HttpResponse response = httpclient.execute(httppost);
					HttpEntity entity = response.getEntity();

					// If the response does not enclose an entity, there is no need
					if (entity != null) {
						InputStream instream = entity.getContent();
						String result = convertStreamToString(instream);
						Log.i("Read from server", result);
						ApplicationData.addregentity(result);
					} else {
						String noresponse = "nodata".toString().trim();
						ApplicationData.addregentity(noresponse);
					}
					
	               }catch (Throwable t) {
	   				t.printStackTrace();
	   				String noresponse = "nodata".toString().trim();
	   				ApplicationData.addregentity(noresponse);
	   		 
	   			   }
				   
				return null;
			}

		}
		/**
		 * We obtain inputstream from host and we convert here inputstream into
		 * string. if inputstream is improper then execption is raised
		 * 
		 * @param instream
		 * @return
		 * @throws Exception
		 */
		public String convertStreamToString(InputStream instream) throws Exception {
			// TODO Auto-generated method stub
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			instream.close();
			return sb.toString();
		}
}
