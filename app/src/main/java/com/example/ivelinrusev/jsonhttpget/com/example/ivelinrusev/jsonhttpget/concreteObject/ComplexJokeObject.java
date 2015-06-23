package com.example.ivelinrusev.jsonhttpget.com.example.ivelinrusev.jsonhttpget.concreteObject;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by ivelin.rusev on 6/22/2015.
 */
public class ComplexJokeObject implements Serializable{


    private int id;
    private String joke;
    private int[] categories;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getCategories() {
        return categories;
    }

    public void setCategories(int[] categories) {
        this.categories = categories;
    }
}
