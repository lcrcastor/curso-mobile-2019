package ar.com.santander.rio.mbanking.components.asymmetricgridview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;
import ar.com.santander.rio.mbanking.components.asymmetricgridview.model.AsymmetricItem;
import ar.com.santander.rio.mbanking.components.asymmetricgridview.widget.AsymmetricGridView;
import ar.com.santander.rio.mbanking.components.asymmetricgridview.widget.AsymmetricGridViewAdapter;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollAdapter;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollOnScrollListener;
import java.util.List;

public class AsymmetricInfiniteScrollListView<T extends AsymmetricItem> extends AsymmetricGridView {
    private boolean a;
    private int b;
    private InfiniteScrollOnScrollListener c;

    public void cleanItems() {
    }

    public void setSelection(int i) {
    }

    public AsymmetricInfiniteScrollListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setListener(InfiniteScrollOnScrollListener infiniteScrollOnScrollListener) {
        this.c = infiniteScrollOnScrollListener;
    }

    public void setAdapter(InfiniteScrollAdapter infiniteScrollAdapter) {
        super.setAdapter((ListAdapter) infiniteScrollAdapter);
    }

    public void appendItems(List<T> list) {
        AsymmetricGridViewAdapter asymmetricGridViewAdapter;
        if (getAdapter() == null) {
            throw new NullPointerException("Can not append items to a null adapter");
        }
        if (getAdapter() instanceof WrapperListAdapter) {
            asymmetricGridViewAdapter = (AsymmetricGridViewAdapter) ((WrapperListAdapter) getAdapter()).getWrappedAdapter();
        } else {
            asymmetricGridViewAdapter = (AsymmetricGridViewAdapter) getAdapter();
        }
        asymmetricGridViewAdapter.appendItems(list);
        if (list.size() == 0) {
            setOnScrollListener(null);
            return;
        }
        setOnScrollListener(this.c);
        this.c.checkForFetchMore(this);
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
