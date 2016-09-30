package com.eruvaka.pm;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.HttpParams;

import android.content.Context;

public class MyHttpsClient extends DefaultHttpClient {
	 final Context context;
	  
	    public MyHttpsClient(HttpParams httpParams, Context context) {
	    	super(httpParams);
	        this.context = context;
	    }
	    
	    @Override
	    protected ClientConnectionManager createClientConnectionManager() {
	    	try
	        {
	    		SchemeRegistry registry = new SchemeRegistry();
	    	    registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

	    	    // Register for port 443 our SSLSocketFactory with our keystore
	    	    // to the ConnectionManager
	    	    registry.register(new Scheme("https", new EasySSLSocketFactory(), 443));

	    	    //http://blog.synyx.de/2010/06/android-and-self-signed-ssl-certificates/
	    	    return new SingleClientConnManager(getParams(), registry);
	        }
	        catch (Exception e)
	        {
	            throw new AssertionError(e);
	        }
	    }
	  
}
