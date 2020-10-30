package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.services.model.general.ElementoNumeroUtil;
import ar.com.santander.rio.mbanking.view.TelephoneRow;
import java.util.ArrayList;

public class NumerosUtilesListAdapter extends ArrayAdapter<ElementoNumeroUtil> {
    /* access modifiers changed from: private */
    public final Context a;
    private final ArrayList<ElementoNumeroUtil> b;
    private TextView c;
    private ListView d;
    /* access modifiers changed from: private */
    public NumerosUtilesListener e;

    public interface NumerosUtilesListener {
        void onPhoneSelected(String str);
    }

    static class ViewHolder {
        /* access modifiers changed from: private */
        public TextView a;
        /* access modifiers changed from: private */
        public TextView b;
        /* access modifiers changed from: private */
        public TelephoneRow c;
        /* access modifiers changed from: private */
        public TelephoneRow d;

        private ViewHolder() {
        }
    }

    public NumerosUtilesListAdapter(Context context, ArrayList<ElementoNumeroUtil> arrayList, TextView textView, ListView listView) {
        super(context, R.layout.numeros_utiles_list_row, arrayList);
        this.a = context;
        this.b = arrayList;
        this.c = textView;
        this.d = listView;
    }

    public NumerosUtilesListener getNumerosUtilesListener() {
        return this.e;
    }

    public void setNumerosUtilesListener(NumerosUtilesListener numerosUtilesListener) {
        this.e = numerosUtilesListener;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = ((SantanderRioMainActivity) this.a).getLayoutInflater().inflate(R.layout.numeros_utiles_list_row, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.a = (TextView) view.findViewById(R.id.numeros_utiles_titulo);
            viewHolder.b = (TextView) view.findViewById(R.id.numeros_utiles_detalle);
            viewHolder.c = (TelephoneRow) view.findViewById(R.id.numeros_utiles_telephoneRow1);
            viewHolder.d = (TelephoneRow) view.findViewById(R.id.numeros_utiles_telephoneRow2);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ElementoNumeroUtil elementoNumeroUtil = (ElementoNumeroUtil) this.b.get(i);
        if (elementoNumeroUtil != null) {
            viewHolder.a.setText(elementoNumeroUtil.getDescripDatoUtil());
            try {
                viewHolder.a.setContentDescription(new CAccessibility(this.a).applyFilterGeneral(viewHolder.a.getText().toString().toLowerCase()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            viewHolder.b.setText(elementoNumeroUtil.getDescripAdicDatoUtil());
            if (!TextUtils.isEmpty(elementoNumeroUtil.getDescripAdicDatoUtil())) {
                try {
                    viewHolder.b.setContentDescription(new CAccessibility(this.a).applyFilterTime(viewHolder.b.getText().toString()));
                } catch (Exception unused) {
                }
            } else {
                viewHolder.b.setContentDescription("");
            }
            if (elementoNumeroUtil.getTel1DatoUtil() == null) {
                viewHolder.c.setVisibility(8);
            } else if (elementoNumeroUtil.getTel1DatoUtil().equals("") || elementoNumeroUtil.getTel1DatoUtil() == null) {
                viewHolder.c.setVisibility(8);
            } else {
                viewHolder.c.setVisibility(0);
                viewHolder.c.setNumber(elementoNumeroUtil.getTel1DatoUtil());
            }
            if (elementoNumeroUtil.getTel2DatoUtil() == null) {
                viewHolder.d.setVisibility(8);
            } else if (elementoNumeroUtil.getTel2DatoUtil().equals("") || elementoNumeroUtil.getTel2DatoUtil() == null) {
                viewHolder.d.setVisibility(8);
            } else {
                viewHolder.d.setVisibility(0);
                viewHolder.d.setNumber(elementoNumeroUtil.getTel2DatoUtil());
            }
        }
        viewHolder.c.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (NumerosUtilesListAdapter.this.e != null) {
                        NumerosUtilesListAdapter.this.e.onPhoneSelected(((TelephoneRow) view).getText().trim());
                    }
                } catch (Throwable unused) {
                    Toast.makeText(NumerosUtilesListAdapter.this.a, R.string.MSG_ERROR_TELEFONO, 1).show();
                }
            }
        });
        viewHolder.d.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (NumerosUtilesListAdapter.this.e != null) {
                        NumerosUtilesListAdapter.this.e.onPhoneSelected(((TelephoneRow) view).getText().trim());
                    }
                } catch (Throwable unused) {
                    Toast.makeText(NumerosUtilesListAdapter.this.a, R.string.MSG_ERROR_TELEFONO, 1).show();
                }
            }
        });
        return view;
    }
}
