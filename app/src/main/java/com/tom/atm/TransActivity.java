package com.tom.atm;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TransActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);
        Request request = new Request.Builder()
                .url("http://atm201605.appspot.com/h")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.d("OKHTTP", json);
                //解析JSON
                //                parseJSON(json);
                //                parseGson(json);
                parseJackson(json);
            }
            @Override
            public void onFailure(Call call, IOException e) {
                //告知使用者連線失敗
            }

        });
//        new TransTask()
//                .execute("http://atm201605.appspot.com/h");
    }
    class TransTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(url.openStream()));
                String line = in.readLine();
                while(line!=null){
                    Log.d("HTTP", line);
                    sb.append(line);
                    line = in.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("JSON", s);
            parseJSON(s);
        }
    }

    private void parseJSON(String s) {
        ArrayList<Transaction> trans = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(s);
            for (int i=0; i<array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                String account = obj.getString("account");
                String date = obj.getString("date");
                int amount = obj.getInt("amount");
                int type = obj.getInt("type");
                Log.d("JSON:",account+"/"+date+"/"+amount+"/"+type);
                Transaction t = new Transaction(account, date, amount, type);
                trans.add(t);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void parseGson(String s){
        Gson gson = new Gson();
        ArrayList<Transaction> list =
            gson.fromJson(s,
                new TypeToken<ArrayList<Transaction>>(){}.getType());
        Log.d("JSON",list.size()+"/"+list.get(0).getAmount());
    }

    private void parseJackson(String s){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final ArrayList<Transaction> list =
                    objectMapper.readValue(s,
                            new TypeReference<List<Transaction>>(){});
            Log.d("JSON",list.size()+"/"+list.get(0).getAmount());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setupRecyclerView(list);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setupRecyclerView(List<Transaction> list){
        RecyclerView recyclerView =
                (RecyclerView) findViewById(R.id.recycler);
        TransactionAdapter adapter = new TransactionAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
