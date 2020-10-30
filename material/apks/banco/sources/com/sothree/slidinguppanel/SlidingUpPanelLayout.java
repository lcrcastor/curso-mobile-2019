package com.sothree.slidinguppanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.nineoldandroids.view.animation.AnimatorProxy;
import com.sothree.slidinguppanel.ViewDragHelper.Callback;
import com.sothree.slidinguppanel.library.R;
import cz.msebera.android.httpclient.HttpStatus;

public class SlidingUpPanelLayout extends ViewGroup {
    private static final String a = "SlidingUpPanelLayout";
    private static PanelState b = PanelState.COLLAPSED;
    private static final int[] c = {16842927};
    private float A;
    private float B;
    private float C;
    private boolean D;
    private PanelSlideListener E;
    /* access modifiers changed from: private */
    public final ViewDragHelper F;
    private boolean G;
    private final Rect H;
    private int d;
    private int e;
    private final Paint f;
    private final Drawable g;
    private int h;
    private int i;
    private int j;
    /* access modifiers changed from: private */
    public boolean k;
    private boolean l;
    private boolean m;
    private View n;
    private int o;
    private View p;
    private int q;
    /* access modifiers changed from: private */
    public View r;
    private View s;
    /* access modifiers changed from: private */
    public PanelState t;
    private PanelState u;
    /* access modifiers changed from: private */
    public float v;
    /* access modifiers changed from: private */
    public int w;
    /* access modifiers changed from: private */
    public float x;
    /* access modifiers changed from: private */
    public boolean y;
    private boolean z;

    class DragHelperCallback extends Callback {
        private DragHelperCallback() {
        }

        public boolean tryCaptureView(View view, int i) {
            boolean z = false;
            if (SlidingUpPanelLayout.this.y) {
                return false;
            }
            if (view == SlidingUpPanelLayout.this.r) {
                z = true;
            }
            return z;
        }

        public void onViewDragStateChanged(int i) {
            if (SlidingUpPanelLayout.this.F.getViewDragState() == 0) {
                SlidingUpPanelLayout.this.v = SlidingUpPanelLayout.this.a(SlidingUpPanelLayout.this.r.getTop());
                SlidingUpPanelLayout.this.c();
                if (SlidingUpPanelLayout.this.v == 1.0f) {
                    if (SlidingUpPanelLayout.this.t != PanelState.EXPANDED) {
                        SlidingUpPanelLayout.this.a();
                        SlidingUpPanelLayout.this.t = PanelState.EXPANDED;
                        SlidingUpPanelLayout.this.b(SlidingUpPanelLayout.this.r);
                    }
                } else if (SlidingUpPanelLayout.this.v == BitmapDescriptorFactory.HUE_RED) {
                    if (SlidingUpPanelLayout.this.t != PanelState.COLLAPSED) {
                        SlidingUpPanelLayout.this.t = PanelState.COLLAPSED;
                        SlidingUpPanelLayout.this.c(SlidingUpPanelLayout.this.r);
                    }
                } else if (SlidingUpPanelLayout.this.v < BitmapDescriptorFactory.HUE_RED) {
                    SlidingUpPanelLayout.this.t = PanelState.HIDDEN;
                    SlidingUpPanelLayout.this.r.setVisibility(4);
                    SlidingUpPanelLayout.this.e(SlidingUpPanelLayout.this.r);
                } else if (SlidingUpPanelLayout.this.t != PanelState.ANCHORED) {
                    SlidingUpPanelLayout.this.a();
                    SlidingUpPanelLayout.this.t = PanelState.ANCHORED;
                    SlidingUpPanelLayout.this.d(SlidingUpPanelLayout.this.r);
                }
            }
        }

        public void onViewCaptured(View view, int i) {
            SlidingUpPanelLayout.this.b();
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            SlidingUpPanelLayout.this.b(i2);
            SlidingUpPanelLayout.this.invalidate();
        }

