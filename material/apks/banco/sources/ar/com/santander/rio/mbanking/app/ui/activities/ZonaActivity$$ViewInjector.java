package ar.com.santander.rio.mbanking.app.ui.activities;

import android.view.View;
import android.widget.ImageView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class ZonaActivity$$ViewInjector {
    public static void inject(Finder finder, final ZonaActivity zonaActivity, Object obj) {
        View findRequiredView = finder.findRequiredView(obj, R.id.toggle, "field 'okMenu' and method 'onClose'");
        zonaActivity.okMenu = (ImageView) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                zonaActivity.onClose(view);
            }
        });
        finder.findRequiredView(obj, R.id.ok, "method 'onAceptar'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                zonaActivity.onAceptar(view);
            }
        });
    }

    public static void reset(ZonaActivity zonaActivity) {
        zonaActivity.okMenu = null;
    }
}
