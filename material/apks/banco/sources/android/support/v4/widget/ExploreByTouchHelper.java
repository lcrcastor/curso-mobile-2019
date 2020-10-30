package android.support.v4.widget;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewParentCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.FocusStrategy.BoundsAdapter;
import android.support.v4.widget.FocusStrategy.CollectionAdapter;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.List;

public abstract class ExploreByTouchHelper extends AccessibilityDelegateCompat {
    public static final int HOST_ID = -1;
    public static final int INVALID_ID = Integer.MIN_VALUE;
    private static final Rect a = new Rect(SubsamplingScaleImageView.TILE_SIZE_AUTO, SubsamplingScaleImageView.TILE_SIZE_AUTO, Integer.MIN_VALUE, Integer.MIN_VALUE);
    private static final BoundsAdapter<AccessibilityNodeInfoCompat> m = new BoundsAdapter<AccessibilityNodeInfoCompat>() {
        /* renamed from: a */
        public void obtainBounds(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, Rect rect) {
            accessibilityNodeInfoCompat.getBoundsInParent(rect);
        }
    };
    private static final CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat> n = new CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat>() {
        /* renamed from: a */
        public AccessibilityNodeInfoCompat get(SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat, int i) {
            return (AccessibilityNodeInfoCompat) sparseArrayCompat.valueAt(i);
        }

        /* renamed from: a */
        public int size(SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat) {
            return sparseArrayCompat.size();
        }
    };
    private final Rect c = new Rect();
    private final Rect d = new Rect();
    private final Rect e = new Rect();
    private final int[] f = new int[2];
    private final AccessibilityManager g;
    private final View h;
    private MyNodeProvider i;
    /* access modifiers changed from: private */
    public int j = Integer.MIN_VALUE;
    /* access modifiers changed from: private */
    public int k = Integer.MIN_VALUE;
    private int l = Integer.MIN_VALUE;

    class MyNodeProvider extends AccessibilityNodeProviderCompat {
        MyNodeProvider() {
        }

        public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int i) {
            return AccessibilityNodeInfoCompat.obtain(ExploreByTouchHelper.this.a(i));
        }

        public boolean performAction(int i, int i2, Bundle bundle) {
            return ExploreByTouchHelper.this.a(i, i2, bundle);
        }

