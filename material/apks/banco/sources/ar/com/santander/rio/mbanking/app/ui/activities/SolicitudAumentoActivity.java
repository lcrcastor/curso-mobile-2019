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
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.CAmountIU;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.TIS.ComprobanteSolicitudAumentoPresenter;
import ar.com.santander.rio.mbanking.app.module.TIS.ComprobanteSolicitudAumentoView;
import ar.com.santander.rio.mbanking.app.module.TIS.ConfirmarSolicitudAumentoPresenter;
import ar.com.santander.rio.mbanking.app.module.TIS.ConfirmarSolicitudAumentoView;
import ar.com.santander.rio.mbanking.app.module.TIS.SolicitudAumentoPresenter;
import ar.com.santander.rio.mbanking.app.module.TIS.SolicitudAumentoSecondPagePresenter;
import ar.com.santander.rio.mbanking.app.module.TIS.SolicitudAumentoSecondPageView;
import ar.com.santander.rio.mbanking.app.module.TIS.SolicitudAumentoView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.BuySellDollarsConstants.Dialogs;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants.accionVerificarDatosTransf;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants.leyendas;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaCreditosFragment;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment.IDateDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgendadosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentasPropiasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudLimiteTransfBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.inject.Inject;
import org.joda.time.DateTime;

public class SolicitudAumentoActivity extends MvpPrivateMenuActivity implements OnClickListener, ComprobanteSolicitudAumentoView, ConfirmarSolicitudAumentoView, SolicitudAumentoSecondPageView, SolicitudAumentoView {
    private SolicitudAumentoSecondPagePresenter A;
    private ConfirmarSolicitudAumentoPresenter B;
    private ComprobanteSolicitudAumentoPresenter C;
    /* access modifiers changed from: private */
    public OptionsToShare D;
    /* access modifiers changed from: private */
    public IsbanDialogFragment E;
    /* access modifiers changed from: private */
    public String F;
    /* access modifiers changed from: private */
    public boolean G = false;
    private Date H = null;
    private DatosCuentasBean I = null;
    private VerificaDatosSalidaOBBean J = null;
    private DatosCuentasDestBSRBean K = null;
    private DatosCuentasDestOBBean L = null;
    private String M = null;
    @InjectView(2131363999)
    Button boton_continuar;
    @InjectView(2131364001)
    Button boton_enviar;
    @InjectView(2131364015)
    Button btn_confirmar;
    @InjectView(2131364038)
    Button btn_volver;
    @InjectView(2131364338)
    ScrollView comprobanteSolicitud;
    @InjectView(2131364035)
    TextView comprobante_data_Titular;
    @InjectView(2131364027)
    TextView comprobante_data_banco_destino;
    @InjectView(2131364028)
    TextView comprobante_data_de;
    @InjectView(2131364029)
    TextView comprobante_data_fecha;
    @InjectView(2131364040)
    TextView comprobante_data_importe;
    @InjectView(2131364030)
    TextView comprobante_data_nroComprobante;
    @InjectView(2131364031)
    TextView comprobante_data_numero;
    @InjectView(2131364032)
    TextView comprobante_data_para;
    @InjectView(2131364036)
    LinearLayout comprobante_data_row_banco_destino;
    @InjectView(2131364037)
    LinearLayout comprobante_data_row_tipo_cuenta;
    @InjectView(2131364033)
    TextView comprobante_data_tipo;
    @InjectView(2131364034)
    TextView comprobante_data_tipo_cuenta;
    @InjectView(2131364012)
    TextView data_Titular;
    @InjectView(2131364005)
    TextView data_banco_destino;
    @InjectView(2131364006)
    TextView data_de;
    @InjectView(2131364007)
    TextView data_fecha;
    @InjectView(2131364016)
    TextView data_importe;
    @InjectView(2131364019)
    TextView data_leyenda;
    @InjectView(2131364043)
    TextView data_leyenda_comprobante;
    @InjectView(2131364008)
    TextView data_numero;
    @InjectView(2131364009)
    TextView data_para;
    @InjectView(2131364013)
    LinearLayout data_row_banco_destino;
    @InjectView(2131364014)
    LinearLayout data_row_tipo_cuenta;
    @InjectView(2131364010)
    TextView data_tipo;
    @InjectView(2131364011)
    TextView data_tipo_cuenta;
    @InjectView(2131364648)
    NumericEditTextWithPrefixAccesibility edit_text_mporte;
    @InjectView(2131364805)
    ImageView flecha_seleccion_importe;
    @InjectView(2131365913)
    TextView lbl_data_cuenta_origen;
    @InjectView(2131365933)
    TextView lbl_data_fecha;
    @InjectView(2131365049)
    LinearLayout linearLayoutDe;
    @InjectView(2131365034)
    LinearLayout lnlAliasSolicitud;
    @InjectView(2131365037)
    LinearLayout lnlBancoDestinoSolicitud;
    @InjectView(2131365040)
    LinearLayout lnlCBUSolicitud;
    @InjectView(2131365042)
    LinearLayout lnlCuitSolicitud;
    @InjectView(2131365069)
    LinearLayout lnlNumeroSolicitud;
    @InjectView(2131365070)
    LinearLayout lnlParaDestinatarios;
    @InjectView(2131365089)
    LinearLayout lnlTipoCuentaSolicitud;
    @InjectView(2131365090)
    LinearLayout lnlTipoSolicitud;
    @InjectView(2131365095)
    LinearLayout lnlTitularDividerSolicitud;
    @InjectView(2131365096)
    LinearLayout lnlTitularSolicitud;
    @InjectView(2131365749)
    ViewFlipper mControlPager;
    @Inject
    SessionManager p;
    @Inject
    AnalyticsManager q;
    LeyendasBean r;
    DatosCuentasBean s;
    @InjectView(2131365681)
    RelativeLayout selector_importe;
    CuentasPropiasBean t;
    @InjectView(2131366295)
    TextView texto_importe_en;
    @InjectView(2131364002)
    TextView texto_leyenda_final;
    @InjectView(2131365999)
    TextView texto_seleccionar;
    @InjectView(2131365882)
    TextView txtAliasSolicitud;
    @InjectView(2131365886)
    TextView txtBancoDestinoSolicitud;
    @InjectView(2131365892)
    TextView txtCBUSolicitud;
    @InjectView(2131365895)
    TextView txtCuitSolicitud;
    @InjectView(2131365990)
    TextView txtNumeroSolicitud;
    @InjectView(2131366005)
    TextView txtTipoCuentaSolicitud;
    @InjectView(2131366007)
    TextView txtTipoSolicitud;
    @InjectView(2131366011)
    TextView txtTitularSolicitud;
    String u = "";
    String v = "";
    String w = "";
    String x = "";
    /* access modifiers changed from: private */
    public AgendaDestinatarios y = null;
    private SolicitudAumentoPresenter z;

    public void clearScreenData() {
    }

    public void configureActionBar() {
    }

    public void configureLayout() {
    }

    public Context getContext() {
        return this;
    }

    public int getMainLayout() {
        return R.layout.solicitud_aumento_layout;
    }

