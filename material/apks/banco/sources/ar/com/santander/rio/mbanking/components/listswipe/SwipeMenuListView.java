package ar.com.santander.rio.mbanking.components.listswipe;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SwipeMenuListView extends ListView {
    private int a = 5;
    private int b = 3;
    private float c;
    private float d;
    private int e;
    private int f;
    /* access modifiers changed from: private */
    public SwipeMenuLayout g;
    private OnSwipeListener h;
    /* access modifiers changed from: private */
    public SwipeMenuCreator i;
    /* access modifiers changed from: private */
    public OnMenuItemClickListener j;
    private Interpolator k;
    private Interpolator l;

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(int i, SwipeMenu swipeMenu, int i2);
    }

    public interface OnSwipeListener {
        void onSwipeEnd(int i);

        void onSwipeStart(int i);
    }

    public SwipeMenuListView(Context context) {
        super(context);
        a();
    }

    public SwipeMenuListView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a();
    }

    public SwipeMenuListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    private void a() {
        this.b = a(this.b);
        this.a = a(this.a);
        this.e = 0;
    }

    public void setAdapter(ListAdapter listAdapter) {
        super.setAdapter(new SwipeMenuAdapter(getContext(), listAdapter) {
            public void createMenu(SwipeMenu swipeMenu) {
                if (SwipeMenuListView.this.i != null) {
                    SwipeMenuListView.this.i.create(swipeMenu);
                }
            }

            public void onItemClick(SwipeMenuView swipeMenuView, SwipeMenu swipeMenu, int i) {
                boolean onMenuItemClick = SwipeMenuListView.this.j != null ? SwipeMenuListView.this.j.onMenuItemClick(swipeMenuView.getPosition(), swipeMenu, i) : false;
                if (SwipeMenuListView.this.g != null && !onMenuItemClick) {
                    SwipeMenuListView.this.g.smoothCloseMenu();
                }
            }
        });
    }

    public void setCloseInterpolator(Interpolator interpolator) {
        this.k = interpolator;
    }

    public void setOpenInterpolator(Interpolator interpolator) {
        this.l = interpolator;
    }

    public Interpolator getOpenInterpolator() {
        return this.l;
    }

    public Interpolator getCloseInterpolator() {
        return this.k;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0 && this.g == null) {
            return super.onTouchEvent(motionEvent);
        }
        switch (motionEvent.getAction()) {
            case 0:
                int i2 = this.f;
                this.c = motionEvent.getX();
                this.d = motionEvent.getY();
                this.e = 0;
                this.f = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
                if (this.f != i2 || this.g == null || !this.g.isOpen()) {
                    View childAt = getChildAt(this.f - getFirstVisiblePosition());
                    if (this.g == null || !this.g.isOpen()) {
                        if (childAt instanceof SwipeMenuLayout) {
                            this.g = (SwipeMenuLayout) childAt;
                        }
                        if (this.g != null) {
                            this.g.onSwipe(motionEvent);
                            break;
                        }
                    } else {
                        this.g.smoothCloseMenu();
                        this.g = null;
                        return super.onTouchEvent(motionEvent);
                    }
                } else {
                    this.e = 1;
                    this.g.onSwipe(motionEvent);
                    return true;
                }
                break;
            case 1:
                if (this.e == 1) {
                    if (this.g != null) {
                        this.g.onSwipe(motionEvent);
                        if (!this.g.isOpen()) {
                            this.f = -1;
                            this.g = null;
                        }
                    }
                    if (this.h != null) {
                        this.h.onSwipeEnd(this.f);
                    }
                    motionEvent.setAction(3);
                    super.onTouchEvent(motionEvent);
                    return true;
                }
                break;
            case 2:
                float abs = Math.abs(motionEvent.getY() - this.d);
                float abs2 = Math.abs(motionEvent.getX() - this.c);
                if (this.e != 1) {
                    if (this.e == 0) {
                        if (Math.abs(abs) <= ((float) this.a)) {
                            if (abs2 > ((float) this.b)) {
                                this.e = 1;
                                if (this.h != null) {
                                    this.h.onSwipeStart(this.f);
                                    break;
                                }
                            }
                        } else {
                            this.e = 2;
                            break;
                        }
                    }
                } else {
                    if (this.g != null) {
                        this.g.onSwipe(motionEvent);
                    }
                    getSelector().setState(new int[]{0});
                    motionEvent.setAction(3);
                    super.onTouchEvent(motionEvent);
                    return true;
                }
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void smoothOpenMenu(int i2) {
        if (i2 >= getFirstVisiblePosition() && i2 <= getLastVisiblePosition()) {
            View childAt = getChildAt(i2 - getFirstVisiblePosition());
            if (childAt instanceof SwipeMenuLayout) {
                this.f = i2;
                if (this.g != null && this.g.isOpen()) {
                    this.g.smoothCloseMenu();
                }
                this.g = (SwipeMenuLayout) childAt;
                this.g.smoothOpenMenu();
            }
        }
    }

    private int a(int i2) {
        return (int) TypedValue.applyDimension(1, (float) i2, getContext().getResources().getDisplayMetrics());
    }

    public void setMenuCreator(SwipeMenuCreator swipeMenuCreator) {
        this.i = swipeMenuCreator;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.j = onMenuItemClickListener;
    }

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.h = onSwipeListener;
    }
}
