package com.squareup.picasso;

import android.graphics.Bitmap;
import com.squareup.picasso.Picasso.LoadedFrom;

class FetchAction extends Action<Object> {
    private final Object m = new Object();
    private Callback n;

    FetchAction(Picasso picasso, Request request, int i, int i2, Object obj, String str, Callback callback) {
        super(picasso, null, request, i, i2, 0, null, str, obj, false);
        this.n = callback;
    }

    /* access modifiers changed from: 0000 */
    public void a(Bitmap bitmap, LoadedFrom loadedFrom) {
        if (this.n != null) {
            this.n.onSuccess();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (this.n != null) {
            this.n.onError();
        }
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        super.b();
        this.n = null;
    }

    /* access modifiers changed from: 0000 */
    public Object d() {
        return this.m;
    }
}
