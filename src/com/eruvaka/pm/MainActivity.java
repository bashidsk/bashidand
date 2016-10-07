package com.eruvaka.pm;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnRefreshListener {
	private static final int TIMEOUT_MILLISEC = 0;
	ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> send_list = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> update_list = new ArrayList<HashMap<String, String>>();
	SharedPreferences ApplicationDetails;
	SharedPreferences.Editor ApplicationDetailsEdit;
	private SwipeRefreshLayout swipeView;
	// variable to toggle city values on refresh
	   boolean refreshToggle = true;
	   AlertDialog.Builder build;
	   String timezone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try{
		 getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.signinupshape));
		  ApplicationDetails = getApplicationContext().getSharedPreferences("com.pm",MODE_PRIVATE);
		   timezone= ApplicationDetails.getString("timezone", null); 
		  build = new AlertDialog.Builder(MainActivity.this);
		  swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe_view);
	      swipeView.setOnRefreshListener(this);
	      swipeView.setColorSchemeColors(Color.BLUE, Color.BLUE, Color.BLUE,Color.BLUE, Color.BLUE);
	      swipeView.setDistanceToTriggerSync(20);// in dips
	      swipeView.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used
		  if(isInternetAvailable()){
			 onRefresh();
			  			
		  }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	Handler handler = new Handler() {
	      public void handleMessage(android.os.Message msg) {
	 
	         if (refreshToggle) {
	            refreshToggle = false;
	            if(isInternetAvailable()){
					 final TableLayout t1=(TableLayout)findViewById(R.id.tblsetting1);
					 t1.setVerticalScrollBarEnabled(true);
				 	 new GetFeederDeatils().execute();
				  				
			  }
	         } else {
	            refreshToggle = true;
	            if(isInternetAvailable()){
					 final TableLayout t1=(TableLayout)findViewById(R.id.tblsetting1);
					 t1.setVerticalScrollBarEnabled(true);
					 new GetFeederDeatils().execute();
				  				
			  }
	         }
	                
	         swipeView.postDelayed(new Runnable() {
	 
	            @Override
	            public void run() {
	              
	               swipeView.setRefreshing(false);
	              
	            }
	         }, 1000);
	      };
	   };
	 @Override
	   public void onRefresh() {
	 
	      swipeView.postDelayed(new Runnable() {
	 
	         @Override
	         public void run() {
	            swipeView.setRefreshing(true);
	            handler.sendEmptyMessage(0);
	         }
	      }, 1000);
	   }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		 getMenuInflater().inflate(R.menu.main, menu);
		 try{
		 MenuItem menuItem = menu.findItem(R.id.login_in1);
		  String user_name = ApplicationDetails.getString("FirstName", null);
	       menuItem.setTitle("User : "+user_name);
	       
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		  if(id==R.id.action_settings1){
			  build.setTitle("Logout");
			  build.setPositiveButton("OK",new DialogInterface.OnClickListener() {
              @Override
				public void onClick(DialogInterface dialog, int id) {
             	 dialog.cancel();
             	signoutdialog();
              }
              });
         		build.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface dialog,
							int which) {

						dialog.cancel();
					}
				});
      		build.show();
			return true;
		}else if(id==R.id.login_in1){
			Intent user_intent = new Intent(MainActivity.this,UserProfileActivity.class);
			user_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(user_intent);
			return true;
		}
		else if(id==R.id.aboutus){
			try{
	    		AboutUsDialog();	
	    	}catch (Exception e) {
				// TODO: handle exception
			}
			return true;	
		}
    	
    	
		return super.onOptionsItemSelected(item);
	}
	private void AboutUsDialog() {
		// TODO Auto-generated method stub
	try{
 	build.setTitle(R.string.aboutus);
	build.setMessage(R.string.versions);
 	build.setPositiveButton(R.string.ok,
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
								 			
				}
			});
	 
	build.create().show();
	}catch(Exception e){
		e.printStackTrace();
	}
	}

	private void signoutdialog() {
		  try{
	    		  ApplicationDetailsEdit = ApplicationDetails.edit();
				  ApplicationDetailsEdit.putBoolean("isLogged", false);
				  ApplicationDetailsEdit.commit();
				  Application app = getApplication();					
				  Intent loginintent = new Intent(app,LoginActivity.class);
				  loginintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				  startActivity(loginintent);
				   finish();	
				  }catch(Exception e){
			    		   e.printStackTrace();
			    }
	}
	protected boolean isInternetAvailable() {
		// TODO Auto-generated method stub
		ConnectivityManager cm =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	     if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			
	  	  return true;		        
		}else{ 
			Toast.makeText(MainActivity.this, R.string.internet, Toast.LENGTH_SHORT).show();					
		}
		return false;
		}
	 public class GetFeederDeatils extends AsyncTask<String, Void, Void> {
	        ProgressDialog progressdialog= new ProgressDialog(MainActivity.this);

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
	            progressdialog.setCanceledOnTouchOutside(false);
	            super.onPreExecute();
	        }
	        /* (non-Javadoc)
	         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	         */
	        @Override
	        protected void onPostExecute(Void result) {
	            // TODO Auto-generated method stub
	                progressdialog.dismiss();
	                swipeView.setRefreshing(false);
	                super.onPostExecute(result);
	                
	    	    	build.setTitle("Feeders Data");
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
			 		    		 
			 		    		String data=jsn.getString("data");
		           				JSONObject jObject2 = new JSONObject(data);
		           				//Get Feeder Details
		           				JSONArray feeder_details = jObject2.getJSONArray("device_data");
		           				mylist.clear();
		           				for (int i = 0; i < feeder_details.length(); i++) {
			           			     try{
			           				HashMap<String, String> map = new HashMap<String, String>();
			           				JSONObject jObject = feeder_details.getJSONObject(i);
			           				map.put("feederId", jObject.getString("feederId"));
			           				map.put("feederSno", jObject.getString("feederSno"));
			           				map.put("feederName", jObject.getString("feederName"));
			           				if(jObject.getString("schedule_date").isEmpty()){
			           					map.put("schedule_date", "0000-00-00");
			           					
			           				}else{
			           					map.put("schedule_date", jObject.getString("schedule_date"));
			           				}
			           				
			           				map.put("last_update_time", jObject.getString("last_update_time"));  
			           				map.put("total_actual_feed", jObject.getString("total_actual_feed"));
			           				map.put("total_dispensed_feed", jObject.getString("total_dispensed_feed"));
			           			 	map.put("kg_feed_disp_time", jObject.getString("kg_feed_disp_time"));
			           				map.put("mode", jObject.getString("mode"));
			           				JSONArray schedules = jObject.getJSONArray("schedules");
			           				map.put("schedules", schedules.toString());
			           				mylist.add(map);
			           			        }catch(Exception e){
			           					  e.printStackTrace();	
			           					}
			           				} 
		           			
		           				if(mylist!=null){
		           					updated();
		           				}
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
					 loginJson.put("sch_check", 1);
					 
					 HttpParams httpParams = new BasicHttpParams();
					 HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT_MILLISEC);
					 HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
				     DefaultHttpClient httpclient = new MyHttpsClient(httpParams,getApplicationContext());
					 HttpPost httppost = new HttpPost(Config.URL_FEEDER_DATA);
					 httppost.setEntity(new ByteArrayEntity(loginJson.toString().getBytes("UTF8")));
					 httppost.setHeader("eruv",loginJson.toString());
					 HttpResponse response = httpclient.execute(httppost);
					 HttpEntity entity = response.getEntity();		
							 
					 // If the response does not enclose an entity, there is no need
					 if (entity != null){
					 InputStream instream = entity.getContent();
					 String result = convertStreamToString(instream);
					 Log.i("Read from server", result);	
						ApplicationData.addregentity(result);
					 }else{
			             String noresponse="nodata".toString().trim();
			           	 ApplicationData.addregentity(noresponse);
			         }
					 
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        	return null;

	        }

	    }
	 private void updated() {
		 try{
			 final TableLayout t1=(TableLayout)findViewById(R.id.tblsetting1);
			 t1.setVerticalScrollBarEnabled(true);
		   	 t1.removeAllViewsInLayout(); 
			 for(int i=0; i<mylist.size(); i++) {
		   		 final HashMap<String, String> map = mylist.get(i);
		   		// final String feederSno=map.get("feederSno").toString().trim();
		   		 //final String feederId=map.get("feederId").toString().trim();
		   		 final String feederName=map.get("feederName").toString().trim();
		   		 final String schedule_date=map.get("schedule_date").toString().trim();
		   		 final String last_update_time=map.get("last_update_time").toString().trim();
		   		 final String total_dispensed_feed=map.get("total_dispensed_feed").toString().trim();
		   		 final String total_actual_feed=map.get("total_actual_feed").toString().trim();
		   		// final String kg_feed_disp_time_all=map.get("kg_feed_disp_time").toString().trim();
		   		 final String modee=map.get("mode").toString().trim();
		   		 
		   		final LinearLayout tablerow1=new LinearLayout(MainActivity.this);
	              tablerow1.setOrientation(LinearLayout.HORIZONTAL);
	              LinearLayout.LayoutParams paramas = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
	                      LinearLayout.LayoutParams.WRAP_CONTENT,1);
	               paramas.setMargins(0,0,0,0);
	               tablerow1.setLayoutParams(paramas);
	               tablerow1.setPadding(5,0,5,5);
	              // tablerow1.setBackgroundResource(R.drawable.shadow);
	              
	               final TextView PondName=new TextView(MainActivity.this);
	               PondName.setText(feederName);
	               PondName.setTextColor(Color.parseColor("#24890d"));
	               PondName.setGravity(Gravity.LEFT);
	               PondName.setFreezesText(true);
	               PondName.setTextSize(18);
	               PondName.setFocusable(false);
	               LinearLayout.LayoutParams paramas2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
		                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
		              paramas2.setMargins(0, 0, 0, 0);
		              PondName.setLayoutParams(paramas2);
	               
	               
	              
	               final LinearLayout tablerow_last_update_time_tv=new LinearLayout(MainActivity.this);
	               tablerow_last_update_time_tv.setOrientation(LinearLayout.HORIZONTAL);
		              LinearLayout.LayoutParams paramas_last_update_time_tv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
		                      LinearLayout.LayoutParams.WRAP_CONTENT,1);
		              paramas_last_update_time_tv.setMargins(0,0,0,0);
		               tablerow_last_update_time_tv.setLayoutParams(paramas_last_update_time_tv);
		               tablerow_last_update_time_tv.setPadding(0,0,0,0);
		              
		               final TextView last_update_time_tv=new TextView(MainActivity.this);
		               last_update_time_tv.setTextColor(Color.parseColor("#a5151d"));
		               last_update_time_tv.setGravity(Gravity.LEFT);
		               last_update_time_tv.setFreezesText(true);
		               last_update_time_tv.setTextSize(18);
		               last_update_time_tv.setFocusable(false);
		               LinearLayout.LayoutParams paramas92 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
		               last_update_time_tv.setLayoutParams(paramas92);
		               last_update_time_tv.setPadding(0,0,0,5);
		               tablerow_last_update_time_tv.addView(last_update_time_tv);
		               Animation anim = new AlphaAnimation(0.0f, 1.0f);
			              anim.setDuration(50); //You can manage the time of the blink with this parameter
			              anim.setStartOffset(20);
			              anim.setRepeatMode(Animation.REVERSE);
			              anim.setRepeatCount(Animation.INFINITE);
			              
			              final TextView status_tv_head=new TextView(MainActivity.this);
			              
			               status_tv_head.setTextColor(Color.parseColor("#5186ab"));
			               status_tv_head.setGravity(Gravity.RIGHT);
			               status_tv_head.setFreezesText(true);
			               status_tv_head.setTextSize(18);
			               status_tv_head.setFocusable(false);
			               status_tv_head.setPadding(0,0,0,10);
			               LinearLayout.LayoutParams paramas4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
				              paramas4.setMargins(0, 0, 0,0);
				              status_tv_head.setLayoutParams(paramas4);
	               final TextView schedules_date=new TextView(MainActivity.this);
	               try{
	              
	    	   if(schedule_date.equals("0000-00-00")||schedule_date.isEmpty()){
	    		   schedules_date.setText("0000-00-00");
	    	     }else{
	    	     
	    	    	 	Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone(timezone));
	    	    		//DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z"); 
	    	    		//DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");    
	    	            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
	    	    		formatter.setTimeZone(TimeZone.getTimeZone(timezone));  
	    	    		String current_date=formatter.format(calendar.getTime());
	    	    		 Date f_date = formatter.parse(schedule_date);
	    	      	    SimpleDateFormat format2  = new SimpleDateFormat("yyyy-MM-dd");
						 format2.setTimeZone(TimeZone.getTimeZone(timezone)); 
						 String startdate=(format2.format(f_date)).toString().trim();
						 if(current_date.equals(startdate)){
							  schedules_date.setText("Today"); 
							  last_update_time_tv.setText("Last Updated At : "+" Today "+last_update_time);
							 // last_update_time_tv.startAnimation(anim);
							  status_tv_head.setText("Today");
						 }else{
							  schedules_date.setText(startdate);
							  last_update_time_tv.setText("Last Updated At : "+startdate+" "+last_update_time);
							  //last_update_time_tv.startAnimation(anim);
							  status_tv_head.setText("Status");
						 }
						
	    	    	 
	    	   }
	   	         
	              
	               }catch(Exception e){
	            	   e.printStackTrace();
	               }
	               schedules_date.setTextColor(Color.BLACK);
	               schedules_date.setGravity(Gravity.LEFT);
	               schedules_date.setFreezesText(true);
	               schedules_date.setTextSize(16);
	               schedules_date.setFocusable(false);
	               
	               LinearLayout.LayoutParams paramas222 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
		                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
		              paramas222.setMargins(0, 0, 0, 0);
		              schedules_date.setLayoutParams(paramas2);
      
	               final TextView tdf=new TextView(MainActivity.this);
	               tdf.setText(" "+total_dispensed_feed +" / "+total_actual_feed+"  Kg");
	               tdf.setTextColor(Color.BLACK);
	               tdf.setGravity(Gravity.CENTER);
	               tdf.setFreezesText(true);
	               tdf.setTextSize(16);
	               tdf.setFocusable(false);
	               
	               LinearLayout.LayoutParams paramas3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
		                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
		              paramas3.setMargins(0, 0, 0,0);
		              tdf.setLayoutParams(paramas3);
		              tdf.setPadding(0,0,0,10);
		              
		             
		              
		              
		            /*  final TextView Pause1=new TextView(MainActivity.this);
		              Pause1.setTextColor(Color.BLACK);
		              Pause1.setGravity(Gravity.LEFT);
		              Pause1.setFreezesText(true);
		              Pause1.setTextSize(18);
		              Pause1.setFocusable(false);
		              Pause1.setPadding(0,0,10,0);
		              Pause1.setHeight(0);
		              Pause1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.s1,0,0,0);
		              
		    				               
		               final TextView Stop1=new TextView(MainActivity.this);
		               Stop1.setTextColor(Color.BLACK);
		               Stop1.setGravity(Gravity.LEFT);
		               Stop1.setFreezesText(true);
		               Stop1.setTextSize(18);
		               Stop1.setFocusable(false);
		               Stop1.setPadding(0,0,10,0);
		               Stop1.setHeight(0);
		               Stop1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.s1,0,0,0);*/
		              
		              
		              
		           tablerow1.addView(PondName);   
	               tablerow1.addView(tdf);
	               tablerow1.addView(status_tv_head);
	               //tablerow1.addView(schedules_date);
	              // tablerow1.addView(Pause1);
	               //tablerow1.addView(Stop1);
	              
	               t1.addView(tablerow1);
	               
	            
	               tablerow1.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							 if(isInternetAvailable()){
				 				 try{
				 					final String feederSno=map.get("feederSno").toString().trim();
									ApplicationData.add_feeder_Sno(feederSno);
							   		final String feederId=map.get("feederId").toString().trim();
							   		ApplicationData.add_feeder_Id(feederId);
							   		final String feederName=map.get("feederName").toString().trim();
							   	    ApplicationData.add_feeder_Name(feederName);
							   	    
									   new GetSingleFeederData().execute();
						    	      }catch(Exception e){
					  			   e.printStackTrace();
					  		       }	
				 			   }
						}
					});
		               final String schedules=map.get("schedules").toString().trim();
				 
				   		JSONArray jsonArray = new JSONArray(schedules);
				   		 if(jsonArray.length()>0){
				   			for (int j = 0; j < jsonArray.length(); j++) {
					   			 
			      				JSONObject jObject = jsonArray.getJSONObject(j);
			      				//final String schedule_id=jObject.getString("schedule_id");
			      				final String schedule_times=jObject.getString("schedule_times");
			      				//final String totalTime=jObject.getString("totalTime");
			      				final String original_feed=jObject.getString("original_feed");
			      				//final String one_cycle_feed=jObject.getString("one_cycle_feed");
			      				//final String feed_gap=jObject.getString("feed_gap");
			      				//final String kg_feed_disp_time=jObject.getString("kg_feed_disp_time");
			      				final String mode=jObject.getString("mode");
			      				 
			      				final String status=jObject.getString("status");
			      				final String dispensed_feed=jObject.getString("dispensed_feed");
			      				 
			      				 final LinearLayout tablerow3=new LinearLayout(MainActivity.this);
					              tablerow3.setOrientation(LinearLayout.HORIZONTAL);
					              LinearLayout.LayoutParams paramas11 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					                      LinearLayout.LayoutParams.WRAP_CONTENT,1);
					           
					               tablerow3.setPadding(5,0,5,5);
					               tablerow3.setLayoutParams(paramas11);
					              // tablerow3.setBackgroundResource(R.drawable.shadow);
					               Date t_date=null;
					               String from_time="00:00";
					               String to_time="00:00";
						      	 	try{
						              String ss[]=schedule_times.split("-");
						 				 String ss1=ss[0];
						 				 String ss2=ss[1];
						 				final SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH:mm");
						 					  dateFormat1.setTimeZone(TimeZone.getTimeZone(timezone)); 
						 						 Date f_date = dateFormat1.parse(ss1);
						 						  t_date = dateFormat1.parse(ss2);
						 					 SimpleDateFormat format2  = new SimpleDateFormat("HH:mm");
						 					format2.setTimeZone(TimeZone.getTimeZone(timezone));
						 					from_time=(format2.format(f_date)).toString().trim();
						 					to_time=(format2.format(t_date)).toString().trim();
						 					
						 			 		
						      	 	}catch(Exception e){
						      	 		e.printStackTrace();
						      	 	}
						      			
					               tablerow3.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										 if(isInternetAvailable()){
							 				 try{
							 					final String feederSno=map.get("feederSno").toString().trim();
												ApplicationData.add_feeder_Sno(feederSno);
										   		final String feederId=map.get("feederId").toString().trim();
										   		ApplicationData.add_feeder_Id(feederId);
										   		final String feederName=map.get("feederName").toString().trim();
										   	    ApplicationData.add_feeder_Name(feederName);
										   	    
												   new GetSingleFeederData().execute();
									    	      }catch(Exception e){
								  			   e.printStackTrace();
								  		       }	
							 			   }
								           
									}
								});
					              
					               final TextView schedule_time=new TextView(MainActivity.this);
					               schedule_time.setTextColor(Color.BLACK);
					               schedule_time.setGravity(Gravity.LEFT);
					               schedule_time.setFreezesText(true);
					               schedule_time.setTextSize(16);
					               schedule_time.setFocusable(false);
					               LinearLayout.LayoutParams paramas51 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
						                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
					               schedule_time.setLayoutParams(paramas51);
					               schedule_time.setPadding(0,0,0,0);
					               schedule_time.setText(from_time+" - "+to_time);
					               tablerow3.addView(schedule_time);
					            
					               
					              
					               
					               final TextView DF11=new TextView(MainActivity.this);
					               DF11.setTextColor(Color.BLACK);
					               DF11.setGravity(Gravity.CENTER);
					               DF11.setFreezesText(true);
					               DF11.setTextSize(16);
					               DF11.setFocusable(false);
					               LinearLayout.LayoutParams paramas91 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
						                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
						           DF11.setLayoutParams(paramas91);
						           DF11.setPadding(0,0,0,5);
						           DF11.setText(" "+dispensed_feed +" / "+original_feed+"  Kg"); 
					               tablerow3.addView(DF11);
					              /* final String comp="completed".toString().trim();
						              
					                 if(status.equals(comp)){
					                	 OF.setText("0" +"  Kg");
					                	  DF11.setText("0"+"  Kg");  
					                 }else{
					                	 OF.setText(original_feed +"  Kg");
					                	  DF11.setText(dispensed_feed+"  Kg"); 
					                 }*/
					               if(mode.equals("78")){
    				      			  // OF.setText(original_feed +"  Kg");
    						          //    tablerow3.addView(OF);
    						           //   DF11.setText(dispensed_feed +"  Kg"); 
    						           //   tablerow3.removeView(schedule_time);
    				      			}
					          			  
					               final TextView Start=new TextView(MainActivity.this);
					               Start.setTextColor(Color.BLACK);
					               Start.setGravity(Gravity.LEFT);
					               Start.setFreezesText(true);
					               Start.setTextSize(18);
					               Start.setFocusable(false);
					               Start.setPadding(0,0,10,0);
					               Start.setCompoundDrawablesWithIntrinsicBounds(R.drawable.s1,0,0,0);
					               
					               final TextView Start1=new TextView(MainActivity.this);
					               Start1.setTextColor(Color.BLACK);
					               Start1.setGravity(Gravity.LEFT);
					               Start1.setFreezesText(true);
					               Start1.setTextSize(18);
					               Start1.setFocusable(false);
					               Start1.setPadding(0,0,10,0);
					               Start1.setHeight(0);
					               Start1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.s1,0,0,0);
					               
					               final TextView Pause=new TextView(MainActivity.this);
						           Pause.setTextColor(Color.BLACK);
					               Pause.setGravity(Gravity.LEFT);
					               Pause.setFreezesText(true);
					               Pause.setTextSize(18);
					               Pause.setFocusable(false);
					               Pause.setPadding(0,0,10,0);
					               Pause.setCompoundDrawablesWithIntrinsicBounds(R.drawable.p1,0,0,0);
					    				               
					               final TextView Stop=new TextView(MainActivity.this);
					               Stop.setTextColor(Color.BLACK);
					               Stop.setGravity(Gravity.LEFT);
					               Stop.setFreezesText(true);
					               Stop.setTextSize(18);
					               Stop.setFocusable(false);
					               Stop.setPadding(0,0,10,0);
					               Stop.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stop1,0,0,0);
					              
					               
					                
					                final TextView status_tv_data=new TextView(MainActivity.this);
					               
					                status_tv_data.setGravity(Gravity.RIGHT);
					                status_tv_data.setFreezesText(true);
					                status_tv_data.setTextSize(16);
					                status_tv_data.setFocusable(false);
					                status_tv_data.setPadding(0,0,0,10);
					                LinearLayout.LayoutParams paramas5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
						                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
						              paramas5.setMargins(0, 0, 0,0);
						              status_tv_data.setLayoutParams(paramas5);
						             
						             
					                
					                tablerow3.addView(status_tv_data);
					            			                
					                t1.addView(tablerow3);
					                
					                String to_be_run="to_be_run".toString().trim();
    				      			String running="running".toString().trim();
    				      			String changed="changed".toString().trim();
    				      			String paused="paused".toString().trim();
    				      			
    				      			if(status.equals(to_be_run)){
    				      				//start.setBackgroundResource(R.drawable.start);	 
    				      				//tvstatus.setText("running");
    				      				status_tv_data.setText("To Be Run");
    				      				
    				      			  /* tablerow3.addView(Start1);
    				      			   tablerow3.addView(Start);
    				      			  Start.setHeight(0);*/
    				      				
    				      			}else if(status.equals(running)){
    				      				 /*tablerow3.addView(Pause);
    				      				 tablerow3.addView(Stop);*/
    				      				 status_tv_data.setTextColor(Color.parseColor("#24890d"));
    				      				status_tv_data.setText("On Run");
    				      				status_tv_data.startAnimation(anim);
    				      				status_tv_data.setTextSize(18);
    				      				//tvstatus.setText("running");
    				      			}else if(status.equals(changed)){
    				      				 /*tablerow3.addView(Pause);
    				      				 tablerow3.addView(Stop);*/
    				      				status_tv_data.setText("Changed");
    				      				 status_tv_data.setTextColor(Color.BLACK);
    				      				//tvstatus.setText("changed");
    				      			}else if(status.equals(paused)){
    				      				status_tv_data.setText("Paused");
    				      				 status_tv_data.setTextColor(Color.BLACK);
    				      			 /* tablerow3.addView(Start);
    				      			  Start.setHeight(0);
    				      			  tablerow3.addView(Stop);*/
    				      		 	 //tvstatus.setText("paused");
    				      			}else{
    				      				status_tv_data.setText("Completed");
    				      				 status_tv_data.setTextColor(Color.BLACK);
    				      			  /*tablerow3.addView(Start1);
    				      			  tablerow3.addView(Start);
    				      			  Start.setHeight(0);*/
    				      				//tvstatus.setText("completed");
    				      			}
    				      			
    				      			          
					   		}
				   		  t1.addView(tablerow_last_update_time_tv);
			                
				   		 }else{
				   			 
				   		 final LinearLayout tablerow3=new LinearLayout(MainActivity.this);
			              tablerow3.setOrientation(LinearLayout.HORIZONTAL);
			              LinearLayout.LayoutParams paramas11 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			              LinearLayout.LayoutParams.WRAP_CONTENT,1);
			           
			               tablerow3.setPadding(5,0,5,5);
			               tablerow3.setLayoutParams(paramas11);
			               //tablerow3.setBackgroundResource(R.drawable.shadow);
			             
			              
			               final TextView schedule_ttme=new TextView(MainActivity.this);
			               schedule_ttme.setText("00:00 - 00:00");
			               schedule_ttme.setTextColor(Color.BLACK);
			               schedule_ttme.setGravity(Gravity.LEFT);
			               schedule_ttme.setFreezesText(true);
			               schedule_ttme.setTextSize(16);
			               schedule_ttme.setFocusable(false);
			               schedule_ttme.setPadding(0,0,0,5);
			               
			               LinearLayout.LayoutParams paramas41 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
				       
			               schedule_ttme.setLayoutParams(paramas41);
			               tablerow3.addView(schedule_ttme);
			               
			               final TextView DF11=new TextView(MainActivity.this);
			               DF11.setText(" 0"+" / "+"0"+"  Kg");
			               DF11.setTextColor(Color.BLACK);
			               DF11.setGravity(Gravity.CENTER);
			               DF11.setFreezesText(true);
			               DF11.setTextSize(16);
			               DF11.setPadding(0,0,0,5);
			               DF11.setFocusable(false);
			               LinearLayout.LayoutParams paramas91 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
				             
				              DF11.setLayoutParams(paramas91);
			               tablerow3.addView(DF11);
			               //tablerow3.addView(EDIT);
			               t1.addView(tablerow3);
			               
			               
			               
			               final TextView status_tv_data=new TextView(MainActivity.this);
			                status_tv_data.setTextColor(Color.BLACK);
			                status_tv_data.setGravity(Gravity.RIGHT);
			                status_tv_data.setFreezesText(true);
			                status_tv_data.setTextSize(16);
			                status_tv_data.setFocusable(false);
			                status_tv_data.setPadding(0,0,0,10);
			                status_tv_data.setText("Add Schedule");
			                LinearLayout.LayoutParams paramas5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
				              paramas5.setMargins(0, 0, 0,0);
				              status_tv_data.setLayoutParams(paramas5);
			                tablerow3.addView(status_tv_data);
			               
			               final TextView Start=new TextView(MainActivity.this);
			               Start.setTextColor(Color.BLACK);
			               Start.setGravity(Gravity.LEFT);
			               Start.setFreezesText(true);
			               Start.setTextSize(18);
			               Start.setFocusable(false);
			               Start.setPadding(0,0,10,0);
			               Start.setHeight(0);
			               Start.setCompoundDrawablesWithIntrinsicBounds(R.drawable.s1,0,0,0);
			              
			               
			               final TextView Start1=new TextView(MainActivity.this);
			               Start1.setTextColor(Color.BLACK);
			               Start1.setGravity(Gravity.LEFT);
			               Start1.setFreezesText(true);
			               Start1.setTextSize(18);
			               Start1.setFocusable(false);
			               Start1.setPadding(0,0,10,0);
			               Start1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.s1,0,0,0);
			               Start1.setHeight(0);
			               
			              // tablerow3.addView(Start1);
			               //tablerow3.addView(Start);
			               
			               tablerow3.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									 if(isInternetAvailable()){
						 				 try{
						 					final String feederSno=map.get("feederSno").toString().trim();
											ApplicationData.add_feeder_Sno(feederSno);
									   		final String feederId=map.get("feederId").toString().trim();
									   		ApplicationData.add_feeder_Id(feederId);
									   		final String feederName=map.get("feederName").toString().trim();
									   	    ApplicationData.add_feeder_Name(feederName);
									   	    
											   new GetSingleFeederData().execute();
								    	      }catch(Exception e){
							  			   e.printStackTrace();
							  		       }	
						 			   }
							           
								}
							});
			            
			          }
		 
				   	 
	                View v=new View(MainActivity.this);
	                v.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	                v.setBackgroundResource(R.drawable.seprator2);
	                v.setPadding(0, 0, 0, 0);
	                t1.addView(v);
	                 	
							  
							     
			 }
		 }catch(Exception e){
			 e.printStackTrace();
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
	 
	 
	 public class GetSingleFeederData extends AsyncTask<String, Void, Void> {
			
	     ProgressDialog progressdialog= new ProgressDialog(MainActivity.this);

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
			// TODO Auto-generated method stub
		 try{
	            // TODO Auto-generated method stub
             progressdialog.dismiss();
             super.onPostExecute(result);
             
 	    	build.setTitle(getResources().getString(R.string.action_settings));
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
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
						alertDialog.setMessage(error);
						alertDialog.setNegativeButton("OK",
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
		 		    		 mylist.clear();
		 		    		String data=jsn.getString("data");
		 		    		JSONObject jObject2 = new JSONObject(data);
	           				String timezone=jObject2.getString("timezone");
	           				           				 
	           			   ApplicationDetailsEdit = ApplicationDetails.edit();
	           			   ApplicationDetailsEdit.putString("timezone",timezone);
	           			   ApplicationDetailsEdit.commit();
	           			   
	           				String schedule_data=jObject2.getString("device_data");
	           				JSONObject jObject3 = new JSONObject(schedule_data);
	           				String schedule_date=jObject3.getString("schedule_date");
	           				//last_update_time
	           				String last_update_time=jObject3.getString("last_update_time");
	           				//String  feederSno=jObject3.getString("feederSno");
	           				String  kg_feed_disp_time=jObject3.getString("kg_feed_disp_time");
	           				String  mode=jObject3.getString("mode");
	           			    HashMap<String, String> map = new HashMap<String, String>();
	           			    map.put("last_update_time",last_update_time);
	           			    map.put("schedule_date",schedule_date);
	           				map.put("kg_feed_disp_time",kg_feed_disp_time);
	           				map.put("mode",mode);
	           				JSONArray schedules = jObject3.getJSONArray("schedules");
	           				map.put("schedules", schedules.toString());
	           				mylist.add(map);
	           			  	
	           				 if(mylist!=null){
	           					try{
	           					Intent viewpager=new Intent(MainActivity.this,ViewpagerActivity.class);
						   		Bundle bundle = new Bundle();
						   		bundle.putString("feederSno", ApplicationData.get_feederSno());
						   		bundle.putString("feederId", ApplicationData.get_feedeerId());
						   		bundle.putString("feederName", ApplicationData.get_feederName());
						   		viewpager.putExtras(bundle);
						   		viewpager.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						   		viewpager.putExtra("arraylist", mylist);
						   		startActivity(viewpager); 
						   		finish();
						   		//ApplicationData.add_mylist(mylist);
						   		ApplicationData.add_compare_mode(mode);
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
		    	 }
		    	 
		    	 
		     }


     }catch(Exception e){
			 e.printStackTrace();
			 Toast.makeText(getApplicationContext(), "no response from server please try again", Toast.LENGTH_SHORT).show();
		 }
						
		}
	@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			
				try {					
				   String user_id = ApplicationDetails.getString("user_id", null);
			       String feederSno=   ApplicationData.get_feederSno().toString().trim();
				 
					try{ 
					Log.i(getClass().getSimpleName(), "sending  task - started");
					JSONObject loginJson = new JSONObject();
					loginJson.put("user_id", user_id);
					loginJson.put("feederSno", feederSno);
					HttpParams httpParams = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT_MILLISEC);
					HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
				    DefaultHttpClient httpclient = new MyHttpsClient(httpParams,getApplicationContext());
					HttpPost httppost = new HttpPost(Config.URL_GET_Single_Feeder);
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
				    }catch (Throwable t) {
					 t.printStackTrace();
					 String noresponse = "nodata".toString().trim();
						ApplicationData.addregentity(noresponse);
					}

							
			return null;		
		}

	
		 
	 }
	 
}
