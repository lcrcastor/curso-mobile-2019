package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.module.nuevopago.ConfirmarPagoServicioPresenter;
import ar.com.santander.rio.mbanking.app.module.nuevopago.ConfirmarPagoServicioView;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.util.TextUtils;
import javax.inject.Inject;

public abstract class ConfirmPaymentNuevoPagoActivity extends BaseMvpActivity implements OnClickListener, ConfirmarPagoServicioView {
    @InjectView(2131361921)
    Button btnConfirmar;
    protected ConfirmarPagoServicioPresenter confirmarPagoPresenter;
    @InjectView(2131361922)
    TextView lblAnticipoCuota;
    @InjectView(2131361923)
    TextView lblCUITempleado;
    @InjectView(2131361924)
    TextView lblCUITempleador;
    @InjectView(2131361944)
    TextView lblDatosAdicionales;
    @InjectView(2131361934)
    TextView lblDinamico4;
    @InjectView(2131361955)
    TextView lblPeriodo;
    @InjectView(2131361957)
    TextView lblVEP;
    protected CuentaDebitoBean mCuenta;
    protected String mDatosAdicionales;
    protected DatosDeudaBean mDeuda;
    protected String mOrigen;
    @Inject
    SessionManager p;
    @Inject
    SoftTokenManager q;
    @Inject
    AnalyticsManager r;
    @InjectView(2131361959)
    LinearLayout rowAnticipoCuota;
    @InjectView(2131361960)
    LinearLayout rowCUITempleado;
    @InjectView(2131361961)
    LinearLayout rowCUITempleador;
    @InjectView(2131361962)
    LinearLayout rowCUR;
    @InjectView(2131361963)
    LinearLayout rowDatosAdicionales;
    @InjectView(2131361964)
    LinearLayout rowDinamico1;
    @InjectView(2131361965)
    LinearLayout rowDinamico2;
    @InjectView(2131361966)
    LinearLayout rowDinamico3;
    @InjectView(2131361967)
    LinearLayout rowDinamico4;
    @InjectView(2131361968)
    LinearLayout rowEmpresa;
    @InjectView(2131361969)
    LinearLayout rowFactura;
    @InjectView(2131361970)
    LinearLayout rowIdentificador;
    @InjectView(2131361972)
    LinearLayout rowInformacionAdicional;
    @InjectView(2131361973)
    LinearLayout rowMedioPago;
    @InjectView(2131361974)
    LinearLayout rowPeriodo;
    @InjectView(2131361976)
    LinearLayout rowVEP;
    @InjectView(2131361975)
    LinearLayout rowVencimiento;
    ImageView s;
    /* access modifiers changed from: private */
    public String t;
    @InjectView(2131361926)
    TextView txtAnticipoCuota;
    @InjectView(2131361927)
    TextView txtCUITempleado;
    @InjectView(2131361928)
    TextView txtCUITempleador;
    @InjectView(2131361930)
    TextView txtDatosAdicionales;
    @InjectView(2131361935)
    TextView txtEmpresa;
    @InjectView(2131361936)
    TextView txtFactura;
    @InjectView(2131361937)
    TextView txtIdentificador;
    @InjectView(2131361938)
    TextView txtImporte;
    @InjectView(2131361939)
    TextView txtInformacionAdicional;
    @InjectView(2131361940)
    TextView txtMedioPago;
    @InjectView(2131361941)
    TextView txtPeriodo;
    @InjectView(2131361943)
    TextView txtVEP;
    @InjectView(2131361942)
    TextView txtVencimiento;

    public void clearScreenData() {
    }

    public Activity getActivity() {
        return this;
    }

