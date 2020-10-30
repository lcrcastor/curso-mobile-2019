package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.IOptionActionBar;
import ar.com.santander.rio.mbanking.app.base.ITRSABaseActivity;
import ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment;
import ar.com.santander.rio.mbanking.app.base.ReceiptEvent;
import ar.com.santander.rio.mbanking.app.base.ReceiptEventBus;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobanteSeguroAccidenteFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.CotizacionSeguroAccidenteFragment;
import ar.com.santander.rio.mbanking.app.ui.interfaces.CotizacionSeguroAccidenteInt;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener.OneClicked;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroAccidenteBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionSeguroAccidenteBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendaSeguroAccidente;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public class ContratarSeguroAccidenteActivity extends ITRSABaseActivity implements IOptionActionBar, CotizacionSeguroAccidenteInt {
    public static final int CONTRATAR_SEGURO_ACCIDENTE = 1003;
    public static final String CONTRATAR_SEGURO_ACCIDENTE_BEAN = "CONTRATAR_SEGURO_ACCIDENTE_BEAN";
    public static final String COTIZACION_SEGURO_ACCIDENTE_BEAN = "COTIZACION_SEGURO_ACCIDENTE_BEAN";
    public static final String LISTA_LEYENDA_SEGURO = "LISTA_LEYENDA_SEGURO";
    public static final String MEDIO_DE_PAGO = "MEDIO_DE_PAGO";
    public static final String NUMERO_CUENTA = "NUMERO_CUENTA";
    public static final String RESPONSE_GET_COTIZACION = "RESPONSE_GET_COTIZACION";
    public static final String RESPONSE_GET_LISTA = "RESPONSE_GET_LISTA";
    public static final String SUCURSAL_CUENTA = "SUCURSAL_CUENTA";
    public static final String TIPO_CUENTA = "TIPO_CUENTA";
    @Inject
    public AnalyticsManager analyticsManager;
    @Inject
    public IDataManager dataManager;
    protected View mActionBar;
    @InjectView(2131364641)
    public DrawerLayout mDrawerLayout;
    @Inject
    SessionManager p;
    @Inject
    SettingsManager q;
    @Inject
    SoftTokenManager r;
    private View s;
    private GetCotizacionSeguroAccidenteBodyResponseBean t;
    private ImageView u;
    private boolean v = false;

    public interface ValidatePermission {
        void showRequestPermissionExplation(int i);
    }

    public interface VisibilityLink {
        void hideLink(boolean z);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_contratar_seguro_accidente);
        getBus().register(this);
        initialize();
        initializePrivateMenuDrawer(this.mDrawerLayout);
    }

    public void initialize() {
        ButterKnife.inject((Activity) this);
        CotizacionSeguroAccidenteBean cotizacionSeguroAccidenteBean = (CotizacionSeguroAccidenteBean) getIntent().getParcelableExtra(RESPONSE_GET_COTIZACION);
        ListaLeyendaSeguroAccidente listaLeyendaSeguroAccidente = new ListaLeyendaSeguroAccidente();
        listaLeyendaSeguroAccidente.setLeyenda(getIntent().getParcelableArrayListExtra(RESPONSE_GET_LISTA));
        this.t = new GetCotizacionSeguroAccidenteBodyResponseBean();
        this.t.setListaLeyendas(listaLeyendaSeguroAccidente);
        this.t.setCotizacion(cotizacionSeguroAccidenteBean);
        showCotizacionSeguroAccidente(this.t);
    }

    public void setProgress(String str) {
        showProgress(str);
    }

    public void hideProgress() {
        dismissProgress();
    }

    public Bus getBus() {
        return getEventBus();
    }

    public void showCotizacionSeguroAccidente(GetCotizacionSeguroAccidenteBodyResponseBean getCotizacionSeguroAccidenteBodyResponseBean) {
        this.t = getCotizacionSeguroAccidenteBodyResponseBean;
        CotizacionSeguroAccidenteFragment cotizacionSeguroAccidenteFragment = new CotizacionSeguroAccidenteFragment(getCotizacionSeguroAccidenteBodyResponseBean);
        cotizacionSeguroAccidenteFragment.setsDisableFragmentAnimations(true);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.content, cotizacionSeguroAccidenteFragment, FragmentConstants.SEGURO_COTIZACION);
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        beginTransaction.commit();
        configActionBarDefault();
    }

    public boolean getTapShareReceipt() {
        return this.v;
    }

    public void setTapShareReceipt(boolean z) {
        this.v = z;
    }

    public void showComprobanteSeguroAccidente(ContratarSeguroAccidenteBean contratarSeguroAccidenteBean, String str) {
        ComprobanteSeguroAccidenteFragment comprobanteSeguroAccidenteFragment = new ComprobanteSeguroAccidenteFragment(contratarSeguroAccidenteBean, this.t, str);
        overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity);
        finish();
        ReceiptEventBus.getInstance().post(new ReceiptEvent((ITRSABaseFragment) comprobanteSeguroAccidenteFragment, "SEGUROS"));
    }

    public void inflateActionBarClose() {
        View customView = getSupportActionBar().getCustomView();
        if (customView != null) {
            this.s = customView.findViewById(R.id.ok);
            if (this.s != null) {
                this.s.setOnClickListener(new OneClickListener(new OneClicked() {
                    public void onClicked(View view) {
                        ContratarSeguroAccidenteActivity.this.onBackPressed();
                    }
                }));
            }
        }
    }

    public void switchDrawer() {
        if (this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer((int) GravityCompat.START);
        } else {
            this.mDrawerLayout.openDrawer((int) GravityCompat.START);
        }
    }

    public void closeDrawer() {
        if (this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer((int) GravityCompat.START);
        }
    }

    public void configActionBarDefault() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.u = (ImageView) this.mActionBar.findViewById(R.id.back_imgButton);
        this.u.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ContratarSeguroAccidenteActivity.this.setResult(0, new Intent());
                ContratarSeguroAccidenteActivity.this.onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager.getBackStackEntryCount() > 0) {
            supportFragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void closeProgres() {
        dismissProgress();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
