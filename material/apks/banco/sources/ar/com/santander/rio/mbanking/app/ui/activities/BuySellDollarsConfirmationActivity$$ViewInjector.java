package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class BuySellDollarsConfirmationActivity$$ViewInjector {
    public static void inject(Finder finder, BuySellDollarsConfirmationActivity buySellDollarsConfirmationActivity, Object obj) {
        buySellDollarsConfirmationActivity.activityTitle = (TextView) finder.findRequiredView(obj, R.id.vTitle, "field 'activityTitle'");
        buySellDollarsConfirmationActivity.lbl_pesosToOperate = (TextView) finder.findRequiredView(obj, R.id.F21_01_LBL_PESOSTOOPERATE, "field 'lbl_pesosToOperate'");
        buySellDollarsConfirmationActivity.btn_continue = (Button) finder.findRequiredView(obj, R.id.F20_01_btn_confirm, "field 'btn_continue'");
        buySellDollarsConfirmationActivity.lbl_data_exchangeRate = (TextView) finder.findRequiredView(obj, R.id.F21_01_LBL_DATA_EXCHANGERATE, "field 'lbl_data_exchangeRate'");
        buySellDollarsConfirmationActivity.lbl_data_originAccount = (TextView) finder.findRequiredView(obj, R.id.F21_01_LBL_DATA_ORIGINACCOUNT, "field 'lbl_data_originAccount'");
        buySellDollarsConfirmationActivity.lbl_data_destinationAccount = (TextView) finder.findRequiredView(obj, R.id.F21_01_LBL_DATA_DESTINATIONACCOUNT, "field 'lbl_data_destinationAccount'");
        buySellDollarsConfirmationActivity.lbl_data_dollarsToOperate = (TextView) finder.findRequiredView(obj, R.id.F21_01_LBL_DATA_DOLLARSTOOPERATE, "field 'lbl_data_dollarsToOperate'");
        buySellDollarsConfirmationActivity.lbl_data_pesosToOperate = (TextView) finder.findRequiredView(obj, R.id.F21_01_LBL_DATA_PESOSTOOPERATE, "field 'lbl_data_pesosToOperate'");
        buySellDollarsConfirmationActivity.lbl_dollarsToOperate = (TextView) finder.findRequiredView(obj, R.id.F21_01_LBL_DOLLARSTOOPERATE, "field 'lbl_dollarsToOperate'");
        buySellDollarsConfirmationActivity.lbl_legals = (TextView) finder.findRequiredView(obj, R.id.F21_01_LBL_LEGALS, "field 'lbl_legals'");
    }

    public static void reset(BuySellDollarsConfirmationActivity buySellDollarsConfirmationActivity) {
        buySellDollarsConfirmationActivity.activityTitle = null;
        buySellDollarsConfirmationActivity.lbl_pesosToOperate = null;
        buySellDollarsConfirmationActivity.btn_continue = null;
        buySellDollarsConfirmationActivity.lbl_data_exchangeRate = null;
        buySellDollarsConfirmationActivity.lbl_data_originAccount = null;
        buySellDollarsConfirmationActivity.lbl_data_destinationAccount = null;
        buySellDollarsConfirmationActivity.lbl_data_dollarsToOperate = null;
        buySellDollarsConfirmationActivity.lbl_data_pesosToOperate = null;
        buySellDollarsConfirmationActivity.lbl_dollarsToOperate = null;
        buySellDollarsConfirmationActivity.lbl_legals = null;
    }
}
