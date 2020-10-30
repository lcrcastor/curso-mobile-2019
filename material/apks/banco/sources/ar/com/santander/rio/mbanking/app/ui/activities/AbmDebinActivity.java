package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
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
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.debin.abmdebin.AbmComprobanteDebinPresenter;
import ar.com.santander.rio.mbanking.app.module.debin.abmdebin.AbmComprobanteDebinView;
import ar.com.santander.rio.mbanking.app.module.debin.abmdebin.AbmConfirmacionDebinPresenter;
import ar.com.santander.rio.mbanking.app.module.debin.abmdebin.AbmConfirmacionDebinView;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.PRE_AUTORIZACIONES;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinCompradorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinVendedorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DetalleDebinBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListDebinesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class AbmDebinActivity extends MvpPrivateMenuActivity implements OnClickListener, AbmComprobanteDebinView, AbmConfirmacionDebinView {
    /* access modifiers changed from: private */
    public OptionsToShare A;
    /* access modifiers changed from: private */
    public boolean B = false;
    /* access modifiers changed from: private */
    public IsbanDialogFragment C;
    @InjectView(2131363931)
    TextView CBU;
    @InjectView(2131363966)
    TextView CBUComprobanteAnulacion;
    @InjectView(2131363926)
    Button btnConfirmar;
    @InjectView(2131363961)
    Button btnVolver;
    @InjectView(2131363962)
    ScrollView comprobanteAbm;
    @InjectView(2131363972)
    TextView lblCBUC;
    @InjectView(2131363937)
    TextView lblCBUV;
    @InjectView(2131363973)
    TextView lblConceptoC;
    @InjectView(2131363938)
    TextView lblConceptoV;
    @InjectView(2131363930)
    TextView lblCuentaDebito;
    @InjectView(2131363965)
    TextView lblCuentaDebitoC;
    @InjectView(2131363968)
    TextView lblDataAliasC;
    @InjectView(2131363933)
    TextView lblDataAliasV;
    @InjectView(2131363939)
    TextView lblDataDescripcionV;
    @InjectView(2131363974)
    TextView lblEstadoC;
    @InjectView(2131363940)
    TextView lblEstadoV;
    @InjectView(2131363975)
    TextView lblFechaOperacionC;
    @InjectView(2131363976)
    TextView lblFechaSolicitudC;
    @InjectView(2131363941)
    TextView lblFechaSolicitudV;
    @InjectView(2131363977)
    TextView lblFechaVencimientoC;
    @InjectView(2131363942)
    TextView lblFechaVencimientoV;
    @InjectView(2131363978)
    TextView lblIdDebinC;
    @InjectView(2131363943)
    TextView lblIdDebinV;
    @InjectView(2131363986)
    TextView lblImporteC;
    @InjectView(2131363950)
    TextView lblImporteV;
    @InjectView(2131363979)
    TextView lblLeyendaC;
    @InjectView(2131363987)
    TextView lblMonedaC;
    @InjectView(2131363951)
    TextView lblMonedaV;
    @InjectView(2131363971)
    TextView lblSaldoCDebitoC;
    @InjectView(2131363936)
    TextView lblSaldoCDebitoV;
    @InjectView(2131363989)
    TextView lblSolicitanteC;
    @InjectView(2131363990)
    TextView lblSolicitanteDatoC;
    @InjectView(2131363953)
    TextView lblSolicitanteDatoV;
    @InjectView(2131363952)
    TextView lblSolicitanteV;
    @InjectView(2131363997)
    TextView lblTituloC;
    @InjectView(2131363954)
    TextView lblTituloV;
    @InjectView(2131363969)
    TextView lblbancoC;
    @InjectView(2131363934)
    TextView lblbancoV;
    @InjectView(2131363970)
    TextView lblcDebitoC;
    @InjectView(2131363935)
    TextView lblcDebitoV;
    @InjectView(2131363980)
    TextView lblnroComprobanteC;
    @InjectView(2131363944)
    TextView leyendaConfirmar;
    @InjectView(2131364111)
    ViewFlipper mControlPager;
    @Inject
    AnalyticsManager p;
    private String q;
    private AbmConfirmacionDebinPresenter r;
    @InjectView(2131363955)
    RelativeLayout relativeAbm;
    @InjectView(2131363991)
    RelativeLayout relativeComprobante;
    @InjectView(2131363992)
    LinearLayout rowAliasC;
    @InjectView(2131363956)
    LinearLayout rowAliasV;
    @InjectView(2131363958)
    LinearLayout rowBanco;
    @InjectView(2131363993)
    LinearLayout rowBancoC;
    @InjectView(2131363957)
    LinearLayout rowDescripcionV;
    @InjectView(2131363994)
    LinearLayout rowEstadoC;
    @InjectView(2131363995)
    LinearLayout rowLeyendaC;
    private AbmComprobanteDebinPresenter s;
    private DetalleDebinBean t;
    private DetalleDebinBean u = new DetalleDebinBean();
    private List<ListTableBean> v = new ArrayList();
    private ListDebinesBean w;
    /* access modifiers changed from: private */
    public String x = "";
    /* access modifiers changed from: private */
    public String y;
    private String z;

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
        return R.layout.layout_abm_debin;
    }

    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        ButterKnife.inject((Activity) this);
        initialize();
    }

    public void initialize() {
        this.r = new AbmConfirmacionDebinPresenter(this.mBus, this.mDataManager, getContext());
        this.s = new AbmComprobanteDebinPresenter(this.mBus, this.mDataManager);
        this.t = (DetalleDebinBean) getIntent().getParcelableExtra(DebinConstants.INTENT_EXTRA_DETALLE_DEBIN);
        this.w = (ListDebinesBean) getIntent().getParcelableExtra(DebinConstants.INTENT_EXTRA_DEBIN);
        this.y = getIntent().getStringExtra(DebinConstants.INTENT_EXTRA_TIPO_ACCION);
        this.z = getIntent().getStringExtra(DebinConstants.INTENT_EXTRA_LEYENDA);
        this.v = this.sessionManager.getConsDescripciones().getListTableBeans();
        goToConfirmarView(this.t);
        try {
            this.CBU.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterCharacterToCharacter(this.CBU.getText().toString()));
            this.CBUComprobanteAnulacion.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterCharacterToCharacter(this.CBUComprobanteAnulacion.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FragmentManager getSupportFragmentManager() {
        return super.getSupportFragmentManager();
    }

    public void goToConfirmarView(DetalleDebinBean detalleDebinBean) {
        gotoPage(0);
        this.r.onCreatePage(detalleDebinBean);
    }

    public void setActionBarConfirmacion() {
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
                    AbmDebinActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonConfirmar() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void setConfirmarDebinView(DetalleDebinBean detalleDebinBean) {
        setActionBarConfirmacion();
        String str = "";
        String str2 = "";
        String str3 = "";
        this.u = this.t.clone();
        if (this.y.equalsIgnoreCase("P")) {
            this.p.trackScreen(getString(R.string.analytics_screen_name_Confirmar_pago_Debin_F_32_30));
            this.lblTituloV.setText(R.string.ID_4435_DEBIN_CONFIRMARPAGODEDEBIN);
            this.lblCuentaDebito.setText(R.string.ID_4424_DEBIN_CUENTADEBITO);
            this.leyendaConfirmar.setText(Html.fromHtml(this.z));
        } else if (this.y.equalsIgnoreCase("R")) {
            this.p.trackScreen(getString(R.string.analytics_screen_name_Confirmar_rechazo_Debin_F_32_40));
            this.lblTituloV.setText(R.string.ID_4441_DEBIN_CONFIRMARRECHAZODEDEBIN);
            this.lblCuentaDebito.setText(R.string.ID_4424_DEBIN_CUENTADEBITO);
        }
        if (this.y.equalsIgnoreCase("A")) {
            this.p.trackScreen(getString(R.string.f216analytics_screen_name_Confirmar_anulacin_Debin_F_32_50));
            this.lblTituloV.setText(R.string.ID_4443_DEBIN_CONFIRMARANULACIONDEDEBIN);
            str = detalleDebinBean.getVendedorBean().getCuentaVendedor().getTipo();
            str2 = detalleDebinBean.getVendedorBean().getCuentaVendedor().getNumero();
            str3 = detalleDebinBean.getVendedorBean().getCuentaVendedor().getSucursal();
            this.lblSolicitanteV.setText(detalleDebinBean.getCompradorBean().getTitular());
            this.lblSolicitanteDatoV.setText(detalleDebinBean.getCompradorBean().getCuit());
            this.lblCBUV.setText(detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getCbu());
            this.lblSaldoCDebitoV.setVisibility(8);
            this.lblCuentaDebito.setText(R.string.ID_4433_DEBIN_CUENTADEACREDITACION);
            if (detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getAlias() != null) {
                this.rowAliasV.setVisibility(0);
                this.lblDataAliasV.setText(detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getAlias());
            } else {
                this.rowAliasV.setVisibility(8);
            }
            if (detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getBanco() != null) {
                this.lblbancoV.setText(detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getBanco());
                this.rowBanco.setVisibility(0);
            } else {
                this.rowBanco.setVisibility(8);
            }
        } else if (this.y.equalsIgnoreCase("P") || this.y.equalsIgnoreCase("R")) {
            str = detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getTipo();
            str2 = detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getNumero();
            str3 = detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getSucursal();
            this.lblSolicitanteV.setText(detalleDebinBean.getVendedorBean().getTitular());
            this.lblSolicitanteDatoV.setText(detalleDebinBean.getVendedorBean().getCuit());
            this.lblCBUV.setText(detalleDebinBean.getVendedorBean().getCuentaVendedor().getCbu());
            if (detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getSaldo() != null) {
                TextView textView = this.lblSaldoCDebitoV;
                StringBuilder sb = new StringBuilder();
                sb.append(a(detalleDebinBean.getMoneda(), PRE_AUTORIZACIONES.MONEDADESCSIMBOLO, this.v));
                sb.append(detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getSaldo());
                textView.setText(sb.toString());
            } else {
                this.lblSaldoCDebitoV.setVisibility(8);
            }
            if (detalleDebinBean.getVendedorBean().getCuentaVendedor().getAlias() != null) {
                this.rowAliasV.setVisibility(0);
                this.lblDataAliasV.setText(detalleDebinBean.getVendedorBean().getCuentaVendedor().getAlias());
            } else {
                this.rowAliasV.setVisibility(8);
            }
            if (detalleDebinBean.getVendedorBean().getCuentaVendedor().getBanco() != null) {
                this.rowBanco.setVisibility(0);
                this.lblbancoV.setText(detalleDebinBean.getVendedorBean().getCuentaVendedor().getBanco());
            } else {
                this.rowBanco.setVisibility(8);
            }
        }
        if (detalleDebinBean.getDescripcion() != null) {
            this.rowDescripcionV.setVisibility(0);
            this.lblDataDescripcionV.setText(detalleDebinBean.getDescripcion());
        } else {
            this.rowDescripcionV.setVisibility(8);
        }
        this.lblcDebitoV.setText(UtilAccount.getAccountFormat(detalleDebinBean.getVendedorBean().getCuentaVendedor().getSucursal(), detalleDebinBean.getVendedorBean().getCuentaVendedor().getNumeroCuenta()));
        this.lblcDebitoV.setText(UtilAccount.getAbreviatureAndAccountFormat(a(PRE_AUTORIZACIONES.TPOCTACORTA), str, str3, str2));
        this.lblFechaSolicitudV.setText(detalleDebinBean.getFechaCreacion());
        this.lblFechaVencimientoV.setText(detalleDebinBean.getFechaVencimiento());
        this.lblIdDebinV.setText(this.w.getIdDebin());
        this.lblEstadoV.setText(a(detalleDebinBean.getCodEstado(), "ESTDEBIN", this.v));
        this.lblConceptoV.setText(a(detalleDebinBean.getCodConcepto(), PRE_AUTORIZACIONES.CONDEBIN, this.v));
        TextView textView2 = this.lblMonedaV;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(a(detalleDebinBean.getMoneda(), PRE_AUTORIZACIONES.MONEDADESCSIMBOLO, this.v));
        sb2.append(UtilsCuentas.SEPARAOR2);
        textView2.setText(sb2.toString());
        this.lblImporteV.setText(detalleDebinBean.getImporte());
        this.btnConfirmar.setOnClickListener(this);
        if (this.y.equalsIgnoreCase("D")) {
            this.p.trackScreen(getString(R.string.analytics_screen_name_Confirmar_desconocimiento_Debin_F_32_50));
            this.lblTituloV.setText(R.string.ID_4443_DEBIN_DESCONOCIMIENTODEDEBIN);
            String tipo = detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getTipo();
            String numero = detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getNumero();
            String sucursal = detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getSucursal();
            this.lblSolicitanteV.setText(detalleDebinBean.getVendedorBean().getTitular());
            this.lblSolicitanteDatoV.setText(detalleDebinBean.getVendedorBean().getCuit());
            this.lblCBUV.setText(detalleDebinBean.getVendedorBean().getCuentaVendedor().getCbu());
            if (!TextUtils.isEmpty(this.z)) {
                this.leyendaConfirmar.setText(Html.fromHtml(this.z));
                this.leyendaConfirmar.setVisibility(0);
            } else {
                this.leyendaConfirmar.setVisibility(8);
                this.leyendaConfirmar.setText("");
            }
            if (detalleDebinBean.getVendedorBean().getCuentaVendedor().getAlias() != null) {
                this.rowAliasV.setVisibility(0);
                this.lblDataAliasV.setText(detalleDebinBean.getVendedorBean().getCuentaVendedor().getAlias());
            } else {
                this.rowAliasV.setVisibility(8);
            }
            if (detalleDebinBean.getVendedorBean().getCuentaVendedor().getBanco() != null) {
                this.rowBanco.setVisibility(0);
                this.lblbancoV.setText(detalleDebinBean.getVendedorBean().getCuentaVendedor().getBanco());
            } else {
                this.rowBanco.setVisibility(8);
            }
            this.lblFechaSolicitudV.setText(detalleDebinBean.getFechaCreacion());
            this.lblFechaVencimientoV.setText(detalleDebinBean.getFechaVencimiento());
            this.lblIdDebinV.setText(this.w.getIdDebin());
            if (detalleDebinBean.getDescripcion() != null) {
                this.rowDescripcionV.setVisibility(0);
                this.lblDataDescripcionV.setText(Html.fromHtml(detalleDebinBean.getDescripcion()));
            } else {
                this.rowDescripcionV.setVisibility(8);
            }
            this.lblEstadoV.setText(a(detalleDebinBean.getCodEstado(), "ESTDEBIN", this.v));
            this.lblConceptoV.setText(a(detalleDebinBean.getCodConcepto(), PRE_AUTORIZACIONES.CONDEBIN, this.v));
            TextView textView3 = this.lblMonedaV;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a(detalleDebinBean.getMoneda(), PRE_AUTORIZACIONES.MONEDADESCSIMBOLO, this.v));
            sb3.append(UtilsCuentas.SEPARAOR2);
            textView3.setText(sb3.toString());
            this.lblImporteV.setText(detalleDebinBean.getImporte());
            this.lblcDebitoV.setText(UtilAccount.getAbreviatureAndAccountFormat(a(PRE_AUTORIZACIONES.TPOCTACORTA), tipo, sucursal, numero));
            if (detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getSaldo() != null) {
                this.lblSaldoCDebitoV.setVisibility(0);
                TextView textView4 = this.lblSaldoCDebitoV;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(a(detalleDebinBean.getMoneda(), PRE_AUTORIZACIONES.MONEDADESCSIMBOLO, this.v));
                sb4.append(detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getSaldo());
                textView4.setText(sb4.toString());
            } else {
                this.lblSaldoCDebitoV.setVisibility(8);
            }
        }
        try {
            this.leyendaConfirmar.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.leyendaConfirmar.getText().toString()));
            RelativeLayout relativeLayout = this.relativeAbm;
            CAccessibility instance = CAccessibility.getInstance(getApplicationContext());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(this.lblMonedaV.getText().toString());
            sb5.append(UtilsCuentas.SEPARAOR2);
            sb5.append(this.lblImporteV.getText().toString());
            relativeLayout.setContentDescription(instance.applyFilterGeneral(sb5.toString()));
            this.lblDataDescripcionV.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.lblDataDescripcionV.getText().toString()));
            this.lblIdDebinV.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblIdDebinV.getText().toString()));
            this.lblcDebitoV.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.lblcDebitoV.getText().toString()));
            this.lblSaldoCDebitoV.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.lblSaldoCDebitoV.getText().toString()));
            this.lblSolicitanteDatoV.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblSolicitanteDatoV.getText().toString()));
            this.lblTituloV.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.lblTituloV.getText().toString()));
            this.lblFechaSolicitudV.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterDate(this.lblFechaSolicitudV.getText().toString()));
            this.lblFechaVencimientoV.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterDate(this.lblFechaVencimientoV.getText().toString()));
            this.lblCBUV.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblCBUV.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.btnConfirmar.setOnClickListener(this);
    }

    public void gotoComprobanteAbmDebin(AbmDebinVendedorBodyResponseBean abmDebinVendedorBodyResponseBean, AbmDebinCompradorBodyResponseBean abmDebinCompradorBodyResponseBean) {
        gotoPage(1);
        this.s.onCreatePage(abmDebinVendedorBodyResponseBean, abmDebinCompradorBodyResponseBean);
    }

    public void setAbmComprobanteDebinView(AbmDebinVendedorBodyResponseBean abmDebinVendedorBodyResponseBean, AbmDebinCompradorBodyResponseBean abmDebinCompradorBodyResponseBean) {
        setActionBarComprobanteAbm();
        String str = "";
        String str2 = "";
        String str3 = "";
        if (this.y.equalsIgnoreCase("P")) {
            this.p.trackScreen(getString(R.string.analytics_screen_name_Comprobante_pago_Debin_F_32_31));
            this.lblTituloC.setText(R.string.ID_4437_DEBIN_COMPROBANTEDEPAGODEDEBINTITLE);
            this.lblCuentaDebitoC.setText(R.string.ID_4424_DEBIN_CUENTADEBITO);
        } else if (this.y.equalsIgnoreCase("R")) {
            this.p.trackScreen(getString(R.string.analytics_screen_name_Comprobante_rechazo_Debin_32_41));
            this.lblTituloC.setText(R.string.ID_4442_DEBIN_COMPROBANTEDERECHAZODEDEBINTITLE);
            this.lblCuentaDebitoC.setText(R.string.ID_4424_DEBIN_CUENTADEBITO);
        }
        if (this.y.equalsIgnoreCase("A")) {
            this.p.trackScreen(getString(R.string.f212analytics_screen_name_Comprobante_anulacin_Debin_F_32_51));
            this.lblTituloC.setText(R.string.ID_4444_DEBIN_COMPROBANTEDEANULACIONDEDEBINTITLE);
            this.lblFechaOperacionC.setText(abmDebinVendedorBodyResponseBean.getFechaOperacion());
            this.q = abmDebinVendedorBodyResponseBean.getFechaOperacion();
            this.lblnroComprobanteC.setText(abmDebinVendedorBodyResponseBean.getNroComprobante());
            this.x = abmDebinVendedorBodyResponseBean.getNroComprobante();
            this.lblCBUC.setText(this.u.getCompradorBean().getCuentaCompradorBean().getCbu());
            this.lblSaldoCDebitoC.setVisibility(8);
            this.lblCuentaDebitoC.setText(R.string.ID_4433_DEBIN_CUENTADEACREDITACION);
            if (this.u.getCompradorBean().getCuentaCompradorBean().getAlias() != null) {
                this.rowAliasC.setVisibility(0);
                this.lblDataAliasC.setText(this.u.getCompradorBean().getCuentaCompradorBean().getAlias());
            } else {
                this.rowAliasC.setVisibility(8);
            }
            if (this.u.getCompradorBean().getCuentaCompradorBean().getBanco() != null) {
                this.rowBancoC.setVisibility(0);
                this.lblbancoC.setText(this.u.getCompradorBean().getCuentaCompradorBean().getBanco());
            } else {
                this.rowBancoC.setVisibility(8);
            }
            this.lblSolicitanteC.setText(this.u.getCompradorBean().getTitular());
            this.lblSolicitanteDatoC.setText(this.u.getCompradorBean().getCuit());
            str = this.u.getVendedorBean().getCuentaVendedor().getTipo();
            str2 = this.u.getVendedorBean().getCuentaVendedor().getNumero();
            str3 = this.u.getVendedorBean().getCuentaVendedor().getSucursal();
        } else if (this.y.equalsIgnoreCase("P") || this.y.equalsIgnoreCase("R")) {
            this.lblLeyendaC.setText(abmDebinCompradorBodyResponseBean.getLeyendaComp());
            this.lblFechaOperacionC.setText(abmDebinCompradorBodyResponseBean.getFechaOperacion());
            this.q = abmDebinCompradorBodyResponseBean.getFechaOperacion();
            this.lblnroComprobanteC.setText(abmDebinCompradorBodyResponseBean.getNroComprobante());
            this.x = abmDebinCompradorBodyResponseBean.getNroComprobante();
            this.lblCBUC.setText(this.u.getVendedorBean().getCuentaVendedor().getCbu());
            this.lblSaldoCDebitoC.setVisibility(8);
            if (this.u.getVendedorBean().getCuentaVendedor().getAlias() != null) {
                this.rowAliasC.setVisibility(0);
                this.lblDataAliasC.setText(this.u.getVendedorBean().getCuentaVendedor().getAlias());
            } else {
                this.rowAliasC.setVisibility(8);
            }
            if (this.u.getVendedorBean().getCuentaVendedor().getBanco() != null) {
                this.rowBancoC.setVisibility(0);
                this.lblbancoC.setText(this.u.getVendedorBean().getCuentaVendedor().getBanco());
            } else {
                this.rowBancoC.setVisibility(8);
            }
            this.lblSolicitanteC.setText(this.u.getVendedorBean().getTitular());
            this.lblSolicitanteDatoC.setText(this.u.getVendedorBean().getCuit());
            str = this.u.getCompradorBean().getCuentaCompradorBean().getTipo();
            str2 = this.u.getCompradorBean().getCuentaCompradorBean().getNumero();
            str3 = this.u.getCompradorBean().getCuentaCompradorBean().getSucursal();
        }
        if (TextUtils.isEmpty(abmDebinVendedorBodyResponseBean.getLeyendaComp())) {
            this.rowLeyendaC.setVisibility(8);
        } else {
            this.rowLeyendaC.setVisibility(0);
            this.lblLeyendaC.setText(abmDebinVendedorBodyResponseBean.getLeyendaComp());
        }
        this.lblFechaSolicitudC.setText(this.u.getFechaCreacion());
        this.lblFechaVencimientoC.setText(this.u.getFechaVencimiento());
        this.lblIdDebinC.setText(this.w.getIdDebin());
        this.rowEstadoC.setVisibility(8);
        this.lblConceptoC.setText(a(this.u.getCodConcepto(), PRE_AUTORIZACIONES.CONDEBIN, this.v));
        TextView textView = this.lblMonedaC;
        StringBuilder sb = new StringBuilder();
        sb.append(a(this.u.getMoneda(), PRE_AUTORIZACIONES.MONEDADESCSIMBOLO, this.v));
        sb.append(UtilsCuentas.SEPARAOR2);
        textView.setText(sb.toString());
        this.lblImporteC.setText(this.u.getImporte());
        this.lblcDebitoC.setText(UtilAccount.getAccountFormat(this.u.getVendedorBean().getCuentaVendedor().getSucursal(), this.u.getVendedorBean().getCuentaVendedor().getNumeroCuenta()));
        this.lblcDebitoC.setText(UtilAccount.getAbreviatureAndAccountFormat(a(PRE_AUTORIZACIONES.TPOCTACORTA), str, str3, str2));
        if (this.y.equalsIgnoreCase("D")) {
            this.p.trackScreen(getString(R.string.analytics_screen_name_Comprobante_desconocimiento_Debin_F_32_31));
            this.lblTituloC.setText(R.string.ID_4443_DEBIN_COMPROBANTE_DESCONOCIMIENTODEDEBIN);
            String tipo = this.u.getCompradorBean().getCuentaCompradorBean().getTipo();
            String numero = this.u.getCompradorBean().getCuentaCompradorBean().getNumero();
            String sucursal = this.u.getCompradorBean().getCuentaCompradorBean().getSucursal();
            this.lblSolicitanteC.setText(this.u.getVendedorBean().getTitular());
            this.lblSolicitanteDatoC.setText(this.u.getVendedorBean().getCuit());
            this.lblCBUC.setText(this.u.getVendedorBean().getCuentaVendedor().getCbu());
            if (!TextUtils.isEmpty(this.z)) {
                this.leyendaConfirmar.setText(Html.fromHtml(this.z));
                this.leyendaConfirmar.setVisibility(0);
            } else {
                this.leyendaConfirmar.setVisibility(8);
                this.leyendaConfirmar.setText("");
            }
            if (this.u.getVendedorBean().getCuentaVendedor().getAlias() != null) {
                this.rowAliasC.setVisibility(0);
                this.lblDataAliasC.setText(this.u.getVendedorBean().getCuentaVendedor().getAlias());
            } else {
                this.rowAliasC.setVisibility(8);
            }
            if (this.u.getVendedorBean().getCuentaVendedor().getBanco() != null) {
                this.rowBancoC.setVisibility(0);
                this.lblbancoC.setText(this.u.getVendedorBean().getCuentaVendedor().getBanco());
            } else {
                this.rowBancoC.setVisibility(8);
            }
            this.lblFechaSolicitudC.setText(this.u.getFechaCreacion());
            this.lblFechaVencimientoC.setText(this.u.getFechaVencimiento());
            this.lblIdDebinC.setText(this.w.getIdDebin());
            if (this.u.getDescripcion() != null) {
                this.rowDescripcionV.setVisibility(0);
                this.lblDataDescripcionV.setText(Html.fromHtml(this.u.getDescripcion()));
            } else {
                this.rowDescripcionV.setVisibility(8);
            }
            this.lblEstadoC.setText(a(this.u.getCodEstado(), "ESTDEBIN", this.v));
            this.lblConceptoC.setText(a(this.u.getCodConcepto(), PRE_AUTORIZACIONES.CONDEBIN, this.v));
            TextView textView2 = this.lblMonedaC;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a(this.u.getMoneda(), PRE_AUTORIZACIONES.MONEDADESCSIMBOLO, this.v));
            sb2.append(UtilsCuentas.SEPARAOR2);
            textView2.setText(sb2.toString());
            this.lblImporteC.setText(this.u.getImporte());
            this.lblcDebitoC.setText(UtilAccount.getAbreviatureAndAccountFormat(a(PRE_AUTORIZACIONES.TPOCTACORTA), tipo, sucursal, numero));
            if (this.u.getCompradorBean().getCuentaCompradorBean().getSaldo() != null) {
                this.lblSaldoCDebitoC.setVisibility(0);
                TextView textView3 = this.lblSaldoCDebitoC;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(a(this.u.getMoneda(), PRE_AUTORIZACIONES.MONEDADESCSIMBOLO, this.v));
                sb3.append(this.u.getCompradorBean().getCuentaCompradorBean().getSaldo());
                textView3.setText(sb3.toString());
            } else {
                this.lblSaldoCDebitoC.setVisibility(8);
            }
            this.lblFechaSolicitudC.setText(this.u.getFechaCreacion());
            this.lblFechaVencimientoC.setText(this.u.getFechaVencimiento());
            this.lblFechaOperacionC.setText(abmDebinCompradorBodyResponseBean.getFechaOperacion());
            this.q = abmDebinCompradorBodyResponseBean.getFechaOperacion();
            this.lblnroComprobanteC.setText(abmDebinCompradorBodyResponseBean.getNroComprobante());
            this.x = abmDebinCompradorBodyResponseBean.getNroComprobante();
            if (TextUtils.isEmpty(abmDebinVendedorBodyResponseBean.getLeyendaComp())) {
                this.rowLeyendaC.setVisibility(8);
            } else {
                this.rowLeyendaC.setVisibility(0);
                this.lblLeyendaC.setText(abmDebinVendedorBodyResponseBean.getLeyendaComp());
            }
        }
        this.btnVolver.setOnClickListener(this);
        try {
            RelativeLayout relativeLayout = this.relativeComprobante;
            CAccessibility instance = CAccessibility.getInstance(getApplicationContext());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(this.lblMonedaC.getText().toString());
            sb4.append(UtilsCuentas.SEPARAOR2);
            sb4.append(this.lblImporteC.getText().toString());
            relativeLayout.setContentDescription(instance.applyFilterGeneral(sb4.toString()));
            this.lblIdDebinC.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblIdDebinC.getText().toString()));
            this.lblSolicitanteDatoC.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblSolicitanteDatoC.getText().toString()));
            this.lblcDebitoC.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.lblcDebitoC.getText().toString()));
            this.lblnroComprobanteC.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblnroComprobanteC.getText().toString()));
            this.lblTituloC.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.lblTituloC.getText().toString()));
            this.lblLeyendaC.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.lblLeyendaC.getText().toString()));
            this.lblFechaOperacionC.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterDate(this.lblFechaOperacionC.getText().toString()));
            this.lblFechaSolicitudC.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterDate(this.lblFechaSolicitudC.getText().toString()));
            this.lblFechaVencimientoC.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterDate(this.lblFechaVencimientoC.getText().toString()));
            this.lblCBUC.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblCBUC.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.y.equalsIgnoreCase("P")) {
            AnalyticsManager analyticsManager = this.p;
            String string = getString(R.string.analytics_transaction_hits_debin);
            StringBuilder sb5 = new StringBuilder();
            sb5.append(this.x);
            sb5.append(UtilsCuentas.SEPARAOR2);
            sb5.append(this.q);
            analyticsManager.trackTransaction(string, sb5.toString());
        } else if (this.y.equalsIgnoreCase("R")) {
            AnalyticsManager analyticsManager2 = this.p;
            String string2 = getString(R.string.analytics_transaction_hits_debin);
            StringBuilder sb6 = new StringBuilder();
            sb6.append(this.x);
            sb6.append(UtilsCuentas.SEPARAOR2);
            sb6.append(this.q);
            analyticsManager2.trackTransaction(string2, sb6.toString());
        } else if (this.y.equalsIgnoreCase("A")) {
            AnalyticsManager analyticsManager3 = this.p;
            String string3 = getString(R.string.analytics_transaction_hits_debin);
            StringBuilder sb7 = new StringBuilder();
            sb7.append(this.x);
            sb7.append(UtilsCuentas.SEPARAOR2);
            sb7.append(this.q);
            analyticsManager3.trackTransaction(string3, sb7.toString());
        }
    }

    public void setActionBarComprobanteAbm() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(false);
        c();
    }

    /* access modifiers changed from: private */
    public void c() {
        this.mActionBar = getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        ImageView imageView2 = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.menu);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!AbmDebinActivity.this.B) {
                        AbmDebinActivity.this.A.showAlert();
                        AbmDebinActivity.this.B = true;
                        return;
                    }
                    AbmDebinActivity.this.switchDrawer();
                }
            });
        }
        d();
        if (imageView2 != null) {
            imageView2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (AbmDebinActivity.this.y.equalsIgnoreCase("P")) {
                        AbmDebinActivity.this.p.trackEvent(AbmDebinActivity.this.getString(R.string.analytics_trackevent_category_debin), AbmDebinActivity.this.getString(R.string.analytics_trackevent_action_pagar_debin), AbmDebinActivity.this.getString(R.string.analytics_trackevent_label_Descargar_compartir_comprobante));
                    } else if (AbmDebinActivity.this.y.equalsIgnoreCase("R")) {
                        AbmDebinActivity.this.p.trackEvent(AbmDebinActivity.this.getString(R.string.analytics_trackevent_category_debin), AbmDebinActivity.this.getString(R.string.analytics_trackevent_action_rechazar_debin), AbmDebinActivity.this.getString(R.string.analytics_trackevent_label_Descargar_compartir_comprobante));
                    } else if (AbmDebinActivity.this.y.equalsIgnoreCase("A")) {
                        AbmDebinActivity.this.p.trackEvent(AbmDebinActivity.this.getString(R.string.analytics_trackevent_category_debin), AbmDebinActivity.this.getString(R.string.analytics_trackevent_action_anular_debin), AbmDebinActivity.this.getString(R.string.analytics_trackevent_label_Descargar_compartir_comprobante));
                    }
                    AbmDebinActivity.this.B = true;
                    AbmDebinActivity.this.C.show(AbmDebinActivity.this.getSupportFragmentManager(), "Dialog");
                }
            });
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.A.onRequestPermissionsResult(i, strArr, iArr);
    }

    private void d() {
        this.A = e();
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
                if (str.equalsIgnoreCase(AbmDebinActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                    if (ContextCompat.checkSelfPermission(AbmDebinActivity.this.getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                        AbmDebinActivity.this.A.optionShareSelected();
                    } else if (VERSION.SDK_INT >= 23) {
                        AbmDebinActivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, OptionsToShareImpl.PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_SHARE);
                    }
                } else if (!str.equalsIgnoreCase(AbmDebinActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                } else {
                    if (ContextCompat.checkSelfPermission(AbmDebinActivity.this.getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                        AbmDebinActivity.this.A.optionDownloadSelected();
                    } else if (VERSION.SDK_INT >= 23) {
                        AbmDebinActivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, OptionsToShareImpl.PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_DOWNLOAD);
                    }
                }
            }
        });
        this.C.setCancelable(true);
    }

    public void showRequestPermissionExplation(final int i) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.write_external_permission_request_message), null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
                AbmDebinActivity.this.c();
            }

            public void onSimpleActionButton() {
                if (VERSION.SDK_INT >= 23) {
                    AbmDebinActivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i);
                }
            }
        });
        newInstance.show(getSupportFragmentManager(), OptionsToShareImpl.PERMISSION_DIALOG_TAG);
    }

    private OptionsToShare e() {
        return new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public View getViewToShare() {
                return AbmDebinActivity.this.comprobanteAbm;
            }

            public void receiveIntentAppShare(Intent intent) {
                AbmDebinActivity.this.startActivity(Intent.createChooser(intent, AbmDebinActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
            }

            public String getFileName() {
                if (AbmDebinActivity.this.y.equalsIgnoreCase("A")) {
                    return AbmDebinActivity.this.getString(R.string.ID_4444_DEBIN_COMPROBANTEDEANULACIONDEDEBINARCHIVO).concat(AbmDebinActivity.this.x);
                }
                if (AbmDebinActivity.this.y.equalsIgnoreCase("R")) {
                    return AbmDebinActivity.this.getString(R.string.ID_4442_DEBIN_COMPROBANTEDERECHAZODEDEBINARCHIVO).concat(AbmDebinActivity.this.x);
                }
                if (AbmDebinActivity.this.y.equalsIgnoreCase("P")) {
                    return AbmDebinActivity.this.getString(R.string.ID_4437_DEBIN_COMPROBANTEDEPAGODEDEBINARCHIVO).concat(AbmDebinActivity.this.x);
                }
                return AbmDebinActivity.this.getString(R.string.ID_4437_DEBIN_COMPROBANTEDEPAGODEDEBINARCHIVO).concat(AbmDebinActivity.this.x);
            }

            public String getSubjectReceiptToShare() {
                if (AbmDebinActivity.this.y.equalsIgnoreCase("A")) {
                    String string = AbmDebinActivity.this.getString(R.string.ID_4444_DEBIN_COMPROBANTEDEANULACIONDEDEBIN);
                    StringBuilder sb = new StringBuilder();
                    sb.append("-");
                    sb.append(AbmDebinActivity.this.x);
                    return string.concat(sb.toString());
                } else if (AbmDebinActivity.this.y.equalsIgnoreCase("R")) {
                    String string2 = AbmDebinActivity.this.getString(R.string.ID_4442_DEBIN_COMPROBANTEDERECHAZODEDEBIN);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("-");
                    sb2.append(AbmDebinActivity.this.x);
                    return string2.concat(sb2.toString());
                } else if (!AbmDebinActivity.this.y.equalsIgnoreCase("P")) {
                    return AbmDebinActivity.this.getString(R.string.ID_4444_DEBIN_COMPROBANTEDEANULACIONDEDEBIN);
                } else {
                    String string3 = AbmDebinActivity.this.getString(R.string.ID_4437_DEBIN_COMPROBANTEDEPAGODEDEBIN);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("-");
                    sb3.append(AbmDebinActivity.this.x);
                    return string3.concat(sb3.toString());
                }
            }

            public void optionDownloadSelected() {
                if (ContextCompat.checkSelfPermission(AbmDebinActivity.this.getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    showRequestPermission(OptionsToShareImpl.PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_DOWNLOAD);
                    return;
                }
                super.optionDownloadSelected();
                AbmDebinActivity.this.B = true;
            }

            public void optionCancelSelected() {
                super.optionCancelSelected();
                AbmDebinActivity.this.B = true;
                AbmDebinActivity.this.onBackPressed();
            }

            public void optionShareSelected() {
                if (ContextCompat.checkSelfPermission(AbmDebinActivity.this.getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    showRequestPermission(OptionsToShareImpl.PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_SHARE);
                    return;
                }
                super.optionShareSelected();
                AbmDebinActivity.this.B = true;
            }
        };
    }

    public boolean canExit(int i) {
        if (!this.B) {
            this.A = e();
            this.A.showAlert();
        }
        return this.B;
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
    }

    public void onBackPressed() {
        int id2 = this.mControlPager.getCurrentView().getId();
        if (id2 != R.id.layout_comprobante_abm_debin) {
            if (id2 == R.id.layout_confirmar_abm_debin) {
                backButtonConfirmar();
            }
        } else if (!this.B) {
            this.B = true;
            this.A.showAlert();
        } else {
            setResult(-1, new Intent());
            super.onBackPressed();
            hideKeyboard();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    }
                    break;
                case 1:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    break;
            }
            this.mControlPager.setDisplayedChild(i);
            attachView();
            hideKeyboard();
        }
    }

    private String a(String str, String str2, List<ListTableBean> list) {
        String str3 = new String();
        for (ListTableBean listTableBean : list) {
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

    private ListTableBean a(String str) {
        ListTableBean listTableBean = new ListTableBean();
        for (ListTableBean listTableBean2 : this.v) {
            if (listTableBean2.getIdTable().equalsIgnoreCase(str)) {
                listTableBean = listTableBean2;
            }
        }
        return listTableBean;
    }

    public void onDestroy() {
        detachView();
        super.onDestroy();
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 != R.id.F32_50_BTN_CONFIRMAR_DEBIN) {
            if (id2 == R.id.F32_51_btn_volver) {
                onBackPressed();
            }
        } else if (this.y.equalsIgnoreCase("A")) {
            this.r.callAbmDebinVendedor(this.y, this.w.getIdDebin(), null);
        } else if (this.y.equalsIgnoreCase("R") || this.y.equalsIgnoreCase("P") || this.y.equalsIgnoreCase("D")) {
            this.t.setIdDebin(this.w.getIdDebin());
            this.r.callAbmDebinVendedor(this.y, null, this.t);
        }
    }

    public AnalyticsManager getAnalyticsManager() {
        return this.p;
    }
}
