package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class DetalleDebinFragment$$ViewInjector {
    public static void inject(Finder finder, DetalleDebinFragment detalleDebinFragment, Object obj) {
        detalleDebinFragment.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.debin_view_flipper, "field 'mControlPager'");
        detalleDebinFragment.pantallaDebin = finder.findRequiredView(obj, R.id.debin_main, "field 'pantallaDebin'");
        detalleDebinFragment.detalleComprador = (LinearLayout) finder.findRequiredView(obj, R.id.F32_02_pantallaDetalleC, "field 'detalleComprador'");
        detalleDebinFragment.detalleVendedor = (LinearLayout) finder.findRequiredView(obj, R.id.F32_05_pantallaDetalleV, "field 'detalleVendedor'");
        detalleDebinFragment.titleConsulta = (TextView) finder.findRequiredView(obj, R.id.textView8, "field 'titleConsulta'");
        detalleDebinFragment.selectorDebinRecibidos = (RelativeLayout) finder.findRequiredView(obj, R.id.F32_00_SELECTOR_DEBIN_RECIBIDOS, "field 'selectorDebinRecibidos'");
        detalleDebinFragment.selectorDebinEnviados = (RelativeLayout) finder.findRequiredView(obj, R.id.F32_00_SELECTOR_DEBIN_ENVIADOS, "field 'selectorDebinEnviados'");
        detalleDebinFragment.cabeceraDebin = (LinearLayout) finder.findRequiredView(obj, R.id.cabeceraDebin, "field 'cabeceraDebin'");
        detalleDebinFragment.sinDebines = (LinearLayout) finder.findRequiredView(obj, R.id.sinDebines, "field 'sinDebines'");
        detalleDebinFragment.buscadorDebin = (RelativeLayout) finder.findRequiredView(obj, R.id.buscadorDebin, "field 'buscadorDebin'");
        detalleDebinFragment.blockBuscador = finder.findRequiredView(obj, R.id.F32_00_BLOCK_BUSCAR, "field 'blockBuscador'");
        detalleDebinFragment.blockTabsEnviadas = finder.findRequiredView(obj, R.id.F32_00_BLOCK_TABS_ENVIADAS, "field 'blockTabsEnviadas'");
        detalleDebinFragment.blockTabsRecibidas = finder.findRequiredView(obj, R.id.F32_00_BLOCK_TABS_RECIBIDAS, "field 'blockTabsRecibidas'");
        detalleDebinFragment.lstDebines = (RecyclerView) finder.findRequiredView(obj, R.id.F32_01_RCV_DEBINES, "field 'lstDebines'");
        detalleDebinFragment.txt_error = (TextView) finder.findRequiredView(obj, R.id.textoError, "field 'txt_error'");
        detalleDebinFragment.txtTodasSolicitudes = (TextView) finder.findRequiredView(obj, R.id.todasSolicitudes, "field 'txtTodasSolicitudes'");
        detalleDebinFragment.lblSolicitanteDestinatario = (TextView) finder.findRequiredView(obj, R.id.F32_01_lbl_solicitante_destinatario, "field 'lblSolicitanteDestinatario'");
        detalleDebinFragment.txtSelectorEnviados = (TextView) finder.findRequiredView(obj, R.id.F32_00_lbl_SELECTOR_DEBIN_ENVIADOS, "field 'txtSelectorEnviados'");
        detalleDebinFragment.txtSelectorRecibidos = (TextView) finder.findRequiredView(obj, R.id.F32_00_lbl_SELECTOR_DEBIN_RECIBIDOS, "field 'txtSelectorRecibidos'");
        detalleDebinFragment.viewSelectorEnviados = finder.findRequiredView(obj, R.id.F32_00_view_SELECTOR_DEBIN_ENVIADOS, "field 'viewSelectorEnviados'");
        detalleDebinFragment.viewSelectorRecibidos = finder.findRequiredView(obj, R.id.F32_00_view_SELECTOR_DEBIN_RECIBIDOS, "field 'viewSelectorRecibidos'");
        detalleDebinFragment.tVCBUComprador = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_cbu, "field 'tVCBUComprador'");
        detalleDebinFragment.tVCBUVendedor = (TextView) finder.findRequiredView(obj, R.id.F32_05_lbl_cbu, "field 'tVCBUVendedor'");
        detalleDebinFragment.lblMoneda = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_moneda, "field 'lblMoneda'");
        detalleDebinFragment.lblImporte = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_importe, "field 'lblImporte'");
        detalleDebinFragment.lblbanco = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_data_banco, "field 'lblbanco'");
        detalleDebinFragment.lblcDebito = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_data_cDebito, "field 'lblcDebito'");
        detalleDebinFragment.lblConcepto = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_data_concepto, "field 'lblConcepto'");
        detalleDebinFragment.lblEstado = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_data_estado, "field 'lblEstado'");
        detalleDebinFragment.lblCBU = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_data_cbu, "field 'lblCBU'");
        detalleDebinFragment.lblFechaSolicitud = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_data_fechaSolicitud, "field 'lblFechaSolicitud'");
        detalleDebinFragment.lblFechaVencimiento = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_data_fechaVencimiento, "field 'lblFechaVencimiento'");
        detalleDebinFragment.lblSolicitante = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_solicitante, "field 'lblSolicitante'");
        detalleDebinFragment.lblSolicitanteDato = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_solicitanteDato, "field 'lblSolicitanteDato'");
        detalleDebinFragment.lblIdDebin = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_data_idDebin, "field 'lblIdDebin'");
        detalleDebinFragment.lblDataAlias = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_data_alias, "field 'lblDataAlias'");
        detalleDebinFragment.lblDataDescripcion = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_data_descripcion, "field 'lblDataDescripcion'");
        detalleDebinFragment.buttonAceptar = (Button) finder.findRequiredView(obj, R.id.F32_02_BTN_PAGAR_DEBIN, "field 'buttonAceptar'");
        detalleDebinFragment.buttonRechazar = (Button) finder.findRequiredView(obj, R.id.F32_02_BTN_RECHAZAR_DEBIN, "field 'buttonRechazar'");
        detalleDebinFragment.buttonDesconocer = (Button) finder.findRequiredView(obj, R.id.F32_02_BTN_DESCONOCER_DEBIN, "field 'buttonDesconocer'");
        detalleDebinFragment.lblSaldoCDebito = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_data_cDebito_saldo, "field 'lblSaldoCDebito'");
        detalleDebinFragment.scrollC = (ScrollView) finder.findRequiredView(obj, R.id.scrollDetC, "field 'scrollC'");
        detalleDebinFragment.rowAlias = (LinearLayout) finder.findRequiredView(obj, R.id.F32_02_rowAlias, "field 'rowAlias'");
        detalleDebinFragment.rowDescripcion = (LinearLayout) finder.findRequiredView(obj, R.id.F32_02_rowDescripcion, "field 'rowDescripcion'");
        detalleDebinFragment.rowBanco = (LinearLayout) finder.findRequiredView(obj, R.id.F32_02_row_banco, "field 'rowBanco'");
        detalleDebinFragment.leyendaDetalleComprador = (TextView) finder.findRequiredView(obj, R.id.F32_02_lbl_leyenda, "field 'leyendaDetalleComprador'");
        detalleDebinFragment.lblMonedaV = (TextView) finder.findRequiredView(obj, R.id.F32_05_lbl_moneda, "field 'lblMonedaV'");
        detalleDebinFragment.lblImporteV = (TextView) finder.findRequiredView(obj, R.id.F32_05_lbl_importe, "field 'lblImporteV'");
        detalleDebinFragment.lblbancoV = (TextView) finder.findRequiredView(obj, R.id.F32_05_lbl_data_banco, "field 'lblbancoV'");
        detalleDebinFragment.lblcDebitoV = (TextView) finder.findRequiredView(obj, R.id.F32_05_lbl_data_cDebito, "field 'lblcDebitoV'");
        detalleDebinFragment.lblConceptoV = (TextView) finder.findRequiredView(obj, R.id.F32_05_lbl_data_concepto, "field 'lblConceptoV'");
        detalleDebinFragment.lblEstadoV = (TextView) finder.findRequiredView(obj, R.id.F32_05_lbl_data_estado, "field 'lblEstadoV'");
        detalleDebinFragment.lblCBUV = (TextView) finder.findRequiredView(obj, R.id.F32_05_lbl_data_cbu, "field 'lblCBUV'");
        detalleDebinFragment.lblFechaSolicitudV = (TextView) finder.findRequiredView(obj, R.id.F32_05_lbl_data_fechaSolicitud, "field 'lblFechaSolicitudV'");
        detalleDebinFragment.lblFechaVencimientoV = (TextView) finder.findRequiredView(obj, R.id.F32_05_lbl_data_fechaVencimiento, "field 'lblFechaVencimientoV'");
        detalleDebinFragment.lblSolicitanteV = (TextView) finder.findRequiredView(obj, R.id.F32_05_lbl_solicitante, "field 'lblSolicitanteV'");
        detalleDebinFragment.lblSolicitanteDatoV = (TextView) finder.findRequiredView(obj, R.id.F32_05_lbl_solicitanteDato, "field 'lblSolicitanteDatoV'");
        detalleDebinFragment.lblIdDebinV = (TextView) finder.findRequiredView(obj, R.id.F32_05_lbl_data_idDebin, "field 'lblIdDebinV'");
        detalleDebinFragment.lblDataAliasV = (TextView) finder.findRequiredView(obj, R.id.F32_05_lbl_data_alias, "field 'lblDataAliasV'");
        detalleDebinFragment.lblDataDescripcionV = (TextView) finder.findRequiredView(obj, R.id.F32_05_lbl_data_descripcion, "field 'lblDataDescripcionV'");
        detalleDebinFragment.btnAnular = (Button) finder.findRequiredView(obj, R.id.F32_05_BTN_ANULAR_DEBIN, "field 'btnAnular'");
        detalleDebinFragment.lblSaldoCDebitoV = (TextView) finder.findRequiredView(obj, R.id.F32_05_lbl_data_cDebito_saldo, "field 'lblSaldoCDebitoV'");
        detalleDebinFragment.scrollV = (ScrollView) finder.findRequiredView(obj, R.id.scrollDetV, "field 'scrollV'");
        detalleDebinFragment.rowAliasV = (LinearLayout) finder.findRequiredView(obj, R.id.F32_05_row_alias, "field 'rowAliasV'");
        detalleDebinFragment.rowDescripcionV = (LinearLayout) finder.findRequiredView(obj, R.id.F32_05_row_descripcion, "field 'rowDescripcionV'");
        detalleDebinFragment.rowBancoV = (LinearLayout) finder.findRequiredView(obj, R.id.F32_05_row_bancco, "field 'rowBancoV'");
        detalleDebinFragment.txtLeyend = (TextView) finder.findRequiredView(obj, R.id.txtLeyend, "field 'txtLeyend'");
        detalleDebinFragment.relativeVendedor = (RelativeLayout) finder.findRequiredView(obj, R.id.rll_env, "field 'relativeVendedor'");
        detalleDebinFragment.buttomsContainer = (LinearLayout) finder.findRequiredView(obj, R.id.F32_02_row_botones, "field 'buttomsContainer'");
        detalleDebinFragment.relativeComprador = (RelativeLayout) finder.findRequiredView(obj, R.id.rll_comp, "field 'relativeComprador'");
    }

    public static void reset(DetalleDebinFragment detalleDebinFragment) {
        detalleDebinFragment.mControlPager = null;
        detalleDebinFragment.pantallaDebin = null;
        detalleDebinFragment.detalleComprador = null;
        detalleDebinFragment.detalleVendedor = null;
        detalleDebinFragment.titleConsulta = null;
        detalleDebinFragment.selectorDebinRecibidos = null;
        detalleDebinFragment.selectorDebinEnviados = null;
        detalleDebinFragment.cabeceraDebin = null;
        detalleDebinFragment.sinDebines = null;
        detalleDebinFragment.buscadorDebin = null;
        detalleDebinFragment.blockBuscador = null;
        detalleDebinFragment.blockTabsEnviadas = null;
        detalleDebinFragment.blockTabsRecibidas = null;
        detalleDebinFragment.lstDebines = null;
        detalleDebinFragment.txt_error = null;
        detalleDebinFragment.txtTodasSolicitudes = null;
        detalleDebinFragment.lblSolicitanteDestinatario = null;
        detalleDebinFragment.txtSelectorEnviados = null;
        detalleDebinFragment.txtSelectorRecibidos = null;
        detalleDebinFragment.viewSelectorEnviados = null;
        detalleDebinFragment.viewSelectorRecibidos = null;
        detalleDebinFragment.tVCBUComprador = null;
        detalleDebinFragment.tVCBUVendedor = null;
        detalleDebinFragment.lblMoneda = null;
        detalleDebinFragment.lblImporte = null;
        detalleDebinFragment.lblbanco = null;
        detalleDebinFragment.lblcDebito = null;
        detalleDebinFragment.lblConcepto = null;
        detalleDebinFragment.lblEstado = null;
        detalleDebinFragment.lblCBU = null;
        detalleDebinFragment.lblFechaSolicitud = null;
        detalleDebinFragment.lblFechaVencimiento = null;
        detalleDebinFragment.lblSolicitante = null;
        detalleDebinFragment.lblSolicitanteDato = null;
        detalleDebinFragment.lblIdDebin = null;
        detalleDebinFragment.lblDataAlias = null;
        detalleDebinFragment.lblDataDescripcion = null;
        detalleDebinFragment.buttonAceptar = null;
        detalleDebinFragment.buttonRechazar = null;
        detalleDebinFragment.buttonDesconocer = null;
        detalleDebinFragment.lblSaldoCDebito = null;
        detalleDebinFragment.scrollC = null;
        detalleDebinFragment.rowAlias = null;
        detalleDebinFragment.rowDescripcion = null;
        detalleDebinFragment.rowBanco = null;
        detalleDebinFragment.leyendaDetalleComprador = null;
        detalleDebinFragment.lblMonedaV = null;
        detalleDebinFragment.lblImporteV = null;
        detalleDebinFragment.lblbancoV = null;
        detalleDebinFragment.lblcDebitoV = null;
        detalleDebinFragment.lblConceptoV = null;
        detalleDebinFragment.lblEstadoV = null;
        detalleDebinFragment.lblCBUV = null;
        detalleDebinFragment.lblFechaSolicitudV = null;
        detalleDebinFragment.lblFechaVencimientoV = null;
        detalleDebinFragment.lblSolicitanteV = null;
        detalleDebinFragment.lblSolicitanteDatoV = null;
        detalleDebinFragment.lblIdDebinV = null;
        detalleDebinFragment.lblDataAliasV = null;
        detalleDebinFragment.lblDataDescripcionV = null;
        detalleDebinFragment.btnAnular = null;
        detalleDebinFragment.lblSaldoCDebitoV = null;
        detalleDebinFragment.scrollV = null;
        detalleDebinFragment.rowAliasV = null;
        detalleDebinFragment.rowDescripcionV = null;
        detalleDebinFragment.rowBancoV = null;
        detalleDebinFragment.txtLeyend = null;
        detalleDebinFragment.relativeVendedor = null;
        detalleDebinFragment.buttomsContainer = null;
        detalleDebinFragment.relativeComprador = null;
    }
}
