package com.example.hp.callwebserviceapplication.View.AsyncTask;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hp.callwebserviceapplication.Adapter.PostAdapter;
import com.example.hp.callwebserviceapplication.Model.Post;
import com.example.hp.callwebserviceapplication.R;
import com.example.hp.callwebserviceapplication.Tools.Constantes;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PostsActivity extends AppCompatActivity {

    private ListView mListView;
    private PostAdapter mPostAdapter;
    private ArrayList<Post> mListPosts;
    private ListPosts mAsyncPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts2);
        mListView = findViewById(R.id.list_post);
        mListPosts = new ArrayList<>();
        mAsyncPosts = new ListPosts();
        try {
            mListPosts = mAsyncPosts.execute().get();
            mPostAdapter = new PostAdapter(mListPosts, this);
            mListView.setAdapter(mPostAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(Constantes.activityPost);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idPost = mListPosts.get(position).getId();
                Intent i = new Intent(PostsActivity.this, CommentsActivity.class);
                i.putExtra(Constantes.post_id, idPost);
                startActivity(i);
            }
        });


    }
}
