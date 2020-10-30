package ar.com.santander.rio.mbanking.app.ui.activities.custodias;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseViewHolder;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Custodia;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;

public class CustodiasAdapter extends Adapter<CustodiasViewHolder> {
    /* access modifiers changed from: private */
    public List<Custodia> a = new ArrayList();
    private ChildClickListener b;

    public interface ChildClickListener {
        void onClick(Custodia custodia);
    }

    public class CustodiasViewHolder extends BaseViewHolder {
        Context m;
        private ChildClickListener o;
        @InjectView(2131366216)
        TextView tvCustodiaDesc;
        @InjectView(2131366220)
        TextView tvCustodiaTenenciaHoy;

        public CustodiasViewHolder(View view, ChildClickListener childClickListener, Context context) {
            super(view);
            this.o = childClickListener;
            this.m = context;
        }

        /* access modifiers changed from: private */
        public void a(Custodia custodia) {
            if (custodia != null) {
                this.tvCustodiaDesc.setText(Html.fromHtml(custodia.getTipoEspecie()));
                this.tvCustodiaTenenciaHoy.setText(custodia.getTenValuadaHoy());
            }
        }

        public void onClick(View view) {
            this.o.onClick((Custodia) CustodiasAdapter.this.a.get(getAdapterPosition()));
        }
    }

    public CustodiasAdapter(ChildClickListener childClickListener) {
        this.b = childClickListener;
    }

    public void setCustodia(List<Custodia> list) {
        this.a.clear();
        this.a.addAll(list);
        notifyDataSetChanged();
    }

    public CustodiasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CustodiasViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_custodias, viewGroup, false), this.b, viewGroup.getContext());
    }

    public void onBindViewHolder(CustodiasViewHolder custodiasViewHolder, int i) {
        custodiasViewHolder.a((Custodia) this.a.get(i));
    }

    public int getItemCount() {
        return this.a.size();
    }
}
