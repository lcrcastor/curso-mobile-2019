package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.AccessibilityEvent;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.analytics.InsuranceAnalytics.Screens;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.AceptaTerminosEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.versions.VAceptaTerminos;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultValues;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class InfoActivity extends BaseActivity implements OnClickListener {
    public static String CONTENT_DESCRIPTION = "CONTENT_DESCRIPTION";
    public static String HTML_FORMAT = "<html><head><style type=\"text/css\">body{color: #333333; background-color: #FFF;}</style><style type=\"text/css\">@font-face { font-family: MyFont; src: url(\"file:///android_asset/fonts/OpenSans-Regular.otf\")}body { font-family: MyFont; font-size: '10.0pt'; text-align: left;}</style></head><body>VALOR_SERVICIO</body></html>";
    public static String ICON_TO_SHOW = "ICON_TO_SHOW";
    public static String SUBTITLE_TO_SHOW = "SUBTITLE_TO_SHOW";
    public static String TEXT_TO_SHOW = "TEXT_TO_SHOW";
    public static String TITLE_TO_SHOW = "TITLE_TO_SHOW";
    public static String TYPE_INFO = "TYPE_INFO";
    private int A = 0;
    /* access modifiers changed from: private */
    public InfoType B;
    @InjectView(2131364117)
    Button aceptarButton;
    @InjectView(2131364739)
    TextView htmlSubTitle;
    @InjectView(2131364738)
    TextView htmlTV;
    @InjectView(2131365258)
    Button noAceptarButton;
    @Inject
    Bus p;
    @Inject
    IDataManager q;
    @Inject
    SessionManager r;
    @Inject
    AnalyticsManager s;
    @InjectView(2131365639)
    ScrollView scrollView;
    CAccessibility t;
    @InjectView(2131366042)
    LinearLayout titleRow;
    @InjectView(2131366043)
    TextView titleWebView;
    private View u;
    private ImageView v;
    @InjectView(2131364106)
    View viewSeparatorSectionTitle;
    private String w;
    @InjectView(2131366432)
    WebView webView;
    private String x;
    private String y;
    private String z;

    class FilterAsync extends AsyncTask<String, String, String> {
        protected String a;

        private FilterAsync() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doInBackground(String... strArr) {
            this.a = strArr[0];
            try {
                return CAccessibility.getInstance(InfoActivity.this.getApplicationContext()).applyFilterGeneral(Html.fromHtml(Utils.formatIsbanHTMLCode(this.a)).toString());
            } catch (Exception e) {
                e.printStackTrace();
                return Html.fromHtml(Utils.formatIsbanHTMLCode(this.a)).toString();
            }
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(String str) {
            InfoActivity.this.p.post(new AccessibilityEvent(this.a, str));
        }
    }

    public enum InfoType {
        RECUPERAR_CLAVE,
        AYUDA,
        CONDICIONES,
        DEFAULT,
        HTMLFORMATTED,
        DEFAULT_WITH_ACCESSIBILITY_LOADING,
        WEBVIEW_HTTP_NO_TITLE,
        REINICIO_APP,
        OLVIDASTE_TU_CLAVE_2,
        WEBVIEW_HTTP_NO_TITLE_WOMEN
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_recuperar_password);
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        ButterKnife.inject((Activity) this);
        this.t = CAccessibility.getInstance(getApplicationContext());
        this.w = getIntent().getStringExtra(TITLE_TO_SHOW);
        this.x = getIntent().getExtras().getString(TEXT_TO_SHOW);
        this.z = getIntent().getExtras().getString(SUBTITLE_TO_SHOW);
        this.A = getIntent().getExtras().getInt(ICON_TO_SHOW, 0);
        this.y = getIntent().getExtras().getString(CONTENT_DESCRIPTION);
        f();
        d();
        b();
    }

    private void b() {
        if (this.y == null) {
            return;
        }
        if (this.y.equalsIgnoreCase(getString(R.string.ID_4796_SEGUROS_TIT_COTIZAR_COBERTURA)) || this.y.equalsIgnoreCase(getString(R.string.ID_4134_SEGUROS_LBL_CONF_CONTR_SGRO))) {
            this.s.trackScreen(Screens.TCObjectsInsurance());
            c();
        }
    }

    private void c() {
        this.viewSeparatorSectionTitle.setVisibility(8);
    }

    private void d() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
        supportActionBar.setDisplayOptions(16);
        setActionBarType(ActionBarType.BACK);
        this.u = supportActionBar.getCustomView();
        this.u.findViewById(R.id.toggle).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                InfoActivity.this.onBackPressed();
            }
        });
        e();
    }

    private void e() {
        if (this.B.equals(InfoType.WEBVIEW_HTTP_NO_TITLE_WOMEN)) {
            this.v = (ImageView) this.u.findViewById(R.id.ok);
            this.v.setVisibility(8);
        }
    }

    private void f() {
        try {
            this.B = (InfoType) getIntent().getSerializableExtra(TYPE_INFO);
        } catch (Exception unused) {
            this.B = InfoType.valueOf(getIntent().getStringExtra(TYPE_INFO));
        }
    }

    public void onRestartAPP() {
        Log.e(Constants.TAG_LOG_ERROR_SESSION_LOST, "InfoActivity.onRestartAPP");
        Crashlytics.log("Constants.TAG_LOG_ERROR_SESSION_LOSTInfoActivity.onRestartAPP");
        Bundle bundle = new Bundle();
        bundle.putString("SESSION_PENDING", getIntent().getStringExtra("SESSION_PENDING"));
        bundle.putString("NUP_PENDING", getIntent().getStringExtra("NUP_PENDING"));
        this.r.setSession(null);
        this.r.setNup(null);
        Intent launchIntentForPackage = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        launchIntentForPackage.setFlags(268468224);
        launchIntentForPackage.putExtras(bundle);
        startActivity(launchIntentForPackage);
    }

    public void onBackPressed() {
        if (this.B == InfoType.CONDICIONES) {
            h();
        } else if (this.B == InfoType.REINICIO_APP) {
            onRestartAPP();
        } else {
            super.onBackPressed();
            finish();
            i();
        }
    }

    @Subscribe
    public void onAceptaTyC(AceptaTerminosEvent aceptaTerminosEvent) {
        if (aceptaTerminosEvent.getResult() == TypeResult.OK) {
            aceptaTerminosEvent.getBeanResponse();
            Intent intent = new Intent();
            intent.putExtra(VAceptaTerminos.nameService, ResultValues.OK);
            setResult(-1, intent);
            finish();
            i();
            return;
        }
        checkError(aceptaTerminosEvent, "");
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        this.p.register(this);
        g();
        super.onResume();
    }

    private void g() {
        if (this.A > 0) {
            ((ImageView) this.u.findViewById(R.id.ok)).setImageResource(this.A);
        }
        if (!TextUtils.isEmpty(this.z)) {
            this.htmlSubTitle.setVisibility(0);
            this.htmlSubTitle.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(this.z)));
            try {
                this.htmlSubTitle.setContentDescription(this.t.applyFilterGeneral(this.htmlSubTitle.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.B == InfoType.RECUPERAR_CLAVE) {
            this.u.findViewById(R.id.toggle).setVisibility(4);
            this.u.findViewById(R.id.ok).setVisibility(0);
            this.u.findViewById(R.id.ok).setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.CONTENT_LISTO)));
            this.u.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    InfoActivity.this.onBackPressed();
                }
            });
            this.titleWebView.setText(getResources().getString(R.string.RECUPERAR_PASSWORD_TITLE));
            try {
                this.titleWebView.setContentDescription(this.t.applyFilterGeneral(this.x));
            } catch (Exception unused) {
            }
            String replace = HTML_FORMAT.replace("VALOR_SERVICIO", this.x);
            this.webView.loadData(replace, "text/html", "UTF-8");
            this.webView.setContentDescription(CAccessibility.getInstance(this).applyFilterPagina(replace));
        } else if (this.B == InfoType.AYUDA) {
            this.titleWebView.setText(getResources().getString(R.string.LOGIN_AYUDA_TITULO_CLAVE_VENCIDA));
            try {
                this.titleWebView.setContentDescription(this.t.applyFilterGeneral(getResources().getString(R.string.LOGIN_AYUDA_TITULO_CLAVE_VENCIDA)));
            } catch (Exception unused2) {
            }
            String replace2 = HTML_FORMAT.replace("VALOR_SERVICIO", this.x);
            this.webView.loadDataWithBaseURL("file:///android_asset/", replace2, "text/html", "utf-8", null);
            this.webView.setContentDescription(CAccessibility.getInstance(this).applyFilterPagina(replace2));
            ((MarginLayoutParams) this.webView.getLayoutParams()).setMargins(35, 0, 35, 0);
        } else if (this.B == InfoType.CONDICIONES) {
            this.aceptarButton.setOnClickListener(this);
            this.aceptarButton.setVisibility(0);
            this.noAceptarButton.setVisibility(0);
            this.noAceptarButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    InfoActivity.this.h();
                }
            });
            this.titleWebView.setText(getResources().getString(R.string.CONDICIONES_TITLE));
            this.u.findViewById(R.id.toggle).setVisibility(4);
            this.htmlTV.setVisibility(0);
            this.htmlTV.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(this.x)));
            this.htmlTV.setMovementMethod(LinkMovementMethod.getInstance());
            this.webView.setVisibility(8);
        } else if (this.B == InfoType.DEFAULT || this.B == InfoType.REINICIO_APP) {
            this.titleWebView.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(getIntent().getExtras().getString(TITLE_TO_SHOW))));
            this.htmlTV.setVisibility(0);
            this.htmlTV.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(this.x)));
            if (this.y == null || this.y.isEmpty()) {
                try {
                    this.htmlTV.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterGeneral(this.htmlTV.getText().toString()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                this.htmlTV.setContentDescription(Html.fromHtml(this.y));
            }
            this.htmlTV.setMovementMethod(LinkMovementMethod.getInstance());
            this.webView.setVisibility(8);
            this.u.findViewById(R.id.toggle).setVisibility(4);
            this.u.findViewById(R.id.ok).setVisibility(0);
            this.u.findViewById(R.id.ok).setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.CONTENT_LISTO)));
            this.u.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (InfoActivity.this.B == InfoType.DEFAULT) {
                        InfoActivity.this.onBackPressed();
                    } else if (InfoActivity.this.B == InfoType.REINICIO_APP) {
                        InfoActivity.this.onRestartAPP();
                    }
                }
            });
        } else if (this.B == InfoType.DEFAULT_WITH_ACCESSIBILITY_LOADING) {
            this.titleWebView.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(getIntent().getExtras().getString(TITLE_TO_SHOW))));
            this.htmlTV.setVisibility(0);
            this.htmlTV.setText(Html.fromHtml(this.x));
            applyFilterGeneralAsync(this.x);
            this.htmlTV.setMovementMethod(LinkMovementMethod.getInstance());
            this.webView.setVisibility(8);
            this.u.findViewById(R.id.toggle).setVisibility(4);
            this.u.findViewById(R.id.ok).setVisibility(0);
            this.u.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    InfoActivity.this.onBackPressed();
                }
            });
        } else if (this.B == InfoType.HTMLFORMATTED) {
            this.titleWebView.setText(getIntent().getExtras().getString(TITLE_TO_SHOW));
            try {
                this.titleWebView.setContentDescription(this.t.applyFilterGeneral(getIntent().getExtras().getString(TITLE_TO_SHOW)));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            String replace3 = HTML_FORMAT.replace("VALOR_SERVICIO", this.x);
            this.webView.loadDataWithBaseURL("file:///android_asset/", replace3, "text/html", "utf-8", null);
            this.webView.setContentDescription(CAccessibility.getInstance(this).applyFilterPagina(replace3));
        } else if (this.B == InfoType.WEBVIEW_HTTP_NO_TITLE) {
            this.titleRow.setVisibility(8);
            this.webView.getSettings().setJavaScriptEnabled(true);
            this.webView.setWebViewClient(new WebViewClient());
            this.webView.loadUrl(Utils.formatIsbanHTMLCode(this.x));
            this.scrollView.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });
        } else if (this.B == InfoType.OLVIDASTE_TU_CLAVE_2) {
            this.titleWebView.setText(this.w);
            try {
                this.titleWebView.setContentDescription(this.t.applyFilterGeneral(this.w));
            } catch (Exception unused3) {
            }
            String replace4 = HTML_FORMAT.replace("VALOR_SERVICIO", this.x);
            this.webView.loadDataWithBaseURL("file:///android_asset/", replace4, "text/html", "utf-8", null);
            this.webView.setContentDescription(CAccessibility.getInstance(this).applyFilterPagina(replace4));
        } else if (this.B == InfoType.WEBVIEW_HTTP_NO_TITLE_WOMEN) {
            this.titleRow.setVisibility(8);
            this.webView.getSettings().setJavaScriptEnabled(true);
            this.webView.getSettings().setLoadWithOverviewMode(true);
            this.webView.getSettings().setUseWideViewPort(true);
            this.webView.getSettings().setBuiltInZoomControls(true);
            this.webView.getSettings().setSupportZoom(true);
            this.webView.setPadding(0, 0, 0, 0);
            this.webView.setWebViewClient(new WebViewClient());
            this.webView.loadUrl(Utils.formatIsbanHTMLCode(this.x));
            this.scrollView.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });
        }
        this.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                webView.loadUrl(str);
                return true;
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.p.unregister(this);
    }

    /* access modifiers changed from: private */
    public void h() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getResources().getString(R.string.MSG_USER000030_Menu_avisoSalir), null, null, "Sí", "No", null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                InfoActivity.this.q.logOutSession();
                InfoActivity.this.r.cleanPrivateData();
                InfoActivity.this.s.trackEvent(InfoActivity.this.getString(R.string.analytics_category_login), InfoActivity.this.getString(R.string.analytics_action_login_terminos_y_condiciones), InfoActivity.this.getString(R.string.analytics_label_login_terminos_y_condiciones_no_aceptacion));
                Intent intent = new Intent();
                intent.putExtra(VAceptaTerminos.nameService, "CANCEL");
                InfoActivity.this.setResult(-1, intent);
                InfoActivity.this.finish();
                InfoActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        newInstance.show(getSupportFragmentManager(), "Dialog");
    }

    public void onClick(View view) {
        if (this.B == InfoType.CONDICIONES) {
            this.q.aceptaTerminos(this.r.getLoginUnico().getDatosPersonales().getNup());
            return;
        }
        finish();
        i();
    }

    private void i() {
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

    public void applyFilterGeneralAsync(String str) {
        new FilterAsync().execute(new String[]{str});
    }

    @Subscribe
    public void onApplyFilterGeneralAsync(AccessibilityEvent accessibilityEvent) {
        this.htmlTV.setContentDescription(accessibilityEvent.accessibilityText);
    }
}
