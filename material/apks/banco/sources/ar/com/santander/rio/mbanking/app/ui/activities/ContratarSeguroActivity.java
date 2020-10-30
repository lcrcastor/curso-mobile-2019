package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment;
import ar.com.santander.rio.mbanking.app.base.ReceiptEvent;
import ar.com.santander.rio.mbanking.app.base.ReceiptEventBus;
import ar.com.santander.rio.mbanking.app.module.seguros.ContratarSeguroPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.ContratarSeguroView;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants.TipoAlta;
import ar.com.santander.rio.mbanking.app.ui.fragments.ErrorFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobanteSeguroObjetoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ListaSegurosFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroObjetoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FamiliaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SegurosBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizacionSeguroMovil;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultValues;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultsKeys;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.TestBatteryActivity;
import butterknife.ButterKnife;
import com.squareup.otto.Bus;
import java.util.ArrayList;
import javax.inject.Inject;

public class ContratarSeguroActivity extends BaseMvpActivity implements ContratarSeguroView {
    public static final String ACCESS_PHONE_STATUS_DIALOG_TAG = "ACCESS_PHONE_STATUS_DIALOG_TAG";
    public static final String KEY_URL = "KEY_URL";
    public static final int READ_PHONE_STATE_REQUEST_PERMISSION = 99;
    @Inject
    public AnalyticsManager analyticsManager;
    public ContratarSeguroPresenter contratarSeguroPresenter;
    String p = "";
    String q;
    SegurosBean r = new SegurosBean();
    SegurosBean s;
    @Inject
    public SessionManager sessionManager;
    private boolean t = false;
    private boolean u = false;

    public void clearScreenData() {
    }

    public void configureLayout() {
    }

    public Context getContext() {
        return this;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        getEventBus().register(this);
        setContentView((int) R.layout.layout_contratar_seguro_activity);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
    }

    public void initialize() {
        this.contratarSeguroPresenter = new ContratarSeguroPresenter(this.mBus, this.mDataManager, this);
        attachView();
        this.q = getIntent().getStringExtra(SegurosConstants.INTENT_TIPO_ALTA_MOVIL);
        this.r = (SegurosBean) getIntent().getParcelableExtra(SegurosConstants.INTENT_LISTA_SEGUROS);
        this.u = getIntent().getBooleanExtra(SegurosConstants.INTENT_SEGUROS_CARTERA, false);
        this.s = (SegurosBean) getIntent().getParcelableExtra(SegurosConstants.INTENT_LISTA_TOTAL_SEGUROS);
        this.contratarSeguroPresenter.onCreatePage(this.q, this.r);
        b();
    }

