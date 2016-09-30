package com.eruvaka.pm;

import java.io.BufferedReader;
import java.io.IOException;
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
import com.eruvaka.pm.ApplicationData;
import com.eruvaka.pm.Config;
import com.eruvaka.pm.LoginActivity;
import com.eruvaka.pm.MyHttpsClient;
import com.eruvaka.pm.ResetPassword;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPassword extends ActionBarActivity implements TextWatcher{
	android.support.v7.app.ActionBar bar;
	EditText crpasswd,repasswd;
	Button reset;
	private static final int TIMEOUT_MILLISEC = 0;
	ProgressDialog	 pd ;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reset);
		  getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.signinupshape));
		  bar = getSupportActionBar();
		  bar.setTitle(getResources().getString(R.string.resetpassword));
		  bar.setHomeButtonEnabled(true);
		  bar.setDisplayHomeAsUpEnabled(true);
		  crpasswd=(EditText)findViewById(R.id.createedittextres);
		  repasswd=(EditText)findViewById(R.id.reenteredittextres);
		  reset=(Button)findViewById(R.id.reset);
		  reset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
					 String crpass=crpasswd.getText().toString().trim();
        			 String repass=repasswd.getText().toString().trim();
        			 String prphno = ApplicationData.get_phno().toString();	
				  if(  crpass.isEmpty() || repass.isEmpty()||prphno.isEmpty())
					 {
						 Toast.makeText(getApplicationContext(), R.string.enterdata,Toast.LENGTH_SHORT).show(); 
					
					 }else if(!(crpasswd.getText().toString().matches("[a-zA-Z0-9._@]+") && crpasswd.length() >= 6 )){
						 Toast.makeText(getApplicationContext(), "Enter Password Atleast 6 to 30 Characters with a Combination of Lowercase Letters,Numbers & Special Charecters(such as @,., and _)",Toast.LENGTH_SHORT).show();  
					 } else if(!(repass.equals(crpass))){
						 Toast.makeText(getApplicationContext(), " Both Passwords Must Be Equal",Toast.LENGTH_SHORT).show();  
					
					 }else{
							     
						 if(NetWorkAvailable()){
							 try{
						 	 new OnSend().execute();
							}catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						 }
     			 }
     	       
     	         }catch (Exception e) {
     				e.printStackTrace();
     			} 
			}
		});
	}
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		if(crpasswd.getText().hashCode() == s.hashCode()){
	    	   
	    	 if(!(crpasswd.getText().toString().matches("[a-zA-Z0-9._@]+") && crpasswd.length() >= 6 )) {
	    		 crpasswd.setError("Enter Password Atleast 6 to 30 Characters with a Combination of Lowercase Letters,Numbers & Special Charecters(such as @,., and _)");
			 }else{
				 crpasswd.setError(null);
			 }
	    	 
	     }else if(repasswd.getText().hashCode() == s.hashCode()){
	    	 if(repasswd.getText().toString().matches("[a-zA-Z0-9._@]+") && repasswd.length() >=6){
				  String crpass=crpasswd.getText().toString().trim();
				  if(crpass.isEmpty()){
					  repasswd.setError("Create Password Shouldnot be Empty");
				  }else{
					  String repass=repasswd.getText().toString().trim();
						if(!(repass.equals(crpass))){
							 repasswd.setError(" Both Passwords Must Be Equal");
			      }  
				  }
					
			}else{
				repasswd.setError("Enter Password Lowercase Letters,Numbers & Special Charecters(such as @,., and _)");
			 }
	    	 
	     } 
	}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
	private boolean NetWorkAvailable() {
		// TODO Auto-generated method stub
	       ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		    	return true;		        
		    }else{  
		    	// TODO Auto-generated method stub
				Toast.makeText(ResetPassword.this, R.string.internet, Toast.LENGTH_SHORT).show();					
		    }
		    return false;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            Intent intent = new Intent(ResetPassword.this, LoginActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	            default:
	            return super.onOptionsItemSelected(item);
	    }
	}
public class OnSend extends AsyncTask<String, Void,Void>{
		
		protected void onPreExecute() {
			// TODO Auto-generated method stub	
			pd = new ProgressDialog(ResetPassword.this);
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
			 AlertDialog.Builder build = new AlertDialog.Builder(ResetPassword.this);
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
			    	    	  
				        	 
			    	    	 
			    	    	 String jsnonb = json.getString("data");
			    	    	  build.setMessage(jsnonb);
								build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										dialog.cancel();
										Intent intent = new Intent(ResetPassword.this, LoginActivity.class);
	 	 			                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	 			                    startActivity(intent); 
	 	 			                    finish();
									}
								});
				    		    build.show();
			    	      }
		    	 }catch(Exception e){
		    		 e.printStackTrace();
		    		 Toast.makeText(ResetPassword.this, R.string.unablegetdata,Toast.LENGTH_SHORT).show();
		    	 }
		     }
		     }


		 
		 
		
			

			@Override
			protected Void doInBackground(String... params) {
				// TODO Auto-generated method stub
				try{
				try {		
					 String crpass=crpasswd.getText().toString().trim();
        			 String prphno = ApplicationData.get_phno().toString();	
        			 
						Log.i(getClass().getSimpleName(),(String)getText(R.string.sendtask));
					    JSONObject loginJson = new JSONObject();
					    
						loginJson.put("primaryno", prphno);
						loginJson.put("pwd", crpass);
					 
						 
						 HttpParams httpParams = new BasicHttpParams();
						 HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT_MILLISEC);
						 HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
						 
						 DefaultHttpClient httpclient = new MyHttpsClient(httpParams,getApplicationContext());
					     HttpPost httppost = new HttpPost(Config.URL_VERIFY_CHANGEPWD);
					     
						 httppost.setEntity(new ByteArrayEntity(loginJson.toString().getBytes("UTF8")));
						 httppost.setHeader("eruv",loginJson.toString());
						 
						 HttpResponse response = httpclient.execute(httppost);
						 HttpEntity entity = response.getEntity();		
						
						 if (entity != null) {
						 InputStream instream = entity.getContent();
						 String entitresult = convertStreamToString(instream);
						 ApplicationData.addregentity2(entitresult);
						 System.out.println("Read From Server :"+entitresult);
						 }
						else{
							String noentity = "noresult".toString().trim();
							ApplicationData.addregentity2(noentity);
						 }
						 
					}	 catch (Throwable t) {
						 t.printStackTrace();
					 
				    
			
			}
				}catch(Exception e){
					e.printStackTrace();
				}
				return null;
			
			
				}

		}


	protected String convertStreamToString(InputStream instream) {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	   try {
			while ((line = reader.readLine()) != null) {
			    sb.append(line);
			}
		
			instream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return sb.toString();
	}

}
