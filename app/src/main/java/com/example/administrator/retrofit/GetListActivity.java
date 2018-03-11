package com.example.administrator.retrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.administrator.retrofit.api.model.Post;
import com.example.administrator.retrofit.api.model.PostList;
import com.example.administrator.retrofit.api.service.PostClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetListActivity extends AppCompatActivity {
    private Intent intent;
    private ProgressBar progressBar;

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Post> listData;
    private List<Map<String, Object>> listDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_list);

        listView = findViewById(R.id.post_list);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        Request();
    }

    public void showMsg(String msg) {
        Toast.makeText(GetListActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void Request() {

        // 创建 Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pgadmin.thatyou.cn/restapi/")                 // 设置网络请求Url
                .addConverterFactory(GsonConverterFactory.create())     // 设置使用gson解析
                .build();

        // 创建 网络请求接口 的实例
        PostClient postClient = retrofit.create(PostClient.class);

        //对 发送请求 进行封装
        Call<PostList> call = postClient.getPostList();

        // 发送网络请求(异步)
        call.enqueue(new Callback<PostList>() {
            // 请求成功时候的回调
            @Override
            public void onResponse(Call<PostList> call, final Response<PostList> response) {
                //请求处理,输出结果
                progressBar.setVisibility(View.GONE);
                listData = response.body().getResults();
                showMsg("请求成功");

                listDate = new ArrayList<>();

                for (int i = 0; i < listData.size(); i++) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", listData.get(i).getId());
                    map.put("post_title", listData.get(i).getPost_title());
                    listDate.add(map);
                }

                listView.setAdapter(new SimpleAdapter(GetListActivity.this, listDate, R.layout.post_item, new String[]{"post_title"}, new int[]{R.id.post_item_title}));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Integer post_id = Integer.parseInt(listDate.get(position).get("id").toString());
                        intent = new Intent(GetListActivity.this, GetDetailActivity.class);
                        intent.putExtra("id", post_id);
                        startActivity(intent);
                    }
                });
            }



            // 请求失败时候的回调
            @Override
            public void onFailure(Call<PostList> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//    }
}
