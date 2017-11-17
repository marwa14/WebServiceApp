package com.example.hp.callwebserviceapplication.View.Retrofit;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.hp.callwebserviceapplication.Adapter.CommentAdapter;
import com.example.hp.callwebserviceapplication.Model.Comment;
import com.example.hp.callwebserviceapplication.Model.PostApi;
import com.example.hp.callwebserviceapplication.R;
import com.example.hp.callwebserviceapplication.Tools.Constantes;
import com.example.hp.callwebserviceapplication.Tools.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class CommentsActivity extends AppCompatActivity {

    private int mIdPost;
    private ListView mListView;
    private CommentAdapter mCommentsAdapter;
    private List<Comment> mList;
    private Retrofit mRetrofit;
    private static String TAG = "CommentsAcivity";
    private AlertDialog mDialog;
    Call<List<Comment>> mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_retrofit);
        mIdPost = getIntent().getExtras().getInt(Constantes.POST_ID, Constantes.ID_POST_DEF);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.comment);
        mListView = findViewById(R.id.list);
        mList = new ArrayList<>();
        mRetrofit = RetrofitClient.getClient(Constantes.BASE_URL);
        View view = getLayoutInflater().inflate(R.layout.progress, null);
        mDialog = new AlertDialog.Builder(this).setView(view).create();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.show();
        PostApi api = mRetrofit.create(PostApi.class);
        mCall = api.getComments(mIdPost);
        mCall.enqueue(getCallback());

    }

    @NonNull
    private Callback<List<Comment>> getCallback() {
        return new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                mDialog.dismiss();
                mList = response.body();
                mCommentsAdapter = new CommentAdapter(mList, CommentsActivity.this);
                mListView.setAdapter(mCommentsAdapter);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.i(TAG, "Failure" + t.toString());

            }

        };
    }

    @Override
    protected void onDestroy() {
        if (!mCall.isCanceled())
            mCall.cancel();
        super.onDestroy();
    }
}
