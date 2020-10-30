package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ScrollView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollListView;
import ar.com.santander.rio.mbanking.view.NumericEditText;
import butterknife.ButterKnife.Finder;

public class PagoTarjetasFragment$$ViewInjector {
    public static void inject(Finder finder, final PagoTarjetasFragment pagoTarjetasFragment, Object obj) {
        pagoTarjetasFragment.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.prepare_payment_form_view_flipper, "field 'mControlPager'");
        pagoTarjetasFragment.mCreditCardsList = (InfiniteScrollListView) finder.findRequiredView(obj, 16908298, "field 'mCreditCardsList'");
        pagoTarjetasFragment.pantallaPrepararPago = finder.findRequiredView(obj, R.id.preparar_pago, "field 'pantallaPrepararPago'");
        pagoTarjetasFragment.pantallaAyudaTarjeta = finder.findRequiredView(obj, R.id.ayuda_tarjeta, "field 'pantallaAyudaTarjeta'");
        pagoTarjetasFragment.pantallaDetalleTarjetaPagoTotalDolares = finder.findRequiredView(obj, R.id.detalle_tarjeta_pago_total_dolares, "field 'pantallaDetalleTarjetaPagoTotalDolares'");
        pagoTarjetasFragment.pantallaDetalleTarjetaPagoTotalPesos = finder.findRequiredView(obj, R.id.detalle_tarjeta_pago_total_pesos, "field 'pantallaDetalleTarjetaPagoTotalPesos'");
        pagoTarjetasFragment.pantallaConfirmarStopDebit = finder.findRequiredView(obj, R.id.confirmar_stop_debit, "field 'pantallaConfirmarStopDebit'");
        pagoTarjetasFragment.pantallaComprobanteStopDebit = finder.findRequiredView(obj, R.id.comprobante_stop_debit, "field 'pantallaComprobanteStopDebit'");
        pagoTarjetasFragment.pantallaConfirmarPago = finder.findRequiredView(obj, R.id.confirmar_pago, "field 'pantallaConfirmarPago'");
        pagoTarjetasFragment.pantallaComprobantePago = finder.findRequiredView(obj, R.id.comprobante_pago, "field 'pantallaComprobantePago'");
        pagoTarjetasFragment.comprobanteStopDebit = finder.findRequiredView(obj, R.id.comprobanteStopDebit, "field 'comprobanteStopDebit'");
        pagoTarjetasFragment.svComprobanteStopDebit = (ScrollView) finder.findRequiredView(obj, R.id.svComprobanteStopDebit, "field 'svComprobanteStopDebit'");
        pagoTarjetasFragment.svComprobantePagoTarjeta = (ScrollView) finder.findRequiredView(obj, R.id.svComprobantePagoTarjeta, "field 'svComprobantePagoTarjeta'");
        View findRequiredView = finder.findRequiredView(obj, R.id.prepare_payment_form_amount_in_dollars_editable_content, "field 'etValueDollars' and method 'onFocusValueDollars'");
        pagoTarjetasFragment.etValueDollars = (NumericEditText) findRequiredView;
        findRequiredView.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                pagoTarjetasFragment.onFocusValueDollars(z);
            }
        });
        View findRequiredView2 = finder.findRequiredView(obj, R.id.prepare_payment_form_amount_in_pesos_editable_content, "field 'etValuePesos' and method 'onFocusValuePesos'");
        pagoTarjetasFragment.etValuePesos = (NumericEditText) findRequiredView2;
        findRequiredView2.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                pagoTarjetasFragment.onFocusValuePesos(z);
            }
        });
    }

    public static void reset(PagoTarjetasFragment pagoTarjetasFragment) {
        pagoTarjetasFragment.mControlPager = null;
        pagoTarjetasFragment.mCreditCardsList = null;
        pagoTarjetasFragment.pantallaPrepararPago = null;
        pagoTarjetasFragment.pantallaAyudaTarjeta = null;
        pagoTarjetasFragment.pantallaDetalleTarjetaPagoTotalDolares = null;
        pagoTarjetasFragment.pantallaDetalleTarjetaPagoTotalPesos = null;
        pagoTarjetasFragment.pantallaConfirmarStopDebit = null;
        pagoTarjetasFragment.pantallaComprobanteStopDebit = null;
        pagoTarjetasFragment.pantallaConfirmarPago = null;
        pagoTarjetasFragment.pantallaComprobantePago = null;
        pagoTarjetasFragment.comprobanteStopDebit = null;
        pagoTarjetasFragment.svComprobanteStopDebit = null;
        pagoTarjetasFragment.svComprobantePagoTarjeta = null;
        pagoTarjetasFragment.etValueDollars = null;
        pagoTarjetasFragment.etValuePesos = null;
    }
}
