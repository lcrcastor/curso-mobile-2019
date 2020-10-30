package ar.com.santander.rio.mbanking.app.ui.activities.custodiadetalles;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.module.inversionesbancaprivada.analytics.InversionesAnalyticsImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Custodia;
import butterknife.ButterKnife;
import butterknife.InjectView;
import javax.inject.Inject;

public class CustodiaDetallesActivity extends BaseActivity {
    public static final String CUSTODIA_DETALLE_TAG = "CUSTODIA_DETALLES";
    public static final String TERMINOS_CONDICIONES_TAG = "TERMINOS_CONDICIONES";
    @Inject
    public AnalyticsManager analyticsManager;
    private Custodia p;
    private String q;
    private InversionesAnalyticsImpl r;
    @InjectView(2131366224)
    TextView tvCustodiaTerminosCondiciones;
    @InjectView(2131366100)
    TextView tvEstado;
    @InjectView(2131366152)
    TextView tvPrecioMercado;
    @InjectView(2131366210)
    TextView tvValorNominal;
    @InjectView(2131366116)
    TextView tvValueDolar;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.custodiadetalles);
        ButterKnife.inject((Activity) this);
        configureActionBar();
        b();
        this.r = new InversionesAnalyticsImpl(this, this.analyticsManager);
        this.r.TenenciaCustodiaDetallebp();
    }

    private void b() {
        this.p = (Custodia) getIntent().getParcelableExtra(CUSTODIA_DETALLE_TAG);
        this.q = getIntent().getStringExtra("TERMINOS_CONDICIONES");
        if (!TextUtils.isEmpty(this.q)) {
            this.tvCustodiaTerminosCondiciones.setText(Html.fromHtml(this.q));
        }
        this.tvValorNominal.setText(this.p.getCanValorNom());
        this.tvEstado.setText(this.p.getEstado());
        this.tvPrecioMercado.setText(this.p.getPrecioMercado());
        this.tvValueDolar.setText(this.p.getTenValuadaHoy());
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        enableBackButton();
    }

    public void enableBackButton() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    CustodiaDetallesActivity.this.finish();
                    CustodiaDetallesActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }
    }
}
