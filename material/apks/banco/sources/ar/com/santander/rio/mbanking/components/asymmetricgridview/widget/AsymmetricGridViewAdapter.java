package ar.com.santander.rio.mbanking.components.asymmetricgridview.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.components.asymmetricgridview.AsymmetricGridViewAdapterContract;
import ar.com.santander.rio.mbanking.components.asymmetricgridview.AsyncTaskCompat;
import ar.com.santander.rio.mbanking.components.asymmetricgridview.model.AsymmetricItem;
import ar.com.santander.rio.mbanking.utils.Utils;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AsymmetricGridViewAdapter<T extends AsymmetricItem> extends BaseAdapter implements OnClickListener, OnLongClickListener, AsymmetricGridViewAdapterContract {
    /* access modifiers changed from: private */
    public Map<Integer, RowInfo<T>> a = new HashMap();
    private final ViewPool<IcsLinearLayout> b;
    private final ViewPool<View> c = new ViewPool<>();
    protected final Context context;
    private ProcessRowsTask d;
    protected final List<T> items;
    protected final AsymmetricGridView listView;

    class ProcessRowsTask extends AsyncTaskCompat<List<T>, Void, List<RowInfo<T>>> {
        ProcessRowsTask() {
        }

        /* access modifiers changed from: protected */
        @SafeVarargs
        /* renamed from: a */
        public final List<RowInfo<T>> doInBackground(List<T>... listArr) {
            return a(0, listArr[0]);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(List<RowInfo<T>> list) {
            for (RowInfo put : list) {
                AsymmetricGridViewAdapter.this.a.put(Integer.valueOf(AsymmetricGridViewAdapter.this.getRowCount()), put);
            }
            AsymmetricGridViewAdapter.this.notifyDataSetChanged();
        }

        private List<RowInfo<T>> a(int i, List<T> list) {
            ArrayList arrayList = new ArrayList();
            while (!list.isEmpty()) {
                RowInfo a2 = AsymmetricGridViewAdapter.this.a(list);
                List<AsymmetricItem> a3 = a2.a();
                if (a3.isEmpty()) {
                    break;
                }
                for (AsymmetricItem remove : a3) {
                    list.remove(remove);
                }
                arrayList.add(a2);
            }
            return arrayList;
        }
    }

    public abstract View getActualView(int i, View view, ViewGroup viewGroup);

    public long getItemId(int i) {
        return (long) i;
    }

    public AsymmetricGridViewAdapter(Context context2, AsymmetricGridView asymmetricGridView, List<T> list) {
        this.b = new ViewPool<>(new LinearLayoutPoolObjectFactory(context2));
        this.items = list;
        this.context = context2;
        this.listView = asymmetricGridView;
    }

    /* access modifiers changed from: protected */
    public int getRowHeight(AsymmetricItem asymmetricItem) {
        return getRowHeight(asymmetricItem.getRowSpan());
    }

    public T getItem(int i) {
        if (i >= 0) {
            return (AsymmetricItem) this.items.get(i);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public int getRowHeight(int i) {
        return (this.listView.getColumnWidth() * i) + ((i - 1) * this.listView.getDividerHeight());
    }

    /* access modifiers changed from: protected */
    public int getRowWidth(AsymmetricItem asymmetricItem) {
        return getRowWidth(asymmetricItem.getColumnSpan());
    }

    /* access modifiers changed from: protected */
    public int getRowWidth(int i) {
        return Math.min((this.listView.getColumnWidth() * i) + ((i - 1) * this.listView.getRequestedHorizontalSpacing()), Utils.getScreenWidth(this.context));
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        IcsLinearLayout a2 = a(view);
        RowInfo rowInfo = (RowInfo) this.a.get(Integer.valueOf(i));
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(rowInfo.a());
        int b2 = rowInfo.b();
        int i2 = 0;
        loop0:
        while (true) {
            int i3 = 0;
            while (!arrayList.isEmpty() && i2 < this.listView.getNumColumns()) {
                AsymmetricItem asymmetricItem = (AsymmetricItem) arrayList.get(i3);
                if (b2 != 0) {
                    if (b2 < asymmetricItem.getRowSpan()) {
                        if (i3 >= arrayList.size() - 1) {
                            break loop0;
                        }
                        i3++;
                    } else {
                        arrayList.remove(asymmetricItem);
                        int indexOf = this.items.indexOf(asymmetricItem);
                        IcsLinearLayout a3 = a((LinearLayout) a2, i2);
                        View actualView = getActualView(indexOf, this.c.a(), viewGroup);
                        if (actualView != null) {
                            actualView.setTag(asymmetricItem);
                            actualView.setOnClickListener(this);
                            actualView.setOnLongClickListener(this);
                            b2 -= asymmetricItem.getRowSpan();
                            actualView.setLayoutParams(new LayoutParams(getRowWidth(asymmetricItem), -2));
                            a3.addView(actualView);
                        }
                    }
                } else {
                    i2++;
                    b2 = rowInfo.b();
                }
            }
        }
        return a2;
    }

    public Parcelable saveState() {
        Bundle bundle = new Bundle();
        bundle.putInt("totalItems", this.items.size());
        for (int i = 0; i < this.items.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("item_");
            sb.append(i);
            bundle.putParcelable(sb.toString(), (Parcelable) this.items.get(i));
        }
        return bundle;
    }

    public void restoreState(Parcelable parcelable) {
        Bundle bundle = (Bundle) parcelable;
        if (bundle != null) {
            bundle.setClassLoader(getClass().getClassLoader());
            int i = bundle.getInt("totalItems");
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < i; i2++) {
                StringBuilder sb = new StringBuilder();
                sb.append("item_");
                sb.append(i2);
                arrayList.add((AsymmetricItem) bundle.getParcelable(sb.toString()));
            }
            setItems(arrayList);
        }
    }

    private IcsLinearLayout a(View view) {
        IcsLinearLayout icsLinearLayout;
        if (view == null || !(view instanceof IcsLinearLayout)) {
            icsLinearLayout = new IcsLinearLayout(this.context, null);
            icsLinearLayout.setShowDividers(2);
            icsLinearLayout.setDividerDrawable(this.context.getResources().getDrawable(R.drawable.item_divider_horizontal));
            icsLinearLayout.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
        } else {
            icsLinearLayout = (IcsLinearLayout) view;
        }
        for (int i = 0; i < icsLinearLayout.getChildCount(); i++) {
            IcsLinearLayout icsLinearLayout2 = (IcsLinearLayout) icsLinearLayout.getChildAt(i);
            this.b.a(icsLinearLayout2);
            for (int i2 = 0; i2 < icsLinearLayout2.getChildCount(); i2++) {
                this.c.a(icsLinearLayout2.getChildAt(i2));
            }
            icsLinearLayout2.removeAllViews();
        }
        icsLinearLayout.removeAllViews();
        return icsLinearLayout;
    }

    private IcsLinearLayout a(LinearLayout linearLayout, int i) {
        IcsLinearLayout icsLinearLayout = (IcsLinearLayout) linearLayout.getChildAt(i);
        if (icsLinearLayout != null) {
            return icsLinearLayout;
        }
        IcsLinearLayout icsLinearLayout2 = (IcsLinearLayout) this.b.a();
        icsLinearLayout2.setOrientation(1);
        icsLinearLayout2.setShowDividers(2);
        icsLinearLayout2.setDividerDrawable(this.context.getResources().getDrawable(R.drawable.item_divider_vertical));
        icsLinearLayout2.setLayoutParams(new AbsListView.LayoutParams(-2, -1));
        linearLayout.addView(icsLinearLayout2);
        return icsLinearLayout2;
    }

    public void setItems(List<T> list) {
        this.b.b();
        this.c.b();
        this.items.clear();
        this.items.addAll(list);
        recalculateItemsPerRow();
        notifyDataSetChanged();
    }

    public void appendItems(List<T> list) {
        this.items.addAll(list);
        int rowCount = getRowCount() - 1;
        RowInfo rowInfo = rowCount >= 0 ? (RowInfo) this.a.get(Integer.valueOf(rowCount)) : null;
        if (rowInfo != null && rowInfo.c() > BitmapDescriptorFactory.HUE_RED) {
            for (AsymmetricItem add : rowInfo.a()) {
                list.add(0, add);
            }
            RowInfo a2 = a(list);
            List<AsymmetricItem> a3 = a2.a();
            if (!a3.isEmpty()) {
                for (AsymmetricItem remove : a3) {
                    list.remove(remove);
                }
                this.a.put(Integer.valueOf(rowCount), a2);
                notifyDataSetChanged();
            }
        }
        this.d = new ProcessRowsTask<>();
        this.d.executeSerially(list);
    }

    public void onClick(View view) {
        this.listView.fireOnItemClick(this.items.indexOf((AsymmetricItem) view.getTag()), view);
    }

    public void cleanItems() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public boolean onLongClick(View view) {
        return this.listView.fireOnItemLongClick(this.items.indexOf((AsymmetricItem) view.getTag()), view);
    }

    public int getCount() {
        return getRowCount();
    }

    public int getRowCount() {
        return this.a.size();
    }

    public void recalculateItemsPerRow() {
        if (this.d != null) {
            this.d.cancel(true);
        }
        this.b.b();
        this.c.b();
        this.a.clear();
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.items);
        this.d = new ProcessRowsTask<>();
        this.d.executeSerially(arrayList);
    }

    /* access modifiers changed from: private */
    public RowInfo<T> a(List<T> list) {
        return a(list, (float) this.listView.getNumColumns());
    }

    private RowInfo<T> a(List<T> list, float f) {
        ArrayList arrayList = new ArrayList();
        float f2 = f;
        int i = 0;
        int i2 = 1;
        while (f2 > BitmapDescriptorFactory.HUE_RED && i < list.size()) {
            int i3 = i + 1;
            AsymmetricItem asymmetricItem = (AsymmetricItem) list.get(i);
            float rowSpan = (float) (asymmetricItem.getRowSpan() * asymmetricItem.getColumnSpan());
            if (i2 < asymmetricItem.getRowSpan()) {
                arrayList.clear();
                i2 = asymmetricItem.getRowSpan();
                f2 = ((float) asymmetricItem.getRowSpan()) * f;
                i = 0;
            } else {
                if (f2 < rowSpan) {
                    if (!this.listView.isAllowReordering()) {
                        break;
                    }
                } else {
                    f2 -= rowSpan;
                    arrayList.add(asymmetricItem);
                }
                i = i3;
            }
        }
        return new RowInfo<>(i2, arrayList, f2);
    }
}
