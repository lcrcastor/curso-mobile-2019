package ar.com.santander.rio.mbanking.app.ui.activities.custodiaterminos;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class CustodiaTerminosActivity extends BaseActivity {
    public static final String TERMINOS_CONDICIONES_TAG = "TERMINOS_CONDICIONES";
    @InjectView(2131366224)
    TextView tvCustodiaTerminosCondiciones;
    @InjectView(2131366421)
    TextView tvTitle;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.legales_custodia);
        ButterKnife.inject((Activity) this);
        this.tvCustodiaTerminosCondiciones.setText(Html.fromHtml(getIntent().getStringExtra("TERMINOS_CONDICIONES")));
        configureActionBar();
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.CONFIRMAR);
        enableBackButton();
    }

    public void enableBackButton() {
        ImageView imageView = (ImageView) getSupportActionBar().getCustomView().findViewById(R.id.confirm_imgButton);
        if (imageView != null) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    CustodiaTerminosActivity.this.finish();
                    CustodiaTerminosActivity.this.onBackPressed();
                }
            });
        }
    }
}
