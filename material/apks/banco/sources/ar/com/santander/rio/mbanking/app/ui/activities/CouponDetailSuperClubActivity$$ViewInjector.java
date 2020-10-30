package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class CouponDetailSuperClubActivity$$ViewInjector {
    public static void inject(Finder finder, CouponDetailSuperClubActivity couponDetailSuperClubActivity, Object obj) {
        couponDetailSuperClubActivity.txtPoints = (TextView) finder.findRequiredView(obj, R.id.F20_02_LBL_DATA_POINTS, "field 'txtPoints'");
        couponDetailSuperClubActivity.imgCouponGroup = (ImageView) finder.findRequiredView(obj, R.id.F20_02_IMG_COUPON_GROUP, "field 'imgCouponGroup'");
        couponDetailSuperClubActivity.txtCouponGroup = (TextView) finder.findRequiredView(obj, R.id.F20_02_LBL_COUPON_GROUP, "field 'txtCouponGroup'");
        couponDetailSuperClubActivity.imgCoupon = (ImageView) finder.findRequiredView(obj, R.id.F20_02_IMG_COUPON, "field 'imgCoupon'");
        couponDetailSuperClubActivity.txtCoupon = (TextView) finder.findRequiredView(obj, R.id.F20_02_LBL_COUPON, "field 'txtCoupon'");
        couponDetailSuperClubActivity.layoutLocalesAdheridos = (LinearLayout) finder.findRequiredView(obj, R.id.F20_02_LNL_STORES, "field 'layoutLocalesAdheridos'");
        couponDetailSuperClubActivity.lblStores = (TextView) finder.findRequiredView(obj, R.id.F20_02_LBL_STORES, "field 'lblStores'");
        couponDetailSuperClubActivity.imgStores = (ImageView) finder.findRequiredView(obj, R.id.F20_02_IMG_STORES, "field 'imgStores'");
        couponDetailSuperClubActivity.txtLegal = (TextView) finder.findRequiredView(obj, R.id.F20_02_LBL_LEGAL, "field 'txtLegal'");
        couponDetailSuperClubActivity.btnChange = (Button) finder.findRequiredView(obj, R.id.F20_03_BTN_CHANGE, "field 'btnChange'");
    }

    public static void reset(CouponDetailSuperClubActivity couponDetailSuperClubActivity) {
        couponDetailSuperClubActivity.txtPoints = null;
        couponDetailSuperClubActivity.imgCouponGroup = null;
        couponDetailSuperClubActivity.txtCouponGroup = null;
        couponDetailSuperClubActivity.imgCoupon = null;
        couponDetailSuperClubActivity.txtCoupon = null;
        couponDetailSuperClubActivity.layoutLocalesAdheridos = null;
        couponDetailSuperClubActivity.lblStores = null;
        couponDetailSuperClubActivity.imgStores = null;
        couponDetailSuperClubActivity.txtLegal = null;
        couponDetailSuperClubActivity.btnChange = null;
    }
}
