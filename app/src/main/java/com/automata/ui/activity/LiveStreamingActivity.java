package com.automata.ui.activity;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 30/05/16
 ==============================================
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.automata.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveStreamingActivity extends AutomataActivity {

    @BindView(R.id.live_streaming_webview)
    WebView mLiveStreaming;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_streaming_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLiveStreaming.loadUrl("http://noiprpi.ddns.net:8079");
        mLiveStreaming.getSettings().setJavaScriptEnabled(true);
        mLiveStreaming.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mLiveStreaming.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                handler.proceed("ashwin", "deshpande");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }
        });
    }
}
