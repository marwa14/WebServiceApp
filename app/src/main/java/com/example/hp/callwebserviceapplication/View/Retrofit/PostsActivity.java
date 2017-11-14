package com.example.hp.callwebserviceapplication.View.Retrofit;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PostsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private Retrofit mRetrofit;
    private PostAdapter mPostAdapter;
    private static String mTAG = "PostAcivity";
    private List<Post> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts3);
        mListView = findViewById(R.id.list);
        mListView.setOnItemClickListener(this);
        mList = new ArrayList<>();
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(Constantes.activityPost);
        mRetrofit = RetrofitClient.getClient(Constantes.base_url);
        PostApi api = mRetrofit.create(PostApi.class);
        Call<List<Post>> call = api.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Response<List<Post>> response, Retrofit retrofit) {

                mList = response.body();
                mPostAdapter = new PostAdapter(mList, PostsActivity.this);
                mListView.setAdapter(mPostAdapter);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i(mTAG, "Failure" + t.toString());

            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int idPost = mList.get(position).getId();
        Intent i = new Intent(this, CommentsActivity.class);
        i.putExtra(Constantes.post_id, idPost);
        startActivity(i);

    }
}
