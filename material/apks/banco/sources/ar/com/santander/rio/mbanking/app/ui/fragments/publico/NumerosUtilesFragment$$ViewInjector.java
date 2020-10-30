package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class NumerosUtilesFragment$$ViewInjector {
    public static void inject(Finder finder, NumerosUtilesFragment numerosUtilesFragment, Object obj) {
        numerosUtilesFragment.txtViewNumber = (TextView) finder.findRequiredView(obj, R.id.textViewNumUtilesNumber, "field 'txtViewNumber'");
    }

    public static void reset(NumerosUtilesFragment numerosUtilesFragment) {
        numerosUtilesFragment.txtViewNumber = null;
    }
}
