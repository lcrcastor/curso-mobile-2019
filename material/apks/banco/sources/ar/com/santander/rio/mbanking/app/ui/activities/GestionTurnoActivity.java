package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler.IActionCustom;
import ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment;
import ar.com.santander.rio.mbanking.app.base.ReceiptEvent;
import ar.com.santander.rio.mbanking.app.base.ReceiptEventBus;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobanteTurnoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConfirmarTurnoFragment.OnFragmentInteractionListener;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.SeleccionSucursalTurnoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.SolicitarTurnoCajaFragment;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmTurnoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetSucursalesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTurnoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalBean;
import com.squareup.otto.Bus;

public class GestionTurnoActivity extends BaseActivity implements IActionCustom, OnFragmentInteractionListener, SeleccionSucursalTurnoFragment.OnFragmentInteractionListener, SolicitarTurnoCajaFragment.OnFragmentInteractionListener {
    private FragmentManager p;
    private Bus q;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_gestion_turno);
        this.q = ReceiptEventBus.getInstance();
        this.q.register(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, SeleccionSucursalTurnoFragment.newInstance((GetSucursalesBodyResponseBean) getIntent().getExtras().getParcelable(SeleccionSucursalTurnoFragment.GET_SUCURSALES_TURNO), (GetTurnoBodyResponseBean) getIntent().getExtras().getParcelable(SeleccionSucursalTurnoFragment.GET_TURNO))).commit();
        this.p = getSupportFragmentManager();
    }

    public void configureBackActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        View actionBarView = getActionBarView();
        if (getActionBarView() != null) {
            ((ImageView) actionBarView.findViewById(R.id.back_imgButton)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    GestionTurnoActivity.this.onBackPressed();
                }
            });
        }
    }

    public void changeFragment(Fragment fragment, String str) {
        getSupportFragmentManager().beginTransaction().addToBackStack(fragment.getClass().getSimpleName()).setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.content, fragment, str).addToBackStack(str).commit();
    }

    public void showProgressBar(String str) {
        showProgress(str);
    }

    public void dismissProgressBar() {
        dismissProgress();
    }

    public void backToPrincipalFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        int backStackEntryCount = supportFragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackEntryCount; i++) {
            supportFragmentManager.popBackStackImmediate();
        }
    }

    public void showComprobanteTurno(AbmTurnoBodyResponseBean abmTurnoBodyResponseBean, SucursalBean sucursalBean, String str) {
        ComprobanteTurnoFragment newInstance = ComprobanteTurnoFragment.newInstance(abmTurnoBodyResponseBean, sucursalBean, "A", str);
        finish();
        overridePendingTransition(0, 0);
        ReceiptEventBus.getInstance().post(new ReceiptEvent((ITRSABaseFragment) newInstance, FragmentConstants.NERS_ENCOLADOR));
    }

    public void finishActivity() {
        finish();
    }

    public void action() {
        finish();
        ReceiptEventBus.getInstance().post(new ReceiptEvent((Activity) this, TypeResult.BEAN_ERROR_RES_5.toString()));
    }
}
