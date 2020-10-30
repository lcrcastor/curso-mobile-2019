package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList;
import ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility;
import butterknife.ButterKnife.Finder;

public class BuySellDollarsFragment$$ViewInjector {
    public static void inject(Finder finder, BuySellDollarsFragment buySellDollarsFragment, Object obj) {
        buySellDollarsFragment.tabSelector = (HorizontalScrollList) finder.findRequiredView(obj, R.id.tabSelector, "field 'tabSelector'");
        buySellDollarsFragment.activityTitle = (TextView) finder.findRequiredView(obj, R.id.vTitle, "field 'activityTitle'");
        buySellDollarsFragment.buySellDollars_home = (RelativeLayout) finder.findRequiredView(obj, R.id.F20_00_RLL_HOME, "field 'buySellDollars_home'");
        buySellDollarsFragment.buySellDollars_rll_selectedCurrency = (RelativeLayout) finder.findRequiredView(obj, R.id.F20_00_RLL_SELECTED_CURRENCY, "field 'buySellDollars_rll_selectedCurrency'");
        buySellDollarsFragment.buySellDollars_selectedCurrency = (TextView) finder.findRequiredView(obj, R.id.F20_00_LBL_SELECTED_CURRENCY, "field 'buySellDollars_selectedCurrency'");
        buySellDollarsFragment.buySellDollars_selectedCurrencyChevron = (ImageView) finder.findRequiredView(obj, R.id.F20_00_IMG_SELECTED_CURRENCY_CHEVRON, "field 'buySellDollars_selectedCurrencyChevron'");
        buySellDollarsFragment.buySellDollars_exchangeRate = (TextView) finder.findRequiredView(obj, R.id.F20_00_TXV_EXCHANGE_RATE, "field 'buySellDollars_exchangeRate'");
        buySellDollarsFragment.inputAmount = (NumericEditTextWithPrefixAccesibility) finder.findRequiredView(obj, R.id.F20_00_INP_AMOUNT, "field 'inputAmount'");
        buySellDollarsFragment.buySellDollars_destinationAccount = (TextView) finder.findRequiredView(obj, R.id.F20_00_LBL_DATA_DESTINATION_ACCOUNT, "field 'buySellDollars_destinationAccount'");
        buySellDollarsFragment.buySellDollars_originAccount = (TextView) finder.findRequiredView(obj, R.id.F20_00_LBL_DATA_ORIGIN_ACCOUNT, "field 'buySellDollars_originAccount'");
        buySellDollarsFragment.buySellDollars_destinationAmount = (TextView) finder.findRequiredView(obj, R.id.F20_00_LBL_DATA_DESTINATION_AMOUNT, "field 'buySellDollars_destinationAmount'");
        buySellDollarsFragment.buySellDollars_originAmount = (TextView) finder.findRequiredView(obj, R.id.F20_00_LBL_DATA_ORIGIN_AMOUNT, "field 'buySellDollars_originAmount'");
        buySellDollarsFragment.btnContinue = (Button) finder.findRequiredView(obj, R.id.F20_00_BTN_CONTINUE, "field 'btnContinue'");
    }

    public static void reset(BuySellDollarsFragment buySellDollarsFragment) {
        buySellDollarsFragment.tabSelector = null;
        buySellDollarsFragment.activityTitle = null;
        buySellDollarsFragment.buySellDollars_home = null;
        buySellDollarsFragment.buySellDollars_rll_selectedCurrency = null;
        buySellDollarsFragment.buySellDollars_selectedCurrency = null;
        buySellDollarsFragment.buySellDollars_selectedCurrencyChevron = null;
        buySellDollarsFragment.buySellDollars_exchangeRate = null;
        buySellDollarsFragment.inputAmount = null;
        buySellDollarsFragment.buySellDollars_destinationAccount = null;
        buySellDollarsFragment.buySellDollars_originAccount = null;
        buySellDollarsFragment.buySellDollars_destinationAmount = null;
        buySellDollarsFragment.buySellDollars_originAmount = null;
        buySellDollarsFragment.btnContinue = null;
    }
}
