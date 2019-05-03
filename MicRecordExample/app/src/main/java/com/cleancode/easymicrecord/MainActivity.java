package com.cleancode.easymicrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.cleancode.easymicrecord.mic.micRecord;

public class MainActivity extends AppCompatActivity implements micRecord.onRecordListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new micRecord(this, findViewById(R.id.btnRec), "myTag");
    }

    @Override
    public void onRecordSuccess(String tag, String path) {
        Log.e("path", path);
    }

    @Override
    public void onRecordError(String tag) { }
}