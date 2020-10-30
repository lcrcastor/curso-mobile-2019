package ar.com.santander.rio.mbanking.app.ui.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.module.recargaSube.ObtenerTarjetasRecargaSubePresenter;
import ar.com.santander.rio.mbanking.app.module.recargaSube.ObtenerTarjetasRecargaSubeView;
import ar.com.santander.rio.mbanking.app.module.recargaSube.SharedPreferencesData;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.errorrecarga.ErrorRecargaActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.CircularCarruselPagerAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.mapInfo.MyMarker;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.analytics.RechargeSubeAnalytics.Screen;
import ar.com.santander.rio.mbanking.managers.checkversion.CheckVersionManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.notifications.PushNotificationsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.CheckVersionEvent;
import ar.com.santander.rio.mbanking.services.events.ConsDescripcionesEvent;
import ar.com.santander.rio.mbanking.services.events.GetPromocionPushEvent;
import ar.com.santander.rio.mbanking.services.events.GetPromocionesHomeEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.model.general.DatosPersonales;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPromocionesPushResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPromocionesHomeBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PromocionSucursalBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VCheckVersion;
import ar.com.santander.rio.mbanking.utils.DeepLinkUtils;
import ar.com.santander.rio.mbanking.utils.UtilDeviceInfo;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import ar.com.santander.rio.mbanking.view.CirclePagerIndicator;
import ar.com.santander.rio.mbanking.view.CirclePagerIndicator.CirclePagerListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.scottyab.rootbeer.RootBeer;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import com.twincoders.twinpush.sdk.services.NotificationIntentService;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import javax.inject.Inject;
import me.leolin.shortcutbadger.ShortcutBadger;

