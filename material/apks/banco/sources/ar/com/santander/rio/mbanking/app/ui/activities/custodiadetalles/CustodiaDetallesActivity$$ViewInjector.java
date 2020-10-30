package ar.com.santander.rio.mbanking.app.ui.activities.custodiadetalles;

import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class CustodiaDetallesActivity$$ViewInjector {
    public static void inject(Finder finder, CustodiaDetallesActivity custodiaDetallesActivity, Object obj) {
        custodiaDetallesActivity.tvValorNominal = (TextView) finder.findRequiredView(obj, R.id.tvValorNominal, "field 'tvValorNominal'");
        custodiaDetallesActivity.tvPrecioMercado = (TextView) finder.findRequiredView(obj, R.id.tvPrecioMercado, "field 'tvPrecioMercado'");
        custodiaDetallesActivity.tvEstado = (TextView) finder.findRequiredView(obj, R.id.tvEstado, "field 'tvEstado'");
        custodiaDetallesActivity.tvValueDolar = (TextView) finder.findRequiredView(obj, R.id.tvLabelValueDolar, "field 'tvValueDolar'");
        custodiaDetallesActivity.tvCustodiaTerminosCondiciones = (TextView) finder.findRequiredView(obj, R.id.tv_custodia_terminos_condiciones, "field 'tvCustodiaTerminosCondiciones'");
    }

    public static void reset(CustodiaDetallesActivity custodiaDetallesActivity) {
        custodiaDetallesActivity.tvValorNominal = null;
        custodiaDetallesActivity.tvPrecioMercado = null;
        custodiaDetallesActivity.tvEstado = null;
        custodiaDetallesActivity.tvValueDolar = null;
        custodiaDetallesActivity.tvCustodiaTerminosCondiciones = null;
    }
}
