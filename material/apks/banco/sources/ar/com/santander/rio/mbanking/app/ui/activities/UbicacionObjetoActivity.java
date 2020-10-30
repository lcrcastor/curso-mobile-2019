package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.location.Location;
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
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.PrepararCoberturaObjetoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.UbicacionSeguroObjetoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.UbicacionSeguroObjetoFragment.OnFragmentInteractionListener;

public class UbicacionObjetoActivity extends BaseActivity implements OnFragmentInteractionListener {
    public static final String ALIAS_EXTRA = "ALIAS_EXTRA";
    public static final String ERROR_MESSAGE_ARG = "ERROR_MESSAGE_ARG";
    public static final String LATITUD_EXTRA = "LATITUD_EXTRA";
    public static final String LEYENDA_ARG = "LEYENDA_ARG";
    public static final String LONGITUD_EXTRA = "LONGITUD_EXTRA";
    public static final String UBICACION_FRAGMNET_TAG = "UBICACION_FRAGMNET";
    private FragmentManager p;
    private String q;
    private String r;

    public void backToPrincipalFragment() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_ubicacion_objeto);
        if (getIntent().getExtras() != null) {
            this.q = getIntent().getExtras().getString(PrepararCoberturaObjetoFragment.SEGOBJ_UBQ_ID, "");
            this.r = getIntent().getExtras().getString(FotoObjetoActivity.FAMILY_NAME, "");
        }
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.content, UbicacionSeguroObjetoFragment.newInstance(this.q, this.r), UBICACION_FRAGMNET_TAG);
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        beginTransaction.commit();
    }

    public void configureBackActionBar() {
        setActionBarType(ActionBarType.PUSH_CLOSE);
        View actionBarView = getActionBarView();
        this.p = getSupportFragmentManager();
        if (getActionBarView() != null) {
            ((ImageView) actionBarView.findViewById(R.id.ok)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra(UbicacionObjetoActivity.ERROR_MESSAGE_ARG, "");
                    UbicacionObjetoActivity.this.setResult(0, intent);
                    UbicacionObjetoActivity.this.finish();
                }
            });
        }
    }

    public void onBackPressed() {
        if (this.p.getBackStackEntryCount() > 0) {
            this.p.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void changeFragment(Fragment fragment, String str) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.content, fragment, str);
        beginTransaction.addToBackStack(str);
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        beginTransaction.commit();
    }

    public void showProgressBar(String str) {
        showProgressBar(str);
    }

    public void dismissProgressBar() {
        dismissProgress();
    }

    public void onMapLoadError(Exception exc) {
        Intent intent = new Intent();
        intent.putExtra(ERROR_MESSAGE_ARG, "Error al Cargar el Mapa");
        setResult(0, intent);
        finish();
    }

    public void onSelectedPosition(Location location, String str) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(LATITUD_EXTRA, String.valueOf(location.getLatitude()));
        bundle.putString(LONGITUD_EXTRA, String.valueOf(location.getLongitude()));
        bundle.putString(ALIAS_EXTRA, str);
        intent.putExtras(bundle);
        setResult(-1, intent);
        finish();
    }

    public void getLocationError() {
        Intent intent = new Intent();
        intent.putExtra(ERROR_MESSAGE_ARG, "Error al obtener la geolocalización");
        setResult(0, intent);
        finish();
    }
}
