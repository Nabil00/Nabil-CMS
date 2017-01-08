package com.cms.user.nabiltest.rest;


import com.cms.user.nabiltest.model.Avatar;
import com.cms.user.nabiltest.model.RegistrationRequestData;
import com.cms.user.nabiltest.model.RegistrationResponseData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiInterface {



    @POST("api/v1/registerations.json")
    Call<RegistrationResponseData> createNewUser(@Body RegistrationRequestData user);



    @Multipart
    @POST("api/v1/registerations.json")
    Call<RegistrationResponseData> createNewUser(
            @Part("user[email]") String email,
            @Part("user[user_name]") String user_name,
            @Part("user[password]") String password,
            @Part MultipartBody.Part file
    );

}
