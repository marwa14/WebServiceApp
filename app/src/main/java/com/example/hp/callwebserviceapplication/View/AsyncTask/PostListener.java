package com.example.hp.callwebserviceapplication.View.AsyncTask;

import com.example.hp.callwebserviceapplication.Model.Post;

import java.util.ArrayList;

/**
 * Created by HP on 15/11/2017.
 */

public interface PostListener {

    void onFail(Exception ex);

    void onResults(ArrayList<Post> posts);
}
