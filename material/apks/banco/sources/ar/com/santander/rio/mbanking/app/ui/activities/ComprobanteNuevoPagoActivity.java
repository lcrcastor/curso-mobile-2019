package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.nuevopago.ComprobantePagoServicioPresenter;
import ar.com.santander.rio.mbanking.app.module.nuevopago.ComprobantePagoServicioView;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean;
import ar.com.santander.rio.mbanking.utils.Utils.Documento.CUIT;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import javax.inject.Inject;

public abstract class ComprobanteNuevoPagoActivity extends MvpPrivateMenuActivity implements OnClickListener, ComprobantePagoServicioView {
    @InjectView(2131361997)
    Button btnVolver;
    protected ComprobantePagoServicioPresenter comprobantePagoPresenter;
    @InjectView(2131362027)
    TextView lblNroComprobante;
    @InjectView(2131362028)
    TextView lblNroControl;
    protected CuentaDebitoBean mCuenta;
    protected DatosDeudaBean mDeuda;
    protected String mOrigen;
    protected PagoServiciosBodyResponseBean mPago;
    ImageView p;
    ImageView q;
    @Inject
    AnalyticsManager r;
    protected Boolean receiptHasBeenDownloaded;
    @InjectView(2131362031)
    LinearLayout rowAnticipoCuota;
    @InjectView(2131362032)
    LinearLayout rowCUIT;
    @InjectView(2131362033)
    LinearLayout rowCUR;
    @InjectView(2131362034)
    LinearLayout rowFactura;
    @InjectView(2131362035)
    LinearLayout rowICS;
    @InjectView(2131362036)
    LinearLayout rowPeriodo;
    @InjectView(2131362037)
    LinearLayout rowVEP;
    @InjectView(2131362055)
    ScrollView scrComprobante;
    protected OptionsToShare sharingOptions;
    protected OptionsToShare sharingOptionsAtExit;
    @InjectView(2131362002)
    TextView txtAnticipoCuota;
    @InjectView(2131362003)
    TextView txtCUIT;
    @InjectView(2131362005)
    TextView txtEmpresa;
    @InjectView(2131362006)
    TextView txtFactura;
    @InjectView(2131362007)
    TextView txtFecha;
    @InjectView(2131362008)
    TextView txtHora;
    @InjectView(2131362009)
    TextView txtICS;
    @InjectView(2131362010)
    TextView txtIdentificador;
    @InjectView(2131362011)
    TextView txtImporte;
    @InjectView(2131362025)
    TextView txtLegales;
    @InjectView(2131362012)
    TextView txtMedioPago;
    @InjectView(2131362013)
    TextView txtNroComprobante;
    @InjectView(2131362014)
    TextView txtNroControl;
    @InjectView(2131362015)
    TextView txtPeriodo;
    @InjectView(2131362016)
    TextView txtVEP;

    public void clearScreenData() {
    }

    public int getMainLayout() {
        return R.layout.activity_comprobante_pago_servicio;
    }

