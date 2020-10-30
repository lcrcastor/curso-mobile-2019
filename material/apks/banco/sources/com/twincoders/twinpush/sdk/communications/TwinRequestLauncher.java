package com.twincoders.twinpush.sdk.communications;

import android.content.Context;

public interface TwinRequestLauncher {
    void cancelRequest(TwinRequest twinRequest);

    Context getContext();

    void launchRequest(TwinRequest twinRequest);

    void setTimeOutSeconds(int i);
}