        public void onViewReleased(View view, float f, float f2) {
            int i;
            if (SlidingUpPanelLayout.this.k) {
                f2 = -f2;
            }
            if (f2 > BitmapDescriptorFactory.HUE_RED && SlidingUpPanelLayout.this.v <= SlidingUpPanelLayout.this.x) {
                i = SlidingUpPanelLayout.this.a(SlidingUpPanelLayout.this.x);
            } else if (f2 > BitmapDescriptorFactory.HUE_RED && SlidingUpPanelLayout.this.v > SlidingUpPanelLayout.this.x) {
                i = SlidingUpPanelLayout.this.a(1.0f);
            } else if (f2 < BitmapDescriptorFactory.HUE_RED && SlidingUpPanelLayout.this.v >= SlidingUpPanelLayout.this.x) {
                i = SlidingUpPanelLayout.this.a(SlidingUpPanelLayout.this.x);
            } else if (f2 < BitmapDescriptorFactory.HUE_RED && SlidingUpPanelLayout.this.v < SlidingUpPanelLayout.this.x) {
                i = SlidingUpPanelLayout.this.a((float) BitmapDescriptorFactory.HUE_RED);
            } else if (SlidingUpPanelLayout.this.v >= (SlidingUpPanelLayout.this.x + 1.0f) / 2.0f) {
                i = SlidingUpPanelLayout.this.a(1.0f);
            } else if (SlidingUpPanelLayout.this.v >= SlidingUpPanelLayout.this.x / 2.0f) {
                i = SlidingUpPanelLayout.this.a(SlidingUpPanelLayout.this.x);
            } else {
                i = SlidingUpPanelLayout.this.a((float) BitmapDescriptorFactory.HUE_RED);
            }
            SlidingUpPanelLayout.this.F.settleCapturedViewAt(view.getLeft(), i);
            SlidingUpPanelLayout.this.invalidate();
        }

        public int getViewVerticalDragRange(View view) {
            return SlidingUpPanelLayout.this.w;
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            int b = SlidingUpPanelLayout.this.a((float) BitmapDescriptorFactory.HUE_RED);
            int b2 = SlidingUpPanelLayout.this.a(1.0f);
            if (SlidingUpPanelLayout.this.k) {
                return Math.min(Math.max(i, b2), b);
            }
            return Math.min(Math.max(i, b), b2);
        }
    }

