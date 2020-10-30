package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.funds.ComprobanteSuscripcionFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.ComprobanteSuscripcionFondoView;
import ar.com.santander.rio.mbanking.app.module.funds.ConfirmarSuscripcionFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.ConfirmarSuscripcionFondoView;
import ar.com.santander.rio.mbanking.app.module.funds.EvaluacionRiesgoSuscribirFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.EvaluacionRiesgoSuscribirFondoView;
import ar.com.santander.rio.mbanking.app.module.funds.FueraHorarioFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.FueraHorarioFondoView;
import ar.com.santander.rio.mbanking.app.module.funds.SuscribirFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.SuscribirFondoView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants.LeyendasLegales;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener.OneClicked;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AlertaEvaluacionRiesgoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaOperativaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DisclaimerFondo;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SimularSuscripcionFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscribirFondoBodyResponseBean;
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

public class SuscribirFondoActivity extends MvpPrivateMenuActivity implements OnClickListener, ComprobanteSuscripcionFondoView, ConfirmarSuscripcionFondoView, EvaluacionRiesgoSuscribirFondoView, FueraHorarioFondoView, SuscribirFondoView {
    /* access modifiers changed from: private */
    public Cuenta A;
    private ArrayList<Cuenta> B;
    private ArrayList<CuentaOperativaBean> C;
    private ArrayList<CuentaFondosBean> D;
    /* access modifiers changed from: private */
    public SimularSuscripcionFondoBodyResponseBean E;
    /* access modifiers changed from: private */
    public SuscribirFondoBodyResponseBean F;
    private ArrayList<Leyenda> G;
    /* access modifiers changed from: private */
    public IsbanDialogFragment H;
    /* access modifiers changed from: private */
    public OptionsToShare I;
    /* access modifiers changed from: private */
    public boolean J = false;
    /* access modifiers changed from: private */
    public String K;
    private String L;
    @InjectView(2131363318)
    TextView btnAceptarRiesgo;
    @InjectView(2131363129)
    Button btnConfirmarSuscripcion;
    @InjectView(2131363099)
    Button btnContinuar;
    @InjectView(2131363319)
    TextView btnNoAceptaRiesgo;
    @InjectView(2131363167)
    Button btnVolverComprobante;
    @InjectView(2131363209)
    ScrollView comprobanteOperacion;
    @InjectView(2131363100)
    ImageView imgCheckBoxValidar;
    @InjectView(2131363130)
    ImageView imgCheckboxReglamento;
    @InjectView(2131363094)
    ImageView imgImportre;
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
    TextView lblDataCertificadoComprobante;
    @InjectView(2131363104)
    TextView lblDataCuentaTitulo;
    @InjectView(2131363105)
    TextView lblDataCuentaTituloBottom;
    @InjectView(2131363176)
    TextView lblDataCuentaTituloComprobante;
    @InjectView(2131363137)
    TextView lblDataCuentaTituloConfirmar;
    @InjectView(2131363179)
    TextView lblDataCuotaparteBottomComprobante;
    @InjectView(2131363138)
    TextView lblDataCuotaparteConfirmar;
    @InjectView(2131363178)
    TextView lblDataCuotapartesComprobante;
    @InjectView(2131363180)
    TextView lblDataFechaHoraComprobante;
    @InjectView(2131363107)
    TextView lblDataFondo;
    @InjectView(2131363139)
    TextView lblDataFondoConfirmar;
    @InjectView(2131363140)
    TextView lblDataFondoSuscribirConfirmar;
    @InjectView(2131363182)
    TextView lblDataImporteBottomComprobante;
    @InjectView(2131363183)
    TextView lblDataImporteComprobante;
    @InjectView(2131363141)
    TextView lblDataImporteConfirmar;
    @InjectView(2131363320)
    TextView lblDataInfoRiesgo;
    @InjectView(2131363109)
    TextView lblDataOrigen;
    @InjectView(2131363177)
    TextView lblDataOrigenComprobante;
    @InjectView(2131363142)
    TextView lblDataOrigenConfirmar;
    @InjectView(2131363185)
    TextView lblDataValorCuotaparteBottomComprobante;
    @InjectView(2131363186)
    TextView lblDataValorCuotaparteComprobante;
    @InjectView(2131363144)
    TextView lblDataValorCuotaparteConfirmar;
    @InjectView(2131363111)
    TextView lblDataValorCuotapartes;
    @InjectView(2131363181)
    TextView lblDatafondoComprobante;
    @InjectView(2131364053)
    TextView lblFueraHorarioMensaje;
    @InjectView(2131363096)
    TextView lblSelectedAmountType;
    @InjectView(2131363196)
    TextView lblTextValorcuotaparte;
    @InjectView(2131363150)
    TextView lblTitleConfirmarSuscripcion;
    @InjectView(2131363097)
    TextView lblTitlefondo;
    @InjectView(2131363135)
    TextView lblTxtCuotaparte;
    @InjectView(2131363172)
    TextView lblTxtCuotapartesComprobante;
    @InjectView(2131363153)
    TextView lblVerReglamento;
    @InjectView(2131363152)
    TextView lblVerTyC;
    @InjectView(2131363203)
    LinearLayout lbltextCuotapartes;
    @InjectView(2131363208)
    LinearLayout lbltextValorCuotapartes;
    @InjectView(2131363114)
    TextView leyendaLegal;
    @InjectView(2131364138)
    ViewFlipper mControlPager;
    @Inject
    AnalyticsManager p;
    Boolean q = Boolean.valueOf(false);
    Boolean r = Boolean.valueOf(false);
    @InjectView(2131363157)
    RelativeLayout rlTerminosYCondiciones;
    @InjectView(2131363098)
    RelativeLayout rllSelectedAmountType;
    @InjectView(2131363200)
    LinearLayout rowCuentaDestinoComprobante;
    @InjectView(2131363159)
    LinearLayout rowCuentaDestinoConfirmar;
    @InjectView(2131363121)
    LinearLayout rowCuentaTituloBottom;
    @InjectView(2131363122)
    LinearLayout rowCuotaPartes;
    @InjectView(2131363124)
    LinearLayout rowImporteTotal;
    @InjectView(2131363163)
    LinearLayout rowPlazoDePago;
    @InjectView(2131363126)
    LinearLayout rowPlazoPago;
    @InjectView(2131363207)
    LinearLayout rowPlazoPagoComprobante;
    @InjectView(2131363118)
    LinearLayout rowSelectorCuentasSuscribir;
    @InjectView(2131363127)
    LinearLayout rowValorCuotaparte;
    @InjectView(2131363164)
    LinearLayout rowValorCuotaparteConfirmar;
    private SuscribirFondoPresenter s;
    @InjectView(2131363210)
    View separatorSectionComprobante;
    @InjectView(2131363165)
    View separatorTexto;
    private ConfirmarSuscripcionFondoPresenter t;
    @InjectView(2131363194)
    TextView txtTitleComprobante;
    @InjectView(2131363189)
    TextView txtTitleFondoSuscribir;
    @InjectView(2131363151)
    TextView txtValorCuotaparte;
    /* access modifiers changed from: private */
    public EvaluacionRiesgoSuscribirFondoPresenter u;
    private FueraHorarioFondoPresenter v;
    @InjectView(2131363128)
    CustomSpinner vgSelectorAccount;
    private ComprobanteSuscripcionFondoPresenter w;
    /* access modifiers changed from: private */
    public FondoBean x;
    /* access modifiers changed from: private */
    public CuentaFondosBean y;
    private CuentaOperativaBean z;

