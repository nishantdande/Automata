package com.automata.device;
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


import com.automata.device.model.Device;
import com.automata.network.NetworkManager;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DeviceManager {


    private static final DeviceManager accountManager = new DeviceManager();

    private DeviceManager(){}

    public static DeviceManager getInstance() {
        return accountManager;
    }

    /**
     * Get the Device Status
     * @param username
     * @param password
     */
    public Observable<Device> getStatus(String username, String password){
        DeviceEndpointInterface deviceEndpointInterface
                = NetworkManager.createService(DeviceEndpointInterface.class);
        return deviceEndpointInterface.getDeviceStatus(username, password).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * Set the Device Status
     * @param username
     * @param password
     */
    public Observable<Device> setStatusById(String username, String password,int id, int status){
        DeviceEndpointInterface deviceEndpointInterface
                = NetworkManager.createService(DeviceEndpointInterface.class);
         return deviceEndpointInterface.setDeviceStatus(username, password,id,status).subscribeOn(Schedulers.newThread())
                 .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * All Off
     * @param username
     * @param password
     */
    public Observable<Device> allOff(String username, String password){
        DeviceEndpointInterface deviceEndpointInterface
                = NetworkManager.createService(DeviceEndpointInterface.class);
        return deviceEndpointInterface.setAllOff(username, password).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
