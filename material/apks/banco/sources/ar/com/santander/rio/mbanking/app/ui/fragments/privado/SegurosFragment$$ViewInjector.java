package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class SegurosFragment$$ViewInjector {
    public static void inject(Finder finder, SegurosFragment segurosFragment, Object obj) {
        segurosFragment.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.seguros_view_flipper, "field 'mControlPager'");
        segurosFragment.lnlHeaderTenenciaSeguros = (LinearLayout) finder.findRequiredView(obj, R.id.F27_00_LNL_TABLE_HEADER, "field 'lnlHeaderTenenciaSeguros'");
        segurosFragment.tblTenenciaSeguros = (TableLayout) finder.findRequiredView(obj, R.id.F27_00_TBL_SEGUROS, "field 'tblTenenciaSeguros'");
        segurosFragment.lblDispositivoNoAsegurado = (LinearLayout) finder.findRequiredView(obj, R.id.F27_00_LNL_WARNING_NO_ASEGURADO, "field 'lblDispositivoNoAsegurado'");
        segurosFragment.lblMasInformacion = (TextView) finder.findRequiredView(obj, R.id.F27_00_LBL_MAS_INFO, "field 'lblMasInformacion'");
        segurosFragment.lblLinkDescripcion = (TextView) finder.findRequiredView(obj, R.id.F27_00_LBL_LINK_DESCRIPTCION, "field 'lblLinkDescripcion'");
        segurosFragment.lblSinSeguros = (TextView) finder.findRequiredView(obj, R.id.F27_00_LBL_SIN_SEGUROS, "field 'lblSinSeguros'");
        segurosFragment.btnContratarSeguro = (Button) finder.findRequiredView(obj, R.id.F27_00_BTN_CONTRATAR_SEGURO, "field 'btnContratarSeguro'");
        segurosFragment.lblTituloDetalle = (TextView) finder.findRequiredView(obj, R.id.F27_01_LBL_DATA_TITULO_SEGURO, "field 'lblTituloDetalle'");
        segurosFragment.scrollViewDetalle = (ScrollView) finder.findRequiredView(obj, R.id.F27_01_SCR_WRAPPER_DETALLE, "field 'scrollViewDetalle'");
        segurosFragment.lblAseguradoraDetalle = (TextView) finder.findRequiredView(obj, R.id.F27_01_LBL_DATA_ASEGURADORA, "field 'lblAseguradoraDetalle'");
        segurosFragment.lblCuotaDetalle = (TextView) finder.findRequiredView(obj, R.id.F27_01_LBL_DATA_CUOTA, "field 'lblCuotaDetalle'");
        segurosFragment.lblSumaAseguradaDetalle = (TextView) finder.findRequiredView(obj, R.id.F27_01_LBL_DATA_SUMA_ASEGURADA, "field 'lblSumaAseguradaDetalle'");
        segurosFragment.lblFechaInicioDetalle = (TextView) finder.findRequiredView(obj, R.id.F27_01_LBL_DATA_FECHA_INICIO, "field 'lblFechaInicioDetalle'");
        segurosFragment.lblPolizaDetalle = (TextView) finder.findRequiredView(obj, R.id.F27_01_LBL_DATA_POLIZA, "field 'lblPolizaDetalle'");
        segurosFragment.lblMedioPagoDetalle = (TextView) finder.findRequiredView(obj, R.id.F27_01_LBL_DATA_MEDIO_PAGO, "field 'lblMedioPagoDetalle'");
        segurosFragment.lblFormaPagoDetalle = (TextView) finder.findRequiredView(obj, R.id.F27_01_LBL_DATA_FORMA_PAGO, "field 'lblFormaPagoDetalle'");
        segurosFragment.lnlWrapperDatosDetalle = (LinearLayout) finder.findRequiredView(obj, R.id.F27_01_LNL_WRAPPER_DATOS, "field 'lnlWrapperDatosDetalle'");
        segurosFragment.lblSeparadorDatosDetalle = (TextView) finder.findRequiredView(obj, R.id.F27_01_LBL_DATA_SEPARADOR, "field 'lblSeparadorDatosDetalle'");
        segurosFragment.tblDatosDetalle = (TableLayout) finder.findRequiredView(obj, R.id.F27_01_TBL_DATOS, "field 'tblDatosDetalle'");
        segurosFragment.btnSolicitarAsistencia = (Button) finder.findRequiredView(obj, R.id.F27_01_BTN_SOLICITAR_ASISTENCIA, "field 'btnSolicitarAsistencia'");
        segurosFragment.tblAsistencias = (TableLayout) finder.findRequiredView(obj, R.id.F27_40_TBL_ASISTENCIAS, "field 'tblAsistencias'");
        segurosFragment.lblTelefonoAsistencia = (TextView) finder.findRequiredView(obj, R.id.F27_41_LBL_TELEFONO, "field 'lblTelefonoAsistencia'");
        segurosFragment.btnLlamarAsistencia = (Button) finder.findRequiredView(obj, R.id.F27_41_BTN_LLAMAR, "field 'btnLlamarAsistencia'");
        segurosFragment.lblTipoPoliza = (TextView) finder.findRequiredView(obj, R.id.F27_01_LBL_DATA_TIPO_POLIZA, "field 'lblTipoPoliza'");
        segurosFragment.lblDataEmail = (TextView) finder.findRequiredView(obj, R.id.F27_01_LBL_DATA_EMAIL, "field 'lblDataEmail'");
        segurosFragment.lblDataOcupacion = (TextView) finder.findRequiredView(obj, R.id.F27_01_LBL_DATA_OCUPACION, "field 'lblDataOcupacion'");
        segurosFragment.icoSinMedioPago = (ImageView) finder.findRequiredView(obj, R.id.F27_00_ICO_SIN_MEDIO_PAGO, "field 'icoSinMedioPago'");
        segurosFragment.lblSinMedioPago = (TextView) finder.findRequiredView(obj, R.id.F27_00_LBL_SIN_MEDIO_PAGO, "field 'lblSinMedioPago'");
        segurosFragment.ll_email = (LinearLayout) finder.findRequiredView(obj, R.id.LL_Email, "field 'll_email'");
        segurosFragment.ll_ocupaciones = (LinearLayout) finder.findRequiredView(obj, R.id.LL_Ocupaciones, "field 'll_ocupaciones'");
        segurosFragment.separatorLeyenda = finder.findRequiredView(obj, R.id.F27_00_SEPARARTOR_LEYENDA_AYUDA, "field 'separatorLeyenda'");
        segurosFragment.rowMedioPago = finder.findRequiredView(obj, R.id.F27_00_rowMedioPago, "field 'rowMedioPago'");
        segurosFragment.rowFechaInicio = (LinearLayout) finder.findRequiredView(obj, R.id.F27_01_ROW_FECHAINICIO, "field 'rowFechaInicio'");
        segurosFragment.rowCuota = (LinearLayout) finder.findRequiredView(obj, R.id.F27_01_ROW_CUOTA, "field 'rowCuota'");
        segurosFragment.rowTipoPoliza = (LinearLayout) finder.findRequiredView(obj, R.id.F27_01_ROW_POLIZA, "field 'rowTipoPoliza'");
    }

    public static void reset(SegurosFragment segurosFragment) {
        segurosFragment.mControlPager = null;
        segurosFragment.lnlHeaderTenenciaSeguros = null;
        segurosFragment.tblTenenciaSeguros = null;
        segurosFragment.lblDispositivoNoAsegurado = null;
        segurosFragment.lblMasInformacion = null;
        segurosFragment.lblLinkDescripcion = null;
        segurosFragment.lblSinSeguros = null;
        segurosFragment.btnContratarSeguro = null;
        segurosFragment.lblTituloDetalle = null;
        segurosFragment.scrollViewDetalle = null;
        segurosFragment.lblAseguradoraDetalle = null;
        segurosFragment.lblCuotaDetalle = null;
        segurosFragment.lblSumaAseguradaDetalle = null;
        segurosFragment.lblFechaInicioDetalle = null;
        segurosFragment.lblPolizaDetalle = null;
        segurosFragment.lblMedioPagoDetalle = null;
        segurosFragment.lblFormaPagoDetalle = null;
        segurosFragment.lnlWrapperDatosDetalle = null;
        segurosFragment.lblSeparadorDatosDetalle = null;
        segurosFragment.tblDatosDetalle = null;
        segurosFragment.btnSolicitarAsistencia = null;
        segurosFragment.tblAsistencias = null;
        segurosFragment.lblTelefonoAsistencia = null;
        segurosFragment.btnLlamarAsistencia = null;
        segurosFragment.lblTipoPoliza = null;
        segurosFragment.lblDataEmail = null;
        segurosFragment.lblDataOcupacion = null;
        segurosFragment.icoSinMedioPago = null;
        segurosFragment.lblSinMedioPago = null;
        segurosFragment.ll_email = null;
        segurosFragment.ll_ocupaciones = null;
        segurosFragment.separatorLeyenda = null;
        segurosFragment.rowMedioPago = null;
        segurosFragment.rowFechaInicio = null;
        segurosFragment.rowCuota = null;
        segurosFragment.rowTipoPoliza = null;
    }
}
