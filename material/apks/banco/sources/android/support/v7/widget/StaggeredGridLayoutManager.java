package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry;
import android.support.v7.widget.RecyclerView.LayoutManager.Properties;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.SmoothScroller.ScrollVectorProvider;
import android.support.v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class StaggeredGridLayoutManager extends LayoutManager implements ScrollVectorProvider {
    @Deprecated
    public static final int GAP_HANDLING_LAZY = 1;
    public static final int GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS = 2;
    public static final int GAP_HANDLING_NONE = 0;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private SavedState A;
    private int B;
    private final Rect C = new Rect();
    private final AnchorInfo D = new AnchorInfo();
    private boolean E = false;
    private boolean F = true;
    private int[] G;
    private final Runnable H = new Runnable() {
        public void run() {
            StaggeredGridLayoutManager.this.a();
        }
    };
    Span[] a;
    @NonNull
    OrientationHelper b;
    @NonNull
    OrientationHelper c;
    boolean d = false;
    boolean e = false;
    int f = -1;
    int g = Integer.MIN_VALUE;
    LazySpanLookup h = new LazySpanLookup();
    private int i = -1;
    private int j;
    private int k;
    @NonNull
    private final LayoutState l;
    private BitSet m;
    private int n = 2;
    private boolean o;
    private boolean z;

    class AnchorInfo {
        int a;
        int b;
        boolean c;
        boolean d;
        boolean e;
        int[] f;

        AnchorInfo() {
            a();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.a = -1;
            this.b = Integer.MIN_VALUE;
            this.c = false;
            this.d = false;
            this.e = false;
            if (this.f != null) {
                Arrays.fill(this.f, -1);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Span[] spanArr) {
            int length = spanArr.length;
            if (this.f == null || this.f.length < length) {
                this.f = new int[StaggeredGridLayoutManager.this.a.length];
            }
            for (int i = 0; i < length; i++) {
                this.f[i] = spanArr[i].a(Integer.MIN_VALUE);
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            int i;
            if (this.c) {
                i = StaggeredGridLayoutManager.this.b.getEndAfterPadding();
            } else {
                i = StaggeredGridLayoutManager.this.b.getStartAfterPadding();
            }
            this.b = i;
        }

        /* access modifiers changed from: 0000 */
        public void a(int i) {
            if (this.c) {
                this.b = StaggeredGridLayoutManager.this.b.getEndAfterPadding() - i;
            } else {
                this.b = StaggeredGridLayoutManager.this.b.getStartAfterPadding() + i;
            }
        }
    }

    public static class LayoutParams extends android.support.v7.widget.RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        Span a;
        boolean b;

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

        public LayoutParams(android.support.v7.widget.RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public void setFullSpan(boolean z) {
            this.b = z;
        }

        public boolean isFullSpan() {
            return this.b;
        }

        public final int getSpanIndex() {
            if (this.a == null) {
                return -1;
            }
            return this.a.e;
        }
    }

    static class LazySpanLookup {
        int[] a;
        List<FullSpanItem> b;

        static class FullSpanItem implements Parcelable {
            public static final Creator<FullSpanItem> CREATOR = new Creator<FullSpanItem>() {
                /* renamed from: a */
                public FullSpanItem createFromParcel(Parcel parcel) {
                    return new FullSpanItem(parcel);
                }

                /* renamed from: a */
                public FullSpanItem[] newArray(int i) {
                    return new FullSpanItem[i];
                }
            };
            int a;
            int b;
            int[] c;
            boolean d;

            public int describeContents() {
                return 0;
            }

            FullSpanItem(Parcel parcel) {
                this.a = parcel.readInt();
                this.b = parcel.readInt();
                boolean z = true;
                if (parcel.readInt() != 1) {
                    z = false;
                }
                this.d = z;
                int readInt = parcel.readInt();
                if (readInt > 0) {
                    this.c = new int[readInt];
                    parcel.readIntArray(this.c);
                }
            }

            FullSpanItem() {
            }

            /* access modifiers changed from: 0000 */
            public int a(int i) {
                if (this.c == null) {
                    return 0;
                }
                return this.c[i];
            }

            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.a);
                parcel.writeInt(this.b);
                parcel.writeInt(this.d ? 1 : 0);
                if (this.c == null || this.c.length <= 0) {
                    parcel.writeInt(0);
                    return;
                }
                parcel.writeInt(this.c.length);
                parcel.writeIntArray(this.c);
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("FullSpanItem{mPosition=");
                sb.append(this.a);
                sb.append(", mGapDir=");
                sb.append(this.b);
                sb.append(", mHasUnwantedGapAfter=");
                sb.append(this.d);
                sb.append(", mGapPerSpan=");
                sb.append(Arrays.toString(this.c));
                sb.append('}');
                return sb.toString();
            }
        }

        LazySpanLookup() {
        }

        /* access modifiers changed from: 0000 */
        public int a(int i) {
            if (this.b != null) {
                for (int size = this.b.size() - 1; size >= 0; size--) {
                    if (((FullSpanItem) this.b.get(size)).a >= i) {
                        this.b.remove(size);
                    }
                }
            }
            return b(i);
        }

        /* access modifiers changed from: 0000 */
        public int b(int i) {
            if (this.a == null || i >= this.a.length) {
                return -1;
            }
            int g = g(i);
            if (g == -1) {
                Arrays.fill(this.a, i, this.a.length, -1);
                return this.a.length;
            }
            int i2 = g + 1;
            Arrays.fill(this.a, i, i2, -1);
            return i2;
        }

        /* access modifiers changed from: 0000 */
        public int c(int i) {
            if (this.a == null || i >= this.a.length) {
                return -1;
            }
            return this.a[i];
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, Span span) {
            e(i);
            this.a[i] = span.e;
        }

        /* access modifiers changed from: 0000 */
        public int d(int i) {
            int length = this.a.length;
            while (length <= i) {
                length *= 2;
            }
            return length;
        }

        /* access modifiers changed from: 0000 */
        public void e(int i) {
            if (this.a == null) {
                this.a = new int[(Math.max(i, 10) + 1)];
                Arrays.fill(this.a, -1);
            } else if (i >= this.a.length) {
                int[] iArr = this.a;
                this.a = new int[d(i)];
                System.arraycopy(iArr, 0, this.a, 0, iArr.length);
                Arrays.fill(this.a, iArr.length, this.a.length, -1);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (this.a != null) {
                Arrays.fill(this.a, -1);
            }
            this.b = null;
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, int i2) {
            if (this.a != null && i < this.a.length) {
                int i3 = i + i2;
                e(i3);
                System.arraycopy(this.a, i3, this.a, i, (this.a.length - i) - i2);
                Arrays.fill(this.a, this.a.length - i2, this.a.length, -1);
                c(i, i2);
            }
        }

        private void c(int i, int i2) {
            if (this.b != null) {
                int i3 = i + i2;
                for (int size = this.b.size() - 1; size >= 0; size--) {
                    FullSpanItem fullSpanItem = (FullSpanItem) this.b.get(size);
                    if (fullSpanItem.a >= i) {
                        if (fullSpanItem.a < i3) {
                            this.b.remove(size);
                        } else {
                            fullSpanItem.a -= i2;
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void b(int i, int i2) {
            if (this.a != null && i < this.a.length) {
                int i3 = i + i2;
                e(i3);
                System.arraycopy(this.a, i, this.a, i3, (this.a.length - i) - i2);
                Arrays.fill(this.a, i, i3, -1);
                d(i, i2);
            }
        }

        private void d(int i, int i2) {
            if (this.b != null) {
                for (int size = this.b.size() - 1; size >= 0; size--) {
                    FullSpanItem fullSpanItem = (FullSpanItem) this.b.get(size);
                    if (fullSpanItem.a >= i) {
                        fullSpanItem.a += i2;
                    }
                }
            }
        }

        private int g(int i) {
            if (this.b == null) {
                return -1;
            }
            FullSpanItem f = f(i);
            if (f != null) {
                this.b.remove(f);
            }
            int size = this.b.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    i2 = -1;
                    break;
                } else if (((FullSpanItem) this.b.get(i2)).a >= i) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 == -1) {
                return -1;
            }
            FullSpanItem fullSpanItem = (FullSpanItem) this.b.get(i2);
            this.b.remove(i2);
            return fullSpanItem.a;
        }

        public void a(FullSpanItem fullSpanItem) {
            if (this.b == null) {
                this.b = new ArrayList();
            }
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                FullSpanItem fullSpanItem2 = (FullSpanItem) this.b.get(i);
                if (fullSpanItem2.a == fullSpanItem.a) {
                    this.b.remove(i);
                }
                if (fullSpanItem2.a >= fullSpanItem.a) {
                    this.b.add(i, fullSpanItem);
                    return;
                }
            }
            this.b.add(fullSpanItem);
        }

        public FullSpanItem f(int i) {
            if (this.b == null) {
                return null;
            }
            for (int size = this.b.size() - 1; size >= 0; size--) {
                FullSpanItem fullSpanItem = (FullSpanItem) this.b.get(size);
                if (fullSpanItem.a == i) {
                    return fullSpanItem;
                }
            }
            return null;
        }

        public FullSpanItem a(int i, int i2, int i3, boolean z) {
            if (this.b == null) {
                return null;
            }
            int size = this.b.size();
            for (int i4 = 0; i4 < size; i4++) {
                FullSpanItem fullSpanItem = (FullSpanItem) this.b.get(i4);
                if (fullSpanItem.a >= i2) {
                    return null;
                }
                if (fullSpanItem.a >= i && (i3 == 0 || fullSpanItem.b == i3 || (z && fullSpanItem.d))) {
                    return fullSpanItem;
                }
            }
            return null;
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
        int c;
        int[] d;
        int e;
        int[] f;
        List<FullSpanItem> g;
        boolean h;
        boolean i;
        boolean j;

        public int describeContents() {
            return 0;
        }

        public SavedState() {
        }

        SavedState(Parcel parcel) {
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            this.c = parcel.readInt();
            if (this.c > 0) {
                this.d = new int[this.c];
                parcel.readIntArray(this.d);
            }
            this.e = parcel.readInt();
            if (this.e > 0) {
                this.f = new int[this.e];
                parcel.readIntArray(this.f);
            }
            boolean z = false;
            this.h = parcel.readInt() == 1;
            this.i = parcel.readInt() == 1;
            if (parcel.readInt() == 1) {
                z = true;
            }
            this.j = z;
            this.g = parcel.readArrayList(FullSpanItem.class.getClassLoader());
        }

        public SavedState(SavedState savedState) {
            this.c = savedState.c;
            this.a = savedState.a;
            this.b = savedState.b;
            this.d = savedState.d;
            this.e = savedState.e;
            this.f = savedState.f;
            this.h = savedState.h;
            this.i = savedState.i;
            this.j = savedState.j;
            this.g = savedState.g;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.d = null;
            this.c = 0;
            this.e = 0;
            this.f = null;
            this.g = null;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.d = null;
            this.c = 0;
            this.a = -1;
            this.b = -1;
        }

        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeInt(this.c);
            if (this.c > 0) {
                parcel.writeIntArray(this.d);
            }
            parcel.writeInt(this.e);
            if (this.e > 0) {
                parcel.writeIntArray(this.f);
            }
            parcel.writeInt(this.h ? 1 : 0);
            parcel.writeInt(this.i ? 1 : 0);
            parcel.writeInt(this.j ? 1 : 0);
            parcel.writeList(this.g);
        }
    }

    class Span {
        ArrayList<View> a = new ArrayList<>();
        int b = Integer.MIN_VALUE;
        int c = Integer.MIN_VALUE;
        int d = 0;
        final int e;

        Span(int i) {
            this.e = i;
        }

        /* access modifiers changed from: 0000 */
        public int a(int i) {
            if (this.b != Integer.MIN_VALUE) {
                return this.b;
            }
            if (this.a.size() == 0) {
                return i;
            }
            a();
            return this.b;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            View view = (View) this.a.get(0);
            LayoutParams c2 = c(view);
            this.b = StaggeredGridLayoutManager.this.b.getDecoratedStart(view);
            if (c2.b) {
                FullSpanItem f2 = StaggeredGridLayoutManager.this.h.f(c2.getViewLayoutPosition());
                if (f2 != null && f2.b == -1) {
                    this.b -= f2.a(this.e);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public int b() {
            if (this.b != Integer.MIN_VALUE) {
                return this.b;
            }
            a();
            return this.b;
        }

        /* access modifiers changed from: 0000 */
        public int b(int i) {
            if (this.c != Integer.MIN_VALUE) {
                return this.c;
            }
            if (this.a.size() == 0) {
                return i;
            }
            c();
            return this.c;
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            View view = (View) this.a.get(this.a.size() - 1);
            LayoutParams c2 = c(view);
            this.c = StaggeredGridLayoutManager.this.b.getDecoratedEnd(view);
            if (c2.b) {
                FullSpanItem f2 = StaggeredGridLayoutManager.this.h.f(c2.getViewLayoutPosition());
                if (f2 != null && f2.b == 1) {
                    this.c += f2.a(this.e);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public int d() {
            if (this.c != Integer.MIN_VALUE) {
                return this.c;
            }
            c();
            return this.c;
        }

        /* access modifiers changed from: 0000 */
        public void a(View view) {
            LayoutParams c2 = c(view);
            c2.a = this;
            this.a.add(0, view);
            this.b = Integer.MIN_VALUE;
            if (this.a.size() == 1) {
                this.c = Integer.MIN_VALUE;
            }
            if (c2.isItemRemoved() || c2.isItemChanged()) {
                this.d += StaggeredGridLayoutManager.this.b.getDecoratedMeasurement(view);
            }
        }

        /* access modifiers changed from: 0000 */
        public void b(View view) {
            LayoutParams c2 = c(view);
            c2.a = this;
            this.a.add(view);
            this.c = Integer.MIN_VALUE;
            if (this.a.size() == 1) {
                this.b = Integer.MIN_VALUE;
            }
            if (c2.isItemRemoved() || c2.isItemChanged()) {
                this.d += StaggeredGridLayoutManager.this.b.getDecoratedMeasurement(view);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(boolean z, int i) {
            int i2;
            if (z) {
                i2 = b(Integer.MIN_VALUE);
            } else {
                i2 = a(Integer.MIN_VALUE);
            }
            e();
            if (i2 != Integer.MIN_VALUE) {
                if ((!z || i2 >= StaggeredGridLayoutManager.this.b.getEndAfterPadding()) && (z || i2 <= StaggeredGridLayoutManager.this.b.getStartAfterPadding())) {
                    if (i != Integer.MIN_VALUE) {
                        i2 += i;
                    }
                    this.c = i2;
                    this.b = i2;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void e() {
            this.a.clear();
            f();
            this.d = 0;
        }

        /* access modifiers changed from: 0000 */
        public void f() {
            this.b = Integer.MIN_VALUE;
            this.c = Integer.MIN_VALUE;
        }

        /* access modifiers changed from: 0000 */
        public void c(int i) {
            this.b = i;
            this.c = i;
        }

        /* access modifiers changed from: 0000 */
        public void g() {
            int size = this.a.size();
            View view = (View) this.a.remove(size - 1);
            LayoutParams c2 = c(view);
            c2.a = null;
            if (c2.isItemRemoved() || c2.isItemChanged()) {
                this.d -= StaggeredGridLayoutManager.this.b.getDecoratedMeasurement(view);
            }
            if (size == 1) {
                this.b = Integer.MIN_VALUE;
            }
            this.c = Integer.MIN_VALUE;
        }

        /* access modifiers changed from: 0000 */
        public void h() {
            View view = (View) this.a.remove(0);
            LayoutParams c2 = c(view);
            c2.a = null;
            if (this.a.size() == 0) {
                this.c = Integer.MIN_VALUE;
            }
            if (c2.isItemRemoved() || c2.isItemChanged()) {
                this.d -= StaggeredGridLayoutManager.this.b.getDecoratedMeasurement(view);
            }
            this.b = Integer.MIN_VALUE;
        }

        public int i() {
            return this.d;
        }

        /* access modifiers changed from: 0000 */
        public LayoutParams c(View view) {
            return (LayoutParams) view.getLayoutParams();
        }

        /* access modifiers changed from: 0000 */
        public void d(int i) {
            if (this.b != Integer.MIN_VALUE) {
                this.b += i;
            }
            if (this.c != Integer.MIN_VALUE) {
                this.c += i;
            }
        }

        public int j() {
            if (StaggeredGridLayoutManager.this.d) {
                return a(this.a.size() - 1, -1, false);
            }
            return a(0, this.a.size(), false);
        }

        public int k() {
            if (StaggeredGridLayoutManager.this.d) {
                return b(this.a.size() - 1, -1, true);
            }
            return b(0, this.a.size(), true);
        }

        public int l() {
            if (StaggeredGridLayoutManager.this.d) {
                return a(this.a.size() - 1, -1, true);
            }
            return a(0, this.a.size(), true);
        }

        public int m() {
            if (StaggeredGridLayoutManager.this.d) {
                return a(0, this.a.size(), false);
            }
            return a(this.a.size() - 1, -1, false);
        }

        public int n() {
            if (StaggeredGridLayoutManager.this.d) {
                return b(0, this.a.size(), true);
            }
            return b(this.a.size() - 1, -1, true);
        }

        public int o() {
            if (StaggeredGridLayoutManager.this.d) {
                return a(0, this.a.size(), true);
            }
            return a(this.a.size() - 1, -1, true);
        }

        /* access modifiers changed from: 0000 */
        public int a(int i, int i2, boolean z, boolean z2, boolean z3) {
            int startAfterPadding = StaggeredGridLayoutManager.this.b.getStartAfterPadding();
            int endAfterPadding = StaggeredGridLayoutManager.this.b.getEndAfterPadding();
            int i3 = i2 > i ? 1 : -1;
            while (i != i2) {
                View view = (View) this.a.get(i);
                int decoratedStart = StaggeredGridLayoutManager.this.b.getDecoratedStart(view);
                int decoratedEnd = StaggeredGridLayoutManager.this.b.getDecoratedEnd(view);
                boolean z4 = false;
                boolean z5 = !z3 ? decoratedStart < endAfterPadding : decoratedStart <= endAfterPadding;
                if (!z3 ? decoratedEnd > startAfterPadding : decoratedEnd >= startAfterPadding) {
                    z4 = true;
                }
                if (z5 && z4) {
                    if (!z || !z2) {
                        if (z2) {
                            return StaggeredGridLayoutManager.this.getPosition(view);
                        }
                        if (decoratedStart < startAfterPadding || decoratedEnd > endAfterPadding) {
                            return StaggeredGridLayoutManager.this.getPosition(view);
                        }
                    } else if (decoratedStart >= startAfterPadding && decoratedEnd <= endAfterPadding) {
                        return StaggeredGridLayoutManager.this.getPosition(view);
                    }
                }
                i += i3;
            }
            return -1;
        }

        /* access modifiers changed from: 0000 */
        public int a(int i, int i2, boolean z) {
            return a(i, i2, z, true, false);
        }

        /* access modifiers changed from: 0000 */
        public int b(int i, int i2, boolean z) {
            return a(i, i2, false, false, z);
        }

        public View a(int i, int i2) {
            View view = null;
            if (i2 != -1) {
                int size = this.a.size() - 1;
                while (size >= 0) {
                    View view2 = (View) this.a.get(size);
                    if ((StaggeredGridLayoutManager.this.d && StaggeredGridLayoutManager.this.getPosition(view2) >= i) || ((!StaggeredGridLayoutManager.this.d && StaggeredGridLayoutManager.this.getPosition(view2) <= i) || !view2.hasFocusable())) {
                        break;
                    }
                    size--;
                    view = view2;
                }
            } else {
                int size2 = this.a.size();
                int i3 = 0;
                while (i3 < size2) {
                    View view3 = (View) this.a.get(i3);
                    if ((StaggeredGridLayoutManager.this.d && StaggeredGridLayoutManager.this.getPosition(view3) <= i) || ((!StaggeredGridLayoutManager.this.d && StaggeredGridLayoutManager.this.getPosition(view3) >= i) || !view3.hasFocusable())) {
                        break;
                    }
                    i3++;
                    view = view3;
                }
            }
            return view;
        }
    }

    public StaggeredGridLayoutManager(Context context, AttributeSet attributeSet, int i2, int i3) {
        Properties properties = getProperties(context, attributeSet, i2, i3);
        setOrientation(properties.orientation);
        setSpanCount(properties.spanCount);
        setReverseLayout(properties.reverseLayout);
        this.l = new LayoutState();
        l();
    }

    public StaggeredGridLayoutManager(int i2, int i3) {
        this.j = i3;
        setSpanCount(i2);
        this.l = new LayoutState();
        l();
    }

    public boolean isAutoMeasureEnabled() {
        return this.n != 0;
    }

    private void l() {
        this.b = OrientationHelper.createOrientationHelper(this, this.j);
        this.c = OrientationHelper.createOrientationHelper(this, 1 - this.j);
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        int i2;
        int i3;
        if (getChildCount() == 0 || this.n == 0 || !isAttachedToWindow()) {
            return false;
        }
        if (this.e) {
            i3 = j();
            i2 = k();
        } else {
            i3 = k();
            i2 = j();
        }
        if (i3 == 0 && b() != null) {
            this.h.a();
            requestSimpleAnimationsInNextLayout();
            requestLayout();
            return true;
        } else if (!this.E) {
            return false;
        } else {
            int i4 = this.e ? -1 : 1;
            int i5 = i2 + 1;
            FullSpanItem a2 = this.h.a(i3, i5, i4, true);
            if (a2 == null) {
                this.E = false;
                this.h.a(i5);
                return false;
            }
            FullSpanItem a3 = this.h.a(i3, a2.a, i4 * -1, true);
            if (a3 == null) {
                this.h.a(a2.a);
            } else {
                this.h.a(a3.a + 1);
            }
            requestSimpleAnimationsInNextLayout();
            requestLayout();
            return true;
        }
    }

    public void onScrollStateChanged(int i2) {
        if (i2 == 0) {
            a();
        }
    }

    public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
        removeCallbacks(this.H);
        for (int i2 = 0; i2 < this.i; i2++) {
            this.a[i2].e();
        }
        recyclerView.requestLayout();
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0074, code lost:
        if (r10 == r11) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0086, code lost:
        if (r10 == r11) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x008a, code lost:
        r10 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View b() {
        /*
            r12 = this;
            int r0 = r12.getChildCount()
            r1 = 1
            int r0 = r0 - r1
            java.util.BitSet r2 = new java.util.BitSet
            int r3 = r12.i
            r2.<init>(r3)
            int r3 = r12.i
            r4 = 0
            r2.set(r4, r3, r1)
            int r3 = r12.j
            r5 = -1
            if (r3 != r1) goto L_0x0020
            boolean r3 = r12.c()
            if (r3 == 0) goto L_0x0020
            r3 = 1
            goto L_0x0021
        L_0x0020:
            r3 = -1
        L_0x0021:
            boolean r6 = r12.e
            if (r6 == 0) goto L_0x0027
            r6 = -1
            goto L_0x002b
        L_0x0027:
            int r0 = r0 + 1
            r6 = r0
            r0 = 0
        L_0x002b:
            if (r0 >= r6) goto L_0x002e
            r5 = 1
        L_0x002e:
            if (r0 == r6) goto L_0x00ab
            android.view.View r7 = r12.getChildAt(r0)
            android.view.ViewGroup$LayoutParams r8 = r7.getLayoutParams()
            android.support.v7.widget.StaggeredGridLayoutManager$LayoutParams r8 = (android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams) r8
            android.support.v7.widget.StaggeredGridLayoutManager$Span r9 = r8.a
            int r9 = r9.e
            boolean r9 = r2.get(r9)
            if (r9 == 0) goto L_0x0054
            android.support.v7.widget.StaggeredGridLayoutManager$Span r9 = r8.a
            boolean r9 = r12.a(r9)
            if (r9 == 0) goto L_0x004d
            return r7
        L_0x004d:
            android.support.v7.widget.StaggeredGridLayoutManager$Span r9 = r8.a
            int r9 = r9.e
            r2.clear(r9)
        L_0x0054:
            boolean r9 = r8.b
            if (r9 == 0) goto L_0x0059
            goto L_0x00a9
        L_0x0059:
            int r9 = r0 + r5
            if (r9 == r6) goto L_0x00a9
            android.view.View r9 = r12.getChildAt(r9)
            boolean r10 = r12.e
            if (r10 == 0) goto L_0x0077
            android.support.v7.widget.OrientationHelper r10 = r12.b
            int r10 = r10.getDecoratedEnd(r7)
            android.support.v7.widget.OrientationHelper r11 = r12.b
            int r11 = r11.getDecoratedEnd(r9)
            if (r10 >= r11) goto L_0x0074
            return r7
        L_0x0074:
            if (r10 != r11) goto L_0x008a
            goto L_0x0088
        L_0x0077:
            android.support.v7.widget.OrientationHelper r10 = r12.b
            int r10 = r10.getDecoratedStart(r7)
            android.support.v7.widget.OrientationHelper r11 = r12.b
            int r11 = r11.getDecoratedStart(r9)
            if (r10 <= r11) goto L_0x0086
            return r7
        L_0x0086:
            if (r10 != r11) goto L_0x008a
        L_0x0088:
            r10 = 1
            goto L_0x008b
        L_0x008a:
            r10 = 0
        L_0x008b:
            if (r10 == 0) goto L_0x00a9
            android.view.ViewGroup$LayoutParams r9 = r9.getLayoutParams()
            android.support.v7.widget.StaggeredGridLayoutManager$LayoutParams r9 = (android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams) r9
            android.support.v7.widget.StaggeredGridLayoutManager$Span r8 = r8.a
            int r8 = r8.e
            android.support.v7.widget.StaggeredGridLayoutManager$Span r9 = r9.a
            int r9 = r9.e
            int r8 = r8 - r9
            if (r8 >= 0) goto L_0x00a0
            r8 = 1
            goto L_0x00a1
        L_0x00a0:
            r8 = 0
        L_0x00a1:
            if (r3 >= 0) goto L_0x00a5
            r9 = 1
            goto L_0x00a6
        L_0x00a5:
            r9 = 0
        L_0x00a6:
            if (r8 == r9) goto L_0x00a9
            return r7
        L_0x00a9:
            int r0 = r0 + r5
            goto L_0x002e
        L_0x00ab:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.b():android.view.View");
    }

    private boolean a(Span span) {
        if (this.e) {
            if (span.d() < this.b.getEndAfterPadding()) {
                return !span.c((View) span.a.get(span.a.size() - 1)).b;
            }
        } else if (span.b() > this.b.getStartAfterPadding()) {
            return !span.c((View) span.a.get(0)).b;
        }
        return false;
    }

    public void setSpanCount(int i2) {
        assertNotInLayoutOrScroll(null);
        if (i2 != this.i) {
            invalidateSpanAssignments();
            this.i = i2;
            this.m = new BitSet(this.i);
            this.a = new Span[this.i];
            for (int i3 = 0; i3 < this.i; i3++) {
                this.a[i3] = new Span(i3);
            }
            requestLayout();
        }
    }

    public void setOrientation(int i2) {
        if (i2 == 0 || i2 == 1) {
            assertNotInLayoutOrScroll(null);
            if (i2 != this.j) {
                this.j = i2;
                OrientationHelper orientationHelper = this.b;
                this.b = this.c;
                this.c = orientationHelper;
                requestLayout();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation.");
    }

    public void setReverseLayout(boolean z2) {
        assertNotInLayoutOrScroll(null);
        if (!(this.A == null || this.A.h == z2)) {
            this.A.h = z2;
        }
        this.d = z2;
        requestLayout();
    }

    public int getGapStrategy() {
        return this.n;
    }

    public void setGapStrategy(int i2) {
        assertNotInLayoutOrScroll(null);
        if (i2 != this.n) {
            if (i2 == 0 || i2 == 2) {
                this.n = i2;
                requestLayout();
                return;
            }
            throw new IllegalArgumentException("invalid gap strategy. Must be GAP_HANDLING_NONE or GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS");
        }
    }

    public void assertNotInLayoutOrScroll(String str) {
        if (this.A == null) {
            super.assertNotInLayoutOrScroll(str);
        }
    }

    public int getSpanCount() {
        return this.i;
    }

    public void invalidateSpanAssignments() {
        this.h.a();
        requestLayout();
    }

    private void m() {
        if (this.j == 1 || !c()) {
            this.e = this.d;
        } else {
            this.e = !this.d;
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean c() {
        return getLayoutDirection() == 1;
    }

    public boolean getReverseLayout() {
        return this.d;
    }

    public void setMeasuredDimension(Rect rect, int i2, int i3) {
        int i4;
        int i5;
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (this.j == 1) {
            i5 = chooseSize(i3, rect.height() + paddingTop, getMinimumHeight());
            i4 = chooseSize(i2, (this.k * this.i) + paddingLeft, getMinimumWidth());
        } else {
            i4 = chooseSize(i2, rect.width() + paddingLeft, getMinimumWidth());
            i5 = chooseSize(i3, (this.k * this.i) + paddingTop, getMinimumHeight());
        }
        setMeasuredDimension(i4, i5);
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        a(recycler, state, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0163, code lost:
        if (a() != false) goto L_0x0167;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(android.support.v7.widget.RecyclerView.Recycler r9, android.support.v7.widget.RecyclerView.State r10, boolean r11) {
        /*
            r8 = this;
            android.support.v7.widget.StaggeredGridLayoutManager$AnchorInfo r0 = r8.D
            android.support.v7.widget.StaggeredGridLayoutManager$SavedState r1 = r8.A
            r2 = -1
            if (r1 != 0) goto L_0x000b
            int r1 = r8.f
            if (r1 == r2) goto L_0x0018
        L_0x000b:
            int r1 = r10.getItemCount()
            if (r1 != 0) goto L_0x0018
            r8.removeAndRecycleAllViews(r9)
            r0.a()
            return
        L_0x0018:
            boolean r1 = r0.e
            r3 = 0
            r4 = 1
            if (r1 == 0) goto L_0x0029
            int r1 = r8.f
            if (r1 != r2) goto L_0x0029
            android.support.v7.widget.StaggeredGridLayoutManager$SavedState r1 = r8.A
            if (r1 == 0) goto L_0x0027
            goto L_0x0029
        L_0x0027:
            r1 = 0
            goto L_0x002a
        L_0x0029:
            r1 = 1
        L_0x002a:
            if (r1 == 0) goto L_0x0043
            r0.a()
            android.support.v7.widget.StaggeredGridLayoutManager$SavedState r5 = r8.A
            if (r5 == 0) goto L_0x0037
            r8.a(r0)
            goto L_0x003e
        L_0x0037:
            r8.m()
            boolean r5 = r8.e
            r0.c = r5
        L_0x003e:
            r8.a(r10, r0)
            r0.e = r4
        L_0x0043:
            android.support.v7.widget.StaggeredGridLayoutManager$SavedState r5 = r8.A
            if (r5 != 0) goto L_0x0060
            int r5 = r8.f
            if (r5 != r2) goto L_0x0060
            boolean r5 = r0.c
            boolean r6 = r8.o
            if (r5 != r6) goto L_0x0059
            boolean r5 = r8.c()
            boolean r6 = r8.z
            if (r5 == r6) goto L_0x0060
        L_0x0059:
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r5 = r8.h
            r5.a()
            r0.d = r4
        L_0x0060:
            int r5 = r8.getChildCount()
            if (r5 <= 0) goto L_0x00cd
            android.support.v7.widget.StaggeredGridLayoutManager$SavedState r5 = r8.A
            if (r5 == 0) goto L_0x0070
            android.support.v7.widget.StaggeredGridLayoutManager$SavedState r5 = r8.A
            int r5 = r5.c
            if (r5 >= r4) goto L_0x00cd
        L_0x0070:
            boolean r5 = r0.d
            if (r5 == 0) goto L_0x0092
            r1 = 0
        L_0x0075:
            int r5 = r8.i
            if (r1 >= r5) goto L_0x00cd
            android.support.v7.widget.StaggeredGridLayoutManager$Span[] r5 = r8.a
            r5 = r5[r1]
            r5.e()
            int r5 = r0.b
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r5 == r6) goto L_0x008f
            android.support.v7.widget.StaggeredGridLayoutManager$Span[] r5 = r8.a
            r5 = r5[r1]
            int r6 = r0.b
            r5.c(r6)
        L_0x008f:
            int r1 = r1 + 1
            goto L_0x0075
        L_0x0092:
            if (r1 != 0) goto L_0x00b3
            android.support.v7.widget.StaggeredGridLayoutManager$AnchorInfo r1 = r8.D
            int[] r1 = r1.f
            if (r1 != 0) goto L_0x009b
            goto L_0x00b3
        L_0x009b:
            r1 = 0
        L_0x009c:
            int r5 = r8.i
            if (r1 >= r5) goto L_0x00cd
            android.support.v7.widget.StaggeredGridLayoutManager$Span[] r5 = r8.a
            r5 = r5[r1]
            r5.e()
            android.support.v7.widget.StaggeredGridLayoutManager$AnchorInfo r6 = r8.D
            int[] r6 = r6.f
            r6 = r6[r1]
            r5.c(r6)
            int r1 = r1 + 1
            goto L_0x009c
        L_0x00b3:
            r1 = 0
        L_0x00b4:
            int r5 = r8.i
            if (r1 >= r5) goto L_0x00c6
            android.support.v7.widget.StaggeredGridLayoutManager$Span[] r5 = r8.a
            r5 = r5[r1]
            boolean r6 = r8.e
            int r7 = r0.b
            r5.a(r6, r7)
            int r1 = r1 + 1
            goto L_0x00b4
        L_0x00c6:
            android.support.v7.widget.StaggeredGridLayoutManager$AnchorInfo r1 = r8.D
            android.support.v7.widget.StaggeredGridLayoutManager$Span[] r5 = r8.a
            r1.a(r5)
        L_0x00cd:
            r8.detachAndScrapAttachedViews(r9)
            android.support.v7.widget.LayoutState r1 = r8.l
            r1.a = r3
            r8.E = r3
            android.support.v7.widget.OrientationHelper r1 = r8.c
            int r1 = r1.getTotalSpace()
            r8.a(r1)
            int r1 = r0.a
            r8.b(r1, r10)
            boolean r1 = r0.c
            if (r1 == 0) goto L_0x0104
            r8.b(r2)
            android.support.v7.widget.LayoutState r1 = r8.l
            r8.a(r9, r1, r10)
            r8.b(r4)
            android.support.v7.widget.LayoutState r1 = r8.l
            int r2 = r0.a
            android.support.v7.widget.LayoutState r5 = r8.l
            int r5 = r5.d
            int r2 = r2 + r5
            r1.c = r2
            android.support.v7.widget.LayoutState r1 = r8.l
            r8.a(r9, r1, r10)
            goto L_0x011f
        L_0x0104:
            r8.b(r4)
            android.support.v7.widget.LayoutState r1 = r8.l
            r8.a(r9, r1, r10)
            r8.b(r2)
            android.support.v7.widget.LayoutState r1 = r8.l
            int r2 = r0.a
            android.support.v7.widget.LayoutState r5 = r8.l
            int r5 = r5.d
            int r2 = r2 + r5
            r1.c = r2
            android.support.v7.widget.LayoutState r1 = r8.l
            r8.a(r9, r1, r10)
        L_0x011f:
            r8.n()
            int r1 = r8.getChildCount()
            if (r1 <= 0) goto L_0x0139
            boolean r1 = r8.e
            if (r1 == 0) goto L_0x0133
            r8.b(r9, r10, r4)
            r8.c(r9, r10, r3)
            goto L_0x0139
        L_0x0133:
            r8.c(r9, r10, r4)
            r8.b(r9, r10, r3)
        L_0x0139:
            if (r11 == 0) goto L_0x0166
            boolean r11 = r10.isPreLayout()
            if (r11 != 0) goto L_0x0166
            int r11 = r8.n
            if (r11 == 0) goto L_0x0157
            int r11 = r8.getChildCount()
            if (r11 <= 0) goto L_0x0157
            boolean r11 = r8.E
            if (r11 != 0) goto L_0x0155
            android.view.View r11 = r8.b()
            if (r11 == 0) goto L_0x0157
        L_0x0155:
            r11 = 1
            goto L_0x0158
        L_0x0157:
            r11 = 0
        L_0x0158:
            if (r11 == 0) goto L_0x0166
            java.lang.Runnable r11 = r8.H
            r8.removeCallbacks(r11)
            boolean r11 = r8.a()
            if (r11 == 0) goto L_0x0166
            goto L_0x0167
        L_0x0166:
            r4 = 0
        L_0x0167:
            boolean r11 = r10.isPreLayout()
            if (r11 == 0) goto L_0x0172
            android.support.v7.widget.StaggeredGridLayoutManager$AnchorInfo r11 = r8.D
            r11.a()
        L_0x0172:
            boolean r11 = r0.c
            r8.o = r11
            boolean r11 = r8.c()
            r8.z = r11
            if (r4 == 0) goto L_0x0186
            android.support.v7.widget.StaggeredGridLayoutManager$AnchorInfo r11 = r8.D
            r11.a()
            r8.a(r9, r10, r3)
        L_0x0186:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.a(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State, boolean):void");
    }

    public void onLayoutCompleted(State state) {
        super.onLayoutCompleted(state);
        this.f = -1;
        this.g = Integer.MIN_VALUE;
        this.A = null;
        this.D.a();
    }

    private void n() {
        if (this.c.getMode() != 1073741824) {
            int childCount = getChildCount();
            float f2 = BitmapDescriptorFactory.HUE_RED;
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                float decoratedMeasurement = (float) this.c.getDecoratedMeasurement(childAt);
                if (decoratedMeasurement >= f2) {
                    if (((LayoutParams) childAt.getLayoutParams()).isFullSpan()) {
                        decoratedMeasurement = (decoratedMeasurement * 1.0f) / ((float) this.i);
                    }
                    f2 = Math.max(f2, decoratedMeasurement);
                }
            }
            int i3 = this.k;
            int round = Math.round(f2 * ((float) this.i));
            if (this.c.getMode() == Integer.MIN_VALUE) {
                round = Math.min(round, this.c.getTotalSpace());
            }
            a(round);
            if (this.k != i3) {
                for (int i4 = 0; i4 < childCount; i4++) {
                    View childAt2 = getChildAt(i4);
                    LayoutParams layoutParams = (LayoutParams) childAt2.getLayoutParams();
                    if (!layoutParams.b) {
                        if (!c() || this.j != 1) {
                            int i5 = layoutParams.a.e * this.k;
                            int i6 = layoutParams.a.e * i3;
                            if (this.j == 1) {
                                childAt2.offsetLeftAndRight(i5 - i6);
                            } else {
                                childAt2.offsetTopAndBottom(i5 - i6);
                            }
                        } else {
                            childAt2.offsetLeftAndRight(((-((this.i - 1) - layoutParams.a.e)) * this.k) - ((-((this.i - 1) - layoutParams.a.e)) * i3));
                        }
                    }
                }
            }
        }
    }

    private void a(AnchorInfo anchorInfo) {
        if (this.A.c > 0) {
            if (this.A.c == this.i) {
                for (int i2 = 0; i2 < this.i; i2++) {
                    this.a[i2].e();
                    int i3 = this.A.d[i2];
                    if (i3 != Integer.MIN_VALUE) {
                        if (this.A.i) {
                            i3 += this.b.getEndAfterPadding();
                        } else {
                            i3 += this.b.getStartAfterPadding();
                        }
                    }
                    this.a[i2].c(i3);
                }
            } else {
                this.A.a();
                this.A.a = this.A.b;
            }
        }
        this.z = this.A.j;
        setReverseLayout(this.A.h);
        m();
        if (this.A.a != -1) {
            this.f = this.A.a;
            anchorInfo.c = this.A.i;
        } else {
            anchorInfo.c = this.e;
        }
        if (this.A.e > 1) {
            this.h.a = this.A.f;
            this.h.b = this.A.g;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(State state, AnchorInfo anchorInfo) {
        if (!b(state, anchorInfo) && !c(state, anchorInfo)) {
            anchorInfo.b();
            anchorInfo.a = 0;
        }
    }

    private boolean c(State state, AnchorInfo anchorInfo) {
        int i2;
        if (this.o) {
            i2 = l(state.getItemCount());
        } else {
            i2 = k(state.getItemCount());
        }
        anchorInfo.a = i2;
        anchorInfo.b = Integer.MIN_VALUE;
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean b(State state, AnchorInfo anchorInfo) {
        int i2;
        int i3;
        boolean z2 = false;
        if (state.isPreLayout() || this.f == -1) {
            return false;
        }
        if (this.f < 0 || this.f >= state.getItemCount()) {
            this.f = -1;
            this.g = Integer.MIN_VALUE;
            return false;
        }
        if (this.A == null || this.A.a == -1 || this.A.c < 1) {
            View findViewByPosition = findViewByPosition(this.f);
            if (findViewByPosition != null) {
                if (this.e) {
                    i2 = j();
                } else {
                    i2 = k();
                }
                anchorInfo.a = i2;
                if (this.g != Integer.MIN_VALUE) {
                    if (anchorInfo.c) {
                        anchorInfo.b = (this.b.getEndAfterPadding() - this.g) - this.b.getDecoratedEnd(findViewByPosition);
                    } else {
                        anchorInfo.b = (this.b.getStartAfterPadding() + this.g) - this.b.getDecoratedStart(findViewByPosition);
                    }
                    return true;
                } else if (this.b.getDecoratedMeasurement(findViewByPosition) > this.b.getTotalSpace()) {
                    if (anchorInfo.c) {
                        i3 = this.b.getEndAfterPadding();
                    } else {
                        i3 = this.b.getStartAfterPadding();
                    }
                    anchorInfo.b = i3;
                    return true;
                } else {
                    int decoratedStart = this.b.getDecoratedStart(findViewByPosition) - this.b.getStartAfterPadding();
                    if (decoratedStart < 0) {
                        anchorInfo.b = -decoratedStart;
                        return true;
                    }
                    int endAfterPadding = this.b.getEndAfterPadding() - this.b.getDecoratedEnd(findViewByPosition);
                    if (endAfterPadding < 0) {
                        anchorInfo.b = endAfterPadding;
                        return true;
                    }
                    anchorInfo.b = Integer.MIN_VALUE;
                }
            } else {
                anchorInfo.a = this.f;
                if (this.g == Integer.MIN_VALUE) {
                    if (j(anchorInfo.a) == 1) {
                        z2 = true;
                    }
                    anchorInfo.c = z2;
                    anchorInfo.b();
                } else {
                    anchorInfo.a(this.g);
                }
                anchorInfo.d = true;
            }
        } else {
            anchorInfo.b = Integer.MIN_VALUE;
            anchorInfo.a = this.f;
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        this.k = i2 / this.i;
        this.B = MeasureSpec.makeMeasureSpec(i2, this.c.getMode());
    }

    public boolean supportsPredictiveItemAnimations() {
        return this.A == null;
    }

    public int[] findFirstVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.i];
        } else if (iArr.length < this.i) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided int[]'s size must be more than or equal to span count. Expected:");
            sb.append(this.i);
            sb.append(", array size:");
            sb.append(iArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
        for (int i2 = 0; i2 < this.i; i2++) {
            iArr[i2] = this.a[i2].j();
        }
        return iArr;
    }

    public int[] findFirstCompletelyVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.i];
        } else if (iArr.length < this.i) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided int[]'s size must be more than or equal to span count. Expected:");
            sb.append(this.i);
            sb.append(", array size:");
            sb.append(iArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
        for (int i2 = 0; i2 < this.i; i2++) {
            iArr[i2] = this.a[i2].l();
        }
        return iArr;
    }

    public int[] findLastVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.i];
        } else if (iArr.length < this.i) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided int[]'s size must be more than or equal to span count. Expected:");
            sb.append(this.i);
            sb.append(", array size:");
            sb.append(iArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
        for (int i2 = 0; i2 < this.i; i2++) {
            iArr[i2] = this.a[i2].m();
        }
        return iArr;
    }

    public int[] findLastCompletelyVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.i];
        } else if (iArr.length < this.i) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided int[]'s size must be more than or equal to span count. Expected:");
            sb.append(this.i);
            sb.append(", array size:");
            sb.append(iArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
        for (int i2 = 0; i2 < this.i; i2++) {
            iArr[i2] = this.a[i2].o();
        }
        return iArr;
    }

    public int computeHorizontalScrollOffset(State state) {
        return a(state);
    }

    private int a(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.a(state, this.b, a(!this.F), b(!this.F), this, this.F, this.e);
    }

    public int computeVerticalScrollOffset(State state) {
        return a(state);
    }

    public int computeHorizontalScrollExtent(State state) {
        return b(state);
    }

    private int b(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.a(state, this.b, a(!this.F), b(!this.F), this, this.F);
    }

    public int computeVerticalScrollExtent(State state) {
        return b(state);
    }

    public int computeHorizontalScrollRange(State state) {
        return c(state);
    }

    private int c(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.b(state, this.b, a(!this.F), b(!this.F), this, this.F);
    }

    public int computeVerticalScrollRange(State state) {
        return c(state);
    }

    private void a(View view, LayoutParams layoutParams, boolean z2) {
        if (layoutParams.b) {
            if (this.j == 1) {
                a(view, this.B, getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom(), layoutParams.height, true), z2);
            } else {
                a(view, getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight(), layoutParams.width, true), this.B, z2);
            }
        } else if (this.j == 1) {
            a(view, getChildMeasureSpec(this.k, getWidthMode(), 0, layoutParams.width, false), getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom(), layoutParams.height, true), z2);
        } else {
            a(view, getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight(), layoutParams.width, true), getChildMeasureSpec(this.k, getHeightMode(), 0, layoutParams.height, false), z2);
        }
    }

    private void a(View view, int i2, int i3, boolean z2) {
        boolean z3;
        calculateItemDecorationsForChild(view, this.C);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int a2 = a(i2, layoutParams.leftMargin + this.C.left, layoutParams.rightMargin + this.C.right);
        int a3 = a(i3, layoutParams.topMargin + this.C.top, layoutParams.bottomMargin + this.C.bottom);
        if (z2) {
            z3 = a(view, a2, a3, (android.support.v7.widget.RecyclerView.LayoutParams) layoutParams);
        } else {
            z3 = b(view, a2, a3, layoutParams);
        }
        if (z3) {
            view.measure(a2, a3);
        }
    }

    private int a(int i2, int i3, int i4) {
        if (i3 == 0 && i4 == 0) {
            return i2;
        }
        int mode = MeasureSpec.getMode(i2);
        if (mode == Integer.MIN_VALUE || mode == 1073741824) {
            return MeasureSpec.makeMeasureSpec(Math.max(0, (MeasureSpec.getSize(i2) - i3) - i4), mode);
        }
        return i2;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.A = (SavedState) parcelable;
            requestLayout();
        }
    }

    public Parcelable onSaveInstanceState() {
        int i2;
        int i3;
        if (this.A != null) {
            return new SavedState(this.A);
        }
        SavedState savedState = new SavedState();
        savedState.h = this.d;
        savedState.i = this.o;
        savedState.j = this.z;
        if (this.h == null || this.h.a == null) {
            savedState.e = 0;
        } else {
            savedState.f = this.h.a;
            savedState.e = savedState.f.length;
            savedState.g = this.h.b;
        }
        if (getChildCount() > 0) {
            if (this.o) {
                i2 = j();
            } else {
                i2 = k();
            }
            savedState.a = i2;
            savedState.b = g();
            savedState.c = this.i;
            savedState.d = new int[this.i];
            for (int i4 = 0; i4 < this.i; i4++) {
                if (this.o) {
                    i3 = this.a[i4].b(Integer.MIN_VALUE);
                    if (i3 != Integer.MIN_VALUE) {
                        i3 -= this.b.getEndAfterPadding();
                    }
                } else {
                    i3 = this.a[i4].a(Integer.MIN_VALUE);
                    if (i3 != Integer.MIN_VALUE) {
                        i3 -= this.b.getStartAfterPadding();
                    }
                }
                savedState.d[i4] = i3;
            }
        } else {
            savedState.a = -1;
            savedState.b = -1;
            savedState.c = 0;
        }
        return savedState;
    }

    public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.a(view, accessibilityNodeInfoCompat);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        if (this.j == 0) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(CollectionItemInfoCompat.obtain(layoutParams2.getSpanIndex(), layoutParams2.b ? this.i : 1, -1, -1, layoutParams2.b, false));
        } else {
            accessibilityNodeInfoCompat.setCollectionItemInfo(CollectionItemInfoCompat.obtain(-1, -1, layoutParams2.getSpanIndex(), layoutParams2.b ? this.i : 1, layoutParams2.b, false));
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            View a2 = a(false);
            View b2 = b(false);
            if (a2 != null && b2 != null) {
                int position = getPosition(a2);
                int position2 = getPosition(b2);
                if (position < position2) {
                    accessibilityEvent.setFromIndex(position);
                    accessibilityEvent.setToIndex(position2);
                } else {
                    accessibilityEvent.setFromIndex(position2);
                    accessibilityEvent.setToIndex(position);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int g() {
        View view;
        if (this.e) {
            view = b(true);
        } else {
            view = a(true);
        }
        if (view == null) {
            return -1;
        }
        return getPosition(view);
    }

    public int getRowCountForAccessibility(Recycler recycler, State state) {
        if (this.j == 0) {
            return this.i;
        }
        return super.getRowCountForAccessibility(recycler, state);
    }

    public int getColumnCountForAccessibility(Recycler recycler, State state) {
        if (this.j == 1) {
            return this.i;
        }
        return super.getColumnCountForAccessibility(recycler, state);
    }

    /* access modifiers changed from: 0000 */
    public View a(boolean z2) {
        int startAfterPadding = this.b.getStartAfterPadding();
        int endAfterPadding = this.b.getEndAfterPadding();
        int childCount = getChildCount();
        View view = null;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            int decoratedStart = this.b.getDecoratedStart(childAt);
            if (this.b.getDecoratedEnd(childAt) > startAfterPadding && decoratedStart < endAfterPadding) {
                if (decoratedStart >= startAfterPadding || !z2) {
                    return childAt;
                }
                if (view == null) {
                    view = childAt;
                }
            }
        }
        return view;
    }

    /* access modifiers changed from: 0000 */
    public View b(boolean z2) {
        int startAfterPadding = this.b.getStartAfterPadding();
        int endAfterPadding = this.b.getEndAfterPadding();
        View view = null;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            int decoratedStart = this.b.getDecoratedStart(childAt);
            int decoratedEnd = this.b.getDecoratedEnd(childAt);
            if (decoratedEnd > startAfterPadding && decoratedStart < endAfterPadding) {
                if (decoratedEnd <= endAfterPadding || !z2) {
                    return childAt;
                }
                if (view == null) {
                    view = childAt;
                }
            }
        }
        return view;
    }

    private void b(Recycler recycler, State state, boolean z2) {
        int g2 = g(Integer.MIN_VALUE);
        if (g2 != Integer.MIN_VALUE) {
            int endAfterPadding = this.b.getEndAfterPadding() - g2;
            if (endAfterPadding > 0) {
                int i2 = endAfterPadding - (-a(-endAfterPadding, recycler, state));
                if (z2 && i2 > 0) {
                    this.b.offsetChildren(i2);
                }
            }
        }
    }

    private void c(Recycler recycler, State state, boolean z2) {
        int f2 = f(SubsamplingScaleImageView.TILE_SIZE_AUTO);
        if (f2 != Integer.MAX_VALUE) {
            int startAfterPadding = f2 - this.b.getStartAfterPadding();
            if (startAfterPadding > 0) {
                int a2 = startAfterPadding - a(startAfterPadding, recycler, state);
                if (z2 && a2 > 0) {
                    this.b.offsetChildren(-a2);
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(int r5, android.support.v7.widget.RecyclerView.State r6) {
        /*
            r4 = this;
            android.support.v7.widget.LayoutState r0 = r4.l
            r1 = 0
            r0.b = r1
            android.support.v7.widget.LayoutState r0 = r4.l
            r0.c = r5
            boolean r0 = r4.isSmoothScrolling()
            r2 = 1
            if (r0 == 0) goto L_0x0030
            int r6 = r6.getTargetScrollPosition()
            r0 = -1
            if (r6 == r0) goto L_0x0030
            boolean r0 = r4.e
            if (r6 >= r5) goto L_0x001d
            r5 = 1
            goto L_0x001e
        L_0x001d:
            r5 = 0
        L_0x001e:
            if (r0 != r5) goto L_0x0029
            android.support.v7.widget.OrientationHelper r5 = r4.b
            int r5 = r5.getTotalSpace()
            r6 = r5
            r5 = 0
            goto L_0x0032
        L_0x0029:
            android.support.v7.widget.OrientationHelper r5 = r4.b
            int r5 = r5.getTotalSpace()
            goto L_0x0031
        L_0x0030:
            r5 = 0
        L_0x0031:
            r6 = 0
        L_0x0032:
            boolean r0 = r4.getClipToPadding()
            if (r0 == 0) goto L_0x004f
            android.support.v7.widget.LayoutState r0 = r4.l
            android.support.v7.widget.OrientationHelper r3 = r4.b
            int r3 = r3.getStartAfterPadding()
            int r3 = r3 - r5
            r0.f = r3
            android.support.v7.widget.LayoutState r5 = r4.l
            android.support.v7.widget.OrientationHelper r0 = r4.b
            int r0 = r0.getEndAfterPadding()
            int r0 = r0 + r6
            r5.g = r0
            goto L_0x005f
        L_0x004f:
            android.support.v7.widget.LayoutState r0 = r4.l
            android.support.v7.widget.OrientationHelper r3 = r4.b
            int r3 = r3.getEnd()
            int r3 = r3 + r6
            r0.g = r3
            android.support.v7.widget.LayoutState r6 = r4.l
            int r5 = -r5
            r6.f = r5
        L_0x005f:
            android.support.v7.widget.LayoutState r5 = r4.l
            r5.h = r1
            android.support.v7.widget.LayoutState r5 = r4.l
            r5.a = r2
            android.support.v7.widget.LayoutState r5 = r4.l
            android.support.v7.widget.OrientationHelper r6 = r4.b
            int r6 = r6.getMode()
            if (r6 != 0) goto L_0x007a
            android.support.v7.widget.OrientationHelper r6 = r4.b
            int r6 = r6.getEnd()
            if (r6 != 0) goto L_0x007a
            r1 = 1
        L_0x007a:
            r5.i = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.b(int, android.support.v7.widget.RecyclerView$State):void");
    }

    private void b(int i2) {
        this.l.e = i2;
        LayoutState layoutState = this.l;
        int i3 = 1;
        if (this.e != (i2 == -1)) {
            i3 = -1;
        }
        layoutState.d = i3;
    }

    public void offsetChildrenHorizontal(int i2) {
        super.offsetChildrenHorizontal(i2);
        for (int i3 = 0; i3 < this.i; i3++) {
            this.a[i3].d(i2);
        }
    }

    public void offsetChildrenVertical(int i2) {
        super.offsetChildrenVertical(i2);
        for (int i3 = 0; i3 < this.i; i3++) {
            this.a[i3].d(i2);
        }
    }

    public void onItemsRemoved(RecyclerView recyclerView, int i2, int i3) {
        b(i2, i3, 2);
    }

    public void onItemsAdded(RecyclerView recyclerView, int i2, int i3) {
        b(i2, i3, 1);
    }

    public void onItemsChanged(RecyclerView recyclerView) {
        this.h.a();
        requestLayout();
    }

    public void onItemsMoved(RecyclerView recyclerView, int i2, int i3, int i4) {
        b(i2, i3, 8);
    }

    public void onItemsUpdated(RecyclerView recyclerView, int i2, int i3, Object obj) {
        b(i2, i3, 4);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0026  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0043 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0044  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(int r6, int r7, int r8) {
        /*
            r5 = this;
            boolean r0 = r5.e
            if (r0 == 0) goto L_0x0009
            int r0 = r5.j()
            goto L_0x000d
        L_0x0009:
            int r0 = r5.k()
        L_0x000d:
            r1 = 8
            if (r8 != r1) goto L_0x001b
            if (r6 >= r7) goto L_0x0016
            int r2 = r7 + 1
            goto L_0x001d
        L_0x0016:
            int r2 = r6 + 1
            r3 = r2
            r2 = r7
            goto L_0x001f
        L_0x001b:
            int r2 = r6 + r7
        L_0x001d:
            r3 = r2
            r2 = r6
        L_0x001f:
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r4 = r5.h
            r4.b(r2)
            if (r8 == r1) goto L_0x0036
            switch(r8) {
                case 1: goto L_0x0030;
                case 2: goto L_0x002a;
                default: goto L_0x0029;
            }
        L_0x0029:
            goto L_0x0041
        L_0x002a:
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r8 = r5.h
            r8.a(r6, r7)
            goto L_0x0041
        L_0x0030:
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r8 = r5.h
            r8.b(r6, r7)
            goto L_0x0041
        L_0x0036:
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r8 = r5.h
            r1 = 1
            r8.a(r6, r1)
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r6 = r5.h
            r6.b(r7, r1)
        L_0x0041:
            if (r3 > r0) goto L_0x0044
            return
        L_0x0044:
            boolean r6 = r5.e
            if (r6 == 0) goto L_0x004d
            int r6 = r5.k()
            goto L_0x0051
        L_0x004d:
            int r6 = r5.j()
        L_0x0051:
            if (r2 > r6) goto L_0x0056
            r5.requestLayout()
        L_0x0056:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.b(int, int, int):void");
    }

    /* JADX WARNING: type inference failed for: r9v0 */
    /* JADX WARNING: type inference failed for: r9v1, types: [int, boolean] */
    /* JADX WARNING: type inference failed for: r9v4 */
    /* JADX WARNING: type inference failed for: r9v9 */
    /* JADX WARNING: type inference failed for: r9v10 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r9v1, types: [int, boolean]
      assigns: []
      uses: [boolean, int, ?[int, short, byte, char]]
      mth insns count: 222
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int a(android.support.v7.widget.RecyclerView.Recycler r18, android.support.v7.widget.LayoutState r19, android.support.v7.widget.RecyclerView.State r20) {
        /*
            r17 = this;
            r6 = r17
            r7 = r18
            r8 = r19
            java.util.BitSet r0 = r6.m
            int r1 = r6.i
            r9 = 0
            r10 = 1
            r0.set(r9, r1, r10)
            android.support.v7.widget.LayoutState r0 = r6.l
            boolean r0 = r0.i
            if (r0 == 0) goto L_0x0025
            int r0 = r8.e
            if (r0 != r10) goto L_0x0020
            r0 = 2147483647(0x7fffffff, float:NaN)
            r11 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x0036
        L_0x0020:
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            r11 = -2147483648(0xffffffff80000000, float:-0.0)
            goto L_0x0036
        L_0x0025:
            int r0 = r8.e
            if (r0 != r10) goto L_0x0030
            int r0 = r8.g
            int r1 = r8.b
            int r0 = r0 + r1
        L_0x002e:
            r11 = r0
            goto L_0x0036
        L_0x0030:
            int r0 = r8.f
            int r1 = r8.b
            int r0 = r0 - r1
            goto L_0x002e
        L_0x0036:
            int r0 = r8.e
            r6.a(r0, r11)
            boolean r0 = r6.e
            if (r0 == 0) goto L_0x0047
            android.support.v7.widget.OrientationHelper r0 = r6.b
            int r0 = r0.getEndAfterPadding()
        L_0x0045:
            r12 = r0
            goto L_0x004e
        L_0x0047:
            android.support.v7.widget.OrientationHelper r0 = r6.b
            int r0 = r0.getStartAfterPadding()
            goto L_0x0045
        L_0x004e:
            r0 = 0
        L_0x004f:
            boolean r1 = r19.a(r20)
            r2 = -1
            if (r1 == 0) goto L_0x01cf
            android.support.v7.widget.LayoutState r1 = r6.l
            boolean r1 = r1.i
            if (r1 != 0) goto L_0x0064
            java.util.BitSet r1 = r6.m
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x01cf
        L_0x0064:
            android.view.View r13 = r8.a(r7)
            android.view.ViewGroup$LayoutParams r0 = r13.getLayoutParams()
            r14 = r0
            android.support.v7.widget.StaggeredGridLayoutManager$LayoutParams r14 = (android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams) r14
            int r0 = r14.getViewLayoutPosition()
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r1 = r6.h
            int r1 = r1.c(r0)
            if (r1 != r2) goto L_0x007d
            r3 = 1
            goto L_0x007e
        L_0x007d:
            r3 = 0
        L_0x007e:
            if (r3 == 0) goto L_0x0093
            boolean r1 = r14.b
            if (r1 == 0) goto L_0x0089
            android.support.v7.widget.StaggeredGridLayoutManager$Span[] r1 = r6.a
            r1 = r1[r9]
            goto L_0x008d
        L_0x0089:
            android.support.v7.widget.StaggeredGridLayoutManager$Span r1 = r6.a(r8)
        L_0x008d:
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r4 = r6.h
            r4.a(r0, r1)
            goto L_0x0097
        L_0x0093:
            android.support.v7.widget.StaggeredGridLayoutManager$Span[] r4 = r6.a
            r1 = r4[r1]
        L_0x0097:
            r15 = r1
            r14.a = r15
            int r1 = r8.e
            if (r1 != r10) goto L_0x00a2
            r6.addView(r13)
            goto L_0x00a5
        L_0x00a2:
            r6.addView(r13, r9)
        L_0x00a5:
            r6.a(r13, r14, r9)
            int r1 = r8.e
            if (r1 != r10) goto L_0x00d6
            boolean r1 = r14.b
            if (r1 == 0) goto L_0x00b5
            int r1 = r6.g(r12)
            goto L_0x00b9
        L_0x00b5:
            int r1 = r15.b(r12)
        L_0x00b9:
            android.support.v7.widget.OrientationHelper r4 = r6.b
            int r4 = r4.getDecoratedMeasurement(r13)
            int r4 = r4 + r1
            if (r3 == 0) goto L_0x00d3
            boolean r5 = r14.b
            if (r5 == 0) goto L_0x00d3
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem r5 = r6.c(r1)
            r5.b = r2
            r5.a = r0
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.h
            r9.a(r5)
        L_0x00d3:
            r5 = r4
            r4 = r1
            goto L_0x00ff
        L_0x00d6:
            boolean r1 = r14.b
            if (r1 == 0) goto L_0x00df
            int r1 = r6.f(r12)
            goto L_0x00e3
        L_0x00df:
            int r1 = r15.a(r12)
        L_0x00e3:
            android.support.v7.widget.OrientationHelper r4 = r6.b
            int r4 = r4.getDecoratedMeasurement(r13)
            int r4 = r1 - r4
            if (r3 == 0) goto L_0x00fe
            boolean r5 = r14.b
            if (r5 == 0) goto L_0x00fe
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem r5 = r6.d(r1)
            r5.b = r10
            r5.a = r0
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.h
            r9.a(r5)
        L_0x00fe:
            r5 = r1
        L_0x00ff:
            boolean r1 = r14.b
            if (r1 == 0) goto L_0x0129
            int r1 = r8.d
            if (r1 != r2) goto L_0x0129
            if (r3 == 0) goto L_0x010c
            r6.E = r10
            goto L_0x0129
        L_0x010c:
            int r1 = r8.e
            if (r1 != r10) goto L_0x0116
            boolean r1 = r17.h()
        L_0x0114:
            r1 = r1 ^ r10
            goto L_0x011b
        L_0x0116:
            boolean r1 = r17.i()
            goto L_0x0114
        L_0x011b:
            if (r1 == 0) goto L_0x0129
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r1 = r6.h
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem r0 = r1.f(r0)
            if (r0 == 0) goto L_0x0127
            r0.d = r10
        L_0x0127:
            r6.E = r10
        L_0x0129:
            r6.a(r13, r14, r8)
            boolean r0 = r17.c()
            if (r0 == 0) goto L_0x015d
            int r0 = r6.j
            if (r0 != r10) goto L_0x015d
            boolean r0 = r14.b
            if (r0 == 0) goto L_0x0141
            android.support.v7.widget.OrientationHelper r0 = r6.c
            int r0 = r0.getEndAfterPadding()
            goto L_0x0152
        L_0x0141:
            android.support.v7.widget.OrientationHelper r0 = r6.c
            int r0 = r0.getEndAfterPadding()
            int r1 = r6.i
            int r1 = r1 - r10
            int r2 = r15.e
            int r1 = r1 - r2
            int r2 = r6.k
            int r1 = r1 * r2
            int r0 = r0 - r1
        L_0x0152:
            android.support.v7.widget.OrientationHelper r1 = r6.c
            int r1 = r1.getDecoratedMeasurement(r13)
            int r1 = r0 - r1
            r9 = r0
            r3 = r1
            goto L_0x017e
        L_0x015d:
            boolean r0 = r14.b
            if (r0 == 0) goto L_0x0168
            android.support.v7.widget.OrientationHelper r0 = r6.c
            int r0 = r0.getStartAfterPadding()
            goto L_0x0175
        L_0x0168:
            int r0 = r15.e
            int r1 = r6.k
            int r0 = r0 * r1
            android.support.v7.widget.OrientationHelper r1 = r6.c
            int r1 = r1.getStartAfterPadding()
            int r0 = r0 + r1
        L_0x0175:
            android.support.v7.widget.OrientationHelper r1 = r6.c
            int r1 = r1.getDecoratedMeasurement(r13)
            int r1 = r1 + r0
            r3 = r0
            r9 = r1
        L_0x017e:
            int r0 = r6.j
            if (r0 != r10) goto L_0x018b
            r0 = r6
            r1 = r13
            r2 = r3
            r3 = r4
            r4 = r9
            r0.layoutDecoratedWithMargins(r1, r2, r3, r4, r5)
            goto L_0x0193
        L_0x018b:
            r0 = r6
            r1 = r13
            r2 = r4
            r4 = r5
            r5 = r9
            r0.layoutDecoratedWithMargins(r1, r2, r3, r4, r5)
        L_0x0193:
            boolean r0 = r14.b
            if (r0 == 0) goto L_0x019f
            android.support.v7.widget.LayoutState r0 = r6.l
            int r0 = r0.e
            r6.a(r0, r11)
            goto L_0x01a6
        L_0x019f:
            android.support.v7.widget.LayoutState r0 = r6.l
            int r0 = r0.e
            r6.a(r15, r0, r11)
        L_0x01a6:
            android.support.v7.widget.LayoutState r0 = r6.l
            r6.a(r7, r0)
            android.support.v7.widget.LayoutState r0 = r6.l
            boolean r0 = r0.h
            if (r0 == 0) goto L_0x01ca
            boolean r0 = r13.hasFocusable()
            if (r0 == 0) goto L_0x01ca
            boolean r0 = r14.b
            if (r0 == 0) goto L_0x01c1
            java.util.BitSet r0 = r6.m
            r0.clear()
            goto L_0x01ca
        L_0x01c1:
            java.util.BitSet r0 = r6.m
            int r1 = r15.e
            r3 = 0
            r0.set(r1, r3)
            goto L_0x01cb
        L_0x01ca:
            r3 = 0
        L_0x01cb:
            r0 = 1
            r9 = 0
            goto L_0x004f
        L_0x01cf:
            r3 = 0
            if (r0 != 0) goto L_0x01d7
            android.support.v7.widget.LayoutState r0 = r6.l
            r6.a(r7, r0)
        L_0x01d7:
            android.support.v7.widget.LayoutState r0 = r6.l
            int r0 = r0.e
            if (r0 != r2) goto L_0x01ef
            android.support.v7.widget.OrientationHelper r0 = r6.b
            int r0 = r0.getStartAfterPadding()
            int r0 = r6.f(r0)
            android.support.v7.widget.OrientationHelper r1 = r6.b
            int r1 = r1.getStartAfterPadding()
            int r1 = r1 - r0
            goto L_0x0201
        L_0x01ef:
            android.support.v7.widget.OrientationHelper r0 = r6.b
            int r0 = r0.getEndAfterPadding()
            int r0 = r6.g(r0)
            android.support.v7.widget.OrientationHelper r1 = r6.b
            int r1 = r1.getEndAfterPadding()
            int r1 = r0 - r1
        L_0x0201:
            if (r1 <= 0) goto L_0x020a
            int r0 = r8.b
            int r9 = java.lang.Math.min(r0, r1)
            r3 = r9
        L_0x020a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.a(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.LayoutState, android.support.v7.widget.RecyclerView$State):int");
    }

    private FullSpanItem c(int i2) {
        FullSpanItem fullSpanItem = new FullSpanItem();
        fullSpanItem.c = new int[this.i];
        for (int i3 = 0; i3 < this.i; i3++) {
            fullSpanItem.c[i3] = i2 - this.a[i3].b(i2);
        }
        return fullSpanItem;
    }

    private FullSpanItem d(int i2) {
        FullSpanItem fullSpanItem = new FullSpanItem();
        fullSpanItem.c = new int[this.i];
        for (int i3 = 0; i3 < this.i; i3++) {
            fullSpanItem.c[i3] = this.a[i3].a(i2) - i2;
        }
        return fullSpanItem;
    }

    private void a(View view, LayoutParams layoutParams, LayoutState layoutState) {
        if (layoutState.e == 1) {
            if (layoutParams.b) {
                a(view);
            } else {
                layoutParams.a.b(view);
            }
        } else if (layoutParams.b) {
            b(view);
        } else {
            layoutParams.a.a(view);
        }
    }

    private void a(Recycler recycler, LayoutState layoutState) {
        int i2;
        int i3;
        if (layoutState.a && !layoutState.i) {
            if (layoutState.b == 0) {
                if (layoutState.e == -1) {
                    b(recycler, layoutState.g);
                } else {
                    a(recycler, layoutState.f);
                }
            } else if (layoutState.e == -1) {
                int e2 = layoutState.f - e(layoutState.f);
                if (e2 < 0) {
                    i3 = layoutState.g;
                } else {
                    i3 = layoutState.g - Math.min(e2, layoutState.b);
                }
                b(recycler, i3);
            } else {
                int h2 = h(layoutState.g) - layoutState.g;
                if (h2 < 0) {
                    i2 = layoutState.f;
                } else {
                    i2 = Math.min(h2, layoutState.b) + layoutState.f;
                }
                a(recycler, i2);
            }
        }
    }

    private void a(View view) {
        for (int i2 = this.i - 1; i2 >= 0; i2--) {
            this.a[i2].b(view);
        }
    }

    private void b(View view) {
        for (int i2 = this.i - 1; i2 >= 0; i2--) {
            this.a[i2].a(view);
        }
    }

    private void a(int i2, int i3) {
        for (int i4 = 0; i4 < this.i; i4++) {
            if (!this.a[i4].a.isEmpty()) {
                a(this.a[i4], i2, i3);
            }
        }
    }

    private void a(Span span, int i2, int i3) {
        int i4 = span.i();
        if (i2 == -1) {
            if (span.b() + i4 <= i3) {
                this.m.set(span.e, false);
            }
        } else if (span.d() - i4 >= i3) {
            this.m.set(span.e, false);
        }
    }

    private int e(int i2) {
        int a2 = this.a[0].a(i2);
        for (int i3 = 1; i3 < this.i; i3++) {
            int a3 = this.a[i3].a(i2);
            if (a3 > a2) {
                a2 = a3;
            }
        }
        return a2;
    }

    private int f(int i2) {
        int a2 = this.a[0].a(i2);
        for (int i3 = 1; i3 < this.i; i3++) {
            int a3 = this.a[i3].a(i2);
            if (a3 < a2) {
                a2 = a3;
            }
        }
        return a2;
    }

    /* access modifiers changed from: 0000 */
    public boolean h() {
        int b2 = this.a[0].b(Integer.MIN_VALUE);
        for (int i2 = 1; i2 < this.i; i2++) {
            if (this.a[i2].b(Integer.MIN_VALUE) != b2) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean i() {
        int a2 = this.a[0].a(Integer.MIN_VALUE);
        for (int i2 = 1; i2 < this.i; i2++) {
            if (this.a[i2].a(Integer.MIN_VALUE) != a2) {
                return false;
            }
        }
        return true;
    }

    private int g(int i2) {
        int b2 = this.a[0].b(i2);
        for (int i3 = 1; i3 < this.i; i3++) {
            int b3 = this.a[i3].b(i2);
            if (b3 > b2) {
                b2 = b3;
            }
        }
        return b2;
    }

    private int h(int i2) {
        int b2 = this.a[0].b(i2);
        for (int i3 = 1; i3 < this.i; i3++) {
            int b3 = this.a[i3].b(i2);
            if (b3 < b2) {
                b2 = b3;
            }
        }
        return b2;
    }

    private void a(Recycler recycler, int i2) {
        while (getChildCount() > 0) {
            View childAt = getChildAt(0);
            if (this.b.getDecoratedEnd(childAt) <= i2 && this.b.getTransformedEndWithDecoration(childAt) <= i2) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.b) {
                    int i3 = 0;
                    while (i3 < this.i) {
                        if (this.a[i3].a.size() != 1) {
                            i3++;
                        } else {
                            return;
                        }
                    }
                    for (int i4 = 0; i4 < this.i; i4++) {
                        this.a[i4].h();
                    }
                } else if (layoutParams.a.a.size() != 1) {
                    layoutParams.a.h();
                } else {
                    return;
                }
                removeAndRecycleView(childAt, recycler);
            } else {
                return;
            }
        }
    }

    private void b(Recycler recycler, int i2) {
        int childCount = getChildCount() - 1;
        while (childCount >= 0) {
            View childAt = getChildAt(childCount);
            if (this.b.getDecoratedStart(childAt) >= i2 && this.b.getTransformedStartWithDecoration(childAt) >= i2) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.b) {
                    int i3 = 0;
                    while (i3 < this.i) {
                        if (this.a[i3].a.size() != 1) {
                            i3++;
                        } else {
                            return;
                        }
                    }
                    for (int i4 = 0; i4 < this.i; i4++) {
                        this.a[i4].g();
                    }
                } else if (layoutParams.a.a.size() != 1) {
                    layoutParams.a.g();
                } else {
                    return;
                }
                removeAndRecycleView(childAt, recycler);
                childCount--;
            } else {
                return;
            }
        }
    }

    private boolean i(int i2) {
        boolean z2 = false;
        if (this.j == 0) {
            if ((i2 == -1) != this.e) {
                z2 = true;
            }
            return z2;
        }
        if (((i2 == -1) == this.e) == c()) {
            z2 = true;
        }
        return z2;
    }

    private Span a(LayoutState layoutState) {
        int i2;
        int i3;
        int i4 = -1;
        if (i(layoutState.e)) {
            i3 = this.i - 1;
            i2 = -1;
        } else {
            i3 = 0;
            i4 = this.i;
            i2 = 1;
        }
        Span span = null;
        if (layoutState.e == 1) {
            int i5 = SubsamplingScaleImageView.TILE_SIZE_AUTO;
            int startAfterPadding = this.b.getStartAfterPadding();
            while (i3 != i4) {
                Span span2 = this.a[i3];
                int b2 = span2.b(startAfterPadding);
                if (b2 < i5) {
                    span = span2;
                    i5 = b2;
                }
                i3 += i2;
            }
            return span;
        }
        int i6 = Integer.MIN_VALUE;
        int endAfterPadding = this.b.getEndAfterPadding();
        while (i3 != i4) {
            Span span3 = this.a[i3];
            int a2 = span3.a(endAfterPadding);
            if (a2 > i6) {
                span = span3;
                i6 = a2;
            }
            i3 += i2;
        }
        return span;
    }

    public boolean canScrollVertically() {
        return this.j == 1;
    }

    public boolean canScrollHorizontally() {
        return this.j == 0;
    }

    public int scrollHorizontallyBy(int i2, Recycler recycler, State state) {
        return a(i2, recycler, state);
    }

    public int scrollVerticallyBy(int i2, Recycler recycler, State state) {
        return a(i2, recycler, state);
    }

    private int j(int i2) {
        int i3 = -1;
        if (getChildCount() == 0) {
            if (this.e) {
                i3 = 1;
            }
            return i3;
        }
        if ((i2 < k()) == this.e) {
            i3 = 1;
        }
        return i3;
    }

    public PointF computeScrollVectorForPosition(int i2) {
        int j2 = j(i2);
        PointF pointF = new PointF();
        if (j2 == 0) {
            return null;
        }
        if (this.j == 0) {
            pointF.x = (float) j2;
            pointF.y = BitmapDescriptorFactory.HUE_RED;
        } else {
            pointF.x = BitmapDescriptorFactory.HUE_RED;
            pointF.y = (float) j2;
        }
        return pointF;
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, State state, int i2) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext());
        linearSmoothScroller.setTargetPosition(i2);
        startSmoothScroll(linearSmoothScroller);
    }

    public void scrollToPosition(int i2) {
        if (!(this.A == null || this.A.a == i2)) {
            this.A.b();
        }
        this.f = i2;
        this.g = Integer.MIN_VALUE;
        requestLayout();
    }

    public void scrollToPositionWithOffset(int i2, int i3) {
        if (this.A != null) {
            this.A.b();
        }
        this.f = i2;
        this.g = i3;
        requestLayout();
    }

    @RestrictTo({Scope.LIBRARY})
    public void collectAdjacentPrefetchPositions(int i2, int i3, State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i4;
        if (this.j != 0) {
            i2 = i3;
        }
        if (getChildCount() != 0 && i2 != 0) {
            a(i2, state);
            if (this.G == null || this.G.length < this.i) {
                this.G = new int[this.i];
            }
            int i5 = 0;
            for (int i6 = 0; i6 < this.i; i6++) {
                if (this.l.d == -1) {
                    i4 = this.l.f - this.a[i6].a(this.l.f);
                } else {
                    i4 = this.a[i6].b(this.l.g) - this.l.g;
                }
                if (i4 >= 0) {
                    this.G[i5] = i4;
                    i5++;
                }
            }
            Arrays.sort(this.G, 0, i5);
            for (int i7 = 0; i7 < i5 && this.l.a(state); i7++) {
                layoutPrefetchRegistry.addPosition(this.l.c, this.G[i7]);
                this.l.c += this.l.d;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, State state) {
        int i3;
        int i4;
        if (i2 > 0) {
            i4 = j();
            i3 = 1;
        } else {
            i4 = k();
            i3 = -1;
        }
        this.l.a = true;
        b(i4, state);
        b(i3);
        this.l.c = i4 + this.l.d;
        this.l.b = Math.abs(i2);
    }

    /* access modifiers changed from: 0000 */
    public int a(int i2, Recycler recycler, State state) {
        if (getChildCount() == 0 || i2 == 0) {
            return 0;
        }
        a(i2, state);
        int a2 = a(recycler, this.l, state);
        if (this.l.b >= a2) {
            i2 = i2 < 0 ? -a2 : a2;
        }
        this.b.offsetChildren(-i2);
        this.o = this.e;
        this.l.b = 0;
        a(recycler, this.l);
        return i2;
    }

    /* access modifiers changed from: 0000 */
    public int j() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return 0;
        }
        return getPosition(getChildAt(childCount - 1));
    }

    /* access modifiers changed from: 0000 */
    public int k() {
        if (getChildCount() == 0) {
            return 0;
        }
        return getPosition(getChildAt(0));
    }

    private int k(int i2) {
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            int position = getPosition(getChildAt(i3));
            if (position >= 0 && position < i2) {
                return position;
            }
        }
        return 0;
    }

    private int l(int i2) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            int position = getPosition(getChildAt(childCount));
            if (position >= 0 && position < i2) {
                return position;
            }
        }
        return 0;
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.j == 0) {
            return new LayoutParams(-2, -1);
        }
        return new LayoutParams(-1, -2);
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof MarginLayoutParams) {
            return new LayoutParams((MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public boolean checkLayoutParams(android.support.v7.widget.RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public int getOrientation() {
        return this.j;
    }

    @Nullable
    public View onFocusSearchFailed(View view, int i2, Recycler recycler, State state) {
        int i3;
        int i4;
        int i5;
        int i6;
        if (getChildCount() == 0) {
            return null;
        }
        View findContainingItemView = findContainingItemView(view);
        if (findContainingItemView == null) {
            return null;
        }
        m();
        int m2 = m(i2);
        if (m2 == Integer.MIN_VALUE) {
            return null;
        }
        LayoutParams layoutParams = (LayoutParams) findContainingItemView.getLayoutParams();
        boolean z2 = layoutParams.b;
        Span span = layoutParams.a;
        if (m2 == 1) {
            i3 = j();
        } else {
            i3 = k();
        }
        b(i3, state);
        b(m2);
        this.l.c = this.l.d + i3;
        this.l.b = (int) (((float) this.b.getTotalSpace()) * 0.33333334f);
        this.l.h = true;
        this.l.a = false;
        a(recycler, this.l, state);
        this.o = this.e;
        if (!z2) {
            View a2 = span.a(i3, m2);
            if (!(a2 == null || a2 == findContainingItemView)) {
                return a2;
            }
        }
        if (i(m2)) {
            for (int i7 = this.i - 1; i7 >= 0; i7--) {
                View a3 = this.a[i7].a(i3, m2);
                if (a3 != null && a3 != findContainingItemView) {
                    return a3;
                }
            }
        } else {
            for (int i8 = 0; i8 < this.i; i8++) {
                View a4 = this.a[i8].a(i3, m2);
                if (a4 != null && a4 != findContainingItemView) {
                    return a4;
                }
            }
        }
        boolean z3 = (this.d ^ true) == (m2 == -1);
        if (!z2) {
            if (z3) {
                i6 = span.k();
            } else {
                i6 = span.n();
            }
            View findViewByPosition = findViewByPosition(i6);
            if (!(findViewByPosition == null || findViewByPosition == findContainingItemView)) {
                return findViewByPosition;
            }
        }
        if (i(m2)) {
            for (int i9 = this.i - 1; i9 >= 0; i9--) {
                if (i9 != span.e) {
                    if (z3) {
                        i5 = this.a[i9].k();
                    } else {
                        i5 = this.a[i9].n();
                    }
                    View findViewByPosition2 = findViewByPosition(i5);
                    if (!(findViewByPosition2 == null || findViewByPosition2 == findContainingItemView)) {
                        return findViewByPosition2;
                    }
                }
            }
        } else {
            for (int i10 = 0; i10 < this.i; i10++) {
                if (z3) {
                    i4 = this.a[i10].k();
                } else {
                    i4 = this.a[i10].n();
                }
                View findViewByPosition3 = findViewByPosition(i4);
                if (findViewByPosition3 != null && findViewByPosition3 != findContainingItemView) {
                    return findViewByPosition3;
                }
            }
        }
        return null;
    }

    private int m(int i2) {
        int i3 = -1;
        int i4 = Integer.MIN_VALUE;
        if (i2 == 17) {
            if (this.j != 0) {
                i3 = Integer.MIN_VALUE;
            }
            return i3;
        } else if (i2 == 33) {
            if (this.j != 1) {
                i3 = Integer.MIN_VALUE;
            }
            return i3;
        } else if (i2 == 66) {
            if (this.j == 0) {
                i4 = 1;
            }
            return i4;
        } else if (i2 != 130) {
            switch (i2) {
                case 1:
                    return (this.j != 1 && c()) ? 1 : -1;
                case 2:
                    return (this.j != 1 && c()) ? -1 : 1;
                default:
                    return Integer.MIN_VALUE;
            }
        } else {
            if (this.j == 1) {
                i4 = 1;
            }
            return i4;
        }
    }
}
