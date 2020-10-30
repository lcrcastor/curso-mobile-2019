package com.facebook;

import android.os.Handler;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

class ProgressNoopOutputStream extends OutputStream implements RequestOutputStream {
    private final Map<Request, RequestProgress> a = new HashMap();
    private final Handler b;
    private Request c;
    private RequestProgress d;
    private int e;

    ProgressNoopOutputStream(Handler handler) {
        this.b = handler;
    }

    public void a(Request request) {
        this.c = request;
        this.d = request != null ? (RequestProgress) this.a.get(request) : null;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public Map<Request, RequestProgress> b() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public void a(long j) {
        if (this.d == null) {
            this.d = new RequestProgress(this.b, this.c);
            this.a.put(this.c, this.d);
        }
        this.d.b(j);
        this.e = (int) (((long) this.e) + j);
    }

    public void write(byte[] bArr) {
        a((long) bArr.length);
    }

    public void write(byte[] bArr, int i, int i2) {
        a((long) i2);
    }

    public void write(int i) {
        a(1);
    }
}
