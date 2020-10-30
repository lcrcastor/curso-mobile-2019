package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.R;
import android.support.design.widget.CoordinatorLayout.DefaultBehavior;
import android.support.design.widget.CoordinatorLayout.LayoutParams;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewGroupUtils;
import android.support.v7.widget.AppCompatImageHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@DefaultBehavior(Behavior.class)
public class FloatingActionButton extends VisibilityAwareImageButton {
    public static final int NO_CUSTOM_SIZE = 0;
    public static final int SIZE_AUTO = -1;
    public static final int SIZE_MINI = 1;
    public static final int SIZE_NORMAL = 0;
    int a;
    boolean b;
    final Rect c;
    private ColorStateList d;
    private Mode e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private final Rect k;
    private AppCompatImageHelper l;
    private FloatingActionButtonImpl m;

    public static class Behavior extends android.support.design.widget.CoordinatorLayout.Behavior<FloatingActionButton> {
        private Rect a;
        private OnVisibilityChangedListener b;
        private boolean c;

        public Behavior() {
            this.c = true;
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionButton_Behavior_Layout);
            this.c = obtainStyledAttributes.getBoolean(R.styleable.FloatingActionButton_Behavior_Layout_behavior_autoHide, true);
            obtainStyledAttributes.recycle();
        }

        public void setAutoHideEnabled(boolean z) {
            this.c = z;
        }

        public boolean isAutoHideEnabled() {
            return this.c;
        }