    public void initialize() {
        this.mOrigen = getIntent().getStringExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN);
        this.mCuenta = (CuentaDebitoBean) getIntent().getParcelableExtra(NuevoPagoServiciosConstants.EXTRA_CUENTA);
        this.mDeuda = (DatosDeudaBean) getIntent().getParcelableExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA);
        String stringExtra = getIntent().getStringExtra("EMPLEADOR");
        if (TextUtils.isEmpty(this.mDeuda.cuitEmpleador)) {
            this.mDeuda.cuitEmpleador = stringExtra;
        }
        this.mDatosAdicionales = getIntent().getStringExtra(NuevoPagoServiciosConstants.EXTRA_DATOS_ADICIONALES);
        this.btnConfirmar.setOnClickListener(this);
        this.t = getString(R.string.IDX_N);
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.s = (ImageView) this.mActionBar.findViewById(R.id.back_imgButton);
        this.s.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_confirmar_pago_servicio);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureLayout();
        this.r.trackScreen(getString(R.string.analytics_screen_name_confirm_payment_services_home));
        this.confirmarPagoPresenter = new ConfirmarPagoServicioPresenter(this.mBus, this.mDataManager, this.q, this);
        this.confirmarPagoPresenter.attachView(this);
        this.confirmarPagoPresenter.showConfirmarPago();
    }

    public void attachView() {
        if (!this.confirmarPagoPresenter.isViewAttached()) {
            this.confirmarPagoPresenter.attachView(this);
        }
    }

    public void detachView() {
        if (this.confirmarPagoPresenter.isViewAttached()) {
            this.confirmarPagoPresenter.detachView();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.F06_03_BTN_CONFIRMAR_PAGO) {
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX32_PAGO_SERVICIO_BTN_CONFIRMAR_PAGO), getString(R.string.IDX33_PAGO_SERVICIO_CONFIRMAR_PAGO_POPUP), null, null, getString(R.string.IDX_ALERT_BTN_YES), getString(R.string.IDX_ALERT_BTN_NO), null);
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onSimpleActionButton() {
                }

                public void onPositiveButton() {
                    ConfirmPaymentNuevoPagoActivity.this.confirmarPagoPresenter.onPagar(ConfirmPaymentNuevoPagoActivity.this.mCuenta, ConfirmPaymentNuevoPagoActivity.this.mDeuda, ConfirmPaymentNuevoPagoActivity.this.mDatosAdicionales, ConfirmPaymentNuevoPagoActivity.this.t, ConfirmPaymentNuevoPagoActivity.this.mOrigen);
                }

                public void onNegativeButton() {
                    ConfirmPaymentNuevoPagoActivity.this.r.trackEvent(ConfirmPaymentNuevoPagoActivity.this.getString(R.string.analytics_category_payment_services), ConfirmPaymentNuevoPagoActivity.this.getString(R.string.analytics_action_payment_services_cancel_dialog_confirm), ConfirmPaymentNuevoPagoActivity.this.getString(R.string.analytics_label_payment_services_cancel_dialog_confirm));
                }
            });
            this.r.trackScreen(getString(R.string.analytics_screen_name_show_dialog_confirm_payment_services_home));
            newInstance.show(getSupportFragmentManager(), "confirmarPagoAlert");
        } else if (id2 == R.id.back_imgButton) {
            onBackPressed();
        }
    }

    public void showAutoDebitWarning(String str) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), str, null, null, getString(R.string.IDX55_PAGO_SERVICIO_BTN_PAGAR_AHORA), getString(R.string.IDX_ALERT_BTN_CANCEL), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                ConfirmPaymentNuevoPagoActivity.this.t = ConfirmPaymentNuevoPagoActivity.this.getString(R.string.IDX_S);
                ConfirmPaymentNuevoPagoActivity.this.confirmarPagoPresenter.onPagar(ConfirmPaymentNuevoPagoActivity.this.mCuenta, ConfirmPaymentNuevoPagoActivity.this.mDeuda, ConfirmPaymentNuevoPagoActivity.this.mDatosAdicionales, ConfirmPaymentNuevoPagoActivity.this.t, ConfirmPaymentNuevoPagoActivity.this.mOrigen);
            }

            public void onNegativeButton() {
                ConfirmPaymentNuevoPagoActivity.this.t = ConfirmPaymentNuevoPagoActivity.this.getString(R.string.IDX_N);
            }
        });
        newInstance.show(getSupportFragmentManager(), "confirmarPagoAlert");
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 2 && i2 == -1) {
            Intent intent2 = new Intent();
            if (intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
                intent2.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION));
                if (intent.hasExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE)) {
                    intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE));
                }
            } else if (intent.hasExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION)) {
                intent2.putExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, intent.getIntExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, -1));
            }
            setResult(-1, intent2);
            finish();
        }
    }
}
