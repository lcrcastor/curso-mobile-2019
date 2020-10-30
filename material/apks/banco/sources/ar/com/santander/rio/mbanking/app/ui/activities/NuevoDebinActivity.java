package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
import ar.com.santander.rio.mbanking.app.module.debin.abmdebin.GenerarDebinPresenter;
import ar.com.santander.rio.mbanking.app.module.debin.abmdebin.GenerarDebinView;
import ar.com.santander.rio.mbanking.app.module.debin.abmdebin.NuevoDebinPresenter;
import ar.com.santander.rio.mbanking.app.module.debin.abmdebin.NuevoDebinView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.PRE_AUTORIZACIONES;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment.IDateDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinVendedorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompradorBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultarTitularCuentaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaCompradorBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaVendedor;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DetalleDebinBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaCuentasVendedorBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VendedorBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

public class NuevoDebinActivity extends MvpPrivateMenuActivity implements OnClickListener, GenerarDebinView, NuevoDebinView {
    public static final String ALIAS = "Alias";
    public static final String AMOUNT = "AMOUNT";
    public static final String CHARACTERTOCHARACTER = "CHARACTERTOCHARACTER";
    public static final String C_B_U = "C B U";
    public static final String DATE = "DATE";
    public static final String PERCENTAGE = "PERCENTAGE";
    private ConsultarTitularCuentaBodyResponseBean A;
    private CuentaVendedor B;
    /* access modifiers changed from: private */
    public String C = UtilDate.getDateFormat(UtilDate.getDateFormat(Calendar.getInstance().getTime(), Constants.FORMAT_DATE_APP_2), Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_WS_1);
    private String D;
    /* access modifiers changed from: private */
    public OptionsToShare E;
    /* access modifiers changed from: private */
    public IsbanDialogFragment F;
    /* access modifiers changed from: private */
    public String G;
    /* access modifiers changed from: private */
    public boolean H = false;
    /* access modifiers changed from: private */
    public DetalleDebinBean I = new DetalleDebinBean();
    private boolean J = false;
    private String K = "";
    private String L = "";
    private String M = "";
    @InjectView(2131363898)
    Button btnAdherirDebin;
    @InjectView(2131363852)
    Button btnConfirmar;
    @InjectView(2131363828)
    Button btnContinuar;
    @InjectView(2131363808)
    Button btnVerificar;
    @InjectView(2131363885)
    Button btnVolver;
    @InjectView(2131363876)
    TextView cbuEE;
    @InjectView(2131363884)
    TextView comproEE;
    @InjectView(2131363887)
    ScrollView comprobanteDebin;
    @InjectView(2131363879)
    TextView cuitEE;
    @InjectView(2131364646)
    EditText etCBU;
    @InjectView(2131363830)
    NumericEditTextWithPrefixAccesibility etImporte;
    @InjectView(2131363855)
    TextView etImporteC;
    @InjectView(2131363888)
    TextView etImporteComp;
    @InjectView(2131363881)
    TextView fvtoEE;
    @InjectView(2131364802)
    ImageView imgDestinoSeleccionado;
    @InjectView(2131363900)
    RelativeLayout layout_sinCuentas;
    @InjectView(2131363812)
    TextView lblAlias;
    @InjectView(2131363836)
    TextView lblAliasC;
    @InjectView(2131363863)
    TextView lblAliasComp;
    @InjectView(2131363813)
    TextView lblBanco;
    @InjectView(2131363837)
    TextView lblBancoC;
    @InjectView(2131363864)
    TextView lblBancoComp;
    @InjectView(2131363814)
    TextView lblCBU;
    @InjectView(2131363838)
    TextView lblCBUC;
    @InjectView(2131363865)
    TextView lblCBUComp;
    @InjectView(2131363829)
    TextView lblComprador;
    @InjectView(2131363853)
    TextView lblCompradorC;
    @InjectView(2131363886)
    TextView lblCompradorComp;
    @InjectView(2131363815)
    TextView lblConcepto;
    @InjectView(2131363839)
    TextView lblConceptoC;
    @InjectView(2131363866)
    TextView lblConceptoComp;
    @InjectView(2131363861)
    TextView lblConfirmarTitle;
    @InjectView(2131363816)
    TextView lblCtaAcreditacion;
    @InjectView(2131363840)
    TextView lblCtaAcreditacionC;
    @InjectView(2131363867)
    TextView lblCtaAcreditacionComp;
    @InjectView(2131363811)
    TextView lblCuit;
    @InjectView(2131363835)
    TextView lblCuitC;
    @InjectView(2131363862)
    TextView lblCuitComp;
    @InjectView(2131363817)
    EditText lblDescripcion;
    @InjectView(2131363841)
    TextView lblDescripcionC;
    @InjectView(2131363868)
    TextView lblDescripcionComp;
    @InjectView(2131363870)
    TextView lblFechaOperacion;
    @InjectView(2131363818)
    TextView lblFechaVto;
    @InjectView(2131363842)
    TextView lblFechaVtoC;
    @InjectView(2131363869)
    TextView lblFechaVtoComp;
    @InjectView(2131363834)
    TextView lblGenerarDebinTitle;
    @InjectView(2131363872)
    TextView lblIdDebinDataComp;
    @InjectView(2131363871)
    TextView lblLeyenda;
    @InjectView(2131363873)
    TextView lblNroComprobante;
    @InjectView(2131363901)
    TextView lblRes4Msj;
    @InjectView(2131363810)
    TextView lblTitle;
    @InjectView(2131363843)
    TextView leyendaConfirmarDebin;
    @InjectView(2131363819)
    TextView leyendaGenerarDebin;
    @InjectView(2131365267)
    ViewFlipper mControlPager;
    String p = "DialogDate";
    @Inject
    AnalyticsManager q;
    private String r;
    @InjectView(2131363831)
    LinearLayout rowAlias;
    @InjectView(2131363856)
    LinearLayout rowAliasC;
    @InjectView(2131363889)
    LinearLayout rowAliasComp;
    @InjectView(2131363832)
    LinearLayout rowBanco;
    @InjectView(2131363857)
    LinearLayout rowBancoC;
    @InjectView(2131363890)
    LinearLayout rowBancoComp;
    @InjectView(2131363833)
    LinearLayout rowCBU;
    @InjectView(2131363858)
    LinearLayout rowCBUC;
    @InjectView(2131363891)
    LinearLayout rowCBUComp;
    @InjectView(2131363859)
    LinearLayout rowDescripcion;
    @InjectView(2131363892)
    LinearLayout rowDescripcionComp;
    @InjectView(2131363893)
    LinearLayout row_fechaOperacion;
    @InjectView(2131363895)
    LinearLayout row_nroComprobante;
    @InjectView(2131363894)
    LinearLayout rowidDebinComp;
    /* access modifiers changed from: private */
    public NuevoDebinPresenter s;
    @InjectView(2131365679)
    RelativeLayout selectorCBU;
    private GenerarDebinPresenter t;
    @InjectView(2131363897)
    TextView titleEE;
    @InjectView(2131366270)
    TextView txtAyuda;
    @InjectView(2131365675)
    TextView txtSeleccionCBU;
    /* access modifiers changed from: private */
    public String u;
    private String v;
    private String w;
    private List<ListTableBean> x = new ArrayList();
    /* access modifiers changed from: private */
    public String y;
    private List<CuentaVendedor> z;

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
        return R.layout.nuevo_debin_layout;
    }

    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        ButterKnife.inject((Activity) this);
        initialize();
        this.u = C_B_U;
    }

    public void initialize() {
        this.s = new NuevoDebinPresenter(this.mBus, this.mDataManager);
        this.t = new GenerarDebinPresenter(this.mBus, this.mDataManager);
        this.x = this.sessionManager.getConsDescripciones().getListTableBeans();
        this.z = this.sessionManager.getCuentasDEBINAdheridas();
        this.y = a("09", PRE_AUTORIZACIONES.CONDEBIN);
        this.L = getIntent().getStringExtra("leyenda");
        if (!this.z.isEmpty()) {
            this.q.trackScreen(getString(R.string.analytics_screen_name_Ingresar_destinatario_F_32_10));
            for (CuentaVendedor statusAdhesion : this.z) {
                if (statusAdhesion.getStatusAdhesion().intValue() == 0) {
                    gotoSetNuevoDebinView();
                    this.J = false;
                    return;
                }
                setDebinRes4View(this.K, Boolean.valueOf(false));
            }
            return;
        }
        this.q.trackScreen(getString(R.string.analytics_screen_name_Adherir_cuenta_generar_Debin_F_32_16));
        this.K = getIntent().getStringExtra("mensaje");
        setDebinRes4View(getIntent().getStringExtra("mensaje"), Boolean.valueOf(false));
    }

    public void gotoSetNuevoDebinView() {
        gotoPage(0);
        this.s.onCreatePage();
    }

    public void gotoSetGenerarDebinView() {
        if (this.mControlPager.getDisplayedChild() == 1) {
            return;
        }
        if (this.u.equalsIgnoreCase(C_B_U)) {
            this.s.consultarTitular(this.w, "");
        } else if (this.u.equalsIgnoreCase("Alias")) {
            this.s.consultarTitular("", this.v);
        }
    }

    public void gotoPage(int i) {
        gotoPage(i, true);
    }

    public void gotoPage(int i, boolean z2) {
        if (this.mControlPager != null) {
            detachView();
            switch (i) {
                case 0:
                    if (!this.J) {
                        if (!z2) {
                            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                            break;
                        }
                    } else {
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
                    if (this.J) {
                        if (!z2) {
                            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                            break;
                        } else {
                            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                            break;
                        }
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.noAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.noAnimation());
                        break;
                    }
                case 3:
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    }
                case 4:
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    }
            }
        }
        this.mControlPager.setDisplayedChild(i);
        attachView();
        hideKeyboard();
    }

    public void setNuevoDebinView() {
        dismissProgressIndicator();
        setActionBarIngresarDestinatario();
        ((RelativeLayout) findViewById(R.id.F32_10_RLL_SCROLL)).setMinimumHeight(Utils.getScreenHeightSizeForContent(this));
        this.lblTitle.setContentDescription(this.lblTitle.getText().toString().toLowerCase());
        this.etCBU.setHint(R.string.ID_4458_DEBIN_C_B_U_DELDESTINATARIO);
        this.etCBU.setContentDescription(getContext().getString(R.string.ID_4458_DEBIN_CBUDELDESTINATARIO_DESCRIPTION));
        this.btnVerificar.setOnClickListener(this);
        this.selectorCBU.setOnClickListener(this);
        this.txtSeleccionCBU.setText(this.u);
        this.txtSeleccionCBU.setText(getString(R.string.F32_13_txt_cbu));
        this.txtSeleccionCBU.setContentDescription(CAccessibility.getInstance(getContext()).applyCBU(this.txtSeleccionCBU.getText().toString()));
        this.etCBU.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                boolean z = false;
                if (NuevoDebinActivity.this.u.equalsIgnoreCase(NuevoDebinActivity.C_B_U)) {
                    NuevoDebinActivity nuevoDebinActivity = NuevoDebinActivity.this;
                    Button button = NuevoDebinActivity.this.btnVerificar;
                    if (NuevoDebinActivity.this.etCBU.getText().toString().length() == 22) {
                        z = true;
                    }
                    nuevoDebinActivity.a(button, Boolean.valueOf(z));
                    NuevoDebinActivity.this.etCBU.setContentDescription(CAccessibility.getInstance(NuevoDebinActivity.this.getContext()).applyCBU(NuevoDebinActivity.this.etCBU.getHint().toString()));
                } else if (NuevoDebinActivity.this.u.equalsIgnoreCase("Alias")) {
                    NuevoDebinActivity nuevoDebinActivity2 = NuevoDebinActivity.this;
                    Button button2 = NuevoDebinActivity.this.btnVerificar;
                    if (NuevoDebinActivity.this.etCBU.getText().toString().length() >= 6) {
                        z = true;
                    }
                    nuevoDebinActivity2.a(button2, Boolean.valueOf(z));
                    try {
                        NuevoDebinActivity.this.etCBU.setContentDescription(CAccessibility.getInstance(NuevoDebinActivity.this.getContext()).applyFilterGeneral(NuevoDebinActivity.this.etCBU.getText().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(Button button, Boolean bool) {
        boolean booleanValue = bool.booleanValue();
        button.setEnabled(booleanValue);
        if (booleanValue) {
            button.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
        } else {
            button.setBackground(getResources().getDrawable(R.drawable.button_dissable));
        }
    }

    public void setActionBarIngresarDestinatario() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        b();
    }

    public void setActionBarComprobanteDebin() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(false);
        c();
    }

    public void setActionBarRes4View() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        b();
    }

    public void setActionBarConfirmarDebin() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        d();
    }

    private void b() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    NuevoDebinActivity.this.onBackPressed();
                }
            });
        }
    }

    private void c() {
        View customView = getSupportActionBar().getCustomView();
        View findViewById = customView.findViewById(R.id.menu);
        View findViewById2 = customView.findViewById(R.id.toggle);
        if (findViewById2 != null) {
            findViewById2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    NuevoDebinActivity.this.switchDrawer();
                }
            });
        }
        m();
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    NuevoDebinActivity.this.q.trackEvent(NuevoDebinActivity.this.getString(R.string.analytics_trackevent_category_debin), NuevoDebinActivity.this.getString(R.string.analytics_trackevent_action_generar_debin), NuevoDebinActivity.this.getString(R.string.analytics_trackevent_label_Descargar_compartir_comprobante));
                    NuevoDebinActivity.this.F.show(NuevoDebinActivity.this.getSupportFragmentManager(), "Dialog");
                }
            });
        }
    }

    private void d() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    NuevoDebinActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonIngresarDestinatario() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void setGenerarDebinView(ConsultarTitularCuentaBodyResponseBean consultarTitularCuentaBodyResponseBean) {
        gotoPage(1);
        this.y = a("09", PRE_AUTORIZACIONES.CONDEBIN);
        this.lblDescripcion.setText("");
        this.C = UtilDate.getDateFormat(UtilDate.getDateFormat(Calendar.getInstance().getTime(), Constants.FORMAT_DATE_APP_2), Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_WS_1);
        this.A = consultarTitularCuentaBodyResponseBean;
        j();
        if (this.z.size() >= 1) {
            this.B = (CuentaVendedor) this.z.get(0);
        }
        if (consultarTitularCuentaBodyResponseBean.getCbu() == null || consultarTitularCuentaBodyResponseBean.getCbu().equalsIgnoreCase("")) {
            this.rowCBU.setVisibility(8);
        } else {
            this.q.trackScreen(getString(R.string.analytics_screen_name_Generar_Debin_F_32_13));
            this.rowCBU.setVisibility(0);
            this.lblCBU.setText(consultarTitularCuentaBodyResponseBean.getCbu());
        }
        if (consultarTitularCuentaBodyResponseBean.getBanco() == null || consultarTitularCuentaBodyResponseBean.getBanco().equalsIgnoreCase("")) {
            this.rowBanco.setVisibility(8);
        } else {
            this.rowBanco.setVisibility(0);
            this.lblBanco.setText(consultarTitularCuentaBodyResponseBean.getBanco());
        }
        if (!this.u.equalsIgnoreCase(C_B_U)) {
            this.rowAlias.setVisibility(0);
            this.lblAlias.setText(this.v);
        } else {
            this.rowAlias.setVisibility(8);
            this.lblAlias.setText("");
        }
        if (this.z.size() > 1) {
            this.lblCtaAcreditacion.setText(UtilAccount.getAbreviatureAndAccountFormat(c(PRE_AUTORIZACIONES.TPOCTACORTA), this.B.getTipo(), this.B.getSucursal(), this.B.getNumero()));
            this.lblCtaAcreditacion.setOnClickListener(this);
            this.lblCtaAcreditacion.setTextColor(getResources().getColor(R.color.generic_black));
        } else {
            this.lblCtaAcreditacion.setText(UtilAccount.getAbreviatureAndAccountFormat(c(PRE_AUTORIZACIONES.TPOCTACORTA), this.B.getTipo(), this.B.getSucursal(), this.B.getNumero()));
            this.lblCtaAcreditacion.setTextColor(getResources().getColor(R.color.generic_black));
        }
        if (this.L != null) {
            this.leyendaGenerarDebin.setText(Html.fromHtml(this.L));
        } else {
            this.leyendaGenerarDebin.setText("");
        }
        setActionBarGenerarDebin();
        this.lblFechaVto.setText(UtilDate.getDateFormat(Calendar.getInstance().getTime(), Constants.FORMAT_DATE_APP_2));
        a(consultarTitularCuentaBodyResponseBean);
        this.lblFechaVto.setOnClickListener(this);
        this.lblConcepto.setOnClickListener(this);
        this.btnContinuar.setOnClickListener(this);
        this.lblComprador.setText(consultarTitularCuentaBodyResponseBean.getTitular());
        this.lblCuit.setText(consultarTitularCuentaBodyResponseBean.getCuit());
        this.lblConcepto.setText(this.y);
        a(this.btnContinuar, Boolean.valueOf(false));
        this.etImporte.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (NuevoDebinActivity.this.etImporte.getText().toString().replaceAll("\\s+", "").equals("") || NuevoDebinActivity.this.etImporte.getText().toString().isEmpty()) {
                    NuevoDebinActivity.this.a(NuevoDebinActivity.this.btnContinuar, Boolean.valueOf(false));
                } else if (NuevoDebinActivity.this.etImporte.getText().toString().replaceAll("\\s+", "").equals("0") || NuevoDebinActivity.this.etImporte.getText().toString().replaceAll("\\s+", "").equals(TarjetasConstants.FORMATTED_ZERO) || NuevoDebinActivity.this.etImporte.getText().toString().replaceAll("\\s+", "").equals("0,0") || NuevoDebinActivity.this.etImporte.getText().toString().replaceAll("\\s+", "").equals("0,")) {
                    NuevoDebinActivity.this.a(NuevoDebinActivity.this.btnContinuar, Boolean.valueOf(false));
                } else {
                    NuevoDebinActivity.this.a(NuevoDebinActivity.this.btnContinuar, Boolean.valueOf(true));
                }
            }
        });
        try {
            this.leyendaGenerarDebin.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.leyendaGenerarDebin.getText().toString().toLowerCase()));
            this.lblGenerarDebinTitle.setContentDescription(this.lblGenerarDebinTitle.getText().toString().toLowerCase());
            this.lblCuit.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblCuit.getText().toString()));
            this.lblNroComprobante.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblNroComprobante.getText().toString()));
            this.lblCtaAcreditacion.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.lblCtaAcreditacion.getText().toString()));
            this.lblFechaVto.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterDate(this.lblFechaVto.getText().toString()));
            this.etImporte.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.etImporte.getText().toString()));
            this.lblCBU.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblCBU.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setComprobanteDebinView(AbmDebinVendedorBodyResponseBean abmDebinVendedorBodyResponseBean) {
        gotoPage(4);
        setActionBarComprobanteDebin();
        this.btnVolver.setOnClickListener(this);
        this.G = abmDebinVendedorBodyResponseBean.getNroComprobante();
        this.lblNroComprobante.setText(abmDebinVendedorBodyResponseBean.getNroComprobante());
        this.lblLeyenda.setText(abmDebinVendedorBodyResponseBean.getLeyendaComp());
        this.lblFechaOperacion.setText(UtilDate.getDateFormat(abmDebinVendedorBodyResponseBean.getFechaOperacion(), Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2));
        this.r = UtilDate.getDateFormat(abmDebinVendedorBodyResponseBean.getFechaOperacion(), Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2);
        this.lblFechaVtoComp.setText(UtilDate.getDateFormat(this.C, Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2));
        this.lblCompradorComp.setText(this.A.getTitular());
        this.lblCuitComp.setText(this.A.getCuit());
        this.lblCtaAcreditacionComp.setText(UtilAccount.getAbreviatureAndAccountFormat(c(PRE_AUTORIZACIONES.TPOCTACORTA), this.B.getTipo(), this.B.getSucursal(), this.B.getNumero()));
        this.lblIdDebinDataComp.setText(abmDebinVendedorBodyResponseBean.getIdDebin());
        if (!this.u.equalsIgnoreCase(C_B_U)) {
            this.rowAliasComp.setVisibility(0);
            this.lblAliasComp.setText(this.v);
        } else {
            this.rowAliasComp.setVisibility(8);
        }
        if (this.rowCBU.getVisibility() == 0) {
            this.q.trackScreen(getString(R.string.f214analytics_screen_name_Comprobante_generacin_Debin_F_32_15));
            this.rowCBUComp.setVisibility(0);
            this.lblCBUComp.setText(this.A.getCbu());
        } else {
            this.rowCBUComp.setVisibility(8);
        }
        if (this.rowBanco.getVisibility() == 0) {
            this.rowBancoComp.setVisibility(0);
            this.lblBancoComp.setText(this.A.getBanco());
        } else {
            this.rowBancoComp.setVisibility(8);
        }
        this.lblConceptoComp.setText(this.y);
        TextView textView = this.etImporteComp;
        StringBuilder sb = new StringBuilder();
        sb.append(this.etImporte.getPrefix());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.etImporte.getText());
        textView.setText(sb.toString());
        if (this.lblDescripcion.getText().toString().replaceAll(UtilsCuentas.SEPARAOR2, "").equalsIgnoreCase("")) {
            this.rowDescripcionComp.setVisibility(8);
        } else {
            this.rowDescripcionComp.setVisibility(0);
            this.lblDescripcionComp.setText(this.lblDescripcion.getText());
        }
        try {
            this.titleEE.setContentDescription(this.titleEE.getText().toString().toLowerCase());
            this.lblCuitComp.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblCuitComp.getText().toString()));
            this.lblNroComprobante.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblNroComprobante.getText().toString()));
            this.lblCtaAcreditacionComp.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.lblCtaAcreditacionComp.getText().toString()));
            this.etImporteComp.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.etImporteComp.getText().toString()));
            this.lblCBUComp.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblCBUComp.getText().toString()));
            this.lblLeyenda.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.lblLeyenda.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AnalyticsManager analyticsManager = this.q;
        String string = getString(R.string.analytics_transaction_hits_debin);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.G);
        sb2.append(UtilsCuentas.SEPARAOR2);
        sb2.append(this.r);
        analyticsManager.trackTransaction(string, sb2.toString());
        this.cbuEE.setOnClickListener(this);
        this.cuitEE.setOnClickListener(this);
        this.comproEE.setOnClickListener(this);
        this.titleEE.setOnClickListener(this);
        this.fvtoEE.setOnClickListener(this);
    }

    public void setConfirmarDebinView() {
        setActionBarConfirmarDebin();
        this.lblCtaAcreditacionC.setText(UtilAccount.getAbreviatureAndAccountFormat(c(PRE_AUTORIZACIONES.TPOCTACORTA), this.B.getTipo(), this.B.getSucursal(), this.B.getNumero()));
        this.lblFechaVtoC.setText(UtilDate.getDateFormat(this.C, Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2));
        this.lblCompradorC.setText(this.A.getTitular());
        this.lblCuitC.setText(this.A.getCuit());
        if (!this.u.equalsIgnoreCase(C_B_U)) {
            this.rowAliasC.setVisibility(0);
            this.lblAliasC.setText(this.v);
        } else {
            this.rowAliasC.setVisibility(8);
        }
        if (this.rowCBU.getVisibility() == 0) {
            this.q.trackScreen(getString(R.string.f218analytics_screen_name_Confirmar_generacin__Debin_F_32_14));
            this.rowCBUC.setVisibility(0);
            this.lblCBUC.setText(this.A.getCbu());
        } else {
            this.rowCBUC.setVisibility(8);
        }
        if (this.rowBanco.getVisibility() == 0) {
            this.rowBancoC.setVisibility(0);
            this.lblBancoC.setText(this.A.getBanco());
        } else {
            this.rowBancoC.setVisibility(8);
        }
        this.lblConceptoC.setText(this.y);
        if (this.etImporte.getText().toString().substring(this.etImporte.getText().toString().length() - 1).equalsIgnoreCase(",")) {
            this.etImporte.setText(this.etImporte.getText().toString().substring(0, this.etImporte.getText().toString().length() - 1));
        }
        TextView textView = this.etImporteC;
        StringBuilder sb = new StringBuilder();
        sb.append(this.etImporte.getPrefix());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.etImporte.getFormatedText());
        textView.setText(sb.toString());
        if (this.lblDescripcion.getText().toString().replaceAll(UtilsCuentas.SEPARAOR2, "").equalsIgnoreCase("")) {
            this.rowDescripcion.setVisibility(8);
        } else {
            this.rowDescripcion.setVisibility(0);
            this.lblDescripcionC.setText(this.lblDescripcion.getText());
        }
        if (this.L != null) {
            this.leyendaConfirmarDebin.setText(Html.fromHtml(this.L));
        }
        this.btnConfirmar.setOnClickListener(this);
        e();
        try {
            this.lblConfirmarTitle.setContentDescription(this.lblConfirmarTitle.getText().toString().toLowerCase());
            this.leyendaConfirmarDebin.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.leyendaConfirmarDebin.getText().toString().toLowerCase()));
            this.lblCuitC.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblCuitC.getText().toString()));
            this.lblCtaAcreditacionC.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.lblCtaAcreditacionC.getText().toString()));
            this.lblFechaVtoC.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterDate(this.lblFechaVtoC.getText().toString()));
            this.etImporteC.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.etImporteC.getText().toString()));
            this.lblCBUC.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblCBUC.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void e() {
        this.I.setImporte(this.etImporte.getFormatedText().toString());
        this.I.setMoneda(f());
        CompradorBean compradorBean = new CompradorBean();
        compradorBean.setTitular(this.A.getTitular());
        compradorBean.setCuit(this.A.getCuit());
        CuentaCompradorBean cuentaCompradorBean = new CuentaCompradorBean();
        cuentaCompradorBean.setNumeroCuenta(this.A.getNumeroCuenta());
        cuentaCompradorBean.setTipoCuenta(this.A.getTipoCuenta());
        cuentaCompradorBean.setCbu(this.A.getCbu());
        cuentaCompradorBean.setBanco(this.A.getBanco());
        compradorBean.setCuentaCompradorBean(cuentaCompradorBean);
        VendedorBean vendedorBean = new VendedorBean();
        CuentaVendedor cuentaVendedor = new CuentaVendedor();
        cuentaVendedor.setTipo(this.B.getTipo());
        cuentaVendedor.setSucursal(this.B.getSucursal());
        cuentaVendedor.setNumero(this.B.getNumero());
        vendedorBean.setCuentaVendedor(cuentaVendedor);
        vendedorBean.setTitular("");
        vendedorBean.setCuit("");
        this.I.setCompradorBean(compradorBean);
        this.I.setVendedorBean(vendedorBean);
        this.I.setFechaVencimiento(UtilDate.getDateFormat(this.C, Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2));
        this.I.setCodConcepto(b(this.y, PRE_AUTORIZACIONES.CONDEBIN));
        this.I.setDescripcion(this.lblDescripcion.getText().toString());
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String f() {
        /*
            r3 = this;
            java.lang.String r0 = ""
            ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaVendedor r1 = r3.B
            java.lang.String r1 = r1.getTipo()
            int r2 = r1.hashCode()
            switch(r2) {
                case 1536: goto L_0x0042;
                case 1537: goto L_0x0038;
                case 1539: goto L_0x002e;
                case 1540: goto L_0x0024;
                case 1545: goto L_0x001a;
                case 1567: goto L_0x0010;
                default: goto L_0x000f;
            }
        L_0x000f:
            goto L_0x004c
        L_0x0010:
            java.lang.String r2 = "10"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x004c
            r1 = 3
            goto L_0x004d
        L_0x001a:
            java.lang.String r2 = "09"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x004c
            r1 = 0
            goto L_0x004d
        L_0x0024:
            java.lang.String r2 = "04"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x004c
            r1 = 4
            goto L_0x004d
        L_0x002e:
            java.lang.String r2 = "03"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x004c
            r1 = 5
            goto L_0x004d
        L_0x0038:
            java.lang.String r2 = "01"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x004c
            r1 = 1
            goto L_0x004d
        L_0x0042:
            java.lang.String r2 = "00"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x004c
            r1 = 2
            goto L_0x004d
        L_0x004c:
            r1 = -1
        L_0x004d:
            switch(r1) {
                case 0: goto L_0x0054;
                case 1: goto L_0x0054;
                case 2: goto L_0x0054;
                case 3: goto L_0x0051;
                case 4: goto L_0x0051;
                case 5: goto L_0x0051;
                default: goto L_0x0050;
            }
        L_0x0050:
            goto L_0x0056
        L_0x0051:
            java.lang.String r0 = "USD"
            goto L_0x0056
        L_0x0054:
            java.lang.String r0 = "ARS"
        L_0x0056:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.NuevoDebinActivity.f():java.lang.String");
    }

    public void setDebinRes4View(String str, Boolean bool) {
        setActionBarRes4View();
        this.J = bool.booleanValue();
        gotoPage(2);
        this.lblRes4Msj.setText(Html.fromHtml(str));
        if (this.lblRes4Msj.getText().toString().equalsIgnoreCase("")) {
            this.lblRes4Msj.setText(getString(R.string.ID_4469_DEBIN_AUNNOTENESCUENTASADHERIDASADEBIN));
        }
        this.btnAdherirDebin.setOnClickListener(this);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultarTitularCuentaBodyResponseBean r3) {
        /*
            r2 = this;
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r0 = r2.etImporte
            java.lang.String r1 = ""
            r0.setText(r1)
            java.lang.String r3 = r3.getCodMonedaOperacion()
            int r0 = r3.hashCode()
            switch(r0) {
                case 49: goto L_0x0027;
                case 50: goto L_0x001d;
                case 51: goto L_0x0013;
                default: goto L_0x0012;
            }
        L_0x0012:
            goto L_0x0031
        L_0x0013:
            java.lang.String r0 = "3"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0031
            r3 = 1
            goto L_0x0032
        L_0x001d:
            java.lang.String r0 = "2"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0031
            r3 = 2
            goto L_0x0032
        L_0x0027:
            java.lang.String r0 = "1"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0031
            r3 = 0
            goto L_0x0032
        L_0x0031:
            r3 = -1
        L_0x0032:
            switch(r3) {
                case 0: goto L_0x0042;
                case 1: goto L_0x0042;
                case 2: goto L_0x0036;
                default: goto L_0x0035;
            }
        L_0x0035:
            goto L_0x004d
        L_0x0036:
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r3 = r2.etImporte
            java.lang.String r0 = "U$S"
            r3.setPrefix(r0)
            java.lang.String r3 = "U$S"
            r2.D = r3
            goto L_0x004d
        L_0x0042:
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r3 = r2.etImporte
            java.lang.String r0 = "$"
            r3.setPrefix(r0)
            java.lang.String r3 = "$"
            r2.D = r3
        L_0x004d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.NuevoDebinActivity.a(ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultarTitularCuentaBodyResponseBean):void");
    }

    /* access modifiers changed from: private */
    public boolean a(String str) {
        Date date;
        try {
            date = new SimpleDateFormat(Constants.FORMAT_DATE_WS_1).parse(UtilDate.getDateFormat(str, Constants.FORMAT_DATE_DASH, Constants.FORMAT_DATE_WS_1));
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        return date == null || !UtilDate.isDateAfter(UtilDate.resetHours(new Date()), date);
    }

    public void setActionBarGenerarDebin() {
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
                    NuevoDebinActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonGenerarDebin() {
        gotoPage(0, false);
    }

    public void backButtonConfirmarDebin() {
        gotoPage(1, false);
    }

    public void backButtonComprobanteDebin() {
        setResult(-1);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void backButtonSinCuentasDebin() {
        gotoPage(0, false);
    }

    public void attachView() {
        int displayedChild = this.mControlPager.getDisplayedChild();
        if (displayedChild != 3) {
            switch (displayedChild) {
                case 0:
                    break;
                case 1:
                    if (!this.t.isViewAttached()) {
                        this.t.attachView(this);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
        if (!this.s.isViewAttached()) {
            this.s.attachView(this);
        }
    }

    public void detachView() {
        if (this.s.isViewAttached()) {
            this.s.detachView();
        }
        if (this.t.isViewAttached()) {
            this.t.detachView();
        }
    }

    public void onBackPressed() {
        switch (this.mControlPager.getCurrentView().getId()) {
            case R.id.layout_comprobante_debin /*2131364907*/:
                if (!this.H) {
                    this.E.showAlert();
                    return;
                } else {
                    backButtonComprobanteDebin();
                    return;
                }
            case R.id.layout_confirmar_debin /*2131364913*/:
                backButtonConfirmarDebin();
                return;
            case R.id.layout_generar_debin /*2131364931*/:
                backButtonGenerarDebin();
                return;
            case R.id.layout_ingresar_destinatario /*2131364935*/:
                backButtonIngresarDestinatario();
                return;
            case R.id.layout_sin_cuentas_debin /*2131364968*/:
                if (this.J) {
                    backButtonSinCuentasDebin();
                    return;
                } else {
                    backButtonIngresarDestinatario();
                    return;
                }
            default:
                return;
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.F32_10_btn_verificar /*2131363808*/:
                if (this.u.equalsIgnoreCase(C_B_U)) {
                    this.q.trackEvent(getString(R.string.analytics_trackevent_category_debin), getString(R.string.analytics_trackevent_action_verificar_destinatario), getString(R.string.analytics_trackevent_label_Verificar_CBU));
                    if (UtilAccount.isCBUValid(this.etCBU.getText().toString()).booleanValue()) {
                        this.w = this.etCBU.getText().toString();
                        this.etCBU.setContentDescription(CAccessibility.getInstance(getContext()).applyCBU(this.etCBU.getText().toString()));
                        gotoSetGenerarDebinView();
                        return;
                    }
                    this.q.trackScreen(getString(R.string.f219analytics_screen_name_Pantalla_error_validacin_destinatario));
                    b(l());
                    return;
                } else if (this.u.equalsIgnoreCase("Alias")) {
                    this.q.trackEvent(getString(R.string.analytics_trackevent_category_debin), getString(R.string.analytics_trackevent_action_verificar_destinatario), getString(R.string.analytics_trackevent_label_Verificar_Alias));
                    if (UtilAccount.validarAlias(this.etCBU.getText().toString())) {
                        this.v = this.etCBU.getText().toString();
                        this.etCBU.setContentDescription(CAccessibility.getInstance(getContext()).applyCBU(this.etCBU.getText().toString()));
                        gotoSetGenerarDebinView();
                        return;
                    }
                    this.q.trackScreen(getString(R.string.f219analytics_screen_name_Pantalla_error_validacin_destinatario));
                    b(l());
                    return;
                } else {
                    return;
                }
            case R.id.F32_13_LBL_DATA_CONCEPTO /*2131363815*/:
                mostrarSelectorConcepto(c(PRE_AUTORIZACIONES.CONDEBIN).getListGroupBeans());
                return;
            case R.id.F32_13_LBL_DATA_CTA_ACREDITACION /*2131363816*/:
                a(c(PRE_AUTORIZACIONES.TPOCTACORTA));
                return;
            case R.id.F32_13_LBL_DATA_FECHA_VENCIMIENTO /*2131363818*/:
                a(this.lblFechaVto);
                return;
            case R.id.F32_13_btn_continuar /*2131363828*/:
                boolean matches = this.lblDescripcion.getText().toString().matches("^.*[^a-zA-Z0-9. ].*$");
                this.q.trackEvent(getString(R.string.analytics_trackevent_category_debin), getString(R.string.analytics_trackevent_action_nuevo_debin), getString(R.string.analytics_trackevent_label_Generar_debin));
                if (matches) {
                    customErrorDialog(PagoTarjetasConstants.ISBAN_DIALOG_WARNING_TITLE, getResources().getString(R.string.MSG_USER00521_DEBIN));
                    return;
                } else {
                    h();
                    return;
                }
            case R.id.F32_14_btn_confirmar /*2131363852*/:
                i();
                return;
            case R.id.F32_15_TXT_CBU /*2131363876*/:
                if (this.M.equalsIgnoreCase("fvto")) {
                    this.M = "cbu";
                    return;
                } else {
                    this.M = "";
                    return;
                }
            case R.id.F32_15_TXT_CUIT /*2131363879*/:
                this.M = "cuit";
                return;
            case R.id.F32_15_TXT_FECHA_VENCIMIENTO /*2131363881*/:
                if (this.M.equalsIgnoreCase("cuit")) {
                    this.M = "fvto";
                    return;
                } else {
                    this.M = "";
                    return;
                }
            case R.id.F32_15_TXT_nroComprobante /*2131363884*/:
                if (this.M.equalsIgnoreCase("cbu")) {
                    this.M = "nroCom";
                    return;
                } else {
                    this.M = "";
                    return;
                }
            case R.id.F32_15_btn_volver /*2131363885*/:
                if (!this.H) {
                    this.E.showAlert();
                    return;
                }
                setResult(-1);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return;
            case R.id.F32_15_txt_title /*2131363897*/:
                if (this.M.equalsIgnoreCase("nroCom")) {
                    Toast.makeText(this, "MAIAMEEEE", 1).show();
                }
                this.M = "";
                return;
            case R.id.F32_16_adherir_cuenta /*2131363898*/:
                this.q.trackEvent(getString(R.string.analytics_trackevent_category_debin), getString(R.string.analytics_trackevent_action_generar_debin), getString(R.string.f222analytics_trackevent_label_Aadir_cuenta_debin));
                a(this.sessionManager.getConsultarAdhesionVendedorBodyResponseBean().getListaCuentasVendedorBean());
                return;
            case R.id.selectorCBU /*2131365679*/:
                this.q.trackEvent(getString(R.string.analytics_trackevent_category_debin), getString(R.string.analytics_trackevent_action_modo_ingreso), getString(R.string.analytics_trackevent_label_Seleccionar_modo_ingreso_destinatario));
                k();
                return;
            default:
                return;
        }
    }

    private void a(ListaCuentasVendedorBean listaCuentasVendedorBean) {
        Intent intent = new Intent(this, GestionAdhesionDebinActivity.class);
        intent.putExtra(DebinConstants.INTENT_EXTRA_CUENTAS_ADHERIDAS, listaCuentasVendedorBean);
        startActivityForResult(intent, 4);
    }

    private void h() {
        gotoPage(3);
        setConfirmarDebinView();
    }

    private void i() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpCconfirmarDebin", "Confirmar", getString(R.string.MSG_USER00520_DEBIN), null, null, "Si", "No", null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                NuevoDebinActivity.this.s.crearDebin("C", NuevoDebinActivity.this.I);
            }

            public void onNegativeButton() {
                NuevoDebinActivity.this.q.trackEvent(NuevoDebinActivity.this.getString(R.string.analytics_trackevent_category_debin), NuevoDebinActivity.this.getString(R.string.analytics_trackevent_action_cancelar), NuevoDebinActivity.this.getString(R.string.f226analytics_trackevent_label_Cancelar_generacin_debin));
                newInstance.dismiss();
            }
        });
        newInstance.show(getSupportFragmentManager(), "popUpCconfirmarDebin");
    }

    private void a(ListTableBean listTableBean) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (CuentaVendedor cuentaVendedor : this.z) {
            String abreviatureAndAccountFormat = UtilAccount.getAbreviatureAndAccountFormat(listTableBean, cuentaVendedor.getTipo(), cuentaVendedor.getSucursal(), cuentaVendedor.getNumero());
            arrayList.add(abreviatureAndAccountFormat);
            try {
                arrayList2.add(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(abreviatureAndAccountFormat));
            } catch (Exception unused) {
                arrayList2.add(abreviatureAndAccountFormat);
            }
        }
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("selectorCuentasPopUp", "Cuentas", null, arrayList, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, UtilAccount.getAbreviatureAndAccountFormat(listTableBean, this.B.getTipo(), this.B.getSucursal(), this.B.getNumero()), arrayList2);
        newInstance.setDialogListener(new IDialogListener() {
            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                NuevoDebinActivity.this.lblCtaAcreditacion.setText(str);
                try {
                    NuevoDebinActivity.this.lblCtaAcreditacion.setContentDescription(CAccessibility.getInstance(NuevoDebinActivity.this.getApplicationContext()).applyFilterGeneral(str));
                } catch (Exception unused) {
                    NuevoDebinActivity.this.lblCtaAcreditacion.setContentDescription(str);
                }
                NuevoDebinActivity.this.a(str, NuevoDebinActivity.this.sessionManager.getCuentasDEBINAdheridas());
            }

            public void onNegativeButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(getSupportFragmentManager(), "selectorCuentasPopUp");
    }

    /* access modifiers changed from: private */
    public void a(String str, List<CuentaVendedor> list) {
        for (CuentaVendedor cuentaVendedor : list) {
            if (UtilAccount.getAbreviatureAndAccountFormat(c(PRE_AUTORIZACIONES.TPOCTACORTA), cuentaVendedor.getTipo(), cuentaVendedor.getSucursal(), cuentaVendedor.getNumero()).equalsIgnoreCase(str)) {
                this.B = cuentaVendedor;
                if (cuentaVendedor.getTipo().equalsIgnoreCase("09") || cuentaVendedor.getTipo().equalsIgnoreCase("00") || cuentaVendedor.getTipo().equalsIgnoreCase("01")) {
                    this.etImporte.setPrefix("$");
                } else if (cuentaVendedor.getTipo().equalsIgnoreCase("10") || cuentaVendedor.getTipo().equalsIgnoreCase("04") || cuentaVendedor.getTipo().equalsIgnoreCase("03")) {
                    this.etImporte.setPrefix("U$S");
                }
            }
        }
    }

    private void j() {
        ArrayList arrayList = new ArrayList();
        if (this.A.getCodMonedaOperacion().equalsIgnoreCase("1")) {
            arrayList.addAll(b("09", this.sessionManager.getCuentasDEBINAdheridas()));
            arrayList.addAll(b("01", this.sessionManager.getCuentasDEBINAdheridas()));
            arrayList.addAll(b("00", this.sessionManager.getCuentasDEBINAdheridas()));
            this.z = arrayList;
        } else if (this.A.getCodMonedaOperacion().equalsIgnoreCase("2")) {
            arrayList.addAll(b("10", this.sessionManager.getCuentasDEBINAdheridas()));
            arrayList.addAll(b("04", this.sessionManager.getCuentasDEBINAdheridas()));
            arrayList.addAll(b("03", this.sessionManager.getCuentasDEBINAdheridas()));
            this.z = arrayList;
        } else if (this.A.getCodMonedaOperacion().equalsIgnoreCase("3")) {
            this.sessionManager.getCuentasDEBINAdheridas();
        }
    }

    private List<CuentaVendedor> b(String str, List<CuentaVendedor> list) {
        ArrayList arrayList = new ArrayList();
        for (CuentaVendedor cuentaVendedor : list) {
            if (cuentaVendedor.getTipo().equalsIgnoreCase(str)) {
                arrayList.add(cuentaVendedor);
            }
        }
        return arrayList;
    }

    private void k() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(C_B_U);
        arrayList.add("Alias");
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("selectorCBUoAliasPopUp", "Seleccionar", null, arrayList, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, this.u, arrayList);
        newInstance.setDialogListener(new IDialogListener() {
            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                NuevoDebinActivity.this.txtSeleccionCBU.setText(str);
                NuevoDebinActivity.this.u = str;
                try {
                    NuevoDebinActivity.this.txtSeleccionCBU.setContentDescription(CAccessibility.getInstance(NuevoDebinActivity.this.getContext()).applyFilterGeneral(NuevoDebinActivity.this.txtSeleccionCBU.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (NuevoDebinActivity.this.u.equalsIgnoreCase("Alias")) {
                    NuevoDebinActivity.this.etCBU.setText("");
                    NuevoDebinActivity.this.etCBU.setHint(R.string.ID_4461_DEBIN_ALIASDELDESTINATARIO);
                    NuevoDebinActivity.this.txtAyuda.setText(R.string.ID_4462_DEBIN_ELALIASCONSTADEENTRE6Y20CARACTERESALFANUMERICOS);
                    try {
                        NuevoDebinActivity.this.txtAyuda.setContentDescription(CAccessibility.getInstance(NuevoDebinActivity.this.getContext()).applyFilterGeneral(NuevoDebinActivity.this.txtAyuda.getText().toString()));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    NuevoDebinActivity.this.etCBU.setInputType(1);
                    NuevoDebinActivity.this.etCBU.setFilters(new InputFilter[]{new LengthFilter(20)});
                    try {
                        NuevoDebinActivity.this.etCBU.setContentDescription(CAccessibility.getInstance(NuevoDebinActivity.this.getContext()).applyFilterGeneral(NuevoDebinActivity.this.etCBU.getHint().toString()));
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } else if (NuevoDebinActivity.this.u.equalsIgnoreCase(NuevoDebinActivity.C_B_U)) {
                    NuevoDebinActivity.this.etCBU.setText("");
                    NuevoDebinActivity.this.etCBU.setHint(R.string.ID_4458_DEBIN_CBUDELDESTINATARIO);
                    NuevoDebinActivity.this.txtAyuda.setText(R.string.ID_4459_DEBIN_ELCBUCONSTADE22DIGITOS);
                    try {
                        NuevoDebinActivity.this.txtAyuda.setContentDescription(CAccessibility.getInstance(NuevoDebinActivity.this.getContext()).applyCBU(NuevoDebinActivity.this.txtAyuda.getText().toString()));
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                    NuevoDebinActivity.this.etCBU.setInputType(2);
                    NuevoDebinActivity.this.etCBU.setFilters(new InputFilter[]{new LengthFilter(22)});
                    NuevoDebinActivity.this.etCBU.setContentDescription(CAccessibility.getInstance(NuevoDebinActivity.this.getContext()).applyCBU(NuevoDebinActivity.this.etCBU.getHint().toString()));
                }
            }

            public void onNegativeButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(getSupportFragmentManager(), "selectorCBUoAliasPopUp");
    }

    private String l() {
        if (this.u.equalsIgnoreCase(C_B_U)) {
            return getString(R.string.MSG_USER00518_DEBIN);
        }
        return this.u.equalsIgnoreCase("Alias") ? getString(R.string.MSG_USER00519_DEBIN) : "";
    }

    private void b(String str) {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpcbuInvalido", "Atención", str, null, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, null, null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(getSupportFragmentManager(), "popUpcbuInvalido");
    }

    private ListTableBean c(String str) {
        ListTableBean listTableBean = new ListTableBean();
        for (ListTableBean listTableBean2 : this.x) {
            if (listTableBean2.getIdTable().equalsIgnoreCase(str)) {
                listTableBean = listTableBean2;
            }
        }
        return listTableBean;
    }

    private String a(String str, String str2) {
        String str3 = new String();
        for (ListTableBean listTableBean : this.x) {
            if (listTableBean.getIdTable().equalsIgnoreCase(str2)) {
                for (ListGroupBean listGroupBean : listTableBean.getListGroupBeans()) {
                    if (listGroupBean.getCode().equalsIgnoreCase(str)) {
                        str3 = listGroupBean.getLabel();
                    }
                }
            }
        }
        return str3;
    }

    private String b(String str, String str2) {
        String str3 = new String();
        for (ListTableBean listTableBean : this.x) {
            if (listTableBean.getIdTable().equalsIgnoreCase(str2)) {
                for (ListGroupBean listGroupBean : listTableBean.getListGroupBeans()) {
                    if (listGroupBean.getLabel().equalsIgnoreCase(str)) {
                        str3 = listGroupBean.getCode();
                    }
                }
            }
        }
        return str3;
    }

    public IsbanDatePickerDialogFragment crearSelectorFecha(final TextView textView) {
        IsbanDatePickerDialogFragment newInstance = IsbanDatePickerDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_SELECCION_FECHA), UtilDate.getDateFormat(textView.getText().toString(), Constants.FORMAT_DATE_DASH, Constants.FORMAT_DATE_APP_2), Constants.FORMAT_DATE_APP_2);
        newInstance.setDialogListener(new IDateDialogListener() {
            public void onDateSelected(Date date) {
                if (NuevoDebinActivity.this.a(UtilDate.getDateFormat(date, Constants.FORMAT_DATE_WS_1))) {
                    textView.setText(UtilDate.getDateFormat(date, Constants.FORMAT_DATE_APP_2));
                    NuevoDebinActivity.this.C = UtilDate.getDateFormat(UtilDate.getDateFormat(date, Constants.FORMAT_DATE_APP_2), Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_WS_1);
                    return;
                }
                textView.setText(UtilDate.getDateFormat(Calendar.getInstance().getTime(), Constants.FORMAT_DATE_APP_2));
                NuevoDebinActivity.this.C = UtilDate.getDateFormat(UtilDate.getDateFormat(Calendar.getInstance().getTime(), Constants.FORMAT_DATE_APP_2), Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_WS_1);
            }
        });
        return newInstance;
    }

    private void a(TextView textView) {
        IsbanDatePickerDialogFragment crearSelectorFecha = crearSelectorFecha(textView);
        getFragmentManager();
        crearSelectorFecha.show(getSupportFragmentManager(), this.p);
    }

    public void mostrarSelectorConcepto(List<ListGroupBean> list) {
        ArrayList arrayList = new ArrayList();
        for (ListGroupBean label : list) {
            arrayList.add(label.getLabel());
        }
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("selectorConceptoPopUp", "Concepto", null, arrayList, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, this.y, arrayList);
        newInstance.setDialogListener(new IDialogListener() {
            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                NuevoDebinActivity.this.lblConcepto.setText(str);
                NuevoDebinActivity.this.y = str;
            }

            public void onNegativeButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(getSupportFragmentManager(), "selectorConceptoPopUp");
    }

    private void m() {
        this.E = n();
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        this.F = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_TITLE), null, arrayList, getString(R.string.ID_4109_SEGUROS_LBL_CANCELAR), null, null, null, arrayList);
        this.F.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(NuevoDebinActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                    NuevoDebinActivity.this.E.optionShareSelected();
                } else if (str.equalsIgnoreCase(NuevoDebinActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                    NuevoDebinActivity.this.E.optionDownloadSelected();
                }
            }
        });
        this.F.setCancelable(true);
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.E.onRequestPermissionsResult(i, strArr, iArr);
    }

    private OptionsToShare n() {
        return new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public View getViewToShare() {
                return NuevoDebinActivity.this.comprobanteDebin;
            }

            public void receiveIntentAppShare(Intent intent) {
                NuevoDebinActivity.this.startActivity(Intent.createChooser(intent, NuevoDebinActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
            }

            public String getFileName() {
                return Html.fromHtml(NuevoDebinActivity.this.getString(R.string.AC_DEBIN_DEBIN_COMP_TITTLE_ARCHIVO).concat(NuevoDebinActivity.this.G)).toString();
            }

            public String getSubjectReceiptToShare() {
                String string = NuevoDebinActivity.this.getString(R.string.DEBIN_COMP_TITTLE);
                StringBuilder sb = new StringBuilder();
                sb.append(" - ");
                sb.append(NuevoDebinActivity.this.G);
                return Html.fromHtml(string.concat(sb.toString())).toString();
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                NuevoDebinActivity.this.H = true;
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                NuevoDebinActivity.this.H = true;
            }

            public void onAbortShare() {
                super.onAbortShare();
                NuevoDebinActivity.this.H = true;
                NuevoDebinActivity.this.onBackPressed();
            }
        };
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (!activityResultHandler(i2, intent) && i == 4) {
            finish();
        }
    }

    public void setLayoutSinCuentasVisible() {
        this.layout_sinCuentas.setVisibility(0);
    }

    public static void setUpLabel(String str, LinearLayout linearLayout, TextView textView) {
        setUpLabel(str, linearLayout, textView, null, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x005d A[Catch:{ Exception -> 0x009d }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x005e A[Catch:{ Exception -> 0x009d }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x006e A[Catch:{ Exception -> 0x009d }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x007e A[Catch:{ Exception -> 0x009d }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x008e A[Catch:{ Exception -> 0x009d }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void setUpLabel(java.lang.String r2, android.widget.LinearLayout r3, android.widget.TextView r4, java.lang.String r5, android.content.Context r6) {
        /*
            if (r2 != 0) goto L_0x0009
            r2 = 8
            r3.setVisibility(r2)
            goto L_0x009d
        L_0x0009:
            r0 = 0
            r3.setVisibility(r0)
            r4.setText(r2)
            if (r5 == 0) goto L_0x009d
            if (r6 == 0) goto L_0x009d
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r2 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r6)
            r3 = -1
            int r6 = r5.hashCode()     // Catch:{ Exception -> 0x009d }
            r1 = -436740454(0xffffffffe5f7de9a, float:-1.4631635E23)
            if (r6 == r1) goto L_0x004f
            r1 = 2090926(0x1fe7ae, float:2.930011E-39)
            if (r6 == r1) goto L_0x0045
            r1 = 523665477(0x1f368045, float:3.86461E-20)
            if (r6 == r1) goto L_0x003b
            r1 = 1934443608(0x734d4458, float:1.6262925E31)
            if (r6 == r1) goto L_0x0032
            goto L_0x0059
        L_0x0032:
            java.lang.String r6 = "AMOUNT"
            boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x009d }
            if (r5 == 0) goto L_0x0059
            goto L_0x005a
        L_0x003b:
            java.lang.String r6 = "CHARACTERTOCHARACTER"
            boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x009d }
            if (r5 == 0) goto L_0x0059
            r0 = 1
            goto L_0x005a
        L_0x0045:
            java.lang.String r6 = "DATE"
            boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x009d }
            if (r5 == 0) goto L_0x0059
            r0 = 2
            goto L_0x005a
        L_0x004f:
            java.lang.String r6 = "PERCENTAGE"
            boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x009d }
            if (r5 == 0) goto L_0x0059
            r0 = 3
            goto L_0x005a
        L_0x0059:
            r0 = -1
        L_0x005a:
            switch(r0) {
                case 0: goto L_0x008e;
                case 1: goto L_0x007e;
                case 2: goto L_0x006e;
                case 3: goto L_0x005e;
                default: goto L_0x005d;
            }     // Catch:{ Exception -> 0x009d }
        L_0x005d:
            goto L_0x009d
        L_0x005e:
            java.lang.CharSequence r3 = r4.getText()     // Catch:{ Exception -> 0x009d }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x009d }
            java.lang.String r2 = r2.applyFilterTasaValue(r3)     // Catch:{ Exception -> 0x009d }
            r4.setContentDescription(r2)     // Catch:{ Exception -> 0x009d }
            goto L_0x009d
        L_0x006e:
            java.lang.CharSequence r3 = r4.getText()     // Catch:{ Exception -> 0x009d }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x009d }
            java.lang.String r2 = r2.applyFilterDate(r3)     // Catch:{ Exception -> 0x009d }
            r4.setContentDescription(r2)     // Catch:{ Exception -> 0x009d }
            goto L_0x009d
        L_0x007e:
            java.lang.CharSequence r3 = r4.getText()     // Catch:{ Exception -> 0x009d }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x009d }
            java.lang.String r2 = r2.applyFilterCharacterToCharacter(r3)     // Catch:{ Exception -> 0x009d }
            r4.setContentDescription(r2)     // Catch:{ Exception -> 0x009d }
            goto L_0x009d
        L_0x008e:
            java.lang.CharSequence r3 = r4.getText()     // Catch:{ Exception -> 0x009d }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x009d }
            java.lang.String r2 = r2.applyFilterAmount(r3)     // Catch:{ Exception -> 0x009d }
            r4.setContentDescription(r2)     // Catch:{ Exception -> 0x009d }
        L_0x009d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.NuevoDebinActivity.setUpLabel(java.lang.String, android.widget.LinearLayout, android.widget.TextView, java.lang.String, android.content.Context):void");
    }
}
