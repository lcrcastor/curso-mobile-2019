package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
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
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import ar.com.santander.rio.mbanking.utils.Utils.Documento;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import javax.inject.Inject;

public class ActivityEnvioDineroComprobanteCancelacion extends PrivateMenuActivity {
    public static final int REQUEST_CODE_ENVIO_DINERO_COMPROBANTE_CANCELACION = 1;
    /* access modifiers changed from: private */
    public IsbanDialogFragment A;
    /* access modifiers changed from: private */
    public OptionsToShare B;
    /* access modifiers changed from: private */
    public boolean C = false;
    @InjectView(2131362600)
    TextView btn_volver;
    @InjectView(2131362628)
    ScrollView comprobanteCancelacion;
    @InjectView(2131362602)
    TextView detalleCodTransaccion;
    @InjectView(2131362603)
    TextView detalleCuenta;
    @InjectView(2131362604)
    TextView detalleDestinatario;
    @InjectView(2131362605)
    TextView detalleDocumento;
    @InjectView(2131362606)
    TextView detalleFechaCancelacion;
    @InjectView(2131362607)
    TextView detalleFechaVencimiento;
    @InjectView(2131362608)
    TextView detalleImporte;
    @InjectView(2131362609)
    TextView detalleNroComprobante;
    public String inputFuncionalidad;
    @InjectView(2131362601)
    TextView lbl_comprobanteCancelacion;
    @InjectView(2131362610)
    TextView lbl_datosOperacion;
    @InjectView(2131362611)
    TextView lbl_detalleCodTransaccion;
    @InjectView(2131362612)
    TextView lbl_detalleCuenta;
    @InjectView(2131362613)
    TextView lbl_detalleDestinatario;
    @InjectView(2131362614)
    TextView lbl_detalleDocumento;
    @InjectView(2131362615)
    TextView lbl_detalleFechaCancelacion;
    @InjectView(2131362616)
    TextView lbl_detalleFechaVencimiento;
    @InjectView(2131362617)
    TextView lbl_detalleImporte;
    @InjectView(2131362618)
    TextView lbl_detalleNroComprobante;
    @Inject
    AnalyticsManager p;
    String q;
    String r;
    @InjectView(2131362625)
    RelativeLayout rllDestinatarios;
    Integer s;
    String t;
    String u;
    String v;
    String w;
    String x;
    String y;
    View z;

    public int getMainLayout() {
        return R.layout.activity_envio_dinero_comprobante_cancelacion;
    }

