package com.example.ivelinrusev.jsonhttpget.mainActivity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by ivelin.rusev on 6/22/2015.
 */
public class ComplexJokeObject implements Serializable{


    private long id;
    private String joke;
    private String[] categories;

    public String getJoke() {
        return joke;
    }

    @Override
    public String toString() {
        return "ComplexJokeObject{" +
                "id=" + id +
                ", joke='" + joke + '\'' +
                ", categories=" + Arrays.toString(categories) +
                '}';
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }
}
