package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class PerfilDelInversorActivity$$ViewInjector {
    public static void inject(Finder finder, PerfilDelInversorActivity perfilDelInversorActivity, Object obj) {
        perfilDelInversorActivity.tvTitleView = (TextView) finder.findRequiredView(obj, R.id.functionality_title, "field 'tvTitleView'");
        perfilDelInversorActivity.nombrePerfil = (TextView) finder.findRequiredView(obj, R.id.tvnombrePerfil, "field 'nombrePerfil'");
        perfilDelInversorActivity.descripcionPerfil = (TextView) finder.findRequiredView(obj, R.id.tvDescripcionPerfil, "field 'descripcionPerfil'");
        perfilDelInversorActivity.tvmasinfo = (TextView) finder.findRequiredView(obj, R.id.tvmasinfo, "field 'tvmasinfo'");
    }

    public static void reset(PerfilDelInversorActivity perfilDelInversorActivity) {
        perfilDelInversorActivity.tvTitleView = null;
        perfilDelInversorActivity.nombrePerfil = null;
        perfilDelInversorActivity.descripcionPerfil = null;
        perfilDelInversorActivity.tvmasinfo = null;
    }
}
