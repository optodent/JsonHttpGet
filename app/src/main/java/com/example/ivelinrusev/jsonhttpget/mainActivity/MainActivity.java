package com.example.ivelinrusev.jsonhttpget.mainActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ivelinrusev.jsonhttpget.R;
import com.example.ivelinrusev.jsonhttpget.allJokesActivity.AllJokesActivity;
import com.example.ivelinrusev.jsonhttpget.contracts.JokesAPI;
import com.example.ivelinrusev.jsonhttpget.dataBaseComponents.DatabaseHelper;
import com.example.ivelinrusev.jsonhttpget.dataBaseComponents.Joke;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    public static final String ENDPOINT = "http://api.icndb.com/";
    private JokeObject jokeObjectJson;
    private Context context;
    private Button getJsonButton;
    private Button viewAllJokesButton;
    private TextView textView;
    private final Gson gs = new Gson();
    private RestAdapter adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog(getClass().getSimpleName())).setEndpoint(ENDPOINT).build();
    private JokesAPI api = adapter.create(JokesAPI.class);
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DatabaseHelper(this);
        getJsonButton = (Button) findViewById(R.id.getJSON);
        viewAllJokesButton = (Button) findViewById(R.id.view_joke_id_button);
        textView = (TextView) findViewById(R.id.main_text_area);

        Log.i(getClass().getSimpleName(), "In Main Activity");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void getJsonHttp(View view) {

        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            requestData();
        } else {
            textView.setText("No network connection available.");
        }

    }

    private void requestData(){

        api.getJoke(new Callback<JokeObject>() {
            @Override
            public void success(JokeObject s, Response response) {

                textView.setText(s.getValue().getJoke());
                Joke jokeForSaving = new Joke(s.getValue().getId(), s.getValue().getJoke(), s.getValue().getCategories().length == 0 ? null : s.getValue().getCategories()[0]);
                helper.addData(jokeForSaving);

            }

            @Override
            public void failure(RetrofitError error) {
                textView.setText("whoops,something went wrong. Try again later.");
                Log.e(MainActivity.class.getSimpleName(), "failed to get joke", error);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("jokeGson", gs.toJson(jokeObjectJson));
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        jokeObjectJson = gs.fromJson(savedInstanceState.getString("jokeGson"), JokeObject.class);
        textView.setText(jokeObjectJson.getValue().getJoke() == null ? "Click the button for next joke" : jokeObjectJson.getValue().getJoke());
    }


    public void viewAllJokes(View view) {

        List<Joke> list = helper.getData();
        Intent intent = new Intent(this, AllJokesActivity.class);
        intent.putExtra("list", (ArrayList<Joke>)list);
        startActivity(intent);

    }


}
