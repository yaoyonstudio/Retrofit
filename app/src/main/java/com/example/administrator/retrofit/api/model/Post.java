package com.example.administrator.retrofit.api.model;

/**
 * Created by Administrator on 2018/3/11.
 */

public class Post {
    private Integer id;
    private String post_title;
    private String post_content;

    public Post(String post_title, String post_content) {
        this.post_title = post_title;
        this.post_content = post_content;
    }

    public Integer getId() {
        return id;
    }

    public String getPost_title() {
        return post_title;
    }

    public String getPost_content() {
        return post_content;
    }

    public void show() {
        System.out.println(id);
        System.out.println(post_title);
    }
}
