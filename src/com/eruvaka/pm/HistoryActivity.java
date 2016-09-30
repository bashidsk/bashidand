package com.eruvaka.pm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
 

public class HistoryActivity extends ActionBarActivity {

	 
	  static final int END_DATE_DIALOG_ID = 2;
	private Calendar cal;
	 
	private int end_day;
	private int end_month;
	private int end_year;
	private int day;
	private int month;
	private int year;
	private int hour;
	private int min;
	
	private static final int TIMEOUT_MILLISEC = 0;
	private TextView txt_startrDate,txt_endDate;
	ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	SharedPreferences ApplicationDetails;
	SharedPreferences.Editor ApplicationDetailsEdit;
	 android.support.v7.app.ActionBar bar;
	 static final int DATE_PICKER_ID = 1111; 
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		 cal = Calendar.getInstance();
		 
		  day = cal.get(Calendar.DAY_OF_MONTH);
		  month = cal.get(Calendar.MONTH);
		  year = cal.get(Calendar.YEAR);
		  
		  
		 try{
		        //action bar themes
		        bar=getSupportActionBar();
				bar.setDisplayOptions( android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM); 
				bar.setCustomView(R.layout.abs_layout);
				bar.setIcon(R.drawable.back_icon);
				//bar.setHomeAsUpIndicator(R.drawable.back_icon);
				TextView mytext=(TextView)findViewById(R.id.mytext);
				mytext.setText("History");
				bar.setHomeButtonEnabled(true);
			    bar.setDisplayHomeAsUpEnabled(true);
				bar.setIcon(android.R.color.transparent);
				 
		        }catch(Exception e){
		        	
		        }
		Intent i = getIntent();
		Bundle extras = i.getExtras();
		 getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.signinupshape));
		String feederSno = extras.getString("feederSno");
		String feederId = extras.getString("feederId");
		String feederName = extras.getString("feederName");
		 
		initializeDate();
		initializeDate1();
		txt_startrDate=(TextView)findViewById(R.id.txtstartdate);
		txt_endDate=(TextView)findViewById(R.id.txtEndDate);
		txt_startrDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//showDialog(START_DATE_DIALOG_ID);
				  showDialog(DATE_PICKER_ID);
			}
		});
		txt_endDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//showDialog(END_DATE_DIALOG_ID);
				  showDialog(END_DATE_DIALOG_ID);
			}
		});
		TextView load=(TextView)findViewById(R.id.loadbttnn);
		load.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
					isInternetAvailable();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		TableLayout t1=(TableLayout)findViewById(R.id.tblhistdemo);
		t1.setVerticalScrollBarEnabled(true);
		TableLayout.LayoutParams lp = new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
     	int leftMargin=5;int topMargin=10;int rightMargin=5;int bottomMargin=5;
   	  lp.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);  

   	 final TableRow tablerow= new TableRow(HistoryActivity.this);
	 tablerow.setLayoutParams(lp);

    	final TextView feederid=new TextView(HistoryActivity.this);
    	      feederid.setText("Schedules Time");
    	      feederid.setTextColor(Color.parseColor("#14648d"));
    	      feederid.setKeyListener(null);
    	      feederid.setTextSize(15);
	      //feedkg.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
    	      feederid.setGravity(Gravity.START);
    	      feederid.setFreezesText(true);
    	      feederid.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
    	      
    	  	final TextView actuvalfeed=new TextView(HistoryActivity.this);
    	  	actuvalfeed.setText("T.F");
    	  	actuvalfeed.setTextColor(Color.parseColor("#14648d"));
    	  	actuvalfeed.setKeyListener(null);
    	  	actuvalfeed.setTextSize(15);
		      //feedkg.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
    	  	actuvalfeed.setGravity(Gravity.CENTER);
    	  	actuvalfeed.setFreezesText(true);
    	  	actuvalfeed.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);  
    	 
    	  	final TextView modifiedfeed=new TextView(HistoryActivity.this);
    	  	modifiedfeed.setText("D.F");
    	  	modifiedfeed.setTextColor(Color.parseColor("#14648d"));
    	  	modifiedfeed.setKeyListener(null);
    	  	modifiedfeed.setTextSize(15);
		      //feedkg.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
    	  	modifiedfeed.setGravity(Gravity.CENTER);
    	  	modifiedfeed.setFreezesText(true);
    	  	modifiedfeed.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI); 

    	tablerow.addView(feederid); 
       	tablerow.addView(actuvalfeed);
       
        tablerow.addView(modifiedfeed);	
         t1.addView(tablerow);	
         View v2=new View(HistoryActivity.this);
         //v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
         TableLayout.LayoutParams lp12 = 
	       			new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
	         	int leftMargin12=0;
	            int topMargin12=0;
	            int rightMargin12=0;
	            int bottomMargin12=0;
	       			lp12.setMargins(leftMargin12, topMargin12, rightMargin12, bottomMargin12);             
	       			v2.setLayoutParams(lp12);
	       		 
         v2.setBackgroundResource(R.drawable.seperator);
	        t1.addView(v2);
	        TextView tab_text_bottom=(TextView)findViewById(R.id.tab_text_bottom);
	       tab_text_bottom.setText("T.F = Total Feed,  D.F = Dispensed Feed");
	}
	protected boolean    isInternetAvailable() {
		// TODO Auto-generated method stub
		ConnectivityManager cm =(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
	     NetworkInfo netInfo = cm.getActiveNetworkInfo();
	       if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	       	try{
	           
	              TableLayout t1=(TableLayout)findViewById(R.id.tblhist);
			  	  t1.setVerticalScrollBarEnabled(true);
			   	  t1.removeAllViewsInLayout();
			   	 SimpleDateFormat input = new SimpleDateFormat("dd MMM yyyy");
	        	   final SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
		            Date date1=input.parse(txt_startrDate.getText().toString());
		               String startdate=(output.format(date1));
       
		   	 SimpleDateFormat input2 = new SimpleDateFormat("dd MMM yyyy");
      	   final SimpleDateFormat output2 = new SimpleDateFormat("yyyy-MM-dd");
	            Date date2=input.parse(txt_endDate.getText().toString());
	               String enddate=(output.format(date2));
	               System.out.println(startdate);
	               System.out.println(enddate);
			      new History().execute();
	    	      }catch(Exception e){
  	  			   e.printStackTrace();
  	  		       }	
				  
			        return true;		        
	               }else{ 
	    	  	Toast.makeText(getApplicationContext(), R.string.internet, Toast.LENGTH_SHORT).show();					
	    }
	    return false;
		}
	public class History extends AsyncTask<String, Void, Void> {		
	     ProgressDialog progressdialog= new ProgressDialog(HistoryActivity.this);

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
             AlertDialog.Builder build = new AlertDialog.Builder(HistoryActivity.this);
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
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(HistoryActivity.this);
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
		 		    		 
		 		    		String data=jsn.getString("data");
	           				JSONObject jObject2 = new JSONObject(data);
	           				//Get Feeder Details
	           				JSONArray feeder_details = jObject2.getJSONArray("schedule_data");
	           				mylist.clear();
	           				for (int i = 0; i < feeder_details.length(); i++) {
		           			     try{
		           				HashMap<String, String> map = new HashMap<String, String>();
		           				JSONObject jObject = feeder_details.getJSONObject(i);
		           				map.put("schedule_date", jObject.getString("schedule_date"));
		           				map.put("schedule_count", jObject.getString("schedule_count"));
		           				//map.put("last_update_time", jObject.getString("last_update_time"));
		           				 
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


     }catch(Exception e){
			 e.printStackTrace();
			 Toast.makeText(getApplicationContext(), "no response from server please try again", Toast.LENGTH_SHORT).show();
		 }
						
		}
	@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			
				try {					
					//store loginpasswd in sharedpreference
				 
			            	 SimpleDateFormat input = new SimpleDateFormat("dd MMM yyyy");
				        	   final SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
					            Date date1=input.parse(txt_startrDate.getText().toString());
					               String startdate=(output.format(date1));
			         
					   	 SimpleDateFormat input2 = new SimpleDateFormat("dd MMM yyyy");
			        	   final SimpleDateFormat output2 = new SimpleDateFormat("yyyy-MM-dd");
				            Date date2=input.parse(txt_endDate.getText().toString());
				               String enddate=(output.format(date2));
		            
					 
					  
						 Context context=getApplicationContext();
			     			ApplicationDetails = context.getSharedPreferences("com.pm",MODE_PRIVATE);
			         	     String user_id = ApplicationDetails.getString("user_id", null);
			     		    Intent i = getIntent();
			     			Bundle extras = i.getExtras();
			     
			     			String feederSno = extras.getString("feederSno");
			     			String feederId = extras.getString("feederId");
			     			String feederName = extras.getString("feederName");
				 
					try{ 
					Log.i(getClass().getSimpleName(), "sending  task - started");
					JSONObject loginJson = new JSONObject();
					loginJson.put("user_id", user_id);
					loginJson.put("from_date", startdate);
					loginJson.put("to_date", enddate);
					loginJson.put("feeder_id", feederSno);
					HttpParams httpParams = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT_MILLISEC);
					HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
				    DefaultHttpClient httpclient = new MyHttpsClient(httpParams,getApplicationContext());
					HttpPost httppost = new HttpPost(Config.URL_FeederLogs);
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
	public void updated() {
		try{
			 final TableLayout t1=(TableLayout)findViewById(R.id.tblhist);
			 
		   	 for(int i=0; i<mylist.size(); i++) {
		   		 final HashMap<String, String> map = mylist.get(i);
		   		  
		   		 
		   		 final TextView datetv=new TextView(HistoryActivity.this);
				    datetv.setTextColor(Color.parseColor("#14648d"));
				    datetv.setKeyListener(null);
				    datetv.setTextSize(15);
				    
			        //feedkg.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
				    datetv.setGravity(Gravity.CENTER);
				    datetv.setFreezesText(true);
				    datetv.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
				    //datetv.setBackgroundResource(R.drawable.rounded_green);   
				  try {
					   String sdate1 = map.get("schedule_date").toString().trim();
					SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
					 Date dt;
						dt = form.parse(sdate1);
						long mill = dt.getTime();
						ApplicationData.addmill(mill);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   	 
				    SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yy",Locale.getDefault());
			        long milk=  ApplicationData.getmill();
			        Date date = new Date(milk);
			        String datetime = formatter.format(date).toString().trim();
			 
			        long today = System.currentTimeMillis();
			        SimpleDateFormat presentdate = new SimpleDateFormat("dd MMM yy",Locale.getDefault());
			        String present = presentdate.format(today).toString().trim();
			        if(present .equals( datetime )) {
			        	String todaydispaly = ("Today").toString().trim();
			        	datetv.setText(todaydispaly);
			        	 	        	
			        } else {
			        	 
			        	datetv.setText(datetime);
			        } 
			        t1.addView(datetv);
		   		 
		   		 
		   		 
		   		 
		   		 
		   	    final String schedules=map.get("schedules").toString().trim();
		   	 JSONArray jsonArray = new JSONArray(schedules);
		 	for (int j = 0; j < jsonArray.length(); j++) {
		 		JSONObject jObject = jsonArray.getJSONObject(j);
  			 
  				
  			
  				final String dispensed_feed=jObject.getString("dispensed_feed");
  				 final TableRow tablerow1 = new TableRow(HistoryActivity.this);
		       	    
		         TableLayout.LayoutParams lp1 = new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
			         	int leftMargin1=5;
			            int topMargin1=3;
			            int rightMargin1=5;
			            int bottomMargin1=3;
			       			lp1.setMargins(leftMargin1, topMargin1, rightMargin1, bottomMargin1);             
			       			tablerow1.setLayoutParams(lp1);
			       			final String schedule_times=jObject.getString("schedule_times");
			       		  final TextView fromtime1=new TextView(HistoryActivity.this);
		      		      try{
		      		      //String schedule=(fromar.get(x)+" - "+toar.get(x)).toString().trim();
		      		    	String schedule=(schedule_times).toString().trim();
		      		      fromtime1.setText(schedule);
		      		      }catch(Exception e){
		      		    	  e.printStackTrace();
		      		      }
		      		     
		      		      fromtime1.setKeyListener(null);
		      		      fromtime1.setTextColor(Color.BLACK);
		      		      fromtime1.setTextSize(13);
						  //offtimer.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
		      		      fromtime1.setGravity(Gravity.START);
		      		      fromtime1.setFreezesText(true);
		      		      fromtime1.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
		      			final String original_feed=jObject.getString("original_feed");
		      			 final TextView actuval_feed=new TextView(HistoryActivity.this);
						   float actuval_float=Float.parseFloat(original_feed);
						   String str1_original_feed=  String.format("%.2f", actuval_float);
						   actuval_feed.setText(str1_original_feed);
						   actuval_feed.setTextColor(Color.BLACK);
						   actuval_feed.setKeyListener(null);
						   actuval_feed.setTextSize(13);
						   //feedkg.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
						   actuval_feed.setGravity(Gravity.CENTER);
						   actuval_feed.setFreezesText(true);
						   actuval_feed.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
						   
						   final TextView disfeedkg=new TextView(HistoryActivity.this);
						   float dispensed_float=Float.parseFloat(dispensed_feed);
						   String str1_dispensed_feed=  String.format("%.2f", dispensed_float);
						   disfeedkg.setText(str1_dispensed_feed);
						   disfeedkg.setTextColor(Color.BLACK);
						   disfeedkg.setKeyListener(null);
						   disfeedkg.setTextSize(13);
						   //feedkg.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
						   disfeedkg.setGravity(Gravity.CENTER);
						   disfeedkg.setFreezesText(true);
						   disfeedkg.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);	
						   
				       		tablerow1.addView(fromtime1);
				       		tablerow1.addView(actuval_feed);
				       		 
				       		tablerow1.addView(disfeedkg);
				       
				       		t1.addView(tablerow1);
		 	}
		   	 }
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * We obtain inputstream from host and we convert here inputstream into string. 
	 * if inputstream is  improper then execption is raised
	 * @param instream
	 * @return
	 * @throws Exception
	 */

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
	private void initializeDate() {
		 try{
			 final Calendar calender = Calendar.getInstance();
				final Date date = new Date(calender.getTimeInMillis());
				day = calender.get(Calendar.DATE);
				month = calender.get(Calendar.MONTH);
				year = calender.get(Calendar.YEAR);
				final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		    	 txt_startrDate = (TextView)findViewById(R.id.txtstartdate);
				if (txt_startrDate != null) {
					txt_startrDate.setText(dateFormat.format(date));
					}
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		}
	private void initializeDate1() {
		 try{
			 final Calendar calender = Calendar.getInstance();
				final Date date = new Date(calender.getTimeInMillis());
				end_day = calender.get(Calendar.DATE);
				end_month = calender.get(Calendar.MONTH);
				end_day = calender.get(Calendar.YEAR);
				final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		    	 txt_endDate = (TextView)findViewById(R.id.txtEndDate);
				if (txt_endDate != null) {
					txt_endDate.setText(dateFormat.format(date));
					}
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		}
	@Override
	 
	 protected Dialog onCreateDialog(int id) {
		switch(id){
      
	   case END_DATE_DIALOG_ID:
  	 return new DatePickerDialog(this, endpickerListener,  year, month,day);
	   case DATE_PICKER_ID:
           
           // open datepicker dialog. 
           // set date picker for current date 
           // add pickerListener listner to date picker
           return new DatePickerDialog(this, pickerListener, year, month,day);
	}
	   return null;
	 }
	 
	 private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
		 
	        // when dialog box is closed, below method will be called.
	        @Override
	        public void onDateSet(DatePicker view, int selectedYear,
	                int selectedMonth, int selectedDay) {
	             
	            year  = selectedYear;
	            month = selectedMonth;
	            day   = selectedDay;
	 
	            // Show selected date 
	            txt_startrDate.setText(new StringBuilder().append(day)
	                    .append("-").append(month+1).append("-").append(year)
	                    .append(" "));
	           try{
	        	   SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
	        	   final SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy");
		            Date date1=input.parse(txt_startrDate.getText().toString());
		            txt_startrDate.setText(output.format(date1));
	           }catch(Exception e){
	        	   e.printStackTrace();
	           }
	           
	            
	           }
	        };
	
	        private DatePickerDialog.OnDateSetListener endpickerListener = new DatePickerDialog.OnDateSetListener() {
	   		 
		        // when dialog box is closed, below method will be called.
		        @Override
		        public void onDateSet(DatePicker view, int selectedYear,
		                int selectedMonth, int selectedDay) {
		        	  year  = selectedYear;
			            month = selectedMonth;
			            day   = selectedDay;
			 
		 
		            // Show selected date 
		            txt_endDate.setText(new StringBuilder().append(day)
		                    .append("-").append(month+1).append("-").append(year)
		                    .append(" "));
		            try{
		            	 SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
			        	   final SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy");
				            Date date1=input.parse(txt_endDate.getText().toString());
				            txt_endDate.setText(output.format(date1));
		            }catch(Exception e){
		            	e.printStackTrace();
		            }
		           
		           }
		        };
			 
			 /* (non-Javadoc)
				 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
				 */
				@Override
				public boolean onOptionsItemSelected(MenuItem item) {
					// TODO Auto-generated method stub
					  switch (item.getItemId()) {
					  case android.R.id.home:
				            // app icon in action bar clicked; go home
						  try{
				        	Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
				            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				            startActivity(intent);
				             finish();
						  }catch (Exception e) {
							// TODO: handle exception
							  e.printStackTrace();
						}
				            return true;
					 
					 
					 	 			
						    default:
							break;
					       
					           }
					return super.onOptionsItemSelected(item);
				}


}
