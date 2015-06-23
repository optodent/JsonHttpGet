package com.example.ivelinrusev.jsonhttpget;

import com.example.ivelinrusev.jsonhttpget.com.example.ivelinrusev.jsonhttpget.concreteObject.JokeObject;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by ivelin.rusev on 6/23/2015.
 */
public interface JokesAPI {

    @GET("/jokes/random")
    public void getJoke(Callback<JokeObject> response);
}
