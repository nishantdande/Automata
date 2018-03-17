package com.automata.account;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description : The Account Manager manages
               all the account related operation
 Date        : 14/01/16
 ==============================================
 */


import com.automata.account.model.Login;
import com.automata.network.NetworkManager;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AccountManager {


    private static final AccountManager accountManager = new AccountManager();

    private AccountManager(){}

    public static AccountManager getInstance() {
        return accountManager;
    }

    /**
     * Login for your valid account
     * @param username
     * @param password
     */
    public Observable<Login> login(String username, String password){
        AccountEndpointInterface accountEndpointInterface
                = NetworkManager.createService(AccountEndpointInterface.class);

        Observable<Login> login = accountEndpointInterface.login(username, password).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        return login;
    }


}
