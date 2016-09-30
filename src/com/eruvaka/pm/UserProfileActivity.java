package com.eruvaka.pm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserProfileActivity extends ActionBarActivity {

	private static final int TIMEOUT_MILLISEC = 0;
	SharedPreferences ApplicationDetails;
	SharedPreferences.Editor ApplicationDetailsEdit;
	android.support.v7.app.ActionBar bar;
	EditText fname,lname,mobile,oldpws,newpwd,emailid;

	Button update;
	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		 getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.signinupshape));
		  bar = getSupportActionBar();
		 // bar.setTitle(getResources().getString(R.string.signup));
		  bar.setTitle("User Details");
		  bar.setHomeButtonEnabled(true);
		  bar.setDisplayHomeAsUpEnabled(true);
		   ApplicationDetails = getApplicationContext().getSharedPreferences("com.pm",MODE_PRIVATE);
		   String user_id = ApplicationDetails.getString("user_id", null);
	       String FirstName = ApplicationDetails.getString("FirstName", null);
	       String lastname = ApplicationDetails.getString("lastname", null);
	       String mobilenumber = ApplicationDetails.getString("mobilenumber", null);
	       String emailId = ApplicationDetails.getString("emailid", null);
	       
	       fname=(EditText)findViewById(R.id.user_fname);
	       mobile=(EditText)findViewById(R.id.user_mobile);
	       lname=(EditText)findViewById(R.id.user_lname);
	       oldpws=(EditText)findViewById(R.id.oldpwd);
	       newpwd=(EditText)findViewById(R.id.newpwd);
	       emailid=(EditText)findViewById(R.id.user_email);
	       fname.setText(FirstName);
	       lname.setText(lastname);
	       mobile.setText(mobilenumber);
	       emailid.setText(emailId);
	       
	       update=(Button)findViewById(R.id.user_update);
	       
	       update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
					 String email=emailid.getText().toString().trim();
        			 String firstname=fname.getText().toString().trim();
        		 	 String lastname=lname.getText().toString().trim();
        		     String mobilenumber=mobile.getText().toString().trim();
        			 String old_pwd=oldpws.getText().toString().trim();
        		     String new_pwd=newpwd.getText().toString().trim();	
        		     
        		     if(  mobilenumber.isEmpty() || old_pwd.isEmpty() || new_pwd.isEmpty() || firstname.isEmpty() ||lastname.isEmpty()  || email.isEmpty())
					 {
						 Toast.makeText(getApplicationContext(), R.string.enterdata,Toast.LENGTH_SHORT).show(); 
					 }else if(mobilenumber.length() > 16 || mobilenumber.length() < 7){
						 Toast.makeText(getApplicationContext(), "Please Enter Valid Mobile Number to verify ",Toast.LENGTH_SHORT).show();  
					 }else if(!(oldpws.getText().toString().matches("[a-zA-Z0-9._@]+") && oldpws.length() >= 6 )){
						 Toast.makeText(getApplicationContext(), "Enter Password Atleast 6 to 30 Characters with a Combination of Lowercase Letters,Numbers & Special Charecters(such as @,., and _)",Toast.LENGTH_SHORT).show();  
					 
				 }else if(!(newpwd.getText().toString().matches("[a-zA-Z0-9._@]+") && newpwd.length() >= 6 )){
					 Toast.makeText(getApplicationContext(), "Enter Password Atleast 6 to 30 Characters with a Combination of Lowercase Letters,Numbers & Special Charecters(such as @,., and _)",Toast.LENGTH_SHORT).show();  
				 } 
					 else if(!(ValidName(fname.getText().toString()) && fname.length()>=3 && fname.length()<25)){
						Toast.makeText(getApplicationContext(), "Please Enter First Name Atleast 3 to 25 Without Any Special Characters. Accepts Only Alphabetics", Toast.LENGTH_SHORT).show();
					 }else if(!(ValidName(lname.getText().toString()) && lname.length()>=1 && lname.length()<25)){
						 Toast.makeText(getApplicationContext(), "Please Enter Last Name Atleast 1 to 25 Without Any Special Characters. Accepts Only Alphabetics", Toast.LENGTH_SHORT).show();
						 
					 }else if(!(ValidEmail(emailid.getText().toString())&& emailid.length()>0)){
						 
						 Toast.makeText(getApplicationContext(), "please Enter Valid Email Id", Toast.LENGTH_SHORT).show(); 
					 }else{
						 if(NetWorkAvailable()){
								new OnSend().execute();
							} 
					 }
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	       
	}
	private boolean ValidName(String firstName) {
		String PATTERN = "[a-zA-Z ]+";
	   Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(firstName);
		return matcher.matches();
	}
	private boolean ValidEmail(String email) {
		String PATTERN = "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+";
	   Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	private boolean NetWorkAvailable() {
		// TODO Auto-generated method stub
	       ConnectivityManager cm =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		    	return true;		        
		    }else{  
		    	// TODO Auto-generated method stub
				Toast.makeText(UserProfileActivity.this, R.string.internet, Toast.LENGTH_SHORT).show();					
		    }
		    return false;
	}
	public class OnSend extends AsyncTask<String, Void,Void>{
		ProgressDialog pd;
		protected void onPreExecute() {
			// TODO Auto-generated method stub	
			pd = new ProgressDialog(UserProfileActivity.this);
			pd.setMessage(getText(R.string.procedialog));
			pd.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbar)); 
			pd.show();	
			pd.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}
		
		
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			pd.dismiss();
			super.onPostExecute(result);
			 AlertDialog.Builder build = new AlertDialog.Builder(UserProfileActivity.this);
		      String json_result=ApplicationData.getregentity2().toString().trim();
		      String noentity = "noentity".toString().trim();
	      	
		     if(noentity.equals(json_result)){
		    	    build.setMessage("unable to get data ");
	         		build.setPositiveButton("OK",new DialogInterface.OnClickListener() {
	              @Override
					public void onClick(DialogInterface dialog, int id) {
	             	 dialog.cancel();
	              }
	              });
	      		build.show();
		     }
		     else{  
		    	 try{
		    		 JSONObject json = new JSONObject(json_result);
			    	    String status = json.getString("status");
			    	    String zero = "0".toString().trim();
			    	    if(status.equals(zero))
			    	     {
			    		    String error = json.getString("error");
							build.setMessage(error);
							build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});
			    		    build.show();
			    	      }else{
			    	    	  try{
			    	    		  String response = json.getString("response");
			    	    		    String user_data = json.getString("data");
			    	    		    
									ApplicationDetails = getApplicationContext().getSharedPreferences("com.pm",MODE_PRIVATE);
									ApplicationDetailsEdit = ApplicationDetails.edit();
									 JSONObject jsn2 = new JSONObject(user_data);
		      						 String FirstName = jsn2.getString("firstname");
		      						 String lastname = jsn2.getString("lastname");
		      						 String mobilenumber = jsn2.getString("mobilenumber");
		      						 String emailid = jsn2.getString("emailid");
									ApplicationDetailsEdit.putString("FirstName",FirstName);
									ApplicationDetailsEdit.putString("lastname",lastname);
									ApplicationDetailsEdit.putString("mobilenumber",mobilenumber);
									ApplicationDetailsEdit.putString("emailid",emailid);
									ApplicationDetailsEdit.commit();
									
									AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserProfileActivity.this);
									alertDialog.setMessage(response);
									alertDialog.setPositiveButton("OK",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int which) {
													dialog.cancel();
													 if(NetWorkAvailable()){
														 Intent backintent=new Intent(UserProfileActivity.this,MainActivity.class);
														 backintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
														 startActivity(backintent);
													  				
												  }
													
												}
											});
									alertDialog.show(); 
				 		    		 
				 		    	 }catch(Exception e){
				 		    		 e.printStackTrace();
				 		    	 }
			    	    	 
			    	      }
		    	 }catch(Exception e){
		    		 e.printStackTrace();
		    	 }
		     }
		     }
					

			@Override
			protected Void doInBackground(String... params) {
				// TODO Auto-generated method stub
				try{
					 Context context=getApplicationContext();
		     			ApplicationDetails = context.getSharedPreferences("com.pm",MODE_PRIVATE);
		     		 
		     		     String user_id = ApplicationDetails.getString("user_id", null);
	 
					
					try {		
						//store loginpasswd in sharedpreference
						 String email=emailid.getText().toString().trim();
	        			 String firstname=fname.getText().toString().trim();
	        		 	 String lastname=lname.getText().toString().trim();
	        		     String mobilenumber=mobile.getText().toString().trim();
	        			 String old_pwd=oldpws.getText().toString().trim();
	        		     String new_pwd=newpwd.getText().toString().trim();	
	        		   
						Log.i(getClass().getSimpleName(),(String)getText(R.string.sendtask));
					    JSONObject loginJson = new JSONObject();
					    loginJson.put("user_id", user_id);
					 	loginJson.put("firstname", firstname);
						loginJson.put("lastname", lastname);
						loginJson.put("mobilenumber", mobilenumber);
						loginJson.put("emailid", email);
						loginJson.put("old_pwd", old_pwd);
						loginJson.put("new_pwd", new_pwd);
					 
						 
						 HttpParams httpParams = new BasicHttpParams();
						 HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT_MILLISEC);
						 HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
						 
						 DefaultHttpClient httpclient = new MyHttpsClient(httpParams,getApplicationContext());
					     HttpPost httppost = new HttpPost(Config.URL_UPDATE_USER_PROFILE);
					     
						 httppost.setEntity(new ByteArrayEntity(loginJson.toString().getBytes("UTF8")));
						 httppost.setHeader("eruv",loginJson.toString());
						 
						 HttpResponse response = httpclient.execute(httppost);
						 HttpEntity entity = response.getEntity();		
						
						 if (entity != null) {
						 InputStream instream = entity.getContent();
						 String entitresult = convertStreamToString(instream);
						 ApplicationData.addregentity2(entitresult);
						 System.out.println("Read From Server :"+entitresult);
						 }else{
							String noentity = "noresult".toString().trim();
							ApplicationData.addregentity2(noentity);
						 }
						 
					     }catch (Throwable t) {
						 t.printStackTrace();
					 
				    
			
			}
				}catch(Exception e){
					e.printStackTrace();
				}
				return null;
			
			
				}

		}
	 public String convertStreamToString(InputStream instream) throws Exception {
			// TODO Auto-generated method stub
			BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				
			    sb.append(line);
			}
			 instream.close();
			return sb.toString();

			}
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {
	         case android.R.id.home:
	             Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
	             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	             startActivity(intent);
	             return true;
	             default:
	             return super.onOptionsItemSelected(item);
	     }
	 }
}
