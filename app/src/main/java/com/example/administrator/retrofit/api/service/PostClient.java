package com.example.administrator.retrofit.api.service;

import com.example.administrator.retrofit.api.model.Post;
import com.example.administrator.retrofit.api.model.PostList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2018/3/11.
 */

public interface PostClient {
    @GET("/restapi/posts/{id}")
    Call<Post> getPostDetail(@Path("id") Integer id);

    @GET("/restapi/posts")
    Call<PostList> getPostList();
}
