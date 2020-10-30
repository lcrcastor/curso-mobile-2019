package ar.com.santander.rio.mbanking.app.ui.activities.recargasubecomprobantepago;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class RecargaSubeComprobantePagoActivity$$ViewInjector {
    public static void inject(Finder finder, RecargaSubeComprobantePagoActivity recargaSubeComprobantePagoActivity, Object obj) {
        recargaSubeComprobantePagoActivity.tvMonto = (TextView) finder.findRequiredView(obj, R.id.tv_monto, "field 'tvMonto'");
        recargaSubeComprobantePagoActivity.tvTarjeta = (TextView) finder.findRequiredView(obj, R.id.tv_tarjeta, "field 'tvTarjeta'");
        recargaSubeComprobantePagoActivity.tvTarjetaAlias = (TextView) finder.findRequiredView(obj, R.id.tv_tarjeta_alias, "field 'tvTarjetaAlias'");
        recargaSubeComprobantePagoActivity.tvMedioPago = (TextView) finder.findRequiredView(obj, R.id.tv_medio_pago, "field 'tvMedioPago'");
        recargaSubeComprobantePagoActivity.tvFecha = (TextView) finder.findRequiredView(obj, R.id.tv_fecha, "field 'tvFecha'");
        recargaSubeComprobantePagoActivity.comprobante = (TextView) finder.findRequiredView(obj, R.id.nro_comprobante, "field 'comprobante'");
        recargaSubeComprobantePagoActivity.tvNroComprobante = (TextView) finder.findRequiredView(obj, R.id.tv_nro_comprobante, "field 'tvNroComprobante'");
        recargaSubeComprobantePagoActivity.tvTerminosCondiciones = (TextView) finder.findRequiredView(obj, R.id.tv_terminos_condiciones, "field 'tvTerminosCondiciones'");
        recargaSubeComprobantePagoActivity.imgLogo = (ImageView) finder.findRequiredView(obj, R.id.logo_pdf, "field 'imgLogo'");
        recargaSubeComprobantePagoActivity.linearLayout = (LinearLayout) finder.findRequiredView(obj, R.id.layout_buttons, "field 'linearLayout'");
        recargaSubeComprobantePagoActivity.btnRealizarRecarga = (TextView) finder.findRequiredView(obj, R.id.btn_realizar_recarga, "field 'btnRealizarRecarga'");
        recargaSubeComprobantePagoActivity.btnVolverHome = (Button) finder.findRequiredView(obj, R.id.btn_volver_home, "field 'btnVolverHome'");
        recargaSubeComprobantePagoActivity.layoutPdf = (LinearLayout) finder.findRequiredView(obj, R.id.layout_pdf, "field 'layoutPdf'");
        recargaSubeComprobantePagoActivity.cardShare = (LinearLayout) finder.findRequiredView(obj, R.id.layout_share, "field 'cardShare'");
        recargaSubeComprobantePagoActivity.cardDownload = (LinearLayout) finder.findRequiredView(obj, R.id.layout_download, "field 'cardDownload'");
    }

    public static void reset(RecargaSubeComprobantePagoActivity recargaSubeComprobantePagoActivity) {
        recargaSubeComprobantePagoActivity.tvMonto = null;
        recargaSubeComprobantePagoActivity.tvTarjeta = null;
        recargaSubeComprobantePagoActivity.tvTarjetaAlias = null;
        recargaSubeComprobantePagoActivity.tvMedioPago = null;
        recargaSubeComprobantePagoActivity.tvFecha = null;
        recargaSubeComprobantePagoActivity.comprobante = null;
        recargaSubeComprobantePagoActivity.tvNroComprobante = null;
        recargaSubeComprobantePagoActivity.tvTerminosCondiciones = null;
        recargaSubeComprobantePagoActivity.imgLogo = null;
        recargaSubeComprobantePagoActivity.linearLayout = null;
        recargaSubeComprobantePagoActivity.btnRealizarRecarga = null;
        recargaSubeComprobantePagoActivity.btnVolverHome = null;
        recargaSubeComprobantePagoActivity.layoutPdf = null;
        recargaSubeComprobantePagoActivity.cardShare = null;
        recargaSubeComprobantePagoActivity.cardDownload = null;
    }
}
