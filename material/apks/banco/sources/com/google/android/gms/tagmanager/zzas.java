package com.google.android.gms.tagmanager;

import android.text.TextUtils;

class zzas {
    private final long a;
    private final long b;
    private final long c;
    private String d;

    zzas(long j, long j2, long j3) {
        this.a = j;
        this.b = j2;
        this.c = j3;
    }

    /* access modifiers changed from: 0000 */
    public long a() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public void a(String str) {
        if (str != null && !TextUtils.isEmpty(str.trim())) {
            this.d = str;
        }
    }

    /* access modifiers changed from: 0000 */
    public long b() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public String c() {
        return this.d;
    }
}
