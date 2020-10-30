package ar.com.santander.rio.mbanking.app.ui.activities.titulosvalores;

import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.activities.titulosvalores.TitulosValoresAdapter.TitulosValoresViewHolder;
import butterknife.ButterKnife.Finder;

public class TitulosValoresAdapter$TitulosValoresViewHolder$$ViewInjector {
    public static void inject(Finder finder, TitulosValoresViewHolder titulosValoresViewHolder, Object obj) {
        titulosValoresViewHolder.layoutParent = (LinearLayout) finder.findRequiredView(obj, R.id.layout_parent, "field 'layoutParent'");
        titulosValoresViewHolder.layoutChild = (LinearLayout) finder.findRequiredView(obj, R.id.layout_child, "field 'layoutChild'");
        titulosValoresViewHolder.tvTitle = (TextView) finder.findRequiredView(obj, R.id.view_title, "field 'tvTitle'");
    }

    public static void reset(TitulosValoresViewHolder titulosValoresViewHolder) {
        titulosValoresViewHolder.layoutParent = null;
        titulosValoresViewHolder.layoutChild = null;
        titulosValoresViewHolder.tvTitle = null;
    }
}
