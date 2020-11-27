package com.evs.automedic.viewModels;

import androidx.lifecycle.LiveData;

import com.evs.automedic.model.UserResponse;


public interface UserAuthListener {

    void onStarted();

    void onSuccess(LiveData<UserResponse> user);

    void onFailure(String message);

}
