package com.appsflyer.share;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

final class c {
    private String a;

    c() {
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str) {
        this.a = str;
    }

    /* access modifiers changed from: 0000 */
    public final void a(Context context) {
        if (this.a != null) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.a)).setFlags(268435456));
        }
    }
}
