package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.module.recargaSube.RegistrarTarjetaRecargaSubePresenter;
import ar.com.santander.rio.mbanking.app.module.recargaSube.RegistrarTarjetaRecargaSubeView;
import ar.com.santander.rio.mbanking.app.module.recargaSube.SharedPreferencesData;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.RecargaSubeErrorFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.RecargaSubeFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.RecargaSubeNotOKFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.RecargaSubeOKFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.RecargaSubeRevisarFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.RegistrarSubeFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.recargasubeconfirmacionpago.RecargaSubeConfirmacionFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.recargasubeconfirmacionpago.RecargaSubeConfirmacionFragment.OnFragmentInteractionListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.squareup.otto.Bus;
import java.util.List;
import javax.inject.Inject;

public class RecargaSubeActivity extends BaseMvpActivity implements RegistrarTarjetaRecargaSubeView, OnFragmentInteractionListener {
    private static int s = 1012;
    public ActionBar mActionBar;
    @Inject
    public IDataManager mDataManager;
    @Inject
    Bus p;
    @Inject
    SessionManager q;
    @Inject
    SettingsManager r;
    private SharedPreferencesData t;
    private String u;
    private RegistrarTarjetaRecargaSubePresenter v;
    private boolean w;
    private String x = "";
    private String y = "";

