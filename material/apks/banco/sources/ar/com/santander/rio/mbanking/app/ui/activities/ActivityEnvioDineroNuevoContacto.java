package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CAmountIU;
import ar.com.santander.rio.mbanking.app.commons.CExtEnv;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.AMAgendadosEnvEfeEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaAgendadosEnvEfeEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAgendadosEnvEfeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaAgendadosEnvEfe;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import ar.com.santander.rio.mbanking.utils.Utils;
import butterknife.InjectView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.lang.reflect.Field;
import javax.inject.Inject;

public class ActivityEnvioDineroNuevoContacto extends BaseActivity {
    String A;
    String B;
    EditText C;
    String D;
    Intent E;
    @InjectView(2131362296)
    Button btn_incorporarDestinatario;
    @InjectView(2131362297)
    TextView inp_destinatarioEmail;
    @InjectView(2131362298)
    TextView inp_destinatarioNombre;
    @InjectView(2131362299)
    TextView inp_destinatarioNroDocumento;
    @InjectView(2131362300)
    TextView inp_destinatarioTipoDocumento;
    @InjectView(2131362301)
    TextView lbl_data_destinatarioNombre;
    @InjectView(2131362302)
    TextView lbl_data_destinatarioNroDocumento;
    @InjectView(2131362303)
    TextView lbl_destinatarioEmail;
    @InjectView(2131362304)
    TextView lbl_destinatarioNombre;
    @InjectView(2131362305)
    TextView lbl_destinatarioNroDocumento;
    @InjectView(2131362306)
    TextView lbl_destinatarioTipoDocumento;
    @InjectView(2131362307)
    TextView lbl_incorporarDestinatario;
    @Inject
    IDataManager p;
    @Inject
    Bus q;
    @Inject
    SessionManager r;
    @Inject
    SoftTokenManager s;
    @Inject
    AnalyticsManager t;
    View u;
    ImageView v;
    String w;
    String x;
    String y;
    String z;

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0194  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.Bundle r7) {
        /*
            r6 = this;
            r0 = 2130772000(0x7f010020, float:1.7147106E38)
            r1 = 2130772003(0x7f010023, float:1.7147112E38)
            r6.overridePendingTransition(r0, r1)
            super.onCreate(r7)
            r7 = 2131492940(0x7f0c004c, float:1.8609346E38)
            r6.setContentView(r7)
            butterknife.ButterKnife.inject(r6)
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r7 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.CANCEL_WITH_CONFIRMAR
            r6.setActionBarType(r7)
            android.support.v7.app.ActionBar r7 = r6.getSupportActionBar()
            android.view.View r7 = r7.getCustomView()
            r6.u = r7
            android.content.Intent r7 = r6.getIntent()
            android.content.res.Resources r0 = r6.getResources()
            r1 = 2131755172(0x7f1000a4, float:1.9141216E38)
            java.lang.String r0 = r0.getString(r1)
            java.lang.String r0 = r7.getStringExtra(r0)
            r6.w = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            ar.com.santander.rio.mbanking.managers.session.SessionManager r1 = r6.r
            java.lang.String[] r1 = ar.com.santander.rio.mbanking.app.commons.CExtEnv.getTiposDocumento(r1)
            java.util.List r1 = java.util.Arrays.asList(r1)
            r0.<init>(r1)
            java.lang.String r1 = r6.w
            int r2 = r1.hashCode()
            r3 = 2123274(0x20660a, float:2.97534E-39)
            r4 = 0
            if (r2 == r3) goto L_0x0064
            r3 = 1996002556(0x76f894fc, float:2.5209207E33)
            if (r2 == r3) goto L_0x005a
            goto L_0x006e
        L_0x005a:
            java.lang.String r2 = "CREATE"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x006e
            r1 = 0
            goto L_0x006f
        L_0x0064:
            java.lang.String r2 = "EDIT"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x006e
            r1 = 1
            goto L_0x006f
        L_0x006e:
            r1 = -1
        L_0x006f:
            r2 = 2131757023(0x7f1007df, float:1.914497E38)
            r3 = 2131757024(0x7f1007e0, float:1.9144972E38)
            r5 = 8
            switch(r1) {
                case 0: goto L_0x0194;
                case 1: goto L_0x009f;
                default: goto L_0x007a;
            }
        L_0x007a:
            android.widget.TextView r7 = r6.inp_destinatarioNombre
            r7.setVisibility(r5)
            android.widget.TextView r7 = r6.inp_destinatarioTipoDocumento
            r7.setVisibility(r5)
            android.widget.TextView r7 = r6.inp_destinatarioNroDocumento
            r7.setVisibility(r5)
            android.widget.TextView r7 = r6.inp_destinatarioEmail
            r7.setVisibility(r5)
            android.widget.TextView r7 = r6.lbl_data_destinatarioNombre
            r7.setVisibility(r5)
            android.widget.TextView r7 = r6.lbl_data_destinatarioNroDocumento
            r7.setVisibility(r5)
            android.widget.TextView r7 = r6.lbl_destinatarioEmail
            r7.setVisibility(r5)
            goto L_0x0231
        L_0x009f:
            android.content.res.Resources r1 = r6.getResources()
            java.lang.String r1 = r1.getString(r3)
            java.lang.String r1 = r7.getStringExtra(r1)
            r6.x = r1
            android.content.res.Resources r1 = r6.getResources()
            r3 = 2131757029(0x7f1007e5, float:1.9144982E38)
            java.lang.String r1 = r1.getString(r3)
            java.lang.String r1 = r7.getStringExtra(r1)
            r6.z = r1
            android.content.res.Resources r1 = r6.getResources()
            r3 = 2131757019(0x7f1007db, float:1.9144962E38)
            java.lang.String r1 = r1.getString(r3)
            java.lang.String r1 = r7.getStringExtra(r1)
            r6.A = r1
            android.content.res.Resources r1 = r6.getResources()
            java.lang.String r1 = r1.getString(r2)
            java.lang.String r7 = r7.getStringExtra(r1)
            r6.B = r7
            android.widget.TextView r7 = r6.inp_destinatarioNombre
            java.lang.String r1 = r6.x
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r7.setText(r1)
            android.widget.TextView r7 = r6.inp_destinatarioNombre
            r7.setEnabled(r4)
            android.widget.TextView r7 = r6.inp_destinatarioNombre
            r7.setVisibility(r5)
            android.widget.TextView r7 = r6.lbl_data_destinatarioNombre
            java.lang.String r1 = r6.x
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r7.setText(r1)
            android.widget.TextView r7 = r6.lbl_data_destinatarioNombre
            r7.setVisibility(r4)
            android.widget.TextView r7 = r6.inp_destinatarioTipoDocumento
            ar.com.santander.rio.mbanking.managers.session.SessionManager r1 = r6.r
            java.lang.String r2 = r6.z
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.CExtEnv.getDescripcionTipoDocumento(r1, r2)
            r7.setText(r1)
            android.widget.TextView r7 = r6.inp_destinatarioTipoDocumento
            r7.setEnabled(r4)
            android.widget.TextView r7 = r6.inp_destinatarioTipoDocumento
            android.content.res.Resources r1 = r6.getResources()
            r2 = 2131099767(0x7f060077, float:1.7811897E38)
            int r1 = r1.getColor(r2)
            r7.setTextColor(r1)
            android.widget.TextView r7 = r6.inp_destinatarioNroDocumento
            java.lang.String r1 = r6.A
            r7.setText(r1)
            android.widget.TextView r7 = r6.inp_destinatarioNroDocumento
            r7.setEnabled(r4)
            android.widget.TextView r7 = r6.inp_destinatarioNroDocumento
            r7.setVisibility(r5)
            android.widget.TextView r7 = r6.lbl_data_destinatarioNroDocumento
            java.lang.String r1 = r6.z
            java.lang.String r2 = r6.A
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.Utils.Documento.format(r1, r2)
            r7.setText(r1)
            android.widget.TextView r7 = r6.lbl_data_destinatarioNroDocumento
            r7.setVisibility(r4)
            android.widget.TextView r7 = r6.inp_destinatarioEmail
            java.lang.String r1 = r6.B
            r7.setText(r1)
            android.widget.TextView r7 = r6.inp_destinatarioEmail
            r7.setVisibility(r4)
            android.widget.TextView r7 = r6.lbl_incorporarDestinatario
            android.content.res.Resources r1 = r6.getResources()
            r2 = 2131755782(0x7f100306, float:1.9142453E38)
            java.lang.String r1 = r1.getString(r2)
            r7.setText(r1)
            android.widget.Button r7 = r6.btn_incorporarDestinatario
            android.content.res.Resources r1 = r6.getResources()
            r2 = 2131755784(0x7f100308, float:1.9142457E38)
            java.lang.String r1 = r1.getString(r2)
            r7.setText(r1)
            android.widget.Button r7 = r6.btn_incorporarDestinatario
            android.widget.Button r1 = r6.btn_incorporarDestinatario
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskBotton(r1)
            r7.setContentDescription(r1)
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r7 = r6.t
            r1 = 2131757586(0x7f100a12, float:1.9146112E38)
            java.lang.String r1 = r6.getString(r1)
            r7.trackScreen(r1)
            goto L_0x0231
        L_0x0194:
            android.content.res.Resources r1 = r6.getResources()
            java.lang.String r1 = r1.getString(r3)
            java.lang.String r1 = r7.getStringExtra(r1)
            r6.x = r1
            android.content.res.Resources r1 = r6.getResources()
            java.lang.String r1 = r1.getString(r2)
            java.lang.String r7 = r7.getStringExtra(r1)
            r6.y = r7
            android.widget.TextView r7 = r6.inp_destinatarioNombre
            java.lang.String r1 = r6.x
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r7.setText(r1)
            android.widget.TextView r7 = r6.inp_destinatarioNombre
            r7.setVisibility(r4)
            android.widget.TextView r7 = r6.lbl_data_destinatarioNombre
            r7.setVisibility(r5)
            java.lang.String r7 = r6.y
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 != 0) goto L_0x01d8
            android.widget.TextView r7 = r6.inp_destinatarioEmail
            java.lang.String r1 = r6.y
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r7.setText(r1)
        L_0x01d8:
            android.widget.TextView r7 = r6.inp_destinatarioNroDocumento
            r7.setVisibility(r4)
            android.widget.TextView r7 = r6.lbl_data_destinatarioNroDocumento
            r7.setVisibility(r5)
            android.widget.TextView r7 = r6.lbl_incorporarDestinatario
            android.content.res.Resources r1 = r6.getResources()
            r2 = 2131755675(0x7f10029b, float:1.9142236E38)
            java.lang.String r1 = r1.getString(r2)
            r7.setText(r1)
            android.widget.Button r7 = r6.btn_incorporarDestinatario
            android.content.res.Resources r1 = r6.getResources()
            r2 = 2131755679(0x7f10029f, float:1.9142244E38)
            java.lang.String r1 = r1.getString(r2)
            r7.setText(r1)
            android.widget.TextView r7 = r6.inp_destinatarioTipoDocumento
            r1 = 2131755803(0x7f10031b, float:1.9142496E38)
            java.lang.String r1 = r6.getString(r1)
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskVinculo(r1)
            r7.setContentDescription(r1)
            android.widget.Button r7 = r6.btn_incorporarDestinatario
            android.widget.Button r1 = r6.btn_incorporarDestinatario
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskBotton(r1)
            r7.setContentDescription(r1)
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r7 = r6.t
            r1 = 2131757588(0x7f100a14, float:1.9146116E38)
            java.lang.String r1 = r6.getString(r1)
            r7.trackScreen(r1)
        L_0x0231:
            android.widget.TextView r7 = r6.inp_destinatarioTipoDocumento
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroNuevoContacto$1 r1 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroNuevoContacto$1
            r1.<init>(r0)
            r7.setOnClickListener(r1)
            android.widget.TextView r7 = r6.lbl_destinatarioNroDocumento     // Catch:{ Exception -> 0x0253 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x0253 }
            r0.<init>(r6)     // Catch:{ Exception -> 0x0253 }
            android.widget.TextView r1 = r6.lbl_destinatarioNroDocumento     // Catch:{ Exception -> 0x0253 }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x0253 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0253 }
            java.lang.String r0 = r0.applyFilterControlNumber(r1)     // Catch:{ Exception -> 0x0253 }
            r7.setContentDescription(r0)     // Catch:{ Exception -> 0x0253 }
        L_0x0253:
            android.view.View r7 = r6.u
            r0 = 2131364245(0x7f0a0995, float:1.8348322E38)
            android.view.View r7 = r7.findViewById(r0)
            android.widget.ImageView r7 = (android.widget.ImageView) r7
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroNuevoContacto$2 r1 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroNuevoContacto$2
            r1.<init>()
            r7.setOnClickListener(r1)
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroNuevoContacto$3 r7 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroNuevoContacto$3
            r7.<init>()
            android.widget.Button r1 = r6.btn_incorporarDestinatario
            r1.setOnClickListener(r7)
            android.view.View r1 = r6.u
            r2 = 2131364360(0x7f0a0a08, float:1.8348555E38)
            android.view.View r1 = r1.findViewById(r2)
            android.widget.ImageView r1 = (android.widget.ImageView) r1
            r6.v = r1
            android.widget.ImageView r1 = r6.v
            android.content.res.Resources r2 = r6.getResources()
            r3 = 2131231005(0x7f08011d, float:1.8078079E38)
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r3)
            r1.setBackground(r2)
            android.widget.ImageView r1 = r6.v
            r1.setOnClickListener(r7)
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroNuevoContacto$4 r7 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroNuevoContacto$4
            r7.<init>()
            android.widget.TextView r1 = r6.inp_destinatarioNombre
            r1.addTextChangedListener(r7)
            android.widget.TextView r1 = r6.inp_destinatarioTipoDocumento
            r1.addTextChangedListener(r7)
            android.widget.TextView r1 = r6.inp_destinatarioNroDocumento
            r1.addTextChangedListener(r7)
            android.widget.TextView r1 = r6.inp_destinatarioEmail
            r1.addTextChangedListener(r7)
            r6.d()
            android.widget.ImageView r7 = r6.v
            r1 = 2131755146(0x7f10008a, float:1.9141163E38)
            java.lang.String r1 = r6.getString(r1)
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskBotton(r1)
            r7.setContentDescription(r1)
            android.view.View r7 = r6.u
            android.view.View r7 = r7.findViewById(r0)
            r0 = 2131755133(0x7f10007d, float:1.9141137E38)
            java.lang.String r0 = r6.getString(r0)
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskBotton(r0)
            r7.setContentDescription(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroNuevoContacto.onCreate(android.os.Bundle):void");
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public boolean changePickerAttributes(NumberPicker numberPicker) {
        int childCount = numberPicker.getChildCount();
        int color = getResources().getColor(R.color.grey_medium_dark);
        int i = 0;
        while (i < childCount) {
            View childAt = numberPicker.getChildAt(i);
            if (childAt instanceof EditText) {
                try {
                    Field declaredField = numberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
                    declaredField.setAccessible(true);
                    ((Paint) declaredField.get(numberPicker)).setColor(color);
                    this.C = (EditText) childAt;
                    this.C.setTextColor(color);
                    this.C.setEnabled(false);
                    this.C.setFocusable(false);
                    this.C.setFocusableInTouchMode(false);
                    numberPicker.invalidate();
                    return true;
                } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException unused) {
                    continue;
                }
            } else {
                i++;
            }
        }
        return false;
    }

    public void applyLetterSpacing() {
        UtilStyleCommons.setLetterSpacing(this.lbl_incorporarDestinatario, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_destinatarioNombre, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_destinatarioTipoDocumento, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_destinatarioNroDocumento, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_destinatarioEmail, -1.5f);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        this.q.register(this);
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.q.unregister(this);
    }

    @Subscribe
    public void onAltaModificacionContacto(AMAgendadosEnvEfeEvent aMAgendadosEnvEfeEvent) {
        dismissProgress();
        final AMAgendadosEnvEfeEvent aMAgendadosEnvEfeEvent2 = aMAgendadosEnvEfeEvent;
        AnonymousClass5 r0 = new BaseWSResponseHandler(this, TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.ENVIO_EFECTIVO, this) {
            public void onOk() {
                ActivityEnvioDineroNuevoContacto.this.a(aMAgendadosEnvEfeEvent2);
            }
        };
        this.btn_incorporarDestinatario.setEnabled(true);
        this.v.setEnabled(true);
        r0.handleWSResponse(aMAgendadosEnvEfeEvent);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002a  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void clearScreenData() {
        /*
            r3 = this;
            java.lang.String r0 = r3.w
            int r1 = r0.hashCode()
            r2 = 2123274(0x20660a, float:2.97534E-39)
            if (r1 == r2) goto L_0x001b
            r2 = 1996002556(0x76f894fc, float:2.5209207E33)
            if (r1 == r2) goto L_0x0011
            goto L_0x0025
        L_0x0011:
            java.lang.String r1 = "CREATE"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0025
            r0 = 0
            goto L_0x0026
        L_0x001b:
            java.lang.String r1 = "EDIT"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0025
            r0 = 1
            goto L_0x0026
        L_0x0025:
            r0 = -1
        L_0x0026:
            switch(r0) {
                case 0: goto L_0x0032;
                case 1: goto L_0x002a;
                default: goto L_0x0029;
            }
        L_0x0029:
            goto L_0x005e
        L_0x002a:
            android.widget.TextView r0 = r3.inp_destinatarioEmail
            java.lang.String r1 = r3.B
            r0.setText(r1)
            goto L_0x005e
        L_0x0032:
            android.widget.TextView r0 = r3.inp_destinatarioNombre
            java.lang.String r1 = ""
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r3.inp_destinatarioNroDocumento
            java.lang.String r1 = ""
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r3.inp_destinatarioEmail
            java.lang.String r1 = ""
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r3.inp_destinatarioTipoDocumento
            java.lang.String r1 = ""
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.setText(r1)
        L_0x005e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroNuevoContacto.clearScreenData():void");
    }

    /* access modifiers changed from: private */
    public void a(AMAgendadosEnvEfeEvent aMAgendadosEnvEfeEvent) {
        try {
            String str = this.w;
            char c = 65535;
            int hashCode = str.hashCode();
            if (hashCode != 2123274) {
                if (hashCode == 1996002556) {
                    if (str.equals("CREATE")) {
                        c = 0;
                    }
                }
            } else if (str.equals("EDIT")) {
                c = 1;
            }
            switch (c) {
                case 0:
                case 1:
                    IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.USER200029_TITLE), Utils.formatIsbanHTMLCode(Html.fromHtml(getString(R.string.USER200029_BODY)).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                    newInstance.setDialogListener(new IDialogListener() {
                        public void onItemSelected(String str) {
                        }

                        public void onNegativeButton() {
                        }

                        public void onPositiveButton() {
                        }

                        public void onSimpleActionButton() {
                            ActivityEnvioDineroNuevoContacto.this.showProgress(VConsultaAgendadosEnvEfe.nameService);
                            CExtEnv.consultaAgendados(ActivityEnvioDineroNuevoContacto.this.p);
                        }
                    });
                    newInstance.show(getSupportFragmentManager(), "Dialog");
                    return;
                default:
                    return;
            }
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onAltaModificacionContactoResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    @Subscribe
    public void onConsultaAgendados(ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent) {
        dismissProgress();
        final ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent2 = consultaAgendadosEnvEfeEvent;
        AnonymousClass7 r0 = new BaseWSResponseHandler(this, TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.ENVIO_EFECTIVO, this) {
            public void onOk() {
                ActivityEnvioDineroNuevoContacto.this.a(consultaAgendadosEnvEfeEvent2);
            }
        };
        r0.handleWSResponse(consultaAgendadosEnvEfeEvent);
    }

    /* access modifiers changed from: private */
    public void a(ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent) {
        try {
            Intent intent = new Intent(this, ActivityEnvioDineroListadoDestinatarios.class);
            intent.putParcelableArrayListExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAAGENDADOS), CExtEnv.getDestinatarios(((ConsultaAgendadosEnvEfeResponseBean) consultaAgendadosEnvEfeEvent.getBeanResponse()).consultaAgendadosEnvEfeBodyResponseBean.listaDestinatarios));
            intent.putExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAAGENDADOS_RES4), ((ConsultaAgendadosEnvEfeResponseBean) consultaAgendadosEnvEfeEvent.getBeanResponse()).consultaAgendadosEnvEfeBodyResponseBean.listaDestinatarios == null ? consultaAgendadosEnvEfeEvent.getMessageToShow() : "");
            dismissProgress();
            setResult(-1, intent);
            onBackPressed();
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onConsultaAgendadosResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    public void salir() {
        if (!c().booleanValue() || this.inp_destinatarioEmail.getText().toString().equalsIgnoreCase(this.B)) {
            onBackPressed();
            return;
        }
        String str = "";
        String str2 = "";
        String str3 = this.w;
        char c = 65535;
        int hashCode = str3.hashCode();
        if (hashCode != 2123274) {
            if (hashCode == 1996002556 && str3.equals("CREATE")) {
                c = 0;
            }
        } else if (str3.equals("EDIT")) {
            c = 1;
        }
        switch (c) {
            case 0:
                str = getString(R.string.USER200030_TITLE);
                str2 = getString(R.string.USER200030_BODY);
                break;
            case 1:
                str = getString(R.string.USER200031_TITLE);
                str2 = getString(R.string.USER200031_BODY).replace(getResources().getString(R.string.STRING_PARAM_KEY), this.inp_destinatarioNombre.getText().toString());
                break;
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(str, str2, null, null, "Sí", "No", null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                View a2 = ActivityEnvioDineroNuevoContacto.this.b();
                if (a2 != null) {
                    a2.requestFocus();
                    if (a2 == ActivityEnvioDineroNuevoContacto.this.inp_destinatarioEmail) {
                        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(ActivityEnvioDineroNuevoContacto.this.getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), ActivityEnvioDineroNuevoContacto.this.getString(R.string.MSG_USER000020_SusSorpresa_errorCorreo), null, null, ActivityEnvioDineroNuevoContacto.this.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        newInstance.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                            }
                        });
                        newInstance.show(ActivityEnvioDineroNuevoContacto.this.getSupportFragmentManager(), "Dialog");
                    } else if (a2 == ActivityEnvioDineroNuevoContacto.this.inp_destinatarioNroDocumento) {
                        IsbanDialogFragment newInstance2 = IsbanDialogFragment.newInstance(ActivityEnvioDineroNuevoContacto.this.getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), ActivityEnvioDineroNuevoContacto.this.getString(R.string.MSG_USER000020_EnvExt_NuevoDestinatario_doc_invalido), null, null, ActivityEnvioDineroNuevoContacto.this.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        newInstance2.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                            }
                        });
                        newInstance2.show(ActivityEnvioDineroNuevoContacto.this.getSupportFragmentManager(), "Dialog");
                    }
                } else {
                    ActivityEnvioDineroNuevoContacto.this.grabar();
                }
            }

            public void onNegativeButton() {
                ActivityEnvioDineroNuevoContacto.this.onBackPressed();
            }
        });
        newInstance.show(getSupportFragmentManager(), "Dialog");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x00e1, code lost:
        if (r2.equals("CREATE") == false) goto L_0x00ee;
     */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x00f3  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x00fe  */
    /* JADX WARNING: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void grabar() {
        /*
            r6 = this;
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            r6.E = r0
            android.content.Intent r0 = r6.E
            android.content.res.Resources r1 = r6.getResources()
            r2 = 2131757024(0x7f1007e0, float:1.9144972E38)
            java.lang.String r1 = r1.getString(r2)
            android.widget.TextView r2 = r6.inp_destinatarioNombre
            java.lang.CharSequence r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            r0.putExtra(r1, r2)
            android.content.Intent r0 = r6.E
            android.content.res.Resources r1 = r6.getResources()
            r2 = 2131757029(0x7f1007e5, float:1.9144982E38)
            java.lang.String r1 = r1.getString(r2)
            android.widget.TextView r2 = r6.inp_destinatarioTipoDocumento
            java.lang.CharSequence r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            r0.putExtra(r1, r2)
            android.content.Intent r0 = r6.E
            android.content.res.Resources r1 = r6.getResources()
            r2 = 2131757019(0x7f1007db, float:1.9144962E38)
            java.lang.String r1 = r1.getString(r2)
            android.widget.TextView r2 = r6.inp_destinatarioNroDocumento
            java.lang.CharSequence r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            r0.putExtra(r1, r2)
            android.content.Intent r0 = r6.E
            android.content.res.Resources r1 = r6.getResources()
            r2 = 2131757023(0x7f1007df, float:1.914497E38)
            java.lang.String r1 = r1.getString(r2)
            android.widget.TextView r2 = r6.inp_destinatarioEmail
            java.lang.CharSequence r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            r0.putExtra(r1, r2)
            android.widget.Button r0 = r6.btn_incorporarDestinatario
            r1 = 0
            r0.setEnabled(r1)
            android.widget.ImageView r0 = r6.v
            r0.setEnabled(r1)
            ar.com.santander.rio.mbanking.managers.security.SoftTokenManager r0 = r6.s
            java.lang.Boolean r0 = r0.isSoftTokenAvailable()
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x0108
            ar.com.santander.rio.mbanking.services.soap.beans.body.AMAgendadosEnvEfeDestinatarioBean r0 = new ar.com.santander.rio.mbanking.services.soap.beans.body.AMAgendadosEnvEfeDestinatarioBean
            android.widget.TextView r2 = r6.inp_destinatarioNombre
            java.lang.CharSequence r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            ar.com.santander.rio.mbanking.managers.session.SessionManager r3 = r6.r
            android.widget.TextView r4 = r6.inp_destinatarioTipoDocumento
            java.lang.CharSequence r4 = r4.getText()
            java.lang.String r4 = r4.toString()
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.commons.CExtEnv.getCodigoTipoDocumento(r3, r4)
            ar.com.santander.rio.mbanking.app.commons.CAmountIU r4 = ar.com.santander.rio.mbanking.app.commons.CAmountIU.getInstance()
            android.widget.TextView r5 = r6.inp_destinatarioNroDocumento
            java.lang.CharSequence r5 = r5.getText()
            java.lang.String r5 = r5.toString()
            java.lang.Double r4 = r4.getDoubleFromInputUser(r5)
            int r4 = r4.intValue()
            java.lang.String r4 = java.lang.String.valueOf(r4)
            android.widget.TextView r5 = r6.inp_destinatarioEmail
            java.lang.CharSequence r5 = r5.getText()
            java.lang.String r5 = r5.toString()
            r0.<init>(r2, r3, r4, r5)
            java.lang.String r2 = r6.w
            r3 = -1
            int r4 = r2.hashCode()
            r5 = 2123274(0x20660a, float:2.97534E-39)
            if (r4 == r5) goto L_0x00e4
            r5 = 1996002556(0x76f894fc, float:2.5209207E33)
            if (r4 == r5) goto L_0x00db
            goto L_0x00ee
        L_0x00db:
            java.lang.String r4 = "CREATE"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x00ee
            goto L_0x00ef
        L_0x00e4:
            java.lang.String r1 = "EDIT"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x00ee
            r1 = 1
            goto L_0x00ef
        L_0x00ee:
            r1 = -1
        L_0x00ef:
            switch(r1) {
                case 0: goto L_0x00fe;
                case 1: goto L_0x00f3;
                default: goto L_0x00f2;
            }
        L_0x00f2:
            goto L_0x0108
        L_0x00f3:
            java.lang.String r1 = "abmAgendadosEnvEfe"
            r6.showProgress(r1)
            ar.com.santander.rio.mbanking.managers.data.IDataManager r1 = r6.p
            r1.MDestinatario(r0, r6)
            goto L_0x0108
        L_0x00fe:
            java.lang.String r1 = "abmAgendadosEnvEfe"
            r6.showProgress(r1)
            ar.com.santander.rio.mbanking.managers.data.IDataManager r1 = r6.p
            r1.ADestinatario(r0, r6)
        L_0x0108:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroNuevoContacto.grabar():void");
    }

    /* access modifiers changed from: private */
    public View b() {
        if (this.inp_destinatarioNombre.getText().toString().isEmpty()) {
            return this.inp_destinatarioNombre;
        }
        if (this.inp_destinatarioTipoDocumento.getText().toString().isEmpty()) {
            return this.inp_destinatarioTipoDocumento;
        }
        if (this.inp_destinatarioNroDocumento.getText().toString().isEmpty() || this.inp_destinatarioNroDocumento.getText().toString().length() < 9) {
            return this.inp_destinatarioNroDocumento;
        }
        if (!CExtEnv.isEmailValid(this.inp_destinatarioEmail.getText().toString()).booleanValue()) {
            return this.inp_destinatarioEmail;
        }
        return null;
    }

    private Boolean c() {
        Double doubleFromInputUser = CAmountIU.getInstance().getDoubleFromInputUser(this.inp_destinatarioNroDocumento.getText().toString());
        return Boolean.valueOf(!this.inp_destinatarioNombre.getText().toString().isEmpty() && !this.inp_destinatarioNroDocumento.getText().toString().isEmpty() && doubleFromInputUser != null && doubleFromInputUser.intValue() != 0 && !this.inp_destinatarioEmail.getText().toString().isEmpty() && !this.inp_destinatarioTipoDocumento.getText().toString().isEmpty() && !this.inp_destinatarioEmail.getText().toString().equalsIgnoreCase(this.B));
    }

    /* access modifiers changed from: private */
    public void d() {
        if (c().booleanValue()) {
            this.btn_incorporarDestinatario.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
            this.btn_incorporarDestinatario.setTextColor(getResources().getColor(R.color.white));
            this.btn_incorporarDestinatario.setEnabled(true);
            this.v.setBackground(getResources().getDrawable(R.drawable.ic_check));
            this.v.setEnabled(true);
            return;
        }
        this.btn_incorporarDestinatario.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
        this.btn_incorporarDestinatario.setTextColor(getResources().getColor(R.color.white));
        this.btn_incorporarDestinatario.setEnabled(false);
        this.v.setBackground(getResources().getDrawable(R.drawable.ic_check_trans));
        this.v.setEnabled(false);
    }
}
