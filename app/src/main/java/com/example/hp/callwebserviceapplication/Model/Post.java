package com.example.hp.callwebserviceapplication.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 09/11/2017.
 */

public class Post {
    @SerializedName("userId")
    private int mUserId;
    @SerializedName("id")
    private int mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("body")
    private String mBody;

    public Post() {

    }

    public Post(int userId, int id, String title, String body) {
        mUserId = userId;
        mId = id;
        mTitle = title;
        mBody = body;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }
}
