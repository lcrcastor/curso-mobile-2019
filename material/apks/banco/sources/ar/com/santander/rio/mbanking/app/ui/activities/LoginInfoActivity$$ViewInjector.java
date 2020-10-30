package ar.com.santander.rio.mbanking.app.ui.activities;

import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class LoginInfoActivity$$ViewInjector {
    public static void inject(Finder finder, final LoginInfoActivity loginInfoActivity, Object obj) {
        View findRequiredView = finder.findRequiredView(obj, R.id.buttonGenerar, "field 'buttonGenerar' and method 'generarUsuario'");
        loginInfoActivity.buttonGenerar = (Button) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                loginInfoActivity.b();
            }
        });
        View findRequiredView2 = finder.findRequiredView(obj, R.id.buttonYaTengo, "field 'buttonYaTengo' and method 'yaTengoUsuario'");
        loginInfoActivity.buttonYaTengo = (Button) findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                loginInfoActivity.c();
            }
        });
        loginInfoActivity.detailWB = (WebView) finder.findRequiredView(obj, R.id.detail, "field 'detailWB'");
    }

    public static void reset(LoginInfoActivity loginInfoActivity) {
        loginInfoActivity.buttonGenerar = null;
        loginInfoActivity.buttonYaTengo = null;
        loginInfoActivity.detailWB = null;
    }
}
