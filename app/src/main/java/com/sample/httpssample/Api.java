package com.sample.httpssample;

import com.sample.networkSecurityConfig.Data;
import com.sample.networkSecurityConfig.Info;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    //get请求
    @GET("api/rand.music")
    Call<Data<Info>> getJsonData(@Query("sort") String sort, @Query("format") String format);
}