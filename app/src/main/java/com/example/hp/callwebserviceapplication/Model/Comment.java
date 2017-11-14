package com.example.hp.callwebserviceapplication.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 09/11/2017.
 */

public class Comment {
    @SerializedName("postId")
    private int mPostId;
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("body")
    private String mBody;

    public Comment(int postId, int id, String name, String email, String body) {
        mPostId = postId;
        mId = id;
        mName = name;
        mEmail = email;
        mBody = body;
    }

    public Comment() {
    }

    public int getPostId() {
        return mPostId;
    }

    public void setPostId(int postId) {
        mPostId = postId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }
}
