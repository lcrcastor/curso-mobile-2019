package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.AmountView;
import butterknife.ButterKnife.Finder;

public class DetallesMovimientoFragment$$ViewInjector {
    public static void inject(Finder finder, DetallesMovimientoFragment detallesMovimientoFragment, Object obj) {
        detallesMovimientoFragment.tableDetailsTransactions = (ViewGroup) finder.findRequiredView(obj, R.id.tblDetailsTransactions, "field 'tableDetailsTransactions'");
        detallesMovimientoFragment.vAmount = (AmountView) finder.findRequiredView(obj, R.id.vAmount, "field 'vAmount'");
        detallesMovimientoFragment.vDescription = (TextView) finder.findRequiredView(obj, R.id.vDescription, "field 'vDescription'");
        detallesMovimientoFragment.vDateDetailsTransaction = (TextView) finder.findRequiredView(obj, R.id.vDateDetailsTransaction, "field 'vDateDetailsTransaction'");
        detallesMovimientoFragment.vDataAccount = (TextView) finder.findRequiredView(obj, R.id.vDataAccount, "field 'vDataAccount'");
    }

    public static void reset(DetallesMovimientoFragment detallesMovimientoFragment) {
        detallesMovimientoFragment.tableDetailsTransactions = null;
        detallesMovimientoFragment.vAmount = null;
        detallesMovimientoFragment.vDescription = null;
        detallesMovimientoFragment.vDateDetailsTransaction = null;
        detallesMovimientoFragment.vDataAccount = null;
    }
}
