package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import ar.com.santander.rio.mbanking.app.base.ITRSABaseActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.PRE_AUTORIZACIONES;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.STATUS_FLAGS;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobantePreAutorizacionDebinFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobantePreAutorizacionDebinFragment.OnFragmentInteractionListener;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConfirmarAceptarRechazarPreAutorizacionDebinFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConfirmarDesconocimientoDePreAutorizacionDebinFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConfirmarPreAutorizacionDebinFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConsultaTenenciaPreAutorizacionesRecibidasFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.DetalleDePreAutorizacionFragmentButtomAceptarRechazar;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.DetalleDePreAutorizacionFragmentButtomDesvincular;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.DetalleDebinFragment;
import ar.com.santander.rio.mbanking.app.ui.utils.PreAutorizacionesDebinUtil;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.DataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.AbmPreautorizacionCompradorEvent;
import ar.com.santander.rio.mbanking.services.events.GetDetallePreAutorizacionCompradorEvent;
import ar.com.santander.rio.mbanking.services.events.GetPreAutorizacionesEvent;
import ar.com.santander.rio.mbanking.services.soap.beans.DetallePreAutorizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PreautorizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetallePreAutorizacionCompradorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VAbmPreautorizacionComprador;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDetallePreAutorizacionComprador;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.otto.Subscribe;
import java.util.List;
import javax.inject.Inject;

