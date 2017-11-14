package com.example.hp.callwebserviceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hp.callwebserviceapplication.View.Thread.PostsActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonThread, mButtonAsyncTask, mButtonRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonAsyncTask = findViewById(R.id.btn_asynctask);
        mButtonThread = findViewById(R.id.btn_thread);
        mButtonRetrofit = findViewById(R.id.btn_retrofit);
        mButtonRetrofit.setOnClickListener(this);
        mButtonThread.setOnClickListener(this);
        mButtonAsyncTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.btn_thread) {
            Intent i = new Intent(MainActivity.this, PostsActivity.class);
            startActivity(i);
        }
        if (v.getId() == R.id.btn_asynctask) {
            Intent i = new Intent(MainActivity.this, com.example.hp.callwebserviceapplication.View.AsyncTask.PostsActivity.class);
            startActivity(i);
        }
        if (v.getId() == R.id.btn_retrofit) {
            Intent i = new Intent(MainActivity.this, com.example.hp.callwebserviceapplication.View.Retrofit.PostsActivity.class);
            startActivity(i);
        }

    }
}
