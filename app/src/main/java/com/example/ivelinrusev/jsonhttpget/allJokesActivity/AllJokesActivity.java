package com.example.ivelinrusev.jsonhttpget.allJokesActivity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ivelinrusev.jsonhttpget.R;
import com.example.ivelinrusev.jsonhttpget.dataBaseComponents.JokeDB;
import com.example.ivelinrusev.jsonhttpget.mainActivity.MainActivity;

import java.util.ArrayList;
import java.util.Iterator;

public class AllJokesActivity extends ActionBarActivity {

    private ListView listView;
    private ArrayAdapter theAdapter;
    private EditText inputSearch;
    private Toolbar toolbar;
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_jokes);
        listView = (ListView)findViewById(R.id.list_item);
        inputSearch = (EditText)findViewById(R.id.input_search);
        ArrayList<JokeDB> list = (ArrayList<JokeDB>)getIntent().getSerializableExtra("list");
        theAdapter = new ArrayAdapter<String>(this ,R.layout.text_view, jokesToStringArray(list));

        listView.setAdapter(theAdapter);
        listView.setLongClickable(true);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                Log.v("long clicked", "pos: " + pos);
                return true;
            }
        });

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                AllJokesActivity.this.theAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
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

        if(id == R.id.navigate){
          Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
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
