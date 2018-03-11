package com.example.administrator.retrofit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Intent intent;

    private Button basic_btn;
    private Button get_detail_btn;
    private Button get_list_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        basic_btn = findViewById(R.id.basic_btn);
        get_detail_btn = findViewById(R.id.get_detail_btn);
        get_list_btn = findViewById(R.id.get_list_btn);

        basic_btn.setOnClickListener(new MyOnclickListener());
        get_detail_btn.setOnClickListener(new MyOnclickListener());
        get_list_btn.setOnClickListener(new MyOnclickListener());
    }

    private class MyOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.basic_btn:
                    intent = new Intent(MainActivity.this, BasicRequestActivity.class);
                    startActivity(intent);
                    break;
                case R.id.get_detail_btn:
                    intent = new Intent(MainActivity.this, GetDetailActivity.class);
                    intent.putExtra("id", 2);
                    startActivity(intent);
                    break;
                case R.id.get_list_btn:
                    intent = new Intent(MainActivity.this, GetListActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