    public static class LayoutParams extends MarginLayoutParams {
        private static final int[] a = {16843137};

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context.obtainStyledAttributes(attributeSet, a).recycle();
        }
    }

    public interface PanelSlideListener {
        void onPanelAnchored(View view);

        void onPanelCollapsed(View view);

        void onPanelExpanded(View view);

        void onPanelHidden(View view);

        void onPanelSlide(View view, float f);
    }

    public enum PanelState {
        EXPANDED,
        COLLAPSED,
        ANCHORED,
        HIDDEN,
        DRAGGING
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        PanelState a;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            try {
                this.a = (PanelState) Enum.valueOf(PanelState.class, parcel.readString());
            } catch (IllegalArgumentException unused) {
                this.a = PanelState.COLLAPSED;
            }
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.a.toString());
        }
    }

    public static class SimplePanelSlideListener implements PanelSlideListener {
        public void onPanelAnchored(View view) {
        }

        public void onPanelCollapsed(View view) {
        }

        public void onPanelExpanded(View view) {
        }

        public void onPanelHidden(View view) {
        }

        public void onPanelSlide(View view, float f) {
        }
    }

    public SlidingUpPanelLayout(Context context) {
        this(context, null);
    }

    public SlidingUpPanelLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingUpPanelLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.d = HttpStatus.SC_BAD_REQUEST;
        this.e = -1728053248;
        this.f = new Paint();
        this.h = -1;
        this.i = -1;
        this.j = -1;
        this.l = false;
        this.m = true;
        this.o = -1;
        this.t = b;
        this.u = null;
        this.x = 1.0f;
        this.D = false;
        this.G = true;
        this.H = new Rect();
        if (isInEditMode()) {
            this.g = null;
            this.F = null;
            return;
        }
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, c);
            if (obtainStyledAttributes != null) {
                setGravity(obtainStyledAttributes.getInt(0, 0));
            }
            obtainStyledAttributes.recycle();
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R.styleable.SlidingUpPanelLayout);
            if (obtainStyledAttributes2 != null) {
                this.h = obtainStyledAttributes2.getDimensionPixelSize(R.styleable.SlidingUpPanelLayout_umanoPanelHeight, -1);
                this.i = obtainStyledAttributes2.getDimensionPixelSize(R.styleable.SlidingUpPanelLayout_umanoShadowHeight, -1);
                this.j = obtainStyledAttributes2.getDimensionPixelSize(R.styleable.SlidingUpPanelLayout_umanoParalaxOffset, -1);
                this.d = obtainStyledAttributes2.getInt(R.styleable.SlidingUpPanelLayout_umanoFlingVelocity, HttpStatus.SC_BAD_REQUEST);
                this.e = obtainStyledAttributes2.getColor(R.styleable.SlidingUpPanelLayout_umanoFadeColor, -1728053248);
                this.o = obtainStyledAttributes2.getResourceId(R.styleable.SlidingUpPanelLayout_umanoDragView, -1);
                this.q = obtainStyledAttributes2.getResourceId(R.styleable.SlidingUpPanelLayout_umanoScrollableView, -1);
                this.l = obtainStyledAttributes2.getBoolean(R.styleable.SlidingUpPanelLayout_umanoOverlay, false);
                this.m = obtainStyledAttributes2.getBoolean(R.styleable.SlidingUpPanelLayout_umanoClipPanel, true);
                this.x = obtainStyledAttributes2.getFloat(R.styleable.SlidingUpPanelLayout_umanoAnchorPoint, 1.0f);
                this.t = PanelState.values()[obtainStyledAttributes2.getInt(R.styleable.SlidingUpPanelLayout_umanoInitialState, b.ordinal())];
            }
            obtainStyledAttributes2.recycle();
        }
        float f2 = context.getResources().getDisplayMetrics().density;
        if (this.h == -1) {
            this.h = (int) ((68.0f * f2) + 0.5f);
        }
        if (this.i == -1) {
            this.i = (int) ((4.0f * f2) + 0.5f);
        }
        if (this.j == -1) {
            this.j = (int) (BitmapDescriptorFactory.HUE_RED * f2);
        }
        if (this.i <= 0) {
            this.g = null;
        } else if (this.k) {
            this.g = getResources().getDrawable(R.drawable.above_shadow);
        } else {
            this.g = getResources().getDrawable(R.drawable.below_shadow);
        }
        setWillNotDraw(false);
        this.F = ViewDragHelper.create(this, 0.5f, new DragHelperCallback());
        this.F.setMinVelocity(((float) this.d) * f2);
        this.z = true;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        if (this.o != -1) {
            setDragView(findViewById(this.o));
        }
        if (this.q != -1) {
            setScrollableView(findViewById(this.q));
        }
    }

    public void setGravity(int i2) {
        if (i2 == 48 || i2 == 80) {
            this.k = i2 == 80;
            if (!this.G) {
                requestLayout();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("gravity must be set to either top or bottom");
    }

    public void setCoveredFadeColor(int i2) {
        this.e = i2;
        invalidate();
    }

    public int getCoveredFadeColor() {
        return this.e;
    }

    public void setTouchEnabled(boolean z2) {
        this.z = z2;
    }

    public boolean isTouchEnabled() {
        return (!this.z || this.r == null || this.t == PanelState.HIDDEN) ? false : true;
    }

    public void setPanelHeight(int i2) {
        if (getPanelHeight() != i2) {
            this.h = i2;
            if (!this.G) {
                requestLayout();
            }
            if (getPanelState() == PanelState.COLLAPSED) {
                smoothToBottom();
                invalidate();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void smoothToBottom() {
        a((float) BitmapDescriptorFactory.HUE_RED, 0);
    }

    public int getShadowHeight() {
        return this.i;
    }

    public void setShadowHeight(int i2) {
        this.i = i2;
        if (!this.G) {
            invalidate();
        }
    }

    public int getPanelHeight() {
        return this.h;
    }

    public int getCurrentParalaxOffset() {
        int max = (int) (((float) this.j) * Math.max(this.v, BitmapDescriptorFactory.HUE_RED));
        return this.k ? -max : max;
    }

    public void setParalaxOffset(int i2) {
        this.j = i2;
        if (!this.G) {
            requestLayout();
        }
    }

    public int getMinFlingVelocity() {
        return this.d;
    }

    public void setMinFlingVelocity(int i2) {
        this.d = i2;
    }

    public void setPanelSlideListener(PanelSlideListener panelSlideListener) {
        this.E = panelSlideListener;
    }

    public void setDragView(View view) {
        if (this.n != null) {
            this.n.setOnClickListener(null);
        }
        this.n = view;
        if (this.n != null) {
            this.n.setClickable(true);
            this.n.setFocusable(false);
            this.n.setFocusableInTouchMode(false);
            this.n.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (SlidingUpPanelLayout.this.isEnabled() && SlidingUpPanelLayout.this.isTouchEnabled()) {
                        if (SlidingUpPanelLayout.this.t == PanelState.EXPANDED || SlidingUpPanelLayout.this.t == PanelState.ANCHORED) {
                            SlidingUpPanelLayout.this.setPanelState(PanelState.COLLAPSED);
                        } else if (SlidingUpPanelLayout.this.x < 1.0f) {
                            SlidingUpPanelLayout.this.setPanelState(PanelState.ANCHORED);
                        } else {
                            SlidingUpPanelLayout.this.setPanelState(PanelState.EXPANDED);
                        }
                    }
                }
            });
        }
    }

    public void setDragView(int i2) {
        this.o = i2;
        setDragView(findViewById(i2));
    }

    public void setScrollableView(View view) {
        this.p = view;
    }

    public void setAnchorPoint(float f2) {
        if (f2 > BitmapDescriptorFactory.HUE_RED && f2 <= 1.0f) {
            this.x = f2;
        }
    }

    public float getAnchorPoint() {
        return this.x;
    }

    public void setOverlayed(boolean z2) {
        this.l = z2;
    }

    public boolean isOverlayed() {
        return this.l;
    }

    public void setClipPanel(boolean z2) {
        this.m = z2;
    }

    public boolean isClipPanel() {
        return this.m;
    }

    /* access modifiers changed from: 0000 */
    public void a(View view) {
        if (this.E != null) {
            this.E.onPanelSlide(view, this.v);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(View view) {
        if (this.E != null) {
            this.E.onPanelExpanded(view);
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void c(View view) {
        if (this.E != null) {
            this.E.onPanelCollapsed(view);
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void d(View view) {
        if (this.E != null) {
            this.E.onPanelAnchored(view);
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void e(View view) {
        if (this.E != null) {
            this.E.onPanelHidden(view);
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        int i2;
        int i3;
        int i4;
        int i5;
        if (getChildCount() != 0) {
            int paddingLeft = getPaddingLeft();
            int width = getWidth() - getPaddingRight();
            int paddingTop = getPaddingTop();
            int height = getHeight() - getPaddingBottom();
            int i6 = 0;
            if (this.r == null || !f(this.r)) {
                i5 = 0;
                i4 = 0;
                i3 = 0;
                i2 = 0;
            } else {
                i5 = this.r.getLeft();
                i4 = this.r.getRight();
                i3 = this.r.getTop();
                i2 = this.r.getBottom();
            }
            View childAt = getChildAt(0);
            int max = Math.max(paddingLeft, childAt.getLeft());
            int max2 = Math.max(paddingTop, childAt.getTop());
            int min = Math.min(width, childAt.getRight());
            int min2 = Math.min(height, childAt.getBottom());
            if (max >= i5 && max2 >= i3 && min <= i4 && min2 <= i2) {
                i6 = 4;
            }
            childAt.setVisibility(i6);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 4) {
                childAt.setVisibility(0);
            }
        }
    }

    private static boolean f(View view) {
        Drawable background = view.getBackground();
        return background != null && background.getOpacity() == -1;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.G = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.G = true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        int mode2 = MeasureSpec.getMode(i3);
        int size2 = MeasureSpec.getSize(i3);
        if (mode != 1073741824) {
            throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
        } else if (mode2 != 1073741824) {
            throw new IllegalStateException("Height must have an exact value or MATCH_PARENT");
        } else {
            int childCount = getChildCount();
            if (childCount != 2) {
                throw new IllegalStateException("Sliding up panel layout must have exactly 2 children!");
            }
            this.s = getChildAt(0);
            this.r = getChildAt(1);
            if (this.n == null) {
                setDragView(this.r);
            }
            if (this.r.getVisibility() != 0) {
                this.t = PanelState.HIDDEN;
            }
            int paddingTop = (size2 - getPaddingTop()) - getPaddingBottom();
            int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
            for (int i8 = 0; i8 < childCount; i8++) {
                View childAt = getChildAt(i8);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (childAt.getVisibility() != 8 || i8 != 0) {
                    if (childAt == this.s) {
                        i5 = (this.l || this.t == PanelState.HIDDEN) ? paddingTop : paddingTop - this.h;
                        i4 = paddingLeft - (layoutParams.leftMargin + layoutParams.rightMargin);
                    } else {
                        i5 = childAt == this.r ? paddingTop - layoutParams.topMargin : paddingTop;
                        i4 = paddingLeft;
                    }
                    if (layoutParams.width == -2) {
                        i6 = MeasureSpec.makeMeasureSpec(i4, Integer.MIN_VALUE);
                    } else if (layoutParams.width == -1) {
                        i6 = MeasureSpec.makeMeasureSpec(i4, 1073741824);
                    } else {
                        i6 = MeasureSpec.makeMeasureSpec(layoutParams.width, 1073741824);
                    }
                    if (layoutParams.height == -2) {
                        i7 = MeasureSpec.makeMeasureSpec(i5, Integer.MIN_VALUE);
                    } else if (layoutParams.height == -1) {
                        i7 = MeasureSpec.makeMeasureSpec(i5, 1073741824);
                    } else {
                        i7 = MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824);
                    }
                    childAt.measure(i6, i7);
                    if (childAt == this.r) {
                        this.w = this.r.getMeasuredHeight() - this.h;
                    }
                }
            }
            setMeasuredDimension(size, size2);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        if (this.G) {
            switch (this.t) {
                case EXPANDED:
                    this.v = 1.0f;
                    break;
                case ANCHORED:
                    this.v = this.x;
                    break;
                case HIDDEN:
                    this.v = a(a((float) BitmapDescriptorFactory.HUE_RED) + (this.k ? this.h : -this.h));
                    break;
                default:
                    this.v = BitmapDescriptorFactory.HUE_RED;
                    break;
            }
        }
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (childAt.getVisibility() != 8 || (i6 != 0 && !this.G)) {
                int measuredHeight = childAt.getMeasuredHeight();
                int a2 = childAt == this.r ? a(this.v) : paddingTop;
                if (!this.k && childAt == this.s && !this.l) {
                    a2 = a(this.v) + this.r.getMeasuredHeight();
                }
                int i7 = layoutParams.leftMargin + paddingLeft;
                childAt.layout(i7, a2, childAt.getMeasuredWidth() + i7, measuredHeight + a2);
            }
        }
        if (this.G) {
            a();
        }
        c();
        this.G = false;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i3 != i5) {
            this.G = true;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.D) {
            this.F.cancel();
            return false;
        }
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        switch (actionMasked) {
            case 0:
                this.y = false;
                this.B = x2;
                this.C = y2;
                break;
            case 1:
            case 3:
                if (this.F.isDragging()) {
                    this.F.processTouchEvent(motionEvent);
                    return true;
                }
                break;
            case 2:
                float abs = Math.abs(x2 - this.B);
                float abs2 = Math.abs(y2 - this.C);
                if ((abs2 > ((float) this.F.getTouchSlop()) && abs > abs2) || !a(this.n, (int) this.B, (int) this.C)) {
                    this.F.cancel();
                    this.y = true;
                    return false;
                }
        }
        return this.F.shouldInterceptTouchEvent(motionEvent);
    }

    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        if (!isEnabled() || !isTouchEnabled()) {
            return super.onTouchEvent(motionEvent);
        }
        try {
            this.F.processTouchEvent(motionEvent);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean dispatchTouchEvent(@NonNull MotionEvent motionEvent) {
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        if (!isEnabled() || !isTouchEnabled() || (this.y && actionMasked != 0)) {
            this.F.cancel();
            return super.dispatchTouchEvent(motionEvent);
        }
        float y2 = motionEvent.getY();
        if (actionMasked == 0) {
            this.D = false;
            this.A = y2;
        } else if (actionMasked == 2) {
            float f2 = y2 - this.A;
            this.A = y2;
            if (!a(this.p, (int) this.B, (int) this.C)) {
                return super.dispatchTouchEvent(motionEvent);
            }
            int i2 = -1;
            if (((float) (this.k ? 1 : -1)) * f2 <= BitmapDescriptorFactory.HUE_RED) {
                if (this.k) {
                    i2 = 1;
                }
                if (f2 * ((float) i2) < BitmapDescriptorFactory.HUE_RED) {
                    if (this.v < 1.0f) {
                        this.D = false;
                        return onTouchEvent(motionEvent);
                    }
                    if (!this.D && this.F.isDragging()) {
                        this.F.cancel();
                        motionEvent.setAction(0);
                    }
                    this.D = true;
                    return super.dispatchTouchEvent(motionEvent);
                }
            } else if (getScrollableViewScrollPosition() > 0) {
                this.D = true;
                return super.dispatchTouchEvent(motionEvent);
            } else {
                if (this.D) {
                    MotionEvent obtain = MotionEvent.obtain(motionEvent);
                    obtain.setAction(3);
                    super.dispatchTouchEvent(obtain);
                    obtain.recycle();
                    motionEvent.setAction(0);
                }
                this.D = false;
                return onTouchEvent(motionEvent);
            }
        } else if (actionMasked == 1 && this.D) {
            this.F.a(0);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    private boolean a(View view, int i2, int i3) {
        if (view == null) {
            return false;
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int[] iArr2 = new int[2];
        getLocationOnScreen(iArr2);
        int i4 = iArr2[0] + i2;
        boolean z2 = true;
        int i5 = iArr2[1] + i3;
        if (i4 < iArr[0] || i4 >= iArr[0] + view.getWidth() || i5 < iArr[1] || i5 >= iArr[1] + view.getHeight()) {
            z2 = false;
        }
        return z2;
    }

    private int getScrollableViewScrollPosition() {
        if (this.p == null) {
            return 0;
        }
        if (this.p instanceof ScrollView) {
            if (this.k) {
                return this.p.getScrollY();
            }
            ScrollView scrollView = (ScrollView) this.p;
            return scrollView.getChildAt(0).getBottom() - (scrollView.getHeight() + scrollView.getScrollY());
        } else if (!(this.p instanceof ListView) || ((ListView) this.p).getChildCount() <= 0) {
            return 0;
        } else {
            ListView listView = (ListView) this.p;
            if (listView.getAdapter() == null) {
                return 0;
            }
            if (this.k) {
                View childAt = listView.getChildAt(0);
                return (listView.getFirstVisiblePosition() * childAt.getHeight()) - childAt.getTop();
            }
            View childAt2 = listView.getChildAt(listView.getChildCount() - 1);
            return ((((listView.getAdapter().getCount() - listView.getLastVisiblePosition()) - 1) * childAt2.getHeight()) + childAt2.getBottom()) - listView.getBottom();
        }
    }

    /* access modifiers changed from: private */
    public int a(float f2) {
        int measuredHeight = this.r != null ? this.r.getMeasuredHeight() : 0;
        int i2 = (int) (f2 * ((float) this.w));
        if (this.k) {
            return ((getMeasuredHeight() - getPaddingBottom()) - this.h) - i2;
        }
        return (getPaddingTop() - measuredHeight) + this.h + i2;
    }

    /* access modifiers changed from: private */
    public float a(int i2) {
        int a2 = a((float) BitmapDescriptorFactory.HUE_RED);
        return (this.k ? (float) (a2 - i2) : (float) (i2 - a2)) / ((float) this.w);
    }

    public PanelState getPanelState() {
        return this.t;
    }

    public void setPanelState(PanelState panelState) {
        if (panelState == null || panelState == PanelState.DRAGGING) {
            throw new IllegalArgumentException("Panel state cannot be null or DRAGGING.");
        } else if (isEnabled() && ((this.G || this.r != null) && panelState != this.t && this.t != PanelState.DRAGGING)) {
            if (!this.G) {
                if (this.t == PanelState.HIDDEN) {
                    this.r.setVisibility(0);
                    requestLayout();
                }
                switch (panelState) {
                    case EXPANDED:
                        a(1.0f, 0);
                        break;
                    case ANCHORED:
                        a(this.x, 0);
                        break;
                    case HIDDEN:
                        a(a(a((float) BitmapDescriptorFactory.HUE_RED) + (this.k ? this.h : -this.h)), 0);
                        break;
                    case COLLAPSED:
                        a((float) BitmapDescriptorFactory.HUE_RED, 0);
                        break;
                }
            } else {
                this.t = panelState;
            }
        }
    }

    /* access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void c() {
        if (this.j > 0) {
            int currentParalaxOffset = getCurrentParalaxOffset();
            if (VERSION.SDK_INT >= 11) {
                this.s.setTranslationY((float) currentParalaxOffset);
            } else {
                AnimatorProxy.wrap(this.s).setTranslationY((float) currentParalaxOffset);
            }
        }
    }

    /* access modifiers changed from: private */
    public void b(int i2) {
        this.u = this.t;
        this.t = PanelState.DRAGGING;
        this.v = a(i2);
        c();
        a(this.r);
        LayoutParams layoutParams = (LayoutParams) this.s.getLayoutParams();
        int height = ((getHeight() - getPaddingBottom()) - getPaddingTop()) - this.h;
        if (this.v <= BitmapDescriptorFactory.HUE_RED && !this.l) {
            layoutParams.height = this.k ? i2 - getPaddingBottom() : ((getHeight() - getPaddingBottom()) - this.r.getMeasuredHeight()) - i2;
            this.s.requestLayout();
        } else if (layoutParams.height != height && !this.l) {
            layoutParams.height = height;
            this.s.requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j2) {
        boolean z2;
        int save = canvas.save(2);
        if (this.r != view) {
            canvas.getClipBounds(this.H);
            if (!this.l) {
                if (this.k) {
                    this.H.bottom = Math.min(this.H.bottom, this.r.getTop());
                } else {
                    this.H.top = Math.max(this.H.top, this.r.getBottom());
                }
            }
            if (this.m) {
                canvas.clipRect(this.H);
            }
            z2 = super.drawChild(canvas, view, j2);
            if (this.e != 0 && this.v > BitmapDescriptorFactory.HUE_RED) {
                this.f.setColor((((int) (((float) ((this.e & ViewCompat.MEASURED_STATE_MASK) >>> 24)) * this.v)) << 24) | (this.e & ViewCompat.MEASURED_SIZE_MASK));
                canvas.drawRect(this.H, this.f);
            }
        } else {
            z2 = super.drawChild(canvas, view, j2);
        }
        canvas.restoreToCount(save);
        return z2;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(float f2, int i2) {
        if (!isEnabled() || this.r == null) {
            return false;
        }
        if (!this.F.smoothSlideViewTo(this.r, this.r.getLeft(), a(f2))) {
            return false;
        }
        b();
        ViewCompat.postInvalidateOnAnimation(this);
        return true;
    }

    public void computeScroll() {
        if (this.F != null && this.F.continueSettling(true)) {
            if (!isEnabled()) {
                this.F.abort();
                return;
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void draw(Canvas canvas) {
        int i2;
        int i3;
        super.draw(canvas);
        if (this.g != null) {
            int right = this.r.getRight();
            if (this.k) {
                i3 = this.r.getTop() - this.i;
                i2 = this.r.getTop();
            } else {
                i3 = this.r.getBottom();
                i2 = this.r.getBottom() + this.i;
            }
            this.g.setBounds(this.r.getLeft(), i3, right, i2);
            this.g.draw(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public boolean canScroll(View view, boolean z2, int i2, int i3, int i4) {
        View view2 = view;
        boolean z3 = true;
        if (view2 instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view2;
            int scrollX = view2.getScrollX();
            int scrollY = view2.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i5 = i3 + scrollX;
                if (i5 >= childAt.getLeft() && i5 < childAt.getRight()) {
                    int i6 = i4 + scrollY;
                    if (i6 >= childAt.getTop() && i6 < childAt.getBottom()) {
                        if (canScroll(childAt, true, i2, i5 - childAt.getLeft(), i6 - childAt.getTop())) {
                            return true;
                        }
                    }
                }
            }
        }
        if (!z2 || !ViewCompat.canScrollHorizontally(view2, -i2)) {
            z3 = false;
        }
        return z3;
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof MarginLayoutParams ? new LayoutParams((MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.t != PanelState.DRAGGING) {
            savedState.a = this.t;
        } else {
            savedState.a = this.u;
        }
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.t = savedState.a != null ? savedState.a : b;
    }
}
