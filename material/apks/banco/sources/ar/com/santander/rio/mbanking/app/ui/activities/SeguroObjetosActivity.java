package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler.IActionCustom;
import ar.com.santander.rio.mbanking.app.base.ReceiptEvent;
import ar.com.santander.rio.mbanking.app.base.ReceiptEventBus;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormData;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.CompletarSeguroObjetosFragment.OnFragmentInteractionListener;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobanteSeguroObjetoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConfirmarSeguroObjetosFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.CotizarCoberturaObjetoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.PreguntasFamiliaFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.PrepararCoberturaObjetoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.SeleccionFamiliaObjetoFragment;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroObjetoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FamiliaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosBodyResponseBean;
import java.util.ArrayList;
import java.util.List;

public class SeguroObjetosActivity extends BaseActivity implements IActionCustom, OnFragmentInteractionListener, ComprobanteSeguroObjetoFragment.OnFragmentInteractionListener, ConfirmarSeguroObjetosFragment.OnFragmentInteractionListener, CotizarCoberturaObjetoFragment.OnFragmentInteractionListener, PreguntasFamiliaFragment.OnFragmentInteractionListener, PrepararCoberturaObjetoFragment.OnFragmentInteractionListener, SeleccionFamiliaObjetoFragment.OnFragmentInteractionListener {
    public static final String GET_FAMILIA_OBJETOS = "GET_FAMILIA_OBJETOS";
    private FragmentManager p;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_seguro_objetos);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, SeleccionFamiliaObjetoFragment.newInstance((GetFamiliasObjetosBodyResponseBean) getIntent().getExtras().getParcelable(GET_FAMILIA_OBJETOS))).commit();
        this.p = getSupportFragmentManager();
    }

    public void onBackPressed() {
        if (this.p.getBackStackEntryCount() > 0) {
            this.p.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void configureBackActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        View actionBarView = getActionBarView();
        if (getActionBarView() != null) {
            ((ImageView) actionBarView.findViewById(R.id.back_imgButton)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SeguroObjetosActivity.this.onBackPressed();
                }
            });
        }
    }

    public void changeFragment(Fragment fragment, String str) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.no_animation, R.anim.no_animation, R.anim.slide_out_right);
        beginTransaction.replace(R.id.content, fragment, str);
        beginTransaction.addToBackStack(str);
        beginTransaction.commit();
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

    private void b() {
        getSharedPreferences(FotoObjetoActivity.SEGUROS_2_SHARE_PREFERENCE_EDITOR_NAME, 0).edit().clear().apply();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        b();
        super.onDestroy();
    }

    public void showComprobanteSeguroAccidente(ContratarSeguroObjetoBodyResponseBean contratarSeguroObjetoBodyResponseBean, List<IFormData> list, List<IFormData> list2, FamiliaBean familiaBean, GetCotizacionSeguroObjetoBodyResponseBean getCotizacionSeguroObjetoBodyResponseBean) {
        Intent intent = new Intent();
        intent.putExtra(SegurosConstants.CONFIRMACION, true);
        intent.putExtra("CONTRATAR_SEGURO_BODY", contratarSeguroObjetoBodyResponseBean);
        intent.putParcelableArrayListExtra("LIST_GENERIC", (ArrayList) list);
        intent.putParcelableArrayListExtra("LIST_PREGUNTAS", (ArrayList) list2);
        intent.putExtra("FAMILIA_SELECTED", familiaBean);
        intent.putExtra("COTIZACIÓN", getCotizacionSeguroObjetoBodyResponseBean);
        setResult(-1, intent);
        finish();
    }

    public void action() {
        finish();
        ReceiptEventBus.getInstance().post(new ReceiptEvent((Activity) this, TypeResult.BEAN_ERROR_RES_5.toString()));
    }
}
