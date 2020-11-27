package com.evs.automedic.chatActivity;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import com.evs.automedic.R;
import com.evs.automedic.utils.Global;
import com.evs.automedic.utils.SessionManager;
import com.evs.automedic.utils.Utills;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 2015;
    private static final int PICK_IMAGE = 100;
    private static final int PICK_DOC = 200;
    private static final int PICK_VIDEO = 441;
    public static RecyclerView recycler_comments;
    DatabaseReference mDatabaseReference, reference, reference_check;
    String person_id, userId, person_name, person_image_url,
            comment_String, time_String, normal_value, swap_value, Type; //jobid,jobName;
    Comment comment;
    String user_id, firebaseid, groupimageicon, devicetoken;
    EditText edtxt_comment;
    ImageView image_send;
    //    ImageView image_attach;
    ImageView back;
    TextView name;
    ProgressDialog mProgressDialog;
    ImageView ivImage;
    SharedPreferences prefs;
    Boolean startchat = false;
    String lastmsg = "";
    Uri uri = null;
    Uri selectedImage, mCapturedImageURI, selectedVideo;
    Cursor cursor;
    int columnindex, i;
    String file_path, image_path = " ", video_path = " ";
    BitmapFactory.Options options;
    Dialog dialog_attachment;
    LinearLayout linear_camera, linear_document, linear_video, linear_gallery;
    private FirebaseAuth firebaseAuth;
    RequestQueue queue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Utills.StatusBarColour(ChatActivity.this);
        }
        setContentView(R.layout.chat_layout);
        queue = Volley.newRequestQueue(ChatActivity.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        prefs = PreferenceManager.getDefaultSharedPreferences(ChatActivity.this);
        firebaseAuth = FirebaseAuth.getInstance();


        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        user_id = SessionManager.get_user_id(prefs);
        options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        recycler_comments = (RecyclerView) findViewById(R.id.recycler_comments);
        edtxt_comment = (EditText) findViewById(R.id.edtxt_comment);
        image_send = findViewById(R.id.image_send);
//        image_attach = (ImageView) findViewById(R.id.image_attach);
        name = (TextView) toolbar.findViewById(R.id.tv_user_name);
        ivImage = (ImageView) toolbar.findViewById(R.id.user_img);


        if (groupimageicon != null) {
            final String url = groupimageicon;

            Glide.with(this).load(url)
                    .placeholder(R.drawable.notification).dontAnimate()
                    .error(R.drawable.notification).dontAnimate()
                    .into(ivImage);
        }

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent i = new Intent(Individual_inner.this, UserProfile.class);
//                i.putExtra("id", person_id);
//                i.putExtra("block", "no");
//                i.putExtra("devicetoken", devicetoken);
//                startActivity(i);
            }
        });

