package com.eruvaka.pm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.content.SharedPreferences;

public class TimeObject {
      static String from_time="00:00";
      String to_time="00:00";
      
   // Shared Preferences reference
      static SharedPreferences pref;

      // Editor reference for Shared preferences
      SharedPreferences.Editor editor;
      // Context
      Context _context;

      // Shared preferences mode
      int PRIVATE_MODE = 0;
      // Shared preferences file name
      public static final String PREFER_NAME = "Reg";
      // Constructor
      public TimeObject(Context context){
          this._context = context;
          pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
          editor = pref.edit();
      }
  
	public static String get_Time() {
		// TODO Auto-generated method stub
		return from_time;
	}
	public static void   add_from_time(String fromtime2) {
		// TODO Auto-generated method stub
		try{
			String sp[]=fromtime2.split(":");
			 String sp1=sp[0];
			 String sp2=sp[1];
			 String sp3 = sp2.replaceAll("[^0-9.]", "");
			 String sp4 = sp2.replaceAll("[^a-zA-Z.]", "");
			 String ts=sp1+":"+sp3;
			 
			String stime="01/07/2015"+ "\t"+ts+"\t"+sp4;
			System.out.println(stime);
			Date date = new Date(stime);
			  SimpleDateFormat sdf=new SimpleDateFormat("HH:mm",Locale.getDefault());
			 Date t_time = sdf.parse(ts);
		    // String timezone= pref.getString("timezone", null); 
	        
	       //  sdf.setTimeZone(TimeZone.getTimeZone(timezone));
			   String str=sdf.format(t_time);
			   System.out.println(str);
			   from_time=str;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void add_to_time(String totime2) {
		// TODO Auto-generated method stub
		try{
			String sp[]=totime2.split(":");
			 String sp1=sp[0];
			 String sp2=sp[1];
			 String sp3 = sp2.replaceAll("[^0-9.]", "");
			 String sp4 = sp2.replaceAll("[^a-zA-Z.]", "");
			 String ts=sp1+":"+sp3;
			 
			 String stime="01/07/2015"+ "\t"+ts+"\t"+sp4;
			Date date = new Date(stime);
			 String timezone= pref.getString("timezone", null); 
	          SimpleDateFormat sdf=new SimpleDateFormat("HH:mm",Locale.getDefault());
	          sdf.setTimeZone(TimeZone.getTimeZone(timezone));
			   String str=sdf.format(date);
			   to_time=str;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String get_To_Time() {
		// TODO Auto-generated method stub
		return to_time;
	}

}
