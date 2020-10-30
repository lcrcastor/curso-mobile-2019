package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaVendedor;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import java.util.List;

public class GestionAdhesionCuentasDebinAdapter extends Adapter<SeleccionarCuentaViewHolder> {
    /* access modifiers changed from: private */
    public static Context b;
    /* access modifiers changed from: private */
    public static SessionManager c;
    /* access modifiers changed from: private */
    public static ShapeDrawable d;
    /* access modifiers changed from: private */
    public static ShapeDrawable e;
    /* access modifiers changed from: private */
    public static OnItemClickListener f;
    AnalyticsManager a;
    private List<CuentaVendedor> g;

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public static class SeleccionarCuentaViewHolder extends ViewHolder {
        private TextView m;
        private TextView n;
        private ImageView o;
        private ImageView p;
        /* access modifiers changed from: private */
        public AnalyticsManager q;

        public SeleccionarCuentaViewHolder(View view, AnalyticsManager analyticsManager) {
            super(view);
            this.m = (TextView) view.findViewById(R.id.F32_02_TXT_NUMERO_CUENTA);
            this.n = (TextView) view.findViewById(R.id.F32_02_TXT_TIPO_CUENTA);
            this.o = (ImageView) view.findViewById(R.id.F32_02_IMG_SEMAFORO);
            this.p = (ImageView) view.findViewById(R.id.F32_02_IMG_ACCION);
            this.q = analyticsManager;
        }

        public void bindCuenta(final CuentaVendedor cuentaVendedor) {
            this.m.setText(UtilAccount.getAccountFormat(cuentaVendedor.getSucursal(), cuentaVendedor.getNumero()));
            try {
                this.m.setContentDescription(CAccessibility.getInstance(GestionAdhesionCuentasDebinAdapter.b).applyFilterCharacterToCharacter(this.m.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.n.setText(UtilAccount.getAccountTypeDescription(GestionAdhesionCuentasDebinAdapter.c, "", cuentaVendedor.getTipo()));
            try {
                this.n.setContentDescription(this.n.getText());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (cuentaVendedor.getStatusAdhesion().intValue() == 0) {
                this.o.setImageDrawable(GestionAdhesionCuentasDebinAdapter.d);
                this.o.setContentDescription(GestionAdhesionCuentasDebinAdapter.b.getString(R.string.ACCESSIBILITY_DEBIN_CUENTA_ADHERIDA));
                this.p.setImageResource(R.drawable.delete);
                this.p.setContentDescription(GestionAdhesionCuentasDebinAdapter.b.getString(R.string.ACCESSIBILITY_DEBIN_ACCION_REMOVER_ADHESION_CUENTA));
            } else {
                this.o.setImageDrawable(GestionAdhesionCuentasDebinAdapter.e);
                this.o.setContentDescription(GestionAdhesionCuentasDebinAdapter.b.getString(R.string.ACCESSIBILITY_DEBIN_CUENTA_NO_ADHERIDA));
                this.p.setImageResource(R.drawable.plus);
                this.p.setContentDescription(GestionAdhesionCuentasDebinAdapter.b.getString(R.string.ACCESSIBILITY_DEBIN_ACCION_ADHERIR_CUENTA));
            }
            this.p.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (GestionAdhesionCuentasDebinAdapter.f != null) {
                        if (cuentaVendedor.getStatusAdhesion().intValue() == 0) {
                            SeleccionarCuentaViewHolder.this.q.trackEvent(GestionAdhesionCuentasDebinAdapter.b.getString(R.string.analytics_trackevent_category_debin), GestionAdhesionCuentasDebinAdapter.b.getString(R.string.analytics_trackevent_action_click), GestionAdhesionCuentasDebinAdapter.b.getString(R.string.analytics_trackevent_label_Desvincular_cuenta));
                        } else {
                            SeleccionarCuentaViewHolder.this.q.trackEvent(GestionAdhesionCuentasDebinAdapter.b.getString(R.string.analytics_trackevent_category_debin), GestionAdhesionCuentasDebinAdapter.b.getString(R.string.analytics_trackevent_action_click), GestionAdhesionCuentasDebinAdapter.b.getString(R.string.analytics_trackevent_label_Adherir_cuenta));
                        }
                        GestionAdhesionCuentasDebinAdapter.f.onItemClick(SeleccionarCuentaViewHolder.this.itemView);
                    }
                }
            });
        }
    }

    public GestionAdhesionCuentasDebinAdapter(Context context, SessionManager sessionManager, List<CuentaVendedor> list, AnalyticsManager analyticsManager) {
        b = context;
        this.g = list;
        c = sessionManager;
        d = drawCircle(b, 13, 13, b.getResources().getColor(R.color.green));
        e = drawCircle(b, 13, 13, b.getResources().getColor(R.color.red_light));
        this.a = analyticsManager;
    }

    public static ShapeDrawable drawCircle(Context context, int i, int i2, int i3) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.setIntrinsicHeight(i2);
        shapeDrawable.setIntrinsicWidth(i);
        shapeDrawable.getPaint().setColor(i3);
        return shapeDrawable;
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener) {
        f = onItemClickListener;
    }

    public SeleccionarCuentaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SeleccionarCuentaViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_gestion_adhesion_cuentas_debin, viewGroup, false), this.a);
    }

    public void onBindViewHolder(SeleccionarCuentaViewHolder seleccionarCuentaViewHolder, int i) {
        seleccionarCuentaViewHolder.bindCuenta((CuentaVendedor) this.g.get(i));
    }

    public int getItemCount() {
        return this.g.size();
    }
}
