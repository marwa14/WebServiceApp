package com.example.hp.callwebserviceapplication.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hp.callwebserviceapplication.Model.Comment;
import com.example.hp.callwebserviceapplication.R;

import java.util.List;

/**
 * Created by HP on 09/11/2017.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {
    private List<Comment> mList;
    private Activity mActivity;

    public CommentAdapter(List list, Activity activity) {
        super(activity, R.layout.row_post, list);
        this.mList = list;
        mActivity = activity;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.row_comment, parent, false);
        TextView name = rowView.findViewById(R.id.name);
        TextView body = rowView.findViewById(R.id.body);
        TextView email = rowView.findViewById(R.id.email);
        name.setText(mList.get(position).getName());
        body.setText(mList.get(position).getBody());
        email.setText(mList.get(position).getEmail());
        return rowView;

    }


    @Override
    public int getCount() {
        return mList.size();
    }

    public void setResult(List<Comment> list)
    {
        mList.addAll(list);
        this.notifyDataSetChanged();
    }

}