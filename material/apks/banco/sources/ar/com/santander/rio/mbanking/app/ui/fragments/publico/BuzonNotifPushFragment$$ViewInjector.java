package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class BuzonNotifPushFragment$$ViewInjector {
    public static void inject(Finder finder, BuzonNotifPushFragment buzonNotifPushFragment, Object obj) {
        buzonNotifPushFragment.lstNotifcaciones = (RecyclerView) finder.findRequiredView(obj, R.id.F28_00_RCV_NOTIFICACIONES, "field 'lstNotifcaciones'");
        buzonNotifPushFragment.lnlSinNotificaciones = (LinearLayout) finder.findRequiredView(obj, R.id.F28_00_LNL_SIN_NOTIFICACIONES, "field 'lnlSinNotificaciones'");
        buzonNotifPushFragment.lblSinNotificaciones = (TextView) finder.findRequiredView(obj, R.id.F28_00_LBL_BUZON_VACIO, "field 'lblSinNotificaciones'");
    }

    public static void reset(BuzonNotifPushFragment buzonNotifPushFragment) {
        buzonNotifPushFragment.lstNotifcaciones = null;
        buzonNotifPushFragment.lnlSinNotificaciones = null;
        buzonNotifPushFragment.lblSinNotificaciones = null;
    }
}
