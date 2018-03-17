package com.automata.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.automata.ui.activity.DetailActivity;
import com.automata.ui.activity.HomeScreenActivity;
import com.automata.ui.activity.LiveStreamingActivity;
import com.automata.ui.activity.LoginActivity;
import com.automata.ui.activity.NewHomeScreenActivity;

/**
 * Created by nishant on 15/03/16.
 */
public class Router {

    /**
     * Launch Home Screen Activity
     * @param activity
     */
    public static void startHomeScreenActivity(Activity activity){
        Intent intent= new Intent(activity,NewHomeScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * Launch Detail Screen Activity
     * @param activity
     */
    public static void startDetailScreenActivity(Activity activity, Bundle bundle){
        Intent intent= new Intent(activity,DetailActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * Launch Home Screen Activity
     * @param activity
     */
    public static void startLoginActivity(Activity activity){
        Intent intent= new Intent(activity,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * Launch Live Streaming Screen Activity
     * @param activity
     */
    public static void startLiveStreamingActivity(Activity activity){
        Intent intent= new Intent(activity,LiveStreamingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}
