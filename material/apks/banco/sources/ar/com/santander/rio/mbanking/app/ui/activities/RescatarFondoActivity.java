package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.funds.ComprobanteRescateFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.ComprobanteRescateFondoView;
import ar.com.santander.rio.mbanking.app.module.funds.ConfirmarRescateFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.ConfirmarRescateFondoView;
import ar.com.santander.rio.mbanking.app.module.funds.FueraHorarioFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.FueraHorarioFondoView;
import ar.com.santander.rio.mbanking.app.module.funds.RescatarFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.RescatarFondoView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaOperativaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RescatarFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
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

public class RescatarFondoActivity extends MvpPrivateMenuActivity implements OnClickListener, ComprobanteRescateFondoView, ConfirmarRescateFondoView, FueraHorarioFondoView, RescatarFondoView {
    private ArrayList<CuentaFondosBean> A;
    /* access modifiers changed from: private */
    public IsbanDialogFragment B;
    /* access modifiers changed from: private */
    public OptionsToShare C;
    /* access modifiers changed from: private */
    public boolean D = false;
    /* access modifiers changed from: private */
    public RescatarFondoBodyResponseBean E;
    private ArrayList<CuentaOperativaBean> F;
    @InjectView(2131363129)
    Button btnConfirmar;
    @InjectView(2131363099)
    Button btnContinuar;
    @InjectView(2131363167)
    Button btnVolver;
    @InjectView(2131363209)
    ScrollView comprobanteOperacion;
    @InjectView(2131363130)
    ImageView imgCheckboxTyC;
    @InjectView(2131363095)
    NumericEditTextWithPrefixAccesibility inpAmount;
    @InjectView(2131363168)
    TextView lblCertificadoComprobante;
    @InjectView(2131363193)
    TextView lblComprobanteTerminosLegales;
    @InjectView(2131363131)
    TextView lblConectorVerTyC;
    @InjectView(2131363149)
    TextView lblConfirmarTerminosLegales;
    @InjectView(2131363174)
    TextView lblDataComprobanteCertificado;
    @InjectView(2131363175)
    TextView lblDataComprobanteCuentaDestino;
    @InjectView(2131363176)
    TextView lblDataComprobanteCuentaTitulo;
    @InjectView(2131363178)
    TextView lblDataComprobanteCuotaparte;
    @InjectView(2131363179)
    TextView lblDataComprobanteCuotapartesBottom;
    @InjectView(2131363180)
    TextView lblDataComprobanteFechaHora;
    @InjectView(2131363181)
    TextView lblDataComprobanteFondo;
    @InjectView(2131363189)
    TextView lblDataComprobanteFondoRescatar;
    @InjectView(2131363183)
    TextView lblDataComprobanteImporte;
    @InjectView(2131363182)
    TextView lblDataComprobanteImporteBottom;
    @InjectView(2131363186)
    TextView lblDataComprobanteValorCuotaparte;
    @InjectView(2131363185)
    TextView lblDataComprobanteValorCuotaparteBottom;
    @InjectView(2131363136)
    TextView lblDataConfirmarCuentaDestino;
    @InjectView(2131363137)
    TextView lblDataConfirmarCuentaTitulo;
    @InjectView(2131363138)
    TextView lblDataConfirmarCuotaparte;
    @InjectView(2131363139)
    TextView lblDataConfirmarFondo;
    @InjectView(2131363140)
    TextView lblDataConfirmarFondoRescatar;
    @InjectView(2131363141)
    TextView lblDataConfirmarImporte;
    @InjectView(2131363143)
    TextView lblDataConfirmarPlazoPago;
    @InjectView(2131363144)
    TextView lblDataConfirmarValorCuotaparte;
    @InjectView(2131363104)
    TextView lblDataCuentaTitulo;
    @InjectView(2131363106)
    TextView lblDataCuotapartes;
    @InjectView(2131363109)
    TextView lblDataDestino;
    @InjectView(2131363107)
    TextView lblDataFondo;
    @InjectView(2131363108)
    TextView lblDataImporteTotal;
    @InjectView(2131363110)
    TextView lblDataPlazoPago;
    @InjectView(2131363184)
    TextView lblDataPlazodePago;
    @InjectView(2131363111)
    TextView lblDataValorCuotapartes;
    @InjectView(2131363115)
    TextView lblDestino;
    @InjectView(2131364053)
    TextView lblFueraHorarioMensaje;
    @InjectView(2131363096)
    TextView lblSelectedAmountType;
    @InjectView(2131363152)
    TextView lblVerTyC;
    @InjectView(2131364704)
    ViewFlipper mControlPager;
    @Inject
    AnalyticsManager p;
    Boolean q = Boolean.valueOf(false);
    private RescatarFondoPresenter r;
    @InjectView(2131363157)
    RelativeLayout rlTerminosyCondiciones;
    @InjectView(2131363098)
    RelativeLayout rllSelectedAmountType;
    @InjectView(2131363202)
    LinearLayout rowCuentaDebitoComprobante;
    @InjectView(2131363121)
    LinearLayout rowCuentaTituloRescatar;
    @InjectView(2131363122)
    LinearLayout rowCuotapartes;
    @InjectView(2131365587)
    LinearLayout rowImporteTop;
    @InjectView(2131363200)
    LinearLayout rowLinearCuentaDestino;
    @InjectView(2131363205)
    LinearLayout rowLinearFondo;
    @InjectView(2131363125)
    LinearLayout rowOrigenRescatar;
    @InjectView(2131363163)
    LinearLayout rowPlazoDePago;
    @InjectView(2131363126)
    LinearLayout rowPlazoPago;
    @InjectView(2131363207)
    LinearLayout rowPlazoPagoComprobante;
    @InjectView(2131363208)
    LinearLayout rowValorCuotaparteComprobante;
    @InjectView(2131363127)
    LinearLayout rowValorCuotapartes;
    @InjectView(2131363164)
    View rowValorCuotapartesBottomConfirmar;
    @InjectView(2131365592)
    LinearLayout rowValorCuotapartesTop;
    private ConfirmarRescateFondoPresenter s;
    private FueraHorarioFondoPresenter t;
    private ComprobanteRescateFondoPresenter u;
    private FondoBean v;
    @InjectView(2131363128)
    CustomSpinner vgSelectorAccount;
    private String w;
    private CuentaFondosBean x;
    private Cuenta y;
    private ArrayList<Cuenta> z;

