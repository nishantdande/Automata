package com.automata.util;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;

/**
 * Created by nishantdande on 24/09/16.
 */

public class AppUtils {

    public static void displayShortSnackbar(Activity activity,@StringRes int s){
        Snackbar.make(activity.findViewById(android.R.id.content), s, Snackbar.LENGTH_LONG).show();
    }

    public static void displayShortSnackbar(Activity activity,String s){
        Snackbar.make(activity.findViewById(android.R.id.content), s, Snackbar.LENGTH_LONG).show();
    }
}
