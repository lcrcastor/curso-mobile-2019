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
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import java.util.List;

public class EmpresaPagoAdapter extends Adapter<EmpresaPagoViewHolder> {
    /* access modifiers changed from: private */
    public static Context a;
    private List<CnsEmpresaDatosEmpresa> b;
    /* access modifiers changed from: private */
    public OnItemClickListener c;

    public static class EmpresaPagoViewHolder extends ViewHolder {
        private TextView m;

        public EmpresaPagoViewHolder(View view) {
            super(view);
            this.m = (TextView) view.findViewById(R.id.F06_lbl_empresa);
        }

        public void bindTitular(CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa) {
            this.m.setText(cnsEmpresaDatosEmpresa.empDescr);
            try {
                this.m.setContentDescription(CAccessibility.getInstance(EmpresaPagoAdapter.a).applyFilterGeneral(this.m.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public EmpresaPagoAdapter(Context context, List<CnsEmpresaDatosEmpresa> list) {
        a = context;
        this.b = list;
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener) {
        this.c = onItemClickListener;
    }

    public EmpresaPagoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_nuevo_pago_empresa, viewGroup, false);
        inflate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (EmpresaPagoAdapter.this.c != null) {
                    EmpresaPagoAdapter.this.c.onItemClick(view);
                }
            }
        });
        return new EmpresaPagoViewHolder(inflate);
    }

    public void onBindViewHolder(EmpresaPagoViewHolder empresaPagoViewHolder, int i) {
        empresaPagoViewHolder.bindTitular((CnsEmpresaDatosEmpresa) this.b.get(i));
    }

    public int getItemCount() {
        return this.b.size();
    }
}
