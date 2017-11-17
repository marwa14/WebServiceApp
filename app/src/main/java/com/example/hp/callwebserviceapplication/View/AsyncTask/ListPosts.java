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

public class ListPosts extends AsyncTask<String, String, Object> {

    private PostListener mListener;

    public ListPosts(PostListener listener) {
        mListener = listener;
    }


    @Override
    protected Object doInBackground(String... strings) {
        try {
            return parseData(UtilsClass.getResponse(Constantes.URL));
        } catch (JSONException e) {
            e.printStackTrace();
            return e;
        } catch (IOException e) {
            e.printStackTrace();
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object posts) {
        if (mListener != null) {
            if (posts instanceof Exception) {
                mListener.onFail((Exception) posts);
            } else {
                mListener.onResults((ArrayList<Post>) posts);
            }
        }
    }

    public ArrayList<Post> parseData(String data) throws JSONException {
        ArrayList<Post> listPosts = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(data);
        for (int i = 0; i < jsonArray.length() - 1; i++) {
            JSONObject obj = new JSONObject(jsonArray.getString(i));
            Post post = new Post();
            post.setUserId(obj.optInt(Constantes.POST_USER_ID));
            post.setId(obj.optInt(Constantes.POST_ID));
            post.setTitle(obj.optString(Constantes.POST_TITLE));
            post.setBody(obj.optString(Constantes.POST_BODY));
            listPosts.add(post);
        }
        return listPosts;
    }

    public void cancel() {
        cancel(true);
        mListener = null;
    }
}
