package com.example.hp.callwebserviceapplication.View.AsyncTask;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.hp.callwebserviceapplication.Adapter.CommentAdapter;
import com.example.hp.callwebserviceapplication.Model.Comment;
import com.example.hp.callwebserviceapplication.R;
import com.example.hp.callwebserviceapplication.Tools.Constantes;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity implements CommentListener {

    private int mIdPost;
    private ListComments mAsyncComments;
    private CommentAdapter mCommentAdapter;
    private ListView mListView;
    private AlertDialog mDialog;
    private static final String TAG = CommentsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_asynctask);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.comment);
        mIdPost = getIntent().getExtras().getInt(Constantes.POST_ID, Constantes.ID_POST_DEF);
        mListView = findViewById(R.id.list_comment);
        mCommentAdapter = new CommentAdapter(new ArrayList(), this);
        mListView.setAdapter(mCommentAdapter);

        View view = getLayoutInflater().inflate(R.layout.progress, null);
        mDialog = new AlertDialog.Builder(this).setView(view).create();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.show();

        mAsyncComments = new ListComments(this);
        mAsyncComments.execute(mIdPost);


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
        if (mAsyncComments != null)
            mAsyncComments.cancel();
        super.onDestroy();
    }
}
