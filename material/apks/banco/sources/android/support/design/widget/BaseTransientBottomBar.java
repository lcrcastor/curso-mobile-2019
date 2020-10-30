package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.R;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.SwipeDismissBehavior.OnDismissListener;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTransientBottomBar<B extends BaseTransientBottomBar<B>> {
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_LONG = 0;
    public static final int LENGTH_SHORT = -1;
    static final Handler a = new Handler(Looper.getMainLooper(), new Callback() {
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    ((BaseTransientBottomBar) message.obj).a();
                    return true;
                case 1:
                    ((BaseTransientBottomBar) message.obj).b(message.arg1);
                    return true;
                default:
                    return false;
            }
        }
    });
    /* access modifiers changed from: private */
    public static final boolean d = (VERSION.SDK_INT >= 16 && VERSION.SDK_INT <= 19);
    final SnackbarBaseLayout b;
    final Callback c = new Callback() {
        public void a() {
            BaseTransientBottomBar.a.sendMessage(BaseTransientBottomBar.a.obtainMessage(0, BaseTransientBottomBar.this));
        }

        public void a(int i) {
            BaseTransientBottomBar.a.sendMessage(BaseTransientBottomBar.a.obtainMessage(1, i, 0, BaseTransientBottomBar.this));
        }
    };
    private final ViewGroup e;
    private final Context f;
    /* access modifiers changed from: private */
    public final ContentViewCallback g;
    private int h;
    private List<BaseCallback<B>> i;
    private final AccessibilityManager j;

    public static abstract class BaseCallback<B> {
        public static final int DISMISS_EVENT_ACTION = 1;
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;
        public static final int DISMISS_EVENT_MANUAL = 3;
        public static final int DISMISS_EVENT_SWIPE = 0;
        public static final int DISMISS_EVENT_TIMEOUT = 2;

        @RestrictTo({Scope.LIBRARY_GROUP})
        @Retention(RetentionPolicy.SOURCE)
        public @interface DismissEvent {
        }

        public void onDismissed(B b, int i) {
        }

        public void onShown(B b) {
        }
    }

    final class Behavior extends SwipeDismissBehavior<SnackbarBaseLayout> {
        Behavior() {
        }

        public boolean canSwipeDismissView(View view) {
            return view instanceof SnackbarBaseLayout;
        }

        /* renamed from: a */
        public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, SnackbarBaseLayout snackbarBaseLayout, MotionEvent motionEvent) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 3) {
                switch (actionMasked) {
                    case 0:
                        if (coordinatorLayout.isPointInChildBounds(snackbarBaseLayout, (int) motionEvent.getX(), (int) motionEvent.getY())) {
                            SnackbarManager.a().c(BaseTransientBottomBar.this.c);
                            break;
                        }
                        break;
                    case 1:
                        break;
                }
            }
            SnackbarManager.a().d(BaseTransientBottomBar.this.c);
            return super.onInterceptTouchEvent(coordinatorLayout, snackbarBaseLayout, motionEvent);
        }
    }

    public interface ContentViewCallback {
        void animateContentIn(int i, int i2);

        void animateContentOut(int i, int i2);
    }

    @IntRange(from = 1)
    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    interface OnAttachStateChangeListener {
        void a(View view);

        void b(View view);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    interface OnLayoutChangeListener {
        void a(View view, int i, int i2, int i3, int i4);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    static class SnackbarBaseLayout extends FrameLayout {
        private OnLayoutChangeListener a;
        private OnAttachStateChangeListener b;

        SnackbarBaseLayout(Context context) {
            this(context, null);
        }

        SnackbarBaseLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SnackbarLayout);
            if (obtainStyledAttributes.hasValue(R.styleable.SnackbarLayout_elevation)) {
                ViewCompat.setElevation(this, (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.SnackbarLayout_elevation, 0));
            }
            obtainStyledAttributes.recycle();
            setClickable(true);
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            if (this.a != null) {
                this.a.a(this, i, i2, i3, i4);
            }
        }

        /* access modifiers changed from: protected */
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            if (this.b != null) {
                this.b.a(this);
            }
            ViewCompat.requestApplyInsets(this);
        }

        /* access modifiers changed from: protected */
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (this.b != null) {
                this.b.b(this);
            }
        }

        /* access modifiers changed from: 0000 */
        public void setOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
            this.a = onLayoutChangeListener;
        }

        /* access modifiers changed from: 0000 */
        public void setOnAttachStateChangeListener(OnAttachStateChangeListener onAttachStateChangeListener) {
            this.b = onAttachStateChangeListener;
        }
    }

    protected BaseTransientBottomBar(@NonNull ViewGroup viewGroup, @NonNull View view, @NonNull ContentViewCallback contentViewCallback) {
        if (viewGroup == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null parent");
        } else if (view == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null content");
        } else if (contentViewCallback == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null callback");
        } else {
            this.e = viewGroup;
            this.g = contentViewCallback;
            this.f = viewGroup.getContext();
            ThemeUtils.a(this.f);
            this.b = (SnackbarBaseLayout) LayoutInflater.from(this.f).inflate(R.layout.design_layout_snackbar, this.e, false);
            this.b.addView(view);
            ViewCompat.setAccessibilityLiveRegion(this.b, 1);
            ViewCompat.setImportantForAccessibility(this.b, 1);
            ViewCompat.setFitsSystemWindows(this.b, true);
            ViewCompat.setOnApplyWindowInsetsListener(this.b, new OnApplyWindowInsetsListener() {
                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                    return windowInsetsCompat;
                }
            });
            this.j = (AccessibilityManager) this.f.getSystemService("accessibility");
        }
    }

    @NonNull
    public B setDuration(int i2) {
        this.h = i2;
        return this;
    }

    public int getDuration() {
        return this.h;
    }

    @NonNull
    public Context getContext() {
        return this.f;
    }

    @NonNull
    public View getView() {
        return this.b;
    }

    public void show() {
        SnackbarManager.a().a(this.h, this.c);
    }

    public void dismiss() {
        a(3);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        SnackbarManager.a().a(this.c, i2);
    }

    @NonNull
    public B addCallback(@NonNull BaseCallback<B> baseCallback) {
        if (baseCallback == null) {
            return this;
        }
        if (this.i == null) {
            this.i = new ArrayList();
        }
        this.i.add(baseCallback);
        return this;
    }

    @NonNull
    public B removeCallback(@NonNull BaseCallback<B> baseCallback) {
        if (baseCallback == null || this.i == null) {
            return this;
        }
        this.i.remove(baseCallback);
        return this;
    }

    public boolean isShown() {
        return SnackbarManager.a().e(this.c);
    }

    public boolean isShownOrQueued() {
        return SnackbarManager.a().f(this.c);
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        if (this.b.getParent() == null) {
            LayoutParams layoutParams = this.b.getLayoutParams();
            if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
                CoordinatorLayout.LayoutParams layoutParams2 = (CoordinatorLayout.LayoutParams) layoutParams;
                Behavior behavior = new Behavior();
                behavior.setStartAlphaSwipeDistance(0.1f);
                behavior.setEndAlphaSwipeDistance(0.6f);
                behavior.setSwipeDirection(0);
                behavior.setListener(new OnDismissListener() {
                    public void onDismiss(View view) {
                        view.setVisibility(8);
                        BaseTransientBottomBar.this.a(0);
                    }

                    public void onDragStateChanged(int i) {
                        switch (i) {
                            case 0:
                                SnackbarManager.a().d(BaseTransientBottomBar.this.c);
                                return;
                            case 1:
                            case 2:
                                SnackbarManager.a().c(BaseTransientBottomBar.this.c);
                                return;
                            default:
                                return;
                        }
                    }
                });
                layoutParams2.setBehavior(behavior);
                layoutParams2.insetEdge = 80;
            }
            this.e.addView(this.b);
        }
        this.b.setOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            public void a(View view) {
            }

            public void b(View view) {
                if (BaseTransientBottomBar.this.isShownOrQueued()) {
                    BaseTransientBottomBar.a.post(new Runnable() {
                        public void run() {
                            BaseTransientBottomBar.this.c(3);
                        }
                    });
                }
            }
        });
        if (!ViewCompat.isLaidOut(this.b)) {
            this.b.setOnLayoutChangeListener(new OnLayoutChangeListener() {
                public void a(View view, int i, int i2, int i3, int i4) {
                    BaseTransientBottomBar.this.b.setOnLayoutChangeListener(null);
                    if (BaseTransientBottomBar.this.d()) {
                        BaseTransientBottomBar.this.b();
                    } else {
                        BaseTransientBottomBar.this.c();
                    }
                }
            });
        } else if (d()) {
            b();
        } else {
            c();
        }
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        if (VERSION.SDK_INT >= 12) {
            final int height = this.b.getHeight();
            if (d) {
                ViewCompat.offsetTopAndBottom(this.b, height);
            } else {
                this.b.setTranslationY((float) height);
            }
            ValueAnimator valueAnimator = new ValueAnimator();
            valueAnimator.setIntValues(new int[]{height, 0});
            valueAnimator.setInterpolator(AnimationUtils.b);
            valueAnimator.setDuration(250);
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationStart(Animator animator) {
                    BaseTransientBottomBar.this.g.animateContentIn(70, 180);
                }

                public void onAnimationEnd(Animator animator) {
                    BaseTransientBottomBar.this.c();
                }
            });
            valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
                private int c = height;

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    if (BaseTransientBottomBar.d) {
                        ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.b, intValue - this.c);
                    } else {
                        BaseTransientBottomBar.this.b.setTranslationY((float) intValue);
                    }
                    this.c = intValue;
                }
            });
            valueAnimator.start();
            return;
        }
        Animation loadAnimation = AnimationUtils.loadAnimation(this.b.getContext(), R.anim.design_snackbar_in);
        loadAnimation.setInterpolator(AnimationUtils.b);
        loadAnimation.setDuration(250);
        loadAnimation.setAnimationListener(new AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                BaseTransientBottomBar.this.c();
            }
        });
        this.b.startAnimation(loadAnimation);
    }

    private void d(final int i2) {
        if (VERSION.SDK_INT >= 12) {
            ValueAnimator valueAnimator = new ValueAnimator();
            valueAnimator.setIntValues(new int[]{0, this.b.getHeight()});
            valueAnimator.setInterpolator(AnimationUtils.b);
            valueAnimator.setDuration(250);
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationStart(Animator animator) {
                    BaseTransientBottomBar.this.g.animateContentOut(0, 180);
                }

                public void onAnimationEnd(Animator animator) {
                    BaseTransientBottomBar.this.c(i2);
                }
            });
            valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
                private int b = 0;

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    if (BaseTransientBottomBar.d) {
                        ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.b, intValue - this.b);
                    } else {
                        BaseTransientBottomBar.this.b.setTranslationY((float) intValue);
                    }
                    this.b = intValue;
                }
            });
            valueAnimator.start();
            return;
        }
        Animation loadAnimation = AnimationUtils.loadAnimation(this.b.getContext(), R.anim.design_snackbar_out);
        loadAnimation.setInterpolator(AnimationUtils.b);
        loadAnimation.setDuration(250);
        loadAnimation.setAnimationListener(new AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                BaseTransientBottomBar.this.c(i2);
            }
        });
        this.b.startAnimation(loadAnimation);
    }

    /* access modifiers changed from: 0000 */
    public final void b(int i2) {
        if (!d() || this.b.getVisibility() != 0) {
            c(i2);
        } else {
            d(i2);
        }
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        SnackbarManager.a().b(this.c);
        if (this.i != null) {
            for (int size = this.i.size() - 1; size >= 0; size--) {
                ((BaseCallback) this.i.get(size)).onShown(this);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void c(int i2) {
        SnackbarManager.a().a(this.c);
        if (this.i != null) {
            for (int size = this.i.size() - 1; size >= 0; size--) {
                ((BaseCallback) this.i.get(size)).onDismissed(this, i2);
            }
        }
        if (VERSION.SDK_INT < 11) {
            this.b.setVisibility(8);
        }
        ViewParent parent = this.b.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this.b);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean d() {
        return !this.j.isEnabled();
    }
}