    private void b() {
        ListaSegurosFragment listaSegurosFragment = new ListaSegurosFragment(this.s, getIntent().getStringExtra(SegurosConstants.SEGURO_DEEPLINK_ACTION));
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.content, listaSegurosFragment, FragmentConstants.SEGURO_LISTA);
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        beginTransaction.commit();
    }

    public void configureActionBar() {
        c();
    }

    private void c() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.back_imgButton);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ContratarSeguroActivity.this.onBackPressed();
                }
            });
        }
    }

    public Bus getBus() {
        return getEventBus();
    }

    public void configActionBarDefault() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        ((ImageView) this.mActionBar.findViewById(R.id.back_imgButton)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ContratarSeguroActivity.this.setResult(0, new Intent());
                ContratarSeguroActivity.this.onBackPressed();
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

    public void gotoCompraProtegida(CotizacionBean cotizacionBean) {
        Intent intent = new Intent(this, CompraProtegidaActivity.class);
        intent.putExtra(SegurosConstants.INTENT_COTIZACION, cotizacionBean);
        startActivityForResult(intent, 1);
    }

    public void gotoFamiliaObjeto(GetFamiliasObjetosBodyResponseBean getFamiliasObjetosBodyResponseBean) {
        Intent intent = new Intent(this, SeguroObjetosActivity.class);
        intent.putExtra(SeguroObjetosActivity.GET_FAMILIA_OBJETOS, getFamiliasObjetosBodyResponseBean);
        startActivityForResult(intent, 7);
    }

    public void gotoSeguroMovil(String str, SegurosBean segurosBean, CotizacionBean cotizacionBean) {
        Intent intent = new Intent(this, SeguroMovilActivity.class);
        intent.putExtra(SegurosConstants.INTENT_COTIZACION, cotizacionBean);
        intent.putExtra(SegurosConstants.INTENT_TIPO_ALTA_MOVIL, str);
        if (str.equals(TipoAlta.CAMBIO_DISPOSITIVO) || str.equals(TipoAlta.DISPOSITIVO_ASEGURADO) || str.equals(TipoAlta.SEGURO_CARTERA)) {
            intent.putExtra(SegurosConstants.INTENT_LISTA_SEGUROS, segurosBean);
        }
        startActivityForResult(intent, 2);
    }

    private void d() {
        if (this.q.equalsIgnoreCase(TipoAlta.ALTA_DISPOSITIVO)) {
            this.contratarSeguroPresenter.contratarSeguroMovil();
        } else {
            this.contratarSeguroPresenter.goToContratarSeguroMovilSinCotizar();
        }
    }

    public void checkReadPhoneStatePermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_PHONE_STATE"}, 99);
            return;
        }
        d();
    }

    public void verifyBatery() {
        Intent intent = new Intent(this, TestBatteryActivity.class);
        this.p = "";
        startActivityForResult(intent, 6);
    }

    public void showError(String str, int i, String str2) {
        getSupportFragmentManager().beginTransaction().addToBackStack(ErrorFragment.class.getSimpleName()).replace(R.id.content, ErrorFragment.newInstance(str, i, str2), "tag_fragment_load").commit();
        c();
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        this.contratarSeguroPresenter.attachView(this);
        if (i == 99) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                Toast.makeText(getContext(), R.string.ubucation_permission_request_message, 0).show();
            } else {
                d();
            }
        }
    }

    public void attachView() {
        if (!this.contratarSeguroPresenter.isViewAttached()) {
            this.contratarSeguroPresenter.attachView(this);
        }
    }

    public void detachView() {
        if (this.contratarSeguroPresenter.isViewAttached()) {
            this.contratarSeguroPresenter.detachView();
        }
    }

    public void obtenerCotizacionSeguroMovil() {
        showProgressIndicator(VGetCotizacionSeguroMovil.nameService);
        this.mDataManager.getCotizacionSeguroMovil();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.t) {
            checkReadPhoneStatePermission();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.t = false;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (!activityResultHandler(i2, intent) && i2 == -1) {
            if (i == 6) {
                if (i2 == -1 && intent != null) {
                    String stringExtra = intent.getStringExtra(ResultsKeys.BATTERY_RESULT_VALUE);
                    if (stringExtra.equals(ResultValues.OK)) {
                        this.t = true;
                    } else if (stringExtra.equals(ResultValues.FAIL)) {
                        this.q = TipoAlta.NO_ASEGURABLE;
                    } else if (i2 == 0 && intent != null && stringExtra.equals(ResultValues.ERROR)) {
                        this.q = TipoAlta.NO_ASEGURABLE;
                    }
                }
            } else if (i != 7) {
                setResult(-1, intent);
                finish();
            } else if (intent != null && intent.getBooleanExtra(SegurosConstants.CONFIRMACION, false)) {
                ContratarSeguroObjetoBodyResponseBean contratarSeguroObjetoBodyResponseBean = (ContratarSeguroObjetoBodyResponseBean) intent.getExtras().get("CONTRATAR_SEGURO_BODY");
                ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("LIST_GENERIC");
                ArrayList parcelableArrayListExtra2 = intent.getParcelableArrayListExtra("LIST_PREGUNTAS");
                FamiliaBean familiaBean = (FamiliaBean) intent.getExtras().get("FAMILIA_SELECTED");
                GetCotizacionSeguroObjetoBodyResponseBean getCotizacionSeguroObjetoBodyResponseBean = (GetCotizacionSeguroObjetoBodyResponseBean) intent.getExtras().get("COTIZACIÓN");
                overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity);
                ReceiptEventBus.getInstance().post(new ReceiptEvent((ITRSABaseFragment) ComprobanteSeguroObjetoFragment.newInstance(contratarSeguroObjetoBodyResponseBean, parcelableArrayListExtra, parcelableArrayListExtra2, familiaBean, getCotizacionSeguroObjetoBodyResponseBean), FragmentConstants.SEGUROS));
                finish();
            }
        }
    }

    public void showOnBoarding() {
        showOnBoarding(R.layout.onboarding_seguros, R.id.F27_XX_BTN_CLOSE_PAGE2, R.id.F27_XX_FLP_ONBOARDING, SegurosConstants.PREFERENCE_ONBOARDING);
    }

    public void goToContratarSeguroMovilSinCotizar() {
        Intent intent = new Intent(this, SeguroMovilActivity.class);
        intent.putExtra(SegurosConstants.INTENT_TIPO_ALTA_MOVIL, this.q);
        if (this.q.equalsIgnoreCase(TipoAlta.CAMBIO_DISPOSITIVO) || this.q.equalsIgnoreCase(TipoAlta.ALTA_O_CAMBIO_DISPOSITIVO) || this.q.equalsIgnoreCase(TipoAlta.DISPOSITIVO_ASEGURADO) || (this.q.equalsIgnoreCase(TipoAlta.SEGURO_CARTERA) && this.r != null && !this.r.getListaSeguros().isEmpty())) {
            intent.putExtra(SegurosConstants.INTENT_LISTA_SEGUROS, this.r);
        }
        this.u = this.q.equalsIgnoreCase(TipoAlta.SEGURO_CARTERA);
        intent.putExtra(SegurosConstants.INTENT_SEGUROS_CARTERA, this.u);
        startActivityForResult(intent, 2);
    }

    public void changeFragment(Fragment fragment, String str) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.content, fragment, str);
        beginTransaction.addToBackStack(FragmentConstants.SEGURO_LISTA);
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        beginTransaction.commit();
    }
}
