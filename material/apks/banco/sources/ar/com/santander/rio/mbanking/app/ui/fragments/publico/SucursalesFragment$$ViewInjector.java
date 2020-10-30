package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.components.SlidingUpPanelLayout;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollListView;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class SucursalesFragment$$ViewInjector {
    public static void inject(Finder finder, final SucursalesFragment sucursalesFragment, Object obj) {
        sucursalesFragment.mListView = (InfiniteScrollListView) finder.findRequiredView(obj, R.id.list, "field 'mListView'");
        sucursalesFragment.mSlidingUpPanelLayout = (SlidingUpPanelLayout) finder.findRequiredView(obj, R.id.slidingLayout, "field 'mSlidingUpPanelLayout'");
        sucursalesFragment.llButtonMap = (RelativeLayout) finder.findRequiredView(obj, R.id.llButtonMap, "field 'llButtonMap'");
        sucursalesFragment.noBusqueda = (LinearLayout) finder.findRequiredView(obj, R.id.noBusqueda, "field 'noBusqueda'");
        sucursalesFragment.editText = (ClearableEditText) finder.findRequiredView(obj, R.id.eTxtSearch, "field 'editText'");
        finder.findRequiredView(obj, R.id.txtLocaliza, "method 'setMyPosition'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                sucursalesFragment.A();
            }
        });
        finder.findRequiredView(obj, R.id.btnOpen, "method 'expand'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                sucursalesFragment.expand();
            }
        });
        finder.findRequiredView(obj, R.id.btnMasSucursales, "method 'cargarMas'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                sucursalesFragment.cargarMas();
            }
        });
        finder.findRequiredView(obj, R.id.imgSearch, "method 'performSearch'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                sucursalesFragment.performSearch();
            }
        });
    }

    public static void reset(SucursalesFragment sucursalesFragment) {
        sucursalesFragment.mListView = null;
        sucursalesFragment.mSlidingUpPanelLayout = null;
        sucursalesFragment.llButtonMap = null;
        sucursalesFragment.noBusqueda = null;
        sucursalesFragment.editText = null;
    }
}
