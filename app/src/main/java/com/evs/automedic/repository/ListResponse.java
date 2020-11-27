package com.evs.automedic.repository;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListResponse<T> {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<T> list;

    public ListResponse(String status, ArrayList<T> items) {
        this.status=status;
        this.list = items;
    }
    public List<T> getList() {
        return list;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }
}
