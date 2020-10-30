package com.squareup.picasso;

import android.app.Notification;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Picasso.Priority;
import com.squareup.picasso.Request.Builder;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestCreator {
    private static final AtomicInteger a = new AtomicInteger();
    private final Picasso b;
    private final Builder c;
    private boolean d;
    private boolean e;
    private boolean f;
    private int g;
    private int h;
    private int i;
    private int j;
    private Drawable k;
    private Drawable l;
    private Object m;

    RequestCreator(Picasso picasso, Uri uri, int i2) {
        this.f = true;
        if (picasso.m) {
            throw new IllegalStateException("Picasso instance already shut down. Cannot submit new requests.");
        }
        this.b = picasso;
        this.c = new Builder(uri, i2, picasso.j);
    }

    RequestCreator() {
        this.f = true;
        this.b = null;
        this.c = new Builder(null, 0, null);
    }

    public RequestCreator noPlaceholder() {
        if (this.g != 0) {
            throw new IllegalStateException("Placeholder resource already set.");
        } else if (this.k != null) {
            throw new IllegalStateException("Placeholder image already set.");
        } else {
            this.f = false;
            return this;
        }
    }

    public RequestCreator placeholder(int i2) {
        if (!this.f) {
            throw new IllegalStateException("Already explicitly declared as no placeholder.");
        } else if (i2 == 0) {
            throw new IllegalArgumentException("Placeholder image resource invalid.");
        } else if (this.k != null) {
            throw new IllegalStateException("Placeholder image already set.");
        } else {
            this.g = i2;
            return this;
        }
    }

    public RequestCreator placeholder(Drawable drawable) {
        if (!this.f) {
            throw new IllegalStateException("Already explicitly declared as no placeholder.");
        } else if (this.g != 0) {
            throw new IllegalStateException("Placeholder image already set.");
        } else {
            this.k = drawable;
            return this;
        }
    }

    public RequestCreator error(int i2) {
        if (i2 == 0) {
            throw new IllegalArgumentException("Error image resource invalid.");
        } else if (this.l != null) {
            throw new IllegalStateException("Error image already set.");
        } else {
            this.h = i2;
            return this;
        }
    }

    public RequestCreator error(Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Error image may not be null.");
        } else if (this.h != 0) {
            throw new IllegalStateException("Error image already set.");
        } else {
            this.l = drawable;
            return this;
        }
    }

    public RequestCreator tag(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Tag invalid.");
        } else if (this.m != null) {
            throw new IllegalStateException("Tag already set.");
        } else {
            this.m = obj;
            return this;
        }
    }

    public RequestCreator fit() {
        this.e = true;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public RequestCreator a() {
        this.e = false;
        return this;
    }

    public RequestCreator resizeDimen(int i2, int i3) {
        Resources resources = this.b.c.getResources();
        return resize(resources.getDimensionPixelSize(i2), resources.getDimensionPixelSize(i3));
    }

    public RequestCreator resize(int i2, int i3) {
        this.c.resize(i2, i3);
        return this;
    }

    public RequestCreator centerCrop() {
        this.c.centerCrop();
        return this;
    }

    public RequestCreator centerInside() {
        this.c.centerInside();
        return this;
    }

    public RequestCreator onlyScaleDown() {
        this.c.onlyScaleDown();
        return this;
    }

    public RequestCreator rotate(float f2) {
        this.c.rotate(f2);
        return this;
    }

    public RequestCreator rotate(float f2, float f3, float f4) {
        this.c.rotate(f2, f3, f4);
        return this;
    }

    public RequestCreator config(Config config) {
        this.c.config(config);
        return this;
    }

    public RequestCreator stableKey(String str) {
        this.c.stableKey(str);
        return this;
    }

    public RequestCreator priority(Priority priority) {
        this.c.priority(priority);
        return this;
    }

    public RequestCreator transform(Transformation transformation) {
        this.c.transform(transformation);
        return this;
    }

    public RequestCreator transform(List<? extends Transformation> list) {
        this.c.transform(list);
        return this;
    }

    @Deprecated
    public RequestCreator skipMemoryCache() {
        return memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
    }

    public RequestCreator memoryPolicy(MemoryPolicy memoryPolicy, MemoryPolicy... memoryPolicyArr) {
        if (memoryPolicy == null) {
            throw new IllegalArgumentException("Memory policy cannot be null.");
        }
        this.i = memoryPolicy.a | this.i;
        if (memoryPolicyArr == null) {
            throw new IllegalArgumentException("Memory policy cannot be null.");
        }
        if (memoryPolicyArr.length > 0) {
            for (MemoryPolicy memoryPolicy2 : memoryPolicyArr) {
                if (memoryPolicy2 == null) {
                    throw new IllegalArgumentException("Memory policy cannot be null.");
                }
                this.i = memoryPolicy2.a | this.i;
            }
        }
        return this;
    }

    public RequestCreator networkPolicy(NetworkPolicy networkPolicy, NetworkPolicy... networkPolicyArr) {
        if (networkPolicy == null) {
            throw new IllegalArgumentException("Network policy cannot be null.");
        }
        this.j = networkPolicy.a | this.j;
        if (networkPolicyArr == null) {
            throw new IllegalArgumentException("Network policy cannot be null.");
        }
        if (networkPolicyArr.length > 0) {
            for (NetworkPolicy networkPolicy2 : networkPolicyArr) {
                if (networkPolicy2 == null) {
                    throw new IllegalArgumentException("Network policy cannot be null.");
                }
                this.j = networkPolicy2.a | this.j;
            }
        }
        return this;
    }

    public RequestCreator noFade() {
        this.d = true;
        return this;
    }

    public Bitmap get() {
        long nanoTime = System.nanoTime();
        Utils.a();
        if (this.e) {
            throw new IllegalStateException("Fit cannot be used with get.");
        } else if (!this.c.a()) {
            return null;
        } else {
            Request a2 = a(nanoTime);
            GetAction getAction = new GetAction(this.b, a2, this.i, this.j, this.m, Utils.a(a2, new StringBuilder()));
            return BitmapHunter.a(this.b, this.b.d, this.b.e, this.b.f, (Action) getAction).a();
        }
    }

    public void fetch() {
        fetch(null);
    }

    public void fetch(Callback callback) {
        long nanoTime = System.nanoTime();
        if (this.e) {
            throw new IllegalStateException("Fit cannot be used with fetch.");
        } else if (this.c.a()) {
            if (!this.c.c()) {
                this.c.priority(Priority.LOW);
            }
            Request a2 = a(nanoTime);
            String a3 = Utils.a(a2, new StringBuilder());
            if (this.b.a(a3) != null) {
                if (this.b.l) {
                    String b2 = a2.b();
                    StringBuilder sb = new StringBuilder();
                    sb.append("from ");
                    sb.append(LoadedFrom.MEMORY);
                    Utils.a("Main", "completed", b2, sb.toString());
                }
                if (callback != null) {
                    callback.onSuccess();
                    return;
                }
                return;
            }
            FetchAction fetchAction = new FetchAction(this.b, a2, this.i, this.j, this.m, a3, callback);
            this.b.b(fetchAction);
        }
    }

    public void into(Target target) {
        long nanoTime = System.nanoTime();
        Utils.b();
        if (target == null) {
            throw new IllegalArgumentException("Target must not be null.");
        } else if (this.e) {
            throw new IllegalStateException("Fit cannot be used with a Target.");
        } else {
            Drawable drawable = null;
            if (!this.c.a()) {
                this.b.cancelRequest(target);
                if (this.f) {
                    drawable = b();
                }
                target.onPrepareLoad(drawable);
                return;
            }
            Request a2 = a(nanoTime);
            String a3 = Utils.a(a2);
            if (MemoryPolicy.a(this.i)) {
                Bitmap a4 = this.b.a(a3);
                if (a4 != null) {
                    this.b.cancelRequest(target);
                    target.onBitmapLoaded(a4, LoadedFrom.MEMORY);
                    return;
                }
            }
            if (this.f) {
                drawable = b();
            }
            target.onPrepareLoad(drawable);
            TargetAction targetAction = new TargetAction(this.b, target, a2, this.i, this.j, this.l, a3, this.m, this.h);
            this.b.a((Action) targetAction);
        }
    }

    public void into(RemoteViews remoteViews, int i2, int i3, Notification notification) {
        long nanoTime = System.nanoTime();
        if (remoteViews == null) {
            throw new IllegalArgumentException("RemoteViews must not be null.");
        } else if (notification == null) {
            throw new IllegalArgumentException("Notification must not be null.");
        } else if (this.e) {
            throw new IllegalStateException("Fit cannot be used with RemoteViews.");
        } else if (this.k == null && this.g == 0 && this.l == null) {
            Request a2 = a(nanoTime);
            RemoteViews remoteViews2 = remoteViews;
            int i4 = i2;
            int i5 = i3;
            Notification notification2 = notification;
            NotificationAction notificationAction = new NotificationAction(this.b, a2, remoteViews2, i4, i5, notification2, this.i, this.j, Utils.a(a2, new StringBuilder()), this.m, this.h);
            a((RemoteViewsAction) notificationAction);
        } else {
            throw new IllegalArgumentException("Cannot use placeholder or error drawables with remote views.");
        }
    }

    public void into(RemoteViews remoteViews, int i2, int[] iArr) {
        long nanoTime = System.nanoTime();
        if (remoteViews == null) {
            throw new IllegalArgumentException("remoteViews must not be null.");
        } else if (iArr == null) {
            throw new IllegalArgumentException("appWidgetIds must not be null.");
        } else if (this.e) {
            throw new IllegalStateException("Fit cannot be used with remote views.");
        } else if (this.k == null && this.g == 0 && this.l == null) {
            Request a2 = a(nanoTime);
            RemoteViews remoteViews2 = remoteViews;
            int i3 = i2;
            int[] iArr2 = iArr;
            AppWidgetAction appWidgetAction = new AppWidgetAction(this.b, a2, remoteViews2, i3, iArr2, this.i, this.j, Utils.a(a2, new StringBuilder()), this.m, this.h);
            a((RemoteViewsAction) appWidgetAction);
        } else {
            throw new IllegalArgumentException("Cannot use placeholder or error drawables with remote views.");
        }
    }

    public void into(ImageView imageView) {
        into(imageView, null);
    }

    public void into(ImageView imageView, Callback callback) {
        ImageView imageView2 = imageView;
        Callback callback2 = callback;
        long nanoTime = System.nanoTime();
        Utils.b();
        if (imageView2 == null) {
            throw new IllegalArgumentException("Target must not be null.");
        } else if (!this.c.a()) {
            this.b.cancelRequest(imageView2);
            if (this.f) {
                PicassoDrawable.a(imageView2, b());
            }
        } else {
            if (this.e) {
                if (this.c.b()) {
                    throw new IllegalStateException("Fit cannot be used with resize.");
                }
                int width = imageView.getWidth();
                int height = imageView.getHeight();
                if (width == 0 || height == 0) {
                    if (this.f) {
                        PicassoDrawable.a(imageView2, b());
                    }
                    this.b.a(imageView2, new DeferredRequestCreator(this, imageView2, callback2));
                    return;
                }
                this.c.resize(width, height);
            }
            Request a2 = a(nanoTime);
            String a3 = Utils.a(a2);
            if (MemoryPolicy.a(this.i)) {
                Bitmap a4 = this.b.a(a3);
                if (a4 != null) {
                    this.b.cancelRequest(imageView2);
                    PicassoDrawable.a(imageView2, this.b.c, a4, LoadedFrom.MEMORY, this.d, this.b.k);
                    if (this.b.l) {
                        String b2 = a2.b();
                        StringBuilder sb = new StringBuilder();
                        sb.append("from ");
                        sb.append(LoadedFrom.MEMORY);
                        Utils.a("Main", "completed", b2, sb.toString());
                    }
                    if (callback2 != null) {
                        callback.onSuccess();
                    }
                    return;
                }
            }
            if (this.f) {
                PicassoDrawable.a(imageView2, b());
            }
            ImageViewAction imageViewAction = new ImageViewAction(this.b, imageView2, a2, this.i, this.j, this.h, this.l, a3, this.m, callback2, this.d);
            this.b.a((Action) imageViewAction);
        }
    }

    private Drawable b() {
        if (this.g != 0) {
            return this.b.c.getResources().getDrawable(this.g);
        }
        return this.k;
    }

    private Request a(long j2) {
        int andIncrement = a.getAndIncrement();
        Request build = this.c.build();
        build.a = andIncrement;
        build.b = j2;
        boolean z = this.b.l;
        if (z) {
            Utils.a("Main", "created", build.b(), build.toString());
        }
        Request a2 = this.b.a(build);
        if (a2 != build) {
            a2.a = andIncrement;
            a2.b = j2;
            if (z) {
                String a3 = a2.a();
                StringBuilder sb = new StringBuilder();
                sb.append("into ");
                sb.append(a2);
                Utils.a("Main", "changed", a3, sb.toString());
            }
        }
        return a2;
    }

    private void a(RemoteViewsAction remoteViewsAction) {
        if (MemoryPolicy.a(this.i)) {
            Bitmap a2 = this.b.a(remoteViewsAction.e());
            if (a2 != null) {
                remoteViewsAction.a(a2, LoadedFrom.MEMORY);
                return;
            }
        }
        if (this.g != 0) {
            remoteViewsAction.a(this.g);
        }
        this.b.a((Action) remoteViewsAction);
    }
}