    public void configureLayout() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_recarga_sube);
        initialize();
        configureActionBar();
    }

    private void a(Fragment fragment) {
        changeFragmentToShow(fragment, null, "RecargaSubeFragment");
    }

    public void backLastFragment() {
        getSupportFragmentManager().popBackStackImmediate();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void changeFragmentToShow(Fragment fragment, Bundle bundle) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if ((fragment instanceof RecargaSubeErrorFragment) || (fragment instanceof RecargaSubeConfirmacionFragment) || (fragment instanceof RecargaSubeNotOKFragment) || (fragment instanceof RecargaSubeOKFragment)) {
            beginTransaction.setCustomAnimations(0, 0, 0, 0);
        } else {
            beginTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        }
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        if (fragment instanceof RecargaSubeFragment) {
            beginTransaction.replace(R.id.content, fragment);
        } else {
            beginTransaction.replace(R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(fragment.getClass().getSimpleName());
        }
        beginTransaction.commit();
        dismissProgress();
    }

    public void changeFragmentToShow(Fragment fragment, Bundle bundle, String str) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        beginTransaction.replace(R.id.content, fragment, str).addToBackStack(str);
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        beginTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left);
        beginTransaction.commit();
        dismissProgress();
    }

    public void initialize() {
        this.t = new SharedPreferencesData(getApplicationContext());
        this.v = new RegistrarTarjetaRecargaSubePresenter(this.p, this.mDataManager, this.r, this);
        this.v.attachView(this);
        this.u = (String) getIntent().getExtras().get(getString(R.string.RECARGA_SUBE_FLOW));
        this.x = getIntent().getStringExtra("SUBE_TITULO");
        this.y = getIntent().getStringExtra("SUBE_DESCRIPCION");
        if (this.u.equalsIgnoreCase(getString(R.string.REGISTRAR_SUBE))) {
            goToRegistrarSube();
        } else if (this.u.equalsIgnoreCase(getString(R.string.AGREGAR_SUBE))) {
            b();
        } else if (this.u.equalsIgnoreCase(getString(R.string.RECARGA_SUBE))) {
            List list = (List) getIntent().getExtras().get(getString(R.string.LISTA_CUENTAS));
            List list2 = (List) getIntent().getExtras().get(getString(R.string.LISTA_RECARGAS));
            List list3 = (List) getIntent().getExtras().get(getString(R.string.LISTA_VALORES));
            String str = (String) getIntent().getExtras().get(getString(R.string.SESSION_USER));
            boolean z = false;
            if (getIntent().hasExtra(FragmentConstants.INTENT_DATA_SUBE_BACK_TRANSITION)) {
                z = getIntent().getBooleanExtra(FragmentConstants.INTENT_DATA_SUBE_BACK_TRANSITION, false);
            }
            a((Fragment) RecargaSubeFragment.newInstance(list, list2, list3, str, z));
        }
    }

    public void configureActionBar() {
        this.mActionBar = getSupportActionBar();
        if (this.mActionBar != null) {
            this.mActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
            setActionBarType(ActionBarType.BACK);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        hideKeyboard();
    }

    public void goToRegistrarSube() {
        Bundle bundle = new Bundle();
        bundle.putString("SUBE_TITULO", this.x);
        bundle.putString("SUBE_DESCRIPCION", this.y);
        changeFragmentToShow(new RegistrarSubeFragment(), bundle, "RegistrarSubeFragment");
    }

    public void callRecargaSubeWs() {
        this.v.callRecargaSubeWs();
    }

    private void b() {
        callRecargaSubeWs();
    }

    public void goToTarjetaSubeRegisterFlow(List<CuentaDebitoBean> list, CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa, DatosDeudaBean datosDeudaBean) {
        dismissProgress();
        gotoRecargaSubeWelcome(getString(R.string.registrar_tarjeta_sube));
    }

    public void gotoRecargaSubeWelcome(String str) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("MENU_ACTION", FragmentConstants.RECARGA_SUBE_WELCOME);
        bundle.putString("SUBE_BUTTON", str);
        bundle.putString("SUBE_TITULO", this.x);
        bundle.putString("SUBE_DESCRIPCION", this.y);
        bundle.putString(FragmentConstants.INTENT_DATA_RECARGA_SUBE, getString(R.string.AGREGAR_SUBE));
        intent.putExtras(bundle);
        finishOK(intent);
    }

    public void showProgressIndicator(String str) {
        showProgress(str);
    }

    public void dismissProgressIndicator() {
        dismissProgress();
    }

    public void attachView() {
        if (!this.v.isViewAttached()) {
            this.v.attachView(this);
        }
    }

    public void detachView() {
        if (this.v.isViewAttached()) {
            this.v.detachView();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            Intent intent2 = new Intent();
            intent2.putExtra(getString(R.string.RECARGA_SUBE), 1234);
            setResult(-1, intent2);
            this.w = true;
            finish();
        } else if (i2 == 0) {
            if (getSupportFragmentManager().getBackStackEntryCount() < 1) {
                goBackToHome();
            }
        } else if (i2 == s) {
            backToPrincipalPage();
        }
    }

    public void finish() {
        if (this.w) {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_down_recarga_sube);
            this.mDataManager.logOutSession();
        }
        super.finish();
    }

    public void finishOK(Intent intent) {
        setResult(-1, intent);
        super.finish();
    }

    public void onBackPressed() {
        a(getSupportFragmentManager());
    }

    private void a(FragmentManager fragmentManager) {
        String tag = ((Fragment) fragmentManager.getFragments().get(0)).getTag();
        if (tag != null && (tag.equalsIgnoreCase("RecargaSubeErrorFragment") || tag.equalsIgnoreCase("RecargaSubeConfirmacionFragment"))) {
            backToPrincipalPage();
        } else if (tag == null || !tag.equalsIgnoreCase("RecargaSubeRevisarFragment")) {
            b(fragmentManager);
        } else {
            ((RecargaSubeRevisarFragment) fragmentManager.getFragments().get(0)).onBackPressed();
        }
    }

    private void b(FragmentManager fragmentManager) {
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        } else {
            goBackToHome();
        }
    }

    public void goBackToHome() {
        if (c()) {
            this.mDataManager.logOutSession();
        }
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(67108864);
        startActivity(intent);
        finish();
    }

    private boolean c() {
        return !this.q.getSession().isEmpty();
    }

    public void backToPrincipalPage() {
        getSupportFragmentManager().popBackStackImmediate(RecargaSubeRevisarFragment.class.getSimpleName(), 1);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
