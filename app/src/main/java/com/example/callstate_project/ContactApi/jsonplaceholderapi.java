package com.example.callstate_project.ContactApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface jsonplaceholderapi
{

    @GET("posts")
    Call<List<post>> getposts();
}

