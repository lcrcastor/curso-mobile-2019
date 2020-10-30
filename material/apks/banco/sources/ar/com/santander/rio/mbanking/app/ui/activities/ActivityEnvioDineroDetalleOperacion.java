package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.CancelaMandatoExtEnvEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.CancelaMandatoExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CancelaMandatoExtEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import butterknife.InjectView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class ActivityEnvioDineroDetalleOperacion extends BaseActivity {
    /* access modifiers changed from: private */
    public String A;
    /* access modifiers changed from: private */
    public String B;
    /* access modifiers changed from: private */
    public String C;
    /* access modifiers changed from: private */
    public String D;
    private String E;
    private String F;
    @InjectView(2131362379)
    TextView F12_08_lbl_detalleDocumento;
    /* access modifiers changed from: private */
    public String G;
    private String H;
    private String I;
    private String J;
    /* access modifiers changed from: private */
    public String K;
    /* access modifiers changed from: private */
    public String L;
    /* access modifiers changed from: private */
    public String M;
    /* access modifiers changed from: private */
    public String N;
    @InjectView(2131362367)
    TextView detalleCodTransaccion;
    @InjectView(2131362368)
    TextView detalleCuenta;
    @InjectView(2131362370)
    TextView detalleDocumento;
    @InjectView(2131362371)
    TextView detalleEstado;
    @InjectView(2131362372)
    TextView detalleFecha;
    @InjectView(2131362365)
    ImageView detalleImagenEstado;
    @InjectView(2131362373)
    TextView detalleImporte;
    @InjectView(2131362369)
    TextView detalleNombre;
    @InjectView(2131362374)
    TextView detalleNroComprobante;
    @InjectView(2131362366)
    TextView lbl_InformacionExtraccion;
    @InjectView(2131362375)
    TextView lbl_datosOperacion;
    @InjectView(2131362376)
    TextView lbl_detalleCodTransaccion;
    @InjectView(2131362377)
    TextView lbl_detalleCuenta;
    @InjectView(2131362378)
    TextView lbl_detalleDestinatario;
    @InjectView(2131362380)
    TextView lbl_detalleEstado;
    @InjectView(2131362381)
    TextView lbl_detalleFechaAlta;
    @InjectView(2131362382)
    TextView lbl_detalleImporte;
    @InjectView(2131362383)
    TextView lbl_detalleNroComprobante;
    @InjectView(2131362384)
    TextView lbl_detalleOperacion;
    @InjectView(2131362385)
    TextView lbl_operacionValidaHasta;
    @InjectView(2131362363)
    Button mCancelarOperacionButton;
    @InjectView(2131362390)
    RelativeLayout mGrupoDestinatario;
    @InjectView(2131362364)
    Button mRepetirOperacionButton;
    @Inject
    IDataManager p;
    @Inject
    SessionManager q;
    @Inject
    SoftTokenManager r;
    @Inject
    AnalyticsManager s;
    @Inject
    Bus t;
    ImageView u;
    /* access modifiers changed from: private */
    public CancelaMandatoExtEnvBodyRequestBean v;
    /* access modifiers changed from: private */
    public String w;
    /* access modifiers changed from: private */
    public String x;
    /* access modifiers changed from: private */
    public String y;
    /* access modifiers changed from: private */
    public Integer z;

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x01d1, code lost:
        if (r7.equals(ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants.Estado.Vencido) != false) goto L_0x01fd;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.Bundle r7) {
        /*
            r6 = this;
            r0 = 2130772000(0x7f010020, float:1.7147106E38)
            r1 = 2130772003(0x7f010023, float:1.7147112E38)
            r6.overridePendingTransition(r0, r1)
            super.onCreate(r7)
            r7 = 2131492938(0x7f0c004a, float:1.8609342E38)
            r6.setContentView(r7)
            butterknife.ButterKnife.inject(r6)
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r7 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.BACK_ONLY
            r6.setActionBarType(r7)
            android.support.v7.app.ActionBar r7 = r6.getSupportActionBar()
            r7.getCustomView()
            r7 = 2131364173(0x7f0a094d, float:1.8348176E38)
            android.view.View r7 = r6.findViewById(r7)
            android.widget.ImageView r7 = (android.widget.ImageView) r7
            r6.u = r7
            android.widget.ImageView r7 = r6.u
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroDetalleOperacion$1 r0 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroDetalleOperacion$1
            r0.<init>()
            r7.setOnClickListener(r0)
            android.content.Intent r7 = r6.getIntent()
            r0 = 2131755173(0x7f1000a5, float:1.9141218E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = r7.getStringExtra(r0)
            java.lang.String r0 = r0.toUpperCase()
            r6.I = r0
            r0 = 2131755174(0x7f1000a6, float:1.914122E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = r7.getStringExtra(r0)
            r6.E = r0
            r0 = 2131755176(0x7f1000a8, float:1.9141224E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = r7.getStringExtra(r0)
            r6.F = r0
            r0 = 2131757017(0x7f1007d9, float:1.9144958E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = r7.getStringExtra(r0)
            r6.A = r0
            r0 = 2131757028(0x7f1007e4, float:1.914498E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = r7.getStringExtra(r0)
            r6.B = r0
            r0 = 2131757027(0x7f1007e3, float:1.9144978E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = r7.getStringExtra(r0)
            r6.C = r0
            r0 = 2131757026(0x7f1007e2, float:1.9144976E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = r7.getStringExtra(r0)
            r6.D = r0
            r0 = 2131757024(0x7f1007e0, float:1.9144972E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = r7.getStringExtra(r0)
            r6.w = r0
            r0 = 2131757029(0x7f1007e5, float:1.9144982E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = r7.getStringExtra(r0)
            r6.x = r0
            r0 = 2131757019(0x7f1007db, float:1.9144962E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = r7.getStringExtra(r0)
            r6.y = r0
            r0 = 2131757018(0x7f1007da, float:1.914496E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = r7.getStringExtra(r0)
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r6.z = r0
            r0 = 2131755160(0x7f100098, float:1.9141191E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = r7.getStringExtra(r0)
            r6.J = r0
            r0 = 2131755161(0x7f100099, float:1.9141193E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = r7.getStringExtra(r0)
            r6.G = r0
            r0 = 2131755179(0x7f1000ab, float:1.914123E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = r7.getStringExtra(r0)
            r6.H = r0
            r0 = 2131757022(0x7f1007de, float:1.9144968E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = r7.getStringExtra(r0)
            r6.M = r0
            r0 = 2131757023(0x7f1007df, float:1.914497E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r7 = r7.getStringExtra(r0)
            r6.N = r7
            android.widget.TextView r7 = r6.detalleFecha
            java.lang.String r0 = r6.E
            java.lang.String r0 = ar.com.santander.rio.mbanking.utils.UtilDate.getDateFormat(r0)
            r7.setText(r0)
            android.widget.TextView r7 = r6.detalleCuenta
            java.lang.String r0 = r6.A
            r7.setText(r0)
            java.lang.String r7 = r6.M
            java.lang.String r0 = "ENV"
            boolean r7 = r7.equalsIgnoreCase(r0)
            r0 = 2131362407(0x7f0a0267, float:1.8344594E38)
            r1 = 8
            if (r7 == 0) goto L_0x015c
            java.lang.String r7 = r6.w
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 != 0) goto L_0x014f
            android.widget.TextView r7 = r6.detalleNombre
            java.lang.String r0 = r6.w
            android.text.Spanned r0 = android.text.Html.fromHtml(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r0 = ar.com.santander.rio.mbanking.utils.UtilString.capitalize(r0)
            r7.setText(r0)
            goto L_0x0172
        L_0x014f:
            android.view.View r7 = r6.findViewById(r0)
            r7.setVisibility(r1)
            android.widget.RelativeLayout r7 = r6.mGrupoDestinatario
            r7.setVisibility(r1)
            goto L_0x0172
        L_0x015c:
            java.lang.String r7 = r6.M
            java.lang.String r2 = "EXT"
            boolean r7 = r7.equalsIgnoreCase(r2)
            if (r7 == 0) goto L_0x0172
            android.view.View r7 = r6.findViewById(r0)
            r7.setVisibility(r1)
            android.widget.RelativeLayout r7 = r6.mGrupoDestinatario
            r7.setVisibility(r1)
        L_0x0172:
            android.widget.TextView r7 = r6.detalleDocumento
            java.lang.String r0 = "%s %s"
            r1 = 2
            java.lang.Object[] r2 = new java.lang.Object[r1]
            ar.com.santander.rio.mbanking.managers.session.SessionManager r3 = r6.q
            java.lang.String r4 = r6.x
            java.lang.String r4 = ar.com.santander.rio.mbanking.app.commons.CExtEnv.convertCodigoTipoDocumentoBanelco(r4)
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.commons.CExtEnv.getDescripcionTipoDocumento(r3, r4)
            r4 = 0
            r2[r4] = r3
            java.lang.String r3 = r6.x
            java.lang.String r5 = r6.y
            java.lang.String r3 = ar.com.santander.rio.mbanking.utils.Utils.Documento.mask(r3, r5)
            r5 = 1
            r2[r5] = r3
            java.lang.String r0 = java.lang.String.format(r0, r2)
            r7.setText(r0)
            android.widget.TextView r7 = r6.detalleImporte
            java.lang.Integer r0 = r6.z
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.CExtEnv.getFormattedValue(r0)
            r7.setText(r0)
            android.widget.TextView r7 = r6.detalleCodTransaccion
            java.lang.String r0 = r6.G
            r7.setText(r0)
            android.widget.TextView r7 = r6.detalleNroComprobante
            java.lang.String r0 = r6.H
            r7.setText(r0)
            android.widget.TextView r7 = r6.detalleEstado
            ar.com.santander.rio.mbanking.managers.session.SessionManager r0 = r6.q
            java.lang.String r2 = r6.I
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.CExtEnv.getDescripcionByEstado(r0, r2)
            r7.setText(r0)
            java.lang.String r7 = r6.I
            r0 = -1
            int r2 = r7.hashCode()
            switch(r2) {
                case 65861: goto L_0x01f2;
                case 66480: goto L_0x01e8;
                case 66902: goto L_0x01de;
                case 79097: goto L_0x01d4;
                case 84863: goto L_0x01cb;
                default: goto L_0x01ca;
            }
        L_0x01ca:
            goto L_0x01fc
        L_0x01cb:
            java.lang.String r2 = "VEN"
            boolean r7 = r7.equals(r2)
            if (r7 == 0) goto L_0x01fc
            goto L_0x01fd
        L_0x01d4:
            java.lang.String r1 = "PEN"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x01fc
            r1 = 1
            goto L_0x01fd
        L_0x01de:
            java.lang.String r1 = "COB"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x01fc
            r1 = 0
            goto L_0x01fd
        L_0x01e8:
            java.lang.String r1 = "CAN"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x01fc
            r1 = 3
            goto L_0x01fd
        L_0x01f2:
            java.lang.String r1 = "BLO"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x01fc
            r1 = 4
            goto L_0x01fd
        L_0x01fc:
            r1 = -1
        L_0x01fd:
            switch(r1) {
                case 0: goto L_0x022d;
                case 1: goto L_0x0217;
                case 2: goto L_0x0201;
                case 3: goto L_0x0201;
                case 4: goto L_0x0201;
                default: goto L_0x0200;
            }
        L_0x0200:
            goto L_0x0242
        L_0x0201:
            android.widget.ImageView r7 = r6.detalleImagenEstado
            android.content.res.Resources r0 = r6.getResources()
            r1 = 2131231065(0x7f080159, float:1.80782E38)
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r1)
            r7.setImageDrawable(r0)
            android.widget.Button r7 = r6.mRepetirOperacionButton
            r7.setVisibility(r4)
            goto L_0x0242
        L_0x0217:
            android.widget.ImageView r7 = r6.detalleImagenEstado
            android.content.res.Resources r0 = r6.getResources()
            r1 = 2131231064(0x7f080158, float:1.8078198E38)
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r1)
            r7.setImageDrawable(r0)
            android.widget.Button r7 = r6.mCancelarOperacionButton
            r7.setVisibility(r4)
            goto L_0x0242
        L_0x022d:
            android.widget.ImageView r7 = r6.detalleImagenEstado
            android.content.res.Resources r0 = r6.getResources()
            r1 = 2131231063(0x7f080157, float:1.8078196E38)
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r1)
            r7.setImageDrawable(r0)
            android.widget.Button r7 = r6.mRepetirOperacionButton
            r7.setVisibility(r4)
        L_0x0242:
            android.widget.ImageView r7 = r6.detalleImagenEstado
            ar.com.santander.rio.mbanking.managers.session.SessionManager r0 = r6.q
            java.lang.String r1 = r6.I
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.CExtEnv.getDescripcionByEstado(r0, r1)
            r7.setContentDescription(r0)
            android.widget.Button r7 = r6.mCancelarOperacionButton
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroDetalleOperacion$2 r0 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroDetalleOperacion$2
            r0.<init>()
            r7.setOnClickListener(r0)
            android.widget.Button r7 = r6.mRepetirOperacionButton
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroDetalleOperacion$3 r0 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroDetalleOperacion$3
            r0.<init>()
            r7.setOnClickListener(r0)
            java.lang.String r7 = r6.M
            java.lang.String r0 = "ENV"
            boolean r7 = r7.equalsIgnoreCase(r0)
            r0 = 33
            if (r7 == 0) goto L_0x0315
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r7 = r6.s
            r1 = 2131757585(0x7f100a11, float:1.914611E38)
            java.lang.String r1 = r6.getString(r1)
            r7.trackScreen(r1)
            android.widget.TextView r7 = r6.lbl_detalleOperacion
            r1 = 2131755711(0x7f1002bf, float:1.914231E38)
            r7.setText(r1)
            android.widget.TextView r7 = r6.lbl_datosOperacion
            r1 = 2131755713(0x7f1002c1, float:1.9142313E38)
            r7.setText(r1)
            android.text.SpannableStringBuilder r7 = new android.text.SpannableStringBuilder
            r7.<init>()
            java.lang.String r1 = "ENV"
            java.lang.String r2 = r6.I
            int r1 = ar.com.santander.rio.mbanking.app.commons.CExtEnv.getLeyendaValidaByEstado(r1, r2)
            java.lang.String r1 = r6.getString(r1)
            android.text.SpannableStringBuilder r1 = r7.append(r1)
            java.lang.String r2 = " "
            android.text.SpannableStringBuilder r1 = r1.append(r2)
            java.lang.String r2 = r6.F
            r1.append(r2)
            uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan r1 = new uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan
            android.content.res.AssetManager r2 = r6.getAssets()
            java.lang.String r3 = "fonts/OpenSans-Bold.otf"
            android.graphics.Typeface r2 = uk.co.chrisjenx.calligraphy.TypefaceUtils.load(r2, r3)
            r1.<init>(r2)
            java.lang.String r2 = "ENV"
            java.lang.String r3 = r6.I
            int r2 = ar.com.santander.rio.mbanking.app.commons.CExtEnv.getLeyendaValidaByEstado(r2, r3)
            java.lang.String r2 = r6.getString(r2)
            int r2 = r2.length()
            java.lang.String r3 = r7.toString()
            int r3 = r3.length()
            r7.setSpan(r1, r2, r3, r0)
            android.widget.TextView r0 = r6.lbl_operacionValidaHasta
            android.widget.TextView$BufferType r1 = android.widget.TextView.BufferType.SPANNABLE
            r0.setText(r7, r1)
            android.widget.TextView r7 = r6.lbl_operacionValidaHasta
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "ENV"
            java.lang.String r2 = r6.I
            int r1 = ar.com.santander.rio.mbanking.app.commons.CExtEnv.getLeyendaValidaByEstado(r1, r2)
            java.lang.String r1 = r6.getString(r1)
            r0.append(r1)
            java.lang.String r1 = " "
            r0.append(r1)
            java.lang.String r1 = r6.F
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r7.setContentDescription(r0)
            android.widget.Button r7 = r6.mRepetirOperacionButton
            r0 = 2131755726(0x7f1002ce, float:1.914234E38)
            r7.setText(r0)
            android.widget.Button r7 = r6.mCancelarOperacionButton
            r0 = 2131755734(0x7f1002d6, float:1.9142356E38)
            r7.setText(r0)
            goto L_0x03c3
        L_0x0315:
            java.lang.String r7 = r6.M
            java.lang.String r1 = "EXT"
            boolean r7 = r7.equalsIgnoreCase(r1)
            if (r7 == 0) goto L_0x03c3
            android.widget.TextView r7 = r6.lbl_detalleOperacion
            r1 = 2131755699(0x7f1002b3, float:1.9142285E38)
            r7.setText(r1)
            android.widget.TextView r7 = r6.lbl_datosOperacion
            r1 = 2131755701(0x7f1002b5, float:1.9142289E38)
            r7.setText(r1)
            android.text.SpannableStringBuilder r7 = new android.text.SpannableStringBuilder
            r7.<init>()
            java.lang.String r1 = "EXT"
            java.lang.String r2 = r6.I
            int r1 = ar.com.santander.rio.mbanking.app.commons.CExtEnv.getLeyendaValidaByEstado(r1, r2)
            java.lang.String r1 = r6.getString(r1)
            android.text.SpannableStringBuilder r1 = r7.append(r1)
            java.lang.String r2 = " "
            android.text.SpannableStringBuilder r1 = r1.append(r2)
            java.lang.String r2 = r6.F
            r1.append(r2)
            uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan r1 = new uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan
            android.content.res.AssetManager r2 = r6.getAssets()
            java.lang.String r3 = "fonts/OpenSans-Bold.otf"
            android.graphics.Typeface r2 = uk.co.chrisjenx.calligraphy.TypefaceUtils.load(r2, r3)
            r1.<init>(r2)
            java.lang.String r2 = "EXT"
            java.lang.String r3 = r6.I
            int r2 = ar.com.santander.rio.mbanking.app.commons.CExtEnv.getLeyendaValidaByEstado(r2, r3)
            java.lang.String r2 = r6.getString(r2)
            int r2 = r2.length()
            java.lang.String r3 = r7.toString()
            int r3 = r3.length()
            r7.setSpan(r1, r2, r3, r0)
            android.widget.TextView r0 = r6.lbl_operacionValidaHasta
            android.widget.TextView$BufferType r1 = android.widget.TextView.BufferType.SPANNABLE
            r0.setText(r7, r1)
            android.widget.TextView r7 = r6.lbl_operacionValidaHasta
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "EXT"
            java.lang.String r2 = r6.I
            int r1 = ar.com.santander.rio.mbanking.app.commons.CExtEnv.getLeyendaValidaByEstado(r1, r2)
            java.lang.String r1 = r6.getString(r1)
            r0.append(r1)
            java.lang.String r1 = " "
            r0.append(r1)
            java.lang.String r1 = r6.F
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r7.setContentDescription(r0)
            android.widget.Button r7 = r6.mRepetirOperacionButton
            r0 = 2131755712(0x7f1002c0, float:1.9142311E38)
            r7.setText(r0)
            android.widget.Button r7 = r6.mCancelarOperacionButton
            r0 = 2131755725(0x7f1002cd, float:1.9142337E38)
            r7.setText(r0)
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r7 = r6.s
            r0 = 2131757649(0x7f100a51, float:1.914624E38)
            java.lang.String r0 = r6.getString(r0)
            r7.trackScreen(r0)
        L_0x03c3:
            android.widget.TextView r7 = r6.detalleCuenta     // Catch:{ Exception -> 0x03d4 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x03d4 }
            r0.<init>(r6)     // Catch:{ Exception -> 0x03d4 }
            java.lang.String r1 = r6.A     // Catch:{ Exception -> 0x03d4 }
            java.lang.String r0 = r0.applyFilterAccount(r1)     // Catch:{ Exception -> 0x03d4 }
            r7.setContentDescription(r0)     // Catch:{ Exception -> 0x03d4 }
            goto L_0x03d8
        L_0x03d4:
            r7 = move-exception
            r7.printStackTrace()
        L_0x03d8:
            android.widget.TextView r7 = r6.detalleImporte     // Catch:{ Exception -> 0x03f1 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x03f1 }
            r0.<init>(r6)     // Catch:{ Exception -> 0x03f1 }
            android.widget.TextView r1 = r6.detalleImporte     // Catch:{ Exception -> 0x03f1 }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x03f1 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x03f1 }
            java.lang.String r0 = r0.applyFilterAmount(r1)     // Catch:{ Exception -> 0x03f1 }
            r7.setContentDescription(r0)     // Catch:{ Exception -> 0x03f1 }
            goto L_0x03f5
        L_0x03f1:
            r7 = move-exception
            r7.printStackTrace()
        L_0x03f5:
            android.widget.TextView r7 = r6.detalleCodTransaccion     // Catch:{ Exception -> 0x040e }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x040e }
            r0.<init>(r6)     // Catch:{ Exception -> 0x040e }
            android.widget.TextView r1 = r6.detalleCodTransaccion     // Catch:{ Exception -> 0x040e }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x040e }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x040e }
            java.lang.String r0 = r0.applyFilterCharacterToCharacter(r1)     // Catch:{ Exception -> 0x040e }
            r7.setContentDescription(r0)     // Catch:{ Exception -> 0x040e }
            goto L_0x0412
        L_0x040e:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0412:
            android.widget.TextView r7 = r6.detalleNroComprobante     // Catch:{ Exception -> 0x042b }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x042b }
            r0.<init>(r6)     // Catch:{ Exception -> 0x042b }
            android.widget.TextView r1 = r6.detalleNroComprobante     // Catch:{ Exception -> 0x042b }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x042b }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x042b }
            java.lang.String r0 = r0.applyFilterCharacterToCharacter(r1)     // Catch:{ Exception -> 0x042b }
            r7.setContentDescription(r0)     // Catch:{ Exception -> 0x042b }
            goto L_0x042f
        L_0x042b:
            r7 = move-exception
            r7.printStackTrace()
        L_0x042f:
            android.widget.Button r7 = r6.mCancelarOperacionButton
            android.widget.Button r0 = r6.mCancelarOperacionButton
            java.lang.CharSequence r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskBotton(r0)
            r7.setContentDescription(r0)
            android.widget.Button r7 = r6.mRepetirOperacionButton
            android.widget.Button r0 = r6.mRepetirOperacionButton
            java.lang.CharSequence r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskBotton(r0)
            r7.setContentDescription(r0)
            android.widget.ImageView r7 = r6.u
            r0 = 2131755130(0x7f10007a, float:1.914113E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskBotton(r0)
            r7.setContentDescription(r0)
            android.widget.TextView r7 = r6.lbl_detalleNroComprobante
            android.widget.TextView r0 = r6.lbl_detalleNroComprobante
            java.lang.CharSequence r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "Nro."
            r2 = 2131756141(0x7f10046d, float:1.9143181E38)
            java.lang.String r2 = r6.getString(r2)
            java.lang.String r0 = r0.replace(r1, r2)
            r7.setContentDescription(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroDetalleOperacion.onCreate(android.os.Bundle):void");
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void applyLetterSpacing() {
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleOperacion, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_datosOperacion, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleEstado, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.detalleEstado, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleFechaAlta, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.detalleFecha, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleCuenta, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.detalleCuenta, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleDestinatario, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.detalleNombre, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_InformacionExtraccion, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.F12_08_lbl_detalleDocumento, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.detalleDocumento, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleImporte, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.detalleImporte, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleCodTransaccion, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.detalleCodTransaccion, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.lbl_detalleNroComprobante, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.detalleNroComprobante, -1.5f, false);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        Intent intent2 = new Intent();
        if (i == 3) {
            switch (i2) {
                case -1:
                    if (intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
                        intent2.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION));
                    } else if (intent.hasExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION)) {
                        intent2.putExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, intent.getIntExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, -1));
                    }
                    intent2.putExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_ENV, intent.getBooleanExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_ENV, false));
                    intent2.putExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_EXT, intent.getBooleanExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_EXT, false));
                    setResult(-1, intent2);
                    finish();
                    return;
                default:
                    return;
            }
        } else if (i == 9 && i2 == -1) {
            if (intent.hasExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION)) {
                intent2.putExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, intent.getIntExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, -1));
            }
            intent2.putExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_ENV, intent.getBooleanExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_ENV, false));
            intent2.putExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_EXT, intent.getBooleanExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_EXT, false));
            setResult(-1, intent2);
            finish();
        }
    }

    @Subscribe
    public void onCancelaMandato(CancelaMandatoExtEnvEvent cancelaMandatoExtEnvEvent) {
        dismissProgress();
        final CancelaMandatoExtEnvEvent cancelaMandatoExtEnvEvent2 = cancelaMandatoExtEnvEvent;
        AnonymousClass4 r0 = new BaseWSResponseHandler(this, TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.ENVIO_EFECTIVO, this) {
            public void onOk() {
                ActivityEnvioDineroDetalleOperacion.this.a(cancelaMandatoExtEnvEvent2);
            }

            public void commonAllErrorsAfterProcess(WebServiceEvent webServiceEvent) {
                ActivityEnvioDineroDetalleOperacion.this.mCancelarOperacionButton.setEnabled(true);
            }
        };
        r0.handleWSResponse(cancelaMandatoExtEnvEvent);
    }

    /* access modifiers changed from: private */
    public void a(CancelaMandatoExtEnvEvent cancelaMandatoExtEnvEvent) {
        try {
            CancelaMandatoExtEnvResponseBean cancelaMandatoExtEnvResponseBean = (CancelaMandatoExtEnvResponseBean) cancelaMandatoExtEnvEvent.getBeanResponse();
            Intent intent = new Intent(this, ActivityEnvioDineroComprobanteCancelacion.class);
            intent.putExtra(getString(R.string.INTENT_PUT_EXTRA_NOMBRE), this.w);
            intent.putExtra(getString(R.string.INTENT_PUT_EXTRA_TIPODOC), this.x);
            intent.putExtra(getString(R.string.INTENT_PUT_EXTRA_DOCUMENTO), this.y);
            intent.putExtra(getString(R.string.INTENT_PUT_EXTRA_DINERO), this.z.toString());
            intent.putExtra(getString(R.string.INTENT_PUT_EXTRA_CUENTA), this.A);
            intent.putExtra(getString(R.string.ED_INTENT_PUT_EXTRA_FECHA_ALTA_OR), this.E);
            intent.putExtra(getString(R.string.ED_INTENT_PUT_EXTRA_NRO_COMPROBANTE_OR), cancelaMandatoExtEnvResponseBean.cancelaMandatoExtEnvBodyResponseBean.comprobante.numComprobante);
            intent.putExtra(getString(R.string.ED_INTENT_PUT_EXTRA_COD_TRANSACCION_OR), this.G);
            intent.putExtra(getString(R.string.ED_INTENT_PUT_EXTRA_FECHA_VENCIMIENTO_OR), this.F);
            intent.putExtra(getString(R.string.ED_INTENT_PUT_EXTRA_FECHA_CANCELACION_OR), cancelaMandatoExtEnvResponseBean.cancelaMandatoExtEnvBodyResponseBean.comprobante.fechaCancelacion);
            intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_FUNCIONALIDAD), this.M);
            startActivityForResult(intent, 9);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onCancelaMandatoResultOk: ", e);
            e.fillInStackTrace();
        }
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
}
