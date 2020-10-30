package ar.com.santander.rio.mbanking.app.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.FormularioSugerirSeguroObjetosFragment;

public class SugerirSeguroObjetosActivity extends BaseActivity {
    private String p;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_sugerir_seguro_objetos);
        if (!(getIntent() == null || getIntent().getExtras() == null)) {
            this.p = getIntent().getExtras().get("leyenda").toString();
        }
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.contenedorFragmentFormularioSugerirSeguroObjetosFragment, FormularioSugerirSeguroObjetosFragment.newInstance(this.p));
        beginTransaction.commit();
        configureActionBar();
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.PUSH_CLOSE);
        enableCloseButton();
    }

    public void enableCloseButton() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.ok);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SugerirSeguroObjetosActivity.this.finish();
                    SugerirSeguroObjetosActivity.this.overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
                }
            });
        }
    }
}
