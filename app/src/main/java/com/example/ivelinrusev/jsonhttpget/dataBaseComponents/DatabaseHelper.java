package com.example.ivelinrusev.jsonhttpget.dataBaseComponents;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "jokes.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Joke, Integer> simpleDao = null;
    private RuntimeExceptionDao<Joke, Integer> simpleRuntimeDao = null;


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public Dao<Joke, Integer> getDao() throws SQLException {
        if (simpleDao == null) {
            simpleDao = getDao(Joke.class);
        }
        return simpleDao;
    }

    public RuntimeExceptionDao<Joke, Integer> getSimpleDataDao() {
        if (simpleRuntimeDao == null) {
            simpleRuntimeDao = getRuntimeExceptionDao(Joke.class);
        }
        return simpleRuntimeDao;
    }

    //method for retrieving all jokes
    public List<Joke> getData()
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        RuntimeExceptionDao<Joke, Integer> simpleDao = helper.getSimpleDataDao();
        List<Joke> list = simpleDao.queryForAll();
        return list;
    }

    //method for insert data
    public void addData(Joke joke)
    {
        RuntimeExceptionDao<Joke, Integer> dao = getSimpleDataDao();
        dao.createIfNotExists(joke);

    }

    public void removeData(Joke joke){

        RuntimeExceptionDao<Joke, Integer> dao = getSimpleDataDao();
        dao.delete(joke);

    }

    //method for delete all rows
    public void deleteAll()
    {
        RuntimeExceptionDao<Joke, Integer> dao = getSimpleDataDao();
        List<Joke> list = dao.queryForAll();
        dao.delete(list);
    }

    @Override
    public void close() {
        super.close();
        simpleRuntimeDao = null;
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Joke.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Joke.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

}
