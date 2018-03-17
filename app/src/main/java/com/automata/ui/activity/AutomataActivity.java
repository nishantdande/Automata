package com.automata.ui.activity;

import android.support.v7.app.AppCompatActivity;

import com.automata.error.APIErrorHandler;

public class AutomataActivity extends AppCompatActivity {

    protected APIErrorHandler apiErrorHandler = new APIErrorHandler(this);
}
