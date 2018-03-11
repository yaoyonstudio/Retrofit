package com.example.administrator.retrofit;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.retrofit.api.model.Post;
import com.example.administrator.retrofit.api.service.PostClient;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetDetailActivity extends AppCompatActivity {
    private Handler mHandler;
    private ProgressBar progressBar;
    private Integer id;

    private TextView post_title;
    private TextView post_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_detail);

        // 获取前一个页面传过来的数据
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        mHandler = new Handler();

        post_title = findViewById(R.id.post_title);
        post_content = findViewById(R.id.post_content);

        Request();
    }

    public void showMsg(String msg) {
        Toast.makeText(GetDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            URL url = null;
            try {
                url = new URL(source);
                drawable = Drawable.createFromStream(url.openStream(), "img");
            } catch (Exception e) {
                return null;
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                    .getIntrinsicHeight());
            return drawable;
        }
    };

    public void Request() {

        // 创建 Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pgadmin.thatyou.cn/restapi/")                 // 设置网络请求Url
                .addConverterFactory(GsonConverterFactory.create())     // 设置使用gson解析
                .build();

        // 创建 网络请求接口 的实例
        PostClient postClient = retrofit.create(PostClient.class);

        //对 发送请求 进行封装
        Call<Post> call = postClient.getPostDetail(id);

        // 发送网络请求(异步)
        call.enqueue(new Callback<Post>() {
            // 请求成功时候的回调
            @Override
            public void onResponse(Call<Post> call, final Response<Post> response) {
                //请求处理,输出结果
                progressBar.setVisibility(View.GONE);
                response.body().show();

                post_title.setText(response.body().getPost_title());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Spanned text = Html.fromHtml(response.body().getPost_content(), imgGetter, null);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                post_content.setText(text);
                            }
                        });
                    }
                }).start();

                showMsg("请求成功");
            }

            // 请求失败时候的回调
            @Override
            public void onFailure(Call<Post> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
    }
}
