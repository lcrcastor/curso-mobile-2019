package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Picasso.Priority;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

abstract class Action<T> {
    final Picasso a;
    final Request b;
    final WeakReference<T> c;
    final boolean d;
    final int e;
    final int f;
    final int g;
    final Drawable h;
    final String i;
    final Object j;
    boolean k;
    boolean l;

    static class RequestWeakReference<M> extends WeakReference<M> {
        final Action a;

        public RequestWeakReference(Action action, M m, ReferenceQueue<? super M> referenceQueue) {
            super(m, referenceQueue);
            this.a = action;
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void a();

    /* access modifiers changed from: 0000 */
    public abstract void a(Bitmap bitmap, LoadedFrom loadedFrom);

    Action(Picasso picasso, T t, Request request, int i2, int i3, int i4, Drawable drawable, String str, Object obj, boolean z) {
        WeakReference<T> weakReference;
        this.a = picasso;
        this.b = request;
        if (t == null) {
            weakReference = null;
        } else {
            weakReference = new RequestWeakReference<>(this, t, picasso.i);
        }
        this.c = weakReference;
        this.e = i2;
        this.f = i3;
        this.d = z;
        this.g = i4;
        this.h = drawable;
        this.i = str;
        if (obj == 0) {
            obj = this;
        }
        this.j = obj;
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        this.l = true;
    }

    /* access modifiers changed from: 0000 */
    public Request c() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public T d() {
        if (this.c == null) {
            return null;
        }
        return this.c.get();
    }

    /* access modifiers changed from: 0000 */
    public String e() {
        return this.i;
    }

    /* access modifiers changed from: 0000 */
    public boolean f() {
        return this.l;
    }

    /* access modifiers changed from: 0000 */
    public boolean g() {
        return this.k;
    }

    /* access modifiers changed from: 0000 */
    public int h() {
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public int i() {
        return this.f;
    }

    /* access modifiers changed from: 0000 */
    public Picasso j() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public Priority k() {
        return this.b.priority;
    }

    /* access modifiers changed from: 0000 */
    public Object l() {
        return this.j;
    }
}
