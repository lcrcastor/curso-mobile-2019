package ar.com.santander.rio.mbanking.components.asymmetricgridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ar.com.santander.rio.mbanking.components.asymmetricgridview.model.AsymmetricItem;
import ar.com.santander.rio.mbanking.components.asymmetricgridview.widget.AsymmetricGridView;
import ar.com.santander.rio.mbanking.components.asymmetricgridview.widget.AsymmetricGridViewAdapter;
import java.util.List;

public abstract class AsymmetricInfiniteScrollAdapter<T extends AsymmetricItem> extends AsymmetricGridViewAdapter<T> {
    private LayoutInflater a;
    protected boolean doneLoading = false;

    public long getItemId(int i) {
        return (long) i;
    }

    public abstract View getLoadingView(LayoutInflater layoutInflater, ViewGroup viewGroup);

    public abstract T getRealItem(int i);

    public abstract View getRealView(LayoutInflater layoutInflater, int i, View view, ViewGroup viewGroup);

    public AsymmetricInfiniteScrollAdapter(Context context, AsymmetricGridView asymmetricGridView, List<T> list) {
        super(context, asymmetricGridView, list);
        this.a = LayoutInflater.from(context);
    }

    public int getCount() {
        return getRowCount() + (this.doneLoading ^ true ? 1 : 0);
    }

    public T getItem(int i) {
        return super.getItem(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (this.doneLoading || i < getRowCount()) {
            return super.getView(i, view, viewGroup);
        }
        return getLoadingView(this.a, viewGroup);
    }

    public void setDoneLoading() {
        this.doneLoading = true;
    }

    public void setLoading() {
        this.doneLoading = false;
    }
}
