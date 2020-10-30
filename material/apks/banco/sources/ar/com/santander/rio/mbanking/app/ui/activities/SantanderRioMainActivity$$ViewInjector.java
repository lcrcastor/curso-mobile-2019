package ar.com.santander.rio.mbanking.app.ui.activities;

import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class SantanderRioMainActivity$$ViewInjector {
    public static void inject(Finder finder, SantanderRioMainActivity santanderRioMainActivity, Object obj) {
        santanderRioMainActivity.mDrawerLayout = (DrawerLayout) finder.findRequiredView(obj, R.id.drawer_layout, "field 'mDrawerLayout'");
        santanderRioMainActivity.menuLateralList = (ListView) finder.findRequiredView(obj, R.id.ID16_PRIVATEMENU_BTN_CREDITS, "field 'menuLateralList'");
    }

    public static void reset(SantanderRioMainActivity santanderRioMainActivity) {
        santanderRioMainActivity.mDrawerLayout = null;
        santanderRioMainActivity.menuLateralList = null;
    }
}
