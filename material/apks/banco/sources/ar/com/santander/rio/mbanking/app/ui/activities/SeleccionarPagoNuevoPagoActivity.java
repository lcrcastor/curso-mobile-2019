package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.nuevopago.SeleccionarPagoNuevoPagoPresenter;
import ar.com.santander.rio.mbanking.app.module.nuevopago.SeleccionarPagoNuevoPagoView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class SeleccionarPagoNuevoPagoActivity extends BaseMvpActivity implements OnClickListener, SeleccionarPagoNuevoPagoView {
    @InjectView(2131362101)
    TextView lblAyudaSeleccionarFactura;
    @InjectView(2131362102)
    TextView lblCampoDinamicoIdentificacion;
    @InjectView(2131362111)
    LinearLayout lstFacturas;
    protected List<CuentaDebitoBean> mCuentas;
    protected List<DatosDeudaBean> mDeudas;
    protected CnsEmpresaDatosEmpresa mEmpresa;
    protected String mPagoElectronico;
    protected String mTextAyuda;
    CAccessibility p;
    protected SeleccionarPagoNuevoPagoPresenter presenter;
    ImageView q;
    @Inject
    AnalyticsManager r;
    @InjectView(2131362103)
    TextView txtCampoDinamicoIdentificacion;
    @InjectView(2131362104)
    TextView txtEmpresa;

    public void clearScreenData() {
    }

    public void configureLayout() {
    }

    public void initialize() {
        this.mEmpresa = (CnsEmpresaDatosEmpresa) getIntent().getParcelableExtra(NuevoPagoServiciosConstants.EXTRA_EMPRESA);
        this.mPagoElectronico = getIntent().getStringExtra(NuevoPagoServiciosConstants.EXTRA_PAGO_ELECTRONICO);
        this.mCuentas = getIntent().getParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS);
        this.mDeudas = getIntent().getParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_DEUDAS);
        this.mTextAyuda = getIntent().getStringExtra(NuevoPagoServiciosConstants.EXTRA_AYUDA);
        this.p = new CAccessibility(this);
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.q = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.back_imgButton);
        this.q.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_select_payment_nuevo_pago);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureLayout();
        this.presenter = new SeleccionarPagoNuevoPagoPresenter(this.mBus, this.mDataManager);
        this.presenter.attachView(this);
        this.presenter.showSelectPayment();
        this.r.trackScreen(getString(R.string.analytics_screen_name_payment_seleccionar_factura));
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

    public void showSelectPayment() {
        this.txtEmpresa.setText(this.mEmpresa.empDescr);
        if (!TextUtils.isEmpty(this.mEmpresa.identificacion1) && !this.mEmpresa.tipoEmpresa.equalsIgnoreCase("F")) {
            this.lblCampoDinamicoIdentificacion.setText(this.mEmpresa.identificacion1);
        }
        this.txtCampoDinamicoIdentificacion.setText(this.mPagoElectronico);
        if (!TextUtils.isEmpty(this.mTextAyuda)) {
            this.lblAyudaSeleccionarFactura.setText(Html.fromHtml(this.mTextAyuda));
            try {
                this.lblAyudaSeleccionarFactura.setContentDescription(this.p.applyFilterGeneral(this.lblAyudaSeleccionarFactura.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        TableLayout tableLayout = new TableLayout(this);
        for (int i = 0; i < this.mDeudas.size(); i++) {
            final DatosDeudaBean datosDeudaBean = (DatosDeudaBean) this.mDeudas.get(i);
            LinearLayout linearLayout = (LinearLayout) ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.list_item_selec_deuda_pago_servicio, null);
            TextView textView = (TextView) ButterKnife.findById((View) linearLayout, (int) R.id.lbl_column2);
            TextView textView2 = (TextView) ButterKnife.findById((View) linearLayout, (int) R.id.lbl_column3);
            ((TextView) ButterKnife.findById((View) linearLayout, (int) R.id.lbl_column1)).setText(datosDeudaBean.vencimiento);
            textView.setText(datosDeudaBean.factura);
            if (!TextUtils.isEmpty(datosDeudaBean.importe)) {
                textView2.setText(String.format("%s%s", new Object[]{UtilCurrency.getSimbolCurrencyFromString(datosDeudaBean.moneda, Constants.SYMBOL_CURRENCY_PESOS), datosDeudaBean.importe}));
            }
            ((LinearLayout) linearLayout.findViewById(R.id.lnl_item_selec_empresa)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SeleccionarPagoNuevoPagoActivity.this.presenter.onPaymentSelected(datosDeudaBean);
                }
            });
            tableLayout.addView(linearLayout, i);
        }
        this.lstFacturas.addView(tableLayout);
    }

    public void showInvoicePreparePayment(DatosDeudaBean datosDeudaBean) {
        Intent intent = new Intent(this, PrepareInvoicePaymentNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, NuevoPagoServiciosConstants.ORIGEN_MANUAL);
        intent.putParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) this.mCuentas);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, datosDeudaBean);
        startActivityForResult(intent, 0);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.back_imgButton) {
            onBackPressed();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 0 && i2 == -1) {
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
