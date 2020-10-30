package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.View;

class AppCompatBackgroundHelper {
    private final View a;
    private final AppCompatDrawableManager b;
    private int c = -1;
    private TintInfo d;
    private TintInfo e;
    private TintInfo f;

    AppCompatBackgroundHelper(View view) {
        this.a = view;
        this.b = AppCompatDrawableManager.get();
    }

    /* access modifiers changed from: 0000 */
    public void a(AttributeSet attributeSet, int i) {
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.a.getContext(), attributeSet, R.styleable.ViewBackgroundHelper, i, 0);
        try {
            if (obtainStyledAttributes.hasValue(R.styleable.ViewBackgroundHelper_android_background)) {
                this.c = obtainStyledAttributes.getResourceId(R.styleable.ViewBackgroundHelper_android_background, -1);
                ColorStateList a2 = this.b.a(this.a.getContext(), this.c);
                if (a2 != null) {
                    b(a2);
                }
            }
            if (obtainStyledAttributes.hasValue(R.styleable.ViewBackgroundHelper_backgroundTint)) {
                ViewCompat.setBackgroundTintList(this.a, obtainStyledAttributes.getColorStateList(R.styleable.ViewBackgroundHelper_backgroundTint));
            }
            if (obtainStyledAttributes.hasValue(R.styleable.ViewBackgroundHelper_backgroundTintMode)) {
                ViewCompat.setBackgroundTintMode(this.a, DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(R.styleable.ViewBackgroundHelper_backgroundTintMode, -1), null));
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i) {
        this.c = i;
        b(this.b != null ? this.b.a(this.a.getContext(), i) : null);
        c();
    }

    /* access modifiers changed from: 0000 */
    public void a(Drawable drawable) {
        this.c = -1;
        b((ColorStateList) null);
        c();
    }

    /* access modifiers changed from: 0000 */
    public void a(ColorStateList colorStateList) {
        if (this.e == null) {
            this.e = new TintInfo();
        }
        this.e.a = colorStateList;
        this.e.d = true;
        c();
    }

    /* access modifiers changed from: 0000 */
    public ColorStateList a() {
        if (this.e != null) {
            return this.e.a;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void a(Mode mode) {
        if (this.e == null) {
            this.e = new TintInfo();
        }
        this.e.b = mode;
        this.e.c = true;
        c();
    }

    /* access modifiers changed from: 0000 */
    public Mode b() {
        if (this.e != null) {
            return this.e.b;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        Drawable background = this.a.getBackground();
        if (background != null && (!d() || !b(background))) {
            if (this.e != null) {
                AppCompatDrawableManager.a(background, this.e, this.a.getDrawableState());
            } else if (this.d != null) {
                AppCompatDrawableManager.a(background, this.d, this.a.getDrawableState());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.d == null) {
                this.d = new TintInfo();
            }
            this.d.a = colorStateList;
            this.d.d = true;
        } else {
            this.d = null;
        }
        c();
    }

    private boolean d() {
        int i = VERSION.SDK_INT;
        boolean z = false;
        if (i <= 21) {
            return i == 21;
        }
        if (this.d != null) {
            z = true;
        }
        return z;
    }

    private boolean b(@NonNull Drawable drawable) {
        if (this.f == null) {
            this.f = new TintInfo();
        }
        TintInfo tintInfo = this.f;
        tintInfo.a();
        ColorStateList backgroundTintList = ViewCompat.getBackgroundTintList(this.a);
        if (backgroundTintList != null) {
            tintInfo.d = true;
            tintInfo.a = backgroundTintList;
        }
        Mode backgroundTintMode = ViewCompat.getBackgroundTintMode(this.a);
        if (backgroundTintMode != null) {
            tintInfo.c = true;
            tintInfo.b = backgroundTintMode;
        }
        if (!tintInfo.d && !tintInfo.c) {
            return false;
        }
        AppCompatDrawableManager.a(drawable, tintInfo, this.a.getDrawableState());
        return true;
    }
}
