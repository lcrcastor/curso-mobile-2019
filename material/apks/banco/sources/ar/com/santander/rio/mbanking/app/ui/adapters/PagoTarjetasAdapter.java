package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAmountAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CDateAcc;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.DateView;
import java.util.ArrayList;
import java.util.Map;

public class PagoTarjetasAdapter extends BaseAdapter {
    public static final String TAG_FRAGMENT_LOAD = "tag_fragment_load";
    private static final String a = "ar.com.santander.rio.mbanking.app.ui.adapters.PagoTarjetasAdapter";
    private static int b;
    private ArrayList<Map<String, String>> c;
    private LayoutInflater d = null;
    private Context e;

    static class ViewHolder {
        DateView a;
        TextView b;
        TextView c;
        AmountView d;
        AmountView e;

        ViewHolder() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return 0;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public PagoTarjetasAdapter(Context context, ArrayList<Map<String, String>> arrayList) {
        this.e = context;
        this.c = arrayList;
        this.d = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.c.size();
    }

    public Object getItem(int i) {
        return this.c.get(i);
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        String str = a;
        StringBuilder sb = new StringBuilder();
        sb.append("in getView for position ");
        sb.append(i);
        sb.append(", convertView is ");
        sb.append(view == null ? "null" : "being recycled");
        Log.v(str, sb.toString());
        if (view == null) {
            view = this.d.inflate(R.layout.list_item_pago_tarjetas, null);
            b++;
            String str2 = a;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(b);
            sb2.append(" convertViews have been created");
            Log.v(str2, sb2.toString());
            viewHolder = new ViewHolder();
            viewHolder.a = (DateView) view.findViewById(R.id.credit_cards_list_exp_date);
            viewHolder.b = (TextView) view.findViewById(R.id.credit_cards_list_credit_card);
            viewHolder.c = (TextView) view.findViewById(R.id.credit_cards_list_credit_card_detail);
            viewHolder.d = (AmountView) view.findViewById(R.id.credit_cards_list_amount_in_pesos);
            viewHolder.e = (AmountView) view.findViewById(R.id.credit_cards_list_amount_in_dollars);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (viewHolder.a != null) {
            viewHolder.a.setCElementAcc(new CDateAcc());
            if (((Map) this.c.get(i)).get("credit_cards_list_exp_date") == null || !UtilDate.isDate((String) ((Map) this.c.get(i)).get("credit_cards_list_exp_date"), Constants.FORMAT_DATE_APP)) {
                viewHolder.a.setDateStr(Constants.FORMAT_DATE_NULL);
            } else {
                viewHolder.a.setDateStr(UtilDate.getDateFormat((String) ((Map) this.c.get(i)).get("credit_cards_list_exp_date"), Constants.FORMAT_DATE_APP));
            }
        }
        viewHolder.b.setText((CharSequence) ((Map) this.c.get(i)).get("credit_cards_list_credit_card"));
        viewHolder.c.setText((CharSequence) ((Map) this.c.get(i)).get("credit_cards_list_credit_card_detail"));
        try {
            viewHolder.c.setContentDescription(CAccessibility.getInstance(this.e).applyFilterCreditCard(viewHolder.c.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (viewHolder.d != null) {
            if (((String) ((Map) this.c.get(i)).get("credit_cards_list_amount_in_pesos")).indexOf(PagoTarjetasConstants.ARS_AND_USD_FORMATTED_AMOUNTS_ASTERISK, 1) != -1) {
                viewHolder.d.setContentDescription(PagoTarjetasConstants.IMPORTE_PESOS_TARJETA_ADICIONAL);
                viewHolder.d.setText((CharSequence) ((Map) this.c.get(i)).get("credit_cards_list_amount_in_pesos"));
            } else {
                CAmount cAmount = new CAmount((String) ((Map) this.c.get(i)).get("credit_cards_list_amount_in_pesos"));
                cAmount.setSymbolCurrencyDollarOrPeso(false);
                viewHolder.d.setCElementAcc(new CAmountAcc());
                viewHolder.d.setAmount(cAmount.getAmount());
            }
        }
        if (viewHolder.e != null) {
            if (((String) ((Map) this.c.get(i)).get("credit_cards_list_amount_in_dollars")).indexOf(PagoTarjetasConstants.ARS_AND_USD_FORMATTED_AMOUNTS_ASTERISK, 1) != -1) {
                viewHolder.e.setContentDescription(PagoTarjetasConstants.IMPORTE_DOLARES_TARJETA_ADICIONAL);
                viewHolder.e.setText((CharSequence) ((Map) this.c.get(i)).get("credit_cards_list_amount_in_dollars"));
            } else {
                CAmount cAmount2 = new CAmount((String) ((Map) this.c.get(i)).get("credit_cards_list_amount_in_dollars"));
                cAmount2.setSymbolCurrencyDollarOrPeso(true);
                viewHolder.e.setCElementAcc(new CAmountAcc());
                viewHolder.e.setAmount(cAmount2.getAmount());
            }
        }
        return view;
    }
}
