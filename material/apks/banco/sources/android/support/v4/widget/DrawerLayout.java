package android.support.v4.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import cz.msebera.android.httpclient.HttpStatus;
import java.util.ArrayList;
import java.util.List;

public class DrawerLayout extends ViewGroup {
    public static final int LOCK_MODE_LOCKED_CLOSED = 1;
    public static final int LOCK_MODE_LOCKED_OPEN = 2;
    public static final int LOCK_MODE_UNDEFINED = 3;
    public static final int LOCK_MODE_UNLOCKED = 0;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    static final int[] a = {16842931};
    static final boolean b = (VERSION.SDK_INT >= 19);
    private static final int[] c = {16843828};
    private static final boolean d;
    private float A;
    private Drawable B;
    private Drawable C;
    private Drawable D;
    private CharSequence E;
    private CharSequence F;
    private Object G;
    private boolean H;
    private Drawable I;
    private Drawable J;
    private Drawable K;
    private Drawable L;
    private final ArrayList<View> M;
    private final ChildAccessibilityDelegate e;
    private float f;
    private int g;
    private int h;
    private float i;
    private Paint j;
    private final ViewDragHelper k;
    private final ViewDragHelper l;
    private final ViewDragCallback m;
    private final ViewDragCallback n;
    private int o;
    private boolean p;
    private boolean q;
    private int r;
    private int s;
    private int t;
    private int u;
    private boolean v;
    private boolean w;
    @Nullable
    private DrawerListener x;
    private List<DrawerListener> y;
    private float z;

    class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private final Rect c = new Rect();

