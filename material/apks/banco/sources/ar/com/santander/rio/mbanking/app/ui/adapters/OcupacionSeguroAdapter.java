package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.services.soap.beans.body.OcupacionBean;
import java.util.List;

public class OcupacionSeguroAdapter extends Adapter<EmpresaPagoViewHolder> {
    /* access modifiers changed from: private */
    public Context a;
    private List<OcupacionBean> b;
    /* access modifiers changed from: private */
    public OnItemClickListener c;
    /* access modifiers changed from: private */
    public OcupacionBean d;

    class EmpresaPagoViewHolder extends ViewHolder {
        private TextView n;
        private ImageView o;

        private EmpresaPagoViewHolder(View view) {
            super(view);
            this.n = (TextView) view.findViewById(R.id.F27_12_LBL_ITEM_OCUPACION);
            this.o = (ImageView) view.findViewById(R.id.F27_12_IMG_SELECTED);
        }

        /* access modifiers changed from: private */
        public void a(OcupacionBean ocupacionBean) {
            this.n.setText(Html.fromHtml(ocupacionBean.getDescOcupacion()));
            try {
                this.n.setContentDescription(CAccessibility.getInstance(OcupacionSeguroAdapter.this.a).applyFilterGeneral(this.n.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (OcupacionSeguroAdapter.this.d == null || !this.n.getText().toString().equalsIgnoreCase(Html.fromHtml(OcupacionSeguroAdapter.this.d.getDescOcupacion()).toString())) {
                this.o.setBackground(OcupacionSeguroAdapter.this.a.getResources().getDrawable(R.drawable.ic_checkbox_off_gris));
            } else {
                this.o.setBackground(OcupacionSeguroAdapter.this.a.getResources().getDrawable(R.drawable.ic_checkbox_on_rojo));
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public OcupacionSeguroAdapter(Context context, List<OcupacionBean> list, OcupacionBean ocupacionBean) {
        this.a = context;
        this.b = list;
        this.d = ocupacionBean;
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener) {
        this.c = onItemClickListener;
    }

    public EmpresaPagoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_ocupacion_seguro, viewGroup, false);
        inflate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (OcupacionSeguroAdapter.this.c != null) {
                    OcupacionSeguroAdapter.this.c.onItemClick(view);
                }
            }
        });
        return new EmpresaPagoViewHolder(inflate);
    }

    public void onBindViewHolder(EmpresaPagoViewHolder empresaPagoViewHolder, int i) {
        empresaPagoViewHolder.a((OcupacionBean) this.b.get(i));
    }

    public int getItemCount() {
        return this.b.size();
    }
}
