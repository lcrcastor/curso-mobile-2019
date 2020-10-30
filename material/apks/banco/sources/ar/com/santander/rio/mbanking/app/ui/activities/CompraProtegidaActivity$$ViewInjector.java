package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class CompraProtegidaActivity$$ViewInjector {
    public static void inject(Finder finder, CompraProtegidaActivity compraProtegidaActivity, Object obj) {
        compraProtegidaActivity.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.compra_protegida_activity_view_flipper, "field 'mControlPager'");
        compraProtegidaActivity.ll_solicitar_seguro = (LinearLayout) finder.findRequiredView(obj, R.id.ll_solicitar_seguro, "field 'll_solicitar_seguro'");
        compraProtegidaActivity.lbl_solicitar_seguro_importe_mesual = (TextView) finder.findRequiredView(obj, R.id.F27_11_lbl_data_importe_mensual, "field 'lbl_solicitar_seguro_importe_mesual'");
        compraProtegidaActivity.lbl_solicitar_seguro_suma_asegurada = (TextView) finder.findRequiredView(obj, R.id.F27_11_lbl_data_suma_asegurada, "field 'lbl_solicitar_seguro_suma_asegurada'");
        compraProtegidaActivity.lbl_solicitar_seguro_detalle = (TextView) finder.findRequiredView(obj, R.id.F27_11_link_detalle_cobertura, "field 'lbl_solicitar_seguro_detalle'");
        compraProtegidaActivity.btn_continuar_solicitar_seguro = (Button) finder.findRequiredView(obj, R.id.F27_11_btn_continuar, "field 'btn_continuar_solicitar_seguro'");
        compraProtegidaActivity.listViewTarjetas = (ListView) finder.findRequiredView(obj, R.id.F27_11_LNL_LISTA, "field 'listViewTarjetas'");
        compraProtegidaActivity.scrollSolicitar = (ScrollView) finder.findRequiredView(obj, R.id.F27_11_scrollView, "field 'scrollSolicitar'");
        compraProtegidaActivity.ll_tarjetas_aseguradas_solicitar = (LinearLayout) finder.findRequiredView(obj, R.id.ll_tarjetas_aseguradas, "field 'll_tarjetas_aseguradas_solicitar'");
        compraProtegidaActivity.lbl_contratcion_importe_mensual = (TextView) finder.findRequiredView(obj, R.id.F27_12_lbl_data_importe_mensual, "field 'lbl_contratcion_importe_mensual'");
        compraProtegidaActivity.lbl_contratcion_cobertura = (TextView) finder.findRequiredView(obj, R.id.F27_12_lbl_data_cobertura, "field 'lbl_contratcion_cobertura'");
        compraProtegidaActivity.lbl_contratcion_suma_asegurada = (TextView) finder.findRequiredView(obj, R.id.F27_12_lbl_data_suma_asegurada, "field 'lbl_contratcion_suma_asegurada'");
        compraProtegidaActivity.lbl_contratcion_medio_pago = (TextView) finder.findRequiredView(obj, R.id.F27_12_lbl_data_medio_pago, "field 'lbl_contratcion_medio_pago'");
        compraProtegidaActivity.lbl_contratcion_forma_pago = (TextView) finder.findRequiredView(obj, R.id.F27_12_lbl_data_medio_envio_poliza, "field 'lbl_contratcion_forma_pago'");
        compraProtegidaActivity.btn_continuar_contratacion = (Button) finder.findRequiredView(obj, R.id.f2ID_4061_SEGUROS_BOTN_CONTRATARAHORA, "field 'btn_continuar_contratacion'");
        compraProtegidaActivity.lbl_seleccionar_ocupacion = (TextView) finder.findRequiredView(obj, R.id.F27_12_lbl_seleccionar, "field 'lbl_seleccionar_ocupacion'");
        compraProtegidaActivity.lbl_contratcion_leyenda = (TextView) finder.findRequiredView(obj, R.id.F27_12_txt_leyenda2, "field 'lbl_contratcion_leyenda'");
        compraProtegidaActivity.row_Ocupacion = (RelativeLayout) finder.findRequiredView(obj, R.id.rowOcupacion, "field 'row_Ocupacion'");
        compraProtegidaActivity.lbl_confirmacion_importe_mensual = (TextView) finder.findRequiredView(obj, R.id.F27_13_lbl_data_importe_mensual, "field 'lbl_confirmacion_importe_mensual'");
        compraProtegidaActivity.lbl_confirmacion_medio_pago = (TextView) finder.findRequiredView(obj, R.id.F27_13_lbl_data_medio_pago, "field 'lbl_confirmacion_medio_pago'");
        compraProtegidaActivity.lbl_confirmacion_forma_pago = (TextView) finder.findRequiredView(obj, R.id.F27_13_lbl_data_forma_pago, "field 'lbl_confirmacion_forma_pago'");
        compraProtegidaActivity.lbl_confirmacion_cobertura = (TextView) finder.findRequiredView(obj, R.id.F27_13_lbl_data_cobertura, "field 'lbl_confirmacion_cobertura'");
        compraProtegidaActivity.lbl_confirmacion_suma_asegurada = (TextView) finder.findRequiredView(obj, R.id.F27_13_lbl_data_suma_asegurada, "field 'lbl_confirmacion_suma_asegurada'");
        compraProtegidaActivity.img_confirmacion_checkbox = (ImageView) finder.findRequiredView(obj, R.id.F27_13_img_checkbox, "field 'img_confirmacion_checkbox'");
        compraProtegidaActivity.img_confirmacion_terminos = (TextView) finder.findRequiredView(obj, R.id.F27_13_terminos, "field 'img_confirmacion_terminos'");
        compraProtegidaActivity.btn_confirmar_confirmacion = (Button) finder.findRequiredView(obj, R.id.F27_13_btn_confirmar, "field 'btn_confirmar_confirmacion'");
        compraProtegidaActivity.tl_lista_tarjetas_confirmar = (TableLayout) finder.findRequiredView(obj, R.id.F27_13_TBL_TARJETAS, "field 'tl_lista_tarjetas_confirmar'");
        compraProtegidaActivity.lbl_confirmacion_ocupacion = (TextView) finder.findRequiredView(obj, R.id.F27_13_lbl_data_ocupacion, "field 'lbl_confirmacion_ocupacion'");
        compraProtegidaActivity.lbl_comprobante_importe_mensual = (TextView) finder.findRequiredView(obj, R.id.F27_14_lbl_data_importe_mensual, "field 'lbl_comprobante_importe_mensual'");
        compraProtegidaActivity.lbl_comprobante_medio_pago = (TextView) finder.findRequiredView(obj, R.id.F27_14_lbl_data_medio_pago, "field 'lbl_comprobante_medio_pago'");
        compraProtegidaActivity.lbl_comprobante_cobertura = (TextView) finder.findRequiredView(obj, R.id.F27_14_lbl_data_cobertura, "field 'lbl_comprobante_cobertura'");
        compraProtegidaActivity.lbl_comprobante_suma_asegurada = (TextView) finder.findRequiredView(obj, R.id.F27_14_lbl_data_suma_asegurada, "field 'lbl_comprobante_suma_asegurada'");
        compraProtegidaActivity.lbl_comprobante_fecha = (TextView) finder.findRequiredView(obj, R.id.F27_14_lbl_data_fecha, "field 'lbl_comprobante_fecha'");
        compraProtegidaActivity.lbl_comprobante_poliza = (TextView) finder.findRequiredView(obj, R.id.F27_14_lbl_data_poliza, "field 'lbl_comprobante_poliza'");
        compraProtegidaActivity.lbl_comprobante_ocupacion = (TextView) finder.findRequiredView(obj, R.id.F27_14_lbl_data_ocupacion, "field 'lbl_comprobante_ocupacion'");
        compraProtegidaActivity.btn_volver_comprobante = (Button) finder.findRequiredView(obj, R.id.F27_14_btn_volver, "field 'btn_volver_comprobante'");
        compraProtegidaActivity.comprobanteContratacion = (ScrollView) finder.findRequiredView(obj, R.id.ComprobanteContratacion, "field 'comprobanteContratacion'");
        compraProtegidaActivity.tl_lista_tarjetas_comprobante = (TableLayout) finder.findRequiredView(obj, R.id.F27_14_TBL_TARJETAS, "field 'tl_lista_tarjetas_comprobante'");
        compraProtegidaActivity.txt_forma_Pago_comprobante = (TextView) finder.findRequiredView(obj, R.id.F27_14_lbl_data_forma_pago, "field 'txt_forma_Pago_comprobante'");
    }

    public static void reset(CompraProtegidaActivity compraProtegidaActivity) {
        compraProtegidaActivity.mControlPager = null;
        compraProtegidaActivity.ll_solicitar_seguro = null;
        compraProtegidaActivity.lbl_solicitar_seguro_importe_mesual = null;
        compraProtegidaActivity.lbl_solicitar_seguro_suma_asegurada = null;
        compraProtegidaActivity.lbl_solicitar_seguro_detalle = null;
        compraProtegidaActivity.btn_continuar_solicitar_seguro = null;
        compraProtegidaActivity.listViewTarjetas = null;
        compraProtegidaActivity.scrollSolicitar = null;
        compraProtegidaActivity.ll_tarjetas_aseguradas_solicitar = null;
        compraProtegidaActivity.lbl_contratcion_importe_mensual = null;
        compraProtegidaActivity.lbl_contratcion_cobertura = null;
        compraProtegidaActivity.lbl_contratcion_suma_asegurada = null;
        compraProtegidaActivity.lbl_contratcion_medio_pago = null;
        compraProtegidaActivity.lbl_contratcion_forma_pago = null;
        compraProtegidaActivity.btn_continuar_contratacion = null;
        compraProtegidaActivity.lbl_seleccionar_ocupacion = null;
        compraProtegidaActivity.lbl_contratcion_leyenda = null;
        compraProtegidaActivity.row_Ocupacion = null;
        compraProtegidaActivity.lbl_confirmacion_importe_mensual = null;
        compraProtegidaActivity.lbl_confirmacion_medio_pago = null;
        compraProtegidaActivity.lbl_confirmacion_forma_pago = null;
        compraProtegidaActivity.lbl_confirmacion_cobertura = null;
        compraProtegidaActivity.lbl_confirmacion_suma_asegurada = null;
        compraProtegidaActivity.img_confirmacion_checkbox = null;
        compraProtegidaActivity.img_confirmacion_terminos = null;
        compraProtegidaActivity.btn_confirmar_confirmacion = null;
        compraProtegidaActivity.tl_lista_tarjetas_confirmar = null;
        compraProtegidaActivity.lbl_confirmacion_ocupacion = null;
        compraProtegidaActivity.lbl_comprobante_importe_mensual = null;
        compraProtegidaActivity.lbl_comprobante_medio_pago = null;
        compraProtegidaActivity.lbl_comprobante_cobertura = null;
        compraProtegidaActivity.lbl_comprobante_suma_asegurada = null;
        compraProtegidaActivity.lbl_comprobante_fecha = null;
        compraProtegidaActivity.lbl_comprobante_poliza = null;
        compraProtegidaActivity.lbl_comprobante_ocupacion = null;
        compraProtegidaActivity.btn_volver_comprobante = null;
        compraProtegidaActivity.comprobanteContratacion = null;
        compraProtegidaActivity.tl_lista_tarjetas_comprobante = null;
        compraProtegidaActivity.txt_forma_Pago_comprobante = null;
    }
}
