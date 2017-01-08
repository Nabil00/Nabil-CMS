package com.cms.user.nabiltest.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class RegistrationResponseData {

    @SerializedName("email")
    private String email;

    @SerializedName("user_name")
    private String user_name;

    @SerializedName("avatar")
    private Avatar avatar;

//    @SerializedName("gender")
//    private String gender;
//
//    @SerializedName("birth_date")
//    private String birth_date;

    @SerializedName("authentication_token")
    private String authentication_token;


    public RegistrationResponseData() {
    }

    public String getEmail() {
        return email;
    }

    public String getUser_name() {
        return user_name;
    }

    public Avatar getAvatar() {
        return avatar;
    }

//    public String getGender() {
//        return gender;
//    }
//
//    public String getBirth_date() {
//        return birth_date;
//    }
//
//    public String getAuthentication_token() {
//        return authentication_token;
//    }
//

}