    public void onPointerCaptureChanged(boolean z2) {
    }

    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        ButterKnife.inject((Activity) this);
        initialize();
    }

    public void initialize() {
        this.z = new SolicitudAumentoPresenter(this.mBus, this.mDataManager);
        this.A = new SolicitudAumentoSecondPagePresenter(this.mBus, this.mDataManager);
        this.B = new ConfirmarSolicitudAumentoPresenter(this.mBus, this.mDataManager, this, this.q);
        this.C = new ComprobanteSolicitudAumentoPresenter(this.mBus, this.mDataManager);
        this.r = (LeyendasBean) getIntent().getParcelableExtra(TransferenciasConstants.cINTENT_EXTRA_SOLICITUD_AUMENTO);
        this.s = (DatosCuentasBean) getIntent().getParcelableExtra(TransferenciasConstants.INTENT_CUENTA_SELECCIONADA);
        this.t = (CuentasPropiasBean) getIntent().getParcelableExtra(TransferenciasConstants.cINTENT_EXTRA_CUENTAS_PROPIAS);
        gotoSetSolicitudAumentoView();
    }

    public void gotoConfirmarSolicitudAumento() {
        gotoPage(2);
        this.B.onCreatePage();
    }

    public void gotoSetSolicitudAumentoView() {
        gotoPage(0);
        this.z.onCreatePage();
    }

    public void gotoSetSolicitudAumentoSecondPage() {
        gotoPage(1);
        this.A.onCreatePage();
    }

    public void gotoComprobanteSolicitud(SolicitudLimiteTransfBodyResponseBean solicitudLimiteTransfBodyResponseBean) {
        gotoPage(3);
        this.C.onCreatePage(solicitudLimiteTransfBodyResponseBean);
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
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    }
                case 2:
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    }
                case 3:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    break;
            }
            this.mControlPager.setDisplayedChild(i);
            attachView();
            hideKeyboard();
        }
    }

    public void setSolicitudAumentoView() {
        final CAccessibility instance = CAccessibility.getInstance(this);
        this.q.trackScreen(getString(R.string.analytics_screen_name_tis_f33_00));
        setActionBarSolicitudAumento();
        this.boton_continuar.setOnClickListener(this);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                WebView webView = (WebView) SolicitudAumentoActivity.this.findViewById(R.id.F33_00_WV_texto_leyenda);
                LinearLayout linearLayout = (LinearLayout) SolicitudAumentoActivity.this.findViewById(R.id.linearWebView);
                if (webView != null) {
                    for (LeyendaBean leyendaBean : SolicitudAumentoActivity.this.r.getLeyendasBean()) {
                        if (leyendaBean.getIdLeyenda().equalsIgnoreCase(leyendas.TRANSF_SOL_AL_1)) {
                            WebSettings settings = webView.getSettings();
                            settings.setDefaultFontSize(14);
                            settings.setAllowContentAccess(false);
                            settings.setAppCacheEnabled(false);
                            settings.setCacheMode(2);
                            settings.setGeolocationEnabled(false);
                            settings.setJavaScriptEnabled(false);
                            settings.setSaveFormData(false);
                            settings.setNeedInitialFocus(false);
                            settings.setSupportMultipleWindows(false);
                            settings.setSupportZoom(false);
                            settings.setUseWideViewPort(false);
                            settings.setRenderPriority(RenderPriority.HIGH);
                            StringBuilder sb = new StringBuilder();
                            sb.append("<font color='#9F9F9F'>");
                            sb.append(leyendaBean.getDescripcion());
                            sb.append("</font>");
                            webView.loadData(sb.toString(), "text/html", "utf-8");
                            webView.setContentDescription("");
                            if (linearLayout != null) {
                                try {
                                    linearLayout.setContentDescription(instance.applyFilterGeneral(Html.fromHtml(leyendaBean.getDescripcion()).toString()));
                                    return;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return;
                                }
                            } else {
                                return;
                            }
                        }
                    }
                }
            }
        }, 50);
    }

    public void setActionBarSolicitudAumento() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        b();
    }

    private void b() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SolicitudAumentoActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonSolicitudAumento() {
        setResult(0);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void setSolicitarAumentoSecondPageView() {
        this.q.trackScreen(getString(R.string.analytics_screen_name_tis_f33_01));
        setActionBarSolicitudAumentoSecondPage();
        this.boton_enviar.setOnClickListener(this);
        this.lnlParaDestinatarios.setOnClickListener(this);
        this.selector_importe.setOnClickListener(this);
        this.lbl_data_cuenta_origen.setText(this.s.getDescCtaDestino());
        Calendar.getInstance().getTime();
        this.lbl_data_fecha.setOnClickListener(this);
        this.H = calcularFechaMinima();
        setFechaSeleccionada(this.H);
        String descCtaDebito = this.s.getDescCtaDebito();
        if (this.s.getMoneda().equalsIgnoreCase(Constants.SYMBOL_CURRENCY_DOLLAR_STR)) {
            setSelectedCurrency("Dólares");
        } else {
            setSelectedCurrency("Pesos");
        }
        a(descCtaDebito);
        this.texto_leyenda_final.setText(k());
        try {
            this.texto_leyenda_final.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.texto_leyenda_final.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.edit_text_mporte.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                SolicitudAumentoActivity.this.d();
            }
        });
        c();
        d();
    }

    private void c() {
        setSelectedCurrency("Pesos");
        Iterator it = this.t.getListDatosCuentasBean().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            DatosCuentasBean datosCuentasBean = (DatosCuentasBean) it.next();
            if (datosCuentasBean.getMoneda().equalsIgnoreCase(Constants.SYMBOL_CURRENCY_PESOS_STR)) {
                a(datosCuentasBean.getDescCtaDebito());
                break;
            }
        }
        this.y = null;
        this.texto_seleccionar.setText(getString(R.string.ID446_TRANSFERENCE_BTN_SELECT));
        try {
            this.texto_seleccionar.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.texto_seleccionar.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.lnlTitularDividerSolicitud.setVisibility(8);
        this.lnlTitularSolicitud.setVisibility(8);
        setFechaSeleccionada(this.H);
    }

    public String getImporteMaximo(String str, String str2, String str3) {
        String str4;
        AgendadosBean agendaTransferencia = this.p.getAgendaTransferencia();
        String str5 = "0";
        try {
            if (str3.equals(TransferenciasConstants.cBANCO_PROPIA)) {
                if (str.equals("01") && str2.equals("0")) {
                    str4 = this.t.getImpMaxD();
                    return str4;
                } else if (str.equals("01") && str2.equals("1")) {
                    str4 = this.t.getImpMaxD();
                    return str4;
                }
            } else if (str3.equals(TransferenciasConstants.cBANCO_SR_TERCEROS)) {
                if ((str.equals("02") || str.equals("04")) && str2.equals("0")) {
                    str4 = ((AgDestBSRBean) agendaTransferencia.getListAgDestBSRBean().get(0)).getImpMaxP();
                    return str4;
                } else if ((str.equals("02") || str.equals("04")) && str2.equals("1")) {
                    str4 = ((AgDestBSRBean) agendaTransferencia.getListAgDestBSRBean().get(0)).getImpMaxD();
                    return str4;
                }
            } else if (str3.equals(TransferenciasConstants.cBANCO_OB)) {
                if ((str.equals("03") || str.equals("05")) && str2.equals("0")) {
                    str4 = ((AgDestOBBean) agendaTransferencia.getListAgDestOBBean().get(0)).getImpMaxP();
                    return str4;
                } else if ((str.equals("03") || str.equals("05")) && str2.equals("1")) {
                    str4 = ((AgDestOBBean) agendaTransferencia.getListAgDestOBBean().get(0)).getImpMaxD();
                    return str4;
                }
            }
            str4 = str5;
            return str4;
        } catch (Exception unused) {
            return str5;
        }
    }

    public int importeValido(String str, String str2, String str3) {
        try {
            Double doubleFromInputUser = CAmountIU.getInstance().getDoubleFromInputUser(this.edit_text_mporte.getText().toString());
            if (doubleFromInputUser.doubleValue() <= 0.0d) {
                return -1;
            }
            if (doubleFromInputUser.doubleValue() <= Double.valueOf(getImporteMaximo(str, str2, str3)).doubleValue()) {
                return 1;
            }
            return 0;
        } catch (Exception unused) {
            return 1;
        }
    }

    public boolean isValidData() {
        return (TextUtils.isEmpty(this.edit_text_mporte.getText()) || this.y == null || this.s == null) ? false : true;
    }

    /* access modifiers changed from: private */
    public void d() {
        if (isValidData()) {
            this.boton_enviar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
            this.boton_enviar.setEnabled(true);
            this.q.trackScreen(getString(R.string.analytics_screen_name_tis_f33_01a));
            return;
        }
        this.boton_enviar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris));
        this.boton_enviar.setEnabled(false);
    }

    public Date calcularFechaMinima() {
        Calendar instance = Calendar.getInstance();
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        instance.add(5, l().intValue());
        int i = instance.get(7);
        if (i == 7 || i == 1 || i == 2 || i == 3) {
            instance.add(5, 2);
        }
        return instance.getTime();
    }

    public void setActionBarSolicitudAumentoSecondPage() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        e();
    }

    private void e() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SolicitudAumentoActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonSolicitudAumentoSecondPage() {
        gotoPage(0, false);
    }

    /* access modifiers changed from: private */
    public DatosCuentasBean a(String str) {
        int i = 0;
        for (DatosCuentasBean datosCuentasBean : this.t.getListDatosCuentasBean()) {
            if (this.F.equalsIgnoreCase("ARS") && datosCuentasBean.getMoneda().equalsIgnoreCase(Constants.SYMBOL_CURRENCY_PESOS_STR)) {
                i++;
            } else if (this.F.equalsIgnoreCase("USD") && datosCuentasBean.getMoneda().equalsIgnoreCase(Constants.SYMBOL_CURRENCY_DOLLAR_STR)) {
                i++;
            }
            if (datosCuentasBean.getDescCtaDebito().equalsIgnoreCase(str)) {
                this.s = datosCuentasBean;
                this.lbl_data_cuenta_origen.setText(this.s.getDescCtaDestino());
                this.lbl_data_cuenta_origen.setText(formatMedioPago(str));
                try {
                    this.lbl_data_cuenta_origen.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.lbl_data_cuenta_origen.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (i > 1) {
            this.linearLayoutDe.setOnClickListener(this);
            this.lbl_data_cuenta_origen.setTextColor(getResources().getColor(R.color.generic_black));
        } else {
            this.linearLayoutDe.setOnClickListener(null);
            this.lbl_data_cuenta_origen.setTextColor(getResources().getColor(R.color.generic_black));
        }
        return this.s;
    }

    public void onClickLinearLayoutDe() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (DatosCuentasBean datosCuentasBean : this.t.getListDatosCuentasBean()) {
            if (this.F.equalsIgnoreCase("ARS") && datosCuentasBean.getMoneda().equalsIgnoreCase(Constants.SYMBOL_CURRENCY_PESOS_STR)) {
                arrayList.add(datosCuentasBean.getDescCtaDebito());
            } else if (this.F.equalsIgnoreCase("USD") && datosCuentasBean.getMoneda().equalsIgnoreCase(Constants.SYMBOL_CURRENCY_DOLLAR_STR)) {
                arrayList.add(datosCuentasBean.getDescCtaDebito());
            }
            try {
                arrayList2.add(new CAccessibility(getContext()).applyFilterGeneral(datosCuentasBean.getDescCtaDebito()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("Dialog", "Seleccionar Cuenta", null, arrayList, PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, this.s.getDescCtaDebito(), arrayList2);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                SolicitudAumentoActivity.this.a(str);
            }
        });
        newInstance.show(getSupportFragmentManager(), "Dialog");
    }

    /* access modifiers changed from: protected */
    public String formatMedioPago(String str) {
        String replaceAll = str.replaceAll("\n", UtilsCuentas.SEPARAOR2);
        String[] split = replaceAll.split("/");
        if (split.length < 2) {
            return replaceAll;
        }
        String[] split2 = split[1].split(UtilsCuentas.SEPARAOR2);
        StringBuilder sb = new StringBuilder();
        sb.append(split[0]);
        sb.append("/");
        sb.append(split2[0]);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(split2[1]);
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(split2[2]);
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb2);
        sb5.append("\n");
        sb5.append(sb4);
        return sb5.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002a  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00be  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setSelectedCurrency(java.lang.String r7) {
        /*
            r6 = this;
            int r0 = r7.hashCode()
            r1 = -1309599940(0xffffffffb1f1173c, float:-7.0166646E-9)
            r2 = 0
            r3 = 1
            if (r0 == r1) goto L_0x001b
            r1 = 77004642(0x496ff62, float:3.5499363E-36)
            if (r0 == r1) goto L_0x0011
            goto L_0x0025
        L_0x0011:
            java.lang.String r0 = "Pesos"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0025
            r0 = 1
            goto L_0x0026
        L_0x001b:
            java.lang.String r0 = "Dólares"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0025
            r0 = 0
            goto L_0x0026
        L_0x0025:
            r0 = -1
        L_0x0026:
            switch(r0) {
                case 0: goto L_0x002f;
                case 1: goto L_0x002a;
                default: goto L_0x0029;
            }
        L_0x0029:
            goto L_0x0033
        L_0x002a:
            java.lang.String r0 = "ARS"
            r6.F = r0
            goto L_0x0033
        L_0x002f:
            java.lang.String r0 = "USD"
            r6.F = r0
        L_0x0033:
            android.text.SpannableStringBuilder r0 = new android.text.SpannableStringBuilder
            r0.<init>()
            java.lang.String r1 = "%s %s"
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r5 = 2131756022(0x7f1003f6, float:1.914294E38)
            java.lang.String r5 = r6.getString(r5)
            r4[r2] = r5
            r4[r3] = r7
            java.lang.String r1 = java.lang.String.format(r1, r4)
            r0.append(r1)
            uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan r1 = new uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan
            android.content.res.AssetManager r2 = r6.getAssets()
            java.lang.String r3 = "fonts/OpenSans-Semibold.otf"
            android.graphics.Typeface r2 = uk.co.chrisjenx.calligraphy.TypefaceUtils.load(r2, r3)
            r1.<init>(r2)
            int r2 = r0.length()
            int r3 = r7.length()
            int r2 = r2 - r3
            int r3 = r0.length()
            r4 = 33
            r0.setSpan(r1, r2, r3, r4)
            android.text.style.ForegroundColorSpan r1 = new android.text.style.ForegroundColorSpan
            android.content.res.Resources r2 = r6.getResources()
            r3 = 2131099749(0x7f060065, float:1.781186E38)
            int r2 = r2.getColor(r3)
            r1.<init>(r2)
            int r2 = r0.length()
            int r3 = r7.length()
            int r2 = r2 - r3
            int r3 = r0.length()
            r0.setSpan(r1, r2, r3, r4)
            android.widget.TextView r1 = r6.texto_importe_en
            android.widget.TextView$BufferType r2 = android.widget.TextView.BufferType.SPANNABLE
            r1.setText(r0, r2)
            android.widget.TextView r0 = r6.texto_importe_en
            android.widget.TextView r1 = r6.texto_importe_en
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskSelector(r1)
            r0.setContentDescription(r1)
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r0 = r6.edit_text_mporte
            java.lang.String r1 = ""
            r0.setText(r1)
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r0 = r6.edit_text_mporte
            java.lang.String r1 = "Dólares"
            boolean r1 = r1.equals(r7)
            if (r1 == 0) goto L_0x00be
            java.lang.String r7 = ar.com.santander.rio.mbanking.app.ui.Constants.SYMBOL_CURRENCY_DOLAR
            goto L_0x00cb
        L_0x00be:
            java.lang.String r1 = "Pesos"
            boolean r7 = r1.equals(r7)
            if (r7 == 0) goto L_0x00c9
            java.lang.String r7 = ar.com.santander.rio.mbanking.app.ui.Constants.SYMBOL_CURRENCY_PESOS
            goto L_0x00cb
        L_0x00c9:
            java.lang.String r7 = ""
        L_0x00cb:
            r0.setPrefix(r7)
            android.widget.LinearLayout r7 = r6.linearLayoutDe
            r0 = 0
            r7.setOnClickListener(r0)
            android.widget.TextView r7 = r6.lbl_data_cuenta_origen
            r1 = 2131756136(0x7f100468, float:1.914317E38)
            java.lang.String r1 = r6.getString(r1)
            r7.setText(r1)
            android.widget.TextView r7 = r6.lbl_data_cuenta_origen     // Catch:{ Exception -> 0x00fc }
            android.content.Context r1 = r6.getApplicationContext()     // Catch:{ Exception -> 0x00fc }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x00fc }
            android.widget.TextView r2 = r6.lbl_data_cuenta_origen     // Catch:{ Exception -> 0x00fc }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x00fc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00fc }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x00fc }
            r7.setContentDescription(r1)     // Catch:{ Exception -> 0x00fc }
            goto L_0x0100
        L_0x00fc:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0100:
            android.widget.TextView r7 = r6.lbl_data_cuenta_origen
            android.content.res.Resources r1 = r6.getResources()
            r2 = 2131099746(0x7f060062, float:1.7811854E38)
            int r1 = r1.getColor(r2)
            r7.setTextColor(r1)
            r6.s = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.SolicitudAumentoActivity.setSelectedCurrency(java.lang.String):void");
    }

    public void onSelectCurrency() {
        String str = this.F.equals("USD") ? "Dólares" : this.F.equals("ARS") ? "Pesos" : "";
        String str2 = str;
        ArrayList arrayList = new ArrayList();
        arrayList.add("Pesos");
        arrayList.add("Dólares");
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(Dialogs.currencySelector, getString(R.string.ID457_TRANSFERENCE_MAIN_PAYMENT_SELECTOR), null, arrayList, getString(R.string.IDX_ALERT_BTN_CANCEL), null, null, str2);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase("Pesos") && SolicitudAumentoActivity.this.F.equalsIgnoreCase("USD")) {
                    SolicitudAumentoActivity.this.setSelectedCurrency("Pesos");
                    Iterator it = SolicitudAumentoActivity.this.t.getListDatosCuentasBean().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        DatosCuentasBean datosCuentasBean = (DatosCuentasBean) it.next();
                        if (datosCuentasBean.getMoneda().equalsIgnoreCase(Constants.SYMBOL_CURRENCY_PESOS_STR)) {
                            SolicitudAumentoActivity.this.a(datosCuentasBean.getDescCtaDebito());
                            break;
                        }
                    }
                    SolicitudAumentoActivity.this.F = "ARS";
                    SolicitudAumentoActivity.this.y = null;
                    SolicitudAumentoActivity.this.texto_seleccionar.setText(SolicitudAumentoActivity.this.getString(R.string.ID446_TRANSFERENCE_BTN_SELECT));
                    try {
                        SolicitudAumentoActivity.this.texto_seleccionar.setContentDescription(CAccessibility.getInstance(SolicitudAumentoActivity.this.getApplicationContext()).applyFilterGeneral(SolicitudAumentoActivity.this.texto_seleccionar.getText().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SolicitudAumentoActivity.this.lnlTitularDividerSolicitud.setVisibility(8);
                    SolicitudAumentoActivity.this.lnlTitularSolicitud.setVisibility(8);
                } else if (str.equalsIgnoreCase("Dólares") && SolicitudAumentoActivity.this.F.equalsIgnoreCase("ARS")) {
                    SolicitudAumentoActivity.this.setSelectedCurrency("Dólares");
                    Iterator it2 = SolicitudAumentoActivity.this.t.getListDatosCuentasBean().iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        DatosCuentasBean datosCuentasBean2 = (DatosCuentasBean) it2.next();
                        if (datosCuentasBean2.getMoneda().equalsIgnoreCase(Constants.SYMBOL_CURRENCY_DOLLAR_STR)) {
                            SolicitudAumentoActivity.this.a(datosCuentasBean2.getDescCtaDebito());
                            break;
                        }
                    }
                    SolicitudAumentoActivity.this.F = "USD";
                    SolicitudAumentoActivity.this.y = null;
                    SolicitudAumentoActivity.this.texto_seleccionar.setText(SolicitudAumentoActivity.this.getString(R.string.ID446_TRANSFERENCE_BTN_SELECT));
                    try {
                        SolicitudAumentoActivity.this.texto_seleccionar.setContentDescription(CAccessibility.getInstance(SolicitudAumentoActivity.this.getApplicationContext()).applyFilterGeneral(SolicitudAumentoActivity.this.texto_seleccionar.getText().toString()));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    SolicitudAumentoActivity.this.lnlTitularDividerSolicitud.setVisibility(8);
                    SolicitudAumentoActivity.this.lnlTitularSolicitud.setVisibility(8);
                }
                SolicitudAumentoActivity.this.d();
            }
        });
        newInstance.setCancelable(true);
        newInstance.show(getSupportFragmentManager(), TenenciaCreditosFragment.DIALOG_SELECTOR);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(26:104|(1:106)(1:107)|108|(1:110)(1:111)|112|(1:114)(1:115)|116|(2:117|118)|121|122|123|126|(3:130|131|132)|135|(2:136|137)|140|141|142|145|(2:146|147)|150|151|(2:152|153)|157|158|160) */
    /* JADX WARNING: Code restructure failed: missing block: B:161:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:157:0x0596 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mostrarDatosDestinatarioSeleccionadoSolicitud() {
        /*
            r6 = this;
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r6.y
            java.lang.String r0 = r0.getInfo1()
            java.lang.String r1 = "Tipo de Cuenta: Propia"
            boolean r0 = r0.equalsIgnoreCase(r1)
            if (r0 == 0) goto L_0x0013
            java.lang.String r0 = "Propia"
            r6.M = r0
            goto L_0x002a
        L_0x0013:
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r6.y
            java.lang.String r0 = r0.getInfo1()
            java.lang.String r1 = "Terceros Santander"
            boolean r0 = r0.equalsIgnoreCase(r1)
            if (r0 == 0) goto L_0x0026
            java.lang.String r0 = "Terceros Santander"
            r6.M = r0
            goto L_0x002a
        L_0x0026:
            java.lang.String r0 = "Otros Bancos"
            r6.M = r0
        L_0x002a:
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r6.y
            java.lang.String r0 = r0.getInfo2()
            android.widget.LinearLayout r1 = r6.lnlTitularDividerSolicitud
            r2 = 0
            r1.setVisibility(r2)
            android.widget.LinearLayout r1 = r6.lnlTitularSolicitud
            r1.setVisibility(r2)
            java.lang.String r1 = r6.M
            java.lang.String r3 = "Propia"
            boolean r1 = r1.equals(r3)
            r3 = 8
            if (r1 == 0) goto L_0x016f
            android.widget.LinearLayout r1 = r6.lnlTipoCuentaSolicitud
            r1.setVisibility(r2)
            android.widget.LinearLayout r1 = r6.lnlTipoSolicitud
            r1.setVisibility(r3)
            android.widget.LinearLayout r1 = r6.lnlNumeroSolicitud
            r1.setVisibility(r3)
            android.widget.TextView r1 = r6.txtTitularSolicitud
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            ar.com.santander.rio.mbanking.managers.session.SessionManager r5 = r6.p
            ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyResponseBean r5 = r5.getLoginUnico()
            ar.com.santander.rio.mbanking.services.model.general.DatosPersonales r5 = r5.getDatosPersonales()
            java.lang.String r5 = r5.getNombre()
            r4.append(r5)
            java.lang.String r5 = " "
            r4.append(r5)
            ar.com.santander.rio.mbanking.managers.session.SessionManager r5 = r6.p
            ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyResponseBean r5 = r5.getLoginUnico()
            ar.com.santander.rio.mbanking.services.model.general.DatosPersonales r5 = r5.getDatosPersonales()
            java.lang.String r5 = r5.getApellido()
            java.lang.String r5 = r5.toUpperCase()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            android.text.Spanned r4 = android.text.Html.fromHtml(r4)
            r1.setText(r4)
            android.widget.TextView r1 = r6.txtTitularSolicitud     // Catch:{ Exception -> 0x00af }
            android.content.Context r4 = r6.getApplicationContext()     // Catch:{ Exception -> 0x00af }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r4 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r4)     // Catch:{ Exception -> 0x00af }
            android.widget.TextView r5 = r6.txtTitularSolicitud     // Catch:{ Exception -> 0x00af }
            java.lang.CharSequence r5 = r5.getText()     // Catch:{ Exception -> 0x00af }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x00af }
            java.lang.String r4 = r4.applyFilterGeneral(r5)     // Catch:{ Exception -> 0x00af }
            r1.setContentDescription(r4)     // Catch:{ Exception -> 0x00af }
            goto L_0x00b3
        L_0x00af:
            r1 = move-exception
            r1.printStackTrace()
        L_0x00b3:
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r1 = r6.y
            java.lang.String r1 = r1.getAlias()
            if (r1 == 0) goto L_0x00f8
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r1 = r6.y
            java.lang.String r1 = r1.getAlias()
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x00f8
            android.widget.TextView r1 = r6.txtAliasSolicitud
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r4 = r6.y
            java.lang.String r4 = r4.getAlias()
            r1.setText(r4)
            android.widget.TextView r1 = r6.txtAliasSolicitud     // Catch:{ Exception -> 0x00ee }
            android.content.Context r4 = r6.getApplicationContext()     // Catch:{ Exception -> 0x00ee }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r4 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r4)     // Catch:{ Exception -> 0x00ee }
            android.widget.TextView r5 = r6.txtAliasSolicitud     // Catch:{ Exception -> 0x00ee }
            java.lang.CharSequence r5 = r5.getText()     // Catch:{ Exception -> 0x00ee }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x00ee }
            java.lang.String r4 = r4.applyFilterGeneral(r5)     // Catch:{ Exception -> 0x00ee }
            r1.setContentDescription(r4)     // Catch:{ Exception -> 0x00ee }
            goto L_0x00f2
        L_0x00ee:
            r1 = move-exception
            r1.printStackTrace()
        L_0x00f2:
            android.widget.LinearLayout r1 = r6.lnlAliasSolicitud
            r1.setVisibility(r2)
            goto L_0x0104
        L_0x00f8:
            android.widget.TextView r1 = r6.txtAliasSolicitud
            java.lang.String r2 = ""
            r1.setText(r2)
            android.widget.LinearLayout r1 = r6.lnlAliasSolicitud
            r1.setVisibility(r3)
        L_0x0104:
            android.widget.TextView r1 = r6.txtTipoCuentaSolicitud
            java.lang.String r2 = r6.M
            android.text.Spanned r2 = android.text.Html.fromHtml(r2)
            r1.setText(r2)
            android.widget.TextView r1 = r6.txtTipoCuentaSolicitud     // Catch:{ Exception -> 0x012b }
            android.content.Context r2 = r6.getApplicationContext()     // Catch:{ Exception -> 0x012b }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r2 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r2)     // Catch:{ Exception -> 0x012b }
            android.widget.TextView r4 = r6.txtTipoCuentaSolicitud     // Catch:{ Exception -> 0x012b }
            java.lang.CharSequence r4 = r4.getText()     // Catch:{ Exception -> 0x012b }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x012b }
            java.lang.String r2 = r2.applyFilterGeneral(r4)     // Catch:{ Exception -> 0x012b }
            r1.setContentDescription(r2)     // Catch:{ Exception -> 0x012b }
            goto L_0x012f
        L_0x012b:
            r1 = move-exception
            r1.printStackTrace()
        L_0x012f:
            android.widget.TextView r1 = r6.texto_seleccionar
            android.text.Spanned r2 = android.text.Html.fromHtml(r0)
            r1.setText(r2)
            android.widget.TextView r1 = r6.texto_seleccionar     // Catch:{ Exception -> 0x0154 }
            android.content.Context r2 = r6.getApplicationContext()     // Catch:{ Exception -> 0x0154 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r2 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r2)     // Catch:{ Exception -> 0x0154 }
            android.widget.TextView r4 = r6.texto_seleccionar     // Catch:{ Exception -> 0x0154 }
            java.lang.CharSequence r4 = r4.getText()     // Catch:{ Exception -> 0x0154 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0154 }
            java.lang.String r2 = r2.applyFilterGeneral(r4)     // Catch:{ Exception -> 0x0154 }
            r1.setContentDescription(r2)     // Catch:{ Exception -> 0x0154 }
            goto L_0x0158
        L_0x0154:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0158:
            android.widget.LinearLayout r1 = r6.lnlCuitSolicitud
            r1.setVisibility(r3)
            android.widget.LinearLayout r1 = r6.lnlBancoDestinoSolicitud
            r1.setVisibility(r3)
            android.widget.LinearLayout r1 = r6.lnlCBUSolicitud
            r1.setVisibility(r3)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r0 = r6.getDatosCuentasBeanPara(r0)
            r6.I = r0
            goto L_0x05b9
        L_0x016f:
            java.lang.String r0 = r6.M
            java.lang.String r1 = "Terceros Santander"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x03da
            android.widget.LinearLayout r0 = r6.lnlTipoSolicitud
            r0.setVisibility(r2)
            android.widget.LinearLayout r0 = r6.lnlNumeroSolicitud
            r0.setVisibility(r2)
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r6.y
            java.lang.String r0 = r0.getAlias()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x01cd
            android.widget.LinearLayout r0 = r6.lnlAliasSolicitud
            r0.setVisibility(r2)
            android.widget.LinearLayout r0 = r6.lnlTipoCuentaSolicitud
            r0.setVisibility(r3)
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r6.J
            java.lang.String r0 = r0.getBancoDestino()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x01ab
            android.widget.LinearLayout r0 = r6.lnlBancoDestinoSolicitud
            r0.setVisibility(r3)
            goto L_0x01b0
        L_0x01ab:
            android.widget.LinearLayout r0 = r6.lnlBancoDestinoSolicitud
            r0.setVisibility(r2)
        L_0x01b0:
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r6.J
            java.lang.String r0 = r0.getCbu()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x01c2
            android.widget.LinearLayout r0 = r6.lnlCBUSolicitud
            r0.setVisibility(r3)
            goto L_0x01c7
        L_0x01c2:
            android.widget.LinearLayout r0 = r6.lnlCBUSolicitud
            r0.setVisibility(r2)
        L_0x01c7:
            android.widget.LinearLayout r0 = r6.lnlCuitSolicitud
            r0.setVisibility(r2)
            goto L_0x01e6
        L_0x01cd:
            android.widget.LinearLayout r0 = r6.lnlAliasSolicitud
            r0.setVisibility(r3)
            android.widget.LinearLayout r0 = r6.lnlTipoCuentaSolicitud
            r0.setVisibility(r2)
            android.widget.LinearLayout r0 = r6.lnlBancoDestinoSolicitud
            r0.setVisibility(r3)
            android.widget.LinearLayout r0 = r6.lnlCBUSolicitud
            r0.setVisibility(r3)
            android.widget.LinearLayout r0 = r6.lnlCuitSolicitud
            r0.setVisibility(r3)
        L_0x01e6:
            android.widget.TextView r0 = r6.texto_seleccionar
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r1 = r6.K
            java.lang.String r1 = r1.getDescripcion()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r6.texto_seleccionar     // Catch:{ Exception -> 0x0211 }
            android.content.Context r1 = r6.getApplicationContext()     // Catch:{ Exception -> 0x0211 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x0211 }
            android.widget.TextView r3 = r6.texto_seleccionar     // Catch:{ Exception -> 0x0211 }
            java.lang.CharSequence r3 = r3.getText()     // Catch:{ Exception -> 0x0211 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0211 }
            java.lang.String r1 = r1.applyFilterGeneral(r3)     // Catch:{ Exception -> 0x0211 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0211 }
            goto L_0x0215
        L_0x0211:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0215:
            android.widget.TextView r0 = r6.txtTitularSolicitud
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r6.J
            java.lang.String r1 = r1.getTitular()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r6.txtTitularSolicitud     // Catch:{ Exception -> 0x0240 }
            android.content.Context r1 = r6.getApplicationContext()     // Catch:{ Exception -> 0x0240 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x0240 }
            android.widget.TextView r3 = r6.txtTitularSolicitud     // Catch:{ Exception -> 0x0240 }
            java.lang.CharSequence r3 = r3.getText()     // Catch:{ Exception -> 0x0240 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0240 }
            java.lang.String r1 = r1.applyFilterGeneral(r3)     // Catch:{ Exception -> 0x0240 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0240 }
            goto L_0x0244
        L_0x0240:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0244:
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r6.y
            java.lang.String r0 = r0.getAlias()
            if (r0 == 0) goto L_0x0283
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r6.y
            java.lang.String r0 = r0.getAlias()
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0283
            android.widget.TextView r0 = r6.txtAliasSolicitud
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r1 = r6.y
            java.lang.String r1 = r1.getAlias()
            r0.setText(r1)
            android.widget.TextView r0 = r6.txtAliasSolicitud     // Catch:{ Exception -> 0x027f }
            android.content.Context r1 = r6.getApplicationContext()     // Catch:{ Exception -> 0x027f }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x027f }
            android.widget.TextView r3 = r6.txtAliasSolicitud     // Catch:{ Exception -> 0x027f }
            java.lang.CharSequence r3 = r3.getText()     // Catch:{ Exception -> 0x027f }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x027f }
            java.lang.String r1 = r1.applyFilterGeneral(r3)     // Catch:{ Exception -> 0x027f }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x027f }
            goto L_0x0283
        L_0x027f:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0283:
            android.widget.TextView r0 = r6.txtTipoCuentaSolicitud
            java.lang.String r1 = r6.M
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r6.txtTipoCuentaSolicitud     // Catch:{ Exception -> 0x02aa }
            android.content.Context r1 = r6.getApplicationContext()     // Catch:{ Exception -> 0x02aa }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x02aa }
            android.widget.TextView r3 = r6.txtTipoCuentaSolicitud     // Catch:{ Exception -> 0x02aa }
            java.lang.CharSequence r3 = r3.getText()     // Catch:{ Exception -> 0x02aa }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x02aa }
            java.lang.String r1 = r1.applyFilterGeneral(r3)     // Catch:{ Exception -> 0x02aa }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x02aa }
            goto L_0x02ae
        L_0x02aa:
            r0 = move-exception
            r0.printStackTrace()
        L_0x02ae:
            android.widget.TextView r0 = r6.txtTipoSolicitud
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r1 = r6.K
            java.lang.String r1 = r1.getTipoDescripcion()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r6.txtTipoSolicitud     // Catch:{ Exception -> 0x02d9 }
            android.content.Context r1 = r6.getApplicationContext()     // Catch:{ Exception -> 0x02d9 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x02d9 }
            android.widget.TextView r3 = r6.txtTipoSolicitud     // Catch:{ Exception -> 0x02d9 }
            java.lang.CharSequence r3 = r3.getText()     // Catch:{ Exception -> 0x02d9 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x02d9 }
            java.lang.String r1 = r1.applyFilterGeneral(r3)     // Catch:{ Exception -> 0x02d9 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x02d9 }
            goto L_0x02dd
        L_0x02d9:
            r0 = move-exception
            r0.printStackTrace()
        L_0x02dd:
            android.widget.TextView r0 = r6.txtNumeroSolicitud
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r3 = r6.K
            java.lang.String r3 = r3.getSucursal()
            r1.append(r3)
            java.lang.String r3 = "-"
            r1.append(r3)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r3 = r6.K
            java.lang.String r3 = r3.getNumero()
            r4 = 6
            java.lang.String r2 = r3.substring(r2, r4)
            r1.append(r2)
            java.lang.String r2 = "/"
            r1.append(r2)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r2 = r6.K
            java.lang.String r2 = r2.getNumero()
            r3 = 7
            java.lang.String r2 = r2.substring(r4, r3)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.setText(r1)
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x0332 }
            r0.<init>(r6)     // Catch:{ Exception -> 0x0332 }
            android.widget.TextView r1 = r6.txtNumeroSolicitud     // Catch:{ Exception -> 0x0332 }
            android.widget.TextView r2 = r6.txtNumeroSolicitud     // Catch:{ Exception -> 0x0332 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0332 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0332 }
            java.lang.String r0 = r0.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x0332 }
            r1.setContentDescription(r0)     // Catch:{ Exception -> 0x0332 }
        L_0x0332:
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r6.J
            java.lang.String r0 = r0.getCbu()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x036d
            android.widget.TextView r0 = r6.txtCBUSolicitud
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r6.J
            java.lang.String r1 = r1.getCbu()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r6.txtCBUSolicitud     // Catch:{ Exception -> 0x0369 }
            android.content.Context r1 = r6.getApplicationContext()     // Catch:{ Exception -> 0x0369 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x0369 }
            android.widget.TextView r2 = r6.txtCBUSolicitud     // Catch:{ Exception -> 0x0369 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0369 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0369 }
            java.lang.String r1 = r1.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x0369 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0369 }
            goto L_0x036d
        L_0x0369:
            r0 = move-exception
            r0.printStackTrace()
        L_0x036d:
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r6.J
            java.lang.String r0 = r0.getBancoDestino()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x03a2
            android.widget.TextView r1 = r6.txtBancoDestinoSolicitud
            android.text.Spanned r0 = android.text.Html.fromHtml(r0)
            r1.setText(r0)
            android.widget.TextView r0 = r6.txtBancoDestinoSolicitud     // Catch:{ Exception -> 0x039e }
            android.content.Context r1 = r6.getApplicationContext()     // Catch:{ Exception -> 0x039e }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x039e }
            android.widget.TextView r2 = r6.txtBancoDestinoSolicitud     // Catch:{ Exception -> 0x039e }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x039e }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x039e }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x039e }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x039e }
            goto L_0x03a2
        L_0x039e:
            r0 = move-exception
            r0.printStackTrace()
        L_0x03a2:
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r6.J
            java.lang.String r0 = r0.getNumeroCuil()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x05b9
            android.widget.TextView r1 = r6.txtCuitSolicitud
            android.text.Spanned r0 = android.text.Html.fromHtml(r0)
            r1.setText(r0)
            android.widget.TextView r0 = r6.txtCuitSolicitud     // Catch:{ Exception -> 0x03d4 }
            android.content.Context r1 = r6.getApplicationContext()     // Catch:{ Exception -> 0x03d4 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x03d4 }
            android.widget.TextView r2 = r6.txtCuitSolicitud     // Catch:{ Exception -> 0x03d4 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x03d4 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03d4 }
            java.lang.String r1 = r1.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x03d4 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03d4 }
            goto L_0x05b9
        L_0x03d4:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x05b9
        L_0x03da:
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r6.y
            java.lang.String r0 = r0.getAlias()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x03f6
            android.widget.LinearLayout r0 = r6.lnlAliasSolicitud
            r0.setVisibility(r2)
            android.widget.LinearLayout r0 = r6.lnlTipoSolicitud
            r0.setVisibility(r2)
            android.widget.LinearLayout r0 = r6.lnlNumeroSolicitud
            r0.setVisibility(r2)
            goto L_0x0405
        L_0x03f6:
            android.widget.LinearLayout r0 = r6.lnlAliasSolicitud
            r0.setVisibility(r3)
            android.widget.LinearLayout r0 = r6.lnlTipoSolicitud
            r0.setVisibility(r3)
            android.widget.LinearLayout r0 = r6.lnlNumeroSolicitud
            r0.setVisibility(r3)
        L_0x0405:
            android.widget.LinearLayout r0 = r6.lnlTipoCuentaSolicitud
            r0.setVisibility(r3)
            android.widget.LinearLayout r0 = r6.lnlCuitSolicitud
            r0.setVisibility(r2)
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r6.J
            java.lang.String r0 = r0.getBancoDestino()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x0421
            android.widget.LinearLayout r0 = r6.lnlBancoDestinoSolicitud
            r0.setVisibility(r2)
            goto L_0x0426
        L_0x0421:
            android.widget.LinearLayout r0 = r6.lnlBancoDestinoSolicitud
            r0.setVisibility(r2)
        L_0x0426:
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r6.J
            java.lang.String r0 = r0.getCbu()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x0438
            android.widget.LinearLayout r0 = r6.lnlCBUSolicitud
            r0.setVisibility(r3)
            goto L_0x043d
        L_0x0438:
            android.widget.LinearLayout r0 = r6.lnlCBUSolicitud
            r0.setVisibility(r2)
        L_0x043d:
            android.widget.TextView r0 = r6.texto_seleccionar
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r1 = r6.L
            java.lang.String r1 = r1.getDescripcion()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r6.texto_seleccionar     // Catch:{ Exception -> 0x0468 }
            android.content.Context r1 = r6.getApplicationContext()     // Catch:{ Exception -> 0x0468 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x0468 }
            android.widget.TextView r2 = r6.texto_seleccionar     // Catch:{ Exception -> 0x0468 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0468 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0468 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x0468 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0468 }
            goto L_0x046c
        L_0x0468:
            r0 = move-exception
            r0.printStackTrace()
        L_0x046c:
            android.widget.TextView r0 = r6.txtTitularSolicitud
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r6.J
            java.lang.String r1 = r1.getTitular()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r6.txtTitularSolicitud     // Catch:{ Exception -> 0x0497 }
            android.content.Context r1 = r6.getApplicationContext()     // Catch:{ Exception -> 0x0497 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x0497 }
            android.widget.TextView r2 = r6.txtTitularSolicitud     // Catch:{ Exception -> 0x0497 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0497 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0497 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x0497 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0497 }
            goto L_0x049b
        L_0x0497:
            r0 = move-exception
            r0.printStackTrace()
        L_0x049b:
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r6.y
            java.lang.String r0 = r0.getAlias()
            if (r0 == 0) goto L_0x04da
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r6.y
            java.lang.String r0 = r0.getAlias()
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x04da
            android.widget.TextView r0 = r6.txtAliasSolicitud
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r1 = r6.y
            java.lang.String r1 = r1.getAlias()
            r0.setText(r1)
            android.widget.TextView r0 = r6.txtAliasSolicitud     // Catch:{ Exception -> 0x04d6 }
            android.content.Context r1 = r6.getApplicationContext()     // Catch:{ Exception -> 0x04d6 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x04d6 }
            android.widget.TextView r2 = r6.txtAliasSolicitud     // Catch:{ Exception -> 0x04d6 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x04d6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x04d6 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x04d6 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x04d6 }
            goto L_0x04da
        L_0x04d6:
            r0 = move-exception
            r0.printStackTrace()
        L_0x04da:
            android.widget.TextView r0 = r6.txtCuitSolicitud
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r6.J
            java.lang.String r1 = r1.getNumeroCuil()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r6.txtCuitSolicitud     // Catch:{ Exception -> 0x0505 }
            android.content.Context r1 = r6.getApplicationContext()     // Catch:{ Exception -> 0x0505 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x0505 }
            android.widget.TextView r2 = r6.txtCuitSolicitud     // Catch:{ Exception -> 0x0505 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0505 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0505 }
            java.lang.String r1 = r1.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x0505 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0505 }
            goto L_0x0509
        L_0x0505:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0509:
            android.widget.TextView r0 = r6.txtBancoDestinoSolicitud
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r6.J
            java.lang.String r1 = r1.getBancoDestino()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r6.txtBancoDestinoSolicitud     // Catch:{ Exception -> 0x0534 }
            android.content.Context r1 = r6.getApplicationContext()     // Catch:{ Exception -> 0x0534 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x0534 }
            android.widget.TextView r2 = r6.txtBancoDestinoSolicitud     // Catch:{ Exception -> 0x0534 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0534 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0534 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x0534 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0534 }
            goto L_0x0538
        L_0x0534:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0538:
            android.widget.TextView r0 = r6.txtCBUSolicitud
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r1 = r6.L
            java.lang.String r1 = r1.getCbu()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r6.txtCBUSolicitud     // Catch:{ Exception -> 0x0563 }
            android.content.Context r1 = r6.getApplicationContext()     // Catch:{ Exception -> 0x0563 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x0563 }
            android.widget.TextView r2 = r6.txtCBUSolicitud     // Catch:{ Exception -> 0x0563 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0563 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0563 }
            java.lang.String r1 = r1.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x0563 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0563 }
            goto L_0x0567
        L_0x0563:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0567:
            android.widget.TextView r0 = r6.txtTipoSolicitud     // Catch:{ Exception -> 0x0596 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r6.J     // Catch:{ Exception -> 0x0596 }
            java.lang.String r1 = r1.getTipoCtaToBane()     // Catch:{ Exception -> 0x0596 }
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilAccount.getOBAccountTypeDescription(r6, r1)     // Catch:{ Exception -> 0x0596 }
            r0.setText(r1)     // Catch:{ Exception -> 0x0596 }
            android.widget.TextView r0 = r6.txtTipoSolicitud     // Catch:{ Exception -> 0x0592 }
            android.content.Context r1 = r6.getApplicationContext()     // Catch:{ Exception -> 0x0592 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x0592 }
            android.widget.TextView r2 = r6.txtTipoSolicitud     // Catch:{ Exception -> 0x0592 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0592 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0592 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x0592 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0596
        L_0x0592:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x0596 }
        L_0x0596:
            android.widget.TextView r0 = r6.txtNumeroSolicitud     // Catch:{ Exception -> 0x05b9 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r6.J     // Catch:{ Exception -> 0x05b9 }
            java.lang.String r1 = r1.getNumero()     // Catch:{ Exception -> 0x05b9 }
            r0.setText(r1)     // Catch:{ Exception -> 0x05b9 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x05b9 }
            r0.<init>(r6)     // Catch:{ Exception -> 0x05b9 }
            android.widget.TextView r1 = r6.txtNumeroSolicitud     // Catch:{ Exception -> 0x05b9 }
            android.widget.TextView r2 = r6.txtNumeroSolicitud     // Catch:{ Exception -> 0x05b9 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x05b9 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05b9 }
            java.lang.String r0 = r0.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x05b9 }
            r1.setContentDescription(r0)     // Catch:{ Exception -> 0x05b9 }
        L_0x05b9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.SolicitudAumentoActivity.mostrarDatosDestinatarioSeleccionadoSolicitud():void");
    }

    public void setConfirmarsolicitudAumentoView() {
        this.q.trackScreen(getString(R.string.analytics_screen_name_tis_f33_02));
        setActionBarConfirmarSolicitudAumento();
        this.btn_confirmar.setOnClickListener(this);
        this.data_leyenda.setText(k());
        if (this.edit_text_mporte.getText().toString().substring(this.edit_text_mporte.getText().toString().length() - 1).equalsIgnoreCase(",")) {
            this.edit_text_mporte.setText(this.edit_text_mporte.getText().toString().substring(0, this.edit_text_mporte.getText().toString().length() - 1));
        }
        if (this.F.equalsIgnoreCase(Constants.SYMBOL_CURRENCY_PESOS_STR)) {
            if (this.edit_text_mporte.getFormatedText().toString().replaceAll("\\s+", "").length() >= 11) {
                this.data_importe.setTextSize(33.0f);
            } else {
                this.data_importe.setTextSize(42.0f);
            }
            TextView textView = this.data_importe;
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.SYMBOL_CURRENCY_PESOS);
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(this.edit_text_mporte.getFormatedText().toString());
            textView.setText(sb.toString());
        } else {
            if (this.edit_text_mporte.getFormatedText().toString().replaceAll("\\s+", "").length() >= 11) {
                this.data_importe.setTextSize(30.0f);
            } else {
                this.data_importe.setTextSize(42.0f);
            }
            TextView textView2 = this.data_importe;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Constants.SYMBOL_CURRENCY_DOLAR);
            sb2.append(UtilsCuentas.SEPARAOR2);
            sb2.append(this.edit_text_mporte.getFormatedText().toString());
            textView2.setText(sb2.toString());
        }
        this.v = this.edit_text_mporte.getText().toString().replace(".", "");
        this.data_fecha.setText(this.lbl_data_fecha.getText());
        this.data_de.setText(this.s.getDescCtaDestino());
        this.u = this.data_fecha.getText().toString();
        mostrarDatosDestinatarioSeleccionadoConfirmacion();
        try {
            this.data_importe.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.data_importe.getText().toString()));
            this.data_de.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.data_de.getText().toString()));
            this.data_fecha.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.data_fecha.getText().toString()));
            this.data_leyenda.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.data_leyenda.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String f() {
        if (this.F.equalsIgnoreCase(Constants.SYMBOL_CURRENCY_PESOS_STR)) {
            this.w = "0";
        } else {
            this.w = "1";
        }
        return this.w;
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:50:0x026f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mostrarDatosDestinatarioSeleccionadoConfirmacion() {
        /*
            r7 = this;
            android.widget.TextView r0 = r7.data_para
            android.widget.TextView r1 = r7.texto_seleccionar
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r7.data_Titular
            android.widget.TextView r1 = r7.txtTitularSolicitud
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            java.lang.String r0 = r7.M
            java.lang.String r1 = "Propia"
            boolean r0 = r0.equals(r1)
            r1 = 7
            r2 = 8
            r3 = 6
            r4 = 0
            if (r0 == 0) goto L_0x00f3
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r7.y
            java.lang.String r0 = r0.getInfo2()
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r0 = r7.getDatosCuentasBeanPara(r0)
            r7.I = r0
            android.widget.TextView r0 = r7.data_tipo_cuenta
            java.lang.String r5 = "Propia"
            r0.setText(r5)
            android.widget.TextView r0 = r7.data_tipo_cuenta     // Catch:{ Exception -> 0x0054 }
            android.content.Context r5 = r7.getApplicationContext()     // Catch:{ Exception -> 0x0054 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r5 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r5)     // Catch:{ Exception -> 0x0054 }
            android.widget.TextView r6 = r7.data_tipo_cuenta     // Catch:{ Exception -> 0x0054 }
            java.lang.CharSequence r6 = r6.getText()     // Catch:{ Exception -> 0x0054 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0054 }
            java.lang.String r5 = r5.applyFilterGeneral(r6)     // Catch:{ Exception -> 0x0054 }
            r0.setContentDescription(r5)     // Catch:{ Exception -> 0x0054 }
            goto L_0x0058
        L_0x0054:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0058:
            android.widget.LinearLayout r0 = r7.data_row_tipo_cuenta
            r0.setVisibility(r4)
            android.widget.TextView r0 = r7.data_banco_destino
            java.lang.String r5 = ""
            r0.setText(r5)
            android.widget.LinearLayout r0 = r7.data_row_banco_destino
            r0.setVisibility(r2)
            android.widget.TextView r0 = r7.data_tipo
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r2 = r7.I
            java.lang.String r2 = r2.getTipoDescripcion()
            android.text.Spanned r2 = android.text.Html.fromHtml(r2)
            r0.setText(r2)
            android.widget.TextView r0 = r7.data_tipo     // Catch:{ Exception -> 0x0094 }
            android.content.Context r2 = r7.getApplicationContext()     // Catch:{ Exception -> 0x0094 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r2 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r2)     // Catch:{ Exception -> 0x0094 }
            android.widget.TextView r5 = r7.data_tipo     // Catch:{ Exception -> 0x0094 }
            java.lang.CharSequence r5 = r5.getText()     // Catch:{ Exception -> 0x0094 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0094 }
            java.lang.String r2 = r2.applyFilterGeneral(r5)     // Catch:{ Exception -> 0x0094 }
            r0.setContentDescription(r2)     // Catch:{ Exception -> 0x0094 }
            goto L_0x0098
        L_0x0094:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0098:
            android.widget.TextView r0 = r7.data_numero
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r5 = r7.I
            java.lang.String r5 = r5.getSucursal()
            r2.append(r5)
            java.lang.String r5 = "-"
            r2.append(r5)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r5 = r7.I
            java.lang.String r5 = r5.getNumero()
            java.lang.String r4 = r5.substring(r4, r3)
            r2.append(r4)
            java.lang.String r4 = "/"
            r2.append(r4)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r4 = r7.I
            java.lang.String r4 = r4.getNumero()
            java.lang.String r1 = r4.substring(r3, r1)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.setText(r1)
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x00ed }
            r0.<init>(r7)     // Catch:{ Exception -> 0x00ed }
            android.widget.TextView r1 = r7.data_numero     // Catch:{ Exception -> 0x00ed }
            android.widget.TextView r2 = r7.data_numero     // Catch:{ Exception -> 0x00ed }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x00ed }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00ed }
            java.lang.String r0 = r0.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x00ed }
            r1.setContentDescription(r0)     // Catch:{ Exception -> 0x00ed }
            goto L_0x026f
        L_0x00ed:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x026f
        L_0x00f3:
            java.lang.String r0 = r7.M
            java.lang.String r5 = "Terceros Santander"
            boolean r0 = r0.equals(r5)
            if (r0 == 0) goto L_0x01b9
            android.widget.TextView r0 = r7.data_tipo_cuenta
            java.lang.String r5 = "Terceros Santander"
            r0.setText(r5)
            android.widget.TextView r0 = r7.data_tipo_cuenta     // Catch:{ Exception -> 0x0120 }
            android.content.Context r5 = r7.getApplicationContext()     // Catch:{ Exception -> 0x0120 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r5 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r5)     // Catch:{ Exception -> 0x0120 }
            android.widget.TextView r6 = r7.data_tipo_cuenta     // Catch:{ Exception -> 0x0120 }
            java.lang.CharSequence r6 = r6.getText()     // Catch:{ Exception -> 0x0120 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0120 }
            java.lang.String r5 = r5.applyFilterGeneral(r6)     // Catch:{ Exception -> 0x0120 }
            r0.setContentDescription(r5)     // Catch:{ Exception -> 0x0120 }
            goto L_0x0124
        L_0x0120:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0124:
            android.widget.LinearLayout r0 = r7.data_row_tipo_cuenta
            r0.setVisibility(r4)
            android.widget.TextView r0 = r7.data_banco_destino
            java.lang.String r5 = ""
            r0.setText(r5)
            android.widget.LinearLayout r0 = r7.data_row_banco_destino
            r0.setVisibility(r2)
            android.widget.TextView r0 = r7.data_tipo
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r2 = r7.K
            java.lang.String r2 = r2.getTipoDescripcion()
            android.text.Spanned r2 = android.text.Html.fromHtml(r2)
            r0.setText(r2)
            android.widget.TextView r0 = r7.data_tipo     // Catch:{ Exception -> 0x0160 }
            android.content.Context r2 = r7.getApplicationContext()     // Catch:{ Exception -> 0x0160 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r2 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r2)     // Catch:{ Exception -> 0x0160 }
            android.widget.TextView r5 = r7.data_tipo     // Catch:{ Exception -> 0x0160 }
            java.lang.CharSequence r5 = r5.getText()     // Catch:{ Exception -> 0x0160 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0160 }
            java.lang.String r2 = r2.applyFilterGeneral(r5)     // Catch:{ Exception -> 0x0160 }
            r0.setContentDescription(r2)     // Catch:{ Exception -> 0x0160 }
            goto L_0x0164
        L_0x0160:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0164:
            android.widget.TextView r0 = r7.data_numero
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r5 = r7.K
            java.lang.String r5 = r5.getSucursal()
            r2.append(r5)
            java.lang.String r5 = "-"
            r2.append(r5)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r5 = r7.K
            java.lang.String r5 = r5.getNumero()
            java.lang.String r4 = r5.substring(r4, r3)
            r2.append(r4)
            java.lang.String r4 = "/"
            r2.append(r4)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r4 = r7.K
            java.lang.String r4 = r4.getNumero()
            java.lang.String r1 = r4.substring(r3, r1)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.setText(r1)
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x026f }
            r0.<init>(r7)     // Catch:{ Exception -> 0x026f }
            android.widget.TextView r1 = r7.data_numero     // Catch:{ Exception -> 0x026f }
            android.widget.TextView r2 = r7.data_numero     // Catch:{ Exception -> 0x026f }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x026f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x026f }
            java.lang.String r0 = r0.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x026f }
            r1.setContentDescription(r0)     // Catch:{ Exception -> 0x026f }
            goto L_0x026f
        L_0x01b9:
            android.widget.TextView r0 = r7.data_tipo_cuenta
            java.lang.String r1 = "Otros Bancos"
            r0.setText(r1)
            android.widget.TextView r0 = r7.data_tipo_cuenta     // Catch:{ Exception -> 0x01dc }
            android.content.Context r1 = r7.getApplicationContext()     // Catch:{ Exception -> 0x01dc }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x01dc }
            android.widget.TextView r3 = r7.data_tipo_cuenta     // Catch:{ Exception -> 0x01dc }
            java.lang.CharSequence r3 = r3.getText()     // Catch:{ Exception -> 0x01dc }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x01dc }
            java.lang.String r1 = r1.applyFilterGeneral(r3)     // Catch:{ Exception -> 0x01dc }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x01dc }
            goto L_0x01e0
        L_0x01dc:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01e0:
            android.widget.LinearLayout r0 = r7.data_row_tipo_cuenta
            r0.setVisibility(r2)
            android.widget.TextView r0 = r7.data_banco_destino
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r7.J
            java.lang.String r1 = r1.getBancoDestino()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r7.data_banco_destino     // Catch:{ Exception -> 0x0210 }
            android.content.Context r1 = r7.getApplicationContext()     // Catch:{ Exception -> 0x0210 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x0210 }
            android.widget.TextView r2 = r7.data_banco_destino     // Catch:{ Exception -> 0x0210 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0210 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0210 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x0210 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0210 }
            goto L_0x0214
        L_0x0210:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0214:
            android.widget.LinearLayout r0 = r7.data_row_banco_destino
            r0.setVisibility(r4)
            android.widget.TextView r0 = r7.data_tipo
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r7.J
            java.lang.String r1 = r1.getTipoCtaToBane()
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilAccount.getOBAccountTypeDescription(r7, r1)
            r0.setText(r1)
            android.widget.TextView r0 = r7.data_tipo     // Catch:{ Exception -> 0x0244 }
            android.content.Context r1 = r7.getApplicationContext()     // Catch:{ Exception -> 0x0244 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x0244 }
            android.widget.TextView r2 = r7.data_tipo     // Catch:{ Exception -> 0x0244 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0244 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0244 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x0244 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0244 }
            goto L_0x0248
        L_0x0244:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0248:
            android.widget.TextView r0 = r7.data_numero
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r1 = r7.L
            java.lang.String r1 = r1.getCbu()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x026f }
            r0.<init>(r7)     // Catch:{ Exception -> 0x026f }
            android.widget.TextView r1 = r7.data_numero     // Catch:{ Exception -> 0x026f }
            android.widget.TextView r2 = r7.data_numero     // Catch:{ Exception -> 0x026f }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x026f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x026f }
            java.lang.String r0 = r0.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x026f }
            r1.setContentDescription(r0)     // Catch:{ Exception -> 0x026f }
        L_0x026f:
            android.widget.TextView r0 = r7.data_para     // Catch:{ Exception -> 0x02a6 }
            android.content.Context r1 = r7.getApplicationContext()     // Catch:{ Exception -> 0x02a6 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x02a6 }
            android.widget.TextView r2 = r7.data_para     // Catch:{ Exception -> 0x02a6 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x02a6 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x02a6 }
            android.widget.TextView r0 = r7.data_Titular     // Catch:{ Exception -> 0x02a6 }
            android.content.Context r1 = r7.getApplicationContext()     // Catch:{ Exception -> 0x02a6 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x02a6 }
            android.widget.TextView r2 = r7.data_Titular     // Catch:{ Exception -> 0x02a6 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x02a6 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x02a6 }
            goto L_0x02aa
        L_0x02a6:
            r0 = move-exception
            r0.printStackTrace()
        L_0x02aa:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.SolicitudAumentoActivity.mostrarDatosDestinatarioSeleccionadoConfirmacion():void");
    }

    public void setActionBarConfirmarSolicitudAumento() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        g();
    }

    private void g() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SolicitudAumentoActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonConfirmarSolicitud() {
        gotoPage(1, false);
    }

    public void setComprobanteSolicitudAumentoView(SolicitudLimiteTransfBodyResponseBean solicitudLimiteTransfBodyResponseBean) {
        this.q.trackScreen(getString(R.string.analytics_screen_name_tis_f33_03));
        setActionBarComprobanteSolicitudAumento();
        this.btn_volver.setOnClickListener(this);
        if (this.F.equalsIgnoreCase(Constants.SYMBOL_CURRENCY_PESOS_STR)) {
            if (this.edit_text_mporte.getFormatedText().toString().replaceAll("\\s+", "").length() >= 11) {
                this.comprobante_data_importe.setTextSize(33.0f);
            } else {
                this.comprobante_data_importe.setTextSize(42.0f);
            }
            TextView textView = this.comprobante_data_importe;
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.SYMBOL_CURRENCY_PESOS);
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(this.edit_text_mporte.getFormatedText().toString());
            textView.setText(sb.toString());
        } else {
            if (this.edit_text_mporte.getFormatedText().toString().replaceAll("\\s+", "").length() >= 11) {
                this.comprobante_data_importe.setTextSize(30.0f);
            } else {
                this.comprobante_data_importe.setTextSize(42.0f);
            }
            TextView textView2 = this.comprobante_data_importe;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Constants.SYMBOL_CURRENCY_DOLAR);
            sb2.append(UtilsCuentas.SEPARAOR2);
            sb2.append(this.edit_text_mporte.getFormatedText().toString());
            textView2.setText(sb2.toString());
        }
        this.comprobante_data_fecha.setText(this.lbl_data_fecha.getText());
        this.comprobante_data_de.setText(this.s.getDescCtaDestino());
        this.comprobante_data_nroComprobante.setText(solicitudLimiteTransfBodyResponseBean.getNroComprobante());
        this.x = this.comprobante_data_nroComprobante.getText().toString();
        this.q.trackTransaction(getString(R.string.analytics_transaction_tis_aumentar_limite_id), this.x);
        this.data_leyenda_comprobante.setText(k());
        mostrarDatosDestinatarioSeleccionadoComprobante();
        try {
            this.comprobante_data_importe.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.comprobante_data_importe.getText().toString()));
            this.comprobante_data_de.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.comprobante_data_de.getText().toString()));
            this.comprobante_data_fecha.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.comprobante_data_fecha.getText().toString()));
            this.comprobante_data_nroComprobante.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.comprobante_data_nroComprobante.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:0|(9:2|3|4|7|8|9|12|13|14)(2:15|(9:17|18|19|22|23|24|27|28|29)(12:30|31|32|35|36|37|40|41|42|45|46|47))|48|49|53) */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x02a0, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x02a1, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:48:0x0269 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mostrarDatosDestinatarioSeleccionadoComprobante() {
        /*
            r7 = this;
            android.widget.TextView r0 = r7.comprobante_data_para
            android.widget.TextView r1 = r7.texto_seleccionar
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r7.comprobante_data_Titular
            android.widget.TextView r1 = r7.txtTitularSolicitud
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            java.lang.String r0 = r7.M
            java.lang.String r1 = "Propia"
            boolean r0 = r0.equals(r1)
            r1 = 7
            r2 = 8
            r3 = 6
            r4 = 0
            if (r0 == 0) goto L_0x00ed
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r7.y
            java.lang.String r0 = r0.getInfo2()
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r0 = r7.getDatosCuentasBeanPara(r0)
            r7.I = r0
            android.widget.TextView r0 = r7.comprobante_data_tipo_cuenta
            java.lang.String r5 = "Propia"
            r0.setText(r5)
            android.widget.TextView r0 = r7.comprobante_data_tipo_cuenta     // Catch:{ Exception -> 0x0054 }
            android.content.Context r5 = r7.getApplicationContext()     // Catch:{ Exception -> 0x0054 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r5 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r5)     // Catch:{ Exception -> 0x0054 }
            android.widget.TextView r6 = r7.comprobante_data_tipo_cuenta     // Catch:{ Exception -> 0x0054 }
            java.lang.CharSequence r6 = r6.getText()     // Catch:{ Exception -> 0x0054 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0054 }
            java.lang.String r5 = r5.applyFilterGeneral(r6)     // Catch:{ Exception -> 0x0054 }
            r0.setContentDescription(r5)     // Catch:{ Exception -> 0x0054 }
            goto L_0x0058
        L_0x0054:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0058:
            android.widget.LinearLayout r0 = r7.comprobante_data_row_tipo_cuenta
            r0.setVisibility(r4)
            android.widget.TextView r0 = r7.comprobante_data_banco_destino
            java.lang.String r5 = ""
            r0.setText(r5)
            android.widget.LinearLayout r0 = r7.comprobante_data_row_banco_destino
            r0.setVisibility(r2)
            android.widget.TextView r0 = r7.comprobante_data_tipo
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r2 = r7.I
            java.lang.String r2 = r2.getTipoDescripcion()
            android.text.Spanned r2 = android.text.Html.fromHtml(r2)
            r0.setText(r2)
            android.widget.TextView r0 = r7.comprobante_data_tipo     // Catch:{ Exception -> 0x0094 }
            android.content.Context r2 = r7.getApplicationContext()     // Catch:{ Exception -> 0x0094 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r2 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r2)     // Catch:{ Exception -> 0x0094 }
            android.widget.TextView r5 = r7.comprobante_data_tipo     // Catch:{ Exception -> 0x0094 }
            java.lang.CharSequence r5 = r5.getText()     // Catch:{ Exception -> 0x0094 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0094 }
            java.lang.String r2 = r2.applyFilterGeneral(r5)     // Catch:{ Exception -> 0x0094 }
            r0.setContentDescription(r2)     // Catch:{ Exception -> 0x0094 }
            goto L_0x0098
        L_0x0094:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0098:
            android.widget.TextView r0 = r7.comprobante_data_numero
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r5 = r7.I
            java.lang.String r5 = r5.getSucursal()
            r2.append(r5)
            java.lang.String r5 = "-"
            r2.append(r5)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r5 = r7.I
            java.lang.String r5 = r5.getNumero()
            java.lang.String r4 = r5.substring(r4, r3)
            r2.append(r4)
            java.lang.String r4 = "/"
            r2.append(r4)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r4 = r7.I
            java.lang.String r4 = r4.getNumero()
            java.lang.String r1 = r4.substring(r3, r1)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.setText(r1)
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x0269 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0269 }
            android.widget.TextView r1 = r7.comprobante_data_numero     // Catch:{ Exception -> 0x0269 }
            android.widget.TextView r2 = r7.comprobante_data_numero     // Catch:{ Exception -> 0x0269 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0269 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0269 }
            java.lang.String r0 = r0.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x0269 }
            r1.setContentDescription(r0)     // Catch:{ Exception -> 0x0269 }
            goto L_0x0269
        L_0x00ed:
            java.lang.String r0 = r7.M
            java.lang.String r5 = "Terceros Santander"
            boolean r0 = r0.equals(r5)
            if (r0 == 0) goto L_0x01b3
            android.widget.TextView r0 = r7.comprobante_data_tipo_cuenta
            java.lang.String r5 = "Terceros Santander"
            r0.setText(r5)
            android.widget.TextView r0 = r7.comprobante_data_tipo_cuenta     // Catch:{ Exception -> 0x011a }
            android.content.Context r5 = r7.getApplicationContext()     // Catch:{ Exception -> 0x011a }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r5 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r5)     // Catch:{ Exception -> 0x011a }
            android.widget.TextView r6 = r7.comprobante_data_tipo_cuenta     // Catch:{ Exception -> 0x011a }
            java.lang.CharSequence r6 = r6.getText()     // Catch:{ Exception -> 0x011a }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x011a }
            java.lang.String r5 = r5.applyFilterGeneral(r6)     // Catch:{ Exception -> 0x011a }
            r0.setContentDescription(r5)     // Catch:{ Exception -> 0x011a }
            goto L_0x011e
        L_0x011a:
            r0 = move-exception
            r0.printStackTrace()
        L_0x011e:
            android.widget.LinearLayout r0 = r7.comprobante_data_row_tipo_cuenta
            r0.setVisibility(r4)
            android.widget.TextView r0 = r7.comprobante_data_banco_destino
            java.lang.String r5 = ""
            r0.setText(r5)
            android.widget.LinearLayout r0 = r7.comprobante_data_row_banco_destino
            r0.setVisibility(r2)
            android.widget.TextView r0 = r7.comprobante_data_tipo
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r2 = r7.K
            java.lang.String r2 = r2.getTipoDescripcion()
            android.text.Spanned r2 = android.text.Html.fromHtml(r2)
            r0.setText(r2)
            android.widget.TextView r0 = r7.comprobante_data_tipo     // Catch:{ Exception -> 0x015a }
            android.content.Context r2 = r7.getApplicationContext()     // Catch:{ Exception -> 0x015a }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r2 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r2)     // Catch:{ Exception -> 0x015a }
            android.widget.TextView r5 = r7.comprobante_data_tipo     // Catch:{ Exception -> 0x015a }
            java.lang.CharSequence r5 = r5.getText()     // Catch:{ Exception -> 0x015a }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x015a }
            java.lang.String r2 = r2.applyFilterGeneral(r5)     // Catch:{ Exception -> 0x015a }
            r0.setContentDescription(r2)     // Catch:{ Exception -> 0x015a }
            goto L_0x015e
        L_0x015a:
            r0 = move-exception
            r0.printStackTrace()
        L_0x015e:
            android.widget.TextView r0 = r7.comprobante_data_numero
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r5 = r7.K
            java.lang.String r5 = r5.getSucursal()
            r2.append(r5)
            java.lang.String r5 = "-"
            r2.append(r5)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r5 = r7.K
            java.lang.String r5 = r5.getNumero()
            java.lang.String r4 = r5.substring(r4, r3)
            r2.append(r4)
            java.lang.String r4 = "/"
            r2.append(r4)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r4 = r7.K
            java.lang.String r4 = r4.getNumero()
            java.lang.String r1 = r4.substring(r3, r1)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.setText(r1)
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x0269 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0269 }
            android.widget.TextView r1 = r7.comprobante_data_numero     // Catch:{ Exception -> 0x0269 }
            android.widget.TextView r2 = r7.comprobante_data_numero     // Catch:{ Exception -> 0x0269 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0269 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0269 }
            java.lang.String r0 = r0.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x0269 }
            r1.setContentDescription(r0)     // Catch:{ Exception -> 0x0269 }
            goto L_0x0269
        L_0x01b3:
            android.widget.TextView r0 = r7.comprobante_data_tipo_cuenta
            java.lang.String r1 = "Otros Bancos"
            r0.setText(r1)
            android.widget.TextView r0 = r7.comprobante_data_tipo_cuenta     // Catch:{ Exception -> 0x01d6 }
            android.content.Context r1 = r7.getApplicationContext()     // Catch:{ Exception -> 0x01d6 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x01d6 }
            android.widget.TextView r3 = r7.comprobante_data_tipo_cuenta     // Catch:{ Exception -> 0x01d6 }
            java.lang.CharSequence r3 = r3.getText()     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r1 = r1.applyFilterGeneral(r3)     // Catch:{ Exception -> 0x01d6 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x01d6 }
            goto L_0x01da
        L_0x01d6:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01da:
            android.widget.LinearLayout r0 = r7.comprobante_data_row_tipo_cuenta
            r0.setVisibility(r2)
            android.widget.TextView r0 = r7.comprobante_data_banco_destino
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r7.J
            java.lang.String r1 = r1.getBancoDestino()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r7.comprobante_data_banco_destino     // Catch:{ Exception -> 0x020a }
            android.content.Context r1 = r7.getApplicationContext()     // Catch:{ Exception -> 0x020a }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x020a }
            android.widget.TextView r2 = r7.comprobante_data_banco_destino     // Catch:{ Exception -> 0x020a }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x020a }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x020a }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x020a }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x020a }
            goto L_0x020e
        L_0x020a:
            r0 = move-exception
            r0.printStackTrace()
        L_0x020e:
            android.widget.LinearLayout r0 = r7.comprobante_data_row_banco_destino
            r0.setVisibility(r4)
            android.widget.TextView r0 = r7.comprobante_data_tipo
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r7.J
            java.lang.String r1 = r1.getTipoCtaToBane()
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilAccount.getOBAccountTypeDescription(r7, r1)
            r0.setText(r1)
            android.widget.TextView r0 = r7.comprobante_data_tipo     // Catch:{ Exception -> 0x023e }
            android.content.Context r1 = r7.getApplicationContext()     // Catch:{ Exception -> 0x023e }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x023e }
            android.widget.TextView r2 = r7.comprobante_data_tipo     // Catch:{ Exception -> 0x023e }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x023e }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x023e }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x023e }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x023e }
            goto L_0x0242
        L_0x023e:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0242:
            android.widget.TextView r0 = r7.comprobante_data_numero
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r1 = r7.L
            java.lang.String r1 = r1.getCbu()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x0269 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0269 }
            android.widget.TextView r1 = r7.comprobante_data_numero     // Catch:{ Exception -> 0x0269 }
            android.widget.TextView r2 = r7.comprobante_data_numero     // Catch:{ Exception -> 0x0269 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0269 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0269 }
            java.lang.String r0 = r0.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x0269 }
            r1.setContentDescription(r0)     // Catch:{ Exception -> 0x0269 }
        L_0x0269:
            android.widget.TextView r0 = r7.comprobante_data_para     // Catch:{ Exception -> 0x02a0 }
            android.content.Context r1 = r7.getApplicationContext()     // Catch:{ Exception -> 0x02a0 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x02a0 }
            android.widget.TextView r2 = r7.comprobante_data_para     // Catch:{ Exception -> 0x02a0 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x02a0 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x02a0 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x02a0 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x02a0 }
            android.widget.TextView r0 = r7.comprobante_data_Titular     // Catch:{ Exception -> 0x02a0 }
            android.content.Context r1 = r7.getApplicationContext()     // Catch:{ Exception -> 0x02a0 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x02a0 }
            android.widget.TextView r2 = r7.comprobante_data_Titular     // Catch:{ Exception -> 0x02a0 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x02a0 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x02a0 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x02a0 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x02a0 }
            goto L_0x02a4
        L_0x02a0:
            r0 = move-exception
            r0.printStackTrace()
        L_0x02a4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.SolicitudAumentoActivity.mostrarDatosDestinatarioSeleccionadoComprobante():void");
    }

    public void setActionBarComprobanteSolicitudAumento() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(false);
        h();
    }

    private void h() {
        this.mActionBar = getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        ImageView imageView2 = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.menu);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SolicitudAumentoActivity.this.switchDrawer();
                }
            });
        }
        i();
        if (imageView2 != null) {
            imageView2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SolicitudAumentoActivity.this.E.show(SolicitudAumentoActivity.this.getSupportFragmentManager(), "Dialog");
                }
            });
        }
    }

    private void i() {
        this.D = j();
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        this.E = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_TITLE), null, arrayList, getString(R.string.ID_4109_SEGUROS_LBL_CANCELAR), null, null, null, arrayList);
        this.E.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(SolicitudAumentoActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                    SolicitudAumentoActivity.this.q.trackEvent(SolicitudAumentoActivity.this.getString(R.string.analytics_event_category_tis), SolicitudAumentoActivity.this.getString(R.string.analytics_event_category_action_tis_aumentar_limite), SolicitudAumentoActivity.this.getString(R.string.analytics_event_tis_03_compartir_comprobante));
                    SolicitudAumentoActivity.this.D.optionShareSelected();
                } else if (str.equalsIgnoreCase(SolicitudAumentoActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                    SolicitudAumentoActivity.this.q.trackEvent(SolicitudAumentoActivity.this.getString(R.string.analytics_event_category_tis), SolicitudAumentoActivity.this.getString(R.string.analytics_event_category_action_tis_aumentar_limite), SolicitudAumentoActivity.this.getString(R.string.analytics_event_tis_03_descarga_comprobante));
                    SolicitudAumentoActivity.this.D.optionDownloadSelected();
                }
            }
        });
        this.E.setCancelable(true);
    }

    private OptionsToShare j() {
        return new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public View getViewToShare() {
                return SolicitudAumentoActivity.this.comprobanteSolicitud;
            }

            public void receiveIntentAppShare(Intent intent) {
                SolicitudAumentoActivity.this.startActivity(Intent.createChooser(intent, SolicitudAumentoActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
            }

            public String getFileName() {
                String string = SolicitudAumentoActivity.this.getString(R.string.F33_03_Lbl_Comprobante_de_Solicitud_de_Aumento_de_Limite);
                StringBuilder sb = new StringBuilder();
                sb.append(" - ");
                sb.append(SolicitudAumentoActivity.this.x);
                return Html.fromHtml(string.concat(sb.toString())).toString();
            }

            public String getSubjectReceiptToShare() {
                return SolicitudAumentoActivity.this.getString(R.string.F33_03_Lbl_Comprobante_de_Solicitud_de_Aumento_de_Limite);
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                SolicitudAumentoActivity.this.G = true;
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                SolicitudAumentoActivity.this.G = true;
            }

            public void onAbortShare() {
                super.onAbortShare();
                SolicitudAumentoActivity.this.G = true;
                SolicitudAumentoActivity.this.onBackPressed();
            }
        };
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.D.onRequestPermissionsResult(i, strArr, iArr);
    }

    public boolean canExit(int i) {
        if (!this.G) {
            final int i2 = i;
            AnonymousClass12 r1 = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
                public View getViewToShare() {
                    return SolicitudAumentoActivity.this.comprobanteSolicitud;
                }

                public void receiveIntentAppShare(Intent intent) {
                    SolicitudAumentoActivity.this.startActivity(Intent.createChooser(intent, SolicitudAumentoActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
                }

                public String getFileName() {
                    String string = SolicitudAumentoActivity.this.getString(R.string.F33_03_Lbl_Comprobante_de_Solicitud_de_Aumento_de_Limite);
                    StringBuilder sb = new StringBuilder();
                    sb.append(" - ");
                    sb.append(SolicitudAumentoActivity.this.x);
                    return Html.fromHtml(string.concat(sb.toString())).toString();
                }

                public String getSubjectReceiptToShare() {
                    return SolicitudAumentoActivity.this.getString(R.string.F33_03_Lbl_Comprobante_de_Solicitud_de_Aumento_de_Limite);
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    SolicitudAumentoActivity.this.G = true;
                    SolicitudAumentoActivity.this.q.trackEvent(SolicitudAumentoActivity.this.getString(R.string.analytics_event_category_tis), SolicitudAumentoActivity.this.getString(R.string.analytics_event_category_action_tis_aumentar_limite), SolicitudAumentoActivity.this.getString(R.string.analytics_event_tis_03_descarga_comprobante));
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    SolicitudAumentoActivity.this.G = true;
                    SolicitudAumentoActivity.this.q.trackEvent(SolicitudAumentoActivity.this.getString(R.string.analytics_event_category_tis), SolicitudAumentoActivity.this.getString(R.string.analytics_event_category_action_tis_aumentar_limite), SolicitudAumentoActivity.this.getString(R.string.analytics_event_tis_03_compartir_comprobante));
                }

                public void onAbortShare() {
                    super.onAbortShare();
                    SolicitudAumentoActivity.this.G = true;
                    SolicitudAumentoActivity.this.onClickItem(i2);
                }
            };
            r1.showAlert();
        }
        return this.G;
    }

    public void backButtonComprobanteSolicitud() {
        setResult(-1);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private String k() {
        String str = "";
        for (LeyendaBean leyendaBean : this.r.getLeyendasBean()) {
            if (leyendaBean.getIdLeyenda().equalsIgnoreCase(leyendas.TRANSF_SOL_AL_2)) {
                return Html.fromHtml(leyendaBean.getDescripcion()).toString();
            }
        }
        return str;
    }

    private Integer l() {
        for (LeyendaBean leyendaBean : this.r.getLeyendasBean()) {
            if (leyendaBean.getIdLeyenda().equalsIgnoreCase(leyendas.HORAS)) {
                return Integer.valueOf(Integer.parseInt(leyendaBean.getDescripcion()) / leyendas.DIA.intValue());
            }
        }
        return null;
    }

    public void onBackPressed() {
        switch (this.mControlPager.getCurrentView().getId()) {
            case R.id.layout_comprobante_solicitud_aumento /*2131364909*/:
                if (!this.G) {
                    this.D.showAlert();
                    return;
                } else {
                    backButtonComprobanteSolicitud();
                    return;
                }
            case R.id.layout_confirmar_solicitud_aumento /*2131364916*/:
                backButtonConfirmarSolicitud();
                return;
            case R.id.layout_solicitud_aumento /*2131364969*/:
                backButtonSolicitudAumento();
                return;
            case R.id.layout_solicitud_aumento2 /*2131364970*/:
                backButtonSolicitudAumentoSecondPage();
                return;
            default:
                return;
        }
    }

    public void attachView() {
        switch (this.mControlPager.getDisplayedChild()) {
            case 0:
                if (!this.z.isViewAttached()) {
                    this.z.attachView(this);
                    return;
                }
                return;
            case 1:
                if (!this.A.isViewAttached()) {
                    this.A.attachView(this);
                    return;
                }
                return;
            case 2:
                if (!this.B.isViewAttached()) {
                    this.B.attachView(this);
                    return;
                }
                return;
            case 3:
                if (!this.C.isViewAttached()) {
                    this.C.attachView(this);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void detachView() {
        if (this.z.isViewAttached()) {
            this.z.detachView();
        }
        if (this.A.isViewAttached()) {
            this.A.detachView();
        }
        if (this.B.isViewAttached()) {
            this.B.detachView();
        }
        if (this.C.isViewAttached()) {
            this.C.detachView();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.F33_00_btn_continuar /*2131363999*/:
                gotoSetSolicitudAumentoSecondPage();
                return;
            case R.id.F33_01_btn_enviar /*2131364001*/:
                if (importeValido(this.y.getTipoDestino(), this.s.getIdMoneda(), this.M) != 0) {
                    customErrorDialog(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.MSG_USER00502_TIS_IMPORTE));
                    return;
                } else {
                    gotoConfirmarSolicitudAumento();
                    return;
                }
            case R.id.F33_02_btn_confirmar /*2131364015*/:
                this.B.showConfirmDialog(this.s.getTipo(), this.s.getSucursal(), this.s.getNumero(), this.v, f(), this.y.getTipoDestino(), m(), n(), o(), UtilDate.getDateFormat(this.H, Constants.FORMAT_DATE_WS_2));
                return;
            case R.id.F33_03_btn_volver /*2131364038*/:
                if (!this.G) {
                    this.D.showAlert();
                    return;
                }
                setResult(-1);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return;
            case R.id.linearLayoutDe /*2131365049*/:
                hideKeyboard();
                onClickLinearLayoutDe();
                return;
            case R.id.linearLayoutPara /*2131365070*/:
                hideKeyboard();
                goToAgendaDestinatarios();
                return;
            case R.id.selectorImporte /*2131365681*/:
                onSelectCurrency();
                return;
            case R.id.textViewFechaEjecucion /*2131365933*/:
                showDateSelector(this.lbl_data_fecha.getText().toString());
                return;
            default:
                return;
        }
    }

    private String m() {
        if (this.M.equals(TransferenciasConstants.cBANCO_PROPIA)) {
            return this.I.getTipo();
        }
        if (this.M.equals(TransferenciasConstants.cBANCO_SR_TERCEROS)) {
            return this.K.getTipo();
        }
        return this.J.getTipoCtaToBane();
    }

    private String n() {
        if (this.M.equals(TransferenciasConstants.cBANCO_PROPIA)) {
            return this.I.getSucursal();
        }
        if (this.M.equals(TransferenciasConstants.cBANCO_SR_TERCEROS)) {
            return this.K.getSucursal();
        }
        return this.J.getSucursal();
    }

    private String o() {
        if (this.M.equals(TransferenciasConstants.cBANCO_PROPIA)) {
            return this.I.getNumero();
        }
        if (this.M.equals(TransferenciasConstants.cBANCO_SR_TERCEROS)) {
            return TextUtils.isEmpty(this.y.getAlias()) ? this.K.getNumero() : this.y.getAlias();
        }
        return TextUtils.isEmpty(this.y.getAlias()) ? this.L.getCbu() : this.y.getAlias();
    }

    public void goToAgendaDestinatarios() {
        Intent intent = new Intent(this, AgendaDestinatariosTransferenciasActivity.class);
        if (this.y != null) {
            intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO, this.y);
        }
        intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_FILTER_CURRENCY, this.F);
        intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_CUENTA_PROPIA, this.s);
        intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_CUENTAS_PROPIAS, this.t);
        intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_ACCION_VERIFICAR, accionVerificarDatosTransf.SELECCION);
        startActivityForResult(intent, 10);
    }

    public void showDateSelector(String str) {
        IsbanDatePickerDialogFragment newInstance = IsbanDatePickerDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_SELECCION_FECHA), UtilDate.getDateFormat(str, getString(R.string.FORMAT_FULL_DATE), Constants.FORMAT_DATE_APP), Constants.FORMAT_DATE_APP);
        newInstance.setDialogListener(new IDateDialogListener() {
            public void onDateSelected(Date date) {
                SolicitudAumentoActivity.this.setFechaSeleccionada(date);
            }
        });
        newInstance.show(getSupportFragmentManager(), "SELECT_DATE_DIAGLO");
    }

    public void setFechaSeleccionada(Date date) {
        this.H = date;
        this.lbl_data_fecha.setText(new DateTime((Object) date).toString(getString(R.string.FORMAT_FULL_DATE)));
        TextView textView = this.lbl_data_fecha;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.CONTENT_FECHA));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.lbl_data_fecha.getText().toString());
        textView.setContentDescription(CAccessibility.applyFilterMaskSelector(sb.toString()));
    }

    public DatosCuentasBean getDatosCuentasBeanPara(String str) {
        for (int i = 0; i < this.t.getListDatosCuentasBean().size(); i++) {
            if (((DatosCuentasBean) this.t.getListDatosCuentasBean().get(i)).getDescCtaDestino().equals(str)) {
                return (DatosCuentasBean) this.t.getListDatosCuentasBean().get(i);
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (!activityResultHandler(i2, intent) && i == 10 && i2 == -1) {
            this.y = (AgendaDestinatarios) intent.getParcelableExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO);
            this.J = (VerificaDatosSalidaOBBean) intent.getParcelableExtra(TransferenciasConstants.cINTENT_EXTRA_VERIFICA_DATOS_TRANSF);
            this.L = (DatosCuentasDestOBBean) intent.getParcelableExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO_OB_BEAN);
            this.K = (DatosCuentasDestBSRBean) intent.getParcelableExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO_BSR_BEAN);
            mostrarDatosDestinatarioSeleccionadoSolicitud();
            d();
        }
    }
}
