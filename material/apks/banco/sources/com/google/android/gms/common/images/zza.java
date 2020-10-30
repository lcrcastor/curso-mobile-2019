package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.widget.ImageView;
import com.google.android.gms.common.images.ImageManager.OnImageLoadedListener;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.internal.zzrt;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrv;
import java.lang.ref.WeakReference;

public abstract class zza {
    protected int Au = 0;
    protected int Av = 0;
    protected boolean Aw = false;
    final C0003zza a;
    private boolean b = true;
    private boolean c = false;
    private boolean d = true;

    /* renamed from: com.google.android.gms.common.images.zza$zza reason: collision with other inner class name */
    static final class C0003zza {
        public final Uri a;

        public C0003zza(Uri uri) {
            this.a = uri;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C0003zza)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            return zzab.equal(((C0003zza) obj).a, this.a);
        }

        public int hashCode() {
            return zzab.hashCode(this.a);
        }
    }

    public static final class zzb extends zza {
        private WeakReference<ImageView> b;

        public zzb(ImageView imageView, int i) {
            super(null, i);
            com.google.android.gms.common.internal.zzc.zzu(imageView);
            this.b = new WeakReference<>(imageView);
        }

        public zzb(ImageView imageView, Uri uri) {
            super(uri, 0);
            com.google.android.gms.common.internal.zzc.zzu(imageView);
            this.b = new WeakReference<>(imageView);
        }

        private void a(ImageView imageView, Drawable drawable, boolean z, boolean z2, boolean z3) {
            int i = 0;
            boolean z4 = !z2 && !z3;
            if (z4 && (imageView instanceof zzru)) {
                int zzatp = ((zzru) imageView).zzatp();
                if (this.Av != 0 && zzatp == this.Av) {
                    return;
                }
            }
            boolean zzc = zzc(z, z2);
            if (zzc) {
                drawable = zza(imageView.getDrawable(), drawable);
            }
            imageView.setImageDrawable(drawable);
            if (imageView instanceof zzru) {
                zzru zzru = (zzru) imageView;
                zzru.zzq(z3 ? this.a.a : null);
                if (z4) {
                    i = this.Av;
                }
                zzru.zzgj(i);
            }
            if (zzc) {
                ((zzrt) drawable).startTransition(Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
            }
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof zzb)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            ImageView imageView = (ImageView) this.b.get();
            ImageView imageView2 = (ImageView) ((zzb) obj).b.get();
            return (imageView2 == null || imageView == null || !zzab.equal(imageView2, imageView)) ? false : true;
        }

        public int hashCode() {
            return 0;
        }

        /* access modifiers changed from: protected */
        public void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
            ImageView imageView = (ImageView) this.b.get();
            if (imageView != null) {
                a(imageView, drawable, z, z2, z3);
            }
        }
    }

    public static final class zzc extends zza {
        private WeakReference<OnImageLoadedListener> b;

        public zzc(OnImageLoadedListener onImageLoadedListener, Uri uri) {
            super(uri, 0);
            com.google.android.gms.common.internal.zzc.zzu(onImageLoadedListener);
            this.b = new WeakReference<>(onImageLoadedListener);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof zzc)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zzc zzc = (zzc) obj;
            OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.b.get();
            OnImageLoadedListener onImageLoadedListener2 = (OnImageLoadedListener) zzc.b.get();
            return onImageLoadedListener2 != null && onImageLoadedListener != null && zzab.equal(onImageLoadedListener2, onImageLoadedListener) && zzab.equal(zzc.a, this.a);
        }

        public int hashCode() {
            return zzab.hashCode(this.a);
        }

        /* access modifiers changed from: protected */
        public void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
            if (!z2) {
                OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.b.get();
                if (onImageLoadedListener != null) {
                    onImageLoadedListener.onImageLoaded(this.a.a, drawable, z3);
                }
            }
        }
    }

    public zza(Uri uri, int i) {
        this.a = new C0003zza(uri);
        this.Av = i;
    }

    private Drawable a(Context context, zzrv zzrv, int i) {
        return context.getResources().getDrawable(i);
    }

    /* access modifiers changed from: 0000 */
    public void a(Context context, Bitmap bitmap, boolean z) {
        com.google.android.gms.common.internal.zzc.zzu(bitmap);
        zza(new BitmapDrawable(context.getResources(), bitmap), z, false, true);
    }

    /* access modifiers changed from: 0000 */
    public void a(Context context, zzrv zzrv) {
        if (this.d) {
            zza(null, false, true, false);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Context context, zzrv zzrv, boolean z) {
        zza(this.Av != 0 ? a(context, zzrv, this.Av) : null, z, false, false);
    }

    /* access modifiers changed from: protected */
    public zzrt zza(Drawable drawable, Drawable drawable2) {
        if (drawable == null) {
            drawable = null;
        } else if (drawable instanceof zzrt) {
            drawable = ((zzrt) drawable).zzatn();
        }
        return new zzrt(drawable, drawable2);
    }

    /* access modifiers changed from: protected */
    public abstract void zza(Drawable drawable, boolean z, boolean z2, boolean z3);

    /* access modifiers changed from: protected */
    public boolean zzc(boolean z, boolean z2) {
        return this.b && !z2 && !z;
    }

    public void zzgh(int i) {
        this.Av = i;
    }
}
