package com.cms.user.nabiltest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nabil on 1/4/2017.
 */
public class RegistrationRequestData {

    @SerializedName("user[email]")
    @Expose
    private String email;

    @SerializedName("user[user_name]")
    @Expose
    private String user_name;

    @SerializedName("user[password]")
    @Expose
    private String password;

    @SerializedName("user[password_confirmation]")
    @Expose
    private String passwordConfirmation;

//    @SerializedName("user[avatar]")
//    private Avatar avatar;
//
//    @SerializedName("user[gender]")
//    private String gender;
//
//    @SerializedName("user[birth_date]")
//    private String birth_date;


    public String getUser_name() {
        return user_name;
    }

    public RegistrationRequestData() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
    private class Avatar {

        @SerializedName("url")
        private String url;

        public Avatar(String url) {
            this.url = url;
        }

        public String getUrl() { return this.url; }
        public void setUrl(String url) { this.url = url; }
    }
}
