package com.evs.automedic.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.evs.automedic.model.MsgResponse;
import com.evs.automedic.model.UserResponse;
import com.evs.automedic.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private MutableLiveData<UserResponse> userResponse = new MutableLiveData<>();

    public void hitLoginApi(String email, String password, String latitude, String longitude, UserAuthListener authListener) {
        authListener.onStarted();
        Repository.getInstance().executeLogin(email, password, latitude, longitude, authListener).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    authListener.onSuccess(new MutableLiveData<UserResponse>(response.body()));
                    Log.e("response.body()", response.body().toString());
                } else {
                    switch (response.code()) {
                        case 404:
                            authListener.onFailure("not found");
                            break;
                        case 500:
                            authListener.onFailure("server Error");
                            break;
                        default:
                            authListener.onFailure("unknown error ");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                authListener.onFailure(" network failure :( inform the user and possibly retry");
            }
        });

    }


    public void hitVendorRegistrationApi(String fullname, String email, String phone, String password, String image,
                                         String device_token, String latitude, String longitude, final AuthListener<UserResponse> authListener) {
        authListener.onStarted();
        Repository.getInstance().executeRegistration(email, fullname, phone, password, image,
                device_token, latitude, longitude, authListener).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    authListener.onSuccess(new MutableLiveData<UserResponse>(response.body()));
                    Log.e("response.body()", response.body().toString());
                } else {
                    switch (response.code()) {
                        case 404:
                            authListener.onFailure("not found");
                            break;
                        case 500:
                            authListener.onFailure("server Error");
                            break;
                        default:
                            authListener.onFailure("unknown error ");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                authListener.onFailure(" network failure :( inform the user and possibly retry");
            }
        });

