package com.sample.networkSecurityConfig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sample.httpssample.Api;
import com.sample.httpssample.R;
import com.sample.httpssample.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    public void request(View view) {
        //构建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求BaseUrl地址
                .baseUrl("https://api.uomg.com/")
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //创建网络请求接口对象实例
        Api api = retrofit.create(Api.class);
        //对发送请求进行封装
        Call<Data<Info>> dataCall = api.getJsonData("新歌榜", "json");
        dataCall.enqueue(new Callback<Data<Info>>() {
            @Override
            public void onResponse(Call<Data<Info>> call, Response<Data<Info>> response) {
                Toast.makeText(MainActivity.this, "get回调成功:异步执行", Toast.LENGTH_SHORT).show();
                Data<Info> body = response.body();
                if (body == null){ return;}
                Info info = body.getData();
                if (info == null) {return;}

                String msg = "返回的数据：" + "\n\n" + info.getName() + "\n" + info.getPicurl();
                binding.text.append("\n\n");
                binding.text.append(msg);
                Log.e(TAG, msg);
            }

            @Override
            public void onFailure(Call<Data<Info>> call, Throwable t) {
                Log.e(TAG, "回调失败：" + t.getMessage() + "," + t.toString());
                Toast.makeText(MainActivity.this, "回调失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}