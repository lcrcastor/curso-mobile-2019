package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.DataManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public class LoginInfoActivity extends BaseActivity implements IBaseView {
    public static String ACTION_GENERATE = "GENERATE";
    public static String CALL_TO_ACTION = "CALL_TO_ACTION";
    public static String HTML_FORMAT = "<html><head><style type=\"text/css\"></style><style type=\"text/css\"></style></head><body>VALOR_SERVICIO</body></html>";
    public static final String NEW_USUARIO = "NEW_USUARIO";
    public static final String SERVICE_NAME = "consultaLeyendasGenerales";
    @InjectView(2131364225)
    Button buttonGenerar;
    @InjectView(2131364230)
    Button buttonYaTengo;
    @InjectView(2131364603)
    WebView detailWB;
    @Inject
    AnalyticsManager p;
    @Inject
    Bus q;
    @Inject
    IDataManager r;
    @Inject
    SettingsManager s;
    @Inject
    SessionManager t;
    private boolean u = false;
    private DataManager v;

    public void attachView() {
    }

    public void configureActionBar() {
    }

    public void configureLayout() {
    }

    public void detachView() {
    }

    public void dismissProgressIndicator() {
    }

    public String getTAG() {
        return "";
    }

    public void initialize() {
    }

    public void showProgressIndicator(String str) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.login_info_view);
        this.q.register(this);
        this.v = new DataManager(BaseApplication.get(this), this.q, this.t, this.s);
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        ButterKnife.inject((Activity) this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
        supportActionBar.setDisplayOptions(16);
        View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.actionbar_back, null);
        supportActionBar.setCustomView(inflate, new LayoutParams(-1, -1, 17));
        inflate.findViewById(R.id.ok).setVisibility(4);
        inflate.findViewById(R.id.toggle).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LoginInfoActivity.this.onBackPressed();
            }
        });
        this.u = getIntent().getExtras().getBoolean(CALL_TO_ACTION, false);
        if (!this.u) {
            this.p.trackScreen(getString(R.string.analytics_screen_name_login_alta_usuario_sin_call_to_action));
            this.buttonGenerar.setVisibility(8);
            this.buttonYaTengo.setVisibility(8);
        } else {
            this.p.trackScreen(getString(R.string.analytics_screen_name_login_alta_usuario_con_call_to_action));
        }
        showProgress("consultaLeyendasGenerales");
        this.r.consultaLeyendasGenerales("NEW_USUARIO");
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x00cd A[Catch:{ Exception -> 0x00d6 }] */
    @com.squareup.otto.Subscribe
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onConsultaLeyenda(ar.com.santander.rio.mbanking.services.events.ConsultaLeyendasEvent r9) {
        /*
            r8 = this;
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r0 = r9.getResult()
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r1 = ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult.OK
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x006e
            com.indra.httpclient.beans.IBeanWS r9 = r9.getBeanResponse()
            ar.com.santander.rio.mbanking.services.soap.beans.ConsultaLeyendasResponseBean r9 = (ar.com.santander.rio.mbanking.services.soap.beans.ConsultaLeyendasResponseBean) r9
            if (r9 != 0) goto L_0x0015
            return
        L_0x0015:
            ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaLeyendasBodyResponseBean r9 = r9.getConsultaLeyendasBodyResponseBean()
            if (r9 != 0) goto L_0x001c
            return
        L_0x001c:
            r8.dismissProgress()
            java.lang.String r0 = HTML_FORMAT
            java.lang.String r1 = "VALOR_SERVICIO"
            ar.com.santander.rio.mbanking.services.model.general.Leyenda r9 = r9.leyenda
            java.lang.String r9 = r9.getDescripcion()
            java.lang.String r9 = r0.replace(r1, r9)
            android.webkit.WebView r0 = r8.detailWB
            r1 = 8
            r0.setVisibility(r1)
            android.webkit.WebView r2 = r8.detailWB
            r3 = 0
            r5 = 0
            java.lang.String r6 = "utf-8"
            r7 = 0
            r4 = r9
            r2.loadDataWithBaseURL(r3, r4, r5, r6, r7)
            android.webkit.WebView r0 = r8.detailWB
            r0.reload()
            android.webkit.WebView r0 = r8.detailWB
            r1 = 0
            r0.setVisibility(r1)
            android.webkit.WebView r0 = r8.detailWB     // Catch:{ Exception -> 0x0069 }
            android.content.Context r1 = r8.getApplicationContext()     // Catch:{ Exception -> 0x0069 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x0069 }
            java.lang.String r9 = ar.com.santander.rio.mbanking.utils.Utils.formatIsbanHTMLCode(r9)     // Catch:{ Exception -> 0x0069 }
            android.text.Spanned r9 = android.text.Html.fromHtml(r9)     // Catch:{ Exception -> 0x0069 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0069 }
            java.lang.String r9 = r1.applyFilterGeneral(r9)     // Catch:{ Exception -> 0x0069 }
            r0.setContentDescription(r9)     // Catch:{ Exception -> 0x0069 }
            goto L_0x00d6
        L_0x0069:
            r9 = move-exception
            r9.printStackTrace()
            goto L_0x00d6
        L_0x006e:
            r8.dismissProgress()
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r0 = r9.getResult()     // Catch:{ Exception -> 0x00d6 }
            r9.getTypeOption()     // Catch:{ Exception -> 0x00d6 }
            java.lang.String r9 = r9.getTitleToShow()     // Catch:{ Exception -> 0x00d6 }
            if (r9 == 0) goto L_0x0089
            java.lang.String r1 = ""
            boolean r1 = r1.equalsIgnoreCase(r9)     // Catch:{ Exception -> 0x00d6 }
            if (r1 == 0) goto L_0x0087
            goto L_0x0089
        L_0x0087:
            r0 = r9
            goto L_0x009d
        L_0x0089:
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r9 = ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult.BEAN_WARNING     // Catch:{ Exception -> 0x00d6 }
            if (r0 == r9) goto L_0x0095
            r9 = 2131756514(0x7f1005e2, float:1.9143938E38)
            java.lang.String r9 = r8.getString(r9)     // Catch:{ Exception -> 0x00d6 }
            goto L_0x0087
        L_0x0095:
            r9 = 2131756513(0x7f1005e1, float:1.9143936E38)
            java.lang.String r9 = r8.getString(r9)     // Catch:{ Exception -> 0x00d6 }
            goto L_0x0087
        L_0x009d:
            android.content.res.Resources r9 = r8.getResources()     // Catch:{ Exception -> 0x00d6 }
            r1 = 2131757115(0x7f10083b, float:1.9145157E38)
            java.lang.String r9 = r9.getString(r1)     // Catch:{ Exception -> 0x00d6 }
            android.text.Spanned r9 = android.text.Html.fromHtml(r9)     // Catch:{ Exception -> 0x00d6 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x00d6 }
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.Utils.formatIsbanHTMLCode(r9)     // Catch:{ Exception -> 0x00d6 }
            r2 = 0
            r3 = 0
            r9 = 2131755637(0x7f100275, float:1.9142159E38)
            java.lang.String r4 = r8.getString(r9)     // Catch:{ Exception -> 0x00d6 }
            r5 = 0
            r6 = 0
            ar.com.santander.rio.mbanking.components.IsbanDialogFragment r9 = ar.com.santander.rio.mbanking.components.IsbanDialogFragment.newInstance(r0, r1, r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x00d6 }
            ar.com.santander.rio.mbanking.app.ui.activities.LoginInfoActivity$2 r0 = new ar.com.santander.rio.mbanking.app.ui.activities.LoginInfoActivity$2     // Catch:{ Exception -> 0x00d6 }
            r0.<init>()     // Catch:{ Exception -> 0x00d6 }
            r9.setDialogListener(r0)     // Catch:{ Exception -> 0x00d6 }
            if (r9 == 0) goto L_0x00d6
            android.support.v4.app.FragmentManager r0 = r8.getSupportFragmentManager()     // Catch:{ Exception -> 0x00d6 }
            java.lang.String r1 = "Dialog"
            r9.show(r0, r1)     // Catch:{ Exception -> 0x00d6 }
        L_0x00d6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.LoginInfoActivity.onConsultaLeyenda(ar.com.santander.rio.mbanking.services.events.ConsultaLeyendasEvent):void");
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.q.unregister(this);
        super.onDestroy();
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131364225})
    public void b() {
        this.p.trackEvent(getString(R.string.analytics_category_login), getString(R.string.analytics_action_login_quiero_generar_usuario), getString(R.string.analytics_action_login_quiero_generar_usuario));
        Intent intent = new Intent();
        intent.putExtra(ACTION_GENERATE, true);
        setResult(-1, intent);
        finish();
        d();
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131364230})
    public void c() {
        this.p.trackEvent(getString(R.string.analytics_category_login), getString(R.string.analytics_action_login_ya_tengo_usuario), getString(R.string.analytics_action_login_ya_tengo_usuario));
        Intent intent = new Intent();
        intent.putExtra(ACTION_GENERATE, false);
        setResult(-1, intent);
        finish();
        d();
    }

    private void d() {
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

    public void onBackPressed() {
        super.onBackPressed();
        d();
    }
}
