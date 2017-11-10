package com.example.hp.callwebserviceapplication.View.Retrofit;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.hp.callwebserviceapplication.Adapter.CommentAdapter;
import com.example.hp.callwebserviceapplication.Model.Comment;
import com.example.hp.callwebserviceapplication.Model.PostApi;
import com.example.hp.callwebserviceapplication.R;
import com.example.hp.callwebserviceapplication.Tools.Constantes;
import com.example.hp.callwebserviceapplication.Tools.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class CommentsActivity extends AppCompatActivity {

    private int mIdPost;
    private ListView mListView;
    private CommentAdapter mCommentsAdapter;
    private List<Comment> mList;
    private Retrofit mRetrofit;
    private static String TAG = "CommentsAcivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments3);
        mIdPost = getIntent().getExtras().getInt(Constantes.post_id, Constantes.idPostDef);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(Constantes.activityComment);
        mListView = findViewById(R.id.list);
        mList = new ArrayList<>();
        mRetrofit = RetrofitClient.getClient(Constantes.base_url);
        PostApi api = mRetrofit.create(PostApi.class);
        Call<List<Comment>> call = api.getComments(mIdPost);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Response<List<Comment>> response, Retrofit retrofit) {

                mList = response.body();
                mCommentsAdapter = new CommentAdapter(mList, CommentsActivity.this);
                mListView.setAdapter(mCommentsAdapter);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i(TAG, "Failure" + t.toString());


            }
        });

    }
}
