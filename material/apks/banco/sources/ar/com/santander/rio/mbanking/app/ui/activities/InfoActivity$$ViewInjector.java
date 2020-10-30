package ar.com.santander.rio.mbanking.app.ui.activities;

import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class InfoActivity$$ViewInjector {
    public static void inject(Finder finder, InfoActivity infoActivity, Object obj) {
        infoActivity.titleWebView = (TextView) finder.findRequiredView(obj, R.id.titleWebView, "field 'titleWebView'");
        infoActivity.titleRow = (LinearLayout) finder.findRequiredView(obj, R.id.titleRow, "field 'titleRow'");
        infoActivity.webView = (WebView) finder.findRequiredView(obj, R.id.webViewRecuperaClave, "field 'webView'");
        infoActivity.htmlTV = (TextView) finder.findRequiredView(obj, R.id.htmlContent, "field 'htmlTV'");
        infoActivity.aceptarButton = (Button) finder.findRequiredView(obj, R.id.aceptarButton, "field 'aceptarButton'");
        infoActivity.noAceptarButton = (Button) finder.findRequiredView(obj, R.id.noAceptarButton, "field 'noAceptarButton'");
        infoActivity.scrollView = (ScrollView) finder.findRequiredView(obj, R.id.scrollViewPage, "field 'scrollView'");
        infoActivity.htmlSubTitle = (TextView) finder.findRequiredView(obj, R.id.htmlSubTitle, "field 'htmlSubTitle'");
        infoActivity.viewSeparatorSectionTitle = finder.findRequiredView(obj, R.id.SeparatorSectionTitle, "field 'viewSeparatorSectionTitle'");
    }

    public static void reset(InfoActivity infoActivity) {
        infoActivity.titleWebView = null;
        infoActivity.titleRow = null;
        infoActivity.webView = null;
        infoActivity.htmlTV = null;
        infoActivity.aceptarButton = null;
        infoActivity.noAceptarButton = null;
        infoActivity.scrollView = null;
        infoActivity.htmlSubTitle = null;
        infoActivity.viewSeparatorSectionTitle = null;
    }
}
