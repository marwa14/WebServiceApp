package com.example.hp.callwebserviceapplication.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by HP on 10/11/2017.
 */

public interface PostApi {

    @GET("/posts")
    Call<List<Post>> getPosts();

    @GET("/posts/{param}/comments")
    Call<List<Comment>> getComments(@Path("param") int param);

}

