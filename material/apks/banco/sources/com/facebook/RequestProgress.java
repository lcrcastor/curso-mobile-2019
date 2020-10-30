package com.facebook;

import android.os.Handler;
import com.facebook.Request.Callback;
import com.facebook.Request.OnProgressCallback;

class RequestProgress {
    private final Request a;
    private final Handler b;
    private final long c = Settings.getOnProgressThreshold();
    private long d;
    private long e;
    private long f;

    RequestProgress(Handler handler, Request request) {
        this.a = request;
        this.b = handler;
    }

    /* access modifiers changed from: 0000 */
    public void a(long j) {
        this.d += j;
        if (this.d >= this.e + this.c || this.d >= this.f) {
            a();
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(long j) {
        this.f += j;
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (this.d > this.e) {
            Callback callback = this.a.getCallback();
            if (this.f > 0 && (callback instanceof OnProgressCallback)) {
                final long j = this.d;
                final long j2 = this.f;
                final OnProgressCallback onProgressCallback = (OnProgressCallback) callback;
                if (this.b == null) {
                    onProgressCallback.onProgress(j, j2);
                } else {
                    Handler handler = this.b;
                    AnonymousClass1 r2 = new Runnable() {
                        public void run() {
                            onProgressCallback.onProgress(j, j2);
                        }
                    };
                    handler.post(r2);
                }
                this.e = this.d;
            }
        }
    }
}
