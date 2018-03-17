package com.automata;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description : This is the base project class to obtain
               base Application context
 Date        : 12/01/16
 ==============================================
 */

import android.content.Context;

public class Automata {

    private static final Automata AUTOMATA = new Automata();
    private Context mContext;

    private Automata() {}

    public static Automata getInstance() {
        return AUTOMATA;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public Context getContext() {
        return mContext;
    }
}
