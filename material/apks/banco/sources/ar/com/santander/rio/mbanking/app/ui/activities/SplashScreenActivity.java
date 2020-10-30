package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.fragments.ISelectorEnvironmentFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.SelectorEnvironmentFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.checkversion.CheckVersionManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.notifications.PushNotificationsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.redessociales.IRedesSocialesManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.CheckVersionEvent;
import ar.com.santander.rio.mbanking.services.events.ConsDescripcionesEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment;
import ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment.Environment;
import ar.com.santander.rio.mbanking.utils.DeepLinkUtils;
import ar.com.santander.rio.mbanking.utils.SplashUtils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.scottyab.rootbeer.RootBeer;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import dagger.Lazy;
import java.io.File;
import javax.inject.Inject;

public class SplashScreenActivity extends BaseActivity implements AnimationListener {
    public static String EXTRA_ERROR = "ERROR";
    public static String LOG_TAG = "ar.com.santander.rio.mbanking.app.ui.activities.SplashScreenActivity";
    /* access modifiers changed from: private */
    public CheckVersionManager A;
    private Enum B;
    private boolean C = false;
    private RootBeer D;
    @InjectView(2131365208)
    public View mLogoCompany;
    @InjectView(2131365759)
    public TextView mSplashLegalText;
    @InjectView(2131365234)
    public TextView mockVersion;
    @Inject
    Bus p;
    @InjectView(2131365399)
    public ProgressBar progressBar;
    @Inject
    IRedesSocialesManager q;
    @Inject
    Lazy<IDataManager> r;
    @Inject
    AnalyticsManager s;
    @Inject
    SettingsManager t;
    @Inject
    SessionManager u;
    @Inject
    PushNotificationsManager v;
    @Inject
    SoftTokenManager w;
    String x = "";
    private ActionBar y;
    private CountDownTimer z;

