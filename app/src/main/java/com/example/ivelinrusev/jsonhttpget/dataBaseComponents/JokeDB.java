package com.example.ivelinrusev.jsonhttpget.dataBaseComponents;

import java.io.Serializable;

/**
 * Created by ivelin.rusev on 6/24/2015.
 */
public class JokeDB implements Serializable{

    private long id;
    private String joke;
    private String category;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String catecory) {
        this.category = catecory;
    }

    @Override
    public String toString() {

        String category = this.category == null ? "No category" : this.category.replace(this.category.charAt(0), (char)(this.category.charAt(0)-32));
        return joke +" "+ category;
    }
}
