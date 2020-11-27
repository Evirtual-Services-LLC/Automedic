package com.evs.automedic.model;

import com.google.gson.annotations.SerializedName;

public class NotifcationModel {
    @SerializedName("notificationId")
    private String Id;
     @SerializedName("created")
    private String created;

    public String getCreated() {
        return created;
    }

    @SerializedName("message")
    private String message;

    public String getId() {
        return Id;
    }

    public String getMessage() {
        return message;
    }
}
