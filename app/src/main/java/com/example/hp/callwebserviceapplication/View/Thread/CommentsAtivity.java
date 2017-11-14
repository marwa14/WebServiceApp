package com.example.hp.callwebserviceapplication.View.Thread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.hp.callwebserviceapplication.Adapter.CommentAdapter;
import com.example.hp.callwebserviceapplication.Model.Comment;
import com.example.hp.callwebserviceapplication.R;
import com.example.hp.callwebserviceapplication.Tools.Constantes;
import com.example.hp.callwebserviceapplication.Tools.UtilsClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommentsAtivity extends AppCompatActivity {

    private int mIdPost;
    private Handler mHandler;
    private String mResponse;
    private List<Comment> mListComment;
    private ListView mListview;
    private CommentAdapter mCommentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_thread);
        mIdPost = getIntent().getExtras().getInt(Constantes.post_id, Constantes.idPostDef);
        mListComment = new ArrayList<>();
        mListview = findViewById(R.id.list);
        mCommentAdapter = new CommentAdapter(mListComment, this);
        mListview.setAdapter(mCommentAdapter);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(Constantes.activityComment);


        Thread getComments = new Thread(new Runnable() {
            public void run() {
                UtilsClass utilsClass = new UtilsClass();
                String res = null;
                try {
                    res = utilsClass.getResponse(Constantes.url + "/" + mIdPost + "/comments");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sendResponse(res);
            }
        });
        getComments.start();
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
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = new JSONObject(jsonArray.getString(i));
            Comment com = new Comment();
            com.setPostId(obj.getInt(Constantes.comment_postId));
            com.setId(obj.getInt(Constantes.comment_id));
            com.setName(obj.getString(Constantes.comment_name));
            com.setBody(obj.getString(Constantes.comment_body));
            com.setEmail(obj.getString(Constantes.comment_email));
            mListComment.add(com);

        }
        mCommentAdapter.notifyDataSetChanged();
    }
}
