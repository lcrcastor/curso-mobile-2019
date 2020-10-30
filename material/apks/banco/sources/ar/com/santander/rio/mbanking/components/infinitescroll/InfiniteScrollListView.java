package ar.com.santander.rio.mbanking.components.infinitescroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;
import java.util.ArrayList;

public class InfiniteScrollListView extends ListView {
    private boolean a = true;
    private int b;
    private InfiniteScrollOnScrollListener c;

    public void setSelection(int i) {
    }

    public InfiniteScrollListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setListener(InfiniteScrollOnScrollListener infiniteScrollOnScrollListener) {
        this.c = infiniteScrollOnScrollListener;
    }

    public void setAdapter(InfiniteScrollAdapter infiniteScrollAdapter) {
        super.setAdapter(infiniteScrollAdapter);
    }

    public void appendItems(ArrayList arrayList) {
        if (getAdapter() == null) {
            throw new NullPointerException("Can not append items to a null adapter");
        }
        if (getAdapter() instanceof InfiniteScrollAdapter) {
            ((InfiniteScrollAdapter) getAdapter()).addItems(arrayList);
        }
        if (arrayList.size() == 0) {
            setOnScrollListener(null);
            return;
        }
        setOnScrollListener(this.c);
        this.c.checkForFetchMore(this);
    }

    public int getRealCount() {
        if (getAdapter() instanceof InfiniteScrollAdapter) {
            return ((InfiniteScrollAdapter) getAdapter()).getItems().size();
        }
        return 0;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.a) {
            return super.dispatchTouchEvent(motionEvent);
        }
        int actionMasked = motionEvent.getActionMasked() & 255;
        if (actionMasked == 0) {
            this.b = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
            return super.dispatchTouchEvent(motionEvent);
        } else if (actionMasked == 2) {
            return true;
        } else {
            if (actionMasked == 1) {
                if (pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY()) == this.b) {
                    super.dispatchTouchEvent(motionEvent);
                } else {
                    setPressed(false);
                    invalidate();
                    return true;
                }
            }
            return super.dispatchTouchEvent(motionEvent);
        }
    }

    public boolean isScrollEnabled() {
        return this.a;
    }

    public void setScrollEnabled(boolean z) {
        this.a = z;
    }
}
