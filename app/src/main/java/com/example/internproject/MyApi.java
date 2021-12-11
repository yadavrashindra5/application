package com.example.internproject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyApi {
    @GET("api/users")
    Call<Class1>getData(@Query("page")int page,@Query("per_page")int per_page);
}
