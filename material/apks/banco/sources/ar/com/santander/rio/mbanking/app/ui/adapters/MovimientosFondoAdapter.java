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
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovimientoFondosBean;
import java.util.List;

public class MovimientosFondoAdapter extends Adapter<MovimientosFondoViewHolder> {
    /* access modifiers changed from: private */
    public static Context a;
    private List<MovimientoFondosBean> b;
    /* access modifiers changed from: private */
    public OnItemClickListener c;

    public static class MovimientosFondoViewHolder extends ViewHolder {
        private TextView m;
        private TextView n;
        private TextView o;

        public MovimientosFondoViewHolder(View view) {
            super(view);
            this.m = (TextView) view.findViewById(R.id.F24_06_lst_fecha);
            this.n = (TextView) view.findViewById(R.id.F24_06_lst_concepto);
            this.o = (TextView) view.findViewById(R.id.F24_06_lst_importe);
        }

        public void bindFondo(MovimientoFondosBean movimientoFondosBean) {
            this.m.setText(movimientoFondosBean.getFecha());
            try {
                this.m.setContentDescription(CAccessibility.getInstance(MovimientosFondoAdapter.a).applyFilterDate(this.m.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.n.setText(movimientoFondosBean.getConcepto());
            try {
                this.n.setContentDescription(CAccessibility.getInstance(MovimientosFondoAdapter.a).applyFilterGeneral(this.n.getText().toString()).replace("susc.", "suscripción").replace("resc.", "rescate"));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.o.setText(movimientoFondosBean.getImporte().toUpperCase());
            try {
                this.o.setContentDescription(CAccessibility.getInstance(MovimientosFondoAdapter.a).applyFilterAmount(this.o.getText().toString()));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public MovimientosFondoAdapter(Context context, List<MovimientoFondosBean> list) {
        a = context;
        this.b = list;
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener) {
        this.c = onItemClickListener;
    }

    public MovimientosFondoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_movimientos_fondo, viewGroup, false);
        inflate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MovimientosFondoAdapter.this.c != null) {
                    MovimientosFondoAdapter.this.c.onItemClick(view);
                }
            }
        });
        return new MovimientosFondoViewHolder(inflate);
    }

    public void onBindViewHolder(MovimientosFondoViewHolder movimientosFondoViewHolder, int i) {
        movimientosFondoViewHolder.bindFondo((MovimientoFondosBean) this.b.get(i));
    }

    public int getItemCount() {
        return this.b.size();
    }
}
