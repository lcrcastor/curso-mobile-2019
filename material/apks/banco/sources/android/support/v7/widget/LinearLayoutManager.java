package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry;
import android.support.v7.widget.RecyclerView.LayoutManager.Properties;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.SmoothScroller.ScrollVectorProvider;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper.ViewDropHandler;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.List;

public class LinearLayoutManager extends LayoutManager implements ScrollVectorProvider, ViewDropHandler {
    public static final int HORIZONTAL = 0;
    public static final int INVALID_OFFSET = Integer.MIN_VALUE;
    public static final int VERTICAL = 1;
    private LayoutState a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e;
    private boolean f;
    private final LayoutChunkResult g;
    private int h;
    int i;
    OrientationHelper j;
    boolean k;
    int l;
    int m;
    SavedState n;
    final AnchorInfo o;

    static class AnchorInfo {
        OrientationHelper a;
        int b;
        int c;
        boolean d;
        boolean e;

        AnchorInfo() {
            a();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.b = -1;
            this.c = Integer.MIN_VALUE;
            this.d = false;
            this.e = false;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            int i;
            if (this.d) {
                i = this.a.getEndAfterPadding();
            } else {
                i = this.a.getStartAfterPadding();
            }
            this.c = i;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("AnchorInfo{mPosition=");
            sb.append(this.b);
            sb.append(", mCoordinate=");
            sb.append(this.c);
            sb.append(", mLayoutFromEnd=");
            sb.append(this.d);
            sb.append(", mValid=");
            sb.append(this.e);
            sb.append('}');
            return sb.toString();
        }

        /* access modifiers changed from: 0000 */
        public boolean a(View view, State state) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            return !layoutParams.isItemRemoved() && layoutParams.getViewLayoutPosition() >= 0 && layoutParams.getViewLayoutPosition() < state.getItemCount();
        }

        public void a(View view, int i) {
            int totalSpaceChange = this.a.getTotalSpaceChange();
            if (totalSpaceChange >= 0) {
                b(view, i);
                return;
            }
            this.b = i;
            if (this.d) {
                int endAfterPadding = (this.a.getEndAfterPadding() - totalSpaceChange) - this.a.getDecoratedEnd(view);
                this.c = this.a.getEndAfterPadding() - endAfterPadding;
                if (endAfterPadding > 0) {
                    int decoratedMeasurement = this.c - this.a.getDecoratedMeasurement(view);
                    int startAfterPadding = this.a.getStartAfterPadding();
                    int min = decoratedMeasurement - (startAfterPadding + Math.min(this.a.getDecoratedStart(view) - startAfterPadding, 0));
                    if (min < 0) {
                        this.c += Math.min(endAfterPadding, -min);
                    }
                }
            } else {
                int decoratedStart = this.a.getDecoratedStart(view);
                int startAfterPadding2 = decoratedStart - this.a.getStartAfterPadding();
                this.c = decoratedStart;
                if (startAfterPadding2 > 0) {
                    int endAfterPadding2 = (this.a.getEndAfterPadding() - Math.min(0, (this.a.getEndAfterPadding() - totalSpaceChange) - this.a.getDecoratedEnd(view))) - (decoratedStart + this.a.getDecoratedMeasurement(view));
                    if (endAfterPadding2 < 0) {
                        this.c -= Math.min(startAfterPadding2, -endAfterPadding2);
                    }
                }
            }
        }

