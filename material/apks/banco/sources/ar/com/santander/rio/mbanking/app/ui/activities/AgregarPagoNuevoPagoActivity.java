package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.nuevopago.AgregarPagoNuevoPagoPresenter;
import ar.com.santander.rio.mbanking.app.module.nuevopago.AgregarPagoNuevoPagoView;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultValues;
import ar.com.santander.rio.mbanking.view.ISBANToast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public abstract class AgregarPagoNuevoPagoActivity extends BaseMvpActivity implements OnClickListener, AgregarPagoNuevoPagoView {
    @InjectView(2131362092)
    Button btnContinuar;
    @InjectView(2131362087)
    LinearLayout groupAfipIdentificacion1;
    @InjectView(2131362089)
    LinearLayout groupAfipIdentificacion2;
    @InjectView(2131362081)
    LinearLayout groupAyuda;
    @InjectView(2131362083)
    LinearLayout groupIdentificacion1;
    @InjectView(2131362084)
    LinearLayout groupIdentificacion2;
    @InjectView(2131362080)
    LinearLayout groupPagoAfip;
    @InjectView(2131362082)
    LinearLayout groupPagoConFactura;
    @InjectView(2131362085)
    LinearLayout groupRecargaCelular;
    @InjectView(2131362088)
    RelativeLayout groupRecargaIdentificacion1;
    @InjectView(2131362090)
    RelativeLayout groupRecargaIdentificacion2;
    @InjectView(2131362066)
    EditText inpAfipIdentificacion1;
    @InjectView(2131362069)
    EditText inpAfipIdentificacion2;
    @InjectView(2131362067)
    EditText inpIdentificacion1;
    @InjectView(2131362070)
    EditText inpIdentificacion2;
    @InjectView(2131362068)
    EditText inpRecargaIdentificacion1;
    @InjectView(2131362071)
    EditText inpRecargaIdentificacion2;
    @InjectView(2131362072)
    TextView lblAfipIdentificacion1;
    @InjectView(2131362075)
    TextView lblAfipIdentificacion2;
    @InjectView(2131362064)
    TextView lblAyudaAfip;
    @InjectView(2131362073)
    TextView lblIdentificacion1;
    @InjectView(2131362076)
    TextView lblIdentificacion2;
    @InjectView(2131362074)
    TextView lblRecargaIdentificacion1;
    @InjectView(2131362078)
    TextView lblRecargaIdentificacion1Prefijo;
    @InjectView(2131362077)
    TextView lblRecargaIdentificacion2;
    protected List<CuentaDebitoBean> mCuentas;
    protected DatosDeudaBean mDeuda;
    protected CnsEmpresaDatosEmpresa mEmpresa;
    protected TextWatcher mHabilitadorBotonContinuar;
    protected Map<Integer, String> mInvalidInputs = new LinkedHashMap();
    ImageView p;
    protected AgregarPagoNuevoPagoPresenter presenter;
    @Inject
    AnalyticsManager q;
    @InjectView(2131362096)
    RelativeLayout rllScroll;
    @InjectView(2131362065)
    TextView txtAyudaIdentificacion;
    @InjectView(2131362093)
    TextView txtEmpresa;

    /* access modifiers changed from: protected */
    public abstract Boolean isDataFilled();

    /* access modifiers changed from: protected */
    public abstract Boolean isDataValid();

    public void clearScreenData() {
        try {
            this.inpIdentificacion1.setText("");
            this.inpIdentificacion2.setText("");
            this.inpAfipIdentificacion1.setText("");
            this.inpAfipIdentificacion2.setText("");
            this.inpRecargaIdentificacion1.setText("");
            this.inpRecargaIdentificacion2.setText("");
        } catch (Exception e) {
            Log.e(ResultValues.ERROR, "limpiarPantalla AgregarNuevoPago: ", e);
        }
    }

    public void initialize() {
        this.mCuentas = getIntent().getParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS);
        this.mEmpresa = (CnsEmpresaDatosEmpresa) getIntent().getParcelableExtra(NuevoPagoServiciosConstants.EXTRA_EMPRESA);
        this.mDeuda = (DatosDeudaBean) getIntent().getParcelableExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA);
        this.mHabilitadorBotonContinuar = new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (AgregarPagoNuevoPagoActivity.this.isDataFilled().booleanValue()) {
                    AgregarPagoNuevoPagoActivity.this.btnContinuar.setBackground(AgregarPagoNuevoPagoActivity.this.getResources().getDrawable(R.drawable.boton_redondeado_rojo));
                    AgregarPagoNuevoPagoActivity.this.btnContinuar.setEnabled(true);
                    return;
                }
                AgregarPagoNuevoPagoActivity.this.btnContinuar.setBackground(AgregarPagoNuevoPagoActivity.this.getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
                AgregarPagoNuevoPagoActivity.this.btnContinuar.setEnabled(false);
            }
        };
        this.btnContinuar.setOnClickListener(this);
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.p = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.back_imgButton);
        this.p.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_add_payment_nuevo_pago);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureLayout();
        this.presenter = new AgregarPagoNuevoPagoPresenter(this.mBus, this.mDataManager, this);
        this.presenter.attachView(this);
        this.presenter.showAddPayment();
        this.q.trackScreen(getString(R.string.analytics_screen_name_payment_services_peticion_pago_electronico));
    }

    public void attachView() {
        if (!this.presenter.isViewAttached()) {
            this.presenter.attachView(this);
        }
    }

    public void detachView() {
        if (this.presenter.isViewAttached()) {
            this.presenter.detachView();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void showAddPayment() {
        this.txtEmpresa.setText(this.mEmpresa.empDescr);
        try {
            this.txtEmpresa.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.txtEmpresa.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showInvalidDataAlert(String str) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), str, null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }
        });
        newInstance.show(getSupportFragmentManager(), "invalidDataAlert");
    }

    public void showToastAlert(String str) {
        ISBANToast.show(this, ISBANToast.ERROR, str, ISBANToast.LENGTH_LONG);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.back_imgButton) {
            onBackPressed();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if ((i == 0 || i == 5) && i2 == -1) {
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
