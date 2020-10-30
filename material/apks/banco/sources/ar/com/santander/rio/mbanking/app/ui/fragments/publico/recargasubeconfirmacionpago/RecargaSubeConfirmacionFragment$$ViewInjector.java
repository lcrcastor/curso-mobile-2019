package ar.com.santander.rio.mbanking.app.ui.fragments.publico.recargasubeconfirmacionpago;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class RecargaSubeConfirmacionFragment$$ViewInjector {
    public static void inject(Finder finder, final RecargaSubeConfirmacionFragment recargaSubeConfirmacionFragment, Object obj) {
        recargaSubeConfirmacionFragment.tvImporteMonto = (TextView) finder.findRequiredView(obj, R.id.tv_importe_monto, "field 'tvImporteMonto'");
        recargaSubeConfirmacionFragment.tvTarjeta = (TextView) finder.findRequiredView(obj, R.id.tv_tarjeta, "field 'tvTarjeta'");
        recargaSubeConfirmacionFragment.tvTarjetaNro = (TextView) finder.findRequiredView(obj, R.id.tv_tarjeta_nro, "field 'tvTarjetaNro'");
        recargaSubeConfirmacionFragment.tvMedioPago = (TextView) finder.findRequiredView(obj, R.id.tv_medio_pago, "field 'tvMedioPago'");
        View findRequiredView = finder.findRequiredView(obj, R.id.btn_volver_home, "field 'btnVolverHome' and method 'onViewClicked'");
        recargaSubeConfirmacionFragment.btnVolverHome = (Button) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                recargaSubeConfirmacionFragment.onViewClicked(view);
            }
        });
        recargaSubeConfirmacionFragment.tvMontoPdf = (TextView) finder.findRequiredView(obj, R.id.tv_monto_pdf, "field 'tvMontoPdf'");
        recargaSubeConfirmacionFragment.tvTarjetaPdf = (TextView) finder.findRequiredView(obj, R.id.tv_tarjeta_pdf, "field 'tvTarjetaPdf'");
        recargaSubeConfirmacionFragment.tvMedioPagoPdf = (TextView) finder.findRequiredView(obj, R.id.tv_medio_pago_pdf, "field 'tvMedioPagoPdf'");
        recargaSubeConfirmacionFragment.tvFechaPdf = (TextView) finder.findRequiredView(obj, R.id.tv_fecha_pdf, "field 'tvFechaPdf'");
        recargaSubeConfirmacionFragment.tvNroComprobantePdf = (TextView) finder.findRequiredView(obj, R.id.tv_nro_comprobante_pdf, "field 'tvNroComprobantePdf'");
        recargaSubeConfirmacionFragment.tvTerminosCondicionesPdf = (TextView) finder.findRequiredView(obj, R.id.tv_terminos_condiciones_pdf, "field 'tvTerminosCondicionesPdf'");
        recargaSubeConfirmacionFragment.layoutPdf = (LinearLayout) finder.findRequiredView(obj, R.id.layout_pdf, "field 'layoutPdf'");
        recargaSubeConfirmacionFragment.closeView = finder.findRequiredView(obj, R.id.close_animation_view, "field 'closeView'");
        recargaSubeConfirmacionFragment.iconOk = (ImageView) finder.findRequiredView(obj, R.id.icon_ok, "field 'iconOk'");
        recargaSubeConfirmacionFragment.separatorView = finder.findRequiredView(obj, R.id.separator_view, "field 'separatorView'");
        recargaSubeConfirmacionFragment.actionbarView = finder.findRequiredView(obj, R.id.recarga_sube_confirmada_actionbar, "field 'actionbarView'");
        View findRequiredView2 = finder.findRequiredView(obj, R.id.layout_sube_app, "field 'layoutSubeApp' and method 'onViewClicked'");
        recargaSubeConfirmacionFragment.layoutSubeApp = (LinearLayout) findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                recargaSubeConfirmacionFragment.onViewClicked(view);
            }
        });
        recargaSubeConfirmacionFragment.tvLegendCredit = (TextView) finder.findRequiredView(obj, R.id.tv_legend_credit, "field 'tvLegendCredit'");
        finder.findRequiredView(obj, R.id.layout_receipt, "method 'onViewClicked'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                recargaSubeConfirmacionFragment.onViewClicked(view);
            }
        });
        finder.findRequiredView(obj, R.id.layout_download, "method 'onViewClicked'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                recargaSubeConfirmacionFragment.onViewClicked(view);
            }
        });
        finder.findRequiredView(obj, R.id.layout_share, "method 'onViewClicked'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                recargaSubeConfirmacionFragment.onViewClicked(view);
            }
        });
        finder.findRequiredView(obj, R.id.btn_realizar_recarga, "method 'onViewClicked'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                recargaSubeConfirmacionFragment.onViewClicked(view);
            }
        });
    }

    public static void reset(RecargaSubeConfirmacionFragment recargaSubeConfirmacionFragment) {
        recargaSubeConfirmacionFragment.tvImporteMonto = null;
        recargaSubeConfirmacionFragment.tvTarjeta = null;
        recargaSubeConfirmacionFragment.tvTarjetaNro = null;
        recargaSubeConfirmacionFragment.tvMedioPago = null;
        recargaSubeConfirmacionFragment.btnVolverHome = null;
        recargaSubeConfirmacionFragment.tvMontoPdf = null;
        recargaSubeConfirmacionFragment.tvTarjetaPdf = null;
        recargaSubeConfirmacionFragment.tvMedioPagoPdf = null;
        recargaSubeConfirmacionFragment.tvFechaPdf = null;
        recargaSubeConfirmacionFragment.tvNroComprobantePdf = null;
        recargaSubeConfirmacionFragment.tvTerminosCondicionesPdf = null;
        recargaSubeConfirmacionFragment.layoutPdf = null;
        recargaSubeConfirmacionFragment.closeView = null;
        recargaSubeConfirmacionFragment.iconOk = null;
        recargaSubeConfirmacionFragment.separatorView = null;
        recargaSubeConfirmacionFragment.actionbarView = null;
        recargaSubeConfirmacionFragment.layoutSubeApp = null;
        recargaSubeConfirmacionFragment.tvLegendCredit = null;
    }
}
