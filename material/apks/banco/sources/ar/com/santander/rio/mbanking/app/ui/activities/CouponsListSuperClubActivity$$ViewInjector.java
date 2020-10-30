package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class CouponsListSuperClubActivity$$ViewInjector {
    public static void inject(Finder finder, CouponsListSuperClubActivity couponsListSuperClubActivity, Object obj) {
        couponsListSuperClubActivity.txtPoints = (TextView) finder.findRequiredView(obj, R.id.F20_01_LBL_DATA_POINTS, "field 'txtPoints'");
        couponsListSuperClubActivity.imgCouponGroup = (ImageView) finder.findRequiredView(obj, R.id.F20_02_IMG_COUPON_GROUP, "field 'imgCouponGroup'");
        couponsListSuperClubActivity.txtCouponGroup = (TextView) finder.findRequiredView(obj, R.id.F20_02_LBL_COUPON_GROUP, "field 'txtCouponGroup'");
        couponsListSuperClubActivity.lst_table_view = (LinearLayout) finder.findRequiredView(obj, R.id.F20_01_LST_TABLE_VIEW, "field 'lst_table_view'");
    }

    public static void reset(CouponsListSuperClubActivity couponsListSuperClubActivity) {
        couponsListSuperClubActivity.txtPoints = null;
        couponsListSuperClubActivity.imgCouponGroup = null;
        couponsListSuperClubActivity.txtCouponGroup = null;
        couponsListSuperClubActivity.lst_table_view = null;
    }
}
