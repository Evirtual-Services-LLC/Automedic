package com.evs.automedic.chatActivity;

import java.util.HashMap;
import java.util.Map;

public class Comment {
    public String chat_date;
    public String chat_message;
    public String chat_receiver;
    public String chat_receiver_img;
    public String chat_sender;
    public String chatSenderId;
    public String chat_sender_img;
    public String chat_time;
    public String attachment_path;
    public String type;
    public Comment(String chattime, String chatsenderimage, String chatsenderfirebaseid, String chatdate, String chatmessage, String chatreceiverimage, String chatreceiver, String chatsender, String attachment_path, String type) {
        this.chat_time = chattime;
        this.chat_sender_img = chatsenderimage;
        this.chatSenderId = chatsenderfirebaseid;
        this.chat_date = chatdate;
        this.chat_message = chatmessage;
        this.chat_receiver_img = chatreceiverimage;
        this.chat_receiver = chatreceiver;
        this.chat_sender = chatsender;
        this.attachment_path = attachment_path;
        this.type = type;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("chat_time", chat_time);
        result.put("chat_sender_img", chat_sender_img);
        result.put("chatSenderId", chatSenderId);
        result.put("chat_date", chat_date);
        result.put("chat_message", chat_message);
        result.put("chat_receiver_img", chat_receiver_img);
        result.put("chat_receiver", chat_receiver);
        result.put("chat_sender", chat_sender);
        result.put("attachment_path", attachment_path);
        result.put("type", type);

        return result;
    }
}
