package com.example.ivelinrusev.jsonhttpget.allJokesActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.Toast;

import com.example.ivelinrusev.jsonhttpget.R;
import com.example.ivelinrusev.jsonhttpget.dataBaseComponents.JokeDB;
import com.example.ivelinrusev.jsonhttpget.dataBaseComponents.JokesDataSource;
import com.example.ivelinrusev.jsonhttpget.mainActivity.MainActivity;

import java.util.ArrayList;
import java.util.Iterator;

public class AllJokesActivity extends ActionBarActivity {

    private ListView listView;
    private ArrayAdapter theAdapter;
    private EditText inputSearch;
    private Toolbar toolbar;
    private ActionMode actionMode;
    private JokesDataSource dataSource;
    private ArrayList<JokeDB> list;
    private ArrayList<View> checkedViews = new ArrayList<View>();
    private ArrayList<String> checkedViewsItems = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_jokes);
        listView = (ListView)findViewById(R.id.list_item);
        inputSearch = (EditText)findViewById(R.id.input_search);
        list = (ArrayList<JokeDB>)getIntent().getSerializableExtra("list");

        theAdapter = new ArrayAdapter<String>(this ,R.layout.text_view, jokesToStringArray(list));
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(theAdapter);
        listView.setLongClickable(true);
        dataSource = new JokesDataSource(this);
        dataSource.open();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                //checks if checkedItems array doesn't contains the selected item, if so it is added.
                if (!checkedViews.contains(arg1)) {
                    checkedViews.add(arg1);
                    checkedViewsItems.add(theAdapter.getItem(pos).toString());
                }

                MyActionModeCallBack callback = new MyActionModeCallBack();
                actionMode = startActionMode(callback);
                toggle(arg1, pos, theAdapter.getItem(pos).toString());
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

        else if(id == R.id.navigate){
          Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<String> jokesToStringArray(ArrayList<JokeDB> list){

        Iterator iterator = list.iterator();
        ArrayList<String> jokes = new ArrayList<String>();
        while (iterator.hasNext()){
            jokes.add(iterator.next().toString());
        }
        return jokes;
    }

    private void toggle(View view, int pos, String item){

        if (listView.isItemChecked(pos)) {

            checkedViews.remove(view);
            checkedViewsItems.remove(item);
            actionMode.setTitle(checkedViews.size() + " Selected");
            listView.setItemChecked(pos, false);;
            view.setBackgroundColor(Color.TRANSPARENT);
            if (checkedViews.size() == 0) actionMode.finish();

        } else {

            listView.setItemChecked(pos, true);
            actionMode.setTitle(checkedViews.size() + " Selected");
            view.setBackgroundColor(Color.GRAY);

        }
    }

    private  JokeDB getJokeByValue(String str){

        for(JokeDB joke : list){
            if (str.contains(joke.getJoke())){
                return joke;
            }
        }
        return null;
    }

    private class MyActionModeCallBack implements  ActionMode.Callback{

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            toolbar.setVisibility(View.GONE);
            mode.getMenuInflater().inflate(R.menu.context, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.delete:

                    for(int i = 0; i < checkedViews.size(); i++) {
                        checkedViews.get(i).setBackgroundColor(Color.TRANSPARENT);
                        theAdapter.remove(checkedViewsItems.get(i));
                        dataSource.deleteJoke(getJokeByValue(checkedViewsItems.get(i)));
                    }
                    checkedViewsItems.clear();
                    checkedViews.clear();
                    theAdapter.notifyDataSetChanged();
                    actionMode.finish();
                    Toast.makeText(getBaseContext(), "Selected items removed", Toast.LENGTH_SHORT).show();
                    return true;

            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            toolbar.setVisibility(View.VISIBLE);
        }
    }
}
