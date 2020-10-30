package ar.com.santander.rio.mbanking.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;

public class GeoWebChromeClient extends WebChromeClient {
    private String a;
    private Callback b;
    private FragmentActivity c;

    public GeoWebChromeClient(SantanderRioMainActivity santanderRioMainActivity) {
        this.c = santanderRioMainActivity;
    }

    public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
        Message obtainMessage = webView.getHandler().obtainMessage();
        webView.requestFocusNodeHref(obtainMessage);
        String string = obtainMessage.getData().getString("url");
        if (string != null) {
            Uri parse = Uri.parse(string);
            if (!string.startsWith("http://") && !string.startsWith("https://")) {
                StringBuilder sb = new StringBuilder();
                sb.append("http://");
                sb.append(string);
                parse = Uri.parse(sb.toString());
            }
            Intent intent = new Intent("android.intent.action.VIEW", parse);
            if (intent.resolveActivity(this.c.getPackageManager()) != null) {
                this.c.startActivity(intent);
            }
        }
        return true;
    }

    public void onGeolocationPermissionsShowPrompt(String str, Callback callback) {
        if (VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(this.c, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            callback.invoke(str, true, true);
            return;
        }
        this.a = str;
        this.b = callback;
    }

    public String getRequestOrigin() {
        return this.a;
    }

    public void setRequestOrigin(String str) {
        this.a = str;
    }

    public Callback getGeoLocationCallback() {
        return this.b;
    }

    public void setGeoLocationCallback(Callback callback) {
        this.b = callback;
    }

    public FragmentActivity getContext() {
        return this.c;
    }

    public void setmContext(SantanderRioMainActivity santanderRioMainActivity) {
        this.c = santanderRioMainActivity;
    }
}
