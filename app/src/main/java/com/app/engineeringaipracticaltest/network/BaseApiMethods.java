package com.app.engineeringaipracticaltest.network;

import com.app.engineeringaipracticaltest.model.ClsUserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by PCQ44 on 12/27/2019.
 */
public interface BaseApiMethods {

    @GET("api/users")
    Call<ClsUserResponse> getUserList(@Query("limit") int limit, @Query("offset") int offset);

}
