package android.support.v4.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.util.Log;
import android.view.Display;
import android.view.PointerIcon;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeProvider;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ViewCompat {
    public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
    public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
    @Deprecated
    public static final int LAYER_TYPE_HARDWARE = 2;
    @Deprecated
    public static final int LAYER_TYPE_NONE = 0;
    @Deprecated
    public static final int LAYER_TYPE_SOFTWARE = 1;
    public static final int LAYOUT_DIRECTION_INHERIT = 2;
    public static final int LAYOUT_DIRECTION_LOCALE = 3;
    public static final int LAYOUT_DIRECTION_LTR = 0;
    public static final int LAYOUT_DIRECTION_RTL = 1;
    @Deprecated
    public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
    @Deprecated
    public static final int MEASURED_SIZE_MASK = 16777215;
    @Deprecated
    public static final int MEASURED_STATE_MASK = -16777216;
    @Deprecated
    public static final int MEASURED_STATE_TOO_SMALL = 16777216;
    @Deprecated
    public static final int OVER_SCROLL_ALWAYS = 0;
    @Deprecated
    public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
    @Deprecated
    public static final int OVER_SCROLL_NEVER = 2;
    public static final int SCROLL_AXIS_HORIZONTAL = 1;
    public static final int SCROLL_AXIS_NONE = 0;
    public static final int SCROLL_AXIS_VERTICAL = 2;
    public static final int SCROLL_INDICATOR_BOTTOM = 2;
    public static final int SCROLL_INDICATOR_END = 32;
    public static final int SCROLL_INDICATOR_LEFT = 4;
    public static final int SCROLL_INDICATOR_RIGHT = 8;
    public static final int SCROLL_INDICATOR_START = 16;
    public static final int SCROLL_INDICATOR_TOP = 1;
    public static final int TYPE_NON_TOUCH = 1;
    public static final int TYPE_TOUCH = 0;
    static final ViewCompatBaseImpl a;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FocusDirection {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FocusRealDirection {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FocusRelativeDirection {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NestedScrollType {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ScrollAxis {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ScrollIndicators {
    }

    @RequiresApi(15)
    static class ViewCompatApi15Impl extends ViewCompatBaseImpl {
        ViewCompatApi15Impl() {
        }

        public boolean a(View view) {
            return view.hasOnClickListeners();
        }
    }

    @RequiresApi(16)
    static class ViewCompatApi16Impl extends ViewCompatApi15Impl {
        ViewCompatApi16Impl() {
        }

        public boolean b(View view) {
            return view.hasTransientState();
        }

        public void a(View view, boolean z) {
            view.setHasTransientState(z);
        }

        public void c(View view) {
            view.postInvalidateOnAnimation();
        }

        public void a(View view, int i, int i2, int i3, int i4) {
            view.postInvalidateOnAnimation(i, i2, i3, i4);
        }

        public void a(View view, Runnable runnable) {
            view.postOnAnimation(runnable);
        }

        public void a(View view, Runnable runnable, long j) {
            view.postOnAnimationDelayed(runnable, j);
        }

        public int d(View view) {
            return view.getImportantForAccessibility();
        }

        public void a(View view, int i) {
            if (i == 4) {
                i = 2;
            }
            view.setImportantForAccessibility(i);
        }

        public boolean a(View view, int i, Bundle bundle) {
            return view.performAccessibilityAction(i, bundle);
        }

        public AccessibilityNodeProviderCompat e(View view) {
            AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
            if (accessibilityNodeProvider != null) {
                return new AccessibilityNodeProviderCompat(accessibilityNodeProvider);
            }
            return null;
        }

        public ViewParent f(View view) {
            return view.getParentForAccessibility();
        }

        public int g(View view) {
            return view.getMinimumWidth();
        }

        public int h(View view) {
            return view.getMinimumHeight();
        }

        public void i(View view) {
            view.requestFitSystemWindows();
        }

        public boolean j(View view) {
            return view.getFitsSystemWindows();
        }

        public boolean k(View view) {
            return view.hasOverlappingRendering();
        }

        public void a(View view, Drawable drawable) {
            view.setBackground(drawable);
        }
    }

    @RequiresApi(17)
    static class ViewCompatApi17Impl extends ViewCompatApi16Impl {
        ViewCompatApi17Impl() {
        }

        public int l(View view) {
            return view.getLabelFor();
        }

        public void b(View view, int i) {
            view.setLabelFor(i);
        }

        public void a(View view, Paint paint) {
            view.setLayerPaint(paint);
        }

        public int m(View view) {
            return view.getLayoutDirection();
        }

        public void c(View view, int i) {
            view.setLayoutDirection(i);
        }

        public int n(View view) {
            return view.getPaddingStart();
        }

        public int o(View view) {
            return view.getPaddingEnd();
        }

        public void b(View view, int i, int i2, int i3, int i4) {
            view.setPaddingRelative(i, i2, i3, i4);
        }

        public int p(View view) {
            return view.getWindowSystemUiVisibility();
        }

        public boolean q(View view) {
            return view.isPaddingRelative();
        }

        public Display r(View view) {
            return view.getDisplay();
        }

        public int a() {
            return View.generateViewId();
        }
    }

    @RequiresApi(18)
    static class ViewCompatApi18Impl extends ViewCompatApi17Impl {
        ViewCompatApi18Impl() {
        }

        public void a(View view, Rect rect) {
            view.setClipBounds(rect);
        }

        public Rect s(View view) {
            return view.getClipBounds();
        }

        public boolean t(View view) {
            return view.isInLayout();
        }
    }

    @RequiresApi(19)
    static class ViewCompatApi19Impl extends ViewCompatApi18Impl {
        ViewCompatApi19Impl() {
        }

        public int u(View view) {
            return view.getAccessibilityLiveRegion();
        }

        public void d(View view, int i) {
            view.setAccessibilityLiveRegion(i);
        }

        public void a(View view, int i) {
            view.setImportantForAccessibility(i);
        }

        public boolean v(View view) {
            return view.isLaidOut();
        }

        public boolean w(View view) {
            return view.isLayoutDirectionResolved();
        }

        public boolean x(View view) {
            return view.isAttachedToWindow();
        }
    }

    @RequiresApi(21)
    static class ViewCompatApi21Impl extends ViewCompatApi19Impl {
        private static ThreadLocal<Rect> d;

        ViewCompatApi21Impl() {
        }

        public void a(View view, String str) {
            view.setTransitionName(str);
        }

        public String y(View view) {
            return view.getTransitionName();
        }

        public void i(View view) {
            view.requestApplyInsets();
        }

        public void a(View view, float f) {
            view.setElevation(f);
        }

        public float z(View view) {
            return view.getElevation();
        }

        public void b(View view, float f) {
            view.setTranslationZ(f);
        }

        public float A(View view) {
            return view.getTranslationZ();
        }

        public void a(View view, final OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
            if (onApplyWindowInsetsListener == null) {
                view.setOnApplyWindowInsetsListener(null);
            } else {
                view.setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {
                    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                        return (WindowInsets) WindowInsetsCompat.a(onApplyWindowInsetsListener.onApplyWindowInsets(view, WindowInsetsCompat.a((Object) windowInsets)));
                    }
                });
            }
        }

        public void b(View view, boolean z) {
            view.setNestedScrollingEnabled(z);
        }

        public boolean B(View view) {
            return view.isNestedScrollingEnabled();
        }

        public boolean e(View view, int i) {
            return view.startNestedScroll(i);
        }

        public void C(View view) {
            view.stopNestedScroll();
        }

        public boolean D(View view) {
            return view.hasNestedScrollingParent();
        }

        public boolean a(View view, int i, int i2, int i3, int i4, int[] iArr) {
            return view.dispatchNestedScroll(i, i2, i3, i4, iArr);
        }

        public boolean a(View view, int i, int i2, int[] iArr, int[] iArr2) {
            return view.dispatchNestedPreScroll(i, i2, iArr, iArr2);
        }

        public boolean a(View view, float f, float f2, boolean z) {
            return view.dispatchNestedFling(f, f2, z);
        }

        public boolean a(View view, float f, float f2) {
            return view.dispatchNestedPreFling(f, f2);
        }

        public boolean E(View view) {
            return view.isImportantForAccessibility();
        }

        public ColorStateList F(View view) {
            return view.getBackgroundTintList();
        }

        public void a(View view, ColorStateList colorStateList) {
            view.setBackgroundTintList(colorStateList);
            if (VERSION.SDK_INT == 21) {
                Drawable background = view.getBackground();
                boolean z = (view.getBackgroundTintList() == null && view.getBackgroundTintMode() == null) ? false : true;
                if (background != null && z) {
                    if (background.isStateful()) {
                        background.setState(view.getDrawableState());
                    }
                    view.setBackground(background);
                }
            }
        }

        public void a(View view, Mode mode) {
            view.setBackgroundTintMode(mode);
            if (VERSION.SDK_INT == 21) {
                Drawable background = view.getBackground();
                boolean z = (view.getBackgroundTintList() == null && view.getBackgroundTintMode() == null) ? false : true;
                if (background != null && z) {
                    if (background.isStateful()) {
                        background.setState(view.getDrawableState());
                    }
                    view.setBackground(background);
                }
            }
        }

        public Mode G(View view) {
            return view.getBackgroundTintMode();
        }

        public WindowInsetsCompat a(View view, WindowInsetsCompat windowInsetsCompat) {
            WindowInsets windowInsets = (WindowInsets) WindowInsetsCompat.a(windowInsetsCompat);
            WindowInsets onApplyWindowInsets = view.onApplyWindowInsets(windowInsets);
            if (onApplyWindowInsets != windowInsets) {
                windowInsets = new WindowInsets(onApplyWindowInsets);
            }
            return WindowInsetsCompat.a((Object) windowInsets);
        }

        public WindowInsetsCompat b(View view, WindowInsetsCompat windowInsetsCompat) {
            WindowInsets windowInsets = (WindowInsets) WindowInsetsCompat.a(windowInsetsCompat);
            WindowInsets dispatchApplyWindowInsets = view.dispatchApplyWindowInsets(windowInsets);
            if (dispatchApplyWindowInsets != windowInsets) {
                windowInsets = new WindowInsets(dispatchApplyWindowInsets);
            }
            return WindowInsetsCompat.a((Object) windowInsets);
        }

        public float H(View view) {
            return view.getZ();
        }

        public void c(View view, float f) {
            view.setZ(f);
        }

        public void f(View view, int i) {
            boolean z;
            Rect c = c();
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                View view2 = (View) parent;
                c.set(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());
                z = !c.intersects(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            } else {
                z = false;
            }
            super.f(view, i);
            if (z && c.intersect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())) {
                ((View) parent).invalidate(c);
            }
        }

        public void g(View view, int i) {
            boolean z;
            Rect c = c();
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                View view2 = (View) parent;
                c.set(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());
                z = !c.intersects(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            } else {
                z = false;
            }
            super.g(view, i);
            if (z && c.intersect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())) {
                ((View) parent).invalidate(c);
            }
        }

        private static Rect c() {
            if (d == null) {
                d = new ThreadLocal<>();
            }
            Rect rect = (Rect) d.get();
            if (rect == null) {
                rect = new Rect();
                d.set(rect);
            }
            rect.setEmpty();
            return rect;
        }
    }

    @RequiresApi(23)
    static class ViewCompatApi23Impl extends ViewCompatApi21Impl {
        ViewCompatApi23Impl() {
        }

        public void h(View view, int i) {
            view.setScrollIndicators(i);
        }

        public void a(View view, int i, int i2) {
            view.setScrollIndicators(i, i2);
        }

        public int I(View view) {
            return view.getScrollIndicators();
        }

        public void f(View view, int i) {
            view.offsetLeftAndRight(i);
        }

        public void g(View view, int i) {
            view.offsetTopAndBottom(i);
        }
    }

    @RequiresApi(24)
    static class ViewCompatApi24Impl extends ViewCompatApi23Impl {
        ViewCompatApi24Impl() {
        }

        public void J(View view) {
            view.dispatchStartTemporaryDetach();
        }

        public void K(View view) {
            view.dispatchFinishTemporaryDetach();
        }

        public void a(View view, PointerIconCompat pointerIconCompat) {
            view.setPointerIcon((PointerIcon) (pointerIconCompat != null ? pointerIconCompat.getPointerIcon() : null));
        }

        public boolean a(View view, ClipData clipData, DragShadowBuilder dragShadowBuilder, Object obj, int i) {
            return view.startDragAndDrop(clipData, dragShadowBuilder, obj, i);
        }

        public void L(View view) {
            view.cancelDragAndDrop();
        }

        public void a(View view, DragShadowBuilder dragShadowBuilder) {
            view.updateDragShadow(dragShadowBuilder);
        }
    }

    @RequiresApi(26)
    static class ViewCompatApi26Impl extends ViewCompatApi24Impl {
        ViewCompatApi26Impl() {
        }

        public void a(@NonNull View view, @Nullable String... strArr) {
            view.setAutofillHints(strArr);
        }

        public int M(@NonNull View view) {
            return view.getImportantForAutofill();
        }

        public void i(@NonNull View view, int i) {
            view.setImportantForAutofill(i);
        }

        public boolean N(@NonNull View view) {
            return view.isImportantForAutofill();
        }

        public void a(View view, CharSequence charSequence) {
            view.setTooltipText(charSequence);
        }

        public int O(@NonNull View view) {
            return view.getNextClusterForwardId();
        }

        public void j(@NonNull View view, int i) {
            view.setNextClusterForwardId(i);
        }

        public boolean P(@NonNull View view) {
            return view.isKeyboardNavigationCluster();
        }

        public void c(@NonNull View view, boolean z) {
            view.setKeyboardNavigationCluster(z);
        }

        public boolean Q(@NonNull View view) {
            return view.isFocusedByDefault();
        }

        public void d(@NonNull View view, boolean z) {
            view.setFocusedByDefault(z);
        }

        public View a(@NonNull View view, View view2, int i) {
            return view.keyboardNavigationClusterSearch(view2, i);
        }

        public void a(@NonNull View view, @NonNull Collection<View> collection, int i) {
            view.addKeyboardNavigationClusters(collection, i);
        }

        public boolean R(@NonNull View view) {
            return view.restoreDefaultFocus();
        }

        public boolean S(@NonNull View view) {
            return view.hasExplicitFocusable();
        }
    }

    static class ViewCompatBaseImpl {
        static Field b = null;
        static boolean c = false;
        private static Field d;
        private static boolean e;
        private static Field f;
        private static boolean g;
        private static WeakHashMap<View, String> h;
        private static final AtomicInteger i = new AtomicInteger(1);
        private static Method m;
        WeakHashMap<View, ViewPropertyAnimatorCompat> a = null;
        private Method j;
        private Method k;
        private boolean l;

        public float A(View view) {
            return BitmapDescriptorFactory.HUE_RED;
        }

        public boolean E(View view) {
            return true;
        }

        public int I(View view) {
            return 0;
        }

        public void L(View view) {
        }

        @TargetApi(26)
        public int M(@NonNull View view) {
            return 0;
        }

        public boolean N(@NonNull View view) {
            return true;
        }

        public int O(@NonNull View view) {
            return -1;
        }

        public boolean P(@NonNull View view) {
            return false;
        }

        public boolean Q(@NonNull View view) {
            return false;
        }

        public WindowInsetsCompat a(View view, WindowInsetsCompat windowInsetsCompat) {
            return windowInsetsCompat;
        }

        public View a(@NonNull View view, View view2, int i2) {
            return null;
        }

        public void a(View view, float f2) {
        }

        public void a(View view, int i2) {
        }

        public void a(View view, int i2, int i3) {
        }

        public void a(View view, Rect rect) {
        }

        public void a(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        }

        public void a(View view, PointerIconCompat pointerIconCompat) {
        }

        public void a(View view, DragShadowBuilder dragShadowBuilder) {
        }

        public void a(View view, CharSequence charSequence) {
        }

        public void a(@NonNull View view, @NonNull Collection<View> collection, int i2) {
        }

        public void a(View view, boolean z) {
        }

        public void a(@NonNull View view, @Nullable String... strArr) {
        }

        public boolean a(View view) {
            return false;
        }

        public boolean a(View view, int i2, Bundle bundle) {
            return false;
        }

        public WindowInsetsCompat b(View view, WindowInsetsCompat windowInsetsCompat) {
            return windowInsetsCompat;
        }

        public void b(View view, float f2) {
        }

        public void b(View view, int i2) {
        }

        public boolean b(View view) {
            return false;
        }

        public void c(View view, float f2) {
        }

        public void c(View view, int i2) {
        }

        public void c(@NonNull View view, boolean z) {
        }

        public int d(View view) {
            return 0;
        }

        public void d(View view, int i2) {
        }

        public void d(@NonNull View view, boolean z) {
        }

        public AccessibilityNodeProviderCompat e(View view) {
            return null;
        }

        public void h(View view, int i2) {
        }

        public void i(View view) {
        }

        public void i(@NonNull View view, int i2) {
        }

        public void j(@NonNull View view, int i2) {
        }

        public boolean j(View view) {
            return false;
        }

        public boolean k(View view) {
            return true;
        }

        public int l(View view) {
            return 0;
        }

        public int m(View view) {
            return 0;
        }

        public int p(View view) {
            return 0;
        }

        public boolean q(View view) {
            return false;
        }

        public Rect s(View view) {
            return null;
        }

        public boolean t(View view) {
            return false;
        }

        public int u(View view) {
            return 0;
        }

        public boolean w(View view) {
            return false;
        }

        public float z(View view) {
            return BitmapDescriptorFactory.HUE_RED;
        }

        ViewCompatBaseImpl() {
        }

        public void a(View view, @Nullable AccessibilityDelegateCompat accessibilityDelegateCompat) {
            view.setAccessibilityDelegate(accessibilityDelegateCompat == null ? null : accessibilityDelegateCompat.a());
        }

        public boolean T(View view) {
            boolean z = false;
            if (c) {
                return false;
            }
            if (b == null) {
                try {
                    b = View.class.getDeclaredField("mAccessibilityDelegate");
                    b.setAccessible(true);
                } catch (Throwable unused) {
                    c = true;
                    return false;
                }
            }
            try {
                if (b.get(view) != null) {
                    z = true;
                }
                return z;
            } catch (Throwable unused2) {
                c = true;
                return false;
            }
        }

        public void a(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            view.onInitializeAccessibilityNodeInfo(accessibilityNodeInfoCompat.unwrap());
        }

        public boolean a(View view, ClipData clipData, DragShadowBuilder dragShadowBuilder, Object obj, int i2) {
            return view.startDrag(clipData, dragShadowBuilder, obj, i2);
        }

        public void c(View view) {
            view.postInvalidate();
        }

        public void a(View view, int i2, int i3, int i4, int i5) {
            view.postInvalidate(i2, i3, i4, i5);
        }

        public void a(View view, Runnable runnable) {
            view.postDelayed(runnable, b());
        }

        public void a(View view, Runnable runnable, long j2) {
            view.postDelayed(runnable, b() + j2);
        }

        /* access modifiers changed from: 0000 */
        public long b() {
            return ValueAnimator.getFrameDelay();
        }

        public void a(View view, Paint paint) {
            view.setLayerType(view.getLayerType(), paint);
            view.invalidate();
        }

        public ViewParent f(View view) {
            return view.getParent();
        }

        public int n(View view) {
            return view.getPaddingLeft();
        }

        public int o(View view) {
            return view.getPaddingRight();
        }

        public void b(View view, int i2, int i3, int i4, int i5) {
            view.setPadding(i2, i3, i4, i5);
        }

        public void J(View view) {
            if (!this.l) {
                c();
            }
            if (this.j != null) {
                try {
                    this.j.invoke(view, new Object[0]);
                } catch (Exception e2) {
                    Log.d("ViewCompat", "Error calling dispatchStartTemporaryDetach", e2);
                }
            } else {
                view.onStartTemporaryDetach();
            }
        }

        public void K(View view) {
            if (!this.l) {
                c();
            }
            if (this.k != null) {
                try {
                    this.k.invoke(view, new Object[0]);
                } catch (Exception e2) {
                    Log.d("ViewCompat", "Error calling dispatchFinishTemporaryDetach", e2);
                }
            } else {
                view.onFinishTemporaryDetach();
            }
        }

        private void c() {
            try {
                this.j = View.class.getDeclaredMethod("dispatchStartTemporaryDetach", new Class[0]);
                this.k = View.class.getDeclaredMethod("dispatchFinishTemporaryDetach", new Class[0]);
            } catch (NoSuchMethodException e2) {
                Log.e("ViewCompat", "Couldn't find method", e2);
            }
            this.l = true;
        }

        public int g(View view) {
            if (!e) {
                try {
                    d = View.class.getDeclaredField("mMinWidth");
                    d.setAccessible(true);
                } catch (NoSuchFieldException unused) {
                }
                e = true;
            }
            if (d != null) {
                try {
                    return ((Integer) d.get(view)).intValue();
                } catch (Exception unused2) {
                }
            }
            return 0;
        }

        public int h(View view) {
            if (!g) {
                try {
                    f = View.class.getDeclaredField("mMinHeight");
                    f.setAccessible(true);
                } catch (NoSuchFieldException unused) {
                }
                g = true;
            }
            if (f != null) {
                try {
                    return ((Integer) f.get(view)).intValue();
                } catch (Exception unused2) {
                }
            }
            return 0;
        }

        public ViewPropertyAnimatorCompat U(View view) {
            if (this.a == null) {
                this.a = new WeakHashMap<>();
            }
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = (ViewPropertyAnimatorCompat) this.a.get(view);
            if (viewPropertyAnimatorCompat != null) {
                return viewPropertyAnimatorCompat;
            }
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = new ViewPropertyAnimatorCompat(view);
            this.a.put(view, viewPropertyAnimatorCompat2);
            return viewPropertyAnimatorCompat2;
        }

        public void a(View view, String str) {
            if (h == null) {
                h = new WeakHashMap<>();
            }
            h.put(view, str);
        }

        public String y(View view) {
            if (h == null) {
                return null;
            }
            return (String) h.get(view);
        }

        public void a(ViewGroup viewGroup, boolean z) {
            if (m == null) {
                try {
                    m = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", new Class[]{Boolean.TYPE});
                } catch (NoSuchMethodException e2) {
                    Log.e("ViewCompat", "Unable to find childrenDrawingOrderEnabled", e2);
                }
                m.setAccessible(true);
            }
            try {
                m.invoke(viewGroup, new Object[]{Boolean.valueOf(z)});
            } catch (IllegalAccessException e3) {
                Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", e3);
            } catch (IllegalArgumentException e4) {
                Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", e4);
            } catch (InvocationTargetException e5) {
                Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", e5);
            }
        }

        public void b(View view, boolean z) {
            if (view instanceof NestedScrollingChild) {
                ((NestedScrollingChild) view).setNestedScrollingEnabled(z);
            }
        }

        public boolean B(View view) {
            if (view instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view).isNestedScrollingEnabled();
            }
            return false;
        }

        public void a(View view, Drawable drawable) {
            view.setBackgroundDrawable(drawable);
        }

        public ColorStateList F(View view) {
            if (view instanceof TintableBackgroundView) {
                return ((TintableBackgroundView) view).getSupportBackgroundTintList();
            }
            return null;
        }

        public void a(View view, ColorStateList colorStateList) {
            if (view instanceof TintableBackgroundView) {
                ((TintableBackgroundView) view).setSupportBackgroundTintList(colorStateList);
            }
        }

        public void a(View view, Mode mode) {
            if (view instanceof TintableBackgroundView) {
                ((TintableBackgroundView) view).setSupportBackgroundTintMode(mode);
            }
        }

        public Mode G(View view) {
            if (view instanceof TintableBackgroundView) {
                return ((TintableBackgroundView) view).getSupportBackgroundTintMode();
            }
            return null;
        }

        public boolean e(View view, int i2) {
            if (view instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view).startNestedScroll(i2);
            }
            return false;
        }

        public void C(View view) {
            if (view instanceof NestedScrollingChild) {
                ((NestedScrollingChild) view).stopNestedScroll();
            }
        }

        public boolean D(View view) {
            if (view instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view).hasNestedScrollingParent();
            }
            return false;
        }

        public boolean a(View view, int i2, int i3, int i4, int i5, int[] iArr) {
            if (view instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view).dispatchNestedScroll(i2, i3, i4, i5, iArr);
            }
            return false;
        }

        public boolean a(View view, int i2, int i3, int[] iArr, int[] iArr2) {
            if (view instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view).dispatchNestedPreScroll(i2, i3, iArr, iArr2);
            }
            return false;
        }

        public boolean a(View view, float f2, float f3, boolean z) {
            if (view instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view).dispatchNestedFling(f2, f3, z);
            }
            return false;
        }

        public boolean a(View view, float f2, float f3) {
            if (view instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view).dispatchNestedPreFling(f2, f3);
            }
            return false;
        }

        public boolean v(View view) {
            return view.getWidth() > 0 && view.getHeight() > 0;
        }

        public float H(View view) {
            return A(view) + z(view);
        }

        public boolean x(View view) {
            return view.getWindowToken() != null;
        }

        public void f(View view, int i2) {
            view.offsetLeftAndRight(i2);
            if (view.getVisibility() == 0) {
                V(view);
                ViewParent parent = view.getParent();
                if (parent instanceof View) {
                    V((View) parent);
                }
            }
        }

        public void g(View view, int i2) {
            view.offsetTopAndBottom(i2);
            if (view.getVisibility() == 0) {
                V(view);
                ViewParent parent = view.getParent();
                if (parent instanceof View) {
                    V((View) parent);
                }
            }
        }

        private static void V(View view) {
            float translationY = view.getTranslationY();
            view.setTranslationY(1.0f + translationY);
            view.setTranslationY(translationY);
        }

        public Display r(View view) {
            if (x(view)) {
                return ((WindowManager) view.getContext().getSystemService("window")).getDefaultDisplay();
            }
            return null;
        }

        public boolean R(@NonNull View view) {
            return view.requestFocus();
        }

        public boolean S(@NonNull View view) {
            return view.hasFocusable();
        }

        public int a() {
            int i2;
            int i3;
            do {
                i2 = i.get();
                i3 = i2 + 1;
                if (i3 > 16777215) {
                    i3 = 1;
                }
            } while (!i.compareAndSet(i2, i3));
            return i2;
        }
    }

    static {
        if (VERSION.SDK_INT >= 26) {
            a = new ViewCompatApi26Impl();
        } else if (VERSION.SDK_INT >= 24) {
            a = new ViewCompatApi24Impl();
        } else if (VERSION.SDK_INT >= 23) {
            a = new ViewCompatApi23Impl();
        } else if (VERSION.SDK_INT >= 21) {
            a = new ViewCompatApi21Impl();
        } else if (VERSION.SDK_INT >= 19) {
            a = new ViewCompatApi19Impl();
        } else if (VERSION.SDK_INT >= 18) {
            a = new ViewCompatApi18Impl();
        } else if (VERSION.SDK_INT >= 17) {
            a = new ViewCompatApi17Impl();
        } else if (VERSION.SDK_INT >= 16) {
            a = new ViewCompatApi16Impl();
        } else if (VERSION.SDK_INT >= 15) {
            a = new ViewCompatApi15Impl();
        } else {
            a = new ViewCompatBaseImpl();
        }
    }

    @Deprecated
    public static boolean canScrollHorizontally(View view, int i) {
        return view.canScrollHorizontally(i);
    }

    @Deprecated
    public static boolean canScrollVertically(View view, int i) {
        return view.canScrollVertically(i);
    }

    @Deprecated
    public static int getOverScrollMode(View view) {
        return view.getOverScrollMode();
    }

    @Deprecated
    public static void setOverScrollMode(View view, int i) {
        view.setOverScrollMode(i);
    }

    @Deprecated
    public static void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        view.onPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Deprecated
    public static void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        view.onInitializeAccessibilityEvent(accessibilityEvent);
    }

    public static void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        a.a(view, accessibilityNodeInfoCompat);
    }

    public static void setAccessibilityDelegate(View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
        a.a(view, accessibilityDelegateCompat);
    }

    public static void setAutofillHints(@NonNull View view, @Nullable String... strArr) {
        a.a(view, strArr);
    }

    public static int getImportantForAutofill(@NonNull View view) {
        return a.M(view);
    }

    public static void setImportantForAutofill(@NonNull View view, int i) {
        a.i(view, i);
    }

    public static boolean isImportantForAutofill(@NonNull View view) {
        return a.N(view);
    }

    public static boolean hasAccessibilityDelegate(View view) {
        return a.T(view);
    }

    public static boolean hasTransientState(View view) {
        return a.b(view);
    }

    public static void setHasTransientState(View view, boolean z) {
        a.a(view, z);
    }

    public static void postInvalidateOnAnimation(View view) {
        a.c(view);
    }

    public static void postInvalidateOnAnimation(View view, int i, int i2, int i3, int i4) {
        a.a(view, i, i2, i3, i4);
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        a.a(view, runnable);
    }

    public static void postOnAnimationDelayed(View view, Runnable runnable, long j) {
        a.a(view, runnable, j);
    }

    public static int getImportantForAccessibility(View view) {
        return a.d(view);
    }

    public static void setImportantForAccessibility(View view, int i) {
        a.a(view, i);
    }

    public static boolean isImportantForAccessibility(View view) {
        return a.E(view);
    }

    public static boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        return a.a(view, i, bundle);
    }

    public static AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        return a.e(view);
    }

    @Deprecated
    public static float getAlpha(View view) {
        return view.getAlpha();
    }

    @Deprecated
    public static void setLayerType(View view, int i, Paint paint) {
        view.setLayerType(i, paint);
    }

    @Deprecated
    public static int getLayerType(View view) {
        return view.getLayerType();
    }

    public static int getLabelFor(View view) {
        return a.l(view);
    }

    public static void setLabelFor(View view, @IdRes int i) {
        a.b(view, i);
    }

    public static void setLayerPaint(View view, Paint paint) {
        a.a(view, paint);
    }

    public static int getLayoutDirection(View view) {
        return a.m(view);
    }

    public static void setLayoutDirection(View view, int i) {
        a.c(view, i);
    }

    public static ViewParent getParentForAccessibility(View view) {
        return a.f(view);
    }

    @NonNull
    public static <T extends View> T requireViewById(@NonNull View view, @IdRes int i) {
        T findViewById = view.findViewById(i);
        if (findViewById != null) {
            return findViewById;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this View");
    }

    @Deprecated
    public static boolean isOpaque(View view) {
        return view.isOpaque();
    }

    @Deprecated
    public static int resolveSizeAndState(int i, int i2, int i3) {
        return View.resolveSizeAndState(i, i2, i3);
    }

    @Deprecated
    public static int getMeasuredWidthAndState(View view) {
        return view.getMeasuredWidthAndState();
    }

    @Deprecated
    public static int getMeasuredHeightAndState(View view) {
        return view.getMeasuredHeightAndState();
    }

    @Deprecated
    public static int getMeasuredState(View view) {
        return view.getMeasuredState();
    }

    @Deprecated
    public static int combineMeasuredStates(int i, int i2) {
        return View.combineMeasuredStates(i, i2);
    }

    public static int getAccessibilityLiveRegion(View view) {
        return a.u(view);
    }

    public static void setAccessibilityLiveRegion(View view, int i) {
        a.d(view, i);
    }

    public static int getPaddingStart(View view) {
        return a.n(view);
    }

    public static int getPaddingEnd(View view) {
        return a.o(view);
    }

    public static void setPaddingRelative(View view, int i, int i2, int i3, int i4) {
        a.b(view, i, i2, i3, i4);
    }

    public static void dispatchStartTemporaryDetach(View view) {
        a.J(view);
    }

    public static void dispatchFinishTemporaryDetach(View view) {
        a.K(view);
    }

    @Deprecated
    public static float getTranslationX(View view) {
        return view.getTranslationX();
    }

    @Deprecated
    public static float getTranslationY(View view) {
        return view.getTranslationY();
    }

    @Nullable
    @Deprecated
    public static Matrix getMatrix(View view) {
        return view.getMatrix();
    }

    public static int getMinimumWidth(View view) {
        return a.g(view);
    }

    public static int getMinimumHeight(View view) {
        return a.h(view);
    }

    public static ViewPropertyAnimatorCompat animate(View view) {
        return a.U(view);
    }

    @Deprecated
    public static void setTranslationX(View view, float f) {
        view.setTranslationX(f);
    }

    @Deprecated
    public static void setTranslationY(View view, float f) {
        view.setTranslationY(f);
    }

    @Deprecated
    public static void setAlpha(View view, @FloatRange(from = 0.0d, to = 1.0d) float f) {
        view.setAlpha(f);
    }

    @Deprecated
    public static void setX(View view, float f) {
        view.setX(f);
    }

    @Deprecated
    public static void setY(View view, float f) {
        view.setY(f);
    }

    @Deprecated
    public static void setRotation(View view, float f) {
        view.setRotation(f);
    }

    @Deprecated
    public static void setRotationX(View view, float f) {
        view.setRotationX(f);
    }

    @Deprecated
    public static void setRotationY(View view, float f) {
        view.setRotationY(f);
    }

    @Deprecated
    public static void setScaleX(View view, float f) {
        view.setScaleX(f);
    }

    @Deprecated
    public static void setScaleY(View view, float f) {
        view.setScaleY(f);
    }

    @Deprecated
    public static float getPivotX(View view) {
        return view.getPivotX();
    }

    @Deprecated
    public static void setPivotX(View view, float f) {
        view.setPivotX(f);
    }

    @Deprecated
    public static float getPivotY(View view) {
        return view.getPivotY();
    }

    @Deprecated
    public static void setPivotY(View view, float f) {
        view.setPivotY(f);
    }

    @Deprecated
    public static float getRotation(View view) {
        return view.getRotation();
    }

    @Deprecated
    public static float getRotationX(View view) {
        return view.getRotationX();
    }

    @Deprecated
    public static float getRotationY(View view) {
        return view.getRotationY();
    }

    @Deprecated
    public static float getScaleX(View view) {
        return view.getScaleX();
    }

    @Deprecated
    public static float getScaleY(View view) {
        return view.getScaleY();
    }

    @Deprecated
    public static float getX(View view) {
        return view.getX();
    }

    @Deprecated
    public static float getY(View view) {
        return view.getY();
    }

    public static void setElevation(View view, float f) {
        a.a(view, f);
    }

    public static float getElevation(View view) {
        return a.z(view);
    }

    public static void setTranslationZ(View view, float f) {
        a.b(view, f);
    }

    public static float getTranslationZ(View view) {
        return a.A(view);
    }

    public static void setTransitionName(View view, String str) {
        a.a(view, str);
    }

    public static String getTransitionName(View view) {
        return a.y(view);
    }

    public static int getWindowSystemUiVisibility(View view) {
        return a.p(view);
    }

    public static void requestApplyInsets(View view) {
        a.i(view);
    }

    @Deprecated
    public static void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean z) {
        a.a(viewGroup, z);
    }

    public static boolean getFitsSystemWindows(View view) {
        return a.j(view);
    }

    @Deprecated
    public static void setFitsSystemWindows(View view, boolean z) {
        view.setFitsSystemWindows(z);
    }

    @Deprecated
    public static void jumpDrawablesToCurrentState(View view) {
        view.jumpDrawablesToCurrentState();
    }

    public static void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        a.a(view, onApplyWindowInsetsListener);
    }

    public static WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        return a.a(view, windowInsetsCompat);
    }

    public static WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        return a.b(view, windowInsetsCompat);
    }

    @Deprecated
    public static void setSaveFromParentEnabled(View view, boolean z) {
        view.setSaveFromParentEnabled(z);
    }

    @Deprecated
    public static void setActivated(View view, boolean z) {
        view.setActivated(z);
    }

    public static boolean hasOverlappingRendering(View view) {
        return a.k(view);
    }

    public static boolean isPaddingRelative(View view) {
        return a.q(view);
    }

    public static void setBackground(View view, Drawable drawable) {
        a.a(view, drawable);
    }

    public static ColorStateList getBackgroundTintList(View view) {
        return a.F(view);
    }

    public static void setBackgroundTintList(View view, ColorStateList colorStateList) {
        a.a(view, colorStateList);
    }

    public static Mode getBackgroundTintMode(View view) {
        return a.G(view);
    }

    public static void setBackgroundTintMode(View view, Mode mode) {
        a.a(view, mode);
    }

    public static void setNestedScrollingEnabled(@NonNull View view, boolean z) {
        a.b(view, z);
    }

    public static boolean isNestedScrollingEnabled(@NonNull View view) {
        return a.B(view);
    }

    public static boolean startNestedScroll(@NonNull View view, int i) {
        return a.e(view, i);
    }

    public static void stopNestedScroll(@NonNull View view) {
        a.C(view);
    }

    public static boolean hasNestedScrollingParent(@NonNull View view) {
        return a.D(view);
    }

    public static boolean dispatchNestedScroll(@NonNull View view, int i, int i2, int i3, int i4, @Nullable int[] iArr) {
        return a.a(view, i, i2, i3, i4, iArr);
    }

    public static boolean dispatchNestedPreScroll(@NonNull View view, int i, int i2, @Nullable int[] iArr, @Nullable int[] iArr2) {
        return a.a(view, i, i2, iArr, iArr2);
    }

    public static boolean startNestedScroll(@NonNull View view, int i, int i2) {
        if (view instanceof NestedScrollingChild2) {
            return ((NestedScrollingChild2) view).startNestedScroll(i, i2);
        }
        if (i2 == 0) {
            return a.e(view, i);
        }
        return false;
    }

    public static void stopNestedScroll(@NonNull View view, int i) {
        if (view instanceof NestedScrollingChild2) {
            ((NestedScrollingChild2) view).stopNestedScroll(i);
        } else if (i == 0) {
            a.C(view);
        }
    }

    public static boolean hasNestedScrollingParent(@NonNull View view, int i) {
        if (view instanceof NestedScrollingChild2) {
            ((NestedScrollingChild2) view).hasNestedScrollingParent(i);
        } else if (i == 0) {
            return a.D(view);
        }
        return false;
    }

    public static boolean dispatchNestedScroll(@NonNull View view, int i, int i2, int i3, int i4, @Nullable int[] iArr, int i5) {
        if (view instanceof NestedScrollingChild2) {
            return ((NestedScrollingChild2) view).dispatchNestedScroll(i, i2, i3, i4, iArr, i5);
        }
        if (i5 == 0) {
            return a.a(view, i, i2, i3, i4, iArr);
        }
        return false;
    }

    public static boolean dispatchNestedPreScroll(@NonNull View view, int i, int i2, @Nullable int[] iArr, @Nullable int[] iArr2, int i3) {
        if (view instanceof NestedScrollingChild2) {
            return ((NestedScrollingChild2) view).dispatchNestedPreScroll(i, i2, iArr, iArr2, i3);
        }
        if (i3 == 0) {
            return a.a(view, i, i2, iArr, iArr2);
        }
        return false;
    }

    public static boolean dispatchNestedFling(@NonNull View view, float f, float f2, boolean z) {
        return a.a(view, f, f2, z);
    }

    public static boolean dispatchNestedPreFling(@NonNull View view, float f, float f2) {
        return a.a(view, f, f2);
    }

    public static boolean isInLayout(View view) {
        return a.t(view);
    }

    public static boolean isLaidOut(View view) {
        return a.v(view);
    }

    public static boolean isLayoutDirectionResolved(View view) {
        return a.w(view);
    }

    public static float getZ(View view) {
        return a.H(view);
    }

    public static void setZ(View view, float f) {
        a.c(view, f);
    }

    public static void offsetTopAndBottom(View view, int i) {
        a.g(view, i);
    }

    public static void offsetLeftAndRight(View view, int i) {
        a.f(view, i);
    }

    public static void setClipBounds(View view, Rect rect) {
        a.a(view, rect);
    }

    public static Rect getClipBounds(View view) {
        return a.s(view);
    }

    public static boolean isAttachedToWindow(View view) {
        return a.x(view);
    }

    public static boolean hasOnClickListeners(View view) {
        return a.a(view);
    }

    public static void setScrollIndicators(@NonNull View view, int i) {
        a.h(view, i);
    }

    public static void setScrollIndicators(@NonNull View view, int i, int i2) {
        a.a(view, i, i2);
    }

    public static int getScrollIndicators(@NonNull View view) {
        return a.I(view);
    }

    public static void setPointerIcon(@NonNull View view, PointerIconCompat pointerIconCompat) {
        a.a(view, pointerIconCompat);
    }

    public static Display getDisplay(@NonNull View view) {
        return a.r(view);
    }

    public static void setTooltipText(@NonNull View view, @Nullable CharSequence charSequence) {
        a.a(view, charSequence);
    }

    public static boolean startDragAndDrop(View view, ClipData clipData, DragShadowBuilder dragShadowBuilder, Object obj, int i) {
        return a.a(view, clipData, dragShadowBuilder, obj, i);
    }

    public static void cancelDragAndDrop(View view) {
        a.L(view);
    }

    public static void updateDragShadow(View view, DragShadowBuilder dragShadowBuilder) {
        a.a(view, dragShadowBuilder);
    }

    public static int getNextClusterForwardId(@NonNull View view) {
        return a.O(view);
    }

    public static void setNextClusterForwardId(@NonNull View view, int i) {
        a.j(view, i);
    }

    public static boolean isKeyboardNavigationCluster(@NonNull View view) {
        return a.P(view);
    }

    public static void setKeyboardNavigationCluster(@NonNull View view, boolean z) {
        a.c(view, z);
    }

    public static boolean isFocusedByDefault(@NonNull View view) {
        return a.Q(view);
    }

    public static void setFocusedByDefault(@NonNull View view, boolean z) {
        a.d(view, z);
    }

    public static View keyboardNavigationClusterSearch(@NonNull View view, View view2, int i) {
        return a.a(view, view2, i);
    }

    public static void addKeyboardNavigationClusters(@NonNull View view, @NonNull Collection<View> collection, int i) {
        a.a(view, collection, i);
    }

    public static boolean restoreDefaultFocus(@NonNull View view) {
        return a.R(view);
    }

    public static boolean hasExplicitFocusable(@NonNull View view) {
        return a.S(view);
    }

    public static int generateViewId() {
        return a.a();
    }

    protected ViewCompat() {
    }
}
