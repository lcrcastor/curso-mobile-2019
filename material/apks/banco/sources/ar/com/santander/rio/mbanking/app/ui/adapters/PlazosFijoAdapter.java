package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTenenciasDatosPFBean;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import java.util.List;

public class PlazosFijoAdapter extends Adapter<PlazosFijosViewHolder> implements OnClickListener {
    /* access modifiers changed from: private */
    public static Context a;
    private List<CnsTenenciasDatosPFBean> b;
    private OnClickListener c;

    public static class PlazosFijosViewHolder extends ViewHolder {
        private TextView m;
        private TextView n;
        private TextView o;
        private TextView p;
        private TextView q;
        private TextView r;

        public PlazosFijosViewHolder(View view) {
            super(view);
            this.m = (TextView) view.findViewById(R.id.F10_00_lbl_adapter_tipo);
            this.n = (TextView) view.findViewById(R.id.F10_00_lbl_adapter_fecha);
            this.o = (TextView) view.findViewById(R.id.F10_00_lbl_adapter_tasa);
            this.p = (TextView) view.findViewById(R.id.F10_00_lbl_adapter_tasa_data);
            this.q = (TextView) view.findViewById(R.id.F10_00_lbl_adapter_capital_data);
            this.r = (TextView) view.findViewById(R.id.F10_00_lbl_adapter_total_data);
        }

        public void bindTitular(CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean) {
            this.m.setText(cnsTenenciasDatosPFBean.tituloResumen);
            this.n.setText(UtilDate.getDateFormat(cnsTenenciasDatosPFBean.fechaProxVencimiento, Constants.FORMAT_DATE_DASH, Constants.FORMAT_DATE_APP_2));
            this.p.setText(cnsTenenciasDatosPFBean.tasaTNA);
            CAmount cAmount = new CAmount(cnsTenenciasDatosPFBean.capital);
            cAmount.setSymbolCurrencyDollarOrPeso(cnsTenenciasDatosPFBean.capital.contains(Constants.SYMBOL_CURRENCY_DOLAR));
            this.q.setText(cAmount.getAmountPossitive());
            CAmount cAmount2 = new CAmount(cnsTenenciasDatosPFBean.totalACobrar);
            cAmount2.setSymbolCurrencyDollarOrPeso(cnsTenenciasDatosPFBean.capital.contains(Constants.SYMBOL_CURRENCY_DOLAR));
            this.r.setText(cAmount2.getAmountPossitive());
            try {
                this.m.setContentDescription(CAccessibility.getInstance(PlazosFijoAdapter.a).applyFilterGeneral(this.m.getText().toString()));
                this.n.setContentDescription(CAccessibility.getInstance(PlazosFijoAdapter.a).applyFilterDate(this.n.getText().toString()));
                this.o.setContentDescription(CAccessibility.getInstance(PlazosFijoAdapter.a).applyFilterTasaInteres(this.o.getText().toString()));
                this.p.setContentDescription(CAccessibility.getInstance(PlazosFijoAdapter.a).applyFilterTasaValue(this.p.getText().toString()));
                this.q.setContentDescription(CAccessibility.getInstance(PlazosFijoAdapter.a).applyFilterAmount(this.q.getText().toString()));
                this.r.setContentDescription(CAccessibility.getInstance(PlazosFijoAdapter.a).applyFilterAmount(this.r.getText().toString()));
            } catch (Exception unused) {
            }
        }
    }

    public PlazosFijoAdapter(List<CnsTenenciasDatosPFBean> list) {
        this.b = list;
    }

    public PlazosFijosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        a = viewGroup.getContext();
        View inflate = LayoutInflater.from(a).inflate(R.layout.list_item_plazo_fijo, viewGroup, false);
        inflate.setOnClickListener(this);
        return new PlazosFijosViewHolder(inflate);
    }

    public void onBindViewHolder(PlazosFijosViewHolder plazosFijosViewHolder, int i) {
        plazosFijosViewHolder.bindTitular((CnsTenenciasDatosPFBean) this.b.get(i));
        if (i % 2 == 0) {
            plazosFijosViewHolder.itemView.setBackgroundColor(a.getResources().getColor(R.color.grey_list_background));
        } else {
            plazosFijosViewHolder.itemView.setBackgroundColor(a.getResources().getColor(R.color.white));
        }
    }

    public int getItemCount() {
        return this.b.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.c = onClickListener;
    }

    public void onClick(View view) {
        if (this.c != null) {
            this.c.onClick(view);
        }
    }
}
