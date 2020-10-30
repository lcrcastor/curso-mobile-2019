package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class RegistrarSubeFragment$$ViewInjector {
    public static void inject(Finder finder, RegistrarSubeFragment registrarSubeFragment, Object obj) {
        registrarSubeFragment.btnRegistrarSube = (Button) finder.findRequiredView(obj, R.id.sube_next_button, "field 'btnRegistrarSube'");
        registrarSubeFragment.tvheader = (TextView) finder.findRequiredView(obj, R.id.tv_header, "field 'tvheader'");
        registrarSubeFragment.tvTitle = (TextView) finder.findRequiredView(obj, R.id.tv_title, "field 'tvTitle'");
        registrarSubeFragment.tvLegend = (TextView) finder.findRequiredView(obj, R.id.tv_legend, "field 'tvLegend'");
    }

    public static void reset(RegistrarSubeFragment registrarSubeFragment) {
        registrarSubeFragment.btnRegistrarSube = null;
        registrarSubeFragment.tvheader = null;
        registrarSubeFragment.tvTitle = null;
        registrarSubeFragment.tvLegend = null;
    }
}
