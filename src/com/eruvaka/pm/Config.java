package com.eruvaka.pm;

public class Config {
	
	    // server URL configuration
	    //testserver
	   // public static final String URL="52.77.24.190/eruvaka_live".toString();
	    public static final String URL="eruvaka.com".toString();
	    public static final String URL_KEY_LOGIN  = "http://"+URL+"/mobile/pondmother_basicmodes/login";
	    public static final String URL_FEEDER_DATA = "http://"+URL+"/mobile/pondmother_basicmodes/getfeeders";
	    public static final String URL_GET_Single_Feeder = "http://"+URL+"/mobile/pondmother_basicmodes/get_singlefeeder";
	    public static final String URL_FeederLogs  ="http://"+URL+"/mobile/pondmother_basicmodes/feederlogs";
	    public static final String URL_Update ="http://"+URL+"/mobile/pondmother_basicmodes/updateSchedules";
	    public static final String URL_UpdateSettings  ="http://"+URL+"/mobile/pondmother_basicmodes/updatesettings";
	    public static final String URL_UPDATE_USER_PROFILE = "http://"+URL+"/mobile/pondmother_basicmodes/updateprofile";	  
	    //login details
	    public static final String URL_REQUEST_SMS = "http://"+URL+"/mobile/pondmother/register";
	    public static final String URL_VERIFY_OTP = "http://"+URL+"/mobile/pondmother/verifysmscode";
	    public static final String URL_VERIFY_CHANGEPWD = "http://"+URL+"/mobile/pondmother/changepassword";
	    public static final String URL_REQUEST_FORGOT = "http://"+URL+"/mobile/pondmother/forgotpassword";
	      
        // If you want custom sender Id, approve ERUVAKA99 to get one
		public static final String SMS_ORIGIN = "ERUVKA";

        // special character to prefix the otp. Make sure this character appears only once in the sms
        public static final String OTP_DELIMITER = ":";
      	    
 }