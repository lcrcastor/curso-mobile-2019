package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.DateView;
import butterknife.ButterKnife.Finder;

public class PaymentServicesAdapter$ViewHolder$$ViewInjector {
    public static void inject(Finder finder, ViewHolder viewHolder, Object obj) {
        viewHolder.tvDate = (DateView) finder.findRequiredView(obj, R.id.vDateTransaction, "field 'tvDate'");
        viewHolder.vCompDesc = (TextView) finder.findRequiredView(obj, R.id.vCompDesc, "field 'vCompDesc'");
        viewHolder.vCompId = (TextView) finder.findRequiredView(obj, R.id.vCompId, "field 'vCompId'");
        viewHolder.tvAmount = (AmountView) finder.findRequiredView(obj, R.id.vAmountTransaction, "field 'tvAmount'");
        viewHolder.wrapperArrow = finder.findRequiredView(obj, R.id.wrapperArrow, "field 'wrapperArrow'");
    }

    public static void reset(ViewHolder viewHolder) {
        viewHolder.tvDate = null;
        viewHolder.vCompDesc = null;
        viewHolder.vCompId = null;
        viewHolder.tvAmount = null;
        viewHolder.wrapperArrow = null;
    }
}
