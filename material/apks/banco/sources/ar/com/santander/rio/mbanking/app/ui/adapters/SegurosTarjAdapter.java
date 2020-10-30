package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean;
import ar.com.santander.rio.mbanking.utils.Utils;
import java.util.Iterator;
import java.util.List;

public abstract class SegurosTarjAdapter extends ArrayAdapter<TarjetaBean> {
    private int a;
    Context b;
    /* access modifiers changed from: private */
    public List<TarjetaBean> c;
    /* access modifiers changed from: private */
    public int d = 0;

    public abstract void checkTipoDuplicadoTarjetas(String str);

    public abstract void checkboxCheck(int i, boolean z);

    public SegurosTarjAdapter(Context context, int i, List<TarjetaBean> list) {
        super(context, i, list);
        this.b = context;
        this.a = i;
        this.c = list;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        int i2 = i;
        View inflate = view == null ? ((Activity) this.b).getLayoutInflater().inflate(this.a, viewGroup, false) : view;
        TarjetaBean tarjetaBean = (TarjetaBean) this.c.get(i2);
        TextView textView = (TextView) inflate.findViewById(R.id.F27_11_LBL_DATA_NUM_TARJ);
        TextView textView2 = (TextView) inflate.findViewById(R.id.F27_11_LBL_DATA_TITULAR);
        CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.F27_11_BTN_CHECK);
        RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.F27_11_RLAYOUT_ITEM_SEGUROS_TARJ);
        tarjetaBean.setListPosition(i2);
        if (tarjetaBean.isChecked().booleanValue()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
        this.d = 0;
        for (TarjetaBean isChecked : this.c) {
            if (isChecked.isChecked().booleanValue()) {
                this.d++;
            }
        }
        relativeLayout.setTag(String.valueOf(i));
        relativeLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Iterator it = SegurosTarjAdapter.this.c.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    TarjetaBean tarjetaBean = (TarjetaBean) it.next();
                    if (tarjetaBean.getListPosition() == Integer.valueOf(view.getTag().toString()).intValue()) {
                        if (tarjetaBean.isChecked().booleanValue()) {
                            tarjetaBean.setChecked(false);
                            SegurosTarjAdapter.this.d = SegurosTarjAdapter.this.d - 1;
                            SegurosTarjAdapter.this.checkboxCheck(SegurosTarjAdapter.this.d, false);
                        } else if (SegurosTarjAdapter.this.d >= 3) {
                            SegurosTarjAdapter.this.checkboxCheck(SegurosTarjAdapter.this.d, true);
                        } else if (!SegurosTarjAdapter.this.a(tarjetaBean.getTipo())) {
                            tarjetaBean.setChecked(true);
                            SegurosTarjAdapter.this.d = SegurosTarjAdapter.this.d + 1;
                            SegurosTarjAdapter.this.checkboxCheck(SegurosTarjAdapter.this.d, false);
                        } else {
                            SegurosTarjAdapter.this.checkTipoDuplicadoTarjetas(Utils.getDescTipoTarjeta(tarjetaBean.getTipo()));
                        }
                    }
                }
                SegurosTarjAdapter.this.notifyDataSetChanged();
            }
        });
        Tarjeta tarjeta = new Tarjeta(tarjetaBean.getNumTarjeta(), null, null, null, tarjetaBean.getTipo(), null, "", null, null, null, null, null);
        textView.setText(Utils.mascaraTarjeta(tarjeta));
        try {
            CAccessibility cAccessibility = new CAccessibility(getContext());
            Tarjeta tarjeta2 = new Tarjeta(tarjetaBean.getNumTarjeta(), null, null, null, tarjetaBean.getTipo(), null, "", null, null, null, null, null);
            textView.setContentDescription(cAccessibility.applyFilterCreditCard(Utils.mascaraTarjeta(tarjeta2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tarjetaBean.getTipo().equalsIgnoreCase("05")) {
            textView2.setText(getContext().getString(R.string.ID_4063_SEGUROS_LBL_TITULAR));
        } else {
            textView2.setText(getContext().getString(R.string.ID_4062_SEGUROS_LBL_TITULAR_ADIC));
        }
        return inflate;
    }

    /* access modifiers changed from: private */
    public boolean a(String str) {
        for (TarjetaBean tarjetaBean : this.c) {
            if (tarjetaBean.isChecked().booleanValue() && tarjetaBean.getTipo().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
}
