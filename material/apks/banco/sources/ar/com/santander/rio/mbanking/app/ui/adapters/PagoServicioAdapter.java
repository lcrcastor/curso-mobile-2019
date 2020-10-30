package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmountIU;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.components.OnSingleClickListener;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import java.util.List;

public class PagoServicioAdapter extends Adapter<PagoServicioAdapterViewHolder> {
    static Context a;
    private List<DatosDeudaBean> b;
    /* access modifiers changed from: private */
    public OnItemClickListener c;

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public static class PagoServicioAdapterViewHolder extends ViewHolder {
        private TextView m;
        private TextView n;
        private TextView o;
        private TextView p;

        public PagoServicioAdapterViewHolder(View view) {
            super(view);
            this.m = (TextView) view.findViewById(R.id.F06_00_lbl_fechavto_adapter);
            this.n = (TextView) view.findViewById(R.id.F06_00_lbl_empresa1_adapter);
            this.o = (TextView) view.findViewById(R.id.F06_00_lbl_empresa2_adapter);
            this.p = (TextView) view.findViewById(R.id.F06_00_lbl_importe_adapter);
        }

        public void bindItem(DatosDeudaBean datosDeudaBean) {
            String str;
            this.m.setText(UtilDate.getDateFormat(datosDeudaBean.vencimiento, Constants.FORMAT_DATE_WS_2, Constants.FORMAT_DATE_APP));
            try {
                this.m.setContentDescription(CAccessibility.getInstance(PagoServicioAdapter.a).applyFilterDate(this.m.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.n.setText(datosDeudaBean.datosEmpresa.empDescr);
            try {
                this.n.setContentDescription(CAccessibility.getInstance(PagoServicioAdapter.a).applyFilterGeneral(this.n.getText().toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            TextView textView = this.o;
            if (!TextUtils.isEmpty(datosDeudaBean.infoAdicional)) {
                str = datosDeudaBean.infoAdicional;
            } else {
                str = String.format("ID. %s", new Object[]{datosDeudaBean.identificacion});
            }
            textView.setText(str);
            try {
                this.o.setContentDescription(CAccessibility.getInstance(PagoServicioAdapter.a).applyFilterGeneral(this.o.getText().toString()));
                this.o.setContentDescription(this.o.getContentDescription().toString().toLowerCase().replace("id.", "identificador"));
                this.o.setContentDescription(CAccessibility.getInstance(PagoServicioAdapter.a).applyNumberCharToChar(this.o.getContentDescription().toString()));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            if (CAmountIU.getInstance().getDoubleFromInputUser(datosDeudaBean.importe).doubleValue() == 0.0d) {
                this.p.setText("");
            } else {
                this.p.setText(String.format("%s%s", new Object[]{UtilCurrency.getSimbolCurrencyFromString(datosDeudaBean.moneda), UtilCurrency.getFormattedAmountInArsFromString(datosDeudaBean.importe)}));
            }
            try {
                this.p.setContentDescription(CAccessibility.getInstance(PagoServicioAdapter.a).applyFilterAmount(this.p.getText().toString()));
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
    }

    public PagoServicioAdapter(Context context, List<DatosDeudaBean> list) {
        a = context;
        this.b = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.c = onItemClickListener;
    }

    public PagoServicioAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_pago_servicios_adapter, viewGroup, false);
        inflate.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                PagoServicioAdapter.this.c.onItemClick(view);
            }
        });
        return new PagoServicioAdapterViewHolder(inflate);
    }

    public int getItemCount() {
        return this.b.size();
    }

    public void onBindViewHolder(PagoServicioAdapterViewHolder pagoServicioAdapterViewHolder, int i) {
        pagoServicioAdapterViewHolder.bindItem((DatosDeudaBean) this.b.get(i));
    }
}
