package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.ITRSABaseActivity;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public class FirmaCredinActivity extends ITRSABaseActivity implements OnClickListener {
    @Inject
    IDataManager p;
    @Inject
    SessionManager q;
    @Inject
    AnalyticsManager r;
    CAccessibility s;
    @Inject
    Bus t;
    private View u;
    private String v;
    private String w;
    @InjectView(2131366429)
    WebView webView;

    public void onClick(View view) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_firma_credin_view);
        this.t.register(this);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        ButterKnife.inject((Activity) this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
        supportActionBar.setDisplayOptions(16);
        this.u = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.actionbar_back_only, null);
        supportActionBar.setCustomView(this.u, new LayoutParams(-1, -1, 17));
        this.u.findViewById(R.id.back_imgButton).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FirmaCredinActivity.this.onBackPressed();
            }
        });
        this.s = CAccessibility.getInstance(getApplicationContext());
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        this.t.register(this);
        b();
        super.onResume();
    }

    private void b() {
        showProgress("prisma");
        this.v = getIntent().getStringExtra(DebinConstants.INTENT_URL_CREDIN);
        this.w = getIntent().getStringExtra(DebinConstants.INTENT_FIRMA_CREDIN);
        this.webView.setWebViewClient(new WebViewClient() {
            boolean a = false;

            public void onPageFinished(WebView webView, String str) {
                if (this.a) {
                    FirmaCredinActivity.this.dismissProgress();
                } else {
                    this.a = true;
                }
            }

            public void onReceivedError(WebView webView, int i, String str, String str2) {
                if (this.a) {
                    FirmaCredinActivity.this.dismissProgress();
                } else {
                    this.a = true;
                }
            }

            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                if (this.a) {
                    sslErrorHandler.proceed();
                    FirmaCredinActivity.this.dismissProgress();
                    return;
                }
                this.a = true;
            }
        });
        this.webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setUseWideViewPort(true);
        this.webView.getSettings().setDomStorageEnabled(true);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setAllowFileAccess(true);
        this.webView.getSettings().setAllowContentAccess(true);
        this.webView.getSettings().setAllowFileAccessFromFileURLs(true);
        this.webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        this.webView.loadDataWithBaseURL(this.v, "<!DOCTYPE html><html> <head> <title></title> </head> <body> <form name=\"formPRISMA\" action=\"URL_PRISMA\" method=\"post\" id=\"formPrismaId\" style=\"visibility: hidden;\"> <input type=\"hidden\" name=\"token\" value=\"TOKEN_PRISMA\"> <input type=\"submit\" value=\"Submit\" id=\"submit\"> </form> </body> <script>window.onload=function (){document.getElementById(\"submit\").click();}</script></html>".replace("URL_PRISMA", this.v).replace("TOKEN_PRISMA", this.w), "text/html", "utf-8", this.v);
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        c();
    }

    private void c() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void dismissProgress() {
        super.dismissProgress();
    }
}
