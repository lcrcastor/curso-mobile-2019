package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.CExtEnv;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants.BodyRequest.TipoOperacion;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.utils.UtilString;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import ar.com.santander.rio.mbanking.utils.Utils.Documento;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import javax.inject.Inject;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public class ActivityEnvioDineroComprobanteEnvio extends PrivateMenuActivity {
    public static final int REQUEST_CODE_ENVIO_DINERO_COMPROBANTE_ENVIO = 1;
    @InjectView(2131362537)
    TextView btn_volver;
    @InjectView(2131362568)
    ScrollView comprobanteOperacion;
    public String inputCuenta;
    public String inputDocumento;
    public String inputEmail;
    public String inputFechaVencimiento;
    public String inputFuncionalidad;
    public Integer inputImporte;
    public String inputNombre;
    public String inputNumeroComprobante;
    public String inputTipoDoc;
    @InjectView(2131362538)
    TextView lbl_InformacionExtraccion;
    @InjectView(2131362539)
    TextView lbl_comprobanteOperacion;
    @InjectView(2131362540)
    TextView lbl_data_detalleCodExtraccion;
    @InjectView(2131362541)
    TextView lbl_data_detalleCodTransaccion;
    @InjectView(2131362542)
    TextView lbl_data_detalleCuenta;
    @InjectView(2131362543)
    TextView lbl_data_detalleDestinatario;
    @InjectView(2131362544)
    TextView lbl_data_detalleDocumento;
    @InjectView(2131362545)
    TextView lbl_data_detalleEmail;
    @InjectView(2131362546)
    TextView lbl_data_detalleImporte;
    @InjectView(2131362547)
    TextView lbl_data_detalleNroComprobante;
    @InjectView(2131362548)
    TextView lbl_detalleCodExtraccion;
    @InjectView(2131362549)
    TextView lbl_detalleCodTransaccion;
    @InjectView(2131362550)
    TextView lbl_detalleCuenta;
    @InjectView(2131362551)
    TextView lbl_detalleDestinatario;
    @InjectView(2131362552)
    TextView lbl_detalleDocumento;
    @InjectView(2131362553)
    TextView lbl_detalleEmail;
    @InjectView(2131362554)
    TextView lbl_detalleImporte;
    @InjectView(2131362555)
    TextView lbl_detalleNroComprobante;
    @InjectView(2131362556)
    TextView lbl_operacionValidaHasta;
    @InjectView(2131362557)
    TextView lbl_seuo;
    @Inject
    AnalyticsManager p;
    /* access modifiers changed from: private */
    public OptionsToShare q;
    private View r;
    /* access modifiers changed from: private */
    public IsbanDialogFragment s;
    /* access modifiers changed from: private */
    public boolean t = false;
    /* access modifiers changed from: private */
    public Context u;

    public int getMainLayout() {
        return R.layout.activity_envio_dinero_comprobante_envio;
    }

    private void b() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.ID2141_ENVEFECT_BTN_COMPARTIR));
        arrayList.add(getResources().getString(R.string.ID2142_ENVEFECT_BTN_DESCARGAR));
        this.s = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.ID2140_ENVEFECT_LBL_COMPROBANTE), null, arrayList, getString(R.string.ID2060_ENVEFECT_BTN_CANCELAR), null, null, null, arrayList);
        this.s.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(ActivityEnvioDineroComprobanteEnvio.this.getResources().getString(R.string.ID2141_ENVEFECT_BTN_COMPARTIR))) {
                    ActivityEnvioDineroComprobanteEnvio.this.p.trackEvent(ActivityEnvioDineroComprobanteEnvio.this.getString(R.string.analytics_category_enviodinero), ActivityEnvioDineroComprobanteEnvio.this.getString(R.string.analytics_action_compartir_comprobante_envio_efectivo), ActivityEnvioDineroComprobanteEnvio.this.getString(R.string.analytics_label_comprobante_extraccion_sin_tarjeta));
                    ActivityEnvioDineroComprobanteEnvio.this.p.trackEvent(ActivityEnvioDineroComprobanteEnvio.this.getString(R.string.analytics_category_extraccion_sin_tarjeta), ActivityEnvioDineroComprobanteEnvio.this.getString(R.string.analytics_action_compartir_comprobante_extraccion), ActivityEnvioDineroComprobanteEnvio.this.getString(R.string.analytics_label_comprobante_extraccion_sin_tarjeta));
                    ActivityEnvioDineroComprobanteEnvio.this.q.optionShareSelected();
                } else if (str.equalsIgnoreCase(ActivityEnvioDineroComprobanteEnvio.this.getResources().getString(R.string.ID2142_ENVEFECT_BTN_DESCARGAR))) {
                    ActivityEnvioDineroComprobanteEnvio.this.p.trackEvent(ActivityEnvioDineroComprobanteEnvio.this.getString(R.string.analytics_category_enviodinero), ActivityEnvioDineroComprobanteEnvio.this.getString(R.string.analytics_action_descargar_comprobante_envio_dinero), ActivityEnvioDineroComprobanteEnvio.this.getString(R.string.analytics_label_comprobante_extraccion_sin_tarjeta));
                    ActivityEnvioDineroComprobanteEnvio.this.p.trackEvent(ActivityEnvioDineroComprobanteEnvio.this.getString(R.string.analytics_category_extraccion_sin_tarjeta), ActivityEnvioDineroComprobanteEnvio.this.getString(R.string.analytics_action_descargar_comprobante_extraccion), ActivityEnvioDineroComprobanteEnvio.this.getString(R.string.analytics_label_comprobante_extraccion_sin_tarjeta));
                    ActivityEnvioDineroComprobanteEnvio.this.q.optionDownloadSelected();
                }
            }
        });
        this.s.setCancelable(true);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0029  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0033  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getSelectedOption() {
        /*
            r3 = this;
            java.lang.String r0 = r3.inputFuncionalidad
            int r1 = r0.hashCode()
            r2 = 68813(0x10ccd, float:9.6428E-41)
            if (r1 == r2) goto L_0x001b
            r2 = 69121(0x10e01, float:9.6859E-41)
            if (r1 == r2) goto L_0x0011
            goto L_0x0025
        L_0x0011:
            java.lang.String r1 = "EXT"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0025
            r0 = 1
            goto L_0x0026
        L_0x001b:
            java.lang.String r1 = "ENV"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0025
            r0 = 0
            goto L_0x0026
        L_0x0025:
            r0 = -1
        L_0x0026:
            switch(r0) {
                case 0: goto L_0x0033;
                case 1: goto L_0x002b;
                default: goto L_0x0029;
            }
        L_0x0029:
            r0 = 0
            return r0
        L_0x002b:
            r0 = 2131756436(0x7f100594, float:1.914378E38)
            java.lang.String r0 = r3.getString(r0)
            return r0
        L_0x0033:
            r0 = 2131756439(0x7f100597, float:1.9143786E38)
            java.lang.String r0 = r3.getString(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio.getSelectedOption():java.lang.String");
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        ButterKnife.inject((Activity) this);
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.r = getSupportActionBar().getCustomView();
        this.q = c();
        ((ImageView) this.r.findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!ActivityEnvioDineroComprobanteEnvio.this.t) {
                    ActivityEnvioDineroComprobanteEnvio.this.q.showAlert();
                } else {
                    ActivityEnvioDineroComprobanteEnvio.this.switchDrawer();
                }
            }
        });
        b();
        ImageView imageView = (ImageView) this.r.findViewById(R.id.menu);
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivityEnvioDineroComprobanteEnvio.this.t = true;
                ActivityEnvioDineroComprobanteEnvio.this.s.show(ActivityEnvioDineroComprobanteEnvio.this.getSupportFragmentManager(), "Dialog");
            }
        });
        Intent intent = getIntent();
        this.inputNombre = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_NOMBRE));
        this.inputTipoDoc = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_TIPODOC));
        this.inputFechaVencimiento = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_FECHAVENCIMIENTO));
        this.inputDocumento = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_DOCUMENTO));
        this.inputImporte = Integer.valueOf(intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_DINERO)));
        this.inputEmail = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_MAIL));
        this.inputCuenta = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_CUENTA));
        this.inputFuncionalidad = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_FUNCIONALIDAD));
        this.inputNumeroComprobante = intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_NROCOMPROBANTE));
        this.lbl_data_detalleCodExtraccion.setText(intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_CODEXTRACCION)));
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(getString(R.string.ID2135_ENVEFECT_LBL_OPERVAL)).append(UtilsCuentas.SEPARAOR2).append(this.inputFechaVencimiento);
        spannableStringBuilder.setSpan(new CalligraphyTypefaceSpan(TypefaceUtils.load(getAssets(), "fonts/OpenSans-Bold.otf")), getString(R.string.ID2135_ENVEFECT_LBL_OPERVAL).length(), spannableStringBuilder.toString().length(), 33);
        this.lbl_operacionValidaHasta.setText(spannableStringBuilder, BufferType.SPANNABLE);
        TextView textView = this.lbl_operacionValidaHasta;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.ID2135_ENVEFECT_LBL_OPERVAL));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.inputFechaVencimiento);
        textView.setContentDescription(sb.toString());
        this.lbl_data_detalleCodExtraccion.setText(intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_CODEXTRACCION)));
        this.lbl_data_detalleNroComprobante.setText(this.inputNumeroComprobante);
        this.lbl_data_detalleCodTransaccion.setText(intent.getStringExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_CODTRANSACCION)));
        this.lbl_data_detalleDestinatario.setText(UtilString.capitalize(Html.fromHtml(this.inputNombre).toString()));
        this.lbl_data_detalleImporte.setText(CExtEnv.getFormattedValue(this.inputImporte));
        this.lbl_data_detalleEmail.setText(this.inputEmail);
        this.lbl_data_detalleDocumento.setText(String.format("%s %s", new Object[]{CExtEnv.getDescripcionTipoDocumento(this.sessionManager, CExtEnv.convertCodigoTipoDocumentoBanelco(this.inputTipoDoc)), Documento.mask(this.inputTipoDoc, this.inputDocumento)}));
        this.lbl_data_detalleCuenta.setText(this.inputCuenta);
        this.btn_volver.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivityEnvioDineroComprobanteEnvio.this.onBackPressed();
            }
        });
        if (this.inputFuncionalidad.equalsIgnoreCase(TipoOperacion.EnvioDinero)) {
            this.p.trackScreen(getString(R.string.analytics_enviodinero_comprobante_extraccion_sin_tarjeta));
            this.p.trackCustomDimension(getResources().getString(R.string.analytics_enviodinero_comprobante_extraccion_sin_tarjeta), 5, getResources().getString(R.string.analytics_custom_dimensions_constant_1));
            this.lbl_comprobanteOperacion.setText(R.string.ID2127_ENVEFECT_LBL_COMPCANOPER);
        } else if (this.inputFuncionalidad.equalsIgnoreCase(TipoOperacion.ExtraccionSinTarjeta)) {
            this.lbl_comprobanteOperacion.setText(R.string.ID2016_EXTTARJETA_LBL_COMPOPERACION);
            this.p.trackScreen(getString(R.string.analytics_extraccion_comprobante_extraccion_sin_tarjeta));
            this.p.trackCustomDimension(getResources().getString(R.string.analytics_extraccion_comprobante_extraccion_sin_tarjeta), 5, getResources().getString(R.string.analytics_custom_dimensions_constant_1));
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
        try {
            this.lbl_data_detalleCodExtraccion.setContentDescription(new CAccessibility(this).applyFilterCharacterToCharacter(this.lbl_data_detalleCodExtraccion.getText().toString()));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            this.lbl_data_detalleCodTransaccion.setContentDescription(new CAccessibility(this).applyFilterCharacterToCharacter(this.lbl_data_detalleCodTransaccion.getText().toString()));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        try {
            this.lbl_data_detalleNroComprobante.setContentDescription(new CAccessibility(this).applyFilterCharacterToCharacter(this.lbl_data_detalleNroComprobante.getText().toString()));
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        imageView.setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.CONTENT_OPCIONES)));
        this.btn_volver.setContentDescription(CAccessibility.applyFilterMaskBotton(this.btn_volver.getText().toString()));
        TextView textView2 = this.lbl_operacionValidaHasta;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getString(R.string.ID2135_ENVEFECT_LBL_OPERVAL));
        sb2.append(UtilsCuentas.SEPARAOR2);
        sb2.append(this.inputFechaVencimiento);
        textView2.setContentDescription(sb2.toString());
        this.lbl_detalleNroComprobante.setContentDescription(this.lbl_detalleNroComprobante.getText().toString().replace("Nro.", getString(R.string.ID452_TRANSFERENCE_THIRDPARTY_NUMBER)));
        this.u = this;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onBackPressed() {
        /*
            r6 = this;
            boolean r0 = r6.t
            if (r0 != 0) goto L_0x000a
            ar.com.santander.rio.mbanking.components.share.OptionsToShare r0 = r6.q
            r0.showAlert()
            goto L_0x0058
        L_0x000a:
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            java.lang.String r1 = r6.inputFuncionalidad
            int r2 = r1.hashCode()
            r3 = 68813(0x10ccd, float:9.6428E-41)
            r4 = -1
            r5 = 1
            if (r2 == r3) goto L_0x002c
            r3 = 69121(0x10e01, float:9.6859E-41)
            if (r2 == r3) goto L_0x0022
            goto L_0x0036
        L_0x0022:
            java.lang.String r2 = "EXT"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0036
            r1 = 1
            goto L_0x0037
        L_0x002c:
            java.lang.String r2 = "ENV"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0036
            r1 = 0
            goto L_0x0037
        L_0x0036:
            r1 = -1
        L_0x0037:
            switch(r1) {
                case 0: goto L_0x0041;
                case 1: goto L_0x003b;
                default: goto L_0x003a;
            }
        L_0x003a:
            goto L_0x0046
        L_0x003b:
            java.lang.String r1 = "RECARGAR_PANTALLA_INICIAL_EXT"
            r0.putExtra(r1, r5)
            goto L_0x0046
        L_0x0041:
            java.lang.String r1 = "RECARGAR_PANTALLA_INICIAL_ENV"
            r0.putExtra(r1, r5)
        L_0x0046:
            r6.setResult(r4, r0)
            super.onBackPressed()
            r6.hideKeyboard()
            r0 = 2130771999(0x7f01001f, float:1.7147104E38)
            r1 = 2130772004(0x7f010024, float:1.7147114E38)
            r6.overridePendingTransition(r0, r1)
        L_0x0058:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio.onBackPressed():void");
    }

    public void applyLetterSpacing() {
        UtilStyleCommons.setLetterSpacing(this.lbl_comprobanteOperacion, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleCuenta, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_data_detalleCuenta, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleDestinatario, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_data_detalleDestinatario, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleEmail, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_data_detalleEmail, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_InformacionExtraccion, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleDocumento, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_data_detalleDocumento, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleImporte, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_data_detalleImporte, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleCodExtraccion, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_data_detalleCodExtraccion, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleCodTransaccion, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_data_detalleCodTransaccion, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleNroComprobante, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.lbl_data_detalleNroComprobante, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.lbl_seuo, -1.5f);
    }

    private OptionsToShare c() {
        return new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public String getSubjectReceiptToShare() {
                return "Comprobante de Envío";
            }

            public View getViewToShare() {
                return ActivityEnvioDineroComprobanteEnvio.this.comprobanteOperacion;
            }

            public void receiveIntentAppShare(Intent intent) {
                ActivityEnvioDineroComprobanteEnvio.this.startActivityForResult(Intent.createChooser(intent, ActivityEnvioDineroComprobanteEnvio.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 1);
            }

            /* JADX WARNING: Removed duplicated region for block: B:12:0x002b  */
            /* JADX WARNING: Removed duplicated region for block: B:14:0x0030  */
            /* JADX WARNING: Removed duplicated region for block: B:16:0x0042  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.String getFileName() {
                /*
                    r3 = this;
                    ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio.this
                    java.lang.String r0 = r0.inputFuncionalidad
                    int r1 = r0.hashCode()
                    r2 = 68813(0x10ccd, float:9.6428E-41)
                    if (r1 == r2) goto L_0x001d
                    r2 = 69121(0x10e01, float:9.6859E-41)
                    if (r1 == r2) goto L_0x0013
                    goto L_0x0027
                L_0x0013:
                    java.lang.String r1 = "EXT"
                    boolean r0 = r0.equals(r1)
                    if (r0 == 0) goto L_0x0027
                    r0 = 1
                    goto L_0x0028
                L_0x001d:
                    java.lang.String r1 = "ENV"
                    boolean r0 = r0.equals(r1)
                    if (r0 == 0) goto L_0x0027
                    r0 = 0
                    goto L_0x0028
                L_0x0027:
                    r0 = -1
                L_0x0028:
                    switch(r0) {
                        case 0: goto L_0x0042;
                        case 1: goto L_0x0030;
                        default: goto L_0x002b;
                    }
                L_0x002b:
                    ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio.this
                    java.lang.String r0 = r0.inputNumeroComprobante
                    return r0
                L_0x0030:
                    ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio.this
                    r1 = 2131756359(0x7f100547, float:1.9143623E38)
                    java.lang.String r0 = r0.getString(r1)
                    ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio r1 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio.this
                    java.lang.String r1 = r1.inputNumeroComprobante
                    java.lang.String r0 = r0.concat(r1)
                    return r0
                L_0x0042:
                    ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio.this
                    r1 = 2131756357(0x7f100545, float:1.914362E38)
                    java.lang.String r0 = r0.getString(r1)
                    ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio r1 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio.this
                    java.lang.String r1 = r1.inputNumeroComprobante
                    java.lang.String r0 = r0.concat(r1)
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio.AnonymousClass5.getFileName():java.lang.String");
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                ActivityEnvioDineroComprobanteEnvio.this.t = true;
            }

            public void optionCancelSelected() {
                super.optionCancelSelected();
                ActivityEnvioDineroComprobanteEnvio.this.t = true;
                ActivityEnvioDineroComprobanteEnvio.this.onBackPressed();
            }

            public void optionShareSelected() {
                if (ContextCompat.checkSelfPermission(ActivityEnvioDineroComprobanteEnvio.this.u, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    showRequestPermission(1);
                    return;
                }
                super.optionShareSelected();
                ActivityEnvioDineroComprobanteEnvio.this.t = true;
            }
        };
    }

    public void showRequestPermissionExplation(final int i) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.write_external_permission_request_message), null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
                ActivityEnvioDineroComprobanteEnvio.this.q.showAlert();
            }

            public void onSimpleActionButton() {
                if (VERSION.SDK_INT >= 23) {
                    ActivityEnvioDineroComprobanteEnvio.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i);
                }
            }
        });
        newInstance.show(getSupportFragmentManager(), OptionsToShareImpl.PERMISSION_DIALOG_TAG);
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.q.onRequestPermissionsResult(i, strArr, iArr);
    }

    public boolean canExit(int i) {
        if (!this.t) {
            final int i2 = i;
            AnonymousClass7 r1 = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
                public String getFileName() {
                    return "comprobanteenvio";
                }

                public String getSubjectReceiptToShare() {
                    return "Comprobante de Envío";
                }

                public View getViewToShare() {
                    return ActivityEnvioDineroComprobanteEnvio.this.comprobanteOperacion;
                }

                public void receiveIntentAppShare(Intent intent) {
                    ActivityEnvioDineroComprobanteEnvio.this.startActivityForResult(Intent.createChooser(intent, ActivityEnvioDineroComprobanteEnvio.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 1);
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    ActivityEnvioDineroComprobanteEnvio.this.t = true;
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    ActivityEnvioDineroComprobanteEnvio.this.t = true;
                }

                public void onAbortShare() {
                    super.onAbortShare();
                    ActivityEnvioDineroComprobanteEnvio.this.t = true;
                    ActivityEnvioDineroComprobanteEnvio.this.onClickItem(i2);
                }
            };
            r1.showAlert();
        }
        return this.t;
    }
}
