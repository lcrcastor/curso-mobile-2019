package ar.com.santander.rio.mbanking.app.ui.fragments;

import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList;
import butterknife.ButterKnife.Finder;

public class DetallePromocionShoppingFragment$$ViewInjector {
    public static void inject(Finder finder, DetallePromocionShoppingFragment detallePromocionShoppingFragment, Object obj) {
        detallePromocionShoppingFragment.txtDirec = (TextView) finder.findRequiredView(obj, R.id.txtDirec, "field 'txtDirec'");
        detallePromocionShoppingFragment.txtDist = (TextView) finder.findRequiredView(obj, R.id.txtDist, "field 'txtDist'");
        detallePromocionShoppingFragment.imgCajero = (ImageView) finder.findRequiredView(obj, R.id.imgCajero, "field 'imgCajero'");
        detallePromocionShoppingFragment.txtNombre = (TextView) finder.findRequiredView(obj, R.id.txtNombre, "field 'txtNombre'");
        detallePromocionShoppingFragment.tabSelector = (HorizontalScrollList) finder.findRequiredView(obj, R.id.tabSelector, "field 'tabSelector'");
        detallePromocionShoppingFragment.txtDesc2 = (TextView) finder.findRequiredView(obj, R.id.txtDesc2, "field 'txtDesc2'");
        detallePromocionShoppingFragment.txtDesc = (TextView) finder.findRequiredView(obj, R.id.txtDesc, "field 'txtDesc'");
        detallePromocionShoppingFragment.txtLegales = (TextView) finder.findRequiredView(obj, R.id.webViewLegales, "field 'txtLegales'");
        detallePromocionShoppingFragment.txtHtmlDesc = (TextView) finder.findRequiredView(obj, R.id.txtHtmlDesc, "field 'txtHtmlDesc'");
        detallePromocionShoppingFragment.scrollDetalle = (LinearLayout) finder.findRequiredView(obj, R.id.scrollDetalle, "field 'scrollDetalle'");
        detallePromocionShoppingFragment.scrollLocales = (LinearLayout) finder.findRequiredView(obj, R.id.scrollLocales, "field 'scrollLocales'");
        detallePromocionShoppingFragment.imgShopping = finder.findRequiredView(obj, R.id.imgShopping, "field 'imgShopping'");
        detallePromocionShoppingFragment.imgShoppingSeparator = finder.findRequiredView(obj, R.id.imgShoppingSeparator, "field 'imgShoppingSeparator'");
        detallePromocionShoppingFragment.txtLocales = (WebView) finder.findRequiredView(obj, R.id.txtLocales, "field 'txtLocales'");
        detallePromocionShoppingFragment.scrollMap = (ScrollView) finder.findRequiredView(obj, R.id.scrollMap, "field 'scrollMap'");
    }

    public static void reset(DetallePromocionShoppingFragment detallePromocionShoppingFragment) {
        detallePromocionShoppingFragment.txtDirec = null;
        detallePromocionShoppingFragment.txtDist = null;
        detallePromocionShoppingFragment.imgCajero = null;
        detallePromocionShoppingFragment.txtNombre = null;
        detallePromocionShoppingFragment.tabSelector = null;
        detallePromocionShoppingFragment.txtDesc2 = null;
        detallePromocionShoppingFragment.txtDesc = null;
        detallePromocionShoppingFragment.txtLegales = null;
        detallePromocionShoppingFragment.txtHtmlDesc = null;
        detallePromocionShoppingFragment.scrollDetalle = null;
        detallePromocionShoppingFragment.scrollLocales = null;
        detallePromocionShoppingFragment.imgShopping = null;
        detallePromocionShoppingFragment.imgShoppingSeparator = null;
        detallePromocionShoppingFragment.txtLocales = null;
        detallePromocionShoppingFragment.scrollMap = null;
    }
}
