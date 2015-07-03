package com.example.ivelinrusev.jsonhttpget.mainActivity;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class JokeObject implements Serializable{

    private String type;
    private ComplexJokeObject value;

    public JokeObject(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ComplexJokeObject getValue() {
        return value;
    }

    public void setValue(ComplexJokeObject value) {
        this.value = value;
    }

    public void serializeObject(Context context, String fileName) throws IOException {

        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
            Log.i(getClass().getSimpleName(), "Serialization successful");

        } catch (IOException e) {
           Log.e(getClass().getSimpleName(), "Serialization failed");
        }

    }

    public JokeObject deserializeObject(Context context, String fileName){

        JokeObject joke = null;

        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            joke = (JokeObject) is.readObject();
            is.close();
            fis.close();
            Log.i(getClass().getSimpleName(), "Deserialization successful");

        } catch (ClassNotFoundException e) {
            Log.e(getClass().getSimpleName(), "Deserialization failed!");
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "Deserialization failed!");
        }
        return joke;
    }

    @Override
    public String toString() {
        return "JokeObject{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }

}
