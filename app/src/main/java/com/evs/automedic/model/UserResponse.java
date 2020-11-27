package com.evs.automedic.model;


import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private UserModel user;

    public UserResponse(String status, String msg, UserModel user) {
        this.status = status;
        this.msg = msg;
        this.user = user;

    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
