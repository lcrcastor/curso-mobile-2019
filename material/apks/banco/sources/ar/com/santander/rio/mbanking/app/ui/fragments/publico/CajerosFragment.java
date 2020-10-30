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

public class CajerosFragment extends BaseFragment {
    @Inject
    IDataManager a;
    /* access modifiers changed from: private */
    public CajerosFragment ad;
    /* access modifiers changed from: private */
    public SantanderRioMainActivity ae;
    @Inject
    AnalyticsManager b;
    @Inject
    SettingsManager c;
    private LocationManager d;
    private LocationListener e;
    /* access modifiers changed from: private */
    public String f;
    /* access modifiers changed from: private */
    public WebView g;
    /* access modifiers changed from: private */
    public RelativeLayout h;
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
                if ((VERSION.SDK_INT < 21 || webResourceRequest.getUrl().toString().equals("https://santander.com.ar/banco/contenthandler/!ut/p/digest!BMpM8gh5LPWrILmlbdr1bg/dav/fs-type1/themes/SRP9Theme/images/favicon/favicon-228.png")) && CajerosFragment.this.ad.isVisible() && CajerosFragment.this.h.getVisibility() != 0) {
                    CajerosFragment.this.C();
                    CajerosFragment.this.D();
                }
            }
        }
    }

    public static CajerosFragment getInstance() {
        return new CajerosFragment();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.ad = this;
        this.ae = (SantanderRioMainActivity) getActivity();
        this.i = new GeoWebChromeClient(this.ae);
        this.d = (LocationManager) this.ae.getSystemService("location");
        this.e = new LocationListener() {
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
        if (ContextCompat.checkSelfPermission(this.ae, "android.permission.ACCESS_FINE_LOCATION") == 0 && this.d != null) {
            if (this.d.isProviderEnabled("network") || this.d.isProviderEnabled("gps")) {
                this.d.requestLocationUpdates("gps", (long) Constants.MIN_TIME_REQUEST_LOCATION, (float) Constants.MIN_METERS_REQUEST_LOCATION, this.e);
                this.g.loadUrl(this.f);
            } else {
                IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("ErrorWebViewCajeros", getString(R.string.ID3715_COMPRA_VENTA_LBL_AVISO), getString(R.string.msg_dialog_request_location), getString(R.string.ID62_LOGIN_SUBSCRIPTION_BTN_ACCEPT), getString(R.string.ID2037_ENVIARDINERO_BTN_CANCELAR));
                newInstance.setDialogListener(new IDialogListener() {
                    public void onItemSelected(String str) {
                    }

                    public void onPositiveButton() {
                        CajerosFragment.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 12333);
                    }

                    public void onNegativeButton() {
                        CajerosFragment.this.g.loadUrl(CajerosFragment.this.f);
                    }

                    public void onSimpleActionButton() {
                        ((SantanderRioMainActivity) CajerosFragment.this.getActivity()).finish();
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
        View inflate = layoutInflater.inflate(R.layout.fragment_map_cajeros, viewGroup, false);
        this.b.trackScreen(getString(R.string.analytics_screen_name_cajeros_home));
        this.g = (WebView) inflate.findViewById(R.id.webViewCajeros);
        this.f = this.c.loadStringObject(SettingsManager.CAJEROS_URL_HOME);
        this.h = (RelativeLayout) inflate.findViewById(R.id.imageErrorConnection);
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
        this.g.setWebViewClient(new MyBrowser());
        WebSettings settings = this.g.getSettings();
        StringBuilder sb = new StringBuilder();
        sb.append(this.g.getSettings().getUserAgentString());
        sb.append("-MB");
        settings.setUserAgentString(sb.toString());
        this.g.getSettings().setDomStorageEnabled(true);
        this.g.getSettings().setSupportMultipleWindows(true);
        this.g.getSettings().setLoadsImagesAutomatically(true);
        this.g.setScrollBarStyle(0);
        this.g.getSettings().setJavaScriptEnabled(true);
        this.g.getSettings().setGeolocationEnabled(true);
        this.g.getSettings().setAppCacheEnabled(true);
        this.g.getSettings().setDatabaseEnabled(true);
        this.g.getSettings().setGeolocationDatabasePath(getActivity().getFilesDir().getPath());
        this.g.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.g.setWebChromeClient(this.i);
    }

    /* access modifiers changed from: private */
    public void C() {
        this.g.setVisibility(8);
        this.h.setVisibility(0);
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
        if (this.d != null) {
            this.d.removeUpdates(this.e);
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
                ((SantanderRioMainActivity) CajerosFragment.this.getActivity()).finish();
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
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(CajerosFragment.this.ae.getCheckVersionManager().getUpdateBean(checkVersionEvent).getUrl()));
                    intent.setFlags(268435456);
                    CajerosFragment.this.ae.getApplicationContext().startActivity(intent);
                    CajerosFragment.this.ae.setResult(-1);
                    CajerosFragment.this.ae.finish();
                }

                public void onNegativeButton() {
                    if (result.equals("3")) {
                        CajerosFragment.this.ae.setResult(-1);
                        CajerosFragment.this.ae.finish();
                    } else if (result.equals("2")) {
                        CajerosFragment.this.ae.getSessionManager().setCheckVersionWarningRaised(Boolean.valueOf(true));
                    }
                }
            });
            return;
        }
        switch (checkVersionEvent.getResult()) {
            case OK:
                this.f = ((CheckVersionResponseBean) checkVersionEvent.getBeanResponse()).getCheckVersionBodyResponseBean().getWebViewHome().getUrlCajeros();
                y();
                break;
            case SERVER_ERROR:
                if (!this.f.isEmpty()) {
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
