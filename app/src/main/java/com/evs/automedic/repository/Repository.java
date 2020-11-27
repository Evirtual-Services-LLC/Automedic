package com.evs.automedic.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.evs.automedic.model.MsgResponse;
import com.evs.automedic.model.UserResponse;
import com.evs.automedic.retrofit.ApiInterfaceService;
import com.evs.automedic.viewModels.AuthListener;
import com.evs.automedic.viewModels.ParentAuthListner;
import com.evs.automedic.viewModels.UserAuthListener;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static Repository repository;

    public static Repository getInstance() {
        if (repository != null) {
            return repository;
        } else {
            repository = new Repository();
            return repository;
        }
    }

    public Call<UserResponse> executeLogin(String username, String password, String latitude, String longitude, final UserAuthListener authListener) {

        return ApiInterfaceService.getApiService().login("login", username, password, latitude, longitude);
    }

    public Call<UserResponse> executeRegistration(String email, String fullname, String phone, String password, String Image,
                                                  String device_token, String latitude, String longitude,
                                                  final AuthListener authListener) {
        return ApiInterfaceService.getApiService().registrationUser(bodyPart("registration"), bodyPart(email), bodyPart(""), bodyPart(fullname), bodyPart(phone), bodyPart(password), imagePart(Image, "image"), bodyPart("Android"), bodyPart(device_token), bodyPart("Member"), bodyPart(latitude), bodyPart(longitude));

    }

    public Call<MsgResponse> executeChangePassword(String userId, String oldPassword, String newPassword, final AuthListener authListener) {
        //noinspection NullableProblems
        return ApiInterfaceService.getApiService().changePassword("changePassword", userId, oldPassword, newPassword);
    }

    //feedback
    public Call<MsgResponse> executeaddFeeback(String userId, String name, String email, String text, final AuthListener authListener) {
        //noinspection NullableProblems
        return ApiInterfaceService.getApiService().addFeedback("addfeedback", userId, name, email, text);
    }

    //add comment
    public Call<MsgResponse> executeaddComment(String postId, String userId, String text, final ParentAuthListner authListener) {
        //noinspection NullableProblems
        return ApiInterfaceService.getApiService().addcomment("addcomment", postId, userId, text);
    }

    //add comment
    public Call<MsgResponse> executeaddForum(String userId, String text, final ParentAuthListner authListener) {
        //noinspection NullableProblems
        return ApiInterfaceService.getApiService().addForum("addforum", userId, text);
    }



    public Call<UserResponse> executeUpdateProfile(String userId, String name, String phoneNumber, String address, String Image) {
        return ApiInterfaceService.getApiService().editProfile(bodyPart("editprofile"), bodyPart(userId),
                bodyPart(name), bodyPart(phoneNumber), bodyPart(address), imagePart(Image, "image"));
    }
