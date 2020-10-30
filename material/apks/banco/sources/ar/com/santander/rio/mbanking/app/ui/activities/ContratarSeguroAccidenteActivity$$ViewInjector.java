package ar.com.santander.rio.mbanking.app.ui.activities;

import android.support.v4.widget.DrawerLayout;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class ContratarSeguroAccidenteActivity$$ViewInjector {
    public static void inject(Finder finder, ContratarSeguroAccidenteActivity contratarSeguroAccidenteActivity, Object obj) {
        contratarSeguroAccidenteActivity.mDrawerLayout = (DrawerLayout) finder.findRequiredView(obj, R.id.drawer_layout, "field 'mDrawerLayout'");
    }

    public static void reset(ContratarSeguroAccidenteActivity contratarSeguroAccidenteActivity) {
        contratarSeguroAccidenteActivity.mDrawerLayout = null;
    }
}
