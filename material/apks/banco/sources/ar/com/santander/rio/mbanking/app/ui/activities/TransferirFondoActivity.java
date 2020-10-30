package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.funds.ComprobanteTransferirFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.ComprobanteTransferirFondoView;
import ar.com.santander.rio.mbanking.app.module.funds.ConfirmarTransferirFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.ConfirmarTransferirFondoView;
import ar.com.santander.rio.mbanking.app.module.funds.EvaluacionRiesgoTransferirFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.EvaluacionRiesgoTransferirFondoView;
import ar.com.santander.rio.mbanking.app.module.funds.FueraHorarioFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.FueraHorarioFondoView;
import ar.com.santander.rio.mbanking.app.module.funds.TransferirFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.TransferirFondoView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants.LeyendasLegales;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AlertaEvaluacionRiesgoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DisclaimerFondo;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SimularTransferenciaFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferirFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilFile;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.CustomSpinner;
import ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class TransferirFondoActivity extends MvpPrivateMenuActivity implements OnClickListener, ComprobanteTransferirFondoView, ConfirmarTransferirFondoView, EvaluacionRiesgoTransferirFondoView, FueraHorarioFondoView, TransferirFondoView {
    /* access modifiers changed from: private */
    public CuentaFondosBean A;
    private ArrayList<CuentaFondosBean> B;
    /* access modifiers changed from: private */
    public IsbanDialogFragment C;
    /* access modifiers changed from: private */
    public OptionsToShare D;
    /* access modifiers changed from: private */
    public boolean E = false;
    /* access modifiers changed from: private */
    public SimularTransferenciaFondoBodyResponseBean F;
    /* access modifiers changed from: private */
    public TransferirFondoBodyResponseBean G;
    private ArrayList<Leyenda> H;
    @InjectView(2131363318)
    TextView btnAceptarRiesgo;
    @InjectView(2131363241)
    Button btnConfirmar;
    @InjectView(2131363217)
    Button btnContinuar;
    @InjectView(2131363319)
    TextView btnNoAceptaRiesgo;
    @InjectView(2131363278)
    Button btnVolver;
    @InjectView(2131363317)
    ScrollView comprobanteOperacion;
    @InjectView(2131363218)
    ImageView imgCheckBoxValidar;
    @InjectView(2131363242)
    ImageView imgCheckboxReglamento;
    @InjectView(2131363212)
    NumericEditTextWithPrefixAccesibility inpAmount;
    @InjectView(2131363219)
    TextView lblAceptacionEjecucion;
    @InjectView(2131363279)
    TextView lblCertificadoComprobante;
    @InjectView(2131363301)
    TextView lblComprobanteTerminosLegales;
    @InjectView(2131363243)
    TextView lblConectorVerTyC;
    @InjectView(2131363262)
    TextView lblConfirmarTerminosLegales;
    @InjectView(2131363283)
    TextView lblDataComprobanteCertificado;
    @InjectView(2131363284)
    TextView lblDataComprobanteCuentaTitulo;
    @InjectView(2131363287)
    TextView lblDataComprobanteFechaHora;
    @InjectView(2131363288)
    TextView lblDataComprobanteFondoDestinoBottom;
    @InjectView(2131363285)
    TextView lblDataComprobanteFondoDestinoCuotapartesBottom;
    @InjectView(2131363289)
    TextView lblDataComprobanteFondoDestinoHeader;
    @InjectView(2131363290)
    TextView lblDataComprobanteFondoOrigenBottom;
    @InjectView(2131363286)
    TextView lblDataComprobanteFondoOrigenCuotapartesBottom;
    @InjectView(2131363291)
    TextView lblDataComprobanteFondoOrigenHeader;
    @InjectView(2131363292)
    TextView lblDataComprobanteImporte;
    @InjectView(2131363293)
    TextView lblDataComprobanteImporteEstimadoBottom;
    @InjectView(2131363294)
    TextView lblDataComprobanteValorCuotapartesDestinoBottom;
    @InjectView(2131363247)
    TextView lblDataConfirmarCuentaTitulo;
    @InjectView(2131363250)
    TextView lblDataConfirmarFondoDestino;
    @InjectView(2131363248)
    TextView lblDataConfirmarFondoDestinoCuotapartes;
    @InjectView(2131363251)
    TextView lblDataConfirmarFondoDestinoHeader;
    @InjectView(2131363252)
    TextView lblDataConfirmarFondoOrigen;
    @InjectView(2131363249)
    TextView lblDataConfirmarFondoOrigenCuotapartes;
    @InjectView(2131363253)
    TextView lblDataConfirmarFondoOrigenHeader;
    @InjectView(2131363254)
    TextView lblDataConfirmarImporte;
    @InjectView(2131363255)
    TextView lblDataConfirmarImporteDetail;
    @InjectView(2131363256)
    TextView lblDataConfirmarValorCuotapartesDestino;
    @InjectView(2131363257)
    TextView lblDataConfirmarValorCuotapartesOrigen;
    @InjectView(2131363222)
    TextView lblDataCuentaTitulo;
    @InjectView(2131363223)
    TextView lblDataCuotapartes;
    @InjectView(2131363225)
    TextView lblDataFondo;
    @InjectView(2131363224)
    TextView lblDataFondoDestino;
    @InjectView(2131363226)
    TextView lblDataImporteTotal;
    @InjectView(2131363320)
    TextView lblDataInfoRiesgo;
    @InjectView(2131363227)
    TextView lblDataValorCuotapartes;
    @InjectView(2131363295)
    TextView lblDataValorCuotapartesOrigenBottom;
    @InjectView(2131364053)
    TextView lblFueraHorarioMensaje;
    @InjectView(2131363213)
    TextView lblSelectedAmountType;
    @InjectView(2131363153)
    TextView lblVerReglamento;
    @InjectView(2131363266)
    TextView lblVerTyC;
    @InjectView(2131363231)
    TextView leyendaLegal;
    @InjectView(2131363216)
    LinearLayout lnlAceptacionEjecucion;
    @InjectView(2131363234)
    LinearLayout lnlCuotapartes;
    @InjectView(2131363233)
    LinearLayout lnlSelectorCuentas;
    @InjectView(2131363238)
    LinearLayout lnlValorCuotaparte;
    @InjectView(2131364705)
    ViewFlipper mControlPager;
    @Inject
    AnalyticsManager p;
    Boolean q = Boolean.valueOf(false);
    Boolean r = Boolean.valueOf(false);
    @InjectView(2131363215)
    RelativeLayout rllSelectedAmountType;
    @InjectView(2131363272)
    LinearLayout rowCuotapartes;
    @InjectView(2131363310)
    LinearLayout rowCuotapartesComp;
    @InjectView(2131363271)
    LinearLayout rowCuotapartesComprobante;
    @InjectView(2131363309)
    LinearLayout rowCuotapartesdestinoComp;
    @InjectView(2131363315)
    LinearLayout rowValorCuotaparteDestinoComp;
    @InjectView(2131363316)
    LinearLayout rowValorCuotapartesComp;
    @InjectView(2131363277)
    LinearLayout rowValorCuotapartesComprobante;
    @InjectView(2131363276)
    LinearLayout rowValorCuotapartesDestinoComprobante;
    private TransferirFondoPresenter s;
    private ConfirmarTransferirFondoPresenter t;
    /* access modifiers changed from: private */
    public EvaluacionRiesgoTransferirFondoPresenter u;
    private FueraHorarioFondoPresenter v;
    @InjectView(2131363239)
    CustomSpinner vgSelectorAccount;
    private ComprobanteTransferirFondoPresenter w;
    private String x;
    private FondoBean y;
    private FondoBean z;

    public void configureActionBar() {
    }

    public void configureLayout() {
    }

    public Context getContext() {
        return this;
    }

    public int getMainLayout() {
        return R.layout.activity_transferir_fondo;
    }

    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        ButterKnife.inject((Activity) this);
        initialize();
        this.lblDataFondo.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                TextView textView = (TextView) view;
                if (textView.getLineCount() >= 2) {
                    textView.setTextSize(12.0f);
                }
                TransferirFondoActivity.this.lblDataFondo.removeOnLayoutChangeListener(this);
                view.setVisibility(8);
                view.setVisibility(0);
            }
        });
        this.lblDataFondoDestino.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                TextView textView = (TextView) view;
                if (textView.getLineCount() >= 2) {
                    textView.setTextSize(12.0f);
                }
                TransferirFondoActivity.this.lblDataFondoDestino.removeOnLayoutChangeListener(this);
                view.setVisibility(8);
                view.setVisibility(0);
            }
        });
        this.p.trackScreen(getString(R.string.analytics_screen_vista_pantalla_transferencias));
    }

    public void initialize() {
        this.s = new TransferirFondoPresenter(this.mBus, this.mDataManager);
        this.t = new ConfirmarTransferirFondoPresenter(this.mBus, this.mDataManager);
        this.u = new EvaluacionRiesgoTransferirFondoPresenter(this.mBus, this.mDataManager);
        this.v = new FueraHorarioFondoPresenter(this.mBus);
        this.w = new ComprobanteTransferirFondoPresenter(this.mBus, this.mDataManager);
        this.B = getIntent().getParcelableArrayListExtra("CUENTAS");
        this.A = (CuentaFondosBean) getIntent().getParcelableExtra("CUENTA");
        this.y = (FondoBean) getIntent().getParcelableExtra(FondosConstants.INTENT_EXTRA_FONDO);
        this.H = getIntent().getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LEGALES);
        String stringExtra = getIntent().getStringExtra(FondosConstants.INTENT_EXTRA_CONTRATO);
        this.x = getIntent().getStringExtra("ORIGEN");
        if (TextUtils.isEmpty(this.x)) {
            this.x = "DETALLE_FONDO";
        }
        this.r = Boolean.valueOf(!TextUtils.isEmpty(stringExtra) && !stringExtra.equalsIgnoreCase("0"));
        gotoTransferirFondo();
    }

    public void gotoTransferirFondo() {
        gotoPage(0);
        this.s.onCreatePage();
    }

    public void setTransferirFondoView() {
        setActionBarTransferir();
        this.p.trackScreen(getString(R.string.analytics_screen_seleccion_importe_transferir));
        this.lnlSelectorCuentas.setVisibility(8);
        this.rllSelectedAmountType.setOnClickListener(this);
        this.lblDataFondoDestino.setOnClickListener(this);
        this.btnContinuar.setOnClickListener(this);
        FondoBean fondoBean = this.y != null ? this.y : null;
        setSelectedAccount(this.A);
        if (fondoBean != null) {
            setSelectedFondoOrigen(fondoBean);
        }
        resetSelectedDestinationFund();
        this.inpAmount.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                TransferirFondoActivity.this.f();
            }
        });
        this.lnlCuotapartes.setVisibility(8);
        this.lnlValorCuotaparte.setVisibility(8);
        if (!b()) {
            e();
            this.imgCheckBoxValidar.setOnClickListener(this);
        } else {
            d();
        }
        if (this.H == null || this.H.size() <= 0) {
            this.leyendaLegal.setText("");
        } else {
            for (int i = 0; i < this.H.size(); i++) {
                if (((Leyenda) this.H.get(i)).getIdLeyenda().equals(LeyendasLegales.SIMULACION)) {
                    this.leyendaLegal.setText(Html.fromHtml(((Leyenda) this.H.get(i)).getDescripcion()));
                }
            }
        }
        try {
            this.leyendaLegal.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.leyendaLegal.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        f();
    }

    private boolean b() {
        return this.r.booleanValue();
    }

    private void c() {
        if (!b()) {
            d();
        } else {
            e();
        }
    }

    private void d() {
        this.imgCheckBoxValidar.setBackground(getResources().getDrawable(R.drawable.ic_checkbox_on_rojo));
        this.r = Boolean.valueOf(true);
        f();
    }

    private void e() {
        this.imgCheckBoxValidar.setBackground(getResources().getDrawable(R.drawable.ic_checkbox_off_gris));
        this.r = Boolean.valueOf(false);
        f();
    }

    /* access modifiers changed from: private */
    public void f() {
        if (isValidDataTransferir()) {
            h();
        } else {
            g();
        }
    }

    private void g() {
        this.btnContinuar.setEnabled(false);
        this.btnContinuar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
    }

    private void h() {
        this.btnContinuar.setEnabled(true);
        this.btnContinuar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
    }

    public void setActionBarTransferir() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        i();
    }

    private void i() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TransferirFondoActivity.this.onBackPressed();
                }
            });
        }
    }

    private void j() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public boolean isValidDataTransferir() {
        return !TextUtils.isEmpty(this.inpAmount.getText()) && this.z != null && this.r.booleanValue();
    }

    public void setSelectedAmountTypeTotal() {
        this.lblSelectedAmountType.setText(getString(R.string.F24_30_SELECTED_AMOUNT_TOTAL));
        this.inpAmount.setPrefix(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.y.getMoneda()).toString()));
        this.inpAmount.setText(this.y.getImporte());
        this.inpAmount.setEnabled(false);
        this.p.trackScreen(getString(R.string.analytics_screen_vista_pantalla_importe_total_transferir));
    }

    public void setSelectedAmountTypeOtro() {
        this.lblSelectedAmountType.setText(getString(R.string.F24_30_SELECTED_AMOUNT_OTRO));
        this.inpAmount.setPrefix(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.y.getMoneda()).toString()));
        this.inpAmount.setText("");
        this.inpAmount.setEnabled(true);
        this.p.trackScreen(getString(R.string.analytics_screen_vista_pantalla_otro_importe_transferir));
    }

    public void setSelectedAmountTypeCuotapartes() {
        this.lblSelectedAmountType.setText(getString(R.string.F24_30_SELECTED_AMOUNT_CUOTAPARTES));
        this.inpAmount.setPrefix("");
        this.inpAmount.setText(this.y.getCantidadCuotapartes());
        this.inpAmount.setEnabled(true);
    }

    public void setSelectedAccount(CuentaFondosBean cuentaFondosBean) {
        this.A = cuentaFondosBean;
        TextView textView = this.lblDataCuentaTitulo;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.F24_31_lbl_cuentaTitulo));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.A.getNumero());
        textView.setText(sb.toString());
        if (this.A.getListaFondos() == null || this.A.getListaFondos().getFondosBean().size() <= 0) {
            this.y = null;
            this.lblDataFondo.setText(getString(R.string.F24_20_lbl_data_seleccionar));
            this.lblDataFondo.setTextColor(getResources().getColor(R.color.generic_selectable));
            this.lblDataFondo.setEnabled(false);
        } else {
            setSelectedFondoOrigen((FondoBean) this.A.getListaFondos().getFondosBean().get(0));
        }
        resetSelectedDestinationFund();
        f();
    }

    public void setSelectedFondoOrigen(FondoBean fondoBean) {
        this.y = fondoBean;
        String nombre = this.y.getNombre();
        this.lblDataFondo.setText(Html.fromHtml(nombre));
        try {
            this.lblDataFondo.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), nombre));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.y.getMoneda();
        this.y.getImporte();
        TextView textView = this.lblDataImporteTotal;
        StringBuilder sb = new StringBuilder();
        sb.append(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.y.getMoneda()).toString()));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.y.getImporte());
        textView.setText(sb.toString());
        try {
            this.lblDataImporteTotal.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblDataImporteTotal.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.lblDataCuotapartes.setText(this.y.getCantidadCuotapartes());
        this.lblDataValorCuotapartes.setText(this.y.getValorCuotaParte());
        if (this.x.equalsIgnoreCase(FondosConstants.ORIGEN_TENENCIAS)) {
            this.lblDataFondo.setTextColor(getResources().getColor(R.color.generic_selectable));
            this.lblDataFondo.setOnClickListener(this);
        } else {
            this.lblDataFondo.setTextColor(getResources().getColor(R.color.generic_selectable));
        }
        setSelectedAmountTypeTotal();
    }

    public void setSelectedFondoDestino(FondoBean fondoBean) {
        this.z = fondoBean;
        this.lblDataFondoDestino.setText(Html.fromHtml(this.z.getNombre()));
        try {
            this.lblDataFondoDestino.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataFondoDestino.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        f();
    }

    public void resetSelectedDestinationFund() {
        this.z = null;
        this.lblDataFondoDestino.setText(getString(R.string.F24_30_lbl_seleccionarDestino));
        f();
    }

    public void gotoConfirmacion(SimularTransferenciaFondoBodyResponseBean simularTransferenciaFondoBodyResponseBean) {
        this.F = simularTransferenciaFondoBodyResponseBean;
        gotoPage(1);
        this.t.onCreatePage(simularTransferenciaFondoBodyResponseBean);
    }

    public void setConfirmarTransferirFondoView(final SimularTransferenciaFondoBodyResponseBean simularTransferenciaFondoBodyResponseBean) {
        setActionBarConfirmarTransferir();
        if (this.lblSelectedAmountType.getText().toString().equalsIgnoreCase(getString(R.string.F24_30_SELECTED_AMOUNT_OTRO))) {
            this.p.trackScreen(getString(R.string.analytics_screen_vista_otro_importe_confirmacion_transferencia));
        } else {
            this.p.trackScreen(getString(R.string.analytics_screen_vista_importe_total_confirmacion_transferencia));
        }
        this.lblDataConfirmarFondoOrigenHeader.setText(Html.fromHtml(this.y.getNombre()));
        try {
            this.lblDataConfirmarFondoOrigenHeader.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataConfirmarFondoOrigenHeader.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.rowCuotapartes.setVisibility(8);
        TextView textView = this.lblDataConfirmarImporte;
        StringBuilder sb = new StringBuilder();
        sb.append(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.z.getMoneda()).toString()));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(simularTransferenciaFondoBodyResponseBean.getImporteTransferir());
        textView.setText(sb.toString());
        try {
            this.lblDataConfirmarImporte.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(this.lblDataConfirmarImporte.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.lblDataConfirmarFondoDestinoHeader.setText(Html.fromHtml(this.z.getNombre()));
        try {
            this.lblDataConfirmarFondoDestinoHeader.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataConfirmarFondoDestinoHeader.getText().toString()));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        this.lblDataConfirmarCuentaTitulo.setText(UtilAccount.formatCuentaTitulo(this.A.getNumero()));
        try {
            this.lblDataConfirmarCuentaTitulo.setContentDescription(new CAccessibility(getContext()).applyFilterCharacterToCharacter(this.lblDataConfirmarCuentaTitulo.getText().toString()));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        TextView textView2 = this.lblDataConfirmarImporteDetail;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.z.getMoneda()).toString()));
        sb2.append(UtilsCuentas.SEPARAOR2);
        sb2.append(simularTransferenciaFondoBodyResponseBean.getImporteTransferir());
        textView2.setText(sb2.toString());
        try {
            this.lblDataConfirmarImporteDetail.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(this.lblDataConfirmarImporteDetail.getText().toString()));
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        this.lblDataConfirmarFondoOrigen.setText(Html.fromHtml(this.y.getNombre()));
        try {
            this.lblDataConfirmarFondoOrigen.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataConfirmarFondoOrigen.getText().toString()));
        } catch (Exception e6) {
            e6.printStackTrace();
        }
        this.lblDataConfirmarFondoOrigenCuotapartes.setText(simularTransferenciaFondoBodyResponseBean.getCantCuotaOrigen());
        this.rowValorCuotapartesComprobante.setVisibility(8);
        this.lblDataConfirmarFondoDestino.setText(Html.fromHtml(this.z.getNombre()));
        try {
            this.lblDataConfirmarFondoDestino.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataConfirmarFondoDestino.getText().toString()));
        } catch (Exception e7) {
            e7.printStackTrace();
        }
        this.rowCuotapartesComprobante.setVisibility(8);
        this.rowValorCuotapartesDestinoComprobante.setVisibility(8);
        n();
        this.imgCheckboxReglamento.setVisibility(0);
        this.imgCheckboxReglamento.setOnClickListener(this);
        this.lblConectorVerTyC.setText(getString(R.string.F24_32_BTN_TERCONDIC_VER));
        this.lblVerTyC.setOnClickListener(this);
        this.lblConfirmarTerminosLegales.setText(Html.fromHtml(a(simularTransferenciaFondoBodyResponseBean.getListaLegales().getLeyendaLegales())));
        try {
            this.lblConfirmarTerminosLegales.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblConfirmarTerminosLegales.getText().toString()));
        } catch (Exception e8) {
            e8.printStackTrace();
        }
        if (!simularTransferenciaFondoBodyResponseBean.getReglamento().isEmpty()) {
            this.lblVerReglamento.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    String obj = Html.fromHtml(simularTransferenciaFondoBodyResponseBean.getReglamento()).toString();
                    TransferirFondoActivity transferirFondoActivity = TransferirFondoActivity.this;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Reglamento - ");
                    sb.append(Html.fromHtml(simularTransferenciaFondoBodyResponseBean.getNombreDestino()));
                    sb.append(".pdf");
                    transferirFondoActivity.downloadFiles(obj, sb.toString());
                }
            });
        }
    }

    public void downloadFiles(final String str, final String str2) {
        showProgressIndicator("downloadFile");
        new Thread(new Runnable() {
            public void run() {
                try {
                    if (TextUtils.isEmpty(UtilFile.downloadFundFile(str2, str, TransferirFondoActivity.this.getContext()))) {
                        new Handler(TransferirFondoActivity.this.getContext().getMainLooper()).post(new Runnable() {
                            public void run() {
                                TransferirFondoActivity.this.dismissProgressIndicator();
                                TransferirFondoActivity.this.popUpErrorDownload();
                            }
                        });
                    } else {
                        new Handler(TransferirFondoActivity.this.getContext().getMainLooper()).post(new Runnable() {
                            public void run() {
                                TransferirFondoActivity.this.dismissProgressIndicator();
                                Toast.makeText(TransferirFondoActivity.this.getContext(), TransferirFondoActivity.this.getContext().getString(R.string.MSG_USER0000XX_FILE_DOWNLOAD_OK), 1).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    new Handler(TransferirFondoActivity.this.getContext().getMainLooper()).post(new Runnable() {
                        public void run() {
                            TransferirFondoActivity.this.dismissProgressIndicator();
                            TransferirFondoActivity.this.popUpErrorDownload();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String a(List<String> list) {
        String str = "";
        for (String concat : list) {
            str = str.concat(concat).concat("<br>");
        }
        return str;
    }

    private boolean k() {
        return this.q.booleanValue();
    }

    private void l() {
        if (!k()) {
            m();
        } else {
            n();
        }
    }

    private void m() {
        this.imgCheckboxReglamento.setBackground(getResources().getDrawable(R.drawable.ic_checkbox_on_rojo));
        this.btnConfirmar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
        this.q = Boolean.valueOf(true);
        this.btnConfirmar.setOnClickListener(this);
    }

    private void n() {
        this.imgCheckboxReglamento.setBackground(getResources().getDrawable(R.drawable.ic_checkbox_off_gris));
        this.btnConfirmar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
        this.q = Boolean.valueOf(false);
    }

    public void setActionBarConfirmarTransferir() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        o();
    }

    private void o() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TransferirFondoActivity.this.onBackPressed();
                }
            });
        }
    }

    private void p() {
        gotoPage(0, false);
        setActionBarTransferir();
    }

    public void gotoEvaluacionRiesgoTransferirFondo(AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean, String str, String str2) {
        gotoPage(2);
        this.u.onCreatePage(alertaEvaluacionRiesgoBean, str, str2);
    }

    public void setEvaluacionRiesgoTransferenciaView(AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean, final String str, final String str2) {
        setActionBarEvaluacionRiesgoTransferencia();
        this.lblDataInfoRiesgo.setText(Html.fromHtml(a(alertaEvaluacionRiesgoBean)));
        try {
            this.lblDataInfoRiesgo.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblDataInfoRiesgo.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.btnAceptarRiesgo.setText(getString(R.string.ID_4014_FONDOS_BTN_AVIRIESGO_TRANSFERENCIA));
        this.btnAceptarRiesgo.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TransferirFondoActivity.this.u.callTransferirFondo(TransferirFondoActivity.this.F, TransferirFondoActivity.this.A, str, str2);
            }
        });
        this.btnNoAceptaRiesgo.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TransferirFondoActivity.this.onBackPressed();
            }
        });
    }

    private String a(AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean) {
        String str = "";
        if (!TextUtils.isEmpty(alertaEvaluacionRiesgoBean.getCabecera())) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("<b>");
            sb.append(alertaEvaluacionRiesgoBean.getCabecera());
            sb.append("</b><br><br>");
            str = sb.toString();
        }
        for (DisclaimerFondo disclaimerFondo : alertaEvaluacionRiesgoBean.getDisclaimers().getDisclaimer()) {
            if (!TextUtils.isEmpty(disclaimerFondo.getTitulo())) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append("<b>");
                sb2.append(disclaimerFondo.getTitulo());
                sb2.append("</b><br><br>");
                str = sb2.toString();
            }
            if (!TextUtils.isEmpty(disclaimerFondo.getTexto())) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str);
                sb3.append(disclaimerFondo.getTexto());
                sb3.append("<br><br>");
                str = sb3.toString();
            }
        }
        if (TextUtils.isEmpty(alertaEvaluacionRiesgoBean.getPie())) {
            return str;
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str);
        sb4.append(alertaEvaluacionRiesgoBean.getPie());
        sb4.append("<br>");
        return sb4.toString();
    }

    public void setActionBarEvaluacionRiesgoTransferencia() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        q();
    }

    private void q() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TransferirFondoActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonEvaluacionRiesgo() {
        if (getSelectedAmountType().equalsIgnoreCase(getString(R.string.F24_20_SELECTED_AMOUNT_TOTAL))) {
            getAnalyticsManager().trackEvent(getString(R.string.analytics_event_category_fondos), getString(R.string.analytics_event_action_cancelar_transferencia_aviso_legal), getString(R.string.analytics_event_label_importe_total));
        } else {
            getAnalyticsManager().trackEvent(getString(R.string.analytics_event_category_fondos), getString(R.string.analytics_event_action_cancelar_transferencia_aviso_legal), getString(R.string.analytics_event_label_cancelar_transferencia_otro_importe));
        }
        gotoPage(1, false);
        setActionBarConfirmarTransferir();
    }

    public void gotoFueraHorarioFondo(String str) {
        gotoPage(4);
        this.v.onCreatePage(str);
    }

    public void setFueraHorarioView(String str) {
        setActionBarFueraHorario();
        if (TextUtils.isEmpty(str)) {
            str = getString(R.string.MSG_USER_OUT_WORKING_HOUR);
        }
        this.lblFueraHorarioMensaje.setText(Html.fromHtml(str));
    }

    public void setActionBarFueraHorario() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        r();
    }

    private void r() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TransferirFondoActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonFueraHorario() {
        gotoPage(0, false);
        setActionBarTransferir();
    }

    public void gotoComprobante(TransferirFondoBodyResponseBean transferirFondoBodyResponseBean) {
        gotoPage(3);
        this.w.onCreatePage(transferirFondoBodyResponseBean);
    }

    public void setComprobanteTransferirFondoView(TransferirFondoBodyResponseBean transferirFondoBodyResponseBean) {
        this.G = transferirFondoBodyResponseBean;
        setActionBarComprobanteTransferir();
        if (this.lblSelectedAmountType.getText().toString().equalsIgnoreCase(getString(R.string.F24_30_SELECTED_AMOUNT_OTRO))) {
            this.p.trackScreen(getString(R.string.analytics_screen_vista_otro_importe_comprobante_transferencia));
        } else {
            this.p.trackScreen(getString(R.string.analytics_screen_vista_importe_total_comprobante_transferencia));
        }
        this.lblDataComprobanteFondoOrigenHeader.setText(Html.fromHtml(this.y.getNombre()));
        try {
            this.lblDataComprobanteFondoOrigenHeader.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataComprobanteFondoOrigenHeader.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView textView = this.lblDataComprobanteImporte;
        StringBuilder sb = new StringBuilder();
        sb.append(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.z.getMoneda()).toString()));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.F.getImporteTransferir());
        textView.setText(sb.toString());
        try {
            this.lblDataComprobanteImporte.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblDataComprobanteImporte.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.rowCuotapartesComp.setVisibility(8);
        this.lblDataComprobanteFondoDestinoHeader.setText(Html.fromHtml(this.z.getNombre()));
        try {
            this.lblDataComprobanteFondoDestinoHeader.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataComprobanteFondoDestinoHeader.getText().toString()));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        TextView textView2 = this.lblDataComprobanteFechaHora;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.G.getTransferencia().getOrigen().getFecha());
        sb2.append(UtilsCuentas.SEPARAOR2);
        sb2.append(this.G.getTransferencia().getOrigen().getHora());
        textView2.setText(sb2.toString());
        try {
            TextView textView3 = this.lblDataComprobanteFechaHora;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(new CAccessibility(getContext()).applyFilterDate(this.G.getTransferencia().getOrigen().getFecha()));
            sb3.append(UtilsCuentas.SEPARAOR2);
            sb3.append(new CAccessibility(getContext()).applyFilterTime(this.G.getTransferencia().getOrigen().getHora()));
            textView3.setContentDescription(sb3.toString());
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        this.lblDataComprobanteCuentaTitulo.setText(UtilAccount.formatCuentaTitulo(this.A.getNumero()));
        try {
            this.lblDataComprobanteCuentaTitulo.setContentDescription(new CAccessibility(getContext()).applyFilterCharacterToCharacter(this.lblDataComprobanteCuentaTitulo.getText().toString()));
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        TextView textView4 = this.lblDataComprobanteImporteEstimadoBottom;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.z.getMoneda()).toString()));
        sb4.append(UtilsCuentas.SEPARAOR2);
        sb4.append(this.F.getImporteTransferir());
        textView4.setText(sb4.toString());
        try {
            this.lblDataComprobanteImporteEstimadoBottom.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblDataComprobanteImporteEstimadoBottom.getText().toString()));
        } catch (Exception e6) {
            e6.printStackTrace();
        }
        this.lblDataComprobanteFondoOrigenBottom.setText(Html.fromHtml(this.y.getNombre()));
        try {
            this.lblDataComprobanteFondoOrigenBottom.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataComprobanteFondoOrigenBottom.getText().toString()));
        } catch (Exception e7) {
            e7.printStackTrace();
        }
        this.lblDataComprobanteFondoOrigenCuotapartesBottom.setText(transferirFondoBodyResponseBean.getTransferencia().getOrigen().getCantCuotaparte());
        this.rowValorCuotapartesComp.setVisibility(8);
        this.lblDataComprobanteFondoDestinoBottom.setText(Html.fromHtml(this.z.getNombre()));
        try {
            this.lblDataComprobanteFondoDestinoBottom.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataComprobanteFondoDestinoBottom.getText().toString()));
        } catch (Exception e8) {
            e8.printStackTrace();
        }
        this.rowCuotapartesdestinoComp.setVisibility(8);
        this.rowValorCuotaparteDestinoComp.setVisibility(8);
        try {
            this.lblCertificadoComprobante.setContentDescription(new CAccessibility(getContext()).applyFilterControlNumber(getResources().getString(R.string.F24_32_lbl_nroCertificado)));
        } catch (Exception e9) {
            e9.printStackTrace();
        }
        this.lblDataComprobanteCertificado.setText(transferirFondoBodyResponseBean.getTransferencia().getNroCertificado());
        try {
            this.lblDataComprobanteCertificado.setContentDescription(new CAccessibility(getContext()).applyFilterCharacterToCharacter(this.lblDataComprobanteCertificado.getText().toString()));
        } catch (Exception e10) {
            e10.printStackTrace();
        }
        this.lblComprobanteTerminosLegales.setText(Html.fromHtml(a(transferirFondoBodyResponseBean.getTransferencia().getLegales().getLeyendaLegales())));
        try {
            this.lblComprobanteTerminosLegales.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblComprobanteTerminosLegales.getText().toString()));
        } catch (Exception e11) {
            e11.printStackTrace();
        }
        this.btnVolver.setOnClickListener(this);
        this.p.trackTransaction(getString(R.string.analytics_transaction_hits_fondos), transferirFondoBodyResponseBean.getTransferencia().getNroCertificado());
    }

    public void setActionBarComprobanteTransferir() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(false);
        s();
    }

    private void s() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        ImageView imageView2 = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.menu);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TransferirFondoActivity.this.switchDrawer();
                }
            });
        }
        t();
        if (imageView2 != null) {
            imageView2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TransferirFondoActivity.this.C.show(TransferirFondoActivity.this.getSupportFragmentManager(), "Dialog");
                }
            });
        }
    }

    private void t() {
        this.D = u();
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        this.C = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_TITLE), null, arrayList, getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL), null, null, null, arrayList);
        this.C.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(TransferirFondoActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                    TransferirFondoActivity.this.D.optionShareSelected();
                } else if (str.equalsIgnoreCase(TransferirFondoActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                    TransferirFondoActivity.this.D.optionDownloadSelected();
                }
            }
        });
        this.C.setCancelable(true);
    }

    private OptionsToShare u() {
        return new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public View getViewToShare() {
                return TransferirFondoActivity.this.comprobanteOperacion;
            }

            public void receiveIntentAppShare(Intent intent) {
                TransferirFondoActivity.this.startActivity(Intent.createChooser(intent, TransferirFondoActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
            }

            public String getFileName() {
                return TransferirFondoActivity.this.getString(R.string.F24_32_COMPROBANTE_TRANSFERIR_FILENAME).concat(TransferirFondoActivity.this.G.getTransferencia().getNroCertificado());
            }

            public String getSubjectReceiptToShare() {
                return TransferirFondoActivity.this.getString(R.string.F24_32_COMPROBANTE_TRANSFERIR_SUBJECT);
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                TransferirFondoActivity.this.E = true;
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                TransferirFondoActivity.this.E = true;
            }

            public void onAbortShare() {
                super.onAbortShare();
                TransferirFondoActivity.this.E = true;
                TransferirFondoActivity.this.onBackPressed();
            }
        };
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.D.onRequestPermissionsResult(i, strArr, iArr);
    }

    public boolean canExit(int i) {
        if (!this.E) {
            final int i2 = i;
            AnonymousClass16 r1 = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
                public View getViewToShare() {
                    return TransferirFondoActivity.this.comprobanteOperacion;
                }

                public void receiveIntentAppShare(Intent intent) {
                    TransferirFondoActivity.this.startActivity(Intent.createChooser(intent, TransferirFondoActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
                }

                public String getFileName() {
                    return TransferirFondoActivity.this.getString(R.string.F24_32_COMPROBANTE_TRANSFERIR_FILENAME).concat(TransferirFondoActivity.this.G.getTransferencia().getNroCertificado());
                }

                public String getSubjectReceiptToShare() {
                    return TransferirFondoActivity.this.getString(R.string.F24_32_COMPROBANTE_TRANSFERIR_SUBJECT);
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    TransferirFondoActivity.this.E = true;
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    TransferirFondoActivity.this.E = true;
                }

                public void onAbortShare() {
                    super.onAbortShare();
                    TransferirFondoActivity.this.E = true;
                    TransferirFondoActivity.this.onClickItem(i2);
                }
            };
            r1.showAlert();
        }
        return this.E;
    }

    public void gotoPage(int i) {
        gotoPage(i, true);
    }

    public void gotoPage(int i, boolean z2) {
        if (this.mControlPager != null) {
            detachView();
            switch (i) {
                case 0:
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    }
                    break;
                case 1:
                    if (z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    }
                case 2:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    break;
                case 3:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    break;
                case 4:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    break;
            }
            this.mControlPager.setDisplayedChild(i);
            attachView();
        }
    }

    public AnalyticsManager getAnalyticsManager() {
        return this.p;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.F24_30_RLL_SELECTED_AMOUNT_TYPE /*2131363215*/:
                this.s.showAmountTypeDialog(this.lblSelectedAmountType);
                this.p.trackEvent(getString(R.string.analytics_event_category_fondos), getString(R.string.analytics_event_action_seleccion_importe), getString(R.string.analytics_event_label_importeTotal_otroImporte));
                return;
            case R.id.F24_30_btn_continuar /*2131363217*/:
                this.s.gotoConfirmar(this.A, this.y, this.z, this.inpAmount.getFormatedText().toString());
                return;
            case R.id.F24_30_img_checkbox /*2131363218*/:
                c();
                return;
            case R.id.F24_30_lbl_data_fondoDestino /*2131363224*/:
                this.lblDataFondoDestino.setOnClickListener(null);
                this.s.callGetFondos();
                return;
            case R.id.F24_30_lbl_data_fondoOrigen /*2131363225*/:
                onBackPressed();
                return;
            case R.id.F24_30_vgSelectorAccount /*2131363239*/:
                this.s.showSelectAccountDialog(this.A, this.B);
                return;
            case R.id.F24_31_btn_confirmar /*2131363241*/:
                this.t.gotoComprobante(this.F, this.A, this.lblSelectedAmountType.getText().toString());
                return;
            case R.id.F24_31_img_checkbox_tyc /*2131363242*/:
                l();
                return;
            case R.id.F24_31_lbl_verTerminos /*2131363266*/:
                this.t.showTyC(this.F.getTermCondiciones());
                return;
            case R.id.F24_32_btn_volver /*2131363278*/:
                onBackPressed();
                return;
            default:
                return;
        }
    }

    public void showSelectDestinyFundActivity(List<CategoriaFondosBean> list) {
        Intent intent = new Intent(this, SeleccionarInformacionFondoActivity.class);
        intent.putExtra("ORIGEN", FondosConstants.ORIGEN_TRANSFERIR);
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_CATEGORIAS, b(list));
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_FONDOS, v());
        startActivityForResult(intent, 3);
    }

    public void popUpErrorDownload() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getContext().getString(R.string.USER200029_TITLE), getContext().getString(R.string.MSG_USER0000XX_FILE_DOWNLOAD_ERROR), null, getContext().getString(R.string.accept), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                newInstance.closeDialog();
            }
        });
        newInstance.show(getSupportFragmentManager(), "popUpErrorDownload");
    }

    private ArrayList<CategoriaFondosBean> b(List<CategoriaFondosBean> list) {
        ArrayList<CategoriaFondosBean> arrayList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (CategoriaFondosBean categoriaFondosBean : list) {
                if (categoriaFondosBean.getFondosBean() != null && categoriaFondosBean.getFondosBean().getFondosBean().size() > 0) {
                    ArrayList arrayList2 = new ArrayList();
                    for (FondoBean fondoBean : categoriaFondosBean.getFondosBean().getFondosBean()) {
                        if (fondoBean.getAptoSuscrip().equalsIgnoreCase("S") && Integer.parseInt(fondoBean.getId()) != Integer.parseInt(this.y.getId())) {
                            if (UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.y.getMoneda()).toString()).equalsIgnoreCase(Constants.SYMBOL_CURRENCY_DOLAR) && fondoBean.getMoneda().equalsIgnoreCase("2")) {
                                arrayList2.add(fondoBean);
                            } else if (UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.y.getMoneda()).toString()).equalsIgnoreCase(Constants.SYMBOL_CURRENCY_PESOS) && fondoBean.getMoneda().equalsIgnoreCase("0")) {
                                arrayList2.add(fondoBean);
                            }
                        }
                    }
                    if (arrayList2.size() > 0) {
                        arrayList.add(new CategoriaFondosBean(categoriaFondosBean.getIdCategoria(), categoriaFondosBean.getNombreCategoria(), new ListaFondosBean((List<FondoBean>) arrayList2)));
                    }
                }
            }
        }
        return arrayList;
    }

    private ArrayList<FondoBean> v() {
        ArrayList<FondoBean> arrayList = new ArrayList<>();
        Iterator it = this.B.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            CuentaFondosBean cuentaFondosBean = (CuentaFondosBean) it.next();
            if (cuentaFondosBean.getNumero().equalsIgnoreCase(this.A.getNumero()) && cuentaFondosBean.getSucursalCuenta().equalsIgnoreCase(this.A.getSucursalCuenta()) && cuentaFondosBean.getListaFondos() != null && cuentaFondosBean.getListaFondos().getFondosBean().size() > 0) {
                for (FondoBean fondoBean : cuentaFondosBean.getListaFondos().getFondosBean()) {
                    if (!fondoBean.getId().equalsIgnoreCase(this.y.getId()) && fondoBean.getAptoSuscrip().equalsIgnoreCase("S") && Html.fromHtml(fondoBean.getMoneda()).toString().equalsIgnoreCase(Html.fromHtml(this.y.getMoneda()).toString())) {
                        arrayList.add(fondoBean);
                    }
                }
            }
        }
        return arrayList;
    }

    public void attachView() {
        switch (this.mControlPager.getDisplayedChild()) {
            case 0:
                if (!this.s.isViewAttached()) {
                    this.s.attachView(this);
                    return;
                }
                return;
            case 1:
                if (!this.t.isViewAttached()) {
                    this.t.attachView(this);
                    return;
                }
                return;
            case 2:
                if (!this.u.isViewAttached()) {
                    this.u.attachView(this);
                    return;
                }
                return;
            case 3:
                if (!this.w.isViewAttached()) {
                    this.w.attachView(this);
                    return;
                }
                return;
            case 4:
                if (!this.v.isViewAttached()) {
                    this.v.attachView(this);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void detachView() {
        if (this.s.isViewAttached()) {
            this.s.detachView();
        }
        if (this.t.isViewAttached()) {
            this.t.detachView();
        }
        if (this.u.isViewAttached()) {
            this.u.detachView();
        }
        if (this.v.isViewAttached()) {
            this.v.detachView();
        }
        if (this.w.isViewAttached()) {
            this.w.detachView();
        }
    }

    public void onBackPressed() {
        switch (this.mControlPager.getCurrentView().getId()) {
            case R.id.comprobante_transferir_fondo /*2131364350*/:
                if (!this.E) {
                    this.D.showAlert();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(FondosConstants.INTENT_EXTRA_RECARGAR_FONDOS, true);
                intent.putExtra("CUENTA", this.A);
                setResult(-1, intent);
                super.onBackPressed();
                hideKeyboard();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return;
            case R.id.confirmar_transferir_fondo /*2131364415*/:
                p();
                return;
            case R.id.layout_evaluacion_riesgo_fondo /*2131364926*/:
                backButtonEvaluacionRiesgo();
                return;
            case R.id.out_working_hours_fondo /*2131365284*/:
                backButtonFueraHorario();
                return;
            case R.id.transferir_fondo /*2131366074*/:
                j();
                return;
            default:
                return;
        }
    }

    public void clearScreenData() {
        if (this.lblSelectedAmountType.getText().toString().equalsIgnoreCase(getString(R.string.F24_30_SELECTED_AMOUNT_OTRO))) {
            this.inpAmount.setText("");
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        this.lblDataFondoDestino.setOnClickListener(this);
        if (i == 3 && i2 == -1 && intent.hasExtra(FondosConstants.INTENT_EXTRA_FONDO)) {
            setSelectedFondoDestino((FondoBean) intent.getParcelableExtra(FondosConstants.INTENT_EXTRA_FONDO));
        }
    }

    public void onDestroy() {
        detachView();
        super.onDestroy();
    }

    public String getSelectedAmountType() {
        return this.lblSelectedAmountType.getText().toString();
    }
}
