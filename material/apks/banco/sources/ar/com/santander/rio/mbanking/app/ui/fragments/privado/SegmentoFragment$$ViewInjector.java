package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.webkit.WebView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class SegmentoFragment$$ViewInjector {
    public static void inject(Finder finder, SegmentoFragment segmentoFragment, Object obj) {
        segmentoFragment.wbv_url_Segmento = (WebView) finder.findRequiredView(obj, R.id.wbv_urlSegmento, "field 'wbv_url_Segmento'");
    }

    public static void reset(SegmentoFragment segmentoFragment) {
        segmentoFragment.wbv_url_Segmento = null;
    }
}
