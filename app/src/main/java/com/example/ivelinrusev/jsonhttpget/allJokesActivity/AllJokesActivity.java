package com.example.ivelinrusev.jsonhttpget.allJokesActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ivelinrusev.jsonhttpget.R;
import com.example.ivelinrusev.jsonhttpget.dataBaseComponents.DatabaseHelper;
import com.example.ivelinrusev.jsonhttpget.dataBaseComponents.Joke;
import com.example.ivelinrusev.jsonhttpget.mainActivity.MainActivity;
import com.example.ivelinrusev.jsonhttpget.adaptors.CustomJokeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class AllJokesActivity extends ActionBarActivity {

    private ListView listView;
    private ArrayAdapter theAdapter;
    private EditText inputSearch;
    private Toolbar toolbar;
    private ActionMode actionMode;
    private DatabaseHelper helper;
    private ArrayList<Joke> list;
    private HashMap<Integer, Boolean> selectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_jokes);
        listView = (ListView)findViewById(R.id.list_item);
        inputSearch = (EditText)findViewById(R.id.input_search);
        list = (ArrayList<Joke>)getIntent().getSerializableExtra("list");
        selectedItems = new HashMap<>();
        theAdapter = new CustomJokeAdapter(this ,R.layout.text_view, jokesToStringArray(list), selectedItems);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(theAdapter);
        listView.setLongClickable(true);
        helper = new DatabaseHelper(getApplicationContext());
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {


                MyActionModeCallBack callback = new MyActionModeCallBack();
                actionMode = startActionMode(callback);
                toggleSection(pos);
                actionMode.setTitle(selectedItems.size() + " Selected");
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

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {



            }
        });

    }
    private void toggleSection(int pos) {

        if(listView.isItemChecked(pos)){
            listView.setItemChecked(pos, false);
            selectedItems.remove(pos);
            if(selectedItems.isEmpty()) actionMode.finish();
        } else{
            listView.setItemChecked(pos, true);
            selectedItems.put(pos, true);
        }

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

    private ArrayList<String> jokesToStringArray(ArrayList<Joke> list){

        Iterator iterator = list.iterator();
        ArrayList<String> jokes = new ArrayList<String>();
        while (iterator.hasNext()){
            jokes.add(iterator.next().toString());
        }
        return jokes;
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
                    ArrayList<Joke> itemsToBeRemoved = new ArrayList<Joke>();
                    for(Integer i : selectedItems.keySet()){

                        theAdapter.remove(list.get(i).toString());
                        //dataSource.deleteJoke(list.get(i));
                        helper.removeData(list.get(i));
                        listView.setItemChecked(i, false);
                        itemsToBeRemoved.add(list.get(i));

                    }

                    list.removeAll(itemsToBeRemoved);
                    itemsToBeRemoved.clear();
                    selectedItems.clear();
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
