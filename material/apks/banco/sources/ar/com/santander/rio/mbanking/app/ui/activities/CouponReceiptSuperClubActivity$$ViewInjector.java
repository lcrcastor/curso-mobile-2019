package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class CouponReceiptSuperClubActivity$$ViewInjector {
    public static void inject(Finder finder, CouponReceiptSuperClubActivity couponReceiptSuperClubActivity, Object obj) {
        couponReceiptSuperClubActivity.scrReceipt = (ScrollView) finder.findRequiredView(obj, R.id.F20_03_SCR_RECEIPT, "field 'scrReceipt'");
        couponReceiptSuperClubActivity.txtDescription = (TextView) finder.findRequiredView(obj, R.id.F20_03_LBL_DESCRIPTION, "field 'txtDescription'");
        couponReceiptSuperClubActivity.txtChangeDate = (TextView) finder.findRequiredView(obj, R.id.F20_03_LBL_DATA_CHANGE_DATE, "field 'txtChangeDate'");
        couponReceiptSuperClubActivity.txtPoints = (TextView) finder.findRequiredView(obj, R.id.F20_03_LBL_DATA_POINTS, "field 'txtPoints'");
        couponReceiptSuperClubActivity.txtReceiptNumber = (TextView) finder.findRequiredView(obj, R.id.F20_03_LBL_DATA_RECEIPT_NUMBER, "field 'txtReceiptNumber'");
        couponReceiptSuperClubActivity.btnStores = (TextView) finder.findRequiredView(obj, R.id.F20_03_LBL_STORES, "field 'btnStores'");
        couponReceiptSuperClubActivity.txtLegal = (TextView) finder.findRequiredView(obj, R.id.F20_03_LBL_LEGAL, "field 'txtLegal'");
        couponReceiptSuperClubActivity.btnBack = (Button) finder.findRequiredView(obj, R.id.F20_03_BTN_BACK, "field 'btnBack'");
    }

    public static void reset(CouponReceiptSuperClubActivity couponReceiptSuperClubActivity) {
        couponReceiptSuperClubActivity.scrReceipt = null;
        couponReceiptSuperClubActivity.txtDescription = null;
        couponReceiptSuperClubActivity.txtChangeDate = null;
        couponReceiptSuperClubActivity.txtPoints = null;
        couponReceiptSuperClubActivity.txtReceiptNumber = null;
        couponReceiptSuperClubActivity.btnStores = null;
        couponReceiptSuperClubActivity.txtLegal = null;
        couponReceiptSuperClubActivity.btnBack = null;
    }
}
