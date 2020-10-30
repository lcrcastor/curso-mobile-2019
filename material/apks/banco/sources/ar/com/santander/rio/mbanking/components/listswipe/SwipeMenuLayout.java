package ar.com.santander.rio.mbanking.components.listswipe;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Interpolator;
import android.widget.AbsListView.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import cz.msebera.android.httpclient.HttpStatus;

public class SwipeMenuLayout extends FrameLayout {
    private View a;
    private SwipeMenuView b;
    private int c;
    private int d;
    private GestureDetectorCompat e;
    private OnGestureListener f;
    /* access modifiers changed from: private */
    public boolean g;
    /* access modifiers changed from: private */
    public int h;
    /* access modifiers changed from: private */
    public int i;
    private ScrollerCompat j;
    private ScrollerCompat k;
    private int l;
    private int m;
    private Interpolator n;
    private Interpolator o;

    public SwipeMenuLayout(View view, SwipeMenuView swipeMenuView) {
        this(view, swipeMenuView, null, null);
    }

    public SwipeMenuLayout(View view, SwipeMenuView swipeMenuView, Interpolator interpolator, Interpolator interpolator2) {
        super(view.getContext());
        this.d = 0;
        this.h = b(15);
        this.i = -b((int) HttpStatus.SC_INTERNAL_SERVER_ERROR);
        this.n = interpolator;
        this.o = interpolator2;
        this.a = view;
        this.b = swipeMenuView;
        this.b.setLayout(this);
        a();
    }

    public int getPosition() {
        return this.m;
    }

    public void setPosition(int i2) {
        this.m = i2;
        this.b.setPosition(i2);
    }

    private void a() {
        setLayoutParams(new LayoutParams(-1, -2));
        this.f = new SimpleOnGestureListener() {
            public boolean onDown(MotionEvent motionEvent) {
                SwipeMenuLayout.this.g = false;
                return true;
            }

            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (motionEvent.getX() - motionEvent2.getX() > ((float) SwipeMenuLayout.this.h) && f < ((float) SwipeMenuLayout.this.i)) {
                    SwipeMenuLayout.this.g = true;
                }
                return super.onFling(motionEvent, motionEvent2, f, f2);
            }
        };
        this.e = new GestureDetectorCompat(getContext(), this.f);
        if (this.n != null) {
            this.k = ScrollerCompat.create(getContext(), this.n);
        } else {
            this.k = ScrollerCompat.create(getContext());
        }
        if (this.o != null) {
            this.j = ScrollerCompat.create(getContext(), this.o);
        } else {
            this.j = ScrollerCompat.create(getContext());
        }
        this.a.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
        if (this.a.getId() < 1) {
            this.a.setId(1);
        }
        this.b.setId(2);
        this.b.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        addView(this.a);
        addView(this.b);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
    }

    public boolean onSwipe(MotionEvent motionEvent) {
        this.e.onTouchEvent(motionEvent);
        switch (motionEvent.getAction()) {
            case 0:
                this.c = (int) motionEvent.getX();
                this.g = false;
                break;
            case 1:
                if (this.g || ((float) this.c) - motionEvent.getX() > ((float) (this.b.getWidth() / 2))) {
                    smoothOpenMenu();
                    break;
                } else {
                    smoothCloseMenu();
                    return false;
                }
            case 2:
                int x = (int) (((float) this.c) - motionEvent.getX());
                if (this.d == 1) {
                    x += this.b.getWidth();
                }
                a(x);
                break;
        }
        return true;
    }

    public boolean isOpen() {
        return this.d == 1;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    private void a(int i2) {
        if (i2 > this.b.getWidth()) {
            i2 = this.b.getWidth();
        }
        if (i2 < 0) {
            i2 = 0;
        }
        this.a.layout(-i2, this.a.getTop(), this.a.getWidth() - i2, getMeasuredHeight());
        ((LinearLayout) ((LinearLayout) this.a).getChildAt(0)).layout(i2, this.a.getTop(), this.a.getWidth() + i2, getMeasuredHeight());
        this.b.layout(this.a.getWidth() - i2, this.b.getTop(), (this.a.getWidth() + this.b.getWidth()) - i2, this.b.getBottom());
    }

    public void computeScroll() {
        if (this.d == 1) {
            if (this.j.computeScrollOffset()) {
                a(this.j.getCurrX());
                postInvalidate();
            }
        } else if (this.k.computeScrollOffset()) {
            a(this.l - this.k.getCurrX());
            postInvalidate();
        }
    }

    public void smoothCloseMenu() {
        this.d = 0;
        this.l = -this.a.getLeft();
        this.k.startScroll(0, 0, this.l, 0, 350);
        postInvalidate();
    }

    public void smoothOpenMenu() {
        this.d = 1;
        this.j.startScroll(-this.a.getLeft(), 0, this.b.getWidth(), 0, 350);
        postInvalidate();
    }

    public void closeMenu() {
        if (this.k.computeScrollOffset()) {
            this.k.abortAnimation();
        }
        if (this.d == 1) {
            this.d = 0;
            a(0);
        }
    }

    public void openMenu() {
        if (this.d == 0) {
            this.d = 1;
            a(this.b.getWidth());
        }
    }

    public View getContentView() {
        return this.a;
    }

    public SwipeMenuView getMenuView() {
        return this.b;
    }

    private int b(int i2) {
        return (int) TypedValue.applyDimension(1, (float) i2, getContext().getResources().getDisplayMetrics());
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        this.b.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        this.a.layout(0, 0, getMeasuredWidth(), this.a.getMeasuredHeight());
        this.b.layout(getMeasuredWidth(), 0, getMeasuredWidth() + this.b.getMeasuredWidth(), this.a.getMeasuredHeight());
    }

    public void setMenuHeight(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("pos = ");
        sb.append(this.m);
        sb.append(", height = ");
        sb.append(i2);
        Log.i("byz", sb.toString());
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.b.getLayoutParams();
        if (layoutParams.height != i2) {
            layoutParams.height = i2;
            this.b.setLayoutParams(this.b.getLayoutParams());
        }
    }
}
