package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.NumericEditText;
import butterknife.ButterKnife.Finder;

public class BusquedaAvanzadaMovimientoFondosActivity$$ViewInjector {
    public static void inject(Finder finder, BusquedaAvanzadaMovimientoFondosActivity busquedaAvanzadaMovimientoFondosActivity, Object obj) {
        busquedaAvanzadaMovimientoFondosActivity.lblDataFechaDesde = (TextView) finder.findRequiredView(obj, R.id.F24_08_lbl_data_fecha_desde, "field 'lblDataFechaDesde'");
        busquedaAvanzadaMovimientoFondosActivity.lblDataFechaHasta = (TextView) finder.findRequiredView(obj, R.id.F24_08_lbl_data_fecha_hasta, "field 'lblDataFechaHasta'");
        busquedaAvanzadaMovimientoFondosActivity.inpImporteDesde = (NumericEditText) finder.findRequiredView(obj, R.id.F24_08_edt_importe_desde, "field 'inpImporteDesde'");
        busquedaAvanzadaMovimientoFondosActivity.inpImporteHasta = (NumericEditText) finder.findRequiredView(obj, R.id.F24_08_edt_importe_hasta, "field 'inpImporteHasta'");
        busquedaAvanzadaMovimientoFondosActivity.btnBuscar = (Button) finder.findRequiredView(obj, R.id.F24_08_btn_buscar, "field 'btnBuscar'");
        busquedaAvanzadaMovimientoFondosActivity.lblImporte = (TextView) finder.findRequiredView(obj, R.id.F24_08_lbl_importe, "field 'lblImporte'");
    }

    public static void reset(BusquedaAvanzadaMovimientoFondosActivity busquedaAvanzadaMovimientoFondosActivity) {
        busquedaAvanzadaMovimientoFondosActivity.lblDataFechaDesde = null;
        busquedaAvanzadaMovimientoFondosActivity.lblDataFechaHasta = null;
        busquedaAvanzadaMovimientoFondosActivity.inpImporteDesde = null;
        busquedaAvanzadaMovimientoFondosActivity.inpImporteHasta = null;
        busquedaAvanzadaMovimientoFondosActivity.btnBuscar = null;
        busquedaAvanzadaMovimientoFondosActivity.lblImporte = null;
    }
}