    private void b() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.ID2141_ENVEFECT_BTN_COMPARTIR));
        arrayList.add(getResources().getString(R.string.ID2142_ENVEFECT_BTN_DESCARGAR));
        this.A = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.ID2140_ENVEFECT_LBL_COMPROBANTE), null, arrayList, getString(R.string.ID2060_ENVEFECT_BTN_CANCELAR), null, null, null, arrayList);
        this.A.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(ActivityEnvioDineroComprobanteCancelacion.this.getResources().getString(R.string.ID2141_ENVEFECT_BTN_COMPARTIR))) {
                    ActivityEnvioDineroComprobanteCancelacion.this.B.optionShareSelected();
                } else if (str.equalsIgnoreCase(ActivityEnvioDineroComprobanteCancelacion.this.getResources().getString(R.string.ID2142_ENVEFECT_BTN_DESCARGAR))) {
                    ActivityEnvioDineroComprobanteCancelacion.this.B.optionDownloadSelected();
                }
            }
        });
        this.A.setCancelable(true);
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
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion.getSelectedOption():java.lang.String");
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        ButterKnife.inject((Activity) this);
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.z = getSupportActionBar().getCustomView();
        this.B = c();
        ((ImageView) this.z.findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivityEnvioDineroComprobanteCancelacion.this.switchDrawer();
            }
        });
        b();
        ImageView imageView = (ImageView) this.z.findViewById(R.id.menu);
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivityEnvioDineroComprobanteCancelacion.this.A.show(ActivityEnvioDineroComprobanteCancelacion.this.getSupportFragmentManager(), "Dialog");
            }
        });
        Intent intent = getIntent();
        this.t = intent.getStringExtra(getString(R.string.INTENT_PUT_EXTRA_CUENTA));
        this.q = intent.getStringExtra(getString(R.string.INTENT_PUT_EXTRA_TIPODOC));
        this.r = intent.getStringExtra(getString(R.string.INTENT_PUT_EXTRA_DOCUMENTO));
        this.s = Integer.valueOf(intent.getStringExtra(getString(R.string.INTENT_PUT_EXTRA_DINERO)));
        this.y = intent.getStringExtra(getString(R.string.INTENT_PUT_EXTRA_NOMBRE));
        this.u = intent.getStringExtra(getString(R.string.ED_INTENT_PUT_EXTRA_FECHA_VENCIMIENTO_OR));
        this.v = intent.getStringExtra(getString(R.string.ED_INTENT_PUT_EXTRA_FECHA_CANCELACION_OR));
        this.w = intent.getStringExtra(getString(R.string.ED_INTENT_PUT_EXTRA_COD_TRANSACCION_OR));
        this.x = intent.getStringExtra(getString(R.string.ED_INTENT_PUT_EXTRA_NRO_COMPROBANTE_OR));
        this.inputFuncionalidad = intent.getStringExtra(getString(R.string.INTENT_PUT_EXTRA_FUNCIONALIDAD));
        this.detalleFechaVencimiento.setText(this.u);
        this.detalleFechaCancelacion.setText(UtilDate.getDateFormat(this.v));
        this.detalleCuenta.setText(this.t);
        this.detalleDocumento.setText(String.format("%s %s", new Object[]{CExtEnv.getDescripcionTipoDocumento(this.sessionManager, CExtEnv.convertCodigoTipoDocumentoBanelco(this.q)), Documento.mask(this.q, this.r)}));
        this.detalleImporte.setText(CExtEnv.getFormattedValue(this.s));
        this.detalleCodTransaccion.setText(this.w);
        this.detalleNroComprobante.setText(this.x);
        this.detalleDestinatario.setText(Html.fromHtml(this.y));
        this.btn_volver.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivityEnvioDineroComprobanteCancelacion.this.onBackPressed();
            }
        });
        if (this.inputFuncionalidad.equalsIgnoreCase(TipoOperacion.EnvioDinero)) {
            if (TextUtils.isEmpty(this.y)) {
                findViewById(R.id.F19_10_txv_separador9).setVisibility(8);
                this.rllDestinatarios.setVisibility(8);
            }
            this.lbl_comprobanteCancelacion.setText(R.string.ID2094_ENVEFECT_LBL_COMPCANOPER);
            this.lbl_datosOperacion.setText(R.string.ID2095_ENVEFECT_LBL_DATOSOPER);
            this.p.trackScreen(getString(R.string.analytics_enviodinero_comprobante_cancelacion));
            this.p.trackCustomDimension(getResources().getString(R.string.analytics_enviodinero_comprobante_cancelacion), 5, getResources().getString(R.string.analytics_custom_dimensions_constant_1));
        } else if (this.inputFuncionalidad.equalsIgnoreCase(TipoOperacion.ExtraccionSinTarjeta)) {
            findViewById(R.id.F19_10_txv_separador9).setVisibility(8);
            this.rllDestinatarios.setVisibility(8);
            this.lbl_comprobanteCancelacion.setText(R.string.ID2078_EXTTARJETA_LBL_COMPCANOPER);
            this.lbl_datosOperacion.setText(R.string.ID2079_EXTTARJETA_LBL_DATOSOPER);
            this.p.trackScreen(getString(R.string.analytics_extraccion_comprobante_cancelar_operacion));
            this.p.trackCustomDimension(getResources().getString(R.string.analytics_extraccion_comprobante_cancelar_operacion), 5, getResources().getString(R.string.analytics_custom_dimensions_constant_1));
        }
        try {
            this.detalleCuenta.setContentDescription(new CAccessibility(this).applyFilterAccount(this.t));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.detalleImporte.setContentDescription(new CAccessibility(this).applyFilterAmount(this.detalleImporte.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.detalleCodTransaccion.setContentDescription(new CAccessibility(this).applyFilterCharacterToCharacter(this.detalleCodTransaccion.getText().toString()));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            this.detalleNroComprobante.setContentDescription(new CAccessibility(this).applyFilterCharacterToCharacter(this.detalleNroComprobante.getText().toString()));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        this.btn_volver.setContentDescription(CAccessibility.applyFilterMaskBotton(this.btn_volver.getText().toString()));
        imageView.setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.CONTENT_OPCIONES)));
        this.lbl_detalleNroComprobante.setContentDescription(this.lbl_detalleNroComprobante.getText().toString().replace("Nro.", getString(R.string.ID452_TRANSFERENCE_THIRDPARTY_NUMBER)));
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onBackPressed() {
        /*
            r6 = this;
            boolean r0 = r6.C
            if (r0 != 0) goto L_0x000a
            ar.com.santander.rio.mbanking.components.share.OptionsToShare r0 = r6.B
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
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion.onBackPressed():void");
    }

    public void applyLetterSpacing() {
        UtilStyleCommons.setLetterSpacing(this.lbl_comprobanteCancelacion, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_datosOperacion, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleFechaVencimiento, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.detalleFechaVencimiento, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleFechaCancelacion, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.detalleFechaCancelacion, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleCuenta, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.detalleCuenta, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleDocumento, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.detalleDocumento, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleImporte, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.detalleImporte, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleCodTransaccion, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.detalleCodTransaccion, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleNroComprobante, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.detalleNroComprobante, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleDestinatario, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.detalleDestinatario, -1.5f);
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.B.onRequestPermissionsResult(i, strArr, iArr);
    }

    private OptionsToShare c() {
        return new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public String getSubjectReceiptToShare() {
                return "Comprobante de Cancelación";
            }

            public View getViewToShare() {
                return ActivityEnvioDineroComprobanteCancelacion.this.comprobanteCancelacion;
            }

            public void receiveIntentAppShare(Intent intent) {
                ActivityEnvioDineroComprobanteCancelacion.this.startActivityForResult(Intent.createChooser(intent, ActivityEnvioDineroComprobanteCancelacion.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 1);
            }

            /* JADX WARNING: Removed duplicated region for block: B:12:0x002b  */
            /* JADX WARNING: Removed duplicated region for block: B:14:0x0030  */
            /* JADX WARNING: Removed duplicated region for block: B:16:0x0042  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.String getFileName() {
                /*
                    r3 = this;
                    ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion.this
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
                    ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion.this
                    java.lang.String r0 = r0.x
                    return r0
                L_0x0030:
                    ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion.this
                    r1 = 2131756358(0x7f100546, float:1.9143621E38)
                    java.lang.String r0 = r0.getString(r1)
                    ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion r1 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion.this
                    java.lang.String r1 = r1.x
                    java.lang.String r0 = r0.concat(r1)
                    return r0
                L_0x0042:
                    ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion r0 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion.this
                    r1 = 2131756356(0x7f100544, float:1.9143617E38)
                    java.lang.String r0 = r0.getString(r1)
                    ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion r1 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion.this
                    java.lang.String r1 = r1.x
                    java.lang.String r0 = r0.concat(r1)
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion.AnonymousClass5.getFileName():java.lang.String");
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                ActivityEnvioDineroComprobanteCancelacion.this.C = true;
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                ActivityEnvioDineroComprobanteCancelacion.this.C = true;
            }

            public void onAbortShare() {
                super.onAbortShare();
                ActivityEnvioDineroComprobanteCancelacion.this.C = true;
                ActivityEnvioDineroComprobanteCancelacion.this.onBackPressed();
            }
        };
    }

    public boolean canExit(int i) {
        if (!this.C) {
            final int i2 = i;
            AnonymousClass6 r1 = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
                public String getFileName() {
                    return "comprobantecancelacion";
                }

                public String getSubjectReceiptToShare() {
                    return "Comprobante de Cancelación";
                }

                public View getViewToShare() {
                    return ActivityEnvioDineroComprobanteCancelacion.this.comprobanteCancelacion;
                }

                public void receiveIntentAppShare(Intent intent) {
                    ActivityEnvioDineroComprobanteCancelacion.this.startActivityForResult(Intent.createChooser(intent, ActivityEnvioDineroComprobanteCancelacion.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 1);
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    ActivityEnvioDineroComprobanteCancelacion.this.C = true;
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    ActivityEnvioDineroComprobanteCancelacion.this.C = true;
                }

                public void onAbortShare() {
                    super.onAbortShare();
                    ActivityEnvioDineroComprobanteCancelacion.this.C = true;
                    ActivityEnvioDineroComprobanteCancelacion.this.onClickItem(i2);
                }
            };
            r1.showAlert();
        }
        return this.C;
    }
}
