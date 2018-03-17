package com.automata.network;
/*
 ==============================================
 Author      : nishant dande
 Version     : 
 Copyright   :
 Description : This is Network Manager Class
               which has the type of Network
               Framework
 Date        : 14/01/16
 ==============================================
 */



import com.automata.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.internal.Internal.logger;


public class NetworkManager {

    private final static NetworkManager networkManager =new NetworkManager();

    private NetworkManager(){}

    public static NetworkManager getInstance() {
        return networkManager;
    }

    private final static Retrofit.Builder retrofitBuilder  = new Retrofit.Builder()
            .baseUrl(BuildConfig.ENV)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60,TimeUnit.SECONDS);


    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = retrofitBuilder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
