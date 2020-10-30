package android.support.design.widget;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class SwipeDismissBehavior<V extends View> extends Behavior<V> {
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    public static final int SWIPE_DIRECTION_ANY = 2;
    public static final int SWIPE_DIRECTION_END_TO_START = 1;
    public static final int SWIPE_DIRECTION_START_TO_END = 0;
    private boolean a;
    ViewDragHelper b;
    OnDismissListener c;
    int d = 2;
    float e = 0.5f;
    float f = BitmapDescriptorFactory.HUE_RED;
    float g = 0.5f;
    private float h = BitmapDescriptorFactory.HUE_RED;
    private boolean i;
    private final Callback j = new Callback() {
        private int b;
        private int c = -1;

        public boolean tryCaptureView(View view, int i) {
            return this.c == -1 && SwipeDismissBehavior.this.canSwipeDismissView(view);
        }

        public void onViewCaptured(View view, int i) {
            this.c = i;
            this.b = view.getLeft();
            ViewParent parent = view.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }

        public void onViewDragStateChanged(int i) {
            if (SwipeDismissBehavior.this.c != null) {
                SwipeDismissBehavior.this.c.onDragStateChanged(i);
            }
        }

        public void onViewReleased(View view, float f, float f2) {
            boolean z;
            int i;
            this.c = -1;
            int width = view.getWidth();
            if (a(view, f)) {
                i = view.getLeft() < this.b ? this.b - width : this.b + width;
                z = true;
            } else {
                i = this.b;
                z = false;
            }
            if (SwipeDismissBehavior.this.b.settleCapturedViewAt(i, view.getTop())) {
                ViewCompat.postOnAnimation(view, new SettleRunnable(view, z));
            } else if (z && SwipeDismissBehavior.this.c != null) {
                SwipeDismissBehavior.this.c.onDismiss(view);
            }
        }

        private boolean a(View view, float f) {
            boolean z = false;
            if (f != BitmapDescriptorFactory.HUE_RED) {
                boolean z2 = ViewCompat.getLayoutDirection(view) == 1;
                if (SwipeDismissBehavior.this.d == 2) {
                    return true;
                }
                if (SwipeDismissBehavior.this.d == 0) {
                    return z2 ? z : z;
                    z = true;
                } else if (SwipeDismissBehavior.this.d != 1) {
                    return false;
                } else {
                    if (!z2 ? f < BitmapDescriptorFactory.HUE_RED : f > BitmapDescriptorFactory.HUE_RED) {
                        z = true;
                    }
                    return z;
                }
            } else {
                if (Math.abs(view.getLeft() - this.b) >= Math.round(((float) view.getWidth()) * SwipeDismissBehavior.this.e)) {
                    z = true;
                }
                return z;
            }
        }

        public int getViewHorizontalDragRange(View view) {
            return view.getWidth();
        }

        public int clampViewPositionHorizontal(View view, int i, int i2) {
            int i3;
            int i4;
            boolean z = ViewCompat.getLayoutDirection(view) == 1;
            if (SwipeDismissBehavior.this.d == 0) {
                if (z) {
                    i3 = this.b - view.getWidth();
                    i4 = this.b;
                } else {
                    i3 = this.b;
                    i4 = view.getWidth() + this.b;
                }
            } else if (SwipeDismissBehavior.this.d != 1) {
                i3 = this.b - view.getWidth();
                i4 = view.getWidth() + this.b;
            } else if (z) {
                i3 = this.b;
                i4 = view.getWidth() + this.b;
            } else {
                i3 = this.b - view.getWidth();
                i4 = this.b;
            }
            return SwipeDismissBehavior.a(i3, i, i4);
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            return view.getTop();
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            float width = ((float) this.b) + (((float) view.getWidth()) * SwipeDismissBehavior.this.f);
            float width2 = ((float) this.b) + (((float) view.getWidth()) * SwipeDismissBehavior.this.g);
            float f = (float) i;
            if (f <= width) {
                view.setAlpha(1.0f);
            } else if (f >= width2) {
                view.setAlpha(BitmapDescriptorFactory.HUE_RED);
            } else {
                view.setAlpha(SwipeDismissBehavior.a((float) BitmapDescriptorFactory.HUE_RED, 1.0f - SwipeDismissBehavior.b(width, width2, f), 1.0f));
            }
        }
    };

    public interface OnDismissListener {
        void onDismiss(View view);

        void onDragStateChanged(int i);
    }

    class SettleRunnable implements Runnable {
        private final View b;
        private final boolean c;

        SettleRunnable(View view, boolean z) {
            this.b = view;
            this.c = z;
        }

        public void run() {
            if (SwipeDismissBehavior.this.b != null && SwipeDismissBehavior.this.b.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.b, this);
            } else if (this.c && SwipeDismissBehavior.this.c != null) {
                SwipeDismissBehavior.this.c.onDismiss(this.b);
            }
        }
    }

    static float b(float f2, float f3, float f4) {
        return (f4 - f2) / (f3 - f2);
    }

    public boolean canSwipeDismissView(@NonNull View view) {
        return true;
    }

    public void setListener(OnDismissListener onDismissListener) {
        this.c = onDismissListener;
    }

    public void setSwipeDirection(int i2) {
        this.d = i2;
    }

    public void setDragDismissDistance(float f2) {
        this.e = a((float) BitmapDescriptorFactory.HUE_RED, f2, 1.0f);
    }

    public void setStartAlphaSwipeDistance(float f2) {
        this.f = a((float) BitmapDescriptorFactory.HUE_RED, f2, 1.0f);
    }

    public void setEndAlphaSwipeDistance(float f2) {
        this.g = a((float) BitmapDescriptorFactory.HUE_RED, f2, 1.0f);
    }

    public void setSensitivity(float f2) {
        this.h = f2;
        this.i = true;
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        boolean z = this.a;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 3) {
            switch (actionMasked) {
                case 0:
                    this.a = coordinatorLayout.isPointInChildBounds(v, (int) motionEvent.getX(), (int) motionEvent.getY());
                    z = this.a;
                    break;
                case 1:
                    break;
            }
        }
        this.a = false;
        if (!z) {
            return false;
        }
        a(coordinatorLayout);
        return this.b.shouldInterceptTouchEvent(motionEvent);
    }

    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (this.b == null) {
            return false;
        }
        this.b.processTouchEvent(motionEvent);
        return true;
    }

    private void a(ViewGroup viewGroup) {
        ViewDragHelper viewDragHelper;
        if (this.b == null) {
            if (this.i) {
                viewDragHelper = ViewDragHelper.create(viewGroup, this.h, this.j);
            } else {
                viewDragHelper = ViewDragHelper.create(viewGroup, this.j);
            }
            this.b = viewDragHelper;
        }
    }

    static float a(float f2, float f3, float f4) {
        return Math.min(Math.max(f2, f3), f4);
    }

    static int a(int i2, int i3, int i4) {
        return Math.min(Math.max(i2, i3), i4);
    }

    public int getDragState() {
        if (this.b != null) {
            return this.b.getViewDragState();
        }
        return 0;
    }
}
