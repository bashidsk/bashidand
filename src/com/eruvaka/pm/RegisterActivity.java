package com.eruvaka.pm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import  com.eruvaka.pm.ApplicationData;
import com.eruvaka.pm.Config;
import  com.eruvaka.pm.HttpService;
import  com.eruvaka.pm.LoginActivity;
import com.eruvaka.pm.MyApplication;
import  com.eruvaka.pm.MyHttpsClient;
import  com.eruvaka.pm.RegisterActivity;
import com.eruvaka.pm.R;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity  extends ActionBarActivity implements TextWatcher,View.OnClickListener{
	
    private static String TAG = RegisterActivity.class.getSimpleName();
    SharedPreferences ApplicationDetails;
	SharedPreferences.Editor ApplicationDetailsEdit;
	ArrayList<HashMap<String, String>> mylist=new ArrayList<HashMap<String,String>>();
	EditText userid,crpasswd,repasswd,name,lname, citys,mail,inputOtp, primaryphoneno,address;
	Button btnRequestSms, btnVerifyOtp;
	android.support.v7.app.ActionBar bar;
	ProgressDialog	 pd ;
	private ViewPager viewPager;
	private ViewPagerAdapter adapter;
	private static final int TIMEOUT_MILLISEC = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regsiter);
		try{
		  getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.signinupshape));
		  bar = getSupportActionBar();
		  bar.setTitle(getResources().getString(R.string.signup));
		  bar.setHomeButtonEnabled(true);
		  bar.setDisplayHomeAsUpEnabled(true);
		 
		  //get xml data 
		  viewPager = (ViewPager)findViewById(R.id.viewPagerVertical);
		  primaryphoneno=(EditText)findViewById(R.id.mobilenumberedittext);
		  crpasswd=(EditText)findViewById(R.id.createedittext);
		  repasswd=(EditText)findViewById(R.id.reenteredittext);
		  name=(EditText)findViewById(R.id.firstnameedittext);
		  lname=(EditText)findViewById(R.id.lastnameedittext);
		  mail=(EditText)findViewById(R.id.emailideditext);
		  inputOtp = (EditText)findViewById(R.id.inputOtp);
		  btnRequestSms = (Button)findViewById(R.id.btn_request_sms1);
	      btnVerifyOtp = (Button)findViewById(R.id.btn_verify_otp);
		  primaryphoneno.addTextChangedListener(this);
		  crpasswd.addTextChangedListener(this);
		  repasswd.addTextChangedListener(this);
		  name.addTextChangedListener(this);
		  lname.addTextChangedListener(this);
		  mail.addTextChangedListener(this);
		  btnRequestSms.setOnClickListener(this);
	      btnVerifyOtp.setOnClickListener(this);
		  adapter = new ViewPagerAdapter();
	      viewPager.setAdapter(adapter);
		  }catch(Exception e){
			e.printStackTrace();
		  }
		  }
	 @Override
	    public void onClick(View view) {
	        switch (view.getId()) {
	        case R.id.btn_request_sms1:
	        	 if(NetWorkAvailable()){
	        		 
	        		 
	        		 try{
	        	    	 String crpass=crpasswd.getText().toString().trim();
	        			 String repass=repasswd.getText().toString().trim();
	        		 	 String fnames=name.getText().toString().trim();
	        		     String lnames=lname.getText().toString().trim();
	        			 String em=mail.getText().toString().trim();
	        		     String prphno=primaryphoneno.getText().toString().trim();
	        		     	        		    
	        		     if(  prphno.isEmpty() || crpass.isEmpty() || repass.isEmpty() || fnames.isEmpty() ||lnames.isEmpty()  || em.isEmpty())
						 {
							 Toast.makeText(getApplicationContext(), R.string.enterdata,Toast.LENGTH_SHORT).show(); 
						 }else if(prphno.length() > 16 || prphno.length() < 7){
							 Toast.makeText(getApplicationContext(), "Please Enter Valid Mobile Number to verify ",Toast.LENGTH_SHORT).show();  
						 }else if(!(crpasswd.getText().toString().matches("[a-zA-Z0-9._@]+") && crpasswd.length() >= 6 )){
							 Toast.makeText(getApplicationContext(), "Enter Password Atleast 6 to 30 Characters with a Combination of Lowercase Letters,Numbers & Special Charecters(such as @,., and _)",Toast.LENGTH_SHORT).show();  
						 } else if(!(repass.equals(crpass))){
							 Toast.makeText(getApplicationContext(), " Both Passwords Must Be Equal",Toast.LENGTH_SHORT).show();  
						 }else if(!(ValidName(name.getText().toString()) && name.length()>=3 && name.length()<25)){
							Toast.makeText(getApplicationContext(), "Please Enter First Name Atleast 3 to 25 Without Any Special Characters. Accepts Only Alphabetics", Toast.LENGTH_SHORT).show();
						 }else if(!(ValidName(lname.getText().toString()) && lname.length()>=1 && lname.length()<25)){
							 Toast.makeText(getApplicationContext(), "Please Enter Last Name Atleast 1 to 25 Without Any Special Characters. Accepts Only Alphabetics", Toast.LENGTH_SHORT).show();
							 
						 }else if(!(ValidEmail(mail.getText().toString())&& mail.length()>0)){
							 
							 Toast.makeText(getApplicationContext(), "please Enter Valid Email Id", Toast.LENGTH_SHORT).show(); 
						 }else{
							 		
					     
							 if(NetWorkAvailable()){
								 try{
							  	 // requestForSMS(prphno, em, fnames,lnames,crpass);
										 
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
	        	break;
	        case R.id.btn_verify_otp:
	             verifyOtp();
	              break;
	       
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
			Toast.makeText(RegisterActivity.this, R.string.internet, Toast.LENGTH_SHORT).show();					
	    }
	    return false;
}
	
	private void requestForSMS(final String prphno, final String em, final String fnames,final String lnames, final String crpass) {
		// TODO Auto-generated method stub
		try{
			 pd=ProgressDialog.show(RegisterActivity.this,"Please Wait...","Please Wait...");
		 		pd.setCanceledOnTouchOutside(false);
   		 
         StringRequest strReq = new StringRequest(Request.Method.POST,
               Config.URL_REQUEST_SMS, new Response.Listener<String>() {

           @Override
           public void onResponse(String json_response) {
               //Log.d(TAG, response.toString());
        	 
        	   AlertDialog.Builder build = new AlertDialog.Builder(RegisterActivity.this);
		    	build.setTitle(getResources().getString(R.string.signup));
               try {
			    	 JSONObject jsn = new JSONObject(json_response);
			    	 System.out.println(jsn);
					 try{
							String response = jsn.getString("status");
						   	String zero="0".toString().trim();
							if(response.equals(zero)){
								String error = jsn.getString("error");
								build.setMessage(error);
				           		build.setPositiveButton("OK",new DialogInterface.OnClickListener() {
				                @Override
								public void onClick(DialogInterface dialog, int id) {
				               	 dialog.cancel();
				               	 pd.dismiss();
				                }
				                });
				        		build.show();
				        		 
							}else{
							 // moving the screen to next pager item i.e otp screen
		                   		String data = jsn.getString("data");
								build.setMessage(data);
				           		build.setPositiveButton("OK",new DialogInterface.OnClickListener() {
				                @Override
								public void onClick(DialogInterface dialog, int id) {
				               	 dialog.cancel();
				               	viewPager.setCurrentItem(1); 
				                }
				                });
				        		build.show();
								 pd.dismiss();
						    	}
							    }catch(Exception e){
								  e.printStackTrace();
								// Toast.makeText(RegisterActivity.this, R.string.unablegetdata, Toast.LENGTH_SHORT).show(); 
							    }
					  	 
			     
                   } catch (JSONException e) {
                   Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                   
                   }

           }
       }, new Response.ErrorListener() {

           @Override
           public void onErrorResponse(VolleyError error) {
               Log.e(TAG, "Error: " + error.getMessage());
               Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                
           }
       }) {

           /**
            * Passing user parameters to our server
            * @return
            */
           @Override
           protected Map<String, String> getParams() {
               Map<String, String> params = new HashMap<String, String>();
               params.put("primaryno", prphno);
               params.put("email", em);
               params.put("fname", fnames);
               params.put("lname", lnames);
               params.put("pwd", crpass);
               Log.e(TAG, "Posting params: " + params.toString());
               return params;
           }

       };
    
       // Adding request to request queue
         MyApplication.getInstance().addToRequestQueue(strReq);
      
		}catch(Exception e){
			e.printStackTrace();
			pd.dismiss();
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
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case android.R.id.home:
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
            default:
            return super.onOptionsItemSelected(item);
    }
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
@Override
public void afterTextChanged(Editable s) {
	// TODO Auto-generated method stub
	 try{
	 if (primaryphoneno.getText().hashCode() == s.hashCode()){
     	 if( primaryphoneno.length() > 16 || primaryphoneno.length() < 7){
				primaryphoneno.setError("Please Enter Valid Mobile Number to verify"); 
			}
      
       }else if(crpasswd.getText().hashCode() == s.hashCode()){
    	   
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
    	 
     }else if(name.getText().hashCode() == s.hashCode()){
    	 if(!(ValidName(name.getText().toString()) && name.length()>=3 && name.length()<25)){
    		 name.setError(" Please Enter First Name Atleast 3 to 25 Without Any Special Characters. Accepts Only Alphabetics");
		 } else{
			 name.setError(null);
		 }
     }else if(lname.getText().hashCode() == s.hashCode()){
    	 if(!(ValidName(lname.getText().toString()) && lname.length()>=1 && lname.length()<25)){
    		 lname.setError(" Please Enter Last Name Without Any Special Characters, Atleast 1 to 25 Any Special Characters. Accepts Only Alphabetics");		
			}else{
				lname.setError(null);	
			}
     }else if(mail.getText().hashCode() == s.hashCode()){
    	 
    	 if(!(ValidEmail(mail.getText().toString())&& mail.length()>0)){
					mail.setError("Please Enter Valid Email Id");
	    }else{
	    	mail.setError(null);
	    }
				 
     }
	 }catch(Exception e){
		 e.printStackTrace();
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
/**
 * sending the OTP to server and activating the user
 */
private void verifyOtp() {
    String otp = inputOtp.getText().toString().trim();
    if(!otp.isEmpty()) {
        Intent grapprIntent = new Intent(getApplicationContext(), HttpService.class);
        grapprIntent.putExtra("otp", otp);
        startService(grapprIntent);
    }else{
        Toast.makeText(getApplicationContext(), "Please Enter the OTP", Toast.LENGTH_SHORT).show();
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
                resId = R.id.layout_sms;
                break;
            case 1:
                resId = R.id.layout_otp;
                break;
        }
        return findViewById(resId);
    }
}
public class OnSend extends AsyncTask<String, Void,Void>{
	
	protected void onPreExecute() {
		// TODO Auto-generated method stub	
		pd = new ProgressDialog(RegisterActivity.this);
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
		 AlertDialog.Builder build = new AlertDialog.Builder(RegisterActivity.this);
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
		    	    	  String prphno=primaryphoneno.getText().toString().trim();
			        		 ApplicationData.add_phno(prphno);
			        		 String one="1".toString().trim();
			        		 ApplicationData.add_reg(one);
		    	    	  viewPager.setCurrentItem(1);
		    	    	 /* JSONObject jsnonb = json.getJSONObject("data");
		    	    	  build.setMessage(jsnonb.toString());
							build.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
									viewPager.setCurrentItem(1);
								}
							});
			    		    build.show();*/
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
				try {		
					//store loginpasswd in sharedpreference
					 String crpass=crpasswd.getText().toString().trim();
        			 String repass=repasswd.getText().toString().trim();
        		 	 String fnames=name.getText().toString().trim();
        		     String lnames=lname.getText().toString().trim();
        			 String em=mail.getText().toString().trim();
        		     String prphno=primaryphoneno.getText().toString().trim();				
					Log.i(getClass().getSimpleName(),(String)getText(R.string.sendtask));
				    JSONObject loginJson = new JSONObject();
				    
					loginJson.put("primaryno", prphno);
					loginJson.put("email", em);
					loginJson.put("fname", fnames);
					loginJson.put("lname", lnames);
					loginJson.put("pwd", crpass);
				 
					 
					 HttpParams httpParams = new BasicHttpParams();
					 HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT_MILLISEC);
					 HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
					 
					 DefaultHttpClient httpclient = new MyHttpsClient(httpParams,getApplicationContext());
				     HttpPost httppost = new HttpPost(Config.URL_REQUEST_SMS);
				     
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
}

