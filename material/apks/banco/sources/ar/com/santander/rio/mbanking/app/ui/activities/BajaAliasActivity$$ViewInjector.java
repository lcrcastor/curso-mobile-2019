package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class BajaAliasActivity$$ViewInjector {
    public static void inject(Finder finder, BajaAliasActivity bajaAliasActivity, Object obj) {
        bajaAliasActivity.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.baja_alias_view_flipper, "field 'mControlPager'");
        bajaAliasActivity.comprobanteOperacion = (ScrollView) finder.findRequiredView(obj, R.id.scrollView_BajaAlias_comprobante, "field 'comprobanteOperacion'");
        bajaAliasActivity.lblDataAlias = (TextView) finder.findRequiredView(obj, R.id.F28_01_LBL_DATA_ALIAS, "field 'lblDataAlias'");
        bajaAliasActivity.lblDataCBU = (TextView) finder.findRequiredView(obj, R.id.F28_01_LBL_DATA_CBU, "field 'lblDataCBU'");
        bajaAliasActivity.btnContinuar = (Button) finder.findRequiredView(obj, R.id.F28_01_BTN_CONTINUAR, "field 'btnContinuar'");
        bajaAliasActivity.lblDataNumeroCuenta = (TextView) finder.findRequiredView(obj, R.id.F28_01_LBL_DATA_NUMERO_CUENTA, "field 'lblDataNumeroCuenta'");
        bajaAliasActivity.lblDataTipoCuenta = (TextView) finder.findRequiredView(obj, R.id.F28_01_LBL_DATA_TIPO_CUENTA, "field 'lblDataTipoCuenta'");
        bajaAliasActivity.lblDataTitular = (TextView) finder.findRequiredView(obj, R.id.F28_01_LBL_DATA_TITULAR, "field 'lblDataTitular'");
        bajaAliasActivity.lblDataCUIT = (TextView) finder.findRequiredView(obj, R.id.F28_01_LBL_DATA_CUIT, "field 'lblDataCUIT'");
        bajaAliasActivity.lblComprobanteAlias = (TextView) finder.findRequiredView(obj, R.id.F28_01_LBL_COMPROBANTE_ALIAS, "field 'lblComprobanteAlias'");
        bajaAliasActivity.lblComprobanteCBU = (TextView) finder.findRequiredView(obj, R.id.F28_01_LBL_COMPROBANTE_CBU, "field 'lblComprobanteCBU'");
        bajaAliasActivity.lblComprobanteNroCuenta = (TextView) finder.findRequiredView(obj, R.id.F28_01_LBL_COMPROBANTE_NRO_CUENTA, "field 'lblComprobanteNroCuenta'");
        bajaAliasActivity.lblComprobanteTipoCuenta = (TextView) finder.findRequiredView(obj, R.id.F28_01_LBL_COMPROBANTE_TIPO_CUENTA, "field 'lblComprobanteTipoCuenta'");
        bajaAliasActivity.lblComprobanteTitular = (TextView) finder.findRequiredView(obj, R.id.F28_01_LBL_COMPROBANTE_TITULAR, "field 'lblComprobanteTitular'");
        bajaAliasActivity.lblComprobanteDocumento = (TextView) finder.findRequiredView(obj, R.id.F28_01_LBL_COMPROBANTE_DOCUMENTO, "field 'lblComprobanteDocumento'");
        bajaAliasActivity.lblComprobanteNroOperacion = (TextView) finder.findRequiredView(obj, R.id.F28_01_LBL_COMPROBANTE_NRO_OPERACION, "field 'lblComprobanteNroOperacion'");
        bajaAliasActivity.lblComprobanteFechaEjecucion = (TextView) finder.findRequiredView(obj, R.id.F28_01_LBL_COMPROBANTE_FECHA_EJECUCION, "field 'lblComprobanteFechaEjecucion'");
        bajaAliasActivity.btnVolver = (Button) finder.findRequiredView(obj, R.id.F28_01_BTN_COMPROBANTE_VOLVER, "field 'btnVolver'");
    }

    public static void reset(BajaAliasActivity bajaAliasActivity) {
        bajaAliasActivity.mControlPager = null;
        bajaAliasActivity.comprobanteOperacion = null;
        bajaAliasActivity.lblDataAlias = null;
        bajaAliasActivity.lblDataCBU = null;
        bajaAliasActivity.btnContinuar = null;
        bajaAliasActivity.lblDataNumeroCuenta = null;
        bajaAliasActivity.lblDataTipoCuenta = null;
        bajaAliasActivity.lblDataTitular = null;
        bajaAliasActivity.lblDataCUIT = null;
        bajaAliasActivity.lblComprobanteAlias = null;
        bajaAliasActivity.lblComprobanteCBU = null;
        bajaAliasActivity.lblComprobanteNroCuenta = null;
        bajaAliasActivity.lblComprobanteTipoCuenta = null;
        bajaAliasActivity.lblComprobanteTitular = null;
        bajaAliasActivity.lblComprobanteDocumento = null;
        bajaAliasActivity.lblComprobanteNroOperacion = null;
        bajaAliasActivity.lblComprobanteFechaEjecucion = null;
        bajaAliasActivity.btnVolver = null;
    }
}
