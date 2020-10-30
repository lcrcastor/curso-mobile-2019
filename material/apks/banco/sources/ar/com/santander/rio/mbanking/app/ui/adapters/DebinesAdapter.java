package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.PRE_AUTORIZACIONES;
import ar.com.santander.rio.mbanking.components.OnSingleClickListener;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListDebinesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.util.List;

public class DebinesAdapter extends Adapter<DebinesAdapterViewHolder> {
    static Context a;
    /* access modifiers changed from: private */
    public static String b;
    private List<ListDebinesBean> c;
    /* access modifiers changed from: private */
    public OnItemClickListener d;
    private SessionManager e;

    public static class DebinesAdapterViewHolder extends ViewHolder {
        private RelativeLayout m;
        private TextView n;
        private TextView o;
        private LinearLayout p;
        private RelativeLayout q;

        public DebinesAdapterViewHolder(View view) {
            super(view);
            this.m = (RelativeLayout) view.findViewById(R.id.debin_row);
            this.n = (TextView) view.findViewById(R.id.txt_solicitanteDebin);
            this.o = (TextView) view.findViewById(R.id.txt_importeDebin);
            this.p = (LinearLayout) view.findViewById(R.id.last_item_list_debin);
            this.q = (RelativeLayout) view.findViewById(R.id.debin_row_data);
        }

        private String a(String str, String str2, List<ListTableBean> list) {
            String str3 = new String();
            for (ListTableBean listTableBean : list) {
                if (listTableBean.getIdTable().equalsIgnoreCase(str2)) {
                    for (ListGroupBean listGroupBean : listTableBean.getListGroupBeans()) {
                        if (listGroupBean.getCode().equalsIgnoreCase(str)) {
                            str3 = listGroupBean.getLabel();
                        }
                    }
                }
            }
            return str3;
        }

        public void bindItem(ListDebinesBean listDebinesBean, SessionManager sessionManager) {
            String str;
            if (listDebinesBean.getIdDebin() != null) {
                this.p.setVisibility(8);
                this.q.setVisibility(0);
                this.n.setText(listDebinesBean.getTitular());
                if (listDebinesBean.getMoneda() == null) {
                    str = UtilsCuentas.SEPARAOR2;
                } else {
                    str = a(listDebinesBean.getMoneda(), PRE_AUTORIZACIONES.MONEDADESCSIMBOLO, sessionManager.getConsDescripciones().getListTableBeans());
                }
                TextView textView = this.o;
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(listDebinesBean.getImporte());
                textView.setText(sb.toString());
                try {
                    this.o.setContentDescription(CAccessibility.getInstance(DebinesAdapter.a).applyFilterGeneral(this.o.getText().toString()));
                    this.n.setContentDescription(CAccessibility.getInstance(DebinesAdapter.a).applyFilterGeneral(this.n.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                this.p.setVisibility(0);
                this.p.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                    }
                });
                this.q.setVisibility(8);
                if (DebinesAdapter.b == null) {
                    this.p.findViewById(R.id.inc_loading_error).setVisibility(8);
                    this.p.findViewById(R.id.inc_loading_spinner).setVisibility(0);
                    return;
                }
                this.p.findViewById(R.id.inc_loading_error).setVisibility(0);
                this.p.findViewById(R.id.inc_loading_spinner).setVisibility(8);
                ((TextView) this.p.findViewById(R.id.inc_loading_error)).setText(DebinesAdapter.b);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public DebinesAdapter(Context context, List<ListDebinesBean> list, SessionManager sessionManager) {
        a = context;
        this.c = list;
        this.e = sessionManager;
        b = null;
    }

    public static void setErrorMessage(String str) {
        b = str;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.d = onItemClickListener;
    }

    public DebinesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_debin_recibidas, viewGroup, false);
        inflate.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                DebinesAdapter.this.d.onItemClick(view);
            }
        });
        return new DebinesAdapterViewHolder(inflate);
    }

    public int getItemCount() {
        return this.c.size();
    }

    public void onBindViewHolder(DebinesAdapterViewHolder debinesAdapterViewHolder, int i) {
        debinesAdapterViewHolder.bindItem((ListDebinesBean) this.c.get(i), this.e);
    }
}
