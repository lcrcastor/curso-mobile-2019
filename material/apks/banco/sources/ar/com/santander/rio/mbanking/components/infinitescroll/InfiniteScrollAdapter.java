package ar.com.santander.rio.mbanking.components.infinitescroll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.Collection;

public abstract class InfiniteScrollAdapter extends BaseAdapter {
    private boolean a = false;
    private LayoutInflater b;
    public Context mContext;

    public abstract void addItems(Collection collection);

    public long getItemId(int i) {
        return (long) i;
    }

    public abstract Collection getItems();

    public abstract View getLoadingView(LayoutInflater layoutInflater, ViewGroup viewGroup);

    public abstract Object getRealItem(int i);

    public abstract View getRealView(LayoutInflater layoutInflater, int i, View view, ViewGroup viewGroup);

    public InfiniteScrollAdapter(Context context) {
        this.b = LayoutInflater.from(context);
        this.mContext = context;
    }

    public int getCount() {
        return getItems().size() + (this.a ^ true ? 1 : 0);
    }

    public Object getItem(int i) {
        if (this.a || i != getCount()) {
            return getRealItem(i);
        }
        throw new IllegalArgumentException("Can not call getItem on loading placeholder.");
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (this.a || i < getItems().size()) {
            return getRealView(this.b, i, view, viewGroup);
        }
        return getLoadingView(this.b, viewGroup);
    }

    /* access modifiers changed from: protected */
    public void setDoneLoading() {
        this.a = true;
    }

    public void cleanItems() {
        if (getItems() != null) {
            getItems().clear();
            notifyDataSetChanged();
        }
    }
}
