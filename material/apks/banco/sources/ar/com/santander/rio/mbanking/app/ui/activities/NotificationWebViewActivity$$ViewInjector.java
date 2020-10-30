package ar.com.santander.rio.mbanking.app.ui.activities;

import android.webkit.WebView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class NotificationWebViewActivity$$ViewInjector {
    public static void inject(Finder finder, NotificationWebViewActivity notificationWebViewActivity, Object obj) {
        notificationWebViewActivity.webView = (WebView) finder.findRequiredView(obj, R.id.webViewNotification, "field 'webView'");
    }

    public static void reset(NotificationWebViewActivity notificationWebViewActivity) {
        notificationWebViewActivity.webView = null;
    }
}
