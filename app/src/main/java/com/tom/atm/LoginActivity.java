package com.tom.atm;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText edUserid = (EditText) findViewById(R.id.userid);
        SharedPreferences setting =
                getSharedPreferences("atm", MODE_PRIVATE);
        edUserid.setText(setting.getString("PREF_USERID", ""));
    }
    public void login(View v){
        EditText edUserid = (EditText) findViewById(R.id.userid);
        EditText edPasswd = (EditText) findViewById(R.id.passwd);
        String uid = edUserid.getText().toString();
        String pw = edPasswd.getText().toString();
        String url = new StringBuilder(
                "http://atm201605.appspot.com/login?uid=")
                .append(uid)
                .append("&pw=")
                .append(pw)
                .toString();
        new LoginTask().execute(url);
        //請註解以下程式碼
        /*
        if (uid.equals("jack") && pw.equals("1234")){ //登入成功
            SharedPreferences setting =
                    getSharedPreferences("atm", MODE_PRIVATE);
            setting.edit()
                    .putString("PREF_USERID", uid)
                    .commit();
            Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show();
            getIntent().putExtra("LOGIN_USERID", uid);
            getIntent().putExtra("LOGIN_PASSWD", pw);
            setResult(RESULT_OK, getIntent());
            finish();
        }else{  //登入失敗
            new AlertDialog.Builder(this)
                    .setTitle("Atm")
                    .setMessage("登入失敗")
                    .setPositiveButton("OK", null)
                    .show();
        }*/
    }
    class LoginTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            boolean logon = false;
            try {
                URL url = new URL(params[0]);
                InputStream is = url.openStream();
                int data = is.read();
                Log.d("HTTP", String.valueOf(data));
                if (data == 49){
                    logon = true;
                }
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return logon;
        }

        @Override
        protected void onPostExecute(Boolean logon) {
            super.onPostExecute(logon);
            if (logon){
                //儲存帳號至SharedPreferences
                //結束本畫面, 回到MainActivity
                Toast.makeText(LoginActivity.this, "登入成功",
                        Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, getIntent());
                finish();
            }else{  //登入失敗
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Atm")
                        .setMessage("登入失敗")
                        .setPositiveButton("OK", null)
                        .show();
            }
        }
    }

    public void cancel(View v){

    }
}
