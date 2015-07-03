package com.example.ivelinrusev.jsonhttpget.contracts;

import com.example.ivelinrusev.jsonhttpget.dataBaseComponents.Joke;
import com.example.ivelinrusev.jsonhttpget.mainActivity.JokeObject;

import retrofit.Callback;
import retrofit.http.GET;

public interface JokesAPI {

    @GET("/jokes/random")
    public void getJoke(Callback<JokeObject> response);
}
