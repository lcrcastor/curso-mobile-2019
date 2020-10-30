package ar.com.santander.rio.mbanking.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
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
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.components.ViewDragHelper.Callback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import cz.msebera.android.httpclient.HttpStatus;

public class SlidingUpPanelLayout extends ViewGroup {
    private static final String a = "SlidingUpPanelLayout";
    private static final int[] b = {16842927};
    private static PanelState c = PanelState.COLLAPSED;
    /* access modifiers changed from: private */
    public int A;
    /* access modifiers changed from: private */
    public boolean B;
    private boolean C;
    private boolean D;
    private float E;
    private float F;
    /* access modifiers changed from: private */
    public float G;
    private PanelSlideListener H;
    private boolean I;
    private Context J;
    private final Paint d;
    private final Drawable e;
    /* access modifiers changed from: private */
    public final ViewDragHelper f;
    private final Rect g;
    private View h;
    private int i;
    private boolean j;
    private float k;
    private float l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    /* access modifiers changed from: private */
    public boolean r;
    private boolean s;
    private View t;
    private int u;
    /* access modifiers changed from: private */
    public View v;
    private View w;
    /* access modifiers changed from: private */
    public PanelState x;
    private PanelState y;
    /* access modifiers changed from: private */
    public float z;

    class DragHelperCallback extends Callback {
        private DragHelperCallback() {
        }

        public boolean tryCaptureView(View view, int i) {
            boolean z = false;
            if (SlidingUpPanelLayout.this.B) {
                return false;
            }
            if (view == SlidingUpPanelLayout.this.v) {
                z = true;
            }
            return z;
        }