//        authListener.onSuccess(responce);
    }

    public void callChagePassword(String userId, String oldPassword, String newPassword, AuthListener authListener) {
        authListener.onStarted();
        Repository.getInstance().executeChangePassword(userId, oldPassword, newPassword, authListener).enqueue(new Callback<MsgResponse>() {
            @Override
            public void onResponse(Call<MsgResponse> call, Response<MsgResponse> response) {
                if (response.isSuccessful()) {
                    authListener.onSuccess(new MutableLiveData<MsgResponse>(response.body()));
                    Log.e("response.body()", response.body().toString());
                } else {
                    switch (response.code()) {
                        case 404:
                            authListener.onFailure("not found");
                            break;
                        case 500:
                            authListener.onFailure("server Error");
                            break;
                        default:
                            authListener.onFailure("unknown error ");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<MsgResponse> call, Throwable t) {
                authListener.onFailure(" network failure :( inform the user and possibly retry");
                Log.e("res onFailure(): ", "network failure :( inform the user and possibly retry");
            }

        });

    }

    // add feedback
    public void callFeedback(String userId, String name, String email, String text, AuthListener authListener) {
        authListener.onStarted();
        Repository.getInstance().executeaddFeeback(userId, name, email, text, authListener).enqueue(new Callback<MsgResponse>() {
            @Override
            public void onResponse(Call<MsgResponse> call, Response<MsgResponse> response) {
                if (response.isSuccessful()) {
                    authListener.onSuccess(new MutableLiveData<MsgResponse>(response.body()));
                    Log.e("response.body()", response.body().toString());
                } else {
                    switch (response.code()) {
                        case 404:
                            authListener.onFailure("not found");
                            break;
                        case 500:
                            authListener.onFailure("server Error");
                            break;
                        default:
                            authListener.onFailure("unknown error ");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<MsgResponse> call, Throwable t) {
                authListener.onFailure(" network failure :( inform the user and possibly retry");
                Log.e("res onFailure(): ", "network failure :( inform the user and possibly retry");
            }

        });

    }

    // add feedback
    public void calladdComment(String postId, String userId, String text, ParentAuthListner authListener) {
        authListener.onStarted();
        Repository.getInstance().executeaddComment(postId, userId, text, authListener).enqueue(new Callback<MsgResponse>() {
            @Override
            public void onResponse(Call<MsgResponse> call, Response<MsgResponse> response) {
                if (response.isSuccessful()) {
                    authListener.onOrderSuccess(new MutableLiveData<MsgResponse>(response.body()));
                    Log.e("response.body()", response.body().toString());
                } else {
                    switch (response.code()) {
                        case 404:
                            authListener.onFailure("not found");
                            break;
                        case 500:
                            authListener.onFailure("server Error");
                            break;
                        default:
                            authListener.onFailure("unknown error ");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<MsgResponse> call, Throwable t) {
                authListener.onFailure(" network failure :( inform the user and possibly retry");
                Log.e("res onFailure(): ", "network failure :( inform the user and possibly retry");
            }

        });

    }
    // add forumPost

    public void calladdForum(String userId, String text, ParentAuthListner authListener) {
        authListener.onStarted();
        Repository.getInstance().executeaddForum(userId, text, authListener).enqueue(new Callback<MsgResponse>() {
            @Override
            public void onResponse(Call<MsgResponse> call, Response<MsgResponse> response) {
                if (response.isSuccessful()) {
                    authListener.onOrderSuccess(new MutableLiveData<MsgResponse>(response.body()));
                    Log.e("response.body()", response.body().toString());
                } else {
                    switch (response.code()) {
                        case 404:
                            authListener.onFailure("not found");
                            break;
                        case 500:
                            authListener.onFailure("server Error");
                            break;
                        default:
                            authListener.onFailure("unknown error ");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<MsgResponse> call, Throwable t) {
                authListener.onFailure(" network failure :( inform the user and possibly retry");
                Log.e("res onFailure(): ", "network failure :( inform the user and possibly retry");
            }

        });

    }
    // forum post like


    //Edit profile
    public void callEditprofile(String userId, String name, String phoneNumber, String address, String Image, final AuthListener authListener) {
        authListener.onStarted();
        Repository.getInstance().executeUpdateProfile(userId, name, phoneNumber, address, Image)
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful()) {
                            authListener.onSuccess(new MutableLiveData<>(response.body()));
                        } else {
                            switch (response.code()) {
                                case 404:
                                    authListener.onFailure("not found");
                                    break;
                                case 500:
                                    authListener.onFailure("server Error");
                                    break;
                                default:
                                    authListener.onFailure("unknown error ");
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        authListener.onFailure(" network failure :( inform the user and possibly retry");
                        Log.e("res onFailure(): ", "network failure :( inform the user and possibly retry");
                    }
                });


    }

//    public void callNotificationList(AuthListener authListener) {
//        authListener.onStarted();
//        Repository.getInstance().executeNotificationList(authListener);
//    }
//
//    // FAQ
//    public void callFaqList(AuthListener authListener) {
//        authListener.onStarted();
//        Repository.getInstance().executeFaqList(authListener);
//    }
//    // Help
//
//    public void callHelpList(HelpAuthListner authListener) {
//        authListener.onStarted();
//        Repository.getInstance().executeHelpList(authListener);
//    }
//    // privacy Policy
//
//    public void callpolicyList(ParentAuthListner authListener) {
//        authListener.onStarted();
//        Repository.getInstance().executePrivacyList(authListener);
//    }
//
//    // conversation list
//    public void callConversationList(final AuthListener authListener) {
//        authListener.onStarted();
//        Repository.getInstance().executeConvesationList(authListener);
//    }
//
//    // forum list
//    public void callForumList(AuthListener authListener) {
//        authListener.onStarted();
//        Repository.getInstance().executeForumList(authListener);
//    }
//    // forgot password
//    public void callForgotPassword(String email, AuthListener authListener) {
//        authListener.onStarted();
//        Repository.getInstance().executeForgotPassword(email, authListener);
//    }
//
//
//    // NearBy list
//    public void callNearbyList(String userId, String latitude, String longitude, AuthListener authListener) {
//        authListener.onStarted();
//        Repository.getInstance().executeNearByList(userId, latitude, longitude, authListener);
//    }

}
