package com.example.ivelinrusev.jsonhttpget.dataBaseComponents;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Joke implements Serializable{

    @DatabaseField(generatedId = true) private long id;
    @DatabaseField private String joke;
    @DatabaseField private String category;


    public Joke(){

    }

    public Joke(long id, String joke, String category){
        this.id = id;
        this.joke = joke;
        this.category = category;
    }

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

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {

        String category = this.category == null ? "No category" : this.category.replace(this.category.charAt(0), (char)(this.category.charAt(0)-32));
        return joke +" "+ category;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Joke)) return false;

        Joke joke = (Joke) o;

        return id == joke.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
