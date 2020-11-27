package com.evs.automedic.chatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.evs.automedic.R;
import com.evs.automedic.utils.SessionManager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.CommentViewHolder> {
    String TAG = "TAG";
    SharedPreferences prefs;
    private Context mContext;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    Comment comment;
    private List<String> mCommentIds = new ArrayList<>();
    private List<Comment> mComments = new ArrayList<>();

    public ChatAdapter(Activity context, DatabaseReference reference) {
        mContext = context;
        mDatabaseReference = reference;
        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                try {
                    comment = dataSnapshot.getValue(Comment.class);
                } catch (DatabaseException e) {

                }
                mCommentIds.add(dataSnapshot.getKey());
                mComments.add(comment);
                notifyItemInserted(mComments.size() - 1);
                ChatActivity.recycler_comments.scrollToPosition(mComments.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
                Comment newComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();
                // [START_EXCLUDE]
                int commentIndex = mCommentIds.indexOf(commentKey);
                if (commentIndex > -1) {
                    // Replace with the new data
                    mComments.set(commentIndex, newComment);

                    // Update the RecyclerView
                    notifyItemChanged(commentIndex);

                } else {
                    Log.w(TAG, "onChildChanged:unknown_child:" + commentKey);
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                String commentKey = dataSnapshot.getKey();
// [START_EXCLUDE]
                int commentIndex = mCommentIds.indexOf(commentKey);
                if (commentIndex > -1) {
                    // Remove data from the list
                    mCommentIds.remove(commentIndex);
                    mComments.remove(commentIndex);

                    // Update the RecyclerView
                    notifyItemRemoved(commentIndex);
                } else {
                    Log.w(TAG, "onChildRemoved:unknown_child:" + commentKey);
                }

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                Comment movedComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(mContext, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mDatabaseReference.addChildEventListener(childEventListener);

        mChildEventListener = childEventListener;
    }

    @Override
    public int getItemViewType(int position) {
        Comment comment = mComments.get(position);
        int viewType;
        if (comment.chatSenderId.equals("@" + SessionManager.get_user_id(prefs))) {
            if (!comment.type.equalsIgnoreCase("text")) {
                viewType = 11;
            } else {
                viewType = 10;//text
            }
        } else {
            if (!comment.type.equalsIgnoreCase("text")) {
                viewType = 21;
            } else {
                viewType = 20;//text
            }
        }
        return viewType;
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = null;
        if (viewType == 10) {
            view = inflater.inflate(R.layout.chat_adapter, parent, false);
        } else if (viewType == 20) {
            view = inflater.inflate(R.layout.chat_adapter2, parent, false);
        } else if (viewType == 11) {
            view = inflater.inflate(R.layout.chat_adapter_smiley, parent, false);
        } else if (viewType == 21) {
            view = inflater.inflate(R.layout.chat_adapter_smiley2, parent, false);
        }
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        final Comment comment = mComments.get(position);
        try {
            try {
                final String url = comment.chat_sender_img;
                Glide.with(mContext).load(url)
                        .placeholder(R.drawable.notification).dontAnimate()
                        .error(R.drawable.notification).dontAnimate()
                        .into(holder.ivImage);

            } catch (Exception e) {
                e.printStackTrace();
            }
//

            holder.text_chatname.setText("");
            holder.text_chat_time.setText(comment.chat_time);
            holder.text_chatdetail.setText(comment.chat_message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView text_chatname, text_chat_time, text_chatdetail;
        ImageView chat_attachment, chat_doc;
        ImageView ivImage;


        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            text_chatname = (TextView) itemView.findViewById(R.id.text_chatname);
            text_chat_time = (TextView) itemView.findViewById(R.id.text_chat_time);
            text_chatdetail = (TextView) itemView.findViewById(R.id.text_chatdetail);
            chat_attachment = (ImageView) itemView.findViewById(R.id.chat_attachment);
            chat_doc = (ImageView) itemView.findViewById(R.id.chat_doc);
            ivImage = (ImageView) itemView.findViewById(R.id.imageView6);
        }
    }

    public class AppWebViewClients extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
        }
    }
}