        public void b(View view, int i) {
            if (this.d) {
                this.c = this.a.getDecoratedEnd(view) + this.a.getTotalSpaceChange();
            } else {
                this.c = this.a.getDecoratedStart(view);
            }
            this.b = i;
        }
    }

    public static class LayoutChunkResult {
        public int mConsumed;
        public boolean mFinished;
        public boolean mFocusable;
        public boolean mIgnoreConsumed;

        protected LayoutChunkResult() {
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.mConsumed = 0;
            this.mFinished = false;
            this.mIgnoreConsumed = false;
            this.mFocusable = false;
        }
    }

    static class LayoutState {
        boolean a = true;
        int b;
        int c;
        int d;
        int e;
        int f;
        int g;
        int h = 0;
        boolean i = false;
        int j;
        List<ViewHolder> k = null;
        boolean l;

        LayoutState() {
        }

        /* access modifiers changed from: 0000 */
        public boolean a(State state) {
            return this.d >= 0 && this.d < state.getItemCount();
        }

        /* access modifiers changed from: 0000 */
        public View a(Recycler recycler) {
            if (this.k != null) {
                return b();
            }
            View viewForPosition = recycler.getViewForPosition(this.d);
            this.d += this.e;
            return viewForPosition;
        }

        private View b() {
            int size = this.k.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view = ((ViewHolder) this.k.get(i2)).itemView;
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                if (!layoutParams.isItemRemoved() && this.d == layoutParams.getViewLayoutPosition()) {
                    a(view);
                    return view;
                }
            }
            return null;
        }

        public void a() {
            a((View) null);
        }

        public void a(View view) {
            View b2 = b(view);
            if (b2 == null) {
                this.d = -1;
            } else {
                this.d = ((LayoutParams) b2.getLayoutParams()).getViewLayoutPosition();
            }
        }

        public View b(View view) {
            int size = this.k.size();
            View view2 = null;
            int i2 = SubsamplingScaleImageView.TILE_SIZE_AUTO;
            for (int i3 = 0; i3 < size; i3++) {
                View view3 = ((ViewHolder) this.k.get(i3)).itemView;
                LayoutParams layoutParams = (LayoutParams) view3.getLayoutParams();
                if (view3 != view && !layoutParams.isItemRemoved()) {
                    int viewLayoutPosition = (layoutParams.getViewLayoutPosition() - this.d) * this.e;
                    if (viewLayoutPosition >= 0 && viewLayoutPosition < i2) {
                        if (viewLayoutPosition == 0) {
                            return view3;
                        }
                        view2 = view3;
                        i2 = viewLayoutPosition;
                    }
                }
            }
            return view2;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static class SavedState implements Parcelable {
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
        int a;
        int b;
        boolean c;

        public int describeContents() {
            return 0;
        }

        public SavedState() {
        }

        SavedState(Parcel parcel) {
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            boolean z = true;
            if (parcel.readInt() != 1) {
                z = false;
            }
            this.c = z;
        }

        public SavedState(SavedState savedState) {
            this.a = savedState.a;
            this.b = savedState.b;
            this.c = savedState.c;
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return this.a >= 0;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.a = -1;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeInt(this.c ? 1 : 0);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Recycler recycler, State state, AnchorInfo anchorInfo, int i2) {
    }

    public boolean isAutoMeasureEnabled() {
        return true;
    }

    public LinearLayoutManager(Context context) {
        this(context, 1, false);
    }

    public LinearLayoutManager(Context context, int i2, boolean z) {
        this.i = 1;
        this.c = false;
        this.k = false;
        this.d = false;
        this.e = true;
        this.l = -1;
        this.m = Integer.MIN_VALUE;
        this.n = null;
        this.o = new AnchorInfo();
        this.g = new LayoutChunkResult();
        this.h = 2;
        setOrientation(i2);
        setReverseLayout(z);
    }

    public LinearLayoutManager(Context context, AttributeSet attributeSet, int i2, int i3) {
        this.i = 1;
        this.c = false;
        this.k = false;
        this.d = false;
        this.e = true;
        this.l = -1;
        this.m = Integer.MIN_VALUE;
        this.n = null;
        this.o = new AnchorInfo();
        this.g = new LayoutChunkResult();
        this.h = 2;
        Properties properties = getProperties(context, attributeSet, i2, i3);
        setOrientation(properties.orientation);
        setReverseLayout(properties.reverseLayout);
        setStackFromEnd(properties.stackFromEnd);
    }

    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public boolean getRecycleChildrenOnDetach() {
        return this.f;
    }

    public void setRecycleChildrenOnDetach(boolean z) {
        this.f = z;
    }

    public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        if (this.f) {
            removeAndRecycleAllViews(recycler);
            recycler.clear();
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            accessibilityEvent.setFromIndex(findFirstVisibleItemPosition());
            accessibilityEvent.setToIndex(findLastVisibleItemPosition());
        }
    }

    public Parcelable onSaveInstanceState() {
        if (this.n != null) {
            return new SavedState(this.n);
        }
        SavedState savedState = new SavedState();
        if (getChildCount() > 0) {
            a();
            boolean z = this.b ^ this.k;
            savedState.c = z;
            if (z) {
                View i2 = i();
                savedState.b = this.j.getEndAfterPadding() - this.j.getDecoratedEnd(i2);
                savedState.a = getPosition(i2);
            } else {
                View h2 = h();
                savedState.a = getPosition(h2);
                savedState.b = this.j.getDecoratedStart(h2) - this.j.getStartAfterPadding();
            }
        } else {
            savedState.b();
        }
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.n = (SavedState) parcelable;
            requestLayout();
        }
    }

    public boolean canScrollHorizontally() {
        return this.i == 0;
    }

    public boolean canScrollVertically() {
        return this.i == 1;
    }

    public void setStackFromEnd(boolean z) {
        assertNotInLayoutOrScroll(null);
        if (this.d != z) {
            this.d = z;
            requestLayout();
        }
    }

    public boolean getStackFromEnd() {
        return this.d;
    }

    public int getOrientation() {
        return this.i;
    }

    public void setOrientation(int i2) {
        if (i2 == 0 || i2 == 1) {
            assertNotInLayoutOrScroll(null);
            if (i2 != this.i || this.j == null) {
                this.j = OrientationHelper.createOrientationHelper(this, i2);
                this.o.a = this.j;
                this.i = i2;
                requestLayout();
                return;
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("invalid orientation:");
        sb.append(i2);
        throw new IllegalArgumentException(sb.toString());
    }

    private void g() {
        if (this.i == 1 || !isLayoutRTL()) {
            this.k = this.c;
        } else {
            this.k = !this.c;
        }
    }

    public boolean getReverseLayout() {
        return this.c;
    }

    public void setReverseLayout(boolean z) {
        assertNotInLayoutOrScroll(null);
        if (z != this.c) {
            this.c = z;
            requestLayout();
        }
    }

    public View findViewByPosition(int i2) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return null;
        }
        int position = i2 - getPosition(getChildAt(0));
        if (position >= 0 && position < childCount) {
            View childAt = getChildAt(position);
            if (getPosition(childAt) == i2) {
                return childAt;
            }
        }
        return super.findViewByPosition(i2);
    }

    /* access modifiers changed from: protected */
    public int getExtraLayoutSpace(State state) {
        if (state.hasTargetScrollPosition()) {
            return this.j.getTotalSpace();
        }
        return 0;
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, State state, int i2) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext());
        linearSmoothScroller.setTargetPosition(i2);
        startSmoothScroll(linearSmoothScroller);
    }

    public PointF computeScrollVectorForPosition(int i2) {
        if (getChildCount() == 0) {
            return null;
        }
        boolean z = false;
        int i3 = 1;
        if (i2 < getPosition(getChildAt(0))) {
            z = true;
        }
        if (z != this.k) {
            i3 = -1;
        }
        if (this.i == 0) {
            return new PointF((float) i3, BitmapDescriptorFactory.HUE_RED);
        }
        return new PointF(BitmapDescriptorFactory.HUE_RED, (float) i3);
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = -1;
        if (!(this.n == null && this.l == -1) && state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        if (this.n != null && this.n.a()) {
            this.l = this.n.a;
        }
        a();
        this.a.a = false;
        g();
        View focusedChild = getFocusedChild();
        if (!this.o.e || this.l != -1 || this.n != null) {
            this.o.a();
            this.o.d = this.k ^ this.d;
            a(recycler, state, this.o);
            this.o.e = true;
        } else if (focusedChild != null && (this.j.getDecoratedStart(focusedChild) >= this.j.getEndAfterPadding() || this.j.getDecoratedEnd(focusedChild) <= this.j.getStartAfterPadding())) {
            this.o.a(focusedChild, getPosition(focusedChild));
        }
        int extraLayoutSpace = getExtraLayoutSpace(state);
        if (this.a.j >= 0) {
            i2 = extraLayoutSpace;
            extraLayoutSpace = 0;
        } else {
            i2 = 0;
        }
        int startAfterPadding = extraLayoutSpace + this.j.getStartAfterPadding();
        int endPadding = i2 + this.j.getEndPadding();
        if (!(!state.isPreLayout() || this.l == -1 || this.m == Integer.MIN_VALUE)) {
            View findViewByPosition = findViewByPosition(this.l);
            if (findViewByPosition != null) {
                if (this.k) {
                    i5 = (this.j.getEndAfterPadding() - this.j.getDecoratedEnd(findViewByPosition)) - this.m;
                } else {
                    i5 = this.m - (this.j.getDecoratedStart(findViewByPosition) - this.j.getStartAfterPadding());
                }
                if (i5 > 0) {
                    startAfterPadding += i5;
                } else {
                    endPadding -= i5;
                }
            }
        }
        if (!this.o.d ? !this.k : this.k) {
            i6 = 1;
        }
        a(recycler, state, this.o, i6);
        detachAndScrapAttachedViews(recycler);
        this.a.l = c();
        this.a.i = state.isPreLayout();
        if (this.o.d) {
            b(this.o);
            this.a.h = startAfterPadding;
            a(recycler, this.a, state, false);
            i4 = this.a.b;
            int i7 = this.a.d;
            if (this.a.c > 0) {
                endPadding += this.a.c;
            }
            a(this.o);
            this.a.h = endPadding;
            this.a.d += this.a.e;
            a(recycler, this.a, state, false);
            i3 = this.a.b;
            if (this.a.c > 0) {
                int i8 = this.a.c;
                e(i7, i4);
                this.a.h = i8;
                a(recycler, this.a, state, false);
                i4 = this.a.b;
            }
        } else {
            a(this.o);
            this.a.h = endPadding;
            a(recycler, this.a, state, false);
            i3 = this.a.b;
            int i9 = this.a.d;
            if (this.a.c > 0) {
                startAfterPadding += this.a.c;
            }
            b(this.o);
            this.a.h = startAfterPadding;
            this.a.d += this.a.e;
            a(recycler, this.a, state, false);
            i4 = this.a.b;
            if (this.a.c > 0) {
                int i10 = this.a.c;
                a(i9, i3);
                this.a.h = i10;
                a(recycler, this.a, state, false);
                i3 = this.a.b;
            }
        }
        if (getChildCount() > 0) {
            if (this.k ^ this.d) {
                int a2 = a(i3, recycler, state, true);
                int i11 = i4 + a2;
                int i12 = i3 + a2;
                int b2 = b(i11, recycler, state, false);
                i4 = i11 + b2;
                i3 = i12 + b2;
            } else {
                int b3 = b(i4, recycler, state, true);
                int i13 = i4 + b3;
                int i14 = i3 + b3;
                int a3 = a(i14, recycler, state, false);
                i4 = i13 + a3;
                i3 = i14 + a3;
            }
        }
        a(recycler, state, i4, i3);
        if (!state.isPreLayout()) {
            this.j.onLayoutComplete();
        } else {
            this.o.a();
        }
        this.b = this.d;
    }

    public void onLayoutCompleted(State state) {
        super.onLayoutCompleted(state);
        this.n = null;
        this.l = -1;
        this.m = Integer.MIN_VALUE;
        this.o.a();
    }

    private void a(Recycler recycler, State state, int i2, int i3) {
        Recycler recycler2 = recycler;
        State state2 = state;
        if (state.willRunPredictiveAnimations() && getChildCount() != 0 && !state.isPreLayout() && supportsPredictiveItemAnimations()) {
            List<ViewHolder> scrapList = recycler.getScrapList();
            int size = scrapList.size();
            int position = getPosition(getChildAt(0));
            int i4 = 0;
            int i5 = 0;
            for (int i6 = 0; i6 < size; i6++) {
                ViewHolder viewHolder = (ViewHolder) scrapList.get(i6);
                if (!viewHolder.m()) {
                    char c2 = 1;
                    if ((viewHolder.getLayoutPosition() < position) != this.k) {
                        c2 = 65535;
                    }
                    if (c2 == 65535) {
                        i4 += this.j.getDecoratedMeasurement(viewHolder.itemView);
                    } else {
                        i5 += this.j.getDecoratedMeasurement(viewHolder.itemView);
                    }
                }
            }
            this.a.k = scrapList;
            if (i4 > 0) {
                e(getPosition(h()), i2);
                this.a.h = i4;
                this.a.c = 0;
                this.a.a();
                a(recycler2, this.a, state2, false);
            }
            if (i5 > 0) {
                a(getPosition(i()), i3);
                this.a.h = i5;
                this.a.c = 0;
                this.a.a();
                a(recycler2, this.a, state2, false);
            }
            this.a.k = null;
        }
    }

    private void a(Recycler recycler, State state, AnchorInfo anchorInfo) {
        if (!a(state, anchorInfo) && !b(recycler, state, anchorInfo)) {
            anchorInfo.b();
            anchorInfo.b = this.d ? state.getItemCount() - 1 : 0;
        }
    }

    private boolean b(Recycler recycler, State state, AnchorInfo anchorInfo) {
        View view;
        int i2;
        boolean z = false;
        if (getChildCount() == 0) {
            return false;
        }
        View focusedChild = getFocusedChild();
        if (focusedChild != null && anchorInfo.a(focusedChild, state)) {
            anchorInfo.a(focusedChild, getPosition(focusedChild));
            return true;
        } else if (this.b != this.d) {
            return false;
        } else {
            if (anchorInfo.d) {
                view = a(recycler, state);
            } else {
                view = b(recycler, state);
            }
            if (view == null) {
                return false;
            }
            anchorInfo.b(view, getPosition(view));
            if (!state.isPreLayout() && supportsPredictiveItemAnimations()) {
                if (this.j.getDecoratedStart(view) >= this.j.getEndAfterPadding() || this.j.getDecoratedEnd(view) < this.j.getStartAfterPadding()) {
                    z = true;
                }
                if (z) {
                    if (anchorInfo.d) {
                        i2 = this.j.getEndAfterPadding();
                    } else {
                        i2 = this.j.getStartAfterPadding();
                    }
                    anchorInfo.c = i2;
                }
            }
            return true;
        }
    }

    private boolean a(State state, AnchorInfo anchorInfo) {
        int i2;
        boolean z = false;
        if (state.isPreLayout() || this.l == -1) {
            return false;
        }
        if (this.l < 0 || this.l >= state.getItemCount()) {
            this.l = -1;
            this.m = Integer.MIN_VALUE;
            return false;
        }
        anchorInfo.b = this.l;
        if (this.n != null && this.n.a()) {
            anchorInfo.d = this.n.c;
            if (anchorInfo.d) {
                anchorInfo.c = this.j.getEndAfterPadding() - this.n.b;
            } else {
                anchorInfo.c = this.j.getStartAfterPadding() + this.n.b;
            }
            return true;
        } else if (this.m == Integer.MIN_VALUE) {
            View findViewByPosition = findViewByPosition(this.l);
            if (findViewByPosition == null) {
                if (getChildCount() > 0) {
                    if ((this.l < getPosition(getChildAt(0))) == this.k) {
                        z = true;
                    }
                    anchorInfo.d = z;
                }
                anchorInfo.b();
            } else if (this.j.getDecoratedMeasurement(findViewByPosition) > this.j.getTotalSpace()) {
                anchorInfo.b();
                return true;
            } else if (this.j.getDecoratedStart(findViewByPosition) - this.j.getStartAfterPadding() < 0) {
                anchorInfo.c = this.j.getStartAfterPadding();
                anchorInfo.d = false;
                return true;
            } else if (this.j.getEndAfterPadding() - this.j.getDecoratedEnd(findViewByPosition) < 0) {
                anchorInfo.c = this.j.getEndAfterPadding();
                anchorInfo.d = true;
                return true;
            } else {
                if (anchorInfo.d) {
                    i2 = this.j.getDecoratedEnd(findViewByPosition) + this.j.getTotalSpaceChange();
                } else {
                    i2 = this.j.getDecoratedStart(findViewByPosition);
                }
                anchorInfo.c = i2;
            }
            return true;
        } else {
            anchorInfo.d = this.k;
            if (this.k) {
                anchorInfo.c = this.j.getEndAfterPadding() - this.m;
            } else {
                anchorInfo.c = this.j.getStartAfterPadding() + this.m;
            }
            return true;
        }
    }

    private int a(int i2, Recycler recycler, State state, boolean z) {
        int endAfterPadding = this.j.getEndAfterPadding() - i2;
        if (endAfterPadding <= 0) {
            return 0;
        }
        int i3 = -a(-endAfterPadding, recycler, state);
        int i4 = i2 + i3;
        if (z) {
            int endAfterPadding2 = this.j.getEndAfterPadding() - i4;
            if (endAfterPadding2 > 0) {
                this.j.offsetChildren(endAfterPadding2);
                return endAfterPadding2 + i3;
            }
        }
        return i3;
    }

    private int b(int i2, Recycler recycler, State state, boolean z) {
        int startAfterPadding = i2 - this.j.getStartAfterPadding();
        if (startAfterPadding <= 0) {
            return 0;
        }
        int i3 = -a(startAfterPadding, recycler, state);
        int i4 = i2 + i3;
        if (z) {
            int startAfterPadding2 = i4 - this.j.getStartAfterPadding();
            if (startAfterPadding2 > 0) {
                this.j.offsetChildren(-startAfterPadding2);
                return i3 - startAfterPadding2;
            }
        }
        return i3;
    }

    private void a(AnchorInfo anchorInfo) {
        a(anchorInfo.b, anchorInfo.c);
    }

    private void a(int i2, int i3) {
        this.a.c = this.j.getEndAfterPadding() - i3;
        this.a.e = this.k ? -1 : 1;
        this.a.d = i2;
        this.a.f = 1;
        this.a.b = i3;
        this.a.g = Integer.MIN_VALUE;
    }

    private void b(AnchorInfo anchorInfo) {
        e(anchorInfo.b, anchorInfo.c);
    }

    private void e(int i2, int i3) {
        this.a.c = i3 - this.j.getStartAfterPadding();
        this.a.d = i2;
        this.a.e = this.k ? 1 : -1;
        this.a.f = -1;
        this.a.b = i3;
        this.a.g = Integer.MIN_VALUE;
    }

    /* access modifiers changed from: protected */
    public boolean isLayoutRTL() {
        return getLayoutDirection() == 1;
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (this.a == null) {
            this.a = b();
        }
    }

    /* access modifiers changed from: 0000 */
    public LayoutState b() {
        return new LayoutState();
    }

    public void scrollToPosition(int i2) {
        this.l = i2;
        this.m = Integer.MIN_VALUE;
        if (this.n != null) {
            this.n.b();
        }
        requestLayout();
    }

    public void scrollToPositionWithOffset(int i2, int i3) {
        this.l = i2;
        this.m = i3;
        if (this.n != null) {
            this.n.b();
        }
        requestLayout();
    }

    public int scrollHorizontallyBy(int i2, Recycler recycler, State state) {
        if (this.i == 1) {
            return 0;
        }
        return a(i2, recycler, state);
    }

    public int scrollVerticallyBy(int i2, Recycler recycler, State state) {
        if (this.i == 0) {
            return 0;
        }
        return a(i2, recycler, state);
    }

    public int computeHorizontalScrollOffset(State state) {
        return a(state);
    }

    public int computeVerticalScrollOffset(State state) {
        return a(state);
    }

    public int computeHorizontalScrollExtent(State state) {
        return b(state);
    }

    public int computeVerticalScrollExtent(State state) {
        return b(state);
    }

    public int computeHorizontalScrollRange(State state) {
        return c(state);
    }

    public int computeVerticalScrollRange(State state) {
        return c(state);
    }

    private int a(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        a();
        OrientationHelper orientationHelper = this.j;
        View a2 = a(!this.e, true);
        return ScrollbarHelper.a(state, orientationHelper, a2, b(!this.e, true), this, this.e, this.k);
    }

    private int b(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        a();
        OrientationHelper orientationHelper = this.j;
        View a2 = a(!this.e, true);
        return ScrollbarHelper.a(state, orientationHelper, a2, b(!this.e, true), this, this.e);
    }

    private int c(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        a();
        OrientationHelper orientationHelper = this.j;
        View a2 = a(!this.e, true);
        return ScrollbarHelper.b(state, orientationHelper, a2, b(!this.e, true), this, this.e);
    }

    public void setSmoothScrollbarEnabled(boolean z) {
        this.e = z;
    }

    public boolean isSmoothScrollbarEnabled() {
        return this.e;
    }

    private void a(int i2, int i3, boolean z, State state) {
        int i4;
        this.a.l = c();
        this.a.h = getExtraLayoutSpace(state);
        this.a.f = i2;
        int i5 = -1;
        if (i2 == 1) {
            this.a.h += this.j.getEndPadding();
            View i6 = i();
            LayoutState layoutState = this.a;
            if (!this.k) {
                i5 = 1;
            }
            layoutState.e = i5;
            this.a.d = getPosition(i6) + this.a.e;
            this.a.b = this.j.getDecoratedEnd(i6);
            i4 = this.j.getDecoratedEnd(i6) - this.j.getEndAfterPadding();
        } else {
            View h2 = h();
            this.a.h += this.j.getStartAfterPadding();
            LayoutState layoutState2 = this.a;
            if (this.k) {
                i5 = 1;
            }
            layoutState2.e = i5;
            this.a.d = getPosition(h2) + this.a.e;
            this.a.b = this.j.getDecoratedStart(h2);
            i4 = (-this.j.getDecoratedStart(h2)) + this.j.getStartAfterPadding();
        }
        this.a.c = i3;
        if (z) {
            this.a.c -= i4;
        }
        this.a.g = i4;
    }

    /* access modifiers changed from: 0000 */
    public boolean c() {
        return this.j.getMode() == 0 && this.j.getEnd() == 0;
    }

    /* access modifiers changed from: 0000 */
    public void a(State state, LayoutState layoutState, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i2 = layoutState.d;
        if (i2 >= 0 && i2 < state.getItemCount()) {
            layoutPrefetchRegistry.addPosition(i2, Math.max(0, layoutState.g));
        }
    }

    public void collectInitialPrefetchPositions(int i2, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i3;
        boolean z;
        int i4 = -1;
        if (this.n == null || !this.n.a()) {
            g();
            z = this.k;
            i3 = this.l == -1 ? z ? i2 - 1 : 0 : this.l;
        } else {
            z = this.n.c;
            i3 = this.n.a;
        }
        if (!z) {
            i4 = 1;
        }
        for (int i5 = 0; i5 < this.h && i3 >= 0 && i3 < i2; i5++) {
            layoutPrefetchRegistry.addPosition(i3, 0);
            i3 += i4;
        }
    }

    public void setInitialPrefetchItemCount(int i2) {
        this.h = i2;
    }

    public int getInitialPrefetchItemCount() {
        return this.h;
    }

    public void collectAdjacentPrefetchPositions(int i2, int i3, State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        if (this.i != 0) {
            i2 = i3;
        }
        if (getChildCount() != 0 && i2 != 0) {
            a();
            a(i2 > 0 ? 1 : -1, Math.abs(i2), true, state);
            a(state, this.a, layoutPrefetchRegistry);
        }
    }

    /* access modifiers changed from: 0000 */
    public int a(int i2, Recycler recycler, State state) {
        if (getChildCount() == 0 || i2 == 0) {
            return 0;
        }
        this.a.a = true;
        a();
        int i3 = i2 > 0 ? 1 : -1;
        int abs = Math.abs(i2);
        a(i3, abs, true, state);
        int a2 = this.a.g + a(recycler, this.a, state, false);
        if (a2 < 0) {
            return 0;
        }
        if (abs > a2) {
            i2 = i3 * a2;
        }
        this.j.offsetChildren(-i2);
        this.a.j = i2;
        return i2;
    }

    public void assertNotInLayoutOrScroll(String str) {
        if (this.n == null) {
            super.assertNotInLayoutOrScroll(str);
        }
    }

    private void a(Recycler recycler, int i2, int i3) {
        if (i2 != i3) {
            if (i3 > i2) {
                for (int i4 = i3 - 1; i4 >= i2; i4--) {
                    removeAndRecycleViewAt(i4, recycler);
                }
            } else {
                while (i2 > i3) {
                    removeAndRecycleViewAt(i2, recycler);
                    i2--;
                }
            }
        }
    }

    private void a(Recycler recycler, int i2) {
        if (i2 >= 0) {
            int childCount = getChildCount();
            if (this.k) {
                int i3 = childCount - 1;
                for (int i4 = i3; i4 >= 0; i4--) {
                    View childAt = getChildAt(i4);
                    if (this.j.getDecoratedEnd(childAt) > i2 || this.j.getTransformedEndWithDecoration(childAt) > i2) {
                        a(recycler, i3, i4);
                        return;
                    }
                }
            } else {
                for (int i5 = 0; i5 < childCount; i5++) {
                    View childAt2 = getChildAt(i5);
                    if (this.j.getDecoratedEnd(childAt2) > i2 || this.j.getTransformedEndWithDecoration(childAt2) > i2) {
                        a(recycler, 0, i5);
                        return;
                    }
                }
            }
        }
    }

    private void b(Recycler recycler, int i2) {
        int childCount = getChildCount();
        if (i2 >= 0) {
            int end = this.j.getEnd() - i2;
            if (this.k) {
                for (int i3 = 0; i3 < childCount; i3++) {
                    View childAt = getChildAt(i3);
                    if (this.j.getDecoratedStart(childAt) < end || this.j.getTransformedStartWithDecoration(childAt) < end) {
                        a(recycler, 0, i3);
                        return;
                    }
                }
            } else {
                int i4 = childCount - 1;
                for (int i5 = i4; i5 >= 0; i5--) {
                    View childAt2 = getChildAt(i5);
                    if (this.j.getDecoratedStart(childAt2) < end || this.j.getTransformedStartWithDecoration(childAt2) < end) {
                        a(recycler, i4, i5);
                        return;
                    }
                }
            }
        }
    }

    private void a(Recycler recycler, LayoutState layoutState) {
        if (layoutState.a && !layoutState.l) {
            if (layoutState.f == -1) {
                b(recycler, layoutState.g);
            } else {
                a(recycler, layoutState.g);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int a(Recycler recycler, LayoutState layoutState, State state, boolean z) {
        int i2 = layoutState.c;
        if (layoutState.g != Integer.MIN_VALUE) {
            if (layoutState.c < 0) {
                layoutState.g += layoutState.c;
            }
            a(recycler, layoutState);
        }
        int i3 = layoutState.c + layoutState.h;
        LayoutChunkResult layoutChunkResult = this.g;
        while (true) {
            if ((!layoutState.l && i3 <= 0) || !layoutState.a(state)) {
                break;
            }
            layoutChunkResult.a();
            a(recycler, state, layoutState, layoutChunkResult);
            if (!layoutChunkResult.mFinished) {
                layoutState.b += layoutChunkResult.mConsumed * layoutState.f;
                if (!layoutChunkResult.mIgnoreConsumed || this.a.k != null || !state.isPreLayout()) {
                    layoutState.c -= layoutChunkResult.mConsumed;
                    i3 -= layoutChunkResult.mConsumed;
                }
                if (layoutState.g != Integer.MIN_VALUE) {
                    layoutState.g += layoutChunkResult.mConsumed;
                    if (layoutState.c < 0) {
                        layoutState.g += layoutState.c;
                    }
                    a(recycler, layoutState);
                }
                if (z && layoutChunkResult.mFocusable) {
                    break;
                }
            } else {
                break;
            }
        }
        return i2 - layoutState.c;
    }

    /* access modifiers changed from: 0000 */
    public void a(Recycler recycler, State state, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        View a2 = layoutState.a(recycler);
        if (a2 == null) {
            layoutChunkResult.mFinished = true;
            return;
        }
        LayoutParams layoutParams = (LayoutParams) a2.getLayoutParams();
        if (layoutState.k == null) {
            if (this.k == (layoutState.f == -1)) {
                addView(a2);
            } else {
                addView(a2, 0);
            }
        } else {
            if (this.k == (layoutState.f == -1)) {
                addDisappearingView(a2);
            } else {
                addDisappearingView(a2, 0);
            }
        }
        measureChildWithMargins(a2, 0, 0);
        layoutChunkResult.mConsumed = this.j.getDecoratedMeasurement(a2);
        if (this.i == 1) {
            if (isLayoutRTL()) {
                i6 = getWidth() - getPaddingRight();
                i5 = i6 - this.j.getDecoratedMeasurementInOther(a2);
            } else {
                i5 = getPaddingLeft();
                i6 = this.j.getDecoratedMeasurementInOther(a2) + i5;
            }
            if (layoutState.f == -1) {
                i4 = layoutState.b - layoutChunkResult.mConsumed;
                i3 = i6;
                i2 = layoutState.b;
            } else {
                i2 = layoutState.b + layoutChunkResult.mConsumed;
                i3 = i6;
                i4 = layoutState.b;
            }
        } else {
            int paddingTop = getPaddingTop();
            int decoratedMeasurementInOther = this.j.getDecoratedMeasurementInOther(a2) + paddingTop;
            if (layoutState.f == -1) {
                i4 = paddingTop;
                i3 = layoutState.b;
                i2 = decoratedMeasurementInOther;
                i5 = layoutState.b - layoutChunkResult.mConsumed;
            } else {
                i3 = layoutState.b + layoutChunkResult.mConsumed;
                i4 = paddingTop;
                i2 = decoratedMeasurementInOther;
                i5 = layoutState.b;
            }
        }
        layoutDecoratedWithMargins(a2, i5, i4, i3, i2);
        if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
            layoutChunkResult.mIgnoreConsumed = true;
        }
        layoutChunkResult.mFocusable = a2.hasFocusable();
    }

    /* access modifiers changed from: 0000 */
    public boolean d() {
        return (getHeightMode() == 1073741824 || getWidthMode() == 1073741824 || !f()) ? false : true;
    }

    /* access modifiers changed from: 0000 */
    public int a(int i2) {
        int i3 = -1;
        int i4 = Integer.MIN_VALUE;
        if (i2 == 17) {
            if (this.i != 0) {
                i3 = Integer.MIN_VALUE;
            }
            return i3;
        } else if (i2 == 33) {
            if (this.i != 1) {
                i3 = Integer.MIN_VALUE;
            }
            return i3;
        } else if (i2 == 66) {
            if (this.i == 0) {
                i4 = 1;
            }
            return i4;
        } else if (i2 != 130) {
            switch (i2) {
                case 1:
                    return (this.i != 1 && isLayoutRTL()) ? 1 : -1;
                case 2:
                    return (this.i != 1 && isLayoutRTL()) ? -1 : 1;
                default:
                    return Integer.MIN_VALUE;
            }
        } else {
            if (this.i == 1) {
                i4 = 1;
            }
            return i4;
        }
    }

    private View h() {
        return getChildAt(this.k ? getChildCount() - 1 : 0);
    }

    private View i() {
        return getChildAt(this.k ? 0 : getChildCount() - 1);
    }

    private View a(boolean z, boolean z2) {
        if (this.k) {
            return a(getChildCount() - 1, -1, z, z2);
        }
        return a(0, getChildCount(), z, z2);
    }

    private View b(boolean z, boolean z2) {
        if (this.k) {
            return a(0, getChildCount(), z, z2);
        }
        return a(getChildCount() - 1, -1, z, z2);
    }

    private View a(Recycler recycler, State state) {
        if (this.k) {
            return c(recycler, state);
        }
        return d(recycler, state);
    }

    private View b(Recycler recycler, State state) {
        if (this.k) {
            return d(recycler, state);
        }
        return c(recycler, state);
    }

    private View c(Recycler recycler, State state) {
        return a(recycler, state, 0, getChildCount(), state.getItemCount());
    }

    private View d(Recycler recycler, State state) {
        return a(recycler, state, getChildCount() - 1, -1, state.getItemCount());
    }

    /* access modifiers changed from: 0000 */
    public View a(Recycler recycler, State state, int i2, int i3, int i4) {
        a();
        int startAfterPadding = this.j.getStartAfterPadding();
        int endAfterPadding = this.j.getEndAfterPadding();
        int i5 = i3 > i2 ? 1 : -1;
        View view = null;
        View view2 = null;
        while (i2 != i3) {
            View childAt = getChildAt(i2);
            int position = getPosition(childAt);
            if (position >= 0 && position < i4) {
                if (((LayoutParams) childAt.getLayoutParams()).isItemRemoved()) {
                    if (view2 == null) {
                        view2 = childAt;
                    }
                } else if (this.j.getDecoratedStart(childAt) < endAfterPadding && this.j.getDecoratedEnd(childAt) >= startAfterPadding) {
                    return childAt;
                } else {
                    if (view == null) {
                        view = childAt;
                    }
                }
            }
            i2 += i5;
        }
        if (view == null) {
            view = view2;
        }
        return view;
    }

    private View e(Recycler recycler, State state) {
        if (this.k) {
            return g(recycler, state);
        }
        return h(recycler, state);
    }

    private View f(Recycler recycler, State state) {
        if (this.k) {
            return h(recycler, state);
        }
        return g(recycler, state);
    }

    private View g(Recycler recycler, State state) {
        return b(0, getChildCount());
    }

    private View h(Recycler recycler, State state) {
        return b(getChildCount() - 1, -1);
    }

    public int findFirstVisibleItemPosition() {
        View a2 = a(0, getChildCount(), false, true);
        if (a2 == null) {
            return -1;
        }
        return getPosition(a2);
    }

    public int findFirstCompletelyVisibleItemPosition() {
        View a2 = a(0, getChildCount(), true, false);
        if (a2 == null) {
            return -1;
        }
        return getPosition(a2);
    }

    public int findLastVisibleItemPosition() {
        View a2 = a(getChildCount() - 1, -1, false, true);
        if (a2 == null) {
            return -1;
        }
        return getPosition(a2);
    }

    public int findLastCompletelyVisibleItemPosition() {
        View a2 = a(getChildCount() - 1, -1, true, false);
        if (a2 == null) {
            return -1;
        }
        return getPosition(a2);
    }

    /* access modifiers changed from: 0000 */
    public View a(int i2, int i3, boolean z, boolean z2) {
        a();
        int i4 = 320;
        int i5 = z ? 24579 : 320;
        if (!z2) {
            i4 = 0;
        }
        if (this.i == 0) {
            return this.r.a(i2, i3, i5, i4);
        }
        return this.s.a(i2, i3, i5, i4);
    }

    /* access modifiers changed from: 0000 */
    public View b(int i2, int i3) {
        int i4;
        int i5;
        View view;
        a();
        char c2 = i3 > i2 ? 1 : i3 < i2 ? (char) 65535 : 0;
        if (c2 == 0) {
            return getChildAt(i2);
        }
        if (this.j.getDecoratedStart(getChildAt(i2)) < this.j.getStartAfterPadding()) {
            i5 = 16644;
            i4 = 16388;
        } else {
            i5 = 4161;
            i4 = FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
        }
        if (this.i == 0) {
            view = this.r.a(i2, i3, i5, i4);
        } else {
            view = this.s.a(i2, i3, i5, i4);
        }
        return view;
    }

    public View onFocusSearchFailed(View view, int i2, Recycler recycler, State state) {
        View view2;
        View view3;
        g();
        if (getChildCount() == 0) {
            return null;
        }
        int a2 = a(i2);
        if (a2 == Integer.MIN_VALUE) {
            return null;
        }
        a();
        a();
        a(a2, (int) (((float) this.j.getTotalSpace()) * 0.33333334f), false, state);
        this.a.g = Integer.MIN_VALUE;
        this.a.a = false;
        a(recycler, this.a, state, true);
        if (a2 == -1) {
            view2 = f(recycler, state);
        } else {
            view2 = e(recycler, state);
        }
        if (a2 == -1) {
            view3 = h();
        } else {
            view3 = i();
        }
        if (!view3.hasFocusable()) {
            return view2;
        }
        if (view2 == null) {
            return null;
        }
        return view3;
    }

    public boolean supportsPredictiveItemAnimations() {
        return this.n == null && this.b == this.d;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void prepareForDrop(View view, View view2, int i2, int i3) {
        assertNotInLayoutOrScroll("Cannot drop a view during a scroll or layout calculation");
        a();
        g();
        int position = getPosition(view);
        int position2 = getPosition(view2);
        char c2 = position < position2 ? (char) 1 : 65535;
        if (this.k) {
            if (c2 == 1) {
                scrollToPositionWithOffset(position2, this.j.getEndAfterPadding() - (this.j.getDecoratedStart(view2) + this.j.getDecoratedMeasurement(view)));
            } else {
                scrollToPositionWithOffset(position2, this.j.getEndAfterPadding() - this.j.getDecoratedEnd(view2));
            }
        } else if (c2 == 65535) {
            scrollToPositionWithOffset(position2, this.j.getDecoratedStart(view2));
        } else {
            scrollToPositionWithOffset(position2, this.j.getDecoratedEnd(view2) - this.j.getDecoratedMeasurement(view));
        }
    }
}
