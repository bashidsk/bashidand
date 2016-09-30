package com.eruvaka.pm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingsFragment  extends Fragment{
	ArrayList<HashMap<String, String>> update_list = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> schedule_list = new ArrayList<HashMap<String, String>>();
	private static final int TIMEOUT_MILLISEC = 0;
	 SharedPreferences ApplicationDetails;
	  	SharedPreferences.Editor ApplicationDetailsEdit;
	  /*	public static SettingsFragment newInstance(String text) {

			SettingsFragment f = new SettingsFragment();
		    Bundle b = new Bundle();
		    b.putString("msg", text);

		    f.setArguments(b);

		    return f;
		}*/
	  	 AlertDialog.Builder build ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View v = inflater.inflate(R.layout.settings, container, false);
	    try{
	  
		  ApplicationDetails = getActivity().getSharedPreferences("com.pm",0);
   	      build = new AlertDialog.Builder(getActivity());
           TextView aliasname=(TextView)v.findViewById(R.id.feederId3);
	 	   aliasname.setText("Device Id :"+ApplicationData.get_feedeerId());
	        final EditText feedername=(EditText)v.findViewById(R.id.feedername);
			feedername.setText(ApplicationData.get_feederName());
			feedername.setSelection(feedername.getText().length());
			final EditText okdtime=(EditText)v.findViewById(R.id.okdtime);
			okdtime.setText(ApplicationData.get_kg_disp_time());
			okdtime.setSelection(okdtime.getText().length());
			final CheckBox feedcheckbox=(CheckBox)v.findViewById(R.id.basic_mode);
  			final CheckBox onoffcheckbox=(CheckBox)v.findViewById(R.id.schedule_mode);
  			String feed_on=ApplicationData.get_compare_mode();
  			
  			if(feed_on.equals("78")){
  				feedcheckbox.setChecked(true);
  				String feedcheck="78".toString().trim();
  				ApplicationData.addFeedChecked(feedcheck);
  			 	 
  			 				      				 
  			}else{
  				feedcheckbox.setChecked(false);
  				String feedcheck="79".toString().trim();
  				ApplicationData.addFeedChecked(feedcheck);
  				 
  			}
  			if(feed_on.equals("79")){
  				onoffcheckbox.setChecked(true);
  				String feedcheck1="79".toString().trim();
  				ApplicationData.addFeedChecked(feedcheck1);
  				 
  			}else{
  				onoffcheckbox.setChecked(false);
  				String feedcheck1="78".toString().trim();
  				ApplicationData.addFeedChecked(feedcheck1);
  				 
  			}	 
  			feedcheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
  				
  				@Override
  				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
  					// TODO Auto-generated method stub
  					if(buttonView.isChecked()){
  						feedcheckbox.setChecked(true);
  						String feedcheck="78".toString().trim();
	      				ApplicationData.addFeedChecked(feedcheck);
	      				onoffcheckbox.setChecked(false);
	      			 
  					}else{
  						onoffcheckbox.setChecked(true);
  						feedcheckbox.setChecked(false);
  						String feedcheck="79".toString().trim();
	      				ApplicationData.addFeedChecked(feedcheck);
	      				 
	      			}
  				}
  			});
  			onoffcheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
  				
  				@Override
  				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
  					// TODO Auto-generated method stub
  					if(buttonView.isChecked()){
  						onoffcheckbox.setChecked(true);
  						feedcheckbox.setChecked(false);
  						String feedcheck="79".toString().trim();
	      				ApplicationData.addFeedChecked(feedcheck);
	      				 
  					}else{
  						feedcheckbox.setChecked(true);
  						onoffcheckbox.setChecked(false);
  						String feedcheck="78".toString().trim();
	      				ApplicationData.addFeedChecked(feedcheck);
	      				 
  					}
  				}
  			});
			Button dialogupdate=(Button)v.findViewById(R.id.update_settings);
			dialogupdate.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					update_list.clear();
				    HashMap<String, String> map=new HashMap<String,String>();							
					map.put("feederId", "feederId");
					map.put("feeder_sno", ApplicationData.get_feederSno());
					map.put("feeder_alias_name", feedername.getText().toString());
					 String sch_mode=ApplicationData.getFeedChecked().toString().trim();
					map.put("sch_mode", sch_mode);
					map.put("feed_disptime", okdtime.getText().toString().trim());
					update_list.add(map);
					String fName=feedername.getText().toString();
					String DTIme=okdtime.getText().toString();
					if(isInternetAvailable()){
      					 try{
      						 if(fName.isEmpty()||DTIme.isEmpty()){
      							Toast.makeText(getActivity(), R.string.nullvalues, Toast.LENGTH_SHORT).show(); 
      						 }else{
      							 try{
      							new Update_Settings().execute(); 
      							 }catch(Exception e){
      								 e.printStackTrace();
      							 }
      						 }
	        					
	        					 }catch(Exception e){
	        						 e.printStackTrace();
	        					 }
      				}
      				 
				}
			});
		  	
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    return v;
	}

	
	protected boolean isInternetAvailable() {
		// TODO Auto-generated method stub
		ConnectivityManager cm =(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	     if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		  return true;		        
		}else{ 
			Toast.makeText(getActivity(), R.string.internet, Toast.LENGTH_SHORT).show();					
		}
		return false;
		}
	 public class Update_Settings extends AsyncTask<String, Void, Void> {		
		 ProgressDialog progressdialog= new ProgressDialog(getActivity());

			/* (non-Javadoc)
			 * @see android.os.AsyncTask#onPreExecute()
			 */
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub	
				   progressdialog.setMessage("Loading. please wait...");
		            progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		            progressdialog.setIndeterminate(true);
		            progressdialog.setCancelable(true);
		           // progressdialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbar));
		            progressdialog.show();
		            progressdialog.setCanceledOnTouchOutside(false);
		            super.onPreExecute();
			}
			/* (non-Javadoc)
			 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(Void result) {
				progressdialog.dismiss();
                super.onPostExecute(result);
          
    	    	build.setTitle("Update Settings");
    	    	String json_response=ApplicationData.getregentity().toString().trim();
    		     String no_response="nodata".toString().trim();
    		     if(no_response.equals(json_response)){
 		    	    build.setMessage("NO DATA Found");
 	           		build.setPositiveButton("OK",new DialogInterface.OnClickListener() {
 	                @Override
 					public void onClick(DialogInterface dialog, int id) {
 	               	 dialog.cancel();
 	                }
 	                });
 	        		build.show();
 		     }else{
 		    	 try{
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
					}else{
						try{
							String error = jsn.getString("data");
							build.setMessage(error);
							build.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
											 if(isInternetAvailable()){
												 Intent backintent=new Intent(getActivity(),MainActivity.class);
												 backintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
												 startActivity(backintent);
											  				
										  }
											
										}
									});
							build.show(); 
		 		    		 
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
							 String user_id = ApplicationDetails.getString("user_id", null);
							 JSONObject loginJson = new JSONObject();
							 loginJson.put("user_id", user_id);
							 
							 for(int i=0; i<update_list.size(); i++) {
						   		 HashMap<String, String> map = update_list.get(i);
						   		 final String feeder_sno=map.get("feeder_sno").toString().trim();
						   		// final String feederId=map.get("feederId").toString().trim();
						   		 final String feeder_alias_name=map.get("feeder_alias_name").toString().trim();
						   		 final String sch_mode=map.get("sch_mode").toString().trim();
						   		 final String feed_disptime=map.get("feed_disptime").toString().trim();
						   					   		 
						   		 loginJson.put("user_id", user_id);
								 loginJson.put("feeder_sno", feeder_sno);
								// loginJson.put("feederId", feederId);
							 	 loginJson.put("feeder_alias_name", feeder_alias_name);
								 loginJson.put("sch_mode",sch_mode);
								 loginJson.put("feed_disptime",feed_disptime);	
								 
							 
							 }
						
						
						 HttpParams httpParams = new BasicHttpParams();
					     HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT_MILLISEC);
					     HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
											       
						 DefaultHttpClient httpclient = new MyHttpsClient(httpParams,getActivity());
						 HttpPost httppost = new HttpPost(Config.URL_UpdateSettings);
						 httppost.setEntity(new ByteArrayEntity(loginJson.toString().getBytes("UTF8")));
						 httppost.setHeader("eruv",loginJson.toString());
						 HttpResponse response = httpclient.execute(httppost);
						 HttpEntity entity = response.getEntity();		
									 
						 // If the response does not enclose an entity, there is no need
						 if (entity != null) {
						 InputStream instream = entity.getContent();
						 String result = convertStreamToString(instream);
						  Log.i("Read from server", result);
						  ApplicationData.addregentity(result);				          
						 }else{
		                  	String noresponse="nodata".toString().trim();
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
}
