package com.facebook;

import com.facebook.internal.NativeProtocol;

public enum SessionDefaultAudience {
    NONE(null),
    ONLY_ME(NativeProtocol.AUDIENCE_ME),
    FRIENDS(NativeProtocol.AUDIENCE_FRIENDS),
    EVERYONE(NativeProtocol.AUDIENCE_EVERYONE);
    
    private final String a;

    private SessionDefaultAudience(String str) {
        this.a = str;
    }

    public String getNativeProtocolAudience() {
        return this.a;
    }
}
