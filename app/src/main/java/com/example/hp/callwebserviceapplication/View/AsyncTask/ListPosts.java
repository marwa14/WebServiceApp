package com.example.hp.callwebserviceapplication.View.AsyncTask;

import android.os.AsyncTask;

import com.example.hp.callwebserviceapplication.Model.Post;
import com.example.hp.callwebserviceapplication.Tools.Constantes;
import com.example.hp.callwebserviceapplication.Tools.UtilsClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by HP on 09/11/2017.
 */

public class ListPosts extends AsyncTask<String, String, ArrayList<Post>> {

    private ArrayList<Post> mListPosts;


    @Override
    protected ArrayList<Post> doInBackground(String... strings) {
        mListPosts = new ArrayList<>();
        UtilsClass utilsClass = new UtilsClass();
        try {
            parseData(utilsClass.getResponse(Constantes.url));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mListPosts;
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
            mListPosts.add(post);

        }

    }

    @Override
    protected void onPostExecute(ArrayList<Post> posts) {
        super.onPostExecute(posts);

    }
}
