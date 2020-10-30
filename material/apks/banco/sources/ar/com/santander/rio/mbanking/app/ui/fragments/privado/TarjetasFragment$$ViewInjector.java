package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.AmountView;
import butterknife.ButterKnife.Finder;

public class TarjetasFragment$$ViewInjector {
    public static void inject(Finder finder, TarjetasFragment tarjetasFragment, Object obj) {
        tarjetasFragment.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.tarjetas_view_flipper, "field 'mControlPager'");
        tarjetasFragment.pantallaTarjetas = finder.findRequiredView(obj, R.id.tarjetas_main, "field 'pantallaTarjetas'");
        tarjetasFragment.pantallaTarjetasLimitesDisponibles = finder.findRequiredView(obj, R.id.limites_disponibles, "field 'pantallaTarjetasLimitesDisponibles'");
        tarjetasFragment.pantallaTarjetasUltimosConsumos = finder.findRequiredView(obj, R.id.ultimos_consumos, "field 'pantallaTarjetasUltimosConsumos'");
        tarjetasFragment.pantallaTarjetasUltimoResumen = finder.findRequiredView(obj, R.id.ultimo_resumen, "field 'pantallaTarjetasUltimoResumen'");
        tarjetasFragment.pantallaTarjetasDetalleConsumo = finder.findRequiredView(obj, R.id.detalle_consumo, "field 'pantallaTarjetasDetalleConsumo'");
        tarjetasFragment.pantallaTarjetasTerminosCondiciones = finder.findRequiredView(obj, R.id.tarjetas_terminos_condiciones, "field 'pantallaTarjetasTerminosCondiciones'");
        tarjetasFragment.tarjetasView = finder.findRequiredView(obj, R.id.idTarjetasView, "field 'tarjetasView'");
        tarjetasFragment.main_total_consumos = (AmountView) finder.findRequiredView(obj, R.id.main_total_consumos, "field 'main_total_consumos'");
        tarjetasFragment.main_total_consumos_2 = (AmountView) finder.findRequiredView(obj, R.id.main_total_consumos_2, "field 'main_total_consumos_2'");
        tarjetasFragment.pendientes_total_consumos = (AmountView) finder.findRequiredView(obj, R.id.pendientes_total_consumos, "field 'pendientes_total_consumos'");
        tarjetasFragment.pendientes_total_consumos_2 = (AmountView) finder.findRequiredView(obj, R.id.pendientes_total_consumos_2, "field 'pendientes_total_consumos_2'");
        tarjetasFragment.main_total_autorizaciones = (AmountView) finder.findRequiredView(obj, R.id.main_total_autorizaciones, "field 'main_total_autorizaciones'");
        tarjetasFragment.main_total_autorizaciones_2 = (AmountView) finder.findRequiredView(obj, R.id.main_total_autorizaciones_2, "field 'main_total_autorizaciones_2'");
        tarjetasFragment.tvTextCardSelected = (TextView) finder.findRequiredView(obj, R.id.text_tarjeta, "field 'tvTextCardSelected'");
        tarjetasFragment.tvProximoCierre = (TextView) finder.findRequiredView(obj, R.id.proximo_cierre, "field 'tvProximoCierre'");
        tarjetasFragment.tvProximoVencimiento = (TextView) finder.findRequiredView(obj, R.id.proximo_vencimiento, "field 'tvProximoVencimiento'");
        tarjetasFragment.autorizPesos = (TextView) finder.findRequiredView(obj, R.id.total_autorizaciones_pesos, "field 'autorizPesos'");
    }

    public static void reset(TarjetasFragment tarjetasFragment) {
        tarjetasFragment.mControlPager = null;
        tarjetasFragment.pantallaTarjetas = null;
        tarjetasFragment.pantallaTarjetasLimitesDisponibles = null;
        tarjetasFragment.pantallaTarjetasUltimosConsumos = null;
        tarjetasFragment.pantallaTarjetasUltimoResumen = null;
        tarjetasFragment.pantallaTarjetasDetalleConsumo = null;
        tarjetasFragment.pantallaTarjetasTerminosCondiciones = null;
        tarjetasFragment.tarjetasView = null;
        tarjetasFragment.main_total_consumos = null;
        tarjetasFragment.main_total_consumos_2 = null;
        tarjetasFragment.pendientes_total_consumos = null;
        tarjetasFragment.pendientes_total_consumos_2 = null;
        tarjetasFragment.main_total_autorizaciones = null;
        tarjetasFragment.main_total_autorizaciones_2 = null;
        tarjetasFragment.tvTextCardSelected = null;
        tarjetasFragment.tvProximoCierre = null;
        tarjetasFragment.tvProximoVencimiento = null;
        tarjetasFragment.autorizPesos = null;
    }
}
