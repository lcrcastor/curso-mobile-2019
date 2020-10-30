package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class TasasDetailsActivity$$ViewInjector {
    public static void inject(Finder finder, TasasDetailsActivity tasasDetailsActivity, Object obj) {
        tasasDetailsActivity.tasasTNA = (TextView) finder.findRequiredView(obj, R.id.tasas_tna, "field 'tasasTNA'");
        tasasDetailsActivity.tasas_TEA = (TextView) finder.findRequiredView(obj, R.id.tasas_tea, "field 'tasas_TEA'");
        tasasDetailsActivity.tasasCFTNAsIVA = (TextView) finder.findRequiredView(obj, R.id.tasas_cftna_sIva, "field 'tasasCFTNAsIVA'");
        tasasDetailsActivity.tasasCFTNAcIVA = (TextView) finder.findRequiredView(obj, R.id.tasas_cftna_cIva, "field 'tasasCFTNAcIVA'");
    }

    public static void reset(TasasDetailsActivity tasasDetailsActivity) {
        tasasDetailsActivity.tasasTNA = null;
        tasasDetailsActivity.tasas_TEA = null;
        tasasDetailsActivity.tasasCFTNAsIVA = null;
        tasasDetailsActivity.tasasCFTNAcIVA = null;
    }
}
