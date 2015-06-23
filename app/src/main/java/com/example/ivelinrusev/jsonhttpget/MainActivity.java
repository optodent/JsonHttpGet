package com.example.ivelinrusev.jsonhttpget;

import android.content.Context;
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

import com.example.ivelinrusev.jsonhttpget.com.example.ivelinrusev.jsonhttpget.concreteObject.JokeObject;
import com.google.gson.Gson;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    public static final String ENDPOINT = "http://api.icndb.com/";
    private JokeObject jokeObject = new JokeObject();
    private Button getJSON;
    private TextView textView;
    private final Gson gs = new Gson();
    private RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
    private JokesAPI api = adapter.create(JokesAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getJSON = (Button) findViewById(R.id.getJSON);
        textView = (TextView) findViewById(R.id.main_text_area);
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
                jokeObject = s;
                textView.setText(jokeObject.getValue().getJoke());
            }
            @Override
            public void failure(RetrofitError error) {
                textView.setText("whoops,something went wrong. Try again later.");
                Log.e(MainActivity.class.getSimpleName(), "failed to get joke", error);
            }
        });

    }

//    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... params) {
//
//            try {
//                return downloadUrl(params[0]);
//            } catch (IOException e) {
//                return "Unable to retrieve web page. URL may be invalid.";
//            }
//        }
//        private String downloadUrl(String param) throws  IOException{
//
//            StringBuilder builder = new StringBuilder();
//            String joke = "";
//            HttpClient client = new DefaultHttpClient();
//            HttpGet httpGet = new HttpGet(address);
//            try{
//
//                HttpResponse response = client.execute(httpGet);
//                StatusLine statusLine = response.getStatusLine();
//                int statusCode = statusLine.getStatusCode();
//
//                if(statusCode == 200){
//                    HttpEntity entity = response.getEntity();
//                    InputStream content = entity.getContent();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//                    String line;
//                    while((line = reader.readLine()) != null){
//                        builder.append(line);
//                    }
//
//                    JokeObject jo = gs.fromJson(builder.toString(), JokeObject.class);
//                    joke = jo.getValue().getJoke();
//
//                    Log.i(getClass().getSimpleName(), jo.toString());
//
//                    jo.serializeObject(MainActivity.this, "joke.ser");
//                    jo.deserializeObject(MainActivity.this, "joke.ser");
//
//                    Log.i(getClass().getSimpleName(), jo.toString());
//
//
//                } else {
//                    Log.e(MainActivity.class.toString(), "Failed JSON object");
//                }
//            }catch(ClientProtocolException e){
//                Log.e(TAG, "Get joke failed", e);
//            } catch (IOException e) {
//                Log.e(getClass().getSimpleName(), "Get joke failed", e);
//            }
//
//            return joke;
//        }
//
//
//        protected void onPostExecute(String result) {
//            textView.setText(result);
//        }
//
//    }
}