public class PreAutorizacionDebinActivity extends ITRSABaseActivity implements OnFragmentInteractionListener, ConfirmarAceptarRechazarPreAutorizacionDebinFragment.OnFragmentInteractionListener, ConfirmarDesconocimientoDePreAutorizacionDebinFragment.OnFragmentInteractionListener, ConfirmarPreAutorizacionDebinFragment.OnFragmentInteractionListener, ConsultaTenenciaPreAutorizacionesRecibidasFragment.OnFragmentInteractionListener, DetalleDePreAutorizacionFragmentButtomAceptarRechazar.OnFragmentInteractionListener, DetalleDePreAutorizacionFragmentButtomDesvincular.OnFragmentInteractionListener, ar.com.santander.rio.mbanking.app.ui.interfaces.PreAutorizacionDebinActivity {
    private String A;
    private DetallePreAutorizacionBean B;
    private ImageView C;
    private boolean D = false;
    private String E = "";
    private String F;
    @InjectView(2131364641)
    DrawerLayout drawerLayout;
    protected View mActionBar;
    ScrollView p;
    @Inject
    AnalyticsManager q;
    @Inject
    SessionManager r;
    @Inject
    SettingsManager s;
    @Inject
    SoftTokenManager t;
    DataManager u;
    private List<ListGroupBean> v;
    private List<ListGroupBean> w;
    private List<ListGroupBean> x;
    private List<ListGroupBean> y;
    private List<ListGroupBean> z;

    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.pre_autorizacion_de_debin);
        b();
        this.E = ((ListGroupBean) this.v.get(0)).getCode();
    }

    /* access modifiers changed from: protected */
    public void onPostResume() {
        super.onPostResume();
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
    }

    private void b() {
        this.u = new DataManager(BaseApplication.get(this), getEventBus(), this.r, this.s);
        ButterKnife.inject((Activity) this);
        Bundle extras = getIntent().getExtras();
        GetPreAutorizacionesBodyResponseBean getPreAutorizacionesBodyResponseBean = (GetPreAutorizacionesBodyResponseBean) extras.getParcelable(DetalleDebinFragment.LIST_PRE_AUTORIZACIONES);
        getPreAutorizacionesBodyResponseBean.getSiguientePagina();
        getPreAutorizacionesBodyResponseBean.getListPreautorizaciones();
        String string = extras.getString(DetalleDebinFragment.MENSAJE_ERROR);
        this.v = this.r.getTableByID(PRE_AUTORIZACIONES.ESTREPEAUT).getListGroupBeans();
        this.w = this.r.getTableByID(PRE_AUTORIZACIONES.CONDEBIN).getListGroupBeans();
        this.z = this.r.getTableByID(PRE_AUTORIZACIONES.MONEDADESCSIMBOLO).getListGroupBeans();
        this.x = this.r.getTableByID(PRE_AUTORIZACIONES.TPOCTACORTA).getListGroupBeans();
        this.y = this.r.getTableByID(PRE_AUTORIZACIONES.PERIODOPREAUT).getListGroupBeans();
        this.z = this.r.getTableByID(PRE_AUTORIZACIONES.MONEDADESCSIMBOLO).getListGroupBeans();
        this.u = new DataManager(BaseApplication.get(this), getEventBus(), this.r, this.s);
        ConsultaTenenciaPreAutorizacionesRecibidasFragment newInstance = ConsultaTenenciaPreAutorizacionesRecibidasFragment.newInstance(this.r, this.s, this.u, string, getPreAutorizacionesBodyResponseBean, ((ListGroupBean) this.v.get(0)).getCode());
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        beginTransaction.replace(R.id.content, newInstance, PRE_AUTORIZACIONES.CONSULTA_TENENCIA_PRE_AUTORIZACIONES_RECIBIDAS);
        beginTransaction.commit();
        configActionBarDefault();
        this.p = (ScrollView) findViewById(R.id.comprobantePreautorizacionDebin);
    }

    public void onBackPressed() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager.getBackStackEntryCount() > 0) {
            supportFragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void configActionBarDefault() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.C = (ImageView) this.mActionBar.findViewById(R.id.back_imgButton);
        this.C.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PreAutorizacionDebinActivity.this.setResult(0, new Intent());
                PreAutorizacionDebinActivity.this.onBackPressed();
            }
        });
    }

    public void configActionBarShared() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        View customView = getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) customView.findViewById(R.id.menu);
        ((ImageView) customView.findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PreAutorizacionDebinActivity.this.switchDrawer();
            }
        });
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ComprobantePreAutorizacionDebinFragment comprobantePreAutorizacionDebinFragment = (ComprobantePreAutorizacionDebinFragment) PreAutorizacionDebinActivity.this.getSupportFragmentManager().findFragmentByTag(PRE_AUTORIZACIONES.COMPROBANTE_PRE_AUTORIZACION_FRAGMENT);
                if (comprobantePreAutorizacionDebinFragment != null && comprobantePreAutorizacionDebinFragment.isVisible()) {
                    comprobantePreAutorizacionDebinFragment.showSharedOptions();
                }
            }
        });
    }

    public void switchDrawer() {
        if (this.drawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            this.drawerLayout.closeDrawer((int) GravityCompat.START);
        } else {
            this.drawerLayout.openDrawer((int) GravityCompat.START);
        }
    }

    public void closeDrawer() {
        if (this.drawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            this.drawerLayout.closeDrawer((int) GravityCompat.START);
        }
    }

    private void c() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        int backStackEntryCount = supportFragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackEntryCount; i++) {
            supportFragmentManager.popBackStackImmediate();
        }
        busquedaAvanzadaPreAutorizaciones(this.E);
    }

    public List<ListGroupBean> getListTableEstpreaut() {
        return this.v;
    }

    public List<ListGroupBean> getListTableConDebin() {
        return this.w;
    }

    public List<ListGroupBean> getListTableMonedaDescSimbolo() {
        return this.z;
    }

    public List<ListGroupBean> getListTableTpoCtaCorta() {
        return this.x;
    }

    public List<ListGroupBean> getListTablePeriodoPreAut() {
        return this.y;
    }

    public void busquedaAvanzadaPreAutorizaciones(String str) {
        this.F = str;
        ConsultaTenenciaPreAutorizacionesRecibidasFragment consultaTenenciaPreAutorizacionesRecibidasFragment = (ConsultaTenenciaPreAutorizacionesRecibidasFragment) getSupportFragmentManager().findFragmentByTag(PRE_AUTORIZACIONES.CONSULTA_TENENCIA_PRE_AUTORIZACIONES_RECIBIDAS);
        if (consultaTenenciaPreAutorizacionesRecibidasFragment != null) {
            consultaTenenciaPreAutorizacionesRecibidasFragment.rechargeList();
        }
    }

    public void updatePreAutorizaciones(String str, String str2) {
        this.F = str;
        showProgress(VGetDetallePreAutorizacionComprador.nameService);
        this.E = str;
        this.u.getPreautorizaciones("C", str, str2);
    }

    public void irADetallePreAutorizacion(PreautorizacionBean preautorizacionBean) {
        showProgress(VGetDetallePreAutorizacionComprador.nameService);
        this.u.getDetallePreautorizacionComprador(preautorizacionBean.getIdPreautorizacion(), preautorizacionBean.getCodEstado());
    }

    private void a(GetDetallePreAutorizacionCompradorBodyResponseBean getDetallePreAutorizacionCompradorBodyResponseBean) {
        configActionBarDefault();
        DetalleDePreAutorizacionFragmentButtomAceptarRechazar newInstance = DetalleDePreAutorizacionFragmentButtomAceptarRechazar.newInstance(this.r, this.s);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable("DETALLE_PREAUTORIZACION_ARG", getDetallePreAutorizacionCompradorBodyResponseBean);
        newInstance.setArguments(bundle);
        beginTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        beginTransaction.replace(R.id.content, newInstance, PRE_AUTORIZACIONES.DETALLE_PRE_AUTORIZACION_COMPRADOR_FRAGMENT);
        beginTransaction.addToBackStack(PRE_AUTORIZACIONES.DETALLE_PRE_AUTORIZACION_COMPRADOR_FRAGMENT);
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        beginTransaction.commit();
    }

    private void b(GetDetallePreAutorizacionCompradorBodyResponseBean getDetallePreAutorizacionCompradorBodyResponseBean) {
        configActionBarDefault();
        DetalleDePreAutorizacionFragmentButtomDesvincular newInstance = DetalleDePreAutorizacionFragmentButtomDesvincular.newInstance(this.r, this.s);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable("DETALLE_PREAUTORIZACION_ARG", getDetallePreAutorizacionCompradorBodyResponseBean);
        newInstance.setArguments(bundle);
        beginTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        beginTransaction.replace(R.id.content, newInstance, PRE_AUTORIZACIONES.DETALLE_PRE_AUTORIZACION_COMPRADOR_FRAGMENT);
        beginTransaction.addToBackStack(PRE_AUTORIZACIONES.DETALLE_PRE_AUTORIZACION_COMPRADOR_FRAGMENT);
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        beginTransaction.commit();
    }

    public void irAlFragmentDetallePreAutorizacionComprador(GetDetallePreAutorizacionCompradorBodyResponseBean getDetallePreAutorizacionCompradorBodyResponseBean) {
        if (getDetallePreAutorizacionCompradorBodyResponseBean.getPreautorizacion().getMostrarAceptarRechazar().equals(STATUS_FLAGS.NEGATIVE) && getDetallePreAutorizacionCompradorBodyResponseBean.getPreautorizacion().getMostrarDesvincular().equals(STATUS_FLAGS.NEGATIVE)) {
            a(getDetallePreAutorizacionCompradorBodyResponseBean);
        }
        if (getDetallePreAutorizacionCompradorBodyResponseBean.getPreautorizacion().getMostrarAceptarRechazar().equals(STATUS_FLAGS.POSITIVE) && getDetallePreAutorizacionCompradorBodyResponseBean.getPreautorizacion().getMostrarDesvincular().equals(STATUS_FLAGS.NEGATIVE)) {
            a(getDetallePreAutorizacionCompradorBodyResponseBean);
        }
        if (getDetallePreAutorizacionCompradorBodyResponseBean.getPreautorizacion().getMostrarDesvincular().equals(STATUS_FLAGS.POSITIVE) && getDetallePreAutorizacionCompradorBodyResponseBean.getPreautorizacion().getMostrarAceptarRechazar().equals(STATUS_FLAGS.NEGATIVE)) {
            b(getDetallePreAutorizacionCompradorBodyResponseBean);
        }
    }

    public void consultaPreAutorizaciones(GetPreAutorizacionesBodyResponseBean getPreAutorizacionesBodyResponseBean, Boolean bool) {
        String siguientePagina = getPreAutorizacionesBodyResponseBean.getSiguientePagina();
        ConsultaTenenciaPreAutorizacionesRecibidasFragment consultaTenenciaPreAutorizacionesRecibidasFragment = (ConsultaTenenciaPreAutorizacionesRecibidasFragment) getSupportFragmentManager().findFragmentByTag(PRE_AUTORIZACIONES.CONSULTA_TENENCIA_PRE_AUTORIZACIONES_RECIBIDAS);
        getEventBus().unregister(this);
        if (bool.booleanValue()) {
            consultaTenenciaPreAutorizacionesRecibidasFragment.clearListPreautorizaciones();
            consultaTenenciaPreAutorizacionesRecibidasFragment.updateListPreautorizacionesBeans(getPreAutorizacionesBodyResponseBean, this.F);
            dismissProgress();
            return;
        }
        consultaTenenciaPreAutorizacionesRecibidasFragment.setListPreautorizacionesBeans(getPreAutorizacionesBodyResponseBean, this.F);
        if (consultaTenenciaPreAutorizacionesRecibidasFragment.isListFinalPosition().booleanValue() || siguientePagina == null) {
            dismissProgress();
        } else {
            updatePreAutorizaciones(this.F, siguientePagina);
        }
    }

    public void pagarPreAutorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean) {
        configActionBarDefault();
        ConfirmarAceptarRechazarPreAutorizacionDebinFragment newInstance = ConfirmarAceptarRechazarPreAutorizacionDebinFragment.newInstance(this.r, this.s, detallePreAutorizacionBean, "A");
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        beginTransaction.replace(R.id.content, newInstance, PRE_AUTORIZACIONES.CONFIRMAR_AUTORZACION_DEBIN_FRAGMENT);
        beginTransaction.addToBackStack(PRE_AUTORIZACIONES.CONFIRMAR_AUTORZACION_DEBIN_FRAGMENT);
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        beginTransaction.commit();
    }

    public void rechazarPreAutorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean) {
        ConfirmarAceptarRechazarPreAutorizacionDebinFragment newInstance = ConfirmarAceptarRechazarPreAutorizacionDebinFragment.newInstance(this.r, this.s, detallePreAutorizacionBean, "R");
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        beginTransaction.replace(R.id.content, newInstance, PRE_AUTORIZACIONES.CONFIRMAR_AUTORZACION_DEBIN_FRAGMENT);
        beginTransaction.addToBackStack(PRE_AUTORIZACIONES.CONFIRMAR_AUTORZACION_DEBIN_FRAGMENT);
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        beginTransaction.commit();
    }

    public void desconocimientoPreAutorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean) {
        ConfirmarAceptarRechazarPreAutorizacionDebinFragment newInstance = ConfirmarAceptarRechazarPreAutorizacionDebinFragment.newInstance(this.r, this.s, detallePreAutorizacionBean, "D");
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        beginTransaction.replace(R.id.content, newInstance, PRE_AUTORIZACIONES.CONFIRMAR_AUTORZACION_DEBIN_FRAGMENT);
        beginTransaction.addToBackStack(PRE_AUTORIZACIONES.CONFIRMAR_AUTORZACION_DEBIN_FRAGMENT);
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        beginTransaction.commit();
    }

    public void desvincularPreAutorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean) {
        ConfirmarAceptarRechazarPreAutorizacionDebinFragment newInstance = ConfirmarAceptarRechazarPreAutorizacionDebinFragment.newInstance(this.r, this.s, detallePreAutorizacionBean, "B");
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        beginTransaction.replace(R.id.content, newInstance, PRE_AUTORIZACIONES.CONFIRMAR_AUTORZACION_DEBIN_FRAGMENT);
        beginTransaction.addToBackStack(PRE_AUTORIZACIONES.CONFIRMAR_AUTORZACION_DEBIN_FRAGMENT);
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        beginTransaction.commit();
    }

    public void procesarPreAutorizacion(String str, DetallePreAutorizacionBean detallePreAutorizacionBean) {
        this.A = str;
        this.B = detallePreAutorizacionBean;
        showProgress(VAbmPreautorizacionComprador.nameService);
        this.u.abmPreautorizacionComprador(str, detallePreAutorizacionBean, this, true);
    }

    /* renamed from: irAComprobanteDeOperación reason: contains not printable characters */
    public void m1irAComprobanteDeOperacin(String str, String str2, String str3, DetallePreAutorizacionBean detallePreAutorizacionBean, String str4) {
        configActionBarShared();
        ComprobantePreAutorizacionDebinFragment newInstance = ComprobantePreAutorizacionDebinFragment.newInstance(this.r, this.s, str, str2, str3, detallePreAutorizacionBean, str4);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        beginTransaction.replace(R.id.content, newInstance, PRE_AUTORIZACIONES.COMPROBANTE_PRE_AUTORIZACION_FRAGMENT);
        beginTransaction.addToBackStack(PRE_AUTORIZACIONES.COMPROBANTE_PRE_AUTORIZACION_FRAGMENT);
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        beginTransaction.commit();
    }

    public void confirmaAccionPreAutorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean, String str) {
        PreAutorizacionesDebinUtil.showDialogConfirmationAbmPreautozicacione(this, str, detallePreAutorizacionBean);
    }

    public void backToPrincipalPage() {
        configActionBarDefault();
        c();
    }

    public void showDialogConfirmationAbmPreAutorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean, String str) {
        PreAutorizacionesDebinUtil.showDialogConfirmationAbmPreautozicacione(this, str, detallePreAutorizacionBean);
    }

    @Subscribe
    public void onGetPreAutorizaciones(GetPreAutorizacionesEvent getPreAutorizacionesEvent) {
        dismissProgress();
        PreAutorizacionesDebinUtil.onGetPreAutorizacionesResult(this, getPreAutorizacionesEvent);
    }

    @Subscribe
    public void onGetDetallePreAutorizacionComprador(GetDetallePreAutorizacionCompradorEvent getDetallePreAutorizacionCompradorEvent) {
        dismissProgress();
        PreAutorizacionesDebinUtil.onGetDetallePreAutorizacionCompradorResult(this, getDetallePreAutorizacionCompradorEvent);
    }

    @Subscribe
    public void onAbmPreAutorizacioneComprador(AbmPreautorizacionCompradorEvent abmPreautorizacionCompradorEvent) {
        dismissProgress();
        PreAutorizacionesDebinUtil.onAbmPreAutorizacioneCompradorResult(this, abmPreautorizacionCompradorEvent, this.A, this.B);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
}