    public void clearScreenData() {
    }

    public void configureActionBar() {
    }

    public Context getContext() {
        return this;
    }

    public int getMainLayout() {
        return R.layout.activity_suscribir_fondo;
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
        ButterKnife.inject((Activity) this);
        initialize();
    }

    public void initialize() {
        this.s = new SuscribirFondoPresenter(this.mBus, this.mDataManager);
        this.t = new ConfirmarSuscripcionFondoPresenter(this.mBus, this.mDataManager);
        this.u = new EvaluacionRiesgoSuscribirFondoPresenter(this.mBus, this.mDataManager);
        this.v = new FueraHorarioFondoPresenter(this.mBus);
        this.w = new ComprobanteSuscripcionFondoPresenter(this.mBus, this.mDataManager);
        this.L = getIntent().getStringExtra("ORIGEN");
        this.K = getIntent().getStringExtra(FondosConstants.INTENT_EXTRA_ACCION);
        this.D = getIntent().getParcelableArrayListExtra("CUENTAS");
        this.y = (CuentaFondosBean) getIntent().getParcelableExtra("CUENTA");
        this.C = getIntent().getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_CUENTAS_OPERATIVAS);
        this.x = (FondoBean) getIntent().getParcelableExtra(FondosConstants.INTENT_EXTRA_FONDO);
        this.G = getIntent().getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LEGALES);
        setSelectedAccount(this.y);
        String stringExtra = getIntent().getStringExtra(FondosConstants.INTENT_EXTRA_CONTRATO);
        this.B = a(CAccounts.getInstance(getSessionManager()).getListAccountsUserFromSession());
        this.q = Boolean.valueOf(!TextUtils.isEmpty(stringExtra) && !stringExtra.equalsIgnoreCase("0"));
        goToFondoSeleccionado();
    }

    private ArrayList<Cuenta> a(List<Cuenta> list) {
        ArrayList<Cuenta> arrayList = new ArrayList<>();
        String str = "";
        if (this.x != null) {
            if (this.x.getMoneda().equalsIgnoreCase("0")) {
                str = Constants.SYMBOL_CURRENCY_PESOS;
            } else if (this.x.getMoneda().equalsIgnoreCase("2")) {
                str = Constants.SYMBOL_CURRENCY_DOLAR;
            } else {
                str = UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.x.getMoneda()).toString());
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

    public void setSelectedAccount(CuentaFondosBean cuentaFondosBean) {
        this.y = cuentaFondosBean;
        String string = getString(R.string.F24_21_lbl_cuentaTitulo);
        String numero = this.y.getNumero();
        TextView textView = this.lblDataCuentaTitulo;
        StringBuilder sb = new StringBuilder();
        sb.append(string);
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(UtilAccount.formatCuentaTitulo(numero));
        textView.setText(sb.toString());
        try {
            TextView textView2 = this.lblDataCuentaTitulo;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(string);
            sb2.append(new CAccessibility(getContext()).applyFilterCharacterToCharacter(UtilAccount.formatCuentaTitulo(numero)));
            textView2.setContentDescription(sb2.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToFondoSeleccionado() {
        gotoPage(0);
        this.s.onCreatePage();
    }

    public void setActionBarSuscribir() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        b();
    }

    private void b() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SuscribirFondoActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonSuscribir() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
            hideKeyboard();
        }
    }

    public void setFondoSeleccionadoView() {
        setActionBarSuscribir();
        if (this.G == null || this.G.size() <= 0) {
            this.leyendaLegal.setText("");
        } else {
            for (int i = 0; i < this.G.size(); i++) {
                if (((Leyenda) this.G.get(i)).getIdLeyenda().equals(LeyendasLegales.SIMULACION)) {
                    this.leyendaLegal.setText(Html.fromHtml(((Leyenda) this.G.get(i)).getDescripcion()));
                }
            }
        }
        try {
            this.leyendaLegal.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.leyendaLegal.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.L.equalsIgnoreCase(FondosConstants.ORIGEN_TENENCIAS)) {
            this.lblDataFondo.setText(Html.fromHtml(this.x.getNombre().toString()));
            try {
                this.lblDataFondo.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataFondo.getText().toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.lblDataFondo.setOnClickListener(this);
            this.lblDataFondo.setTextColor(getResources().getColor(R.color.generic_selectable));
        } else {
            this.lblDataFondo.setText(Html.fromHtml(this.x.getNombre()).toString());
            try {
                this.lblDataFondo.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataFondo.getText().toString()));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (this.K.equalsIgnoreCase(FondosConstants.ACCION_SUSCRIBIR_CUOTAPARTES)) {
            this.p.trackScreen(getString(R.string.analytics_screen_pantalla_suscripcion_cuotapartes));
            this.rowSelectorCuentasSuscribir.setVisibility(8);
            this.lblTitlefondo.setText(getString(R.string.F24_40_LBL_TITLE_SUSCRIBIR_MAS));
            this.lblSelectedAmountType.setText(getString(R.string.F24_40_LBL_IMPORTE));
            this.imgImportre.setVisibility(8);
            String formatCuentaTitulo = UtilAccount.formatCuentaTitulo(this.y.getNumero());
            this.lblDataCuentaTituloBottom.setText(formatCuentaTitulo);
            try {
                this.lblDataCuentaTituloBottom.setContentDescription(new CAccessibility(getContext()).applyFilterCharacterToCharacter(formatCuentaTitulo));
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            this.rowImporteTotal.setVisibility(8);
            this.rowCuotaPartes.setVisibility(8);
            String valorCuotaParte = this.x.getValorCuotaParte();
            this.lblDataValorCuotapartes.setText(valorCuotaParte);
            try {
                this.lblDataValorCuotapartes.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(valorCuotaParte));
            } catch (Exception e5) {
                e5.printStackTrace();
            }
            this.rowPlazoPago.setVisibility(8);
            this.rowValorCuotaparte.setVisibility(8);
        } else {
            this.p.trackScreen(getString(R.string.analytics_screen_vista_Detalles_Fondo));
            this.imgImportre.setVisibility(8);
            this.vgSelectorAccount.setOnClickListener(this);
            if (this.D == null || c() <= 1) {
                this.vgSelectorAccount.setVisibility(8);
            } else {
                this.vgSelectorAccount.setVisibility(0);
            }
            setSelectedAccount(this.y);
            this.rllSelectedAmountType.setOnClickListener(this);
            this.lblTitlefondo.setText(getString(R.string.F24_50_LBL_TITLE_SUSCRIBIR_FONDO));
            this.lblSelectedAmountType.setText(getString(R.string.F24_41_SELECTED_AMOUNT_TOTAL));
            this.rowCuentaTituloBottom.setVisibility(8);
            this.rowImporteTotal.setVisibility(8);
            this.rowCuotaPartes.setVisibility(8);
            this.lblDataValorCuotapartes.setText(this.x.getValorCuotaParte());
            this.rowPlazoPago.setVisibility(8);
            this.rowValorCuotaparte.setVisibility(8);
        }
        List cuentasValidas = getCuentasValidas(this.C);
        if (cuentasValidas == null || cuentasValidas.size() == 0) {
            this.lblDataOrigen.setText(getString(R.string.F24_20_lbl_data_seleccionar));
            this.lblDataOrigen.setTextColor(getResources().getColor(R.color.grey_medium_light));
        } else if (cuentasValidas.size() > 1) {
            this.lblDataOrigen.setText(getString(R.string.F24_20_lbl_data_seleccionar));
            this.lblDataOrigen.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/OpenSans-Semibold.otf"));
            this.lblDataOrigen.setTextColor(getResources().getColor(R.color.generic_black));
            this.lblDataOrigen.setTextSize(13.0f);
            this.lblDataOrigen.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
            this.lblDataOrigen.setOnClickListener(this);
        } else if (cuentasValidas.size() == 1) {
            this.lblDataOrigen.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/OpenSans-Semibold.otf"));
            this.lblDataOrigen.setTextColor(getResources().getColor(R.color.generic_black));
            this.lblDataOrigen.setTextSize(13.0f);
            setSelectedDestinationAccount(new Cuenta(((CuentaOperativaBean) cuentasValidas.get(0)).getTipoCta(), ((CuentaOperativaBean) cuentasValidas.get(0)).getNumero(), ((CuentaOperativaBean) cuentasValidas.get(0)).getSucursal()));
        }
        this.inpAmount.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                SuscribirFondoActivity.this.i();
            }
        });
        if (!d()) {
            g();
            this.imgCheckBoxValidar.setOnClickListener(this);
        } else {
            f();
        }
        setSelectedAmountTypeTotal();
    }

    private int c() {
        Iterator it = this.D.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (((CuentaFondosBean) it.next()).getTipoCuenta().equalsIgnoreCase("08")) {
                i++;
            }
        }
        return i;
    }

    private boolean d() {
        return this.q.booleanValue();
    }

    private void e() {
        if (!d()) {
            h();
        } else {
            g();
        }
    }

    private void f() {
        this.imgCheckBoxValidar.setBackground(getResources().getDrawable(R.drawable.ic_checkbox_on_gris));
        this.q = Boolean.valueOf(true);
        this.imgCheckBoxValidar.setOnClickListener(null);
        i();
    }

    private void g() {
        this.imgCheckBoxValidar.setBackground(getResources().getDrawable(R.drawable.ic_checkbox_off_gris));
        this.q = Boolean.valueOf(false);
        i();
    }

    private void h() {
        this.imgCheckBoxValidar.setBackground(getResources().getDrawable(R.drawable.ic_checkbox_on_rojo));
        this.q = Boolean.valueOf(true);
        i();
    }

    /* access modifiers changed from: private */
    public void i() {
        if (isValidDataSuscribir()) {
            k();
        } else {
            j();
        }
    }

    private void j() {
        this.btnContinuar.setEnabled(false);
        this.btnContinuar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
    }

    private void k() {
        this.btnContinuar.setEnabled(true);
        this.btnContinuar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
        this.btnContinuar.setOnClickListener(this);
    }

    public void setSelectedDestinationAccount(Cuenta cuenta) {
        this.A = cuenta;
        String abrevAccount = CAccounts.getInstance(this.sessionManager).getAbrevAccount(this.A);
        this.z = a(abrevAccount);
        TextView textView = this.lblDataOrigen;
        StringBuilder sb = new StringBuilder();
        sb.append(abrevAccount);
        sb.append("\n$");
        sb.append(this.z.getDescCtaDebito().trim().substring(abrevAccount.length() + 1));
        textView.setText(sb.toString());
        try {
            this.lblDataOrigen.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblDataOrigen.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        i();
    }

    public boolean isValidDataSuscribir() {
        return !TextUtils.isEmpty(this.inpAmount.getText()) && this.A != null && this.q.booleanValue();
    }

    public void setSelectedAmountTypeTotal() {
        this.inpAmount.setPrefix(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.x.getMoneda()).toString()));
        this.inpAmount.setText("");
    }

    public void setSelectedAmountTypeOtro() {
        this.inpAmount.setPrefix(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.x.getMoneda()).toString()));
        this.inpAmount.setText("");
    }

    public void setSelectedAmountTypeCuotapartes() {
        this.inpAmount.setPrefix("");
    }

    public void setSelectedFondo(FondoBean fondoBean) {
        this.x = fondoBean;
    }

    public void gotoConfirmarSuscripcionFondo(SimularSuscripcionFondoBodyResponseBean simularSuscripcionFondoBodyResponseBean) {
        gotoPage(1);
        this.t.onCreatePage(simularSuscripcionFondoBodyResponseBean);
    }

    public void setActionBarConfirmarSuscripcion() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        l();
    }

    private void l() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SuscribirFondoActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonConfirmar() {
        gotoPage(0, false);
        setActionBarSuscribir();
    }

    public void setConfirmarSuscripcionView(SimularSuscripcionFondoBodyResponseBean simularSuscripcionFondoBodyResponseBean) {
        this.E = simularSuscripcionFondoBodyResponseBean;
        setActionBarConfirmarSuscripcion();
        this.lblDataFondoSuscribirConfirmar.setText(Html.fromHtml(this.x.getNombre()).toString());
        try {
            this.lblDataFondoSuscribirConfirmar.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataFondoSuscribirConfirmar.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String importeSusc = simularSuscripcionFondoBodyResponseBean.getImporteSusc();
        this.lblDataImporteConfirmar.setText(importeSusc);
        try {
            this.lblDataImporteConfirmar.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(importeSusc));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        String abrevAccount = CAccounts.getInstance(this.sessionManager).getAbrevAccount(this.A);
        this.lblDataOrigenConfirmar.setText(abrevAccount);
        try {
            this.lblDataOrigenConfirmar.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(abrevAccount));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        this.lblDataCuentaTituloConfirmar.setText(UtilAccount.formatCuentaTitulo(this.y.getNumero()));
        try {
            this.lblDataCuentaTituloConfirmar.setContentDescription(new CAccessibility(getContext()).applyFilterCharacterToCharacter(this.lblDataCuentaTituloConfirmar.getText().toString()));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        this.lblDataFondoConfirmar.setText(Html.fromHtml(this.x.getNombre()));
        try {
            this.lblDataFondoConfirmar.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDataFondoConfirmar.getText().toString()));
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        this.rowCuentaDestinoConfirmar.setVisibility(8);
        this.rowPlazoDePago.setVisibility(8);
        this.lblConfirmarTerminosLegales.setText(Html.fromHtml(b(simularSuscripcionFondoBodyResponseBean.getListaLegales().getLeyendaLegales())));
        try {
            this.lblConfirmarTerminosLegales.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblConfirmarTerminosLegales.getText().toString()));
        } catch (Exception e6) {
            e6.printStackTrace();
        }
        this.lblTxtCuotaparte.setVisibility(8);
        this.lblDataCuotaparteConfirmar.setVisibility(8);
        this.separatorTexto.setVisibility(8);
        this.rowValorCuotaparteConfirmar.setVisibility(8);
        if (this.K.equalsIgnoreCase(FondosConstants.ACCION_SUSCRIBIR_CUOTAPARTES)) {
            this.p.trackScreen(getString(R.string.analytics_screen_confirmacion_suscripcion_cuotapartes));
            this.lblTitleConfirmarSuscripcion.setText(getString(R.string.F24_41_TITLE_CONFIRMAR));
        } else {
            this.p.trackScreen(getString(R.string.analytics_screen_nuevo_fondo_confirmar_suscripcion));
            this.lblTitleConfirmarSuscripcion.setText(getString(R.string.ID_3931_FONDOS_LBL_CONFIRMAR));
        }
        this.imgCheckboxReglamento.setVisibility(0);
        this.imgCheckboxReglamento.setOnClickListener(this);
        q();
        this.lblConectorVerTyC.setText(getString(R.string.F24_22_BTN_TERCONDIC_VER));
        this.lblVerTyC.setOnClickListener(this);
        if (!this.E.getReglamento().isEmpty()) {
            this.lblVerReglamento.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    String obj = Html.fromHtml(SuscribirFondoActivity.this.E.getReglamento()).toString();
                    SuscribirFondoActivity suscribirFondoActivity = SuscribirFondoActivity.this;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Reglamento - ");
                    sb.append(Html.fromHtml(SuscribirFondoActivity.this.x.getNombre()));
                    sb.append(".pdf");
                    suscribirFondoActivity.downloadFiles(obj, sb.toString());
                }
            });
        }
    }

    public void gotoEvaluacionRiesgoSuscripcionFondo(AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean, String str, String str2) {
        gotoPage(2);
        this.u.onCreatePage(alertaEvaluacionRiesgoBean, str, str2);
    }

    public void setEvaluacionRiesgoSuscripcionView(AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean, final String str, final String str2) {
        setActionBarEvaluacionRiesgoSuscripcion();
        this.lblDataInfoRiesgo.setText(Html.fromHtml(a(alertaEvaluacionRiesgoBean)));
        try {
            this.lblDataInfoRiesgo.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblDataInfoRiesgo.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.btnAceptarRiesgo.setText(getString(R.string.ID_4012_FONDOS_BTN_AVIRIESGO_SUSCRIBIR));
        this.btnAceptarRiesgo.setOnClickListener(new OneClickListener(new OneClicked() {
            public void onClicked(View view) {
                SuscribirFondoActivity.this.u.callSuscripcionFondo(SuscribirFondoActivity.this.x, SuscribirFondoActivity.this.y, SuscribirFondoActivity.this.inpAmount.getFormatedText().toString(), SuscribirFondoActivity.this.A, SuscribirFondoActivity.this.E, str, str2);
            }
        }));
        this.btnNoAceptaRiesgo.setOnClickListener(new OneClickListener(new OneClicked() {
            public void onClicked(View view) {
                SuscribirFondoActivity.this.onBackPressed();
            }
        }));
    }

    public void showConfirmDialog(final String str) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getContext().getString(R.string.F24_41_TITLE_DIALOG_CONFIRMAR), String.format(getContext().getString(R.string.F24_41_BODY_CONFIRM_DIALOG), new Object[]{this.x.getNombre()}), null, null, getContext().getString(R.string.IDX_ALERT_BTN_YES), getContext().getString(R.string.IDX_ALERT_BTN_NO), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                SuscribirFondoActivity.this.u.callSuscripcionFondo(SuscribirFondoActivity.this.x, SuscribirFondoActivity.this.y, SuscribirFondoActivity.this.inpAmount.getFormatedText().toString(), SuscribirFondoActivity.this.A, SuscribirFondoActivity.this.E, "1", str);
            }

            public void onNegativeButton() {
                if (SuscribirFondoActivity.this.K.equalsIgnoreCase(FondosConstants.ACCION_SUSCRIBIR_CUOTAPARTES)) {
                    SuscribirFondoActivity.this.getAnalyticsManager().trackEvent(SuscribirFondoActivity.this.getContext().getString(R.string.analytics_event_category_fondos), SuscribirFondoActivity.this.getContext().getString(R.string.analytics_event_action_cancelar_suscripcion_cuotapartes), SuscribirFondoActivity.this.getContext().getString(R.string.analytics_event_label_cancelacion_suscripcion_cuotapartes));
                } else {
                    SuscribirFondoActivity.this.getAnalyticsManager().trackEvent(SuscribirFondoActivity.this.getContext().getString(R.string.analytics_event_category_fondos), SuscribirFondoActivity.this.getContext().getString(R.string.analytics_event_action_cancelar_suscripcion_nuevo_fondo), SuscribirFondoActivity.this.getContext().getString(R.string.analytics_event_label_cancelacion_suscripcion_nuevo_fondo));
                }
            }
        });
        newInstance.show(getSupportFragmentManager(), "Dialog");
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

    public void setActionBarEvaluacionRiesgoSuscripcion() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        m();
    }

    private void m() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SuscribirFondoActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonEvaluacionRiesgo() {
        this.p.trackEvent(getString(R.string.analytics_event_category_fondos), getString(R.string.analytics_event_action_cancelar_suscripcion_aviso_legal), getString(R.string.analytics_event_label_cancelacion_suscripcion_aviso_legal));
        gotoPage(1, false);
        setActionBarConfirmarSuscripcion();
    }

    public void downloadFiles(final String str, final String str2) {
        showProgressIndicator("downloadFile");
        new Thread(new Runnable() {
            public void run() {
                try {
                    if (TextUtils.isEmpty(UtilFile.downloadFundFile(str2, str, SuscribirFondoActivity.this.getContext()))) {
                        new Handler(SuscribirFondoActivity.this.getContext().getMainLooper()).post(new Runnable() {
                            public void run() {
                                SuscribirFondoActivity.this.dismissProgressIndicator();
                                SuscribirFondoActivity.this.popUpErrorDownload();
                            }
                        });
                    } else {
                        new Handler(SuscribirFondoActivity.this.getContext().getMainLooper()).post(new Runnable() {
                            public void run() {
                                SuscribirFondoActivity.this.dismissProgressIndicator();
                                Toast.makeText(SuscribirFondoActivity.this.getContext(), SuscribirFondoActivity.this.getContext().getString(R.string.MSG_USER0000XX_FILE_DOWNLOAD_OK), 1).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    new Handler(SuscribirFondoActivity.this.getContext().getMainLooper()).post(new Runnable() {
                        public void run() {
                            SuscribirFondoActivity.this.dismissProgressIndicator();
                            SuscribirFondoActivity.this.popUpErrorDownload();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private boolean n() {
        return this.r.booleanValue();
    }

    private void o() {
        if (!n()) {
            p();
        } else {
            q();
        }
    }

    private void p() {
        this.imgCheckboxReglamento.setBackground(getResources().getDrawable(R.drawable.ic_checkbox_on_rojo));
        this.btnConfirmarSuscripcion.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
        this.r = Boolean.valueOf(true);
        this.btnConfirmarSuscripcion.setOnClickListener(this);
        this.btnConfirmarSuscripcion.setEnabled(true);
    }

    private void q() {
        this.imgCheckboxReglamento.setBackground(getResources().getDrawable(R.drawable.ic_checkbox_off_gris));
        this.btnConfirmarSuscripcion.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
        this.r = Boolean.valueOf(false);
        this.btnConfirmarSuscripcion.setEnabled(false);
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
                    SuscribirFondoActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonFueraHorario() {
        gotoPage(0, false);
        setActionBarSuscribir();
    }

    public void gotoComprobante(SuscribirFondoBodyResponseBean suscribirFondoBodyResponseBean) {
        gotoPage(3);
        this.w.onCreatePage(suscribirFondoBodyResponseBean);
    }

    public void setActionBarComprobanteSuscripcion() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(false);
        s();
    }

    private void s() {
        this.mActionBar = getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        ImageView imageView2 = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.menu);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SuscribirFondoActivity.this.switchDrawer();
                }
            });
        }
        t();
        if (imageView2 != null) {
            imageView2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SuscribirFondoActivity.this.H.show(SuscribirFondoActivity.this.getSupportFragmentManager(), "Dialog");
                }
            });
        }
    }

    private void t() {
        this.I = u();
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        this.H = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_TITLE), null, arrayList, getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL), null, null, null, arrayList);
        this.H.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(SuscribirFondoActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                    SuscribirFondoActivity.this.I.optionShareSelected();
                } else if (str.equalsIgnoreCase(SuscribirFondoActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                    SuscribirFondoActivity.this.I.optionDownloadSelected();
                }
            }
        });
        this.H.setCancelable(true);
    }

    private OptionsToShare u() {
        return new OptionsToShareImpl(this, this, getSupportFragmentManager()) {
            public View getViewToShare() {
                return SuscribirFondoActivity.this.comprobanteOperacion;
            }

            public void receiveIntentAppShare(Intent intent) {
                SuscribirFondoActivity.this.startActivity(Intent.createChooser(intent, SuscribirFondoActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
            }

            public String getFileName() {
                String string = SuscribirFondoActivity.this.getString(R.string.F24_42_COMPROBANTE_SUSCRIPCION_FILENAME);
                StringBuilder sb = new StringBuilder();
                sb.append("-");
                sb.append(SuscribirFondoActivity.this.F.getSuscripcion().getNroCertificado());
                return string.concat(sb.toString());
            }

            public String getSubjectReceiptToShare() {
                return SuscribirFondoActivity.this.getString(R.string.F24_42_COMPROBANTE_SUSCRIPCION_SUBJECT);
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                SuscribirFondoActivity.this.J = true;
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                SuscribirFondoActivity.this.J = true;
            }

            public void onAbortShare() {
                super.onAbortShare();
                SuscribirFondoActivity.this.J = true;
                SuscribirFondoActivity.this.onBackPressed();
            }
        };
    }

    public boolean canExit(int i) {
        if (!this.J) {
            final int i2 = i;
            AnonymousClass15 r1 = new OptionsToShareImpl(this, this, getSupportFragmentManager()) {
                public View getViewToShare() {
                    return SuscribirFondoActivity.this.comprobanteOperacion;
                }

                public void receiveIntentAppShare(Intent intent) {
                    SuscribirFondoActivity.this.startActivity(Intent.createChooser(intent, SuscribirFondoActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
                }

                public String getFileName() {
                    String string = SuscribirFondoActivity.this.getString(R.string.F24_42_COMPROBANTE_SUSCRIPCION_FILENAME);
                    StringBuilder sb = new StringBuilder();
                    sb.append("-");
                    sb.append(SuscribirFondoActivity.this.F.getSuscripcion().getNroCertificado());
                    return string.concat(sb.toString());
                }

                public String getSubjectReceiptToShare() {
                    return SuscribirFondoActivity.this.getString(R.string.F24_22_COMPROBANTE_RESCATE_SUBJECT);
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    SuscribirFondoActivity.this.J = true;
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    SuscribirFondoActivity.this.J = true;
                }

                public void onAbortShare() {
                    super.onAbortShare();
                    SuscribirFondoActivity.this.J = true;
                    SuscribirFondoActivity.this.onClickItem(i2);
                }
            };
            r1.showAlert();
        }
        return this.J;
    }

    public void setComprobanteSuscripcionView(SuscribirFondoBodyResponseBean suscribirFondoBodyResponseBean) {
        this.F = suscribirFondoBodyResponseBean;
        setActionBarComprobanteSuscripcion();
        this.txtTitleFondoSuscribir.setText(Html.fromHtml(this.x.getNombre()));
        try {
            this.txtTitleFondoSuscribir.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.txtTitleFondoSuscribir.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String importeSusc = this.E.getImporteSusc();
        this.lblDataImporteComprobante.setText(importeSusc);
        try {
            this.lblDataImporteComprobante.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(importeSusc));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        TextView textView = this.lblDataFechaHoraComprobante;
        StringBuilder sb = new StringBuilder();
        sb.append(suscribirFondoBodyResponseBean.getSuscripcion().getFecha());
        sb.append(" - ");
        sb.append(suscribirFondoBodyResponseBean.getSuscripcion().getHora());
        textView.setText(sb.toString());
        try {
            TextView textView2 = this.lblDataFechaHoraComprobante;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(new CAccessibility(getContext()).applyFilterDate(suscribirFondoBodyResponseBean.getSuscripcion().getFecha()));
            sb2.append("  ");
            sb2.append(new CAccessibility(getContext()).applyFilterTime(suscribirFondoBodyResponseBean.getSuscripcion().getHora()));
            textView2.setContentDescription(sb2.toString());
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        String abrevAccount = CAccounts.getInstance(this.sessionManager).getAbrevAccount(this.A);
        this.lblDataOrigenComprobante.setText(abrevAccount);
        try {
            this.lblDataOrigenComprobante.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(abrevAccount));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        String formatCuentaTitulo = UtilAccount.formatCuentaTitulo(this.y.getNumero());
        this.lblDataCuentaTituloComprobante.setText(formatCuentaTitulo);
        try {
            this.lblDataCuentaTituloComprobante.setContentDescription(new CAccessibility(getContext()).applyFilterCharacterToCharacter(formatCuentaTitulo));
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        this.lblDatafondoComprobante.setText(Html.fromHtml(this.x.getNombre()));
        try {
            this.lblDatafondoComprobante.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblDatafondoComprobante.getText().toString()));
        } catch (Exception e6) {
            e6.printStackTrace();
        }
        this.rowCuentaDestinoComprobante.setVisibility(8);
        String importeSusc2 = this.E.getImporteSusc();
        this.lblDataImporteBottomComprobante.setText(importeSusc2);
        try {
            this.lblDataImporteBottomComprobante.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(importeSusc2));
        } catch (Exception e7) {
            e7.printStackTrace();
        }
        try {
            this.lblCertificadoComprobante.setContentDescription(new CAccessibility(getContext()).applyFilterControlNumber(getResources().getString(R.string.ID_3932_FONDOS_LBL_NRO_CERTIFICADO)));
        } catch (Exception e8) {
            e8.printStackTrace();
        }
        String nroCertificado = suscribirFondoBodyResponseBean.getSuscripcion().getNroCertificado();
        this.lblDataCertificadoComprobante.setText(nroCertificado);
        try {
            this.lblDataCertificadoComprobante.setContentDescription(new CAccessibility(getContext()).applyFilterCharacterToCharacter(nroCertificado));
        } catch (Exception e9) {
            e9.printStackTrace();
        }
        this.rowPlazoPagoComprobante.setVisibility(8);
        this.lblComprobanteTerminosLegales.setText(Html.fromHtml(b(suscribirFondoBodyResponseBean.getSuscripcion().getLegales().getLeyendaLegales())));
        try {
            this.lblComprobanteTerminosLegales.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(this.lblComprobanteTerminosLegales.getText().toString()));
        } catch (Exception e10) {
            e10.printStackTrace();
        }
        this.lblTxtCuotapartesComprobante.setVisibility(8);
        this.lblDataCuotapartesComprobante.setVisibility(8);
        this.separatorSectionComprobante.setVisibility(8);
        this.lblDataValorCuotaparteComprobante.setVisibility(8);
        this.lblTextValorcuotaparte.setVisibility(8);
        this.lbltextValorCuotapartes.setVisibility(8);
        this.lbltextCuotapartes.setVisibility(8);
        if (this.K.equalsIgnoreCase(FondosConstants.ACCION_SUSCRIBIR_CUOTAPARTES)) {
            this.p.trackScreen(getString(R.string.analytics_screen_comprobante_suscripcion_cuotapartes));
            this.txtTitleComprobante.setText(getString(R.string.F24_42_TITLE_COMPROBANTE_CUOTAPARTES));
        } else {
            this.p.trackScreen(getString(R.string.analytics_screen_nuevo_fondo_comprobante_suscripcion));
            this.txtTitleComprobante.setText(getString(R.string.F24_42_TITLE_COMPROBANTE));
        }
        String nroCertificado2 = suscribirFondoBodyResponseBean.getSuscripcion().getNroCertificado();
        String fecha = suscribirFondoBodyResponseBean.getSuscripcion().getFecha();
        String hora = suscribirFondoBodyResponseBean.getSuscripcion().getHora();
        AnalyticsManager analyticsManager = this.p;
        String string = getString(R.string.analytics_transaction_hits_fondos);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(nroCertificado2);
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(fecha);
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(hora);
        analyticsManager.trackTransaction(string, sb3.toString());
        this.btnVolverComprobante.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SuscribirFondoActivity.this.onBackPressed();
            }
        });
    }

    private String b(List<String> list) {
        String str = "";
        for (String concat : list) {
            str = str.concat(concat).concat("<br>");
        }
        return str;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.F24_20_RLL_SELECTED_AMOUNT_TYPE /*2131363098*/:
                this.s.showAmountTypeDialog(this.lblSelectedAmountType);
                return;
            case R.id.F24_20_btn_continuar /*2131363099*/:
                this.s.gotoConfirmar(this.x, this.y, this.inpAmount.getFormatedText().toString(), this.A);
                return;
            case R.id.F24_20_img_checkbox_tyc /*2131363100*/:
                e();
                return;
            case R.id.F24_20_lbl_data_fondo /*2131363107*/:
                onBackPressed();
                return;
            case R.id.F24_20_lbl_data_origen /*2131363109*/:
                this.p.trackScreen(getString(R.string.analytics_screen_vista_selecion_origen_Detalles_Fondo));
                String str = "";
                if (this.x != null) {
                    if (this.x.getMoneda().equalsIgnoreCase("0")) {
                        str = Constants.SYMBOL_CURRENCY_PESOS;
                    } else if (this.x.getMoneda().equalsIgnoreCase("2")) {
                        str = Constants.SYMBOL_CURRENCY_DOLAR;
                    } else {
                        str = UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.x.getMoneda()).toString());
                    }
                }
                this.s.showSelectDestinationAccountDialog(this.A, this.B, str, this.C);
                return;
            case R.id.F24_20_vgSelectorAccount /*2131363128*/:
                this.s.showSelectAccountDialog(this.y, this.D);
                return;
            case R.id.F24_21_btn_confirmar /*2131363129*/:
                this.t.gotoComprobante(this.x, this.y, this.inpAmount.getFormatedText().toString(), this.A, this.E, this.K);
                return;
            case R.id.F24_21_img_checkbox_tyc /*2131363130*/:
                o();
                return;
            case R.id.F24_21_lbl_verTerminos /*2131363152*/:
                this.t.showTyC(this.E.getTermCondiciones());
                return;
            case R.id.F24_22_btn_volver /*2131363167*/:
                onBackPressed();
                return;
            default:
                return;
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.I.onRequestPermissionsResult(i, strArr, iArr);
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
            case R.id.layout_comprobante_suscripcion_fondo /*2131364910*/:
                if (!this.J) {
                    this.I.showAlert();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(FondosConstants.INTENT_EXTRA_RECARGAR_FONDOS, true);
                intent.putExtra("CUENTA", this.y);
                setResult(-1, intent);
                super.onBackPressed();
                hideKeyboard();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return;
            case R.id.layout_confirmacion_suscripcion_fondo /*2131364911*/:
                backButtonConfirmar();
                return;
            case R.id.layout_evaluacion_riesgo_fondo /*2131364926*/:
                backButtonEvaluacionRiesgo();
                return;
            case R.id.layout_fondo_seleccionado /*2131364929*/:
                backButtonSuscribir();
                return;
            case R.id.out_working_hours_fondo /*2131365284*/:
                backButtonFueraHorario();
                return;
            default:
                return;
        }
    }

    public void configureLayout() {
        this.vgSelectorAccount.setOnClickListener(this);
    }

    public void onDestroy() {
        detachView();
        super.onDestroy();
    }

    public void setListaCuentasOperativas(ArrayList<CuentaOperativaBean> arrayList) {
        this.C = arrayList;
    }

    public String getDestinoDeCuentaOpEnLista(String str) {
        CuentaOperativaBean a = a(str);
        return a != null ? a.getDescCtaDestino() : "";
    }

    private CuentaOperativaBean a(String str) {
        Iterator it = this.C.iterator();
        while (it.hasNext()) {
            CuentaOperativaBean cuentaOperativaBean = (CuentaOperativaBean) it.next();
            if (!str.equalsIgnoreCase(cuentaOperativaBean.getDescCtaDebito())) {
                if (str.equalsIgnoreCase(cuentaOperativaBean.getDescCtaDestino())) {
                }
            }
            return cuentaOperativaBean;
        }
        return null;
    }

    public List<CuentaOperativaBean> getCuentasValidas(List<CuentaOperativaBean> list) {
        ArrayList arrayList = new ArrayList();
        String simbolCurrencyFromDescription = UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.x.getMoneda()).toString());
        for (CuentaOperativaBean cuentaOperativaBean : list) {
            if (TextUtils.isEmpty(simbolCurrencyFromDescription) || cuentaOperativaBean.getTipoCta().equalsIgnoreCase("02") || UtilAccount.getCurrencyOfAccount(getSessionManager(), cuentaOperativaBean).equalsIgnoreCase(simbolCurrencyFromDescription)) {
                arrayList.add(cuentaOperativaBean);
            }
        }
        return arrayList;
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
}
