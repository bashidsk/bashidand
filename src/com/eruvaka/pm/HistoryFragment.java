package com.eruvaka.pm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
 

public class HistoryFragment extends Fragment implements OnClickListener{
	
	/*public static HistoryFragment newInstance(String text) {

		HistoryFragment f = new HistoryFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }*/
	TextView txtStockEntryDate;
	TextView txtEndDate;
	private int mStartDay = 0;
	private int mStartMonth = 0;
	private int mStartYear = 0;
	private int mEndDay=0;
	private int mEndMonth=0;
	private int mEndYear=0;
	 SharedPreferences ApplicationDetails;
	  	SharedPreferences.Editor ApplicationDetailsEdit;
	  	private static final int TIMEOUT_MILLISEC = 0;
	    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	    ArrayList<HashMap<String, String>> updatelist = new ArrayList<HashMap<String, String>>();
	    String timezone;
	    TableLayout td;
	    AlertDialog.Builder build;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View v = inflater.inflate(R.layout.history, container, false);
	    try{
         TextView aliasname=(TextView)v.findViewById(R.id.feederId2);
 	     aliasname.setText(ApplicationData.get_feederName());
 	    ApplicationDetails = getActivity().getSharedPreferences("com.pm",0);
		 timezone = ApplicationDetails.getString("timezone", null);
		   build = new AlertDialog.Builder(getActivity());
		 	txtStockEntryDate=(TextView)v.findViewById(R.id.txtstartdate);
			txtEndDate=(TextView)v.findViewById(R.id.txtEndDate);
			TextView load=(TextView)v.findViewById(R.id.loadbttnn);
			td=(TableLayout)v.findViewById(R.id.tblhist);
			 initializeUI();
			 intializeUI2();
			 load.setOnClickListener(this);
			 txtStockEntryDate.setOnClickListener(this);
			 txtEndDate.setOnClickListener(this);
				
				 TableLayout t1=(TableLayout)v.findViewById(R.id.tblhistdemo);
					t1.setVerticalScrollBarEnabled(true);
			   	 final LinearLayout tablerow=new LinearLayout(getActivity());
			   	  tablerow.setOrientation(LinearLayout.HORIZONTAL);
	              LinearLayout.LayoutParams paramas11 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
	                      LinearLayout.LayoutParams.WRAP_CONTENT,1);
	              tablerow.setPadding(5,5,5,5);
	              tablerow.setLayoutParams(paramas11);

			    	final TextView feederid=new TextView(getActivity());
			    	LinearLayout.LayoutParams paramas91 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
		                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
			    	feederid.setLayoutParams(paramas91);
			    	feederid.setPadding(5,0,5,0);
			    	      feederid.setText("S.Time");
			    	      feederid.setTextColor(Color.parseColor("#14648d"));
			    	      feederid.setKeyListener(null);
			    	      feederid.setTextSize(17);
				      //feedkg.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
			    	      feederid.setGravity(Gravity.START);
			    	      feederid.setFreezesText(true);
			    	      feederid.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
			    	      
