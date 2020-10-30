package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.SystemClock;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public final class zzrt extends Drawable implements Callback {
    private int a;
    private long b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private boolean h;
    private boolean i;
    private zzb j;
    private Drawable k;
    private Drawable l;
    private boolean m;
    private boolean n;
    private boolean o;
    private int p;

    static final class zza extends Drawable {
        /* access modifiers changed from: private */
        public static final zza a = new zza();
        private static final C0026zza b = new C0026zza();

        /* renamed from: com.google.android.gms.internal.zzrt$zza$zza reason: collision with other inner class name */
        static final class C0026zza extends ConstantState {
            private C0026zza() {
            }

            public int getChangingConfigurations() {
                return 0;
            }

            public Drawable newDrawable() {
                return zza.a;
            }
        }

        private zza() {
        }

        public void draw(Canvas canvas) {
        }

        public ConstantState getConstantState() {
            return b;
        }

        public int getOpacity() {
            return -2;
        }

        public void setAlpha(int i) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }
    }

    static final class zzb extends ConstantState {
        int a;
        int b;

        zzb(zzb zzb) {
            if (zzb != null) {
                this.a = zzb.a;
                this.b = zzb.b;
            }
        }

        public int getChangingConfigurations() {
            return this.a;
        }

        public Drawable newDrawable() {
            return new zzrt(this);
        }
    }

    public zzrt(Drawable drawable, Drawable drawable2) {
        this(null);
        if (drawable == null) {
            drawable = zza.a;
        }
        this.k = drawable;
        drawable.setCallback(this);
        zzb zzb2 = this.j;
        zzb2.b = drawable.getChangingConfigurations() | zzb2.b;
        if (drawable2 == null) {
            drawable2 = zza.a;
        }
        this.l = drawable2;
        drawable2.setCallback(this);
        zzb zzb3 = this.j;
        zzb3.b = drawable2.getChangingConfigurations() | zzb3.b;
    }

    zzrt(zzb zzb2) {
        this.a = 0;
        this.e = 255;
        this.g = 0;
        this.h = true;
        this.j = new zzb(zzb2);
    }

    public boolean canConstantState() {
        if (!this.m) {
            this.n = (this.k.getConstantState() == null || this.l.getConstantState() == null) ? false : true;
            this.m = true;
        }
        return this.n;
    }

    public void draw(Canvas canvas) {
        boolean z = true;
        switch (this.a) {
            case 1:
                this.b = SystemClock.uptimeMillis();
                this.a = 2;
                z = false;
                break;
            case 2:
                if (this.b >= 0) {
                    float uptimeMillis = ((float) (SystemClock.uptimeMillis() - this.b)) / ((float) this.f);
                    if (uptimeMillis < 1.0f) {
                        z = false;
                    }
                    if (z) {
                        this.a = 0;
                    }
                    this.g = (int) ((((float) (this.d - 0)) * Math.min(uptimeMillis, 1.0f)) + BitmapDescriptorFactory.HUE_RED);
                    break;
                }
                break;
        }
        int i2 = this.g;
        boolean z2 = this.h;
        Drawable drawable = this.k;
        Drawable drawable2 = this.l;
        if (z) {
            if (!z2 || i2 == 0) {
                drawable.draw(canvas);
            }
            if (i2 == this.e) {
                drawable2.setAlpha(this.e);
                drawable2.draw(canvas);
            }
            return;
        }
        if (z2) {
            drawable.setAlpha(this.e - i2);
        }
        drawable.draw(canvas);
        if (z2) {
            drawable.setAlpha(this.e);
        }
        if (i2 > 0) {
            drawable2.setAlpha(i2);
            drawable2.draw(canvas);
            drawable2.setAlpha(this.e);
        }
        invalidateSelf();
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.j.a | this.j.b;
    }

    public ConstantState getConstantState() {
        if (!canConstantState()) {
            return null;
        }
        this.j.a = getChangingConfigurations();
        return this.j;
    }

    public int getIntrinsicHeight() {
        return Math.max(this.k.getIntrinsicHeight(), this.l.getIntrinsicHeight());
    }

    public int getIntrinsicWidth() {
        return Math.max(this.k.getIntrinsicWidth(), this.l.getIntrinsicWidth());
    }

    public int getOpacity() {
        if (!this.o) {
            this.p = Drawable.resolveOpacity(this.k.getOpacity(), this.l.getOpacity());
            this.o = true;
        }
        return this.p;
    }

    @TargetApi(11)
    public void invalidateDrawable(Drawable drawable) {
        if (zzs.zzaxk()) {
            Callback callback = getCallback();
            if (callback != null) {
                callback.invalidateDrawable(this);
            }
        }
    }

    public Drawable mutate() {
        if (!this.i && super.mutate() == this) {
            if (!canConstantState()) {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
            this.k.mutate();
            this.l.mutate();
            this.i = true;
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        this.k.setBounds(rect);
        this.l.setBounds(rect);
    }

    @TargetApi(11)
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j2) {
        if (zzs.zzaxk()) {
            Callback callback = getCallback();
            if (callback != null) {
                callback.scheduleDrawable(this, runnable, j2);
            }
        }
    }

    public void setAlpha(int i2) {
        if (this.g == this.e) {
            this.g = i2;
        }
        this.e = i2;
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.k.setColorFilter(colorFilter);
        this.l.setColorFilter(colorFilter);
    }

    public void startTransition(int i2) {
        this.c = 0;
        this.d = this.e;
        this.g = 0;
        this.f = i2;
        this.a = 1;
        invalidateSelf();
    }

    @TargetApi(11)
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        if (zzs.zzaxk()) {
            Callback callback = getCallback();
            if (callback != null) {
                callback.unscheduleDrawable(this, runnable);
            }
        }
    }

    public Drawable zzatn() {
        return this.l;
    }
}
