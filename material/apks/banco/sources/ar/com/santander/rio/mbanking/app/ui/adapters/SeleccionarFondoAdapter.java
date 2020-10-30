package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.util.List;

public class SeleccionarFondoAdapter extends Adapter<SeleccionarFondoViewHolder> {
    /* access modifiers changed from: private */
    public static Context a;
    private List<FondoBean> b;
    /* access modifiers changed from: private */
    public OnItemClickListener c;

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public static class SeleccionarFondoViewHolder extends ViewHolder {
        private TextView m;
        private TextView n;

        public SeleccionarFondoViewHolder(View view) {
            super(view);
            this.m = (TextView) view.findViewById(R.id.F24_02_lbl_fondo);
            this.n = (TextView) view.findViewById(R.id.F24_02_lbl_importe);
        }

        public void bindFondo(FondoBean fondoBean) {
            this.m.setText(Html.fromHtml(fondoBean.getNombre()));
            try {
                this.m.setContentDescription(FondosConstants.applyAccesibilityFilterName(SeleccionarFondoAdapter.a, this.m.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (UtilCurrency.isDolares(fondoBean.getMoneda())) {
                TextView textView = this.n;
                StringBuilder sb = new StringBuilder();
                sb.append(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(fondoBean.getMoneda()).toString()));
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(fondoBean.getImporte());
                textView.setText(sb.toString());
            } else if (UtilCurrency.isPesos(fondoBean.getMoneda())) {
                TextView textView2 = this.n;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(fondoBean.getMoneda()).toString()));
                sb2.append(UtilsCuentas.SEPARAOR2);
                sb2.append(fondoBean.getImporte());
                textView2.setText(sb2.toString());
            }
            try {
                this.n.setContentDescription(CAccessibility.getInstance(SeleccionarFondoAdapter.a).applyFilterAmount(this.n.getText().toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public SeleccionarFondoAdapter(Context context, List<FondoBean> list) {
        a = context;
        this.b = list;
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener) {
        this.c = onItemClickListener;
    }

    public SeleccionarFondoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_seleccionar_fondo, viewGroup, false);
        inflate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SeleccionarFondoAdapter.this.c != null) {
                    SeleccionarFondoAdapter.this.c.onItemClick(view);
                }
            }
        });
        return new SeleccionarFondoViewHolder(inflate);
    }

    public void onBindViewHolder(SeleccionarFondoViewHolder seleccionarFondoViewHolder, int i) {
        seleccionarFondoViewHolder.bindFondo((FondoBean) this.b.get(i));
    }

    public int getItemCount() {
        return this.b.size();
    }
}
