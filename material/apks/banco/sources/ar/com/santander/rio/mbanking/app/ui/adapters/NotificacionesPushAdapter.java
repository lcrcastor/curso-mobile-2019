package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.components.OnSingleClickListener;
import ar.com.santander.rio.mbanking.services.soap.beans.body.NotificacionPushBean;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.util.List;

public class NotificacionesPushAdapter extends Adapter<NotificacionesPushAdapterViewHolder> {
    static Context a;
    private List<NotificacionPushBean> b;
    /* access modifiers changed from: private */
    public OnItemClickListener c;

    public static class NotificacionesPushAdapterViewHolder extends ViewHolder {
        private RelativeLayout m;
        private TextView n;
        private TextView o;
        private LinearLayout p;
        private RelativeLayout q;

        public NotificacionesPushAdapterViewHolder(View view) {
            super(view);
            this.m = (RelativeLayout) view.findViewById(R.id.rll_row);
            this.n = (TextView) view.findViewById(R.id.lbl_fecha);
            this.o = (TextView) view.findViewById(R.id.lbl_mensaje);
            this.p = (LinearLayout) view.findViewById(R.id.inc_loading);
            this.q = (RelativeLayout) view.findViewById(R.id.rll_row_data);
        }

        public void bindItem(NotificacionPushBean notificacionPushBean) {
            if (notificacionPushBean.getIdNotificacion() != null) {
                this.p.setVisibility(8);
                this.q.setVisibility(0);
                this.n.setText(notificacionPushBean.getFechaCreacion());
                try {
                    TextView textView = this.n;
                    StringBuilder sb = new StringBuilder();
                    sb.append(NotificacionesPushAdapter.a.getString(R.string.ID400X_F28_00_LBL_ACC_FECHA_NOVEDAD));
                    sb.append(UtilsCuentas.SEPARAOR2);
                    sb.append(CAccessibility.getInstance(NotificacionesPushAdapter.a).applyFilterDate(this.n.getText().toString()));
                    textView.setContentDescription(sb.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.o.setText(Html.fromHtml(notificacionPushBean.getMensaje()));
                try {
                    this.o.setContentDescription(CAccessibility.getInstance(NotificacionesPushAdapter.a).applyFilterGeneral(this.o.getText().toString()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (TextUtils.isEmpty(notificacionPushBean.getFechaApertura()) || notificacionPushBean.getFechaApertura().equalsIgnoreCase("null")) {
                    this.m.setBackgroundColor(NotificacionesPushAdapter.a.getResources().getColor(R.color.container_area_light_gray));
                    this.o.setTypeface(Typeface.createFromAsset(NotificacionesPushAdapter.a.getAssets(), "fonts/OpenSans-Bold.otf"));
                    return;
                }
                this.m.setBackgroundColor(NotificacionesPushAdapter.a.getResources().getColor(R.color.white));
                this.o.setTypeface(Typeface.createFromAsset(NotificacionesPushAdapter.a.getAssets(), "fonts/OpenSans-Regular.otf"));
                return;
            }
            this.p.setVisibility(0);
            this.q.setVisibility(8);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public NotificacionesPushAdapter(Context context, List<NotificacionPushBean> list) {
        a = context;
        this.b = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.c = onItemClickListener;
    }

    public NotificacionesPushAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_notificaciones_push, viewGroup, false);
        inflate.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                NotificacionesPushAdapter.this.c.onItemClick(view);
            }
        });
        return new NotificacionesPushAdapterViewHolder(inflate);
    }

    public int getItemCount() {
        return this.b.size();
    }

    public void onBindViewHolder(NotificacionesPushAdapterViewHolder notificacionesPushAdapterViewHolder, int i) {
        notificacionesPushAdapterViewHolder.bindItem((NotificacionPushBean) this.b.get(i));
    }
}