//
//    //notifcation list
//    public void executeNotificationList(final AuthListener authListener) {
//        //noinspection NullableProblems
//        ApiInterfaceService.getApiService().hitNotification("notificationlist")
//                .enqueue(new Callback<ListResponse<NotifcationModel>>() {
//                    @Override
//                    public void onResponse(Call<ListResponse<NotifcationModel>> call, Response<ListResponse<NotifcationModel>> response) {
//                        if (response.isSuccessful()) {
//                            authListener.onSuccess(new MutableLiveData(response.body()));
////                            loginResponse.setValue(response.body());
//                        } else {
//                            switch (response.code()) {
//                                case 404:
//                                    authListener.onFailure("not found");
//                                    break;
//                                case 500:
//                                    authListener.onFailure("server Error");
//                                    break;
//                                default:
//                                    authListener.onFailure("unknown error " + response.code());
//                                    break;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ListResponse<NotifcationModel>> call, Throwable t) {
//                        authListener.onFailure(" network failure :( inform the user and possibly retry");
//                        Log.e("res onFailure(): ", "network failure :( inform the user and possibly retry");
//                    }
//                });
//    }
//
//
//    //conversation list
//
//    public void executeConvesationList(final AuthListener authListener) {
//        authListener.onStarted();
//        //noinspection NullableProblems
//        ApiInterfaceService.getApiService().hitConversation("category")
//                .enqueue(new Callback<ListResponse<ConversationModel>>() {
//                    @Override
//                    public void onResponse(Call<ListResponse<ConversationModel>> call, Response<ListResponse<ConversationModel>> response) {
//                        if (response.isSuccessful()) {
//                            authListener.onSuccess(new MutableLiveData(response.body()));
////                            loginResponse.setValue(response.body());
//                        } else {
//                            switch (response.code()) {
//                                case 404:
//                                    authListener.onFailure("not found");
//                                    break;
//                                case 500:
//                                    authListener.onFailure("server Error");
//                                    break;
//                                default:
//                                    authListener.onFailure("unknown error " + response.code());
//                                    break;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ListResponse<ConversationModel>> call, Throwable t) {
//                        authListener.onFailure(" network failure :( inform the user and possibly retry");
//                        Log.e("res onFailure(): ", "network failure :( inform the user and possibly retry");
//                    }
//                });
//    }
//
//    //Faq list
//
//    public void executeFaqList(final AuthListener authListener) {
//        authListener.onStarted();
//        //noinspection NullableProblems
//        ApiInterfaceService.getApiService().hitFaq("faq")
//                .enqueue(new Callback<ListResponse<FAQDataModel>>() {
//                    @Override
//                    public void onResponse(Call<ListResponse<FAQDataModel>> call, Response<ListResponse<FAQDataModel>> response) {
//                        if (response.isSuccessful()) {
//                            authListener.onSuccess(new MutableLiveData(response.body()));
////                            loginResponse.setValue(response.body());
//                        } else {
//                            switch (response.code()) {
//                                case 404:
//                                    authListener.onFailure("not found");
//                                    break;
//                                case 500:
//                                    authListener.onFailure("server Error");
//                                    break;
//                                default:
//                                    authListener.onFailure("unknown error " + response.code());
//                                    break;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ListResponse<FAQDataModel>> call, Throwable t) {
//                        authListener.onFailure(" network failure :( inform the user and possibly retry");
//                        Log.e("res onFailure(): ", "network failure :( inform the user and possibly retry");
//                    }
//                });
//    }
// //Faq list
//
//    public void executeHelpList(final HelpAuthListner authListener) {
//        authListener.onStarted();
//        //noinspection NullableProblems
//        ApiInterfaceService.getApiService().hitHelp("help")
//                .enqueue(new Callback<HelpResponse>() {
//                    @Override
//                    public void onResponse(Call<HelpResponse> call, Response<HelpResponse> response) {
//                        if (response.isSuccessful()) {
//                            authListener.onSuccess(new MutableLiveData(response.body()));
////                            loginResponse.setValue(response.body());
//                        } else {
//                            switch (response.code()) {
//                                case 404:
//                                    authListener.onFailure("not found");
//                                    break;
//                                case 500:
//                                    authListener.onFailure("server Error");
//                                    break;
//                                default:
//                                    authListener.onFailure("unknown error " + response.code());
//                                    break;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<HelpResponse> call, Throwable t) {
//                        authListener.onFailure(" network failure :( inform the user and possibly retry");
//                        Log.e("res onFailure(): ", "network failure :( inform the user and possibly retry");
//                    }
//                });
//    }
//    //forgot password
//    public void executeForgotPassword(String email, final AuthListener authListener) {
//        //noinspection NullableProblems
//        ApiInterfaceService.getApiService().forgotpassword("forgotpassword", email)
//                .enqueue(new Callback<MsgResponse>() {
//                    @Override
//                    public void onResponse(Call<MsgResponse> call, Response<MsgResponse> response) {
//                        if (response.isSuccessful()) {
//
//                            authListener.onSuccess(new MutableLiveData(response.body()));
////                            loginResponse.setValue(response.body());
//
//                        } else {
//                            switch (response.code()) {
//                                case 404:
//                                    authListener.onFailure("not found");
//                                    break;
//                                case 500:
//                                    authListener.onFailure("server Error");
//                                    break;
//                                default:
//                                    authListener.onFailure("unknown error ");
//                                    break;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<MsgResponse> call, Throwable t) {
//                        authListener.onFailure(" network failure :( inform the user and possibly retry");
//                        Log.e("res onFailure(): ", "network failure :( inform the user and possibly retry");
//                    }
//                });
//
////        return response;
//    }
// //Faq list
//
//    public void executePrivacyList(final ParentAuthListner authListener) {
//        authListener.onStarted();
//        //noinspection NullableProblems
//        ApiInterfaceService.getApiService().hitPrivacy("termAndConditions")
//                .enqueue(new Callback<MsgResponse>() {
//                    @Override
//                    public void onResponse(Call<MsgResponse> call, Response<MsgResponse> response) {
//                        if (response.isSuccessful()) {
//                            authListener.onOrderSuccess(new MutableLiveData(response.body()));
////                            loginResponse.setValue(response.body());
//                        } else {
//                            switch (response.code()) {
//                                case 404:
//                                    authListener.onFailure("not found");
//                                    break;
//                                case 500:
//                                    authListener.onFailure("server Error");
//                                    break;
//                                default:
//                                    authListener.onFailure("unknown error " + response.code());
//                                    break;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<MsgResponse> call, Throwable t) {
//                        authListener.onFailure(" network failure :( inform the user and possibly retry");
//                        Log.e("res onFailure(): ", "network failure :( inform the user and possibly retry");
//                    }
//                });
//    }
//
//    //forum list
//
//    public void executeForumList(final AuthListener authListener) {
//        authListener.onStarted();
//        //noinspection NullableProblems
//        ApiInterfaceService.getApiService().forumList("forumlist")
//                .enqueue(new Callback<ListResponse<ForumModel>>() {
//                    @Override
//                    public void onResponse(Call<ListResponse<ForumModel>> call, Response<ListResponse<ForumModel>> response) {
//                        if (response.isSuccessful()) {
//                            authListener.onSuccess(new MutableLiveData(response.body()));
////                            loginResponse.setValue(response.body());
//                        } else {
//                            switch (response.code()) {
//                                case 404:
//                                    authListener.onFailure("not found");
//                                    break;
//                                case 500:
//                                    authListener.onFailure("server Error");
//                                    break;
//                                default:
//                                    authListener.onFailure("unknown error " + response.code());
//                                    break;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ListResponse<ForumModel>> call, Throwable t) {
//                        authListener.onFailure(" network failure :( inform the user and possibly retry");
//                        Log.e("res onFailure(): ", "network failure :( inform the user and possibly retry");
//                    }
//                });
//    }
//
//    //forum list
//    public void executeNearByList(String userId, String latitude, String longitude, final AuthListener authListener) {
//        authListener.onStarted();
//        //noinspection NullableProblems
//        ApiInterfaceService.getApiService().forumNearByList("findvendor", userId, latitude, longitude)
//                .enqueue(new Callback<ListResponse<NearByModel>>() {
//                    @Override
//                    public void onResponse(Call<ListResponse<NearByModel>> call, Response<ListResponse<NearByModel>> response) {
//                        if (response.isSuccessful()) {
//                            authListener.onSuccess(new MutableLiveData(response.body()));
////                            loginResponse.setValue(response.body());
//                        } else {
//                            switch (response.code()) {
//                                case 404:
//                                    authListener.onFailure("not found");
//                                    break;
//                                case 500:
//                                    authListener.onFailure("server Error");
//                                    break;
//                                default:
//                                    authListener.onFailure("unknown error " + response.code());
//                                    break;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ListResponse<NearByModel>> call, Throwable t) {
//                        authListener.onFailure(" network failure :( inform the user and possibly retry");
//                        Log.e("res onFailure(): ", "network failure :( inform the user and possibly retry");
//                    }
//                });
//    }

    private MultipartBody.Part imagePart(String selectedpath, String name) {
        MultipartBody.Part image = null;
        if (!selectedpath.equals("")) {
            File file = new File(selectedpath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image = MultipartBody.Part.createFormData(name + "", file.getName(), requestFile);
        }
        return image;

    }

    private RequestBody bodyPart(String name) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), name);

    }
}
