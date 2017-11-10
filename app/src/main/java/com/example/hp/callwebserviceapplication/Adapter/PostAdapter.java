package com.example.hp.callwebserviceapplication.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hp.callwebserviceapplication.Model.Post;
import com.example.hp.callwebserviceapplication.R;

import java.util.List;

/**
 * Created by HP on 09/11/2017.
 */

public class PostAdapter extends ArrayAdapter<Post> {

    private List<Post> mList;
    private Activity mActivity;

    public PostAdapter(List list, Activity activity) {
        super(activity, R.layout.row_post, list);
        this.mList = list;
        mActivity = activity;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.row_post, parent, false);
        TextView title = rowView.findViewById(R.id.title);
        TextView body = rowView.findViewById(R.id.body);
        title.setText(mList.get(position).getTitle());
        body.setText(mList.get(position).getBody());
        return rowView;

    }


    @Override
    public int getCount() {
        return mList.size();
    }

    public List<Post> getmList() {
        return mList;
    }

    public void setmList(List<Post> mList) {
        this.mList = mList;
    }
}
