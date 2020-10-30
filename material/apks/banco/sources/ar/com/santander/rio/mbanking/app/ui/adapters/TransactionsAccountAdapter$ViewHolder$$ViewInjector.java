package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.DateView;
import butterknife.ButterKnife.Finder;

public class TransactionsAccountAdapter$ViewHolder$$ViewInjector {
    public static void inject(Finder finder, ViewHolder viewHolder, Object obj) {
        viewHolder.tvDate = (DateView) finder.findRequiredView(obj, R.id.vDateTransaction, "field 'tvDate'");
        viewHolder.tvConcept = (TextView) finder.findRequiredView(obj, R.id.vConceptTransaction, "field 'tvConcept'");
        viewHolder.tvAmount = (AmountView) finder.findRequiredView(obj, R.id.vAmountTransaction, "field 'tvAmount'");
    }

    public static void reset(ViewHolder viewHolder) {
        viewHolder.tvDate = null;
        viewHolder.tvConcept = null;
        viewHolder.tvAmount = null;
    }
}
