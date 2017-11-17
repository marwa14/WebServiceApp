package com.example.hp.callwebserviceapplication.View.Thread;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.hp.callwebserviceapplication.Adapter.CommentAdapter;
import com.example.hp.callwebserviceapplication.Model.Comment;
import com.example.hp.callwebserviceapplication.R;
import com.example.hp.callwebserviceapplication.Tools.Constantes;
import com.example.hp.callwebserviceapplication.View.AsyncTask.CommentListener;

import java.util.ArrayList;
import java.util.List;

public class CommentsAtivity extends AppCompatActivity implements CommentListener {

    private int mIdPost;
    private Handler mHandler;
    private List<Comment> mListComment;
    private ListView mListview;
    private CommentAdapter mCommentAdapter;
    private CommentThread mCommentThread;
    private AlertDialog mDialog;
    private static final String TAG = CommentsAtivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_thread);
        mIdPost = getIntent().getExtras().getInt(Constantes.POST_ID, Constantes.ID_POST_DEF);
        mListComment = new ArrayList<>();
        mListview = findViewById(R.id.list);
        mCommentAdapter = new CommentAdapter(mListComment, this);
        mListview.setAdapter(mCommentAdapter);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.comment);

        View view = getLayoutInflater().inflate(R.layout.progress, null);
        mDialog = new AlertDialog.Builder(this).setView(view).create();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.show();
        mHandler = new Handler(getWSCallback());
        mCommentThread = new CommentThread(mHandler, mIdPost);
        mCommentThread.start();


    }


    @NonNull
    private Handler.Callback getWSCallback() {
        return new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.obj instanceof Exception) {
                    onFail((Exception) msg.obj);
                } else {
                    onResult((ArrayList<Comment>) msg.obj);
                }
                return true;
            }
        };
    }

    @Override
    public void onFail(Exception e) {
        Log.i(TAG, e.toString());
    }

    @Override
    public void onResult(List<Comment> list) {
        mDialog.dismiss();
        mCommentAdapter.setResult(list);
    }

    @Override
    protected void onDestroy() {
        mCommentThread.cancel();
        super.onDestroy();

    }
}
