package com.facebook;

public enum AccessTokenSource {
    NONE(false),
    FACEBOOK_APPLICATION_WEB(true),
    FACEBOOK_APPLICATION_NATIVE(true),
    FACEBOOK_APPLICATION_SERVICE(true),
    WEB_VIEW(false),
    TEST_USER(true),
    CLIENT_TOKEN(true);
    
    private final boolean a;

    private AccessTokenSource(boolean z) {
        this.a = z;
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return this.a;
    }
}
