package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

class WrappedDrawableApi14 extends Drawable implements Callback, TintAwareDrawable, WrappedDrawable {
    static final Mode a = Mode.SRC_IN;
    DrawableWrapperState b;
    Drawable c;
    private int d;
    private Mode e;
    private boolean f;
    private boolean g;

    public static abstract class DrawableWrapperState extends ConstantState {
        int a;
        ConstantState b;
        ColorStateList c = null;
        Mode d = WrappedDrawableApi14.a;

        @NonNull
        public abstract Drawable newDrawable(@Nullable Resources resources);

        DrawableWrapperState(@Nullable DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
            if (drawableWrapperState != null) {
                this.a = drawableWrapperState.a;
                this.b = drawableWrapperState.b;
                this.c = drawableWrapperState.c;
                this.d = drawableWrapperState.d;
            }
        }

        @NonNull
        public Drawable newDrawable() {
            return newDrawable(null);
        }

        public int getChangingConfigurations() {
            return this.a | (this.b != null ? this.b.getChangingConfigurations() : 0);
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return this.b != null;
        }
    }

    static class DrawableWrapperStateBase extends DrawableWrapperState {
        DrawableWrapperStateBase(@Nullable DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
            super(drawableWrapperState, resources);
        }

        @NonNull
        public Drawable newDrawable(@Nullable Resources resources) {
            return new WrappedDrawableApi14(this, resources);
        }
    }

    /* access modifiers changed from: protected */
    public boolean b() {
        return true;
    }

    WrappedDrawableApi14(@NonNull DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
        this.b = drawableWrapperState;
        a(resources);
    }

    WrappedDrawableApi14(@Nullable Drawable drawable) {
        this.b = a();
        setWrappedDrawable(drawable);
    }

    private void a(@Nullable Resources resources) {
        if (this.b != null && this.b.b != null) {
            setWrappedDrawable(this.b.b.newDrawable(resources));
        }
    }

    public void jumpToCurrentState() {
        this.c.jumpToCurrentState();
    }

    public void draw(@NonNull Canvas canvas) {
        this.c.draw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        if (this.c != null) {
            this.c.setBounds(rect);
        }
    }

    public void setChangingConfigurations(int i) {
        this.c.setChangingConfigurations(i);
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | (this.b != null ? this.b.getChangingConfigurations() : 0) | this.c.getChangingConfigurations();
    }

    public void setDither(boolean z) {
        this.c.setDither(z);
    }

    public void setFilterBitmap(boolean z) {
        this.c.setFilterBitmap(z);
    }

    public void setAlpha(int i) {
        this.c.setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.c.setColorFilter(colorFilter);
    }

    public boolean isStateful() {
        ColorStateList colorStateList = (!b() || this.b == null) ? null : this.b.c;
        return (colorStateList != null && colorStateList.isStateful()) || this.c.isStateful();
    }

    public boolean setState(@NonNull int[] iArr) {
        return a(iArr) || this.c.setState(iArr);
    }

    @NonNull
    public int[] getState() {
        return this.c.getState();
    }

    @NonNull
    public Drawable getCurrent() {
        return this.c.getCurrent();
    }

    public boolean setVisible(boolean z, boolean z2) {
        return super.setVisible(z, z2) || this.c.setVisible(z, z2);
    }

    public int getOpacity() {
        return this.c.getOpacity();
    }

    public Region getTransparentRegion() {
        return this.c.getTransparentRegion();
    }

    public int getIntrinsicWidth() {
        return this.c.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() {
        return this.c.getIntrinsicHeight();
    }

    public int getMinimumWidth() {
        return this.c.getMinimumWidth();
    }

    public int getMinimumHeight() {
        return this.c.getMinimumHeight();
    }

    public boolean getPadding(@NonNull Rect rect) {
        return this.c.getPadding(rect);
    }

    @Nullable
    public ConstantState getConstantState() {
        if (this.b == null || !this.b.a()) {
            return null;
        }
        this.b.a = getChangingConfigurations();
        return this.b;
    }

    @NonNull
    public Drawable mutate() {
        if (!this.g && super.mutate() == this) {
            this.b = a();
            if (this.c != null) {
                this.c.mutate();
            }
            if (this.b != null) {
                this.b.b = this.c != null ? this.c.getConstantState() : null;
            }
            this.g = true;
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public DrawableWrapperState a() {
        return new DrawableWrapperStateBase(this.b, null);
    }

    public void invalidateDrawable(@NonNull Drawable drawable) {
        invalidateSelf();
    }

    public void scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    public void unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
        unscheduleSelf(runnable);
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int i) {
        return this.c.setLevel(i);
    }

    public void setTint(int i) {
        setTintList(ColorStateList.valueOf(i));
    }

    public void setTintList(ColorStateList colorStateList) {
        this.b.c = colorStateList;
        a(getState());
    }

    public void setTintMode(@NonNull Mode mode) {
        this.b.d = mode;
        a(getState());
    }

    private boolean a(int[] iArr) {
        if (!b()) {
            return false;
        }
        ColorStateList colorStateList = this.b.c;
        Mode mode = this.b.d;
        if (colorStateList == null || mode == null) {
            this.f = false;
            clearColorFilter();
        } else {
            int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
            if (!(this.f && colorForState == this.d && mode == this.e)) {
                setColorFilter(colorForState, mode);
                this.d = colorForState;
                this.e = mode;
                this.f = true;
                return true;
            }
        }
        return false;
    }

    public final Drawable getWrappedDrawable() {
        return this.c;
    }

    public final void setWrappedDrawable(Drawable drawable) {
        if (this.c != null) {
            this.c.setCallback(null);
        }
        this.c = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            setVisible(drawable.isVisible(), true);
            setState(drawable.getState());
            setLevel(drawable.getLevel());
            setBounds(drawable.getBounds());
            if (this.b != null) {
                this.b.b = drawable.getConstantState();
            }
        }
        invalidateSelf();
    }
}
