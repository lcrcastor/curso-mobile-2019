package android.support.v7.util;

import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.support.v7.util.ThreadUtil.BackgroundCallback;
import android.support.v7.util.ThreadUtil.MainThreadCallback;
import android.support.v7.util.TileList.Tile;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

public class AsyncListUtil<T> {
    final Class<T> a;
    final int b;
    final DataCallback<T> c;
    final ViewCallback d;
    final TileList<T> e;
    final MainThreadCallback<T> f;
    final BackgroundCallback<T> g;
    final int[] h = new int[2];
    final int[] i = new int[2];
    final int[] j = new int[2];
    boolean k;
    int l = 0;
    int m = 0;
    int n = this.m;
    final SparseIntArray o = new SparseIntArray();
    private int p = 0;
    private final MainThreadCallback<T> q = new MainThreadCallback<T>() {
        public void updateItemCount(int i, int i2) {
            if (a(i)) {
                AsyncListUtil.this.l = i2;
                AsyncListUtil.this.d.onDataRefresh();
                AsyncListUtil.this.m = AsyncListUtil.this.n;
                a();
                AsyncListUtil.this.k = false;
                AsyncListUtil.this.a();
            }
        }

        public void addTile(int i, Tile<T> tile) {
            if (!a(i)) {
                AsyncListUtil.this.g.recycleTile(tile);
                return;
            }
            Tile a2 = AsyncListUtil.this.e.a(tile);
            if (a2 != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("duplicate tile @");
                sb.append(a2.mStartPosition);
                Log.e("AsyncListUtil", sb.toString());
                AsyncListUtil.this.g.recycleTile(a2);
            }
            int i2 = tile.mStartPosition + tile.mItemCount;
            int i3 = 0;
            while (i3 < AsyncListUtil.this.o.size()) {
                int keyAt = AsyncListUtil.this.o.keyAt(i3);
                if (tile.mStartPosition > keyAt || keyAt >= i2) {
                    i3++;
                } else {
                    AsyncListUtil.this.o.removeAt(i3);
                    AsyncListUtil.this.d.onItemLoaded(keyAt);
                }
            }
        }

        public void removeTile(int i, int i2) {
            if (a(i)) {
                Tile c = AsyncListUtil.this.e.c(i2);
                if (c == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("tile not found @");
                    sb.append(i2);
                    Log.e("AsyncListUtil", sb.toString());
                    return;
                }
                AsyncListUtil.this.g.recycleTile(c);
            }
        }

        private void a() {
            for (int i = 0; i < AsyncListUtil.this.e.a(); i++) {
                AsyncListUtil.this.g.recycleTile(AsyncListUtil.this.e.b(i));
            }
            AsyncListUtil.this.e.b();
        }

        private boolean a(int i) {
            return i == AsyncListUtil.this.n;
        }
    };
    private final BackgroundCallback<T> r = new BackgroundCallback<T>() {
        final SparseBooleanArray a = new SparseBooleanArray();
        private Tile<T> c;
        private int d;
        private int e;
        private int f;
        private int g;

        public void refresh(int i) {
            this.d = i;
            this.a.clear();
            this.e = AsyncListUtil.this.c.refreshData();
            AsyncListUtil.this.f.updateItemCount(this.d, this.e);
        }

        public void updateRange(int i, int i2, int i3, int i4, int i5) {
            if (i <= i2) {
                int a2 = a(i);
                int a3 = a(i2);
                this.f = a(i3);
                this.g = a(i4);
                if (i5 == 1) {
                    a(this.f, a3, i5, true);
                    a(a3 + AsyncListUtil.this.b, this.g, i5, false);
                } else {
                    a(a2, this.g, i5, false);
                    a(this.f, a2 - AsyncListUtil.this.b, i5, true);
                }
            }
        }

        private int a(int i) {
            return i - (i % AsyncListUtil.this.b);
        }

        private void a(int i, int i2, int i3, boolean z) {
            int i4 = i;
            while (i4 <= i2) {
                AsyncListUtil.this.g.loadTile(z ? (i2 + i) - i4 : i4, i3);
                i4 += AsyncListUtil.this.b;
            }
        }

        public void loadTile(int i, int i2) {
            if (!b(i)) {
                Tile a2 = a();
                a2.mStartPosition = i;
                a2.mItemCount = Math.min(AsyncListUtil.this.b, this.e - a2.mStartPosition);
                AsyncListUtil.this.c.fillData(a2.mItems, a2.mStartPosition, a2.mItemCount);
                d(i2);
                a(a2);
            }
        }

        public void recycleTile(Tile<T> tile) {
            AsyncListUtil.this.c.recycleData(tile.mItems, tile.mItemCount);
            tile.a = this.c;
            this.c = tile;
        }

        private Tile<T> a() {
            if (this.c == null) {
                return new Tile<>(AsyncListUtil.this.a, AsyncListUtil.this.b);
            }
            Tile<T> tile = this.c;
            this.c = this.c.a;
            return tile;
        }

        private boolean b(int i) {
            return this.a.get(i);
        }

        private void a(Tile<T> tile) {
            this.a.put(tile.mStartPosition, true);
            AsyncListUtil.this.f.addTile(this.d, tile);
        }

        private void c(int i) {
            this.a.delete(i);
            AsyncListUtil.this.f.removeTile(this.d, i);
        }

        private void d(int i) {
            int maxCachedTiles = AsyncListUtil.this.c.getMaxCachedTiles();
            while (this.a.size() >= maxCachedTiles) {
                int keyAt = this.a.keyAt(0);
                int keyAt2 = this.a.keyAt(this.a.size() - 1);
                int i2 = this.f - keyAt;
                int i3 = keyAt2 - this.g;
                if (i2 > 0 && (i2 >= i3 || i == 2)) {
                    c(keyAt);
                } else if (i3 > 0 && (i2 < i3 || i == 1)) {
                    c(keyAt2);
                } else {
                    return;
                }
            }
        }
    };

