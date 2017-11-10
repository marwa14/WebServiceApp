package com.example.hp.callwebserviceapplication.Model;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;


/**
 * Created by HP on 10/11/2017.
 */

public interface PostApi {

    @GET("/posts")
    Call<List<Post>> getPosts();

    @GET("/posts/{param}/comments")
    Call<List<Comment>> getComments(@Path("param") int param);

}

