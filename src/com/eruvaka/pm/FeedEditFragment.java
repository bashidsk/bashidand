package com.eruvaka.pm;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.apache.http.impl.io.SocketOutputBuffer;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncStatusObserver;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
 

public class FeedEditFragment extends Fragment implements View.OnClickListener {
	
	android.support.v7.app.ActionBar bar;
	
	/*public static FeedEditFragment newInstance(String text) {

    	FeedEditFragment f = new FeedEditFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }*/
	private int mStartDay = 0;
	private int mStartMonth = 0;
	private int mStartYear = 0;
	private int mEndDay = 0;
	private int mEndMonth = 0;
	private int mEndYear = 0;
	ActionBar actionbar;
	SharedPreferences ApplicationDetails;
  	SharedPreferences.Editor ApplicationDetailsEdit;
  	private static final int TIMEOUT_MILLISEC = 0;
  	View v;
  	EditText totalfeed,ocf,feedgap,okdtime,total_time,dispense_feed_ed;
  	TextView tvstatus;
  	ImageButton start,pause,stop;
    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> mylist_schedule = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> update_list = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> send_list = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> send_list2 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> add_mylist = new ArrayList<HashMap<String, String>>();
    ArrayList<String> total_feed_array=new ArrayList<String>(); 
    ArrayList<String> original_feed_array=new ArrayList<String>();
    ArrayList<String> one_cycle_feed_array=new ArrayList<String>();
    ArrayList<String> feed_gap_array=new ArrayList<String>();
    ArrayList<String> status_array=new ArrayList<String>();
    ArrayList<String> kg_feed_disp_time_array=new ArrayList<String>();
    ArrayList<String> schedule_id_array=new ArrayList<String>();
    ArrayList<String> totalTime_array=new ArrayList<String>();
    ArrayList<String> mode_array=new ArrayList<String>();
    ArrayList<String> fromTime_array=new ArrayList<String>();
    ArrayList<String> toTime_array=new ArrayList<String>();
    ArrayList<String> onTime_array=new ArrayList<String>();
    ArrayList<String> offTime_array=new ArrayList<String>();
    ArrayList<String> check_array=new ArrayList<String>();
    TableLayout t1,demo;
    ArrayList<HashMap<String, String>> get_mylist = new ArrayList<HashMap<String, String>>();
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int min;
    LinearLayout liner,liner_table;
    Button schedule_update,basic_update;
    String timezone;
    AlertDialog.Builder build;
    TextView tv,start_date,end_date,last_update_time_tv;
    
