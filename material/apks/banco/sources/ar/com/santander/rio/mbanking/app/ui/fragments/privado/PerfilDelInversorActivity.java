package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.module.inversionesbancaprivada.analytics.InversionesAnalyticsImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import javax.inject.Inject;

public class PerfilDelInversorActivity extends BaseActivity {
    public static final String PERFIL_AGRESIVO = "AGRESIVO";
    public static final String PERFIL_CONSERVADOR = "CONSERVADOR";
    public static final String PERFIL_MODERADO = "MODERADO";
    public static final String TAG_DESCRIPCION_PERFIL = "DESCRIPCION_PERFIL";
    public static final String TAG_NOMBRE_PERFIL = "NOMBRE_PERFIL";
    @Inject
    public AnalyticsManager analyticsManager;
    @InjectView(2131366097)
    TextView descripcionPerfil;
    @InjectView(2131366266)
    TextView nombrePerfil;
    private String p;
    private String q;
    /* access modifiers changed from: private */
    public WebView r;
    /* access modifiers changed from: private */
    public InversionesAnalyticsImpl s;
    @InjectView(2131364716)
    TextView tvTitleView;
    @InjectView(2131366265)
    TextView tvmasinfo;

    class MyWebViewCliente extends WebViewClient {
        private MyWebViewCliente() {
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            return true;
        }
    }

    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_perfil_del_inversor);
        ButterKnife.inject((Activity) this);
        this.p = getIntent().getStringExtra("NOMBRE_PERFIL");
        this.q = getIntent().getStringExtra("DESCRIPCION_PERFIL");
        this.tvTitleView.setText(getString(R.string.TITLE_PERFIL_DEL_INVERSOR));
        this.nombrePerfil.setText(Html.fromHtml(this.p));
        if (this.q != null) {
            this.descripcionPerfil.setText(Html.fromHtml(this.q));
        }
        this.s = new InversionesAnalyticsImpl(this, this.analyticsManager);
        String str = this.p;
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -1679334424) {
            if (hashCode != 137749950) {
                if (hashCode == 163769117 && str.equals(PERFIL_MODERADO)) {
                    c = 2;
                }
            } else if (str.equals(PERFIL_CONSERVADOR)) {
                c = 1;
            }
        } else if (str.equals(PERFIL_AGRESIVO)) {
            c = 0;
        }
        switch (c) {
            case 0:
                this.s.registerScreenProfileAgresivo();
                break;
            case 1:
                this.s.registerScreenProfileConservador();
                break;
            case 2:
                this.s.registerScreenProfileModerado();
                break;
        }
        this.s.registerScreenNonProfile();
        configureActionBar();
        this.tvmasinfo.setText("Más información");
        this.tvmasinfo.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PerfilDelInversorActivity.this.setContentView((int) R.layout.webview);
                PerfilDelInversorActivity.this.r = (WebView) PerfilDelInversorActivity.this.findViewById(R.id.webview);
                WebSettings settings = PerfilDelInversorActivity.this.r.getSettings();
                StringBuilder sb = new StringBuilder();
                sb.append(PerfilDelInversorActivity.this.r.getSettings().getUserAgentString());
                sb.append("-MB");
                settings.setUserAgentString(sb.toString());
                PerfilDelInversorActivity.this.r.setWebViewClient(new MyWebViewCliente());
                PerfilDelInversorActivity.this.r.getSettings().setJavaScriptEnabled(true);
                PerfilDelInversorActivity.this.r.loadUrl("https://www.santander.com.ar/banco/online/personas/inversiones/super-fondos/clasificacion-productos?utm_source=Online_Banking&utm_medium=OBP&utm_content=footer_OBP&utm_campaign=Inversiones_OB");
                PerfilDelInversorActivity.this.s.PerfilInversorMasInfoConcentracionActivos();
            }
        });
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        enableBackButton();
    }

    public void enableBackButton() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    PerfilDelInversorActivity.this.finish();
                    PerfilDelInversorActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }
    }
}
