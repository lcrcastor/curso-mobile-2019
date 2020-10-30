package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.NetworkInfo;
import com.squareup.picasso.Downloader.ResponseException;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Picasso.Priority;
import com.squareup.picasso.RequestHandler.Result;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

class BitmapHunter implements Runnable {
    private static final Object t = new Object();
    private static final ThreadLocal<StringBuilder> u = new ThreadLocal<StringBuilder>() {
        /* access modifiers changed from: protected */
        /* renamed from: a */
        public StringBuilder initialValue() {
            return new StringBuilder("Picasso-");
        }
    };
    private static final AtomicInteger v = new AtomicInteger();
    private static final RequestHandler w = new RequestHandler() {
        public boolean canHandleRequest(Request request) {
            return true;
        }

        public Result load(Request request, int i) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unrecognized type of request: ");
            sb.append(request);
            throw new IllegalStateException(sb.toString());
        }
    };
    final int a = v.incrementAndGet();
    final Picasso b;
    final Dispatcher c;
    final Cache d;
    final Stats e;
    final String f;
    final Request g;
    final int h;
    int i;
    final RequestHandler j;
    Action k;
    List<Action> l;
    Bitmap m;
    Future<?> n;
    LoadedFrom o;
    Exception p;
    int q;
    int r;
    Priority s;

    private static boolean a(boolean z, int i2, int i3, int i4, int i5) {
        return !z || i2 > i4 || i3 > i5;
    }

    BitmapHunter(Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action, RequestHandler requestHandler) {
        this.b = picasso;
        this.c = dispatcher;
        this.d = cache;
        this.e = stats;
        this.k = action;
        this.f = action.e();
        this.g = action.c();
        this.s = action.k();
        this.h = action.h();
        this.i = action.i();
        this.j = requestHandler;
        this.r = requestHandler.a();
    }

    static Bitmap a(InputStream inputStream, Request request) {
        MarkableInputStream markableInputStream = new MarkableInputStream(inputStream);
        long a2 = markableInputStream.a(65536);
        Options b2 = RequestHandler.b(request);
        boolean a3 = RequestHandler.a(b2);
        boolean c2 = Utils.c((InputStream) markableInputStream);
        markableInputStream.a(a2);
        if (c2) {
            byte[] b3 = Utils.b((InputStream) markableInputStream);
            if (a3) {
                BitmapFactory.decodeByteArray(b3, 0, b3.length, b2);
                RequestHandler.a(request.targetWidth, request.targetHeight, b2, request);
            }
            return BitmapFactory.decodeByteArray(b3, 0, b3.length, b2);
        }
        if (a3) {
            BitmapFactory.decodeStream(markableInputStream, null, b2);
            RequestHandler.a(request.targetWidth, request.targetHeight, b2, request);
            markableInputStream.a(a2);
        }
        Bitmap decodeStream = BitmapFactory.decodeStream(markableInputStream, null, b2);
        if (decodeStream != null) {
            return decodeStream;
        }
        throw new IOException("Failed to decode stream.");
    }

    public void run() {
        try {
            a(this.g);
            if (this.b.l) {
                Utils.a("Hunter", "executing", Utils.a(this));
            }
            this.m = a();
            if (this.m == null) {
                this.c.c(this);
            } else {
                this.c.a(this);
            }
        } catch (ResponseException e2) {
            if (!e2.a || e2.b != 504) {
                this.p = e2;
            }
            this.c.c(this);
        } catch (ContentLengthException e3) {
            this.p = e3;
            this.c.b(this);
        } catch (IOException e4) {
            this.p = e4;
            this.c.b(this);
        } catch (OutOfMemoryError e5) {
            StringWriter stringWriter = new StringWriter();
            this.e.f().dump(new PrintWriter(stringWriter));
            this.p = new RuntimeException(stringWriter.toString(), e5);
            this.c.c(this);
        } catch (Exception e6) {
            this.p = e6;
            this.c.c(this);
        } catch (Throwable th) {
            Thread.currentThread().setName("Picasso-Idle");
            throw th;
        }
        Thread.currentThread().setName("Picasso-Idle");
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: 0000 */
    public Bitmap a() {
        Bitmap bitmap;
        if (MemoryPolicy.a(this.h)) {
            bitmap = this.d.get(this.f);
            if (bitmap != null) {
                this.e.a();
                this.o = LoadedFrom.MEMORY;
                if (this.b.l) {
                    Utils.a("Hunter", "decoded", this.g.a(), "from cache");
                }
                return bitmap;
            }
        } else {
            bitmap = null;
        }
        this.g.c = this.r == 0 ? NetworkPolicy.OFFLINE.a : this.i;
        Result load = this.j.load(this.g, this.i);
        if (load != null) {
            this.o = load.getLoadedFrom();
            this.q = load.a();
            bitmap = load.getBitmap();
            if (bitmap == null) {
                InputStream stream = load.getStream();
                try {
                    Bitmap a2 = a(stream, this.g);
                    Utils.a(stream);
                    bitmap = a2;
                } catch (Throwable th) {
                    Utils.a(stream);
                    throw th;
                }
            }
        }
        if (bitmap != null) {
            if (this.b.l) {
                Utils.a("Hunter", "decoded", this.g.a());
            }
            this.e.a(bitmap);
            if (this.g.d() || this.q != 0) {
                synchronized (t) {
                    if (this.g.e() || this.q != 0) {
                        bitmap = a(this.g, bitmap, this.q);
                        if (this.b.l) {
                            Utils.a("Hunter", "transformed", this.g.a());
                        }
                    }
                    if (this.g.f()) {
                        bitmap = a(this.g.transformations, bitmap);
                        if (this.b.l) {
                            Utils.a("Hunter", "transformed", this.g.a(), "from custom transformations");
                        }
                    }
                }
                if (bitmap != null) {
                    this.e.b(bitmap);
                }
            }
        }
        return bitmap;
    }

    /* access modifiers changed from: 0000 */
    public void a(Action action) {
        boolean z = this.b.l;
        Request request = action.b;
        if (this.k == null) {
            this.k = action;
            if (z) {
                if (this.l == null || this.l.isEmpty()) {
                    Utils.a("Hunter", "joined", request.a(), "to empty hunter");
                } else {
                    Utils.a("Hunter", "joined", request.a(), Utils.a(this, "to "));
                }
            }
            return;
        }
        if (this.l == null) {
            this.l = new ArrayList(3);
        }
        this.l.add(action);
        if (z) {
            Utils.a("Hunter", "joined", request.a(), Utils.a(this, "to "));
        }
        Priority k2 = action.k();
        if (k2.ordinal() > this.s.ordinal()) {
            this.s = k2;
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(Action action) {
        boolean z;
        if (this.k == action) {
            this.k = null;
            z = true;
        } else {
            z = this.l != null ? this.l.remove(action) : false;
        }
        if (z && action.k() == this.s) {
            this.s = o();
        }
        if (this.b.l) {
            Utils.a("Hunter", "removed", action.b.a(), Utils.a(this, "from "));
        }
    }

    private Priority o() {
        Priority priority = Priority.LOW;
        boolean z = true;
        boolean z2 = this.l != null && !this.l.isEmpty();
        if (this.k == null && !z2) {
            z = false;
        }
        if (!z) {
            return priority;
        }
        if (this.k != null) {
            priority = this.k.k();
        }
        if (z2) {
            int size = this.l.size();
            for (int i2 = 0; i2 < size; i2++) {
                Priority k2 = ((Action) this.l.get(i2)).k();
                if (k2.ordinal() > priority.ordinal()) {
                    priority = k2;
                }
            }
        }
        return priority;
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        if (this.k != null) {
            return false;
        }
        if ((this.l == null || this.l.isEmpty()) && this.n != null && this.n.cancel(false)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean c() {
        return this.n != null && this.n.isCancelled();
    }

    /* access modifiers changed from: 0000 */
    public boolean a(boolean z, NetworkInfo networkInfo) {
        if (!(this.r > 0)) {
            return false;
        }
        this.r--;
        return this.j.a(z, networkInfo);
    }

    /* access modifiers changed from: 0000 */
    public boolean d() {
        return this.j.b();
    }

    /* access modifiers changed from: 0000 */
    public Bitmap e() {
        return this.m;
    }

    /* access modifiers changed from: 0000 */
    public String f() {
        return this.f;
    }

    /* access modifiers changed from: 0000 */
    public int g() {
        return this.h;
    }

    /* access modifiers changed from: 0000 */
    public Request h() {
        return this.g;
    }

    /* access modifiers changed from: 0000 */
    public Action i() {
        return this.k;
    }

    /* access modifiers changed from: 0000 */
    public Picasso j() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public List<Action> k() {
        return this.l;
    }

    /* access modifiers changed from: 0000 */
    public Exception l() {
        return this.p;
    }

    /* access modifiers changed from: 0000 */
    public LoadedFrom m() {
        return this.o;
    }

    /* access modifiers changed from: 0000 */
    public Priority n() {
        return this.s;
    }

    static void a(Request request) {
        String c2 = request.c();
        StringBuilder sb = (StringBuilder) u.get();
        sb.ensureCapacity("Picasso-".length() + c2.length());
        sb.replace("Picasso-".length(), sb.length(), c2);
        Thread.currentThread().setName(sb.toString());
    }

    static BitmapHunter a(Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        Request c2 = action.c();
        List a2 = picasso.a();
        int size = a2.size();
        for (int i2 = 0; i2 < size; i2++) {
            RequestHandler requestHandler = (RequestHandler) a2.get(i2);
            if (requestHandler.canHandleRequest(c2)) {
                BitmapHunter bitmapHunter = new BitmapHunter(picasso, dispatcher, cache, stats, action, requestHandler);
                return bitmapHunter;
            }
        }
        BitmapHunter bitmapHunter2 = new BitmapHunter(picasso, dispatcher, cache, stats, action, w);
        return bitmapHunter2;
    }

    static Bitmap a(List<Transformation> list, Bitmap bitmap) {
        int size = list.size();
        int i2 = 0;
        while (i2 < size) {
            final Transformation transformation = (Transformation) list.get(i2);
            try {
                Bitmap transform = transformation.transform(bitmap);
                if (transform == null) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append("Transformation ");
                    sb.append(transformation.key());
                    sb.append(" returned null after ");
                    sb.append(i2);
                    sb.append(" previous transformation(s).\n\nTransformation list:\n");
                    for (Transformation key : list) {
                        sb.append(key.key());
                        sb.append(10);
                    }
                    Picasso.a.post(new Runnable() {
                        public void run() {
                            throw new NullPointerException(sb.toString());
                        }
                    });
                    return null;
                } else if (transform == bitmap && bitmap.isRecycled()) {
                    Picasso.a.post(new Runnable() {
                        public void run() {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Transformation ");
                            sb.append(transformation.key());
                            sb.append(" returned input Bitmap but recycled it.");
                            throw new IllegalStateException(sb.toString());
                        }
                    });
                    return null;
                } else if (transform == bitmap || bitmap.isRecycled()) {
                    i2++;
                    bitmap = transform;
                } else {
                    Picasso.a.post(new Runnable() {
                        public void run() {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Transformation ");
                            sb.append(transformation.key());
                            sb.append(" mutated input Bitmap but failed to recycle the original.");
                            throw new IllegalStateException(sb.toString());
                        }
                    });
                    return null;
                }
            } catch (RuntimeException e2) {
                Picasso.a.post(new Runnable() {
                    public void run() {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Transformation ");
                        sb.append(transformation.key());
                        sb.append(" crashed with exception.");
                        throw new RuntimeException(sb.toString(), e2);
                    }
                });
                return null;
            }
        }
        return bitmap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00c6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static android.graphics.Bitmap a(com.squareup.picasso.Request r13, android.graphics.Bitmap r14, int r15) {
        /*
            int r0 = r14.getWidth()
            int r1 = r14.getHeight()
            boolean r2 = r13.onlyScaleDown
            android.graphics.Matrix r8 = new android.graphics.Matrix
            r8.<init>()
            boolean r3 = r13.e()
            r4 = 0
            if (r3 == 0) goto L_0x00b1
            int r3 = r13.targetWidth
            int r5 = r13.targetHeight
            float r6 = r13.rotationDegrees
            r7 = 0
            int r7 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r7 == 0) goto L_0x0030
            boolean r7 = r13.hasRotationPivot
            if (r7 == 0) goto L_0x002d
            float r7 = r13.rotationPivotX
            float r9 = r13.rotationPivotY
            r8.setRotate(r6, r7, r9)
            goto L_0x0030
        L_0x002d:
            r8.setRotate(r6)
        L_0x0030:
            boolean r6 = r13.centerCrop
            if (r6 == 0) goto L_0x0074
            float r13 = (float) r3
            float r6 = (float) r0
            float r7 = r13 / r6
            float r9 = (float) r5
            float r10 = (float) r1
            float r11 = r9 / r10
            int r12 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r12 <= 0) goto L_0x0054
            float r11 = r11 / r7
            float r10 = r10 * r11
            double r10 = (double) r10
            double r10 = java.lang.Math.ceil(r10)
            int r13 = (int) r10
            int r6 = r1 - r13
            int r6 = r6 / 2
            float r10 = (float) r13
            float r11 = r9 / r10
            r9 = r13
            r13 = r7
            r7 = r0
            goto L_0x0067
        L_0x0054:
            float r7 = r7 / r11
            float r6 = r6 * r7
            double r6 = (double) r6
            double r6 = java.lang.Math.ceil(r6)
            int r6 = (int) r6
            int r7 = r0 - r6
            int r7 = r7 / 2
            float r9 = (float) r6
            float r13 = r13 / r9
            r9 = r1
            r4 = r7
            r7 = r6
            r6 = 0
        L_0x0067:
            boolean r0 = a(r2, r0, r1, r3, r5)
            if (r0 == 0) goto L_0x0070
            r8.preScale(r13, r11)
        L_0x0070:
            r5 = r6
            r6 = r7
            r7 = r9
            goto L_0x00b4
        L_0x0074:
            boolean r13 = r13.centerInside
            if (r13 == 0) goto L_0x008e
            float r13 = (float) r3
            float r6 = (float) r0
            float r13 = r13 / r6
            float r6 = (float) r5
            float r7 = (float) r1
            float r6 = r6 / r7
            int r7 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
            if (r7 >= 0) goto L_0x0083
            goto L_0x0084
        L_0x0083:
            r13 = r6
        L_0x0084:
            boolean r2 = a(r2, r0, r1, r3, r5)
            if (r2 == 0) goto L_0x00b1
            r8.preScale(r13, r13)
            goto L_0x00b1
        L_0x008e:
            if (r3 != 0) goto L_0x0092
            if (r5 == 0) goto L_0x00b1
        L_0x0092:
            if (r3 != r0) goto L_0x0096
            if (r5 == r1) goto L_0x00b1
        L_0x0096:
            if (r3 == 0) goto L_0x009c
            float r13 = (float) r3
            float r6 = (float) r0
        L_0x009a:
            float r13 = r13 / r6
            goto L_0x009f
        L_0x009c:
            float r13 = (float) r5
            float r6 = (float) r1
            goto L_0x009a
        L_0x009f:
            if (r5 == 0) goto L_0x00a5
            float r6 = (float) r5
            float r7 = (float) r1
        L_0x00a3:
            float r6 = r6 / r7
            goto L_0x00a8
        L_0x00a5:
            float r6 = (float) r3
            float r7 = (float) r0
            goto L_0x00a3
        L_0x00a8:
            boolean r2 = a(r2, r0, r1, r3, r5)
            if (r2 == 0) goto L_0x00b1
            r8.preScale(r13, r6)
        L_0x00b1:
            r6 = r0
            r7 = r1
            r5 = 0
        L_0x00b4:
            if (r15 == 0) goto L_0x00ba
            float r13 = (float) r15
            r8.preRotate(r13)
        L_0x00ba:
            r9 = 1
            r3 = r14
            android.graphics.Bitmap r13 = android.graphics.Bitmap.createBitmap(r3, r4, r5, r6, r7, r8, r9)
            if (r13 == r14) goto L_0x00c6
            r14.recycle()
            goto L_0x00c7
        L_0x00c6:
            r13 = r14
        L_0x00c7:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.picasso.BitmapHunter.a(com.squareup.picasso.Request, android.graphics.Bitmap, int):android.graphics.Bitmap");
    }
}
