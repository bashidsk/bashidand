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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotActivity extends ActionBarActivity implements  View.OnClickListener  {
	 
	private static String TAG = RegisterActivity.class.getSimpleName();
	SharedPreferences ApplicationDetails;
    SharedPreferences.Editor ApplicationDetailsEdit;	
	private ViewPager viewPager;
	private ViewPagerAdapter adapter;
	private static final int TIMEOUT_MILLISEC = 0;
	ProgressDialog	 pd ;
	EditText primaryphoneno,inputOtp;
	android.support.v7.app.ActionBar bar;
	Button btnRequestSms,btnVerifyOtp;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot);
		try{
			  getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.signinupshape));
			  bar = getSupportActionBar();
			  bar.setTitle(getResources().getString(R.string.resetpassword));
			  bar.setHomeButtonEnabled(true);
			  bar.setDisplayHomeAsUpEnabled(true);
			  ApplicationDetails = getSharedPreferences("com.pondmother",MODE_PRIVATE);
			  viewPager = (ViewPager)findViewById(R.id.viewPagerVertical1);
			  primaryphoneno=(EditText)findViewById(R.id.mobilenumberedittext1);
			  inputOtp = (EditText)findViewById(R.id.inputOtp1);
			  btnRequestSms = (Button)findViewById(R.id.btn_request_sms12);
		      btnVerifyOtp = (Button)findViewById(R.id.btn_verify_otp1);
		      btnRequestSms.setOnClickListener(this);
		      btnVerifyOtp.setOnClickListener(this);
		      adapter = new ViewPagerAdapter();
		      viewPager.setAdapter(adapter);
		}catch(Exception e){
			e.printStackTrace();
		}
		  
	      
	}
	class ViewPagerAdapter extends PagerAdapter {
		 
	    @Override
	    public int getCount() {
	        return 2;
	    }

	    @Override
	    public boolean isViewFromObject(View view, Object object) {
	        return view == ((View) object);
	    }

	    public Object instantiateItem(View collection, int position) {
	        int resId = 0;
	        switch (position) {
	            case 0:
	                resId = R.id.layout_sms1;
	                break;
	            case 1:
	                resId = R.id.layout_otp1;
	                break;
	        }
	        return findViewById(resId);
	    }
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	try{
	            Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	        	}catch (Exception e) {
					// TODO: handle exception
	        		e.printStackTrace();
				}
	            return true;
	            default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
	  switch (view.getId()) {
	  case R.id.btn_request_sms12:
    	 if(NetWorkAvailable()){
    		 String prphno=primaryphoneno.getText().toString().trim();
 		    
		     if(prphno.isEmpty())
			 {
				 Toast.makeText(getApplicationContext(), R.string.enterdata,Toast.LENGTH_SHORT).show(); 
			 }else{
				 try{
				   				 
					new OnSend().execute();
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
			 }
    		break;
	      
    	 }
	  case R.id.btn_verify_otp1:
         verifyOtp();
          break;
    	 }
	}
	/**
	 * sending the OTP to server and activating the user
	 */
	private void verifyOtp() {
	    String otp = inputOtp.getText().toString().trim();
	    if(!otp.isEmpty()) {
	    	try{
	    		 
	        Intent grapprIntent = new Intent(getApplicationContext(), HttpService.class);
	        grapprIntent.putExtra("otp", otp);
	     
	        startService(grapprIntent);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }else{
	        Toast.makeText(getApplicationContext(), "Please Enter the OTP", Toast.LENGTH_SHORT).show();
	    }
	}
	private boolean NetWorkAvailable() {
		// TODO Auto-generated method stub
	       ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		    	return true;		        
		    }else{  
		    	// TODO Auto-generated method stub
				Toast.makeText(ForgotActivity.this, R.string.internet, Toast.LENGTH_SHORT).show();					
		    }
		    return false;
	}
	public class OnSend extends AsyncTask<String, Void,Void>{
		
		protected void onPreExecute() {
			// TODO Auto-generated method stub	
			pd = new ProgressDialog(ForgotActivity.this);
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
			try{
				 AlertDialog.Builder build = new AlertDialog.Builder(ForgotActivity.this);
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
				    	    	  String prphno=primaryphoneno.getText().toString().trim();
					        		 ApplicationData.add_phno(prphno);
					        		 String one="0".toString().trim();
					        		 ApplicationData.add_reg(one);
				    	    	     viewPager.setCurrentItem(1);
				    	    	  }catch(Exception e){
				    	    		  e.printStackTrace();
				    	    		  Toast.makeText(ForgotActivity.this, R.string.unablegetdata,Toast.LENGTH_SHORT).show();
				    	    	  }
				    	    	  
				    	      }
			    	 }catch(Exception e){
			    		 e.printStackTrace();
			    		 Toast.makeText(ForgotActivity.this, R.string.unablegetdata,Toast.LENGTH_SHORT).show();
			    	 }
			     }	
			     }catch(Exception e){
				     e.printStackTrace();
				   Toast.makeText(ForgotActivity.this, R.string.unablegetdata,Toast.LENGTH_SHORT).show();
			  }
			
		     }
	        @Override
			protected Void doInBackground(String... params) {
				// TODO Auto-generated method stub
				 
				try {		
					 
	        		     String prphno=primaryphoneno.getText().toString().trim();
	        		           		     
						Log.i(getClass().getSimpleName(),(String)getText(R.string.sendtask));
					    JSONObject loginJson = new JSONObject();
					    
						loginJson.put("primaryno", prphno);
											 
						 HttpParams httpParams = new BasicHttpParams();
						 HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT_MILLISEC);
						 HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
						 
						 DefaultHttpClient httpclient = new MyHttpsClient(httpParams,getApplicationContext());
					     HttpPost httppost = new HttpPost(Config.URL_REQUEST_FORGOT);
					     
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
						  String noresponse = "nodata".toString().trim();
			   			  ApplicationData.addregentity(noresponse);
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
