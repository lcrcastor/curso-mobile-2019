package ar.com.santander.rio.mbanking.app.ui.activities;

import android.view.View;
import android.widget.ImageView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class FiltrosActivity$$ViewInjector {
    public static void inject(Finder finder, final FiltrosActivity filtrosActivity, Object obj) {
        View findRequiredView = finder.findRequiredView(obj, R.id.ok, "field 'okMenu' and method 'onAceptar'");
        filtrosActivity.okMenu = (ImageView) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                filtrosActivity.onAceptar(view);
            }
        });
        finder.findRequiredView(obj, R.id.toggle, "method 'onBackPressed'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                filtrosActivity.onBackPressed();
            }
        });
    }

    public static void reset(FiltrosActivity filtrosActivity) {
        filtrosActivity.okMenu = null;
    }
}