public class HomeActivity extends BaseActivity implements OnPageChangeListener, OnClickListener, ObtenerTarjetasRecargaSubeView, CirclePagerListener {
    public static final String ACCESS_PHONE_STATUS_DIALOG_TAG = "ACCESS_PHONE_STATUS_DIALOG";
    public static String ANDROID_PREF = "A";
    public static final int CLOSE_ACTIVITY = 19440;
    public static final int CLOSE_ACTIVITY_WITH_RECARGA = 19441;
    public static String LOG_TAG = "ar.com.santander.rio.mbanking.app.ui.activities.HomeActivity";
    public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    /* access modifiers changed from: private */
    public CheckVersionManager A;
    private ImageView B;
    private boolean C = false;
    /* access modifiers changed from: private */
    public Handler D = new Handler();
    private final Runnable E = new Runnable() {
        public void run() {
            try {
                HomeActivity.this.e();
                HomeActivity.this.D.postDelayed(this, LocalizacionManager.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private RootBeer F;
    private boolean G = false;
    @InjectView(2131364231)
    Button cajerosButton;
    @InjectView(2131364259)
    ConstraintLayout carrusel;
    @InjectView(2131364283)
    CirclePagerIndicator circlePagerIndicator;
    @InjectView(2131364796)
    ImageView imageViewCellPhone;
    @InjectView(2131364733)
    RelativeLayout mLayoutHomeBody;
    @InjectView(2131364728)
    ConstraintLayout mLayoutHomeTop;
    @InjectView(2131364894)
    LinearLayout mLayoutHomeTopDisabled;
    @InjectView(2131365283)
    LinearLayout mLayoutOptions;
    @InjectView(2131365404)
    ConstraintLayout mLayoutPromotions;
    @InjectView(2131365465)
    ConstraintLayout mLayoutRecargaSube;
    @InjectView(2131365233)
    ImageButton mobileBankingButton;
    @InjectView(2131364087)
    TextView mobileBankingText;
    @InjectView(2131364232)
    Button numerosUtilesButton;
    @Inject
    Bus p;
    @InjectView(2131365399)
    ProgressBar progressBar;
    @InjectView(2131365403)
    ImageButton promocionButton;
    @InjectView(2131365405)
    ImageButton promocionesButton;
    @InjectView(2131364260)
    ViewPager promocionesViewPager;
    @Inject
    SessionManager q;
    @Inject
    AnalyticsManager r;
    @Inject
    IDataManager s;
    @InjectView(2131364233)
    Button softTokenButton;
    @InjectView(2131365466)
    ImageButton subeButton;
    @InjectView(2131364234)
    Button sucursalesButton;
    @Inject
    SettingsManager t;
    CircularCarruselPagerAdapter u;
    private boolean v = false;
    private SharedPreferencesData w;
    private ObtenerTarjetasRecargaSubePresenter x;
    /* access modifiers changed from: private */
    public LocalizacionBean y = new LocalizacionBean(String.valueOf(-34.606737d), String.valueOf(-58.373381d));
    private Timer z;

    public void configureActionBar() {
    }

    public void configureLayout() {
    }

    public void initialize() {
    }

    public void onPageScrollStateChanged(int i) {
    }

    public void onPageScrolled(int i, float f, int i2) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.home_activity);
        ButterKnife.inject((Activity) this);
        this.F = new RootBeer(this);
        j();
        this.r.trackScreen(getString(R.string.analytics_screen_name_login_home));
        this.w = new SharedPreferencesData(getApplicationContext());
        this.x = new ObtenerTarjetasRecargaSubePresenter(this.p, this.s, this.t, this);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
            supportActionBar.setDisplayOptions(16);
            supportActionBar.setHomeButtonEnabled(false);
        }
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService("layout_inflater");
        View view = null;
        if (layoutInflater != null) {
            view = layoutInflater.inflate(R.layout.actionbar_notif, null);
        }
        this.B = (ImageView) ButterKnife.findById(view, (int) R.id.F00_00_IMG_NOTIF);
        if (this.B != null) {
            this.B.setOnClickListener(this);
        }
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        if (supportActionBar != null) {
            supportActionBar.setCustomView(view, layoutParams);
        }
        this.numerosUtilesButton.setOnClickListener(this);
        this.cajerosButton.setOnClickListener(this);
        this.promocionesButton.setOnClickListener(this);
        this.promocionButton.setOnClickListener(this);
        this.sucursalesButton.setOnClickListener(this);
        this.softTokenButton.setOnClickListener(this);
        this.subeButton.setOnClickListener(this);
        this.carrusel.setVisibility(8);
        if (this.A == null) {
            this.A = new CheckVersionManager(this.q, this);
        }
        i();
        b();
        if (!TextUtils.isEmpty(this.q.getCustomURLNavFragment()) && this.q.getCustomURLNavFragment().equalsIgnoreCase(FragmentConstants.COMODINES_SUPERCLUB)) {
            showProgress(VCheckVersion.nameService);
        }
        if (!TextUtils.isEmpty(this.q.getCustomURLNavFragment())) {
            this.C = true;
        }
    }

    /* access modifiers changed from: protected */
    public void onPostResume() {
        super.onPostResume();
        m();
    }

    public void onPostCreate(@Nullable Bundle bundle) {
        super.onPostCreate(bundle);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getBoolean("llegoARespuesta", false) && extras.getSerializable("eventResult") != TypeResult.OK) {
                k();
            }
            CheckVersionEvent checkVersionEvent = (CheckVersionEvent) extras.getParcelable(SettingsManager.CHECK_VERSION);
            if (checkVersionEvent != null) {
                this.q.setCheckVersionEvent(checkVersionEvent);
            }
        }
    }

    private void b() {
        this.q.getGetPromocionesHome();
    }

    /* access modifiers changed from: private */
    public void c() {
        try {
            final CheckVersionEvent checkVersionEvent = this.q.getCheckVersionEvent();
            if (checkVersionEvent != null) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        HomeActivity.this.p.post(checkVersionEvent);
                    }
                });
            } else {
                i();
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void onWindowFocusChanged(boolean z2) {
        super.onWindowFocusChanged(z2);
    }

    private void d() {
        if (this.mLayoutHomeTop != null && this.mLayoutPromotions != null && this.mLayoutHomeBody != null) {
            Animation loadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.no_animation);
            loadAnimation.setAnimationListener(new AnimationListener() {
                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                    Animation loadAnimation = AnimationUtils.loadAnimation(HomeActivity.this.getApplicationContext(), R.anim.slide_down_home);
                    HomeActivity.this.mLayoutHomeTop.startAnimation(loadAnimation);
                    if (HomeActivity.this.mLayoutOptions != null) {
                        HomeActivity.this.mLayoutOptions.startAnimation(AnimationUtils.loadAnimation(HomeActivity.this.getApplicationContext(), R.anim.slide_up_home));
                    }
                    HomeActivity.this.mLayoutPromotions.startAnimation(loadAnimation);
                    if (HomeActivity.this.mLayoutRecargaSube != null && HomeActivity.this.mLayoutRecargaSube.getVisibility() == 0) {
                        HomeActivity.this.mLayoutRecargaSube.startAnimation(loadAnimation);
                    }
                }

                public void onAnimationEnd(Animation animation) {
                    HomeActivity.this.f();
                }
            });
            this.mLayoutHomeBody.startAnimation(loadAnimation);
        }
    }

    @Subscribe
    public void onGetPromocionesHome(GetPromocionesHomeEvent getPromocionesHomeEvent) {
        Log.i(LOG_TAG, "onGetPromocionesHome");
        if (getPromocionesHomeEvent.getResult() != TypeResult.OK) {
            this.carrusel.setVisibility(8);
        }
    }

    @Subscribe
    public void onConsDescripciones(ConsDescripcionesEvent consDescripcionesEvent) {
        if ((consDescripcionesEvent.getResult() == TypeResult.BEAN_ERROR_RES_1 || consDescripcionesEvent.getResult() == TypeResult.SERVER_ERROR) && consDescripcionesEvent.isRetry()) {
            this.s.consDescripciones(false, false);
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        try {
            TextView textView = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.F00_00_LBL_NOTIF);
            if (textView != null) {
                int i = getApplicationContext().getSharedPreferences(Constants.PUSH_PREFERENCES, 0).getInt(Constants.ID_NOTIFICACION_INDEX, 0);
                if (i > 0) {
                    textView.setVisibility(0);
                    textView.setText(Integer.toString(i));
                    ImageView imageView = this.B;
                    StringBuilder sb = new StringBuilder();
                    sb.append(getString(R.string.CONTENT_NOTIF));
                    sb.append(UtilsCuentas.SEPARAOR2);
                    sb.append(i);
                    sb.append(UtilsCuentas.SEPARAOR2);
                    sb.append(getString(R.string.CONTENT_SIN_LEER));
                    imageView.setContentDescription(sb.toString());
                    return;
                }
                textView.setVisibility(8);
                textView.setText("0");
                this.B.setContentDescription(getString(R.string.CONTENT_NOTIF));
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "updateNotificationBadgeCounter: ", e);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        this.p.register(this);
        b();
        if (!this.G) {
            d();
        }
        this.D.post(this.E);
        i();
        super.onResume();
    }

    /* access modifiers changed from: private */
    public void f() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") == 0 || this.v) {
            g();
            this.v = true;
            k();
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_PHONE_STATE"}, 0);
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i == 0) {
            this.v = true;
            g();
        }
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    private void g() {
        String str = ANDROID_PREF;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService("phone");
            if (telephonyManager != null) {
                String deviceId = telephonyManager.getDeviceId();
                if (deviceId != null) {
                    str = str.concat(deviceId);
                } else {
                    throw new Exception("getDeviceId == null");
                }
            }
        } catch (Exception unused) {
            str = UtilDeviceInfo.ANDROID_PREF_DEFAULT;
        }
        this.t.setKeyUniqueID(str);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (this.z != null) {
            this.z.cancel();
        }
        this.p.unregister(this);
        this.D.removeCallbacks(this.E);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.D.removeCallbacks(this.E);
    }

    public void onClick(View view) {
        if (this.v) {
            Intent intent = new Intent(this, SantanderRioMainActivity.class);
            intent.addFlags(603979776);
            Bundle bundle = new Bundle();
            int id2 = view.getId();
            if (id2 == R.id.F00_00_IMG_NOTIF) {
                bundle.putString("MENU_ACTION", FragmentConstants.NOTIF_PUSH);
                intent.putExtras(bundle);
                startActivity(intent);
            } else if (id2 == R.id.mobile_banking_button) {
                bundle.putString("MENU_ACTION", FragmentConstants.MOBILE_BANKING);
                intent.putExtras(bundle);
                startActivityForResult(intent, CLOSE_ACTIVITY);
            } else if (id2 != R.id.promocion_button) {
                if (id2 != R.id.promociones_button) {
                    if (id2 != R.id.recargaSube_button) {
                        switch (id2) {
                            case R.id.button_cajeros /*2131364231*/:
                            case R.id.button_sucursales /*2131364234*/:
                                break;
                            case R.id.button_numeros_utiles /*2131364232*/:
                                bundle.putString("MENU_ACTION", FragmentConstants.NUMEROS_UTILES);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                break;
                            case R.id.button_soft_token /*2131364233*/:
                                bundle.putString("MENU_ACTION", FragmentConstants.SOFT_TOKEN);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                break;
                        }
                    } else {
                        a(bundle, intent);
                    }
                }
                c(view.getId());
            } else {
                int currentItem = this.u.getCurrentItem();
                if (this.u.isCurrentItemOnRange(currentItem).booleanValue()) {
                    GetPromocionesHomeBodyResponseBean getPromocionesHome = this.q.getGetPromocionesHome();
                    if (getPromocionesHome != null) {
                        PromocionSucursalBean promocionSucursalBean = (PromocionSucursalBean) getPromocionesHome.getPromocionesBean().getPromocionSucursalBeanList().get(currentItem);
                        a(new MyMarker(promocionSucursalBean), promocionSucursalBean.tipo);
                    }
                }
            }
        }
    }

    private void a(Bundle bundle, Intent intent) {
        this.r.trackScreen(Screen.init());
        if (h()) {
            String session = TextUtils.isEmpty(this.q.getSession()) ? "" : this.q.getSession();
            DatosPersonales personalDate = this.q.getPersonalDate();
            this.x.attachView(this);
            this.x.getTarjetasSubeData("SUBE", personalDate.getNroDocumento(), personalDate.getFechaNacimiento(), session, personalDate.getNup());
            return;
        }
        a("", "", FragmentConstants.RECARGA_SUBE, false, false);
    }

    private boolean h() {
        return this.q.getPersonalDate() != null;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 19440 && i2 == -1) {
            finish();
        }
        if (i == 19441) {
            this.G = true;
        }
        if (i == 103) {
            if (i2 == -1 && intent != null && intent.getExtras() != null) {
                Intent intent2 = new Intent(this, SantanderRioMainActivity.class);
                intent2.addFlags(603979776);
                Bundle bundle = new Bundle();
                bundle.putString("MENU_ACTION", FragmentConstants.AGREGAR_SUBE);
                bundle.putString("SUBE_BUTTON", getString(R.string.registrar_tarjeta_sube));
                if (intent.hasExtra("SUBE_TITULO")) {
                    bundle.putString("SUBE_TITULO", intent.getStringExtra("SUBE_TITULO"));
                }
                if (intent.hasExtra("SUBE_DESCRIPCION")) {
                    bundle.putString("SUBE_DESCRIPCION", intent.getStringExtra("SUBE_DESCRIPCION"));
                }
                bundle.putBoolean(FragmentConstants.INTENT_DATA_SUBE_HAS_SUBE, true);
                bundle.putString(FragmentConstants.INTENT_DATA_RECARGA_SUBE, FragmentConstants.SUBE_FLOW_AGREGAR);
                intent2.putExtras(bundle);
                startActivity(intent2);
            }
        } else if (i == 904) {
            overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_down_recarga_sube);
        }
    }

    private void c(int i) {
        Intent intent = new Intent(this, SantanderRioMainActivity.class);
        intent.addFlags(603979776);
        Bundle bundle = new Bundle();
        if (i == R.id.button_cajeros) {
            bundle.putString("MENU_ACTION", FragmentConstants.CAJEROS);
        } else if (i == R.id.button_sucursales) {
            bundle.putString("MENU_ACTION", FragmentConstants.SUCURSALES);
        } else if (i == R.id.promociones_button) {
            bundle.putString("MENU_ACTION", FragmentConstants.PROMOS);
        }
        intent.putExtras(bundle);
        startActivityForResult(intent, CLOSE_ACTIVITY);
    }

    private void i() {
        l();
        this.promocionesButton.setOnClickListener(this);
        this.promocionButton.setOnClickListener(this);
        this.cajerosButton.setOnClickListener(this);
        this.sucursalesButton.setOnClickListener(this);
        this.softTokenButton.setOnClickListener(this);
    }

    public void onPageSelected(int i) {
        this.circlePagerIndicator.updateDotsState(i);
    }

    public void onDotSelected(int i) {
        this.u.setCurrentItem(i);
    }

    private void j() {
        PushNotification pendingPushNotification = this.q.getPendingPushNotification();
        if (pendingPushNotification != null) {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.PUSH_PREFERENCES, 0);
            Editor edit = sharedPreferences.edit();
            int i = sharedPreferences.getInt(Constants.ID_NOTIFICACION_INDEX, 0) - 1;
            if (i < 0) {
                i = 0;
            }
            edit.putInt(Constants.ID_NOTIFICACION_INDEX, i);
            edit.apply();
            ShortcutBadger.applyCount(getApplicationContext(), i);
            if (pendingPushNotification.getCustomProperties().containsKey(PushNotificationsManager.PROMO_TYPE)) {
                a((String) pendingPushNotification.getCustomProperties().get(PushNotificationsManager.PROMO_TYPE));
            } else if (pendingPushNotification.isRichNotification()) {
                Intent intent = new Intent(getApplicationContext(), NotificationWebViewActivity.class);
                if (pendingPushNotification.getCustomProperties().containsKey(PushNotificationsManager.SEGMENT_TYPE)) {
                    intent.putExtra(NotificationWebViewActivity.SEGMENT_PARAM, (String) pendingPushNotification.getCustomProperties().get(PushNotificationsManager.SEGMENT_TYPE));
                }
                intent.putExtra(NotificationWebViewActivity.URL_NOTIFICATION_PARAM, pendingPushNotification.getRichURL());
                intent.putExtra(NotificationWebViewActivity.ID_NOTIFICATION_PARAM, pendingPushNotification.getId());
                startActivity(intent);
            } else if (!(pendingPushNotification.getTitle() == null || pendingPushNotification.getMessage() == null)) {
                IsbanDialogFragment.newInstance(pendingPushNotification.getTitle(), Html.fromHtml(Utils.formatIsbanHTMLCode(pendingPushNotification.getMessage())).toString(), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null).show(getSupportFragmentManager(), "Dialog");
            }
            this.q.setPendingPushNotification(null);
        }
        ((NotificationManager) getSystemService(NotificationIntentService.EXTRA_NOTIFICATION)).cancelAll();
    }

    private void a(String str) {
        new LocalizacionManager(this) {
            public void onConnected(Bundle bundle) {
                HomeActivity.this.y.changeLocation(getCurrentLocation());
            }
        };
        this.s.getPromocionPush(this.y, str);
    }

    @Subscribe
    public void onGetPromocionesPush(GetPromocionPushEvent getPromocionPushEvent) {
        Log.i(LOG_TAG, "onGetPromocionesPush");
        if (getPromocionPushEvent.getResult() == TypeResult.OK) {
            GetPromocionesPushResponseBean getPromocionesPushResponseBean = (GetPromocionesPushResponseBean) getPromocionPushEvent.getBeanResponse();
            a(new MyMarker(getPromocionesPushResponseBean.getGetPromocionesPushBodyResponseBean().getPromocionBean()), getPromocionesPushResponseBean.getGetPromocionesPushBodyResponseBean().getPromocionBean().tipo);
        }
    }

    private void a(MyMarker myMarker, String str) {
        if (checkPlayServices()) {
            Intent intent = new Intent(this, PromocionDetalleActivity.class);
            intent.putExtra("marker", myMarker);
            intent.putExtra("tipo", str);
            startActivity(intent);
            return;
        }
        showMapsDialog();
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void k() {
        /*
            r7 = this;
            ar.com.santander.rio.mbanking.managers.checkversion.CheckVersionManager r0 = r7.A
            java.lang.String r0 = r0.getResult()
            int r1 = r0.hashCode()
            switch(r1) {
                case 48: goto L_0x0022;
                case 49: goto L_0x000d;
                case 50: goto L_0x0018;
                case 51: goto L_0x000e;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x002c
        L_0x000e:
            java.lang.String r1 = "3"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002c
            r0 = 2
            goto L_0x002d
        L_0x0018:
            java.lang.String r1 = "2"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002c
            r0 = 1
            goto L_0x002d
        L_0x0022:
            java.lang.String r1 = "0"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002c
            r0 = 0
            goto L_0x002d
        L_0x002c:
            r0 = -1
        L_0x002d:
            switch(r0) {
                case 0: goto L_0x0044;
                case 1: goto L_0x0034;
                case 2: goto L_0x0034;
                default: goto L_0x0030;
            }
        L_0x0030:
            r7.l()
            goto L_0x0059
        L_0x0034:
            ar.com.santander.rio.mbanking.managers.session.SessionManager r0 = r7.q
            java.lang.Boolean r0 = r0.getCheckVersionWarningRaised()
            boolean r0 = r0.booleanValue()
            if (r0 != 0) goto L_0x0059
            r7.c()
            goto L_0x0059
        L_0x0044:
            java.util.Timer r0 = new java.util.Timer
            r0.<init>()
            r7.z = r0
            java.util.Timer r1 = r7.z
            ar.com.santander.rio.mbanking.app.ui.activities.HomeActivity$5 r2 = new ar.com.santander.rio.mbanking.app.ui.activities.HomeActivity$5
            r2.<init>()
            r3 = 0
            r5 = 2000(0x7d0, double:9.88E-321)
            r1.scheduleAtFixedRate(r2, r3, r5)
        L_0x0059:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.HomeActivity.k():void");
    }

    private void l() {
        this.mLayoutHomeTopDisabled.setVisibility(8);
        this.progressBar.setVisibility(8);
        this.mobileBankingButton.setVisibility(0);
        this.mobileBankingButton.setEnabled(true);
        this.mobileBankingButton.setOnClickListener(this);
        this.imageViewCellPhone.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.generic_red), Mode.MULTIPLY);
        this.mobileBankingText.setTextColor(getResources().getColor(R.color.generic_red));
        dismissProgress();
    }

    private void m() {
        if (n()) {
            this.q.setDeeplinkFlown(false);
            o();
        }
    }

    private boolean n() {
        return this.q.isDeeplinkFlown() && DeepLinkUtils.validateDeepLink(this.q.getCustomURLNavFragment());
    }

    private void o() {
        Intent intent = new Intent(this, SantanderRioMainActivity.class);
        intent.addFlags(603979776);
        Bundle bundle = new Bundle();
        bundle.putString("MENU_ACTION", FragmentConstants.MOBILE_BANKING);
        intent.putExtras(bundle);
        startActivityForResult(intent, CLOSE_ACTIVITY);
    }

    public void showProgressIndicator(String str) {
        showProgress(str);
    }

    public void dismissProgressIndicator() {
        dismissProgress();
    }

    public void attachView() {
        if (!this.x.isViewAttached()) {
            this.x.attachView(this);
        }
    }

    public void detachView() {
        if (this.x.isViewAttached()) {
            this.x.detachView();
        }
        dismissProgress();
    }

    public void getTarjetas(List list, List list2, List list3, String str, String str2, String str3) {
        detachView();
        Intent intent = new Intent(this, RecargaSubeActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra(getString(R.string.RECARGA_SUBE_FLOW), getString(R.string.RECARGA_SUBE));
        bundle.putParcelableArrayList(getString(R.string.LISTA_CUENTAS), (ArrayList) list);
        bundle.putParcelableArrayList(getString(R.string.LISTA_RECARGAS), (ArrayList) list2);
        bundle.putParcelableArrayList(getString(R.string.LISTA_VALORES), (ArrayList) list3);
        bundle.putString("SUBE_TITULO", str2);
        bundle.putString("SUBE_DESCRIPCION", str3);
        bundle.putString(getString(R.string.SESSION_USER), str);
        intent.putExtras(bundle);
        intent.addFlags(603979776);
        startActivityForResult(intent, 103);
    }

    public void goToRegistrarSube(String str, String str2) {
        a(str, str2, FragmentConstants.AGREGAR_SUBE_SIN_TARJETA, true, false);
    }

    public void callErrorScreen(String str, String str2) {
        Intent intent = new Intent(this, ErrorRecargaActivity.class);
        intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_TITLE, str);
        intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, str2);
        startActivityForResult(intent, WSErrorHandlerConstants.INTENT_RES4_REQUEST_CODE);
        overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_down_recarga_sube);
    }

    private void a(String str, String str2, String str3, boolean z2, boolean z3) {
        detachView();
        Intent intent = new Intent(this, SantanderRioMainActivity.class);
        intent.addFlags(603979776);
        Bundle bundle = new Bundle();
        bundle.putString("MENU_ACTION", FragmentConstants.RECARGA_SUBE_WELCOME);
        bundle.putString(FragmentConstants.INTENT_DATA_RECARGA_SUBE, str3);
        bundle.putString("SUBE_TITULO", str);
        bundle.putString("SUBE_DESCRIPCION", str2);
        bundle.putBoolean(FragmentConstants.INTENT_DATA_SUBE_IS_LOGGED, z2);
        bundle.putBoolean(FragmentConstants.INTENT_DATA_SUBE_HAS_SUBE, z3);
        intent.putExtras(bundle);
        startActivityForResult(intent, CLOSE_ACTIVITY_WITH_RECARGA);
    }

    public void onBackPressed() {
        this.q.cleanAllSessionData();
        if (this.C) {
            ExitActivity.exitApplication(this);
        } else {
            super.onBackPressed();
        }
    }
}
