package com.eruvaka.pm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();
 
    @Override
    public void onReceive(Context context, Intent intent) {
       
        try {
        	  final Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (Object aPdusObj : pdusObj) {
                	try{
                	 
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
               		String senderAddress = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();
                    Log.e(TAG, "Received SMS: " + message + ". Sender: " + senderAddress);
                   
                    // if the SMS is not from our gateway, ignore the message
                    if (!senderAddress.toLowerCase().contains(Config.SMS_ORIGIN.toLowerCase())) {
                    	 System.out.println(senderAddress);  
                         return;
                     }else{
                    	 // verification code from sms
                    	 try{
                    		 String verificationCode = getVerificationCode(message);
                             System.out.println("Eruvaka");  
                             Log.e(TAG, "OTP received: " + verificationCode);
                             Intent hhtpIntent = new Intent(context, HttpService.class);
                             hhtpIntent.putExtra("otp", verificationCode);
                             context.startService(hhtpIntent); 
                    	 }catch(Exception e){
                    		 e.printStackTrace();
                    	 }
                        
                     }
                }catch(Exception e){
            		e.printStackTrace();
            	}                
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
 
    /**
     * Getting the OTP from sms message body
     * ':' is the separator of OTP from the message
     *
     * @param message
     * @return
     */
    private String getVerificationCode(String message) {
        String code = null;
        
        int index = message.indexOf(Config.OTP_DELIMITER);
        
        if (index != -1) {
        	 
            int start = index + 2;
            int length = 4;
            
            code = message.substring(start, start + length);
           
            return code;
        }
 
        return code;
    }

}
