package com.example.gym.Api;

import com.example.gym.Model.LoginRequest;
import com.example.gym.Model.LoginResponse;
import com.example.gym.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("users/{username}")
    static Call<User> getUser(@Path("username") String username) {
        return null;
    }


    @GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);


    @POST("users/new")
    Call<User> createUser(@Body User user);

    @POST("api/Auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

}


