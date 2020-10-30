package ar.com.santander.rio.mbanking.components.listswipe;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.internal.view.SupportMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;
import ar.com.santander.rio.mbanking.components.listswipe.SwipeMenuListView.OnMenuItemClickListener;
import ar.com.santander.rio.mbanking.components.listswipe.SwipeMenuView.OnSwipeItemClickListener;
import cz.msebera.android.httpclient.HttpStatus;

public class SwipeMenuAdapter implements WrapperListAdapter, OnSwipeItemClickListener {
    private ListAdapter a;
    private Context b;
    private OnMenuItemClickListener c;

    public SwipeMenuAdapter(Context context, ListAdapter listAdapter) {
        this.a = listAdapter;
        this.b = context;
    }

    public int getCount() {
        return this.a.getCount();
    }

    public Object getItem(int i) {
        return this.a.getItem(i);
    }

    public long getItemId(int i) {
        return this.a.getItemId(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            View view2 = this.a.getView(i, view, viewGroup);
            SwipeMenu swipeMenu = new SwipeMenu(this.b);
            swipeMenu.setViewType(this.a.getItemViewType(i));
            createMenu(swipeMenu);
            SwipeMenuListView swipeMenuListView = (SwipeMenuListView) viewGroup;
            SwipeMenuView swipeMenuView = new SwipeMenuView(swipeMenu, swipeMenuListView);
            swipeMenuView.setOnSwipeItemClickListener(this);
            SwipeMenuLayout swipeMenuLayout = new SwipeMenuLayout(view2, swipeMenuView, swipeMenuListView.getCloseInterpolator(), swipeMenuListView.getOpenInterpolator());
            swipeMenuLayout.setPosition(i);
            return swipeMenuLayout;
        }
        SwipeMenuLayout swipeMenuLayout2 = (SwipeMenuLayout) view;
        swipeMenuLayout2.closeMenu();
        swipeMenuLayout2.setPosition(i);
        this.a.getView(i, swipeMenuLayout2.getContentView(), viewGroup);
        return swipeMenuLayout2;
    }

    public void createMenu(SwipeMenu swipeMenu) {
        SwipeMenuItem swipeMenuItem = new SwipeMenuItem(this.b);
        swipeMenuItem.setTitle("Item 1");
        swipeMenuItem.setBackground((Drawable) new ColorDrawable(-7829368));
        swipeMenuItem.setWidth(HttpStatus.SC_MULTIPLE_CHOICES);
        swipeMenu.addMenuItem(swipeMenuItem);
        SwipeMenuItem swipeMenuItem2 = new SwipeMenuItem(this.b);
        swipeMenuItem2.setTitle("Item 2");
        swipeMenuItem2.setBackground((Drawable) new ColorDrawable(SupportMenu.CATEGORY_MASK));
        swipeMenuItem2.setWidth(HttpStatus.SC_MULTIPLE_CHOICES);
        swipeMenu.addMenuItem(swipeMenuItem2);
    }

    public void onItemClick(SwipeMenuView swipeMenuView, SwipeMenu swipeMenu, int i) {
        if (this.c != null) {
            this.c.onMenuItemClick(swipeMenuView.getPosition(), swipeMenu, i);
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.c = onMenuItemClickListener;
    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.a.registerDataSetObserver(dataSetObserver);
    }

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.a.unregisterDataSetObserver(dataSetObserver);
    }

    public boolean areAllItemsEnabled() {
        return this.a.areAllItemsEnabled();
    }

    public boolean isEnabled(int i) {
        return this.a.isEnabled(i);
    }

    public boolean hasStableIds() {
        return this.a.hasStableIds();
    }

    public int getItemViewType(int i) {
        return this.a.getItemViewType(i);
    }

    public int getViewTypeCount() {
        return this.a.getViewTypeCount();
    }

    public boolean isEmpty() {
        return this.a.isEmpty();
    }

    public ListAdapter getWrappedAdapter() {
        return this.a;
    }
}
