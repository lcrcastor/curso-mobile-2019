package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class SubcategoryCouponDetailSuperClubActivity$$ViewInjector {
    public static void inject(Finder finder, SubcategoryCouponDetailSuperClubActivity subcategoryCouponDetailSuperClubActivity, Object obj) {
        CouponDetailSuperClubActivity$$ViewInjector.inject(finder, subcategoryCouponDetailSuperClubActivity, obj);
        subcategoryCouponDetailSuperClubActivity.txtLocalesAdheridos = (TextView) finder.findRequiredView(obj, R.id.F20_02_LBL_STORES, "field 'txtLocalesAdheridos'");
        subcategoryCouponDetailSuperClubActivity.imgLocalesAdheridos = (ImageView) finder.findRequiredView(obj, R.id.F20_02_IMG_STORES, "field 'imgLocalesAdheridos'");
    }

    public static void reset(SubcategoryCouponDetailSuperClubActivity subcategoryCouponDetailSuperClubActivity) {
        CouponDetailSuperClubActivity$$ViewInjector.reset(subcategoryCouponDetailSuperClubActivity);
        subcategoryCouponDetailSuperClubActivity.txtLocalesAdheridos = null;
        subcategoryCouponDetailSuperClubActivity.imgLocalesAdheridos = null;
    }
}
