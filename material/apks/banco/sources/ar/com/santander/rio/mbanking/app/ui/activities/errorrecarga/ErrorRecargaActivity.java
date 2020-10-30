package ar.com.santander.rio.mbanking.app.ui.activities.errorrecarga;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.HomeActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.errorrecarga.ErrorRecargaContract.View;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import javax.inject.Inject;

public class ErrorRecargaActivity extends BaseMvpActivity implements View {
    @InjectView(2131364151)
    TextView algoSalioMalText;
    @InjectView(2131364208)
    Button btnIntentaOtraVez;
    @InjectView(2131364213)
    TextView btnVolverHome;
    @InjectView(2131364300)
    android.view.View closeAnimationView;
    @InjectView(2131364841)
    TextView intentaNuevamenteText;
    ErrorRecargaPresenter p;
    @Inject
    SessionManager q;
    private String r;
    private String s;

    public void attachView() {
    }

    public void configureLayout() {
    }

    public void detachView() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_error_sube);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
    }

    public void onBackPressed() {
        b();
    }

    public void configureActionBar() {
        findViewById(R.id.recarga_sube_error_actionbar).setVisibility(8);
        getSupportActionBar().show();
        setActionBarType(ActionBarType.WHITE_SUBE);
        enableBackButton();
    }

    public void enableBackButton() {
        android.view.View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.ok);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(android.view.View view) {
                    ErrorRecargaActivity.this.b();
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    /* access modifiers changed from: private */
    public void b() {
        if (c()) {
            this.mDataManager.logOutSession();
        }
        overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_down_recarga_sube);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(67108864);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_down_recarga_sube);
    }

    private boolean c() {
        return !this.q.getSession().isEmpty();
    }

    public void initialize() {
        this.p = new ErrorRecargaPresenter(this.mBus, this.mDataManager);
        this.p.attachView(this);
        d();
        this.btnIntentaOtraVez.setVisibility(0);
        this.btnIntentaOtraVez.setText(R.string.recarga_label_volver_a_home);
        this.btnVolverHome.setVisibility(8);
        this.p.getErrorRecargaMsg(this.r, this.s);
        this.closeAnimationView.setVisibility(8);
    }

    private void d() {
        this.r = getIntent().getExtras().getString(WSErrorHandlerConstants.INTENT_EXTRA_TITLE);
        this.s = getIntent().getExtras().getString(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE);
    }

    public void setErrorRecargaView(String str, String str2) {
        this.algoSalioMalText.setText(str);
        this.intentaNuevamenteText.setText(str2);
    }

    @OnClick({2131364208})
    public void onViewClicked() {
        b();
    }
}
