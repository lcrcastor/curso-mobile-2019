package com.squareup.picasso;

import android.graphics.Bitmap.Config;
import android.net.Uri;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.squareup.picasso.Picasso.Priority;
import cz.msebera.android.httpclient.message.TokenParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class Request {
    private static final long d = TimeUnit.SECONDS.toNanos(5);
    int a;
    long b;
    int c;
    public final boolean centerCrop;
    public final boolean centerInside;
    public final Config config;
    public final boolean hasRotationPivot;
    public final boolean onlyScaleDown;
    public final Priority priority;
    public final int resourceId;
    public final float rotationDegrees;
    public final float rotationPivotX;
    public final float rotationPivotY;
    public final String stableKey;
    public final int targetHeight;
    public final int targetWidth;
    public final List<Transformation> transformations;
    public final Uri uri;

    public static final class Builder {
        private Uri a;
        private int b;
        private String c;
        private int d;
        private int e;
        private boolean f;
        private boolean g;
        private boolean h;
        private float i;
        private float j;
        private float k;
        private boolean l;
        private List<Transformation> m;
        private Config n;
        private Priority o;

        public Builder(Uri uri) {
            setUri(uri);
        }

        public Builder(int i2) {
            setResourceId(i2);
        }

        Builder(Uri uri, int i2, Config config) {
            this.a = uri;
            this.b = i2;
            this.n = config;
        }

        private Builder(Request request) {
            this.a = request.uri;
            this.b = request.resourceId;
            this.c = request.stableKey;
            this.d = request.targetWidth;
            this.e = request.targetHeight;
            this.f = request.centerCrop;
            this.g = request.centerInside;
            this.i = request.rotationDegrees;
            this.j = request.rotationPivotX;
            this.k = request.rotationPivotY;
            this.l = request.hasRotationPivot;
            this.h = request.onlyScaleDown;
            if (request.transformations != null) {
                this.m = new ArrayList(request.transformations);
            }
            this.n = request.config;
            this.o = request.priority;
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return (this.a == null && this.b == 0) ? false : true;
        }

        /* access modifiers changed from: 0000 */
        public boolean b() {
            return (this.d == 0 && this.e == 0) ? false : true;
        }

        /* access modifiers changed from: 0000 */
        public boolean c() {
            return this.o != null;
        }

        public Builder setUri(Uri uri) {
            if (uri == null) {
                throw new IllegalArgumentException("Image URI may not be null.");
            }
            this.a = uri;
            this.b = 0;
            return this;
        }

        public Builder setResourceId(int i2) {
            if (i2 == 0) {
                throw new IllegalArgumentException("Image resource ID may not be 0.");
            }
            this.b = i2;
            this.a = null;
            return this;
        }

        public Builder stableKey(String str) {
            this.c = str;
            return this;
        }

        public Builder resize(int i2, int i3) {
            if (i2 < 0) {
                throw new IllegalArgumentException("Width must be positive number or 0.");
            } else if (i3 < 0) {
                throw new IllegalArgumentException("Height must be positive number or 0.");
            } else if (i3 == 0 && i2 == 0) {
                throw new IllegalArgumentException("At least one dimension has to be positive number.");
            } else {
                this.d = i2;
                this.e = i3;
                return this;
            }
        }

        public Builder clearResize() {
            this.d = 0;
            this.e = 0;
            this.f = false;
            this.g = false;
            return this;
        }

        public Builder centerCrop() {
            if (this.g) {
                throw new IllegalStateException("Center crop can not be used after calling centerInside");
            }
            this.f = true;
            return this;
        }

        public Builder clearCenterCrop() {
            this.f = false;
            return this;
        }

        public Builder centerInside() {
            if (this.f) {
                throw new IllegalStateException("Center inside can not be used after calling centerCrop");
            }
            this.g = true;
            return this;
        }

        public Builder clearCenterInside() {
            this.g = false;
            return this;
        }

        public Builder onlyScaleDown() {
            if (this.e == 0 && this.d == 0) {
                throw new IllegalStateException("onlyScaleDown can not be applied without resize");
            }
            this.h = true;
            return this;
        }

        public Builder clearOnlyScaleDown() {
            this.h = false;
            return this;
        }

        public Builder rotate(float f2) {
            this.i = f2;
            return this;
        }

        public Builder rotate(float f2, float f3, float f4) {
            this.i = f2;
            this.j = f3;
            this.k = f4;
            this.l = true;
            return this;
        }

        public Builder clearRotation() {
            this.i = BitmapDescriptorFactory.HUE_RED;
            this.j = BitmapDescriptorFactory.HUE_RED;
            this.k = BitmapDescriptorFactory.HUE_RED;
            this.l = false;
            return this;
        }

        public Builder config(Config config) {
            this.n = config;
            return this;
        }

        public Builder priority(Priority priority) {
            if (priority == null) {
                throw new IllegalArgumentException("Priority invalid.");
            } else if (this.o != null) {
                throw new IllegalStateException("Priority already set.");
            } else {
                this.o = priority;
                return this;
            }
        }

        public Builder transform(Transformation transformation) {
            if (transformation == null) {
                throw new IllegalArgumentException("Transformation must not be null.");
            } else if (transformation.key() == null) {
                throw new IllegalArgumentException("Transformation key must not be null.");
            } else {
                if (this.m == null) {
                    this.m = new ArrayList(2);
                }
                this.m.add(transformation);
                return this;
            }
        }

        public Builder transform(List<? extends Transformation> list) {
            if (list == null) {
                throw new IllegalArgumentException("Transformation list must not be null.");
            }
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                transform((Transformation) list.get(i2));
            }
            return this;
        }

        public Request build() {
            if (this.g && this.f) {
                throw new IllegalStateException("Center crop and center inside can not be used together.");
            } else if (this.f && this.d == 0 && this.e == 0) {
                throw new IllegalStateException("Center crop requires calling resize with positive width and height.");
            } else if (this.g && this.d == 0 && this.e == 0) {
                throw new IllegalStateException("Center inside requires calling resize with positive width and height.");
            } else {
                if (this.o == null) {
                    this.o = Priority.NORMAL;
                }
                Uri uri = this.a;
                int i2 = this.b;
                String str = this.c;
                List<Transformation> list = this.m;
                int i3 = this.d;
                int i4 = this.e;
                boolean z = this.f;
                boolean z2 = this.g;
                boolean z3 = this.h;
                float f2 = this.i;
                float f3 = this.j;
                float f4 = this.k;
                boolean z4 = this.l;
                boolean z5 = z4;
                boolean z6 = z5;
                Request request = new Request(uri, i2, str, list, i3, i4, z, z2, z3, f2, f3, f4, z6, this.n, this.o);
                return request;
            }
        }
    }

    private Request(Uri uri2, int i, String str, List<Transformation> list, int i2, int i3, boolean z, boolean z2, boolean z3, float f, float f2, float f3, boolean z4, Config config2, Priority priority2) {
        this.uri = uri2;
        this.resourceId = i;
        this.stableKey = str;
        if (list == null) {
            this.transformations = null;
        } else {
            this.transformations = Collections.unmodifiableList(list);
        }
        this.targetWidth = i2;
        this.targetHeight = i3;
        this.centerCrop = z;
        this.centerInside = z2;
        this.onlyScaleDown = z3;
        this.rotationDegrees = f;
        this.rotationPivotX = f2;
        this.rotationPivotY = f3;
        this.hasRotationPivot = z4;
        this.config = config2;
        this.priority = priority2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Request{");
        if (this.resourceId > 0) {
            sb.append(this.resourceId);
        } else {
            sb.append(this.uri);
        }
        if (this.transformations != null && !this.transformations.isEmpty()) {
            for (Transformation transformation : this.transformations) {
                sb.append(TokenParser.SP);
                sb.append(transformation.key());
            }
        }
        if (this.stableKey != null) {
            sb.append(" stableKey(");
            sb.append(this.stableKey);
            sb.append(')');
        }
        if (this.targetWidth > 0) {
            sb.append(" resize(");
            sb.append(this.targetWidth);
            sb.append(',');
            sb.append(this.targetHeight);
            sb.append(')');
        }
        if (this.centerCrop) {
            sb.append(" centerCrop");
        }
        if (this.centerInside) {
            sb.append(" centerInside");
        }
        if (this.rotationDegrees != BitmapDescriptorFactory.HUE_RED) {
            sb.append(" rotation(");
            sb.append(this.rotationDegrees);
            if (this.hasRotationPivot) {
                sb.append(" @ ");
                sb.append(this.rotationPivotX);
                sb.append(',');
                sb.append(this.rotationPivotY);
            }
            sb.append(')');
        }
        if (this.config != null) {
            sb.append(TokenParser.SP);
            sb.append(this.config);
        }
        sb.append('}');
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public String a() {
        long nanoTime = System.nanoTime() - this.b;
        if (nanoTime > d) {
            StringBuilder sb = new StringBuilder();
            sb.append(b());
            sb.append('+');
            sb.append(TimeUnit.NANOSECONDS.toSeconds(nanoTime));
            sb.append('s');
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(b());
        sb2.append('+');
        sb2.append(TimeUnit.NANOSECONDS.toMillis(nanoTime));
        sb2.append("ms");
        return sb2.toString();
    }

    /* access modifiers changed from: 0000 */
    public String b() {
        StringBuilder sb = new StringBuilder();
        sb.append("[R");
        sb.append(this.a);
        sb.append(']');
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public String c() {
        if (this.uri != null) {
            return String.valueOf(this.uri.getPath());
        }
        return Integer.toHexString(this.resourceId);
    }

    public boolean hasSize() {
        return (this.targetWidth == 0 && this.targetHeight == 0) ? false : true;
    }

    /* access modifiers changed from: 0000 */
    public boolean d() {
        return e() || f();
    }

    /* access modifiers changed from: 0000 */
    public boolean e() {
        return hasSize() || this.rotationDegrees != BitmapDescriptorFactory.HUE_RED;
    }

    /* access modifiers changed from: 0000 */
    public boolean f() {
        return this.transformations != null;
    }

    public Builder buildUpon() {
        return new Builder();
    }
}
