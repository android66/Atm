package com.tom.atm;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class AddActivity extends AppCompatActivity {

    private EditText edDate;
    private EditText edInfo;
    private EditText edAmount;
    private MyDBHelper helper;
    private DatePicker datePicker;
    private AutoCompleteTextView autoInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        findViews();
        helper = MyDBHelper.getInstance(this);
        setupAdapter();
    }

    private void setupAdapter() {
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                infos);*/
        Cursor cursor = helper.getReadableDatabase()
                .query("infos", null, null, null, null, null, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{"info"},
                new int[]{android.R.id.text1}, 0);
        autoInfo.setAdapter(adapter);
        autoInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoInfo.showDropDown();
            }
        });
    }

    private void findViews() {
        edDate = (EditText) findViewById(R.id.ed_date);
        edInfo = (EditText) findViewById(R.id.ed_info);
        edAmount = (EditText) findViewById(R.id.ed_amount);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        autoInfo = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
    }

    public void add(View v) {
//        String cdate = edDate.getText().toString();
        // 取得DatePicker並轉換字串
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1;
        int day = datePicker.getDayOfMonth();
        String cdate = year + "-" + month + "-" + day;

        String info = edInfo.getText().toString();
        int amount = Integer.parseInt(edAmount.getText().toString());
        ContentValues values = new ContentValues();
        values.put("cdate", cdate);
        values.put("info", info);
        values.put("amount", amount);
        long id = helper.getWritableDatabase().insert("exp", null, values);
        Log.d("ADD", id + "");
    }
}
