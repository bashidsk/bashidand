package com.eruvaka.pm;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.eruvaka.pm.LoginActivity;
import com.eruvaka.pm.ApplicationData;
import com.eruvaka.pm.Config;
import com.eruvaka.pm.HttpService;
import com.eruvaka.pm.MyApplication;
import com.eruvaka.pm.ResetPassword;
import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class HttpService extends IntentService {

	 
    private static String TAG = HttpService.class.getSimpleName();
    SharedPreferences ApplicationDetails;
	SharedPreferences.Editor ApplicationDetailsEdit;
	ProgressDialog progressdialog;
	 
    public HttpService() {
        super(HttpService.class.getSimpleName());
    }
     
    @Override
    protected void onHandleIntent(Intent intent) {
    	try{
    		if (intent != null) {
    			try{
                String otp = intent.getStringExtra("otp");
            	String phone_number = ApplicationData.get_phno().toString();
               	String mphno="9999999999";
                String one=ApplicationData.get_reg().toString().trim();
        		String mone="999";
        		 
        		if(phone_number.isEmpty()||phone_number.equals(mphno)||one.isEmpty()||one.equals(mone)){
        			
        		}else{
        			  verifyOtp(otp,phone_number,one);
        		}
              
                
    			}catch(Exception e){
    				e.printStackTrace();
    			}
                
            }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        
    }
  
    /**
     * Posting the OTP to server and activating the user
     *
     * @param otp otp received in the SMS
     */
    private void verifyOtp(final String otp ,final String phone_number,final String one) {
    	   	try{
    	   	   	 
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config.URL_VERIFY_OTP, new Response.Listener<String>() {
 
            @Override
            public void onResponse(String json_response) {
               // Log.d(TAG, json_response.toString());
        	   System.out.println("hi"+json_response);
                try {

 			    	 JSONObject jsn = new JSONObject(json_response);
 			    	 
 					 try{
 							String response = jsn.getString("status");
 						   	String zero="0".toString().trim();
 							if(response.equals(zero)){
 							String error = jsn.getString("error");
 							Toast.makeText(HttpService.this, error, Toast.LENGTH_SHORT).show(); 
 				        		 
 							}else{
 							 try{
 								String data = jsn.getString("data");
								 Toast.makeText(HttpService.this, data, Toast.LENGTH_LONG).show();
								 String one_equal="1".toString().trim();
								 String one=jsn.getString("register");
								 String ones="999".toString().trim();
				        		 ApplicationData.add_reg(ones);
								 if(one_equal.equals(one)){
								try{
									Intent intent = new Intent(HttpService.this, LoginActivity.class);
	 			                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 			                    startActivity(intent); 
									 }catch(Exception e){
										 e.printStackTrace();
									 }
								 }else{
									 try{
									Intent intent = new Intent(HttpService.this, ResetPassword.class);
	 			                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 			                    startActivity(intent); 
									 }catch(Exception e){
										 e.printStackTrace();
									 }
								 } 
 							 }catch(Exception e){
 								 e.printStackTrace();
 							 }
 								 								
 						    	}
 							    }catch(Exception e){
 								  e.printStackTrace();
 								 // Toast.makeText(HttpService.this, R.string.unablegetdata, Toast.LENGTH_SHORT).show(); 
 							    }
 					  	 
 			     
                    } catch (JSONException e) {
                     // Toast.makeText(HttpService.this,"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                    
                    }
         
 
                   }
              }, new Response.ErrorListener() {
 
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                
            }
        }) {
 
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("otp", otp);
                params.put("phone_number", phone_number);
                params.put("register", one); 
                Log.e(TAG, "Posting params: " + params.toString());
                return params;
            }
 
        };
 
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
        
    	    }catch(Exception e){
    			e.printStackTrace();
    		}
    }
    
    

}
