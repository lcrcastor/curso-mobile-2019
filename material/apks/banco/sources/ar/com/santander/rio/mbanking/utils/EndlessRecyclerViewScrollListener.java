package ar.com.santander.rio.mbanking.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class EndlessRecyclerViewScrollListener extends OnScrollListener {
    private int a = 5;
    private int b = 0;
    private int c = 0;
    private boolean d = true;
    private int e = 0;
    private EndlessRecyclerViewScrollListenerInteface f;
    private LayoutManager g;

    public interface EndlessRecyclerViewScrollListenerInteface {
        void onLoadMore(int i, RecyclerView recyclerView);
    }

    public void setLoading(boolean z) {
        this.d = z;
    }

    public EndlessRecyclerViewScrollListener(LayoutManager layoutManager, EndlessRecyclerViewScrollListenerInteface endlessRecyclerViewScrollListenerInteface) {
        this.g = layoutManager;
        this.f = endlessRecyclerViewScrollListenerInteface;
    }

    public EndlessRecyclerViewScrollListener(LinearLayoutManager linearLayoutManager, EndlessRecyclerViewScrollListenerInteface endlessRecyclerViewScrollListenerInteface) {
        this.g = linearLayoutManager;
        this.f = endlessRecyclerViewScrollListenerInteface;
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager gridLayoutManager, EndlessRecyclerViewScrollListenerInteface endlessRecyclerViewScrollListenerInteface) {
        this.g = gridLayoutManager;
        this.a *= gridLayoutManager.getSpanCount();
        this.f = endlessRecyclerViewScrollListenerInteface;
    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager staggeredGridLayoutManager, EndlessRecyclerViewScrollListenerInteface endlessRecyclerViewScrollListenerInteface) {
        this.g = staggeredGridLayoutManager;
        this.a *= staggeredGridLayoutManager.getSpanCount();
        this.f = endlessRecyclerViewScrollListenerInteface;
    }

    public int getLastVisibleItem(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (i2 == 0) {
                i = iArr[i2];
            } else if (iArr[i2] > i) {
                i = iArr[i2];
            }
        }
        return i;
    }

    public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        int itemCount = this.g.getItemCount();
        int i3 = this.g instanceof StaggeredGridLayoutManager ? getLastVisibleItem(((StaggeredGridLayoutManager) this.g).findLastVisibleItemPositions(null)) : this.g instanceof GridLayoutManager ? ((GridLayoutManager) this.g).findLastVisibleItemPosition() : this.g instanceof LinearLayoutManager ? ((LinearLayoutManager) this.g).findLastVisibleItemPosition() : 0;
        if (itemCount < this.c) {
            this.b = this.e;
            this.c = itemCount;
            if (itemCount == 0) {
                this.d = true;
            }
        }
        if (this.d && itemCount > this.c) {
            this.d = false;
            this.c = itemCount;
        }
        if (!this.d && i3 + this.a > itemCount) {
            this.b++;
            this.f.onLoadMore(itemCount, recyclerView);
        }
    }

    public void resetState() {
        this.b = this.e;
        this.c = 0;
        this.d = true;
    }
}
