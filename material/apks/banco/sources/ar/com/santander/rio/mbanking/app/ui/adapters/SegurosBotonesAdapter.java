package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BotonSeguroBean;
import java.util.List;

public class SegurosBotonesAdapter extends Adapter<BotonesViewHolder> {
    private List<BotonSeguroBean> a;
    /* access modifiers changed from: private */
    public AdapterListenes b;

    public interface AdapterListenes {
        void onClickItemListener(View view, BotonSeguroBean botonSeguroBean);
    }

    public class BotonesViewHolder extends ViewHolder {
        private TextView n;
        private TextView o;
        /* access modifiers changed from: private */
        public BotonSeguroBean p;

        BotonesViewHolder(View view) {
            super(view);
            this.n = (TextView) view.findViewById(R.id.tvNombeBotonSeguro);
            this.o = (TextView) view.findViewById(R.id.tvDescBotonSeguro);
        }

        /* access modifiers changed from: 0000 */
        public void a(BotonSeguroBean botonSeguroBean) {
            this.n.setText(Html.fromHtml(botonSeguroBean.getNombre()));
            this.o.setText(Html.fromHtml(botonSeguroBean.getDescripcion()));
            this.p = botonSeguroBean;
        }
    }

    public SegurosBotonesAdapter(List<BotonSeguroBean> list, AdapterListenes adapterListenes) {
        this.a = list;
        this.b = adapterListenes;
    }

    public BotonesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_seguro_boton, viewGroup, false);
        final BotonesViewHolder botonesViewHolder = new BotonesViewHolder(inflate);
        inflate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    SegurosBotonesAdapter.this.b.onClickItemListener(view, botonesViewHolder.p);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return botonesViewHolder;
    }

    public void onBindViewHolder(BotonesViewHolder botonesViewHolder, int i) {
        botonesViewHolder.a((BotonSeguroBean) this.a.get(i));
    }

    public int getItemCount() {
        return this.a.size();
    }
}
