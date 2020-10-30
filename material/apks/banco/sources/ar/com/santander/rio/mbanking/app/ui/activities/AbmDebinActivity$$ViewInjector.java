package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class AbmDebinActivity$$ViewInjector {
    public static void inject(Finder finder, AbmDebinActivity abmDebinActivity, Object obj) {
        abmDebinActivity.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.abm_debin_view_flipper, "field 'mControlPager'");
        abmDebinActivity.lblCuentaDebito = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_cDebito, "field 'lblCuentaDebito'");
        abmDebinActivity.lblMonedaV = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_moneda, "field 'lblMonedaV'");
        abmDebinActivity.lblImporteV = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_importe, "field 'lblImporteV'");
        abmDebinActivity.lblbancoV = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_data_banco, "field 'lblbancoV'");
        abmDebinActivity.lblcDebitoV = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_data_cDebito, "field 'lblcDebitoV'");
        abmDebinActivity.lblConceptoV = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_data_concepto, "field 'lblConceptoV'");
        abmDebinActivity.lblEstadoV = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_data_estado, "field 'lblEstadoV'");
        abmDebinActivity.lblCBUV = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_data_cbu, "field 'lblCBUV'");
        abmDebinActivity.lblFechaSolicitudV = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_data_fechaSolicitud, "field 'lblFechaSolicitudV'");
        abmDebinActivity.lblFechaVencimientoV = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_data_fechaVencimiento, "field 'lblFechaVencimientoV'");
        abmDebinActivity.lblSolicitanteV = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_solicitante, "field 'lblSolicitanteV'");
        abmDebinActivity.lblSolicitanteDatoV = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_solicitanteDato, "field 'lblSolicitanteDatoV'");
        abmDebinActivity.lblIdDebinV = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_data_idDebin, "field 'lblIdDebinV'");
        abmDebinActivity.lblDataDescripcionV = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_data_descripcion, "field 'lblDataDescripcionV'");
        abmDebinActivity.lblDataAliasV = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_data_alias, "field 'lblDataAliasV'");
        abmDebinActivity.btnConfirmar = (Button) finder.findRequiredView(obj, R.id.F32_50_BTN_CONFIRMAR_DEBIN, "field 'btnConfirmar'");
        abmDebinActivity.lblSaldoCDebitoV = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_data_cDebito_saldo, "field 'lblSaldoCDebitoV'");
        abmDebinActivity.lblTituloV = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_titulo, "field 'lblTituloV'");
        abmDebinActivity.rowAliasV = (LinearLayout) finder.findRequiredView(obj, R.id.F32_50_rowAlias, "field 'rowAliasV'");
        abmDebinActivity.rowDescripcionV = (LinearLayout) finder.findRequiredView(obj, R.id.F32_50_rowDescripcion, "field 'rowDescripcionV'");
        abmDebinActivity.rowBanco = (LinearLayout) finder.findRequiredView(obj, R.id.F32_50_row_banco, "field 'rowBanco'");
        abmDebinActivity.CBU = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_cbu, "field 'CBU'");
        abmDebinActivity.CBUComprobanteAnulacion = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_cbu, "field 'CBUComprobanteAnulacion'");
        abmDebinActivity.lblCuentaDebitoC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_cDebito, "field 'lblCuentaDebitoC'");
        abmDebinActivity.lblMonedaC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_moneda, "field 'lblMonedaC'");
        abmDebinActivity.lblImporteC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_importe, "field 'lblImporteC'");
        abmDebinActivity.lblbancoC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_data_banco, "field 'lblbancoC'");
        abmDebinActivity.lblcDebitoC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_data_cDebito, "field 'lblcDebitoC'");
        abmDebinActivity.lblConceptoC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_data_concepto, "field 'lblConceptoC'");
        abmDebinActivity.lblEstadoC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_data_estado, "field 'lblEstadoC'");
        abmDebinActivity.lblCBUC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_data_cbu, "field 'lblCBUC'");
        abmDebinActivity.lblFechaSolicitudC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_data_fechaSolicitud, "field 'lblFechaSolicitudC'");
        abmDebinActivity.lblFechaVencimientoC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_data_fechaVencimiento, "field 'lblFechaVencimientoC'");
        abmDebinActivity.lblSolicitanteC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_solicitante, "field 'lblSolicitanteC'");
        abmDebinActivity.lblSolicitanteDatoC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_solicitanteDato, "field 'lblSolicitanteDatoC'");
        abmDebinActivity.lblIdDebinC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_data_idDebin, "field 'lblIdDebinC'");
        abmDebinActivity.lblDataAliasC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_data_alias, "field 'lblDataAliasC'");
        abmDebinActivity.btnVolver = (Button) finder.findRequiredView(obj, R.id.F32_51_btn_volver, "field 'btnVolver'");
        abmDebinActivity.lblSaldoCDebitoC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_data_cDebito_saldo, "field 'lblSaldoCDebitoC'");
        abmDebinActivity.lblLeyendaC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_data_leyenda, "field 'lblLeyendaC'");
        abmDebinActivity.lblFechaOperacionC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_data_fechaOperacion, "field 'lblFechaOperacionC'");
        abmDebinActivity.lblnroComprobanteC = (TextView) finder.findRequiredView(obj, R.id.F32_51_lbl_data_nroComprobante, "field 'lblnroComprobanteC'");
        abmDebinActivity.lblTituloC = (TextView) finder.findRequiredView(obj, R.id.F32_51_txt_title, "field 'lblTituloC'");
        abmDebinActivity.rowAliasC = (LinearLayout) finder.findRequiredView(obj, R.id.F32_51_rowAlias, "field 'rowAliasC'");
        abmDebinActivity.relativeComprobante = (RelativeLayout) finder.findRequiredView(obj, R.id.F32_51_rll_comprobante_importe, "field 'relativeComprobante'");
        abmDebinActivity.rowBancoC = (LinearLayout) finder.findRequiredView(obj, R.id.F32_51_row_banco, "field 'rowBancoC'");
        abmDebinActivity.rowEstadoC = (LinearLayout) finder.findRequiredView(obj, R.id.F32_51_row_estado, "field 'rowEstadoC'");
        abmDebinActivity.rowLeyendaC = (LinearLayout) finder.findRequiredView(obj, R.id.F32_51_row_leyenda, "field 'rowLeyendaC'");
        abmDebinActivity.comprobanteAbm = (ScrollView) finder.findRequiredView(obj, R.id.F32_51_comprobanteGeneracionDebin, "field 'comprobanteAbm'");
        abmDebinActivity.relativeAbm = (RelativeLayout) finder.findRequiredView(obj, R.id.F32_50_rll_abm_importe, "field 'relativeAbm'");
        abmDebinActivity.leyendaConfirmar = (TextView) finder.findRequiredView(obj, R.id.F32_50_lbl_data_leyenda, "field 'leyendaConfirmar'");
    }

    public static void reset(AbmDebinActivity abmDebinActivity) {
        abmDebinActivity.mControlPager = null;
        abmDebinActivity.lblCuentaDebito = null;
        abmDebinActivity.lblMonedaV = null;
        abmDebinActivity.lblImporteV = null;
        abmDebinActivity.lblbancoV = null;
        abmDebinActivity.lblcDebitoV = null;
        abmDebinActivity.lblConceptoV = null;
        abmDebinActivity.lblEstadoV = null;
        abmDebinActivity.lblCBUV = null;
        abmDebinActivity.lblFechaSolicitudV = null;
        abmDebinActivity.lblFechaVencimientoV = null;
        abmDebinActivity.lblSolicitanteV = null;
        abmDebinActivity.lblSolicitanteDatoV = null;
        abmDebinActivity.lblIdDebinV = null;
        abmDebinActivity.lblDataDescripcionV = null;
        abmDebinActivity.lblDataAliasV = null;
        abmDebinActivity.btnConfirmar = null;
        abmDebinActivity.lblSaldoCDebitoV = null;
        abmDebinActivity.lblTituloV = null;
        abmDebinActivity.rowAliasV = null;
        abmDebinActivity.rowDescripcionV = null;
        abmDebinActivity.rowBanco = null;
        abmDebinActivity.CBU = null;
        abmDebinActivity.CBUComprobanteAnulacion = null;
        abmDebinActivity.lblCuentaDebitoC = null;
        abmDebinActivity.lblMonedaC = null;
        abmDebinActivity.lblImporteC = null;
        abmDebinActivity.lblbancoC = null;
        abmDebinActivity.lblcDebitoC = null;
        abmDebinActivity.lblConceptoC = null;
        abmDebinActivity.lblEstadoC = null;
        abmDebinActivity.lblCBUC = null;
        abmDebinActivity.lblFechaSolicitudC = null;
        abmDebinActivity.lblFechaVencimientoC = null;
        abmDebinActivity.lblSolicitanteC = null;
        abmDebinActivity.lblSolicitanteDatoC = null;
        abmDebinActivity.lblIdDebinC = null;
        abmDebinActivity.lblDataAliasC = null;
        abmDebinActivity.btnVolver = null;
        abmDebinActivity.lblSaldoCDebitoC = null;
        abmDebinActivity.lblLeyendaC = null;
        abmDebinActivity.lblFechaOperacionC = null;
        abmDebinActivity.lblnroComprobanteC = null;
        abmDebinActivity.lblTituloC = null;
        abmDebinActivity.rowAliasC = null;
        abmDebinActivity.relativeComprobante = null;
        abmDebinActivity.rowBancoC = null;
        abmDebinActivity.rowEstadoC = null;
        abmDebinActivity.rowLeyendaC = null;
        abmDebinActivity.comprobanteAbm = null;
        abmDebinActivity.relativeAbm = null;
        abmDebinActivity.leyendaConfirmar = null;
    }
}
