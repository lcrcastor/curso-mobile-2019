package android.support.v7.widget;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnHoverListener;
import android.view.View.OnLongClickListener;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;

@RestrictTo({Scope.LIBRARY_GROUP})
class TooltipCompatHandler implements OnAttachStateChangeListener, OnHoverListener, OnLongClickListener {
    private static TooltipCompatHandler i;
    private static TooltipCompatHandler j;
    private final View a;
    private final CharSequence b;
    private final Runnable c = new Runnable() {
        public void run() {
            TooltipCompatHandler.this.a(false);
        }
    };
    private final Runnable d = new Runnable() {
        public void run() {
            TooltipCompatHandler.this.a();
        }
    };
    private int e;
    private int f;
    private TooltipPopup g;
    private boolean h;

    public void onViewAttachedToWindow(View view) {
    }

    public static void a(View view, CharSequence charSequence) {
        if (i != null && i.a == view) {
            b(null);
        }
        if (TextUtils.isEmpty(charSequence)) {
            if (j != null && j.a == view) {
                j.a();
            }
            view.setOnLongClickListener(null);
            view.setLongClickable(false);
            view.setOnHoverListener(null);
            return;
        }
        new TooltipCompatHandler(view, charSequence);
    }

    private TooltipCompatHandler(View view, CharSequence charSequence) {
        this.a = view;
        this.b = charSequence;
        this.a.setOnLongClickListener(this);
        this.a.setOnHoverListener(this);
    }

    public boolean onLongClick(View view) {
        this.e = view.getWidth() / 2;
        this.f = view.getHeight() / 2;
        a(true);
        return true;
    }

    public boolean onHover(View view, MotionEvent motionEvent) {
        if (this.g != null && this.h) {
            return false;
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.a.getContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 7) {
            if (action == 10) {
                a();
            }
        } else if (this.a.isEnabled() && this.g == null) {
            this.e = (int) motionEvent.getX();
            this.f = (int) motionEvent.getY();
            b(this);
        }
        return false;
    }

    public void onViewDetachedFromWindow(View view) {
        a();
    }

    /* access modifiers changed from: private */
    public void a(boolean z) {
        long j2;
        if (ViewCompat.isAttachedToWindow(this.a)) {
            b(null);
            if (j != null) {
                j.a();
            }
            j = this;
            this.h = z;
            this.g = new TooltipPopup(this.a.getContext());
            this.g.a(this.a, this.e, this.f, this.h, this.b);
            this.a.addOnAttachStateChangeListener(this);
            if (this.h) {
                j2 = 2500;
            } else if ((ViewCompat.getWindowSystemUiVisibility(this.a) & 1) == 1) {
                j2 = 3000 - ((long) ViewConfiguration.getLongPressTimeout());
            } else {
                j2 = 15000 - ((long) ViewConfiguration.getLongPressTimeout());
            }
            this.a.removeCallbacks(this.d);
            this.a.postDelayed(this.d, j2);
        }
    }

    /* access modifiers changed from: private */
    public void a() {
        if (j == this) {
            j = null;
            if (this.g != null) {
                this.g.a();
                this.g = null;
                this.a.removeOnAttachStateChangeListener(this);
            } else {
                Log.e("TooltipCompatHandler", "sActiveHandler.mPopup == null");
            }
        }
        if (i == this) {
            b(null);
        }
        this.a.removeCallbacks(this.d);
    }

    private static void b(TooltipCompatHandler tooltipCompatHandler) {
        if (i != null) {
            i.c();
        }
        i = tooltipCompatHandler;
        if (i != null) {
            i.b();
        }
    }

    private void b() {
        this.a.postDelayed(this.c, (long) ViewConfiguration.getLongPressTimeout());
    }

    private void c() {
        this.a.removeCallbacks(this.c);
    }
}
