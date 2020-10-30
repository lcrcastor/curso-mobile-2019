package ar.com.santander.rio.mbanking.app.ui.activities;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class DetalleFondoActivity$$ViewInjector {
    public static void inject(Finder finder, DetalleFondoActivity detalleFondoActivity, Object obj) {
        detalleFondoActivity.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.detalle_fondo_activity_view_flipper, "field 'mControlPager'");
        detalleFondoActivity.lblNombreDetalle = (TextView) finder.findRequiredView(obj, R.id.F24_05_LBL_NOMBRE, "field 'lblNombreDetalle'");
        detalleFondoActivity.lblDataImporte = (TextView) finder.findRequiredView(obj, R.id.F24_05_LBL_DATA_IMPORTE, "field 'lblDataImporte'");
        detalleFondoActivity.lblDataCuentaTitulo = (TextView) finder.findRequiredView(obj, R.id.F24_05_LBL_DATA_CUENTA_TITULO, "field 'lblDataCuentaTitulo'");
        detalleFondoActivity.lblDataCuotaPartes = (TextView) finder.findRequiredView(obj, R.id.F24_05_LBL_DATA_CUOTAPARTES, "field 'lblDataCuotaPartes'");
        detalleFondoActivity.lblDataValorCuotarpe = (TextView) finder.findRequiredView(obj, R.id.F24_05_LBL_DATA_VALOR_CUOTAPARTE, "field 'lblDataValorCuotarpe'");
        detalleFondoActivity.lblDataMoneda = (TextView) finder.findRequiredView(obj, R.id.F24_05_LBL_DATA_MONEDA, "field 'lblDataMoneda'");
        detalleFondoActivity.lblDataPlazoPago = (TextView) finder.findRequiredView(obj, R.id.F24_05_LBL_DATA_PLAZO_PAGO, "field 'lblDataPlazoPago'");
        detalleFondoActivity.btnSuscribirCuotapartes = (Button) finder.findRequiredView(obj, R.id.F24_05_BTN_SUSCRIBIR_MAS_CUOTAPARTES, "field 'btnSuscribirCuotapartes'");
        detalleFondoActivity.titleDetalleMovimiento = (TextView) finder.findRequiredView(obj, R.id.F24_07_LBL_TITULO_COMPROBANTE, "field 'titleDetalleMovimiento'");
        detalleFondoActivity.lblDataHorario = (TextView) finder.findRequiredView(obj, R.id.F24_05_LBL_DATA_HORARIO, "field 'lblDataHorario'");
        detalleFondoActivity.lblDataCotizacion = (TextView) finder.findRequiredView(obj, R.id.F24_05_LBL_DATA_ULTIMO_DIA, "field 'lblDataCotizacion'");
        detalleFondoActivity.lnlCotizacion = finder.findRequiredView(obj, R.id.F24_05_LNL_COTIZACION, "field 'lnlCotizacion'");
        detalleFondoActivity.lblDataAdministracionFondo = (TextView) finder.findRequiredView(obj, R.id.F24_05_LBL_DATA_ADMINISTRACION_FONDO, "field 'lblDataAdministracionFondo'");
        detalleFondoActivity.lblDataEntrada = (TextView) finder.findRequiredView(obj, R.id.F24_05_LBL_DATA_ENTRADA, "field 'lblDataEntrada'");
        detalleFondoActivity.lblDataSalida = (TextView) finder.findRequiredView(obj, R.id.F24_05_LBL_DATA_SALIDA, "field 'lblDataSalida'");
        detalleFondoActivity.lblDataTextoLegal = (TextView) finder.findRequiredView(obj, R.id.F24_05_LBL_LEYENDA_LEGAL, "field 'lblDataTextoLegal'");
        detalleFondoActivity.flechaDetalle = (ImageView) finder.findRequiredView(obj, R.id.F24_06_IMG_FLECHA_ULTIMO_DIA, "field 'flechaDetalle'");
        detalleFondoActivity.lstMovimientos = (RecyclerView) finder.findRequiredView(obj, R.id.F24_06_RV_movimientos, "field 'lstMovimientos'");
        detalleFondoActivity.lblTitleUltimosMov = (TextView) finder.findRequiredView(obj, R.id.F24_06_lbl_nombre_fondo, "field 'lblTitleUltimosMov'");
        detalleFondoActivity.rllBusquedaAvanzada = (RelativeLayout) finder.findRequiredView(obj, R.id.F24_06_RLL_BUSCAR, "field 'rllBusquedaAvanzada'");
        detalleFondoActivity.lblSinMovimientos = (LinearLayout) finder.findRequiredView(obj, R.id.F24_06_lbl_sin_movimientos, "field 'lblSinMovimientos'");
        detalleFondoActivity.lblSinMovimientosTxt = (TextView) finder.findRequiredView(obj, R.id.F24_06_lbl_sin_movimientos_txt, "field 'lblSinMovimientosTxt'");
        detalleFondoActivity.linearTitle = (LinearLayout) finder.findRequiredView(obj, R.id.F24_06_LL_TITLE, "field 'linearTitle'");
        detalleFondoActivity.lblDataCuentaTituloDetalleMov = (TextView) finder.findRequiredView(obj, R.id.F24_07_LBL_DATA_CUENTA_TITULO, "field 'lblDataCuentaTituloDetalleMov'");
        detalleFondoActivity.lblDataFondoDetalleMov = (TextView) finder.findRequiredView(obj, R.id.F24_07_LBL_DATA_FONDO, "field 'lblDataFondoDetalleMov'");
        detalleFondoActivity.lblDataCuotaparteDetalleMov = (TextView) finder.findRequiredView(obj, R.id.F24_07_LBL_DATA_CUOTAPARTE, "field 'lblDataCuotaparteDetalleMov'");
        detalleFondoActivity.lblDataCotizacionDetalleMov = (TextView) finder.findRequiredView(obj, R.id.F24_07_LBL_DATA_COTIZACION, "field 'lblDataCotizacionDetalleMov'");
        detalleFondoActivity.lblDataFechaDetalleMov = (TextView) finder.findRequiredView(obj, R.id.F24_07_LBL_DATA_FECHA, "field 'lblDataFechaDetalleMov'");
        detalleFondoActivity.lblDataConceptoDetalleMov = (TextView) finder.findRequiredView(obj, R.id.F24_07_LBL_DATA_CONCEPTO, "field 'lblDataConceptoDetalleMov'");
        detalleFondoActivity.lblDataFondoTxtDetalleMov = (TextView) finder.findRequiredView(obj, R.id.F24_07_LBL_DATA_FONDO_TXT, "field 'lblDataFondoTxtDetalleMov'");
        detalleFondoActivity.lblDataCertificadoDetalleMov = (TextView) finder.findRequiredView(obj, R.id.F24_07_LBL_DATA_CERTIFICADO, "field 'lblDataCertificadoDetalleMov'");
        detalleFondoActivity.lblFechaFondo = (TextView) finder.findRequiredView(obj, R.id.F24_07_LBL_FECHA_FONDO, "field 'lblFechaFondo'");
        detalleFondoActivity.lblConceptoDetalle = (TextView) finder.findRequiredView(obj, R.id.F24_07_LBL_SUSCRIPCION, "field 'lblConceptoDetalle'");
        detalleFondoActivity.comprobanteFondo = (LinearLayout) finder.findRequiredView(obj, R.id.F24_22_LY_COMPROBANTE_FONDO, "field 'comprobanteFondo'");
        detalleFondoActivity.lblUltimosMovimientos = (TextView) finder.findRequiredView(obj, R.id.F24_06_LBL_ULTIMOS_MOVIMIENTOS, "field 'lblUltimosMovimientos'");
        detalleFondoActivity.lblBuscar = (TextView) finder.findRequiredView(obj, R.id.F24_06_LBL_BUSCAR, "field 'lblBuscar'");
    }

    public static void reset(DetalleFondoActivity detalleFondoActivity) {
        detalleFondoActivity.mControlPager = null;
        detalleFondoActivity.lblNombreDetalle = null;
        detalleFondoActivity.lblDataImporte = null;
        detalleFondoActivity.lblDataCuentaTitulo = null;
        detalleFondoActivity.lblDataCuotaPartes = null;
        detalleFondoActivity.lblDataValorCuotarpe = null;
        detalleFondoActivity.lblDataMoneda = null;
        detalleFondoActivity.lblDataPlazoPago = null;
        detalleFondoActivity.btnSuscribirCuotapartes = null;
        detalleFondoActivity.titleDetalleMovimiento = null;
        detalleFondoActivity.lblDataHorario = null;
        detalleFondoActivity.lblDataCotizacion = null;
        detalleFondoActivity.lnlCotizacion = null;
        detalleFondoActivity.lblDataAdministracionFondo = null;
        detalleFondoActivity.lblDataEntrada = null;
        detalleFondoActivity.lblDataSalida = null;
        detalleFondoActivity.lblDataTextoLegal = null;
        detalleFondoActivity.flechaDetalle = null;
        detalleFondoActivity.lstMovimientos = null;
        detalleFondoActivity.lblTitleUltimosMov = null;
        detalleFondoActivity.rllBusquedaAvanzada = null;
        detalleFondoActivity.lblSinMovimientos = null;
        detalleFondoActivity.lblSinMovimientosTxt = null;
        detalleFondoActivity.linearTitle = null;
        detalleFondoActivity.lblDataCuentaTituloDetalleMov = null;
        detalleFondoActivity.lblDataFondoDetalleMov = null;
        detalleFondoActivity.lblDataCuotaparteDetalleMov = null;
        detalleFondoActivity.lblDataCotizacionDetalleMov = null;
        detalleFondoActivity.lblDataFechaDetalleMov = null;
        detalleFondoActivity.lblDataConceptoDetalleMov = null;
        detalleFondoActivity.lblDataFondoTxtDetalleMov = null;
        detalleFondoActivity.lblDataCertificadoDetalleMov = null;
        detalleFondoActivity.lblFechaFondo = null;
        detalleFondoActivity.lblConceptoDetalle = null;
        detalleFondoActivity.comprobanteFondo = null;
        detalleFondoActivity.lblUltimosMovimientos = null;
        detalleFondoActivity.lblBuscar = null;
    }
}