        AccessibilityDelegate() {
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (DrawerLayout.b) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            } else {
                AccessibilityNodeInfoCompat obtain = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat);
                super.onInitializeAccessibilityNodeInfo(view, obtain);
                accessibilityNodeInfoCompat.setSource(view);
                ViewParent parentForAccessibility = ViewCompat.getParentForAccessibility(view);
                if (parentForAccessibility instanceof View) {
                    accessibilityNodeInfoCompat.setParent((View) parentForAccessibility);
                }
                a(accessibilityNodeInfoCompat, obtain);
                obtain.recycle();
                a(accessibilityNodeInfoCompat, (ViewGroup) view);
            }
            accessibilityNodeInfoCompat.setClassName(DrawerLayout.class.getName());
            accessibilityNodeInfoCompat.setFocusable(false);
            accessibilityNodeInfoCompat.setFocused(false);
            accessibilityNodeInfoCompat.removeAction(AccessibilityActionCompat.ACTION_FOCUS);
            accessibilityNodeInfoCompat.removeAction(AccessibilityActionCompat.ACTION_CLEAR_FOCUS);
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(DrawerLayout.class.getName());
        }

        public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            if (accessibilityEvent.getEventType() != 32) {
                return super.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
            }
            List text = accessibilityEvent.getText();
            View b = DrawerLayout.this.b();
            if (b != null) {
                CharSequence drawerTitle = DrawerLayout.this.getDrawerTitle(DrawerLayout.this.d(b));
                if (drawerTitle != null) {
                    text.add(drawerTitle);
                }
            }
            return true;
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (DrawerLayout.b || DrawerLayout.g(view)) {
                return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            }
            return false;
        }

        private void a(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, ViewGroup viewGroup) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (DrawerLayout.g(childAt)) {
                    accessibilityNodeInfoCompat.addChild(childAt);
                }
            }
        }

        private void a(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2) {
            Rect rect = this.c;
            accessibilityNodeInfoCompat2.getBoundsInParent(rect);
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            accessibilityNodeInfoCompat2.getBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setVisibleToUser(accessibilityNodeInfoCompat2.isVisibleToUser());
            accessibilityNodeInfoCompat.setPackageName(accessibilityNodeInfoCompat2.getPackageName());
            accessibilityNodeInfoCompat.setClassName(accessibilityNodeInfoCompat2.getClassName());
            accessibilityNodeInfoCompat.setContentDescription(accessibilityNodeInfoCompat2.getContentDescription());
            accessibilityNodeInfoCompat.setEnabled(accessibilityNodeInfoCompat2.isEnabled());
            accessibilityNodeInfoCompat.setClickable(accessibilityNodeInfoCompat2.isClickable());
            accessibilityNodeInfoCompat.setFocusable(accessibilityNodeInfoCompat2.isFocusable());
            accessibilityNodeInfoCompat.setFocused(accessibilityNodeInfoCompat2.isFocused());
            accessibilityNodeInfoCompat.setAccessibilityFocused(accessibilityNodeInfoCompat2.isAccessibilityFocused());
            accessibilityNodeInfoCompat.setSelected(accessibilityNodeInfoCompat2.isSelected());
            accessibilityNodeInfoCompat.setLongClickable(accessibilityNodeInfoCompat2.isLongClickable());
            accessibilityNodeInfoCompat.addAction(accessibilityNodeInfoCompat2.getActions());
        }
    }

    static final class ChildAccessibilityDelegate extends AccessibilityDelegateCompat {
        ChildAccessibilityDelegate() {
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            if (!DrawerLayout.g(view)) {
                accessibilityNodeInfoCompat.setParent(null);
            }
        }
    }

    public interface DrawerListener {
        void onDrawerClosed(@NonNull View view);

        void onDrawerOpened(@NonNull View view);

        void onDrawerSlide(@NonNull View view, float f);

        void onDrawerStateChanged(int i);
    }

    public static class LayoutParams extends MarginLayoutParams {
        float a;
        boolean b;
        int c;
        public int gravity;

        public LayoutParams(@NonNull Context context, @Nullable AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = 0;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, DrawerLayout.a);
            this.gravity = obtainStyledAttributes.getInt(0, 0);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.gravity = 0;
        }

        public LayoutParams(int i, int i2, int i3) {
            this(i, i2);
            this.gravity = i3;
        }

        public LayoutParams(@NonNull LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = 0;
            this.gravity = layoutParams.gravity;
        }

        public LayoutParams(@NonNull android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = 0;
        }

        public LayoutParams(@NonNull MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.gravity = 0;
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
        int a = 0;
        int b;
        int c;
        int d;
        int e;

        public SavedState(@NonNull Parcel parcel, @Nullable ClassLoader classLoader) {
            super(parcel, classLoader);
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            this.c = parcel.readInt();
            this.d = parcel.readInt();
            this.e = parcel.readInt();
        }

        public SavedState(@NonNull Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeInt(this.c);
            parcel.writeInt(this.d);
            parcel.writeInt(this.e);
        }
    }

    public static abstract class SimpleDrawerListener implements DrawerListener {
        public void onDrawerClosed(View view) {
        }

        public void onDrawerOpened(View view) {
        }

        public void onDrawerSlide(View view, float f) {
        }

        public void onDrawerStateChanged(int i) {
        }
    }

    class ViewDragCallback extends Callback {
        private final int b;
        private ViewDragHelper c;
        private final Runnable d = new Runnable() {
            public void run() {
                ViewDragCallback.this.b();
            }
        };

        public boolean onEdgeLock(int i) {
            return false;
        }

        ViewDragCallback(int i) {
            this.b = i;
        }

        public void a(ViewDragHelper viewDragHelper) {
            this.c = viewDragHelper;
        }

        public void a() {
            DrawerLayout.this.removeCallbacks(this.d);
        }

        public boolean tryCaptureView(View view, int i) {
            return DrawerLayout.this.f(view) && DrawerLayout.this.a(view, this.b) && DrawerLayout.this.getDrawerLockMode(view) == 0;
        }

        public void onViewDragStateChanged(int i) {
            DrawerLayout.this.a(this.b, i, this.c.getCapturedView());
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            float f;
            int width = view.getWidth();
            if (DrawerLayout.this.a(view, 3)) {
                f = ((float) (i + width)) / ((float) width);
            } else {
                f = ((float) (DrawerLayout.this.getWidth() - i)) / ((float) width);
            }
            DrawerLayout.this.b(view, f);
            view.setVisibility(f == BitmapDescriptorFactory.HUE_RED ? 4 : 0);
            DrawerLayout.this.invalidate();
        }

        public void onViewCaptured(View view, int i) {
            ((LayoutParams) view.getLayoutParams()).b = false;
            c();
        }

        private void c() {
            int i = 3;
            if (this.b == 3) {
                i = 5;
            }
            View a2 = DrawerLayout.this.a(i);
            if (a2 != null) {
                DrawerLayout.this.closeDrawer(a2);
            }
        }

        public void onViewReleased(View view, float f, float f2) {
            int i;
            float c2 = DrawerLayout.this.c(view);
            int width = view.getWidth();
            if (DrawerLayout.this.a(view, 3)) {
                i = (f > BitmapDescriptorFactory.HUE_RED || (f == BitmapDescriptorFactory.HUE_RED && c2 > 0.5f)) ? 0 : -width;
            } else {
                int width2 = DrawerLayout.this.getWidth();
                if (f < BitmapDescriptorFactory.HUE_RED || (f == BitmapDescriptorFactory.HUE_RED && c2 > 0.5f)) {
                    width2 -= width;
                }
                i = width2;
            }
            this.c.settleCapturedViewAt(i, view.getTop());
            DrawerLayout.this.invalidate();
        }

        public void onEdgeTouched(int i, int i2) {
            DrawerLayout.this.postDelayed(this.d, 160);
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            View view;
            int i;
            int edgeSize = this.c.getEdgeSize();
            int i2 = 0;
            boolean z = this.b == 3;
            if (z) {
                view = DrawerLayout.this.a(3);
                if (view != null) {
                    i2 = -view.getWidth();
                }
                i = i2 + edgeSize;
            } else {
                view = DrawerLayout.this.a(5);
                i = DrawerLayout.this.getWidth() - edgeSize;
            }
            if (view == null) {
                return;
            }
            if (((z && view.getLeft() < i) || (!z && view.getLeft() > i)) && DrawerLayout.this.getDrawerLockMode(view) == 0) {
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                this.c.smoothSlideViewTo(view, i, view.getTop());
                layoutParams.b = true;
                DrawerLayout.this.invalidate();
                c();
                DrawerLayout.this.c();
            }
        }

        public void onEdgeDragStarted(int i, int i2) {
            View view;
            if ((i & 1) == 1) {
                view = DrawerLayout.this.a(3);
            } else {
                view = DrawerLayout.this.a(5);
            }
            if (view != null && DrawerLayout.this.getDrawerLockMode(view) == 0) {
                this.c.captureChildView(view, i2);
            }
        }

        public int getViewHorizontalDragRange(View view) {
            if (DrawerLayout.this.f(view)) {
                return view.getWidth();
            }
            return 0;
        }

        public int clampViewPositionHorizontal(View view, int i, int i2) {
            if (DrawerLayout.this.a(view, 3)) {
                return Math.max(-view.getWidth(), Math.min(i, 0));
            }
            int width = DrawerLayout.this.getWidth();
            return Math.max(width - view.getWidth(), Math.min(i, width));
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            return view.getTop();
        }
    }

    static {
        boolean z2 = true;
        if (VERSION.SDK_INT < 21) {
            z2 = false;
        }
        d = z2;
    }

    public DrawerLayout(@NonNull Context context) {
        this(context, null);
    }

    public DrawerLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DrawerLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.e = new ChildAccessibilityDelegate();
        this.h = -1728053248;
        this.j = new Paint();
        this.q = true;
        this.r = 3;
        this.s = 3;
        this.t = 3;
        this.u = 3;
        this.I = null;
        this.J = null;
        this.K = null;
        this.L = null;
        setDescendantFocusability(262144);
        float f2 = getResources().getDisplayMetrics().density;
        this.g = (int) ((64.0f * f2) + 0.5f);
        float f3 = 400.0f * f2;
        this.m = new ViewDragCallback(3);
        this.n = new ViewDragCallback(5);
        this.k = ViewDragHelper.create(this, 1.0f, this.m);
        this.k.setEdgeTrackingEnabled(1);
        this.k.setMinVelocity(f3);
        this.m.a(this.k);
        this.l = ViewDragHelper.create(this, 1.0f, this.n);
        this.l.setEdgeTrackingEnabled(2);
        this.l.setMinVelocity(f3);
        this.n.a(this.l);
        setFocusableInTouchMode(true);
        ViewCompat.setImportantForAccessibility(this, 1);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
        setMotionEventSplittingEnabled(false);
        if (ViewCompat.getFitsSystemWindows(this)) {
            if (VERSION.SDK_INT >= 21) {
                setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {
                    @TargetApi(21)
                    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                        ((DrawerLayout) view).setChildInsets(windowInsets, windowInsets.getSystemWindowInsetTop() > 0);
                        return windowInsets.consumeSystemWindowInsets();
                    }
                });
                setSystemUiVisibility(1280);
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(c);
                try {
                    this.B = obtainStyledAttributes.getDrawable(0);
                } finally {
                    obtainStyledAttributes.recycle();
                }
            } else {
                this.B = null;
            }
        }
        this.f = f2 * 10.0f;
        this.M = new ArrayList<>();
    }

    public void setDrawerElevation(float f2) {
        this.f = f2;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (f(childAt)) {
                ViewCompat.setElevation(childAt, this.f);
            }
        }
    }

    public float getDrawerElevation() {
        return d ? this.f : BitmapDescriptorFactory.HUE_RED;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setChildInsets(Object obj, boolean z2) {
        this.G = obj;
        this.H = z2;
        setWillNotDraw(!z2 && getBackground() == null);
        requestLayout();
    }

    public void setDrawerShadow(Drawable drawable, int i2) {
        if (!d) {
            if ((i2 & GravityCompat.START) == 8388611) {
                this.I = drawable;
            } else if ((i2 & GravityCompat.END) == 8388613) {
                this.J = drawable;
            } else if ((i2 & 3) == 3) {
                this.K = drawable;
            } else if ((i2 & 5) == 5) {
                this.L = drawable;
            } else {
                return;
            }
            d();
            invalidate();
        }
    }

    public void setDrawerShadow(@DrawableRes int i2, int i3) {
        setDrawerShadow(ContextCompat.getDrawable(getContext(), i2), i3);
    }

    public void setScrimColor(@ColorInt int i2) {
        this.h = i2;
        invalidate();
    }

    @Deprecated
    public void setDrawerListener(DrawerListener drawerListener) {
        if (this.x != null) {
            removeDrawerListener(this.x);
        }
        if (drawerListener != null) {
            addDrawerListener(drawerListener);
        }
        this.x = drawerListener;
    }

    public void addDrawerListener(@NonNull DrawerListener drawerListener) {
        if (drawerListener != null) {
            if (this.y == null) {
                this.y = new ArrayList();
            }
            this.y.add(drawerListener);
        }
    }

    public void removeDrawerListener(@NonNull DrawerListener drawerListener) {
        if (drawerListener != null && this.y != null) {
            this.y.remove(drawerListener);
        }
    }

    public void setDrawerLockMode(int i2) {
        setDrawerLockMode(i2, 3);
        setDrawerLockMode(i2, 5);
    }

    public void setDrawerLockMode(int i2, int i3) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i3, ViewCompat.getLayoutDirection(this));
        if (i3 == 3) {
            this.r = i2;
        } else if (i3 == 5) {
            this.s = i2;
        } else if (i3 == 8388611) {
            this.t = i2;
        } else if (i3 == 8388613) {
            this.u = i2;
        }
        if (i2 != 0) {
            (absoluteGravity == 3 ? this.k : this.l).cancel();
        }
        switch (i2) {
            case 1:
                View a2 = a(absoluteGravity);
                if (a2 != null) {
                    closeDrawer(a2);
                    return;
                }
                return;
            case 2:
                View a3 = a(absoluteGravity);
                if (a3 != null) {
                    openDrawer(a3);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void setDrawerLockMode(int i2, @NonNull View view) {
        if (!f(view)) {
            StringBuilder sb = new StringBuilder();
            sb.append("View ");
            sb.append(view);
            sb.append(" is not a ");
            sb.append("drawer with appropriate layout_gravity");
            throw new IllegalArgumentException(sb.toString());
        }
        setDrawerLockMode(i2, ((LayoutParams) view.getLayoutParams()).gravity);
    }

    public int getDrawerLockMode(int i2) {
        int i3;
        int i4;
        int i5;
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        if (i2 != 3) {
            if (i2 != 5) {
                if (i2 != 8388611) {
                    if (i2 == 8388613) {
                        if (this.u != 3) {
                            return this.u;
                        }
                        if (layoutDirection == 0) {
                            i5 = this.s;
                        } else {
                            i5 = this.r;
                        }
                        if (i5 != 3) {
                            return i5;
                        }
                    }
                } else if (this.t != 3) {
                    return this.t;
                } else {
                    if (layoutDirection == 0) {
                        i4 = this.r;
                    } else {
                        i4 = this.s;
                    }
                    if (i4 != 3) {
                        return i4;
                    }
                }
            } else if (this.s != 3) {
                return this.s;
            } else {
                if (layoutDirection == 0) {
                    i3 = this.u;
                } else {
                    i3 = this.t;
                }
                if (i3 != 3) {
                    return i3;
                }
            }
        } else if (this.r != 3) {
            return this.r;
        } else {
            int i6 = layoutDirection == 0 ? this.t : this.u;
            if (i6 != 3) {
                return i6;
            }
        }
        return 0;
    }

    public int getDrawerLockMode(@NonNull View view) {
        if (f(view)) {
            return getDrawerLockMode(((LayoutParams) view.getLayoutParams()).gravity);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("View ");
        sb.append(view);
        sb.append(" is not a drawer");
        throw new IllegalArgumentException(sb.toString());
    }

    public void setDrawerTitle(int i2, @Nullable CharSequence charSequence) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i2, ViewCompat.getLayoutDirection(this));
        if (absoluteGravity == 3) {
            this.E = charSequence;
        } else if (absoluteGravity == 5) {
            this.F = charSequence;
        }
    }

    @Nullable
    public CharSequence getDrawerTitle(int i2) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i2, ViewCompat.getLayoutDirection(this));
        if (absoluteGravity == 3) {
            return this.E;
        }
        if (absoluteGravity == 5) {
            return this.F;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, int i3, View view) {
        int viewDragState = this.k.getViewDragState();
        int viewDragState2 = this.l.getViewDragState();
        int i4 = 2;
        if (viewDragState == 1 || viewDragState2 == 1) {
            i4 = 1;
        } else if (!(viewDragState == 2 || viewDragState2 == 2)) {
            i4 = 0;
        }
        if (view != null && i3 == 0) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (layoutParams.a == BitmapDescriptorFactory.HUE_RED) {
                a(view);
            } else if (layoutParams.a == 1.0f) {
                b(view);
            }
        }
        if (i4 != this.o) {
            this.o = i4;
            if (this.y != null) {
                for (int size = this.y.size() - 1; size >= 0; size--) {
                    ((DrawerListener) this.y.get(size)).onDrawerStateChanged(i4);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if ((layoutParams.c & 1) == 1) {
            layoutParams.c = 0;
            if (this.y != null) {
                for (int size = this.y.size() - 1; size >= 0; size--) {
                    ((DrawerListener) this.y.get(size)).onDrawerClosed(view);
                }
            }
            a(view, false);
            if (hasWindowFocus()) {
                View rootView = getRootView();
                if (rootView != null) {
                    rootView.sendAccessibilityEvent(32);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if ((layoutParams.c & 1) == 0) {
            layoutParams.c = 1;
            if (this.y != null) {
                for (int size = this.y.size() - 1; size >= 0; size--) {
                    ((DrawerListener) this.y.get(size)).onDrawerOpened(view);
                }
            }
            a(view, true);
            if (hasWindowFocus()) {
                sendAccessibilityEvent(32);
            }
        }
    }

    private void a(View view, boolean z2) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if ((z2 || f(childAt)) && (!z2 || childAt != view)) {
                ViewCompat.setImportantForAccessibility(childAt, 4);
            } else {
                ViewCompat.setImportantForAccessibility(childAt, 1);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(View view, float f2) {
        if (this.y != null) {
            for (int size = this.y.size() - 1; size >= 0; size--) {
                ((DrawerListener) this.y.get(size)).onDrawerSlide(view, f2);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(View view, float f2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (f2 != layoutParams.a) {
            layoutParams.a = f2;
            a(view, f2);
        }
    }

    /* access modifiers changed from: 0000 */
    public float c(View view) {
        return ((LayoutParams) view.getLayoutParams()).a;
    }

    /* access modifiers changed from: 0000 */
    public int d(View view) {
        return GravityCompat.getAbsoluteGravity(((LayoutParams) view.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(this));
    }

    /* access modifiers changed from: 0000 */
    public boolean a(View view, int i2) {
        return (d(view) & i2) == i2;
    }

    /* access modifiers changed from: 0000 */
    public View a() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if ((((LayoutParams) childAt.getLayoutParams()).c & 1) == 1) {
                return childAt;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void c(View view, float f2) {
        float width = (float) view.getWidth();
        int i2 = (int) (width * f2);
        int c2 = i2 - ((int) (c(view) * width));
        if (!a(view, 3)) {
            c2 = -c2;
        }
        view.offsetLeftAndRight(c2);
        b(view, f2);
    }

    /* access modifiers changed from: 0000 */
    public View a(int i2) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i2, ViewCompat.getLayoutDirection(this)) & 7;
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if ((d(childAt) & 7) == absoluteGravity) {
                return childAt;
            }
        }
        return null;
    }

    static String b(int i2) {
        if ((i2 & 3) == 3) {
            return "LEFT";
        }
        return (i2 & 5) == 5 ? "RIGHT" : Integer.toHexString(i2);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.q = true;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.q = true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int mode = MeasureSpec.getMode(i2);
        int mode2 = MeasureSpec.getMode(i3);
        int size = MeasureSpec.getSize(i2);
        int size2 = MeasureSpec.getSize(i3);
        if (!(mode == 1073741824 && mode2 == 1073741824)) {
            if (isInEditMode()) {
                if (mode != Integer.MIN_VALUE && mode == 0) {
                    size = HttpStatus.SC_MULTIPLE_CHOICES;
                }
                if (mode2 != Integer.MIN_VALUE && mode2 == 0) {
                    size2 = HttpStatus.SC_MULTIPLE_CHOICES;
                }
            } else {
                throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
            }
        }
        setMeasuredDimension(size, size2);
        int i4 = 0;
        boolean z2 = this.G != null && ViewCompat.getFitsSystemWindows(this);
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int childCount = getChildCount();
        int i5 = 0;
        boolean z3 = false;
        boolean z4 = false;
        while (i5 < childCount) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (z2) {
                    int absoluteGravity = GravityCompat.getAbsoluteGravity(layoutParams.gravity, layoutDirection);
                    if (ViewCompat.getFitsSystemWindows(childAt)) {
                        if (VERSION.SDK_INT >= 21) {
                            WindowInsets windowInsets = (WindowInsets) this.G;
                            if (absoluteGravity == 3) {
                                windowInsets = windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), i4, windowInsets.getSystemWindowInsetBottom());
                            } else if (absoluteGravity == 5) {
                                windowInsets = windowInsets.replaceSystemWindowInsets(i4, windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
                            }
                            childAt.dispatchApplyWindowInsets(windowInsets);
                        }
                    } else if (VERSION.SDK_INT >= 21) {
                        WindowInsets windowInsets2 = (WindowInsets) this.G;
                        if (absoluteGravity == 3) {
                            windowInsets2 = windowInsets2.replaceSystemWindowInsets(windowInsets2.getSystemWindowInsetLeft(), windowInsets2.getSystemWindowInsetTop(), i4, windowInsets2.getSystemWindowInsetBottom());
                        } else if (absoluteGravity == 5) {
                            windowInsets2 = windowInsets2.replaceSystemWindowInsets(i4, windowInsets2.getSystemWindowInsetTop(), windowInsets2.getSystemWindowInsetRight(), windowInsets2.getSystemWindowInsetBottom());
                        }
                        layoutParams.leftMargin = windowInsets2.getSystemWindowInsetLeft();
                        layoutParams.topMargin = windowInsets2.getSystemWindowInsetTop();
                        layoutParams.rightMargin = windowInsets2.getSystemWindowInsetRight();
                        layoutParams.bottomMargin = windowInsets2.getSystemWindowInsetBottom();
                    }
                }
                if (e(childAt)) {
                    childAt.measure(MeasureSpec.makeMeasureSpec((size - layoutParams.leftMargin) - layoutParams.rightMargin, 1073741824), MeasureSpec.makeMeasureSpec((size2 - layoutParams.topMargin) - layoutParams.bottomMargin, 1073741824));
                } else if (f(childAt)) {
                    if (d && ViewCompat.getElevation(childAt) != this.f) {
                        ViewCompat.setElevation(childAt, this.f);
                    }
                    int d2 = d(childAt) & 7;
                    boolean z5 = d2 == 3;
                    if ((!z5 || !z3) && (z5 || !z4)) {
                        if (z5) {
                            z3 = true;
                        } else {
                            z4 = true;
                        }
                        childAt.measure(getChildMeasureSpec(i2, this.g + layoutParams.leftMargin + layoutParams.rightMargin, layoutParams.width), getChildMeasureSpec(i3, layoutParams.topMargin + layoutParams.bottomMargin, layoutParams.height));
                        i5++;
                        i4 = 0;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Child drawer has absolute gravity ");
                        sb.append(b(d2));
                        sb.append(" but this ");
                        sb.append("DrawerLayout");
                        sb.append(" already has a ");
                        sb.append("drawer view along that edge");
                        throw new IllegalStateException(sb.toString());
                    }
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Child ");
                    sb2.append(childAt);
                    sb2.append(" at index ");
                    sb2.append(i5);
                    sb2.append(" does not have a valid layout_gravity - must be Gravity.LEFT, ");
                    sb2.append("Gravity.RIGHT or Gravity.NO_GRAVITY");
                    throw new IllegalStateException(sb2.toString());
                }
            }
            int i6 = i2;
            int i7 = i3;
            i5++;
            i4 = 0;
        }
    }

    private void d() {
        if (!d) {
            this.C = e();
            this.D = f();
        }
    }

    private Drawable e() {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        if (layoutDirection == 0) {
            if (this.I != null) {
                a(this.I, layoutDirection);
                return this.I;
            }
        } else if (this.J != null) {
            a(this.J, layoutDirection);
            return this.J;
        }
        return this.K;
    }

    private Drawable f() {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        if (layoutDirection == 0) {
            if (this.J != null) {
                a(this.J, layoutDirection);
                return this.J;
            }
        } else if (this.I != null) {
            a(this.I, layoutDirection);
            return this.I;
        }
        return this.L;
    }

    private boolean a(Drawable drawable, int i2) {
        if (drawable == null || !DrawableCompat.isAutoMirrored(drawable)) {
            return false;
        }
        DrawableCompat.setLayoutDirection(drawable, i2);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        float f2;
        int i6;
        this.p = true;
        int i7 = i4 - i2;
        int childCount = getChildCount();
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (e(childAt)) {
                    childAt.layout(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.leftMargin + childAt.getMeasuredWidth(), layoutParams.topMargin + childAt.getMeasuredHeight());
                } else {
                    int measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (a(childAt, 3)) {
                        float f3 = (float) measuredWidth;
                        i6 = (-measuredWidth) + ((int) (layoutParams.a * f3));
                        f2 = ((float) (measuredWidth + i6)) / f3;
                    } else {
                        float f4 = (float) measuredWidth;
                        int i9 = i7 - ((int) (layoutParams.a * f4));
                        f2 = ((float) (i7 - i9)) / f4;
                        i6 = i9;
                    }
                    boolean z3 = f2 != layoutParams.a;
                    int i10 = layoutParams.gravity & 112;
                    if (i10 == 16) {
                        int i11 = i5 - i3;
                        int i12 = (i11 - measuredHeight) / 2;
                        if (i12 < layoutParams.topMargin) {
                            i12 = layoutParams.topMargin;
                        } else if (i12 + measuredHeight > i11 - layoutParams.bottomMargin) {
                            i12 = (i11 - layoutParams.bottomMargin) - measuredHeight;
                        }
                        childAt.layout(i6, i12, measuredWidth + i6, measuredHeight + i12);
                    } else if (i10 != 80) {
                        childAt.layout(i6, layoutParams.topMargin, measuredWidth + i6, layoutParams.topMargin + measuredHeight);
                    } else {
                        int i13 = i5 - i3;
                        childAt.layout(i6, (i13 - layoutParams.bottomMargin) - childAt.getMeasuredHeight(), measuredWidth + i6, i13 - layoutParams.bottomMargin);
                    }
                    if (z3) {
                        b(childAt, f2);
                    }
                    int i14 = layoutParams.a > BitmapDescriptorFactory.HUE_RED ? 0 : 4;
                    if (childAt.getVisibility() != i14) {
                        childAt.setVisibility(i14);
                    }
                }
            }
        }
        this.p = false;
        this.q = false;
    }

    public void requestLayout() {
        if (!this.p) {
            super.requestLayout();
        }
    }

    public void computeScroll() {
        int childCount = getChildCount();
        float f2 = BitmapDescriptorFactory.HUE_RED;
        for (int i2 = 0; i2 < childCount; i2++) {
            f2 = Math.max(f2, ((LayoutParams) getChildAt(i2).getLayoutParams()).a);
        }
        this.i = f2;
        boolean continueSettling = this.k.continueSettling(true);
        boolean continueSettling2 = this.l.continueSettling(true);
        if (continueSettling || continueSettling2) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private static boolean h(View view) {
        Drawable background = view.getBackground();
        boolean z2 = false;
        if (background == null) {
            return false;
        }
        if (background.getOpacity() == -1) {
            z2 = true;
        }
        return z2;
    }

    public void setStatusBarBackground(@Nullable Drawable drawable) {
        this.B = drawable;
        invalidate();
    }

    @Nullable
    public Drawable getStatusBarBackgroundDrawable() {
        return this.B;
    }

    public void setStatusBarBackground(int i2) {
        this.B = i2 != 0 ? ContextCompat.getDrawable(getContext(), i2) : null;
        invalidate();
    }

    public void setStatusBarBackgroundColor(@ColorInt int i2) {
        this.B = new ColorDrawable(i2);
        invalidate();
    }

    public void onRtlPropertiesChanged(int i2) {
        d();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.H && this.B != null) {
            int systemWindowInsetTop = (VERSION.SDK_INT < 21 || this.G == null) ? 0 : ((WindowInsets) this.G).getSystemWindowInsetTop();
            if (systemWindowInsetTop > 0) {
                this.B.setBounds(0, 0, getWidth(), systemWindowInsetTop);
                this.B.draw(canvas);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j2) {
        int i2;
        int i3;
        Canvas canvas2 = canvas;
        View view2 = view;
        int height = getHeight();
        boolean e2 = e(view2);
        int width = getWidth();
        int save = canvas2.save();
        if (e2) {
            int childCount = getChildCount();
            i2 = width;
            i3 = 0;
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt = getChildAt(i4);
                if (childAt != view2 && childAt.getVisibility() == 0 && h(childAt) && f(childAt) && childAt.getHeight() >= height) {
                    if (a(childAt, 3)) {
                        int right = childAt.getRight();
                        if (right > i3) {
                            i3 = right;
                        }
                    } else {
                        int left = childAt.getLeft();
                        if (left < i2) {
                            i2 = left;
                        }
                    }
                }
            }
            canvas2.clipRect(i3, 0, i2, getHeight());
        } else {
            i2 = width;
            i3 = 0;
        }
        boolean drawChild = super.drawChild(canvas, view, j2);
        canvas2.restoreToCount(save);
        if (this.i > BitmapDescriptorFactory.HUE_RED && e2) {
            this.j.setColor((((int) (((float) ((this.h & ViewCompat.MEASURED_STATE_MASK) >>> 24)) * this.i)) << 24) | (this.h & ViewCompat.MEASURED_SIZE_MASK));
            canvas2.drawRect((float) i3, BitmapDescriptorFactory.HUE_RED, (float) i2, (float) getHeight(), this.j);
        } else if (this.C != null && a(view2, 3)) {
            int intrinsicWidth = this.C.getIntrinsicWidth();
            int right2 = view.getRight();
            float max = Math.max(BitmapDescriptorFactory.HUE_RED, Math.min(((float) right2) / ((float) this.k.getEdgeSize()), 1.0f));
            this.C.setBounds(right2, view.getTop(), intrinsicWidth + right2, view.getBottom());
            this.C.setAlpha((int) (max * 255.0f));
            this.C.draw(canvas2);
        } else if (this.D != null && a(view2, 5)) {
            int intrinsicWidth2 = this.D.getIntrinsicWidth();
            int left2 = view.getLeft();
            float max2 = Math.max(BitmapDescriptorFactory.HUE_RED, Math.min(((float) (getWidth() - left2)) / ((float) this.l.getEdgeSize()), 1.0f));
            this.D.setBounds(left2 - intrinsicWidth2, view.getTop(), left2, view.getBottom());
            this.D.setAlpha((int) (max2 * 255.0f));
            this.D.draw(canvas2);
        }
        return drawChild;
    }

    /* access modifiers changed from: 0000 */
    public boolean e(View view) {
        return ((LayoutParams) view.getLayoutParams()).gravity == 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean f(View view) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(((LayoutParams) view.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(view));
        return ((absoluteGravity & 3) == 0 && (absoluteGravity & 5) == 0) ? false : true;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z2;
        boolean shouldInterceptTouchEvent = this.k.shouldInterceptTouchEvent(motionEvent) | this.l.shouldInterceptTouchEvent(motionEvent);
        switch (motionEvent.getActionMasked()) {
            case 0:
                float x2 = motionEvent.getX();
                float y2 = motionEvent.getY();
                this.z = x2;
                this.A = y2;
                if (this.i > BitmapDescriptorFactory.HUE_RED) {
                    View findTopChildUnder = this.k.findTopChildUnder((int) x2, (int) y2);
                    if (findTopChildUnder != null && e(findTopChildUnder)) {
                        z2 = true;
                        this.v = false;
                        this.w = false;
                        break;
                    }
                }
                z2 = false;
                this.v = false;
                this.w = false;
            case 1:
            case 3:
                a(true);
                this.v = false;
                this.w = false;
                break;
            case 2:
                if (this.k.checkTouchSlop(3)) {
                    this.m.a();
                    this.n.a();
                    break;
                }
                break;
        }
        z2 = false;
        if (shouldInterceptTouchEvent || z2 || g() || this.w) {
            return true;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z2;
        this.k.processTouchEvent(motionEvent);
        this.l.processTouchEvent(motionEvent);
        int action = motionEvent.getAction() & 255;
        if (action != 3) {
            switch (action) {
                case 0:
                    float x2 = motionEvent.getX();
                    float y2 = motionEvent.getY();
                    this.z = x2;
                    this.A = y2;
                    this.v = false;
                    this.w = false;
                    break;
                case 1:
                    float x3 = motionEvent.getX();
                    float y3 = motionEvent.getY();
                    View findTopChildUnder = this.k.findTopChildUnder((int) x3, (int) y3);
                    if (findTopChildUnder != null && e(findTopChildUnder)) {
                        float f2 = x3 - this.z;
                        float f3 = y3 - this.A;
                        int touchSlop = this.k.getTouchSlop();
                        if ((f2 * f2) + (f3 * f3) < ((float) (touchSlop * touchSlop))) {
                            View a2 = a();
                            if (!(a2 == null || getDrawerLockMode(a2) == 2)) {
                                z2 = false;
                                a(z2);
                                this.v = false;
                                break;
                            }
                        }
                    }
                    z2 = true;
                    a(z2);
                    this.v = false;
            }
        } else {
            a(true);
            this.v = false;
            this.w = false;
        }
        return true;
    }

    public void requestDisallowInterceptTouchEvent(boolean z2) {
        super.requestDisallowInterceptTouchEvent(z2);
        this.v = z2;
        if (z2) {
            a(true);
        }
    }

    public void closeDrawers() {
        a(false);
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z2) {
        int childCount = getChildCount();
        boolean z3 = false;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (f(childAt) && (!z2 || layoutParams.b)) {
                int width = childAt.getWidth();
                if (a(childAt, 3)) {
                    z3 |= this.k.smoothSlideViewTo(childAt, -width, childAt.getTop());
                } else {
                    z3 |= this.l.smoothSlideViewTo(childAt, getWidth(), childAt.getTop());
                }
                layoutParams.b = false;
            }
        }
        this.m.a();
        this.n.a();
        if (z3) {
            invalidate();
        }
    }

    public void openDrawer(@NonNull View view) {
        openDrawer(view, true);
    }

    public void openDrawer(@NonNull View view, boolean z2) {
        if (!f(view)) {
            StringBuilder sb = new StringBuilder();
            sb.append("View ");
            sb.append(view);
            sb.append(" is not a sliding drawer");
            throw new IllegalArgumentException(sb.toString());
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (this.q) {
            layoutParams.a = 1.0f;
            layoutParams.c = 1;
            a(view, true);
        } else if (z2) {
            layoutParams.c |= 2;
            if (a(view, 3)) {
                this.k.smoothSlideViewTo(view, 0, view.getTop());
            } else {
                this.l.smoothSlideViewTo(view, getWidth() - view.getWidth(), view.getTop());
            }
        } else {
            c(view, 1.0f);
            a(layoutParams.gravity, 0, view);
            view.setVisibility(0);
        }
        invalidate();
    }

    public void openDrawer(int i2) {
        openDrawer(i2, true);
    }

    public void openDrawer(int i2, boolean z2) {
        View a2 = a(i2);
        if (a2 == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("No drawer view found with gravity ");
            sb.append(b(i2));
            throw new IllegalArgumentException(sb.toString());
        }
        openDrawer(a2, z2);
    }

    public void closeDrawer(@NonNull View view) {
        closeDrawer(view, true);
    }

    public void closeDrawer(@NonNull View view, boolean z2) {
        if (!f(view)) {
            StringBuilder sb = new StringBuilder();
            sb.append("View ");
            sb.append(view);
            sb.append(" is not a sliding drawer");
            throw new IllegalArgumentException(sb.toString());
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (this.q) {
            layoutParams.a = BitmapDescriptorFactory.HUE_RED;
            layoutParams.c = 0;
        } else if (z2) {
            layoutParams.c |= 4;
            if (a(view, 3)) {
                this.k.smoothSlideViewTo(view, -view.getWidth(), view.getTop());
            } else {
                this.l.smoothSlideViewTo(view, getWidth(), view.getTop());
            }
        } else {
            c(view, BitmapDescriptorFactory.HUE_RED);
            a(layoutParams.gravity, 0, view);
            view.setVisibility(4);
        }
        invalidate();
    }

    public void closeDrawer(int i2) {
        closeDrawer(i2, true);
    }

    public void closeDrawer(int i2, boolean z2) {
        View a2 = a(i2);
        if (a2 == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("No drawer view found with gravity ");
            sb.append(b(i2));
            throw new IllegalArgumentException(sb.toString());
        }
        closeDrawer(a2, z2);
    }

    public boolean isDrawerOpen(@NonNull View view) {
        if (f(view)) {
            return (((LayoutParams) view.getLayoutParams()).c & 1) == 1;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("View ");
        sb.append(view);
        sb.append(" is not a drawer");
        throw new IllegalArgumentException(sb.toString());
    }

    public boolean isDrawerOpen(int i2) {
        View a2 = a(i2);
        if (a2 != null) {
            return isDrawerOpen(a2);
        }
        return false;
    }

    public boolean isDrawerVisible(@NonNull View view) {
        if (f(view)) {
            return ((LayoutParams) view.getLayoutParams()).a > BitmapDescriptorFactory.HUE_RED;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("View ");
        sb.append(view);
        sb.append(" is not a drawer");
        throw new IllegalArgumentException(sb.toString());
    }

    public boolean isDrawerVisible(int i2) {
        View a2 = a(i2);
        if (a2 != null) {
            return isDrawerVisible(a2);
        }
        return false;
    }

    private boolean g() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            if (((LayoutParams) getChildAt(i2).getLayoutParams()).b) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        return layoutParams instanceof MarginLayoutParams ? new LayoutParams((MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public void addFocusables(ArrayList<View> arrayList, int i2, int i3) {
        if (getDescendantFocusability() != 393216) {
            int childCount = getChildCount();
            boolean z2 = false;
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt = getChildAt(i4);
                if (!f(childAt)) {
                    this.M.add(childAt);
                } else if (isDrawerOpen(childAt)) {
                    childAt.addFocusables(arrayList, i2, i3);
                    z2 = true;
                }
            }
            if (!z2) {
                int size = this.M.size();
                for (int i5 = 0; i5 < size; i5++) {
                    View view = (View) this.M.get(i5);
                    if (view.getVisibility() == 0) {
                        view.addFocusables(arrayList, i2, i3);
                    }
                }
            }
            this.M.clear();
        }
    }

    private boolean h() {
        return b() != null;
    }

    /* access modifiers changed from: 0000 */
    public View b() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (f(childAt) && isDrawerVisible(childAt)) {
                return childAt;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        if (!this.w) {
            long uptimeMillis = SystemClock.uptimeMillis();
            MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                getChildAt(i2).dispatchTouchEvent(obtain);
            }
            obtain.recycle();
            this.w = true;
        }
    }

    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 != 4 || !h()) {
            return super.onKeyDown(i2, keyEvent);
        }
        keyEvent.startTracking();
        return true;
    }

    public boolean onKeyUp(int i2, KeyEvent keyEvent) {
        if (i2 != 4) {
            return super.onKeyUp(i2, keyEvent);
        }
        View b2 = b();
        if (b2 != null && getDrawerLockMode(b2) == 0) {
            closeDrawers();
        }
        return b2 != null;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.a != 0) {
            View a2 = a(savedState.a);
            if (a2 != null) {
                openDrawer(a2);
            }
        }
        if (savedState.b != 3) {
            setDrawerLockMode(savedState.b, 3);
        }
        if (savedState.c != 3) {
            setDrawerLockMode(savedState.c, 5);
        }
        if (savedState.d != 3) {
            setDrawerLockMode(savedState.d, (int) GravityCompat.START);
        }
        if (savedState.e != 3) {
            setDrawerLockMode(savedState.e, (int) GravityCompat.END);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        int childCount = getChildCount();
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            LayoutParams layoutParams = (LayoutParams) getChildAt(i2).getLayoutParams();
            boolean z2 = true;
            boolean z3 = layoutParams.c == 1;
            if (layoutParams.c != 2) {
                z2 = false;
            }
            if (z3 || z2) {
                savedState.a = layoutParams.gravity;
            } else {
                i2++;
            }
        }
        savedState.b = this.r;
        savedState.c = this.s;
        savedState.d = this.t;
        savedState.e = this.u;
        return savedState;
    }

    public void addView(View view, int i2, android.view.ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i2, layoutParams);
        if (a() != null || f(view)) {
            ViewCompat.setImportantForAccessibility(view, 4);
        } else {
            ViewCompat.setImportantForAccessibility(view, 1);
        }
        if (!b) {
            ViewCompat.setAccessibilityDelegate(view, this.e);
        }
    }

    static boolean g(View view) {
        return (ViewCompat.getImportantForAccessibility(view) == 4 || ViewCompat.getImportantForAccessibility(view) == 2) ? false : true;
    }
}
