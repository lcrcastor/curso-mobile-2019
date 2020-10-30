package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.utils.Utils;
import butterknife.ButterKnife;
import butterknife.InjectView;
import javax.inject.Inject;

public class SegmentoFragment extends BaseFragment {
    @Inject
    SessionManager a;
    @InjectView(2131366425)
    WebView wbv_url_Segmento;

    public static SegmentoFragment getInstance() {
        return new SegmentoFragment();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_segmento, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        String urlSegmento = this.a.getLoginUnico().getDatosPersonales().getUrlSegmento();
        this.wbv_url_Segmento.getSettings().setJavaScriptEnabled(true);
        this.wbv_url_Segmento.setWebViewClient(new WebViewClient());
        this.wbv_url_Segmento.loadUrl(Utils.formatIsbanHTMLCode(urlSegmento));
        return inflate;
    }
}
