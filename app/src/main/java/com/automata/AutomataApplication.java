package com.automata;

import android.app.Application;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by nishant on 15/03/16.
 */
public class AutomataApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Automata.getInstance().setContext(this);

        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }
}