        public void onViewDragStateChanged(int i) {
            if (SlidingUpPanelLayout.this.f.getViewDragState() == 0) {
                SlidingUpPanelLayout.this.z = SlidingUpPanelLayout.this.a(SlidingUpPanelLayout.this.v.getTop());
                if (SlidingUpPanelLayout.this.z == 1.0f) {
                    if (SlidingUpPanelLayout.this.x != PanelState.EXPANDED) {
                        SlidingUpPanelLayout.this.a();
                        SlidingUpPanelLayout.this.x = PanelState.EXPANDED;
                        SlidingUpPanelLayout.this.b(SlidingUpPanelLayout.this.v);
                    }
                } else if (SlidingUpPanelLayout.this.z == BitmapDescriptorFactory.HUE_RED) {
                    if (SlidingUpPanelLayout.this.x != PanelState.COLLAPSED) {
                        SlidingUpPanelLayout.this.x = PanelState.COLLAPSED;
                        SlidingUpPanelLayout.this.c(SlidingUpPanelLayout.this.v);
                    }
                } else if (SlidingUpPanelLayout.this.z < BitmapDescriptorFactory.HUE_RED) {
                    SlidingUpPanelLayout.this.x = PanelState.HIDDEN;
                    SlidingUpPanelLayout.this.v.setVisibility(4);
                    SlidingUpPanelLayout.this.e(SlidingUpPanelLayout.this.v);
                } else if (SlidingUpPanelLayout.this.x != PanelState.ANCHORED) {
                    SlidingUpPanelLayout.this.a();
                    SlidingUpPanelLayout.this.x = PanelState.ANCHORED;
                    SlidingUpPanelLayout.this.d(SlidingUpPanelLayout.this.v);
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
            if (SlidingUpPanelLayout.this.r) {
                f2 = -f2;
            }
            if (f2 > BitmapDescriptorFactory.HUE_RED) {
                i = SlidingUpPanelLayout.this.a(1.0f);
            } else if (f2 < BitmapDescriptorFactory.HUE_RED) {
                i = SlidingUpPanelLayout.this.a((float) BitmapDescriptorFactory.HUE_RED);
            } else if (SlidingUpPanelLayout.this.G != 1.0f && SlidingUpPanelLayout.this.z >= (SlidingUpPanelLayout.this.G + 1.0f) / 2.0f) {
                i = SlidingUpPanelLayout.this.a(1.0f);
            } else if (SlidingUpPanelLayout.this.G == 1.0f && SlidingUpPanelLayout.this.z >= 0.5f) {
                i = SlidingUpPanelLayout.this.a(1.0f);
            } else if (SlidingUpPanelLayout.this.G != 1.0f && SlidingUpPanelLayout.this.z >= SlidingUpPanelLayout.this.G) {
                i = SlidingUpPanelLayout.this.a(SlidingUpPanelLayout.this.G);
            } else if (SlidingUpPanelLayout.this.G == 1.0f || SlidingUpPanelLayout.this.z < SlidingUpPanelLayout.this.G / 2.0f) {
                i = SlidingUpPanelLayout.this.a((float) BitmapDescriptorFactory.HUE_RED);
            } else {
                i = SlidingUpPanelLayout.this.a(SlidingUpPanelLayout.this.G);
            }
            SlidingUpPanelLayout.this.f.settleCapturedViewAt(view.getLeft(), i);
            SlidingUpPanelLayout.this.invalidate();
        }

        public int getViewVerticalDragRange(View view) {
            return SlidingUpPanelLayout.this.A;
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            int b = SlidingUpPanelLayout.this.a((float) BitmapDescriptorFactory.HUE_RED);
            int b2 = SlidingUpPanelLayout.this.a(1.0f);
            if (SlidingUpPanelLayout.this.r) {
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
        this.J = context;
    }

    public SlidingUpPanelLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        this.J = context;
    }

    public SlidingUpPanelLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.d = new Paint();
        this.g = new Rect();
        this.i = -1;
        this.j = false;
        this.m = HttpStatus.SC_BAD_REQUEST;
        this.n = 0;
        this.o = -1;
        this.p = -1;
        this.q = -1;
        this.s = false;
        this.u = -1;
        this.x = PanelState.COLLAPSED;
        this.y = PanelState.ANCHORED;
        this.G = 1.0f;
        this.I = true;
        this.J = context;
        if (isInEditMode()) {
            this.e = null;
            this.f = null;
            return;
        }
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, b);
            if (obtainStyledAttributes != null) {
                setGravity(obtainStyledAttributes.getInt(0, 0));
                obtainStyledAttributes.recycle();
            }
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R.styleable.SlidingUpPanelLayout);
            if (obtainStyledAttributes2 != null) {
                this.o = obtainStyledAttributes2.getDimensionPixelSize(8, -1);
                this.p = obtainStyledAttributes2.getDimensionPixelSize(11, -1);
                this.q = obtainStyledAttributes2.getDimensionPixelSize(9, -1);
                this.i = obtainStyledAttributes2.getResourceId(0, -1);
                this.m = obtainStyledAttributes2.getInt(5, HttpStatus.SC_BAD_REQUEST);
                this.n = obtainStyledAttributes2.getColor(4, 0);
                this.u = obtainStyledAttributes2.getResourceId(3, -1);
                this.s = obtainStyledAttributes2.getBoolean(7, false);
                this.G = obtainStyledAttributes2.getFloat(1, 1.0f);
                this.x = PanelState.values()[obtainStyledAttributes2.getInt(6, c.ordinal())];
                obtainStyledAttributes2.recycle();
            }
        }
        float f2 = context.getResources().getDisplayMetrics().density;
        if (this.o == -1) {
            this.o = (int) ((68.0f * f2) + 0.5f);
        }
        if (this.p == -1) {
            this.p = (int) ((4.0f * f2) + 0.5f);
        }
        if (this.q == -1) {
            this.q = (int) (BitmapDescriptorFactory.HUE_RED * f2);
        }
        this.e = null;
        setWillNotDraw(false);
        this.f = ViewDragHelper.create(this, 0.5f, new DragHelperCallback());
        this.f.setMinVelocity(((float) this.m) * f2);
        this.C = true;
    }

    private static boolean f(View view) {
        Drawable background = view.getBackground();
        return background != null && background.getOpacity() == -1;
    }

    public boolean isShowing() {
        return (this.x == PanelState.COLLAPSED || this.x == PanelState.HIDDEN) ? false : true;
    }

    public boolean isAnchored() {
        return this.x == PanelState.ANCHORED;
    }

    public boolean isExpanded() {
        return this.x == PanelState.EXPANDED;
    }

    public void openPanel() {
        if (getAnchorPoint() == 1.0f) {
            setAnchorPoint(0.7f);
        }
        setPanelState(this.y);
    }

    public void closePanel() {
        if (getPanelState() == PanelState.DRAGGING) {
            this.y = PanelState.EXPANDED;
        } else {
            this.y = getPanelState();
        }
        setPanelState(PanelState.HIDDEN);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        if (this.u != -1) {
            setDragView(findViewById(this.u));
        }
        if (this.i != -1) {
            this.h = findViewById(this.i);
        }
    }

    private boolean a(int i2, int i3) {
        if (this.h == null) {
            return false;
        }
        int[] iArr = new int[2];
        this.h.getLocationOnScreen(iArr);
        int[] iArr2 = new int[2];
        getLocationOnScreen(iArr2);
        int i4 = iArr2[0] + i2;
        boolean z2 = true;
        int i5 = iArr2[1] + i3;
        if (i4 < iArr[0] || i4 >= iArr[0] + this.h.getWidth() || i5 < iArr[1] || i5 >= iArr[1] + this.h.getHeight()) {
            z2 = false;
        }
        return z2;
    }

    public void setGravity(int i2) {
        if (i2 == 48 || i2 == 80) {
            this.r = i2 == 80;
            if (!this.I) {
                requestLayout();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("gravity must be set to either top or bottom");
    }

    public int getFirstChildTopOffset(ListView listView) {
        View childAt = listView.getChildAt(0);
        if (childAt == null) {
            return 0;
        }
        return listView.getPaddingTop() - childAt.getTop();
    }

    public boolean dispatchTouchEvent(@NonNull MotionEvent motionEvent) {
        if (this.h == null) {
            return super.dispatchTouchEvent(motionEvent);
        }
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        boolean z2 = false;
        if (actionMasked == 0) {
            this.f.shouldInterceptTouchEvent(motionEvent);
            this.k = x2;
            this.E = x2;
            this.l = y2;
            this.F = y2;
            this.j = false;
        } else if (actionMasked == 2) {
            float f2 = this.k;
            float f3 = y2 - this.l;
            this.k = x2;
            this.l = y2;
            if (!a((int) x2, (int) y2)) {
                if (onTouchEvent(motionEvent) || super.dispatchTouchEvent(motionEvent)) {
                    z2 = true;
                }
                return z2;
            } else if (f3 > BitmapDescriptorFactory.HUE_RED) {
                if (((ListView) this.h).getFirstVisiblePosition() > 0 || getFirstChildTopOffset((ListView) this.h) > 0) {
                    this.j = true;
                    return super.dispatchTouchEvent(motionEvent);
                }
                if (this.j) {
                    MotionEvent obtain = MotionEvent.obtain(motionEvent);
                    obtain.setAction(1);
                    super.dispatchTouchEvent(obtain);
                    obtain.recycle();
                    motionEvent.setAction(0);
                }
                this.j = false;
                return onTouchEvent(motionEvent);
            } else if (f3 < BitmapDescriptorFactory.HUE_RED) {
                if (this.z < 1.0f) {
                    this.j = false;
                    if (onTouchEvent(motionEvent) || super.dispatchTouchEvent(motionEvent)) {
                        z2 = true;
                    }
                    return z2;
                }
                if (!this.j) {
                    this.f.cancel();
                    motionEvent.setAction(0);
                }
                this.j = true;
                return super.dispatchTouchEvent(motionEvent);
            }
        } else if ((actionMasked == 3 || actionMasked == 1) && !this.j) {
            float f4 = x2 - this.E;
            float f5 = y2 - this.F;
            int touchSlop = this.f.getTouchSlop();
            if (!this.D || (f4 * f4) + (f5 * f5) >= ((float) (touchSlop * touchSlop))) {
                return onTouchEvent(motionEvent);
            }
            return super.dispatchTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public int getCoveredFadeColor() {
        return this.n;
    }

    public void setCoveredFadeColor(int i2) {
        this.n = i2;
        invalidate();
    }

    public boolean isTouchEnabled() {
        return this.C && this.v != null;
    }

    public void setTouchEnabled(boolean z2) {
        this.C = z2;
    }

    public int getShadowHeight() {
        return this.p;
    }

    public void setShadowHeight(int i2) {
        this.p = i2;
        if (!this.I) {
            invalidate();
        }
    }

    public int getPanelHeight() {
        return this.o;
    }

    public void setPanelHeight(int i2) {
        this.o = i2;
        if (!this.I) {
            requestLayout();
        }
    }

    public int getCurrentParalaxOffset() {
        int max = (int) (((float) this.q) * Math.max(this.z, BitmapDescriptorFactory.HUE_RED));
        return this.r ? -max : max;
    }

    public void setParalaxOffset(int i2) {
        this.q = i2;
        if (!this.I) {
            requestLayout();
        }
    }

    public int getMinFlingVelocity() {
        return this.m;
    }

    public void setMinFlingVelocity(int i2) {
        this.m = i2;
    }

    public void setPanelSlideListener(PanelSlideListener panelSlideListener) {
        this.H = panelSlideListener;
    }

    public void setDragView(View view) {
        if (this.t != null) {
            this.t.setOnClickListener(null);
        }
        this.t = view;
        if (this.t != null) {
            this.t.setClickable(true);
            this.t.setFocusable(false);
            this.t.setFocusableInTouchMode(false);
            this.t.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (SlidingUpPanelLayout.this.isEnabled() && SlidingUpPanelLayout.this.isTouchEnabled()) {
                        if (SlidingUpPanelLayout.this.x == PanelState.EXPANDED || SlidingUpPanelLayout.this.x == PanelState.ANCHORED) {
                            SlidingUpPanelLayout.this.setPanelState(PanelState.COLLAPSED);
                        } else if (SlidingUpPanelLayout.this.G < 1.0f) {
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
        this.u = i2;
        setDragView(findViewById(i2));
    }

    public float getAnchorPoint() {
        return this.G;
    }

    public void setAnchorPoint(float f2) {
        if (f2 > BitmapDescriptorFactory.HUE_RED && f2 <= 1.0f) {
            this.G = f2;
        }
    }

    public boolean isOverlayed() {
        return this.s;
    }

    public void setOverlayed(boolean z2) {
        this.s = z2;
    }

    /* access modifiers changed from: 0000 */
    public void a(View view) {
        if (this.H != null) {
            this.H.onPanelSlide(view, this.z);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(View view) {
        if (this.H != null) {
            this.H.onPanelExpanded(view);
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void c(View view) {
        if (this.H != null) {
            this.H.onPanelCollapsed(view);
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void d(View view) {
        if (this.H != null) {
            this.H.onPanelAnchored(view);
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void e(View view) {
        if (this.H != null) {
            this.H.onPanelHidden(view);
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
            if (this.v == null || !f(this.v)) {
                i5 = 0;
                i4 = 0;
                i3 = 0;
                i2 = 0;
            } else {
                i5 = this.v.getLeft();
                i4 = this.v.getRight();
                i3 = this.v.getTop();
                i2 = this.v.getBottom();
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

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.I = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.I = true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int i4;
        int i5;
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        MeasureSpec.getMode(i3);
        int size2 = MeasureSpec.getSize(i3);
        if (mode != 1073741824) {
            throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
        }
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new IllegalStateException("Sliding up panel layout must have exactly 2 children!");
        }
        this.w = getChildAt(0);
        this.v = getChildAt(1);
        if (this.t == null) {
            setDragView(this.v);
        }
        if (this.v.getVisibility() != 0) {
            this.x = PanelState.HIDDEN;
        }
        int paddingTop = (size2 - getPaddingTop()) - getPaddingBottom();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (childAt.getVisibility() != 8 || i6 != 0) {
                int i7 = (childAt != this.w || this.s || this.x == PanelState.HIDDEN) ? paddingTop : paddingTop - this.o;
                if (layoutParams.width == -2) {
                    i4 = MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE);
                } else if (layoutParams.width == -1) {
                    i4 = MeasureSpec.makeMeasureSpec(size, 1073741824);
                } else {
                    i4 = MeasureSpec.makeMeasureSpec(layoutParams.width, 1073741824);
                }
                if (layoutParams.height == -2) {
                    i5 = MeasureSpec.makeMeasureSpec(i7, Integer.MIN_VALUE);
                } else if (layoutParams.height == -1) {
                    i5 = MeasureSpec.makeMeasureSpec(i7, 1073741824);
                } else {
                    i5 = MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824);
                }
                childAt.measure(i4, i5);
                if (childAt == this.v) {
                    this.A = this.v.getMeasuredHeight() - this.o;
                }
            }
        }
        setMeasuredDimension(size, size2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        if (this.I) {
            switch (this.x) {
                case EXPANDED:
                    this.z = 1.0f;
                    break;
                case ANCHORED:
                    this.z = this.G;
                    break;
                case HIDDEN:
                    this.z = a(a((float) BitmapDescriptorFactory.HUE_RED) + (this.r ? this.o : -this.o));
                    break;
                default:
                    this.z = BitmapDescriptorFactory.HUE_RED;
                    break;
            }
        }
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8 || (i6 != 0 && !this.I)) {
                int measuredHeight = childAt.getMeasuredHeight();
                int a2 = childAt == this.v ? a(this.z) : paddingTop;
                if (!this.r && childAt == this.w && !this.s) {
                    a2 = a(this.z) + this.v.getMeasuredHeight();
                }
                childAt.layout(paddingLeft, a2, childAt.getMeasuredWidth() + paddingLeft, measuredHeight + a2);
            }
        }
        if (this.I) {
            a();
        }
        this.I = false;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i3 != i5) {
            this.I = true;
        }
    }

    public void setEnableDragViewTouchEvents(boolean z2) {
        this.D = z2;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled() || !isTouchEnabled()) {
            return super.onTouchEvent(motionEvent);
        }
        try {
            this.f.processTouchEvent(motionEvent);
            if ((motionEvent.getAction() & 255) == 1) {
                float x2 = motionEvent.getX();
                float y2 = motionEvent.getY();
                float f2 = x2 - this.E;
                float f3 = y2 - this.F;
                int touchSlop = this.f.getTouchSlop();
                View view = this.t != null ? this.t : this.v;
                if ((f2 * f2) + (f3 * f3) < ((float) (touchSlop * touchSlop))) {
                    int i2 = (int) x2;
                    int i3 = (int) y2;
                    if (b(i2, i3) && !a(i2, i3)) {
                        view.playSoundEffect(0);
                        if (isExpanded() || isAnchored()) {
                            setPanelState(PanelState.COLLAPSED);
                        } else {
                            setPanelState(PanelState.ANCHORED);
                        }
                    }
                }
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    private boolean b(int i2, int i3) {
        if (this.t == null) {
            return false;
        }
        int[] iArr = new int[2];
        this.t.getLocationOnScreen(iArr);
        int[] iArr2 = new int[2];
        getLocationOnScreen(iArr2);
        int i4 = iArr2[0] + i2;
        boolean z2 = true;
        int i5 = iArr2[1] + i3;
        if (i4 < iArr[0] || i4 >= iArr[0] + this.t.getWidth() || i5 < iArr[1] || i5 >= iArr[1] + this.t.getHeight()) {
            z2 = false;
        }
        return z2;
    }

    /* access modifiers changed from: private */
    public int a(float f2) {
        int measuredHeight = this.v != null ? this.v.getMeasuredHeight() : 0;
        int i2 = (int) (f2 * ((float) this.A));
        if (this.r) {
            return ((getMeasuredHeight() - getPaddingBottom()) - this.o) - i2;
        }
        return (getPaddingTop() - measuredHeight) + this.o + i2;
    }

    /* access modifiers changed from: private */
    public float a(int i2) {
        int a2 = a((float) BitmapDescriptorFactory.HUE_RED);
        return (this.r ? (float) (a2 - i2) : (float) (i2 - a2)) / ((float) this.A);
    }

    public PanelState getPanelState() {
        return this.x;
    }

    public void setPanelState(PanelState panelState) {
        if (panelState == null || panelState == PanelState.DRAGGING) {
            throw new IllegalArgumentException("Panel state cannot be null or DRAGGING.");
        } else if (isEnabled() && this.v != null && panelState != this.x) {
            if (!this.I) {
                if (this.x == PanelState.HIDDEN) {
                    this.v.setVisibility(0);
                    requestLayout();
                }
                switch (panelState) {
                    case EXPANDED:
                        a(1.0f, 0);
                        break;
                    case ANCHORED:
                        a(this.G, 0);
                        break;
                    case HIDDEN:
                        a(a(a((float) BitmapDescriptorFactory.HUE_RED) + (this.r ? this.o : -this.o)), 0);
                        break;
                    case COLLAPSED:
                        a((float) BitmapDescriptorFactory.HUE_RED, 0);
                        break;
                }
            } else {
                this.x = panelState;
            }
        }
    }

    private PanelState getPreviousPanelState() {
        return this.y;
    }

    /* access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void b(int i2) {
        this.x = PanelState.DRAGGING;
        this.z = a(i2);
        if (this.q > 0 && this.z >= BitmapDescriptorFactory.HUE_RED) {
            this.w.setTranslationY((float) getCurrentParalaxOffset());
        }
        a(this.v);
        LayoutParams layoutParams = (LayoutParams) this.w.getLayoutParams();
        int height = ((getHeight() - getPaddingBottom()) - getPaddingTop()) - this.o;
        if (this.z <= BitmapDescriptorFactory.HUE_RED && !this.s) {
            if (this.r) {
                layoutParams.height = i2 - getPaddingBottom();
            } else {
                layoutParams.height = ((getHeight() - getPaddingBottom()) - this.v.getMeasuredHeight()) - i2;
            }
            this.w.requestLayout();
        } else if (this.z > BitmapDescriptorFactory.HUE_RED && !this.s) {
            layoutParams.height = i2 + ((int) ((getResources().getDisplayMetrics().density * 58.0f) + 0.5f));
            this.w.requestLayout();
        } else if (layoutParams.height != height && !this.s) {
            layoutParams.height = height;
            this.w.requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j2) {
        boolean z2;
        int save = canvas.save();
        if (this.v != view) {
            canvas.getClipBounds(this.g);
            if (!this.s) {
                if (this.r) {
                    this.g.bottom = Math.min(this.g.bottom, this.v.getTop());
                } else {
                    this.g.top = Math.max(this.g.top, this.v.getBottom());
                }
            }
            canvas.clipRect(this.g);
            z2 = super.drawChild(canvas, view, j2);
            if (this.n != 0 && this.z > BitmapDescriptorFactory.HUE_RED) {
                this.d.setColor((((int) (((float) ((this.n & ViewCompat.MEASURED_STATE_MASK) >>> 24)) * this.z)) << 24) | (this.n & ViewCompat.MEASURED_SIZE_MASK));
                canvas.drawRect(this.g, this.d);
            }
        } else {
            z2 = super.drawChild(canvas, view, j2);
        }
        canvas.restoreToCount(save);
        return z2;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(float f2, int i2) {
        if (!isEnabled()) {
            return false;
        }
        if (!this.f.smoothSlideViewTo(this.v, this.v.getLeft(), a(f2))) {
            return false;
        }
        b();
        ViewCompat.postInvalidateOnAnimation(this);
        return true;
    }

    public void computeScroll() {
        if (this.f != null && this.f.continueSettling(true)) {
            if (!isEnabled()) {
                this.f.abort();
                return;
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void draw(Canvas canvas) {
        int i2;
        int i3;
        super.draw(canvas);
        if (this.e != null) {
            int right = this.v.getRight();
            if (this.r) {
                i3 = this.v.getTop() - this.p;
                i2 = this.v.getTop();
            } else {
                i3 = this.v.getBottom();
                i2 = this.v.getBottom() + this.p;
            }
            this.e.setBounds(this.v.getLeft(), i3, right, i2);
            this.e.draw(canvas);
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
        savedState.a = this.x;
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.x = savedState.a;
    }
}
