package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CoberturaBean;
import ar.com.santander.rio.mbanking.utils.itrsa.HtmlTextView;
import java.util.List;

public class CotizarCoberturaObjetoAdapter extends Adapter<CotizarCoberturaObjetoHolder> {
    private Context a;
    private List<CoberturaBean> b;

    public class CotizarCoberturaObjetoHolder extends ViewHolder {
        public HtmlTextView htmltvLabelCotizacion;
        public TextView tvValueCotizacion;

        public CotizarCoberturaObjetoHolder(View view) {
            super(view);
            this.htmltvLabelCotizacion = (HtmlTextView) view.findViewById(R.id.htmltvLabelCotizacion);
            this.tvValueCotizacion = (TextView) view.findViewById(R.id.tvValueCotizacion);
        }
    }

    public CotizarCoberturaObjetoAdapter(Context context) {
        this.a = context;
    }

    public void setList(List<CoberturaBean> list) {
        this.b = list;
        notifyDataSetChanged();
    }

    public CotizarCoberturaObjetoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CotizarCoberturaObjetoHolder(LayoutInflater.from(this.a).inflate(R.layout.cotizar_cobertura_list_item, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull CotizarCoberturaObjetoHolder cotizarCoberturaObjetoHolder, int i) {
        CoberturaBean coberturaBean = (CoberturaBean) this.b.get(i);
        cotizarCoberturaObjetoHolder.htmltvLabelCotizacion.setText(this.a.getString(R.string.ID_405X_SEGUROS_LABEL_SUMAASEGURADA, new Object[]{coberturaBean.getDescripcion()}));
        cotizarCoberturaObjetoHolder.tvValueCotizacion.setText(coberturaBean.getSumaAseguradaCober());
        a(cotizarCoberturaObjetoHolder);
    }

    private void a(CotizarCoberturaObjetoHolder cotizarCoberturaObjetoHolder) {
        try {
            cotizarCoberturaObjetoHolder.tvValueCotizacion.setContentDescription(CAccessibility.getInstance((Activity) this.a).applyFilterAmount(cotizarCoberturaObjetoHolder.tvValueCotizacion.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getItemCount() {
        return this.b.size();
    }
}
