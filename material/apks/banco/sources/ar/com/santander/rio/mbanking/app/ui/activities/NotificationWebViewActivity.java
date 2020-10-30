package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import javax.inject.Inject;

public class NotificationWebViewActivity extends BaseActivity {
    public static final String ID_NOTIFICATION_PARAM = "id_url_notification";
    public static final String SEGMENT_PARAM = "push_segment";
    public static final String URL_NOTIFICATION_PARAM = "push_url_notification";
    @Inject
    AnalyticsManager p;
    View q;
    ImageView r;
    private String s;
    private String t;
    private String u = "";
    @InjectView(2131366431)
    WebView webView;

    public void initialize() {
        if (getIntent().hasExtra(SEGMENT_PARAM)) {
            this.s = getIntent().getStringExtra(SEGMENT_PARAM);
        }
        if (getIntent().hasExtra(URL_NOTIFICATION_PARAM)) {
            this.t = getIntent().getStringExtra(URL_NOTIFICATION_PARAM);
        }
        if (getIntent().hasExtra(ID_NOTIFICATION_PARAM)) {
            this.u = getIntent().getStringExtra(ID_NOTIFICATION_PARAM);
        }
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.PUSH_CLOSE, this.s);
        this.q = getSupportActionBar().getCustomView();
        this.r = (ImageView) ButterKnife.findById(this.q, (int) R.id.ok);
        this.r.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NotificationWebViewActivity.this.onBackPressed();
            }
        });
    }

    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.notification_view);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.webView.loadUrl(this.t);
        this.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                webView.loadUrl(str);
                return true;
            }
        });
        this.webView.getSettings().setBuiltInZoomControls(true);
        this.webView.getSettings().setDisplayZoomControls(false);
        this.webView.getSettings().setJavaScriptEnabled(true);
        if (!TextUtils.isEmpty(this.u)) {
            this.p.trackEvent(getString(R.string.analytics_category_buzon_novedades_push), getString(R.string.analytics_event_action_buzon_novedades_push_impresion_novedad), this.u);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
