package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class EditarEliminarDestinatarioTransferenciaActivity$$ViewInjector {
    public static void inject(Finder finder, EditarEliminarDestinatarioTransferenciaActivity editarEliminarDestinatarioTransferenciaActivity, Object obj) {
        editarEliminarDestinatarioTransferenciaActivity.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.editar_destinatario_view_flipper, "field 'mControlPager'");
        editarEliminarDestinatarioTransferenciaActivity.comprobanteOperacion = (ScrollView) finder.findRequiredView(obj, R.id.F23_22_scrollView, "field 'comprobanteOperacion'");
        editarEliminarDestinatarioTransferenciaActivity.inpRecordatorio = (EditText) finder.findRequiredView(obj, R.id.F23_20_INP_RECORDATORIO, "field 'inpRecordatorio'");
        editarEliminarDestinatarioTransferenciaActivity.inpEmail = (EditText) finder.findRequiredView(obj, R.id.F23_20_INP_EMAIL, "field 'inpEmail'");
        editarEliminarDestinatarioTransferenciaActivity.lblTitleEdit = (TextView) finder.findRequiredView(obj, R.id.F23_20_LBL_TITLE, "field 'lblTitleEdit'");
        editarEliminarDestinatarioTransferenciaActivity.lblTitularEdit = (TextView) finder.findRequiredView(obj, R.id.F23_20_LBL_DATA_TITULAR, "field 'lblTitularEdit'");
        editarEliminarDestinatarioTransferenciaActivity.titleAlias = (TextView) finder.findRequiredView(obj, R.id.F23_20_LBL_ALIAS, "field 'titleAlias'");
        editarEliminarDestinatarioTransferenciaActivity.lblAlias = (TextView) finder.findRequiredView(obj, R.id.F23_20_LBL_DATA_ALIAS, "field 'lblAlias'");
        editarEliminarDestinatarioTransferenciaActivity.lblCuentasEdit = (TextView) finder.findRequiredView(obj, R.id.F23_20_LBL_CUIT_CUIL_HEADER, "field 'lblCuentasEdit'");
        editarEliminarDestinatarioTransferenciaActivity.lblCuitCuentasEdit = (TextView) finder.findRequiredView(obj, R.id.F23_20_LBL_DATA_CUIT_CUIL_HEADER, "field 'lblCuitCuentasEdit'");
        editarEliminarDestinatarioTransferenciaActivity.txtCUITVerificar = (TextView) finder.findRequiredView(obj, R.id.F23_20_LBL_DATA_CUIT, "field 'txtCUITVerificar'");
        editarEliminarDestinatarioTransferenciaActivity.cbuRowEdit = (LinearLayout) finder.findRequiredView(obj, R.id.F23_20_LNL_ROW_CBU, "field 'cbuRowEdit'");
        editarEliminarDestinatarioTransferenciaActivity.lblCbuEdit = (TextView) finder.findRequiredView(obj, R.id.F23_20_LBL_DATA_CBU, "field 'lblCbuEdit'");
        editarEliminarDestinatarioTransferenciaActivity.rowCUITVerificar = (LinearLayout) finder.findRequiredView(obj, R.id.F23_20_LNL_ROW_CUIT, "field 'rowCUITVerificar'");
        editarEliminarDestinatarioTransferenciaActivity.lblBancoEdit = (TextView) finder.findRequiredView(obj, R.id.F23_20_LBL_DATA_BANCO, "field 'lblBancoEdit'");
        editarEliminarDestinatarioTransferenciaActivity.numeroCuentaRowEdit = (LinearLayout) finder.findRequiredView(obj, R.id.F23_20_LNL_ROW_NUMERO_CUENTA, "field 'numeroCuentaRowEdit'");
        editarEliminarDestinatarioTransferenciaActivity.lblNumeroCuentaEdit = (TextView) finder.findRequiredView(obj, R.id.F23_20_LBL_DATA_NUMERO_CUENTA, "field 'lblNumeroCuentaEdit'");
        editarEliminarDestinatarioTransferenciaActivity.lblNumeroCuentaDestinoEdit = (TextView) finder.findRequiredView(obj, R.id.F23_20_LBL_NUMERO_CUENTA, "field 'lblNumeroCuentaDestinoEdit'");
        editarEliminarDestinatarioTransferenciaActivity.tipoCuentaRowEdit = (LinearLayout) finder.findRequiredView(obj, R.id.F23_20_LNL_ROW_TIPO_CUENTA, "field 'tipoCuentaRowEdit'");
        editarEliminarDestinatarioTransferenciaActivity.lblTipoCuentaEdit = (TextView) finder.findRequiredView(obj, R.id.F23_20_LBL_DATA_TIPO_CUENTA, "field 'lblTipoCuentaEdit'");
        editarEliminarDestinatarioTransferenciaActivity.lblTitleConfirmar = (TextView) finder.findRequiredView(obj, R.id.F23_21_LBL_TITLE, "field 'lblTitleConfirmar'");
        editarEliminarDestinatarioTransferenciaActivity.tableLayoutConfirmar = (TableLayout) finder.findRequiredView(obj, R.id.F23_21_TABLE, "field 'tableLayoutConfirmar'");
        editarEliminarDestinatarioTransferenciaActivity.btnGuardarConfirmar = (Button) finder.findRequiredView(obj, R.id.F23_21_BTN_GUARDAR, "field 'btnGuardarConfirmar'");
        editarEliminarDestinatarioTransferenciaActivity.lblTitleComprobante = (TextView) finder.findRequiredView(obj, R.id.F23_22_LBL_TITLE, "field 'lblTitleComprobante'");
        editarEliminarDestinatarioTransferenciaActivity.tableLayoutComprobante = (TableLayout) finder.findRequiredView(obj, R.id.F23_22_TABLE, "field 'tableLayoutComprobante'");
        editarEliminarDestinatarioTransferenciaActivity.btnTransferirComprobante = (Button) finder.findRequiredView(obj, R.id.F23_22_BTN_TRANSFERIR, "field 'btnTransferirComprobante'");
        editarEliminarDestinatarioTransferenciaActivity.btnAgendaComprobante = (Button) finder.findRequiredView(obj, R.id.F23_22_BTN_AGENDA, "field 'btnAgendaComprobante'");
        editarEliminarDestinatarioTransferenciaActivity.btnContinuarEdit = (Button) finder.findRequiredView(obj, R.id.F23_20_BTN_CONTINUAR, "field 'btnContinuarEdit'");
    }

    public static void reset(EditarEliminarDestinatarioTransferenciaActivity editarEliminarDestinatarioTransferenciaActivity) {
        editarEliminarDestinatarioTransferenciaActivity.mControlPager = null;
        editarEliminarDestinatarioTransferenciaActivity.comprobanteOperacion = null;
        editarEliminarDestinatarioTransferenciaActivity.inpRecordatorio = null;
        editarEliminarDestinatarioTransferenciaActivity.inpEmail = null;
        editarEliminarDestinatarioTransferenciaActivity.lblTitleEdit = null;
        editarEliminarDestinatarioTransferenciaActivity.lblTitularEdit = null;
        editarEliminarDestinatarioTransferenciaActivity.titleAlias = null;
        editarEliminarDestinatarioTransferenciaActivity.lblAlias = null;
        editarEliminarDestinatarioTransferenciaActivity.lblCuentasEdit = null;
        editarEliminarDestinatarioTransferenciaActivity.lblCuitCuentasEdit = null;
        editarEliminarDestinatarioTransferenciaActivity.txtCUITVerificar = null;
        editarEliminarDestinatarioTransferenciaActivity.cbuRowEdit = null;
        editarEliminarDestinatarioTransferenciaActivity.lblCbuEdit = null;
        editarEliminarDestinatarioTransferenciaActivity.rowCUITVerificar = null;
        editarEliminarDestinatarioTransferenciaActivity.lblBancoEdit = null;
        editarEliminarDestinatarioTransferenciaActivity.numeroCuentaRowEdit = null;
        editarEliminarDestinatarioTransferenciaActivity.lblNumeroCuentaEdit = null;
        editarEliminarDestinatarioTransferenciaActivity.lblNumeroCuentaDestinoEdit = null;
        editarEliminarDestinatarioTransferenciaActivity.tipoCuentaRowEdit = null;
        editarEliminarDestinatarioTransferenciaActivity.lblTipoCuentaEdit = null;
        editarEliminarDestinatarioTransferenciaActivity.lblTitleConfirmar = null;
        editarEliminarDestinatarioTransferenciaActivity.tableLayoutConfirmar = null;
        editarEliminarDestinatarioTransferenciaActivity.btnGuardarConfirmar = null;
        editarEliminarDestinatarioTransferenciaActivity.lblTitleComprobante = null;
        editarEliminarDestinatarioTransferenciaActivity.tableLayoutComprobante = null;
        editarEliminarDestinatarioTransferenciaActivity.btnTransferirComprobante = null;
        editarEliminarDestinatarioTransferenciaActivity.btnAgendaComprobante = null;
        editarEliminarDestinatarioTransferenciaActivity.btnContinuarEdit = null;
    }
}