    public void clearScreenData() {
    }

    public void configureActionBar() {
    }

    public Context getContext() {
        return this;
    }

    public int getMainLayout() {
        return R.layout.activity_rescatar_fondo;
    }

    public SessionManager getSessionManager() {
        return this.sessionManager;
    }

    public AnalyticsManager getAnalyticsManager() {
        return this.p;
    }

    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        this.p.trackScreen(getString(R.string.analytics_screen_pantalla_indicador_de_importe_rescate));
        ButterKnife.inject((Activity) this);
        initialize();
    }

    public void initialize() {
        this.r = new RescatarFondoPresenter(this.mBus, this.mDataManager);
        this.s = new ConfirmarRescateFondoPresenter(this.mBus, this.mDataManager);
        this.t = new FueraHorarioFondoPresenter(this.mBus);
        this.u = new ComprobanteRescateFondoPresenter(this.mBus, this.mDataManager);
        this.A = getIntent().getParcelableArrayListExtra("CUENTAS");
        this.x = (CuentaFondosBean) getIntent().getParcelableExtra("CUENTA");
        this.v = (FondoBean) getIntent().getParcelableExtra(FondosConstants.INTENT_EXTRA_FONDO);
        this.w = getIntent().getStringExtra("ORIGEN");
        this.F = getIntent().getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_CUENTAS_OPERATIVAS);
        this.z = a(CAccounts.getInstance(getSessionManager()).getListAccountsUserFromSession());
        this.q = getRescatarTyC();
        gotoRescatarFondo();
    }

    private ArrayList<Cuenta> a(List<Cuenta> list) {
        ArrayList<Cuenta> arrayList = new ArrayList<>();
        String str = "";
        if (this.v != null) {
            if (this.v.getMoneda().equalsIgnoreCase("0")) {
                str = Constants.SYMBOL_CURRENCY_PESOS;
            } else if (this.v.getMoneda().equalsIgnoreCase("2")) {
                str = Constants.SYMBOL_CURRENCY_DOLAR;
            } else {
                str = UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.v.getMoneda()).toString());
            }
        }
        for (Cuenta cuenta : list) {
            if (TextUtils.isEmpty(str) || cuenta.getTipo().equalsIgnoreCase("02") || UtilAccount.getCurrencyOfAccount(getSessionManager(), cuenta).equalsIgnoreCase(str)) {
                if (!cuenta.getTipo().equalsIgnoreCase("02")) {
                    arrayList.add(cuenta);
                } else if (str.equalsIgnoreCase(Constants.SYMBOL_CURRENCY_PESOS)) {
                    Cuenta cuenta2 = (Cuenta) cuenta.clone();
                    cuenta2.setTipo("09");
                    arrayList.add(cuenta2);
                } else {
                    Cuenta cuenta3 = (Cuenta) cuenta.clone();
                    cuenta3.setTipo("10");
                    arrayList.add(cuenta3);
                }
            }
        }
        return arrayList;
    }

    public void configureLayout() {
        this.vgSelectorAccount.setOnClickListener(this);
    }

    public Boolean getRescatarTyC() {
        return Boolean.valueOf(this.sessionManager.aceptaTyCRescatarFondo.equalsIgnoreCase("S"));
    }

    public void gotoRescatarFondo() {
        gotoPage(0);
        this.r.onCreatePage();
    }

    public void setRescatarFondoView() {
        Cuenta cuenta;
        setActionBarRescatar();
        this.vgSelectorAccount.setVisibility(8);
        FondoBean fondoBean = this.v != null ? this.v : null;
        setSelectedAccount(this.x);
        if (fondoBean != null) {
            setSelectedFondo(fondoBean);
        }
        if (this.w.equalsIgnoreCase("DETALLE_FONDO")) {
            this.lblDataFondo.setTextColor(getResources().getColor(R.color.generic_selectable));
            try {
                this.lblDataFondo.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataFondo.getText().toString()));
            } catch (Exception unused) {
            }
        } else {
            this.lblDataFondo.setOnClickListener(this);
            this.lblDataFondo.setTextColor(getResources().getColor(R.color.generic_selectable));
        }
        this.rllSelectedAmountType.setOnClickListener(this);
        this.lblSelectedAmountType.setText(getString(R.string.F24_20_SELECTED_AMOUNT_TOTAL));
        this.inpAmount.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                RescatarFondoActivity.this.b();
            }
        });
        this.rowCuentaTituloRescatar.setVisibility(8);
        this.btnContinuar.setOnClickListener(this);
        this.lblDestino.setText(getString(R.string.F24_20_lbl_cuenta_destino));
        this.rowCuotapartes.setVisibility(8);
        this.rowValorCuotapartes.setVisibility(8);
        List cuentasValidas = getCuentasValidas(this.F);
        if (this.z == null || this.z.size() == 0 || cuentasValidas.size() == 0) {
            this.lblDataDestino.setText(getString(R.string.F24_20_lbl_data_seleccionar));
            this.lblDataDestino.setTextColor(getResources().getColor(R.color.grey_medium_light));
        } else if (cuentasValidas.size() > 1) {
            this.lblDataDestino.setText(getString(R.string.F24_20_lbl_data_seleccionar));
            this.lblDataDestino.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/OpenSans-Semibold.otf"));
            this.lblDataDestino.setTextColor(getResources().getColor(R.color.generic_black));
            this.lblDataDestino.setTextSize(13.0f);
            this.lblDataDestino.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
            this.lblDataDestino.setOnClickListener(this);
        } else {
            this.lblDataDestino.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/OpenSans-Semibold.otf"));
            this.lblDataDestino.setTextColor(getResources().getColor(R.color.generic_black));
            this.lblDataDestino.setTextSize(13.0f);
            if (!UtilCurrency.isDolares(fondoBean.getMoneda()) || cuentasValidas.size() <= 1) {
                cuenta = new Cuenta(((CuentaOperativaBean) cuentasValidas.get(0)).getTipoCta(), ((CuentaOperativaBean) cuentasValidas.get(0)).getNumero(), ((CuentaOperativaBean) cuentasValidas.get(0)).getSucursal());
            } else {
                cuenta = new Cuenta(((CuentaOperativaBean) cuentasValidas.get(1)).getTipoCta(), ((CuentaOperativaBean) cuentasValidas.get(1)).getNumero(), ((CuentaOperativaBean) cuentasValidas.get(1)).getSucursal());
            }
            setSelectedDestinationAccount(cuenta);
        }
        setSelectedAmountTypeTotal();
    }

    /* access modifiers changed from: private */
    public void b() {
        if (isValidDataRescatar()) {
            c();
        } else {
            d();
        }
    }

    private void c() {
        this.btnContinuar.setEnabled(true);
        this.btnContinuar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
    }

    private void d() {
        this.btnContinuar.setEnabled(false);
        this.btnContinuar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
    }

    public void setActionBarRescatar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        e();
    }

    private void e() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    RescatarFondoActivity.this.f();
                    RescatarFondoActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void f() {
        finish();
    }

    public boolean isValidDataRescatar() {
        return (TextUtils.isEmpty(this.inpAmount.getText()) || this.v == null || this.y == null) ? false : true;
    }

    public void setSelectedAmountTypeTotal() {
        this.lblSelectedAmountType.setText(getString(R.string.F24_30_SELECTED_AMOUNT_TOTAL));
        this.inpAmount.setPrefix(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.v.getMoneda()).toString()));
        this.inpAmount.setText(this.v.getImporte());
        this.inpAmount.setEnabled(false);
        this.rowPlazoPago.setVisibility(0);
    }

    public void setSelectedAmountTypeOtro() {
        this.lblSelectedAmountType.setText(getString(R.string.F24_30_SELECTED_AMOUNT_OTRO));
        this.inpAmount.setPrefix(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.v.getMoneda()).toString()));
        this.inpAmount.setText("");
        this.inpAmount.setEnabled(true);
    }

    public void setSelectedAmountTypeCuotapartes() {
        this.lblSelectedAmountType.setText(getString(R.string.F24_30_SELECTED_AMOUNT_CUOTAPARTES));
        this.inpAmount.setPrefix("");
        this.inpAmount.setText(this.v.getCantidadCuotapartes());
        this.inpAmount.setEnabled(true);
        this.rowPlazoPago.setVisibility(8);
    }

    public void setSelectedAccount(CuentaFondosBean cuentaFondosBean) {
        this.x = cuentaFondosBean;
        TextView textView = this.lblDataCuentaTitulo;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.F24_21_lbl_cuentaTitulo));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(UtilAccount.formatCuentaTitulo(this.x.getNumero()));
        textView.setText(sb.toString());
        try {
            TextView textView2 = this.lblDataCuentaTitulo;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getString(R.string.F24_21_lbl_cuentaTitulo));
            sb2.append(new CAccessibility(getContext()).applyFilterCharacterToCharacter(this.lblDataCuentaTitulo.getText().toString()));
            textView2.setContentDescription(sb2.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.x.getListaFondos() == null || this.x.getListaFondos().getFondosBean().size() <= 0) {
            this.v = null;
            this.lblDataFondo.setText(getString(R.string.F24_20_lbl_data_seleccionar));
            this.lblDataFondo.setTextColor(getResources().getColor(R.color.grey_medium_light));
            this.lblDataFondo.setEnabled(false);
        } else {
            setSelectedFondo((FondoBean) this.x.getListaFondos().getFondosBean().get(0));
        }
        resetSelectedDestinationAccount();
        b();
    }

    public void setSelectedFondo(FondoBean fondoBean) {
        this.v = fondoBean;
        this.lblDataFondo.setText(Html.fromHtml(fondoBean.getNombre().toString()));
        try {
            this.lblDataFondo.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataFondo.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.lblDataFondo.setTextColor(getResources().getColor(R.color.red_light));
        this.lblDataFondo.setEnabled(true);
        this.inpAmount.setPrefix(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.v.getMoneda()).toString()));
        this.inpAmount.setText(this.v.getImporte());
        TextView textView = this.lblDataImporteTotal;
        StringBuilder sb = new StringBuilder();
        sb.append(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.v.getMoneda()).toString()));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.v.getImporte());
        textView.setText(sb.toString());
        try {
            TextView textView2 = this.lblDataImporteTotal;
            CAccessibility cAccessibility = new CAccessibility(getContext());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.v.getMoneda()).toString()));
            sb2.append(this.v.getImporte());
            textView2.setContentDescription(cAccessibility.applyFilterAmount(sb2.toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.lblDataCuotapartes.setText(this.v.getCantidadCuotapartes());
        this.lblDataValorCuotapartes.setText(this.v.getValorCuotaParte());
        this.lblDataPlazoPago.setText(Html.fromHtml(this.v.getPlazoPago()));
        try {
            this.lblDataPlazoPago.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblDataPlazoPago.getText().toString()));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        resetSelectedDestinationAccount();
    }

    public void gotoConfirmacion(RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean) {
        this.p.trackScreen(getString(R.string.analytics_screen_pantalla_confirmacion_rescate_fondo));
        gotoPage(1);
        this.s.onCreatePage(rescatarFondoBodyResponseBean);
    }

    public void setConfirmarRescateFondoView(RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean) {
        this.E = rescatarFondoBodyResponseBean;
        setActionBarConfirmarRescate();
        if (this.lblSelectedAmountType.getText().toString().equalsIgnoreCase(getString(R.string.F24_30_SELECTED_AMOUNT_OTRO))) {
            this.p.trackScreen(getString(R.string.analytics_screen_pantalla_rescate_de_otro_importe));
        }
        this.lblDataConfirmarFondoRescatar.setText(Html.fromHtml(this.v.getNombre()));
        try {
            this.lblDataConfirmarFondoRescatar.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataConfirmarFondoRescatar.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.lblSelectedAmountType.getText().toString().equalsIgnoreCase(getString(R.string.F24_30_SELECTED_AMOUNT_CUOTAPARTES))) {
            this.lblDataConfirmarImporte.setText(rescatarFondoBodyResponseBean.getImporteRescate());
            try {
                this.lblDataConfirmarImporte.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(rescatarFondoBodyResponseBean.getImporteRescate()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.lblDataConfirmarCuotaparte.setText(rescatarFondoBodyResponseBean.getCantCuotaParte());
        } else {
            this.lblDataConfirmarImporte.setText(rescatarFondoBodyResponseBean.getImporteRescate());
            try {
                this.lblDataConfirmarImporte.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(rescatarFondoBodyResponseBean.getImporteRescate()));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            this.lblDataConfirmarCuotaparte.setText(rescatarFondoBodyResponseBean.getCantCuotaParte());
        }
        this.lblDataConfirmarValorCuotaparte.setText(this.v.getValorCuotaParte());
        try {
            this.lblDataConfirmarValorCuotaparte.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(this.lblDataConfirmarValorCuotaparte.getText().toString()));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        this.rowValorCuotapartesBottomConfirmar.setVisibility(8);
        this.lblDataConfirmarCuentaTitulo.setText(UtilAccount.formatCuentaTitulo(this.x.getNumero()));
        try {
            this.lblDataConfirmarCuentaTitulo.setContentDescription(new CAccessibility(getContext()).applyFilterCharacterToCharacter(this.lblDataConfirmarCuentaTitulo.getText().toString()));
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        this.lblDataConfirmarFondo.setText(Html.fromHtml(this.v.getNombre()));
        try {
            this.lblDataConfirmarFondo.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataConfirmarFondo.getText().toString()));
        } catch (Exception e6) {
            e6.printStackTrace();
        }
        this.lblDataConfirmarCuentaDestino.setText(CAccounts.getInstance(this.sessionManager).getAbrevAccount(this.y));
        try {
            this.lblDataConfirmarCuentaDestino.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(CAccounts.getInstance(this.sessionManager).getAbrevAccount(this.y)));
        } catch (Exception e7) {
            e7.printStackTrace();
        }
        this.lblDataConfirmarPlazoPago.setText(Html.fromHtml(this.v.getPlazoPago()));
        try {
            this.lblDataConfirmarPlazoPago.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(Html.fromHtml(this.v.getPlazoPago()).toString()));
        } catch (Exception e8) {
            e8.printStackTrace();
        }
        this.imgCheckboxTyC.setVisibility(8);
        this.btnConfirmar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
        this.lblConfirmarTerminosLegales.setText(Html.fromHtml(b(rescatarFondoBodyResponseBean.getLegales().getLeyendaLegales())));
        try {
            this.lblConfirmarTerminosLegales.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblConfirmarTerminosLegales.getText().toString()));
        } catch (Exception e9) {
            e9.printStackTrace();
        }
        this.btnConfirmar.setOnClickListener(this);
        this.lblConectorVerTyC.setText(getString(R.string.F24_22_BTN_TERCONDIC_VER));
        this.lblVerTyC.setOnClickListener(this);
    }

    private String b(List<String> list) {
        String str = "";
        for (String concat : list) {
            str = str.concat(concat).concat("<br>");
        }
        return str;
    }

    public void setActionBarConfirmarRescate() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        g();
    }

    private void g() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    RescatarFondoActivity.this.h();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void h() {
        gotoPage(0, false);
        setActionBarRescatar();
    }

    public void gotoFueraHorarioFondo(String str) {
        gotoPage(3);
        this.t.onCreatePage(str);
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
        i();
    }

    private void i() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    RescatarFondoActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonFueraHorario() {
        gotoPage(0, false);
        setActionBarRescatar();
    }

    public void gotoComprobante(RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean) {
        gotoPage(2);
        this.u.onCreatePage(rescatarFondoBodyResponseBean);
    }

    public void setComprobanteRescateFondoView(RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean) {
        this.E = rescatarFondoBodyResponseBean;
        setActionBarComprobanteRescate();
        if (this.lblSelectedAmountType.getText().toString().equalsIgnoreCase(getString(R.string.F24_30_SELECTED_AMOUNT_TOTAL))) {
            this.p.trackScreen(getString(R.string.analytics_screen_pantalla_comprobante_rescate_fondo));
        } else {
            this.p.trackScreen(getString(R.string.analytics_screen_vista_otro_importe_comprobante_rescate));
        }
        this.lblDataComprobanteFondoRescatar.setText(Html.fromHtml(this.v.getNombre()));
        try {
            this.lblDataComprobanteFondoRescatar.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataComprobanteFondoRescatar.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.rowLinearFondo.setVisibility(8);
        this.rowLinearCuentaDestino.setVisibility(8);
        this.lblDataComprobanteImporte.setText(rescatarFondoBodyResponseBean.getImporteRescate());
        try {
            this.lblDataComprobanteImporte.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(rescatarFondoBodyResponseBean.getImporteRescate()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.lblDataComprobanteImporteBottom.setText(Html.fromHtml(rescatarFondoBodyResponseBean.getImporteRescate()));
        try {
            this.lblDataComprobanteImporteBottom.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblDataComprobanteImporteBottom.getText().toString()));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        this.lblDataComprobanteCuotaparte.setText(rescatarFondoBodyResponseBean.getCantCuotaParte());
        this.lblDataComprobanteImporteBottom.setText(rescatarFondoBodyResponseBean.getImporteRescate());
        this.lblDataComprobanteCuotapartesBottom.setText(rescatarFondoBodyResponseBean.getCantCuotaParte());
        try {
            this.lblDataComprobanteCuotapartesBottom.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(rescatarFondoBodyResponseBean.getCantCuotaParte()));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        this.lblDataComprobanteValorCuotaparte.setText(this.v.getValorCuotaParte());
        try {
            this.lblDataComprobanteValorCuotaparte.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(this.v.getValorCuotaParte()));
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        TextView textView = this.lblDataComprobanteFechaHora;
        StringBuilder sb = new StringBuilder();
        sb.append(rescatarFondoBodyResponseBean.getFecha());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(rescatarFondoBodyResponseBean.getHora());
        textView.setText(sb.toString());
        try {
            TextView textView2 = this.lblDataComprobanteFechaHora;
            CAccessibility cAccessibility = new CAccessibility(getContext());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(rescatarFondoBodyResponseBean.getFecha());
            sb2.append(UtilsCuentas.SEPARAOR2);
            sb2.append(new CAccessibility(getContext()).applyFilterTime(rescatarFondoBodyResponseBean.getHora()));
            textView2.setContentDescription(cAccessibility.applyFilterDate(sb2.toString()));
        } catch (Exception e6) {
            e6.printStackTrace();
        }
        this.rowCuentaDebitoComprobante.setVisibility(8);
        this.lblDataComprobanteCuentaTitulo.setText(UtilAccount.formatCuentaTitulo(this.x.getNumero()));
        try {
            this.lblDataComprobanteCuentaTitulo.setContentDescription(new CAccessibility(getContext()).applyFilterCharacterToCharacter(this.lblDataComprobanteCuentaTitulo.getText().toString()));
        } catch (Exception e7) {
            e7.printStackTrace();
        }
        this.lblDataComprobanteFondo.setText(Html.fromHtml(this.v.getNombre()));
        try {
            this.lblDataComprobanteFondo.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblDataComprobanteFondo.getText().toString()));
        } catch (Exception e8) {
            e8.printStackTrace();
        }
        this.lblDataComprobanteCuentaDestino.setText(CAccounts.getInstance(this.sessionManager).getAbrevAccount(this.y));
        try {
            this.lblDataComprobanteCuentaDestino.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblDataComprobanteCuentaDestino.getText().toString()));
        } catch (Exception e9) {
            e9.printStackTrace();
        }
        try {
            this.lblDataPlazodePago.setText(Html.fromHtml(rescatarFondoBodyResponseBean.getPlazoPago()));
            this.lblDataPlazodePago.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblDataPlazodePago.getText().toString()));
        } catch (Exception e10) {
            e10.printStackTrace();
        }
        this.rowImporteTop.setVisibility(8);
        this.rowValorCuotapartesTop.setVisibility(8);
        this.rowValorCuotaparteComprobante.setVisibility(8);
        try {
            this.lblCertificadoComprobante.setContentDescription(new CAccessibility(getContext()).applyFilterControlNumber(getResources().getString(R.string.ID_3932_FONDOS_LBL_NRO_CERTIFICADO)));
        } catch (Exception e11) {
            e11.printStackTrace();
        }
        this.lblDataComprobanteCertificado.setText(rescatarFondoBodyResponseBean.getNroCertificado());
        try {
            this.lblDataComprobanteCertificado.setContentDescription(new CAccessibility(getContext()).applyFilterCharacterToCharacter(rescatarFondoBodyResponseBean.getNroCertificado()));
        } catch (Exception e12) {
            e12.printStackTrace();
        }
        this.lblComprobanteTerminosLegales.setText(Html.fromHtml(b(rescatarFondoBodyResponseBean.getLegales().getLeyendaLegales())));
        try {
            this.lblComprobanteTerminosLegales.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblComprobanteTerminosLegales.getText().toString()));
        } catch (Exception e13) {
            e13.printStackTrace();
        }
        this.btnVolver.setOnClickListener(this);
        String nroCertificado = rescatarFondoBodyResponseBean.getNroCertificado();
        String fecha = rescatarFondoBodyResponseBean.getFecha();
        String hora = rescatarFondoBodyResponseBean.getHora();
        AnalyticsManager analyticsManager = this.p;
        String string = getString(R.string.analytics_transaction_hits_fondos);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(nroCertificado);
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(fecha);
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(hora);
        analyticsManager.trackTransaction(string, sb3.toString());
    }

    public void setActionBarComprobanteRescate() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(false);
        j();
    }

    private void j() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        ImageView imageView2 = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.menu);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    RescatarFondoActivity.this.switchDrawer();
                }
            });
        }
        k();
        if (imageView2 != null) {
            imageView2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    RescatarFondoActivity.this.B.show(RescatarFondoActivity.this.getSupportFragmentManager(), "Dialog");
                }
            });
        }
    }

    private void k() {
        this.C = l();
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        this.B = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_TITLE), null, arrayList, getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL), null, null, null, arrayList);
        this.B.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(RescatarFondoActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                    RescatarFondoActivity.this.C.optionShareSelected();
                } else if (str.equalsIgnoreCase(RescatarFondoActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                    RescatarFondoActivity.this.C.optionDownloadSelected();
                }
            }
        });
        this.B.setCancelable(true);
    }

    private OptionsToShare l() {
        return new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public View getViewToShare() {
                return RescatarFondoActivity.this.comprobanteOperacion;
            }

            public void receiveIntentAppShare(Intent intent) {
                RescatarFondoActivity.this.startActivity(Intent.createChooser(intent, RescatarFondoActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
            }

            public String getFileName() {
                return RescatarFondoActivity.this.getString(R.string.F24_22_COMPROBANTE_RESCATE_FILENAME).concat(RescatarFondoActivity.this.E.getNroCertificado());
            }

            public String getSubjectReceiptToShare() {
                return RescatarFondoActivity.this.getString(R.string.F24_22_COMPROBANTE_RESCATE_SUBJECT);
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                RescatarFondoActivity.this.D = true;
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                RescatarFondoActivity.this.D = true;
            }

            public void onAbortShare() {
                super.onAbortShare();
                RescatarFondoActivity.this.D = true;
                RescatarFondoActivity.this.onBackPressed();
            }
        };
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.C.onRequestPermissionsResult(i, strArr, iArr);
    }

    public boolean canExit(int i) {
        if (!this.D) {
            final int i2 = i;
            AnonymousClass9 r1 = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
                public View getViewToShare() {
                    return RescatarFondoActivity.this.comprobanteOperacion;
                }

                public void receiveIntentAppShare(Intent intent) {
                    RescatarFondoActivity.this.startActivity(Intent.createChooser(intent, RescatarFondoActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
                }

                public String getFileName() {
                    return RescatarFondoActivity.this.getString(R.string.F24_22_COMPROBANTE_RESCATE_FILENAME).concat(RescatarFondoActivity.this.E.getNroCertificado());
                }

                public String getSubjectReceiptToShare() {
                    return RescatarFondoActivity.this.getString(R.string.F24_22_COMPROBANTE_RESCATE_SUBJECT);
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    RescatarFondoActivity.this.D = true;
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    RescatarFondoActivity.this.D = true;
                }

                public void onAbortShare() {
                    super.onAbortShare();
                    RescatarFondoActivity.this.D = true;
                    RescatarFondoActivity.this.onClickItem(i2);
                }
            };
            r1.showAlert();
        }
        return this.D;
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
            }
            this.mControlPager.setDisplayedChild(i);
            attachView();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.F24_20_RLL_SELECTED_AMOUNT_TYPE /*2131363098*/:
                this.p.trackScreen(getString(R.string.analytics_screen_pantalla_opciones_indicador_de_importe_rescate));
                this.p.trackEvent(getString(R.string.analytics_event_category_fondos), getString(R.string.analytics_event_action_seleccion_importe), getString(R.string.analytics_event_label_importeTotal_otroImporte));
                this.r.showAmountTypeDialog(this.lblSelectedAmountType);
                return;
            case R.id.F24_20_btn_continuar /*2131363099*/:
                this.r.gotoConfirmar(this.x, this.v, this.inpAmount.getFormatedText().toString(), this.y);
                return;
            case R.id.F24_20_lbl_data_origen /*2131363109*/:
                String str = "";
                if (this.v != null) {
                    if (this.v.getMoneda().equalsIgnoreCase("0")) {
                        str = Constants.SYMBOL_CURRENCY_PESOS;
                    } else if (this.v.getMoneda().equalsIgnoreCase("2")) {
                        str = Constants.SYMBOL_CURRENCY_DOLAR;
                    } else {
                        str = UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.v.getMoneda()).toString());
                    }
                }
                this.r.showSelectDestinationAccountDialog(this.y, this.z, str, this.F);
                return;
            case R.id.F24_21_btn_confirmar /*2131363129*/:
                this.s.gotoComprobante(this.x, this.v, this.inpAmount.getFormatedText().toString(), this.E, this.y, this.lblSelectedAmountType.getText().toString());
                return;
            case R.id.F24_21_lbl_verTerminos /*2131363152*/:
                this.s.showTyC(this.E.getTermCondiciones());
                return;
            case R.id.F24_22_btn_volver /*2131363167*/:
                onBackPressed();
                return;
            default:
                return;
        }
    }

    public void resetSelectedDestinationAccount() {
        this.y = null;
        this.lblDataDestino.setText(getString(R.string.F24_20_lbl_data_seleccionar));
        this.lblDataDestino.setContentDescription(this.lblDataDestino.getText().toString());
    }

    public void setSelectedDestinationAccount(Cuenta cuenta) {
        this.y = cuenta;
        String abrevAccount = CAccounts.getInstance(this.sessionManager).getAbrevAccount(this.y);
        this.lblDataDestino.setText(abrevAccount);
        try {
            this.lblDataDestino.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(abrevAccount));
        } catch (Exception e) {
            e.printStackTrace();
        }
        b();
    }

    public void attachView() {
        switch (this.mControlPager.getDisplayedChild()) {
            case 0:
                if (!this.r.isViewAttached()) {
                    this.r.attachView(this);
                    return;
                }
                return;
            case 1:
                if (!this.s.isViewAttached()) {
                    this.s.attachView(this);
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
                if (!this.t.isViewAttached()) {
                    this.t.attachView(this);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void detachView() {
        if (this.r.isViewAttached()) {
            this.r.detachView();
        }
        if (this.s.isViewAttached()) {
            this.s.detachView();
        }
        if (this.t.isViewAttached()) {
            this.t.detachView();
        }
        if (this.u.isViewAttached()) {
            this.u.detachView();
        }
    }

    public void onBackPressed() {
        int id2 = this.mControlPager.getCurrentView().getId();
        if (id2 != R.id.comprobante_rescatar_fondo) {
            if (id2 == R.id.confirmar_rescatar_fondo) {
                h();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            } else if (id2 == R.id.out_working_hours_fondo) {
                backButtonFueraHorario();
            } else if (id2 == R.id.rescatar_fondo) {
                f();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        } else if (!this.D) {
            this.C.showAlert();
        } else {
            Intent intent = new Intent();
            intent.putExtra(FondosConstants.INTENT_EXTRA_RECARGAR_FONDOS, true);
            intent.putExtra("CUENTA", this.x);
            setResult(-1, intent);
            super.onBackPressed();
            hideKeyboard();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    public void onDestroy() {
        detachView();
        super.onDestroy();
    }

    public String getDestinoDeCuentaOpEnLista(String str) {
        CuentaOperativaBean a = a(str);
        return a != null ? a.getDescCtaDestino() : "";
    }

    private CuentaOperativaBean a(String str) {
        Iterator it = this.F.iterator();
        while (it.hasNext()) {
            CuentaOperativaBean cuentaOperativaBean = (CuentaOperativaBean) it.next();
            if (str.equalsIgnoreCase(cuentaOperativaBean.getDescCtaDebito())) {
                return cuentaOperativaBean;
            }
        }
        return null;
    }

    public List<CuentaOperativaBean> getCuentasValidas(List<CuentaOperativaBean> list) {
        ArrayList arrayList = new ArrayList();
        String simbolCurrencyFromDescription = UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.v.getMoneda()).toString());
        for (CuentaOperativaBean cuentaOperativaBean : list) {
            if (TextUtils.isEmpty(simbolCurrencyFromDescription) || cuentaOperativaBean.getTipoCta().equalsIgnoreCase("02") || UtilAccount.getCurrencyOfAccount(getSessionManager(), cuentaOperativaBean).equalsIgnoreCase(simbolCurrencyFromDescription)) {
                arrayList.add(cuentaOperativaBean);
            }
        }
        return arrayList;
    }
}
