package com.example.hp.callwebserviceapplication.View.AsyncTask;

import android.os.AsyncTask;

import com.example.hp.callwebserviceapplication.Model.Comment;
import com.example.hp.callwebserviceapplication.Tools.Constantes;
import com.example.hp.callwebserviceapplication.Tools.UtilsClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 09/11/2017.
 */

public class ListComments extends AsyncTask<String, String, List<Comment>> {
    private String mIdPost;
    private List<Comment> mListComment;

    @Override
    protected List<Comment> doInBackground(String... strings) {
        mListComment = new ArrayList<>();
        mIdPost = strings[0];
        UtilsClass utilsClass = new UtilsClass();
        try {
            parseData(utilsClass.getResponse(Constantes.url + "/" + mIdPost + "/comments"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mListComment;

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
    }
}
