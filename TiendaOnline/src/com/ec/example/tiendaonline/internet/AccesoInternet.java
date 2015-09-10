package com.ec.example.tiendaonline.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AccesoInternet {
	
	public String checkConnectivity(Context context)
    {
		 String enabled = "2";
		 
	        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
	         
	        if (info != null && info.isConnectedOrConnecting())
	        {
	            enabled = "1";
	        }
	        return enabled;         
   }
}

