package ar.com.santander.rio.mbanking.app.ui.activities;

import android.webkit.WebView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class FirmaCredinActivity$$ViewInjector {
    public static void inject(Finder finder, FirmaCredinActivity firmaCredinActivity, Object obj) {
        firmaCredinActivity.webView = (WebView) finder.findRequiredView(obj, R.id.webViewFirmaCredin, "field 'webView'");
    }

    public static void reset(FirmaCredinActivity firmaCredinActivity) {
        firmaCredinActivity.webView = null;
    }
}
