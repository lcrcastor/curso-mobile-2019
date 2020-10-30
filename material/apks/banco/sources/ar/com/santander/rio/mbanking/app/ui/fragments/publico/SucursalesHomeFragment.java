package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.services.events.CheckVersionEvent;
import ar.com.santander.rio.mbanking.services.soap.beans.CheckVersionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VCheckVersion;
import ar.com.santander.rio.mbanking.utils.GeoWebChromeClient;
import com.scottyab.rootbeer.RootBeer;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class SucursalesHomeFragment extends BaseFragment {
    @Inject
    IDataManager a;
    /* access modifiers changed from: private */
    public SucursalesHomeFragment ad;
    /* access modifiers changed from: private */
    public SantanderRioMainActivity ae;
    @Inject
    AnalyticsManager b;
    @Inject
    SettingsManager c;
    /* access modifiers changed from: private */
    public String d;
    /* access modifiers changed from: private */
    public WebView e;
    /* access modifiers changed from: private */
    public RelativeLayout f;
    private LocationManager g;
    private LocationListener h;
    private GeoWebChromeClient i;

    class MyBrowser extends WebViewClient {
        private MyBrowser() {
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            return true;
        }

        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            if (VERSION.SDK_INT < 23 || webResourceError.getErrorCode() != -9) {
                if ((VERSION.SDK_INT < 21 || webResourceRequest.getUrl().toString().equals("https://santander.com.ar/banco/contenthandler/!ut/p/digest!BMpM8gh5LPWrILmlbdr1bg/dav/fs-type1/themes/SRP9Theme/images/favicon/favicon-228.png")) && SucursalesHomeFragment.this.ad.isVisible() && SucursalesHomeFragment.this.f.getVisibility() != 0) {
                    SucursalesHomeFragment.this.C();
                    SucursalesHomeFragment.this.D();
                }
            }
        }
    }

    public static SucursalesHomeFragment getInstance() {
        return new SucursalesHomeFragment();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.ad = this;
        this.ae = (SantanderRioMainActivity) getActivity();
        this.i = new GeoWebChromeClient(this.ae);
        this.g = (LocationManager) this.ae.getSystemService("location");
        this.h = new LocationListener() {
            public void onLocationChanged(Location location) {
            }

            public void onProviderDisabled(String str) {
            }

            public void onProviderEnabled(String str) {
            }

            public void onStatusChanged(String str, int i, Bundle bundle) {
            }
        };
    }

    private void y() {
        if (VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(this.ae, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            z();
            return;
        }
        requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, Constants.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
    }

    private void z() {
        if (ContextCompat.checkSelfPermission(this.ae, "android.permission.ACCESS_FINE_LOCATION") == 0 && this.g != null) {
            if (this.g.isProviderEnabled("network") || this.g.isProviderEnabled("gps")) {
                this.g.requestLocationUpdates("gps", (long) Constants.MIN_TIME_REQUEST_LOCATION, (float) Constants.MIN_METERS_REQUEST_LOCATION, this.h);
                this.e.loadUrl(this.d);
            } else {
                IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("ErrorWebViewCajeros", getString(R.string.ID3715_COMPRA_VENTA_LBL_AVISO), getString(R.string.msg_dialog_request_location), getString(R.string.ID62_LOGIN_SUBSCRIPTION_BTN_ACCEPT), getString(R.string.ID2037_ENVIARDINERO_BTN_CANCELAR));
                newInstance.setDialogListener(new IDialogListener() {
                    public void onItemSelected(String str) {
                    }

                    public void onPositiveButton() {
                        SucursalesHomeFragment.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 12333);
                    }

                    public void onNegativeButton() {
                        SucursalesHomeFragment.this.e.loadUrl(SucursalesHomeFragment.this.d);
                    }

                    public void onSimpleActionButton() {
                        ((SantanderRioMainActivity) SucursalesHomeFragment.this.getActivity()).finish();
                    }
                });
                newInstance.show(getFragmentManager(), "ErrorWebViewCajeros");
            }
        }
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        if (i2 == 12333) {
            y();
        } else {
            super.onActivityResult(i2, i3, intent);
        }
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.sucursales_home_fragment, viewGroup, false);
        this.b.trackScreen(getString(R.string.analytics_screen_name_sucursales_home));
        this.e = (WebView) inflate.findViewById(R.id.webViewSucursalesHome);
        this.d = this.c.loadStringObject(SettingsManager.SUCURSALES_URL_HOME);
        this.f = (RelativeLayout) inflate.findViewById(R.id.imageErrorConnection);
        B();
        A();
        return inflate;
    }

    private void A() {
        String str;
        String str2 = "";
        try {
            str = this.ae.getPackageManager().getPackageInfo(this.ae.getPackageName(), 0).versionName;
        } catch (Exception e2) {
            e2.fillInStackTrace();
            str = str2;
        }
        String str3 = !new RootBeer(this.ae).isRootedWithoutBusyBoxCheck() ? "888" : "";
        if (!this.ae.sessionManager.getCheckVersion()) {
            this.ae.showProgress("");
            this.a.cancelRequest(VCheckVersion.nameService);
            this.a.checkVersion(Constants.SPLASH_ID_RUNTIME, str, str3, this.ae, false);
            return;
        }
        y();
    }

    private void B() {
        this.e.setWebViewClient(new MyBrowser());
        WebSettings settings = this.e.getSettings();
        StringBuilder sb = new StringBuilder();
        sb.append(this.e.getSettings().getUserAgentString());
        sb.append("-MB");
        settings.setUserAgentString(sb.toString());
        this.e.getSettings().setDomStorageEnabled(true);
        this.e.getSettings().setSupportMultipleWindows(true);
        this.e.getSettings().setLoadsImagesAutomatically(true);
        this.e.setScrollBarStyle(0);
        this.e.getSettings().setJavaScriptEnabled(true);
        this.e.getSettings().setGeolocationEnabled(true);
        this.e.getSettings().setAppCacheEnabled(true);
        this.e.getSettings().setDatabaseEnabled(true);
        this.e.getSettings().setGeolocationDatabasePath(getActivity().getFilesDir().getPath());
        this.e.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.e.setWebChromeClient(this.i);
    }

    /* access modifiers changed from: private */
    public void C() {
        this.e.setVisibility(8);
        this.f.setVisibility(0);
    }

    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i2 != 1452) {
            return;
        }
        if (iArr.length > 0 && iArr[0] == 0) {
            z();
            if (this.i.getGeoLocationCallback() != null) {
                this.i.getGeoLocationCallback().invoke(this.i.getRequestOrigin(), true, true);
            }
        } else if (this.i.getGeoLocationCallback() != null) {
            this.i.getGeoLocationCallback().invoke(this.i.getRequestOrigin(), false, false);
        }
    }

    public void onStop() {
        if (this.g != null) {
            this.g.removeUpdates(this.h);
        }
        super.onStop();
    }

    /* access modifiers changed from: private */
    public void D() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("ErrorWebViewCajeros", PagoTarjetasConstants.ISBAN_DIALOG_WARNING_TITLE, getString(R.string.MSG_USER000002_General_errorNoconexion), PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                ((SantanderRioMainActivity) SucursalesHomeFragment.this.getActivity()).finish();
            }
        });
        newInstance.show(getFragmentManager(), "ErrorWebViewCajeros");
    }

    @Subscribe
    public void onCheckVersion(final CheckVersionEvent checkVersionEvent) {
        final String result = this.ae.getCheckVersionManager().getResult(checkVersionEvent);
        this.ae.dismissProgress();
        if (this.ae.getCheckVersionManager().isUpdateAvailable()) {
            this.ae.getCheckVersionManager().check(checkVersionEvent, new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onSimpleActionButton() {
                }

                public void onPositiveButton() {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(SucursalesHomeFragment.this.ae.getCheckVersionManager().getUpdateBean(checkVersionEvent).getUrl()));
                    intent.setFlags(268435456);
                    SucursalesHomeFragment.this.ae.getApplicationContext().startActivity(intent);
                    SucursalesHomeFragment.this.ae.setResult(-1);
                    SucursalesHomeFragment.this.ae.finish();
                }

                public void onNegativeButton() {
                    if (result.equals("3")) {
                        SucursalesHomeFragment.this.ae.setResult(-1);
                        SucursalesHomeFragment.this.ae.finish();
                    } else if (result.equals("2")) {
                        SucursalesHomeFragment.this.ae.getSessionManager().setCheckVersionWarningRaised(Boolean.valueOf(true));
                    }
                }
            });
            return;
        }
        switch (checkVersionEvent.getResult()) {
            case OK:
                this.d = ((CheckVersionResponseBean) checkVersionEvent.getBeanResponse()).getCheckVersionBodyResponseBean().getWebViewHome().getUrlCajeros();
                y();
                break;
            case SERVER_ERROR:
                if (!this.d.isEmpty()) {
                    y();
                    break;
                } else {
                    C();
                    D();
                    break;
                }
            case BEAN_ERROR_RES_1:
                this.ae.getCheckVersionManager().check(checkVersionEvent, new IDialogListener() {
                    public void onItemSelected(String str) {
                    }

                    public void onNegativeButton() {
                    }

                    public void onPositiveButton() {
                    }

                    public void onSimpleActionButton() {
                    }
                });
                break;
        }
    }
}
