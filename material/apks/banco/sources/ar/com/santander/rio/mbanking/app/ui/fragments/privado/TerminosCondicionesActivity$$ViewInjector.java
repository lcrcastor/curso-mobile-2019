package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class TerminosCondicionesActivity$$ViewInjector {
    public static void inject(Finder finder, TerminosCondicionesActivity terminosCondicionesActivity, Object obj) {
        terminosCondicionesActivity.tvTitleView = (TextView) finder.findRequiredView(obj, R.id.functionality_title, "field 'tvTitleView'");
        terminosCondicionesActivity.tvTituloTermino = (TextView) finder.findRequiredView(obj, R.id.tv_title_leyenda, "field 'tvTituloTermino'");
        terminosCondicionesActivity.tvDescripcionTermino = (TextView) finder.findRequiredView(obj, R.id.tv_desc_leyenda, "field 'tvDescripcionTermino'");
    }

    public static void reset(TerminosCondicionesActivity terminosCondicionesActivity) {
        terminosCondicionesActivity.tvTitleView = null;
        terminosCondicionesActivity.tvTituloTermino = null;
        terminosCondicionesActivity.tvDescripcionTermino = null;
    }
}