    public void onAnimationRepeat(Animation animation) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.splash_screen);
        ButterKnife.inject((Activity) this);
        this.D = new RootBeer(this);
        this.y = getSupportActionBar();
        this.y.setElevation(BitmapDescriptorFactory.HUE_RED);
        this.y.setDisplayOptions(16);
        this.y.setHomeButtonEnabled(false);
        this.y.setHomeButtonEnabled(false);
        this.y.setCustomView(((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.actionbar_title_only, null), new LayoutParams(-1, -1));
        this.y.hide();
        this.s.trackScreen(getString(R.string.analytics_screen_name_login_splash));
        this.v.setUp(this);
        a(this.v.checkPushNotification(getIntent()));
        this.q.registerTwitter(this, getString(R.string.ANDROID_SECRET_TWITTER));
        this.A = new CheckVersionManager(this.u, this);
        this.u.setCheckVersion(false);
        c();
    }

    private boolean b() {
        File fileStreamPath = getBaseContext().getFileStreamPath(EXTRA_ERROR);
        if (!fileStreamPath.exists()) {
            return false;
        }
        fileStreamPath.delete();
        return true;
    }

    /* access modifiers changed from: private */
    public void c() {
        try {
            this.x = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.fillInStackTrace();
            String str = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("PackageManager.NameNotFoundException: ");
            sb.append(e);
            Log.e(str, sb.toString());
        }
        ManagerTypeEnvironment.setCurrentEnvironment(getString(R.string.INIT_ENVIRONMENT_GRADLE), this);
        Crashlytics.setBool("debug_build", false);
        if (!ManagerTypeEnvironment.getCurrentEnvironment(this).getEnvironmentType().equals(Environment.PRODUCTION)) {
            d();
            this.mockVersion.setVisibility(0);
            TextView textView = this.mockVersion;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.mockVersion.getText());
            sb2.append(UtilsCuentas.SEPARAOR2);
            sb2.append(this.x);
            textView.setText(sb2.toString());
            return;
        }
        e();
    }

    private void d() {
        SelectorEnvironmentFragment selectorEnvironmentFragment = new SelectorEnvironmentFragment();
        selectorEnvironmentFragment.setListener(new ISelectorEnvironmentFragment() {
            public void onCloseDialog() {
                SplashScreenActivity.this.e();
            }
        });
        if (!selectorEnvironmentFragment.isVisible()) {
            selectorEnvironmentFragment.show(getSupportFragmentManager(), "");
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        Crashlytics.setString("environment", String.valueOf(ManagerTypeEnvironment.getCurrentEnvironment(this).getEnvironmentType()));
        g();
        f();
    }

    private void f() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") == 0) {
            ((IDataManager) this.r.get()).checkVersion(Constants.SPLASH_ID_RUNTIME, this.x, this.D.isRootedWithoutBusyBoxCheck() ? "888" : "", this, true);
            ((IDataManager) this.r.get()).consDescripciones(true, false);
            AnonymousClass2 r3 = new CountDownTimer(2000, 125) {
                public void onTick(long j) {
                    if (!SplashScreenActivity.this.A.getResult().equals("0")) {
                        cancel();
                    }
                }

                public void onFinish() {
                    SplashScreenActivity.this.i();
                }
            };
            this.z = r3.start();
            return;
        }
        i();
    }

    private void g() {
        Bundle extras = getIntent().getExtras();
        this.u.cleanAllSessionData();
        if (extras != null && extras.containsKey("SESSION_PENDING") && extras.containsKey("NUP_PENDING")) {
            String string = extras.getString("SESSION_PENDING");
            String string2 = extras.getString("NUP_PENDING");
            this.u.setSession(string);
            this.u.setNup(string2);
        }
    }

    @Subscribe
    public void onCheckVersion(final CheckVersionEvent checkVersionEvent) {
        this.z.cancel();
        this.B = checkVersionEvent.getResult();
        this.C = true;
        final String result = this.A.getResult(checkVersionEvent);
        if (this.A.isUpdateAvailable()) {
            this.A.check(checkVersionEvent, new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onSimpleActionButton() {
                }

                public void onPositiveButton() {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(SplashScreenActivity.this.A.getUpdateBean(checkVersionEvent).getUrl()));
                    intent.setFlags(268435456);
                    SplashScreenActivity.this.getApplicationContext().startActivity(intent);
                    SplashScreenActivity.this.finish();
                }

                public void onNegativeButton() {
                    if (result.equals("3")) {
                        SplashScreenActivity.this.finish();
                    } else {
                        SplashScreenActivity.this.i();
                    }
                }
            });
        } else if (checkVersionEvent.getResult() != TypeResult.SERVER_ERROR || !checkVersionEvent.isRetry()) {
            i();
        } else {
            ((IDataManager) this.r.get()).checkVersion(Constants.SPLASH_ID_RUNTIME, this.x, this.D.isRootedWithoutBusyBoxCheck() ? "888" : "", this, false);
        }
    }

    @Subscribe
    public void onConsDescripciones(ConsDescripcionesEvent consDescripcionesEvent) {
        if (consDescripcionesEvent.needRetry() && !SplashUtils.hasToken(getIntent())) {
            ((IDataManager) this.r.get()).consDescripciones(false, false);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        this.p.register(this);
        super.onResume();
        this.q.registerFacebook(getApplicationContext().getString(R.string.SPLASH_API_KEY_FACEBOOK));
        if (b()) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), Html.fromHtml(getResources().getString(R.string.MSG_USER000029_General_errorGenerico)).toString(), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
            newInstance.show(supportFragmentManager, "Dialog");
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                    SplashScreenActivity.this.c();
                }

                public void onSimpleActionButton() {
                    SplashScreenActivity.this.c();
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.p.unregister(this);
    }

    private void h() {
        Intent intent = new Intent(this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("eventResult", this.B);
        bundle.putBoolean("llegoARespuesta", this.C);
        intent.addFlags(603979776);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    /* access modifiers changed from: private */
    public void i() {
        this.mLogoCompany.startAnimation(SplashUtils.getEndAnimation(this, k()));
    }

    public void onAnimationStart(Animation animation) {
        this.mSplashLegalText.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_splash));
    }

    public void onAnimationEnd(Animation animation) {
        if (SplashUtils.hasDeepLink(getIntent())) {
            j();
        } else {
            h();
        }
    }

    private void j() {
        String hostDomain = SplashUtils.getHostDomain(getIntent());
        if (SplashUtils.hasToken(hostDomain)) {
            goToTokenOBP();
            return;
        }
        this.u.deepLinkSetUp(DeepLinkUtils.deepLinksManager(hostDomain));
        h();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void a(PushNotification pushNotification) {
        this.u.setPendingPushNotification(pushNotification);
    }

    private float k() {
        int[] iArr = {0, 0};
        int[] iArr2 = {0, 0};
        this.mLogoCompany.getLocationInWindow(iArr2);
        this.y.getCustomView().getLocationInWindow(iArr);
        return (float) (iArr[1] - iArr2[1]);
    }

    public void goToTokenOBP() {
        Uri data = getIntent().getData();
        Intent intent = new Intent(this, TokenOBPActivity.class);
        intent.putExtra("URI", data.toString());
        intent.addFlags(276922368);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
