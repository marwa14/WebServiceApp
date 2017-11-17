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

public class ListComments extends AsyncTask<Integer, String, Object> {

    private CommentListener mListener;

    public ListComments(CommentListener commentListener) {
        mListener = commentListener;


    }

    @Override
    protected Object doInBackground(Integer... strings) {
        int id = strings[0];
        try {
            return parseData(UtilsClass.getResponse(Constantes.URL + "/" + id + "/comments"));
        } catch (JSONException e) {
            return e;
        } catch (IOException e) {
            return e;
        }
    }

    public List<Comment> parseData(String data) throws JSONException {
        List<Comment> list = new ArrayList<Comment>();
        JSONArray jsonArray = new JSONArray(data);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = new JSONObject(jsonArray.optString(i, ""));
            Comment com = new Comment();
            com.setPostId(obj.optInt(Constantes.COMMENT_POST_ID));
            com.setId(obj.optInt(Constantes.COMMENT_ID));
            com.setName(obj.optString(Constantes.COMMENT_NAME));
            com.setBody(obj.optString(Constantes.COMMENT_BODY));
            com.setEmail(obj.optString(Constantes.COMMENT_EMAIL));
            list.add(com);
        }
        return list;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (mListener != null) {
            if (o instanceof Exception) {
                mListener.onFail((Exception) o);
            } else {
                mListener.onResult((ArrayList<Comment>) o);
            }

        }

    }

    public void cancel() {
        this.cancel(true);
        mListener = null;
    }
}
