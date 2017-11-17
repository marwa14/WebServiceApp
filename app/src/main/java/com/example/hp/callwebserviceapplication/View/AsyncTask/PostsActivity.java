package com.example.hp.callwebserviceapplication.View.AsyncTask;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import java.util.ArrayList;

public class PostsActivity extends AppCompatActivity implements PostListener {

    private static final String TAG = PostsActivity.class.getName();

    private ListView mListView;
    private PostAdapter mPostAdapter;
    private ArrayList<Post> mListPosts;
    private ListPosts mAsyncPosts;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_asynctask);
        mListView = findViewById(R.id.list_post);
        mListPosts = new ArrayList<>();
        mPostAdapter = new PostAdapter(mListPosts, this);
        mListView.setAdapter(mPostAdapter);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.post);
        mListView.setOnItemClickListener(getListener());


        View view = getLayoutInflater().inflate(R.layout.progress, null);
        mDialog = new AlertDialog.Builder(this).setView(view).create();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.show();

        mAsyncPosts = new ListPosts(this);
        mAsyncPosts.execute();

    }

    @NonNull
    private AdapterView.OnItemClickListener getListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idPost = mListPosts.get(position).getId();
                Intent i = new Intent(PostsActivity.this, CommentsActivity.class);
                i.putExtra(Constantes.POST_ID, idPost);
                startActivity(i);
            }
        };
    }

    @Override
    public void onFail(Exception ex) {
        Log.e(TAG, ex.toString());
    }

    @Override
    public void onResults(ArrayList<Post> posts) {
        Log.e(TAG, "onResults");
        mDialog.dismiss();
        mPostAdapter.onResults(posts);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        if (mAsyncPosts != null) {
            mAsyncPosts.cancel();
        }
        super.onDestroy();
    }
}
