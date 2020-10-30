package ar.com.santander.rio.mbanking.app.ui.activities.custodiaterminos;

import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class CustodiaTerminosActivity$$ViewInjector {
    public static void inject(Finder finder, CustodiaTerminosActivity custodiaTerminosActivity, Object obj) {
        custodiaTerminosActivity.tvCustodiaTerminosCondiciones = (TextView) finder.findRequiredView(obj, R.id.tv_custodia_terminos_condiciones, "field 'tvCustodiaTerminosCondiciones'");
        custodiaTerminosActivity.tvTitle = (TextView) finder.findRequiredView(obj, R.id.view_title, "field 'tvTitle'");
    }

    public static void reset(CustodiaTerminosActivity custodiaTerminosActivity) {
        custodiaTerminosActivity.tvCustodiaTerminosCondiciones = null;
        custodiaTerminosActivity.tvTitle = null;
    }
}
