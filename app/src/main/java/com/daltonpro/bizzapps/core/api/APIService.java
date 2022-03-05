package com.daltonpro.bizzapps.core.api;

import com.daltonpro.bizzapps.model.request.LoginRequest;
import com.daltonpro.bizzapps.model.request.upload.UploadPhotoRequest;
import com.daltonpro.bizzapps.model.request.upload.UploadRequest;
import com.daltonpro.bizzapps.model.response.download.callplan.DownloadResponseCallplan;
import com.daltonpro.bizzapps.model.response.download.customer.DownloadResponseCustomer;
import com.daltonpro.bizzapps.model.response.download.info.DownloadResponse;
import com.daltonpro.bizzapps.model.response.login.LoginResponse;
import com.daltonpro.bizzapps.model.response.upload.PhotoUploadResponse;
import com.daltonpro.bizzapps.model.response.upload.UploadResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @POST("usermobile/login")
    Call<LoginResponse> login(@Body LoginRequest loginModel);

    @GET("download")
    Call<DownloadResponse> download(@Header("Authorization") String token);

    @GET("download/callplan")
    Call<DownloadResponseCallplan> downloadCallplan(@Header("Authorization") String token, @Query("limit") int limit, @Query("offset") int offset);

    @GET("download/customercallplan")
    Call<DownloadResponseCustomer> downloadCustomer(@Header("Authorization") String token, @Query("limit") int limit, @Query("offset") int offset);

    @POST("upload/monitorusermobile")
    Call<UploadResponse> upload(@Header("Authorization") String token, @Body UploadRequest uploadRequest);

    @POST("upload/photomonitorusermobile")
    Call<PhotoUploadResponse> uploadPhoto(@Header("Authorization") String token, @Body UploadPhotoRequest uploadPhotoRequest);

}
