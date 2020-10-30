package ar.com.santander.rio.mbanking.app.ui.fragments;

import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class DetallePromocionStandardFragment$$ViewInjector {
    public static void inject(Finder finder, DetallePromocionStandardFragment detallePromocionStandardFragment, Object obj) {
        detallePromocionStandardFragment.txtDirec = (TextView) finder.findRequiredView(obj, R.id.txtDirec, "field 'txtDirec'");
        detallePromocionStandardFragment.txtDist = (TextView) finder.findRequiredView(obj, R.id.txtDist, "field 'txtDist'");
        detallePromocionStandardFragment.imgCajero = (ImageView) finder.findRequiredView(obj, R.id.imgCajero, "field 'imgCajero'");
        detallePromocionStandardFragment.txtNombre = (TextView) finder.findRequiredView(obj, R.id.txtNombre, "field 'txtNombre'");
        detallePromocionStandardFragment.txtDesc2 = (TextView) finder.findRequiredView(obj, R.id.txtDesc2, "field 'txtDesc2'");
        detallePromocionStandardFragment.txtDesc = (TextView) finder.findRequiredView(obj, R.id.txtDesc, "field 'txtDesc'");
        detallePromocionStandardFragment.webViewLegales = (WebView) finder.findRequiredView(obj, R.id.webViewLegales, "field 'webViewLegales'");
        detallePromocionStandardFragment.imgShopping = finder.findRequiredView(obj, R.id.imgShopping, "field 'imgShopping'");
        detallePromocionStandardFragment.imgShoppingSeparator = finder.findRequiredView(obj, R.id.imgShoppingSeparator, "field 'imgShoppingSeparator'");
        detallePromocionStandardFragment.scrollMap = (ScrollView) finder.findRequiredView(obj, R.id.scrollMap, "field 'scrollMap'");
    }

    public static void reset(DetallePromocionStandardFragment detallePromocionStandardFragment) {
        detallePromocionStandardFragment.txtDirec = null;
        detallePromocionStandardFragment.txtDist = null;
        detallePromocionStandardFragment.imgCajero = null;
        detallePromocionStandardFragment.txtNombre = null;
        detallePromocionStandardFragment.txtDesc2 = null;
        detallePromocionStandardFragment.txtDesc = null;
        detallePromocionStandardFragment.webViewLegales = null;
        detallePromocionStandardFragment.imgShopping = null;
        detallePromocionStandardFragment.imgShoppingSeparator = null;
        detallePromocionStandardFragment.scrollMap = null;
    }
}
