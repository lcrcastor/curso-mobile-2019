package com.facebook.internal;

import android.content.Context;
import android.os.Bundle;

final class LikeStatusClient extends PlatformServiceClient {
    private String a;

    LikeStatusClient(Context context, String str, String str2) {
        super(context, NativeProtocol.MESSAGE_GET_LIKE_STATUS_REQUEST, 65543, NativeProtocol.PROTOCOL_VERSION_20141001, str);
        this.a = str2;
    }

    /* access modifiers changed from: protected */
    public void populateRequestBundle(Bundle bundle) {
        bundle.putString(NativeProtocol.EXTRA_OBJECT_ID, this.a);
    }
}
