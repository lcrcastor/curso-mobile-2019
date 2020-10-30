package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.adapters.IntervinientesAdapter.ViewHolder;
import butterknife.ButterKnife.Finder;

public class IntervinientesAdapter$ViewHolder$$ViewInjector {
    public static void inject(Finder finder, ViewHolder viewHolder, Object obj) {
        viewHolder.tvText = (TextView) finder.findRequiredView(obj, R.id.tvLabel, "field 'tvText'");
    }

    public static void reset(ViewHolder viewHolder) {
        viewHolder.tvText = null;
    }
}
