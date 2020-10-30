package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.PreautorizacionBean;
import java.util.List;

public class PreautorizacionDebinAdapter extends Adapter<ViewHolder> {
    private static String b;
    private Context a;
    private List<PreautorizacionBean> c;
    private OnItemClickListener d;
    private SessionManager e;

    public interface OnItemClickListener {
        void onPreAutorizacionSelected(View view, PreautorizacionBean preautorizacionBean);
    }

    public static class PreautorizacionesDebinAdapterViewHolder extends ViewHolder implements OnClickListener {
        private RelativeLayout m;
        private TextView n;
        private TextView o;
        private LinearLayout p;
        private RelativeLayout q;
        private OnItemClickListener r;
        private PreautorizacionBean s;

        public PreautorizacionesDebinAdapterViewHolder(View view) {
            super(view);
            this.m = (RelativeLayout) view.findViewById(R.id.debin_row);
            this.n = (TextView) view.findViewById(R.id.txt_solicitanteDebin);
            this.o = (TextView) view.findViewById(R.id.txt_importeDebin);
            this.p = (LinearLayout) view.findViewById(R.id.last_item_list_debin);
            this.q = (RelativeLayout) view.findViewById(R.id.debin_row_data);
        }

        public PreautorizacionBean getPreautorizacionBean() {
            return this.s;
        }

        public void bindItem(PreautorizacionBean preautorizacionBean, SessionManager sessionManager, Context context, OnItemClickListener onItemClickListener) {
            if (preautorizacionBean.getIdPreautorizacion() != null) {
                this.s = preautorizacionBean;
                this.p.setVisibility(8);
                this.q.setVisibility(0);
                this.r = onItemClickListener;
                this.q.setOnClickListener(this);
                this.n.setText(preautorizacionBean.getVendedorBean().getCuit());
                if (preautorizacionBean.getMoneda() != null) {
                    preautorizacionBean.getMoneda();
                }
                try {
                    this.o.setContentDescription(CAccessibility.getInstance(context).applyFilterGeneral(this.o.getText().toString()));
                    this.n.setContentDescription(CAccessibility.getInstance(context).applyFilterCharacterToCharacter(this.n.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void onClick(View view) {
            this.r.onPreAutorizacionSelected(this.itemView, getPreautorizacionBean());
        }
    }

    public static class ProgressViewHolder extends ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View view) {
            super(view);
            this.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }

    public PreautorizacionDebinAdapter(List<PreautorizacionBean> list, Context context, SessionManager sessionManager, OnItemClickListener onItemClickListener) {
        this.a = context;
        this.c = list;
        this.e = sessionManager;
        this.d = onItemClickListener;
        b = null;
    }

    public static void setErrorMessage(String str) {
        b = str;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.d = onItemClickListener;
    }

    public int getItemViewType(int i) {
        return this.c.get(i) != null ? 0 : 1;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new PreautorizacionesDebinAdapterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_debin_recibidas, viewGroup, false));
        }
        return new ProgressViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_loading_debin, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        PreautorizacionBean preautorizacionBean = (PreautorizacionBean) this.c.get(i);
        if (viewHolder instanceof PreautorizacionesDebinAdapterViewHolder) {
            ((PreautorizacionesDebinAdapterViewHolder) viewHolder).bindItem(preautorizacionBean, this.e, this.a, this.d);
        }
    }

    public int getItemCount() {
        if (this.c != null) {
            return this.c.size();
        }
        return 0;
    }
}
