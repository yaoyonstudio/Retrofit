package com.example.administrator.retrofit.api.model;

import java.util.List;

/**
 * Created by Administrator on 2018/3/11.
 */

public class PostList {
    private Integer count;
    private List<Post> results;

    public Integer getCount() {
        return count;
    }

    public List<Post> getResults() {
        return results;
    }

    public void show() {
        System.out.println(count);
        System.out.println(results);
    }
}