        public void onAttachedToLayoutParams(@NonNull LayoutParams layoutParams) {
            if (layoutParams.dodgeInsetEdges == 0) {
                layoutParams.dodgeInsetEdges = 80;
            }
        }

        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
            if (view instanceof AppBarLayout) {
                a(coordinatorLayout, (AppBarLayout) view, floatingActionButton);
            } else if (a(view)) {
                b(view, floatingActionButton);
            }
            return false;
        }

        private static boolean a(@NonNull View view) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof LayoutParams) {
                return ((LayoutParams) layoutParams).getBehavior() instanceof BottomSheetBehavior;
            }
            return false;
        }

        private boolean a(View view, FloatingActionButton floatingActionButton) {
            LayoutParams layoutParams = (LayoutParams) floatingActionButton.getLayoutParams();
            if (this.c && layoutParams.getAnchorId() == view.getId() && floatingActionButton.getUserSetVisibility() == 0) {
                return true;
            }
            return false;
        }

        private boolean a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, FloatingActionButton floatingActionButton) {
            if (!a((View) appBarLayout, floatingActionButton)) {
                return false;
            }
            if (this.a == null) {
                this.a = new Rect();
            }
            Rect rect = this.a;
            ViewGroupUtils.getDescendantRect(coordinatorLayout, appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                floatingActionButton.b(this.b, false);
            } else {
                floatingActionButton.a(this.b, false);
            }
            return true;
        }

        private boolean b(View view, FloatingActionButton floatingActionButton) {
            if (!a(view, floatingActionButton)) {
                return false;
            }
            if (view.getTop() < (floatingActionButton.getHeight() / 2) + ((LayoutParams) floatingActionButton.getLayoutParams()).topMargin) {
                floatingActionButton.b(this.b, false);
            } else {
                floatingActionButton.a(this.b, false);
            }
            return true;
        }

        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, int i) {
            List dependencies = coordinatorLayout.getDependencies(floatingActionButton);
            int size = dependencies.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view = (View) dependencies.get(i2);
                if (!(view instanceof AppBarLayout)) {
                    if (a(view) && b(view, floatingActionButton)) {
                        break;
                    }
                } else if (a(coordinatorLayout, (AppBarLayout) view, floatingActionButton)) {
                    break;
                }
            }
            coordinatorLayout.onLayoutChild(floatingActionButton, i);
            a(coordinatorLayout, floatingActionButton);
            return true;
        }

        public boolean getInsetDodgeRect(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton floatingActionButton, @NonNull Rect rect) {
            Rect rect2 = floatingActionButton.c;
            rect.set(floatingActionButton.getLeft() + rect2.left, floatingActionButton.getTop() + rect2.top, floatingActionButton.getRight() - rect2.right, floatingActionButton.getBottom() - rect2.bottom);
            return true;
        }

        private void a(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton) {
            Rect rect = floatingActionButton.c;
            if (rect != null && rect.centerX() > 0 && rect.centerY() > 0) {
                LayoutParams layoutParams = (LayoutParams) floatingActionButton.getLayoutParams();
                int i = 0;
                int i2 = floatingActionButton.getRight() >= coordinatorLayout.getWidth() - layoutParams.rightMargin ? rect.right : floatingActionButton.getLeft() <= layoutParams.leftMargin ? -rect.left : 0;
                if (floatingActionButton.getBottom() >= coordinatorLayout.getHeight() - layoutParams.bottomMargin) {
                    i = rect.bottom;
                } else if (floatingActionButton.getTop() <= layoutParams.topMargin) {
                    i = -rect.top;
                }
                if (i != 0) {
                    ViewCompat.offsetTopAndBottom(floatingActionButton, i);
                }
                if (i2 != 0) {
                    ViewCompat.offsetLeftAndRight(floatingActionButton, i2);
                }
            }
        }
    }

    public static abstract class OnVisibilityChangedListener {
        public void onHidden(FloatingActionButton floatingActionButton) {
        }

        public void onShown(FloatingActionButton floatingActionButton) {
        }
    }

    class ShadowDelegateImpl implements ShadowViewDelegate {
        ShadowDelegateImpl() {
        }

        public float a() {
            return ((float) FloatingActionButton.this.getSizeDimension()) / 2.0f;
        }

        public void a(int i, int i2, int i3, int i4) {
            FloatingActionButton.this.c.set(i, i2, i3, i4);
            FloatingActionButton.this.setPadding(i + FloatingActionButton.this.a, i2 + FloatingActionButton.this.a, i3 + FloatingActionButton.this.a, i4 + FloatingActionButton.this.a);
        }

        public void a(Drawable drawable) {
            FloatingActionButton.super.setBackgroundDrawable(drawable);
        }

        public boolean b() {
            return FloatingActionButton.this.b;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Size {
    }

    public /* bridge */ /* synthetic */ void setVisibility(int i2) {
        super.setVisibility(i2);
    }

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.c = new Rect();
        this.k = new Rect();
        ThemeUtils.a(context);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionButton, i2, R.style.Widget_Design_FloatingActionButton);
        this.d = obtainStyledAttributes.getColorStateList(R.styleable.FloatingActionButton_backgroundTint);
        this.e = ViewUtils.a(obtainStyledAttributes.getInt(R.styleable.FloatingActionButton_backgroundTintMode, -1), null);
        this.g = obtainStyledAttributes.getColor(R.styleable.FloatingActionButton_rippleColor, 0);
        this.h = obtainStyledAttributes.getInt(R.styleable.FloatingActionButton_fabSize, -1);
        this.i = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FloatingActionButton_fabCustomSize, 0);
        this.f = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FloatingActionButton_borderWidth, 0);
        float dimension = obtainStyledAttributes.getDimension(R.styleable.FloatingActionButton_elevation, BitmapDescriptorFactory.HUE_RED);
        float dimension2 = obtainStyledAttributes.getDimension(R.styleable.FloatingActionButton_pressedTranslationZ, BitmapDescriptorFactory.HUE_RED);
        this.b = obtainStyledAttributes.getBoolean(R.styleable.FloatingActionButton_useCompatPadding, false);
        obtainStyledAttributes.recycle();
        this.l = new AppCompatImageHelper(this);
        this.l.loadFromAttributes(attributeSet, i2);
        this.j = (int) getResources().getDimension(R.dimen.design_fab_image_size);
        getImpl().a(this.d, this.e, this.g, this.f);
        getImpl().a(dimension);
        getImpl().b(dimension2);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int sizeDimension = getSizeDimension();
        this.a = (sizeDimension - this.j) / 2;
        getImpl().e();
        int min = Math.min(a(sizeDimension, i2), a(sizeDimension, i3));
        setMeasuredDimension(this.c.left + min + this.c.right, min + this.c.top + this.c.bottom);
    }

    @ColorInt
    public int getRippleColor() {
        return this.g;
    }

    public void setRippleColor(@ColorInt int i2) {
        if (this.g != i2) {
            this.g = i2;
            getImpl().a(i2);
        }
    }

    @Nullable
    public ColorStateList getBackgroundTintList() {
        return this.d;
    }

    public void setBackgroundTintList(@Nullable ColorStateList colorStateList) {
        if (this.d != colorStateList) {
            this.d = colorStateList;
            getImpl().a(colorStateList);
        }
    }

    @Nullable
    public Mode getBackgroundTintMode() {
        return this.e;
    }

    public void setBackgroundTintMode(@Nullable Mode mode) {
        if (this.e != mode) {
            this.e = mode;
            getImpl().a(mode);
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    public void setBackgroundResource(int i2) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    public void setBackgroundColor(int i2) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    public void setImageResource(@DrawableRes int i2) {
        this.l.setImageResource(i2);
    }

    public void show() {
        show(null);
    }

    public void show(@Nullable OnVisibilityChangedListener onVisibilityChangedListener) {
        a(onVisibilityChangedListener, true);
    }

    /* access modifiers changed from: 0000 */
    public void a(OnVisibilityChangedListener onVisibilityChangedListener, boolean z) {
        getImpl().b(a(onVisibilityChangedListener), z);
    }

    public void hide() {
        hide(null);
    }

    public void hide(@Nullable OnVisibilityChangedListener onVisibilityChangedListener) {
        b(onVisibilityChangedListener, true);
    }

    /* access modifiers changed from: 0000 */
    public void b(@Nullable OnVisibilityChangedListener onVisibilityChangedListener, boolean z) {
        getImpl().a(a(onVisibilityChangedListener), z);
    }

    public void setUseCompatPadding(boolean z) {
        if (this.b != z) {
            this.b = z;
            getImpl().d();
        }
    }

    public boolean getUseCompatPadding() {
        return this.b;
    }

    public void setSize(int i2) {
        if (i2 != this.h) {
            this.h = i2;
            requestLayout();
        }
    }

    public int getSize() {
        return this.h;
    }

    @Nullable
    private InternalVisibilityChangedListener a(@Nullable final OnVisibilityChangedListener onVisibilityChangedListener) {
        if (onVisibilityChangedListener == null) {
            return null;
        }
        return new InternalVisibilityChangedListener() {
            public void a() {
                onVisibilityChangedListener.onShown(FloatingActionButton.this);
            }

            public void b() {
                onVisibilityChangedListener.onHidden(FloatingActionButton.this);
            }
        };
    }

    public void setCustomSize(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Custom size should be non-negative.");
        }
        this.i = i2;
    }

    public int getCustomSize() {
        return this.i;
    }

    /* access modifiers changed from: 0000 */
    public int getSizeDimension() {
        return a(this.h);
    }

    private int a(int i2) {
        int i3;
        Resources resources = getResources();
        if (this.i != 0) {
            return this.i;
        }
        if (i2 == -1) {
            if (Math.max(resources.getConfiguration().screenWidthDp, resources.getConfiguration().screenHeightDp) < 470) {
                i3 = a(1);
            } else {
                i3 = a(0);
            }
            return i3;
        } else if (i2 != 1) {
            return resources.getDimensionPixelSize(R.dimen.design_fab_size_normal);
        } else {
            return resources.getDimensionPixelSize(R.dimen.design_fab_size_mini);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getImpl().f();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getImpl().g();
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        getImpl().a(getDrawableState());
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        getImpl().b();
    }

    public boolean getContentRect(@NonNull Rect rect) {
        if (!ViewCompat.isLaidOut(this)) {
            return false;
        }
        rect.set(0, 0, getWidth(), getHeight());
        rect.left += this.c.left;
        rect.top += this.c.top;
        rect.right -= this.c.right;
        rect.bottom -= this.c.bottom;
        return true;
    }

    @NonNull
    public Drawable getContentBackground() {
        return getImpl().c();
    }

    private static int a(int i2, int i3) {
        int mode = MeasureSpec.getMode(i3);
        int size = MeasureSpec.getSize(i3);
        if (mode != Integer.MIN_VALUE) {
            return (mode == 0 || mode != 1073741824) ? i2 : size;
        }
        return Math.min(i2, size);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && getContentRect(this.k) && !this.k.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    public float getCompatElevation() {
        return getImpl().a();
    }

    public void setCompatElevation(float f2) {
        getImpl().a(f2);
    }

    private FloatingActionButtonImpl getImpl() {
        if (this.m == null) {
            this.m = a();
        }
        return this.m;
    }

    private FloatingActionButtonImpl a() {
        if (VERSION.SDK_INT >= 21) {
            return new FloatingActionButtonLollipop(this, new ShadowDelegateImpl());
        }
        return new FloatingActionButtonImpl(this, new ShadowDelegateImpl());
    }
}
