package ar.com.santander.rio.mbanking.app.ui.activities;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.NumericEditText;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class BusquedaAvanzadaActivity$$ViewInjector {
    public static void inject(Finder finder, final BusquedaAvanzadaActivity busquedaAvanzadaActivity, Object obj) {
        busquedaAvanzadaActivity.lblBusquedaAvanzada = (TextView) finder.findRequiredView(obj, R.id.F12_11_lbl_busquedaAvanzada, "field 'lblBusquedaAvanzada'");
        busquedaAvanzadaActivity.lblEstado = (TextView) finder.findRequiredView(obj, R.id.F12_11_lbl_estado, "field 'lblEstado'");
        View findRequiredView = finder.findRequiredView(obj, R.id.F12_11_lbl_data_estado, "field 'lblEstadoData' and method 'mostrarSelectorEstado'");
        busquedaAvanzadaActivity.lblEstadoData = (TextView) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                busquedaAvanzadaActivity.mostrarSelectorEstado((TextView) view);
            }
        });
        busquedaAvanzadaActivity.lblFechaAltaEstado = (TextView) finder.findRequiredView(obj, R.id.F12_11_lbl_fechaAlta, "field 'lblFechaAltaEstado'");
        busquedaAvanzadaActivity.lblDesde = (TextView) finder.findRequiredView(obj, R.id.F12_11_lbl_fecha_desde, "field 'lblDesde'");
        busquedaAvanzadaActivity.lblHasta = (TextView) finder.findRequiredView(obj, R.id.F12_11_lbl_fecha_hasta, "field 'lblHasta'");
        busquedaAvanzadaActivity.lblImporte = (TextView) finder.findRequiredView(obj, R.id.F12_11_lbl_importe, "field 'lblImporte'");
        busquedaAvanzadaActivity.lblImporteDesde = (TextView) finder.findRequiredView(obj, R.id.F12_11_lbl_importe_desde, "field 'lblImporteDesde'");
        busquedaAvanzadaActivity.lblImporteHasta = (TextView) finder.findRequiredView(obj, R.id.F12_11_lbl_importe_hasta, "field 'lblImporteHasta'");
        busquedaAvanzadaActivity.lblDestinatario = (TextView) finder.findRequiredView(obj, R.id.F12_11_lbl_destinatario, "field 'lblDestinatario'");
        busquedaAvanzadaActivity.rllDestinatarios = (RelativeLayout) finder.findRequiredView(obj, R.id.F12_11_rll_destinatario, "field 'rllDestinatarios'");
        View findRequiredView2 = finder.findRequiredView(obj, R.id.F12_11_lbl_data_destinatario, "field 'lblDestinatarios' and method 'mostrarSelectorDestinatario'");
        busquedaAvanzadaActivity.lblDestinatarios = (TextView) findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                busquedaAvanzadaActivity.mostrarSelectorDestinatario((TextView) view);
            }
        });
        View findRequiredView3 = finder.findRequiredView(obj, R.id.F12_11_lbl_data_fecha_desde, "field 'lblFechaDesde' and method 'mostrarSelectorFechaDesde'");
        busquedaAvanzadaActivity.lblFechaDesde = (TextView) findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                busquedaAvanzadaActivity.mostrarSelectorFechaDesde((TextView) view);
            }
        });
        View findRequiredView4 = finder.findRequiredView(obj, R.id.F12_11_lbl_data_fecha_hasta, "field 'lblFechaHasta' and method 'mostrarSelectorFechaHasta'");
        busquedaAvanzadaActivity.lblFechaHasta = (TextView) findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                busquedaAvanzadaActivity.mostrarSelectorFechaHasta((TextView) view);
            }
        });
        busquedaAvanzadaActivity.inpImporteDesde = (NumericEditText) finder.findRequiredView(obj, R.id.F12_11_num_importe_desde, "field 'inpImporteDesde'");
        busquedaAvanzadaActivity.inpImporteHasta = (NumericEditText) finder.findRequiredView(obj, R.id.F12_11_num_importe_hasta, "field 'inpImporteHasta'");
        busquedaAvanzadaActivity.btnBuscar = (Button) finder.findRequiredView(obj, R.id.F12_11_btn_buscar, "field 'btnBuscar'");
    }

    public static void reset(BusquedaAvanzadaActivity busquedaAvanzadaActivity) {
        busquedaAvanzadaActivity.lblBusquedaAvanzada = null;
        busquedaAvanzadaActivity.lblEstado = null;
        busquedaAvanzadaActivity.lblEstadoData = null;
        busquedaAvanzadaActivity.lblFechaAltaEstado = null;
        busquedaAvanzadaActivity.lblDesde = null;
        busquedaAvanzadaActivity.lblHasta = null;
        busquedaAvanzadaActivity.lblImporte = null;
        busquedaAvanzadaActivity.lblImporteDesde = null;
        busquedaAvanzadaActivity.lblImporteHasta = null;
        busquedaAvanzadaActivity.lblDestinatario = null;
        busquedaAvanzadaActivity.rllDestinatarios = null;
        busquedaAvanzadaActivity.lblDestinatarios = null;
        busquedaAvanzadaActivity.lblFechaDesde = null;
        busquedaAvanzadaActivity.lblFechaHasta = null;
        busquedaAvanzadaActivity.inpImporteDesde = null;
        busquedaAvanzadaActivity.inpImporteHasta = null;
        busquedaAvanzadaActivity.btnBuscar = null;
    }
}
