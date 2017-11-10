package com.example.hp.callwebserviceapplication.View.AsyncTask;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.hp.callwebserviceapplication.Adapter.CommentAdapter;
import com.example.hp.callwebserviceapplication.Model.Comment;
import com.example.hp.callwebserviceapplication.R;
import com.example.hp.callwebserviceapplication.Tools.Constantes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CommentsActivity extends AppCompatActivity {

    private int mIdPost;
    private ListComments mAsyncComments;
    private CommentAdapter mCommentAdapter;
    private List<Comment> mListComment;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments2);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(Constantes.activityComment);
        mListView = findViewById(R.id.list_comment);
        mListComment = new ArrayList<>();
        mIdPost = getIntent().getExtras().getInt(Constantes.post_id, Constantes.idPostDef);
        mAsyncComments = new ListComments();
        try {
            mListComment = mAsyncComments.execute(String.valueOf(mIdPost)).get();
            mCommentAdapter = new CommentAdapter(mListComment, this);
            mListView.setAdapter(mCommentAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
