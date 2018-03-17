package com.automata.device;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 15/03/16
 ==============================================
 */



import com.automata.device.model.Device;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface DeviceEndpointInterface {

    /**
     * Get Device Status API
     * @param username
     * @param password
     * @return
     */
    @GET("/getstatus?")
    Observable<Device> getDeviceStatus(@Query("username") String username,
                                       @Query("password") String password);

    /**
     * Get Device Status API
     * @param username
     * @param password
     * @return
     */
    @GET("/setstatus?")
    Observable<Device> setDeviceStatus(@Query("username") String username,
                                     @Query("password") String password,
                                     @Query("id") Integer id,
                                     @Query("status") Integer status);

    @FormUrlEncoded
    @POST("/alloff")
    Observable<Device> setAllOff(@Field("username") String username,
                                 @Field("password") String password);
}