			    	  	final TextView actuvalfeed=new TextView(getActivity());
			    	  	actuvalfeed.setLayoutParams(paramas91);
			    	  	actuvalfeed.setPadding(5,0,5,0);
			    	  	actuvalfeed.setText("T.F");
			    	  	actuvalfeed.setTextColor(Color.parseColor("#14648d"));
			    	  	actuvalfeed.setKeyListener(null);
			    	  	actuvalfeed.setTextSize(17);
					      //feedkg.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
			    	  	actuvalfeed.setGravity(Gravity.CENTER);
			    	  	actuvalfeed.setFreezesText(true);
			    	  	actuvalfeed.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);  
			    	 
			    	  	final TextView modifiedfeed=new TextView(getActivity());
			    	  	modifiedfeed.setLayoutParams(paramas91);
			    	  	modifiedfeed.setPadding(5,0,5,0);
			    	  	modifiedfeed.setText("D.F");
			    	  	modifiedfeed.setTextColor(Color.parseColor("#14648d"));
			    	  	modifiedfeed.setKeyListener(null);
			    	  	modifiedfeed.setTextSize(17);
					      //feedkg.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
			    	  	modifiedfeed.setGravity(Gravity.CENTER);
			    	  	modifiedfeed.setFreezesText(true);
			    	  	modifiedfeed.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI); 

			    	tablerow.addView(feederid); 
			       	tablerow.addView(actuvalfeed);
			       
			        tablerow.addView(modifiedfeed);	
			         t1.addView(tablerow);	
			         View v2=new View(getActivity());
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
				        TextView tab_text_bottom=(TextView)v.findViewById(R.id.tab_text_bottom);
				       tab_text_bottom.setText("S.Time = Schedules Time,T.F = Total Feed,  D.F = Dispensed Feed");
			 }catch(Exception e){
				 e.printStackTrace();
			 }
	    return v;
	}
	private void initializeUI() {
		 		
    	Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone(timezone));
		//DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z"); 
		DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");    
		formatter.setTimeZone(TimeZone.getTimeZone(timezone));  
	    txtStockEntryDate.setText(formatter.format(calendar.getTime()));
	 	   
}
	private void intializeUI2() {
		// TODO Auto-generated method stub
		Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone(timezone));
		//DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");    
		DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");    
		formatter.setTimeZone(TimeZone.getTimeZone(timezone));  
	  	txtEndDate.setText(formatter.format(calendar.getTime()));
		 
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
        case R.id.txtstartdate:
        	startdialog();
        	break;
        case R.id.txtEndDate:
        	enddialog();
              break;
        case R.id.loadbttnn:
        	/* try{
				 updatelist.clear();
				 mylist.clear();
				 
					final Calendar start = Calendar.getInstance();
					start.set(mStartYear, mStartMonth, mStartDay, 0, 0, 0);
					final Calendar end_date = Calendar.getInstance();
					end_date.set(mEndYear, mEndMonth, mEndDay, 0, 0, 0);
				 
					final Calendar end = Calendar.getInstance();
				 	final Date date2 = new Date(end.getTimeInMillis()-(24*60*60*1000));
				 	final Date Sdate = new Date(start.getTimeInMillis());
				 	final Date Edate = new Date(end_date.getTimeInMillis());
				 	
					/*if(Sdate.after(date2)){
						Toast.makeText(getActivity(), "Future Dates are Not Allowed", Toast.LENGTH_SHORT).show();
					}else if(Edate.after(date2)){
						Toast.makeText(getActivity(), "Future Dates are Not Allowed", Toast.LENGTH_SHORT).show();
					}else{
						
					} 
					isInternetAvailable();
				}catch(Exception e){
					e.printStackTrace();
				 
				}*/
        	 try{
        		//current date
        		 Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone(timezone));
				 DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");    
					formatter.setTimeZone(TimeZone.getTimeZone(timezone));  
				   String cdate=formatter.format(calendar.getTime());
				   //from and to date
        	 final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
			  dateFormat.setTimeZone(TimeZone.getTimeZone(timezone)); 
			  String from_date=txtStockEntryDate.getText().toString().trim();
			  String to_date=txtEndDate.getText().toString().trim();
				 Date f_date = dateFormat.parse(from_date);
				 Date t_date = dateFormat.parse(to_date);
				 Date c_date=dateFormat.parse(cdate);
				 SimpleDateFormat format2  = new SimpleDateFormat("yyyy-MM-dd");
				 format2.setTimeZone(TimeZone.getTimeZone(timezone)); 
				 //String str_from_date=(format2.format(f_date)).toString().trim();
				 //String str_to_date=(format2.format(t_date)).toString().trim();
				
				 if(f_date.after(c_date)){
					 Toast.makeText(getActivity(), "Future Date are Not Allowed in Start Date", Toast.LENGTH_SHORT).show();
				 }else if(t_date.after(c_date)){
					 Toast.makeText(getActivity(), "Future Date  are Not Allowed in End Date", Toast.LENGTH_SHORT).show();
				 }else if(f_date.after(t_date)){
					 Toast.makeText(getActivity(), "End Date is Greater than Start Date", Toast.LENGTH_SHORT).show();
				 }else{
					 isInternetAvailable();
				 }
				 
        	 }catch (Exception e) {
				// TODO: handle exception
        		 e.printStackTrace();
			}
        	break;
		}
	}
	private void startdialog() {
		  DatePickerStartFragment date = new DatePickerStartFragment();
		  try{
				  final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
				  dateFormat.setTimeZone(TimeZone.getTimeZone(timezone)); 
				  String from_date=txtStockEntryDate.getText().toString().trim();
					 Date f_date = dateFormat.parse(from_date);
					 SimpleDateFormat format2  = new SimpleDateFormat("dd-MM-yyyy");
					 format2.setTimeZone(TimeZone.getTimeZone(timezone));
					 String str_date=(format2.format(f_date)).toString().trim();
				   String[] split = str_date.split("-");
				   final int day = Integer.valueOf(split[0]);
				   final  int month = Integer.valueOf(split[1]);
				   final  int year = Integer.valueOf(split[2]);
				   Bundle args = new Bundle();
					args.putInt("year", year);
					args.putInt("month", month-1);
					args.putInt("day", day);
				    date.setArguments(args);
						 
				  }catch(Exception e){
					  e.printStackTrace();
				  }
				
			  /**
			   * Set Call back to capture selected date
			   */
			  date.setCallBack(ondate);
			  date.show(getChildFragmentManager(), "Date Picker");
		  
		   
		 }
	 OnDateSetListener ondate = new OnDateSetListener() {
		  @Override
		  public void onDateSet(DatePicker view, int selectedYear, int selectedMonth,int selectedDay) {
			  final Calendar c = new GregorianCalendar(TimeZone.getTimeZone(timezone));
				 c.set(selectedYear, selectedMonth, selectedDay);
				mStartDay = selectedDay;
				mStartMonth = selectedMonth;
				mStartYear = selectedYear;
				if (txtStockEntryDate != null) {
					final Date date = new Date(c.getTimeInMillis());
					//SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
					SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
					sdf.setTimeZone(TimeZone.getTimeZone(timezone)); 
				 	txtStockEntryDate.setText(sdf.format(date));
				}
			  }
		 };
		//end date
		 private void enddialog() {
			  DatePickerEndFragment enddate = new DatePickerEndFragment();
			  /**
			   * Set Up Current Date Into dialog
			   */
			  try{
				   
				  final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
				  dateFormat.setTimeZone(TimeZone.getTimeZone(timezone)); 
				  String from_date=txtEndDate.getText().toString().trim();
					 Date f_date = dateFormat.parse(from_date);
					 SimpleDateFormat format2  = new SimpleDateFormat("dd-MM-yyyy");
					 format2.setTimeZone(TimeZone.getTimeZone(timezone)); 
					 String str_date=(format2.format(f_date)).toString().trim();
				  String[] split = str_date.split("-");
				   final int day = Integer.valueOf(split[0]);
				   final  int month = Integer.valueOf(split[1]);
				   final  int year = Integer.valueOf(split[2]);
				   Bundle args = new Bundle();
					args.putInt("year", year);
					args.putInt("month", month-1);
					args.putInt("day", day);
					enddate.setArguments(args);
				  }catch(Exception e){
					  e.printStackTrace();
				  }
				
			  /**
			   * Set Call back to capture selected date
			   */
			  enddate.setCallBack(ondate1);
			  enddate.show(getChildFragmentManager(), "Date Picker");
			 }
		//end date
		 OnDateSetListener ondate1 = new OnDateSetListener() {
			  @Override
			  public void onDateSet(DatePicker view, int selectedYear, int selectedMonth,int selectedDay) {
			         final Calendar c = new GregorianCalendar(TimeZone.getTimeZone(timezone));
					 c.set(selectedYear, selectedMonth, selectedDay);
					 mEndDay = selectedDay;
					 mEndMonth = selectedMonth;
					 mEndYear = selectedYear;
					if (txtEndDate != null) {
						final Date date = new Date(c.getTimeInMillis());
						//SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
						SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
						sdf.setTimeZone(TimeZone.getTimeZone(timezone)); 
						txtEndDate.setText(sdf.format(date));
					 	 
						}
			  }
			 };
			 protected boolean    isInternetAvailable() {
					// TODO Auto-generated method stub
					ConnectivityManager cm =(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
				     NetworkInfo netInfo = cm.getActiveNetworkInfo();
				       if (netInfo != null && netInfo.isConnectedOrConnecting()) {
				       	try{
				              mylist.clear();
				              updatelist.clear();
				           	  td.setVerticalScrollBarEnabled(true);
						   	  td.removeAllViewsInLayout();
						       new History().execute();
				    	      }catch(Exception e){
		      	  			   e.printStackTrace();
		      	  		       }	
							  
						        return true;		        
				               }else{ 
				    	  	Toast.makeText(getActivity(), R.string.internet, Toast.LENGTH_SHORT).show();					
				    }
				    return false;
					}
			 public class History extends AsyncTask<String, Void, Void> {		
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
								 
								build.setMessage(error);
								build.setNegativeButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int which) {
												 td.setVerticalScrollBarEnabled(true);
											   	  td.removeAllViewsInLayout();
												dialog.cancel();
											}
										});
								build.show();
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
					 Toast.makeText(getActivity(), "no response from server please try again", Toast.LENGTH_SHORT).show();
				 }
								
				}
			@Override
				protected Void doInBackground(String... params) {
					// TODO Auto-generated method stub
					
						try {					
							//store loginpasswd in sharedpreference
						 
							 final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
							  dateFormat.setTimeZone(TimeZone.getTimeZone(timezone)); 
							  String from_date=txtStockEntryDate.getText().toString().trim();
							  String to_date=txtEndDate.getText().toString().trim();
								 Date f_date = dateFormat.parse(from_date);
								 Date t_date = dateFormat.parse(to_date);
								 SimpleDateFormat format2  = new SimpleDateFormat("yyyy-MM-dd");
								 format2.setTimeZone(TimeZone.getTimeZone(timezone)); 
								 String startdate=(format2.format(f_date)).toString().trim();
								 String enddate=(format2.format(t_date)).toString().trim();
							 
							  
							 
					         	     String user_id = ApplicationDetails.getString("user_id", null);
					         	 String feederSno=   ApplicationData.get_feederSno().toString().trim();
						 
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
						    DefaultHttpClient httpclient = new MyHttpsClient(httpParams,getActivity());
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
				public void updated(){
					try{
						td.setVerticalScrollBarEnabled(true);
						td.removeAllViewsInLayout();
						 
					   	 for(int i=0; i<mylist.size(); i++) {
					   		 final HashMap<String, String> map = mylist.get(i);
					   		  
					   		 
					   		 final TextView datetv=new TextView(getActivity());
					   		 LinearLayout.LayoutParams paramas41 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
					   		datetv.setLayoutParams(paramas41);
							    datetv.setTextColor(Color.parseColor("#14648d"));
							    datetv.setKeyListener(null);
							    datetv.setTextSize(16);
							    
						        //feedkg.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
							    datetv.setGravity(Gravity.START);
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
						       // td.addView(datetv);
					   	  		 
					   		 
						        float a=0,b=0; 
					   	    final String schedules=map.get("schedules").toString().trim();
					   	 JSONArray jsonArray = new JSONArray(schedules);
					 	for (int j = 0; j < jsonArray.length(); j++) {
					 		JSONObject jObject = jsonArray.getJSONObject(j);
			  			 		  			
			  				final String dispensed_feed=jObject.getString("dispensed_feed");
			  				final String schedule_times=jObject.getString("schedule_times");
			  				final String original_feed=jObject.getString("original_feed");
			  				
		      				 final LinearLayout tablerow1=new LinearLayout(getActivity());
		      				 tablerow1.setOrientation(LinearLayout.HORIZONTAL);
				              LinearLayout.LayoutParams paramas11 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				                      LinearLayout.LayoutParams.WRAP_CONTENT,1);
				              tablerow1.setPadding(5,0,5,0);
				              tablerow1.setLayoutParams(paramas11);
						        		  
					      		      try{
					      		    	final TextView fromtime1=new TextView(getActivity());
							       		 LinearLayout.LayoutParams paramas1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
							                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
							       		fromtime1.setLayoutParams(paramas1);
							       		fromtime1.setPadding(5,0,5,0);
							       		String sp[]=schedule_times.split("-");
										 String sp1=sp[0];
										 String sp2=sp[1];
										 String tsp=sp1+" - "+sp2;
					      		      String schedule=(tsp).toString().trim();
					      		      fromtime1.setText(schedule);
					      		      fromtime1.setKeyListener(null);
					      		      fromtime1.setTextColor(Color.BLACK);
					      		      fromtime1.setTextSize(15);
									  fromtime1.setGravity(Gravity.START);
					      		      fromtime1.setFreezesText(true);
					      		      fromtime1.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
					      		         
					      		     
					      		      
					      		 
					      		    	final TextView actuval_feed=new TextView(getActivity());
					      		    	 actuval_feed.setPadding(5,0,5,0);
					      		    	actuval_feed.setLayoutParams(paramas1);
										   float actuval_float=Float.parseFloat(original_feed);
										   String str1_original_feed=  String.format("%.2f", actuval_float);
										   actuval_feed.setText(str1_original_feed);
										   actuval_feed.setTextColor(Color.BLACK);
										   actuval_feed.setKeyListener(null);
										   actuval_feed.setTextSize(15);
										   //feedkg.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
										   actuval_feed.setGravity(Gravity.CENTER);
										   actuval_feed.setFreezesText(true);
										   actuval_feed.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
					      		  				      			 
									   
									   final TextView disfeedkg=new TextView(getActivity());
									  
									   float dispensed_float=Float.parseFloat(dispensed_feed);
									   String str1_dispensed_feed=  String.format("%.2f", dispensed_float);
									   disfeedkg.setText(str1_dispensed_feed);
									   disfeedkg.setPadding(5,0,5,0);
									   disfeedkg.setLayoutParams(paramas1);
									   disfeedkg.setTextColor(Color.BLACK);
									   disfeedkg.setKeyListener(null);
									   disfeedkg.setTextSize(15);
									  
									   disfeedkg.setGravity(Gravity.CENTER);
									   disfeedkg.setFreezesText(true);
									   disfeedkg.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);	
									   
							       		tablerow1.addView(fromtime1);
							       		tablerow1.addView(actuval_feed);
							       		 
							       		tablerow1.addView(disfeedkg);
							       	
							       		td.addView(tablerow1);
							       		try{
							       	    	
								       	    float flfeed=Float.parseFloat(original_feed);
								       	          a+=flfeed;
								       	       //int feedint=Math.round(a);
								       	       //int feedint=Math.round(a);
								       	       String str1=  String.format("%.2f", a);
								       	   // String str= String.format("%.2f", s1);
								       	        ApplicationData.addfeedtotal(str1);
								       	        
								       	    float dsfeed=Float.parseFloat(dispensed_feed);
								       	         b+=dsfeed;
								       	         //int dsfeedint=Math.round(b);
								       	        // String str2=Integer.toString(dsfeedint);
								       	    String str2=  String.format("%.2f", b);
								       	      //String s2=Float.toString(b);
								       	     //String str2= String.format("%.2f", s2);
								       	         ApplicationData.adddisfeedtotal(str2);
								       	         
								       	       
								       	    }catch(Exception e){
								       	    	
								       	    }
							       		
							       	
					      		    }catch(Exception e){
					      		    	  e.printStackTrace();
					      		      }
					 	}
						try{
				       		   final LinearLayout tablerow3=new LinearLayout(getActivity());	
				       		 tablerow3.setOrientation(LinearLayout.HORIZONTAL);
				              LinearLayout.LayoutParams paramas31 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				                      LinearLayout.LayoutParams.WRAP_CONTENT,1);
				              tablerow3.setPadding(5,0,5,0);
				               tablerow3.setLayoutParams(paramas31);
				               
				              final TextView totalfeed=new TextView(getActivity());
				              LinearLayout.LayoutParams paramas1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
				               totalfeed.setLayoutParams(paramas1);
								String str1=ApplicationData.getfeedtotal().toString().trim();
							 	totalfeed.setText(str1); 
							 	totalfeed.setPadding(5,0,5,0);
							 	totalfeed.setLayoutParams(paramas1);
							 	totalfeed.setTextColor(Color.parseColor("#368306"));
							 	totalfeed.setKeyListener(null);
							 	totalfeed.setTextSize(16);
							 	totalfeed.setGravity(Gravity.CENTER);
							 	totalfeed.setFreezesText(true);
							 	totalfeed.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
								
								final TextView dispensefeedtotal=new TextView(getActivity());
								dispensefeedtotal.setLayoutParams(paramas1);
								String str11=ApplicationData.getdisfeedtotal().toString().trim();
							    dispensefeedtotal.setText(str11); 
							    dispensefeedtotal.setPadding(5,0,5,0);
							    dispensefeedtotal.setLayoutParams(paramas1);
							    dispensefeedtotal.setTextColor(Color.parseColor("#368306"));
							    dispensefeedtotal.setKeyListener(null);
							    dispensefeedtotal.setTextSize(16);
								dispensefeedtotal.setGravity(Gravity.CENTER);
							    dispensefeedtotal.setFreezesText(true);
							    dispensefeedtotal.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);	
															
								
								tablerow3.addView(datetv);
								tablerow3.addView(totalfeed);
								tablerow3.addView(dispensefeedtotal);
								td.addView(tablerow3);
				       		}catch(Exception e){
				       			e.printStackTrace();
				       		}
					 	
					    View v=new View(getActivity());
		                v.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		                v.setBackgroundResource(R.drawable.seprator2);
		                td.addView(v);
					   	 }
					
					}catch(Exception e){
						e.printStackTrace();
					}
				}
}
