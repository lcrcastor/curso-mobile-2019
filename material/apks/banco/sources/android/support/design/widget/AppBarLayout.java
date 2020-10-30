package android.support.design.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.VisibleForTesting;
import android.support.design.R;
import android.support.design.widget.CoordinatorLayout.DefaultBehavior;
import android.support.v4.math.MathUtils;
import android.support.v4.util.ObjectsCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@DefaultBehavior(Behavior.class)
public class AppBarLayout extends LinearLayout {
    private int a;
    private int b;
    private int c;
    private boolean d;
    private int e;
    private WindowInsetsCompat f;
    private List<OnOffsetChangedListener> g;
    private boolean h;
    private boolean i;
    private int[] j;

    public static class Behavior extends HeaderBehavior<AppBarLayout> {
        /* access modifiers changed from: private */
        public int b;
        private ValueAnimator c;
        private int d = -1;
        private boolean e;
        private float f;
        private WeakReference<View> g;
        private DragCallback h;

        public static abstract class DragCallback {
            public abstract boolean canDrag(@NonNull AppBarLayout appBarLayout);
        }

        public static class SavedState extends AbsSavedState {
            public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
                /* renamed from: a */
                public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    return new SavedState(parcel, classLoader);
                }

                /* renamed from: a */
                public SavedState createFromParcel(Parcel parcel) {
                    return new SavedState(parcel, null);
                }

                /* renamed from: a */
                public SavedState[] newArray(int i) {
                    return new SavedState[i];
                }
            };
            int a;
            float b;
            boolean c;

            public SavedState(Parcel parcel, ClassLoader classLoader) {
                super(parcel, classLoader);
                this.a = parcel.readInt();
                this.b = parcel.readFloat();
                this.c = parcel.readByte() != 0;
            }

            public SavedState(Parcelable parcelable) {
                super(parcelable);
            }

