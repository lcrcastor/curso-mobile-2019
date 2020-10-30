package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import ar.com.santander.rio.mbanking.app.ui.Model.ISBANPopupMenuItem;
import ar.com.santander.rio.mbanking.app.ui.adapters.ISBANPopupMenuAdapter;
import java.util.ArrayList;

public class ISBANPopupMenu {
    private Context a;
    private View b;
    /* access modifiers changed from: private */
    public ArrayList<ISBANPopupMenuItem> c;
    /* access modifiers changed from: private */
    public ListPopupWindow d;
    /* access modifiers changed from: private */
    public ISBANPopupMenuListener e;

    public interface ISBANPopupMenuListener {
        void onISBANPopupMenuItemClick(Integer num);
    }

    public ISBANPopupMenu(Context context, ISBANPopupMenuListener iSBANPopupMenuListener, View view) {
        this(context, iSBANPopupMenuListener, view, new ArrayList());
    }

    public ISBANPopupMenu(Context context, ISBANPopupMenuListener iSBANPopupMenuListener, View view, ArrayList<ISBANPopupMenuItem> arrayList) {
        this.c = new ArrayList<>();
        this.a = context;
        this.b = view;
        this.c = arrayList;
        this.d = new ListPopupWindow(context);
        ISBANPopupMenuAdapter iSBANPopupMenuAdapter = new ISBANPopupMenuAdapter(this.a, this.c);
        this.d.setAdapter(iSBANPopupMenuAdapter);
        this.d.setAnchorView(this.b);
        this.d.setHorizontalOffset((this.b.getWidth() - a((ListAdapter) iSBANPopupMenuAdapter)) - 40);
        this.d.setVerticalOffset((this.b.getHeight() - 40) * -1);
        this.d.setContentWidth(a((ListAdapter) iSBANPopupMenuAdapter));
        this.d.setModal(true);
        this.d.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (((ISBANPopupMenuItem) ISBANPopupMenu.this.c.get(i)).getMenuItemType() != 1) {
                    ISBANPopupMenu.this.e.onISBANPopupMenuItemClick(Integer.valueOf(i));
                    ISBANPopupMenu.this.d.dismiss();
                }
            }
        });
        this.e = iSBANPopupMenuListener;
    }

    private int a(ListAdapter listAdapter) {
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
        int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(0, 0);
        int count = listAdapter.getCount();
        ViewGroup viewGroup = null;
        View view = null;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < count; i3++) {
            int itemViewType = listAdapter.getItemViewType(i3);
            if (itemViewType != i) {
                view = null;
                i = itemViewType;
            }
            if (viewGroup == null) {
                viewGroup = new FrameLayout(this.a);
            }
            view = listAdapter.getView(i3, view, viewGroup);
            view.measure(makeMeasureSpec, makeMeasureSpec2);
            int measuredWidth = view.getMeasuredWidth();
            if (measuredWidth > i2) {
                i2 = measuredWidth;
            }
        }
        return i2;
    }

    public void show() {
        if (this.c.size() > 0) {
            this.d.show();
        }
    }

    public void dismiss() {
        this.d.dismiss();
    }
}
