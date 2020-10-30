package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.module.marcacionPorViaje.SeleccionarTarjetasMarcacionPresenter;
import ar.com.santander.rio.mbanking.app.module.marcacionPorViaje.SeleccionarTarjetasMarcacionView;
import ar.com.santander.rio.mbanking.app.ui.adapters.TarjetasMarcacionAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UsuarioMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UsuariosMarcacionBean;
import ar.com.santander.rio.mbanking.view.AnimatedExpandableListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class SeleccionarTarjetasMarcacionActivity extends BaseMvpActivity implements SeleccionarTarjetasMarcacionView {
    @InjectView(2131363360)
    Button btnGuardar;
    @InjectView(2131363369)
    TextView lblTitulo;
    @InjectView(2131363362)
    AnimatedExpandableListView listaTarjetas;
    @Inject
    SessionManager p;
    @Inject
    AnalyticsManager q;
    private SeleccionarTarjetasMarcacionPresenter r;
    /* access modifiers changed from: private */
    public TarjetasMarcacionAdapter s;
    private List<String> t;
    private HashMap<String, List<TarjetaMarcacionBean>> u;
    /* access modifiers changed from: private */
    public String v;
    private boolean w = false;
    private boolean x = false;
    private boolean y = false;

    public void clearScreenData() {
    }

    public void configureLayout() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_seleccionar_tarjetas_marcacion);
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
    }

    public void initialize() {
        this.r = new SeleccionarTarjetasMarcacionPresenter(this.mBus, this.mDataManager, this);
        this.r.attachView(this);
        this.v = getIntent().getStringExtra(MarcacionViajeConstants.cINTENT_EXTRA_ACCION);
        this.r.onCreatePage((UsuariosMarcacionBean) getIntent().getParcelableExtra(MarcacionViajeConstants.cINTENT_EXTRA_USUARIOS));
    }

    public void setSeleccionarTarjetaMarcacionView(UsuariosMarcacionBean usuariosMarcacionBean) {
        b(usuariosMarcacionBean);
        a(usuariosMarcacionBean);
        if (this.v.equals("1") || this.v.equals("3") || this.v.equals("4")) {
            this.lblTitulo.setText(getString(R.string.ID3991_MARCACION_VIAJE_TITLE_SELECCIONAR_TARJETAS));
            this.q.trackScreen(getString(R.string.analytics_screen_name_travels_tarjetas_habilitar));
            this.btnGuardar.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SeleccionarTarjetasMarcacionActivity.this.q.trackEvent(SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_guardar_boton), SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_guardar_tarjetas));
                    SeleccionarTarjetasMarcacionActivity.this.c(-1);
                }
            });
            return;
        }
        this.lblTitulo.setText(getString(R.string.ID3994_MARCACION_VIAJE_TITTLE_TARJETAS_SELECCIONADAS));
        this.btnGuardar.setVisibility(8);
        this.q.trackScreen(getString(R.string.analytics_screen_name_travels_tarjetas_habilitadas));
    }

    private void a(UsuariosMarcacionBean usuariosMarcacionBean) {
        TarjetasMarcacionAdapter tarjetasMarcacionAdapter = new TarjetasMarcacionAdapter(getApplicationContext(), this.t, this.u, this.v, this.q);
        this.s = tarjetasMarcacionAdapter;
        this.listaTarjetas.setAdapter(this.s);
        for (int i = 0; i < this.t.size(); i++) {
            this.listaTarjetas.expandGroup(i);
        }
        this.listaTarjetas.setOnGroupClickListener(new OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                if (SeleccionarTarjetasMarcacionActivity.this.listaTarjetas.isGroupExpanded(i)) {
                    SeleccionarTarjetasMarcacionActivity.this.listaTarjetas.collapseGroupWithAnimation(i);
                } else {
                    SeleccionarTarjetasMarcacionActivity.this.listaTarjetas.expandGroupWithAnimation(i);
                }
                return true;
            }
        });
        for (Tarjeta tarjeta : this.p.getLoginUnico().getProductos().getTarjetas().getTarjetas()) {
            if (tarjeta.getClase().equals("N")) {
                this.w = true;
            }
            if (tarjeta.getClase().equals("H")) {
                this.x = true;
            }
        }
        Iterator it = usuariosMarcacionBean.getListaUsuarios().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            UsuarioMarcacionBean usuarioMarcacionBean = (UsuarioMarcacionBean) it.next();
            if (usuarioMarcacionBean.getOtrasTarjetas() != null && usuarioMarcacionBean.getOtrasTarjetas().equalsIgnoreCase("S")) {
                this.y = true;
                break;
            }
        }
        View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.layout_tarjetas_marcacion_leyenda, null, false);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.F26_04_LNL_LEYENDA_1_WRAPPER);
        LinearLayout linearLayout2 = (LinearLayout) inflate.findViewById(R.id.F26_04_LNL_LEYENDA_2_WRAPPER);
        LinearLayout linearLayout3 = (LinearLayout) inflate.findViewById(R.id.F26_04_LNL_LEYENDA_3_WRAPPER);
        this.listaTarjetas.addFooterView(inflate);
        if (this.w) {
            linearLayout.setVisibility(0);
        } else {
            linearLayout.setVisibility(8);
        }
        if (this.x) {
            linearLayout2.setVisibility(0);
        } else {
            linearLayout2.setVisibility(8);
        }
        if (this.y) {
            linearLayout3.setVisibility(0);
        } else {
            linearLayout3.setVisibility(8);
        }
    }

    public void onBackPressed() {
        if (this.v.equals("0") || this.v.equals("2") || !this.s.getHuboCambios().booleanValue()) {
            c(0);
        } else {
            b();
        }
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

    public void attachView() {
        if (!this.r.isViewAttached()) {
            this.r.attachView(this);
        }
    }

    public void detachView() {
        if (this.r.isViewAttached()) {
            this.r.detachView();
        }
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.CANCEL_WITH_CONFIRMAR);
        View customView = getSupportActionBar().getCustomView();
        View findViewById = customView.findViewById(R.id.cancel_imgButton);
        View findViewById2 = customView.findViewById(R.id.confirm_imgButton);
        if (findViewById2 != null) {
            if (this.v.equals("0") || this.v.equals("2")) {
                findViewById2.setVisibility(8);
            } else {
                findViewById2.setVisibility(0);
                findViewById2.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        SeleccionarTarjetasMarcacionActivity.this.q.trackEvent(SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_guardar_tilde), SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_guardar_tarjetas));
                        SeleccionarTarjetasMarcacionActivity.this.c(-1);
                    }
                });
            }
        }
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (SeleccionarTarjetasMarcacionActivity.this.v.equals("0") || SeleccionarTarjetasMarcacionActivity.this.v.equals("2")) {
                        SeleccionarTarjetasMarcacionActivity.this.q.trackEvent(SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_cerrar), SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_consulta_tarjetas));
                        SeleccionarTarjetasMarcacionActivity.this.c(0);
                    } else if (SeleccionarTarjetasMarcacionActivity.this.s.getHuboCambios().booleanValue()) {
                        SeleccionarTarjetasMarcacionActivity.this.q.trackEvent(SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_cerrar), SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_no_guardar_tarjetas));
                        SeleccionarTarjetasMarcacionActivity.this.b();
                    } else {
                        SeleccionarTarjetasMarcacionActivity.this.c(0);
                    }
                }
            });
        }
    }

    private void b(UsuariosMarcacionBean usuariosMarcacionBean) {
        this.t = new ArrayList();
        this.u = new HashMap<>();
        for (UsuarioMarcacionBean usuarioMarcacionBean : usuariosMarcacionBean.getListaUsuarios()) {
            this.t.add(usuarioMarcacionBean.getNombre());
            this.u.put(usuarioMarcacionBean.getNombre(), usuarioMarcacionBean.getTarjetas().getListaTarjetas());
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpCancelar", getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.IDXX_MARCACION_VIAJE_CANCELAR_SELECCION), getString(R.string.IDX_ALERT_BTN_YES), getString(R.string.IDX_ALERT_BTN_NO));
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                newInstance.dismiss();
                SeleccionarTarjetasMarcacionActivity.this.q.trackEvent(SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_si), SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_no_guardar_tarjetas));
                SeleccionarTarjetasMarcacionActivity.this.c(0);
            }

            public void onNegativeButton() {
                newInstance.dismiss();
                SeleccionarTarjetasMarcacionActivity.this.q.trackEvent(SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_no), SeleccionarTarjetasMarcacionActivity.this.getString(R.string.analytics_action_travels_guardar_tarjetas));
            }
        });
        newInstance.show(getSupportFragmentManager(), "popUpCancelar");
    }

    /* access modifiers changed from: private */
    public void c(int i) {
        Intent intent = new Intent();
        if (i == -1) {
            intent.putExtra(MarcacionViajeConstants.cINTENT_EXTRA_USUARIOS, this.s.obtenerTarjetasActualizadas());
        }
        setResult(i, intent);
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }
}
