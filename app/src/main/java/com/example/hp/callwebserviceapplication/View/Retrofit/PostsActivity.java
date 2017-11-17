package com.example.hp.callwebserviceapplication.View.Retrofit;

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


public class PostsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private Retrofit mRetrofit;
    private PostAdapter mPostAdapter;
    private static String TAG = "PostAcivity";
    private List<Post> mList;
    private AlertDialog mDialog;
    Call<List<Post>> mCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_retrofit);
        mListView = findViewById(R.id.list);
        mListView.setOnItemClickListener(this);
        mList = new ArrayList<>();
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.post);
        View view = getLayoutInflater().inflate(R.layout.progress, null);
        mDialog = new AlertDialog.Builder(this).setView(view).create();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.show();
        mRetrofit = RetrofitClient.getClient(Constantes.BASE_URL);
        PostApi api = mRetrofit.create(PostApi.class);
        mCall = api.getPosts();

        mCall.enqueue(getOnResponse());


    }

    @NonNull
    private Callback<List<Post>> getOnResponse() {
        return new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.i(TAG, "onResponse");
                mDialog.dismiss();
                mList = response.body();
                mPostAdapter = new PostAdapter(mList, PostsActivity.this);
                mListView.setAdapter(mPostAdapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.i(TAG, "Failure" + t.toString());
            }
        };
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int idPost = mList.get(position).getId();
        Intent i = new Intent(this, CommentsActivity.class);
        i.putExtra(Constantes.POST_ID, idPost);
        startActivity(i);

    }

    @Override
    protected void onDestroy() {
        if (!mCall.isCanceled())
            mCall.cancel();
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
