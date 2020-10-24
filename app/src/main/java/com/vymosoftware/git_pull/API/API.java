package com.vymosoftware.git_pull.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    //GET Request
    @GET("pulls")
    Call<List<PullModel>> getPulls();
}
