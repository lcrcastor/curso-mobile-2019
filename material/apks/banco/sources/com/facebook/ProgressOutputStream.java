package com.facebook;

import android.os.Handler;
import com.facebook.RequestBatch.Callback;
import com.facebook.RequestBatch.OnProgressCallback;
import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.util.Map;

class ProgressOutputStream extends FilterOutputStream implements RequestOutputStream {
    private final Map<Request, RequestProgress> a;
    /* access modifiers changed from: private */
    public final RequestBatch b;
    private final long c = Settings.getOnProgressThreshold();
    /* access modifiers changed from: private */
    public long d;
    private long e;
    /* access modifiers changed from: private */
    public long f;
    private RequestProgress g;

    ProgressOutputStream(OutputStream outputStream, RequestBatch requestBatch, Map<Request, RequestProgress> map, long j) {
        super(outputStream);
        this.b = requestBatch;
        this.a = map;
        this.f = j;
    }

    private void a(long j) {
        if (this.g != null) {
            this.g.a(j);
        }
        this.d += j;
        if (this.d >= this.e + this.c || this.d >= this.f) {
            a();
        }
    }

    private void a() {
        if (this.d > this.e) {
            for (Callback callback : this.b.d()) {
                if (callback instanceof OnProgressCallback) {
                    Handler b2 = this.b.b();
                    final OnProgressCallback onProgressCallback = (OnProgressCallback) callback;
                    if (b2 == null) {
                        onProgressCallback.onBatchProgress(this.b, this.d, this.f);
                    } else {
                        b2.post(new Runnable() {
                            public void run() {
                                onProgressCallback.onBatchProgress(ProgressOutputStream.this.b, ProgressOutputStream.this.d, ProgressOutputStream.this.f);
                            }
                        });
                    }
                }
            }
            this.e = this.d;
        }
    }

    public void a(Request request) {
        this.g = request != null ? (RequestProgress) this.a.get(request) : null;
    }

    public void write(byte[] bArr) {
        this.out.write(bArr);
        a((long) bArr.length);
    }

    public void write(byte[] bArr, int i, int i2) {
        this.out.write(bArr, i, i2);
        a((long) i2);
    }

    public void write(int i) {
        this.out.write(i);
        a(1);
    }

    public void close() {
        super.close();
        for (RequestProgress a2 : this.a.values()) {
            a2.a();
        }
        a();
    }
}
