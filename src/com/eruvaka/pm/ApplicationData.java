package com.eruvaka.pm;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

public class ApplicationData {
	
	 
	private static String mregentity="novalue";
	public static void addregentity(String result) {
		// TODO Auto-generated method stub
		mregentity = result;
	}
	public static String getregentity() {
		return mregentity;
	}
	private static String mstatus="run";
	public static void addstatus(String to_be_run) {
		// TODO Auto-generated method stub
		mstatus=to_be_run;
	}
	public static String getstatus(){
		return mstatus;
	}
	private static long maddmill = 0;
	public static void addmill(long mill) {
		// TODO Auto-generated method stub
		maddmill = mill;
	}

	public static long getmill() {
		// TODO Auto-generated method stub
		return maddmill;
	}
	private static  String mphno="9999999999";
	public static void add_phno(String prphno) {
		// TODO Auto-generated method stub
		mphno=prphno;
	}
	private static String mchecked=null;
	  public static void addFeedChecked(String feedcheck) {
		// TODO Auto-generated method stub
		mchecked=feedcheck;
	  }
	 public static String getFeedChecked(){
		return mchecked;
	 }
	public static String get_phno() {
		// TODO Auto-generated method stub
		return mphno;
	}
	private static String regentity2=null;
	public static void addregentity2(String entitresult) {
		// TODO Auto-generated method stub
		regentity2 = entitresult;
	}
	public static String getregentity2() {
		return regentity2;
	}
	private static String mone="999";
	public static void add_reg(String one) {
		// TODO Auto-generated method stub
		mone=one;
	}

	public static String get_reg() {
		// TODO Auto-generated method stub
		return mone;
	}
	private static String mfeedesno="999";
	public static void add_feeder_Sno(String feederSno) {
		// TODO Auto-generated method stub
		mfeedesno=feederSno;
	}
	public static String get_feederSno() {
		// TODO Auto-generated method stub
		return mfeedesno;
	}
	 private static String mdisfeedtotal=null;
		public static void adddisfeedtotal(String str2) {
			// TODO Auto-generated method stub
			mdisfeedtotal=str2;
		}

		public static String getdisfeedtotal() {
			// TODO Auto-generated method stub
			return mdisfeedtotal;
		}
	private static String mfeedername="eruvaka";
	public static void add_feeder_Name(String feederName) {
		// TODO Auto-generated method stub
		mfeedername=feederName;
	}
	public static String get_feederName() {
		// TODO Auto-generated method stub
		return mfeedername;
	}
	private static String mkg_disp_time="99";
	public static void add_kg_feed_disp_Time(String kg_feed_disp_time_all) {
		// TODO Auto-generated method stub
		mkg_disp_time=kg_feed_disp_time_all;
	}
	public static String get_kg_disp_time() {
		// TODO Auto-generated method stub
		return mkg_disp_time;
	}
	private static String mmode="99";
	public static void add_mode(String modee) {
		// TODO Auto-generated method stub
		mmode=modee;
	}
	public static String get_mode() {
		// TODO Auto-generated method stub
		return mmode;
	}
	 private static String mstr=null;
		public static void addfeedtotal(String str) {
			// TODO Auto-generated method stub
			mstr=str;
		}

		public static String getfeedtotal() {
			// TODO Auto-generated method stub
			return mstr;
		}
	private static String mfeederId="99";
	public static void add_feeder_Id(String feederId) {
		// TODO Auto-generated method stub
		mfeederId=feederId;
	}
	public static String get_feedeerId() {
		// TODO Auto-generated method stub
		return mfeederId;
	}
	/* private static ArrayList<HashMap<String, String>> mmylist = new ArrayList<HashMap<String, String>>();
	public static void add_mylist(ArrayList<HashMap<String, String>> mylist) {
		// TODO Auto-generated method stub
		mmylist= mylist;
	}
	public static ArrayList<HashMap<String, String>> get_mylist() {
		// TODO Auto-generated method stub
		return mmylist;
	}*/
	private static String m_mode="99";
	public static void add_compare_mode(String mode) {
		// TODO Auto-generated method stub
		m_mode=mode;
	}
	public static String get_compare_mode() {
		// TODO Auto-generated method stub
		return m_mode;
	}
	private static ArrayList<HashMap<String, String>> remove_mylist = new ArrayList<HashMap<String, String>>();
	public static void add_remove_list(ArrayList<HashMap<String, String>> remove_list) {
		// TODO Auto-generated method stub
		remove_mylist=remove_list;
	}
	public static ArrayList<HashMap<String, String>> get_removelist() {
		// TODO Auto-generated method stub
		return remove_mylist;
	}
	private static JSONArray mjsonarray=new JSONArray();
	public static void add_jsonarray(JSONArray jsonArray) {
		// TODO Auto-generated method stub
		mjsonarray=jsonArray;
	}
	public static JSONArray get_jsonarray() {
		// TODO Auto-generated method stub
		return mjsonarray;
	}
	/*private static int mposition=99;
	public static void add_position(int j) {
		// TODO Auto-generated method stub
		mposition=j;
	}
	public static Integer getPosition(){
		return mposition;
	}
	private static String mfrom_time;
	public static void add_fromtime(String string) {
		// TODO Auto-generated method stub
		mfrom_time=string;
	}
	public static String get_from_time() {
		// TODO Auto-generated method stub
		return mfrom_time;
	}*/
	 
	 
}
