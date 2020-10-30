package ar.com.santander.rio.mbanking.app.ui.activities.titulosvaloresdetail;

import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.activities.titulosvaloresdetail.TitulosValoresDetailAdapter.TitulosValoresDetailViewHolder;
import butterknife.ButterKnife.Finder;

public class TitulosValoresDetailAdapter$TitulosValoresDetailViewHolder$$ViewInjector {
    public static void inject(Finder finder, TitulosValoresDetailViewHolder titulosValoresDetailViewHolder, Object obj) {
        titulosValoresDetailViewHolder.layoutProducto = (LinearLayout) finder.findRequiredView(obj, R.id.layout_producto, "field 'layoutProducto'");
        titulosValoresDetailViewHolder.tvTitulosValoresDesc = (TextView) finder.findRequiredView(obj, R.id.tv_titulos_valores_desc, "field 'tvTitulosValoresDesc'");
        titulosValoresDetailViewHolder.tvTitulosValoresValue = (TextView) finder.findRequiredView(obj, R.id.tv_titulos_valores_value, "field 'tvTitulosValoresValue'");
    }

    public static void reset(TitulosValoresDetailViewHolder titulosValoresDetailViewHolder) {
        titulosValoresDetailViewHolder.layoutProducto = null;
        titulosValoresDetailViewHolder.tvTitulosValoresDesc = null;
        titulosValoresDetailViewHolder.tvTitulosValoresValue = null;
    }
}
