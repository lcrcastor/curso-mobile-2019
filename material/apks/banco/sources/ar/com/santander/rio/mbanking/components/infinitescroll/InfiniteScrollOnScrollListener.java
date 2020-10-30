package ar.com.santander.rio.mbanking.components.infinitescroll;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class InfiniteScrollOnScrollListener implements OnScrollListener {
    /* access modifiers changed from: private */
    public IInfiniteScrollListener a;

    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    public InfiniteScrollOnScrollListener(IInfiniteScrollListener iInfiniteScrollListener) {
        this.a = iInfiniteScrollListener;
    }

    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        if (i3 - ((i + 1) + i2) < 2 && i2 < i3) {
            this.a.endIsNear();
        }
        this.a.onScrollCalled(i, i2, i3);
    }

    public void checkForFetchMore(final AbsListView absListView) {
        absListView.post(new Runnable() {
            public void run() {
                int lastVisiblePosition = absListView.getLastVisiblePosition();
                if (absListView.getChildAt(lastVisiblePosition) != null) {
                    int bottom = absListView.getChildAt(lastVisiblePosition).getBottom();
                    int count = absListView.getCount();
                    int height = absListView.getHeight();
                    if (lastVisiblePosition == count - 1 && bottom <= height) {
                        InfiniteScrollOnScrollListener.this.a.endIsNear();
                    }
                }
            }
        });
    }
}
