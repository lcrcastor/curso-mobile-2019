package com.facebook;

public enum SessionLoginBehavior {
    SSO_WITH_FALLBACK(true, true),
    SSO_ONLY(true, false),
    SUPPRESS_SSO(false, true);
    
    private final boolean a;
    private final boolean b;

    private SessionLoginBehavior(boolean z, boolean z2) {
        this.a = z;
        this.b = z2;
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return this.b;
    }
}
