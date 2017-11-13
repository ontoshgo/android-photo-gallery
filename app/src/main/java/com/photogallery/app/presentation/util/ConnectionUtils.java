package com.photogallery.app.presentation.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionUtils {

	public static boolean isNetworkAvailable(Context context) {
	    ConnectivityManager connectivityManager
	          = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager!=null
				? connectivityManager.getActiveNetworkInfo() : null;
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}
	
}
