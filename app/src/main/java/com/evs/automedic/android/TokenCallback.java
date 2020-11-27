package com.evs.automedic.android;


import com.evs.automedic.android.model.Token;

public abstract class TokenCallback {
    public abstract void onError(Exception error);
    public abstract void onSuccess(Token token);
}
