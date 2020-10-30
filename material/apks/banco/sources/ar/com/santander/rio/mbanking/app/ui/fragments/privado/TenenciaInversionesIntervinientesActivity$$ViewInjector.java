package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class TenenciaInversionesIntervinientesActivity$$ViewInjector {
    public static void inject(Finder finder, TenenciaInversionesIntervinientesActivity tenenciaInversionesIntervinientesActivity, Object obj) {
        tenenciaInversionesIntervinientesActivity.intervinientesRv = (RecyclerView) finder.findRequiredView(obj, R.id.rvPerfilInversor, "field 'intervinientesRv'");
        tenenciaInversionesIntervinientesActivity.tvTitle = (TextView) finder.findRequiredView(obj, R.id.tv_functionality_title, "field 'tvTitle'");
        tenenciaInversionesIntervinientesActivity.tvSubtitle = (TextView) finder.findRequiredView(obj, R.id.tv_subtitle, "field 'tvSubtitle'");
        tenenciaInversionesIntervinientesActivity.tvAccount = (TextView) finder.findRequiredView(obj, R.id.tv_account, "field 'tvAccount'");
    }

    public static void reset(TenenciaInversionesIntervinientesActivity tenenciaInversionesIntervinientesActivity) {
        tenenciaInversionesIntervinientesActivity.intervinientesRv = null;
        tenenciaInversionesIntervinientesActivity.tvTitle = null;
        tenenciaInversionesIntervinientesActivity.tvSubtitle = null;
        tenenciaInversionesIntervinientesActivity.tvAccount = null;
    }
}
