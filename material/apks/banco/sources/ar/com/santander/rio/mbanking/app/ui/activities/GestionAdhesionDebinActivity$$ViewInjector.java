package ar.com.santander.rio.mbanking.app.ui.activities;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class GestionAdhesionDebinActivity$$ViewInjector {
    public static void inject(Finder finder, GestionAdhesionDebinActivity gestionAdhesionDebinActivity, Object obj) {
        gestionAdhesionDebinActivity.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.gestion_adhesion_debin_view_flipper, "field 'mControlPager'");
        gestionAdhesionDebinActivity.comprobanteOperacion = (ScrollView) finder.findRequiredView(obj, R.id.F32_27_SCROLL_VIEW, "field 'comprobanteOperacion'");
        gestionAdhesionDebinActivity.mRecyclerView = (RecyclerView) finder.findRequiredView(obj, R.id.F32_02_LST_RECYCLERVIEW, "field 'mRecyclerView'");
        gestionAdhesionDebinActivity.lbl_confirmar_title = (TextView) finder.findRequiredView(obj, R.id.F32_26_txt_title, "field 'lbl_confirmar_title'");
        gestionAdhesionDebinActivity.lbl_confirmar_cuentaAdhesion = (TextView) finder.findRequiredView(obj, R.id.F32_26_lbl_cuentaAdhesion, "field 'lbl_confirmar_cuentaAdhesion'");
        gestionAdhesionDebinActivity.lbl_data_tipoCuentaAdhesion = (TextView) finder.findRequiredView(obj, R.id.F32_26_lbl_data_cuentaAdhesion, "field 'lbl_data_tipoCuentaAdhesion'");
        gestionAdhesionDebinActivity.lbl_data_confirmacion_numeroCuentaAdhesion = (TextView) finder.findRequiredView(obj, R.id.F32_26_lbl_data_nroCuenta, "field 'lbl_data_confirmacion_numeroCuentaAdhesion'");
        gestionAdhesionDebinActivity.btnConfirmarn = (TextView) finder.findRequiredView(obj, R.id.F32_26_btn_confirmar, "field 'btnConfirmarn'");
        gestionAdhesionDebinActivity.lbl_comprobante_title = (TextView) finder.findRequiredView(obj, R.id.F32_27_txt_title, "field 'lbl_comprobante_title'");
        gestionAdhesionDebinActivity.lbl_comprobante_operacion_cuenta = (TextView) finder.findRequiredView(obj, R.id.F32_27_lbl_cuentaAdhesion, "field 'lbl_comprobante_operacion_cuenta'");
        gestionAdhesionDebinActivity.lbl_data_comprobante_tipoCuentaAdhesion = (TextView) finder.findRequiredView(obj, R.id.F32_27_lbl_data_cuentaAdhesion, "field 'lbl_data_comprobante_tipoCuentaAdhesion'");
        gestionAdhesionDebinActivity.lbl_data_comprobante_numeroCuentaAdhesion = (TextView) finder.findRequiredView(obj, R.id.F32_27_lbl_data_nroCuenta, "field 'lbl_data_comprobante_numeroCuentaAdhesion'");
        gestionAdhesionDebinActivity.lbl_data_comprobante_fechaOperacion = (TextView) finder.findRequiredView(obj, R.id.F32_27_lbl_data_fechaOperacion, "field 'lbl_data_comprobante_fechaOperacion'");
        gestionAdhesionDebinActivity.lbl_data_comprobante_nroOperacion = (TextView) finder.findRequiredView(obj, R.id.F32_27_lbl_data_nroComprobante, "field 'lbl_data_comprobante_nroOperacion'");
        gestionAdhesionDebinActivity.lbl_comprobante_terminos_legales = (TextView) finder.findRequiredView(obj, R.id.F32_27_lbl_terminosLegales, "field 'lbl_comprobante_terminos_legales'");
        gestionAdhesionDebinActivity.btnVolverComprobante = (Button) finder.findRequiredView(obj, R.id.F32_27_btn_volver, "field 'btnVolverComprobante'");
    }

    public static void reset(GestionAdhesionDebinActivity gestionAdhesionDebinActivity) {
        gestionAdhesionDebinActivity.mControlPager = null;
        gestionAdhesionDebinActivity.comprobanteOperacion = null;
        gestionAdhesionDebinActivity.mRecyclerView = null;
        gestionAdhesionDebinActivity.lbl_confirmar_title = null;
        gestionAdhesionDebinActivity.lbl_confirmar_cuentaAdhesion = null;
        gestionAdhesionDebinActivity.lbl_data_tipoCuentaAdhesion = null;
        gestionAdhesionDebinActivity.lbl_data_confirmacion_numeroCuentaAdhesion = null;
        gestionAdhesionDebinActivity.btnConfirmarn = null;
        gestionAdhesionDebinActivity.lbl_comprobante_title = null;
        gestionAdhesionDebinActivity.lbl_comprobante_operacion_cuenta = null;
        gestionAdhesionDebinActivity.lbl_data_comprobante_tipoCuentaAdhesion = null;
        gestionAdhesionDebinActivity.lbl_data_comprobante_numeroCuentaAdhesion = null;
        gestionAdhesionDebinActivity.lbl_data_comprobante_fechaOperacion = null;
        gestionAdhesionDebinActivity.lbl_data_comprobante_nroOperacion = null;
        gestionAdhesionDebinActivity.lbl_comprobante_terminos_legales = null;
        gestionAdhesionDebinActivity.btnVolverComprobante = null;
    }
}