//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        image_send.setOnClickListener(this);
//        image_attach.setOnClickListener(this);

        Intent intent = getIntent();
        person_image_url = intent.getStringExtra("image");
        person_name = intent.getStringExtra("name");
        person_id = intent.getStringExtra("userId");
        devicetoken = intent.getStringExtra("deviceToken");

        Log.e("onCreate: ", "uid" + person_id + "fid" + userId + "token" + devicetoken);

        name.setText("" + person_name);


        normal_value = user_id + "+" + person_id;
        swap_value = person_id + "+" + user_id;

        Glide.with(this).load(person_image_url)
                .placeholder(R.drawable.notification).dontAnimate()
                .error(R.drawable.notification).dontAnimate()
                .into(ivImage);


        if (Build.VERSION.SDK_INT >= 11) {
            mProgressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        } else {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recycler_comments.setLayoutManager(linearLayoutManager);

        method_get_all_chat();

    }

    private void method_get_all_chat() {

        reference_check = FirebaseDatabase.getInstance().getReference().child("one_to_one");
        reference_check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.e("get val  ", "" + normal_value);
                Log.e("has dat  ", "" + dataSnapshot.hasChild(normal_value));

                if (dataSnapshot.hasChild(normal_value)) {
                    reference = FirebaseDatabase.getInstance().getReference().child("one_to_one").child(normal_value);
                    ChatAdapter individualchat_adapter = new ChatAdapter(ChatActivity.this, reference);
                    recycler_comments.setAdapter(individualchat_adapter);
                    individualchat_adapter.notifyDataSetChanged();
                    mProgressDialog.dismiss();
                } else if (dataSnapshot.hasChild(swap_value)) {
                    reference = FirebaseDatabase.getInstance().getReference().child("one_to_one").child(swap_value);
                    ChatAdapter individualchat_adapter = new ChatAdapter(ChatActivity.this, reference);
                    recycler_comments.setAdapter(individualchat_adapter);
                    individualchat_adapter.notifyDataSetChanged();
                    mProgressDialog.dismiss();
                } else {
                    reference = FirebaseDatabase.getInstance().getReference().child("one_to_one").child(normal_value);
                    ChatAdapter individualchat_adapter = new ChatAdapter(ChatActivity.this, reference);
                    recycler_comments.setAdapter(individualchat_adapter);
                    individualchat_adapter.notifyDataSetChanged();
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressDialog.dismiss();
                Log.e("DatabaseError ", "onCancelled: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.image_send:
                comment_String = edtxt_comment.getText().toString().trim();
//                startchat();
                startchat = true;
                lastmsg = edtxt_comment.getText().toString().trim();
                DateFormat df = new SimpleDateFormat("h:mm a");
                String date = df.format(Calendar.getInstance().getTime());
                SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = df1.format(Calendar.getInstance().getTime());
                time_String = date;
                Type = "Text";

                if (comment_String.equals("")) {
                } else {

                    notification_tasksingle("https://fcm.googleapis.com/fcm/send", comment_String, devicetoken);
                    edtxt_comment.setText("");
                    //SessionManager.get_firebaseId(prefs)
                    comment = new Comment(time_String, SessionManager.get_image(prefs), "@" + SessionManager.get_user_id(prefs),
                            formattedDate, comment_String, person_image_url, person_name, SessionManager.get_name(prefs), "", Type);

                    mDatabaseReference.child("one_to_one").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Log.e("value  ", "" + normal_value);
                            Log.e("has child  ", "" + dataSnapshot.hasChild(normal_value));
                            if ((dataSnapshot.hasChild(normal_value))) {
                                String key = mDatabaseReference.child("one_to_one").child(normal_value).push().getKey();
                                Map<String, Object> postValues = comment.toMap();
                                Map<String, Object> childUpdates = new HashMap<>();
                                childUpdates.put(("/one_to_one/" + normal_value + "/") + key, postValues);
                                mDatabaseReference.updateChildren(childUpdates);
                            } else if ((dataSnapshot.hasChild(swap_value))) {
                                String key = mDatabaseReference.child("one_to_one").child(swap_value).push().getKey();
                                Map<String, Object> postValues = comment.toMap();
                                Map<String, Object> childUpdates = new HashMap<>();
                                childUpdates.put(("/one_to_one/" + swap_value + "/") + key, postValues);
                                mDatabaseReference.updateChildren(childUpdates);
                            } else {
                                String key = mDatabaseReference.child("one_to_one").child(normal_value).push().getKey();
                                Map<String, Object> postValues = comment.toMap();
                                Map<String, Object> childUpdates = new HashMap<>();
                                childUpdates.put(("/one_to_one/" + normal_value + "/") + key, postValues);
                                mDatabaseReference.updateChildren(childUpdates);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
//                    method_get_all_chat();

                }
                break;

//            case R.id.image_attach:
//                dialog_attachment = new Dialog(Individual_inner.this);
//                dialog_attachment.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog_attachment.setContentView(R.layout.dialog_attachment);
//                dialog_attachment.setCancelable(true);
//                linear_camera = (LinearLayout) dialog_attachment.findViewById(R.id.linear_camera);
////                linear_document = (LinearLayout) dialog_attachment.findViewById(R.id.linear_document);
////                linear_video = (LinearLayout) dialog_attachment.findViewById(R.id.linear_video);
//                linear_gallery = (LinearLayout) dialog_attachment.findViewById(R.id.linear_gallery);
//                linear_camera.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Type = "Image";
//                        openCamera();
//                    }
//                });
////                linear_video.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        // TODO Auto-generated method stub
////                        type = "video";
////                        openvideo();
////                    }
////                });
////                linear_audio.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        // TODO Auto-generated method stub
////                        type = "audio";
////                        audio_rec();
////                        dialog_attachment.dismiss();
////                    }
////                });
//                linear_gallery.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Type = "Image";
//                        openGallery();
//                    }
//                });
////                linear_document.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        Type = "DOC";
////                        selectDoc();
////                    }
////                });
//                dialog_attachment.show();
//                break;
            default:
                break;
        }
    }

    public void notification_tasksingle(String url, final String chattext, final String token) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("login response  " + response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", Global.fbkey);
                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<String, String>();
                param.put("to", "" + token);
                JSONObject data = new JSONObject();
                try {
                    data.put("message", chattext);
                    data.put("type", "chat");
                    data.put("name", SessionManager.get_name(prefs));
                    data.put("image", SessionManager.get_image(prefs));
                    data.put("userId", SessionManager.get_user_id(prefs));
                    data.put("deviceToken", SessionManager.get_device_token(prefs));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                param.put("data", String.valueOf(data));
                Log.e("Notification getParam: ", param.toString());
                return param;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        queue.add(postRequest);
    }

    public void notification_videocall(String url, final String chattext, final String token) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            calling(person_id);
                            calling(person_id, "VIDEO");
//                            notification_IOS(chattext, token, "videocall");
                            System.out.println("notification_Call response : " + response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", Global.fbkey);
                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<String, String>();
                param.put("to", "" + token);
                JSONObject data = new JSONObject();
                try {
                    data.put("message", chattext);
                    data.put("type", "videocall");
                    data.put("channel", normal_value);
                    data.put("userId", SessionManager.get_name(prefs));
                    data.put("name", SessionManager.get_name(prefs));
                    data.put("image", SessionManager.get_image(prefs));
                    data.put("deviceToken", SessionManager.get_device(prefs));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                param.put("data", String.valueOf(data));

                JSONObject notification = new JSONObject();
                try {
                    notification.put("title", "" + SessionManager.get_name(prefs));
                    notification.put("content_available", "true");
                    notification.put("body", chattext);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                param.put("notification", String.valueOf(notification));
                param.put("priority", "high");

                Log.e("Notification getParam: ", param.toString());
                Log.e("Notification getParam: ", param.toString());
                return param;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        queue.add(postRequest);
    }

    public void notification_AudioCall(String url, final String chattext, final String token) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            calling(person_id, "AUDIO");
//                            notification_IOS(chattext, token, "audiocall");
                            System.out.println("notification_audioCall response : " + response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", Global.fbkey);
                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<String, String>();
                param.put("to", "" + token);
                JSONObject data = new JSONObject();
                try {
                    data.put("message", chattext);
                    data.put("type", "audiocall");
                    data.put("channel", normal_value);
                    data.put("userId", SessionManager.get_name(prefs));
                    data.put("name", SessionManager.get_name(prefs));
                    data.put("image", SessionManager.get_image(prefs));
                    data.put("deviceToken", SessionManager.get_device_token(prefs));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject notification = new JSONObject();
                try {
                    notification.put("title", "" + SessionManager.get_name(prefs));
                    notification.put("content_available", "true");
                    notification.put("body", chattext);
                    notification.put("priority", "high");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                param.put("notification", String.valueOf(notification));
                param.put("data", String.valueOf(data));
                param.put("priority", "high");

                Log.e("Notification getParam: ", param.toString());
                return param;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        queue.add(postRequest);
    }


    void send_data(String attachment_path, String type) {

//        DateFormat df = new SimpleDateFormat("MM/dd/yyyy h:mm a");
//        String date = df.format(Calendar.getInstance().getTime());
//        time_String = date;

        DateFormat df = new SimpleDateFormat("h:mm a");
        String date = df.format(Calendar.getInstance().getTime());
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df1.format(Calendar.getInstance().getTime());
        time_String = date;

        if (Global.isOnline(ChatActivity.this)) {

            comment_String = "";
            //SessionManager.get_firebaseId(prefs)
            comment = new Comment(time_String, SessionManager.get_image(prefs), "@" + SessionManager.get_user_id(prefs),
                    formattedDate, comment_String, person_image_url, person_name, SessionManager.get_name(prefs)
                    , attachment_path, type);


            mDatabaseReference.child("one_to_one").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Log.e("value  ", "" + normal_value);
                    Log.e("has child  ", "" + dataSnapshot.hasChild(normal_value));
                    if ((dataSnapshot.hasChild(normal_value))) {
                        String key = mDatabaseReference.child("one_to_one").child(normal_value).push().getKey();
                        Map<String, Object> postValues = comment.toMap();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put(("/one_to_one/" + normal_value + "/") + key, postValues);
                        mDatabaseReference.updateChildren(childUpdates);
                    } else if ((dataSnapshot.hasChild(swap_value))) {
                        String key = mDatabaseReference.child("one_to_one").child(swap_value).push().getKey();
                        Map<String, Object> postValues = comment.toMap();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put(("/one_to_one/" + swap_value + "/") + key, postValues);
                        mDatabaseReference.updateChildren(childUpdates);
                    } else {
                        String key = mDatabaseReference.child("one_to_one").child(normal_value).push().getKey();
                        Map<String, Object> postValues = comment.toMap();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put(("/one_to_one/" + normal_value + "/") + key, postValues);
                        mDatabaseReference.updateChildren(childUpdates);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        } else {
            Global.showDialog(ChatActivity.this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        SessionManager.save_onlinestatus(prefs, false);
    }

    @Override
    public void onResume() {
        super.onResume();
//        SessionManager.save_onlinestatus(prefs, true);
    }

    @Override
    protected void onStop() {
        saveLastChat(comment_String);
        super.onStop();
//        SessionManager.save_onlinestatus(prefs, false);
    }


    public void calling(final String receiver_id, String type) {
        final JSONObject json = new JSONObject();
        try {
//   action:calling,user_id:113,receiver_id:115
            json.put("action", "calling");
            json.put("user_id", SessionManager.get_user_id(prefs));
            json.put("receiver_id", receiver_id);
            json.put("type", type);
            Log.e("postComment Data :: ", " data = " + json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, Global.URL, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("postComment response", "" + response);
                        try {
                            JSONObject jsonObject = response;
                            if (jsonObject.getString("status").equals("Success")) {
                            } else if (jsonObject.getString("status").equals("Fail")) {
                                Log.e("onResponse", "onResponse: " + jsonObject.getString("msg"));
                            } else {
                                Log.e("onResponse", "onResponse: " + jsonObject.getString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("onResponse", "error : " + error.getLocalizedMessage());
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        queue.add(jsonObjectRequest);
    }

    public void saveLastChat(final String comment) {
        final JSONObject json = new JSONObject();
        try {
//   "action:lastchat,user_id:50,profile_id:128,message:test"

            json.put("action", "updatelastmessage");
            json.put("userId", SessionManager.get_user_id(prefs));
            json.put("profileId", person_id);
            json.put("message", comment);
            Log.e("postComment Data :: ", " data = " + json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, Global.URL, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("postComment response", "" + response);
                        try {
                            JSONObject jsonObject = response;
                            if (jsonObject.getString("status").equals("Success")) {
                            } else if (jsonObject.getString("status").equals("Fail")) {
                                Log.e("onResponse", "onResponse: " + jsonObject.getString("msg"));
                            } else {
                                Log.e("onResponse", "onResponse: " + jsonObject.getString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("onResponse", "error : " + error.getLocalizedMessage());
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        queue.add(jsonObjectRequest);
    }
}
