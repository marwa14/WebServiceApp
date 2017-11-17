package com.example.hp.callwebserviceapplication.View.Thread;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hp.callwebserviceapplication.Adapter.PostAdapter;
import com.example.hp.callwebserviceapplication.Model.Post;
import com.example.hp.callwebserviceapplication.R;
import com.example.hp.callwebserviceapplication.Tools.Constantes;
import com.example.hp.callwebserviceapplication.View.AsyncTask.PostListener;

import java.util.ArrayList;
import java.util.List;

public class PostsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, PostListener {

    private List<Post> mList;
    private ListView mListPosts;
    private PostAdapter mPostAdapter;
    private PostThread mPostThread;
    private static final String TAG = PostsActivity.class.getName();
    private AlertDialog mDialog;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_thread);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.post);
        mList = new ArrayList<>();
        mListPosts = findViewById(R.id.list_p);
        mPostAdapter = new PostAdapter(mList, this);
        mListPosts.setAdapter(mPostAdapter);
        mListPosts.setOnItemClickListener(this);
        mHandler = new Handler(getWSCallback());
        View view = getLayoutInflater().inflate(R.layout.progress, null);
        mDialog = new AlertDialog.Builder(this).setView(view).create();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.show();

        mPostThread = new PostThread(mHandler);
        mPostThread.start();
        Log.e(TAG, "onCreate");

    }

    @NonNull
    private Handler.Callback getWSCallback() {
        return new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.obj instanceof Exception) {
                    onFail((Exception) msg.obj);
                } else {
                    onResults((ArrayList<Post>) msg.obj);
                }
                return true;
            }
        };
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int idPost = mList.get(position).getId();
        Intent i = new Intent(PostsActivity.this, CommentsAtivity.class);
        i.putExtra(Constantes.POST_ID, idPost);
        startActivity(i);
    }

    @Override
    public void onFail(Exception ex) {
        Log.i(TAG, ex.toString());
    }

    @Override
    public void onResults(ArrayList<Post> posts) {
        mDialog.dismiss();
        mPostAdapter.onResults(posts);
        Log.e(TAG, "onRes");
    }

    @Override
    protected void onDestroy() {
        mPostThread.cancel();
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause");
        super.onPause();
    }
}
