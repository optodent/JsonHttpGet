package com.example.ivelinrusev.jsonhttpget.allJokesActivity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivelinrusev.jsonhttpget.R;
import com.example.ivelinrusev.jsonhttpget.adaptors.MyAdapter;
import com.example.ivelinrusev.jsonhttpget.dataBaseComponents.JokeDB;
import com.example.ivelinrusev.jsonhttpget.mainActivity.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class AllJokesActivity extends ActionBarActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_jokes);
        listView = (ListView)findViewById(R.id.list_item);

        ArrayList<JokeDB> list = (ArrayList<JokeDB>)getIntent().getSerializableExtra("list");
        ListAdapter theAdapter = new MyAdapter(this, jokesToStringArray(list));

        listView.setAdapter(theAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String tvShowPicked = "You selected " + String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(AllJokesActivity.this, tvShowPicked, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_jokes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String[] jokesToStringArray(ArrayList<JokeDB> list){


        Iterator iterator = list.iterator();
        String[] jokes = new String[list.size()];
        int index = 0;
        while (iterator.hasNext()){
            jokes[index++] = iterator.next().toString();
        }
        return jokes;
    }
}
