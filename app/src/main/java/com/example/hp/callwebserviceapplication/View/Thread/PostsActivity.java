package com.example.hp.callwebserviceapplication.View.Thread;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.example.hp.callwebserviceapplication.Tools.UtilsClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Handler mHandler;
    private String mResponse;
    private List<Post> mList;
    private ListView mListPosts;
    private PostAdapter mPostAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        mList = new ArrayList<>();
        mListPosts = findViewById(R.id.list_p);
        mPostAdapter = new PostAdapter(mList, this);
        mListPosts.setAdapter(mPostAdapter);
        mListPosts.setOnItemClickListener(this);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(Constantes.activityPost);


        Thread getPost = new Thread(new Runnable() {
            public void run() {

                UtilsClass utilsClass = new UtilsClass();
                try {
                    String rep = utilsClass.getResponse(Constantes.url);
                    sendResponse(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


        });


        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                mResponse = msg.getData().getString(Constantes.key_response);
                try {
                    parseData(mResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        getPost.start();

    }

    public void sendResponse(String rep) {
        Message msgObj = mHandler.obtainMessage();
        Bundle b = new Bundle();
        b.putString(Constantes.key_response, rep);
        msgObj.setData(b);
        mHandler.sendMessage(msgObj);

    }

    public void parseData(String data) throws JSONException {
        JSONArray jsonArray = new JSONArray(data);
        for (int i = 0; i < jsonArray.length() - 1; i++) {
            JSONObject obj = new JSONObject(jsonArray.getString(i));
            Post post = new Post();
            post.setUserId(obj.getInt(Constantes.post_userId));
            post.setId(obj.getInt(Constantes.post_id));
            post.setTitle(obj.getString(Constantes.post_title));
            post.setBody(obj.getString(Constantes.post_body));

            mList.add(post);

        }
        mPostAdapter.notifyDataSetChanged();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int idPost = mList.get(position).getId();
        Intent i = new Intent(PostsActivity.this, CommentsAtivity.class);
        i.putExtra(Constantes.post_id, idPost);
        startActivity(i);
    }
}
