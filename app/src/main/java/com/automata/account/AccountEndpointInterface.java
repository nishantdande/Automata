package com.automata.account;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 15/03/16
 ==============================================
 */



import com.automata.account.model.Login;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface AccountEndpointInterface {

    /**
     * Login API
     * @param username
     * @param password
     * @return
     */
    @GET("/login?")
    Observable<Login> login(@Query("username") String username,
                            @Query("password") String password);
}
