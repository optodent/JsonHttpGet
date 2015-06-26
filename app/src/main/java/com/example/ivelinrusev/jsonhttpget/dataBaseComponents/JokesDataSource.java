package com.example.ivelinrusev.jsonhttpget.dataBaseComponents;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ivelin.rusev on 6/24/2015.
 */
public class JokesDataSource {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = { SQLiteHelper.COLUMN_ID, SQLiteHelper.COLUMN_JOKE, SQLiteHelper.COLUMN_CATEGORY };

    public JokesDataSource(Context context){
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public JokeDB createJoke(String joke, String category) {

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_JOKE, joke);
        values.put(SQLiteHelper.COLUMN_CATEGORY, category);

        boolean isPresent = isPresentJoke(joke);
        if (!isPresent){
            long insertId = database.insert(SQLiteHelper.TABLE_NAME, null, values);
            Cursor cursor = database.query(SQLiteHelper.TABLE_NAME, allColumns,
                    SQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
            cursor.moveToFirst();
            JokeDB newJoke = cursorToJoke(cursor);
            cursor.close();
            return newJoke;
        }
        return null;
    }

    public void deleteJoke(JokeDB joke) {

        long id = joke.getId();
        database.delete(SQLiteHelper.TABLE_NAME, SQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<JokeDB> getAllJokes() {

        ArrayList<JokeDB> jokes = new ArrayList<JokeDB>();
        Cursor cursor = database.query(SQLiteHelper.TABLE_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            JokeDB joke = cursorToJoke(cursor);
            jokes.add(joke);
            cursor.moveToNext();
        }
        cursor.close();
        return jokes;
    }

    private boolean isPresentJoke(String joke){

        String query = "select * from jokes where joke = " +"\""+joke.trim()+"\"";
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }

    private JokeDB cursorToJoke(Cursor cursor) {

        JokeDB joke = new JokeDB();
        joke.setId(cursor.getLong(0));
        joke.setJoke(cursor.getString(1));
        joke.setCategory(cursor.getString(2));
        return joke;
    }

}
