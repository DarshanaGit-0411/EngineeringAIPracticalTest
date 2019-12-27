package com.app.engineeringaipracticaltest.network;

import com.app.engineeringaipracticaltest.model.ClsUserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface BaseApiMethods {

    @GET("api/users")
    Call<ClsUserResponse> getUserList(@Query("limit") int limit, @Query("offset") int offset);

}
