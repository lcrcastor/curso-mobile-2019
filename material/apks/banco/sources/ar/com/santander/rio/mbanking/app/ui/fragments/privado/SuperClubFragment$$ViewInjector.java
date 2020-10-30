package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.webkit.WebView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class SuperClubFragment$$ViewInjector {
    public static void inject(Finder finder, SuperClubFragment superClubFragment, Object obj) {
        superClubFragment.wbv_catalogo = (WebView) finder.findRequiredView(obj, R.id.wbv_catalogo, "field 'wbv_catalogo'");
    }

    public static void reset(SuperClubFragment superClubFragment) {
        superClubFragment.wbv_catalogo = null;
    }
}
