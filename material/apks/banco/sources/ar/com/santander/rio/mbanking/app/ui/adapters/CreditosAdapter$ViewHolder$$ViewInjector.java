package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class CreditosAdapter$ViewHolder$$ViewInjector {
    public static void inject(Finder finder, ViewHolder viewHolder, Object obj) {
        viewHolder.tipoTasa = (TextView) finder.findRequiredView(obj, R.id.tipoTasa, "field 'tipoTasa'");
        viewHolder.tasa = (TextView) finder.findRequiredView(obj, R.id.tasa, "field 'tasa'");
        viewHolder.cuotas = (TextView) finder.findRequiredView(obj, R.id.cuotas, "field 'cuotas'");
    }

    public static void reset(ViewHolder viewHolder) {
        viewHolder.tipoTasa = null;
        viewHolder.tasa = null;
        viewHolder.cuotas = null;
    }
}
