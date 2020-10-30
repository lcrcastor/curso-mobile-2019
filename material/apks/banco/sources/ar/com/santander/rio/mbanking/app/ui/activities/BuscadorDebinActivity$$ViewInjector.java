package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class BuscadorDebinActivity$$ViewInjector {
    public static void inject(Finder finder, BuscadorDebinActivity buscadorDebinActivity, Object obj) {
        buscadorDebinActivity.seleccionarEstado = (RelativeLayout) finder.findRequiredView(obj, R.id.F32_01_row_estado, "field 'seleccionarEstado'");
        buscadorDebinActivity.seleccionarFechaDesde = (RelativeLayout) finder.findRequiredView(obj, R.id.F32_01_row_fecha_desde, "field 'seleccionarFechaDesde'");
        buscadorDebinActivity.seleccionarFechaHasta = (RelativeLayout) finder.findRequiredView(obj, R.id.F32_01_row_fecha_hasta, "field 'seleccionarFechaHasta'");
        buscadorDebinActivity.estadoDebin = (TextView) finder.findRequiredView(obj, R.id.F32_01_txt_estado, "field 'estadoDebin'");
        buscadorDebinActivity.fechaDesde = (TextView) finder.findRequiredView(obj, R.id.F32_01_txt_fecha_desde, "field 'fechaDesde'");
        buscadorDebinActivity.fechaHasta = (TextView) finder.findRequiredView(obj, R.id.F32_01_txt_fecha_hasta, "field 'fechaHasta'");
        buscadorDebinActivity.lblFechaHasta = (TextView) finder.findRequiredView(obj, R.id.F32_01_label_fecha_hasta, "field 'lblFechaHasta'");
        buscadorDebinActivity.lblFechaDesde = (TextView) finder.findRequiredView(obj, R.id.F32_01_label_fecha_desde, "field 'lblFechaDesde'");
        buscadorDebinActivity.btnBuscarDebin = (Button) finder.findRequiredView(obj, R.id.F32_01_BTN_BUSCAR_DEBIN, "field 'btnBuscarDebin'");
        buscadorDebinActivity.lblTitulo = (TextView) finder.findRequiredView(obj, R.id.F32_01_lbl_titulo, "field 'lblTitulo'");
    }

    public static void reset(BuscadorDebinActivity buscadorDebinActivity) {
        buscadorDebinActivity.seleccionarEstado = null;
        buscadorDebinActivity.seleccionarFechaDesde = null;
        buscadorDebinActivity.seleccionarFechaHasta = null;
        buscadorDebinActivity.estadoDebin = null;
        buscadorDebinActivity.fechaDesde = null;
        buscadorDebinActivity.fechaHasta = null;
        buscadorDebinActivity.lblFechaHasta = null;
        buscadorDebinActivity.lblFechaDesde = null;
        buscadorDebinActivity.btnBuscarDebin = null;
        buscadorDebinActivity.lblTitulo = null;
    }
}
