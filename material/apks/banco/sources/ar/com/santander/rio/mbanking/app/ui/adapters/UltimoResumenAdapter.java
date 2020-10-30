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
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CDateAcc;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.DateView;
import java.util.ArrayList;
import java.util.HashMap;

public class UltimoResumenAdapter extends BaseAdapter {
    public static final String TAG_FRAGMENT_LOAD = "tag_fragment_load";
    private ArrayList<HashMap<String, String>> a;
    private LayoutInflater b = null;

    static class ViewHolder {
        DateView a;
        TextView b;
        AmountView c;
        AmountView d;

        ViewHolder() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public UltimoResumenAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.a = arrayList;
        this.b = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.a.size();
    }

    public Object getItem(int i) {
        return this.a.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = this.b.inflate(R.layout.tarjetas_row_item, null);
            viewHolder = new ViewHolder();
            viewHolder.a = (DateView) view.findViewById(R.id.cell_date);
            viewHolder.b = (TextView) view.findViewById(R.id.cell_desc);
            viewHolder.c = (AmountView) view.findViewById(R.id.cell_value);
            viewHolder.d = (AmountView) view.findViewById(R.id.cell_value_2);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (((HashMap) this.a.get(i)).get("cell_date") == null) {
            viewHolder.a.setDateStr("");
        } else if (UtilDate.isDate((String) ((HashMap) this.a.get(i)).get("cell_date"), Constants.FORMAT_DATE_APP)) {
            viewHolder.a.setDateStr(UtilDate.getDateFormat((String) ((HashMap) this.a.get(i)).get("cell_date"), Constants.FORMAT_DATE_APP));
        } else if (UtilDate.isDate((String) ((HashMap) this.a.get(i)).get("cell_date"), Constants.FORMAT_DATE_APP_2)) {
            viewHolder.a.setDateStr(Constants.FORMAT_DATE_APP_2);
        }
        viewHolder.a.setCElementAcc(new CDateAcc());
        viewHolder.b.setText(String.valueOf(Html.fromHtml((String) ((HashMap) this.a.get(i)).get("cell_desc"))).toUpperCase());
        viewHolder.b.setContentDescription(String.valueOf(viewHolder.b.getText().toString()));
        viewHolder.c.setCElementAcc(new CAmountAcc());
        CAmount cAmount = new CAmount((String) ((HashMap) this.a.get(i)).get("cell_value"));
        cAmount.setSymbolCurrencyDollarOrPeso(((String) ((HashMap) this.a.get(i)).get("cell_moneda")).equals(TarjetasConstants.DOLAR));
        viewHolder.c.setAmount(cAmount.getAmount());
        if (((HashMap) this.a.get(i)).get("cell_value_2") != null) {
            viewHolder.d.setCElementAcc(new CAmountAcc());
            CAmount cAmount2 = new CAmount((String) ((HashMap) this.a.get(i)).get("cell_value_2"));
            cAmount2.setSymbolCurrencyDollarOrPeso(((String) ((HashMap) this.a.get(i)).get("cell_moneda_2")).equals(TarjetasConstants.DOLAR));
            viewHolder.d.setAmount(cAmount2.getAmount());
            viewHolder.d.setVisibility(0);
        } else {
            viewHolder.d.setVisibility(8);
        }
        return view;
    }
}
