package com.evs.automedic.retrofit;



import com.evs.automedic.model.MsgResponse;
import com.evs.automedic.model.UserResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    //    @FormUrlEncoded
//    @POST("read_mod.php")
//    Call<ListResponse<SectionModel>> Modulelist(
//            @Field("id_language") String action,
//            @Field("id_module") String userId,
//            @Field("token") String email,
//            @Field("api_id") String fullName
//    );
    @FormUrlEncoded
    @POST(".")
    Call<UserResponse> login(
            @Field("action") String action,
            @Field("email") String username,
            @Field("password") String password,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude);

    @Multipart
    @POST(".")
    Call<UserResponse> registrationUser(
            @Part("action") RequestBody action,
            @Part("email") RequestBody email,
            @Part("username") RequestBody username,
            @Part("fullName") RequestBody fullName,
            @Part("contactNumber") RequestBody contactNumber,
            @Part("password") RequestBody password,
            @Part MultipartBody.Part image,
            @Part("device") RequestBody device,
            @Part("deviceToken") RequestBody deviceToken,
            @Part("role") RequestBody role,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude
    );

    @FormUrlEncoded
    @POST(".")
    Call<MsgResponse> changePassword(
            @Field("action") String action,
            @Field("userId") String userId,
            @Field("oldPassword") String oldPassword,
            @Field("newPassword") String newPassword);

    //add feedback
    @FormUrlEncoded
    @POST(".")
    Call<MsgResponse> addFeedback(
            @Field("action") String action,
            @Field("userId") String userId,
            @Field("name") String name,
            @Field("email") String email,
            @Field("text") String text);

    //add comment
    @FormUrlEncoded
    @POST(".")
    Call<MsgResponse> addcomment(
            @Field("action") String action,
            @Field("postId") String postId,
            @Field("userId") String userId,
            @Field("comment") String text);

    //add Forum
    @FormUrlEncoded
    @POST(".")
    Call<MsgResponse> addForum(
            @Field("action") String action,
            @Field("userId") String userId,
            @Field("content") String text);


    @Multipart
    @POST(".")
    Call<UserResponse> editProfile(
            @Part("action") RequestBody action,
            @Part("userId") RequestBody userId,
            @Part("fullName") RequestBody fullname,
            @Part("contactNumber") RequestBody contactNumber,
            @Part("address") RequestBody address,
            @Part MultipartBody.Part image
    );
}
