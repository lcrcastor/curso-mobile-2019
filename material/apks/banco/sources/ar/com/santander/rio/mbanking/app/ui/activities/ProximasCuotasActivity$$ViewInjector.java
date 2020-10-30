package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.ListView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class ProximasCuotasActivity$$ViewInjector {
    public static void inject(Finder finder, ProximasCuotasActivity proximasCuotasActivity, Object obj) {
        proximasCuotasActivity.cuotasListView = (ListView) finder.findRequiredView(obj, R.id.proximas_cuotas_list_view, "field 'cuotasListView'");
    }

    public static void reset(ProximasCuotasActivity proximasCuotasActivity) {
        proximasCuotasActivity.cuotasListView = null;
    }
}
