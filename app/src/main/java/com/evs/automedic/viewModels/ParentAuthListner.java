package com.evs.automedic.viewModels;

import androidx.lifecycle.LiveData;

import com.evs.automedic.model.MsgResponse;


public interface ParentAuthListner {
    void onStarted();

  void onOrderSuccess(LiveData<MsgResponse> user);

    void onFailure(String message);
}
