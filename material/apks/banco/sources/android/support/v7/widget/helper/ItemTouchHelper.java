package android.support.v7.widget.helper;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ChildDrawingOrderCallback;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnChildAttachStateChangeListener;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.List;

public class ItemTouchHelper extends ItemDecoration implements OnChildAttachStateChangeListener {
    public static final int ACTION_STATE_DRAG = 2;
    public static final int ACTION_STATE_IDLE = 0;
    public static final int ACTION_STATE_SWIPE = 1;
    public static final int ANIMATION_TYPE_DRAG = 8;
    public static final int ANIMATION_TYPE_SWIPE_CANCEL = 4;
    public static final int ANIMATION_TYPE_SWIPE_SUCCESS = 2;
    public static final int DOWN = 2;
    public static final int END = 32;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int START = 16;
    public static final int UP = 1;
    private ItemTouchHelperGestureListener A;
    private final OnItemTouchListener B = new OnItemTouchListener() {
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            ItemTouchHelper.this.u.onTouchEvent(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                ItemTouchHelper.this.k = motionEvent.getPointerId(0);
                ItemTouchHelper.this.c = motionEvent.getX();
                ItemTouchHelper.this.d = motionEvent.getY();
                ItemTouchHelper.this.c();
                if (ItemTouchHelper.this.b == null) {
                    RecoverAnimation b = ItemTouchHelper.this.b(motionEvent);
                    if (b != null) {
                        ItemTouchHelper.this.c -= b.l;
                        ItemTouchHelper.this.d -= b.m;
                        ItemTouchHelper.this.a(b.h, true);
                        if (ItemTouchHelper.this.a.remove(b.h.itemView)) {
                            ItemTouchHelper.this.l.clearView(ItemTouchHelper.this.p, b.h);
                        }
                        ItemTouchHelper.this.a(b.h, b.i);
                        ItemTouchHelper.this.a(motionEvent, ItemTouchHelper.this.n, 0);
                    }
                }
            } else if (actionMasked == 3 || actionMasked == 1) {
                ItemTouchHelper.this.k = -1;
                ItemTouchHelper.this.a((ViewHolder) null, 0);
            } else if (ItemTouchHelper.this.k != -1) {
                int findPointerIndex = motionEvent.findPointerIndex(ItemTouchHelper.this.k);
                if (findPointerIndex >= 0) {
                    ItemTouchHelper.this.a(actionMasked, motionEvent, findPointerIndex);
                }
            }
            if (ItemTouchHelper.this.r != null) {
                ItemTouchHelper.this.r.addMovement(motionEvent);
            }
            if (ItemTouchHelper.this.b != null) {
                return true;
            }
            return false;
        }

        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            ItemTouchHelper.this.u.onTouchEvent(motionEvent);
            if (ItemTouchHelper.this.r != null) {
                ItemTouchHelper.this.r.addMovement(motionEvent);
            }
            if (ItemTouchHelper.this.k != -1) {
                int actionMasked = motionEvent.getActionMasked();
                int findPointerIndex = motionEvent.findPointerIndex(ItemTouchHelper.this.k);
                if (findPointerIndex >= 0) {
                    ItemTouchHelper.this.a(actionMasked, motionEvent, findPointerIndex);
                }
                ViewHolder viewHolder = ItemTouchHelper.this.b;
                if (viewHolder != null) {
                    int i = 0;
                    if (actionMasked != 6) {
                        switch (actionMasked) {
                            case 1:
                                break;
                            case 2:
                                if (findPointerIndex >= 0) {
                                    ItemTouchHelper.this.a(motionEvent, ItemTouchHelper.this.n, findPointerIndex);
                                    ItemTouchHelper.this.a(viewHolder);
                                    ItemTouchHelper.this.p.removeCallbacks(ItemTouchHelper.this.q);
                                    ItemTouchHelper.this.q.run();
                                    ItemTouchHelper.this.p.invalidate();
                                    break;
                                }
                                break;
                            case 3:
                                if (ItemTouchHelper.this.r != null) {
                                    ItemTouchHelper.this.r.clear();
                                    break;
                                }
                                break;
                        }
                        ItemTouchHelper.this.a((ViewHolder) null, 0);
                        ItemTouchHelper.this.k = -1;
                    } else {
                        int actionIndex = motionEvent.getActionIndex();
                        if (motionEvent.getPointerId(actionIndex) == ItemTouchHelper.this.k) {
                            if (actionIndex == 0) {
                                i = 1;
                            }
                            ItemTouchHelper.this.k = motionEvent.getPointerId(i);
                            ItemTouchHelper.this.a(motionEvent, ItemTouchHelper.this.n, actionIndex);
                        }
                    }
                }
            }
        }

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
            if (z) {
                ItemTouchHelper.this.a((ViewHolder) null, 0);
            }
        }
    };
    private Rect C;
    private long D;
    final List<View> a = new ArrayList();
    ViewHolder b = null;
    float c;
    float d;
    float e;
    float f;
    float g;
    float h;
    float i;
    float j;
    int k = -1;
    Callback l;
    int m = 0;
    int n;
    List<RecoverAnimation> o = new ArrayList();
    RecyclerView p;
    final Runnable q = new Runnable() {
        public void run() {
            if (ItemTouchHelper.this.b != null && ItemTouchHelper.this.b()) {
                if (ItemTouchHelper.this.b != null) {
                    ItemTouchHelper.this.a(ItemTouchHelper.this.b);
                }
                ItemTouchHelper.this.p.removeCallbacks(ItemTouchHelper.this.q);
                ViewCompat.postOnAnimation(ItemTouchHelper.this.p, this);
            }
        }
    };
    VelocityTracker r;
    View s = null;
    int t = -1;
    GestureDetectorCompat u;
    private final float[] v = new float[2];
    private int w;
    private List<ViewHolder> x;
    private List<Integer> y;
    private ChildDrawingOrderCallback z = null;

    public static abstract class Callback {
        public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
        public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
        private static final ItemTouchUIUtil a;
        private static final Interpolator b = new Interpolator() {
            public float getInterpolation(float f) {
                return f * f * f * f * f;
            }
        };
        private static final Interpolator c = new Interpolator() {
            public float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * f2 * f2 * f2 * f2) + 1.0f;
            }
        };
        private int d = -1;

        public static int convertToRelativeDirection(int i, int i2) {
            int i3 = i & 789516;
            if (i3 == 0) {
                return i;
            }
            int i4 = i & (i3 ^ -1);
            if (i2 == 0) {
                return i4 | (i3 << 2);
            }
            int i5 = i3 << 1;
            return i4 | (-789517 & i5) | ((i5 & 789516) << 2);
        }

        public static int makeFlag(int i, int i2) {
            return i2 << (i * 8);
        }

        public boolean canDropOver(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder viewHolder2) {
            return true;
        }

        public int convertToAbsoluteDirection(int i, int i2) {
            int i3 = i & 3158064;
            if (i3 == 0) {
                return i;
            }
            int i4 = i & (i3 ^ -1);
            if (i2 == 0) {
                return i4 | (i3 >> 2);
            }
            int i5 = i3 >> 1;
            return i4 | (-3158065 & i5) | ((i5 & 3158064) >> 2);
        }

        public int getBoundingBoxMargin() {
            return 0;
        }

        public float getMoveThreshold(ViewHolder viewHolder) {
            return 0.5f;
        }

        public abstract int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder);

        public float getSwipeEscapeVelocity(float f) {
            return f;
        }

        public float getSwipeThreshold(ViewHolder viewHolder) {
            return 0.5f;
        }

        public float getSwipeVelocityThreshold(float f) {
            return f;
        }

        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        public boolean isLongPressDragEnabled() {
            return true;
        }

        public abstract boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder viewHolder2);

        public abstract void onSwiped(ViewHolder viewHolder, int i);

        static {
            if (VERSION.SDK_INT >= 21) {
                a = new Api21Impl();
            } else {
                a = new BaseImpl();
            }
        }

        public static ItemTouchUIUtil getDefaultUIUtil() {
            return a;
        }

        public static int makeMovementFlags(int i, int i2) {
            return makeFlag(2, i) | makeFlag(1, i2) | makeFlag(0, i2 | i);
        }

        /* access modifiers changed from: 0000 */
        public final int a(RecyclerView recyclerView, ViewHolder viewHolder) {
            return convertToAbsoluteDirection(getMovementFlags(recyclerView, viewHolder), ViewCompat.getLayoutDirection(recyclerView));
        }

        /* access modifiers changed from: 0000 */
        public boolean b(RecyclerView recyclerView, ViewHolder viewHolder) {
            return (a(recyclerView, viewHolder) & 16711680) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean c(RecyclerView recyclerView, ViewHolder viewHolder) {
            return (a(recyclerView, viewHolder) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) != 0;
        }

        public ViewHolder chooseDropTarget(ViewHolder viewHolder, List<ViewHolder> list, int i, int i2) {
            ViewHolder viewHolder2 = viewHolder;
            int width = i + viewHolder2.itemView.getWidth();
            int height = i2 + viewHolder2.itemView.getHeight();
            int left = i - viewHolder2.itemView.getLeft();
            int top = i2 - viewHolder2.itemView.getTop();
            int size = list.size();
            ViewHolder viewHolder3 = null;
            int i3 = -1;
            for (int i4 = 0; i4 < size; i4++) {
                ViewHolder viewHolder4 = (ViewHolder) list.get(i4);
                if (left > 0) {
                    int right = viewHolder4.itemView.getRight() - width;
                    if (right < 0 && viewHolder4.itemView.getRight() > viewHolder2.itemView.getRight()) {
                        int abs = Math.abs(right);
                        if (abs > i3) {
                            viewHolder3 = viewHolder4;
                            i3 = abs;
                        }
                    }
                }
                if (left < 0) {
                    int left2 = viewHolder4.itemView.getLeft() - i;
                    if (left2 > 0 && viewHolder4.itemView.getLeft() < viewHolder2.itemView.getLeft()) {
                        int abs2 = Math.abs(left2);
                        if (abs2 > i3) {
                            viewHolder3 = viewHolder4;
                            i3 = abs2;
                        }
                    }
                }
                if (top < 0) {
                    int top2 = viewHolder4.itemView.getTop() - i2;
                    if (top2 > 0 && viewHolder4.itemView.getTop() < viewHolder2.itemView.getTop()) {
                        int abs3 = Math.abs(top2);
                        if (abs3 > i3) {
                            viewHolder3 = viewHolder4;
                            i3 = abs3;
                        }
                    }
                }
                if (top > 0) {
                    int bottom = viewHolder4.itemView.getBottom() - height;
                    if (bottom < 0 && viewHolder4.itemView.getBottom() > viewHolder2.itemView.getBottom()) {
                        int abs4 = Math.abs(bottom);
                        if (abs4 > i3) {
                            viewHolder3 = viewHolder4;
                            i3 = abs4;
                        }
                    }
                }
            }
            return viewHolder3;
        }

        public void onSelectedChanged(ViewHolder viewHolder, int i) {
            if (viewHolder != null) {
                a.onSelected(viewHolder.itemView);
            }
        }

        private int a(RecyclerView recyclerView) {
            if (this.d == -1) {
                this.d = recyclerView.getResources().getDimensionPixelSize(R.dimen.item_touch_helper_max_drag_scroll_per_frame);
            }
            return this.d;
        }

        public void onMoved(RecyclerView recyclerView, ViewHolder viewHolder, int i, ViewHolder viewHolder2, int i2, int i3, int i4) {
            LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof ViewDropHandler) {
                ((ViewDropHandler) layoutManager).prepareForDrop(viewHolder.itemView, viewHolder2.itemView, i3, i4);
                return;
            }
            if (layoutManager.canScrollHorizontally()) {
                if (layoutManager.getDecoratedLeft(viewHolder2.itemView) <= recyclerView.getPaddingLeft()) {
                    recyclerView.scrollToPosition(i2);
                }
                if (layoutManager.getDecoratedRight(viewHolder2.itemView) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                    recyclerView.scrollToPosition(i2);
                }
            }
            if (layoutManager.canScrollVertically()) {
                if (layoutManager.getDecoratedTop(viewHolder2.itemView) <= recyclerView.getPaddingTop()) {
                    recyclerView.scrollToPosition(i2);
                }
                if (layoutManager.getDecoratedBottom(viewHolder2.itemView) >= recyclerView.getHeight() - recyclerView.getPaddingBottom()) {
                    recyclerView.scrollToPosition(i2);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, List<RecoverAnimation> list, int i, float f, float f2) {
            Canvas canvas2 = canvas;
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                RecoverAnimation recoverAnimation = (RecoverAnimation) list.get(i2);
                recoverAnimation.c();
                int save = canvas2.save();
                onChildDraw(canvas2, recyclerView, recoverAnimation.h, recoverAnimation.l, recoverAnimation.m, recoverAnimation.i, false);
                canvas2.restoreToCount(save);
            }
            if (viewHolder != null) {
                int save2 = canvas2.save();
                onChildDraw(canvas2, recyclerView, viewHolder, f, f2, i, true);
                canvas2.restoreToCount(save2);
            }
        }

        /* access modifiers changed from: 0000 */
        public void b(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, List<RecoverAnimation> list, int i, float f, float f2) {
            Canvas canvas2 = canvas;
            List<RecoverAnimation> list2 = list;
            int size = list.size();
            boolean z = false;
            for (int i2 = 0; i2 < size; i2++) {
                RecoverAnimation recoverAnimation = (RecoverAnimation) list2.get(i2);
                int save = canvas2.save();
                onChildDrawOver(canvas2, recyclerView, recoverAnimation.h, recoverAnimation.l, recoverAnimation.m, recoverAnimation.i, false);
                canvas2.restoreToCount(save);
            }
            if (viewHolder != null) {
                int save2 = canvas2.save();
                onChildDrawOver(canvas2, recyclerView, viewHolder, f, f2, i, true);
                canvas2.restoreToCount(save2);
            }
            for (int i3 = size - 1; i3 >= 0; i3--) {
                RecoverAnimation recoverAnimation2 = (RecoverAnimation) list2.get(i3);
                if (recoverAnimation2.o && !recoverAnimation2.k) {
                    list2.remove(i3);
                } else if (!recoverAnimation2.o) {
                    z = true;
                }
            }
            if (z) {
                recyclerView.invalidate();
            }
        }

        public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
            a.clearView(viewHolder.itemView);
        }

        public void onChildDraw(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            a.onDraw(canvas, recyclerView, viewHolder.itemView, f, f2, i, z);
        }

        public void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            a.onDrawOver(canvas, recyclerView, viewHolder.itemView, f, f2, i, z);
        }

        public long getAnimationDuration(RecyclerView recyclerView, int i, float f, float f2) {
            long j;
            ItemAnimator itemAnimator = recyclerView.getItemAnimator();
            if (itemAnimator == null) {
                return i == 8 ? 200 : 250;
            }
            if (i == 8) {
                j = itemAnimator.getMoveDuration();
            } else {
                j = itemAnimator.getRemoveDuration();
            }
            return j;
        }

        public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int i, int i2, int i3, long j) {
            float f = 1.0f;
            int signum = (int) (((float) (((int) Math.signum((float) i2)) * a(recyclerView))) * c.getInterpolation(Math.min(1.0f, (((float) Math.abs(i2)) * 1.0f) / ((float) i))));
            if (j <= 2000) {
                f = ((float) j) / 2000.0f;
            }
            int interpolation = (int) (((float) signum) * b.getInterpolation(f));
            if (interpolation != 0) {
                return interpolation;
            }
            return i2 > 0 ? 1 : -1;
        }
    }

    class ItemTouchHelperGestureListener extends SimpleOnGestureListener {
        private boolean b = true;

        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        ItemTouchHelperGestureListener() {
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.b = false;
        }

        public void onLongPress(MotionEvent motionEvent) {
            if (this.b) {
                View a2 = ItemTouchHelper.this.a(motionEvent);
                if (a2 != null) {
                    ViewHolder childViewHolder = ItemTouchHelper.this.p.getChildViewHolder(a2);
                    if (childViewHolder != null && ItemTouchHelper.this.l.b(ItemTouchHelper.this.p, childViewHolder) && motionEvent.getPointerId(0) == ItemTouchHelper.this.k) {
                        int findPointerIndex = motionEvent.findPointerIndex(ItemTouchHelper.this.k);
                        float x = motionEvent.getX(findPointerIndex);
                        float y = motionEvent.getY(findPointerIndex);
                        ItemTouchHelper.this.c = x;
                        ItemTouchHelper.this.d = y;
                        ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                        ItemTouchHelper.this.h = BitmapDescriptorFactory.HUE_RED;
                        itemTouchHelper.g = BitmapDescriptorFactory.HUE_RED;
                        if (ItemTouchHelper.this.l.isLongPressDragEnabled()) {
                            ItemTouchHelper.this.a(childViewHolder, 2);
                        }
                    }
                }
            }
        }
    }

    static class RecoverAnimation implements AnimatorListener {
        private final ValueAnimator a;
        private float b;
        final float d;
        final float e;
        final float f;
        final float g;
        final ViewHolder h;
        final int i;
        final int j;
        public boolean k;
        float l;
        float m;
        boolean n = false;
        boolean o = false;

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
        }

        RecoverAnimation(ViewHolder viewHolder, int i2, int i3, float f2, float f3, float f4, float f5) {
            this.i = i3;
            this.j = i2;
            this.h = viewHolder;
            this.d = f2;
            this.e = f3;
            this.f = f4;
            this.g = f5;
            this.a = ValueAnimator.ofFloat(new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
            this.a.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    RecoverAnimation.this.a(valueAnimator.getAnimatedFraction());
                }
            });
            this.a.setTarget(viewHolder.itemView);
            this.a.addListener(this);
            a((float) BitmapDescriptorFactory.HUE_RED);
        }

        public void a(long j2) {
            this.a.setDuration(j2);
        }

        public void a() {
            this.h.setIsRecyclable(false);
            this.a.start();
        }

        public void b() {
            this.a.cancel();
        }

        public void a(float f2) {
            this.b = f2;
        }

        public void c() {
            if (this.d == this.f) {
                this.l = this.h.itemView.getTranslationX();
            } else {
                this.l = this.d + (this.b * (this.f - this.d));
            }
            if (this.e == this.g) {
                this.m = this.h.itemView.getTranslationY();
            } else {
                this.m = this.e + (this.b * (this.g - this.e));
            }
        }

        public void onAnimationEnd(Animator animator) {
            if (!this.o) {
                this.h.setIsRecyclable(true);
            }
            this.o = true;
        }

        public void onAnimationCancel(Animator animator) {
            a(1.0f);
        }
    }

    public static abstract class SimpleCallback extends Callback {
        private int a;
        private int b;

        public SimpleCallback(int i, int i2) {
            this.a = i2;
            this.b = i;
        }

        public void setDefaultSwipeDirs(int i) {
            this.a = i;
        }

        public void setDefaultDragDirs(int i) {
            this.b = i;
        }

        public int getSwipeDirs(RecyclerView recyclerView, ViewHolder viewHolder) {
            return this.a;
        }

        public int getDragDirs(RecyclerView recyclerView, ViewHolder viewHolder) {
            return this.b;
        }

        public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
            return makeMovementFlags(getDragDirs(recyclerView, viewHolder), getSwipeDirs(recyclerView, viewHolder));
        }
    }

    public interface ViewDropHandler {
        void prepareForDrop(View view, View view2, int i, int i2);
    }

    public void onChildViewAttachedToWindow(View view) {
    }

    public ItemTouchHelper(Callback callback) {
        this.l = callback;
    }

    private static boolean a(View view, float f2, float f3, float f4, float f5) {
        return f2 >= f4 && f2 <= f4 + ((float) view.getWidth()) && f3 >= f5 && f3 <= f5 + ((float) view.getHeight());
    }

    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) {
        if (this.p != recyclerView) {
            if (this.p != null) {
                e();
            }
            this.p = recyclerView;
            if (recyclerView != null) {
                Resources resources = recyclerView.getResources();
                this.e = resources.getDimension(R.dimen.item_touch_helper_swipe_escape_velocity);
                this.f = resources.getDimension(R.dimen.item_touch_helper_swipe_escape_max_velocity);
                d();
            }
        }
    }

    private void d() {
        this.w = ViewConfiguration.get(this.p.getContext()).getScaledTouchSlop();
        this.p.addItemDecoration(this);
        this.p.addOnItemTouchListener(this.B);
        this.p.addOnChildAttachStateChangeListener(this);
        f();
    }

    private void e() {
        this.p.removeItemDecoration(this);
        this.p.removeOnItemTouchListener(this.B);
        this.p.removeOnChildAttachStateChangeListener(this);
        for (int size = this.o.size() - 1; size >= 0; size--) {
            this.l.clearView(this.p, ((RecoverAnimation) this.o.get(0)).h);
        }
        this.o.clear();
        this.s = null;
        this.t = -1;
        h();
        g();
    }

    private void f() {
        this.A = new ItemTouchHelperGestureListener();
        this.u = new GestureDetectorCompat(this.p.getContext(), this.A);
    }

    private void g() {
        if (this.A != null) {
            this.A.a();
            this.A = null;
        }
        if (this.u != null) {
            this.u = null;
        }
    }

    private void a(float[] fArr) {
        if ((this.n & 12) != 0) {
            fArr[0] = (this.i + this.g) - ((float) this.b.itemView.getLeft());
        } else {
            fArr[0] = this.b.itemView.getTranslationX();
        }
        if ((this.n & 3) != 0) {
            fArr[1] = (this.j + this.h) - ((float) this.b.itemView.getTop());
        } else {
            fArr[1] = this.b.itemView.getTranslationY();
        }
    }

    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
        float f2;
        float f3;
        if (this.b != null) {
            a(this.v);
            float f4 = this.v[0];
            f2 = this.v[1];
            f3 = f4;
        } else {
            f3 = BitmapDescriptorFactory.HUE_RED;
            f2 = BitmapDescriptorFactory.HUE_RED;
        }
        this.l.b(canvas, recyclerView, this.b, this.o, this.m, f3, f2);
    }

    public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
        float f2;
        float f3;
        this.t = -1;
        if (this.b != null) {
            a(this.v);
            float f4 = this.v[0];
            f2 = this.v[1];
            f3 = f4;
        } else {
            f3 = BitmapDescriptorFactory.HUE_RED;
            f2 = BitmapDescriptorFactory.HUE_RED;
        }
        this.l.a(canvas, recyclerView, this.b, this.o, this.m, f3, f2);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0125  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x012f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(android.support.v7.widget.RecyclerView.ViewHolder r25, int r26) {
        /*
            r24 = this;
            r11 = r24
            r12 = r25
            r13 = r26
            android.support.v7.widget.RecyclerView$ViewHolder r0 = r11.b
            if (r12 != r0) goto L_0x000f
            int r0 = r11.m
            if (r13 != r0) goto L_0x000f
            return
        L_0x000f:
            r0 = -9223372036854775808
            r11.D = r0
            int r4 = r11.m
            r14 = 1
            r11.a(r12, r14)
            r11.m = r13
            r15 = 2
            if (r13 != r15) goto L_0x0025
            android.view.View r0 = r12.itemView
            r11.s = r0
            r24.i()
        L_0x0025:
            int r0 = r13 * 8
            r10 = 8
            int r0 = r0 + r10
            int r0 = r14 << r0
            int r16 = r0 + -1
            android.support.v7.widget.RecyclerView$ViewHolder r0 = r11.b
            r9 = 0
            if (r0 == 0) goto L_0x00e6
            android.support.v7.widget.RecyclerView$ViewHolder r8 = r11.b
            android.view.View r0 = r8.itemView
            android.view.ViewParent r0 = r0.getParent()
            if (r0 == 0) goto L_0x00d2
            if (r4 != r15) goto L_0x0041
            r7 = 0
            goto L_0x0046
        L_0x0041:
            int r0 = r11.c(r8)
            r7 = r0
        L_0x0046:
            r24.h()
            r0 = 4
            r1 = 0
            if (r7 == r0) goto L_0x0073
            if (r7 == r10) goto L_0x0073
            r2 = 16
            if (r7 == r2) goto L_0x0073
            r2 = 32
            if (r7 == r2) goto L_0x0073
            switch(r7) {
                case 1: goto L_0x005f;
                case 2: goto L_0x005f;
                default: goto L_0x005a;
            }
        L_0x005a:
            r17 = 0
        L_0x005c:
            r18 = 0
            goto L_0x0085
        L_0x005f:
            float r2 = r11.h
            float r2 = java.lang.Math.signum(r2)
            android.support.v7.widget.RecyclerView r3 = r11.p
            int r3 = r3.getHeight()
            float r3 = (float) r3
            float r2 = r2 * r3
            r18 = r2
            r17 = 0
            goto L_0x0085
        L_0x0073:
            float r2 = r11.g
            float r2 = java.lang.Math.signum(r2)
            android.support.v7.widget.RecyclerView r3 = r11.p
            int r3 = r3.getWidth()
            float r3 = (float) r3
            float r2 = r2 * r3
            r17 = r2
            goto L_0x005c
        L_0x0085:
            if (r4 != r15) goto L_0x008a
            r6 = 8
            goto L_0x008f
        L_0x008a:
            if (r7 <= 0) goto L_0x008e
            r6 = 2
            goto L_0x008f
        L_0x008e:
            r6 = 4
        L_0x008f:
            float[] r0 = r11.v
            r11.a(r0)
            float[] r0 = r11.v
            r19 = r0[r9]
            float[] r0 = r11.v
            r20 = r0[r14]
            android.support.v7.widget.helper.ItemTouchHelper$3 r5 = new android.support.v7.widget.helper.ItemTouchHelper$3
            r0 = r5
            r1 = r11
            r2 = r8
            r3 = r6
            r14 = r5
            r5 = r19
            r15 = r6
            r6 = r20
            r21 = r7
            r7 = r17
            r22 = r8
            r8 = r18
            r9 = r21
            r21 = 8
            r10 = r22
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            android.support.v7.widget.helper.ItemTouchHelper$Callback r0 = r11.l
            android.support.v7.widget.RecyclerView r1 = r11.p
            float r2 = r17 - r19
            float r3 = r18 - r20
            long r0 = r0.getAnimationDuration(r1, r15, r2, r3)
            r14.a(r0)
            java.util.List<android.support.v7.widget.helper.ItemTouchHelper$RecoverAnimation> r0 = r11.o
            r0.add(r14)
            r14.a()
            r9 = 1
            goto L_0x00e2
        L_0x00d2:
            r0 = r8
            r21 = 8
            android.view.View r1 = r0.itemView
            r11.a(r1)
            android.support.v7.widget.helper.ItemTouchHelper$Callback r1 = r11.l
            android.support.v7.widget.RecyclerView r2 = r11.p
            r1.clearView(r2, r0)
            r9 = 0
        L_0x00e2:
            r0 = 0
            r11.b = r0
            goto L_0x00e9
        L_0x00e6:
            r21 = 8
            r9 = 0
        L_0x00e9:
            if (r12 == 0) goto L_0x011c
            android.support.v7.widget.helper.ItemTouchHelper$Callback r0 = r11.l
            android.support.v7.widget.RecyclerView r1 = r11.p
            int r0 = r0.a(r1, r12)
            r0 = r0 & r16
            int r1 = r11.m
            int r1 = r1 * 8
            int r0 = r0 >> r1
            r11.n = r0
            android.view.View r0 = r12.itemView
            int r0 = r0.getLeft()
            float r0 = (float) r0
            r11.i = r0
            android.view.View r0 = r12.itemView
            int r0 = r0.getTop()
            float r0 = (float) r0
            r11.j = r0
            r11.b = r12
            r0 = 2
            if (r13 != r0) goto L_0x011c
            android.support.v7.widget.RecyclerView$ViewHolder r0 = r11.b
            android.view.View r0 = r0.itemView
            r1 = 0
            r0.performHapticFeedback(r1)
            goto L_0x011d
        L_0x011c:
            r1 = 0
        L_0x011d:
            android.support.v7.widget.RecyclerView r0 = r11.p
            android.view.ViewParent r0 = r0.getParent()
            if (r0 == 0) goto L_0x012d
            android.support.v7.widget.RecyclerView$ViewHolder r2 = r11.b
            if (r2 == 0) goto L_0x012a
            r1 = 1
        L_0x012a:
            r0.requestDisallowInterceptTouchEvent(r1)
        L_0x012d:
            if (r9 != 0) goto L_0x0138
            android.support.v7.widget.RecyclerView r0 = r11.p
            android.support.v7.widget.RecyclerView$LayoutManager r0 = r0.getLayoutManager()
            r0.requestSimpleAnimationsInNextLayout()
        L_0x0138:
            android.support.v7.widget.helper.ItemTouchHelper$Callback r0 = r11.l
            android.support.v7.widget.RecyclerView$ViewHolder r1 = r11.b
            int r2 = r11.m
            r0.onSelectedChanged(r1, r2)
            android.support.v7.widget.RecyclerView r0 = r11.p
            r0.invalidate()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.helper.ItemTouchHelper.a(android.support.v7.widget.RecyclerView$ViewHolder, int):void");
    }

    /* access modifiers changed from: 0000 */
    public void a(final RecoverAnimation recoverAnimation, final int i2) {
        this.p.post(new Runnable() {
            public void run() {
                if (ItemTouchHelper.this.p != null && ItemTouchHelper.this.p.isAttachedToWindow() && !recoverAnimation.n && recoverAnimation.h.getAdapterPosition() != -1) {
                    ItemAnimator itemAnimator = ItemTouchHelper.this.p.getItemAnimator();
                    if ((itemAnimator == null || !itemAnimator.isRunning(null)) && !ItemTouchHelper.this.a()) {
                        ItemTouchHelper.this.l.onSwiped(recoverAnimation.h, i2);
                    } else {
                        ItemTouchHelper.this.p.post(this);
                    }
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        int size = this.o.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (!((RecoverAnimation) this.o.get(i2)).o) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00c8, code lost:
        if (r1 > 0) goto L_0x00cc;
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00e9  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0104 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0110  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean b() {
        /*
            r18 = this;
            r0 = r18
            android.support.v7.widget.RecyclerView$ViewHolder r1 = r0.b
            r2 = 0
            r3 = -9223372036854775808
            if (r1 != 0) goto L_0x000c
            r0.D = r3
            return r2
        L_0x000c:
            long r5 = java.lang.System.currentTimeMillis()
            long r7 = r0.D
            int r1 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r1 != 0) goto L_0x0019
            r7 = 0
            goto L_0x001e
        L_0x0019:
            long r7 = r0.D
            long r9 = r5 - r7
            r7 = r9
        L_0x001e:
            android.support.v7.widget.RecyclerView r1 = r0.p
            android.support.v7.widget.RecyclerView$LayoutManager r1 = r1.getLayoutManager()
            android.graphics.Rect r9 = r0.C
            if (r9 != 0) goto L_0x002f
            android.graphics.Rect r9 = new android.graphics.Rect
            r9.<init>()
            r0.C = r9
        L_0x002f:
            android.support.v7.widget.RecyclerView$ViewHolder r9 = r0.b
            android.view.View r9 = r9.itemView
            android.graphics.Rect r10 = r0.C
            r1.calculateItemDecorationsForChild(r9, r10)
            boolean r9 = r1.canScrollHorizontally()
            r10 = 0
            if (r9 == 0) goto L_0x0082
            float r9 = r0.i
            float r11 = r0.g
            float r9 = r9 + r11
            int r9 = (int) r9
            android.graphics.Rect r11 = r0.C
            int r11 = r11.left
            int r11 = r9 - r11
            android.support.v7.widget.RecyclerView r12 = r0.p
            int r12 = r12.getPaddingLeft()
            int r11 = r11 - r12
            float r12 = r0.g
            int r12 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
            if (r12 >= 0) goto L_0x005c
            if (r11 >= 0) goto L_0x005c
            r14 = r11
            goto L_0x0083
        L_0x005c:
            float r11 = r0.g
            int r11 = (r11 > r10 ? 1 : (r11 == r10 ? 0 : -1))
            if (r11 <= 0) goto L_0x0082
            android.support.v7.widget.RecyclerView$ViewHolder r11 = r0.b
            android.view.View r11 = r11.itemView
            int r11 = r11.getWidth()
            int r9 = r9 + r11
            android.graphics.Rect r11 = r0.C
            int r11 = r11.right
            int r9 = r9 + r11
            android.support.v7.widget.RecyclerView r11 = r0.p
            int r11 = r11.getWidth()
            android.support.v7.widget.RecyclerView r12 = r0.p
            int r12 = r12.getPaddingRight()
            int r11 = r11 - r12
            int r9 = r9 - r11
            if (r9 <= 0) goto L_0x0082
            r14 = r9
            goto L_0x0083
        L_0x0082:
            r14 = 0
        L_0x0083:
            boolean r1 = r1.canScrollVertically()
            if (r1 == 0) goto L_0x00cb
            float r1 = r0.j
            float r9 = r0.h
            float r1 = r1 + r9
            int r1 = (int) r1
            android.graphics.Rect r9 = r0.C
            int r9 = r9.top
            int r9 = r1 - r9
            android.support.v7.widget.RecyclerView r11 = r0.p
            int r11 = r11.getPaddingTop()
            int r9 = r9 - r11
            float r11 = r0.h
            int r11 = (r11 > r10 ? 1 : (r11 == r10 ? 0 : -1))
            if (r11 >= 0) goto L_0x00a6
            if (r9 >= 0) goto L_0x00a6
            r1 = r9
            goto L_0x00cc
        L_0x00a6:
            float r9 = r0.h
            int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r9 <= 0) goto L_0x00cb
            android.support.v7.widget.RecyclerView$ViewHolder r9 = r0.b
            android.view.View r9 = r9.itemView
            int r9 = r9.getHeight()
            int r1 = r1 + r9
            android.graphics.Rect r9 = r0.C
            int r9 = r9.bottom
            int r1 = r1 + r9
            android.support.v7.widget.RecyclerView r9 = r0.p
            int r9 = r9.getHeight()
            android.support.v7.widget.RecyclerView r10 = r0.p
            int r10 = r10.getPaddingBottom()
            int r9 = r9 - r10
            int r1 = r1 - r9
            if (r1 <= 0) goto L_0x00cb
            goto L_0x00cc
        L_0x00cb:
            r1 = 0
        L_0x00cc:
            if (r14 == 0) goto L_0x00e6
            android.support.v7.widget.helper.ItemTouchHelper$Callback r11 = r0.l
            android.support.v7.widget.RecyclerView r12 = r0.p
            android.support.v7.widget.RecyclerView$ViewHolder r9 = r0.b
            android.view.View r9 = r9.itemView
            int r13 = r9.getWidth()
            android.support.v7.widget.RecyclerView r9 = r0.p
            int r15 = r9.getWidth()
            r16 = r7
            int r14 = r11.interpolateOutOfBoundsScroll(r12, r13, r14, r15, r16)
        L_0x00e6:
            r9 = r14
            if (r1 == 0) goto L_0x0102
            android.support.v7.widget.helper.ItemTouchHelper$Callback r11 = r0.l
            android.support.v7.widget.RecyclerView r12 = r0.p
            android.support.v7.widget.RecyclerView$ViewHolder r10 = r0.b
            android.view.View r10 = r10.itemView
            int r13 = r10.getHeight()
            android.support.v7.widget.RecyclerView r10 = r0.p
            int r15 = r10.getHeight()
            r14 = r1
            r16 = r7
            int r1 = r11.interpolateOutOfBoundsScroll(r12, r13, r14, r15, r16)
        L_0x0102:
            if (r9 != 0) goto L_0x010a
            if (r1 == 0) goto L_0x0107
            goto L_0x010a
        L_0x0107:
            r0.D = r3
            return r2
        L_0x010a:
            long r7 = r0.D
            int r2 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r2 != 0) goto L_0x0112
            r0.D = r5
        L_0x0112:
            android.support.v7.widget.RecyclerView r2 = r0.p
            r2.scrollBy(r9, r1)
            r1 = 1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.helper.ItemTouchHelper.b():boolean");
    }

    private List<ViewHolder> b(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
        if (this.x == null) {
            this.x = new ArrayList();
            this.y = new ArrayList();
        } else {
            this.x.clear();
            this.y.clear();
        }
        int boundingBoxMargin = this.l.getBoundingBoxMargin();
        int round = Math.round(this.i + this.g) - boundingBoxMargin;
        int round2 = Math.round(this.j + this.h) - boundingBoxMargin;
        int i2 = boundingBoxMargin * 2;
        int width = viewHolder2.itemView.getWidth() + round + i2;
        int height = viewHolder2.itemView.getHeight() + round2 + i2;
        int i3 = (round + width) / 2;
        int i4 = (round2 + height) / 2;
        LayoutManager layoutManager = this.p.getLayoutManager();
        int childCount = layoutManager.getChildCount();
        int i5 = 0;
        while (i5 < childCount) {
            View childAt = layoutManager.getChildAt(i5);
            if (childAt != viewHolder2.itemView && childAt.getBottom() >= round2 && childAt.getTop() <= height && childAt.getRight() >= round && childAt.getLeft() <= width) {
                ViewHolder childViewHolder = this.p.getChildViewHolder(childAt);
                if (this.l.canDropOver(this.p, this.b, childViewHolder)) {
                    int abs = Math.abs(i3 - ((childAt.getLeft() + childAt.getRight()) / 2));
                    int abs2 = Math.abs(i4 - ((childAt.getTop() + childAt.getBottom()) / 2));
                    int i6 = (abs * abs) + (abs2 * abs2);
                    int size = this.x.size();
                    int i7 = 0;
                    int i8 = 0;
                    while (i7 < size && i6 > ((Integer) this.y.get(i7)).intValue()) {
                        i8++;
                        i7++;
                        ViewHolder viewHolder3 = viewHolder;
                    }
                    this.x.add(i8, childViewHolder);
                    this.y.add(i8, Integer.valueOf(i6));
                }
            }
            i5++;
            viewHolder2 = viewHolder;
        }
        return this.x;
    }

    /* access modifiers changed from: 0000 */
    public void a(ViewHolder viewHolder) {
        if (!this.p.isLayoutRequested() && this.m == 2) {
            float moveThreshold = this.l.getMoveThreshold(viewHolder);
            int i2 = (int) (this.i + this.g);
            int i3 = (int) (this.j + this.h);
            if (((float) Math.abs(i3 - viewHolder.itemView.getTop())) >= ((float) viewHolder.itemView.getHeight()) * moveThreshold || ((float) Math.abs(i2 - viewHolder.itemView.getLeft())) >= ((float) viewHolder.itemView.getWidth()) * moveThreshold) {
                List b2 = b(viewHolder);
                if (b2.size() != 0) {
                    ViewHolder chooseDropTarget = this.l.chooseDropTarget(viewHolder, b2, i2, i3);
                    if (chooseDropTarget == null) {
                        this.x.clear();
                        this.y.clear();
                        return;
                    }
                    int adapterPosition = chooseDropTarget.getAdapterPosition();
                    int adapterPosition2 = viewHolder.getAdapterPosition();
                    if (this.l.onMove(this.p, viewHolder, chooseDropTarget)) {
                        this.l.onMoved(this.p, viewHolder, adapterPosition2, chooseDropTarget, adapterPosition, i2, i3);
                    }
                }
            }
        }
    }

    public void onChildViewDetachedFromWindow(View view) {
        a(view);
        ViewHolder childViewHolder = this.p.getChildViewHolder(view);
        if (childViewHolder != null) {
            if (this.b == null || childViewHolder != this.b) {
                a(childViewHolder, false);
                if (this.a.remove(childViewHolder.itemView)) {
                    this.l.clearView(this.p, childViewHolder);
                }
            } else {
                a((ViewHolder) null, 0);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int a(ViewHolder viewHolder, boolean z2) {
        for (int size = this.o.size() - 1; size >= 0; size--) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) this.o.get(size);
            if (recoverAnimation.h == viewHolder) {
                recoverAnimation.n |= z2;
                if (!recoverAnimation.o) {
                    recoverAnimation.b();
                }
                this.o.remove(size);
                return recoverAnimation.j;
            }
        }
        return 0;
    }

    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
        rect.setEmpty();
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        if (this.r != null) {
            this.r.recycle();
        }
        this.r = VelocityTracker.obtain();
    }

    private void h() {
        if (this.r != null) {
            this.r.recycle();
            this.r = null;
        }
    }

    private ViewHolder c(MotionEvent motionEvent) {
        LayoutManager layoutManager = this.p.getLayoutManager();
        if (this.k == -1) {
            return null;
        }
        int findPointerIndex = motionEvent.findPointerIndex(this.k);
        float x2 = motionEvent.getX(findPointerIndex) - this.c;
        float y2 = motionEvent.getY(findPointerIndex) - this.d;
        float abs = Math.abs(x2);
        float abs2 = Math.abs(y2);
        if (abs < ((float) this.w) && abs2 < ((float) this.w)) {
            return null;
        }
        if (abs > abs2 && layoutManager.canScrollHorizontally()) {
            return null;
        }
        if (abs2 > abs && layoutManager.canScrollVertically()) {
            return null;
        }
        View a2 = a(motionEvent);
        if (a2 == null) {
            return null;
        }
        return this.p.getChildViewHolder(a2);
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i2, MotionEvent motionEvent, int i3) {
        if (this.b != null || i2 != 2 || this.m == 2 || !this.l.isItemViewSwipeEnabled() || this.p.getScrollState() == 1) {
            return false;
        }
        ViewHolder c2 = c(motionEvent);
        if (c2 == null) {
            return false;
        }
        int a2 = (this.l.a(this.p, c2) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
        if (a2 == 0) {
            return false;
        }
        float x2 = motionEvent.getX(i3);
        float f2 = x2 - this.c;
        float y2 = motionEvent.getY(i3) - this.d;
        float abs = Math.abs(f2);
        float abs2 = Math.abs(y2);
        if (abs < ((float) this.w) && abs2 < ((float) this.w)) {
            return false;
        }
        if (abs > abs2) {
            if (f2 < BitmapDescriptorFactory.HUE_RED && (a2 & 4) == 0) {
                return false;
            }
            if (f2 > BitmapDescriptorFactory.HUE_RED && (a2 & 8) == 0) {
                return false;
            }
        } else if (y2 < BitmapDescriptorFactory.HUE_RED && (a2 & 1) == 0) {
            return false;
        } else {
            if (y2 > BitmapDescriptorFactory.HUE_RED && (a2 & 2) == 0) {
                return false;
            }
        }
        this.h = BitmapDescriptorFactory.HUE_RED;
        this.g = BitmapDescriptorFactory.HUE_RED;
        this.k = motionEvent.getPointerId(0);
        a(c2, 1);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public View a(MotionEvent motionEvent) {
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        if (this.b != null) {
            View view = this.b.itemView;
            if (a(view, x2, y2, this.i + this.g, this.j + this.h)) {
                return view;
            }
        }
        for (int size = this.o.size() - 1; size >= 0; size--) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) this.o.get(size);
            View view2 = recoverAnimation.h.itemView;
            if (a(view2, x2, y2, recoverAnimation.l, recoverAnimation.m)) {
                return view2;
            }
        }
        return this.p.findChildViewUnder(x2, y2);
    }

    public void startDrag(ViewHolder viewHolder) {
        if (!this.l.b(this.p, viewHolder)) {
            Log.e("ItemTouchHelper", "Start drag has been called but dragging is not enabled");
        } else if (viewHolder.itemView.getParent() != this.p) {
            Log.e("ItemTouchHelper", "Start drag has been called with a view holder which is not a child of the RecyclerView which is controlled by this ItemTouchHelper.");
        } else {
            c();
            this.h = BitmapDescriptorFactory.HUE_RED;
            this.g = BitmapDescriptorFactory.HUE_RED;
            a(viewHolder, 2);
        }
    }

    public void startSwipe(ViewHolder viewHolder) {
        if (!this.l.c(this.p, viewHolder)) {
            Log.e("ItemTouchHelper", "Start swipe has been called but swiping is not enabled");
        } else if (viewHolder.itemView.getParent() != this.p) {
            Log.e("ItemTouchHelper", "Start swipe has been called with a view holder which is not a child of the RecyclerView controlled by this ItemTouchHelper.");
        } else {
            c();
            this.h = BitmapDescriptorFactory.HUE_RED;
            this.g = BitmapDescriptorFactory.HUE_RED;
            a(viewHolder, 1);
        }
    }

    /* access modifiers changed from: 0000 */
    public RecoverAnimation b(MotionEvent motionEvent) {
        if (this.o.isEmpty()) {
            return null;
        }
        View a2 = a(motionEvent);
        for (int size = this.o.size() - 1; size >= 0; size--) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) this.o.get(size);
            if (recoverAnimation.h.itemView == a2) {
                return recoverAnimation;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void a(MotionEvent motionEvent, int i2, int i3) {
        float x2 = motionEvent.getX(i3);
        float y2 = motionEvent.getY(i3);
        this.g = x2 - this.c;
        this.h = y2 - this.d;
        if ((i2 & 4) == 0) {
            this.g = Math.max(BitmapDescriptorFactory.HUE_RED, this.g);
        }
        if ((i2 & 8) == 0) {
            this.g = Math.min(BitmapDescriptorFactory.HUE_RED, this.g);
        }
        if ((i2 & 1) == 0) {
            this.h = Math.max(BitmapDescriptorFactory.HUE_RED, this.h);
        }
        if ((i2 & 2) == 0) {
            this.h = Math.min(BitmapDescriptorFactory.HUE_RED, this.h);
        }
    }

    private int c(ViewHolder viewHolder) {
        if (this.m == 2) {
            return 0;
        }
        int movementFlags = this.l.getMovementFlags(this.p, viewHolder);
        int convertToAbsoluteDirection = (this.l.convertToAbsoluteDirection(movementFlags, ViewCompat.getLayoutDirection(this.p)) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
        if (convertToAbsoluteDirection == 0) {
            return 0;
        }
        int i2 = (movementFlags & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
        if (Math.abs(this.g) > Math.abs(this.h)) {
            int b2 = b(viewHolder, convertToAbsoluteDirection);
            if (b2 > 0) {
                return (i2 & b2) == 0 ? Callback.convertToRelativeDirection(b2, ViewCompat.getLayoutDirection(this.p)) : b2;
            }
            int c2 = c(viewHolder, convertToAbsoluteDirection);
            if (c2 > 0) {
                return c2;
            }
        } else {
            int c3 = c(viewHolder, convertToAbsoluteDirection);
            if (c3 > 0) {
                return c3;
            }
            int b3 = b(viewHolder, convertToAbsoluteDirection);
            if (b3 > 0) {
                return (i2 & b3) == 0 ? Callback.convertToRelativeDirection(b3, ViewCompat.getLayoutDirection(this.p)) : b3;
            }
        }
        return 0;
    }

    private int b(ViewHolder viewHolder, int i2) {
        if ((i2 & 12) != 0) {
            int i3 = 4;
            int i4 = this.g > BitmapDescriptorFactory.HUE_RED ? 8 : 4;
            if (this.r != null && this.k > -1) {
                this.r.computeCurrentVelocity(1000, this.l.getSwipeVelocityThreshold(this.f));
                float xVelocity = this.r.getXVelocity(this.k);
                float yVelocity = this.r.getYVelocity(this.k);
                if (xVelocity > BitmapDescriptorFactory.HUE_RED) {
                    i3 = 8;
                }
                float abs = Math.abs(xVelocity);
                if ((i3 & i2) != 0 && i4 == i3 && abs >= this.l.getSwipeEscapeVelocity(this.e) && abs > Math.abs(yVelocity)) {
                    return i3;
                }
            }
            float width = ((float) this.p.getWidth()) * this.l.getSwipeThreshold(viewHolder);
            if ((i2 & i4) != 0 && Math.abs(this.g) > width) {
                return i4;
            }
        }
        return 0;
    }

    private int c(ViewHolder viewHolder, int i2) {
        if ((i2 & 3) != 0) {
            int i3 = 1;
            int i4 = this.h > BitmapDescriptorFactory.HUE_RED ? 2 : 1;
            if (this.r != null && this.k > -1) {
                this.r.computeCurrentVelocity(1000, this.l.getSwipeVelocityThreshold(this.f));
                float xVelocity = this.r.getXVelocity(this.k);
                float yVelocity = this.r.getYVelocity(this.k);
                if (yVelocity > BitmapDescriptorFactory.HUE_RED) {
                    i3 = 2;
                }
                float abs = Math.abs(yVelocity);
                if ((i3 & i2) != 0 && i3 == i4 && abs >= this.l.getSwipeEscapeVelocity(this.e) && abs > Math.abs(xVelocity)) {
                    return i3;
                }
            }
            float height = ((float) this.p.getHeight()) * this.l.getSwipeThreshold(viewHolder);
            if ((i2 & i4) != 0 && Math.abs(this.h) > height) {
                return i4;
            }
        }
        return 0;
    }

    private void i() {
        if (VERSION.SDK_INT < 21) {
            if (this.z == null) {
                this.z = new ChildDrawingOrderCallback() {
                    public int onGetChildDrawingOrder(int i, int i2) {
                        if (ItemTouchHelper.this.s == null) {
                            return i2;
                        }
                        int i3 = ItemTouchHelper.this.t;
                        if (i3 == -1) {
                            i3 = ItemTouchHelper.this.p.indexOfChild(ItemTouchHelper.this.s);
                            ItemTouchHelper.this.t = i3;
                        }
                        if (i2 == i - 1) {
                            return i3;
                        }
                        if (i2 >= i3) {
                            i2++;
                        }
                        return i2;
                    }
                };
            }
            this.p.setChildDrawingOrderCallback(this.z);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(View view) {
        if (view == this.s) {
            this.s = null;
            if (this.z != null) {
                this.p.setChildDrawingOrderCallback(null);
            }
        }
    }
}
