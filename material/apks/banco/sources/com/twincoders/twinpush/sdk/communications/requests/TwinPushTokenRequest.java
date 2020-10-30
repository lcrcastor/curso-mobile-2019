package com.twincoders.twinpush.sdk.communications.requests;

import com.twincoders.twinpush.sdk.TwinPushSDK;
import com.twincoders.twinpush.sdk.communications.asyhttp.AsyncHttpClient;

public abstract class TwinPushTokenRequest extends TwinPushRequest {
    public TwinPushTokenRequest(String str) {
        super(str);
    }

    public void onSetupClient(AsyncHttpClient asyncHttpClient) {
        super.onSetupClient(asyncHttpClient);
        asyncHttpClient.addHeader("X-TwinPush-REST-API-Token", getApiToken());
    }

    /* access modifiers changed from: protected */
    public String getApiToken() {
        return TwinPushSDK.getInstance(getRequestLauncher().getContext()).getApiKey();
    }
}