    public void initialize() {
        this.receiptHasBeenDownloaded = Boolean.valueOf(true);
        this.mOrigen = getIntent().getStringExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN);
        this.mDeuda = (DatosDeudaBean) getIntent().getParcelableExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA);
        this.mCuenta = (CuentaDebitoBean) getIntent().getParcelableExtra(NuevoPagoServiciosConstants.EXTRA_CUENTA);
        this.mPago = (PagoServiciosBodyResponseBean) getIntent().getParcelableExtra(NuevoPagoServiciosConstants.EXTRA_PAGO);
        this.btnVolver.setOnClickListener(this);
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.p = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        this.q = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.menu);
        this.p.setOnClickListener(this);
        this.q.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void configureOptionsShare() {
        this.sharingOptions = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public String getFileName() {
                return "COMPROBANTE_NUEVO_PAGO_SERVICIOS";
            }

            public View getViewToShare() {
                return ComprobanteNuevoPagoActivity.this.scrComprobante;
            }

            public void receiveIntentAppShare(Intent intent) {
                ComprobanteNuevoPagoActivity.this.startActivityForResult(Intent.createChooser(intent, ComprobanteNuevoPagoActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 2);
            }

            public String getSubjectReceiptToShare() {
                ComprobanteNuevoPagoActivity.this.r.trackEvent(ComprobanteNuevoPagoActivity.this.getString(R.string.analytics_category_payment_services), ComprobanteNuevoPagoActivity.this.getString(R.string.analytics_action_payment_services_share_receipt), ComprobanteNuevoPagoActivity.this.getString(R.string.analytics_label_payment_services_share_receipt_download));
                return "Comprobante Nuevo Pago Servicios";
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                ComprobanteNuevoPagoActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                ComprobanteNuevoPagoActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
            }

            public void onAbortShare() {
                super.onAbortShare();
                ComprobanteNuevoPagoActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
                ComprobanteNuevoPagoActivity.this.onBackPressed();
            }
        };
    }

    /* access modifiers changed from: protected */
    public void configureOptionsShare(int i) {
        final int i2 = i;
        AnonymousClass2 r0 = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public String getFileName() {
                return "COMPROBANTE_NUEVO_PAGO_SERVICIOS";
            }

            public String getSubjectReceiptToShare() {
                return "Comprobante Nuevo Pago Servicios";
            }

            public View getViewToShare() {
                return ComprobanteNuevoPagoActivity.this.scrComprobante;
            }

            public void receiveIntentAppShare(Intent intent) {
                ComprobanteNuevoPagoActivity.this.startActivityForResult(Intent.createChooser(intent, ComprobanteNuevoPagoActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 2);
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                ComprobanteNuevoPagoActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                ComprobanteNuevoPagoActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
            }

            public void onAbortShare() {
                super.onAbortShare();
                ComprobanteNuevoPagoActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
                ComprobanteNuevoPagoActivity.this.onClickItem(i2);
            }
        };
        this.sharingOptionsAtExit = r0;
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.sharingOptions.onRequestPermissionsResult(i, strArr, iArr);
    }

    public String getSelectedOption() {
        return getString(R.string.ID17_PRIVATEMENU_BTN_SERVICEPAYM);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureOptionsShare();
        configureLayout();
        try {
            this.r.trackScreen(getString(R.string.analytics_screen_name_receipt_payment_services_home));
            this.r.trackTransaction(getString(R.string.analytics_category_payment_services), this.mPago.nroComp);
        } catch (Exception unused) {
        }
        this.comprobantePagoPresenter = new ComprobantePagoServicioPresenter(this.mBus, this.mDataManager);
        this.comprobantePagoPresenter.attachView(this);
        this.comprobantePagoPresenter.showComprobantePago();
    }

    public void attachView() {
        if (!this.comprobantePagoPresenter.isViewAttached()) {
            this.comprobantePagoPresenter.attachView(this);
        }
    }

    public void detachView() {
        if (this.comprobantePagoPresenter.isViewAttached()) {
            this.comprobantePagoPresenter.detachView();
        }
    }

    public void onBackPressed() {
        if (!this.receiptHasBeenDownloaded.booleanValue()) {
            this.sharingOptions.showAlert();
            return;
        }
        setResult(-1, new Intent());
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void showComprobantePago() {
        this.txtEmpresa.setText(this.mPago.empDescr);
        this.txtIdentificador.setText(this.mPago.identificacion);
        this.txtImporte.setText(this.mPago.importe);
        this.txtMedioPago.setText(this.mPago.descCtaDebito);
        if (this.rowFactura.getVisibility() != 8) {
            this.txtFactura.setText(this.mPago.factura);
        }
        if (this.rowCUR.getVisibility() != 8) {
            this.txtCUIT.setText(this.mPago.cur);
        }
        if (this.rowCUIT.getVisibility() != 8) {
            this.txtCUIT.setText(CUIT.format(this.mPago.cuit));
        }
        this.txtNroComprobante.setText(this.mPago.nroComp);
        this.txtNroControl.setText(this.mPago.nroControl);
        this.txtFecha.setText(this.mPago.fecha);
        this.txtHora.setText(this.mPago.hora);
        this.txtLegales.setText(Html.fromHtml(TextUtils.isEmpty(this.mPago.leyendaComp) ? "" : this.mPago.leyendaComp));
        try {
            this.txtEmpresa.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.txtEmpresa.getText().toString()));
            this.txtIdentificador.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.txtIdentificador.getText().toString()));
            this.txtImporte.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterAmount(this.txtImporte.getText().toString()));
            this.txtMedioPago.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterAccount(this.txtMedioPago.getText().toString()));
            if (this.rowFactura.getVisibility() != 8) {
                this.txtFactura.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.txtFactura.getText().toString()));
            }
            if (!(this.rowCUR.getVisibility() == 8 && this.rowCUIT.getVisibility() == 8)) {
                this.txtCUIT.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.txtCUIT.getText().toString()));
            }
            this.txtNroComprobante.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.txtNroComprobante.getText().toString()));
            this.txtNroControl.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.txtNroControl.getText().toString()));
            this.txtFecha.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterDate(this.txtFecha.getText().toString()));
            this.txtHora.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterTime(this.txtHora.getText().toString()));
            this.lblNroComprobante.setContentDescription(new CAccessibility(this).applyFilterControlNumber(this.lblNroComprobante.getText().toString()));
            this.lblNroControl.setContentDescription(new CAccessibility(this).applyFilterControlNumber(this.lblNroControl.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean canExit(int i) {
        if (!this.receiptHasBeenDownloaded.booleanValue()) {
            configureOptionsShare(i);
            this.sharingOptionsAtExit.showAlert();
        }
        return this.receiptHasBeenDownloaded.booleanValue();
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.F06_04_BTN_VOLVER) {
            onBackPressed();
        } else if (id2 == R.id.menu) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
            arrayList.add(getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("mPopupMenu", "Comprobante", null, arrayList, getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL), null, null, null, arrayList);
            newInstance.setDialogListener(new IDialogListener() {
                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                }

                public void onItemSelected(String str) {
                    if (str.equalsIgnoreCase(ComprobanteNuevoPagoActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                        ComprobanteNuevoPagoActivity.this.r.trackEvent(ComprobanteNuevoPagoActivity.this.getString(R.string.analytics_category_payment_services), ComprobanteNuevoPagoActivity.this.getString(R.string.analytics_action_payment_services_share_receipt), ComprobanteNuevoPagoActivity.this.getString(R.string.analytics_label_payment_services_share_receipt_download));
                        ComprobanteNuevoPagoActivity.this.sharingOptions.optionShareSelected();
                    } else if (str.equalsIgnoreCase(ComprobanteNuevoPagoActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                        ComprobanteNuevoPagoActivity.this.r.trackEvent(ComprobanteNuevoPagoActivity.this.getString(R.string.analytics_category_payment_services), ComprobanteNuevoPagoActivity.this.getString(R.string.analytics_action_payment_services_share_receipt), ComprobanteNuevoPagoActivity.this.getString(R.string.analytics_label_payment_services_share_receipt_download));
                        ComprobanteNuevoPagoActivity.this.sharingOptions.optionDownloadSelected();
                    }
                }
            });
            newInstance.setCancelable(true);
            newInstance.show(getSupportFragmentManager(), "mPopupMenu");
        } else if (id2 == R.id.toggle) {
            switchDrawer();
        }
    }
}