        public AccessibilityNodeInfoCompat findFocus(int i) {
            int a2 = i == 2 ? ExploreByTouchHelper.this.j : ExploreByTouchHelper.this.k;
            if (a2 == Integer.MIN_VALUE) {
                return null;
            }
            return createAccessibilityNodeInfo(a2);
        }
    }

    private static int b(int i2) {
        switch (i2) {
            case 19:
                return 33;
            case 21:
                return 17;
            case 22:
                return 66;
            default:
                return 130;
        }
    }

    /* access modifiers changed from: protected */
    public abstract int getVirtualViewAt(float f2, float f3);

    /* access modifiers changed from: protected */
    public abstract void getVisibleVirtualViews(List<Integer> list);

    /* access modifiers changed from: protected */
    public abstract boolean onPerformActionForVirtualView(int i2, int i3, @Nullable Bundle bundle);

    /* access modifiers changed from: protected */
    public void onPopulateEventForHost(@NonNull AccessibilityEvent accessibilityEvent) {
    }

    /* access modifiers changed from: protected */
    public void onPopulateEventForVirtualView(int i2, @NonNull AccessibilityEvent accessibilityEvent) {
    }

    /* access modifiers changed from: protected */
    public void onPopulateNodeForHost(@NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
    }

    /* access modifiers changed from: protected */
    public abstract void onPopulateNodeForVirtualView(int i2, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

    /* access modifiers changed from: protected */
    public void onVirtualViewKeyboardFocusChanged(int i2, boolean z) {
    }

    public ExploreByTouchHelper(@NonNull View view) {
        if (view == null) {
            throw new IllegalArgumentException("View may not be null");
        }
        this.h = view;
        this.g = (AccessibilityManager) view.getContext().getSystemService("accessibility");
        view.setFocusable(true);
        if (ViewCompat.getImportantForAccessibility(view) == 0) {
            ViewCompat.setImportantForAccessibility(view, 1);
        }
    }

    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        if (this.i == null) {
            this.i = new MyNodeProvider();
        }
        return this.i;
    }

    public final boolean dispatchHoverEvent(@NonNull MotionEvent motionEvent) {
        boolean z = false;
        if (!this.g.isEnabled() || !this.g.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 7) {
            switch (action) {
                case 9:
                    break;
                case 10:
                    if (this.j == Integer.MIN_VALUE) {
                        return false;
                    }
                    c(Integer.MIN_VALUE);
                    return true;
                default:
                    return false;
            }
        }
        int virtualViewAt = getVirtualViewAt(motionEvent.getX(), motionEvent.getY());
        c(virtualViewAt);
        if (virtualViewAt != Integer.MIN_VALUE) {
            z = true;
        }
        return z;
    }

    public final boolean dispatchKeyEvent(@NonNull KeyEvent keyEvent) {
        int i2 = 0;
        if (keyEvent.getAction() == 1) {
            return false;
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyCode != 61) {
            if (keyCode != 66) {
                switch (keyCode) {
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                        if (!keyEvent.hasNoModifiers()) {
                            return false;
                        }
                        int b = b(keyCode);
                        int repeatCount = keyEvent.getRepeatCount() + 1;
                        boolean z = false;
                        while (i2 < repeatCount && b(b, (Rect) null)) {
                            i2++;
                            z = true;
                        }
                        return z;
                    case 23:
                        break;
                    default:
                        return false;
                }
            }
            if (!keyEvent.hasNoModifiers() || keyEvent.getRepeatCount() != 0) {
                return false;
            }
            c();
            return true;
        } else if (keyEvent.hasNoModifiers()) {
            return b(2, (Rect) null);
        } else {
            if (keyEvent.hasModifiers(1)) {
                return b(1, (Rect) null);
            }
            return false;
        }
    }

    public final void onFocusChanged(boolean z, int i2, @Nullable Rect rect) {
        if (this.k != Integer.MIN_VALUE) {
            clearKeyboardFocusForVirtualView(this.k);
        }
        if (z) {
            b(i2, rect);
        }
    }

    public final int getAccessibilityFocusedVirtualViewId() {
        return this.j;
    }

    public final int getKeyboardFocusedVirtualViewId() {
        return this.k;
    }

    private void a(int i2, Rect rect) {
        a(i2).getBoundsInParent(rect);
    }

    private boolean b(int i2, @Nullable Rect rect) {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat;
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2;
        SparseArrayCompat b = b();
        int i3 = this.k;
        int i4 = Integer.MIN_VALUE;
        if (i3 == Integer.MIN_VALUE) {
            accessibilityNodeInfoCompat = null;
        } else {
            accessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat) b.get(i3);
        }
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat3 = accessibilityNodeInfoCompat;
        if (i2 == 17 || i2 == 33 || i2 == 66 || i2 == 130) {
            Rect rect2 = new Rect();
            if (this.k != Integer.MIN_VALUE) {
                a(this.k, rect2);
            } else if (rect != null) {
                rect2.set(rect);
            } else {
                a(this.h, i2, rect2);
            }
            accessibilityNodeInfoCompat2 = (AccessibilityNodeInfoCompat) FocusStrategy.a(b, n, m, accessibilityNodeInfoCompat3, rect2, i2);
        } else {
            switch (i2) {
                case 1:
                case 2:
                    accessibilityNodeInfoCompat2 = (AccessibilityNodeInfoCompat) FocusStrategy.a(b, n, m, accessibilityNodeInfoCompat3, i2, ViewCompat.getLayoutDirection(this.h) == 1, false);
                    break;
                default:
                    throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD, FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
            }
        }
        if (accessibilityNodeInfoCompat2 != null) {
            i4 = b.keyAt(b.indexOfValue(accessibilityNodeInfoCompat2));
        }
        return requestKeyboardFocusForVirtualView(i4);
    }

    private SparseArrayCompat<AccessibilityNodeInfoCompat> b() {
        ArrayList arrayList = new ArrayList();
        getVisibleVirtualViews(arrayList);
        SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat = new SparseArrayCompat<>();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            sparseArrayCompat.put(i2, e(i2));
        }
        return sparseArrayCompat;
    }

    private static Rect a(@NonNull View view, int i2, @NonNull Rect rect) {
        int width = view.getWidth();
        int height = view.getHeight();
        if (i2 == 17) {
            rect.set(width, 0, width, height);
        } else if (i2 == 33) {
            rect.set(0, height, width, height);
        } else if (i2 == 66) {
            rect.set(-1, 0, -1, height);
        } else if (i2 != 130) {
            throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        } else {
            rect.set(0, -1, width, -1);
        }
        return rect;
    }

    private boolean c() {
        return this.k != Integer.MIN_VALUE && onPerformActionForVirtualView(this.k, 16, null);
    }

    public final boolean sendEventForVirtualView(int i2, int i3) {
        if (i2 == Integer.MIN_VALUE || !this.g.isEnabled()) {
            return false;
        }
        ViewParent parent = this.h.getParent();
        if (parent == null) {
            return false;
        }
        return ViewParentCompat.requestSendAccessibilityEvent(parent, this.h, a(i2, i3));
    }

    public final void invalidateRoot() {
        invalidateVirtualView(-1, 1);
    }

    public final void invalidateVirtualView(int i2) {
        invalidateVirtualView(i2, 0);
    }

    public final void invalidateVirtualView(int i2, int i3) {
        if (i2 != Integer.MIN_VALUE && this.g.isEnabled()) {
            ViewParent parent = this.h.getParent();
            if (parent != null) {
                AccessibilityEvent a2 = a(i2, 2048);
                AccessibilityEventCompat.setContentChangeTypes(a2, i3);
                ViewParentCompat.requestSendAccessibilityEvent(parent, this.h, a2);
            }
        }
    }

    @Deprecated
    public int getFocusedVirtualView() {
        return getAccessibilityFocusedVirtualViewId();
    }

    private void c(int i2) {
        if (this.l != i2) {
            int i3 = this.l;
            this.l = i2;
            sendEventForVirtualView(i2, 128);
            sendEventForVirtualView(i3, 256);
        }
    }

    private AccessibilityEvent a(int i2, int i3) {
        if (i2 != -1) {
            return b(i2, i3);
        }
        return d(i3);
    }

    private AccessibilityEvent d(int i2) {
        AccessibilityEvent obtain = AccessibilityEvent.obtain(i2);
        this.h.onInitializeAccessibilityEvent(obtain);
        return obtain;
    }

    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        onPopulateEventForHost(accessibilityEvent);
    }

    private AccessibilityEvent b(int i2, int i3) {
        AccessibilityEvent obtain = AccessibilityEvent.obtain(i3);
        AccessibilityNodeInfoCompat a2 = a(i2);
        obtain.getText().add(a2.getText());
        obtain.setContentDescription(a2.getContentDescription());
        obtain.setScrollable(a2.isScrollable());
        obtain.setPassword(a2.isPassword());
        obtain.setEnabled(a2.isEnabled());
        obtain.setChecked(a2.isChecked());
        onPopulateEventForVirtualView(i2, obtain);
        if (!obtain.getText().isEmpty() || obtain.getContentDescription() != null) {
            obtain.setClassName(a2.getClassName());
            AccessibilityRecordCompat.setSource(obtain, this.h, i2);
            obtain.setPackageName(this.h.getContext().getPackageName());
            return obtain;
        }
        throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public AccessibilityNodeInfoCompat a(int i2) {
        if (i2 == -1) {
            return d();
        }
        return e(i2);
    }

    @NonNull
    private AccessibilityNodeInfoCompat d() {
        AccessibilityNodeInfoCompat obtain = AccessibilityNodeInfoCompat.obtain(this.h);
        ViewCompat.onInitializeAccessibilityNodeInfo(this.h, obtain);
        ArrayList arrayList = new ArrayList();
        getVisibleVirtualViews(arrayList);
        if (obtain.getChildCount() <= 0 || arrayList.size() <= 0) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                obtain.addChild(this.h, ((Integer) arrayList.get(i2)).intValue());
            }
            return obtain;
        }
        throw new RuntimeException("Views cannot have both real and virtual children");
    }

    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
        onPopulateNodeForHost(accessibilityNodeInfoCompat);
    }

    @NonNull
    private AccessibilityNodeInfoCompat e(int i2) {
        AccessibilityNodeInfoCompat obtain = AccessibilityNodeInfoCompat.obtain();
        obtain.setEnabled(true);
        obtain.setFocusable(true);
        obtain.setClassName("android.view.View");
        obtain.setBoundsInParent(a);
        obtain.setBoundsInScreen(a);
        obtain.setParent(this.h);
        onPopulateNodeForVirtualView(i2, obtain);
        if (obtain.getText() == null && obtain.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        obtain.getBoundsInParent(this.d);
        if (this.d.equals(a)) {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
        int actions = obtain.getActions();
        if ((actions & 64) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        } else if ((actions & 128) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        } else {
            obtain.setPackageName(this.h.getContext().getPackageName());
            obtain.setSource(this.h, i2);
            if (this.j == i2) {
                obtain.setAccessibilityFocused(true);
                obtain.addAction(128);
            } else {
                obtain.setAccessibilityFocused(false);
                obtain.addAction(64);
            }
            boolean z = this.k == i2;
            if (z) {
                obtain.addAction(2);
            } else if (obtain.isFocusable()) {
                obtain.addAction(1);
            }
            obtain.setFocused(z);
            this.h.getLocationOnScreen(this.f);
            obtain.getBoundsInScreen(this.c);
            if (this.c.equals(a)) {
                obtain.getBoundsInParent(this.c);
                if (obtain.mParentVirtualDescendantId != -1) {
                    AccessibilityNodeInfoCompat obtain2 = AccessibilityNodeInfoCompat.obtain();
                    for (int i3 = obtain.mParentVirtualDescendantId; i3 != -1; i3 = obtain2.mParentVirtualDescendantId) {
                        obtain2.setParent(this.h, -1);
                        obtain2.setBoundsInParent(a);
                        onPopulateNodeForVirtualView(i3, obtain2);
                        obtain2.getBoundsInParent(this.d);
                        this.c.offset(this.d.left, this.d.top);
                    }
                    obtain2.recycle();
                }
                this.c.offset(this.f[0] - this.h.getScrollX(), this.f[1] - this.h.getScrollY());
            }
            if (this.h.getLocalVisibleRect(this.e)) {
                this.e.offset(this.f[0] - this.h.getScrollX(), this.f[1] - this.h.getScrollY());
                if (this.c.intersect(this.e)) {
                    obtain.setBoundsInScreen(this.c);
                    if (a(this.c)) {
                        obtain.setVisibleToUser(true);
                    }
                }
            }
            return obtain;
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i2, int i3, Bundle bundle) {
        if (i2 != -1) {
            return b(i2, i3, bundle);
        }
        return a(i3, bundle);
    }

    private boolean a(int i2, Bundle bundle) {
        return ViewCompat.performAccessibilityAction(this.h, i2, bundle);
    }

    private boolean b(int i2, int i3, Bundle bundle) {
        if (i3 == 64) {
            return f(i2);
        }
        if (i3 == 128) {
            return g(i2);
        }
        switch (i3) {
            case 1:
                return requestKeyboardFocusForVirtualView(i2);
            case 2:
                return clearKeyboardFocusForVirtualView(i2);
            default:
                return onPerformActionForVirtualView(i2, i3, bundle);
        }
    }

    private boolean a(Rect rect) {
        boolean z = false;
        if (rect == null || rect.isEmpty() || this.h.getWindowVisibility() != 0) {
            return false;
        }
        ViewParent parent = this.h.getParent();
        while (parent instanceof View) {
            View view = (View) parent;
            if (view.getAlpha() <= BitmapDescriptorFactory.HUE_RED || view.getVisibility() != 0) {
                return false;
            }
            parent = view.getParent();
        }
        if (parent != null) {
            z = true;
        }
        return z;
    }

    private boolean f(int i2) {
        if (!this.g.isEnabled() || !this.g.isTouchExplorationEnabled() || this.j == i2) {
            return false;
        }
        if (this.j != Integer.MIN_VALUE) {
            g(this.j);
        }
        this.j = i2;
        this.h.invalidate();
        sendEventForVirtualView(i2, 32768);
        return true;
    }

    private boolean g(int i2) {
        if (this.j != i2) {
            return false;
        }
        this.j = Integer.MIN_VALUE;
        this.h.invalidate();
        sendEventForVirtualView(i2, 65536);
        return true;
    }

    public final boolean requestKeyboardFocusForVirtualView(int i2) {
        if ((!this.h.isFocused() && !this.h.requestFocus()) || this.k == i2) {
            return false;
        }
        if (this.k != Integer.MIN_VALUE) {
            clearKeyboardFocusForVirtualView(this.k);
        }
        this.k = i2;
        onVirtualViewKeyboardFocusChanged(i2, true);
        sendEventForVirtualView(i2, 8);
        return true;
    }

    public final boolean clearKeyboardFocusForVirtualView(int i2) {
        if (this.k != i2) {
            return false;
        }
        this.k = Integer.MIN_VALUE;
        onVirtualViewKeyboardFocusChanged(i2, false);
        sendEventForVirtualView(i2, 8);
        return true;
    }
}
