package android.support.design.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.VisibleForTesting;
import android.support.coreui.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.math.MathUtils;
import android.support.v4.util.ObjectsCompat;
import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SynchronizedPool;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.widget.DirectedAcyclicGraph;
import android.support.v4.widget.ViewGroupUtils;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.ViewParent;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatorLayout extends ViewGroup implements NestedScrollingParent2 {
    static final String a;
    static final Class<?>[] b = {Context.class, AttributeSet.class};
    static final ThreadLocal<Map<String, Constructor<Behavior>>> c = new ThreadLocal<>();
    static final Comparator<View> d;
    private static final Pool<Rect> f = new SynchronizedPool(12);
    OnHierarchyChangeListener e;
    private final List<View> g;
    private final DirectedAcyclicGraph<View> h;
    private final List<View> i;
    private final List<View> j;
    private final int[] k;
    private Paint l;
    private boolean m;
    private boolean n;
    private int[] o;
    private View p;
    private View q;
    private OnPreDrawListener r;
    private boolean s;
    private WindowInsetsCompat t;
    private boolean u;
    private Drawable v;
    private OnApplyWindowInsetsListener w;
    private final NestedScrollingParentHelper x;

    public interface AttachedBehavior {
        @NonNull
        Behavior getBehavior();
    }

    public static abstract class Behavior<V extends View> {
        public boolean getInsetDodgeRect(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull Rect rect) {
            return false;
        }

        @ColorInt
        public int getScrimColor(CoordinatorLayout coordinatorLayout, V v) {
            return ViewCompat.MEASURED_STATE_MASK;
        }

        @FloatRange(from = 0.0d, to = 1.0d)
        public float getScrimOpacity(CoordinatorLayout coordinatorLayout, V v) {
            return BitmapDescriptorFactory.HUE_RED;
        }

        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, V v, View view) {
            return false;
        }

        @NonNull
        public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout coordinatorLayout, V v, WindowInsetsCompat windowInsetsCompat) {
            return windowInsetsCompat;
        }

        public void onAttachedToLayoutParams(@NonNull LayoutParams layoutParams) {
        }

        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, V v, View view) {
            return false;
        }

        public void onDependentViewRemoved(CoordinatorLayout coordinatorLayout, V v, View view) {
        }

        public void onDetachedFromLayoutParams() {
        }

        public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            return false;
        }

        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int i) {
            return false;
        }

        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3, int i4) {
            return false;
        }

        public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, float f, float f2, boolean z) {
            return false;
        }

        public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, float f, float f2) {
            return false;
        }

        @Deprecated
        public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int i, int i2, @NonNull int[] iArr) {
        }

        @Deprecated
        public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int i, int i2, int i3, int i4) {
        }

        @Deprecated
        public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int i) {
        }

        public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, V v, Rect rect, boolean z) {
            return false;
        }

        public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
        }

        @Deprecated
        public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int i) {
            return false;
        }

        @Deprecated
        public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view) {
        }

        public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            return false;
        }

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
        }

        public boolean blocksInteractionBelow(CoordinatorLayout coordinatorLayout, V v) {
            return getScrimOpacity(coordinatorLayout, v) > BitmapDescriptorFactory.HUE_RED;
        }

        public static void setTag(View view, Object obj) {
            ((LayoutParams) view.getLayoutParams()).i = obj;
        }

        public static Object getTag(View view) {
            return ((LayoutParams) view.getLayoutParams()).i;
        }

        public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int i, int i2) {
            if (i2 == 0) {
                return onStartNestedScroll(coordinatorLayout, v, view, view2, i);
            }
            return false;
        }

        public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int i, int i2) {
            if (i2 == 0) {
                onNestedScrollAccepted(coordinatorLayout, v, view, view2, i);
            }
        }

        public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int i) {
            if (i == 0) {
                onStopNestedScroll(coordinatorLayout, v, view);
            }
        }

        public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int i, int i2, int i3, int i4, int i5) {
            if (i5 == 0) {
                onNestedScroll(coordinatorLayout, v, view, i, i2, i3, i4);
            }
        }

        public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int i, int i2, @NonNull int[] iArr, int i3) {
            if (i3 == 0) {
                onNestedPreScroll(coordinatorLayout, v, view, i, i2, iArr);
            }
        }

        public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v) {
            return BaseSavedState.EMPTY_STATE;
        }
    }

    @Deprecated
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DefaultBehavior {
        Class<? extends Behavior> value();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DispatchChangeEvent {
    }

    class HierarchyChangeListener implements OnHierarchyChangeListener {
        HierarchyChangeListener() {
        }

        public void onChildViewAdded(View view, View view2) {
            if (CoordinatorLayout.this.e != null) {
                CoordinatorLayout.this.e.onChildViewAdded(view, view2);
            }
        }

        public void onChildViewRemoved(View view, View view2) {
            CoordinatorLayout.this.a(2);
            if (CoordinatorLayout.this.e != null) {
                CoordinatorLayout.this.e.onChildViewRemoved(view, view2);
            }
        }
    }

    public static class LayoutParams extends MarginLayoutParams {
        Behavior a;
        public int anchorGravity = 0;
        boolean b = false;
        int c = -1;
        int d;
        public int dodgeInsetEdges = 0;
        int e;
        View f;
        View g;
        public int gravity = 0;
        final Rect h = new Rect();
        Object i;
        public int insetEdge = 0;
        private boolean j;
        private boolean k;
        public int keyline = -1;
        private boolean l;
        private boolean m;

        public LayoutParams(int i2, int i3) {
            super(i2, i3);
        }

        LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout_Layout);
            this.gravity = obtainStyledAttributes.getInteger(R.styleable.CoordinatorLayout_Layout_android_layout_gravity, 0);
            this.c = obtainStyledAttributes.getResourceId(R.styleable.CoordinatorLayout_Layout_layout_anchor, -1);
            this.anchorGravity = obtainStyledAttributes.getInteger(R.styleable.CoordinatorLayout_Layout_layout_anchorGravity, 0);
            this.keyline = obtainStyledAttributes.getInteger(R.styleable.CoordinatorLayout_Layout_layout_keyline, -1);
            this.insetEdge = obtainStyledAttributes.getInt(R.styleable.CoordinatorLayout_Layout_layout_insetEdge, 0);
            this.dodgeInsetEdges = obtainStyledAttributes.getInt(R.styleable.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
            this.b = obtainStyledAttributes.hasValue(R.styleable.CoordinatorLayout_Layout_layout_behavior);
            if (this.b) {
                this.a = CoordinatorLayout.a(context, attributeSet, obtainStyledAttributes.getString(R.styleable.CoordinatorLayout_Layout_layout_behavior));
            }
            obtainStyledAttributes.recycle();
            if (this.a != null) {
                this.a.onAttachedToLayoutParams(this);
            }
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        @IdRes
        public int getAnchorId() {
            return this.c;
        }

        public void setAnchorId(@IdRes int i2) {
            g();
            this.c = i2;
        }

        @Nullable
        public Behavior getBehavior() {
            return this.a;
        }

        public void setBehavior(@Nullable Behavior behavior) {
            if (this.a != behavior) {
                if (this.a != null) {
                    this.a.onDetachedFromLayoutParams();
                }
                this.a = behavior;
                this.i = null;
                this.b = true;
                if (behavior != null) {
                    behavior.onAttachedToLayoutParams(this);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Rect rect) {
            this.h.set(rect);
        }

        /* access modifiers changed from: 0000 */
        public Rect a() {
            return this.h;
        }

        /* access modifiers changed from: 0000 */
        public boolean b() {
            return this.f == null && this.c != -1;
        }

        /* access modifiers changed from: 0000 */
        public boolean c() {
            if (this.a == null) {
                this.j = false;
            }
            return this.j;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(CoordinatorLayout coordinatorLayout, View view) {
            if (this.j) {
                return true;
            }
            boolean blocksInteractionBelow = (this.a != null ? this.a.blocksInteractionBelow(coordinatorLayout, view) : false) | this.j;
            this.j = blocksInteractionBelow;
            return blocksInteractionBelow;
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            this.j = false;
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2) {
            a(i2, false);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2, boolean z) {
            switch (i2) {
                case 0:
                    this.k = z;
                    return;
                case 1:
                    this.l = z;
                    return;
                default:
                    return;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean b(int i2) {
            switch (i2) {
                case 0:
                    return this.k;
                case 1:
                    return this.l;
                default:
                    return false;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean e() {
            return this.m;
        }

        /* access modifiers changed from: 0000 */
        public void a(boolean z) {
            this.m = z;
        }

        /* access modifiers changed from: 0000 */
        public void f() {
            this.m = false;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return view2 == this.g || a(view2, ViewCompat.getLayoutDirection(coordinatorLayout)) || (this.a != null && this.a.layoutDependsOn(coordinatorLayout, view, view2));
        }

        /* access modifiers changed from: 0000 */
        public void g() {
            this.g = null;
            this.f = null;
        }

        /* access modifiers changed from: 0000 */
        public View b(CoordinatorLayout coordinatorLayout, View view) {
            if (this.c == -1) {
                this.g = null;
                this.f = null;
                return null;
            }
            if (this.f == null || !b(view, coordinatorLayout)) {
                a(view, coordinatorLayout);
            }
            return this.f;
        }

        private void a(View view, CoordinatorLayout coordinatorLayout) {
            this.f = coordinatorLayout.findViewById(this.c);
            if (this.f != null) {
                if (this.f != coordinatorLayout) {
                    View view2 = this.f;
                    ViewParent parent = this.f.getParent();
                    while (parent != coordinatorLayout && parent != null) {
                        if (parent != view) {
                            if (parent instanceof View) {
                                view2 = (View) parent;
                            }
                            parent = parent.getParent();
                        } else if (coordinatorLayout.isInEditMode()) {
                            this.g = null;
                            this.f = null;
                            return;
                        } else {
                            throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
                        }
                    }
                    this.g = view2;
                } else if (coordinatorLayout.isInEditMode()) {
                    this.g = null;
                    this.f = null;
                } else {
                    throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
                }
            } else if (coordinatorLayout.isInEditMode()) {
                this.g = null;
                this.f = null;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Could not find CoordinatorLayout descendant view with id ");
                sb.append(coordinatorLayout.getResources().getResourceName(this.c));
                sb.append(" to anchor view ");
                sb.append(view);
                throw new IllegalStateException(sb.toString());
            }
        }

        private boolean b(View view, CoordinatorLayout coordinatorLayout) {
            if (this.f.getId() != this.c) {
                return false;
            }
            View view2 = this.f;
            for (ViewParent parent = this.f.getParent(); parent != coordinatorLayout; parent = parent.getParent()) {
                if (parent == null || parent == view) {
                    this.g = null;
                    this.f = null;
                    return false;
                }
                if (parent instanceof View) {
                    view2 = (View) parent;
                }
            }
            this.g = view2;
            return true;
        }

        private boolean a(View view, int i2) {
            int absoluteGravity = GravityCompat.getAbsoluteGravity(((LayoutParams) view.getLayoutParams()).insetEdge, i2);
            return absoluteGravity != 0 && (GravityCompat.getAbsoluteGravity(this.dodgeInsetEdges, i2) & absoluteGravity) == absoluteGravity;
        }
    }

    class OnPreDrawListener implements android.view.ViewTreeObserver.OnPreDrawListener {
        OnPreDrawListener() {
        }

        public boolean onPreDraw() {
            CoordinatorLayout.this.a(0);
            return true;
        }
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
        SparseArray<Parcelable> a;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            int readInt = parcel.readInt();
            int[] iArr = new int[readInt];
            parcel.readIntArray(iArr);
            Parcelable[] readParcelableArray = parcel.readParcelableArray(classLoader);
            this.a = new SparseArray<>(readInt);
            for (int i = 0; i < readInt; i++) {
                this.a.append(iArr[i], readParcelableArray[i]);
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            int size = this.a != null ? this.a.size() : 0;
            parcel.writeInt(size);
            int[] iArr = new int[size];
            Parcelable[] parcelableArr = new Parcelable[size];
            for (int i2 = 0; i2 < size; i2++) {
                iArr[i2] = this.a.keyAt(i2);
                parcelableArr[i2] = (Parcelable) this.a.valueAt(i2);
            }
            parcel.writeIntArray(iArr);
            parcel.writeParcelableArray(parcelableArr, i);
        }
    }

    static class ViewElevationComparator implements Comparator<View> {
        ViewElevationComparator() {
        }

        /* renamed from: a */
        public int compare(View view, View view2) {
            float z = ViewCompat.getZ(view);
            float z2 = ViewCompat.getZ(view2);
            if (z > z2) {
                return -1;
            }
            return z < z2 ? 1 : 0;
        }
    }

    private static int c(int i2) {
        if ((i2 & 7) == 0) {
            i2 |= GravityCompat.START;
        }
        return (i2 & 112) == 0 ? i2 | 48 : i2;
    }

    private static int d(int i2) {
        if (i2 == 0) {
            return 8388661;
        }
        return i2;
    }

    private static int e(int i2) {
        if (i2 == 0) {
            return 17;
        }
        return i2;
    }

    static {
        Package packageR = CoordinatorLayout.class.getPackage();
        a = packageR != null ? packageR.getName() : null;
        if (VERSION.SDK_INT >= 21) {
            d = new ViewElevationComparator();
        } else {
            d = null;
        }
    }

    @NonNull
    private static Rect d() {
        Rect rect = (Rect) f.acquire();
        return rect == null ? new Rect() : rect;
    }

    private static void a(@NonNull Rect rect) {
        rect.setEmpty();
        f.release(rect);
    }

    public CoordinatorLayout(Context context) {
        this(context, null);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.coordinatorLayoutStyle);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet, int i2) {
        TypedArray typedArray;
        super(context, attributeSet, i2);
        this.g = new ArrayList();
        this.h = new DirectedAcyclicGraph<>();
        this.i = new ArrayList();
        this.j = new ArrayList();
        this.k = new int[2];
        this.x = new NestedScrollingParentHelper(this);
        if (i2 == 0) {
            typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout, 0, R.style.Widget_Support_CoordinatorLayout);
        } else {
            typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout, i2, 0);
        }
        int resourceId = typedArray.getResourceId(R.styleable.CoordinatorLayout_keylines, 0);
        if (resourceId != 0) {
            Resources resources = context.getResources();
            this.o = resources.getIntArray(resourceId);
            float f2 = resources.getDisplayMetrics().density;
            int length = this.o.length;
            for (int i3 = 0; i3 < length; i3++) {
                this.o[i3] = (int) (((float) this.o[i3]) * f2);
            }
        }
        this.v = typedArray.getDrawable(R.styleable.CoordinatorLayout_statusBarBackground);
        typedArray.recycle();
        f();
        super.setOnHierarchyChangeListener(new HierarchyChangeListener());
    }

    public void setOnHierarchyChangeListener(OnHierarchyChangeListener onHierarchyChangeListener) {
        this.e = onHierarchyChangeListener;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        a(false);
        if (this.s) {
            if (this.r == null) {
                this.r = new OnPreDrawListener();
            }
            getViewTreeObserver().addOnPreDrawListener(this.r);
        }
        if (this.t == null && ViewCompat.getFitsSystemWindows(this)) {
            ViewCompat.requestApplyInsets(this);
        }
        this.n = true;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        a(false);
        if (this.s && this.r != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.r);
        }
        if (this.q != null) {
            onStopNestedScroll(this.q);
        }
        this.n = false;
    }

    public void setStatusBarBackground(@Nullable Drawable drawable) {
        if (this.v != drawable) {
            Drawable drawable2 = null;
            if (this.v != null) {
                this.v.setCallback(null);
            }
            if (drawable != null) {
                drawable2 = drawable.mutate();
            }
            this.v = drawable2;
            if (this.v != null) {
                if (this.v.isStateful()) {
                    this.v.setState(getDrawableState());
                }
                DrawableCompat.setLayoutDirection(this.v, ViewCompat.getLayoutDirection(this));
                this.v.setVisible(getVisibility() == 0, false);
                this.v.setCallback(this);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Nullable
    public Drawable getStatusBarBackground() {
        return this.v;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.v;
        boolean z = false;
        if (drawable != null && drawable.isStateful()) {
            z = false | drawable.setState(drawableState);
        }
        if (z) {
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.v;
    }

    public void setVisibility(int i2) {
        super.setVisibility(i2);
        boolean z = i2 == 0;
        if (this.v != null && this.v.isVisible() != z) {
            this.v.setVisible(z, false);
        }
    }

    public void setStatusBarBackgroundResource(@DrawableRes int i2) {
        setStatusBarBackground(i2 != 0 ? ContextCompat.getDrawable(getContext(), i2) : null);
    }

    public void setStatusBarBackgroundColor(@ColorInt int i2) {
        setStatusBarBackground(new ColorDrawable(i2));
    }

    /* access modifiers changed from: 0000 */
    public final WindowInsetsCompat a(WindowInsetsCompat windowInsetsCompat) {
        if (ObjectsCompat.equals(this.t, windowInsetsCompat)) {
            return windowInsetsCompat;
        }
        this.t = windowInsetsCompat;
        boolean z = false;
        this.u = windowInsetsCompat != null && windowInsetsCompat.getSystemWindowInsetTop() > 0;
        if (!this.u && getBackground() == null) {
            z = true;
        }
        setWillNotDraw(z);
        WindowInsetsCompat b2 = b(windowInsetsCompat);
        requestLayout();
        return b2;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public final WindowInsetsCompat getLastWindowInsets() {
        return this.t;
    }

    private void a(boolean z) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            Behavior behavior = ((LayoutParams) childAt.getLayoutParams()).getBehavior();
            if (behavior != null) {
                long uptimeMillis = SystemClock.uptimeMillis();
                MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
                if (z) {
                    behavior.onInterceptTouchEvent(this, childAt, obtain);
                } else {
                    behavior.onTouchEvent(this, childAt, obtain);
                }
                obtain.recycle();
            }
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            ((LayoutParams) getChildAt(i3).getLayoutParams()).d();
        }
        this.p = null;
        this.m = false;
    }

    private void a(List<View> list) {
        list.clear();
        boolean isChildrenDrawingOrderEnabled = isChildrenDrawingOrderEnabled();
        int childCount = getChildCount();
        int i2 = childCount - 1;
        while (i2 >= 0) {
            list.add(getChildAt(isChildrenDrawingOrderEnabled ? getChildDrawingOrder(childCount, i2) : i2));
            i2--;
        }
        if (d != null) {
            Collections.sort(list, d);
        }
    }

    private boolean a(MotionEvent motionEvent, int i2) {
        MotionEvent motionEvent2 = motionEvent;
        int actionMasked = motionEvent.getActionMasked();
        List<View> list = this.i;
        a(list);
        int size = list.size();
        MotionEvent motionEvent3 = null;
        boolean z = false;
        boolean z2 = false;
        for (int i3 = 0; i3 < size; i3++) {
            View view = (View) list.get(i3);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Behavior behavior = layoutParams.getBehavior();
            if ((z || z2) && actionMasked != 0) {
                if (behavior != null) {
                    if (motionEvent3 == null) {
                        long uptimeMillis = SystemClock.uptimeMillis();
                        motionEvent3 = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
                    }
                    switch (i2) {
                        case 0:
                            behavior.onInterceptTouchEvent(this, view, motionEvent3);
                            break;
                        case 1:
                            behavior.onTouchEvent(this, view, motionEvent3);
                            break;
                    }
                }
            } else {
                if (!z && behavior != null) {
                    switch (i2) {
                        case 0:
                            z = behavior.onInterceptTouchEvent(this, view, motionEvent2);
                            break;
                        case 1:
                            z = behavior.onTouchEvent(this, view, motionEvent2);
                            break;
                    }
                    if (z) {
                        this.p = view;
                    }
                }
                boolean c2 = layoutParams.c();
                boolean a2 = layoutParams.a(this, view);
                z2 = a2 && !c2;
                if (a2 && !z2) {
                    list.clear();
                    return z;
                }
            }
        }
        list.clear();
        return z;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            a(true);
        }
        boolean a2 = a(motionEvent, 0);
        if (actionMasked == 1 || actionMasked == 3) {
            a(true);
        }
        return a2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0012, code lost:
        if (r3 != false) goto L_0x0018;
     */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            int r2 = r18.getActionMasked()
            android.view.View r3 = r0.p
            r4 = 1
            r5 = 0
            if (r3 != 0) goto L_0x0017
            boolean r3 = r0.a(r1, r4)
            if (r3 == 0) goto L_0x0015
            goto L_0x0018
        L_0x0015:
            r6 = 0
            goto L_0x002c
        L_0x0017:
            r3 = 0
        L_0x0018:
            android.view.View r6 = r0.p
            android.view.ViewGroup$LayoutParams r6 = r6.getLayoutParams()
            android.support.design.widget.CoordinatorLayout$LayoutParams r6 = (android.support.design.widget.CoordinatorLayout.LayoutParams) r6
            android.support.design.widget.CoordinatorLayout$Behavior r6 = r6.getBehavior()
            if (r6 == 0) goto L_0x0015
            android.view.View r7 = r0.p
            boolean r6 = r6.onTouchEvent(r0, r7, r1)
        L_0x002c:
            android.view.View r7 = r0.p
            r8 = 0
            if (r7 != 0) goto L_0x0037
            boolean r1 = super.onTouchEvent(r18)
            r6 = r6 | r1
            goto L_0x004a
        L_0x0037:
            if (r3 == 0) goto L_0x004a
            long r11 = android.os.SystemClock.uptimeMillis()
            r13 = 3
            r14 = 0
            r15 = 0
            r16 = 0
            r9 = r11
            android.view.MotionEvent r8 = android.view.MotionEvent.obtain(r9, r11, r13, r14, r15, r16)
            super.onTouchEvent(r8)
        L_0x004a:
            if (r8 == 0) goto L_0x004f
            r8.recycle()
        L_0x004f:
            if (r2 == r4) goto L_0x0054
            r1 = 3
            if (r2 != r1) goto L_0x0057
        L_0x0054:
            r0.a(r5)
        L_0x0057:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.CoordinatorLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        if (z && !this.m) {
            a(false);
            this.m = true;
        }
    }

    private int b(int i2) {
        if (this.o == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("No keylines defined for ");
            sb.append(this);
            sb.append(" - attempted index lookup ");
            sb.append(i2);
            Log.e("CoordinatorLayout", sb.toString());
            return 0;
        } else if (i2 >= 0 && i2 < this.o.length) {
            return this.o[i2];
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Keyline index ");
            sb2.append(i2);
            sb2.append(" out of range for ");
            sb2.append(this);
            Log.e("CoordinatorLayout", sb2.toString());
            return 0;
        }
    }

    static Behavior a(Context context, AttributeSet attributeSet, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith(".")) {
            StringBuilder sb = new StringBuilder();
            sb.append(context.getPackageName());
            sb.append(str);
            str = sb.toString();
        } else if (str.indexOf(46) < 0 && !TextUtils.isEmpty(a)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a);
            sb2.append('.');
            sb2.append(str);
            str = sb2.toString();
        }
        try {
            Map map = (Map) c.get();
            if (map == null) {
                map = new HashMap();
                c.set(map);
            }
            Constructor constructor = (Constructor) map.get(str);
            if (constructor == null) {
                constructor = context.getClassLoader().loadClass(str).getConstructor(b);
                constructor.setAccessible(true);
                map.put(str, constructor);
            }
            return (Behavior) constructor.newInstance(new Object[]{context, attributeSet});
        } catch (Exception e2) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Could not inflate Behavior subclass ");
            sb3.append(str);
            throw new RuntimeException(sb3.toString(), e2);
        }
    }

    /* access modifiers changed from: 0000 */
    public LayoutParams a(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (!layoutParams.b) {
            if (view instanceof AttachedBehavior) {
                Behavior behavior = ((AttachedBehavior) view).getBehavior();
                if (behavior == null) {
                    Log.e("CoordinatorLayout", "Attached behavior class is null");
                }
                layoutParams.setBehavior(behavior);
                layoutParams.b = true;
            } else {
                DefaultBehavior defaultBehavior = null;
                for (Class cls = view.getClass(); cls != null; cls = cls.getSuperclass()) {
                    defaultBehavior = (DefaultBehavior) cls.getAnnotation(DefaultBehavior.class);
                    if (defaultBehavior != null) {
                        break;
                    }
                }
                if (defaultBehavior != null) {
                    try {
                        layoutParams.setBehavior((Behavior) defaultBehavior.value().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
                    } catch (Exception e2) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Default behavior class ");
                        sb.append(defaultBehavior.value().getName());
                        sb.append(" could not be instantiated. Did you forget");
                        sb.append(" a default constructor?");
                        Log.e("CoordinatorLayout", sb.toString(), e2);
                    }
                }
                layoutParams.b = true;
            }
        }
        return layoutParams;
    }

    private void e() {
        this.g.clear();
        this.h.clear();
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            LayoutParams a2 = a(childAt);
            a2.b(this, childAt);
            this.h.addNode(childAt);
            for (int i3 = 0; i3 < childCount; i3++) {
                if (i3 != i2) {
                    View childAt2 = getChildAt(i3);
                    if (a2.a(this, childAt, childAt2)) {
                        if (!this.h.contains(childAt2)) {
                            this.h.addNode(childAt2);
                        }
                        this.h.addEdge(childAt2, childAt);
                    }
                }
            }
        }
        this.g.addAll(this.h.getSortedList());
        Collections.reverse(this.g);
    }

    /* access modifiers changed from: 0000 */
    public void a(View view, Rect rect) {
        ViewGroupUtils.getDescendantRect(this, view, rect);
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), getPaddingLeft() + getPaddingRight());
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), getPaddingTop() + getPaddingBottom());
    }

    public void onMeasureChild(View view, int i2, int i3, int i4, int i5) {
        measureChildWithMargins(view, i2, i3, i4, i5);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0120, code lost:
        if (r0.onMeasureChild(r7, r3, r23, r20, r25, 0) == false) goto L_0x0134;
     */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00c6  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0126  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r32, int r33) {
        /*
            r31 = this;
            r7 = r31
            r31.e()
            r31.a()
            int r8 = r31.getPaddingLeft()
            int r0 = r31.getPaddingTop()
            int r9 = r31.getPaddingRight()
            int r1 = r31.getPaddingBottom()
            int r10 = android.support.v4.view.ViewCompat.getLayoutDirection(r31)
            r2 = 1
            if (r10 != r2) goto L_0x0021
            r12 = 1
            goto L_0x0022
        L_0x0021:
            r12 = 0
        L_0x0022:
            int r13 = android.view.View.MeasureSpec.getMode(r32)
            int r14 = android.view.View.MeasureSpec.getSize(r32)
            int r15 = android.view.View.MeasureSpec.getMode(r33)
            int r16 = android.view.View.MeasureSpec.getSize(r33)
            int r17 = r8 + r9
            int r18 = r0 + r1
            int r0 = r31.getSuggestedMinimumWidth()
            int r1 = r31.getSuggestedMinimumHeight()
            android.support.v4.view.WindowInsetsCompat r3 = r7.t
            if (r3 == 0) goto L_0x004b
            boolean r3 = android.support.v4.view.ViewCompat.getFitsSystemWindows(r31)
            if (r3 == 0) goto L_0x004b
            r19 = 1
            goto L_0x004d
        L_0x004b:
            r19 = 0
        L_0x004d:
            java.util.List<android.view.View> r2 = r7.g
            int r6 = r2.size()
            r4 = r0
            r2 = r1
            r3 = 0
            r5 = 0
        L_0x0057:
            if (r5 >= r6) goto L_0x0178
            java.util.List<android.view.View> r0 = r7.g
            java.lang.Object r0 = r0.get(r5)
            r1 = r0
            android.view.View r1 = (android.view.View) r1
            int r0 = r1.getVisibility()
            r11 = 8
            if (r0 != r11) goto L_0x0072
            r22 = r5
            r24 = r6
            r21 = 0
            goto L_0x0172
        L_0x0072:
            android.view.ViewGroup$LayoutParams r0 = r1.getLayoutParams()
            r11 = r0
            android.support.design.widget.CoordinatorLayout$LayoutParams r11 = (android.support.design.widget.CoordinatorLayout.LayoutParams) r11
            int r0 = r11.keyline
            if (r0 < 0) goto L_0x00bd
            if (r13 == 0) goto L_0x00bd
            int r0 = r11.keyline
            int r0 = r7.b(r0)
            r21 = r2
            int r2 = r11.gravity
            int r2 = d(r2)
            int r2 = android.support.v4.view.GravityCompat.getAbsoluteGravity(r2, r10)
            r2 = r2 & 7
            r22 = r3
            r3 = 3
            if (r2 != r3) goto L_0x009a
            if (r12 == 0) goto L_0x009f
        L_0x009a:
            r3 = 5
            if (r2 != r3) goto L_0x00ab
            if (r12 == 0) goto L_0x00ab
        L_0x009f:
            int r2 = r14 - r9
            int r2 = r2 - r0
            r0 = 0
            int r2 = java.lang.Math.max(r0, r2)
            r20 = r2
            r3 = 0
            goto L_0x00c4
        L_0x00ab:
            if (r2 != r3) goto L_0x00af
            if (r12 == 0) goto L_0x00b4
        L_0x00af:
            r3 = 3
            if (r2 != r3) goto L_0x00c1
            if (r12 == 0) goto L_0x00c1
        L_0x00b4:
            int r0 = r0 - r8
            r3 = 0
            int r0 = java.lang.Math.max(r3, r0)
            r20 = r0
            goto L_0x00c4
        L_0x00bd:
            r21 = r2
            r22 = r3
        L_0x00c1:
            r3 = 0
            r20 = 0
        L_0x00c4:
            if (r19 == 0) goto L_0x00f7
            boolean r0 = android.support.v4.view.ViewCompat.getFitsSystemWindows(r1)
            if (r0 != 0) goto L_0x00f7
            android.support.v4.view.WindowInsetsCompat r0 = r7.t
            int r0 = r0.getSystemWindowInsetLeft()
            android.support.v4.view.WindowInsetsCompat r2 = r7.t
            int r2 = r2.getSystemWindowInsetRight()
            int r0 = r0 + r2
            android.support.v4.view.WindowInsetsCompat r2 = r7.t
            int r2 = r2.getSystemWindowInsetTop()
            android.support.v4.view.WindowInsetsCompat r3 = r7.t
            int r3 = r3.getSystemWindowInsetBottom()
            int r2 = r2 + r3
            int r0 = r14 - r0
            int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r13)
            int r2 = r16 - r2
            int r2 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r15)
            r23 = r0
            r25 = r2
            goto L_0x00fb
        L_0x00f7:
            r23 = r32
            r25 = r33
        L_0x00fb:
            android.support.design.widget.CoordinatorLayout$Behavior r0 = r11.getBehavior()
            if (r0 == 0) goto L_0x0126
            r26 = 0
            r3 = r1
            r1 = r7
            r27 = r21
            r2 = r3
            r28 = r3
            r29 = r22
            r21 = 0
            r3 = r23
            r30 = r4
            r4 = r20
            r22 = r5
            r5 = r25
            r24 = r6
            r6 = r26
            boolean r0 = r0.onMeasureChild(r1, r2, r3, r4, r5, r6)
            if (r0 != 0) goto L_0x0123
            goto L_0x0134
        L_0x0123:
            r0 = r28
            goto L_0x0142
        L_0x0126:
            r28 = r1
            r30 = r4
            r24 = r6
            r27 = r21
            r29 = r22
            r21 = 0
            r22 = r5
        L_0x0134:
            r5 = 0
            r0 = r7
            r1 = r28
            r2 = r23
            r3 = r20
            r4 = r25
            r0.onMeasureChild(r1, r2, r3, r4, r5)
            goto L_0x0123
        L_0x0142:
            int r1 = r0.getMeasuredWidth()
            int r1 = r17 + r1
            int r2 = r11.leftMargin
            int r1 = r1 + r2
            int r2 = r11.rightMargin
            int r1 = r1 + r2
            r2 = r30
            int r1 = java.lang.Math.max(r2, r1)
            int r2 = r0.getMeasuredHeight()
            int r2 = r18 + r2
            int r3 = r11.topMargin
            int r2 = r2 + r3
            int r3 = r11.bottomMargin
            int r2 = r2 + r3
            r3 = r27
            int r2 = java.lang.Math.max(r3, r2)
            int r0 = r0.getMeasuredState()
            r11 = r29
            int r0 = android.view.View.combineMeasuredStates(r11, r0)
            r3 = r0
            r4 = r1
        L_0x0172:
            int r5 = r22 + 1
            r6 = r24
            goto L_0x0057
        L_0x0178:
            r11 = r3
            r3 = r2
            r2 = r4
            r0 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r0 = r0 & r11
            r1 = r32
            int r0 = android.view.View.resolveSizeAndState(r2, r1, r0)
            int r1 = r11 << 16
            r2 = r33
            int r1 = android.view.View.resolveSizeAndState(r3, r2, r1)
            r7.setMeasuredDimension(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.CoordinatorLayout.onMeasure(int, int):void");
    }

    private WindowInsetsCompat b(WindowInsetsCompat windowInsetsCompat) {
        if (windowInsetsCompat.isConsumed()) {
            return windowInsetsCompat;
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (ViewCompat.getFitsSystemWindows(childAt)) {
                Behavior behavior = ((LayoutParams) childAt.getLayoutParams()).getBehavior();
                if (behavior != null) {
                    windowInsetsCompat = behavior.onApplyWindowInsets(this, childAt, windowInsetsCompat);
                    if (windowInsetsCompat.isConsumed()) {
                        break;
                    }
                } else {
                    continue;
                }
            }
        }
        return windowInsetsCompat;
    }

    public void onLayoutChild(View view, int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (layoutParams.b()) {
            throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
        } else if (layoutParams.f != null) {
            a(view, layoutParams.f, i2);
        } else if (layoutParams.keyline >= 0) {
            a(view, layoutParams.keyline, i2);
        } else {
            b(view, i2);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int size = this.g.size();
        for (int i6 = 0; i6 < size; i6++) {
            View view = (View) this.g.get(i6);
            if (view.getVisibility() != 8) {
                Behavior behavior = ((LayoutParams) view.getLayoutParams()).getBehavior();
                if (behavior == null || !behavior.onLayoutChild(this, view, layoutDirection)) {
                    onLayoutChild(view, layoutDirection);
                }
            }
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.u && this.v != null) {
            int systemWindowInsetTop = this.t != null ? this.t.getSystemWindowInsetTop() : 0;
            if (systemWindowInsetTop > 0) {
                this.v.setBounds(0, 0, getWidth(), systemWindowInsetTop);
                this.v.draw(canvas);
            }
        }
    }

    public void setFitsSystemWindows(boolean z) {
        super.setFitsSystemWindows(z);
        f();
    }

    /* access modifiers changed from: 0000 */
    public void b(View view, Rect rect) {
        ((LayoutParams) view.getLayoutParams()).a(rect);
    }

    /* access modifiers changed from: 0000 */
    public void c(View view, Rect rect) {
        rect.set(((LayoutParams) view.getLayoutParams()).a());
    }

    /* access modifiers changed from: 0000 */
    public void a(View view, boolean z, Rect rect) {
        if (view.isLayoutRequested() || view.getVisibility() == 8) {
            rect.setEmpty();
            return;
        }
        if (z) {
            a(view, rect);
        } else {
            rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
    }

    private void a(View view, int i2, Rect rect, Rect rect2, LayoutParams layoutParams, int i3, int i4) {
        int i5;
        int i6;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(e(layoutParams.gravity), i2);
        int absoluteGravity2 = GravityCompat.getAbsoluteGravity(c(layoutParams.anchorGravity), i2);
        int i7 = absoluteGravity & 7;
        int i8 = absoluteGravity & 112;
        int i9 = absoluteGravity2 & 7;
        int i10 = absoluteGravity2 & 112;
        if (i9 == 1) {
            i5 = rect.left + (rect.width() / 2);
        } else if (i9 != 5) {
            i5 = rect.left;
        } else {
            i5 = rect.right;
        }
        if (i10 == 16) {
            i6 = rect.top + (rect.height() / 2);
        } else if (i10 != 80) {
            i6 = rect.top;
        } else {
            i6 = rect.bottom;
        }
        if (i7 == 1) {
            i5 -= i3 / 2;
        } else if (i7 != 5) {
            i5 -= i3;
        }
        if (i8 == 16) {
            i6 -= i4 / 2;
        } else if (i8 != 80) {
            i6 -= i4;
        }
        rect2.set(i5, i6, i3 + i5, i4 + i6);
    }

    private void a(LayoutParams layoutParams, Rect rect, int i2, int i3) {
        int width = getWidth();
        int height = getHeight();
        int max = Math.max(getPaddingLeft() + layoutParams.leftMargin, Math.min(rect.left, ((width - getPaddingRight()) - i2) - layoutParams.rightMargin));
        int max2 = Math.max(getPaddingTop() + layoutParams.topMargin, Math.min(rect.top, ((height - getPaddingBottom()) - i3) - layoutParams.bottomMargin));
        rect.set(max, max2, i2 + max, i3 + max2);
    }

    /* access modifiers changed from: 0000 */
    public void a(View view, int i2, Rect rect, Rect rect2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        a(view, i2, rect, rect2, layoutParams, measuredWidth, measuredHeight);
        a(layoutParams, rect2, measuredWidth, measuredHeight);
    }

    private void a(View view, View view2, int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect d2 = d();
        Rect d3 = d();
        try {
            a(view2, d2);
            a(view, i2, d2, d3);
            view.layout(d3.left, d3.top, d3.right, d3.bottom);
        } finally {
            a(d2);
            a(d3);
        }
    }

    private void a(View view, int i2, int i3) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int absoluteGravity = GravityCompat.getAbsoluteGravity(d(layoutParams.gravity), i3);
        int i4 = absoluteGravity & 7;
        int i5 = absoluteGravity & 112;
        int width = getWidth();
        int height = getHeight();
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        if (i3 == 1) {
            i2 = width - i2;
        }
        int b2 = b(i2) - measuredWidth;
        int i6 = 0;
        if (i4 == 1) {
            b2 += measuredWidth / 2;
        } else if (i4 == 5) {
            b2 += measuredWidth;
        }
        if (i5 == 16) {
            i6 = 0 + (measuredHeight / 2);
        } else if (i5 == 80) {
            i6 = measuredHeight + 0;
        }
        int max = Math.max(getPaddingLeft() + layoutParams.leftMargin, Math.min(b2, ((width - getPaddingRight()) - measuredWidth) - layoutParams.rightMargin));
        int max2 = Math.max(getPaddingTop() + layoutParams.topMargin, Math.min(i6, ((height - getPaddingBottom()) - measuredHeight) - layoutParams.bottomMargin));
        view.layout(max, max2, measuredWidth + max, measuredHeight + max2);
    }

    private void b(View view, int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect d2 = d();
        d2.set(getPaddingLeft() + layoutParams.leftMargin, getPaddingTop() + layoutParams.topMargin, (getWidth() - getPaddingRight()) - layoutParams.rightMargin, (getHeight() - getPaddingBottom()) - layoutParams.bottomMargin);
        if (this.t != null && ViewCompat.getFitsSystemWindows(this) && !ViewCompat.getFitsSystemWindows(view)) {
            d2.left += this.t.getSystemWindowInsetLeft();
            d2.top += this.t.getSystemWindowInsetTop();
            d2.right -= this.t.getSystemWindowInsetRight();
            d2.bottom -= this.t.getSystemWindowInsetBottom();
        }
        Rect d3 = d();
        GravityCompat.apply(c(layoutParams.gravity), view.getMeasuredWidth(), view.getMeasuredHeight(), d2, d3, i2);
        view.layout(d3.left, d3.top, d3.right, d3.bottom);
        a(d2);
        a(d3);
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (layoutParams.a != null) {
            float scrimOpacity = layoutParams.a.getScrimOpacity(this, view);
            if (scrimOpacity > BitmapDescriptorFactory.HUE_RED) {
                if (this.l == null) {
                    this.l = new Paint();
                }
                this.l.setColor(layoutParams.a.getScrimColor(this, view));
                this.l.setAlpha(MathUtils.clamp(Math.round(scrimOpacity * 255.0f), 0, 255));
                int save = canvas.save();
                if (view.isOpaque()) {
                    canvas.clipRect((float) view.getLeft(), (float) view.getTop(), (float) view.getRight(), (float) view.getBottom(), Op.DIFFERENCE);
                }
                canvas.drawRect((float) getPaddingLeft(), (float) getPaddingTop(), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - getPaddingBottom()), this.l);
                canvas.restoreToCount(save);
            }
        }
        return super.drawChild(canvas, view, j2);
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i2) {
        boolean z;
        int i3 = i2;
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int size = this.g.size();
        Rect d2 = d();
        Rect d3 = d();
        Rect d4 = d();
        for (int i4 = 0; i4 < size; i4++) {
            View view = (View) this.g.get(i4);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (i3 != 0 || view.getVisibility() != 8) {
                for (int i5 = 0; i5 < i4; i5++) {
                    if (layoutParams.g == ((View) this.g.get(i5))) {
                        a(view, layoutDirection);
                    }
                }
                a(view, true, d3);
                if (layoutParams.insetEdge != 0 && !d3.isEmpty()) {
                    int absoluteGravity = GravityCompat.getAbsoluteGravity(layoutParams.insetEdge, layoutDirection);
                    int i6 = absoluteGravity & 112;
                    if (i6 == 48) {
                        d2.top = Math.max(d2.top, d3.bottom);
                    } else if (i6 == 80) {
                        d2.bottom = Math.max(d2.bottom, getHeight() - d3.top);
                    }
                    int i7 = absoluteGravity & 7;
                    if (i7 == 3) {
                        d2.left = Math.max(d2.left, d3.right);
                    } else if (i7 == 5) {
                        d2.right = Math.max(d2.right, getWidth() - d3.left);
                    }
                }
                if (layoutParams.dodgeInsetEdges != 0 && view.getVisibility() == 0) {
                    a(view, d2, layoutDirection);
                }
                if (i3 != 2) {
                    c(view, d4);
                    if (!d4.equals(d3)) {
                        b(view, d3);
                    }
                }
                for (int i8 = i4 + 1; i8 < size; i8++) {
                    View view2 = (View) this.g.get(i8);
                    LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams();
                    Behavior behavior = layoutParams2.getBehavior();
                    if (behavior != null && behavior.layoutDependsOn(this, view2, view)) {
                        if (i3 != 0 || !layoutParams2.e()) {
                            if (i3 != 2) {
                                z = behavior.onDependentViewChanged(this, view2, view);
                            } else {
                                behavior.onDependentViewRemoved(this, view2, view);
                                z = true;
                            }
                            if (i3 == 1) {
                                layoutParams2.a(z);
                            }
                        } else {
                            layoutParams2.f();
                        }
                    }
                }
            }
        }
        a(d2);
        a(d3);
        a(d4);
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a8  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00ca  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00e3  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00fd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(android.view.View r9, android.graphics.Rect r10, int r11) {
        /*
            r8 = this;
            boolean r0 = android.support.v4.view.ViewCompat.isLaidOut(r9)
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            int r0 = r9.getWidth()
            if (r0 <= 0) goto L_0x0104
            int r0 = r9.getHeight()
            if (r0 > 0) goto L_0x0015
            goto L_0x0104
        L_0x0015:
            android.view.ViewGroup$LayoutParams r0 = r9.getLayoutParams()
            android.support.design.widget.CoordinatorLayout$LayoutParams r0 = (android.support.design.widget.CoordinatorLayout.LayoutParams) r0
            android.support.design.widget.CoordinatorLayout$Behavior r1 = r0.getBehavior()
            android.graphics.Rect r2 = d()
            android.graphics.Rect r3 = d()
            int r4 = r9.getLeft()
            int r5 = r9.getTop()
            int r6 = r9.getRight()
            int r7 = r9.getBottom()
            r3.set(r4, r5, r6, r7)
            if (r1 == 0) goto L_0x006f
            boolean r1 = r1.getInsetDodgeRect(r8, r9, r2)
            if (r1 == 0) goto L_0x006f
            boolean r1 = r3.contains(r2)
            if (r1 != 0) goto L_0x0072
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Rect should be within the child's bounds. Rect:"
            r10.append(r11)
            java.lang.String r11 = r2.toShortString()
            r10.append(r11)
            java.lang.String r11 = " | Bounds:"
            r10.append(r11)
            java.lang.String r11 = r3.toShortString()
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            r9.<init>(r10)
            throw r9
        L_0x006f:
            r2.set(r3)
        L_0x0072:
            a(r3)
            boolean r1 = r2.isEmpty()
            if (r1 == 0) goto L_0x007f
            a(r2)
            return
        L_0x007f:
            int r1 = r0.dodgeInsetEdges
            int r11 = android.support.v4.view.GravityCompat.getAbsoluteGravity(r1, r11)
            r1 = r11 & 48
            r3 = 48
            r4 = 1
            r5 = 0
            if (r1 != r3) goto L_0x00a1
            int r1 = r2.top
            int r3 = r0.topMargin
            int r1 = r1 - r3
            int r3 = r0.e
            int r1 = r1 - r3
            int r3 = r10.top
            if (r1 >= r3) goto L_0x00a1
            int r3 = r10.top
            int r3 = r3 - r1
            r8.d(r9, r3)
            r1 = 1
            goto L_0x00a2
        L_0x00a1:
            r1 = 0
        L_0x00a2:
            r3 = r11 & 80
            r6 = 80
            if (r3 != r6) goto L_0x00c0
            int r3 = r8.getHeight()
            int r6 = r2.bottom
            int r3 = r3 - r6
            int r6 = r0.bottomMargin
            int r3 = r3 - r6
            int r6 = r0.e
            int r3 = r3 + r6
            int r6 = r10.bottom
            if (r3 >= r6) goto L_0x00c0
            int r1 = r10.bottom
            int r3 = r3 - r1
            r8.d(r9, r3)
            r1 = 1
        L_0x00c0:
            if (r1 != 0) goto L_0x00c5
            r8.d(r9, r5)
        L_0x00c5:
            r1 = r11 & 3
            r3 = 3
            if (r1 != r3) goto L_0x00de
            int r1 = r2.left
            int r3 = r0.leftMargin
            int r1 = r1 - r3
            int r3 = r0.d
            int r1 = r1 - r3
            int r3 = r10.left
            if (r1 >= r3) goto L_0x00de
            int r3 = r10.left
            int r3 = r3 - r1
            r8.c(r9, r3)
            r1 = 1
            goto L_0x00df
        L_0x00de:
            r1 = 0
        L_0x00df:
            r3 = 5
            r11 = r11 & r3
            if (r11 != r3) goto L_0x00fb
            int r11 = r8.getWidth()
            int r3 = r2.right
            int r11 = r11 - r3
            int r3 = r0.rightMargin
            int r11 = r11 - r3
            int r0 = r0.d
            int r11 = r11 + r0
            int r0 = r10.right
            if (r11 >= r0) goto L_0x00fb
            int r10 = r10.right
            int r11 = r11 - r10
            r8.c(r9, r11)
            r1 = 1
        L_0x00fb:
            if (r1 != 0) goto L_0x0100
            r8.c(r9, r5)
        L_0x0100:
            a(r2)
            return
        L_0x0104:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.CoordinatorLayout.a(android.view.View, android.graphics.Rect, int):void");
    }

    private void c(View view, int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (layoutParams.d != i2) {
            ViewCompat.offsetLeftAndRight(view, i2 - layoutParams.d);
            layoutParams.d = i2;
        }
    }

    private void d(View view, int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (layoutParams.e != i2) {
            ViewCompat.offsetTopAndBottom(view, i2 - layoutParams.e);
            layoutParams.e = i2;
        }
    }

    public void dispatchDependentViewsChanged(View view) {
        List incomingEdges = this.h.getIncomingEdges(view);
        if (incomingEdges != null && !incomingEdges.isEmpty()) {
            for (int i2 = 0; i2 < incomingEdges.size(); i2++) {
                View view2 = (View) incomingEdges.get(i2);
                Behavior behavior = ((LayoutParams) view2.getLayoutParams()).getBehavior();
                if (behavior != null) {
                    behavior.onDependentViewChanged(this, view2, view);
                }
            }
        }
    }

    @NonNull
    public List<View> getDependencies(@NonNull View view) {
        List outgoingEdges = this.h.getOutgoingEdges(view);
        this.j.clear();
        if (outgoingEdges != null) {
            this.j.addAll(outgoingEdges);
        }
        return this.j;
    }

    @NonNull
    public List<View> getDependents(@NonNull View view) {
        List incomingEdges = this.h.getIncomingEdges(view);
        this.j.clear();
        if (incomingEdges != null) {
            this.j.addAll(incomingEdges);
        }
        return this.j;
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public final List<View> getDependencySortedChildren() {
        e();
        return Collections.unmodifiableList(this.g);
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        int childCount = getChildCount();
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            } else if (b(getChildAt(i2))) {
                z = true;
                break;
            } else {
                i2++;
            }
        }
        if (z == this.s) {
            return;
        }
        if (z) {
            b();
        } else {
            c();
        }
    }

    private boolean b(View view) {
        return this.h.hasOutgoingEdges(view);
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        if (this.n) {
            if (this.r == null) {
                this.r = new OnPreDrawListener();
            }
            getViewTreeObserver().addOnPreDrawListener(this.r);
        }
        this.s = true;
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        if (this.n && this.r != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.r);
        }
        this.s = false;
    }

    /* access modifiers changed from: 0000 */
    public void a(View view, int i2) {
        View view2 = view;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (layoutParams.f != null) {
            Rect d2 = d();
            Rect d3 = d();
            Rect d4 = d();
            a(layoutParams.f, d2);
            boolean z = false;
            a(view2, false, d3);
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            int i3 = measuredHeight;
            a(view2, i2, d2, d4, layoutParams, measuredWidth, measuredHeight);
            if (!(d4.left == d3.left && d4.top == d3.top)) {
                z = true;
            }
            a(layoutParams, d4, measuredWidth, i3);
            int i4 = d4.left - d3.left;
            int i5 = d4.top - d3.top;
            if (i4 != 0) {
                ViewCompat.offsetLeftAndRight(view2, i4);
            }
            if (i5 != 0) {
                ViewCompat.offsetTopAndBottom(view2, i5);
            }
            if (z) {
                Behavior behavior = layoutParams.getBehavior();
                if (behavior != null) {
                    behavior.onDependentViewChanged(this, view2, layoutParams.f);
                }
            }
            a(d2);
            a(d3);
            a(d4);
        }
    }

    public boolean isPointInChildBounds(View view, int i2, int i3) {
        Rect d2 = d();
        a(view, d2);
        try {
            return d2.contains(i2, i3);
        } finally {
            a(d2);
        }
    }

    public boolean doViewsOverlap(View view, View view2) {
        boolean z = false;
        if (view.getVisibility() != 0 || view2.getVisibility() != 0) {
            return false;
        }
        Rect d2 = d();
        a(view, view.getParent() != this, d2);
        Rect d3 = d();
        a(view2, view2.getParent() != this, d3);
        try {
            if (d2.left <= d3.right && d2.top <= d3.bottom && d2.right >= d3.left && d2.bottom >= d3.top) {
                z = true;
            }
            return z;
        } finally {
            a(d2);
            a(d3);
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        if (layoutParams instanceof MarginLayoutParams) {
            return new LayoutParams((MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public boolean onStartNestedScroll(View view, View view2, int i2) {
        return onStartNestedScroll(view, view2, i2, 0);
    }

    public boolean onStartNestedScroll(View view, View view2, int i2, int i3) {
        int i4 = i3;
        int childCount = getChildCount();
        boolean z = false;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                Behavior behavior = layoutParams.getBehavior();
                if (behavior != null) {
                    boolean onStartNestedScroll = behavior.onStartNestedScroll(this, childAt, view, view2, i2, i4);
                    boolean z2 = z | onStartNestedScroll;
                    layoutParams.a(i4, onStartNestedScroll);
                    z = z2;
                } else {
                    layoutParams.a(i4, false);
                }
            }
        }
        return z;
    }

    public void onNestedScrollAccepted(View view, View view2, int i2) {
        onNestedScrollAccepted(view, view2, i2, 0);
    }

    public void onNestedScrollAccepted(View view, View view2, int i2, int i3) {
        this.x.onNestedScrollAccepted(view, view2, i2, i3);
        this.q = view2;
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (layoutParams.b(i3)) {
                Behavior behavior = layoutParams.getBehavior();
                if (behavior != null) {
                    behavior.onNestedScrollAccepted(this, childAt, view, view2, i2, i3);
                }
            }
        }
    }

    public void onStopNestedScroll(View view) {
        onStopNestedScroll(view, 0);
    }

    public void onStopNestedScroll(View view, int i2) {
        this.x.onStopNestedScroll(view, i2);
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (layoutParams.b(i2)) {
                Behavior behavior = layoutParams.getBehavior();
                if (behavior != null) {
                    behavior.onStopNestedScroll(this, childAt, view, i2);
                }
                layoutParams.a(i2);
                layoutParams.f();
            }
        }
        this.q = null;
    }

    public void onNestedScroll(View view, int i2, int i3, int i4, int i5) {
        onNestedScroll(view, i2, i3, i4, i5, 0);
    }

    public void onNestedScroll(View view, int i2, int i3, int i4, int i5, int i6) {
        int childCount = getChildCount();
        boolean z = false;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() == 8) {
                int i8 = i6;
            } else {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int i9 = i6;
                if (layoutParams.b(i9)) {
                    Behavior behavior = layoutParams.getBehavior();
                    if (behavior != null) {
                        behavior.onNestedScroll(this, childAt, view, i2, i3, i4, i5, i9);
                        z = true;
                    }
                }
            }
        }
        if (z) {
            a(1);
        }
    }

    public void onNestedPreScroll(View view, int i2, int i3, int[] iArr) {
        onNestedPreScroll(view, i2, i3, iArr, 0);
    }

    public void onNestedPreScroll(View view, int i2, int i3, int[] iArr, int i4) {
        int i5;
        int i6;
        int childCount = getChildCount();
        boolean z = false;
        int i7 = 0;
        int i8 = 0;
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt = getChildAt(i9);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.b(i4)) {
                    Behavior behavior = layoutParams.getBehavior();
                    if (behavior != null) {
                        int[] iArr2 = this.k;
                        this.k[1] = 0;
                        iArr2[0] = 0;
                        behavior.onNestedPreScroll(this, childAt, view, i2, i3, this.k, i4);
                        if (i2 > 0) {
                            i5 = Math.max(i7, this.k[0]);
                        } else {
                            i5 = Math.min(i7, this.k[0]);
                        }
                        if (i3 > 0) {
                            i6 = Math.max(i8, this.k[1]);
                        } else {
                            i6 = Math.min(i8, this.k[1]);
                        }
                        i7 = i5;
                        i8 = i6;
                        z = true;
                    }
                }
            }
        }
        iArr[0] = i7;
        iArr[1] = i8;
        if (z) {
            a(1);
        }
    }

    public boolean onNestedFling(View view, float f2, float f3, boolean z) {
        int childCount = getChildCount();
        boolean z2 = false;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.b(0)) {
                    Behavior behavior = layoutParams.getBehavior();
                    if (behavior != null) {
                        z2 |= behavior.onNestedFling(this, childAt, view, f2, f3, z);
                    }
                }
            }
        }
        if (z2) {
            a(1);
        }
        return z2;
    }

    public boolean onNestedPreFling(View view, float f2, float f3) {
        int childCount = getChildCount();
        boolean z = false;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.b(0)) {
                    Behavior behavior = layoutParams.getBehavior();
                    if (behavior != null) {
                        z |= behavior.onNestedPreFling(this, childAt, view, f2, f3);
                    }
                }
            }
        }
        return z;
    }

    public int getNestedScrollAxes() {
        return this.x.getNestedScrollAxes();
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        SparseArray<Parcelable> sparseArray = savedState.a;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            int id2 = childAt.getId();
            Behavior behavior = a(childAt).getBehavior();
            if (!(id2 == -1 || behavior == null)) {
                Parcelable parcelable2 = (Parcelable) sparseArray.get(id2);
                if (parcelable2 != null) {
                    behavior.onRestoreInstanceState(this, childAt, parcelable2);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SparseArray<Parcelable> sparseArray = new SparseArray<>();
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            int id2 = childAt.getId();
            Behavior behavior = ((LayoutParams) childAt.getLayoutParams()).getBehavior();
            if (!(id2 == -1 || behavior == null)) {
                Parcelable onSaveInstanceState = behavior.onSaveInstanceState(this, childAt);
                if (onSaveInstanceState != null) {
                    sparseArray.append(id2, onSaveInstanceState);
                }
            }
        }
        savedState.a = sparseArray;
        return savedState;
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        Behavior behavior = ((LayoutParams) view.getLayoutParams()).getBehavior();
        if (behavior == null || !behavior.onRequestChildRectangleOnScreen(this, view, rect, z)) {
            return super.requestChildRectangleOnScreen(view, rect, z);
        }
        return true;
    }

    private void f() {
        if (VERSION.SDK_INT >= 21) {
            if (ViewCompat.getFitsSystemWindows(this)) {
                if (this.w == null) {
                    this.w = new OnApplyWindowInsetsListener() {
                        public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                            return CoordinatorLayout.this.a(windowInsetsCompat);
                        }
                    };
                }
                ViewCompat.setOnApplyWindowInsetsListener(this, this.w);
                setSystemUiVisibility(1280);
            } else {
                ViewCompat.setOnApplyWindowInsetsListener(this, null);
            }
        }
    }
}
