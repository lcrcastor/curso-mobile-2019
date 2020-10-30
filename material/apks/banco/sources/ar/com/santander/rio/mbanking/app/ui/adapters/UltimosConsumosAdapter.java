package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAmountAcc;
import ar.com.santander.rio.mbanking.view.AmountView;
import java.util.ArrayList;
import java.util.HashMap;

public class UltimosConsumosAdapter extends BaseAdapter {
    public static final String TAG_FRAGMENT_LOAD = "tag_fragment_load";
    private static int a;
    private ArrayList<HashMap<String, String>> b;
    private LayoutInflater c = null;

    static class ViewHolder {
        TextView a;
        AmountView b;
        AmountView c;

        ViewHolder() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public UltimosConsumosAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.b = arrayList;
        this.c = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.b.size();
    }

    public Object getItem(int i) {
        return this.b.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = this.c.inflate(R.layout.tarjetas_ultimos_consumos_sublist_footer, null);
            a++;
            viewHolder = new ViewHolder();
            viewHolder.a = (TextView) view.findViewById(R.id.sublist_title);
            viewHolder.b = (AmountView) view.findViewById(R.id.sublist_footer_total);
            viewHolder.c = (AmountView) view.findViewById(R.id.sublist_footer_total_2);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.a.setText(Html.fromHtml((String) ((HashMap) this.b.get(i)).get("sublist_title")));
        CAmount cAmount = new CAmount((String) ((HashMap) this.b.get(i)).get("sublist_footer_total"));
        cAmount.setSymbolCurrencyDollarOrPeso(false);
        viewHolder.b.setCElementAcc(new CAmountAcc());
        viewHolder.b.setAmount(cAmount.getAmount());
        CAmount cAmount2 = new CAmount((String) ((HashMap) this.b.get(i)).get("sublist_footer_total_2"));
        cAmount2.setSymbolCurrencyDollarOrPeso(true);
        viewHolder.c.setCElementAcc(new CAmountAcc());
        viewHolder.c.setAmount(cAmount2.getAmount());
        return view;
    }
}
