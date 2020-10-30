package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.widget.ImageView;

@RestrictTo({Scope.LIBRARY_GROUP})
public class AppCompatImageHelper {
    private final ImageView a;
    private TintInfo b;
    private TintInfo c;
    private TintInfo d;

    public AppCompatImageHelper(ImageView imageView) {
        this.a = imageView;
    }

    public void loadFromAttributes(AttributeSet attributeSet, int i) {
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.a.getContext(), attributeSet, R.styleable.AppCompatImageView, i, 0);
        try {
            Drawable drawable = this.a.getDrawable();
            if (drawable == null) {
                int resourceId = obtainStyledAttributes.getResourceId(R.styleable.AppCompatImageView_srcCompat, -1);
                if (resourceId != -1) {
                    drawable = AppCompatResources.getDrawable(this.a.getContext(), resourceId);
                    if (drawable != null) {
                        this.a.setImageDrawable(drawable);
                    }
                }
            }
            if (drawable != null) {
                DrawableUtils.a(drawable);
            }
            if (obtainStyledAttributes.hasValue(R.styleable.AppCompatImageView_tint)) {
                ImageViewCompat.setImageTintList(this.a, obtainStyledAttributes.getColorStateList(R.styleable.AppCompatImageView_tint));
            }
            if (obtainStyledAttributes.hasValue(R.styleable.AppCompatImageView_tintMode)) {
                ImageViewCompat.setImageTintMode(this.a, DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(R.styleable.AppCompatImageView_tintMode, -1), null));
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public void setImageResource(int i) {
        if (i != 0) {
            Drawable drawable = AppCompatResources.getDrawable(this.a.getContext(), i);
            if (drawable != null) {
                DrawableUtils.a(drawable);
            }
            this.a.setImageDrawable(drawable);
        } else {
            this.a.setImageDrawable(null);
        }
        d();
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return VERSION.SDK_INT < 21 || !(this.a.getBackground() instanceof RippleDrawable);
    }

    /* access modifiers changed from: 0000 */
    public void a(ColorStateList colorStateList) {
        if (this.c == null) {
            this.c = new TintInfo();
        }
        this.c.a = colorStateList;
        this.c.d = true;
        d();
    }

    /* access modifiers changed from: 0000 */
    public ColorStateList b() {
        if (this.c != null) {
            return this.c.a;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void a(Mode mode) {
        if (this.c == null) {
            this.c = new TintInfo();
        }
        this.c.b = mode;
        this.c.c = true;
        d();
    }

    /* access modifiers changed from: 0000 */
    public Mode c() {
        if (this.c != null) {
            return this.c.b;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void d() {
        Drawable drawable = this.a.getDrawable();
        if (drawable != null) {
            DrawableUtils.a(drawable);
        }
        if (drawable != null && (!e() || !a(drawable))) {
            if (this.c != null) {
                AppCompatDrawableManager.a(drawable, this.c, this.a.getDrawableState());
            } else if (this.b != null) {
                AppCompatDrawableManager.a(drawable, this.b, this.a.getDrawableState());
            }
        }
    }

    private boolean e() {
        int i = VERSION.SDK_INT;
        boolean z = false;
        if (i <= 21) {
            return i == 21;
        }
        if (this.b != null) {
            z = true;
        }
        return z;
    }

    private boolean a(@NonNull Drawable drawable) {
        if (this.d == null) {
            this.d = new TintInfo();
        }
        TintInfo tintInfo = this.d;
        tintInfo.a();
        ColorStateList imageTintList = ImageViewCompat.getImageTintList(this.a);
        if (imageTintList != null) {
            tintInfo.d = true;
            tintInfo.a = imageTintList;
        }
        Mode imageTintMode = ImageViewCompat.getImageTintMode(this.a);
        if (imageTintMode != null) {
            tintInfo.c = true;
            tintInfo.b = imageTintMode;
        }
        if (!tintInfo.d && !tintInfo.c) {
            return false;
        }
        AppCompatDrawableManager.a(drawable, tintInfo, this.a.getDrawableState());
        return true;
    }
}
