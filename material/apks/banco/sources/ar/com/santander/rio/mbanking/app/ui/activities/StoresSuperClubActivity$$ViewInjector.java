package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.ScrollView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class StoresSuperClubActivity$$ViewInjector {
    public static void inject(Finder finder, StoresSuperClubActivity storesSuperClubActivity, Object obj) {
        storesSuperClubActivity.scrStores = (ScrollView) finder.findRequiredView(obj, R.id.F20_04_SCR_STORES, "field 'scrStores'");
        storesSuperClubActivity.txtBrand = (TextView) finder.findRequiredView(obj, R.id.F20_04_LBL_TITLE, "field 'txtBrand'");
        storesSuperClubActivity.txtStores = (TextView) finder.findRequiredView(obj, R.id.F20_04_LBL_STORES, "field 'txtStores'");
    }

    public static void reset(StoresSuperClubActivity storesSuperClubActivity) {
        storesSuperClubActivity.scrStores = null;
        storesSuperClubActivity.txtBrand = null;
        storesSuperClubActivity.txtStores = null;
    }
}
