package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CExtEnv;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants.BodyRequest.TipoOperacion;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.OnSingleClickListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GeneraMandatoExtEnvEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.GeneraMandatoExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtEnvComprobanteBean;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import ar.com.santander.rio.mbanking.utils.Utils.Documento;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class ActivityEnvioDineroConfirmacionEnvio extends BaseActivity {
    public String avisoConfirmacion;
    public String boyconfirmacion;
    @InjectView(2131362265)
    Button btn_confirmarEnvio;
    public ImageView buttonVolver;
    @InjectView(2131362266)
    ImageView img_checkboxTyc;
    public String inputCuenta;
    public String inputDocumento;
    public String inputEmail;
    public String inputFuncionalidad;
    public Integer inputImporte;
    public String inputNombre;
    public String inputNumeroCuenta;
    public String inputSucursalCuenta;
    public String inputTipoCuenta;
    public String inputTipoDoc;
    @InjectView(2131362267)
    TextView lbl_conectorVerTerminos;
    @InjectView(2131362268)
    TextView lbl_confirmacionEnvio;
    @InjectView(2131362269)
    TextView lbl_data_detalleCuenta;
    @InjectView(2131362270)
    TextView lbl_data_detalleDestinatario;
    @InjectView(2131362271)
    TextView lbl_data_detalleDocumento;
    @InjectView(2131362272)
    TextView lbl_data_detalleEmail;
    @InjectView(2131362273)
    TextView lbl_data_detalleImporte;
    @InjectView(2131362274)
    TextView lbl_detalleCuenta;
    @InjectView(2131362275)
    TextView lbl_detalleDestinatario;
    @InjectView(2131362276)
    TextView lbl_detalleDocumento;
    @InjectView(2131362277)
    TextView lbl_detalleEmail;
    @InjectView(2131362278)
    TextView lbl_detalleImporte;
    @InjectView(2131362279)
    TextView lbl_terminosycondiciones;
    @InjectView(2131362280)
    TextView lbl_verificaDatos;
    Intent p;
    Boolean q = Boolean.valueOf(false);
    @Inject
    IDataManager r;
    @Inject
    SessionManager s;
    @Inject
    Bus t;
    public String tipoOperacion;
    @Inject
    SoftTokenManager u;
    @Inject
    AnalyticsManager v;
    GeneraMandatoEnvBodyRequestBean w;
    GeneraMandatoExtBodyRequestBean x;

    /* access modifiers changed from: private */
    public Activity b() {
        return this;
    }

    @OnClick({2131362279})
    public void verTerminos(View view) {
        this.p = new Intent(this, InfoActivity.class);
        this.p.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        this.p.putExtra(InfoActivity.TITLE_TO_SHOW, CExtEnv.getTyCTitulo(this.s));
        this.p.putExtra(InfoActivity.TEXT_TO_SHOW, CExtEnv.getTyCDescripcion(this.s));
        if (this.inputFuncionalidad.equalsIgnoreCase(TipoOperacion.EnvioDinero)) {
            this.v.trackScreen(getString(R.string.analytics_enviodinero_terminos_condiciones));
        } else if (this.inputFuncionalidad.equalsIgnoreCase(TipoOperacion.ExtraccionSinTarjeta)) {
            this.v.trackScreen(getString(R.string.analytics_extraccion_terminos_condiciones));
        }
        startActivity(this.p);
    }

    public void confirmarEnvio() {
        if (!this.q.booleanValue()) {
            this.img_checkboxTyc.requestFocus();
        } else {
            if (this.inputFuncionalidad.equalsIgnoreCase(TipoOperacion.EnvioDinero)) {
                this.avisoConfirmacion = getString(R.string.USER200001_ENV_TITLE);
                this.boyconfirmacion = getString(R.string.USER200001_ENV_BODY);
            } else if (this.inputFuncionalidad.equalsIgnoreCase(TipoOperacion.ExtraccionSinTarjeta)) {
                this.avisoConfirmacion = getString(R.string.USER200001_EXT_TITLE);
                this.boyconfirmacion = getString(R.string.USER200001_EXT_BODY);
            }
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(this.avisoConfirmacion, this.boyconfirmacion, null, null, "Sí", "No", null);
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onSimpleActionButton() {
                }

                /* JADX WARNING: Code restructure failed: missing block: B:8:0x002b, code lost:
                    if (r0.equals(ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants.BodyRequest.TipoOperacion.EnvioDinero) != false) goto L_0x002f;
                 */
                /* JADX WARNING: Removed duplicated region for block: B:11:0x0034  */
                /* JADX WARNING: Removed duplicated region for block: B:14:0x0086  */
                /* JADX WARNING: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void onPositiveButton() {
                    /*
                        r9 = this;
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        android.widget.Button r0 = r0.btn_confirmarEnvio
                        r1 = 0
                        r0.setEnabled(r1)
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        java.lang.String r0 = r0.inputFuncionalidad
                        int r2 = r0.hashCode()
                        r3 = 68813(0x10ccd, float:9.6428E-41)
                        if (r2 == r3) goto L_0x0025
                        r1 = 69121(0x10e01, float:9.6859E-41)
                        if (r2 == r1) goto L_0x001b
                        goto L_0x002e
                    L_0x001b:
                        java.lang.String r1 = "EXT"
                        boolean r0 = r0.equals(r1)
                        if (r0 == 0) goto L_0x002e
                        r1 = 1
                        goto L_0x002f
                    L_0x0025:
                        java.lang.String r2 = "ENV"
                        boolean r0 = r0.equals(r2)
                        if (r0 == 0) goto L_0x002e
                        goto L_0x002f
                    L_0x002e:
                        r1 = -1
                    L_0x002f:
                        switch(r1) {
                            case 0: goto L_0x0086;
                            case 1: goto L_0x0034;
                            default: goto L_0x0032;
                        }
                    L_0x0032:
                        goto L_0x00df
                    L_0x0034:
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        ar.com.santander.rio.mbanking.managers.security.SoftTokenManager r0 = r0.u
                        java.lang.Boolean r0 = r0.isSoftTokenAvailable()
                        boolean r0 = r0.booleanValue()
                        if (r0 == 0) goto L_0x00df
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        java.lang.String r1 = "generaMandatoExtEnv"
                        r0.showProgress(r1)
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtBodyRequestBean r1 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtBodyRequestBean
                        java.lang.String r2 = "EXT"
                        java.lang.String r3 = "S"
                        ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtEnvMandatarioBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtEnvMandatarioBean
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r5 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        java.lang.String r5 = r5.inputTipoCuenta
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r6 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        java.lang.String r6 = r6.inputSucursalCuenta
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r7 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        java.lang.String r7 = r7.inputNumeroCuenta
                        r4.<init>(r5, r6, r7)
                        ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtMandatoBean r5 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtMandatoBean
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r6 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        java.lang.Integer r6 = r6.inputImporte
                        java.lang.String r6 = r6.toString()
                        r5.<init>(r6)
                        r1.<init>(r2, r3, r4, r5)
                        r0.x = r1
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        ar.com.santander.rio.mbanking.managers.data.IDataManager r0 = r0.r
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r1 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtBodyRequestBean r1 = r1.x
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r2 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        android.app.Activity r2 = r2.b()
                        r0.generaMandato(r1, r2)
                        goto L_0x00df
                    L_0x0086:
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        ar.com.santander.rio.mbanking.managers.security.SoftTokenManager r0 = r0.u
                        java.lang.Boolean r0 = r0.isSoftTokenAvailable()
                        boolean r0 = r0.booleanValue()
                        if (r0 == 0) goto L_0x00df
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        java.lang.String r1 = "generaMandatoExtEnv"
                        r0.showProgress(r1)
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoEnvBodyRequestBean r1 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoEnvBodyRequestBean
                        java.lang.String r2 = "ENV"
                        java.lang.String r3 = "S"
                        ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtEnvMandatarioBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtEnvMandatarioBean
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r5 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        java.lang.String r5 = r5.inputTipoCuenta
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r6 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        java.lang.String r6 = r6.inputSucursalCuenta
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r7 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        java.lang.String r7 = r7.inputNumeroCuenta
                        r4.<init>(r5, r6, r7)
                        ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoEnvMandatoBean r5 = new ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoEnvMandatoBean
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r6 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        java.lang.Integer r6 = r6.inputImporte
                        java.lang.String r6 = r6.toString()
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r7 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        java.lang.String r7 = r7.inputTipoDoc
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r8 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        java.lang.String r8 = r8.inputDocumento
                        r5.<init>(r6, r7, r8)
                        r1.<init>(r2, r3, r4, r5)
                        r0.w = r1
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        ar.com.santander.rio.mbanking.managers.data.IDataManager r0 = r0.r
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r1 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoEnvBodyRequestBean r1 = r1.w
                        ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio r2 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.this
                        android.app.Activity r2 = r2.b()
                        r0.generaMandato(r1, r2)
                    L_0x00df:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio.AnonymousClass1.onPositiveButton():void");
                }

                public void onNegativeButton() {
                    if (ActivityEnvioDineroConfirmacionEnvio.this.inputFuncionalidad.equalsIgnoreCase(TipoOperacion.EnvioDinero)) {
                        ActivityEnvioDineroConfirmacionEnvio.this.v.trackEvent(ActivityEnvioDineroConfirmacionEnvio.this.getString(R.string.analytics_category_enviodinero), ActivityEnvioDineroConfirmacionEnvio.this.getString(R.string.analytics_action_no_confirmacion_extraccion_sin_tarjeta), ActivityEnvioDineroConfirmacionEnvio.this.getString(R.string.analytics_label_extraccion_sin_tarjeta_no_confirmada));
                    } else if (ActivityEnvioDineroConfirmacionEnvio.this.inputFuncionalidad.equalsIgnoreCase(TipoOperacion.ExtraccionSinTarjeta)) {
                        ActivityEnvioDineroConfirmacionEnvio.this.v.trackEvent(ActivityEnvioDineroConfirmacionEnvio.this.getString(R.string.analytics_category_extraccion_sin_tarjeta), ActivityEnvioDineroConfirmacionEnvio.this.getString(R.string.analytics_action_no_confirmacion_extraccion_sin_tarjeta), ActivityEnvioDineroConfirmacionEnvio.this.getString(R.string.analytics_label_extraccion_sin_tarjeta_no_confirmada));
                    }
                }
            });
            newInstance.show(getSupportFragmentManager(), "Dialog");
        }
        if (this.inputFuncionalidad.equalsIgnoreCase(TipoOperacion.EnvioDinero)) {
            this.v.trackScreen(getString(R.string.analytics_enviodinero_aviso_confirmacion));
        } else if (this.inputFuncionalidad.equalsIgnoreCase(TipoOperacion.ExtraccionSinTarjeta)) {
            this.v.trackScreen(getString(R.string.analytics_extraccion_aviso_confirmar_operacion));
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_envio_dinero_confirmacion_envio);
        ButterKnife.inject((Activity) this);
        setActionBarType(ActionBarType.BACK_ONLY);
        View customView = getSupportActionBar().getCustomView();
        Intent intent = getIntent();
        this.tipoOperacion = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_EXTENV_TIPOTRANSACCION));
        this.inputNombre = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_NOMBRE));
        this.inputTipoDoc = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_TIPODOC));
        this.inputDocumento = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_DOCUMENTO));
        this.inputImporte = Integer.valueOf(intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_DINERO)));
        this.inputEmail = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_MAIL));
        this.inputCuenta = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_CUENTA));
        this.inputTipoCuenta = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_TIPOCUENTA));
        this.inputSucursalCuenta = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_SUCURSALCUENTA));
        this.inputNumeroCuenta = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_NUMEROCUENTA));
        this.inputFuncionalidad = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_FUNCIONALIDAD));
        this.lbl_data_detalleDestinatario.setText(Html.fromHtml(this.inputNombre));
        this.lbl_data_detalleImporte.setText(CExtEnv.getFormattedValue(this.inputImporte));
        this.lbl_data_detalleEmail.setText(this.inputEmail);
        this.lbl_data_detalleCuenta.setText(this.inputCuenta);
        this.lbl_data_detalleDocumento.setText(String.format("%s %s", new Object[]{CExtEnv.getDescripcionTipoDocumento(this.s, CExtEnv.convertCodigoTipoDocumentoBanelco(this.inputTipoDoc)), Documento.format(this.inputTipoDoc, this.inputDocumento)}));
        this.buttonVolver = (ImageView) customView.findViewById(R.id.back_imgButton);
        this.buttonVolver.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivityEnvioDineroConfirmacionEnvio.this.setResult(0, new Intent());
                ActivityEnvioDineroConfirmacionEnvio.this.onBackPressed();
            }
        });
        this.q = CExtEnv.getTyC(this.s);
        if (!this.q.booleanValue()) {
            this.img_checkboxTyc.setVisibility(0);
            this.lbl_conectorVerTerminos.setText(getResources().getString(R.string.ID2023_ENVEFECT_BTN_TERCONDIC_ACEPTA));
            e();
            this.img_checkboxTyc.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ActivityEnvioDineroConfirmacionEnvio.this.c();
                }
            });
        } else {
            this.img_checkboxTyc.setVisibility(8);
            this.lbl_conectorVerTerminos.setText(getResources().getString(R.string.ID2023_ENVEFECT_BTN_TERCONDIC_VER));
            d();
        }
        if (this.inputFuncionalidad.equalsIgnoreCase(TipoOperacion.EnvioDinero)) {
            this.v.trackScreen(getString(R.string.analytics_enviodinero_confirmacion_operacion));
            this.lbl_confirmacionEnvio.setText(R.string.ID2017_ENVEFECT_LBL_CONFOPERACION);
        } else if (this.inputFuncionalidad.equalsIgnoreCase(TipoOperacion.ExtraccionSinTarjeta)) {
            this.lbl_confirmacionEnvio.setText(R.string.ID2007_EXTTARJETA_LBL_CONFOPER);
            this.v.trackScreen(getString(R.string.analytics_extraccion_confirmacion_operacion));
        }
        try {
            this.lbl_data_detalleCuenta.setContentDescription(new CAccessibility(this).applyFilterAccount(this.inputCuenta));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.lbl_data_detalleImporte.setContentDescription(new CAccessibility(this).applyFilterAmount(this.lbl_data_detalleImporte.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.img_checkboxTyc.setContentDescription(CAccessibility.applyFilterMaskInterruptor(getString(R.string.ID2023_ENVEFECT_BTN_TERCONDIC)));
        TextView textView = this.lbl_conectorVerTerminos;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.ID2207_TOKENSEGURIDAD_LBL_TYC_1_ACEPTO));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(getString(R.string.ID2207_TOKENSEGURIDAD_LBL_TYC_2));
        textView.setContentDescription(sb.toString());
        this.lbl_terminosycondiciones.setContentDescription(CAccessibility.applyFilterMaskVinculo(getString(R.string.ID2023_ENVEFECT_BTN_TERCONDIC)));
        this.btn_confirmarEnvio.setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.ID2024_ENVEFECT_BTN_OPERVALIDA)));
        this.buttonVolver.setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.CONTENT_ATRAS)));
        this.btn_confirmarEnvio.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                ActivityEnvioDineroConfirmacionEnvio.this.confirmarEnvio();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /* access modifiers changed from: private */
    public void c() {
        if (!this.q.booleanValue()) {
            d();
        } else {
            e();
        }
    }

    private void d() {
        this.img_checkboxTyc.setBackground(getResources().getDrawable(R.drawable.ic_checkbox_on_rojo));
        this.btn_confirmarEnvio.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
        this.btn_confirmarEnvio.setTextColor(getResources().getColor(R.color.white));
        this.q = Boolean.valueOf(true);
    }

    private void e() {
        this.img_checkboxTyc.setBackground(getResources().getDrawable(R.drawable.ic_checkbox_off_gris));
        this.btn_confirmarEnvio.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
        this.btn_confirmarEnvio.setTextColor(getResources().getColor(R.color.white));
        this.q = Boolean.valueOf(false);
    }

    public void applyLetterSpacing() {
        UtilStyleCommons.setLetterSpacing(this.lbl_confirmacionEnvio, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_verificaDatos, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleCuenta, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_data_detalleCuenta, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleImporte, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_data_detalleImporte, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleDestinatario, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_data_detalleDestinatario, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleDocumento, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_data_detalleDocumento, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleEmail, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_data_detalleEmail, -1.5f);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 6) {
            Intent intent2 = new Intent();
            if (i2 == -1) {
                if (intent.hasExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION)) {
                    intent2.putExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, intent.getIntExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, -1));
                }
                intent2.putExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_ENV, intent.getBooleanExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_ENV, false));
                intent2.putExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_EXT, intent.getBooleanExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_EXT, false));
                setResult(-1, intent2);
                finish();
            }
        }
    }

    private void a(Intent intent, GeneraMandatoExtEnvComprobanteBean generaMandatoExtEnvComprobanteBean) {
        intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_NOMBRE), this.inputNombre);
        intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_TIPODOC), this.inputTipoDoc);
        intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_DOCUMENTO), this.inputDocumento);
        intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_MAIL), this.inputEmail);
        intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_DINERO), this.inputImporte.toString());
        intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_CUENTA), this.inputCuenta);
        intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_CODEXTRACCION), generaMandatoExtEnvComprobanteBean.codExtraccion);
        intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_CODTRANSACCION), generaMandatoExtEnvComprobanteBean.idMandato);
        intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_FECHAVENCIMIENTO), generaMandatoExtEnvComprobanteBean.fechaVencimiento);
        intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_NROCOMPROBANTE), generaMandatoExtEnvComprobanteBean.numComprobante);
        intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_FUNCIONALIDAD), this.inputFuncionalidad);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        this.t.register(this);
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.t.unregister(this);
    }

    @Subscribe
    public void onGeneraMandato(GeneraMandatoExtEnvEvent generaMandatoExtEnvEvent) {
        dismissProgress();
        final GeneraMandatoExtEnvEvent generaMandatoExtEnvEvent2 = generaMandatoExtEnvEvent;
        AnonymousClass5 r0 = new BaseWSResponseHandler(this, TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.ENVIO_EFECTIVO, this) {
            public void onOk() {
                ActivityEnvioDineroConfirmacionEnvio.this.a(generaMandatoExtEnvEvent2);
            }

            public void commonAllErrorsAfterProcess(WebServiceEvent webServiceEvent) {
                ActivityEnvioDineroConfirmacionEnvio.this.btn_confirmarEnvio.setEnabled(true);
            }
        };
        r0.handleWSResponse(generaMandatoExtEnvEvent);
    }

    /* access modifiers changed from: private */
    public void a(GeneraMandatoExtEnvEvent generaMandatoExtEnvEvent) {
        try {
            GeneraMandatoExtEnvResponseBean generaMandatoExtEnvResponseBean = (GeneraMandatoExtEnvResponseBean) generaMandatoExtEnvEvent.getBeanResponse();
            Intent intent = new Intent(this, ActivityEnvioDineroComprobanteEnvio.class);
            a(intent, generaMandatoExtEnvResponseBean.generaMandatoExtEnvBodyResponseBean.comprobante);
            startActivityForResult(intent, 6);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onGeneraMandatoResultOk: ", e);
            e.fillInStackTrace();
        }
    }
}