    ArrayList<Integer> ar=new ArrayList<Integer>();
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 try{ 
	         v = inflater.inflate(R.layout.dialog, container, false);
	            ApplicationDetails = getActivity().getSharedPreferences("com.pm",0);
	            timezone= ApplicationDetails.getString("timezone", null); 
	             mylist = (ArrayList<HashMap<String, String>>)getActivity().getIntent().getSerializableExtra("arraylist");
	             build = new AlertDialog.Builder(getActivity());
	            update_list.clear();
	            add_mylist.clear();
				get_mylist.clear();
				start_date=(TextView)v.findViewById(R.id.date_popup);
				end_date=(TextView)v.findViewById(R.id.end_date);
				//date intilize into textview
				createDate();
				createDate2();
				
	                liner=(LinearLayout)v.findViewById(R.id.liner1);
	                liner_table=(LinearLayout)v.findViewById(R.id.liner_table);
	                last_update_time_tv=(TextView)v.findViewById(R.id.last_update_time);
	                t1=(TableLayout)v.findViewById(R.id.updatetbl1);
	                demo=(TableLayout)v.findViewById(R.id.updatehome);
	           	    totalfeed=(EditText)v.findViewById(R.id.totalfeed);
	        	    totalfeed.setSelection(totalfeed.getText().length());
	        	    ocf=(EditText)v.findViewById(R.id.ocf);
	        	    ocf.setSelection(ocf.getText().length());
	        	    feedgap=(EditText)v.findViewById(R.id.feedgap);
	        	    feedgap.setSelection(feedgap.getText().length());
		  			okdtime=(EditText)v.findViewById(R.id.okdtime);
		  			total_time=(EditText)v.findViewById(R.id.total_time);
		  			total_time.setSelection(total_time.getText().length());
		  			total_time.setKeyListener(null);
		  			dispense_feed_ed=(EditText)v.findViewById(R.id.dispensedfeed);
		  			dispense_feed_ed.setSelection(total_time.getText().length());
		  			dispense_feed_ed.setKeyListener(null);
		  			tvstatus=(TextView)v.findViewById(R.id.tvstatus);
		  			start=(ImageButton)v.findViewById(R.id.start);
		  		  	pause=(ImageButton)v.findViewById(R.id.pause);
		  			stop=(ImageButton)v.findViewById(R.id.stop);
		  		   //String feederSno=   ApplicationData.get_feederSno().toString().trim();
	        	   String FeederName=ApplicationData.get_feederName().toString().trim();
	        	   TextView aliasname=(TextView)v.findViewById(R.id.feederId1);
	        	   aliasname.setText(FeederName);
	          	    schedule_update=(Button)v.findViewById(R.id.update_tbl); 
	  	            basic_update=(Button)v.findViewById(R.id.update);
	  	            schedule_update.setOnClickListener(this);
	  	            basic_update.setOnClickListener(this);
  		             tv=(TextView)v.findViewById(R.id.tab_text_bottom);
  		            tv.setText("T.F = Total Feed(k.g), O.C.F = One Cycle Feed(gms), F.G = Feed Gap(min)");
  		          start_date.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						startdialog();
					}
				});
                 end_date.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						enddialog();
					}
				}); 
  		     
	           }catch(Exception e){
	        	   e.printStackTrace();
	           }
	           
	           						 try{
	           			  		        //display data on schedules
	           			  	          String m_mode=ApplicationData.get_compare_mode().toString().trim();
	           				  	         String mode1="78".toString().trim();
	           				  	         String mode2="79".toString().trim();
	           				  	         
	           				  	         //display schedule mode and basic mode
	           				  	          if(m_mode.equals(mode1)){
	           				  	        	  liner.setVisibility(View.VISIBLE);
	           				  	        	  liner_table.setVisibility(View.GONE);
	           				  	            //Basic Mode
	     	           						 basicMode(); 
	           				  	          }else if(m_mode.equals(mode2)){
	           				  	        	  liner.setVisibility(View.GONE);
	           				  	        	  liner_table.setVisibility(View.VISIBLE);
	           				  	        	  //SChedule Mode
	           				  	        	   DisplayShow();
	           				  	        	  scheduleMode();
	           				  	   	        tv.setVisibility(View.VISIBLE);
	           				  	          }else{
	           				  	           	build.setTitle("Mode");
	           				  	        	build.setMessage("Mode is Empty , Please Set Mode in Settings Page");
	           								build.setPositiveButton("OK",
	           										new DialogInterface.OnClickListener() {
	           											public void onClick(
	           													DialogInterface dialog,
	           													int which) {
	           												dialog.cancel();
	           												 
	           												
	           											}
	           										});
	           								build.show();   
	           				  	          }
	           							           			  		          
	           			  		           }catch(Exception e){
	           			  		        	   e.printStackTrace();
	           			  		           }
	                               
	           						add_mylist.clear();
	           						get_mylist.clear();
	           						ApplicationData.add_remove_list(add_mylist);
	        TextView date_load=(TextView)v.findViewById(R.id.date_load);
	        date_load.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(isInternetAvailable()){
						String user_id = ApplicationDetails.getString("user_id", null);
					    String feederSno=   ApplicationData.get_feederSno().toString().trim();
					      
						if(user_id.isEmpty()||feederSno.isEmpty()){
						Toast.makeText(getActivity(), R.string.nullvalues, Toast.LENGTH_SHORT).show();	
						}else{
							   
								try{ 
								final Calendar end = Calendar.getInstance();
							 	final Date date2 = new Date(end.getTimeInMillis()-(24*60*60*1000));
							 	SimpleDateFormat dfDate  = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
					 	          dfDate.setTimeZone(TimeZone.getTimeZone(timezone));
						   String str_date1=start_date.getText().toString().trim();
						    String str_date2=end_date.getText().toString().trim();
						    
						    java.util.Date  d1 = dfDate.parse(str_date1);
							 java.util.Date  d2 = dfDate.parse(str_date2);
							 	
								if(d1.before(date2)||d2.before(date2)) {
							  			Toast.makeText(getActivity(), R.string.pastdate, Toast.LENGTH_SHORT).show();
							  	 } else if(d2.before(d1)){
							  		Toast.makeText(getActivity(), "Schedule End Date Should be greater than the Start date", Toast.LENGTH_SHORT).show();
							  	 }else{
							  		 
							  		new GetSingleFeederData().execute();
							  	 }
								}catch(Exception e){
									e.printStackTrace();
								}
							
						}
					}
				}
			});
	           						 
	           					          						
	        return v;
	    }
	 
	 
	  
		 
	 @Override
	    public void onClick(View view) {
	        switch (view.getId()) {
	        case R.id.update_tbl:
	        	//schedule mode
	        	onSaveClick();
	             	
	        	break;
	        case R.id.update:
				// TODO Auto-generated method stub
	        	//basic mode
				if(isInternetAvailable()){
					  send_list2.clear();
					  HashMap<String, String> map1=new HashMap<String,String>();
					 for(int i=0; i<send_list.size(); i++) {
				   		 HashMap<String, String> map = send_list.get(i);
				   		 final String schedule_id=map.get("schedule_id").toString().trim();
				   		 final String totalTime=map.get("totalTime").toString().trim();
				   	     final String fromTime=map.get("fromTime").toString().trim();
				   	     final String to_time=map.get("to_time").toString().trim();
				   		 final String mode=map.get("mode").toString().trim();
				   	   	       map1.put("schedule_id", schedule_id);
						       map1.put("totalTime", totalTime);
						       map1.put("fromTime", fromTime);
						       map1.put("to_time", to_time);
						       map1.put("mode", mode);
						      
				   		 }
					 					   
 					 try{
 						String status=tvstatus.getText().toString().trim();
 						String tfeed=totalfeed.getText().toString().trim();
					    String ocfeed=ocf.getText().toString().trim();
					    String fgp=feedgap.getText().toString().trim();
					    String dispensedfeed=okdtime.getText().toString().trim();
					   
					    	 final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
							  dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
								 Date f_date = dateFormat.parse(start_date.getText().toString());
								 Date t_date = dateFormat.parse(end_date.getText().toString());
								 SimpleDateFormat format2  = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
								 format2.setTimeZone(TimeZone.getTimeZone(timezone)); 
								 String schedule_start_date = format2.format(f_date);
								 String schedule_end_date = format2.format(t_date);
								 map1.put("schedule_date", schedule_start_date);
								 map1.put("schedule_end_date", schedule_end_date);
					  	   							 
		  			    float tf = 0;
					    float df = 0;
					    String zero="0".toString().trim();
					    try{
					      tf=Float.parseFloat(tfeed);
					      df=Float.parseFloat(dispensedfeed);
					    }catch(Exception e){
					    	e.printStackTrace();
					    } 
					    
					    /* final Calendar start = Calendar.getInstance();
						start.set(mStartYear, mStartMonth, mStartDay, 0, 0, 0);
						 final Calendar end_d = Calendar.getInstance();
						 end_d.set(mEndYear, mEndMonth, mEndDay, 0, 0, 0);
						 
						
					 	
					 	//final Date start_date_date = new Date(start.getTimeInMillis());
					 	//final Date end_date_date= new Date(end_d.getTimeInMillis());*/
					 	
					 final Calendar end = Calendar.getInstance();
					 	final Date date2 = new Date(end.getTimeInMillis()-(24*60*60*1000));
					 	 SimpleDateFormat dfDate  = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
					 	          dfDate.setTimeZone(TimeZone.getTimeZone(timezone));
						   String str_date1=start_date.getText().toString().trim();
						    String str_date2=end_date.getText().toString().trim();
						    
						    java.util.Date  d1 = dfDate.parse(str_date1);
							 java.util.Date  d2 = dfDate.parse(str_date2);
							 String current_date = dfDate.format(System.currentTimeMillis());
							 int diffInDays = (int)((d1.getTime() - d2.getTime())/ (1000 * 60 * 60 * 24));
					 	
					 	    System.out.println(diffInDays);
					 	   System.out.println(current_date);
					 	    
					  	if(tfeed.isEmpty()||ocfeed.isEmpty()||fgp.isEmpty()||tfeed.equals(zero)||ocfeed.equals(zero)||fgp.equals(zero)){
					  			Toast.makeText(getActivity(), R.string.nullvalues, Toast.LENGTH_SHORT).show();
					  	 }else if(tf<df){
					  			Toast.makeText(getActivity(), R.string.dispensedfeed, Toast.LENGTH_SHORT).show();
					  	 }else if(d1.before(date2)||d2.before(date2)) {
					  			Toast.makeText(getActivity(), R.string.pastdate, Toast.LENGTH_SHORT).show();
					  	 }else if(d2.before(d1)){
					  		Toast.makeText(getActivity(), "Schedule End Date Should be greater than the Start date", Toast.LENGTH_SHORT).show();
					  	 }else if(diffInDays==0) {
					  		   map1.put("tfeed", tfeed);
						       map1.put("ocfeed", ocfeed);
						       map1.put("fgp", fgp);
						       map1.put("status", status);
						        send_list2.add(map1);
										        
						      if(send_list2.size()>0){
						    	  if(isInternetAvailable()){
						    		  new Update().execute();  
						    		  System.out.println("ok");
						    	  }
						    	 
						      }
	 					 	  
					  		}else if(str_date1.equals(current_date)){
					  			Toast.makeText(getActivity(), "Please don't select current date in the Date Range", Toast.LENGTH_SHORT).show();
					  		}else{
					  			 System.out.println("ok");
					  			   map1.put("tfeed", tfeed);
							       map1.put("ocfeed", ocfeed);
							       map1.put("fgp", fgp);
							       map1.put("status", status);
							        send_list2.add(map1);
											        
							      if(send_list2.size()>0){
							    	  if(isInternetAvailable()){
							    		  new Update().execute();  
							    	  }
							    	 
							      }
					  		}
					     
 							
       					 }catch(Exception e){
       						 e.printStackTrace();
       					 }
 				}
			
	              break;
	       
	        }
	    } 
	 
	 private boolean update_Schedules(){
			 
			if(update_list.size()>0){
				 
				for(int j=0;j<update_list.size();j++) {
					
					for(int x=j+1; x<update_list.size();x++){
						
						final HashMap<String, String> map1 = update_list.get(j);
						final HashMap<String, String> map2 = update_list.get(x);
					    String from_time_str= map1.get("from_time").toString().trim();
						String to_time_str= map1.get("to_time").toString().trim();
					    String from_time_str2= map2.get("from_time").toString().trim();
						   
						if (compareValues(from_time_str, to_time_str) == true){ 
						     //check schedules fromtime ovelapping 
							Toast.makeText(getActivity(), "Please Check Schedule"+(j+1) +"Timings!!!", Toast.LENGTH_SHORT).show();
							return false;
						} 
						
						if (compareValues(from_time_str, from_time_str2) == true){ 
							 // fromtime with fromtime overlapping 
							Toast.makeText(getActivity(), "Schedule  "+(x+1)+" FromTime OverLapping With Schedule "+(j+1)+" From Time !!!", Toast.LENGTH_SHORT).show();
							return false;
						} 
						if (compareValues(to_time_str, from_time_str2) == true){ 
							 // fromtime with totime overlapping  
							Toast.makeText(getActivity(), "Schedule  "+(x+1)+"  From Time OverLapping With Schedule "+(j+1)+" To Time !!!", Toast.LENGTH_SHORT).show();
							return false;
						} 
						
						
					}
					
				 

				}
			}
			return true;
		}
	 private boolean onSaveClick() {
			total_feed_array.clear();
		    one_cycle_feed_array.clear();
		    feed_gap_array.clear();
		    status_array.clear();
		    schedule_id_array.clear();
		    totalTime_array.clear(); 
		    mode_array.clear();
		    fromTime_array.clear();
		    toTime_array.clear();
		    onTime_array.clear();
		    offTime_array.clear();
		    
    	if(isInternetAvailable()){
			if(update_list.size()>0){
				try{
					
				
				ArrayList<String> error=new ArrayList<String>();
				 
				 for(int i=0; i<update_list.size(); i++) {
			   		 HashMap<String, String> map = update_list.get(i);
			   		String schedule_id= map.get("schedule_id").toString().trim();
				    String from_time= map.get("from_time").toString().trim();
				    String to_time= map.get("to_time").toString().trim();
				    String totalTime=map.get("totalTime").toString().trim();
				    //String kg_feed_disp_time=map.get("kg_feed_disp_time").toString().trim();
				    String mode=map.get("mode").toString().trim();
				    String status=map.get("status").toString().trim();
				   // String dispensed_feed=map.get("dispensed_feed").toString().trim();  
				    String total_feed=map.get("total_feed").toString().trim();
				    String ocf=map.get("ocf").toString().trim();
				    String fg=map.get("fg").toString().trim();
				    System.out.println(ocf);
				    if(from_time.isEmpty()||from_time.equals("0")||to_time.isEmpty()||to_time.equals("0")||total_feed.equals("0")||total_feed.isEmpty()||ocf.equals("0")||ocf.isEmpty()||fg.isEmpty()||fg.equals("0")){
				    	 error.add("Schedule"+ (i+1));	
				    } 
				    		 
			      }//closed 
				/* final Calendar start = Calendar.getInstance();
					start.set(mStartYear, mStartMonth, mStartDay, 0, 0, 0);
					 final Calendar end_d = Calendar.getInstance();
					 end_d.set(mEndYear, mEndMonth, mEndDay, 0, 0, 0);
					 
					
				 	
				 	//final Date start_date_date = new Date(start.getTimeInMillis());
				 	//final Date end_date_date= new Date(end_d.getTimeInMillis());*/
				 	
				 final Calendar end = Calendar.getInstance();
				 	final Date date2 = new Date(end.getTimeInMillis()-(24*60*60*1000));
				 	 SimpleDateFormat dfDate  = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
				 	          dfDate.setTimeZone(TimeZone.getTimeZone(timezone));
					   String str_date1=start_date.getText().toString().trim();
					    String str_date2=end_date.getText().toString().trim();
					    
					    java.util.Date  d1 = dfDate.parse(str_date1);
						 java.util.Date  d2 = dfDate.parse(str_date2);
						 String current_date = dfDate.format(System.currentTimeMillis());
						 int diffInDays = (int)((d1.getTime() - d2.getTime())/ (1000 * 60 * 60 * 24));
						 
				 
				 if(error.size()>0){
				 Toast.makeText(getActivity(), "Empty or Zero Values not Allowed "+" in "+error.get(0), Toast.LENGTH_SHORT).show();
				 } else if(d1.before(date2)||d2.before(date2)) {
			  			Toast.makeText(getActivity(), R.string.pastdate, Toast.LENGTH_SHORT).show();
				 } else if(d2.before(d1)){
				  		Toast.makeText(getActivity(), "Schedule End Date Should be greater than the Start date", Toast.LENGTH_SHORT).show();
				 }else if(diffInDays==0) {
					 if(update_Schedules()){
						  System.out.println(update_list);
						new Update2().execute();
					 }
				 }else if(str_date1.equals(current_date)){
					 Toast.makeText(getActivity(), "Please don't select current date in the Date Range", Toast.LENGTH_SHORT).show();
								
				 }else{
					 if(update_Schedules()){
						  System.out.println(update_list); 
						new Update2().execute();
					 }
				 }
				 
				 
				 
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			
			}
			return true;
		  
		}
	 
	 
		boolean compareValues(String fromTime, String toTime)
		{
			try {
				final SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");
				timeformat.setTimeZone(TimeZone.getTimeZone(timezone)); 
				Date time1 = timeformat.parse(fromTime);
				int fromhour = time1.getHours();
				int fromminute = time1.getMinutes();
				Date time2 = timeformat.parse(toTime);
				int tohour = time2.getHours();
				int tominute = time2.getMinutes();
				//Log.e(TAG, "compareValues:: fromhour:"+fromhour + ":fromminute:"+fromminute+":tohour:"+tohour+":tominute:"+tominute);

				// do not comment on this time and accept it as correct time. Here Toast message is not required to show. so return false.
				if ((23 == tohour) && (59 == tominute) && (fromhour == 23) && (fromminute == 59))
				{
					return false;
				}

				// wrong time. FromHour > ToHour. return true to show Toast message to user.
				if (fromhour > tohour) {
					return true;
				}
				// wrong time. FromHour = ToHour, but FromMins >= ToMins. return true to show Toast message to user.
				else if ((fromhour == tohour) && (fromminute >= tominute))
				{
					return true;
				}

			} catch(Exception e)
			{
				//Log.e(TAG, "compareValues:: Exception "+e);
			}
			// correct time
			return false;
		}
	 
	 private void basicMode(){
              
			try{
				 
				//ArrayList<HashMap<String, String>> mylist = (ArrayList<HashMap<String, String>>)getActivity().getIntent().getSerializableExtra("arraylist");
				 		 
				 for(int i=0; i<mylist.size(); i++) {
					 try{
						 final HashMap<String, String> map = mylist.get(i);
						 //schedule_date
						 final String schedule_date=map.get("schedule_date").toString().trim();
					     final String kg_feed_disp_time_all=map.get("kg_feed_disp_time").toString().trim();
					     //last_update_time
					     SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
							
						  dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
							 Date f_date = dateFormat.parse(schedule_date);
							 
							 final SimpleDateFormat format2 = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
							 format2.setTimeZone(TimeZone.getTimeZone(timezone)); 
							 String schedule_last_date = format2.format(f_date);
							  String current_date = format2.format(System.currentTimeMillis());
							  
					     final String last_update_time=map.get("last_update_time").toString().trim();
					     if(last_update_time.isEmpty()){
					    	 
					     }else{
					    	/* final SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm a",Locale.getDefault());
						     timeformat.setTimeZone(TimeZone.getTimeZone(timezone));
						     Date t_time = timeformat.parse(last_update_time);
						     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm",Locale.getDefault());
							 simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
							 String schedule_last_time = simpleDateFormat.format(t_time);*/
							 
						     if(current_date.equals(schedule_last_date)){
						    	 last_update_time_tv.setText("Last Updated At: Today  "+last_update_time);
						     }else{
						    	 last_update_time_tv.setText("Last Updated At: "+schedule_last_date+" "+last_update_time); 
						     } 
					     }
					     
					     
					   	 final String modee=map.get("mode").toString().trim();
				   	     final String schedules=map.get("schedules").toString().trim();
				   	     ApplicationData.add_kg_feed_disp_Time(kg_feed_disp_time_all);
				   	     ApplicationData.add_mode(modee);
				   		 JSONArray jsonArray = new JSONArray(schedules);
				   	
				   		 if(jsonArray.length()>0){
				   			for (int j = 0; j < jsonArray.length(); j++) {
				   				JSONObject jObject = jsonArray.getJSONObject(j);
			      				final String schedule_id=jObject.getString("schedule_id");
			      				final String schedule_times=jObject.getString("schedule_times");
			      				final String totalTime=jObject.getString("totalTime");
			      				final String original_feed=jObject.getString("original_feed");
			      				final String one_cycle_feed=jObject.getString("one_cycle_feed");
			      				final String feed_gap=jObject.getString("feed_gap");
			      				final String kg_feed_disp_time=jObject.getString("kg_feed_disp_time");
			      				final String mode=jObject.getString("mode");
			      				final String status=jObject.getString("status");
			      				final String dispensed_feed=jObject.getString("dispensed_feed");
			      				dispense_feed_ed.setText(dispensed_feed);
			      				total_time.setText(totalTime);
			      				totalfeed.setText(original_feed);
			      			    totalfeed.setSelection(totalfeed.getText().length());
			      				ocf.setText(one_cycle_feed);
			      			    ocf.setSelection(ocf.getText().length());
			      				feedgap.setText(feed_gap);
			      				feedgap.setSelection(feedgap.getText().length());
			      				okdtime.setText(dispensed_feed);
			      				try{
									String tf=totalfeed.getText().toString().trim();
									String df=dispensed_feed.toString().trim();
									String cf=ocf.getText().toString().trim();
									String fg=feedgap.getText().toString().trim();
									float f_tf=Float.parseFloat(tf);
									float f_df=Float.parseFloat(df);
									float f_cf=Float.parseFloat(cf);
									float f_fg=Float.parseFloat(fg);
									float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
									float ttl_time = cycles * f_fg;
									int  tt=(int)ttl_time;
									int hours = tt / 60; 
									int minutes = tt % 60;
								 	total_time.setText(hours+" : "+minutes);
									}catch(Exception e){
										e.printStackTrace();
									}
			      				String to_be_run="to_be_run".toString().trim();
				      			String running="running".toString().trim();
				      			String changed="changed".toString().trim();
				      			String paused="paused".toString().trim();
				      			//String completed="completed".toString().trim();
				      			
			      				if(status.equals(to_be_run))	{	 			
				      				start.setBackgroundResource(R.drawable.s1);
				      				//tvstart.setText("Start");
				      				//tvpause.setVisibility(8);
				      				pause.setVisibility(8);
				      				//tvstop.setVisibility(8);
				      				stop.setVisibility(8);
				      				System.out.println("to_be -run");
				      				tvstatus.setText("running");
				      			}else if(status.equals(running)){
				      				pause.setBackgroundResource(R.drawable.p1);
				      				stop.setBackgroundResource(R.drawable.stop1);
				      				//tvpause.setText("Pause");
				      				//tvstop.setText("Stop");
				      				//tvstart.setVisibility(8);
				      				start.setVisibility(8);
				      				System.out.println("running");
				      				tvstatus.setText("running");
				      			}else if(status.equals(changed)){
				      				pause.setBackgroundResource(R.drawable.p1);
				      				stop.setBackgroundResource(R.drawable.stop1);
				      				//tvpause.setText("Pause");
				      				//tvstop.setText("Stop");
				      				//tvstart.setVisibility(8);
				      				start.setVisibility(8);
				      				System.out.println("changed");
				      				tvstatus.setText("changed");
				      			}else if(status.equals(paused)){
				      				start.setBackgroundResource(R.drawable.s1);
				      				//tvstart.setText("Start");
				      				stop.setBackgroundResource(R.drawable.stop1);
				      				//tvpause.setVisibility(8); 
				      				//tvstop.setText("Stop");
				      				pause.setVisibility(8);
				      				System.out.println("paused");
				      				tvstatus.setText("paused");
				      			}else{
				      				start.setBackgroundResource(R.drawable.s1);
				      				//tvstart.setText("Start");
				      				//tvstop.setVisibility(8);
				      				stop.setVisibility(8);
				      				//tvpause.setVisibility(8);
				      				pause.setVisibility(8);
				      				System.out.println("completed");
				      				tvstatus.setText("completed");
				      			}
			      				
			      				start.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										pause.setBackgroundResource(R.drawable.p1);
					      				stop.setBackgroundResource(R.drawable.stop1);
					      				//tvpause.setText("Pause");
					      				//tvstop.setText("Stop");
					      				stop.setVisibility(0);
					      				pause.setVisibility(0);
					      				//tvstop.setVisibility(0);
					      				//tvpause.setVisibility(0);
					      				//tvstart.setVisibility(8);
					      				start.setVisibility(8);
					      				//running
					      				System.out.println("running");
					      				tvstatus.setText("running");
									}
								});
				      			pause.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										start.setBackgroundResource(R.drawable.s1);
					      				stop.setBackgroundResource(R.drawable.stop1);
					      				//tvstart.setText("Start");
					      				//tvstop.setText("Stop");
					      				//tvstart.setVisibility(0);
					      				//tvstop.setVisibility(0);
					      				start.setVisibility(0);
					      				stop.setVisibility(0);
					      				pause.setVisibility(8);
	    				      			//tvpause.setVisibility(8);
	    				      			//paused
	    				      			System.out.println("paused");
	    				      			tvstatus.setText("paused");
									}
								});
			        		    stop.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										start.setBackgroundResource(R.drawable.s1);
	    				      			//tvstart.setText("Start");
	    				      			start.setVisibility(0);
	    				      			//tvstart.setVisibility(0);
	    				      			stop.setVisibility(8);
	    				      			//tvstop.setVisibility(8);
	    				      			pause.setVisibility(8);
	    				      			//tvpause.setVisibility(8);
	    				      			//completed
	    				      			System.out.println("completed");
	    				      			tvstatus.setText("completed");
									}
								});
		                  send_list.clear();
		              HashMap<String, String> map1=new HashMap<String,String>();
		              final String status1=tvstatus.getText().toString().trim();
		             final String comp="completed".toString().trim();
		              
	                 if(status1.equals(comp)){
		             map1.put("schedule_id", "0");
		             map1.put("schedule_times", "00:00");
					 map1.put("totalTime","00:00");
					 map1.put("fromTime","00:00");
					 map1.put("to_time","00:00");
					 map1.put("mode", mode);
					 tvstatus.setText("running");
					  totalfeed.setText("0");
					  ocf.setText("0");
					  feedgap.setText("0");
					  okdtime.setText("0");
					  total_time.setText("0");
					  dispense_feed_ed.setText("0");
	                 }else{
		              map1.put("schedule_id", schedule_id);
		              String[] separated = schedule_times.split("-");
	                  map1.put("schedule_times", separated[0]);
		              map1.put("fromTime",separated[0]);
		              map1.put("to_time",separated[1]);
		              map1.put("totalTime", totalTime);
		              map1.put("mode", mode);
	                 }
	               
			         send_list.add(map1);
			         ocf.addTextChangedListener(new TextWatcher() {
							
							@Override
							public void onTextChanged(CharSequence s, int start, int before, int count) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void beforeTextChanged(CharSequence s, int start, int count, int after) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void afterTextChanged(Editable s) {
								// TODO Auto-generated method stub
								try{
								
								 if(status1.equals(comp)){
									 String tf=totalfeed.getText().toString().trim();
										String df="0".toString();
										String cf=ocf.getText().toString().trim();
										String fg=feedgap.getText().toString().trim();
										float f_tf=Float.parseFloat(tf);
										float f_df=Float.parseFloat(df);
										float f_cf=Float.parseFloat(cf);
										float f_fg=Float.parseFloat(fg);
										float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
									 	float ttl_time = cycles * f_fg;
										int  tt=(int)ttl_time;
										int hours = tt / 60; 
										int minutes = tt % 60;
										total_time.setText(hours+" : "+minutes);
								 }else{
									    String tf=totalfeed.getText().toString().trim();
										String df=dispensed_feed.toString().trim();
										String cf=ocf.getText().toString().trim();
										String fg=feedgap.getText().toString().trim();
										float f_tf=Float.parseFloat(tf);
										float f_df=Float.parseFloat(df);
										float f_cf=Float.parseFloat(cf);
										float f_fg=Float.parseFloat(fg);
										float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
									 	float ttl_time = cycles * f_fg;
										int  tt=(int)ttl_time;
										int hours = tt / 60; 
										int minutes = tt % 60;
										total_time.setText(hours+" : "+minutes); 
								 }
							
								
								}catch(Exception e){
									e.printStackTrace();
								}
							}
						});
			         totalfeed.addTextChangedListener(new TextWatcher() {
							
							@Override
							public void onTextChanged(CharSequence s, int start, int before, int count) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void beforeTextChanged(CharSequence s, int start, int count, int after) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void afterTextChanged(Editable s) {
								// TODO Auto-generated method stub
								try{
									if(status1.equals(comp)){
										 String tf=totalfeed.getText().toString().trim();
											String df="0".toString();
											String cf=ocf.getText().toString().trim();
											String fg=feedgap.getText().toString().trim();
											float f_tf=Float.parseFloat(tf);
											float f_df=Float.parseFloat(df);
											float f_cf=Float.parseFloat(cf);
											float f_fg=Float.parseFloat(fg);
											float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
										 	float ttl_time = cycles * f_fg;
											int  tt=(int)ttl_time;
											int hours = tt / 60; 
											int minutes = tt % 60;
											total_time.setText(hours+" : "+minutes);
									 }else{
										 String tf=totalfeed.getText().toString().trim();
											String df=dispensed_feed.toString().trim();
											String cf=ocf.getText().toString().trim();
											String fg=feedgap.getText().toString().trim();
											float f_tf=Float.parseFloat(tf);
											float f_df=Float.parseFloat(df);
											float f_cf=Float.parseFloat(cf);
											float f_fg=Float.parseFloat(fg);
											float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
										 	float ttl_time = cycles * f_fg;
											int  tt=(int)ttl_time;
											int hours = tt / 60; 
											int minutes = tt % 60;
											total_time.setText(hours+" : "+minutes); 
									 }
								}catch(Exception e){
									e.printStackTrace();
								}
							}
						});
			         feedgap.addTextChangedListener(new TextWatcher() {
							
							@Override
							public void onTextChanged(CharSequence s, int start, int before, int count) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void beforeTextChanged(CharSequence s, int start, int count, int after) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void afterTextChanged(Editable s) {
								// TODO Auto-generated method stub
								try{
									if(status1.equals(comp)){
										 String tf=totalfeed.getText().toString().trim();
											String df="0".toString();
											String cf=ocf.getText().toString().trim();
											String fg=feedgap.getText().toString().trim();
											float f_tf=Float.parseFloat(tf);
											float f_df=Float.parseFloat(df);
											float f_cf=Float.parseFloat(cf);
											float f_fg=Float.parseFloat(fg);
											float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
										 	float ttl_time = cycles * f_fg;
											int  tt=(int)ttl_time;
											int hours = tt / 60; 
											int minutes = tt % 60;
											total_time.setText(hours+" : "+minutes);
									 }else{
										 String tf=totalfeed.getText().toString().trim();
											String df=dispensed_feed.toString().trim();
											String cf=ocf.getText().toString().trim();
											String fg=feedgap.getText().toString().trim();
											float f_tf=Float.parseFloat(tf);
											float f_df=Float.parseFloat(df);
											float f_cf=Float.parseFloat(cf);
											float f_fg=Float.parseFloat(fg);
											float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
										 	float ttl_time = cycles * f_fg;
											int  tt=(int)ttl_time;
											int hours = tt / 60; 
											int minutes = tt % 60;
											total_time.setText(hours+" : "+minutes);
									 }
								}catch(Exception e){
									e.printStackTrace();
								}
							}
						});
				   			}//for loop
				   		// array have schedules	
				   		 }
				   		 
				   		 else{
				   			send_list.clear();
					        totalfeed.setText("0");
					        dispense_feed_ed.setText("0");
					        totalfeed.setSelection(totalfeed.getText().length());
				  			ocf.setText("0");
				  			ocf.setSelection(ocf.getText().length());
				  			
				  			feedgap.setText("0");
				  			feedgap.setSelection(feedgap.getText().length());
				  		
				  			okdtime.setText("0");
				  			okdtime.setSelection(okdtime.getText().length());
				  		 		
				  			start.setBackgroundResource(R.drawable.s1);
			      			//tvstart.setText("Start");
			      			stop.setVisibility(8);
			      			//tvstop.setVisibility(8);
			      			pause.setVisibility(8);
			      			//tvpause.setVisibility(8);
			      	    	////to be_run
			      			 
			      			tvstatus.setText("running");
			      			start.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									
									pause.setBackgroundResource(R.drawable.p1);
				      				stop.setBackgroundResource(R.drawable.stop1);
				      				//tvpause.setText("Pause");
				      				//tvstop.setText("Stop");
				      				//tvstart.setVisibility(8);
					      			start.setVisibility(8);
				      				stop.setVisibility(0);
					      			//tvstop.setVisibility(0);
					      			pause.setVisibility(0);
					      			//tvpause.setVisibility(0);
					      			System.out.println("running");
					      			tvstatus.setText("running");
								}
							});
		        			pause.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									start.setBackgroundResource(R.drawable.s1);
				      				stop.setBackgroundResource(R.drawable.stop1);
				      				//tvstart.setText("Start");
				      				//tvstop.setText("Stop");
				      				//tvstart.setVisibility(0);
				      				//tvstop.setVisibility(0);
				      				start.setVisibility(0);
				      				stop.setVisibility(0);
				      				pause.setVisibility(8);
					      			//tvpause.setVisibility(8);
					      			//paused
					      			System.out.println("paused");
					      			tvstatus.setText("paused");
								}
							});
		        			stop.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									start.setBackgroundResource(R.drawable.s1);
					      			//tvstart.setText("Start");
					      			start.setVisibility(0);
					      			//tvstart.setVisibility(0);
					      			stop.setVisibility(8);
					      			//tvstop.setVisibility(8);
					      			pause.setVisibility(8);
					      			//tvpause.setVisibility(8);
					      			//completed
					      			System.out.println("completed");
					      			tvstatus.setText("completed");
								}
							});
		        			HashMap<String, String> map1=new HashMap<String,String>();
						 	map1.put("schedule_id", "0");
							map1.put("schedule_times", "00:00");
							map1.put("totalTime","00:00");
							map1.put("fromTime","00:00");
							map1.put("to_time","00:00");
							map1.put("mode", modee);
			      		    send_list.add(map1);
			      		   totalfeed.addTextChangedListener(new TextWatcher() {
								
								@Override
								public void onTextChanged(CharSequence s, int start, int before, int count) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void beforeTextChanged(CharSequence s, int start, int count, int after) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void afterTextChanged(Editable s) {
									// TODO Auto-generated method stub
									try{
									String tf=totalfeed.getText().toString().trim();
									String df="0".toString().trim();
									String cf=ocf.getText().toString().trim();
									String fg=feedgap.getText().toString().trim();
									float f_tf=Float.parseFloat(tf);
									float f_df=Float.parseFloat(df);
									float f_cf=Float.parseFloat(cf);
									float f_fg=Float.parseFloat(fg);
									float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
								 	float ttl_time = cycles * f_fg;
									int  tt=(int)ttl_time;
									int hours = tt / 60; 
									int minutes = tt % 60;
									total_time.setText(hours+":"+minutes);
									}catch(Exception e){
										e.printStackTrace();
									}
								}
							});
			      		   ocf.addTextChangedListener(new TextWatcher() {
								
								@Override
								public void onTextChanged(CharSequence s, int start, int before, int count) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void beforeTextChanged(CharSequence s, int start, int count, int after) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void afterTextChanged(Editable s) {
									// TODO Auto-generated method stub
									try{
									String tf=totalfeed.getText().toString().trim();
									String df="0".toString().trim();
									String cf=ocf.getText().toString().trim();
									String fg=feedgap.getText().toString().trim();
									float f_tf=Float.parseFloat(tf);
									float f_df=Float.parseFloat(df);
									float f_cf=Float.parseFloat(cf);
									float f_fg=Float.parseFloat(fg);
									float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
								 	float ttl_time = cycles * f_fg;
									int  tt=(int)ttl_time;
									int hours = tt / 60; 
									int minutes = tt % 60;
									total_time.setText(hours+":"+minutes);
									}catch(Exception e){
										e.printStackTrace();
									}
								}
							});
			      		   feedgap.addTextChangedListener(new TextWatcher() {
								
								@Override
								public void onTextChanged(CharSequence s, int start, int before, int count) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void beforeTextChanged(CharSequence s, int start, int count, int after) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void afterTextChanged(Editable s) {
									// TODO Auto-generated method stub
									try{
									String tf=totalfeed.getText().toString().trim();
									String df="0".toString().trim();
									String cf=ocf.getText().toString().trim();
									String fg=feedgap.getText().toString().trim();
									float f_tf=Float.parseFloat(tf);
									float f_df=Float.parseFloat(df);
									float f_cf=Float.parseFloat(cf);
									float f_fg=Float.parseFloat(fg);
									float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
								 	float ttl_time = cycles * f_fg;
									int  tt=(int)ttl_time;
									int hours = tt / 60; 
									int minutes = tt % 60;
									total_time.setText(hours+":"+minutes);
									}catch(Exception e){
										e.printStackTrace();
									}
								}
							});
			      		    
				   		 }
					 }catch (Exception e) {
							// TODO: handle exception
						 System.out.println(e.toString());
						}
					    		
				 }
			}catch(Exception e){
				e.printStackTrace();
			}
			 
	 }
	public class Update extends AsyncTask<String, Void, Void> {		
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
               	build.setTitle("Update");
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
							JSONObject jsn2 = new JSONObject(error);
							String response2 = jsn2.getString("response");
						 
							build.setMessage(response2);
							send_list.clear();
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
												 getActivity().finish();
																						  				
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
					try {	
						   
					       String user_id = ApplicationDetails.getString("user_id", null);
					       String feederSno=   ApplicationData.get_feederSno().toString().trim();
			        		
						try{
							 JSONObject loginJson = new JSONObject();
							 loginJson.put("user_id", user_id);
							 loginJson.put("feederSno", feederSno);
							 
							 for(int i=0; i<send_list2.size(); i++) {
						   		 HashMap<String, String> map = send_list2.get(i);
						   		 	   		 
						   		 final String tfeed=map.get("tfeed").toString().trim();
						   		 final String ocfeed=map.get("ocfeed").toString().trim();
						   		 final String fgp=map.get("fgp").toString().trim();
						   		 final String status=map.get("status").toString().trim();
						   	 
						   		 final String schedule_date=map.get("schedule_date").toString().trim();
						   		 final String schedule_end_date=map.get("schedule_end_date").toString().trim();
						   		 
						   		 final String schedule_id=map.get("schedule_id").toString().trim();
						   		 
						   		 final String totalTime=map.get("totalTime").toString().trim();
						   	     final String fromTime=map.get("fromTime").toString().trim();
						   	     final String toTime=map.get("to_time").toString().trim();
						   		 final String mode=map.get("mode").toString().trim();
						   		 
						   		 loginJson.put("user_id", user_id);
								 loginJson.put("schedule_date",schedule_date);	
								 loginJson.put("schedule_end_date",schedule_end_date);	
								 
								 total_feed_array.add("\"" + tfeed + "\"");
							 	 loginJson.put("original_feed", total_feed_array);
							 	 
							 	 one_cycle_feed_array.add("\"" + ocfeed + "\"");
								 loginJson.put("one_cycle_feed",one_cycle_feed_array);
								 
								 feed_gap_array.add("\"" + fgp + "\"");
								 loginJson.put("feed_gap",feed_gap_array);	
								 
								 status_array.add("\"" + status + "\"");
								 loginJson.put("status",status_array);
							
								 schedule_id_array.add("\"" + schedule_id + "\"");
								 loginJson.put("schedule_id",schedule_id_array);	
							
								 totalTime_array.add("\"" + totalTime + "\"");
								 loginJson.put("total_time",totalTime_array);	
								 
								 mode_array.add("\"" + mode + "\"");
								 loginJson.put("mode",mode_array);
								 
								 fromTime_array.add("\"" + fromTime + "\"");
								 loginJson.put("from_time",fromTime_array);	
							
								 toTime_array.add("\"" +toTime + "\"");
								 loginJson.put("to_time",toTime_array);
													
								 onTime_array.add("\"" + "00:00" + "\"");
								 loginJson.put("on_time",onTime_array);
								
								 offTime_array.add("\"" + "00:00" + "\"");
								 loginJson.put("off_time",offTime_array);
							 
							 }
						
						
						 HttpParams httpParams = new BasicHttpParams();
					     HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT_MILLISEC);
					     HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
											       
						 DefaultHttpClient httpclient = new MyHttpsClient(httpParams,getActivity());
						 HttpPost httppost = new HttpPost(Config.URL_Update);
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
	 protected boolean    isInternetAvailable() {
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
		private void DisplayShow(){
			 try{
				 final LinearLayout tablerow1=new LinearLayout(getActivity());
		           tablerow1.setOrientation(LinearLayout.HORIZONTAL);
		           LinearLayout.LayoutParams paramas = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
		           	LinearLayout.LayoutParams.WRAP_CONTENT,1);
		           	 paramas.setMargins(0, 0, 0, 0);                  
		           	 tablerow1.setLayoutParams(paramas);
		           	 final LinearLayout tablerow2=new LinearLayout(getActivity());
			           tablerow2.setOrientation(LinearLayout.HORIZONTAL);
			      	 tablerow2.setLayoutParams(paramas);
			      	 
		           	 LinearLayout.LayoutParams fromtime_paramas2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
		 		            LinearLayout.LayoutParams.WRAP_CONTENT,1);
		           	fromtime_paramas2.setMargins(5, 0, 5, 0);
		           	
		            LinearLayout.LayoutParams total_feed_paramas5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
		            total_feed_paramas5.setMargins(5, 0, 5, 0);    
		            LinearLayout.LayoutParams ocf_paramas6 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
		            ocf_paramas6.setMargins(5, 0, 5, 0); 	
		            LinearLayout.LayoutParams fg_paramas7 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
		            fg_paramas7.setMargins(5, 0, 5, 0);
		            
		            
		            final TextView fromtime=new TextView(getActivity());
		            fromtime.setText("Start");
		           fromtime.setTextColor(Color.BLACK);
		           fromtime.setGravity(Gravity.CENTER);
		           fromtime.setFreezesText(true);
		           fromtime.setTextSize(16);
		           fromtime.setFocusable(false); 
		           fromtime.setLayoutParams(fromtime_paramas2);
		           fromtime.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
		           
		           final TextView totime=new TextView(getActivity());
		            totime.setText("End");
		           totime.setTextColor(Color.BLACK);
		           totime.setGravity(Gravity.CENTER);
		           totime.setFreezesText(true);
		           totime.setTextSize(16);
		           totime.setFocusable(false); 
		           totime.setLayoutParams(fromtime_paramas2);
		           totime.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
		           
		           final TextView total_feed=new TextView(getActivity());
		           		  total_feed.setText("T.F");
		           		  total_feed.setTextColor(Color.BLACK);
		           		  total_feed.setLayoutParams(total_feed_paramas5);
		           		  total_feed.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
		           		  total_feed.setGravity(Gravity.CENTER);
		           		  total_feed.setFreezesText(true);
		           		  total_feed.setTextSize(16);
		           		  total_feed.setEnabled(false); 
		           		  
		           	      final TextView ocf=new TextView(getActivity());
		           				 ocf.setText("O.C.F");
		           				 ocf.setTextColor(Color.BLACK);
		           				 ocf.setLayoutParams(ocf_paramas6);
		           				 ocf.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
		           				 ocf.setGravity(Gravity.CENTER);
		           				 ocf.setFreezesText(true);
		           				 ocf.setTextSize(16);
		           				 ocf.setEnabled(false);
		           							 
		           					     
		           					     final TextView fg=new TextView(getActivity());
		           					     fg.setText("F.G");
		           					     fg.setTextColor(Color.BLACK);
		           					     fg.setLayoutParams(fg_paramas7);
		           					     fg.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
		           					     fg.setGravity(Gravity.CENTER);
		           					     fg.setFreezesText(true);
		           					     fg.setTextSize(16);
		           					     
		           					    
		           					  final TextView add=new TextView(getActivity());
		     				           add.setGravity(Gravity.CENTER);
		     					       add.setPadding(5, 0, 5, 0);  
		     					       //add.setHeight(0);
		     					       add.setBackgroundResource(R.drawable.adg);
		     					      add.setOnClickListener(new OnClickListener() {
										
										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											AddData();
										}
									});			      
		                                  tablerow1.addView(fromtime);
		                                  tablerow1.addView(totime);
		           					      tablerow1.addView(total_feed);
		           					      tablerow1.addView(ocf);
		           					      tablerow1.addView(fg);
		           					      tablerow1.addView(add);
		           					  
		           						  demo.addView(tablerow1); 
		           						  
		                                  final TextView time=new TextView(getActivity());
		              		            time.setText("Time");
		              		           time.setTextColor(Color.BLACK);
		              		           time.setGravity(Gravity.CENTER);
		              		           time.setFreezesText(true);
		              		           time.setTextSize(16);
		              		           time.setFocusable(false); 
		              		           time.setLayoutParams(fromtime_paramas2);
		              		           time.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
		              		           
		              		           final TextView kg=new TextView(getActivity());
		              		           		 kg.setText("k.g");
		              		           		  kg.setTextColor(Color.BLACK);
		              		           		  kg.setLayoutParams(total_feed_paramas5);
		              		           		  kg.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
		              		           		  kg.setGravity(Gravity.CENTER);
		              		           		  kg.setFreezesText(true);
		              		           		  kg.setTextSize(16);
		              		           		  kg.setEnabled(false); 
		              		           		  
		              		           	      final TextView gms=new TextView(getActivity());
		              		           				 gms.setText("gms");
		              		           				 gms.setTextColor(Color.BLACK);
		              		           					    gms.setLayoutParams(ocf_paramas6);
		              		           					     gms.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
		              		           					     gms.setGravity(Gravity.CENTER);
		              		           					     gms.setFreezesText(true);
		              		           					     gms.setTextSize(16);
		              		           					     gms.setEnabled(false);
		              		       		           					 
		              		           					     
		              		           					     final TextView min=new TextView(getActivity());
		              		           					    min.setText("min");
		              		           					     min.setTextColor(Color.BLACK);
		              		           					     min.setLayoutParams(fg_paramas7);
		              		           					    min.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
		              		           					    min.setGravity(Gravity.CENTER);
		              		           					    min.setFreezesText(true);
		              		           					    min.setTextSize(16);
		              		           					     
		              		           					    
		              		           					  final TextView add2=new TextView(getActivity());
		              		     				           add2.setGravity(Gravity.CENTER);
		              		     					       add2.setPadding(10, 0, 5, 0);  
		              		     					       add2.setHeight(0);
		              		     					       add2.setBackgroundResource(R.drawable.none);
		              		     					       
		              		     					      
		              		                                  tablerow2.addView(time);
		              		           					      tablerow2.addView(kg);
		              		           					      tablerow2.addView(gms);
		              		           					      tablerow2.addView(min);
		              		           					      tablerow2.addView(add2);
		              		           					     // demo.addView(tablerow2);
		           					View v=new View(getActivity());
		           	                v.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		           	                v.setBackgroundResource(R.drawable.seprator2);
		           	                demo.addView(v);
		           	                
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		}
		
		private void scheduleMode(){
						
			   t1.setVerticalScrollBarEnabled(true);
			   t1.removeAllViewsInLayout();
               
			 for(int i=0; i<mylist.size(); i++) {
				 try{
					 final HashMap<String, String> map = mylist.get(i);
				     final String kg_feed_disp_time_all=map.get("kg_feed_disp_time").toString().trim();
				   	 final String modee=map.get("mode").toString().trim();
				     ApplicationData.add_kg_feed_disp_Time(kg_feed_disp_time_all);
			   	     ApplicationData.add_mode(modee);
			   	    
			   	     final String schedule_date=map.get("schedule_date").toString().trim();
			   	     final String last_update_time=map.get("last_update_time").toString().trim();
			   	   
			   	      final String schedules=map.get("schedules").toString().trim();
			   	 	 final JSONArray jsonArray = new JSONArray(schedules); 
			   		  mylist_schedule.clear();
			   		  //Added Schedules to Array
			   		check_array.clear();
			   		for (int j = 0; j < jsonArray.length(); j++) {
			   			JSONObject jObject = jsonArray.getJSONObject(j);
			   		    final HashMap<String, String> map1 = new HashMap<String, String>();
			   			final String schedule_id=jObject.getString("schedule_id");
	      				final String schedule_times=jObject.getString("schedule_times");
	      				final String totalTime=jObject.getString("totalTime");
	      				final String original_feed=jObject.getString("original_feed");
	      				final String one_cycle_feed=jObject.getString("one_cycle_feed");
	      				final String feed_gap=jObject.getString("feed_gap");
	      				final String kg_feed_disp_time=jObject.getString("kg_feed_disp_time");
	      				final String mode=jObject.getString("mode");
	      				final String status=jObject.getString("status");
	      				final String dispensed_feed=jObject.getString("dispensed_feed");
	      				if(modee.equals(mode)){
	      					check_array.add(mode);
	      					map1.put("schedule_id", schedule_id);
		      				map1.put("schedule_times", schedule_times);
		      				map1.put("totalTime", totalTime);
		      				map1.put("original_feed", original_feed);
		      				map1.put("one_cycle_feed", one_cycle_feed);
		      				map1.put("feed_gap", feed_gap);
		      				map1.put("kg_feed_disp_time", kg_feed_disp_time);
		      				map1.put("mode", mode);
		      				map1.put("status", status);
		      				map1.put("dispensed_feed", dispensed_feed);
		      				mylist_schedule.add(map1);
	      				}
			   		}
			   		 
			   		//Checking Add/Remove Schedules 
			   		get_mylist=ApplicationData.get_removelist();
			   		System.out.println(get_mylist);
			   		if(get_mylist.size()>0){
			   			mylist_schedule.clear();
			   			check_array.clear();
			   			for (int j = 0; j < get_mylist.size(); j++) {
					   	    HashMap<String, String> map2=get_mylist.get(j); 
				   		    final HashMap<String, String> map1 = new HashMap<String, String>();
				   		    final String schedule_id=map2.get("schedule_id").toString().trim();
		      				final String schedule_times=map2.get("schedule_times").toString().trim();
		      				final String totalTime=map2.get("totalTime").toString().trim();
		      				final String original_feed=map2.get("original_feed").toString().trim();
		      				final String one_cycle_feed=map2.get("one_cycle_feed").toString().trim();
		      				final String feed_gap=map2.get("feed_gap").toString().trim();
		      				final String kg_feed_disp_time=map2.get("kg_feed_disp_time").toString().trim();
		      				final String mode=map2.get("mode").toString().trim();
		      				final String status=map2.get("status").toString().trim();
		      				final String dispensed_feed=map2.get("dispensed_feed").toString().trim();
		      				map1.put("schedule_id", schedule_id);
		      				map1.put("schedule_times", schedule_times);
		      				map1.put("totalTime", totalTime);
		      				map1.put("original_feed", original_feed);
		      				map1.put("one_cycle_feed", one_cycle_feed);
		      				map1.put("feed_gap", feed_gap);
		      				map1.put("kg_feed_disp_time", kg_feed_disp_time);
		      				map1.put("mode", mode);
		      				map1.put("status", status);
		      				map1.put("dispensed_feed", dispensed_feed);
		      				
		      				if(modee.equals(mode)){
		      					check_array.add(mode);
		      					mylist_schedule.add(map1);
		      				}
			   			}
			   		}
			   	 
			   		  //schedules are not empty  have display
			   		 if(mylist_schedule.size()>0 && check_array.size()>0){
			   			update_list.clear();
			   			ar.clear();
			   			for (int j = 0; j < mylist_schedule.size(); j++) {
			   		    HashMap<String, String> map2=mylist_schedule.get(j); 
			   		    final HashMap<String, String> map1 = new HashMap<String, String>();
	      				final String schedule_id=map2.get("schedule_id").toString().trim();
	      				final String schedule_times=map2.get("schedule_times").toString().trim();
	      				final String totalTime=map2.get("totalTime").toString().trim();
	      				final String original_feed=map2.get("original_feed").toString().trim();
	      				final String one_cycle_feed=map2.get("one_cycle_feed").toString().trim();
	      				final String feed_gap=map2.get("feed_gap").toString().trim();
	      				final String kg_feed_disp_time=map2.get("kg_feed_disp_time").toString().trim();
	      				final String mode=map2.get("mode").toString().trim();
	      				final String status=map2.get("status").toString().trim();
	      				final String dispensed_feed=map2.get("dispensed_feed").toString().trim();
	      	 			 
	      				
	      				final LinearLayout tablerow1=new LinearLayout(getActivity());
	                    tablerow1.setOrientation(LinearLayout.HORIZONTAL);
	                    LinearLayout.LayoutParams paramas = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
	                            LinearLayout.LayoutParams.WRAP_CONTENT,1);
	                     paramas.setMargins(0, 0, 0, 0);
	                     tablerow1.setLayoutParams(paramas);
	                     LinearLayout.LayoutParams paramas2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
			              paramas2.setMargins(5, 0, 5, 0);
	                 
			      		 final EditText fromtime=new EditText(getActivity());
			      		 fromtime.setTextColor(Color.BLACK);
			      	 	 fromtime.setGravity(Gravity.CENTER);
			      		 fromtime.setFreezesText(true);
			      		 fromtime.setTextSize(15);
			      		 fromtime.setFocusable(false); 
			      		 fromtime.setLayoutParams(paramas2);
			      	 	 fromtime.setBackgroundResource(R.drawable.rectangle);
			      	 	 fromtime.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
			      	 	 
			      	 	final EditText totime=new EditText(getActivity());
			      	 	totime.setTextColor(Color.BLACK);
			      	 	totime.setGravity(Gravity.CENTER);
			      	 	totime.setFreezesText(true);
			      	 	totime.setTextSize(15);
			      	 	totime.setFocusable(false); 
			      	 	totime.setLayoutParams(paramas2);
			      	 	totime.setBackgroundResource(R.drawable.rectangle2);
			      	 	totime.setKeyListener(null);
			      	 	totime.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
			      	 	      	 
			      	 	String   to_date="00:00";
			      	    Date t_date=null;
			     
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
			 					 String str_date=(format2.format(f_date)).toString().trim();
			 					    to_date=(format2.format(t_date)).toString().trim();
			 					 fromtime.setText(str_date);
			 					totime.setText(to_date);
			 			 		
			      	 	}catch(Exception e){
			      	 		e.printStackTrace();
			      	 	}
      		
			      	  final EditText total_feed=new EditText(getActivity());
					     total_feed.setText(original_feed);
					     total_feed.setTextColor(Color.BLACK);
					     LinearLayout.LayoutParams paramas5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
					 	 paramas5.setMargins(5, 0, 5, 0);
					     total_feed.setLayoutParams(paramas5);
					     total_feed.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
					     total_feed.setGravity(Gravity.CENTER);
					     total_feed.setFreezesText(true);
					     total_feed.setTextSize(15);
					     int maxLength = 4;    
						 total_feed.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
					     
					     final EditText ocf=new EditText(getActivity());
					     ocf.setText(one_cycle_feed);
					     ocf.setTextColor(Color.BLACK);
					     LinearLayout.LayoutParams paramas6 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
					 	 paramas6.setMargins(5, 0, 5, 0);
					     ocf.setLayoutParams(paramas6);
					     ocf.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
					     ocf.setGravity(Gravity.CENTER);
					     ocf.setFreezesText(true);
					     ocf.setTextSize(15);
					     ocf.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
					     
					     final EditText fg=new EditText(getActivity());
					     fg.setText(feed_gap);
					     fg.setTextColor(Color.BLACK);
					     LinearLayout.LayoutParams paramas7 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
					 	 paramas7.setMargins(5, 0, 5, 0);
					     fg.setLayoutParams(paramas7);
					     fg.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
					     fg.setGravity(Gravity.CENTER);
					     fg.setFreezesText(true);
					     fg.setTextSize(15);
					     fg.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
			      	 
			      		final TextView sub=new TextView(getActivity());
				         sub.setGravity(Gravity.CENTER);
					     sub.setPadding(5, 0, 5, 0);  
					     sub.setId(j);
					     
					    if(sub.getId()==0){
					    	 sub.setBackgroundResource(R.drawable.none);
					     }else{
					    	 sub.setBackgroundResource(R.drawable.rsub);
					     } 
					    
					    final TextView start=new TextView(getActivity());
					    start.setGravity(Gravity.CENTER);
					    start.setPadding(5, 0, 5, 0); 
					    
					    final TextView stop=new TextView(getActivity());
					    stop.setGravity(Gravity.CENTER);
					    stop.setPadding(5, 0, 5, 0);  
							    
					    final Calendar calender=new GregorianCalendar(TimeZone.getTimeZone(timezone));
					    final Date date = new Date(calender.getTimeInMillis());
						final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
					    String currenttimestr=dateFormat.format(date).toString().trim();
					    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
						simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
						Date ftime = simpleDateFormat.parse(fromtime.getText().toString()); 
						Date ctime = simpleDateFormat.parse(currenttimestr); 
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
				        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
						 String dt1=sdf.format(ftime);
						 String dt2=sdf.format(ctime);
						 String dt3=sdf.format(t_date);
						 String str="0".toString().trim();
								    	 
								    	if(schedule_id.equals(str)){
 									    	 fromtime.setEnabled(true);
								    	}else{
								    		 if(dt1.compareTo(dt2)<=0){
								    			 fromtime.setEnabled(false);
									    		 fromtime.setBackgroundResource(R.drawable.roundedshape2);
									    		 sub.setBackgroundResource(R.drawable.none);
												 sub.setEnabled(false); 
												  }
								    		 if(dt3.compareTo(dt2)<=0){
								    			 fromtime.setEnabled(false);
									    		 fromtime.setBackgroundResource(R.drawable.roundedshape2);
									    		 sub.setBackgroundResource(R.drawable.none);
												 sub.setEnabled(false);  
												 total_feed.setEnabled(false);
												// total_feed.setBackgroundColor(Color.DKGRAY);
												 ocf.setEnabled(false);
												// ocf.setBackgroundResource(R.drawable.roundedshape2);
												 fg.setEnabled(false);
												// fg.setBackgroundResource(R.drawable.roundedshape2);
								    		 }
								    		  
								    		}
								    	
								    	    String to_be_run="to_be_run".toString().trim();
							      			String running="running".toString().trim();
							      			String changed="changed".toString().trim();
							      			String paused="paused".toString().trim();
							      			String completed="completed".toString().trim();
                                           
				                            if(status.equals(to_be_run))	{
				                               start.setBackgroundResource(R.drawable.s1);
				                               stop.setBackgroundResource(R.drawable.none);	
				                              // System.out.println(mylist_schedule.get(j)+"Schedule"+j);
							      			}else if(status.equals(running)){
							      				start.setBackgroundResource(R.drawable.p1);
							      				stop.setBackgroundResource(R.drawable.stop1);
							      			}else if(status.equals(changed)){
							      				start.setBackgroundResource(R.drawable.p1);
							      				stop.setBackgroundResource(R.drawable.stop1);
							      				 //System.out.println(mylist_schedule.get(j)+"Schedule"+j);
							      			}else if(status.equals(paused)){
							      				start.setBackgroundResource(R.drawable.s1);
							      				stop.setBackgroundResource(R.drawable.stop1);
							      				// System.out.println(mylist_schedule.get(j)+"Schedule"+j);
							      			}else if(status.equals(completed)){
							      				  total_feed.setEnabled(false);
												   ocf.setEnabled(false); 
												   fg.setEnabled(false);
												   fromtime.setEnabled(false);
										    	   fromtime.setBackgroundResource(R.drawable.roundedshape2);
										    	   start.setBackgroundResource(R.drawable.s1);
										    	   stop.setBackgroundResource(R.drawable.none);		
												   sub.setEnabled(false); 
												  // System.out.println(mylist_schedule.get(j)+"Schedule"+j);
							      				 }
					     if(modee.equals(mode)){
					    	  tablerow1.addView(fromtime);
					    	  tablerow1.addView(totime);
						      tablerow1.addView(total_feed);
						      tablerow1.addView(ocf);
						      tablerow1.addView(fg);
						      tablerow1.addView(sub);
						      //tablerow1.addView(start);
						      //tablerow1.addView(stop);
							  t1.addView(tablerow1);
							   map1.put("schedule_id", schedule_id);
							   map1.put("from_time", fromtime.getText().toString());
							   map1.put("to_time", to_date);
							   map1.put("total_feed", total_feed.getText().toString());
							   map1.put("ocf", ocf.getText().toString());
							   map1.put("fg", fg.getText().toString());
							   map1.put("totalTime",totalTime);
							   map1.put("kg_feed_disp_time", kg_feed_disp_time);
							   map1.put("mode", mode);
							   map1.put("status", status);
							   map1.put("dispensed_feed", dispensed_feed);
							   update_list.add(map1);
					      }
					     
					     fromtime.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									try{
										 
							            	 Date time=null;
							            	try{
							            		final SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm",Locale.getDefault());
							            		
								            	  time=timeformat.parse(fromtime.getText().toString());
								                 map1.put("from_time", fromtime.getText().toString());
								                  	  
							            	}catch(Exception e){
							            		e.printStackTrace();
							            	}
							            						               
						                    final int fhour =time.getHours();
						                   	final  int fminute = time.getMinutes();
						                
	                                       OnTimeSetListener timesetlistener=new OnTimeSetListener() {
												
												@Override
												public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
													// TODO Auto-generated method stub
												String h1,m1;
						                         
						                         if(String.valueOf(hourOfDay).length()==1){
						                        	   h1=("0"+String.valueOf(hourOfDay));
						                        	 
						                         }else{
						                        	   h1=String.valueOf(hourOfDay);
						                         }
						                         
						                         if((String.valueOf(minute).length()==1)){
						                        	 m1=("0"+String.valueOf(minute));
						                         }else{
						                        	 m1=(String.valueOf(minute)); 
						                         }
							                   
						                         fromtime.setText(h1 + ":" +m1);
						                         map1.put("from_time", fromtime.getText().toString());
						                         try{
								            		  int hours=0;
													  int minutes=0;
								            		String tf=total_feed.getText().toString().trim();
													String df="0".toString().trim();
													String cf=ocf.getText().toString().trim();
													String f_g=fg.getText().toString().trim();
													float f_tf=Float.parseFloat(tf);
													float f_df=Float.parseFloat(df);
													float f_cf=Float.parseFloat(cf);
													float f_fg=Float.parseFloat(f_g);
													float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
												 	float ttl_time = cycles * f_fg;
													int  tt=(int)ttl_time;
													 hours = tt / 60; 
												     minutes = tt % 60;
													//total_time.setText(hours+":"+minutes);
											        String start_time=fromtime.getText().toString();
													String[] separated = start_time.split(":");
													String hour_str=separated[0]; 
													String min_str=separated[1];
													int hour_int=Integer.parseInt(hour_str);
													int mint_int=Integer.parseInt(min_str);
													int hour_endtime=(hour_int+hours);
													int mintue_endtime=(mint_int+minutes);
													totime.setText(hour_endtime+":"+mintue_endtime);
													SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
													 simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
													Date ftime = simpleDateFormat.parse(totime.getText().toString()); 
													SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
												     sdf.setTimeZone(TimeZone.getTimeZone(timezone));
													String stime=sdf.format(ftime);
													totime.setText(stime);
													 
													 map1.put("to_time", totime.getText().toString());	
								            	}catch(Exception e){
								            		e.printStackTrace();
								            	}
						                         try{
						                        	 final Calendar calender=new GregorianCalendar(TimeZone.getTimeZone(timezone));
													    final Date date = new Date(calender.getTimeInMillis());
																			
							                         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
							 						simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
							 						String currenttimestr=simpleDateFormat.format(date).toString().trim();
							 						Date ctime = simpleDateFormat.parse(currenttimestr); 
							 						
							 						Date ftime = simpleDateFormat.parse(fromtime.getText().toString()); 
							                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
							 				        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
							 						 String stime=sdf.format(ftime);
							 						 final String dt2=sdf.format(ctime); 
							                         if(dt2.compareTo(stime)<=0){
							                        	  
							                         }else{
							                        	 final SimpleDateFormat format2 = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
							   							 format2.setTimeZone(TimeZone.getTimeZone(timezone)); 
							   							 String selected_date = start_date.getText().toString().trim();
							   							  String current_date = format2.format(System.currentTimeMillis());
							     							  if(current_date.equals(selected_date)){
							     								 fromtime.setText(dt2);
									                        	 Toast.makeText(getActivity(), "Past Time not Allowed", Toast.LENGTH_SHORT).show();
							     							  }
							                        	 
							                         }
							                         }catch(Exception e){
							                        	 e.printStackTrace();
							                         }
												}
											};
											
											TimePickerDialog tpd=new TimePickerDialog(getActivity(), timesetlistener, fhour, fminute,  DateFormat.is24HourFormat(getActivity()));
											tpd.show();
							            	
									}catch(Exception e){
										e.printStackTrace();
									}
									
								}
							});
					     total_feed.addTextChangedListener(new TextWatcher() {
							
							@Override
							public void onTextChanged(CharSequence s, int start, int before, int count) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void beforeTextChanged(CharSequence s, int start, int count, int after) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void afterTextChanged(Editable s) {
								// TODO Auto-generated method stub
								 map1.put("total_feed", s.toString());
								 try{
				            		  int hours=0;
									  int minutes=0;
				            		String tf=total_feed.getText().toString().trim();
									String df="0".toString().trim();
									String cf=ocf.getText().toString().trim();
									String f_g=fg.getText().toString().trim();
									float f_tf=Float.parseFloat(tf);
									float f_df=Float.parseFloat(df);
									float f_cf=Float.parseFloat(cf);
									float f_fg=Float.parseFloat(f_g);
									float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
								 	float ttl_time = cycles * f_fg;
									int  tt=(int)ttl_time;
									 hours = tt / 60; 
								     minutes = tt % 60;
									//total_time.setText(hours+":"+minutes);
							        String start_time=fromtime.getText().toString();
									String[] separated = start_time.split(":");
									String hour_str=separated[0]; 
									String min_str=separated[1];
									int hour_int=Integer.parseInt(hour_str);
									int mint_int=Integer.parseInt(min_str);
									int hour_endtime=(hour_int+hours);
									int mintue_endtime=(mint_int+minutes);
									totime.setText(hour_endtime+":"+mintue_endtime);
									SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
									 simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
									Date ftime = simpleDateFormat.parse(totime.getText().toString()); 
									SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
								     sdf.setTimeZone(TimeZone.getTimeZone(timezone));
									String stime=sdf.format(ftime);
									totime.setText(stime);
									 map1.put("to_time", totime.getText().toString());	
				            	}catch(Exception e){
				            		e.printStackTrace();
				            	}
							}
						});	
					     ocf.addTextChangedListener(new TextWatcher() {
							
							@Override
							public void onTextChanged(CharSequence s, int start, int before, int count) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void beforeTextChanged(CharSequence s, int start, int count, int after) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void afterTextChanged(Editable s) {
								// TODO Auto-generated method stub
								  map1.put("ocf", s.toString());
								  try{
				            		  int hours=0;
									  int minutes=0;
				            		String tf=total_feed.getText().toString().trim();
									String df="0".toString().trim();
									String cf=ocf.getText().toString().trim();
									String f_g=fg.getText().toString().trim();
									float f_tf=Float.parseFloat(tf);
									float f_df=Float.parseFloat(df);
									float f_cf=Float.parseFloat(cf);
									float f_fg=Float.parseFloat(f_g);
									float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
								 	float ttl_time = cycles * f_fg;
									int  tt=(int)ttl_time;
									 hours = tt / 60; 
								     minutes = tt % 60;
									//total_time.setText(hours+":"+minutes);
							        String start_time=fromtime.getText().toString();
									String[] separated = start_time.split(":");
									String hour_str=separated[0]; 
									String min_str=separated[1];
									int hour_int=Integer.parseInt(hour_str);
									int mint_int=Integer.parseInt(min_str);
									int hour_endtime=(hour_int+hours);
									int mintue_endtime=(mint_int+minutes);
									totime.setText(hour_endtime+":"+mintue_endtime);
									SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
									 simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
									Date ftime = simpleDateFormat.parse(totime.getText().toString()); 
									SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
								     sdf.setTimeZone(TimeZone.getTimeZone(timezone));
									String stime=sdf.format(ftime);
									totime.setText(stime);
									 map1.put("to_time", totime.getText().toString());	
				            	}catch(Exception e){
				            		e.printStackTrace();
				            	}
							}
						});
					     fg.addTextChangedListener(new TextWatcher() {
							
							@Override
							public void onTextChanged(CharSequence s, int start, int before, int count) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void beforeTextChanged(CharSequence s, int start, int count, int after) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void afterTextChanged(Editable s) {
								// TODO Auto-generated method stub
								  map1.put("fg", s.toString());
								  try{
				            		  int hours=0;
									  int minutes=0;
				            		String tf=total_feed.getText().toString().trim();
									String df="0".toString().trim();
									String cf=ocf.getText().toString().trim();
									String f_g=fg.getText().toString().trim();
									float f_tf=Float.parseFloat(tf);
									float f_df=Float.parseFloat(df);
									float f_cf=Float.parseFloat(cf);
									float f_fg=Float.parseFloat(f_g);
									float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
								 	float ttl_time = cycles * f_fg;
									int  tt=(int)ttl_time;
									 hours = tt / 60; 
								     minutes = tt % 60;
									//total_time.setText(hours+":"+minutes);
							        String start_time=fromtime.getText().toString();
									String[] separated = start_time.split(":");
									String hour_str=separated[0]; 
									String min_str=separated[1];
									int hour_int=Integer.parseInt(hour_str);
									int mint_int=Integer.parseInt(min_str);
									int hour_endtime=(hour_int+hours);
									int mintue_endtime=(mint_int+minutes);
									totime.setText(hour_endtime+":"+mintue_endtime);
									SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
									 simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
									Date ftime = simpleDateFormat.parse(totime.getText().toString()); 
									SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
								     sdf.setTimeZone(TimeZone.getTimeZone(timezone));
									String stime=sdf.format(ftime);
									totime.setText(stime);
									 map1.put("to_time", totime.getText().toString());	
				            	}catch(Exception e){
				            		e.printStackTrace();
				            	}
							}
						});
						  sub.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								 
								if(sub.getId()==0){
									 Toast.makeText(getActivity(), " Cant be Removed This Record", Toast.LENGTH_SHORT).show();	 
								}else{
									try{
										  mylist_schedule.remove(sub.getId());
									 	  update_list.remove(sub.getId());
									       t1.removeViewAt(sub.getId());
							  							 	  
							 	        RemoveData();		 	 
							 	   }catch(Exception e){
										e.printStackTrace();
										System.out.println(e.toString());
									}
								  
								}
							}
						});
						  if(dt3.compareTo(dt2)<=0){
		      					
		      				}else{
		      					if(status.equals(running)){
		      						try{
		      						ar.add(j);	 
		      						}catch(Exception e){
		      							e.printStackTrace();
		      						}
		      					}             
		      				
		      			      					      				  
		      				}
						
			   			}
			   		 final LinearLayout liner_manual=new LinearLayout(getActivity());
			         // liner_manual.setBackgroundColor(Color.parseColor("#EEEEEE"));
			     
			         liner_manual.setOrientation(LinearLayout.HORIZONTAL);
			         LinearLayout.LayoutParams paramas1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			                 LinearLayout.LayoutParams.WRAP_CONTENT,1);
			          paramas1.setMargins(0, 0, 0, 0);
			         //liner_manual.setPadding(5, 5, 5, 5);
			         liner_manual.setLayoutParams(paramas1);
			        
			         EditText pondrunner_name=new EditText(getActivity());
			         pondrunner_name.setText("Manual");
			         pondrunner_name.setTextSize(15);
			         pondrunner_name.setGravity(Gravity.CENTER);
			         
			         //pondrunner_name.setTop(30);
			          
			         pondrunner_name.setBackgroundResource(R.drawable.rounded_shape);
			         LinearLayout.LayoutParams paramas11 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			                 LinearLayout.LayoutParams.WRAP_CONTENT,1);
			          paramas11.setMargins(5, 0, 5, 0);
			         pondrunner_name.setLayoutParams(paramas11);
			         //pondrunner_name.setBackgroundColor(Color.parseColor("#EEEEEE"));

			         TextView total_aerators=new TextView(getActivity());
			         total_aerators.setText("  ");
			         total_aerators.setTextSize(15);
			         total_aerators.setGravity(Gravity.CENTER);
			         //total_aerators.setTop(30);
			         liner_manual.addView(total_aerators);   
			   	        
			        
			        final TextView tv_status=new TextView(getActivity());
			        tv_status.setTextSize(18);
			        tv_status.setGravity(Gravity.CENTER);
			        tv_status.setTextColor(Color.BLACK);
			        tv_status.setPadding(10, 0, 10, 5);  
			         //total_aerators.setTop(30);
			         liner_manual.addView(tv_status); 
			    			      
			         final TextView start=new TextView(getActivity());
			         start.setGravity(Gravity.CENTER);
			         start.setPadding(20, 0, 20, 0); 
					   
					    
					    final TextView tv_empty=new TextView(getActivity());
				        tv_empty.setTextSize(18);
				        tv_empty.setGravity(Gravity.CENTER);
				        tv_empty.setTextColor(Color.BLACK);
				        tv_empty.setPadding(10, 0, 10, 5);  
				        tv_empty.setText("  ");
				       
				        
				        final TextView pause=new TextView(getActivity());
				        pause.setGravity(Gravity.CENTER);
				        pause.setPadding(20, 0, 20, 0); 
					   
					   
					    final TextView tv_empty1=new TextView(getActivity());
				        tv_empty1.setTextSize(18);
				        tv_empty1.setGravity(Gravity.CENTER);
				        tv_empty1.setTextColor(Color.BLACK);
				        tv_empty1.setPadding(10, 0, 10, 5);  
				        tv_empty1.setText("  ");
				       
				        
			         final TextView stop=new TextView(getActivity());
					    stop.setGravity(Gravity.CENTER);
					    stop.setPadding(20, 0, 20, 0);
					    
					    final TextView last_update_time_tv=new TextView(getActivity());
					    last_update_time_tv.setTextSize(15);
					    last_update_time_tv.setGravity(Gravity.CENTER);
					    last_update_time_tv.setTextColor(Color.parseColor("#6CA4CC"));
					    last_update_time_tv.setPadding(10, 0, 10, 5);  
					    
					    SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
						
						  dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
							 Date f_date = dateFormat.parse(schedule_date);
							 
							 final SimpleDateFormat format2 = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
							 format2.setTimeZone(TimeZone.getTimeZone(timezone)); 
							 String schedule_last_date = format2.format(f_date);
							  String current_date = format2.format(System.currentTimeMillis());
							  
					     
							  if(last_update_time.isEmpty()){
								  
							  }else{
								    /*final SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm a",Locale.getDefault());
								     timeformat.setTimeZone(TimeZone.getTimeZone(timezone));
								     Date t_time = timeformat.parse(last_update_time);
								     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm",Locale.getDefault());
									 simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
									 String schedule_last_time = simpleDateFormat.format(t_time);
									 //TimeObject.add_from_time(last_update_time);*/
									 
								     if(current_date.equals(schedule_last_date)){
								    	 last_update_time_tv.setText("Last Updated At: Today  "+last_update_time);
								     }else{
								    	 last_update_time_tv.setText("Last Updated At: "+schedule_last_date+" "+last_update_time); 
								     }  
							  }
					   
					    
					    
					        liner_manual.addView(start);
						    liner_manual.addView(tv_empty); 
						    liner_manual.addView(pause);
						    liner_manual.addView(tv_empty1); 
						    liner_manual.addView(stop);
						    liner_manual.addView(last_update_time_tv);
					    
					    start.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								try{
									if(ar.size()>0){
										 HashMap<String, String> map = new HashMap<String, String>();
										 map = update_list.get(ar.get(0));
										 System.out.println(map);
										 map.put("status", "running");
										  stop.setVisibility(0);
						      			  pause.setVisibility(0);
						      			  start.setVisibility(8);
										 pause.setBackgroundResource(R.drawable.p1);
						      			 stop.setBackgroundResource(R.drawable.stop1);
									}
								
								}catch(Exception e){
									e.printStackTrace();
								}
				      			 
							}
						});
					    pause.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							try{
								if(ar.size()>0){	 
						  HashMap<String, String> map = new HashMap<String, String>();
						 map = update_list.get(ar.get(0));
						 System.out.println(map); 
						 map.put("status", "paused");
						   start.setVisibility(0);
		      				stop.setVisibility(0);
		      				pause.setVisibility(8);
		      				tv_empty.setVisibility(8);
						    start.setBackgroundResource(R.drawable.s1);
		      				stop.setBackgroundResource(R.drawable.stop1);
								}
							}catch(Exception e){
								e.printStackTrace();
							}
							
						}
					    });
				   
                       stop.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							try{
								if(ar.size()>0){
									HashMap<String, String> map = new HashMap<String, String>();
									 map = update_list.get(ar.get(0));
									 System.out.println(map);
									 map.put("status", "completed");
									 start.setBackgroundResource(R.drawable.s1);
									 start.setVisibility(0);
									 stop.setVisibility(8);
									 pause.setVisibility(8);	
								}
						 
							}catch(Exception e){
								e.printStackTrace();
							}
						 }
					     });
                       
					    if(ar.size()>0){
					    	 int get_id=ar.get(0);
				    		 if(get_id==99){
				    			 pause.setKeyListener(null);
				    			 stop.setKeyListener(null);
				    			 start.setKeyListener(null);
				    		 }else{
				    			  pause.setBackgroundResource(R.drawable.p1);
				      			  stop.setBackgroundResource(R.drawable.stop1); 
				      			  //String str=  ApplicationData.get_from_time().toString().trim();
								 // tv_status.setText(" Live "+str);
				      			
				    		 }
					    }
				         
			     
			         t1.addView(liner_manual);
			   			
			   		 }else{
			   			 
			   			// schedules are empty
			   		   final HashMap<String, String> map1 = new HashMap<String, String>();
			   		final LinearLayout tablerow1=new LinearLayout(getActivity());
			   	      tablerow1.setOrientation(LinearLayout.HORIZONTAL);
			   	      LinearLayout.LayoutParams paramas = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			   		                    LinearLayout.LayoutParams.WRAP_CONTENT,1);
			   	      paramas.setMargins(0, 0, 0, 0);
			   	      tablerow1.setLayoutParams(paramas);
			   	   LinearLayout.LayoutParams paramas2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			   	   LinearLayout.LayoutParams.WRAP_CONTENT,1);
			   	    paramas2.setMargins(5, 0, 5, 0);
			   	  
			   	 final EditText fromtime=new EditText(getActivity());
			   	 fromtime.setTextColor(Color.BLACK);
			   	 fromtime.setGravity(Gravity.CENTER);
			   	 fromtime.setFreezesText(true);
			   	 fromtime.setTextSize(15);
			   	 fromtime.setFocusable(false); 
			   	 fromtime.setLayoutParams(paramas2);
			   	 fromtime.setBackgroundResource(R.drawable.rectangle);
			   	 fromtime.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
			   	 
			   	 final EditText totime=new EditText(getActivity());
			   	totime.setTextColor(Color.BLACK);
			   	totime.setGravity(Gravity.CENTER);
			   	totime.setFreezesText(true);
			   	totime.setText("00:00");
			   	totime.setTextSize(15);
			   	totime.setFocusable(false); 
			   	totime.setLayoutParams(paramas2);
			   	totime.setBackgroundResource(R.drawable.rectangle2);
			   	totime.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
			   	 
			     final Calendar calender=new GregorianCalendar(TimeZone.getTimeZone(timezone));
				     final Date date = new Date(calender.getTimeInMillis());
					 final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
					 dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
					 String currenttimestr=dateFormat.format(date).toString().trim();
					 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
					 simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
					 Date ctime = simpleDateFormat.parse(currenttimestr); 
					 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
			         sdf.setTimeZone(TimeZone.getTimeZone(timezone));
					  final String dt2=sdf.format(ctime);
					  fromtime.setText(dt2);
						   	
			   	 
			   	                         final EditText total_feed=new EditText(getActivity());
			   						     total_feed.setText("0");
			   						     total_feed.setTextColor(Color.BLACK);
			   						     LinearLayout.LayoutParams paramas5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			   				                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
			   						 	 paramas5.setMargins(5, 0, 5, 0);
			   						     total_feed.setLayoutParams(paramas5);
			   						     total_feed.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
			   						     total_feed.setGravity(Gravity.CENTER);
			   						     total_feed.setFreezesText(true);
			   						     total_feed.setTextSize(15);
			   						     int maxLength = 4;    
			   						     total_feed.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
			 					     
			   						     
			   						     final EditText ocf=new EditText(getActivity());
			   						     ocf.setText("0");
			   						     ocf.setTextColor(Color.BLACK);
			   						     LinearLayout.LayoutParams paramas6 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			   				                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
			   						 	 paramas6.setMargins(5, 0, 5, 0);
			   						     ocf.setLayoutParams(paramas6);
			   						     ocf.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
			   						     ocf.setGravity(Gravity.CENTER);
			   						     ocf.setFreezesText(true);
			   						     ocf.setTextSize(15);
			   						    	ocf.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
			 					     
			   						     
			   						     final EditText fg=new EditText(getActivity());
			   						     fg.setText("0");
			   						     fg.setTextColor(Color.BLACK);
			   						     LinearLayout.LayoutParams paramas7 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			   				                     LinearLayout.LayoutParams.WRAP_CONTENT,1);
			   						 	 paramas7.setMargins(5, 0, 5, 0);
			   						     fg.setLayoutParams(paramas7);
			   						     fg.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
			   						     fg.setGravity(Gravity.CENTER);
			   						     fg.setFreezesText(true);
			   						     fg.setTextSize(15);
			   						     fg.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
			   				      	 
			   				      		final TextView add=new TextView(getActivity());
			   					         add.setGravity(Gravity.CENTER);
			   						     add.setPadding(10, 0, 5, 0);  
			   						     add.setId(i);
			   						     add.setBackgroundResource(R.drawable.rsub);
			   						    if(add.getId()==0){
			   						    	 add.setBackgroundResource(R.drawable.none);
			   						    	 add.setKeyListener(null);
			   						     }else{
			   						    	 add.setBackgroundResource(R.drawable.rsub);
			   						     }   
			   						   
			   						     
			   						      tablerow1.addView(fromtime);
			   						      tablerow1.addView(totime);
			   						      tablerow1.addView(total_feed);
			   						      tablerow1.addView(ocf);
			   						      tablerow1.addView(fg);
			   						      tablerow1.addView(add);
			   							  t1.addView(tablerow1);
			   							  
			            	 LinearLayout liner_manual=new LinearLayout(getActivity());
			   				          liner_manual.setOrientation(LinearLayout.HORIZONTAL);
			   				         LinearLayout.LayoutParams paramas1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			   				                 LinearLayout.LayoutParams.WRAP_CONTENT,1);
			   				          paramas1.setMargins(0, 0, 0, 0);
			   				         
			   				         liner_manual.setLayoutParams(paramas1);
			   				     
			   				          
			   				         EditText pondrunner_name=new EditText(getActivity());
			   				      LinearLayout.LayoutParams paramas11 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			   				                 LinearLayout.LayoutParams.WRAP_CONTENT,1);
			   				          paramas11.setMargins(5, 0, 5, 0); 
			   				         pondrunner_name.setText("Manual");
			   				         pondrunner_name.setTextSize(15);
			   				         pondrunner_name.setGravity(Gravity.CENTER);
			   				         pondrunner_name.setBackgroundResource(R.drawable.rounded_shape);
			   				         pondrunner_name.setLayoutParams(paramas11);
			   				      
			   				         TextView total_aerators=new TextView(getActivity());
			   				         total_aerators.setText("  ");
			   				         total_aerators.setTextSize(15);
			   				         total_aerators.setGravity(Gravity.CENTER);
			   				         
			   				                  
			   				        
			   				         t1.addView(liner_manual);
			   				       
								   fromtime.setOnClickListener(new OnClickListener() {
										
										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											try{
												 
									            	 Date time=null;
									            	try{
									            		final SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm",Locale.getDefault());
										            	  time=timeformat.parse(fromtime.getText().toString());
										            	  map1.put("from_time", fromtime.getText().toString());
										                  	  
									            	}catch(Exception e){
									            		e.printStackTrace();
									            	}
									            	int hours=0;
													 int minutes=0;
													try{
														String tf=total_feed.getText().toString().trim();
														String df="0".toString().trim();
														String cf=ocf.getText().toString().trim();
														String f_g=fg.getText().toString().trim();
														float f_tf=Float.parseFloat(tf);
														float f_df=Float.parseFloat(df);
														float f_cf=Float.parseFloat(cf);
														float f_fg=Float.parseFloat(f_g);
														float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
													 	float ttl_time = cycles * f_fg;
														int  tt=(int)ttl_time;
														 hours = tt / 60; 
													     minutes = tt % 60;
														//total_time.setText(hours+":"+minutes);
												        String start_time=fromtime.getText().toString();
														String[] separated = start_time.split(":");
														String hour_str=separated[0]; 
														String min_str=separated[1];
														int hour_int=Integer.parseInt(hour_str);
														int mint_int=Integer.parseInt(min_str);
														int hour_endtime=(hour_int+hours);
														int mintue_endtime=(mint_int+minutes);
														totime.setText(hour_endtime+":"+mintue_endtime);
														SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
														 simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
														Date ftime = simpleDateFormat.parse(totime.getText().toString()); 
														SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
													     sdf.setTimeZone(TimeZone.getTimeZone(timezone));
														String stime=sdf.format(ftime);
														totime.setText(stime);
														 map1.put("to_time", totime.getText().toString());
													}catch(Exception e){
														e.printStackTrace();
													}					               
								                    final int fhour =time.getHours();
								                   	final  int fminute = time.getMinutes();
								                
				                                OnTimeSetListener timesetlistener=new OnTimeSetListener() {
														
														@Override
														public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
															// TODO Auto-generated method stub
														String h1,m1;
								                         
								                         if(String.valueOf(hourOfDay).length()==1){
								                        	   h1=("0"+String.valueOf(hourOfDay));
								                        	 
								                         }else{
								                        	   h1=String.valueOf(hourOfDay);
								                         }
								                         
								                         if((String.valueOf(minute).length()==1)){
								                        	 m1=("0"+String.valueOf(minute));
								                         }else{
								                        	 m1=(String.valueOf(minute)); 
								                         }
									                   
								                         fromtime.setText(h1 + ":" +m1);
								                         map1.put("from_time", fromtime.getText().toString());
								                         try{
								                         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
								 						simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
								 						Date ftime = simpleDateFormat.parse(fromtime.getText().toString()); 
								                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
								 				        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
								 						 String stime=sdf.format(ftime);
								                         if(dt2.compareTo(stime)<=0){
								                        	  
								                         }else{
								                        	  
								   							 
								   							 final SimpleDateFormat format2 = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
								   							 format2.setTimeZone(TimeZone.getTimeZone(timezone)); 
								   							 String selected_date = start_date.getText().toString().trim();
								   							  String current_date = format2.format(System.currentTimeMillis());
								   							if(selected_date.equals(current_date)){
								   								fromtime.setText(dt2);
									                        	 Toast.makeText(getActivity(), "Past Time not Allowed", Toast.LENGTH_SHORT).show();
								   							}
								                        	 
								                         }
								                         
								                         }catch(Exception e){
								                        	 e.printStackTrace();
								                         }
								                         int hours=0;
														 int minutes=0;
														try{
															String tf=total_feed.getText().toString().trim();
															String df="0".toString().trim();
															String cf=ocf.getText().toString().trim();
															String f_g=fg.getText().toString().trim();
															float f_tf=Float.parseFloat(tf);
															float f_df=Float.parseFloat(df);
															float f_cf=Float.parseFloat(cf);
															float f_fg=Float.parseFloat(f_g);
															float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
														 	float ttl_time = cycles * f_fg;
															int  tt=(int)ttl_time;
															 hours = tt / 60; 
														     minutes = tt % 60;
															//total_time.setText(hours+":"+minutes);
													        String start_time=fromtime.getText().toString();
															String[] separated = start_time.split(":");
															String hour_str=separated[0]; 
															String min_str=separated[1];
															int hour_int=Integer.parseInt(hour_str);
															int mint_int=Integer.parseInt(min_str);
															int hour_endtime=(hour_int+hours);
															int mintue_endtime=(mint_int+minutes);
															totime.setText(hour_endtime+":"+mintue_endtime);
															SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
															 simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
															Date ftime = simpleDateFormat.parse(totime.getText().toString()); 
															SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
														     sdf.setTimeZone(TimeZone.getTimeZone(timezone));
															String stime=sdf.format(ftime);
															totime.setText(stime);
															 map1.put("to_time", totime.getText().toString());
														}catch(Exception e){
															e.printStackTrace();
														}
														}
													};
													TimePickerDialog tpd=new TimePickerDialog(getActivity(), timesetlistener, fhour, fminute,  DateFormat.is24HourFormat(getActivity()));
													tpd.show();
									            	
											}catch(Exception e){
												e.printStackTrace();
											}
											
										}
									});
								   total_feed.addTextChangedListener(new TextWatcher() {
										
										@Override
										public void onTextChanged(CharSequence s, int start, int before, int count) {
											// TODO Auto-generated method stub
											
										}
										
										@Override
										public void beforeTextChanged(CharSequence s, int start, int count, int after) {
											// TODO Auto-generated method stub
											
										}
										
										@Override
										public void afterTextChanged(Editable s) {
											// TODO Auto-generated method stub
											 map1.put("total_feed", s.toString());
											 int hours=0;
											 int minutes=0;
											try{
												String tf=total_feed.getText().toString().trim();
												String df="0".toString().trim();
												String cf=ocf.getText().toString().trim();
												String f_g=fg.getText().toString().trim();
												float f_tf=Float.parseFloat(tf);
												float f_df=Float.parseFloat(df);
												float f_cf=Float.parseFloat(cf);
												float f_fg=Float.parseFloat(f_g);
												float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
											 	float ttl_time = cycles * f_fg;
												int  tt=(int)ttl_time;
												 hours = tt / 60; 
											     minutes = tt % 60;
												//total_time.setText(hours+":"+minutes);
										        String start_time=fromtime.getText().toString();
												String[] separated = start_time.split(":");
												String hour_str=separated[0]; 
												String min_str=separated[1];
												int hour_int=Integer.parseInt(hour_str);
												int mint_int=Integer.parseInt(min_str);
												int hour_endtime=(hour_int+hours);
												int mintue_endtime=(mint_int+minutes);
												totime.setText(hour_endtime+":"+mintue_endtime);
												SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
												 simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
												Date ftime = simpleDateFormat.parse(totime.getText().toString()); 
												SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
											     sdf.setTimeZone(TimeZone.getTimeZone(timezone));
												String stime=sdf.format(ftime);
												totime.setText(stime);
												 map1.put("to_time", totime.getText().toString());
											}catch(Exception e){
												e.printStackTrace();
											}
											 
										}
									});	
								     ocf.addTextChangedListener(new TextWatcher() {
										
										@Override
										public void onTextChanged(CharSequence s, int start, int before, int count) {
											// TODO Auto-generated method stub
											
										}
										
										@Override
										public void beforeTextChanged(CharSequence s, int start, int count, int after) {
											// TODO Auto-generated method stub
											
										}
										
										@Override
										public void afterTextChanged(Editable s) {
											// TODO Auto-generated method stub
											  map1.put("ocf", s.toString());
											  int hours=0;
												 int minutes=0;
												try{
													String tf=total_feed.getText().toString().trim();
													String df="0".toString().trim();
													String cf=ocf.getText().toString().trim();
													String f_g=fg.getText().toString().trim();
													float f_tf=Float.parseFloat(tf);
													float f_df=Float.parseFloat(df);
													float f_cf=Float.parseFloat(cf);
													float f_fg=Float.parseFloat(f_g);
													float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
												 	float ttl_time = cycles * f_fg;
													int  tt=(int)ttl_time;
													 hours = tt / 60; 
												     minutes = tt % 60;
													//total_time.setText(hours+":"+minutes);
											        String start_time=fromtime.getText().toString();
													String[] separated = start_time.split(":");
													String hour_str=separated[0]; 
													String min_str=separated[1];
													int hour_int=Integer.parseInt(hour_str);
													int mint_int=Integer.parseInt(min_str);
													int hour_endtime=(hour_int+hours);
													int mintue_endtime=(mint_int+minutes);
													totime.setText(hour_endtime+":"+mintue_endtime);
													SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
													 simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
													Date ftime = simpleDateFormat.parse(totime.getText().toString()); 
													SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
												     sdf.setTimeZone(TimeZone.getTimeZone(timezone));
													String stime=sdf.format(ftime);
													totime.setText(stime);
													 map1.put("to_time", totime.getText().toString());
												}catch(Exception e){
													e.printStackTrace();
												}
										}
									});
								     fg.addTextChangedListener(new TextWatcher() {
										
										@Override
										public void onTextChanged(CharSequence s, int start, int before, int count) {
											// TODO Auto-generated method stub
											
										}
										
										@Override
										public void beforeTextChanged(CharSequence s, int start, int count, int after) {
											// TODO Auto-generated method stub
											
										}
										
										@Override
										public void afterTextChanged(Editable s) {
											// TODO Auto-generated method stub
											  map1.put("fg", s.toString());
											  int hours=0;
												 int minutes=0;
												try{
													String tf=total_feed.getText().toString().trim();
													String df="0".toString().trim();
													String cf=ocf.getText().toString().trim();
													String f_g=fg.getText().toString().trim();
													float f_tf=Float.parseFloat(tf);
													float f_df=Float.parseFloat(df);
													float f_cf=Float.parseFloat(cf);
													float f_fg=Float.parseFloat(f_g);
													float cycles = ((f_tf - f_df) * 1000 )/ f_cf;
												 	float ttl_time = cycles * f_fg;
													int  tt=(int)ttl_time;
													 hours = tt / 60; 
												     minutes = tt % 60;
													//total_time.setText(hours+":"+minutes);
											        String start_time=fromtime.getText().toString();
													String[] separated = start_time.split(":");
													String hour_str=separated[0]; 
													String min_str=separated[1];
													int hour_int=Integer.parseInt(hour_str);
													int mint_int=Integer.parseInt(min_str);
													int hour_endtime=(hour_int+hours);
													int mintue_endtime=(mint_int+minutes);
													totime.setText(hour_endtime+":"+mintue_endtime);
													SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
													 simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
													Date ftime = simpleDateFormat.parse(totime.getText().toString()); 
													SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
												     sdf.setTimeZone(TimeZone.getTimeZone(timezone));
													String stime=sdf.format(ftime);
													totime.setText(stime);
													 map1.put("to_time", totime.getText().toString());
												}catch(Exception e){
													e.printStackTrace();
												}
										}
									});
			   				      		
								       String to_be_run="to_be_run".toString().trim();
				   				       map1.put("schedule_id", "0");
									   map1.put("from_time", fromtime.getText().toString());
									   map1.put("to_time", totime.getText().toString());
									   map1.put("total_feed", total_feed.getText().toString());
									   map1.put("ocf", ocf.getText().toString());
									   map1.put("fg", fg.getText().toString());
									   map1.put("totalTime","00:00");
									   map1.put("kg_feed_disp_time", "0");
									   map1.put("mode", "79");
									   map1.put("status", to_be_run);
									   map1.put("dispensed_feed", "0");
									   update_list.add(map1);
			   		 }
			   		
				 }catch(Exception e){
					 e.printStackTrace();
				 }
			 }
		}
	
		 

		protected void RemoveData(){
     try{
    	 if(update_list.size()>0){
		   		add_mylist.clear();
		   		get_mylist.clear();
		   	  for(int i=0;i<update_list.size();i++){
					 HashMap<String, String> map=update_list.get(i); 
					 HashMap<String, String> map1=new HashMap<String, String>();
					    String schedule_id= map.get("schedule_id").toString().trim();
					    String from_time= map.get("from_time").toString().trim();
					    String to_time=map.get("to_time").toString().trim();
					    String total_feed=map.get("total_feed").toString().trim();
					    String ocf=map.get("ocf").toString().trim();
					    String fg=map.get("fg").toString().trim();
					    String totalTime=map.get("totalTime").toString().trim();
					    String kg_feed_disp_time=map.get("kg_feed_disp_time").toString().trim();
					    String mode=map.get("mode").toString().trim();
					    String status=map.get("status").toString().trim();
					    String dispensed_feed=map.get("dispensed_feed").toString().trim();
					   
					      map1.put("schedule_id", schedule_id);
						  map1.put("schedule_times", from_time+"-"+to_time);
						  map1.put("original_feed", total_feed);
						  map1.put("one_cycle_feed", ocf);
						  map1.put("feed_gap", fg);
						  map1.put("totalTime", totalTime);
						  map1.put("kg_feed_disp_time", kg_feed_disp_time);
						  map1.put("mode", mode);
						  map1.put("status", status);
						  map1.put("dispensed_feed", dispensed_feed);
						  add_mylist.add(map1);
				 }
		   	   ApplicationData.add_remove_list(add_mylist);
		           scheduleMode();
		   	  
		   	 }
     }catch(Exception e){
    	 e.printStackTrace();
     }
		   	
		 }
		 
		 protected void AddData() {
			 if(update_list.size()>7){
					Toast.makeText(getActivity(), " Only 8 Schedules are Allowed Per Day ", Toast.LENGTH_SHORT).show();	
				}else{
					 
				   	 if(update_list.size()>0){
				   		add_mylist.clear();
				   		get_mylist.clear();
				   	  for(int i=0;i<update_list.size();i++){
							 HashMap<String, String> map=update_list.get(i); 
							 HashMap<String, String> map1=new HashMap<String, String>();
							    String schedule_id= map.get("schedule_id").toString().trim();
							    String from_time= map.get("from_time").toString().trim();
							    String to_time=map.get("to_time").toString().trim();
							    String total_feed=map.get("total_feed").toString().trim();
							    String ocf=map.get("ocf").toString().trim();
							    String fg=map.get("fg").toString().trim();
							    String totalTime=map.get("totalTime").toString().trim();
							    String kg_feed_disp_time=map.get("kg_feed_disp_time").toString().trim();
							    String mode=map.get("mode").toString().trim();
							    String status=map.get("status").toString().trim();
							    String dispensed_feed=map.get("dispensed_feed").toString().trim();
							   
							      map1.put("schedule_id", schedule_id);
								  map1.put("schedule_times", from_time+"-"+to_time);
								  map1.put("original_feed", total_feed);
								  map1.put("one_cycle_feed", ocf);
								  map1.put("feed_gap", fg);
								  map1.put("totalTime", totalTime);
								  map1.put("kg_feed_disp_time", kg_feed_disp_time);
								  map1.put("mode", mode);
								  map1.put("status", status);
								  map1.put("dispensed_feed", dispensed_feed);
								  add_mylist.add(map1);
						 }
				   	  try{
				   	  HashMap<String, String> addmap=new HashMap<String, String>();
				   	    final Calendar calender=new GregorianCalendar(TimeZone.getTimeZone(timezone));
					    final Date date = new Date(calender.getTimeInMillis());
						final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
						String currenttimestr=dateFormat.format(date).toString().trim();
					    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
						simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
					    Date ctime = simpleDateFormat.parse(currenttimestr); 
					    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
				        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
					    String cdt=sdf.format(ctime);
					    /*
					     * map1.put("schedule_id", schedule_id);
  				map1.put("schedule_times", schedule_times);
  				map1.put("totalTime", totalTime);
  				map1.put("original_feed", original_feed);
  				map1.put("one_cycle_feed", one_cycle_feed);
  				map1.put("feed_gap", feed_gap);
  				map1.put("kg_feed_disp_time", kg_feed_disp_time);
  				map1.put("mode", mode);
  				map1.put("status", status);
  				map1.put("dispensed_feed", dispensed_feed);
					     */
					    String to_be_run="to_be_run".toString().trim();
				   	addmap.put("schedule_id", "0");
				   	addmap.put("schedule_times", cdt+"-"+"00:00");
				   	addmap.put("original_feed", "0");
				   	addmap.put("one_cycle_feed", "0");
				   	addmap.put("feed_gap", "0");
				   	addmap.put("totalTime", "0");
				   	addmap.put("kg_feed_disp_time", "0");
				   	addmap.put("mode", "79"); 
				   	addmap.put("status", to_be_run);
				   	addmap.put("dispensed_feed", "0");
				    add_mylist.add(addmap);
				    ApplicationData.add_remove_list(add_mylist);
				    scheduleMode();
				   	  }catch(Exception e){
				   		  e.printStackTrace();
				   	  }
				   	  
				   	 }else{
				   		 try{
				   			 
				   		 }catch(Exception e){
				   			 e.printStackTrace();
				   		 }
				   	 }
				}
		 }
		 public class Update2 extends AsyncTask<String, Void, Void> {		
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
	               	build.setTitle("Update");
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
							 total_feed_array.clear();
							    one_cycle_feed_array.clear();
							    feed_gap_array.clear();
							    status_array.clear();
							    schedule_id_array.clear();
							    totalTime_array.clear(); 
							    mode_array.clear();
							    fromTime_array.clear();
							    toTime_array.clear();
							    onTime_array.clear();
							    offTime_array.clear();
							    //get_mylist.clear();
							build.setMessage(error);
							build.setPositiveButton("OK",
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
								JSONObject jsn2 = new JSONObject(error);
								String response2 = jsn2.getString("response");
								total_feed_array.clear();
							    one_cycle_feed_array.clear();
							    feed_gap_array.clear();
							    status_array.clear();
							    schedule_id_array.clear();
							    totalTime_array.clear(); 
							    mode_array.clear();
							    fromTime_array.clear();
							    toTime_array.clear();
							    onTime_array.clear();
							    offTime_array.clear();
							    update_list.clear();
							    get_mylist.clear();
							 	build.setMessage(response2);
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
													 getActivity().finish();
												  				
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
						try {	
							   
						       String user_id = ApplicationDetails.getString("user_id", null);
						       String feederSno=   ApplicationData.get_feederSno().toString().trim();
				        		
							try{
								 JSONObject loginJson = new JSONObject();
								 loginJson.put("user_id", user_id);
								 loginJson.put("feederSno", feederSno);
								
								 final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
								  dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
									 Date f_date = dateFormat.parse(start_date.getText().toString());
									 Date t_date = dateFormat.parse(end_date.getText().toString());
									 SimpleDateFormat format2  = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
									 format2.setTimeZone(TimeZone.getTimeZone(timezone)); 
									 String schedule_start_date = format2.format(f_date);
									 String schedule_end_date = format2.format(t_date);
								 loginJson.put("schedule_date",schedule_start_date);	
								 loginJson.put("schedule_end_date",schedule_end_date);
								 
								 for(int i=0; i<update_list.size(); i++) {
							   		HashMap<String, String> map = update_list.get(i);
							   		String schedule_id= map.get("schedule_id").toString().trim();
								    String from_time= map.get("from_time").toString().trim();
								    String to_time= map.get("to_time").toString().trim();
								    String total_feed=map.get("total_feed").toString().trim();
								    String ocf=map.get("ocf").toString().trim();
								    System.out.println(ocf);
								    String fg=map.get("fg").toString().trim();
								    String totalTime=map.get("totalTime").toString().trim();
								    //String kg_feed_disp_time=map.get("kg_feed_disp_time").toString().trim();
								    String mode=map.get("mode").toString().trim();
								    String status=map.get("status").toString().trim();
								   // String dispensed_feed=map.get("dispensed_feed").toString().trim();   		 
								   
									 total_feed_array.add("\"" + total_feed + "\"");
								 	 loginJson.put("original_feed", total_feed_array);
								 	 
								 	 one_cycle_feed_array.add("\"" + ocf + "\"");
									 loginJson.put("one_cycle_feed",one_cycle_feed_array);
									 
									 feed_gap_array.add("\"" + fg + "\"");
									 loginJson.put("feed_gap",feed_gap_array);	
									 
									 status_array.add("\"" + status + "\"");
									 loginJson.put("status",status_array);
								
									 schedule_id_array.add("\"" + schedule_id + "\"");
									 loginJson.put("schedule_id",schedule_id_array);	
								
									 totalTime_array.add("\"" + totalTime + "\"");
									 loginJson.put("total_time",totalTime_array);	
									 
									 mode_array.add("\"" + mode + "\"");
									 loginJson.put("mode",mode_array);
									 
									 fromTime_array.add("\"" + from_time + "\"");
									 loginJson.put("from_time",fromTime_array);	
								
									 toTime_array.add("\"" +to_time + "\"");
									 loginJson.put("to_time",toTime_array);
																	
									 onTime_array.add("\"" + "00:00" + "\"");
									 loginJson.put("on_time",onTime_array);
									
									 offTime_array.add("\"" + "00:00" + "\"");
									 loginJson.put("off_time",offTime_array);
								 
								 }
													 
							 HttpParams httpParams = new BasicHttpParams();
						     HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT_MILLISEC);
						     HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
												       
							 DefaultHttpClient httpclient = new MyHttpsClient(httpParams,getActivity());
							 HttpPost httppost = new HttpPost(Config.URL_Update);
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
		 
		 private void createDate() {
				final Calendar calender = Calendar.getInstance();
				final Date date = new Date(calender.getTimeInMillis());
				mStartDay = calender.get(Calendar.DATE);
				mStartMonth = calender.get(Calendar.MONTH);
				mStartYear = calender.get(Calendar.YEAR);
				final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
			    dateFormat.setTimeZone(TimeZone.getTimeZone(timezone)); 
				if (start_date != null) {
					start_date.setText(dateFormat.format(date));
					 
					}
		}	 
		 private void createDate2() {
				final Calendar calender = Calendar.getInstance();
				final Date date = new Date(calender.getTimeInMillis());
				mEndDay = calender.get(Calendar.DATE);
				mEndMonth = calender.get(Calendar.MONTH);
				mEndYear = calender.get(Calendar.YEAR);
				final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
			    dateFormat.setTimeZone(TimeZone.getTimeZone(timezone)); 
				if (end_date != null) {
					 
					end_date.setText(dateFormat.format(date));
					}
		}	 
		 private void startdialog() {
			  DatePickerStartFragment date = new DatePickerStartFragment();
			 
			  try{
			  final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
			  dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
			  String from_date=start_date.getText().toString().trim();
				 Date f_date = dateFormat.parse(from_date);
				 SimpleDateFormat format2  = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
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
			  date.setCallBack(ondate);
			  date.show(getChildFragmentManager(), "Date Picker");
			  
			  }catch(Exception e){
				  e.printStackTrace();
			  }
			 }

			 OnDateSetListener ondate = new OnDateSetListener() {
			  @Override
			  public void onDateSet(DatePicker view, int selectedYear, int selectedMonth,int selectedDay) {
				  try{
					// TODO Auto-generated method stub
			        	final Calendar c = Calendar.getInstance();
						 c.set(selectedYear, selectedMonth, selectedDay);
						mStartDay = selectedDay;
						mStartMonth = selectedMonth;
						mStartYear = selectedYear;
						final Calendar start = Calendar.getInstance();
						start.set(mStartYear, mStartMonth, mStartDay, 0, 0, 0);
						//long dt = start.getTimeInMillis(); 
						
						 final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
						 dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
						 try{
							    SimpleDateFormat dfDate  = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
							    dfDate.setTimeZone(TimeZone.getTimeZone(timezone));
							    java.util.Date d = null;
							    java.util.Date d1 = null;
							    
								final Calendar end = Calendar.getInstance();
							 	final Date date2 = new Date(end.getTimeInMillis());
							 	final Date date = new Date(start.getTimeInMillis());
								 
							 	/*if(date.before(date2)){
							 		Toast.makeText(getActivity(), "past date should not be allowed", Toast.LENGTH_SHORT).show();
							 		txtStockEntryDate.setText(dateFormat.format(d1));
							 	
							 	}*/
							    String str_date=start_date.getText().toString().trim();
							    Calendar cal = Calendar.getInstance();
							    try {
							            d = dfDate.parse(str_date);
							            d1 = dfDate.parse(dfDate.format(cal.getTime())); 
							        } catch (java.text.ParseException e) {
							            e.printStackTrace();
							        }
							   			    
							    int diffInDays = (int)((d1.getTime() - date.getTime())/ (1000 * 60 * 60 * 24));
							    int compare=-7;
							   	if(diffInDays>compare){
							   		start_date.setText(dateFormat.format(date));
							   		//LoadData(); 	 
							    	}else{
							    		start_date.setText(dateFormat.format(d1));
							    	Toast.makeText(getActivity(),R.string.daterange, Toast.LENGTH_SHORT).show();
							        }
							  
							    	 
						 }catch(Exception e){
							 e.printStackTrace();
						 }
						
				  }catch(Exception e){
					  e.printStackTrace(); 
				  }
			  }
			 };
			 private void enddialog() {
				  DatePickerEndFragment date = new DatePickerEndFragment();
				  try{
					  final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
					  dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
					  String from_date=end_date.getText().toString().trim();
						 Date f_date = dateFormat.parse(from_date);
						 SimpleDateFormat format2  = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
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
					  date.setCallBack(ondate1);
					  date.show(getChildFragmentManager(), "Date Picker");
					 
					  }catch(Exception e){
						  e.printStackTrace();
					  }
				  
				 }
			 OnDateSetListener ondate1 = new OnDateSetListener() {
				  @Override
				  public void onDateSet(DatePicker view, int selectedYear, int selectedMonth,int selectedDay) {
					  try{
							// TODO Auto-generated method stub
					        	final Calendar c = Calendar.getInstance();
								 c.set(selectedYear, selectedMonth, selectedDay);
								mEndDay = selectedDay;
								mEndMonth = selectedMonth;
								mEndYear = selectedYear;
								final Calendar start = Calendar.getInstance();
								start.set(mEndYear, mEndMonth, mEndDay, 0, 0, 0);
								//long dt = start.getTimeInMillis(); 
								
								 final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
								             dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
								 try{
									    SimpleDateFormat dfDate  = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
									    dfDate.setTimeZone(TimeZone.getTimeZone(timezone));
									    java.util.Date d = null;
									    java.util.Date d1 = null;
									    
										//final Calendar end = Calendar.getInstance();
									 	//final Date date2 = new Date(end.getTimeInMillis());
									 	final Date date = new Date(start.getTimeInMillis());
									 	
									    String str_date=end_date.getText().toString().trim();
									    Calendar cal = Calendar.getInstance();
									    try {
									            d = dfDate.parse(str_date);
									            d1 = dfDate.parse(dfDate.format(cal.getTime())); 
									        } catch (java.text.ParseException e) {
									            e.printStackTrace();
									        }
									   			    
									    int diffInDays = (int)((d1.getTime() - date.getTime())/ (1000 * 60 * 60 * 24));
									    int compare=-7;
									   	if(diffInDays>compare){
									   		end_date.setText(dateFormat.format(date));
									   		//LoadData(); 	 
									    	}else{
									    		end_date.setText(dateFormat.format(d1));
									    	Toast.makeText(getActivity(),"allowed only 7 days", Toast.LENGTH_SHORT).show();
									        }
									   
									    	 
								 }catch(Exception e){
									 e.printStackTrace();
								 }
								
						  }catch(Exception e){
							  e.printStackTrace();
						  }
							     
				  }       
				 };
				 
				 // get single feeder data
				 public class GetSingleFeederData extends AsyncTask<String, Void, Void> {
						
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
									AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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
				           					 String mode1="78".toString().trim();
				           				  	         String mode2="79".toString().trim();
				           				  	         
				           				  	         //display schedule mode and basic mode
				           				  	          if(mode.equals(mode1)){
				           				  	        	  liner.setVisibility(View.VISIBLE);
				           				  	        	  liner_table.setVisibility(View.GONE);
				           				  	            //Basic Mode
				     	           						 basicMode(); 
				           				  	          }else if(mode.equals(mode2)){
				           				  	        	  liner.setVisibility(View.GONE);
				           				  	        	  liner_table.setVisibility(View.VISIBLE);
				           				  	        	  //SChedule Mode
				           				  	       
				           				  	        	  //DisplayShow();
				           				  	        	  scheduleMode();
				           				  	   	        tv.setVisibility(View.VISIBLE);
				           				  	          }else{
				           				  	           	build.setTitle("Mode");
				           				  	        	build.setMessage("Mode is Empty , Please Set Mode in Settings Page");
				           								build.setPositiveButton("OK",
				           										new DialogInterface.OnClickListener() {
				           											public void onClick(
				           													DialogInterface dialog,
				           													int which) {
				           												dialog.cancel();
				           												 
				           												
				           											}
				           										});
				           								build.show();   
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
						 Toast.makeText(getActivity(), "no response from server please try again", Toast.LENGTH_SHORT).show();
					 }
									
					}
				@Override
					protected Void doInBackground(String... params) {
						// TODO Auto-generated method stub
						
							try {					
							   String user_id = ApplicationDetails.getString("user_id", null);
						       String feederSno=   ApplicationData.get_feederSno().toString().trim();
						       final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
								  dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
									 Date f_date = dateFormat.parse(start_date.getText().toString());
									 Date t_date = dateFormat.parse(end_date.getText().toString());
									 SimpleDateFormat format2  = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
									 format2.setTimeZone(TimeZone.getTimeZone(timezone)); 
									 String schedule_start_date = format2.format(f_date);
									 String schedule_end_date = format2.format(t_date);
								try{ 
								Log.i(getClass().getSimpleName(), "sending  task - started");
								JSONObject loginJson = new JSONObject();
								loginJson.put("user_id", user_id);
								loginJson.put("feederSno", feederSno);
								loginJson.put("schedule_start_date",schedule_start_date);
								
								HttpParams httpParams = new BasicHttpParams();
								HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT_MILLISEC);
								HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
							    DefaultHttpClient httpclient = new MyHttpsClient(httpParams,getActivity());
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
