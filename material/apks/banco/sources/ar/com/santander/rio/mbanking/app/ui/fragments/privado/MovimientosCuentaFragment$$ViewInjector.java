package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollListView;
import ar.com.santander.rio.mbanking.view.CustomSpinner;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class MovimientosCuentaFragment$$ViewInjector {
    public static void inject(Finder finder, final MovimientosCuentaFragment movimientosCuentaFragment, Object obj) {
        movimientosCuentaFragment.mListTransactions = (InfiniteScrollListView) finder.findRequiredView(obj, R.id.gvList, "field 'mListTransactions'");
        movimientosCuentaFragment.vgWrapperMessageTransactions = (ViewGroup) finder.findRequiredView(obj, R.id.wrapperMessageTransactions, "field 'vgWrapperMessageTransactions'");
        movimientosCuentaFragment.vMessageTransactions = (TextView) finder.findRequiredView(obj, R.id.vMessageTransactions, "field 'vMessageTransactions'");
        movimientosCuentaFragment.vMessageTransactions7Days = (TextView) finder.findRequiredView(obj, R.id.vMessageTransactions7Days, "field 'vMessageTransactions7Days'");
        View findRequiredView = finder.findRequiredView(obj, R.id.vgCustomSpinner, "field 'vgCustomerSpinner' and method 'onClickSelectorFilters'");
        movimientosCuentaFragment.vgCustomerSpinner = (CustomSpinner) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                movimientosCuentaFragment.onClickSelectorFilters(view);
            }
        });
        movimientosCuentaFragment.vLabelFilterSelected = (TextView) finder.findRequiredView(obj, R.id.vLabelFilterSelected, "field 'vLabelFilterSelected'");
    }

    public static void reset(MovimientosCuentaFragment movimientosCuentaFragment) {
        movimientosCuentaFragment.mListTransactions = null;
        movimientosCuentaFragment.vgWrapperMessageTransactions = null;
        movimientosCuentaFragment.vMessageTransactions = null;
        movimientosCuentaFragment.vMessageTransactions7Days = null;
        movimientosCuentaFragment.vgCustomerSpinner = null;
        movimientosCuentaFragment.vLabelFilterSelected = null;
    }
}
