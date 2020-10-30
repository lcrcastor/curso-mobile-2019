package ar.com.santander.rio.mbanking.components.listswipe;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class SwipeMenu {
    private Context a;
    private List<SwipeMenuItem> b = new ArrayList();
    private int c;

    public SwipeMenu(Context context) {
        this.a = context;
    }

    public Context getContext() {
        return this.a;
    }

    public void addMenuItem(SwipeMenuItem swipeMenuItem) {
        this.b.add(swipeMenuItem);
    }

    public void removeMenuItem(SwipeMenuItem swipeMenuItem) {
        this.b.remove(swipeMenuItem);
    }

    public List<SwipeMenuItem> getMenuItems() {
        return this.b;
    }

    public SwipeMenuItem getMenuItem(int i) {
        return (SwipeMenuItem) this.b.get(i);
    }

    public int getViewType() {
        return this.c;
    }

    public void setViewType(int i) {
        this.c = i;
    }
}
