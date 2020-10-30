package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.VisibleForTesting;
import android.support.v4.os.TraceCompat;
import android.support.v4.util.Preconditions;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.v7.recyclerview.R;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerView extends ViewGroup implements NestedScrollingChild2, ScrollingView {
    public static final int HORIZONTAL = 0;
    static final Interpolator I = new Interpolator() {
        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    public static final int INVALID_TYPE = -1;
    private static final int[] J = {16843830};
    private static final int[] K = {16842987};
    /* access modifiers changed from: private */
    public static final boolean L = (VERSION.SDK_INT >= 21);
    private static final boolean M = (VERSION.SDK_INT <= 15);
    private static final boolean N = (VERSION.SDK_INT <= 15);
    public static final long NO_ID = -1;
    public static final int NO_POSITION = -1;
    private static final Class<?>[] O = {Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE};
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    public static final int TOUCH_SLOP_DEFAULT = 0;
    public static final int TOUCH_SLOP_PAGING = 1;
    public static final int VERTICAL = 1;
    static final boolean a = (VERSION.SDK_INT == 18 || VERSION.SDK_INT == 19 || VERSION.SDK_INT == 20);
    static final boolean b = (VERSION.SDK_INT >= 23);
    static final boolean c = (VERSION.SDK_INT >= 16);
    GapWorker A;
    LayoutPrefetchRegistryImpl B;
    final State C;
    boolean D;
    boolean E;
    boolean F;
    RecyclerViewAccessibilityDelegate G;
    @VisibleForTesting
    final List<ViewHolder> H;
    private final RecyclerViewDataObserver P;
    private SavedState Q;
    private final Rect R;
    private final ArrayList<OnItemTouchListener> S;
    private OnItemTouchListener T;
    private int U;
    private boolean V;
    private int W;
    private ChildDrawingOrderCallback aA;
    private final int[] aB;
    private NestedScrollingChildHelper aC;
    private final int[] aD;
    /* access modifiers changed from: private */
    public final int[] aE;
    private final int[] aF;
    private Runnable aG;
    private final ProcessCallback aH;
    private final AccessibilityManager aa;
    private List<OnChildAttachStateChangeListener> ab;
    private int ac;
    private int ad;
    @NonNull
    private EdgeEffectFactory ae;
    private EdgeEffect af;
    private EdgeEffect ag;
    private EdgeEffect ah;
    private EdgeEffect ai;
    private int aj;
    private int ak;
    private VelocityTracker al;
    private int am;
    private int an;
    private int ao;
    private int ap;
    private int aq;

    /* renamed from: ar reason: collision with root package name */
    private OnFlingListener f235ar;
    private final int as;
    private final int at;
    private float au;
    private float av;
    private boolean aw;
    private OnScrollListener ax;
    private List<OnScrollListener> ay;
    private ItemAnimatorListener az;
    final Recycler d;
    AdapterHelper e;
    ChildHelper f;
    final ViewInfoStore g;
    boolean h;
    final Runnable i;
    final Rect j;
    final RectF k;
    Adapter l;
    @VisibleForTesting
    LayoutManager m;
    RecyclerListener n;
    final ArrayList<ItemDecoration> o;
    boolean p;
    boolean q;
    boolean r;
    @VisibleForTesting
    boolean s;
    boolean t;
    boolean u;
    boolean v;
    boolean w;
    boolean x;
    ItemAnimator y;
    final ViewFlinger z;

    public static abstract class Adapter<VH extends ViewHolder> {
        private final AdapterDataObservable a = new AdapterDataObservable();
        private boolean b = false;

        public abstract int getItemCount();

        public long getItemId(int i) {
            return -1;
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        }

        public abstract void onBindViewHolder(@NonNull VH vh, int i);

        @NonNull
        public abstract VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i);

        public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        }

        public boolean onFailedToRecycleView(@NonNull VH vh) {
            return false;
        }

        public void onViewAttachedToWindow(@NonNull VH vh) {
        }

        public void onViewDetachedFromWindow(@NonNull VH vh) {
        }

        public void onViewRecycled(@NonNull VH vh) {
        }

        public void onBindViewHolder(@NonNull VH vh, int i, @NonNull List<Object> list) {
            onBindViewHolder(vh, i);
        }

        public final VH createViewHolder(@NonNull ViewGroup viewGroup, int i) {
            try {
                TraceCompat.beginSection("RV CreateView");
                VH onCreateViewHolder = onCreateViewHolder(viewGroup, i);
                if (onCreateViewHolder.itemView.getParent() != null) {
                    throw new IllegalStateException("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
                }
                onCreateViewHolder.e = i;
                return onCreateViewHolder;
            } finally {
                TraceCompat.endSection();
            }
        }

        public final void bindViewHolder(@NonNull VH vh, int i) {
            vh.b = i;
            if (hasStableIds()) {
                vh.d = getItemId(i);
            }
            vh.a(1, 519);
            TraceCompat.beginSection("RV OnBindView");
            onBindViewHolder(vh, i, vh.q());
            vh.p();
            android.view.ViewGroup.LayoutParams layoutParams = vh.itemView.getLayoutParams();
            if (layoutParams instanceof LayoutParams) {
                ((LayoutParams) layoutParams).e = true;
            }
            TraceCompat.endSection();
        }

        public void setHasStableIds(boolean z) {
            if (hasObservers()) {
                throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
            }
            this.b = z;
        }

        public final boolean hasStableIds() {
            return this.b;
        }

        public final boolean hasObservers() {
            return this.a.a();
        }

        public void registerAdapterDataObserver(@NonNull AdapterDataObserver adapterDataObserver) {
            this.a.registerObserver(adapterDataObserver);
        }

        public void unregisterAdapterDataObserver(@NonNull AdapterDataObserver adapterDataObserver) {
            this.a.unregisterObserver(adapterDataObserver);
        }

        public final void notifyDataSetChanged() {
            this.a.b();
        }

        public final void notifyItemChanged(int i) {
            this.a.a(i, 1);
        }

        public final void notifyItemChanged(int i, @Nullable Object obj) {
            this.a.a(i, 1, obj);
        }

        public final void notifyItemRangeChanged(int i, int i2) {
            this.a.a(i, i2);
        }

        public final void notifyItemRangeChanged(int i, int i2, @Nullable Object obj) {
            this.a.a(i, i2, obj);
        }

        public final void notifyItemInserted(int i) {
            this.a.b(i, 1);
        }

        public final void notifyItemMoved(int i, int i2) {
            this.a.d(i, i2);
        }

        public final void notifyItemRangeInserted(int i, int i2) {
            this.a.b(i, i2);
        }

        public final void notifyItemRemoved(int i) {
            this.a.c(i, 1);
        }

        public final void notifyItemRangeRemoved(int i, int i2) {
            this.a.c(i, i2);
        }
    }

    static class AdapterDataObservable extends Observable<AdapterDataObserver> {
        AdapterDataObservable() {
        }

        public boolean a() {
            return !this.mObservers.isEmpty();
        }

        public void b() {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) this.mObservers.get(size)).onChanged();
            }
        }

        public void a(int i, int i2) {
            a(i, i2, null);
        }

        public void a(int i, int i2, @Nullable Object obj) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) this.mObservers.get(size)).onItemRangeChanged(i, i2, obj);
            }
        }

        public void b(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) this.mObservers.get(size)).onItemRangeInserted(i, i2);
            }
        }

        public void c(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) this.mObservers.get(size)).onItemRangeRemoved(i, i2);
            }
        }

        public void d(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) this.mObservers.get(size)).onItemRangeMoved(i, i2, 1);
            }
        }
    }

    public static abstract class AdapterDataObserver {
        public void onChanged() {
        }

        public void onItemRangeChanged(int i, int i2) {
        }

        public void onItemRangeInserted(int i, int i2) {
        }

        public void onItemRangeMoved(int i, int i2, int i3) {
        }

        public void onItemRangeRemoved(int i, int i2) {
        }

        public void onItemRangeChanged(int i, int i2, @Nullable Object obj) {
            onItemRangeChanged(i, i2);
        }
    }

    public interface ChildDrawingOrderCallback {
        int onGetChildDrawingOrder(int i, int i2);
    }

    public static class EdgeEffectFactory {
        public static final int DIRECTION_BOTTOM = 3;
        public static final int DIRECTION_LEFT = 0;
        public static final int DIRECTION_RIGHT = 2;
        public static final int DIRECTION_TOP = 1;

        @Retention(RetentionPolicy.SOURCE)
        public @interface EdgeDirection {
        }

        /* access modifiers changed from: protected */
        @NonNull
        public EdgeEffect createEdgeEffect(RecyclerView recyclerView, int i) {
            return new EdgeEffect(recyclerView.getContext());
        }
    }

    public static abstract class ItemAnimator {
        public static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        public static final int FLAG_CHANGED = 2;
        public static final int FLAG_INVALIDATED = 4;
        public static final int FLAG_MOVED = 2048;
        public static final int FLAG_REMOVED = 8;
        private ItemAnimatorListener a = null;
        private ArrayList<ItemAnimatorFinishedListener> b = new ArrayList<>();
        private long c = 120;
        private long d = 120;
        private long e = 250;
        private long f = 250;

        @Retention(RetentionPolicy.SOURCE)
        public @interface AdapterChanges {
        }

        public interface ItemAnimatorFinishedListener {
            void onAnimationsFinished();
        }

        interface ItemAnimatorListener {
            void a(ViewHolder viewHolder);
        }

        public static class ItemHolderInfo {
            public int bottom;
            public int changeFlags;
            public int left;
            public int right;
            public int top;

            public ItemHolderInfo setFrom(ViewHolder viewHolder) {
                return setFrom(viewHolder, 0);
            }

            public ItemHolderInfo setFrom(ViewHolder viewHolder, int i) {
                View view = viewHolder.itemView;
                this.left = view.getLeft();
                this.top = view.getTop();
                this.right = view.getRight();
                this.bottom = view.getBottom();
                return this;
            }
        }

        public abstract boolean animateAppearance(@NonNull ViewHolder viewHolder, @Nullable ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        public abstract boolean animateChange(@NonNull ViewHolder viewHolder, @NonNull ViewHolder viewHolder2, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        public abstract boolean animateDisappearance(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2);

        public abstract boolean animatePersistence(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder) {
            return true;
        }

        public abstract void endAnimation(ViewHolder viewHolder);

        public abstract void endAnimations();

        public abstract boolean isRunning();

        public void onAnimationFinished(ViewHolder viewHolder) {
        }

        public void onAnimationStarted(ViewHolder viewHolder) {
        }

        public abstract void runPendingAnimations();

        public long getMoveDuration() {
            return this.e;
        }

        public void setMoveDuration(long j) {
            this.e = j;
        }

        public long getAddDuration() {
            return this.c;
        }

        public void setAddDuration(long j) {
            this.c = j;
        }

        public long getRemoveDuration() {
            return this.d;
        }

        public void setRemoveDuration(long j) {
            this.d = j;
        }

        public long getChangeDuration() {
            return this.f;
        }

        public void setChangeDuration(long j) {
            this.f = j;
        }

        /* access modifiers changed from: 0000 */
        public void a(ItemAnimatorListener itemAnimatorListener) {
            this.a = itemAnimatorListener;
        }

        @NonNull
        public ItemHolderInfo recordPreLayoutInformation(@NonNull State state, @NonNull ViewHolder viewHolder, int i, @NonNull List<Object> list) {
            return obtainHolderInfo().setFrom(viewHolder);
        }

        @NonNull
        public ItemHolderInfo recordPostLayoutInformation(@NonNull State state, @NonNull ViewHolder viewHolder) {
            return obtainHolderInfo().setFrom(viewHolder);
        }

        static int b(ViewHolder viewHolder) {
            int d2 = viewHolder.m & 14;
            if (viewHolder.j()) {
                return 4;
            }
            if ((d2 & 4) == 0) {
                int oldPosition = viewHolder.getOldPosition();
                int adapterPosition = viewHolder.getAdapterPosition();
                if (!(oldPosition == -1 || adapterPosition == -1 || oldPosition == adapterPosition)) {
                    d2 |= 2048;
                }
            }
            return d2;
        }

        public final void dispatchAnimationFinished(ViewHolder viewHolder) {
            onAnimationFinished(viewHolder);
            if (this.a != null) {
                this.a.a(viewHolder);
            }
        }

        public final void dispatchAnimationStarted(ViewHolder viewHolder) {
            onAnimationStarted(viewHolder);
        }

        public final boolean isRunning(ItemAnimatorFinishedListener itemAnimatorFinishedListener) {
            boolean isRunning = isRunning();
            if (itemAnimatorFinishedListener != null) {
                if (!isRunning) {
                    itemAnimatorFinishedListener.onAnimationsFinished();
                } else {
                    this.b.add(itemAnimatorFinishedListener);
                }
            }
            return isRunning;
        }

        public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder, @NonNull List<Object> list) {
            return canReuseUpdatedViewHolder(viewHolder);
        }

        public final void dispatchAnimationsFinished() {
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                ((ItemAnimatorFinishedListener) this.b.get(i)).onAnimationsFinished();
            }
            this.b.clear();
        }

        public ItemHolderInfo obtainHolderInfo() {
            return new ItemHolderInfo();
        }
    }

    class ItemAnimatorRestoreListener implements ItemAnimatorListener {
        ItemAnimatorRestoreListener() {
        }

        public void a(ViewHolder viewHolder) {
            viewHolder.setIsRecyclable(true);
            if (viewHolder.g != null && viewHolder.h == null) {
                viewHolder.g = null;
            }
            viewHolder.h = null;
            if (!viewHolder.u() && !RecyclerView.this.a(viewHolder.itemView) && viewHolder.n()) {
                RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
            }
        }
    }

    public static abstract class ItemDecoration {
        @Deprecated
        public void onDraw(Canvas canvas, RecyclerView recyclerView) {
        }

        @Deprecated
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
            onDraw(canvas, recyclerView);
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
            onDrawOver(canvas, recyclerView);
        }

        @Deprecated
        public void getItemOffsets(Rect rect, int i, RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            getItemOffsets(rect, ((LayoutParams) view.getLayoutParams()).getViewLayoutPosition(), recyclerView);
        }
    }

    public static abstract class LayoutManager {
        private final Callback a = new Callback() {
            public View a(int i) {
                return LayoutManager.this.getChildAt(i);
            }

            public int a() {
                return LayoutManager.this.getPaddingLeft();
            }

            public int b() {
                return LayoutManager.this.getWidth() - LayoutManager.this.getPaddingRight();
            }

            public int a(View view) {
                return LayoutManager.this.getDecoratedLeft(view) - ((LayoutParams) view.getLayoutParams()).leftMargin;
            }

            public int b(View view) {
                return LayoutManager.this.getDecoratedRight(view) + ((LayoutParams) view.getLayoutParams()).rightMargin;
            }
        };
        private final Callback b = new Callback() {
            public View a(int i) {
                return LayoutManager.this.getChildAt(i);
            }

            public int a() {
                return LayoutManager.this.getPaddingTop();
            }

            public int b() {
                return LayoutManager.this.getHeight() - LayoutManager.this.getPaddingBottom();
            }

            public int a(View view) {
                return LayoutManager.this.getDecoratedTop(view) - ((LayoutParams) view.getLayoutParams()).topMargin;
            }

            public int b(View view) {
                return LayoutManager.this.getDecoratedBottom(view) + ((LayoutParams) view.getLayoutParams()).bottomMargin;
            }
        };
        private boolean c = true;
        private boolean d = true;
        private int e;
        private int f;
        private int g;
        private int h;
        ChildHelper p;
        RecyclerView q;
        ViewBoundsCheck r = new ViewBoundsCheck(this.a);
        ViewBoundsCheck s = new ViewBoundsCheck(this.b);
        @Nullable
        SmoothScroller t;
        boolean u = false;
        boolean v = false;
        boolean w = false;
        int x;
        boolean y;

        public interface LayoutPrefetchRegistry {
            void addPosition(int i, int i2);
        }

        public static class Properties {
            public int orientation;
            public boolean reverseLayout;
            public int spanCount;
            public boolean stackFromEnd;
        }

        public boolean canScrollHorizontally() {
            return false;
        }

        public boolean canScrollVertically() {
            return false;
        }

        public boolean checkLayoutParams(LayoutParams layoutParams) {
            return layoutParams != null;
        }

        public void collectAdjacentPrefetchPositions(int i, int i2, State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        public void collectInitialPrefetchPositions(int i, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        public int computeHorizontalScrollExtent(State state) {
            return 0;
        }

        public int computeHorizontalScrollOffset(State state) {
            return 0;
        }

        public int computeHorizontalScrollRange(State state) {
            return 0;
        }

        public int computeVerticalScrollExtent(State state) {
            return 0;
        }

        public int computeVerticalScrollOffset(State state) {
            return 0;
        }

        public int computeVerticalScrollRange(State state) {
            return 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean d() {
            return false;
        }

        public abstract LayoutParams generateDefaultLayoutParams();

        public int getBaseline() {
            return -1;
        }

        public int getSelectionModeForAccessibility(Recycler recycler, State state) {
            return 0;
        }

        public boolean isLayoutHierarchical(Recycler recycler, State state) {
            return false;
        }

        public void onAdapterChanged(Adapter adapter, Adapter adapter2) {
        }

        public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> arrayList, int i, int i2) {
            return false;
        }

        @CallSuper
        public void onAttachedToWindow(RecyclerView recyclerView) {
        }

        @Deprecated
        public void onDetachedFromWindow(RecyclerView recyclerView) {
        }

        @Nullable
        public View onFocusSearchFailed(View view, int i, Recycler recycler, State state) {
            return null;
        }

        public View onInterceptFocusSearch(View view, int i) {
            return null;
        }

        public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        }

        public void onItemsChanged(RecyclerView recyclerView) {
        }

        public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        }

        public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
        }

        public void onLayoutCompleted(State state) {
        }

        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        public void onScrollStateChanged(int i) {
        }

        public boolean performAccessibilityActionForItem(Recycler recycler, State state, View view, int i, Bundle bundle) {
            return false;
        }

        public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
            return 0;
        }

        public void scrollToPosition(int i) {
        }

        public int scrollVerticallyBy(int i, Recycler recycler, State state) {
            return 0;
        }

        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void a(RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.q = null;
                this.p = null;
                this.g = 0;
                this.h = 0;
            } else {
                this.q = recyclerView;
                this.p = recyclerView.f;
                this.g = recyclerView.getWidth();
                this.h = recyclerView.getHeight();
            }
            this.e = 1073741824;
            this.f = 1073741824;
        }

        /* access modifiers changed from: 0000 */
        public void c(int i, int i2) {
            this.g = MeasureSpec.getSize(i);
            this.e = MeasureSpec.getMode(i);
            if (this.e == 0 && !RecyclerView.b) {
                this.g = 0;
            }
            this.h = MeasureSpec.getSize(i2);
            this.f = MeasureSpec.getMode(i2);
            if (this.f == 0 && !RecyclerView.b) {
                this.h = 0;
            }
        }

        /* access modifiers changed from: 0000 */
        public void d(int i, int i2) {
            int childCount = getChildCount();
            if (childCount == 0) {
                this.q.c(i, i2);
                return;
            }
            int i3 = SubsamplingScaleImageView.TILE_SIZE_AUTO;
            int i4 = SubsamplingScaleImageView.TILE_SIZE_AUTO;
            int i5 = Integer.MIN_VALUE;
            int i6 = Integer.MIN_VALUE;
            for (int i7 = 0; i7 < childCount; i7++) {
                View childAt = getChildAt(i7);
                Rect rect = this.q.j;
                getDecoratedBoundsWithMargins(childAt, rect);
                if (rect.left < i3) {
                    i3 = rect.left;
                }
                if (rect.right > i5) {
                    i5 = rect.right;
                }
                if (rect.top < i4) {
                    i4 = rect.top;
                }
                if (rect.bottom > i6) {
                    i6 = rect.bottom;
                }
            }
            this.q.j.set(i3, i4, i5, i6);
            setMeasuredDimension(this.q.j, i, i2);
        }

        public void setMeasuredDimension(Rect rect, int i, int i2) {
            setMeasuredDimension(chooseSize(i, rect.width() + getPaddingLeft() + getPaddingRight(), getMinimumWidth()), chooseSize(i2, rect.height() + getPaddingTop() + getPaddingBottom(), getMinimumHeight()));
        }

        public void requestLayout() {
            if (this.q != null) {
                this.q.requestLayout();
            }
        }

        public void assertInLayoutOrScroll(String str) {
            if (this.q != null) {
                this.q.a(str);
            }
        }

        public static int chooseSize(int i, int i2, int i3) {
            int mode = MeasureSpec.getMode(i);
            int size = MeasureSpec.getSize(i);
            if (mode != Integer.MIN_VALUE) {
                return mode != 1073741824 ? Math.max(i2, i3) : size;
            }
            return Math.min(size, Math.max(i2, i3));
        }

        public void assertNotInLayoutOrScroll(String str) {
            if (this.q != null) {
                this.q.b(str);
            }
        }

        @Deprecated
        public void setAutoMeasureEnabled(boolean z) {
            this.w = z;
        }

        public boolean isAutoMeasureEnabled() {
            return this.w;
        }

        public final void setItemPrefetchEnabled(boolean z) {
            if (z != this.d) {
                this.d = z;
                this.x = 0;
                if (this.q != null) {
                    this.q.d.a();
                }
            }
        }

        public final boolean isItemPrefetchEnabled() {
            return this.d;
        }

        /* access modifiers changed from: 0000 */
        public void b(RecyclerView recyclerView) {
            this.v = true;
            onAttachedToWindow(recyclerView);
        }

        /* access modifiers changed from: 0000 */
        public void a(RecyclerView recyclerView, Recycler recycler) {
            this.v = false;
            onDetachedFromWindow(recyclerView, recycler);
        }

        public boolean isAttachedToWindow() {
            return this.v;
        }

        public void postOnAnimation(Runnable runnable) {
            if (this.q != null) {
                ViewCompat.postOnAnimation(this.q, runnable);
            }
        }

        public boolean removeCallbacks(Runnable runnable) {
            if (this.q != null) {
                return this.q.removeCallbacks(runnable);
            }
            return false;
        }

        @CallSuper
        public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            onDetachedFromWindow(recyclerView);
        }

        public boolean getClipToPadding() {
            return this.q != null && this.q.h;
        }

        public void onLayoutChildren(Recycler recycler, State state) {
            Log.e("RecyclerView", "You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            if (layoutParams instanceof LayoutParams) {
                return new LayoutParams((LayoutParams) layoutParams);
            }
            if (layoutParams instanceof MarginLayoutParams) {
                return new LayoutParams((MarginLayoutParams) layoutParams);
            }
            return new LayoutParams(layoutParams);
        }

        public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
            return new LayoutParams(context, attributeSet);
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, State state, int i) {
            Log.e("RecyclerView", "You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void startSmoothScroll(SmoothScroller smoothScroller) {
            if (!(this.t == null || smoothScroller == this.t || !this.t.isRunning())) {
                this.t.stop();
            }
            this.t = smoothScroller;
            this.t.a(this.q, this);
        }

        public boolean isSmoothScrolling() {
            return this.t != null && this.t.isRunning();
        }

        public int getLayoutDirection() {
            return ViewCompat.getLayoutDirection(this.q);
        }

        public void endAnimation(View view) {
            if (this.q.y != null) {
                this.q.y.endAnimation(RecyclerView.b(view));
            }
        }

        public void addDisappearingView(View view) {
            addDisappearingView(view, -1);
        }

        public void addDisappearingView(View view, int i) {
            a(view, i, true);
        }

        public void addView(View view) {
            addView(view, -1);
        }

        public void addView(View view, int i) {
            a(view, i, false);
        }

        private void a(View view, int i, boolean z) {
            ViewHolder b2 = RecyclerView.b(view);
            if (z || b2.m()) {
                this.q.g.e(b2);
            } else {
                this.q.g.f(b2);
            }
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (b2.f() || b2.d()) {
                if (b2.d()) {
                    b2.e();
                } else {
                    b2.g();
                }
                this.p.a(view, i, view.getLayoutParams(), false);
            } else if (view.getParent() == this.q) {
                int b3 = this.p.b(view);
                if (i == -1) {
                    i = this.p.b();
                }
                if (b3 == -1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:");
                    sb.append(this.q.indexOfChild(view));
                    sb.append(this.q.a());
                    throw new IllegalStateException(sb.toString());
                } else if (b3 != i) {
                    this.q.m.moveView(b3, i);
                }
            } else {
                this.p.a(view, i, false);
                layoutParams.e = true;
                if (this.t != null && this.t.isRunning()) {
                    this.t.onChildAttachedToWindow(view);
                }
            }
            if (layoutParams.f) {
                b2.itemView.invalidate();
                layoutParams.f = false;
            }
        }

        public void removeView(View view) {
            this.p.a(view);
        }

        public void removeViewAt(int i) {
            if (getChildAt(i) != null) {
                this.p.a(i);
            }
        }

        public void removeAllViews() {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                this.p.a(childCount);
            }
        }

        public int getPosition(View view) {
            return ((LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        }

        public int getItemViewType(View view) {
            return RecyclerView.b(view).getItemViewType();
        }

        @Nullable
        public View findContainingItemView(View view) {
            if (this.q == null) {
                return null;
            }
            View findContainingItemView = this.q.findContainingItemView(view);
            if (findContainingItemView != null && !this.p.c(findContainingItemView)) {
                return findContainingItemView;
            }
            return null;
        }

        public View findViewByPosition(int i) {
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                ViewHolder b2 = RecyclerView.b(childAt);
                if (b2 != null && b2.getLayoutPosition() == i && !b2.c() && (this.q.C.isPreLayout() || !b2.m())) {
                    return childAt;
                }
            }
            return null;
        }

        public void detachView(View view) {
            int b2 = this.p.b(view);
            if (b2 >= 0) {
                a(b2, view);
            }
        }

        public void detachViewAt(int i) {
            a(i, getChildAt(i));
        }

        private void a(int i, View view) {
            this.p.e(i);
        }

        public void attachView(View view, int i, LayoutParams layoutParams) {
            ViewHolder b2 = RecyclerView.b(view);
            if (b2.m()) {
                this.q.g.e(b2);
            } else {
                this.q.g.f(b2);
            }
            this.p.a(view, i, layoutParams, b2.m());
        }

        public void attachView(View view, int i) {
            attachView(view, i, (LayoutParams) view.getLayoutParams());
        }

        public void attachView(View view) {
            attachView(view, -1);
        }

        public void removeDetachedView(View view) {
            this.q.removeDetachedView(view, false);
        }

        public void moveView(int i, int i2) {
            View childAt = getChildAt(i);
            if (childAt == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Cannot move a child from non-existing index:");
                sb.append(i);
                sb.append(this.q.toString());
                throw new IllegalArgumentException(sb.toString());
            }
            detachViewAt(i);
            attachView(childAt, i2);
        }

        public void detachAndScrapView(View view, Recycler recycler) {
            a(recycler, this.p.b(view), view);
        }

        public void detachAndScrapViewAt(int i, Recycler recycler) {
            a(recycler, i, getChildAt(i));
        }

        public void removeAndRecycleView(View view, Recycler recycler) {
            removeView(view);
            recycler.recycleView(view);
        }

        public void removeAndRecycleViewAt(int i, Recycler recycler) {
            View childAt = getChildAt(i);
            removeViewAt(i);
            recycler.recycleView(childAt);
        }

        public int getChildCount() {
            if (this.p != null) {
                return this.p.b();
            }
            return 0;
        }

        public View getChildAt(int i) {
            if (this.p != null) {
                return this.p.b(i);
            }
            return null;
        }

        public int getWidthMode() {
            return this.e;
        }

        public int getHeightMode() {
            return this.f;
        }

        public int getWidth() {
            return this.g;
        }

        public int getHeight() {
            return this.h;
        }

        public int getPaddingLeft() {
            if (this.q != null) {
                return this.q.getPaddingLeft();
            }
            return 0;
        }

        public int getPaddingTop() {
            if (this.q != null) {
                return this.q.getPaddingTop();
            }
            return 0;
        }

        public int getPaddingRight() {
            if (this.q != null) {
                return this.q.getPaddingRight();
            }
            return 0;
        }

        public int getPaddingBottom() {
            if (this.q != null) {
                return this.q.getPaddingBottom();
            }
            return 0;
        }

        public int getPaddingStart() {
            if (this.q != null) {
                return ViewCompat.getPaddingStart(this.q);
            }
            return 0;
        }

        public int getPaddingEnd() {
            if (this.q != null) {
                return ViewCompat.getPaddingEnd(this.q);
            }
            return 0;
        }

        public boolean isFocused() {
            return this.q != null && this.q.isFocused();
        }

        public boolean hasFocus() {
            return this.q != null && this.q.hasFocus();
        }

        public View getFocusedChild() {
            if (this.q == null) {
                return null;
            }
            View focusedChild = this.q.getFocusedChild();
            if (focusedChild == null || this.p.c(focusedChild)) {
                return null;
            }
            return focusedChild;
        }

        public int getItemCount() {
            Adapter adapter = this.q != null ? this.q.getAdapter() : null;
            if (adapter != null) {
                return adapter.getItemCount();
            }
            return 0;
        }

        public void offsetChildrenHorizontal(int i) {
            if (this.q != null) {
                this.q.offsetChildrenHorizontal(i);
            }
        }

        public void offsetChildrenVertical(int i) {
            if (this.q != null) {
                this.q.offsetChildrenVertical(i);
            }
        }

        public void ignoreView(View view) {
            if (view.getParent() != this.q || this.q.indexOfChild(view) == -1) {
                StringBuilder sb = new StringBuilder();
                sb.append("View should be fully attached to be ignored");
                sb.append(this.q.a());
                throw new IllegalArgumentException(sb.toString());
            }
            ViewHolder b2 = RecyclerView.b(view);
            b2.b(128);
            this.q.g.g(b2);
        }

        public void stopIgnoringView(View view) {
            ViewHolder b2 = RecyclerView.b(view);
            b2.i();
            b2.r();
            b2.b(4);
        }

        public void detachAndScrapAttachedViews(Recycler recycler) {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                a(recycler, childCount, getChildAt(childCount));
            }
        }

        private void a(Recycler recycler, int i, View view) {
            ViewHolder b2 = RecyclerView.b(view);
            if (!b2.c()) {
                if (!b2.j() || b2.m() || this.q.l.hasStableIds()) {
                    detachViewAt(i);
                    recycler.b(view);
                    this.q.g.h(b2);
                } else {
                    removeViewAt(i);
                    recycler.b(b2);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Recycler recycler) {
            int c2 = recycler.c();
            for (int i = c2 - 1; i >= 0; i--) {
                View b2 = recycler.b(i);
                ViewHolder b3 = RecyclerView.b(b2);
                if (!b3.c()) {
                    b3.setIsRecyclable(false);
                    if (b3.n()) {
                        this.q.removeDetachedView(b2, false);
                    }
                    if (this.q.y != null) {
                        this.q.y.endAnimation(b3);
                    }
                    b3.setIsRecyclable(true);
                    recycler.a(b2);
                }
            }
            recycler.d();
            if (c2 > 0) {
                this.q.invalidate();
            }
        }

        public void measureChild(View view, int i, int i2) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect c2 = this.q.c(view);
            int i3 = i2 + c2.top + c2.bottom;
            int childMeasureSpec = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + i + c2.left + c2.right, layoutParams.width, canScrollHorizontally());
            int childMeasureSpec2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + i3, layoutParams.height, canScrollVertically());
            if (b(view, childMeasureSpec, childMeasureSpec2, layoutParams)) {
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(View view, int i, int i2, LayoutParams layoutParams) {
            return !this.c || !a(view.getMeasuredWidth(), i, layoutParams.width) || !a(view.getMeasuredHeight(), i2, layoutParams.height);
        }

        /* access modifiers changed from: 0000 */
        public boolean b(View view, int i, int i2, LayoutParams layoutParams) {
            return view.isLayoutRequested() || !this.c || !a(view.getWidth(), i, layoutParams.width) || !a(view.getHeight(), i2, layoutParams.height);
        }

        public boolean isMeasurementCacheEnabled() {
            return this.c;
        }

        public void setMeasurementCacheEnabled(boolean z) {
            this.c = z;
        }

        private static boolean a(int i, int i2, int i3) {
            int mode = MeasureSpec.getMode(i2);
            int size = MeasureSpec.getSize(i2);
            boolean z = false;
            if (i3 > 0 && i != i3) {
                return false;
            }
            if (mode == Integer.MIN_VALUE) {
                if (size >= i) {
                    z = true;
                }
                return z;
            } else if (mode == 0) {
                return true;
            } else {
                if (mode != 1073741824) {
                    return false;
                }
                if (size == i) {
                    z = true;
                }
                return z;
            }
        }

        public void measureChildWithMargins(View view, int i, int i2) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect c2 = this.q.c(view);
            int i3 = i2 + c2.top + c2.bottom;
            int childMeasureSpec = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin + i + c2.left + c2.right, layoutParams.width, canScrollHorizontally());
            int childMeasureSpec2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin + i3, layoutParams.height, canScrollVertically());
            if (b(view, childMeasureSpec, childMeasureSpec2, layoutParams)) {
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
            if (r3 >= 0) goto L_0x000c;
         */
        @java.lang.Deprecated
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static int getChildMeasureSpec(int r1, int r2, int r3, boolean r4) {
            /*
                int r1 = r1 - r2
                r2 = 0
                int r1 = java.lang.Math.max(r2, r1)
                r0 = 1073741824(0x40000000, float:2.0)
                if (r4 == 0) goto L_0x0012
                if (r3 < 0) goto L_0x0010
            L_0x000c:
                r1 = r3
            L_0x000d:
                r2 = 1073741824(0x40000000, float:2.0)
                goto L_0x001e
            L_0x0010:
                r1 = 0
                goto L_0x001e
            L_0x0012:
                if (r3 < 0) goto L_0x0015
                goto L_0x000c
            L_0x0015:
                r4 = -1
                if (r3 != r4) goto L_0x0019
                goto L_0x000d
            L_0x0019:
                r4 = -2
                if (r3 != r4) goto L_0x0010
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
            L_0x001e:
                int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r2)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.LayoutManager.getChildMeasureSpec(int, int, int, boolean):int");
        }

        public static int getChildMeasureSpec(int i, int i2, int i3, int i4, boolean z) {
            int i5;
            int i6 = i - i3;
            int i7 = 0;
            int max = Math.max(0, i6);
            if (z) {
                if (i4 < 0) {
                    if (i4 == -1) {
                        if (i2 == Integer.MIN_VALUE || (i2 != 0 && i2 == 1073741824)) {
                            i5 = max;
                        } else {
                            i2 = 0;
                            i5 = 0;
                        }
                        i7 = i2;
                        max = i5;
                        return MeasureSpec.makeMeasureSpec(max, i7);
                    }
                    max = 0;
                    return MeasureSpec.makeMeasureSpec(max, i7);
                }
            } else if (i4 < 0) {
                if (i4 == -1) {
                    i7 = i2;
                } else {
                    if (i4 == -2) {
                        if (i2 == Integer.MIN_VALUE || i2 == 1073741824) {
                            i7 = Integer.MIN_VALUE;
                        }
                    }
                    max = 0;
                }
                return MeasureSpec.makeMeasureSpec(max, i7);
            }
            max = i4;
            i7 = 1073741824;
            return MeasureSpec.makeMeasureSpec(max, i7);
        }

        public int getDecoratedMeasuredWidth(View view) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).d;
            return view.getMeasuredWidth() + rect.left + rect.right;
        }

        public int getDecoratedMeasuredHeight(View view) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).d;
            return view.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public void layoutDecorated(View view, int i, int i2, int i3, int i4) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).d;
            view.layout(i + rect.left, i2 + rect.top, i3 - rect.right, i4 - rect.bottom);
        }

        public void layoutDecoratedWithMargins(View view, int i, int i2, int i3, int i4) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect rect = layoutParams.d;
            view.layout(i + rect.left + layoutParams.leftMargin, i2 + rect.top + layoutParams.topMargin, (i3 - rect.right) - layoutParams.rightMargin, (i4 - rect.bottom) - layoutParams.bottomMargin);
        }

        public void getTransformedBoundingBox(View view, boolean z, Rect rect) {
            if (z) {
                Rect rect2 = ((LayoutParams) view.getLayoutParams()).d;
                rect.set(-rect2.left, -rect2.top, view.getWidth() + rect2.right, view.getHeight() + rect2.bottom);
            } else {
                rect.set(0, 0, view.getWidth(), view.getHeight());
            }
            if (this.q != null) {
                Matrix matrix = view.getMatrix();
                if (matrix != null && !matrix.isIdentity()) {
                    RectF rectF = this.q.k;
                    rectF.set(rect);
                    matrix.mapRect(rectF);
                    rect.set((int) Math.floor((double) rectF.left), (int) Math.floor((double) rectF.top), (int) Math.ceil((double) rectF.right), (int) Math.ceil((double) rectF.bottom));
                }
            }
            rect.offset(view.getLeft(), view.getTop());
        }

        public void getDecoratedBoundsWithMargins(View view, Rect rect) {
            RecyclerView.a(view, rect);
        }

        public int getDecoratedLeft(View view) {
            return view.getLeft() - getLeftDecorationWidth(view);
        }

        public int getDecoratedTop(View view) {
            return view.getTop() - getTopDecorationHeight(view);
        }

        public int getDecoratedRight(View view) {
            return view.getRight() + getRightDecorationWidth(view);
        }

        public int getDecoratedBottom(View view) {
            return view.getBottom() + getBottomDecorationHeight(view);
        }

        public void calculateItemDecorationsForChild(View view, Rect rect) {
            if (this.q == null) {
                rect.set(0, 0, 0, 0);
            } else {
                rect.set(this.q.c(view));
            }
        }

        public int getTopDecorationHeight(View view) {
            return ((LayoutParams) view.getLayoutParams()).d.top;
        }

        public int getBottomDecorationHeight(View view) {
            return ((LayoutParams) view.getLayoutParams()).d.bottom;
        }

        public int getLeftDecorationWidth(View view) {
            return ((LayoutParams) view.getLayoutParams()).d.left;
        }

        public int getRightDecorationWidth(View view) {
            return ((LayoutParams) view.getLayoutParams()).d.right;
        }

        private int[] a(RecyclerView recyclerView, View view, Rect rect, boolean z) {
            int[] iArr = new int[2];
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int width = getWidth() - getPaddingRight();
            int height = getHeight() - getPaddingBottom();
            int left = (view.getLeft() + rect.left) - view.getScrollX();
            int top = (view.getTop() + rect.top) - view.getScrollY();
            int width2 = rect.width() + left;
            int height2 = rect.height() + top;
            int i = left - paddingLeft;
            int min = Math.min(0, i);
            int i2 = top - paddingTop;
            int min2 = Math.min(0, i2);
            int i3 = width2 - width;
            int max = Math.max(0, i3);
            int max2 = Math.max(0, height2 - height);
            if (getLayoutDirection() != 1) {
                if (min == 0) {
                    min = Math.min(i, max);
                }
                max = min;
            } else if (max == 0) {
                max = Math.max(min, i3);
            }
            if (min2 == 0) {
                min2 = Math.min(i2, max2);
            }
            iArr[0] = max;
            iArr[1] = min2;
            return iArr;
        }

        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z) {
            return requestChildRectangleOnScreen(recyclerView, view, rect, z, false);
        }

        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
            int[] a2 = a(recyclerView, view, rect, z);
            int i = a2[0];
            int i2 = a2[1];
            if ((z2 && !a(recyclerView, i, i2)) || (i == 0 && i2 == 0)) {
                return false;
            }
            if (z) {
                recyclerView.scrollBy(i, i2);
            } else {
                recyclerView.smoothScrollBy(i, i2);
            }
            return true;
        }

        public boolean isViewPartiallyVisible(@NonNull View view, boolean z, boolean z2) {
            boolean z3 = this.r.a(view, 24579) && this.s.a(view, 24579);
            return z ? z3 : !z3;
        }

        private boolean a(RecyclerView recyclerView, int i, int i2) {
            View focusedChild = recyclerView.getFocusedChild();
            if (focusedChild == null) {
                return false;
            }
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int width = getWidth() - getPaddingRight();
            int height = getHeight() - getPaddingBottom();
            Rect rect = this.q.j;
            getDecoratedBoundsWithMargins(focusedChild, rect);
            if (rect.left - i >= width || rect.right - i <= paddingLeft || rect.top - i2 >= height || rect.bottom - i2 <= paddingTop) {
                return false;
            }
            return true;
        }

        @Deprecated
        public boolean onRequestChildFocus(RecyclerView recyclerView, View view, View view2) {
            return isSmoothScrolling() || recyclerView.isComputingLayout();
        }

        public boolean onRequestChildFocus(RecyclerView recyclerView, State state, View view, View view2) {
            return onRequestChildFocus(recyclerView, view, view2);
        }

        public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
            onItemsUpdated(recyclerView, i, i2);
        }

        public void onMeasure(Recycler recycler, State state, int i, int i2) {
            this.q.c(i, i2);
        }

        public void setMeasuredDimension(int i, int i2) {
            this.q.setMeasuredDimension(i, i2);
        }

        public int getMinimumWidth() {
            return ViewCompat.getMinimumWidth(this.q);
        }

        public int getMinimumHeight() {
            return ViewCompat.getMinimumHeight(this.q);
        }

        /* access modifiers changed from: 0000 */
        public void e() {
            if (this.t != null) {
                this.t.stop();
            }
        }

        /* access modifiers changed from: private */
        public void a(SmoothScroller smoothScroller) {
            if (this.t == smoothScroller) {
                this.t = null;
            }
        }

        public void removeAndRecycleAllViews(Recycler recycler) {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                if (!RecyclerView.b(getChildAt(childCount)).c()) {
                    removeAndRecycleViewAt(childCount, recycler);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            onInitializeAccessibilityNodeInfo(this.q.d, this.q.C, accessibilityNodeInfoCompat);
        }

        public void onInitializeAccessibilityNodeInfo(Recycler recycler, State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (this.q.canScrollVertically(-1) || this.q.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            if (this.q.canScrollVertically(1) || this.q.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            accessibilityNodeInfoCompat.setCollectionInfo(CollectionInfoCompat.obtain(getRowCountForAccessibility(recycler, state), getColumnCountForAccessibility(recycler, state), isLayoutHierarchical(recycler, state), getSelectionModeForAccessibility(recycler, state)));
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            onInitializeAccessibilityEvent(this.q.d, this.q.C, accessibilityEvent);
        }

        public void onInitializeAccessibilityEvent(Recycler recycler, State state, AccessibilityEvent accessibilityEvent) {
            if (this.q != null && accessibilityEvent != null) {
                boolean z = true;
                if (!this.q.canScrollVertically(1) && !this.q.canScrollVertically(-1) && !this.q.canScrollHorizontally(-1) && !this.q.canScrollHorizontally(1)) {
                    z = false;
                }
                accessibilityEvent.setScrollable(z);
                if (this.q.l != null) {
                    accessibilityEvent.setItemCount(this.q.l.getItemCount());
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            ViewHolder b2 = RecyclerView.b(view);
            if (b2 != null && !b2.m() && !this.p.c(b2.itemView)) {
                onInitializeAccessibilityNodeInfoForItem(this.q.d, this.q.C, view, accessibilityNodeInfoCompat);
            }
        }

        public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(CollectionItemInfoCompat.obtain(canScrollVertically() ? getPosition(view) : 0, 1, canScrollHorizontally() ? getPosition(view) : 0, 1, false, false));
        }

        public void requestSimpleAnimationsInNextLayout() {
            this.u = true;
        }

        public int getRowCountForAccessibility(Recycler recycler, State state) {
            int i = 1;
            if (this.q == null || this.q.l == null) {
                return 1;
            }
            if (canScrollVertically()) {
                i = this.q.l.getItemCount();
            }
            return i;
        }

        public int getColumnCountForAccessibility(Recycler recycler, State state) {
            int i = 1;
            if (this.q == null || this.q.l == null) {
                return 1;
            }
            if (canScrollHorizontally()) {
                i = this.q.l.getItemCount();
            }
            return i;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(int i, Bundle bundle) {
            return performAccessibilityAction(this.q.d, this.q.C, i, bundle);
        }

        /* JADX WARNING: Removed duplicated region for block: B:24:0x0074 A[ADDED_TO_REGION] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean performAccessibilityAction(android.support.v7.widget.RecyclerView.Recycler r2, android.support.v7.widget.RecyclerView.State r3, int r4, android.os.Bundle r5) {
            /*
                r1 = this;
                android.support.v7.widget.RecyclerView r2 = r1.q
                r3 = 0
                if (r2 != 0) goto L_0x0006
                return r3
            L_0x0006:
                r2 = 4096(0x1000, float:5.74E-42)
                r5 = 1
                if (r4 == r2) goto L_0x0044
                r2 = 8192(0x2000, float:1.14794E-41)
                if (r4 == r2) goto L_0x0012
                r2 = 0
            L_0x0010:
                r4 = 0
                goto L_0x0072
            L_0x0012:
                android.support.v7.widget.RecyclerView r2 = r1.q
                r4 = -1
                boolean r2 = r2.canScrollVertically(r4)
                if (r2 == 0) goto L_0x002b
                int r2 = r1.getHeight()
                int r0 = r1.getPaddingTop()
                int r2 = r2 - r0
                int r0 = r1.getPaddingBottom()
                int r2 = r2 - r0
                int r2 = -r2
                goto L_0x002c
            L_0x002b:
                r2 = 0
            L_0x002c:
                android.support.v7.widget.RecyclerView r0 = r1.q
                boolean r4 = r0.canScrollHorizontally(r4)
                if (r4 == 0) goto L_0x0010
                int r4 = r1.getWidth()
                int r0 = r1.getPaddingLeft()
                int r4 = r4 - r0
                int r0 = r1.getPaddingRight()
                int r4 = r4 - r0
                int r4 = -r4
                goto L_0x0072
            L_0x0044:
                android.support.v7.widget.RecyclerView r2 = r1.q
                boolean r2 = r2.canScrollVertically(r5)
                if (r2 == 0) goto L_0x005b
                int r2 = r1.getHeight()
                int r4 = r1.getPaddingTop()
                int r2 = r2 - r4
                int r4 = r1.getPaddingBottom()
                int r2 = r2 - r4
                goto L_0x005c
            L_0x005b:
                r2 = 0
            L_0x005c:
                android.support.v7.widget.RecyclerView r4 = r1.q
                boolean r4 = r4.canScrollHorizontally(r5)
                if (r4 == 0) goto L_0x0010
                int r4 = r1.getWidth()
                int r0 = r1.getPaddingLeft()
                int r4 = r4 - r0
                int r0 = r1.getPaddingRight()
                int r4 = r4 - r0
            L_0x0072:
                if (r2 != 0) goto L_0x0077
                if (r4 != 0) goto L_0x0077
                return r3
            L_0x0077:
                android.support.v7.widget.RecyclerView r3 = r1.q
                r3.scrollBy(r4, r2)
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.LayoutManager.performAccessibilityAction(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State, int, android.os.Bundle):boolean");
        }

        /* access modifiers changed from: 0000 */
        public boolean a(View view, int i, Bundle bundle) {
            return performAccessibilityActionForItem(this.q.d, this.q.C, view, i, bundle);
        }

        public static Properties getProperties(Context context, AttributeSet attributeSet, int i, int i2) {
            Properties properties = new Properties();
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, i, i2);
            properties.orientation = obtainStyledAttributes.getInt(R.styleable.RecyclerView_android_orientation, 1);
            properties.spanCount = obtainStyledAttributes.getInt(R.styleable.RecyclerView_spanCount, 1);
            properties.reverseLayout = obtainStyledAttributes.getBoolean(R.styleable.RecyclerView_reverseLayout, false);
            properties.stackFromEnd = obtainStyledAttributes.getBoolean(R.styleable.RecyclerView_stackFromEnd, false);
            obtainStyledAttributes.recycle();
            return properties;
        }

        /* access modifiers changed from: 0000 */
        public void c(RecyclerView recyclerView) {
            c(MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 1073741824));
        }

        /* access modifiers changed from: 0000 */
        public boolean f() {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.ViewGroup.LayoutParams layoutParams = getChildAt(i).getLayoutParams();
                if (layoutParams.width < 0 && layoutParams.height < 0) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class LayoutParams extends MarginLayoutParams {
        ViewHolder c;
        final Rect d = new Rect();
        boolean e = true;
        boolean f = false;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
        }

        public boolean viewNeedsUpdate() {
            return this.c.k();
        }

        public boolean isViewInvalid() {
            return this.c.j();
        }

        public boolean isItemRemoved() {
            return this.c.m();
        }

        public boolean isItemChanged() {
            return this.c.s();
        }

        @Deprecated
        public int getViewPosition() {
            return this.c.getPosition();
        }

        public int getViewLayoutPosition() {
            return this.c.getLayoutPosition();
        }

        public int getViewAdapterPosition() {
            return this.c.getAdapterPosition();
        }
    }

    public interface OnChildAttachStateChangeListener {
        void onChildViewAttachedToWindow(View view);

        void onChildViewDetachedFromWindow(View view);
    }

    public static abstract class OnFlingListener {
        public abstract boolean onFling(int i, int i2);
    }

    public interface OnItemTouchListener {
        boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent);

        void onRequestDisallowInterceptTouchEvent(boolean z);

        void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent);
    }

    public static abstract class OnScrollListener {
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    public static class RecycledViewPool {
        SparseArray<ScrapData> a = new SparseArray<>();
        private int b = 0;

        static class ScrapData {
            final ArrayList<ViewHolder> a = new ArrayList<>();
            int b = 5;
            long c = 0;
            long d = 0;

            ScrapData() {
            }
        }

        public void clear() {
            for (int i = 0; i < this.a.size(); i++) {
                ((ScrapData) this.a.valueAt(i)).a.clear();
            }
        }

        public void setMaxRecycledViews(int i, int i2) {
            ScrapData a2 = a(i);
            a2.b = i2;
            ArrayList<ViewHolder> arrayList = a2.a;
            while (arrayList.size() > i2) {
                arrayList.remove(arrayList.size() - 1);
            }
        }

        public int getRecycledViewCount(int i) {
            return a(i).a.size();
        }

        @Nullable
        public ViewHolder getRecycledView(int i) {
            ScrapData scrapData = (ScrapData) this.a.get(i);
            if (scrapData == null || scrapData.a.isEmpty()) {
                return null;
            }
            ArrayList<ViewHolder> arrayList = scrapData.a;
            return (ViewHolder) arrayList.remove(arrayList.size() - 1);
        }

        public void putRecycledView(ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            ArrayList<ViewHolder> arrayList = a(itemViewType).a;
            if (((ScrapData) this.a.get(itemViewType)).b > arrayList.size()) {
                viewHolder.r();
                arrayList.add(viewHolder);
            }
        }

        /* access modifiers changed from: 0000 */
        public long a(long j, long j2) {
            return j == 0 ? j2 : ((j / 4) * 3) + (j2 / 4);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, long j) {
            ScrapData a2 = a(i);
            a2.c = a(a2.c, j);
        }

        /* access modifiers changed from: 0000 */
        public void b(int i, long j) {
            ScrapData a2 = a(i);
            a2.d = a(a2.d, j);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(int i, long j, long j2) {
            long j3 = a(i).c;
            return j3 == 0 || j + j3 < j2;
        }

        /* access modifiers changed from: 0000 */
        public boolean b(int i, long j, long j2) {
            long j3 = a(i).d;
            return j3 == 0 || j + j3 < j2;
        }

        /* access modifiers changed from: 0000 */
        public void a(Adapter adapter) {
            this.b++;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.b--;
        }

        /* access modifiers changed from: 0000 */
        public void a(Adapter adapter, Adapter adapter2, boolean z) {
            if (adapter != null) {
                a();
            }
            if (!z && this.b == 0) {
                clear();
            }
            if (adapter2 != null) {
                a(adapter2);
            }
        }

        private ScrapData a(int i) {
            ScrapData scrapData = (ScrapData) this.a.get(i);
            if (scrapData != null) {
                return scrapData;
            }
            ScrapData scrapData2 = new ScrapData();
            this.a.put(i, scrapData2);
            return scrapData2;
        }
    }

    public final class Recycler {
        final ArrayList<ViewHolder> a = new ArrayList<>();
        ArrayList<ViewHolder> b = null;
        final ArrayList<ViewHolder> c = new ArrayList<>();
        int d = 2;
        RecycledViewPool e;
        private final List<ViewHolder> g = Collections.unmodifiableList(this.a);
        private int h = 2;
        private ViewCacheExtension i;

        public Recycler() {
        }

        public void clear() {
            this.a.clear();
            b();
        }

        public void setViewCacheSize(int i2) {
            this.h = i2;
            a();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.d = this.h + (RecyclerView.this.m != null ? RecyclerView.this.m.x : 0);
            for (int size = this.c.size() - 1; size >= 0 && this.c.size() > this.d; size--) {
                a(size);
            }
        }

        public List<ViewHolder> getScrapList() {
            return this.g;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(ViewHolder viewHolder) {
            if (viewHolder.m()) {
                return RecyclerView.this.C.isPreLayout();
            }
            if (viewHolder.b < 0 || viewHolder.b >= RecyclerView.this.l.getItemCount()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Inconsistency detected. Invalid view holder adapter position");
                sb.append(viewHolder);
                sb.append(RecyclerView.this.a());
                throw new IndexOutOfBoundsException(sb.toString());
            }
            boolean z = false;
            if (!RecyclerView.this.C.isPreLayout() && RecyclerView.this.l.getItemViewType(viewHolder.b) != viewHolder.getItemViewType()) {
                return false;
            }
            if (!RecyclerView.this.l.hasStableIds()) {
                return true;
            }
            if (viewHolder.getItemId() == RecyclerView.this.l.getItemId(viewHolder.b)) {
                z = true;
            }
            return z;
        }

        private boolean a(ViewHolder viewHolder, int i2, int i3, long j) {
            viewHolder.l = RecyclerView.this;
            int itemViewType = viewHolder.getItemViewType();
            long nanoTime = RecyclerView.this.getNanoTime();
            if (j != Long.MAX_VALUE && !this.e.b(itemViewType, nanoTime, j)) {
                return false;
            }
            RecyclerView.this.l.bindViewHolder(viewHolder, i2);
            this.e.b(viewHolder.getItemViewType(), RecyclerView.this.getNanoTime() - nanoTime);
            e(viewHolder);
            if (RecyclerView.this.C.isPreLayout()) {
                viewHolder.f = i3;
            }
            return true;
        }

        public void bindViewToPosition(View view, int i2) {
            LayoutParams layoutParams;
            ViewHolder b2 = RecyclerView.b(view);
            if (b2 == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("The view does not have a ViewHolder. You cannot pass arbitrary views to this method, they should be created by the Adapter");
                sb.append(RecyclerView.this.a());
                throw new IllegalArgumentException(sb.toString());
            }
            int b3 = RecyclerView.this.e.b(i2);
            if (b3 < 0 || b3 >= RecyclerView.this.l.getItemCount()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Inconsistency detected. Invalid item position ");
                sb2.append(i2);
                sb2.append("(offset:");
                sb2.append(b3);
                sb2.append(").");
                sb2.append("state:");
                sb2.append(RecyclerView.this.C.getItemCount());
                sb2.append(RecyclerView.this.a());
                throw new IndexOutOfBoundsException(sb2.toString());
            }
            a(b2, b3, i2, Long.MAX_VALUE);
            android.view.ViewGroup.LayoutParams layoutParams2 = b2.itemView.getLayoutParams();
            if (layoutParams2 == null) {
                layoutParams = (LayoutParams) RecyclerView.this.generateDefaultLayoutParams();
                b2.itemView.setLayoutParams(layoutParams);
            } else if (!RecyclerView.this.checkLayoutParams(layoutParams2)) {
                layoutParams = (LayoutParams) RecyclerView.this.generateLayoutParams(layoutParams2);
                b2.itemView.setLayoutParams(layoutParams);
            } else {
                layoutParams = (LayoutParams) layoutParams2;
            }
            boolean z = true;
            layoutParams.e = true;
            layoutParams.c = b2;
            if (b2.itemView.getParent() != null) {
                z = false;
            }
            layoutParams.f = z;
        }

        public int convertPreLayoutPositionToPostLayout(int i2) {
            if (i2 < 0 || i2 >= RecyclerView.this.C.getItemCount()) {
                StringBuilder sb = new StringBuilder();
                sb.append("invalid position ");
                sb.append(i2);
                sb.append(". State ");
                sb.append("item count is ");
                sb.append(RecyclerView.this.C.getItemCount());
                sb.append(RecyclerView.this.a());
                throw new IndexOutOfBoundsException(sb.toString());
            } else if (!RecyclerView.this.C.isPreLayout()) {
                return i2;
            } else {
                return RecyclerView.this.e.b(i2);
            }
        }

        public View getViewForPosition(int i2) {
            return a(i2, false);
        }

        /* access modifiers changed from: 0000 */
        public View a(int i2, boolean z) {
            return a(i2, z, Long.MAX_VALUE).itemView;
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Removed duplicated region for block: B:12:0x002d  */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x0061  */
        /* JADX WARNING: Removed duplicated region for block: B:78:0x01b0  */
        /* JADX WARNING: Removed duplicated region for block: B:81:0x01d5  */
        /* JADX WARNING: Removed duplicated region for block: B:94:0x020b  */
        /* JADX WARNING: Removed duplicated region for block: B:95:0x0219  */
        @android.support.annotation.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.support.v7.widget.RecyclerView.ViewHolder a(int r18, boolean r19, long r20) {
            /*
                r17 = this;
                r6 = r17
                r3 = r18
                r0 = r19
                if (r3 < 0) goto L_0x023c
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r1 = r1.C
                int r1 = r1.getItemCount()
                if (r3 < r1) goto L_0x0014
                goto L_0x023c
            L_0x0014:
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r1 = r1.C
                boolean r1 = r1.isPreLayout()
                r2 = 0
                r7 = 1
                r8 = 0
                if (r1 == 0) goto L_0x0029
                android.support.v7.widget.RecyclerView$ViewHolder r1 = r17.c(r18)
                if (r1 == 0) goto L_0x002a
                r4 = 1
                goto L_0x002b
            L_0x0029:
                r1 = r2
            L_0x002a:
                r4 = 0
            L_0x002b:
                if (r1 != 0) goto L_0x005f
                android.support.v7.widget.RecyclerView$ViewHolder r1 = r17.b(r18, r19)
                if (r1 == 0) goto L_0x005f
                boolean r5 = r6.a(r1)
                if (r5 != 0) goto L_0x005e
                if (r0 != 0) goto L_0x005c
                r5 = 4
                r1.b(r5)
                boolean r5 = r1.d()
                if (r5 == 0) goto L_0x0050
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                android.view.View r9 = r1.itemView
                r5.removeDetachedView(r9, r8)
                r1.e()
                goto L_0x0059
            L_0x0050:
                boolean r5 = r1.f()
                if (r5 == 0) goto L_0x0059
                r1.g()
            L_0x0059:
                r6.b(r1)
            L_0x005c:
                r1 = r2
                goto L_0x005f
            L_0x005e:
                r4 = 1
            L_0x005f:
                if (r1 != 0) goto L_0x018f
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.AdapterHelper r5 = r5.e
                int r5 = r5.b(r3)
                if (r5 < 0) goto L_0x0152
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Adapter r9 = r9.l
                int r9 = r9.getItemCount()
                if (r5 < r9) goto L_0x0077
                goto L_0x0152
            L_0x0077:
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Adapter r9 = r9.l
                int r9 = r9.getItemViewType(r5)
                android.support.v7.widget.RecyclerView r10 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Adapter r10 = r10.l
                boolean r10 = r10.hasStableIds()
                if (r10 == 0) goto L_0x009a
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Adapter r1 = r1.l
                long r10 = r1.getItemId(r5)
                android.support.v7.widget.RecyclerView$ViewHolder r1 = r6.a(r10, r9, r0)
                if (r1 == 0) goto L_0x009a
                r1.b = r5
                r4 = 1
            L_0x009a:
                if (r1 != 0) goto L_0x00f0
                android.support.v7.widget.RecyclerView$ViewCacheExtension r0 = r6.i
                if (r0 == 0) goto L_0x00f0
                android.support.v7.widget.RecyclerView$ViewCacheExtension r0 = r6.i
                android.view.View r0 = r0.getViewForPositionAndType(r6, r3, r9)
                if (r0 == 0) goto L_0x00f0
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$ViewHolder r1 = r1.getChildViewHolder(r0)
                if (r1 != 0) goto L_0x00cd
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "getViewForPositionAndType returned a view which does not have a ViewHolder"
                r1.append(r2)
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                java.lang.String r2 = r2.a()
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                throw r0
            L_0x00cd:
                boolean r0 = r1.c()
                if (r0 == 0) goto L_0x00f0
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view."
                r1.append(r2)
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                java.lang.String r2 = r2.a()
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                throw r0
            L_0x00f0:
                if (r1 != 0) goto L_0x0106
                android.support.v7.widget.RecyclerView$RecycledViewPool r0 = r17.e()
                android.support.v7.widget.RecyclerView$ViewHolder r1 = r0.getRecycledView(r9)
                if (r1 == 0) goto L_0x0106
                r1.r()
                boolean r0 = android.support.v7.widget.RecyclerView.a
                if (r0 == 0) goto L_0x0106
                r6.f(r1)
            L_0x0106:
                if (r1 != 0) goto L_0x018f
                android.support.v7.widget.RecyclerView r0 = android.support.v7.widget.RecyclerView.this
                long r0 = r0.getNanoTime()
                r10 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r5 = (r20 > r10 ? 1 : (r20 == r10 ? 0 : -1))
                if (r5 == 0) goto L_0x0124
                android.support.v7.widget.RecyclerView$RecycledViewPool r10 = r6.e
                r11 = r9
                r12 = r0
                r14 = r20
                boolean r5 = r10.a(r11, r12, r14)
                if (r5 != 0) goto L_0x0124
                return r2
            L_0x0124:
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Adapter r2 = r2.l
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$ViewHolder r2 = r2.createViewHolder(r5, r9)
                boolean r5 = android.support.v7.widget.RecyclerView.L
                if (r5 == 0) goto L_0x0143
                android.view.View r5 = r2.itemView
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.d(r5)
                if (r5 == 0) goto L_0x0143
                java.lang.ref.WeakReference r10 = new java.lang.ref.WeakReference
                r10.<init>(r5)
                r2.a = r10
            L_0x0143:
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                long r10 = r5.getNanoTime()
                android.support.v7.widget.RecyclerView$RecycledViewPool r5 = r6.e
                long r12 = r10 - r0
                r5.a(r9, r12)
                r10 = r2
                goto L_0x0190
            L_0x0152:
                java.lang.IndexOutOfBoundsException r0 = new java.lang.IndexOutOfBoundsException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Inconsistency detected. Invalid item position "
                r1.append(r2)
                r1.append(r3)
                java.lang.String r2 = "(offset:"
                r1.append(r2)
                r1.append(r5)
                java.lang.String r2 = ")."
                r1.append(r2)
                java.lang.String r2 = "state:"
                r1.append(r2)
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r2 = r2.C
                int r2 = r2.getItemCount()
                r1.append(r2)
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                java.lang.String r2 = r2.a()
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                throw r0
            L_0x018f:
                r10 = r1
            L_0x0190:
                r9 = r4
                if (r9 == 0) goto L_0x01cb
                android.support.v7.widget.RecyclerView r0 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r0 = r0.C
                boolean r0 = r0.isPreLayout()
                if (r0 != 0) goto L_0x01cb
                r0 = 8192(0x2000, float:1.14794E-41)
                boolean r1 = r10.a(r0)
                if (r1 == 0) goto L_0x01cb
                r10.a(r8, r0)
                android.support.v7.widget.RecyclerView r0 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r0 = r0.C
                boolean r0 = r0.i
                if (r0 == 0) goto L_0x01cb
                int r0 = android.support.v7.widget.RecyclerView.ItemAnimator.b(r10)
                r0 = r0 | 4096(0x1000, float:5.74E-42)
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$ItemAnimator r1 = r1.y
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r2 = r2.C
                java.util.List r4 = r10.q()
                android.support.v7.widget.RecyclerView$ItemAnimator$ItemHolderInfo r0 = r1.recordPreLayoutInformation(r2, r10, r0, r4)
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                r1.a(r10, r0)
            L_0x01cb:
                android.support.v7.widget.RecyclerView r0 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r0 = r0.C
                boolean r0 = r0.isPreLayout()
                if (r0 == 0) goto L_0x01de
                boolean r0 = r10.l()
                if (r0 == 0) goto L_0x01de
                r10.f = r3
                goto L_0x01f1
            L_0x01de:
                boolean r0 = r10.l()
                if (r0 == 0) goto L_0x01f3
                boolean r0 = r10.k()
                if (r0 != 0) goto L_0x01f3
                boolean r0 = r10.j()
                if (r0 == 0) goto L_0x01f1
                goto L_0x01f3
            L_0x01f1:
                r0 = 0
                goto L_0x0203
            L_0x01f3:
                android.support.v7.widget.RecyclerView r0 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.AdapterHelper r0 = r0.e
                int r2 = r0.b(r3)
                r0 = r6
                r1 = r10
                r4 = r20
                boolean r0 = r0.a(r1, r2, r3, r4)
            L_0x0203:
                android.view.View r1 = r10.itemView
                android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
                if (r1 != 0) goto L_0x0219
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.view.ViewGroup$LayoutParams r1 = r1.generateDefaultLayoutParams()
                android.support.v7.widget.RecyclerView$LayoutParams r1 = (android.support.v7.widget.RecyclerView.LayoutParams) r1
                android.view.View r2 = r10.itemView
                r2.setLayoutParams(r1)
                goto L_0x0231
            L_0x0219:
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                boolean r2 = r2.checkLayoutParams(r1)
                if (r2 != 0) goto L_0x022f
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                android.view.ViewGroup$LayoutParams r1 = r2.generateLayoutParams(r1)
                android.support.v7.widget.RecyclerView$LayoutParams r1 = (android.support.v7.widget.RecyclerView.LayoutParams) r1
                android.view.View r2 = r10.itemView
                r2.setLayoutParams(r1)
                goto L_0x0231
            L_0x022f:
                android.support.v7.widget.RecyclerView$LayoutParams r1 = (android.support.v7.widget.RecyclerView.LayoutParams) r1
            L_0x0231:
                r1.c = r10
                if (r9 == 0) goto L_0x0238
                if (r0 == 0) goto L_0x0238
                goto L_0x0239
            L_0x0238:
                r7 = 0
            L_0x0239:
                r1.f = r7
                return r10
            L_0x023c:
                java.lang.IndexOutOfBoundsException r0 = new java.lang.IndexOutOfBoundsException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Invalid item position "
                r1.append(r2)
                r1.append(r3)
                java.lang.String r2 = "("
                r1.append(r2)
                r1.append(r3)
                java.lang.String r2 = "). Item count:"
                r1.append(r2)
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r2 = r2.C
                int r2 = r2.getItemCount()
                r1.append(r2)
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                java.lang.String r2 = r2.a()
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.Recycler.a(int, boolean, long):android.support.v7.widget.RecyclerView$ViewHolder");
        }

        private void e(ViewHolder viewHolder) {
            if (RecyclerView.this.m()) {
                View view = viewHolder.itemView;
                if (ViewCompat.getImportantForAccessibility(view) == 0) {
                    ViewCompat.setImportantForAccessibility(view, 1);
                }
                if (!ViewCompat.hasAccessibilityDelegate(view)) {
                    viewHolder.b(16384);
                    ViewCompat.setAccessibilityDelegate(view, RecyclerView.this.G.getItemDelegate());
                }
            }
        }

        private void f(ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof ViewGroup) {
                a((ViewGroup) viewHolder.itemView, false);
            }
        }

        private void a(ViewGroup viewGroup, boolean z) {
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                if (childAt instanceof ViewGroup) {
                    a((ViewGroup) childAt, true);
                }
            }
            if (z) {
                if (viewGroup.getVisibility() == 4) {
                    viewGroup.setVisibility(0);
                    viewGroup.setVisibility(4);
                } else {
                    int visibility = viewGroup.getVisibility();
                    viewGroup.setVisibility(4);
                    viewGroup.setVisibility(visibility);
                }
            }
        }

        public void recycleView(View view) {
            ViewHolder b2 = RecyclerView.b(view);
            if (b2.n()) {
                RecyclerView.this.removeDetachedView(view, false);
            }
            if (b2.d()) {
                b2.e();
            } else if (b2.f()) {
                b2.g();
            }
            b(b2);
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            for (int size = this.c.size() - 1; size >= 0; size--) {
                a(size);
            }
            this.c.clear();
            if (RecyclerView.L) {
                RecyclerView.this.B.a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2) {
            a((ViewHolder) this.c.get(i2), true);
            this.c.remove(i2);
        }

        /* access modifiers changed from: 0000 */
        public void b(ViewHolder viewHolder) {
            boolean z;
            boolean z2 = false;
            if (viewHolder.d() || viewHolder.itemView.getParent() != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Scrapped or attached views may not be recycled. isScrap:");
                sb.append(viewHolder.d());
                sb.append(" isAttached:");
                if (viewHolder.itemView.getParent() != null) {
                    z2 = true;
                }
                sb.append(z2);
                sb.append(RecyclerView.this.a());
                throw new IllegalArgumentException(sb.toString());
            } else if (viewHolder.n()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Tmp detached view should be removed from RecyclerView before it can be recycled: ");
                sb2.append(viewHolder);
                sb2.append(RecyclerView.this.a());
                throw new IllegalArgumentException(sb2.toString());
            } else if (viewHolder.c()) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.");
                sb3.append(RecyclerView.this.a());
                throw new IllegalArgumentException(sb3.toString());
            } else {
                boolean a2 = viewHolder.v();
                if ((RecyclerView.this.l != null && a2 && RecyclerView.this.l.onFailedToRecycleView(viewHolder)) || viewHolder.isRecyclable()) {
                    if (this.d <= 0 || viewHolder.a(526)) {
                        z = false;
                    } else {
                        int size = this.c.size();
                        if (size >= this.d && size > 0) {
                            a(0);
                            size--;
                        }
                        if (RecyclerView.L && size > 0 && !RecyclerView.this.B.a(viewHolder.b)) {
                            int i2 = size - 1;
                            while (i2 >= 0) {
                                if (!RecyclerView.this.B.a(((ViewHolder) this.c.get(i2)).b)) {
                                    break;
                                }
                                i2--;
                            }
                            size = i2 + 1;
                        }
                        this.c.add(size, viewHolder);
                        z = true;
                    }
                    if (!z) {
                        a(viewHolder, true);
                        z2 = true;
                    }
                } else {
                    z = false;
                }
                RecyclerView.this.g.g(viewHolder);
                if (!z && !z2 && a2) {
                    viewHolder.l = null;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(ViewHolder viewHolder, boolean z) {
            RecyclerView.c(viewHolder);
            if (viewHolder.a(16384)) {
                viewHolder.a(0, 16384);
                ViewCompat.setAccessibilityDelegate(viewHolder.itemView, null);
            }
            if (z) {
                d(viewHolder);
            }
            viewHolder.l = null;
            e().putRecycledView(viewHolder);
        }

        /* access modifiers changed from: 0000 */
        public void a(View view) {
            ViewHolder b2 = RecyclerView.b(view);
            b2.p = null;
            b2.q = false;
            b2.g();
            b(b2);
        }

        /* access modifiers changed from: 0000 */
        public void b(View view) {
            ViewHolder b2 = RecyclerView.b(view);
            if (!b2.a(12) && b2.s() && !RecyclerView.this.b(b2)) {
                if (this.b == null) {
                    this.b = new ArrayList<>();
                }
                b2.a(this, true);
                this.b.add(b2);
            } else if (!b2.j() || b2.m() || RecyclerView.this.l.hasStableIds()) {
                b2.a(this, false);
                this.a.add(b2);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
                sb.append(RecyclerView.this.a());
                throw new IllegalArgumentException(sb.toString());
            }
        }

        /* access modifiers changed from: 0000 */
        public void c(ViewHolder viewHolder) {
            if (viewHolder.q) {
                this.b.remove(viewHolder);
            } else {
                this.a.remove(viewHolder);
            }
            viewHolder.p = null;
            viewHolder.q = false;
            viewHolder.g();
        }

        /* access modifiers changed from: 0000 */
        public int c() {
            return this.a.size();
        }

        /* access modifiers changed from: 0000 */
        public View b(int i2) {
            return ((ViewHolder) this.a.get(i2)).itemView;
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            this.a.clear();
            if (this.b != null) {
                this.b.clear();
            }
        }

        /* access modifiers changed from: 0000 */
        public ViewHolder c(int i2) {
            if (this.b != null) {
                int size = this.b.size();
                if (size != 0) {
                    int i3 = 0;
                    int i4 = 0;
                    while (i4 < size) {
                        ViewHolder viewHolder = (ViewHolder) this.b.get(i4);
                        if (viewHolder.f() || viewHolder.getLayoutPosition() != i2) {
                            i4++;
                        } else {
                            viewHolder.b(32);
                            return viewHolder;
                        }
                    }
                    if (RecyclerView.this.l.hasStableIds()) {
                        int b2 = RecyclerView.this.e.b(i2);
                        if (b2 > 0 && b2 < RecyclerView.this.l.getItemCount()) {
                            long itemId = RecyclerView.this.l.getItemId(b2);
                            while (i3 < size) {
                                ViewHolder viewHolder2 = (ViewHolder) this.b.get(i3);
                                if (viewHolder2.f() || viewHolder2.getItemId() != itemId) {
                                    i3++;
                                } else {
                                    viewHolder2.b(32);
                                    return viewHolder2;
                                }
                            }
                        }
                    }
                    return null;
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public ViewHolder b(int i2, boolean z) {
            int size = this.a.size();
            int i3 = 0;
            int i4 = 0;
            while (i4 < size) {
                ViewHolder viewHolder = (ViewHolder) this.a.get(i4);
                if (viewHolder.f() || viewHolder.getLayoutPosition() != i2 || viewHolder.j() || (!RecyclerView.this.C.f && viewHolder.m())) {
                    i4++;
                } else {
                    viewHolder.b(32);
                    return viewHolder;
                }
            }
            if (!z) {
                View c2 = RecyclerView.this.f.c(i2);
                if (c2 != null) {
                    ViewHolder b2 = RecyclerView.b(c2);
                    RecyclerView.this.f.e(c2);
                    int b3 = RecyclerView.this.f.b(c2);
                    if (b3 == -1) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("layout index should not be -1 after unhiding a view:");
                        sb.append(b2);
                        sb.append(RecyclerView.this.a());
                        throw new IllegalStateException(sb.toString());
                    }
                    RecyclerView.this.f.e(b3);
                    b(c2);
                    b2.b(8224);
                    return b2;
                }
            }
            int size2 = this.c.size();
            while (i3 < size2) {
                ViewHolder viewHolder2 = (ViewHolder) this.c.get(i3);
                if (viewHolder2.j() || viewHolder2.getLayoutPosition() != i2) {
                    i3++;
                } else {
                    if (!z) {
                        this.c.remove(i3);
                    }
                    return viewHolder2;
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public ViewHolder a(long j, int i2, boolean z) {
            for (int size = this.a.size() - 1; size >= 0; size--) {
                ViewHolder viewHolder = (ViewHolder) this.a.get(size);
                if (viewHolder.getItemId() == j && !viewHolder.f()) {
                    if (i2 == viewHolder.getItemViewType()) {
                        viewHolder.b(32);
                        if (viewHolder.m() && !RecyclerView.this.C.isPreLayout()) {
                            viewHolder.a(2, 14);
                        }
                        return viewHolder;
                    } else if (!z) {
                        this.a.remove(size);
                        RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
                        a(viewHolder.itemView);
                    }
                }
            }
            int size2 = this.c.size();
            while (true) {
                size2--;
                if (size2 < 0) {
                    return null;
                }
                ViewHolder viewHolder2 = (ViewHolder) this.c.get(size2);
                if (viewHolder2.getItemId() == j) {
                    if (i2 == viewHolder2.getItemViewType()) {
                        if (!z) {
                            this.c.remove(size2);
                        }
                        return viewHolder2;
                    } else if (!z) {
                        a(size2);
                        return null;
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void d(ViewHolder viewHolder) {
            if (RecyclerView.this.n != null) {
                RecyclerView.this.n.onViewRecycled(viewHolder);
            }
            if (RecyclerView.this.l != null) {
                RecyclerView.this.l.onViewRecycled(viewHolder);
            }
            if (RecyclerView.this.C != null) {
                RecyclerView.this.g.g(viewHolder);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Adapter adapter, Adapter adapter2, boolean z) {
            clear();
            e().a(adapter, adapter2, z);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2, int i3) {
            int i4;
            int i5;
            int i6;
            if (i2 < i3) {
                i6 = i2;
                i5 = i3;
                i4 = -1;
            } else {
                i5 = i2;
                i6 = i3;
                i4 = 1;
            }
            int size = this.c.size();
            for (int i7 = 0; i7 < size; i7++) {
                ViewHolder viewHolder = (ViewHolder) this.c.get(i7);
                if (viewHolder != null && viewHolder.b >= i6 && viewHolder.b <= i5) {
                    if (viewHolder.b == i2) {
                        viewHolder.a(i3 - i2, false);
                    } else {
                        viewHolder.a(i4, false);
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void b(int i2, int i3) {
            int size = this.c.size();
            for (int i4 = 0; i4 < size; i4++) {
                ViewHolder viewHolder = (ViewHolder) this.c.get(i4);
                if (viewHolder != null && viewHolder.b >= i2) {
                    viewHolder.a(i3, true);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2, int i3, boolean z) {
            int i4 = i2 + i3;
            for (int size = this.c.size() - 1; size >= 0; size--) {
                ViewHolder viewHolder = (ViewHolder) this.c.get(size);
                if (viewHolder != null) {
                    if (viewHolder.b >= i4) {
                        viewHolder.a(-i3, z);
                    } else if (viewHolder.b >= i2) {
                        viewHolder.b(8);
                        a(size);
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(ViewCacheExtension viewCacheExtension) {
            this.i = viewCacheExtension;
        }

        /* access modifiers changed from: 0000 */
        public void a(RecycledViewPool recycledViewPool) {
            if (this.e != null) {
                this.e.a();
            }
            this.e = recycledViewPool;
            if (recycledViewPool != null) {
                this.e.a(RecyclerView.this.getAdapter());
            }
        }

        /* access modifiers changed from: 0000 */
        public RecycledViewPool e() {
            if (this.e == null) {
                this.e = new RecycledViewPool();
            }
            return this.e;
        }

        /* access modifiers changed from: 0000 */
        public void c(int i2, int i3) {
            int i4 = i3 + i2;
            for (int size = this.c.size() - 1; size >= 0; size--) {
                ViewHolder viewHolder = (ViewHolder) this.c.get(size);
                if (viewHolder != null) {
                    int i5 = viewHolder.b;
                    if (i5 >= i2 && i5 < i4) {
                        viewHolder.b(2);
                        a(size);
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void f() {
            int size = this.c.size();
            for (int i2 = 0; i2 < size; i2++) {
                ViewHolder viewHolder = (ViewHolder) this.c.get(i2);
                if (viewHolder != null) {
                    viewHolder.b(6);
                    viewHolder.a((Object) null);
                }
            }
            if (RecyclerView.this.l == null || !RecyclerView.this.l.hasStableIds()) {
                b();
            }
        }

        /* access modifiers changed from: 0000 */
        public void g() {
            int size = this.c.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((ViewHolder) this.c.get(i2)).a();
            }
            int size2 = this.a.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ((ViewHolder) this.a.get(i3)).a();
            }
            if (this.b != null) {
                int size3 = this.b.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    ((ViewHolder) this.b.get(i4)).a();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void h() {
            int size = this.c.size();
            for (int i2 = 0; i2 < size; i2++) {
                LayoutParams layoutParams = (LayoutParams) ((ViewHolder) this.c.get(i2)).itemView.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.e = true;
                }
            }
        }
    }

    public interface RecyclerListener {
        void onViewRecycled(ViewHolder viewHolder);
    }

    class RecyclerViewDataObserver extends AdapterDataObserver {
        RecyclerViewDataObserver() {
        }

        public void onChanged() {
            RecyclerView.this.b((String) null);
            RecyclerView.this.C.e = true;
            RecyclerView.this.c(true);
            if (!RecyclerView.this.e.d()) {
                RecyclerView.this.requestLayout();
            }
        }

        public void onItemRangeChanged(int i, int i2, Object obj) {
            RecyclerView.this.b((String) null);
            if (RecyclerView.this.e.a(i, i2, obj)) {
                a();
            }
        }

        public void onItemRangeInserted(int i, int i2) {
            RecyclerView.this.b((String) null);
            if (RecyclerView.this.e.b(i, i2)) {
                a();
            }
        }

        public void onItemRangeRemoved(int i, int i2) {
            RecyclerView.this.b((String) null);
            if (RecyclerView.this.e.c(i, i2)) {
                a();
            }
        }

        public void onItemRangeMoved(int i, int i2, int i3) {
            RecyclerView.this.b((String) null);
            if (RecyclerView.this.e.a(i, i2, i3)) {
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (!RecyclerView.c || !RecyclerView.this.q || !RecyclerView.this.p) {
                RecyclerView.this.v = true;
                RecyclerView.this.requestLayout();
                return;
            }
            ViewCompat.postOnAnimation(RecyclerView.this, RecyclerView.this.i);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        Parcelable a;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            if (classLoader == null) {
                classLoader = LayoutManager.class.getClassLoader();
            }
            this.a = parcel.readParcelable(classLoader);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.a, 0);
        }

        /* access modifiers changed from: 0000 */
        public void a(SavedState savedState) {
            this.a = savedState.a;
        }
    }

    public static class SimpleOnItemTouchListener implements OnItemTouchListener {
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            return false;
        }

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
        }

        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        }
    }

    public static abstract class SmoothScroller {
        private int a = -1;
        private RecyclerView b;
        private LayoutManager c;
        private boolean d;
        private boolean e;
        private View f;
        private final Action g = new Action(0, 0);

        public static class Action {
            public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
            private int a;
            private int b;
            private int c;
            private int d;
            private Interpolator e;
            private boolean f;
            private int g;

            public Action(int i, int i2) {
                this(i, i2, Integer.MIN_VALUE, null);
            }

            public Action(int i, int i2, int i3) {
                this(i, i2, i3, null);
            }

            public Action(int i, int i2, int i3, Interpolator interpolator) {
                this.d = -1;
                this.f = false;
                this.g = 0;
                this.a = i;
                this.b = i2;
                this.c = i3;
                this.e = interpolator;
            }

            public void jumpTo(int i) {
                this.d = i;
            }

            /* access modifiers changed from: 0000 */
            public boolean a() {
                return this.d >= 0;
            }

            /* access modifiers changed from: 0000 */
            public void a(RecyclerView recyclerView) {
                if (this.d >= 0) {
                    int i = this.d;
                    this.d = -1;
                    recyclerView.a(i);
                    this.f = false;
                    return;
                }
                if (this.f) {
                    b();
                    if (this.e != null) {
                        recyclerView.z.a(this.a, this.b, this.c, this.e);
                    } else if (this.c == Integer.MIN_VALUE) {
                        recyclerView.z.b(this.a, this.b);
                    } else {
                        recyclerView.z.a(this.a, this.b, this.c);
                    }
                    this.g++;
                    if (this.g > 10) {
                        Log.e("RecyclerView", "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                    }
                    this.f = false;
                } else {
                    this.g = 0;
                }
            }

            private void b() {
                if (this.e != null && this.c < 1) {
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                } else if (this.c < 1) {
                    throw new IllegalStateException("Scroll duration must be a positive number");
                }
            }

            public int getDx() {
                return this.a;
            }

            public void setDx(int i) {
                this.f = true;
                this.a = i;
            }

            public int getDy() {
                return this.b;
            }

            public void setDy(int i) {
                this.f = true;
                this.b = i;
            }

            public int getDuration() {
                return this.c;
            }

            public void setDuration(int i) {
                this.f = true;
                this.c = i;
            }

            public Interpolator getInterpolator() {
                return this.e;
            }

            public void setInterpolator(Interpolator interpolator) {
                this.f = true;
                this.e = interpolator;
            }

            public void update(int i, int i2, int i3, Interpolator interpolator) {
                this.a = i;
                this.b = i2;
                this.c = i3;
                this.e = interpolator;
                this.f = true;
            }
        }

        public interface ScrollVectorProvider {
            PointF computeScrollVectorForPosition(int i);
        }

        /* access modifiers changed from: protected */
        public abstract void onSeekTargetStep(int i, int i2, State state, Action action);

        /* access modifiers changed from: protected */
        public abstract void onStart();

        /* access modifiers changed from: protected */
        public abstract void onStop();

        /* access modifiers changed from: protected */
        public abstract void onTargetFound(View view, State state, Action action);

        /* access modifiers changed from: 0000 */
        public void a(RecyclerView recyclerView, LayoutManager layoutManager) {
            this.b = recyclerView;
            this.c = layoutManager;
            if (this.a == -1) {
                throw new IllegalArgumentException("Invalid target position");
            }
            this.b.C.p = this.a;
            this.e = true;
            this.d = true;
            this.f = findViewByPosition(getTargetPosition());
            onStart();
            this.b.z.a();
        }

        public void setTargetPosition(int i) {
            this.a = i;
        }

        @Nullable
        public LayoutManager getLayoutManager() {
            return this.c;
        }

        /* access modifiers changed from: protected */
        public final void stop() {
            if (this.e) {
                onStop();
                this.b.C.p = -1;
                this.f = null;
                this.a = -1;
                this.d = false;
                this.e = false;
                this.c.a(this);
                this.c = null;
                this.b = null;
            }
        }

        public boolean isPendingInitialRun() {
            return this.d;
        }

        public boolean isRunning() {
            return this.e;
        }

        public int getTargetPosition() {
            return this.a;
        }

        /* access modifiers changed from: private */
        public void a(int i, int i2) {
            RecyclerView recyclerView = this.b;
            if (!this.e || this.a == -1 || recyclerView == null) {
                stop();
            }
            this.d = false;
            if (this.f != null) {
                if (getChildPosition(this.f) == this.a) {
                    onTargetFound(this.f, recyclerView.C, this.g);
                    this.g.a(recyclerView);
                    stop();
                } else {
                    Log.e("RecyclerView", "Passed over target position while smooth scrolling.");
                    this.f = null;
                }
            }
            if (this.e) {
                onSeekTargetStep(i, i2, recyclerView.C, this.g);
                boolean a2 = this.g.a();
                this.g.a(recyclerView);
                if (!a2) {
                    return;
                }
                if (this.e) {
                    this.d = true;
                    recyclerView.z.a();
                    return;
                }
                stop();
            }
        }

        public int getChildPosition(View view) {
            return this.b.getChildLayoutPosition(view);
        }

        public int getChildCount() {
            return this.b.m.getChildCount();
        }

        public View findViewByPosition(int i) {
            return this.b.m.findViewByPosition(i);
        }

        @Deprecated
        public void instantScrollToPosition(int i) {
            this.b.scrollToPosition(i);
        }

        /* access modifiers changed from: protected */
        public void onChildAttachedToWindow(View view) {
            if (getChildPosition(view) == getTargetPosition()) {
                this.f = view;
            }
        }

        /* access modifiers changed from: protected */
        public void normalize(PointF pointF) {
            float sqrt = (float) Math.sqrt((double) ((pointF.x * pointF.x) + (pointF.y * pointF.y)));
            pointF.x /= sqrt;
            pointF.y /= sqrt;
        }
    }

    public static class State {
        int a = 0;
        int b = 0;
        int c = 1;
        int d = 0;
        boolean e = false;
        boolean f = false;
        boolean g = false;
        boolean h = false;
        boolean i = false;
        boolean j = false;
        int k;
        long l;
        int m;
        int n;
        int o;
        /* access modifiers changed from: private */
        public int p = -1;
        private SparseArray<Object> q;

        /* access modifiers changed from: 0000 */
        public void a(int i2) {
            if ((this.c & i2) == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("Layout state should be one of ");
                sb.append(Integer.toBinaryString(i2));
                sb.append(" but it is ");
                sb.append(Integer.toBinaryString(this.c));
                throw new IllegalStateException(sb.toString());
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Adapter adapter) {
            this.c = 1;
            this.d = adapter.getItemCount();
            this.f = false;
            this.g = false;
            this.h = false;
        }

        public boolean isMeasuring() {
            return this.h;
        }

        public boolean isPreLayout() {
            return this.f;
        }

        public boolean willRunPredictiveAnimations() {
            return this.j;
        }

        public boolean willRunSimpleAnimations() {
            return this.i;
        }

        public void remove(int i2) {
            if (this.q != null) {
                this.q.remove(i2);
            }
        }

        public <T> T get(int i2) {
            if (this.q == null) {
                return null;
            }
            return this.q.get(i2);
        }

        public void put(int i2, Object obj) {
            if (this.q == null) {
                this.q = new SparseArray<>();
            }
            this.q.put(i2, obj);
        }

        public int getTargetScrollPosition() {
            return this.p;
        }

        public boolean hasTargetScrollPosition() {
            return this.p != -1;
        }

        public boolean didStructureChange() {
            return this.e;
        }

        public int getItemCount() {
            return this.f ? this.a - this.b : this.d;
        }

        public int getRemainingScrollHorizontal() {
            return this.n;
        }

        public int getRemainingScrollVertical() {
            return this.o;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("State{mTargetPosition=");
            sb.append(this.p);
            sb.append(", mData=");
            sb.append(this.q);
            sb.append(", mItemCount=");
            sb.append(this.d);
            sb.append(", mIsMeasuring=");
            sb.append(this.h);
            sb.append(", mPreviousLayoutItemCount=");
            sb.append(this.a);
            sb.append(", mDeletedInvisibleItemCountSincePreviousLayout=");
            sb.append(this.b);
            sb.append(", mStructureChanged=");
            sb.append(this.e);
            sb.append(", mInPreLayout=");
            sb.append(this.f);
            sb.append(", mRunSimpleAnimations=");
            sb.append(this.i);
            sb.append(", mRunPredictiveAnimations=");
            sb.append(this.j);
            sb.append('}');
            return sb.toString();
        }
    }

    public static abstract class ViewCacheExtension {
        public abstract View getViewForPositionAndType(Recycler recycler, int i, int i2);
    }

    class ViewFlinger implements Runnable {
        Interpolator a = RecyclerView.I;
        private int c;
        private int d;
        /* access modifiers changed from: private */
        public OverScroller e;
        private boolean f = false;
        private boolean g = false;

        ViewFlinger() {
            this.e = new OverScroller(RecyclerView.this.getContext(), RecyclerView.I);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:52:0x0134, code lost:
            if (r8 > 0) goto L_0x0138;
         */
        /* JADX WARNING: Removed duplicated region for block: B:50:0x0130  */
        /* JADX WARNING: Removed duplicated region for block: B:56:0x0140  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r23 = this;
                r0 = r23
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$LayoutManager r1 = r1.m
                if (r1 != 0) goto L_0x000c
                r23.b()
                return
            L_0x000c:
                r23.c()
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                r1.d()
                android.widget.OverScroller r1 = r0.e
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$LayoutManager r2 = r2.m
                android.support.v7.widget.RecyclerView$SmoothScroller r2 = r2.t
                boolean r3 = r1.computeScrollOffset()
                r4 = 0
                if (r3 == 0) goto L_0x01dc
                android.support.v7.widget.RecyclerView r3 = android.support.v7.widget.RecyclerView.this
                int[] r3 = r3.aE
                int r11 = r1.getCurrX()
                int r12 = r1.getCurrY()
                int r5 = r0.c
                int r13 = r11 - r5
                int r5 = r0.d
                int r14 = r12 - r5
                r0.c = r11
                r0.d = r12
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                r9 = 0
                r10 = 1
                r6 = r13
                r7 = r14
                r8 = r3
                boolean r5 = r5.dispatchNestedPreScroll(r6, r7, r8, r9, r10)
                r6 = 1
                if (r5 == 0) goto L_0x0051
                r5 = r3[r4]
                int r13 = r13 - r5
                r3 = r3[r6]
                int r14 = r14 - r3
            L_0x0051:
                android.support.v7.widget.RecyclerView r3 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Adapter r3 = r3.l
                if (r3 == 0) goto L_0x00e5
                android.support.v7.widget.RecyclerView r3 = android.support.v7.widget.RecyclerView.this
                r3.e()
                android.support.v7.widget.RecyclerView r3 = android.support.v7.widget.RecyclerView.this
                r3.k()
                java.lang.String r3 = "RV Scroll"
                android.support.v4.os.TraceCompat.beginSection(r3)
                android.support.v7.widget.RecyclerView r3 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r5 = r5.C
                r3.a(r5)
                if (r13 == 0) goto L_0x0084
                android.support.v7.widget.RecyclerView r3 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$LayoutManager r3 = r3.m
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Recycler r5 = r5.d
                android.support.v7.widget.RecyclerView r7 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r7 = r7.C
                int r3 = r3.scrollHorizontallyBy(r13, r5, r7)
                int r5 = r13 - r3
                goto L_0x0086
            L_0x0084:
                r3 = 0
                r5 = 0
            L_0x0086:
                if (r14 == 0) goto L_0x009b
                android.support.v7.widget.RecyclerView r7 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$LayoutManager r7 = r7.m
                android.support.v7.widget.RecyclerView r8 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Recycler r8 = r8.d
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r9 = r9.C
                int r7 = r7.scrollVerticallyBy(r14, r8, r9)
                int r8 = r14 - r7
                goto L_0x009d
            L_0x009b:
                r7 = 0
                r8 = 0
            L_0x009d:
                android.support.v4.os.TraceCompat.endSection()
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                r9.t()
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                r9.l()
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                r9.a(r4)
                if (r2 == 0) goto L_0x00e9
                boolean r9 = r2.isPendingInitialRun()
                if (r9 != 0) goto L_0x00e9
                boolean r9 = r2.isRunning()
                if (r9 == 0) goto L_0x00e9
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r9 = r9.C
                int r9 = r9.getItemCount()
                if (r9 != 0) goto L_0x00cb
                r2.stop()
                goto L_0x00e9
            L_0x00cb:
                int r10 = r2.getTargetPosition()
                if (r10 < r9) goto L_0x00dd
                int r9 = r9 - r6
                r2.setTargetPosition(r9)
                int r9 = r13 - r5
                int r10 = r14 - r8
                r2.a(r9, r10)
                goto L_0x00e9
            L_0x00dd:
                int r9 = r13 - r5
                int r10 = r14 - r8
                r2.a(r9, r10)
                goto L_0x00e9
            L_0x00e5:
                r3 = 0
                r5 = 0
                r7 = 0
                r8 = 0
            L_0x00e9:
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                java.util.ArrayList<android.support.v7.widget.RecyclerView$ItemDecoration> r9 = r9.o
                boolean r9 = r9.isEmpty()
                if (r9 != 0) goto L_0x00f8
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                r9.invalidate()
            L_0x00f8:
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                int r9 = r9.getOverScrollMode()
                r10 = 2
                if (r9 == r10) goto L_0x0106
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                r9.a(r13, r14)
            L_0x0106:
                android.support.v7.widget.RecyclerView r15 = android.support.v7.widget.RecyclerView.this
                r20 = 0
                r21 = 1
                r16 = r3
                r17 = r7
                r18 = r5
                r19 = r8
                boolean r9 = r15.dispatchNestedScroll(r16, r17, r18, r19, r20, r21)
                if (r9 != 0) goto L_0x015c
                if (r5 != 0) goto L_0x011e
                if (r8 == 0) goto L_0x015c
            L_0x011e:
                float r9 = r1.getCurrVelocity()
                int r9 = (int) r9
                if (r5 == r11) goto L_0x012d
                if (r5 >= 0) goto L_0x0129
                int r15 = -r9
                goto L_0x012e
            L_0x0129:
                if (r5 <= 0) goto L_0x012d
                r15 = r9
                goto L_0x012e
            L_0x012d:
                r15 = 0
            L_0x012e:
                if (r8 == r12) goto L_0x0137
                if (r8 >= 0) goto L_0x0134
                int r9 = -r9
                goto L_0x0138
            L_0x0134:
                if (r8 <= 0) goto L_0x0137
                goto L_0x0138
            L_0x0137:
                r9 = 0
            L_0x0138:
                android.support.v7.widget.RecyclerView r4 = android.support.v7.widget.RecyclerView.this
                int r4 = r4.getOverScrollMode()
                if (r4 == r10) goto L_0x0145
                android.support.v7.widget.RecyclerView r4 = android.support.v7.widget.RecyclerView.this
                r4.b(r15, r9)
            L_0x0145:
                if (r15 != 0) goto L_0x014f
                if (r5 == r11) goto L_0x014f
                int r4 = r1.getFinalX()
                if (r4 != 0) goto L_0x015c
            L_0x014f:
                if (r9 != 0) goto L_0x0159
                if (r8 == r12) goto L_0x0159
                int r4 = r1.getFinalY()
                if (r4 != 0) goto L_0x015c
            L_0x0159:
                r1.abortAnimation()
            L_0x015c:
                if (r3 != 0) goto L_0x0160
                if (r7 == 0) goto L_0x0165
            L_0x0160:
                android.support.v7.widget.RecyclerView r4 = android.support.v7.widget.RecyclerView.this
                r4.f(r3, r7)
            L_0x0165:
                android.support.v7.widget.RecyclerView r4 = android.support.v7.widget.RecyclerView.this
                boolean r4 = r4.awakenScrollBars()
                if (r4 != 0) goto L_0x0172
                android.support.v7.widget.RecyclerView r4 = android.support.v7.widget.RecyclerView.this
                r4.invalidate()
            L_0x0172:
                if (r14 == 0) goto L_0x0182
                android.support.v7.widget.RecyclerView r4 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$LayoutManager r4 = r4.m
                boolean r4 = r4.canScrollVertically()
                if (r4 == 0) goto L_0x0182
                if (r7 != r14) goto L_0x0182
                r4 = 1
                goto L_0x0183
            L_0x0182:
                r4 = 0
            L_0x0183:
                if (r13 == 0) goto L_0x0193
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$LayoutManager r5 = r5.m
                boolean r5 = r5.canScrollHorizontally()
                if (r5 == 0) goto L_0x0193
                if (r3 != r13) goto L_0x0193
                r3 = 1
                goto L_0x0194
            L_0x0193:
                r3 = 0
            L_0x0194:
                if (r13 != 0) goto L_0x0198
                if (r14 == 0) goto L_0x019f
            L_0x0198:
                if (r3 != 0) goto L_0x019f
                if (r4 == 0) goto L_0x019d
                goto L_0x019f
            L_0x019d:
                r3 = 0
                goto L_0x01a0
            L_0x019f:
                r3 = 1
            L_0x01a0:
                boolean r1 = r1.isFinished()
                if (r1 != 0) goto L_0x01c4
                if (r3 != 0) goto L_0x01b1
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                boolean r1 = r1.hasNestedScrollingParent(r6)
                if (r1 != 0) goto L_0x01b1
                goto L_0x01c4
            L_0x01b1:
                r23.a()
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.GapWorker r1 = r1.A
                if (r1 == 0) goto L_0x01dc
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.GapWorker r1 = r1.A
                android.support.v7.widget.RecyclerView r3 = android.support.v7.widget.RecyclerView.this
                r1.a(r3, r13, r14)
                goto L_0x01dc
            L_0x01c4:
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                r3 = 0
                r1.setScrollState(r3)
                boolean r1 = android.support.v7.widget.RecyclerView.L
                if (r1 == 0) goto L_0x01d7
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.GapWorker$LayoutPrefetchRegistryImpl r1 = r1.B
                r1.a()
            L_0x01d7:
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                r1.stopNestedScroll(r6)
            L_0x01dc:
                if (r2 == 0) goto L_0x01ef
                boolean r1 = r2.isPendingInitialRun()
                if (r1 == 0) goto L_0x01e8
                r1 = 0
                r2.a(r1, r1)
            L_0x01e8:
                boolean r1 = r0.g
                if (r1 != 0) goto L_0x01ef
                r2.stop()
            L_0x01ef:
                r23.d()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.ViewFlinger.run():void");
        }

        private void c() {
            this.g = false;
            this.f = true;
        }

        private void d() {
            this.f = false;
            if (this.g) {
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (this.f) {
                this.g = true;
                return;
            }
            RecyclerView.this.removeCallbacks(this);
            ViewCompat.postOnAnimation(RecyclerView.this, this);
        }

        public void a(int i, int i2) {
            RecyclerView.this.setScrollState(2);
            this.d = 0;
            this.c = 0;
            this.e.fling(0, 0, i, i2, Integer.MIN_VALUE, SubsamplingScaleImageView.TILE_SIZE_AUTO, Integer.MIN_VALUE, SubsamplingScaleImageView.TILE_SIZE_AUTO);
            a();
        }

        public void b(int i, int i2) {
            a(i, i2, 0, 0);
        }

        public void a(int i, int i2, int i3, int i4) {
            a(i, i2, b(i, i2, i3, i4));
        }

        private float a(float f2) {
            return (float) Math.sin((double) ((f2 - 0.5f) * 0.47123894f));
        }

        private int b(int i, int i2, int i3, int i4) {
            int i5;
            int abs = Math.abs(i);
            int abs2 = Math.abs(i2);
            boolean z = abs > abs2;
            int sqrt = (int) Math.sqrt((double) ((i3 * i3) + (i4 * i4)));
            int sqrt2 = (int) Math.sqrt((double) ((i * i) + (i2 * i2)));
            int width = z ? RecyclerView.this.getWidth() : RecyclerView.this.getHeight();
            int i6 = width / 2;
            float f2 = (float) width;
            float f3 = (float) i6;
            float a2 = f3 + (a(Math.min(1.0f, (((float) sqrt2) * 1.0f) / f2)) * f3);
            if (sqrt > 0) {
                i5 = Math.round(Math.abs(a2 / ((float) sqrt)) * 1000.0f) * 4;
            } else {
                if (!z) {
                    abs = abs2;
                }
                i5 = (int) (((((float) abs) / f2) + 1.0f) * 300.0f);
            }
            return Math.min(i5, 2000);
        }

        public void a(int i, int i2, int i3) {
            a(i, i2, i3, RecyclerView.I);
        }

        public void a(int i, int i2, Interpolator interpolator) {
            int b2 = b(i, i2, 0, 0);
            if (interpolator == null) {
                interpolator = RecyclerView.I;
            }
            a(i, i2, b2, interpolator);
        }

        public void a(int i, int i2, int i3, Interpolator interpolator) {
            if (this.a != interpolator) {
                this.a = interpolator;
                this.e = new OverScroller(RecyclerView.this.getContext(), interpolator);
            }
            RecyclerView.this.setScrollState(2);
            this.d = 0;
            this.c = 0;
            this.e.startScroll(0, 0, i, i2, i3);
            if (VERSION.SDK_INT < 23) {
                this.e.computeScrollOffset();
            }
            a();
        }

        public void b() {
            RecyclerView.this.removeCallbacks(this);
            this.e.abortAnimation();
        }
    }

    public static abstract class ViewHolder {
        private static final List<Object> n = Collections.EMPTY_LIST;
        WeakReference<RecyclerView> a;
        int b = -1;
        int c = -1;
        long d = -1;
        int e = -1;
        int f = -1;
        ViewHolder g = null;
        ViewHolder h = null;
        List<Object> i = null;
        public final View itemView;
        List<Object> j = null;
        @VisibleForTesting
        int k = -1;
        RecyclerView l;
        /* access modifiers changed from: private */
        public int m;
        private int o = 0;
        /* access modifiers changed from: private */
        public Recycler p = null;
        /* access modifiers changed from: private */
        public boolean q = false;
        private int r = 0;

        public ViewHolder(View view) {
            if (view == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = view;
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2, int i3, boolean z) {
            b(8);
            a(i3, z);
            this.b = i2;
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2, boolean z) {
            if (this.c == -1) {
                this.c = this.b;
            }
            if (this.f == -1) {
                this.f = this.b;
            }
            if (z) {
                this.f += i2;
            }
            this.b += i2;
            if (this.itemView.getLayoutParams() != null) {
                ((LayoutParams) this.itemView.getLayoutParams()).e = true;
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.c = -1;
            this.f = -1;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (this.c == -1) {
                this.c = this.b;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean c() {
            return (this.m & 128) != 0;
        }

        @Deprecated
        public final int getPosition() {
            return this.f == -1 ? this.b : this.f;
        }

        public final int getLayoutPosition() {
            return this.f == -1 ? this.b : this.f;
        }

        public final int getAdapterPosition() {
            if (this.l == null) {
                return -1;
            }
            return this.l.d(this);
        }

        public final int getOldPosition() {
            return this.c;
        }

        public final long getItemId() {
            return this.d;
        }

        public final int getItemViewType() {
            return this.e;
        }

        /* access modifiers changed from: 0000 */
        public boolean d() {
            return this.p != null;
        }

        /* access modifiers changed from: 0000 */
        public void e() {
            this.p.c(this);
        }

        /* access modifiers changed from: 0000 */
        public boolean f() {
            return (this.m & 32) != 0;
        }

        /* access modifiers changed from: 0000 */
        public void g() {
            this.m &= -33;
        }

        /* access modifiers changed from: 0000 */
        public void h() {
            this.m &= -257;
        }

        /* access modifiers changed from: 0000 */
        public void i() {
            this.m &= -129;
        }

        /* access modifiers changed from: 0000 */
        public void a(Recycler recycler, boolean z) {
            this.p = recycler;
            this.q = z;
        }

        /* access modifiers changed from: 0000 */
        public boolean j() {
            return (this.m & 4) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean k() {
            return (this.m & 2) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean l() {
            return (this.m & 1) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean m() {
            return (this.m & 8) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(int i2) {
            return (i2 & this.m) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean n() {
            return (this.m & 256) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean o() {
            return (this.m & 512) != 0 || j();
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2, int i3) {
            this.m = (i2 & i3) | (this.m & (i3 ^ -1));
        }

        /* access modifiers changed from: 0000 */
        public void b(int i2) {
            this.m = i2 | this.m;
        }

        /* access modifiers changed from: 0000 */
        public void a(Object obj) {
            if (obj == null) {
                b(1024);
            } else if ((1024 & this.m) == 0) {
                t();
                this.i.add(obj);
            }
        }

        private void t() {
            if (this.i == null) {
                this.i = new ArrayList();
                this.j = Collections.unmodifiableList(this.i);
            }
        }

        /* access modifiers changed from: 0000 */
        public void p() {
            if (this.i != null) {
                this.i.clear();
            }
            this.m &= -1025;
        }

        /* access modifiers changed from: 0000 */
        public List<Object> q() {
            if ((this.m & 1024) != 0) {
                return n;
            }
            if (this.i == null || this.i.size() == 0) {
                return n;
            }
            return this.j;
        }

        /* access modifiers changed from: 0000 */
        public void r() {
            this.m = 0;
            this.b = -1;
            this.c = -1;
            this.d = -1;
            this.f = -1;
            this.o = 0;
            this.g = null;
            this.h = null;
            p();
            this.r = 0;
            this.k = -1;
            RecyclerView.c(this);
        }

        /* access modifiers changed from: private */
        public void a(RecyclerView recyclerView) {
            if (this.k != -1) {
                this.r = this.k;
            } else {
                this.r = ViewCompat.getImportantForAccessibility(this.itemView);
            }
            recyclerView.a(this, 4);
        }

        /* access modifiers changed from: private */
        public void b(RecyclerView recyclerView) {
            recyclerView.a(this, this.r);
            this.r = 0;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ViewHolder{");
            sb.append(Integer.toHexString(hashCode()));
            sb.append(" position=");
            sb.append(this.b);
            sb.append(" id=");
            sb.append(this.d);
            sb.append(", oldPos=");
            sb.append(this.c);
            sb.append(", pLpos:");
            sb.append(this.f);
            StringBuilder sb2 = new StringBuilder(sb.toString());
            if (d()) {
                sb2.append(" scrap ");
                sb2.append(this.q ? "[changeScrap]" : "[attachedScrap]");
            }
            if (j()) {
                sb2.append(" invalid");
            }
            if (!l()) {
                sb2.append(" unbound");
            }
            if (k()) {
                sb2.append(" update");
            }
            if (m()) {
                sb2.append(" removed");
            }
            if (c()) {
                sb2.append(" ignored");
            }
            if (n()) {
                sb2.append(" tmpDetached");
            }
            if (!isRecyclable()) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(" not recyclable(");
                sb3.append(this.o);
                sb3.append(")");
                sb2.append(sb3.toString());
            }
            if (o()) {
                sb2.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                sb2.append(" no parent");
            }
            sb2.append("}");
            return sb2.toString();
        }

        public final void setIsRecyclable(boolean z) {
            this.o = z ? this.o - 1 : this.o + 1;
            if (this.o < 0) {
                this.o = 0;
                StringBuilder sb = new StringBuilder();
                sb.append("isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for ");
                sb.append(this);
                Log.e("View", sb.toString());
            } else if (!z && this.o == 1) {
                this.m |= 16;
            } else if (z && this.o == 0) {
                this.m &= -17;
            }
        }

        public final boolean isRecyclable() {
            return (this.m & 16) == 0 && !ViewCompat.hasTransientState(this.itemView);
        }

        /* access modifiers changed from: private */
        public boolean u() {
            return (this.m & 16) != 0;
        }

        /* access modifiers changed from: private */
        public boolean v() {
            return (this.m & 16) == 0 && ViewCompat.hasTransientState(this.itemView);
        }

        /* access modifiers changed from: 0000 */
        public boolean s() {
            return (this.m & 2) != 0;
        }
    }

    public void onChildAttachedToWindow(View view) {
    }

    public void onChildDetachedFromWindow(View view) {
    }

    public void onScrollStateChanged(int i2) {
    }

    public void onScrolled(int i2, int i3) {
    }

    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.P = new RecyclerViewDataObserver();
        this.d = new Recycler();
        this.g = new ViewInfoStore();
        this.i = new Runnable() {
            public void run() {
                if (RecyclerView.this.s && !RecyclerView.this.isLayoutRequested()) {
                    if (!RecyclerView.this.p) {
                        RecyclerView.this.requestLayout();
                    } else if (RecyclerView.this.u) {
                        RecyclerView.this.t = true;
                    } else {
                        RecyclerView.this.d();
                    }
                }
            }
        };
        this.j = new Rect();
        this.R = new Rect();
        this.k = new RectF();
        this.o = new ArrayList<>();
        this.S = new ArrayList<>();
        this.U = 0;
        this.w = false;
        this.x = false;
        this.ac = 0;
        this.ad = 0;
        this.ae = new EdgeEffectFactory();
        this.y = new DefaultItemAnimator();
        this.aj = 0;
        this.ak = -1;
        this.au = Float.MIN_VALUE;
        this.av = Float.MIN_VALUE;
        boolean z2 = true;
        this.aw = true;
        this.z = new ViewFlinger();
        this.B = L ? new LayoutPrefetchRegistryImpl() : null;
        this.C = new State();
        this.D = false;
        this.E = false;
        this.az = new ItemAnimatorRestoreListener();
        this.F = false;
        this.aB = new int[2];
        this.aD = new int[2];
        this.aE = new int[2];
        this.aF = new int[2];
        this.H = new ArrayList();
        this.aG = new Runnable() {
            public void run() {
                if (RecyclerView.this.y != null) {
                    RecyclerView.this.y.runPendingAnimations();
                }
                RecyclerView.this.F = false;
            }
        };
        this.aH = new ProcessCallback() {
            public void a(ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2) {
                RecyclerView.this.d.c(viewHolder);
                RecyclerView.this.b(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            public void b(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2) {
                RecyclerView.this.a(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            public void c(ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2) {
                viewHolder.setIsRecyclable(false);
                if (RecyclerView.this.w) {
                    if (RecyclerView.this.y.animateChange(viewHolder, viewHolder, itemHolderInfo, itemHolderInfo2)) {
                        RecyclerView.this.n();
                    }
                } else if (RecyclerView.this.y.animatePersistence(viewHolder, itemHolderInfo, itemHolderInfo2)) {
                    RecyclerView.this.n();
                }
            }

            public void a(ViewHolder viewHolder) {
                RecyclerView.this.m.removeAndRecycleView(viewHolder.itemView, RecyclerView.this.d);
            }
        };
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, K, i2, 0);
            this.h = obtainStyledAttributes.getBoolean(0, true);
            obtainStyledAttributes.recycle();
        } else {
            this.h = true;
        }
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.aq = viewConfiguration.getScaledTouchSlop();
        this.au = ViewConfigurationCompat.getScaledHorizontalScrollFactor(viewConfiguration, context);
        this.av = ViewConfigurationCompat.getScaledVerticalScrollFactor(viewConfiguration, context);
        this.as = viewConfiguration.getScaledMinimumFlingVelocity();
        this.at = viewConfiguration.getScaledMaximumFlingVelocity();
        setWillNotDraw(getOverScrollMode() == 2);
        this.y.a(this.az);
        b();
        w();
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        this.aa = (AccessibilityManager) getContext().getSystemService("accessibility");
        setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this));
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, i2, 0);
            String string = obtainStyledAttributes2.getString(R.styleable.RecyclerView_layoutManager);
            if (obtainStyledAttributes2.getInt(R.styleable.RecyclerView_android_descendantFocusability, -1) == -1) {
                setDescendantFocusability(262144);
            }
            this.r = obtainStyledAttributes2.getBoolean(R.styleable.RecyclerView_fastScrollEnabled, false);
            if (this.r) {
                a((StateListDrawable) obtainStyledAttributes2.getDrawable(R.styleable.RecyclerView_fastScrollVerticalThumbDrawable), obtainStyledAttributes2.getDrawable(R.styleable.RecyclerView_fastScrollVerticalTrackDrawable), (StateListDrawable) obtainStyledAttributes2.getDrawable(R.styleable.RecyclerView_fastScrollHorizontalThumbDrawable), obtainStyledAttributes2.getDrawable(R.styleable.RecyclerView_fastScrollHorizontalTrackDrawable));
            }
            obtainStyledAttributes2.recycle();
            a(context, string, attributeSet, i2, 0);
            if (VERSION.SDK_INT >= 21) {
                TypedArray obtainStyledAttributes3 = context.obtainStyledAttributes(attributeSet, J, i2, 0);
                boolean z3 = obtainStyledAttributes3.getBoolean(0, true);
                obtainStyledAttributes3.recycle();
                z2 = z3;
            }
        } else {
            setDescendantFocusability(262144);
        }
        setNestedScrollingEnabled(z2);
    }

    /* access modifiers changed from: 0000 */
    public String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(super.toString());
        sb.append(", adapter:");
        sb.append(this.l);
        sb.append(", layout:");
        sb.append(this.m);
        sb.append(", context:");
        sb.append(getContext());
        return sb.toString();
    }

    public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate() {
        return this.G;
    }

    public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate) {
        this.G = recyclerViewAccessibilityDelegate;
        ViewCompat.setAccessibilityDelegate(this, this.G);
    }

    private void a(Context context, String str, AttributeSet attributeSet, int i2, int i3) {
        ClassLoader classLoader;
        Constructor constructor;
        if (str != null) {
            String trim = str.trim();
            if (!trim.isEmpty()) {
                String a2 = a(context, trim);
                try {
                    if (isInEditMode()) {
                        classLoader = getClass().getClassLoader();
                    } else {
                        classLoader = context.getClassLoader();
                    }
                    Class asSubclass = classLoader.loadClass(a2).asSubclass(LayoutManager.class);
                    Object[] objArr = null;
                    try {
                        constructor = asSubclass.getConstructor(O);
                        objArr = new Object[]{context, attributeSet, Integer.valueOf(i2), Integer.valueOf(i3)};
                    } catch (NoSuchMethodException e2) {
                        constructor = asSubclass.getConstructor(new Class[0]);
                    }
                    constructor.setAccessible(true);
                    setLayoutManager((LayoutManager) constructor.newInstance(objArr));
                } catch (NoSuchMethodException e3) {
                    e3.initCause(e2);
                    StringBuilder sb = new StringBuilder();
                    sb.append(attributeSet.getPositionDescription());
                    sb.append(": Error creating LayoutManager ");
                    sb.append(a2);
                    throw new IllegalStateException(sb.toString(), e3);
                } catch (ClassNotFoundException e4) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(attributeSet.getPositionDescription());
                    sb2.append(": Unable to find LayoutManager ");
                    sb2.append(a2);
                    throw new IllegalStateException(sb2.toString(), e4);
                } catch (InvocationTargetException e5) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(attributeSet.getPositionDescription());
                    sb3.append(": Could not instantiate the LayoutManager: ");
                    sb3.append(a2);
                    throw new IllegalStateException(sb3.toString(), e5);
                } catch (InstantiationException e6) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(attributeSet.getPositionDescription());
                    sb4.append(": Could not instantiate the LayoutManager: ");
                    sb4.append(a2);
                    throw new IllegalStateException(sb4.toString(), e6);
                } catch (IllegalAccessException e7) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(attributeSet.getPositionDescription());
                    sb5.append(": Cannot access non-public constructor ");
                    sb5.append(a2);
                    throw new IllegalStateException(sb5.toString(), e7);
                } catch (ClassCastException e8) {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(attributeSet.getPositionDescription());
                    sb6.append(": Class is not a LayoutManager ");
                    sb6.append(a2);
                    throw new IllegalStateException(sb6.toString(), e8);
                }
            }
        }
    }

    private String a(Context context, String str) {
        if (str.charAt(0) == '.') {
            StringBuilder sb = new StringBuilder();
            sb.append(context.getPackageName());
            sb.append(str);
            return sb.toString();
        } else if (str.contains(".")) {
            return str;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(RecyclerView.class.getPackage().getName());
            sb2.append('.');
            sb2.append(str);
            return sb2.toString();
        }
    }

    private void w() {
        this.f = new ChildHelper(new Callback() {
            public int a() {
                return RecyclerView.this.getChildCount();
            }

            public void a(View view, int i) {
                RecyclerView.this.addView(view, i);
                RecyclerView.this.f(view);
            }

            public int a(View view) {
                return RecyclerView.this.indexOfChild(view);
            }

            public void a(int i) {
                View childAt = RecyclerView.this.getChildAt(i);
                if (childAt != null) {
                    RecyclerView.this.e(childAt);
                    childAt.clearAnimation();
                }
                RecyclerView.this.removeViewAt(i);
            }

            public View b(int i) {
                return RecyclerView.this.getChildAt(i);
            }

            public void b() {
                int a2 = a();
                for (int i = 0; i < a2; i++) {
                    View b = b(i);
                    RecyclerView.this.e(b);
                    b.clearAnimation();
                }
                RecyclerView.this.removeAllViews();
            }

            public ViewHolder b(View view) {
                return RecyclerView.b(view);
            }

            public void a(View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
                ViewHolder b = RecyclerView.b(view);
                if (b != null) {
                    if (b.n() || b.c()) {
                        b.h();
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Called attach on a child which is not detached: ");
                        sb.append(b);
                        sb.append(RecyclerView.this.a());
                        throw new IllegalArgumentException(sb.toString());
                    }
                }
                RecyclerView.this.attachViewToParent(view, i, layoutParams);
            }

            public void c(int i) {
                View b = b(i);
                if (b != null) {
                    ViewHolder b2 = RecyclerView.b(b);
                    if (b2 != null) {
                        if (!b2.n() || b2.c()) {
                            b2.b(256);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("called detach on an already detached child ");
                            sb.append(b2);
                            sb.append(RecyclerView.this.a());
                            throw new IllegalArgumentException(sb.toString());
                        }
                    }
                }
                RecyclerView.this.detachViewFromParent(i);
            }

            public void c(View view) {
                ViewHolder b = RecyclerView.b(view);
                if (b != null) {
                    b.a(RecyclerView.this);
                }
            }

            public void d(View view) {
                ViewHolder b = RecyclerView.b(view);
                if (b != null) {
                    b.b(RecyclerView.this);
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        this.e = new AdapterHelper(new Callback() {
            public ViewHolder a(int i) {
                ViewHolder a2 = RecyclerView.this.a(i, true);
                if (a2 != null && !RecyclerView.this.f.c(a2.itemView)) {
                    return a2;
                }
                return null;
            }

            public void a(int i, int i2) {
                RecyclerView.this.a(i, i2, true);
                RecyclerView.this.D = true;
                RecyclerView.this.C.b += i2;
            }

            public void b(int i, int i2) {
                RecyclerView.this.a(i, i2, false);
                RecyclerView.this.D = true;
            }

            public void a(int i, int i2, Object obj) {
                RecyclerView.this.a(i, i2, obj);
                RecyclerView.this.E = true;
            }

            public void a(UpdateOp updateOp) {
                c(updateOp);
            }

            /* access modifiers changed from: 0000 */
            public void c(UpdateOp updateOp) {
                int i = updateOp.a;
                if (i == 4) {
                    RecyclerView.this.m.onItemsUpdated(RecyclerView.this, updateOp.b, updateOp.d, updateOp.c);
                } else if (i != 8) {
                    switch (i) {
                        case 1:
                            RecyclerView.this.m.onItemsAdded(RecyclerView.this, updateOp.b, updateOp.d);
                            return;
                        case 2:
                            RecyclerView.this.m.onItemsRemoved(RecyclerView.this, updateOp.b, updateOp.d);
                            return;
                        default:
                            return;
                    }
                } else {
                    RecyclerView.this.m.onItemsMoved(RecyclerView.this, updateOp.b, updateOp.d, 1);
                }
            }

            public void b(UpdateOp updateOp) {
                c(updateOp);
            }

            public void c(int i, int i2) {
                RecyclerView.this.e(i, i2);
                RecyclerView.this.D = true;
            }

            public void d(int i, int i2) {
                RecyclerView.this.d(i, i2);
                RecyclerView.this.D = true;
            }
        });
    }

    public void setHasFixedSize(boolean z2) {
        this.q = z2;
    }

    public boolean hasFixedSize() {
        return this.q;
    }

    public void setClipToPadding(boolean z2) {
        if (z2 != this.h) {
            j();
        }
        this.h = z2;
        super.setClipToPadding(z2);
        if (this.s) {
            requestLayout();
        }
    }

    public boolean getClipToPadding() {
        return this.h;
    }

    public void setScrollingTouchSlop(int i2) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        switch (i2) {
            case 0:
                break;
            case 1:
                this.aq = viewConfiguration.getScaledPagingTouchSlop();
                return;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("setScrollingTouchSlop(): bad argument constant ");
                sb.append(i2);
                sb.append("; using default value");
                Log.w("RecyclerView", sb.toString());
                break;
        }
        this.aq = viewConfiguration.getScaledTouchSlop();
    }

    public void swapAdapter(Adapter adapter, boolean z2) {
        setLayoutFrozen(false);
        a(adapter, true, z2);
        c(true);
        requestLayout();
    }

    public void setAdapter(Adapter adapter) {
        setLayoutFrozen(false);
        a(adapter, false, true);
        c(false);
        requestLayout();
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        if (this.y != null) {
            this.y.endAnimations();
        }
        if (this.m != null) {
            this.m.removeAndRecycleAllViews(this.d);
            this.m.a(this.d);
        }
        this.d.clear();
    }

    private void a(Adapter adapter, boolean z2, boolean z3) {
        if (this.l != null) {
            this.l.unregisterAdapterDataObserver(this.P);
            this.l.onDetachedFromRecyclerView(this);
        }
        if (!z2 || z3) {
            c();
        }
        this.e.a();
        Adapter adapter2 = this.l;
        this.l = adapter;
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.P);
            adapter.onAttachedToRecyclerView(this);
        }
        if (this.m != null) {
            this.m.onAdapterChanged(adapter2, this.l);
        }
        this.d.a(adapter2, this.l, z2);
        this.C.e = true;
    }

    public Adapter getAdapter() {
        return this.l;
    }

    public void setRecyclerListener(RecyclerListener recyclerListener) {
        this.n = recyclerListener;
    }

    public int getBaseline() {
        if (this.m != null) {
            return this.m.getBaseline();
        }
        return super.getBaseline();
    }

    public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        if (this.ab == null) {
            this.ab = new ArrayList();
        }
        this.ab.add(onChildAttachStateChangeListener);
    }

    public void removeOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        if (this.ab != null) {
            this.ab.remove(onChildAttachStateChangeListener);
        }
    }

    public void clearOnChildAttachStateChangeListeners() {
        if (this.ab != null) {
            this.ab.clear();
        }
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        if (layoutManager != this.m) {
            stopScroll();
            if (this.m != null) {
                if (this.y != null) {
                    this.y.endAnimations();
                }
                this.m.removeAndRecycleAllViews(this.d);
                this.m.a(this.d);
                this.d.clear();
                if (this.p) {
                    this.m.a(this, this.d);
                }
                this.m.a((RecyclerView) null);
                this.m = null;
            } else {
                this.d.clear();
            }
            this.f.a();
            this.m = layoutManager;
            if (layoutManager != null) {
                if (layoutManager.q != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("LayoutManager ");
                    sb.append(layoutManager);
                    sb.append(" is already attached to a RecyclerView:");
                    sb.append(layoutManager.q.a());
                    throw new IllegalArgumentException(sb.toString());
                }
                this.m.a(this);
                if (this.p) {
                    this.m.b(this);
                }
            }
            this.d.a();
            requestLayout();
        }
    }

    public void setOnFlingListener(@Nullable OnFlingListener onFlingListener) {
        this.f235ar = onFlingListener;
    }

    @Nullable
    public OnFlingListener getOnFlingListener() {
        return this.f235ar;
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.Q != null) {
            savedState.a(this.Q);
        } else if (this.m != null) {
            savedState.a = this.m.onSaveInstanceState();
        } else {
            savedState.a = null;
        }
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        this.Q = (SavedState) parcelable;
        super.onRestoreInstanceState(this.Q.getSuperState());
        if (!(this.m == null || this.Q.a == null)) {
            this.m.onRestoreInstanceState(this.Q.a);
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    private void e(ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        boolean z2 = view.getParent() == this;
        this.d.c(getChildViewHolder(view));
        if (viewHolder.n()) {
            this.f.a(view, -1, view.getLayoutParams(), true);
        } else if (!z2) {
            this.f.a(view, true);
        } else {
            this.f.d(view);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(View view) {
        e();
        boolean f2 = this.f.f(view);
        if (f2) {
            ViewHolder b2 = b(view);
            this.d.c(b2);
            this.d.b(b2);
        }
        a(!f2);
        return f2;
    }

    public LayoutManager getLayoutManager() {
        return this.m;
    }

    public RecycledViewPool getRecycledViewPool() {
        return this.d.e();
    }

    public void setRecycledViewPool(RecycledViewPool recycledViewPool) {
        this.d.a(recycledViewPool);
    }

    public void setViewCacheExtension(ViewCacheExtension viewCacheExtension) {
        this.d.a(viewCacheExtension);
    }

    public void setItemViewCacheSize(int i2) {
        this.d.setViewCacheSize(i2);
    }

    public int getScrollState() {
        return this.aj;
    }

    /* access modifiers changed from: 0000 */
    public void setScrollState(int i2) {
        if (i2 != this.aj) {
            this.aj = i2;
            if (i2 != 2) {
                y();
            }
            b(i2);
        }
    }

    public void addItemDecoration(ItemDecoration itemDecoration, int i2) {
        if (this.m != null) {
            this.m.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
        }
        if (this.o.isEmpty()) {
            setWillNotDraw(false);
        }
        if (i2 < 0) {
            this.o.add(itemDecoration);
        } else {
            this.o.add(i2, itemDecoration);
        }
        p();
        requestLayout();
    }

    public void addItemDecoration(ItemDecoration itemDecoration) {
        addItemDecoration(itemDecoration, -1);
    }

    public ItemDecoration getItemDecorationAt(int i2) {
        int itemDecorationCount = getItemDecorationCount();
        if (i2 >= 0 && i2 < itemDecorationCount) {
            return (ItemDecoration) this.o.get(i2);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(i2);
        sb.append(" is an invalid index for size ");
        sb.append(itemDecorationCount);
        throw new IndexOutOfBoundsException(sb.toString());
    }

    public int getItemDecorationCount() {
        return this.o.size();
    }

    public void removeItemDecorationAt(int i2) {
        int itemDecorationCount = getItemDecorationCount();
        if (i2 < 0 || i2 >= itemDecorationCount) {
            StringBuilder sb = new StringBuilder();
            sb.append(i2);
            sb.append(" is an invalid index for size ");
            sb.append(itemDecorationCount);
            throw new IndexOutOfBoundsException(sb.toString());
        }
        removeItemDecoration(getItemDecorationAt(i2));
    }

    public void removeItemDecoration(ItemDecoration itemDecoration) {
        if (this.m != null) {
            this.m.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
        }
        this.o.remove(itemDecoration);
        if (this.o.isEmpty()) {
            setWillNotDraw(getOverScrollMode() == 2);
        }
        p();
        requestLayout();
    }

    public void setChildDrawingOrderCallback(ChildDrawingOrderCallback childDrawingOrderCallback) {
        if (childDrawingOrderCallback != this.aA) {
            this.aA = childDrawingOrderCallback;
            setChildrenDrawingOrderEnabled(this.aA != null);
        }
    }

    @Deprecated
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.ax = onScrollListener;
    }

    public void addOnScrollListener(OnScrollListener onScrollListener) {
        if (this.ay == null) {
            this.ay = new ArrayList();
        }
        this.ay.add(onScrollListener);
    }

    public void removeOnScrollListener(OnScrollListener onScrollListener) {
        if (this.ay != null) {
            this.ay.remove(onScrollListener);
        }
    }

    public void clearOnScrollListeners() {
        if (this.ay != null) {
            this.ay.clear();
        }
    }

    public void scrollToPosition(int i2) {
        if (!this.u) {
            stopScroll();
            if (this.m == null) {
                Log.e("RecyclerView", "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
                return;
            }
            this.m.scrollToPosition(i2);
            awakenScrollBars();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        if (this.m != null) {
            this.m.scrollToPosition(i2);
            awakenScrollBars();
        }
    }

    public void smoothScrollToPosition(int i2) {
        if (!this.u) {
            if (this.m == null) {
                Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            } else {
                this.m.smoothScrollToPosition(this, this.C, i2);
            }
        }
    }

    public void scrollTo(int i2, int i3) {
        Log.w("RecyclerView", "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void scrollBy(int i2, int i3) {
        if (this.m == null) {
            Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.u) {
            boolean canScrollHorizontally = this.m.canScrollHorizontally();
            boolean canScrollVertically = this.m.canScrollVertically();
            if (canScrollHorizontally || canScrollVertically) {
                if (!canScrollHorizontally) {
                    i2 = 0;
                }
                if (!canScrollVertically) {
                    i3 = 0;
                }
                a(i2, i3, (MotionEvent) null);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void d() {
        if (!this.s || this.w) {
            TraceCompat.beginSection("RV FullInvalidate");
            o();
            TraceCompat.endSection();
        } else if (this.e.d()) {
            if (this.e.a(4) && !this.e.a(11)) {
                TraceCompat.beginSection("RV PartialInvalidate");
                e();
                k();
                this.e.b();
                if (!this.t) {
                    if (x()) {
                        o();
                    } else {
                        this.e.c();
                    }
                }
                a(true);
                l();
                TraceCompat.endSection();
            } else if (this.e.d()) {
                TraceCompat.beginSection("RV FullInvalidate");
                o();
                TraceCompat.endSection();
            }
        }
    }

    private boolean x() {
        int b2 = this.f.b();
        for (int i2 = 0; i2 < b2; i2++) {
            ViewHolder b3 = b(this.f.b(i2));
            if (b3 != null && !b3.c() && b3.s()) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i2, int i3, MotionEvent motionEvent) {
        int i4;
        int i5;
        int i6;
        int i7;
        d();
        if (this.l != null) {
            e();
            k();
            TraceCompat.beginSection("RV Scroll");
            a(this.C);
            if (i2 != 0) {
                i7 = this.m.scrollHorizontallyBy(i2, this.d, this.C);
                i6 = i2 - i7;
            } else {
                i7 = 0;
                i6 = 0;
            }
            if (i3 != 0) {
                i5 = this.m.scrollVerticallyBy(i3, this.d, this.C);
                i4 = i3 - i5;
            } else {
                i5 = 0;
                i4 = 0;
            }
            TraceCompat.endSection();
            t();
            l();
            a(false);
        } else {
            i7 = 0;
            i6 = 0;
            i5 = 0;
            i4 = 0;
        }
        if (!this.o.isEmpty()) {
            invalidate();
        }
        if (dispatchNestedScroll(i7, i5, i6, i4, this.aD, 0)) {
            this.ao -= this.aD[0];
            this.ap -= this.aD[1];
            if (motionEvent != null) {
                motionEvent.offsetLocation((float) this.aD[0], (float) this.aD[1]);
            }
            int[] iArr = this.aF;
            iArr[0] = iArr[0] + this.aD[0];
            int[] iArr2 = this.aF;
            iArr2[1] = iArr2[1] + this.aD[1];
        } else if (getOverScrollMode() != 2) {
            if (motionEvent != null && !MotionEventCompat.isFromSource(motionEvent, 8194)) {
                a(motionEvent.getX(), (float) i6, motionEvent.getY(), (float) i4);
            }
            a(i2, i3);
        }
        if (!(i7 == 0 && i5 == 0)) {
            f(i7, i5);
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        if (i7 == 0 && i5 == 0) {
            return false;
        }
        return true;
    }

    public int computeHorizontalScrollOffset() {
        int i2 = 0;
        if (this.m == null) {
            return 0;
        }
        if (this.m.canScrollHorizontally()) {
            i2 = this.m.computeHorizontalScrollOffset(this.C);
        }
        return i2;
    }

    public int computeHorizontalScrollExtent() {
        int i2 = 0;
        if (this.m == null) {
            return 0;
        }
        if (this.m.canScrollHorizontally()) {
            i2 = this.m.computeHorizontalScrollExtent(this.C);
        }
        return i2;
    }

    public int computeHorizontalScrollRange() {
        int i2 = 0;
        if (this.m == null) {
            return 0;
        }
        if (this.m.canScrollHorizontally()) {
            i2 = this.m.computeHorizontalScrollRange(this.C);
        }
        return i2;
    }

    public int computeVerticalScrollOffset() {
        int i2 = 0;
        if (this.m == null) {
            return 0;
        }
        if (this.m.canScrollVertically()) {
            i2 = this.m.computeVerticalScrollOffset(this.C);
        }
        return i2;
    }

    public int computeVerticalScrollExtent() {
        int i2 = 0;
        if (this.m == null) {
            return 0;
        }
        if (this.m.canScrollVertically()) {
            i2 = this.m.computeVerticalScrollExtent(this.C);
        }
        return i2;
    }

    public int computeVerticalScrollRange() {
        int i2 = 0;
        if (this.m == null) {
            return 0;
        }
        if (this.m.canScrollVertically()) {
            i2 = this.m.computeVerticalScrollRange(this.C);
        }
        return i2;
    }

    /* access modifiers changed from: 0000 */
    public void e() {
        this.U++;
        if (this.U == 1 && !this.u) {
            this.t = false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z2) {
        if (this.U < 1) {
            this.U = 1;
        }
        if (!z2 && !this.u) {
            this.t = false;
        }
        if (this.U == 1) {
            if (z2 && this.t && !this.u && this.m != null && this.l != null) {
                o();
            }
            if (!this.u) {
                this.t = false;
            }
        }
        this.U--;
    }

    public void setLayoutFrozen(boolean z2) {
        if (z2 != this.u) {
            b("Do not setLayoutFrozen in layout or scroll");
            if (!z2) {
                this.u = false;
                if (!(!this.t || this.m == null || this.l == null)) {
                    requestLayout();
                }
                this.t = false;
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            onTouchEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0));
            this.u = true;
            this.V = true;
            stopScroll();
        }
    }

    public boolean isLayoutFrozen() {
        return this.u;
    }

    public void smoothScrollBy(int i2, int i3) {
        smoothScrollBy(i2, i3, null);
    }

    public void smoothScrollBy(int i2, int i3, Interpolator interpolator) {
        if (this.m == null) {
            Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.u) {
            if (!this.m.canScrollHorizontally()) {
                i2 = 0;
            }
            if (!this.m.canScrollVertically()) {
                i3 = 0;
            }
            if (!(i2 == 0 && i3 == 0)) {
                this.z.a(i2, i3, interpolator);
            }
        }
    }

    public boolean fling(int i2, int i3) {
        int i4 = 0;
        if (this.m == null) {
            Log.e("RecyclerView", "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return false;
        } else if (this.u) {
            return false;
        } else {
            boolean canScrollHorizontally = this.m.canScrollHorizontally();
            boolean canScrollVertically = this.m.canScrollVertically();
            if (!canScrollHorizontally || Math.abs(i2) < this.as) {
                i2 = 0;
            }
            if (!canScrollVertically || Math.abs(i3) < this.as) {
                i3 = 0;
            }
            if (i2 == 0 && i3 == 0) {
                return false;
            }
            float f2 = (float) i2;
            float f3 = (float) i3;
            if (!dispatchNestedPreFling(f2, f3)) {
                boolean z2 = canScrollHorizontally || canScrollVertically;
                dispatchNestedFling(f2, f3, z2);
                if (this.f235ar != null && this.f235ar.onFling(i2, i3)) {
                    return true;
                }
                if (z2) {
                    if (canScrollHorizontally) {
                        i4 = 1;
                    }
                    if (canScrollVertically) {
                        i4 |= 2;
                    }
                    startNestedScroll(i4, 1);
                    this.z.a(Math.max(-this.at, Math.min(i2, this.at)), Math.max(-this.at, Math.min(i3, this.at)));
                    return true;
                }
            }
            return false;
        }
    }

    public void stopScroll() {
        setScrollState(0);
        y();
    }

    private void y() {
        this.z.b();
        if (this.m != null) {
            this.m.e();
        }
    }

    public int getMinFlingVelocity() {
        return this.as;
    }

    public int getMaxFlingVelocity() {
        return this.at;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0056  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(float r7, float r8, float r9, float r10) {
        /*
            r6 = this;
            r0 = 0
            int r1 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            r2 = 1065353216(0x3f800000, float:1.0)
            r3 = 1
            if (r1 >= 0) goto L_0x0021
            r6.f()
            android.widget.EdgeEffect r1 = r6.af
            float r4 = -r8
            int r5 = r6.getWidth()
            float r5 = (float) r5
            float r4 = r4 / r5
            int r5 = r6.getHeight()
            float r5 = (float) r5
            float r9 = r9 / r5
            float r9 = r2 - r9
            android.support.v4.widget.EdgeEffectCompat.onPull(r1, r4, r9)
        L_0x001f:
            r9 = 1
            goto L_0x003c
        L_0x0021:
            int r1 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r1 <= 0) goto L_0x003b
            r6.g()
            android.widget.EdgeEffect r1 = r6.ah
            int r4 = r6.getWidth()
            float r4 = (float) r4
            float r4 = r8 / r4
            int r5 = r6.getHeight()
            float r5 = (float) r5
            float r9 = r9 / r5
            android.support.v4.widget.EdgeEffectCompat.onPull(r1, r4, r9)
            goto L_0x001f
        L_0x003b:
            r9 = 0
        L_0x003c:
            int r1 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r1 >= 0) goto L_0x0056
            r6.h()
            android.widget.EdgeEffect r9 = r6.ag
            float r1 = -r10
            int r2 = r6.getHeight()
            float r2 = (float) r2
            float r1 = r1 / r2
            int r2 = r6.getWidth()
            float r2 = (float) r2
            float r7 = r7 / r2
            android.support.v4.widget.EdgeEffectCompat.onPull(r9, r1, r7)
            goto L_0x0072
        L_0x0056:
            int r1 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r1 <= 0) goto L_0x0071
            r6.i()
            android.widget.EdgeEffect r9 = r6.ai
            int r1 = r6.getHeight()
            float r1 = (float) r1
            float r1 = r10 / r1
            int r4 = r6.getWidth()
            float r4 = (float) r4
            float r7 = r7 / r4
            float r2 = r2 - r7
            android.support.v4.widget.EdgeEffectCompat.onPull(r9, r1, r2)
            goto L_0x0072
        L_0x0071:
            r3 = r9
        L_0x0072:
            if (r3 != 0) goto L_0x007c
            int r7 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r7 != 0) goto L_0x007c
            int r7 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r7 == 0) goto L_0x007f
        L_0x007c:
            android.support.v4.view.ViewCompat.postInvalidateOnAnimation(r6)
        L_0x007f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.a(float, float, float, float):void");
    }

    private void z() {
        boolean z2;
        if (this.af != null) {
            this.af.onRelease();
            z2 = this.af.isFinished();
        } else {
            z2 = false;
        }
        if (this.ag != null) {
            this.ag.onRelease();
            z2 |= this.ag.isFinished();
        }
        if (this.ah != null) {
            this.ah.onRelease();
            z2 |= this.ah.isFinished();
        }
        if (this.ai != null) {
            this.ai.onRelease();
            z2 |= this.ai.isFinished();
        }
        if (z2) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, int i3) {
        boolean z2;
        if (this.af == null || this.af.isFinished() || i2 <= 0) {
            z2 = false;
        } else {
            this.af.onRelease();
            z2 = this.af.isFinished();
        }
        if (this.ah != null && !this.ah.isFinished() && i2 < 0) {
            this.ah.onRelease();
            z2 |= this.ah.isFinished();
        }
        if (this.ag != null && !this.ag.isFinished() && i3 > 0) {
            this.ag.onRelease();
            z2 |= this.ag.isFinished();
        }
        if (this.ai != null && !this.ai.isFinished() && i3 < 0) {
            this.ai.onRelease();
            z2 |= this.ai.isFinished();
        }
        if (z2) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(int i2, int i3) {
        if (i2 < 0) {
            f();
            this.af.onAbsorb(-i2);
        } else if (i2 > 0) {
            g();
            this.ah.onAbsorb(i2);
        }
        if (i3 < 0) {
            h();
            this.ag.onAbsorb(-i3);
        } else if (i3 > 0) {
            i();
            this.ai.onAbsorb(i3);
        }
        if (i2 != 0 || i3 != 0) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: 0000 */
    public void f() {
        if (this.af == null) {
            this.af = this.ae.createEdgeEffect(this, 0);
            if (this.h) {
                this.af.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            } else {
                this.af.setSize(getMeasuredHeight(), getMeasuredWidth());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void g() {
        if (this.ah == null) {
            this.ah = this.ae.createEdgeEffect(this, 2);
            if (this.h) {
                this.ah.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            } else {
                this.ah.setSize(getMeasuredHeight(), getMeasuredWidth());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void h() {
        if (this.ag == null) {
            this.ag = this.ae.createEdgeEffect(this, 1);
            if (this.h) {
                this.ag.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            } else {
                this.ag.setSize(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void i() {
        if (this.ai == null) {
            this.ai = this.ae.createEdgeEffect(this, 3);
            if (this.h) {
                this.ai.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            } else {
                this.ai.setSize(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void j() {
        this.ai = null;
        this.ag = null;
        this.ah = null;
        this.af = null;
    }

    public void setEdgeEffectFactory(@NonNull EdgeEffectFactory edgeEffectFactory) {
        Preconditions.checkNotNull(edgeEffectFactory);
        this.ae = edgeEffectFactory;
        j();
    }

    public EdgeEffectFactory getEdgeEffectFactory() {
        return this.ae;
    }

    public View focusSearch(View view, int i2) {
        View view2;
        boolean z2;
        View onInterceptFocusSearch = this.m.onInterceptFocusSearch(view, i2);
        if (onInterceptFocusSearch != null) {
            return onInterceptFocusSearch;
        }
        boolean z3 = this.l != null && this.m != null && !isComputingLayout() && !this.u;
        FocusFinder instance = FocusFinder.getInstance();
        if (!z3 || !(i2 == 2 || i2 == 1)) {
            View findNextFocus = instance.findNextFocus(this, view, i2);
            if (findNextFocus != null || !z3) {
                view2 = findNextFocus;
            } else {
                d();
                if (findContainingItemView(view) == null) {
                    return null;
                }
                e();
                view2 = this.m.onFocusSearchFailed(view, i2, this.d, this.C);
                a(false);
            }
        } else {
            if (this.m.canScrollVertically()) {
                int i3 = i2 == 2 ? 130 : 33;
                z2 = instance.findNextFocus(this, view, i3) == null;
                if (M) {
                    i2 = i3;
                }
            } else {
                z2 = false;
            }
            if (!z2 && this.m.canScrollHorizontally()) {
                int i4 = (this.m.getLayoutDirection() == 1) ^ (i2 == 2) ? 66 : 17;
                z2 = instance.findNextFocus(this, view, i4) == null;
                if (M) {
                    i2 = i4;
                }
            }
            if (z2) {
                d();
                if (findContainingItemView(view) == null) {
                    return null;
                }
                e();
                this.m.onFocusSearchFailed(view, i2, this.d, this.C);
                a(false);
            }
            view2 = instance.findNextFocus(this, view, i2);
        }
        if (view2 == null || view2.hasFocusable()) {
            if (!a(view, view2, i2)) {
                view2 = super.focusSearch(view, i2);
            }
            return view2;
        } else if (getFocusedChild() == null) {
            return super.focusSearch(view, i2);
        } else {
            a(view2, (View) null);
            return view;
        }
    }

    private boolean a(View view, View view2, int i2) {
        boolean z2 = false;
        if (view2 == null || view2 == this || findContainingItemView(view2) == null) {
            return false;
        }
        if (view == null || findContainingItemView(view) == null) {
            return true;
        }
        this.j.set(0, 0, view.getWidth(), view.getHeight());
        this.R.set(0, 0, view2.getWidth(), view2.getHeight());
        offsetDescendantRectToMyCoords(view, this.j);
        offsetDescendantRectToMyCoords(view2, this.R);
        char c2 = 65535;
        int i3 = this.m.getLayoutDirection() == 1 ? -1 : 1;
        int i4 = ((this.j.left < this.R.left || this.j.right <= this.R.left) && this.j.right < this.R.right) ? 1 : ((this.j.right > this.R.right || this.j.left >= this.R.right) && this.j.left > this.R.left) ? -1 : 0;
        if ((this.j.top < this.R.top || this.j.bottom <= this.R.top) && this.j.bottom < this.R.bottom) {
            c2 = 1;
        } else if ((this.j.bottom <= this.R.bottom && this.j.top < this.R.bottom) || this.j.top <= this.R.top) {
            c2 = 0;
        }
        if (i2 == 17) {
            if (i4 < 0) {
                z2 = true;
            }
            return z2;
        } else if (i2 == 33) {
            if (c2 < 0) {
                z2 = true;
            }
            return z2;
        } else if (i2 == 66) {
            if (i4 > 0) {
                z2 = true;
            }
            return z2;
        } else if (i2 != 130) {
            switch (i2) {
                case 1:
                    if (c2 < 0 || (c2 == 0 && i4 * i3 <= 0)) {
                        z2 = true;
                    }
                    return z2;
                case 2:
                    if (c2 > 0 || (c2 == 0 && i4 * i3 >= 0)) {
                        z2 = true;
                    }
                    return z2;
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("Invalid direction: ");
                    sb.append(i2);
                    sb.append(a());
                    throw new IllegalArgumentException(sb.toString());
            }
        } else {
            if (c2 > 0) {
                z2 = true;
            }
            return z2;
        }
    }

    public void requestChildFocus(View view, View view2) {
        if (!this.m.onRequestChildFocus(this, this.C, view, view2) && view2 != null) {
            a(view, view2);
        }
        super.requestChildFocus(view, view2);
    }

    private void a(@NonNull View view, @Nullable View view2) {
        View view3 = view2 != null ? view2 : view;
        this.j.set(0, 0, view3.getWidth(), view3.getHeight());
        android.view.ViewGroup.LayoutParams layoutParams = view3.getLayoutParams();
        if (layoutParams instanceof LayoutParams) {
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            if (!layoutParams2.e) {
                Rect rect = layoutParams2.d;
                this.j.left -= rect.left;
                this.j.right += rect.right;
                this.j.top -= rect.top;
                this.j.bottom += rect.bottom;
            }
        }
        if (view2 != null) {
            offsetDescendantRectToMyCoords(view2, this.j);
            offsetRectIntoDescendantCoords(view, this.j);
        }
        this.m.requestChildRectangleOnScreen(this, view, this.j, !this.s, view2 == null);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z2) {
        return this.m.requestChildRectangleOnScreen(this, view, rect, z2);
    }

    public void addFocusables(ArrayList<View> arrayList, int i2, int i3) {
        if (this.m == null || !this.m.onAddFocusables(this, arrayList, i2, i3)) {
            super.addFocusables(arrayList, i2, i3);
        }
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int i2, Rect rect) {
        if (isComputingLayout()) {
            return false;
        }
        return super.onRequestFocusInDescendants(i2, rect);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0051, code lost:
        if (r0 >= 30.0f) goto L_0x0056;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onAttachedToWindow() {
        /*
            r4 = this;
            super.onAttachedToWindow()
            r0 = 0
            r4.ac = r0
            r1 = 1
            r4.p = r1
            boolean r2 = r4.s
            if (r2 == 0) goto L_0x0014
            boolean r2 = r4.isLayoutRequested()
            if (r2 != 0) goto L_0x0014
            goto L_0x0015
        L_0x0014:
            r1 = 0
        L_0x0015:
            r4.s = r1
            android.support.v7.widget.RecyclerView$LayoutManager r1 = r4.m
            if (r1 == 0) goto L_0x0020
            android.support.v7.widget.RecyclerView$LayoutManager r1 = r4.m
            r1.b(r4)
        L_0x0020:
            r4.F = r0
            boolean r0 = L
            if (r0 == 0) goto L_0x006b
            java.lang.ThreadLocal<android.support.v7.widget.GapWorker> r0 = android.support.v7.widget.GapWorker.a
            java.lang.Object r0 = r0.get()
            android.support.v7.widget.GapWorker r0 = (android.support.v7.widget.GapWorker) r0
            r4.A = r0
            android.support.v7.widget.GapWorker r0 = r4.A
            if (r0 != 0) goto L_0x0066
            android.support.v7.widget.GapWorker r0 = new android.support.v7.widget.GapWorker
            r0.<init>()
            r4.A = r0
            android.view.Display r0 = android.support.v4.view.ViewCompat.getDisplay(r4)
            r1 = 1114636288(0x42700000, float:60.0)
            boolean r2 = r4.isInEditMode()
            if (r2 != 0) goto L_0x0054
            if (r0 == 0) goto L_0x0054
            float r0 = r0.getRefreshRate()
            r2 = 1106247680(0x41f00000, float:30.0)
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 < 0) goto L_0x0054
            goto L_0x0056
        L_0x0054:
            r0 = 1114636288(0x42700000, float:60.0)
        L_0x0056:
            android.support.v7.widget.GapWorker r1 = r4.A
            r2 = 1315859240(0x4e6e6b28, float:1.0E9)
            float r2 = r2 / r0
            long r2 = (long) r2
            r1.d = r2
            java.lang.ThreadLocal<android.support.v7.widget.GapWorker> r0 = android.support.v7.widget.GapWorker.a
            android.support.v7.widget.GapWorker r1 = r4.A
            r0.set(r1)
        L_0x0066:
            android.support.v7.widget.GapWorker r0 = r4.A
            r0.a(r4)
        L_0x006b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.onAttachedToWindow():void");
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.y != null) {
            this.y.endAnimations();
        }
        stopScroll();
        this.p = false;
        if (this.m != null) {
            this.m.a(this, this.d);
        }
        this.H.clear();
        removeCallbacks(this.aG);
        this.g.b();
        if (L && this.A != null) {
            this.A.b(this);
            this.A = null;
        }
    }

    public boolean isAttachedToWindow() {
        return this.p;
    }

    /* access modifiers changed from: 0000 */
    public void a(String str) {
        if (isComputingLayout()) {
            return;
        }
        if (str == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot call this method unless RecyclerView is computing a layout or scrolling");
            sb.append(a());
            throw new IllegalStateException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(a());
        throw new IllegalStateException(sb2.toString());
    }

    /* access modifiers changed from: 0000 */
    public void b(String str) {
        if (isComputingLayout()) {
            if (str == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Cannot call this method while RecyclerView is computing a layout or scrolling");
                sb.append(a());
                throw new IllegalStateException(sb.toString());
            }
            throw new IllegalStateException(str);
        } else if (this.ad > 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(a());
            Log.w("RecyclerView", "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new IllegalStateException(sb2.toString()));
        }
    }

    public void addOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.S.add(onItemTouchListener);
    }

    public void removeOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.S.remove(onItemTouchListener);
        if (this.T == onItemTouchListener) {
            this.T = null;
        }
    }

    private boolean a(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 3 || action == 0) {
            this.T = null;
        }
        int size = this.S.size();
        int i2 = 0;
        while (i2 < size) {
            OnItemTouchListener onItemTouchListener = (OnItemTouchListener) this.S.get(i2);
            if (!onItemTouchListener.onInterceptTouchEvent(this, motionEvent) || action == 3) {
                i2++;
            } else {
                this.T = onItemTouchListener;
                return true;
            }
        }
        return false;
    }

    private boolean b(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (this.T != null) {
            if (action == 0) {
                this.T = null;
            } else {
                this.T.onTouchEvent(this, motionEvent);
                if (action == 3 || action == 1) {
                    this.T = null;
                }
                return true;
            }
        }
        if (action != 0) {
            int size = this.S.size();
            for (int i2 = 0; i2 < size; i2++) {
                OnItemTouchListener onItemTouchListener = (OnItemTouchListener) this.S.get(i2);
                if (onItemTouchListener.onInterceptTouchEvent(this, motionEvent)) {
                    this.T = onItemTouchListener;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z2;
        boolean z3 = false;
        if (this.u) {
            return false;
        }
        if (a(motionEvent)) {
            B();
            return true;
        } else if (this.m == null) {
            return false;
        } else {
            boolean canScrollHorizontally = this.m.canScrollHorizontally();
            boolean canScrollVertically = this.m.canScrollVertically();
            if (this.al == null) {
                this.al = VelocityTracker.obtain();
            }
            this.al.addMovement(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            int actionIndex = motionEvent.getActionIndex();
            switch (actionMasked) {
                case 0:
                    if (this.V) {
                        this.V = false;
                    }
                    this.ak = motionEvent.getPointerId(0);
                    int x2 = (int) (motionEvent.getX() + 0.5f);
                    this.ao = x2;
                    this.am = x2;
                    int y2 = (int) (motionEvent.getY() + 0.5f);
                    this.ap = y2;
                    this.an = y2;
                    if (this.aj == 2) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        setScrollState(1);
                    }
                    int[] iArr = this.aF;
                    this.aF[1] = 0;
                    iArr[0] = 0;
                    int i2 = canScrollHorizontally ? 1 : 0;
                    if (canScrollVertically) {
                        i2 |= 2;
                    }
                    startNestedScroll(i2, 0);
                    break;
                case 1:
                    this.al.clear();
                    stopNestedScroll(0);
                    break;
                case 2:
                    int findPointerIndex = motionEvent.findPointerIndex(this.ak);
                    if (findPointerIndex >= 0) {
                        int x3 = (int) (motionEvent.getX(findPointerIndex) + 0.5f);
                        int y3 = (int) (motionEvent.getY(findPointerIndex) + 0.5f);
                        if (this.aj != 1) {
                            int i3 = x3 - this.am;
                            int i4 = y3 - this.an;
                            if (!canScrollHorizontally || Math.abs(i3) <= this.aq) {
                                z2 = false;
                            } else {
                                this.ao = x3;
                                z2 = true;
                            }
                            if (canScrollVertically && Math.abs(i4) > this.aq) {
                                this.ap = y3;
                                z2 = true;
                            }
                            if (z2) {
                                setScrollState(1);
                                break;
                            }
                        }
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Error processing scroll; pointer index for id ");
                        sb.append(this.ak);
                        sb.append(" not found. Did any MotionEvents get skipped?");
                        Log.e("RecyclerView", sb.toString());
                        return false;
                    }
                    break;
                case 3:
                    B();
                    break;
                case 5:
                    this.ak = motionEvent.getPointerId(actionIndex);
                    int x4 = (int) (motionEvent.getX(actionIndex) + 0.5f);
                    this.ao = x4;
                    this.am = x4;
                    int y4 = (int) (motionEvent.getY(actionIndex) + 0.5f);
                    this.ap = y4;
                    this.an = y4;
                    break;
                case 6:
                    c(motionEvent);
                    break;
            }
            if (this.aj == 1) {
                z3 = true;
            }
            return z3;
        }
    }

    public void requestDisallowInterceptTouchEvent(boolean z2) {
        int size = this.S.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((OnItemTouchListener) this.S.get(i2)).onRequestDisallowInterceptTouchEvent(z2);
        }
        super.requestDisallowInterceptTouchEvent(z2);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i2;
        int i3;
        boolean z2;
        boolean z3 = false;
        if (this.u || this.V) {
            return false;
        }
        if (b(motionEvent)) {
            B();
            return true;
        } else if (this.m == null) {
            return false;
        } else {
            boolean canScrollHorizontally = this.m.canScrollHorizontally();
            boolean canScrollVertically = this.m.canScrollVertically();
            if (this.al == null) {
                this.al = VelocityTracker.obtain();
            }
            MotionEvent obtain = MotionEvent.obtain(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            int actionIndex = motionEvent.getActionIndex();
            if (actionMasked == 0) {
                int[] iArr = this.aF;
                this.aF[1] = 0;
                iArr[0] = 0;
            }
            obtain.offsetLocation((float) this.aF[0], (float) this.aF[1]);
            switch (actionMasked) {
                case 0:
                    this.ak = motionEvent.getPointerId(0);
                    int x2 = (int) (motionEvent.getX() + 0.5f);
                    this.ao = x2;
                    this.am = x2;
                    int y2 = (int) (motionEvent.getY() + 0.5f);
                    this.ap = y2;
                    this.an = y2;
                    int i4 = canScrollHorizontally ? 1 : 0;
                    if (canScrollVertically) {
                        i4 |= 2;
                    }
                    startNestedScroll(i4, 0);
                    break;
                case 1:
                    this.al.addMovement(obtain);
                    this.al.computeCurrentVelocity(1000, (float) this.at);
                    float f2 = canScrollHorizontally ? -this.al.getXVelocity(this.ak) : BitmapDescriptorFactory.HUE_RED;
                    float f3 = canScrollVertically ? -this.al.getYVelocity(this.ak) : BitmapDescriptorFactory.HUE_RED;
                    if ((f2 == BitmapDescriptorFactory.HUE_RED && f3 == BitmapDescriptorFactory.HUE_RED) || !fling((int) f2, (int) f3)) {
                        setScrollState(0);
                    }
                    A();
                    z3 = true;
                    break;
                case 2:
                    int findPointerIndex = motionEvent.findPointerIndex(this.ak);
                    if (findPointerIndex >= 0) {
                        int x3 = (int) (motionEvent.getX(findPointerIndex) + 0.5f);
                        int y3 = (int) (motionEvent.getY(findPointerIndex) + 0.5f);
                        int i5 = this.ao - x3;
                        int i6 = this.ap - y3;
                        if (dispatchNestedPreScroll(i5, i6, this.aE, this.aD, 0)) {
                            i5 -= this.aE[0];
                            i6 -= this.aE[1];
                            obtain.offsetLocation((float) this.aD[0], (float) this.aD[1]);
                            int[] iArr2 = this.aF;
                            iArr2[0] = iArr2[0] + this.aD[0];
                            int[] iArr3 = this.aF;
                            iArr3[1] = iArr3[1] + this.aD[1];
                        }
                        if (this.aj != 1) {
                            if (!canScrollHorizontally || Math.abs(i3) <= this.aq) {
                                z2 = false;
                            } else {
                                if (i3 > 0) {
                                    i3 -= this.aq;
                                } else {
                                    i3 += this.aq;
                                }
                                z2 = true;
                            }
                            if (canScrollVertically && Math.abs(i2) > this.aq) {
                                if (i2 > 0) {
                                    i2 -= this.aq;
                                } else {
                                    i2 += this.aq;
                                }
                                z2 = true;
                            }
                            if (z2) {
                                setScrollState(1);
                            }
                        }
                        if (this.aj == 1) {
                            this.ao = x3 - this.aD[0];
                            this.ap = y3 - this.aD[1];
                            if (a(canScrollHorizontally ? i3 : 0, canScrollVertically ? i2 : 0, obtain)) {
                                getParent().requestDisallowInterceptTouchEvent(true);
                            }
                            if (!(this.A == null || (i3 == 0 && i2 == 0))) {
                                this.A.a(this, i3, i2);
                                break;
                            }
                        }
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Error processing scroll; pointer index for id ");
                        sb.append(this.ak);
                        sb.append(" not found. Did any MotionEvents get skipped?");
                        Log.e("RecyclerView", sb.toString());
                        return false;
                    }
                    break;
                case 3:
                    B();
                    break;
                case 5:
                    this.ak = motionEvent.getPointerId(actionIndex);
                    int x4 = (int) (motionEvent.getX(actionIndex) + 0.5f);
                    this.ao = x4;
                    this.am = x4;
                    int y4 = (int) (motionEvent.getY(actionIndex) + 0.5f);
                    this.ap = y4;
                    this.an = y4;
                    break;
                case 6:
                    c(motionEvent);
                    break;
            }
            if (!z3) {
                this.al.addMovement(obtain);
            }
            obtain.recycle();
            return true;
        }
    }

    private void A() {
        if (this.al != null) {
            this.al.clear();
        }
        stopNestedScroll(0);
        z();
    }

    private void B() {
        A();
        setScrollState(0);
    }

    private void c(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.ak) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.ak = motionEvent.getPointerId(i2);
            int x2 = (int) (motionEvent.getX(i2) + 0.5f);
            this.ao = x2;
            this.am = x2;
            int y2 = (int) (motionEvent.getY(i2) + 0.5f);
            this.ap = y2;
            this.an = y2;
        }
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        float f2;
        float f3;
        if (this.m != null && !this.u && motionEvent.getAction() == 8) {
            if ((motionEvent.getSource() & 2) != 0) {
                f3 = this.m.canScrollVertically() ? -motionEvent.getAxisValue(9) : BitmapDescriptorFactory.HUE_RED;
                if (this.m.canScrollHorizontally()) {
                    f2 = motionEvent.getAxisValue(10);
                    if (!(f3 == BitmapDescriptorFactory.HUE_RED && f2 == BitmapDescriptorFactory.HUE_RED)) {
                        a((int) (f2 * this.au), (int) (f3 * this.av), motionEvent);
                    }
                }
            } else {
                if ((motionEvent.getSource() & 4194304) != 0) {
                    float axisValue = motionEvent.getAxisValue(26);
                    if (this.m.canScrollVertically()) {
                        f3 = -axisValue;
                    } else if (this.m.canScrollHorizontally()) {
                        f2 = axisValue;
                        f3 = BitmapDescriptorFactory.HUE_RED;
                        a((int) (f2 * this.au), (int) (f3 * this.av), motionEvent);
                    }
                }
                f3 = BitmapDescriptorFactory.HUE_RED;
            }
            f2 = BitmapDescriptorFactory.HUE_RED;
            a((int) (f2 * this.au), (int) (f3 * this.av), motionEvent);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        if (this.m == null) {
            c(i2, i3);
            return;
        }
        boolean z2 = false;
        if (this.m.isAutoMeasureEnabled()) {
            int mode = MeasureSpec.getMode(i2);
            int mode2 = MeasureSpec.getMode(i3);
            this.m.onMeasure(this.d, this.C, i2, i3);
            if (mode == 1073741824 && mode2 == 1073741824) {
                z2 = true;
            }
            if (!z2 && this.l != null) {
                if (this.C.c == 1) {
                    J();
                }
                this.m.c(i2, i3);
                this.C.h = true;
                K();
                this.m.d(i2, i3);
                if (this.m.d()) {
                    this.m.c(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
                    this.C.h = true;
                    K();
                    this.m.d(i2, i3);
                }
            }
        } else if (this.q) {
            this.m.onMeasure(this.d, this.C, i2, i3);
        } else {
            if (this.v) {
                e();
                k();
                E();
                l();
                if (this.C.j) {
                    this.C.f = true;
                } else {
                    this.e.e();
                    this.C.f = false;
                }
                this.v = false;
                a(false);
            } else if (this.C.j) {
                setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
                return;
            }
            if (this.l != null) {
                this.C.d = this.l.getItemCount();
            } else {
                this.C.d = 0;
            }
            e();
            this.m.onMeasure(this.d, this.C, i2, i3);
            a(false);
            this.C.f = false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void c(int i2, int i3) {
        setMeasuredDimension(LayoutManager.chooseSize(i2, getPaddingLeft() + getPaddingRight(), ViewCompat.getMinimumWidth(this)), LayoutManager.chooseSize(i3, getPaddingTop() + getPaddingBottom(), ViewCompat.getMinimumHeight(this)));
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 != i4 || i3 != i5) {
            j();
        }
    }

    public void setItemAnimator(ItemAnimator itemAnimator) {
        if (this.y != null) {
            this.y.endAnimations();
            this.y.a(null);
        }
        this.y = itemAnimator;
        if (this.y != null) {
            this.y.a(this.az);
        }
    }

    /* access modifiers changed from: 0000 */
    public void k() {
        this.ac++;
    }

    /* access modifiers changed from: 0000 */
    public void l() {
        b(true);
    }

    /* access modifiers changed from: 0000 */
    public void b(boolean z2) {
        this.ac--;
        if (this.ac < 1) {
            this.ac = 0;
            if (z2) {
                C();
                u();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean m() {
        return this.aa != null && this.aa.isEnabled();
    }

    private void C() {
        int i2 = this.W;
        this.W = 0;
        if (i2 != 0 && m()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            AccessibilityEventCompat.setContentChangeTypes(obtain, i2);
            sendAccessibilityEventUnchecked(obtain);
        }
    }

    public boolean isComputingLayout() {
        return this.ac > 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(AccessibilityEvent accessibilityEvent) {
        if (!isComputingLayout()) {
            return false;
        }
        int contentChangeTypes = accessibilityEvent != null ? AccessibilityEventCompat.getContentChangeTypes(accessibilityEvent) : 0;
        if (contentChangeTypes == 0) {
            contentChangeTypes = 0;
        }
        this.W = contentChangeTypes | this.W;
        return true;
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (!a(accessibilityEvent)) {
            super.sendAccessibilityEventUnchecked(accessibilityEvent);
        }
    }

    public ItemAnimator getItemAnimator() {
        return this.y;
    }

    /* access modifiers changed from: 0000 */
    public void n() {
        if (!this.F && this.p) {
            ViewCompat.postOnAnimation(this, this.aG);
            this.F = true;
        }
    }

    private boolean D() {
        return this.y != null && this.m.supportsPredictiveItemAnimations();
    }

    private void E() {
        if (this.w) {
            this.e.a();
            if (this.x) {
                this.m.onItemsChanged(this);
            }
        }
        if (D()) {
            this.e.b();
        } else {
            this.e.e();
        }
        boolean z2 = true;
        boolean z3 = this.D || this.E;
        this.C.i = this.s && this.y != null && (this.w || z3 || this.m.u) && (!this.w || this.l.hasStableIds());
        State state = this.C;
        if (!this.C.i || !z3 || this.w || !D()) {
            z2 = false;
        }
        state.j = z2;
    }

    /* access modifiers changed from: 0000 */
    public void o() {
        if (this.l == null) {
            Log.e("RecyclerView", "No adapter attached; skipping layout");
        } else if (this.m == null) {
            Log.e("RecyclerView", "No layout manager attached; skipping layout");
        } else {
            this.C.h = false;
            if (this.C.c == 1) {
                J();
                this.m.c(this);
                K();
            } else if (!this.e.f() && this.m.getWidth() == getWidth() && this.m.getHeight() == getHeight()) {
                this.m.c(this);
            } else {
                this.m.c(this);
                K();
            }
            L();
        }
    }

    private void F() {
        int i2;
        ViewHolder viewHolder = null;
        View focusedChild = (!this.aw || !hasFocus() || this.l == null) ? null : getFocusedChild();
        if (focusedChild != null) {
            viewHolder = findContainingViewHolder(focusedChild);
        }
        if (viewHolder == null) {
            G();
            return;
        }
        this.C.l = this.l.hasStableIds() ? viewHolder.getItemId() : -1;
        State state = this.C;
        if (this.w) {
            i2 = -1;
        } else if (viewHolder.m()) {
            i2 = viewHolder.c;
        } else {
            i2 = viewHolder.getAdapterPosition();
        }
        state.k = i2;
        this.C.m = g(viewHolder.itemView);
    }

    private void G() {
        this.C.l = -1;
        this.C.k = -1;
        this.C.m = -1;
    }

    @Nullable
    private View H() {
        int i2 = this.C.k != -1 ? this.C.k : 0;
        int itemCount = this.C.getItemCount();
        int i3 = i2;
        while (i3 < itemCount) {
            ViewHolder findViewHolderForAdapterPosition = findViewHolderForAdapterPosition(i3);
            if (findViewHolderForAdapterPosition == null) {
                break;
            } else if (findViewHolderForAdapterPosition.itemView.hasFocusable()) {
                return findViewHolderForAdapterPosition.itemView;
            } else {
                i3++;
            }
        }
        int min = Math.min(itemCount, i2);
        while (true) {
            min--;
            if (min < 0) {
                return null;
            }
            ViewHolder findViewHolderForAdapterPosition2 = findViewHolderForAdapterPosition(min);
            if (findViewHolderForAdapterPosition2 == null) {
                return null;
            }
            if (findViewHolderForAdapterPosition2.itemView.hasFocusable()) {
                return findViewHolderForAdapterPosition2.itemView;
            }
        }
    }

    private void I() {
        if (this.aw && this.l != null && hasFocus() && getDescendantFocusability() != 393216 && (getDescendantFocusability() != 131072 || !isFocused())) {
            if (!isFocused()) {
                View focusedChild = getFocusedChild();
                if (!N || (focusedChild.getParent() != null && focusedChild.hasFocus())) {
                    if (!this.f.c(focusedChild)) {
                        return;
                    }
                } else if (this.f.b() == 0) {
                    requestFocus();
                    return;
                }
            }
            View view = null;
            ViewHolder findViewHolderForItemId = (this.C.l == -1 || !this.l.hasStableIds()) ? null : findViewHolderForItemId(this.C.l);
            if (findViewHolderForItemId != null && !this.f.c(findViewHolderForItemId.itemView) && findViewHolderForItemId.itemView.hasFocusable()) {
                view = findViewHolderForItemId.itemView;
            } else if (this.f.b() > 0) {
                view = H();
            }
            if (view != null) {
                if (((long) this.C.m) != -1) {
                    View findViewById = view.findViewById(this.C.m);
                    if (findViewById != null && findViewById.isFocusable()) {
                        view = findViewById;
                    }
                }
                view.requestFocus();
            }
        }
    }

    private int g(View view) {
        int id2 = view.getId();
        while (!view.isFocused() && (view instanceof ViewGroup) && view.hasFocus()) {
            view = ((ViewGroup) view).getFocusedChild();
            if (view.getId() != -1) {
                id2 = view.getId();
            }
        }
        return id2;
    }

    /* access modifiers changed from: 0000 */
    public final void a(State state) {
        if (getScrollState() == 2) {
            OverScroller a2 = this.z.e;
            state.n = a2.getFinalX() - a2.getCurrX();
            state.o = a2.getFinalY() - a2.getCurrY();
            return;
        }
        state.n = 0;
        state.o = 0;
    }

    private void J() {
        boolean z2 = true;
        this.C.a(1);
        a(this.C);
        this.C.h = false;
        e();
        this.g.a();
        k();
        E();
        F();
        State state = this.C;
        if (!this.C.i || !this.E) {
            z2 = false;
        }
        state.g = z2;
        this.E = false;
        this.D = false;
        this.C.f = this.C.j;
        this.C.d = this.l.getItemCount();
        a(this.aB);
        if (this.C.i) {
            int b2 = this.f.b();
            for (int i2 = 0; i2 < b2; i2++) {
                ViewHolder b3 = b(this.f.b(i2));
                if (!b3.c() && (!b3.j() || this.l.hasStableIds())) {
                    this.g.a(b3, this.y.recordPreLayoutInformation(this.C, b3, ItemAnimator.b(b3), b3.q()));
                    if (this.C.g && b3.s() && !b3.m() && !b3.c() && !b3.j()) {
                        this.g.a(a(b3), b3);
                    }
                }
            }
        }
        if (this.C.j) {
            q();
            boolean z3 = this.C.e;
            this.C.e = false;
            this.m.onLayoutChildren(this.d, this.C);
            this.C.e = z3;
            for (int i3 = 0; i3 < this.f.b(); i3++) {
                ViewHolder b4 = b(this.f.b(i3));
                if (!b4.c() && !this.g.d(b4)) {
                    int b5 = ItemAnimator.b(b4);
                    boolean a2 = b4.a(8192);
                    if (!a2) {
                        b5 |= 4096;
                    }
                    ItemHolderInfo recordPreLayoutInformation = this.y.recordPreLayoutInformation(this.C, b4, b5, b4.q());
                    if (a2) {
                        a(b4, recordPreLayoutInformation);
                    } else {
                        this.g.b(b4, recordPreLayoutInformation);
                    }
                }
            }
            r();
        } else {
            r();
        }
        l();
        a(false);
        this.C.c = 2;
    }

    private void K() {
        e();
        k();
        this.C.a(6);
        this.e.e();
        this.C.d = this.l.getItemCount();
        this.C.b = 0;
        this.C.f = false;
        this.m.onLayoutChildren(this.d, this.C);
        this.C.e = false;
        this.Q = null;
        this.C.i = this.C.i && this.y != null;
        this.C.c = 4;
        l();
        a(false);
    }

    private void L() {
        this.C.a(4);
        e();
        k();
        this.C.c = 1;
        if (this.C.i) {
            for (int b2 = this.f.b() - 1; b2 >= 0; b2--) {
                ViewHolder b3 = b(this.f.b(b2));
                if (!b3.c()) {
                    long a2 = a(b3);
                    ItemHolderInfo recordPostLayoutInformation = this.y.recordPostLayoutInformation(this.C, b3);
                    ViewHolder a3 = this.g.a(a2);
                    if (a3 == null || a3.c()) {
                        this.g.c(b3, recordPostLayoutInformation);
                    } else {
                        boolean a4 = this.g.a(a3);
                        boolean a5 = this.g.a(b3);
                        if (!a4 || a3 != b3) {
                            ItemHolderInfo b4 = this.g.b(a3);
                            this.g.c(b3, recordPostLayoutInformation);
                            ItemHolderInfo c2 = this.g.c(b3);
                            if (b4 == null) {
                                a(a2, b3, a3);
                            } else {
                                a(a3, b3, b4, c2, a4, a5);
                            }
                        } else {
                            this.g.c(b3, recordPostLayoutInformation);
                        }
                    }
                }
            }
            this.g.a(this.aH);
        }
        this.m.a(this.d);
        this.C.a = this.C.d;
        this.w = false;
        this.x = false;
        this.C.i = false;
        this.C.j = false;
        this.m.u = false;
        if (this.d.b != null) {
            this.d.b.clear();
        }
        if (this.m.y) {
            this.m.x = 0;
            this.m.y = false;
            this.d.a();
        }
        this.m.onLayoutCompleted(this.C);
        l();
        a(false);
        this.g.a();
        if (g(this.aB[0], this.aB[1])) {
            f(0, 0);
        }
        I();
        G();
    }

    private void a(long j2, ViewHolder viewHolder, ViewHolder viewHolder2) {
        int b2 = this.f.b();
        int i2 = 0;
        while (i2 < b2) {
            ViewHolder b3 = b(this.f.b(i2));
            if (b3 == viewHolder || a(b3) != j2) {
                i2++;
            } else if (this.l == null || !this.l.hasStableIds()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:");
                sb.append(b3);
                sb.append(" \n View Holder 2:");
                sb.append(viewHolder);
                sb.append(a());
                throw new IllegalStateException(sb.toString());
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:");
                sb2.append(b3);
                sb2.append(" \n View Holder 2:");
                sb2.append(viewHolder);
                sb2.append(a());
                throw new IllegalStateException(sb2.toString());
            }
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Problem while matching changed view holders with the newones. The pre-layout information for the change holder ");
        sb3.append(viewHolder2);
        sb3.append(" cannot be found but it is necessary for ");
        sb3.append(viewHolder);
        sb3.append(a());
        Log.e("RecyclerView", sb3.toString());
    }

    /* access modifiers changed from: 0000 */
    public void a(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo) {
        viewHolder.a(0, 8192);
        if (this.C.g && viewHolder.s() && !viewHolder.m() && !viewHolder.c()) {
            this.g.a(a(viewHolder), viewHolder);
        }
        this.g.a(viewHolder, itemHolderInfo);
    }

    private void a(int[] iArr) {
        int b2 = this.f.b();
        if (b2 == 0) {
            iArr[0] = -1;
            iArr[1] = -1;
            return;
        }
        int i2 = SubsamplingScaleImageView.TILE_SIZE_AUTO;
        int i3 = Integer.MIN_VALUE;
        for (int i4 = 0; i4 < b2; i4++) {
            ViewHolder b3 = b(this.f.b(i4));
            if (!b3.c()) {
                int layoutPosition = b3.getLayoutPosition();
                if (layoutPosition < i2) {
                    i2 = layoutPosition;
                }
                if (layoutPosition > i3) {
                    i3 = layoutPosition;
                }
            }
        }
        iArr[0] = i2;
        iArr[1] = i3;
    }

    private boolean g(int i2, int i3) {
        a(this.aB);
        return (this.aB[0] == i2 && this.aB[1] == i3) ? false : true;
    }

    /* access modifiers changed from: protected */
    public void removeDetachedView(View view, boolean z2) {
        ViewHolder b2 = b(view);
        if (b2 != null) {
            if (b2.n()) {
                b2.h();
            } else if (!b2.c()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Called removeDetachedView with a view which is not flagged as tmp detached.");
                sb.append(b2);
                sb.append(a());
                throw new IllegalArgumentException(sb.toString());
            }
        }
        view.clearAnimation();
        e(view);
        super.removeDetachedView(view, z2);
    }

    /* access modifiers changed from: 0000 */
    public long a(ViewHolder viewHolder) {
        return this.l.hasStableIds() ? viewHolder.getItemId() : (long) viewHolder.b;
    }

    /* access modifiers changed from: 0000 */
    public void a(@NonNull ViewHolder viewHolder, @Nullable ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2) {
        viewHolder.setIsRecyclable(false);
        if (this.y.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2)) {
            n();
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2) {
        e(viewHolder);
        viewHolder.setIsRecyclable(false);
        if (this.y.animateDisappearance(viewHolder, itemHolderInfo, itemHolderInfo2)) {
            n();
        }
    }

    private void a(@NonNull ViewHolder viewHolder, @NonNull ViewHolder viewHolder2, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2, boolean z2, boolean z3) {
        viewHolder.setIsRecyclable(false);
        if (z2) {
            e(viewHolder);
        }
        if (viewHolder != viewHolder2) {
            if (z3) {
                e(viewHolder2);
            }
            viewHolder.g = viewHolder2;
            e(viewHolder);
            this.d.c(viewHolder);
            viewHolder2.setIsRecyclable(false);
            viewHolder2.h = viewHolder;
        }
        if (this.y.animateChange(viewHolder, viewHolder2, itemHolderInfo, itemHolderInfo2)) {
            n();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        TraceCompat.beginSection("RV OnLayout");
        o();
        TraceCompat.endSection();
        this.s = true;
    }

    public void requestLayout() {
        if (this.U != 0 || this.u) {
            this.t = true;
        } else {
            super.requestLayout();
        }
    }

    /* access modifiers changed from: 0000 */
    public void p() {
        int c2 = this.f.c();
        for (int i2 = 0; i2 < c2; i2++) {
            ((LayoutParams) this.f.d(i2).getLayoutParams()).e = true;
        }
        this.d.h();
    }

    public void draw(Canvas canvas) {
        boolean z2;
        boolean z3;
        super.draw(canvas);
        int size = this.o.size();
        boolean z4 = false;
        for (int i2 = 0; i2 < size; i2++) {
            ((ItemDecoration) this.o.get(i2)).onDrawOver(canvas, this, this.C);
        }
        if (this.af == null || this.af.isFinished()) {
            z2 = false;
        } else {
            int save = canvas.save();
            int paddingBottom = this.h ? getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((float) ((-getHeight()) + paddingBottom), BitmapDescriptorFactory.HUE_RED);
            z2 = this.af != null && this.af.draw(canvas);
            canvas.restoreToCount(save);
        }
        if (this.ag != null && !this.ag.isFinished()) {
            int save2 = canvas.save();
            if (this.h) {
                canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
            }
            z2 |= this.ag != null && this.ag.draw(canvas);
            canvas.restoreToCount(save2);
        }
        if (this.ah != null && !this.ah.isFinished()) {
            int save3 = canvas.save();
            int width = getWidth();
            int paddingTop = this.h ? getPaddingTop() : 0;
            canvas.rotate(90.0f);
            canvas.translate((float) (-paddingTop), (float) (-width));
            z2 |= this.ah != null && this.ah.draw(canvas);
            canvas.restoreToCount(save3);
        }
        if (this.ai == null || this.ai.isFinished()) {
            z3 = z2;
        } else {
            int save4 = canvas.save();
            canvas.rotate(180.0f);
            if (this.h) {
                canvas.translate((float) ((-getWidth()) + getPaddingRight()), (float) ((-getHeight()) + getPaddingBottom()));
            } else {
                canvas.translate((float) (-getWidth()), (float) (-getHeight()));
            }
            if (this.ai != null && this.ai.draw(canvas)) {
                z4 = true;
            }
            z3 = z4 | z2;
            canvas.restoreToCount(save4);
        }
        if (!z3 && this.y != null && this.o.size() > 0 && this.y.isRunning()) {
            z3 = true;
        }
        if (z3) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = this.o.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((ItemDecoration) this.o.get(i2)).onDraw(canvas, this, this.C);
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && this.m.checkLayoutParams((LayoutParams) layoutParams);
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        if (this.m != null) {
            return this.m.generateDefaultLayoutParams();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("RecyclerView has no LayoutManager");
        sb.append(a());
        throw new IllegalStateException(sb.toString());
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        if (this.m != null) {
            return this.m.generateLayoutParams(getContext(), attributeSet);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("RecyclerView has no LayoutManager");
        sb.append(a());
        throw new IllegalStateException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (this.m != null) {
            return this.m.generateLayoutParams(layoutParams);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("RecyclerView has no LayoutManager");
        sb.append(a());
        throw new IllegalStateException(sb.toString());
    }

    public boolean isAnimating() {
        return this.y != null && this.y.isRunning();
    }

    /* access modifiers changed from: 0000 */
    public void q() {
        int c2 = this.f.c();
        for (int i2 = 0; i2 < c2; i2++) {
            ViewHolder b2 = b(this.f.d(i2));
            if (!b2.c()) {
                b2.b();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void r() {
        int c2 = this.f.c();
        for (int i2 = 0; i2 < c2; i2++) {
            ViewHolder b2 = b(this.f.d(i2));
            if (!b2.c()) {
                b2.a();
            }
        }
        this.d.g();
    }

    /* access modifiers changed from: 0000 */
    public void d(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int c2 = this.f.c();
        if (i2 < i3) {
            i6 = i2;
            i5 = i3;
            i4 = -1;
        } else {
            i5 = i2;
            i6 = i3;
            i4 = 1;
        }
        for (int i7 = 0; i7 < c2; i7++) {
            ViewHolder b2 = b(this.f.d(i7));
            if (b2 != null && b2.b >= i6 && b2.b <= i5) {
                if (b2.b == i2) {
                    b2.a(i3 - i2, false);
                } else {
                    b2.a(i4, false);
                }
                this.C.e = true;
            }
        }
        this.d.a(i2, i3);
        requestLayout();
    }

    /* access modifiers changed from: 0000 */
    public void e(int i2, int i3) {
        int c2 = this.f.c();
        for (int i4 = 0; i4 < c2; i4++) {
            ViewHolder b2 = b(this.f.d(i4));
            if (b2 != null && !b2.c() && b2.b >= i2) {
                b2.a(i3, false);
                this.C.e = true;
            }
        }
        this.d.b(i2, i3);
        requestLayout();
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, int i3, boolean z2) {
        int i4 = i2 + i3;
        int c2 = this.f.c();
        for (int i5 = 0; i5 < c2; i5++) {
            ViewHolder b2 = b(this.f.d(i5));
            if (b2 != null && !b2.c()) {
                if (b2.b >= i4) {
                    b2.a(-i3, z2);
                    this.C.e = true;
                } else if (b2.b >= i2) {
                    b2.a(i2 - 1, -i3, z2);
                    this.C.e = true;
                }
            }
        }
        this.d.a(i2, i3, z2);
        requestLayout();
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, int i3, Object obj) {
        int c2 = this.f.c();
        int i4 = i2 + i3;
        for (int i5 = 0; i5 < c2; i5++) {
            View d2 = this.f.d(i5);
            ViewHolder b2 = b(d2);
            if (b2 != null && !b2.c() && b2.b >= i2 && b2.b < i4) {
                b2.b(2);
                b2.a(obj);
                ((LayoutParams) d2.getLayoutParams()).e = true;
            }
        }
        this.d.c(i2, i3);
    }

    /* access modifiers changed from: 0000 */
    public boolean b(ViewHolder viewHolder) {
        return this.y == null || this.y.canReuseUpdatedViewHolder(viewHolder, viewHolder.q());
    }

    /* access modifiers changed from: 0000 */
    public void c(boolean z2) {
        this.x = z2 | this.x;
        this.w = true;
        s();
    }

    /* access modifiers changed from: 0000 */
    public void s() {
        int c2 = this.f.c();
        for (int i2 = 0; i2 < c2; i2++) {
            ViewHolder b2 = b(this.f.d(i2));
            if (b2 != null && !b2.c()) {
                b2.b(6);
            }
        }
        p();
        this.d.f();
    }

    public void invalidateItemDecorations() {
        if (this.o.size() != 0) {
            if (this.m != null) {
                this.m.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout");
            }
            p();
            requestLayout();
        }
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.aw;
    }

    public void setPreserveFocusAfterLayout(boolean z2) {
        this.aw = z2;
    }

    public ViewHolder getChildViewHolder(View view) {
        ViewParent parent = view.getParent();
        if (parent == null || parent == this) {
            return b(view);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("View ");
        sb.append(view);
        sb.append(" is not a direct child of ");
        sb.append(this);
        throw new IllegalArgumentException(sb.toString());
    }

    @Nullable
    public View findContainingItemView(View view) {
        ViewParent parent = view.getParent();
        while (parent != null && parent != this && (parent instanceof View)) {
            view = (View) parent;
            parent = view.getParent();
        }
        if (parent == this) {
            return view;
        }
        return null;
    }

    @Nullable
    public ViewHolder findContainingViewHolder(View view) {
        View findContainingItemView = findContainingItemView(view);
        if (findContainingItemView == null) {
            return null;
        }
        return getChildViewHolder(findContainingItemView);
    }

    static ViewHolder b(View view) {
        if (view == null) {
            return null;
        }
        return ((LayoutParams) view.getLayoutParams()).c;
    }

    @Deprecated
    public int getChildPosition(View view) {
        return getChildAdapterPosition(view);
    }

    public int getChildAdapterPosition(View view) {
        ViewHolder b2 = b(view);
        if (b2 != null) {
            return b2.getAdapterPosition();
        }
        return -1;
    }

    public int getChildLayoutPosition(View view) {
        ViewHolder b2 = b(view);
        if (b2 != null) {
            return b2.getLayoutPosition();
        }
        return -1;
    }

    public long getChildItemId(View view) {
        long j2 = -1;
        if (this.l == null || !this.l.hasStableIds()) {
            return -1;
        }
        ViewHolder b2 = b(view);
        if (b2 != null) {
            j2 = b2.getItemId();
        }
        return j2;
    }

    @Deprecated
    public ViewHolder findViewHolderForPosition(int i2) {
        return a(i2, false);
    }

    public ViewHolder findViewHolderForLayoutPosition(int i2) {
        return a(i2, false);
    }

    public ViewHolder findViewHolderForAdapterPosition(int i2) {
        ViewHolder viewHolder = null;
        if (this.w) {
            return null;
        }
        int c2 = this.f.c();
        for (int i3 = 0; i3 < c2; i3++) {
            ViewHolder b2 = b(this.f.d(i3));
            if (b2 != null && !b2.m() && d(b2) == i2) {
                if (!this.f.c(b2.itemView)) {
                    return b2;
                }
                viewHolder = b2;
            }
        }
        return viewHolder;
    }

    /* access modifiers changed from: 0000 */
    public ViewHolder a(int i2, boolean z2) {
        int c2 = this.f.c();
        ViewHolder viewHolder = null;
        for (int i3 = 0; i3 < c2; i3++) {
            ViewHolder b2 = b(this.f.d(i3));
            if (b2 != null && !b2.m()) {
                if (z2) {
                    if (b2.b != i2) {
                        continue;
                    }
                } else if (b2.getLayoutPosition() != i2) {
                    continue;
                }
                if (!this.f.c(b2.itemView)) {
                    return b2;
                }
                viewHolder = b2;
            }
        }
        return viewHolder;
    }

    public ViewHolder findViewHolderForItemId(long j2) {
        ViewHolder viewHolder = null;
        if (this.l == null || !this.l.hasStableIds()) {
            return null;
        }
        int c2 = this.f.c();
        for (int i2 = 0; i2 < c2; i2++) {
            ViewHolder b2 = b(this.f.d(i2));
            if (b2 != null && !b2.m() && b2.getItemId() == j2) {
                if (!this.f.c(b2.itemView)) {
                    return b2;
                }
                viewHolder = b2;
            }
        }
        return viewHolder;
    }

    public View findChildViewUnder(float f2, float f3) {
        for (int b2 = this.f.b() - 1; b2 >= 0; b2--) {
            View b3 = this.f.b(b2);
            float translationX = b3.getTranslationX();
            float translationY = b3.getTranslationY();
            if (f2 >= ((float) b3.getLeft()) + translationX && f2 <= ((float) b3.getRight()) + translationX && f3 >= ((float) b3.getTop()) + translationY && f3 <= ((float) b3.getBottom()) + translationY) {
                return b3;
            }
        }
        return null;
    }

    public boolean drawChild(Canvas canvas, View view, long j2) {
        return super.drawChild(canvas, view, j2);
    }

    public void offsetChildrenVertical(int i2) {
        int b2 = this.f.b();
        for (int i3 = 0; i3 < b2; i3++) {
            this.f.b(i3).offsetTopAndBottom(i2);
        }
    }

    public void offsetChildrenHorizontal(int i2) {
        int b2 = this.f.b();
        for (int i3 = 0; i3 < b2; i3++) {
            this.f.b(i3).offsetLeftAndRight(i2);
        }
    }

    public void getDecoratedBoundsWithMargins(View view, Rect rect) {
        a(view, rect);
    }

    static void a(View view, Rect rect) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect2 = layoutParams.d;
        rect.set((view.getLeft() - rect2.left) - layoutParams.leftMargin, (view.getTop() - rect2.top) - layoutParams.topMargin, view.getRight() + rect2.right + layoutParams.rightMargin, view.getBottom() + rect2.bottom + layoutParams.bottomMargin);
    }

    /* access modifiers changed from: 0000 */
    public Rect c(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (!layoutParams.e) {
            return layoutParams.d;
        }
        if (this.C.isPreLayout() && (layoutParams.isItemChanged() || layoutParams.isViewInvalid())) {
            return layoutParams.d;
        }
        Rect rect = layoutParams.d;
        rect.set(0, 0, 0, 0);
        int size = this.o.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.j.set(0, 0, 0, 0);
            ((ItemDecoration) this.o.get(i2)).getItemOffsets(this.j, view, this, this.C);
            rect.left += this.j.left;
            rect.top += this.j.top;
            rect.right += this.j.right;
            rect.bottom += this.j.bottom;
        }
        layoutParams.e = false;
        return rect;
    }

    /* access modifiers changed from: 0000 */
    public void f(int i2, int i3) {
        this.ad++;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        onScrollChanged(scrollX, scrollY, scrollX, scrollY);
        onScrolled(i2, i3);
        if (this.ax != null) {
            this.ax.onScrolled(this, i2, i3);
        }
        if (this.ay != null) {
            for (int size = this.ay.size() - 1; size >= 0; size--) {
                ((OnScrollListener) this.ay.get(size)).onScrolled(this, i2, i3);
            }
        }
        this.ad--;
    }

    /* access modifiers changed from: 0000 */
    public void b(int i2) {
        if (this.m != null) {
            this.m.onScrollStateChanged(i2);
        }
        onScrollStateChanged(i2);
        if (this.ax != null) {
            this.ax.onScrollStateChanged(this, i2);
        }
        if (this.ay != null) {
            for (int size = this.ay.size() - 1; size >= 0; size--) {
                ((OnScrollListener) this.ay.get(size)).onScrollStateChanged(this, i2);
            }
        }
    }

    public boolean hasPendingAdapterUpdates() {
        return !this.s || this.w || this.e.d();
    }

    /* access modifiers changed from: 0000 */
    public void t() {
        int b2 = this.f.b();
        for (int i2 = 0; i2 < b2; i2++) {
            View b3 = this.f.b(i2);
            ViewHolder childViewHolder = getChildViewHolder(b3);
            if (!(childViewHolder == null || childViewHolder.h == null)) {
                View view = childViewHolder.h.itemView;
                int left = b3.getLeft();
                int top = b3.getTop();
                if (left != view.getLeft() || top != view.getTop()) {
                    view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
                }
            }
        }
    }

    @Nullable
    static RecyclerView d(@NonNull View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        if (view instanceof RecyclerView) {
            return (RecyclerView) view;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            RecyclerView d2 = d(viewGroup.getChildAt(i2));
            if (d2 != null) {
                return d2;
            }
        }
        return null;
    }

    static void c(@NonNull ViewHolder viewHolder) {
        if (viewHolder.a != null) {
            View view = (View) viewHolder.a.get();
            while (view != null) {
                if (view != viewHolder.itemView) {
                    ViewParent parent = view.getParent();
                    view = parent instanceof View ? (View) parent : null;
                } else {
                    return;
                }
            }
            viewHolder.a = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public long getNanoTime() {
        if (L) {
            return System.nanoTime();
        }
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public void e(View view) {
        ViewHolder b2 = b(view);
        onChildDetachedFromWindow(view);
        if (!(this.l == null || b2 == null)) {
            this.l.onViewDetachedFromWindow(b2);
        }
        if (this.ab != null) {
            for (int size = this.ab.size() - 1; size >= 0; size--) {
                ((OnChildAttachStateChangeListener) this.ab.get(size)).onChildViewDetachedFromWindow(view);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void f(View view) {
        ViewHolder b2 = b(view);
        onChildAttachedToWindow(view);
        if (!(this.l == null || b2 == null)) {
            this.l.onViewAttachedToWindow(b2);
        }
        if (this.ab != null) {
            for (int size = this.ab.size() - 1; size >= 0; size--) {
                ((OnChildAttachStateChangeListener) this.ab.get(size)).onChildViewAttachedToWindow(view);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public boolean a(ViewHolder viewHolder, int i2) {
        if (isComputingLayout()) {
            viewHolder.k = i2;
            this.H.add(viewHolder);
            return false;
        }
        ViewCompat.setImportantForAccessibility(viewHolder.itemView, i2);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void u() {
        for (int size = this.H.size() - 1; size >= 0; size--) {
            ViewHolder viewHolder = (ViewHolder) this.H.get(size);
            if (viewHolder.itemView.getParent() == this && !viewHolder.c()) {
                int i2 = viewHolder.k;
                if (i2 != -1) {
                    ViewCompat.setImportantForAccessibility(viewHolder.itemView, i2);
                    viewHolder.k = -1;
                }
            }
        }
        this.H.clear();
    }

    /* access modifiers changed from: 0000 */
    public int d(ViewHolder viewHolder) {
        if (viewHolder.a(524) || !viewHolder.l()) {
            return -1;
        }
        return this.e.c(viewHolder.b);
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public void a(StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2) {
        if (stateListDrawable == null || drawable == null || stateListDrawable2 == null || drawable2 == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Trying to set fast scroller without both required drawables.");
            sb.append(a());
            throw new IllegalArgumentException(sb.toString());
        }
        Resources resources = getContext().getResources();
        new FastScroller(this, stateListDrawable, drawable, stateListDrawable2, drawable2, resources.getDimensionPixelSize(R.dimen.fastscroll_default_thickness), resources.getDimensionPixelSize(R.dimen.fastscroll_minimum_range), resources.getDimensionPixelOffset(R.dimen.fastscroll_margin));
    }

    public void setNestedScrollingEnabled(boolean z2) {
        getScrollingChildHelper().setNestedScrollingEnabled(z2);
    }

    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    public boolean startNestedScroll(int i2) {
        return getScrollingChildHelper().startNestedScroll(i2);
    }

    public boolean startNestedScroll(int i2, int i3) {
        return getScrollingChildHelper().startNestedScroll(i2, i3);
    }

    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    public void stopNestedScroll(int i2) {
        getScrollingChildHelper().stopNestedScroll(i2);
    }

    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    public boolean hasNestedScrollingParent(int i2) {
        return getScrollingChildHelper().hasNestedScrollingParent(i2);
    }

    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr) {
        return getScrollingChildHelper().dispatchNestedScroll(i2, i3, i4, i5, iArr);
    }

    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr, int i6) {
        return getScrollingChildHelper().dispatchNestedScroll(i2, i3, i4, i5, iArr, i6);
    }

    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2) {
        return getScrollingChildHelper().dispatchNestedPreScroll(i2, i3, iArr, iArr2);
    }

    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2, int i4) {
        return getScrollingChildHelper().dispatchNestedPreScroll(i2, i3, iArr, iArr2, i4);
    }

    public boolean dispatchNestedFling(float f2, float f3, boolean z2) {
        return getScrollingChildHelper().dispatchNestedFling(f2, f3, z2);
    }

    public boolean dispatchNestedPreFling(float f2, float f3) {
        return getScrollingChildHelper().dispatchNestedPreFling(f2, f3);
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i2, int i3) {
        if (this.aA == null) {
            return super.getChildDrawingOrder(i2, i3);
        }
        return this.aA.onGetChildDrawingOrder(i2, i3);
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (this.aC == null) {
            this.aC = new NestedScrollingChildHelper(this);
        }
        return this.aC;
    }
}