            public void writeToParcel(Parcel parcel, int i) {
                super.writeToParcel(parcel, i);
                parcel.writeInt(this.a);
                parcel.writeFloat(this.b);
                parcel.writeByte(this.c ? (byte) 1 : 0);
            }
        }

        private static boolean a(int i, int i2) {
            return (i & i2) == i2;
        }

        public /* bridge */ /* synthetic */ int getLeftAndRightOffset() {
            return super.getLeftAndRightOffset();
        }

        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        public /* bridge */ /* synthetic */ boolean setLeftAndRightOffset(int i) {
            return super.setLeftAndRightOffset(i);
        }

        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int i) {
            return super.setTopAndBottomOffset(i);
        }

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, View view2, int i, int i2) {
            boolean z = (i & 2) != 0 && appBarLayout.b() && coordinatorLayout.getHeight() - view.getHeight() <= appBarLayout.getHeight();
            if (z && this.c != null) {
                this.c.cancel();
            }
            this.g = null;
            return z;
        }

        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int[] iArr, int i3) {
            int i4;
            int i5;
            if (i2 != 0) {
                if (i2 < 0) {
                    int i6 = -appBarLayout.getTotalScrollRange();
                    i5 = i6;
                    i4 = appBarLayout.getDownNestedPreScrollRange() + i6;
                } else {
                    i5 = -appBarLayout.getUpNestedPreScrollRange();
                    i4 = 0;
                }
                if (i5 != i4) {
                    iArr[1] = b(coordinatorLayout, appBarLayout, i2, i5, i4);
                }
            }
        }

        public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int i3, int i4, int i5) {
            if (i4 < 0) {
                b(coordinatorLayout, appBarLayout, i4, -appBarLayout.getDownNestedScrollRange(), 0);
            }
        }

        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i) {
            if (i == 0) {
                b(coordinatorLayout, appBarLayout);
            }
            this.g = new WeakReference<>(view);
        }

        public void setDragCallback(@Nullable DragCallback dragCallback) {
            this.h = dragCallback;
        }

        private void a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, float f2) {
            int i2;
            int abs = Math.abs(a() - i);
            float abs2 = Math.abs(f2);
            if (abs2 > BitmapDescriptorFactory.HUE_RED) {
                i2 = Math.round((((float) abs) / abs2) * 1000.0f) * 3;
            } else {
                i2 = (int) (((((float) abs) / ((float) appBarLayout.getHeight())) + 1.0f) * 150.0f);
            }
            a(coordinatorLayout, appBarLayout, i, i2);
        }

        private void a(final CoordinatorLayout coordinatorLayout, final AppBarLayout appBarLayout, int i, int i2) {
            int a = a();
            if (a == i) {
                if (this.c != null && this.c.isRunning()) {
                    this.c.cancel();
                }
                return;
            }
            if (this.c == null) {
                this.c = new ValueAnimator();
                this.c.setInterpolator(AnimationUtils.e);
                this.c.addUpdateListener(new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        Behavior.this.a(coordinatorLayout, appBarLayout, ((Integer) valueAnimator.getAnimatedValue()).intValue());
                    }
                });
            } else {
                this.c.cancel();
            }
            this.c.setDuration((long) Math.min(i2, SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_DEFAULT));
            this.c.setIntValues(new int[]{a, i});
            this.c.start();
        }

        private int a(AppBarLayout appBarLayout, int i) {
            int childCount = appBarLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = appBarLayout.getChildAt(i2);
                int i3 = -i;
                if (childAt.getTop() <= i3 && childAt.getBottom() >= i3) {
                    return i2;
                }
            }
            return -1;
        }

        private void b(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            int a = a();
            int a2 = a(appBarLayout, a);
            if (a2 >= 0) {
                View childAt = appBarLayout.getChildAt(a2);
                int scrollFlags = ((LayoutParams) childAt.getLayoutParams()).getScrollFlags();
                if ((scrollFlags & 17) == 17) {
                    int i = -childAt.getTop();
                    int i2 = -childAt.getBottom();
                    if (a2 == appBarLayout.getChildCount() - 1) {
                        i2 += appBarLayout.getTopInset();
                    }
                    if (a(scrollFlags, 2)) {
                        i2 += ViewCompat.getMinimumHeight(childAt);
                    } else if (a(scrollFlags, 5)) {
                        int minimumHeight = ViewCompat.getMinimumHeight(childAt) + i2;
                        if (a < minimumHeight) {
                            i = minimumHeight;
                        } else {
                            i2 = minimumHeight;
                        }
                    }
                    if (a < (i2 + i) / 2) {
                        i = i2;
                    }
                    a(coordinatorLayout, appBarLayout, MathUtils.clamp(i, -appBarLayout.getTotalScrollRange(), 0), (float) BitmapDescriptorFactory.HUE_RED);
                }
            }
        }

        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, int i3, int i4) {
            if (((android.support.design.widget.CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).height != -2) {
                return super.onMeasureChild(coordinatorLayout, appBarLayout, i, i2, i3, i4);
            }
            coordinatorLayout.onMeasureChild(appBarLayout, i, i2, MeasureSpec.makeMeasureSpec(0, 0), i4);
            return true;
        }

        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i) {
            int i2;
            boolean onLayoutChild = super.onLayoutChild(coordinatorLayout, appBarLayout, i);
            int pendingAction = appBarLayout.getPendingAction();
            if (this.d >= 0 && (pendingAction & 8) == 0) {
                View childAt = appBarLayout.getChildAt(this.d);
                int i3 = -childAt.getBottom();
                if (this.e) {
                    i2 = i3 + ViewCompat.getMinimumHeight(childAt) + appBarLayout.getTopInset();
                } else {
                    i2 = i3 + Math.round(((float) childAt.getHeight()) * this.f);
                }
                a(coordinatorLayout, appBarLayout, i2);
            } else if (pendingAction != 0) {
                boolean z = (pendingAction & 4) != 0;
                if ((pendingAction & 2) != 0) {
                    int i4 = -appBarLayout.getUpNestedPreScrollRange();
                    if (z) {
                        a(coordinatorLayout, appBarLayout, i4, (float) BitmapDescriptorFactory.HUE_RED);
                    } else {
                        a(coordinatorLayout, appBarLayout, i4);
                    }
                } else if ((pendingAction & 1) != 0) {
                    if (z) {
                        a(coordinatorLayout, appBarLayout, 0, (float) BitmapDescriptorFactory.HUE_RED);
                    } else {
                        a(coordinatorLayout, appBarLayout, 0);
                    }
                }
            }
            appBarLayout.c();
            this.d = -1;
            setTopAndBottomOffset(MathUtils.clamp(getTopAndBottomOffset(), -appBarLayout.getTotalScrollRange(), 0));
            a(coordinatorLayout, appBarLayout, getTopAndBottomOffset(), 0, true);
            appBarLayout.a(getTopAndBottomOffset());
            return onLayoutChild;
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: a */
        public boolean c(AppBarLayout appBarLayout) {
            if (this.h != null) {
                return this.h.canDrag(appBarLayout);
            }
            boolean z = true;
            if (this.g == null) {
                return true;
            }
            View view = (View) this.g.get();
            if (view == null || !view.isShown() || view.canScrollVertically(-1)) {
                z = false;
            }
            return z;
        }

        /* access modifiers changed from: 0000 */
        public void a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            b(coordinatorLayout, appBarLayout);
        }

        /* access modifiers changed from: 0000 */
        public int b(AppBarLayout appBarLayout) {
            return -appBarLayout.getDownNestedScrollRange();
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: c */
        public int a(AppBarLayout appBarLayout) {
            return appBarLayout.getTotalScrollRange();
        }

        /* access modifiers changed from: 0000 */
        public int a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, int i3) {
            int a = a();
            int i4 = 0;
            if (i2 == 0 || a < i2 || a > i3) {
                this.b = 0;
            } else {
                int clamp = MathUtils.clamp(i, i2, i3);
                if (a != clamp) {
                    int b2 = appBarLayout.a() ? b(appBarLayout, clamp) : clamp;
                    boolean topAndBottomOffset = setTopAndBottomOffset(b2);
                    i4 = a - clamp;
                    this.b = clamp - b2;
                    if (!topAndBottomOffset && appBarLayout.a()) {
                        coordinatorLayout.dispatchDependentViewsChanged(appBarLayout);
                    }
                    appBarLayout.a(getTopAndBottomOffset());
                    a(coordinatorLayout, appBarLayout, clamp, clamp < a ? -1 : 1, false);
                }
            }
            return i4;
        }

        private int b(AppBarLayout appBarLayout, int i) {
            int abs = Math.abs(i);
            int childCount = appBarLayout.getChildCount();
            int i2 = 0;
            int i3 = 0;
            while (true) {
                if (i3 >= childCount) {
                    break;
                }
                View childAt = appBarLayout.getChildAt(i3);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                Interpolator scrollInterpolator = layoutParams.getScrollInterpolator();
                if (abs < childAt.getTop() || abs > childAt.getBottom()) {
                    i3++;
                } else if (scrollInterpolator != null) {
                    int scrollFlags = layoutParams.getScrollFlags();
                    if ((scrollFlags & 1) != 0) {
                        i2 = 0 + childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                        if ((scrollFlags & 2) != 0) {
                            i2 -= ViewCompat.getMinimumHeight(childAt);
                        }
                    }
                    if (ViewCompat.getFitsSystemWindows(childAt)) {
                        i2 -= appBarLayout.getTopInset();
                    }
                    if (i2 > 0) {
                        float f2 = (float) i2;
                        return Integer.signum(i) * (childAt.getTop() + Math.round(f2 * scrollInterpolator.getInterpolation(((float) (abs - childAt.getTop())) / f2)));
                    }
                }
            }
            return i;
        }

        private void a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, boolean z) {
            View c2 = c(appBarLayout, i);
            if (c2 != null) {
                int scrollFlags = ((LayoutParams) c2.getLayoutParams()).getScrollFlags();
                boolean z2 = false;
                if ((scrollFlags & 1) != 0) {
                    int minimumHeight = ViewCompat.getMinimumHeight(c2);
                    if (i2 <= 0 || (scrollFlags & 12) == 0 ? !((scrollFlags & 2) == 0 || (-i) < (c2.getBottom() - minimumHeight) - appBarLayout.getTopInset()) : (-i) >= (c2.getBottom() - minimumHeight) - appBarLayout.getTopInset()) {
                        z2 = true;
                    }
                }
                boolean a = appBarLayout.a(z2);
                if (VERSION.SDK_INT < 11) {
                    return;
                }
                if (z || (a && c(coordinatorLayout, appBarLayout))) {
                    appBarLayout.jumpDrawablesToCurrentState();
                }
            }
        }

        private boolean c(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            List dependents = coordinatorLayout.getDependents(appBarLayout);
            int size = dependents.size();
            boolean z = false;
            for (int i = 0; i < size; i++) {
                android.support.design.widget.CoordinatorLayout.Behavior behavior = ((android.support.design.widget.CoordinatorLayout.LayoutParams) ((View) dependents.get(i)).getLayoutParams()).getBehavior();
                if (behavior instanceof ScrollingViewBehavior) {
                    if (((ScrollingViewBehavior) behavior).getOverlayTop() != 0) {
                        z = true;
                    }
                    return z;
                }
            }
            return false;
        }

        private static View c(AppBarLayout appBarLayout, int i) {
            int abs = Math.abs(i);
            int childCount = appBarLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = appBarLayout.getChildAt(i2);
                if (abs >= childAt.getTop() && abs <= childAt.getBottom()) {
                    return childAt;
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public int a() {
            return getTopAndBottomOffset() + this.b;
        }

        public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            Parcelable onSaveInstanceState = super.onSaveInstanceState(coordinatorLayout, appBarLayout);
            int topAndBottomOffset = getTopAndBottomOffset();
            int childCount = appBarLayout.getChildCount();
            boolean z = false;
            int i = 0;
            while (i < childCount) {
                View childAt = appBarLayout.getChildAt(i);
                int bottom = childAt.getBottom() + topAndBottomOffset;
                if (childAt.getTop() + topAndBottomOffset > 0 || bottom < 0) {
                    i++;
                } else {
                    SavedState savedState = new SavedState(onSaveInstanceState);
                    savedState.a = i;
                    if (bottom == ViewCompat.getMinimumHeight(childAt) + appBarLayout.getTopInset()) {
                        z = true;
                    }
                    savedState.c = z;
                    savedState.b = ((float) bottom) / ((float) childAt.getHeight());
                    return savedState;
                }
            }
            return onSaveInstanceState;
        }

        public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, Parcelable parcelable) {
            if (parcelable instanceof SavedState) {
                SavedState savedState = (SavedState) parcelable;
                super.onRestoreInstanceState(coordinatorLayout, appBarLayout, savedState.getSuperState());
                this.d = savedState.a;
                this.f = savedState.b;
                this.e = savedState.c;
                return;
            }
            super.onRestoreInstanceState(coordinatorLayout, appBarLayout, parcelable);
            this.d = -1;
        }
    }

    public static class LayoutParams extends android.widget.LinearLayout.LayoutParams {
        public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
        public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
        public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
        public static final int SCROLL_FLAG_SCROLL = 1;
        public static final int SCROLL_FLAG_SNAP = 16;
        int a = 1;
        Interpolator b;

        @RestrictTo({Scope.LIBRARY_GROUP})
        @Retention(RetentionPolicy.SOURCE)
        public @interface ScrollFlags {
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AppBarLayout_Layout);
            this.a = obtainStyledAttributes.getInt(R.styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
            if (obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
                this.b = AnimationUtils.loadInterpolator(context, obtainStyledAttributes.getResourceId(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0));
            }
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2, f);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        @RequiresApi(19)
        public LayoutParams(android.widget.LinearLayout.LayoutParams layoutParams) {
            super(layoutParams);
        }

        @RequiresApi(19)
        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.a = layoutParams.a;
            this.b = layoutParams.b;
        }

        public void setScrollFlags(int i) {
            this.a = i;
        }

        public int getScrollFlags() {
            return this.a;
        }

        public void setScrollInterpolator(Interpolator interpolator) {
            this.b = interpolator;
        }

        public Interpolator getScrollInterpolator() {
            return this.b;
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return (this.a & 1) == 1 && (this.a & 10) != 0;
        }
    }

    public interface OnOffsetChangedListener {
        void onOffsetChanged(AppBarLayout appBarLayout, int i);
    }

    public static class ScrollingViewBehavior extends HeaderScrollingViewBehavior {
        public /* bridge */ /* synthetic */ int getLeftAndRightOffset() {
            return super.getLeftAndRightOffset();
        }

        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        public /* bridge */ /* synthetic */ boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            return super.onLayoutChild(coordinatorLayout, view, i);
        }

        public /* bridge */ /* synthetic */ boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int i4) {
            return super.onMeasureChild(coordinatorLayout, view, i, i2, i3, i4);
        }

        public /* bridge */ /* synthetic */ boolean setLeftAndRightOffset(int i) {
            return super.setLeftAndRightOffset(i);
        }

        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int i) {
            return super.setTopAndBottomOffset(i);
        }

        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollingViewBehavior_Layout);
            setOverlayTop(obtainStyledAttributes.getDimensionPixelSize(R.styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
            obtainStyledAttributes.recycle();
        }

        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return view2 instanceof AppBarLayout;
        }

        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            a(coordinatorLayout, view, view2);
            return false;
        }

        public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean z) {
            AppBarLayout a = b(coordinatorLayout.getDependencies(view));
            if (a != null) {
                rect.offset(view.getLeft(), view.getTop());
                Rect rect2 = this.a;
                rect2.set(0, 0, coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                if (!rect2.contains(rect)) {
                    a.setExpanded(false, !z);
                    return true;
                }
            }
            return false;
        }

        private void a(CoordinatorLayout coordinatorLayout, View view, View view2) {
            android.support.design.widget.CoordinatorLayout.Behavior behavior = ((android.support.design.widget.CoordinatorLayout.LayoutParams) view2.getLayoutParams()).getBehavior();
            if (behavior instanceof Behavior) {
                ViewCompat.offsetTopAndBottom(view, (((view2.getBottom() - view.getTop()) + ((Behavior) behavior).b) + a()) - c(view2));
            }
        }

        /* access modifiers changed from: 0000 */
        public float a(View view) {
            if (view instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) view;
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                int downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange();
                int a = a(appBarLayout);
                if (downNestedPreScrollRange != 0 && totalScrollRange + a <= downNestedPreScrollRange) {
                    return BitmapDescriptorFactory.HUE_RED;
                }
                int i = totalScrollRange - downNestedPreScrollRange;
                if (i != 0) {
                    return (((float) a) / ((float) i)) + 1.0f;
                }
            }
            return BitmapDescriptorFactory.HUE_RED;
        }

        private static int a(AppBarLayout appBarLayout) {
            android.support.design.widget.CoordinatorLayout.Behavior behavior = ((android.support.design.widget.CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
            if (behavior instanceof Behavior) {
                return ((Behavior) behavior).a();
            }
            return 0;
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: a */
        public AppBarLayout b(List<View> list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                View view = (View) list.get(i);
                if (view instanceof AppBarLayout) {
                    return (AppBarLayout) view;
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public int b(View view) {
            if (view instanceof AppBarLayout) {
                return ((AppBarLayout) view).getTotalScrollRange();
            }
            return super.b(view);
        }
    }

    @Deprecated
    public float getTargetElevation() {
        return BitmapDescriptorFactory.HUE_RED;
    }

    public AppBarLayout(Context context) {
        this(context, null);
    }

    public AppBarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = -1;
        this.b = -1;
        this.c = -1;
        this.e = 0;
        setOrientation(1);
        ThemeUtils.a(context);
        if (VERSION.SDK_INT >= 21) {
            ViewUtilsLollipop.a(this);
            ViewUtilsLollipop.a(this, attributeSet, 0, R.style.Widget_Design_AppBarLayout);
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AppBarLayout, 0, R.style.Widget_Design_AppBarLayout);
        ViewCompat.setBackground(this, obtainStyledAttributes.getDrawable(R.styleable.AppBarLayout_android_background));
        if (obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_expanded)) {
            a(obtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_expanded, false), false, false);
        }
        if (VERSION.SDK_INT >= 21 && obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_elevation)) {
            ViewUtilsLollipop.a(this, (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.AppBarLayout_elevation, 0));
        }
        if (VERSION.SDK_INT >= 26) {
            if (obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_android_keyboardNavigationCluster)) {
                setKeyboardNavigationCluster(obtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_android_keyboardNavigationCluster, false));
            }
            if (obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_android_touchscreenBlocksFocus)) {
                setTouchscreenBlocksFocus(obtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_android_touchscreenBlocksFocus, false));
            }
        }
        obtainStyledAttributes.recycle();
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return AppBarLayout.this.a(windowInsetsCompat);
            }
        });
    }

    public void addOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        if (this.g == null) {
            this.g = new ArrayList();
        }
        if (onOffsetChangedListener != null && !this.g.contains(onOffsetChangedListener)) {
            this.g.add(onOffsetChangedListener);
        }
    }

    public void removeOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        if (this.g != null && onOffsetChangedListener != null) {
            this.g.remove(onOffsetChangedListener);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        e();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        super.onLayout(z, i2, i3, i4, i5);
        e();
        int i6 = 0;
        this.d = false;
        int childCount = getChildCount();
        while (true) {
            if (i6 >= childCount) {
                break;
            } else if (((LayoutParams) getChildAt(i6).getLayoutParams()).getScrollInterpolator() != null) {
                this.d = true;
                break;
            } else {
                i6++;
            }
        }
        d();
    }

    private void d() {
        int childCount = getChildCount();
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            } else if (((LayoutParams) getChildAt(i2).getLayoutParams()).a()) {
                z = true;
                break;
            } else {
                i2++;
            }
        }
        b(z);
    }

    private void e() {
        this.a = -1;
        this.b = -1;
        this.c = -1;
    }

    public void setOrientation(int i2) {
        if (i2 != 1) {
            throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
        }
        super.setOrientation(i2);
    }

    public void setExpanded(boolean z) {
        setExpanded(z, ViewCompat.isLaidOut(this));
    }

    public void setExpanded(boolean z, boolean z2) {
        a(z, z2, true);
    }

    private void a(boolean z, boolean z2, boolean z3) {
        int i2 = 0;
        int i3 = (z ? 1 : 2) | (z2 ? 4 : 0);
        if (z3) {
            i2 = 8;
        }
        this.e = i3 | i2;
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (VERSION.SDK_INT >= 19 && (layoutParams instanceof android.widget.LinearLayout.LayoutParams)) {
            return new LayoutParams((android.widget.LinearLayout.LayoutParams) layoutParams);
        }
        if (layoutParams instanceof MarginLayoutParams) {
            return new LayoutParams((MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return this.d;
    }

    public final int getTotalScrollRange() {
        if (this.a != -1) {
            return this.a;
        }
        int childCount = getChildCount();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            View childAt = getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i4 = layoutParams.a;
            if ((i4 & 1) == 0) {
                break;
            }
            i3 += measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin;
            if ((i4 & 2) != 0) {
                i3 -= ViewCompat.getMinimumHeight(childAt);
                break;
            }
            i2++;
        }
        int max = Math.max(0, i3 - getTopInset());
        this.a = max;
        return max;
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return getTotalScrollRange() != 0;
    }

    /* access modifiers changed from: 0000 */
    public int getUpNestedPreScrollRange() {
        return getTotalScrollRange();
    }

    /* access modifiers changed from: 0000 */
    public int getDownNestedPreScrollRange() {
        if (this.b != -1) {
            return this.b;
        }
        int i2 = 0;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i3 = layoutParams.a;
            if ((i3 & 5) == 5) {
                int i4 = i2 + layoutParams.topMargin + layoutParams.bottomMargin;
                if ((i3 & 8) != 0) {
                    i2 = i4 + ViewCompat.getMinimumHeight(childAt);
                } else if ((i3 & 2) != 0) {
                    i2 = i4 + (measuredHeight - ViewCompat.getMinimumHeight(childAt));
                } else {
                    i2 = i4 + (measuredHeight - getTopInset());
                }
            } else if (i2 > 0) {
                break;
            }
        }
        int max = Math.max(0, i2);
        this.b = max;
        return max;
    }

    /* access modifiers changed from: 0000 */
    public int getDownNestedScrollRange() {
        if (this.c != -1) {
            return this.c;
        }
        int childCount = getChildCount();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            View childAt = getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            int i4 = layoutParams.a;
            if ((i4 & 1) == 0) {
                break;
            }
            i3 += measuredHeight;
            if ((i4 & 2) != 0) {
                i3 -= ViewCompat.getMinimumHeight(childAt) + getTopInset();
                break;
            }
            i2++;
        }
        int max = Math.max(0, i3);
        this.c = max;
        return max;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        if (this.g != null) {
            int size = this.g.size();
            for (int i3 = 0; i3 < size; i3++) {
                OnOffsetChangedListener onOffsetChangedListener = (OnOffsetChangedListener) this.g.get(i3);
                if (onOffsetChangedListener != null) {
                    onOffsetChangedListener.onOffsetChanged(this, i2);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final int getMinimumHeightForVisibleOverlappingContent() {
        int topInset = getTopInset();
        int minimumHeight = ViewCompat.getMinimumHeight(this);
        if (minimumHeight != 0) {
            return (minimumHeight * 2) + topInset;
        }
        int childCount = getChildCount();
        int minimumHeight2 = childCount >= 1 ? ViewCompat.getMinimumHeight(getChildAt(childCount - 1)) : 0;
        if (minimumHeight2 != 0) {
            return (minimumHeight2 * 2) + topInset;
        }
        return getHeight() / 3;
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i2) {
        if (this.j == null) {
            this.j = new int[2];
        }
        int[] iArr = this.j;
        int[] onCreateDrawableState = super.onCreateDrawableState(i2 + iArr.length);
        iArr[0] = this.h ? R.attr.state_collapsible : -R.attr.state_collapsible;
        iArr[1] = (!this.h || !this.i) ? -R.attr.state_collapsed : R.attr.state_collapsed;
        return mergeDrawableStates(onCreateDrawableState, iArr);
    }

    private boolean b(boolean z) {
        if (this.h == z) {
            return false;
        }
        this.h = z;
        refreshDrawableState();
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(boolean z) {
        if (this.i == z) {
            return false;
        }
        this.i = z;
        refreshDrawableState();
        return true;
    }

    @Deprecated
    public void setTargetElevation(float f2) {
        if (VERSION.SDK_INT >= 21) {
            ViewUtilsLollipop.a(this, f2);
        }
    }

    /* access modifiers changed from: 0000 */
    public int getPendingAction() {
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        this.e = 0;
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public final int getTopInset() {
        if (this.f != null) {
            return this.f.getSystemWindowInsetTop();
        }
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public WindowInsetsCompat a(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat windowInsetsCompat2 = ViewCompat.getFitsSystemWindows(this) ? windowInsetsCompat : null;
        if (!ObjectsCompat.equals(this.f, windowInsetsCompat2)) {
            this.f = windowInsetsCompat2;
            e();
        }
        return windowInsetsCompat;
    }
}
