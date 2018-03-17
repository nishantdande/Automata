package com.automata.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.automata.Automata;

/**
 * Created by nishant on 11/04/16.
 */
public class ConnectivityUtil {

    public static final boolean isOnline(){
        ConnectivityManager cm =
                (ConnectivityManager) Automata.getInstance().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