    public static abstract class DataCallback<T> {
        @WorkerThread
        public abstract void fillData(T[] tArr, int i, int i2);

        @WorkerThread
        public int getMaxCachedTiles() {
            return 10;
        }

        @WorkerThread
        public void recycleData(T[] tArr, int i) {
        }

        @WorkerThread
        public abstract int refreshData();
    }

    public static abstract class ViewCallback {
        public static final int HINT_SCROLL_ASC = 2;
        public static final int HINT_SCROLL_DESC = 1;
        public static final int HINT_SCROLL_NONE = 0;

        @UiThread
        public abstract void getItemRangeInto(int[] iArr);

        @UiThread
        public abstract void onDataRefresh();

        @UiThread
        public abstract void onItemLoaded(int i);

        @UiThread
        public void extendRangeInto(int[] iArr, int[] iArr2, int i) {
            int i2 = (iArr[1] - iArr[0]) + 1;
            int i3 = i2 / 2;
            iArr2[0] = iArr[0] - (i == 1 ? i2 : i3);
            int i4 = iArr[1];
            if (i != 2) {
                i2 = i3;
            }
            iArr2[1] = i4 + i2;
        }
    }

    public AsyncListUtil(Class<T> cls, int i2, DataCallback<T> dataCallback, ViewCallback viewCallback) {
        this.a = cls;
        this.b = i2;
        this.c = dataCallback;
        this.d = viewCallback;
        this.e = new TileList<>(this.b);
        MessageThreadUtil messageThreadUtil = new MessageThreadUtil();
        this.f = messageThreadUtil.a(this.q);
        this.g = messageThreadUtil.a(this.r);
        refresh();
    }

    private boolean b() {
        return this.n != this.m;
    }

    public void onRangeChanged() {
        if (!b()) {
            a();
            this.k = true;
        }
    }

    public void refresh() {
        this.o.clear();
        BackgroundCallback<T> backgroundCallback = this.g;
        int i2 = this.n + 1;
        this.n = i2;
        backgroundCallback.refresh(i2);
    }

    public T getItem(int i2) {
        if (i2 < 0 || i2 >= this.l) {
            StringBuilder sb = new StringBuilder();
            sb.append(i2);
            sb.append(" is not within 0 and ");
            sb.append(this.l);
            throw new IndexOutOfBoundsException(sb.toString());
        }
        T a2 = this.e.a(i2);
        if (a2 == null && !b()) {
            this.o.put(i2, 0);
        }
        return a2;
    }

    public int getItemCount() {
        return this.l;
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.d.getItemRangeInto(this.h);
        if (this.h[0] <= this.h[1] && this.h[0] >= 0 && this.h[1] < this.l) {
            if (!this.k) {
                this.p = 0;
            } else if (this.h[0] > this.i[1] || this.i[0] > this.h[1]) {
                this.p = 0;
            } else if (this.h[0] < this.i[0]) {
                this.p = 1;
            } else if (this.h[0] > this.i[0]) {
                this.p = 2;
            }
            this.i[0] = this.h[0];
            this.i[1] = this.h[1];
            this.d.extendRangeInto(this.h, this.j, this.p);
            this.j[0] = Math.min(this.h[0], Math.max(this.j[0], 0));
            this.j[1] = Math.max(this.h[1], Math.min(this.j[1], this.l - 1));
            this.g.updateRange(this.h[0], this.h[1], this.j[0], this.j[1], this.p);
        }
    }
}
