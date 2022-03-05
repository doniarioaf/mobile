package com.daltonpro.bizzapps.model.response.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDataResponse {


    @SerializedName("permissions")
    @Expose
    private String permissions;

    @SerializedName("token")
    @Expose
    private String token;

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}