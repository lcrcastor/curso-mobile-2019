package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosCredito;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class TasasDetailsActivity extends BaseActivity {
    private DatosCredito p;
    @InjectView(2131365831)
    public TextView tasasCFTNAcIVA;
    @InjectView(2131365832)
    public TextView tasasCFTNAsIVA;
    @InjectView(2131365835)
    public TextView tasasTNA;
    @InjectView(2131365833)
    public TextView tasas_TEA;

    public String safeString(String str) {
        return str == null ? "" : str;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_tasas_details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.p = (DatosCredito) extras.getParcelable("credito_seleccionado");
        }
        ButterKnife.inject((Activity) this);
        b();
        configureActionBar();
    }

    private void b() {
        this.tasasTNA.setText(safeString(this.p.getTasatna()));
        this.tasas_TEA.setText(safeString(this.p.getTasatea()));
        this.tasasCFTNAsIVA.setText(safeString(this.p.getTasacftna()));
        this.tasasCFTNAcIVA.setText(safeString(this.p.getTasactfnaiva()));
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
                    TasasDetailsActivity.this.finish();
                }
            });
        }
    }
}
