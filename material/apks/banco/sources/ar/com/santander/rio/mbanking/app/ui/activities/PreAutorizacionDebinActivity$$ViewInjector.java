package ar.com.santander.rio.mbanking.app.ui.activities;

import android.support.v4.widget.DrawerLayout;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class PreAutorizacionDebinActivity$$ViewInjector {
    public static void inject(Finder finder, PreAutorizacionDebinActivity preAutorizacionDebinActivity, Object obj) {
        preAutorizacionDebinActivity.drawerLayout = (DrawerLayout) finder.findRequiredView(obj, R.id.drawer_layout, "field 'drawerLayout'");
    }

    public static void reset(PreAutorizacionDebinActivity preAutorizacionDebinActivity) {
        preAutorizacionDebinActivity.drawerLayout = null;
    }
}
