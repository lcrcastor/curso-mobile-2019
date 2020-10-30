package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility;
import butterknife.ButterKnife.Finder;

public class TransferenciasFragment$$ViewInjector {
    public static void inject(Finder finder, final TransferenciasFragment transferenciasFragment, Object obj) {
        transferenciasFragment.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.transferencias_view_flipper, "field 'mControlPager'");
        transferenciasFragment.pantallaTransferencias = finder.findRequiredView(obj, R.id.transferencias, "field 'pantallaTransferencias'");
        transferenciasFragment.pantallaLimitesHorarios = finder.findRequiredView(obj, R.id.limites_horarios, "field 'pantallaLimitesHorarios'");
        transferenciasFragment.pantallaConfirmacionTransferencia = finder.findRequiredView(obj, R.id.confirmacion_transferencia, "field 'pantallaConfirmacionTransferencia'");
        transferenciasFragment.pantallaAgendaDestinatarios = finder.findRequiredView(obj, R.id.agenda_destinatarios, "field 'pantallaAgendaDestinatarios'");
        transferenciasFragment.pantallaTerminosCondiciones = finder.findRequiredView(obj, R.id.terminos_condiciones, "field 'pantallaTerminosCondiciones'");
        transferenciasFragment.pantallaComprobanteTransferencia = finder.findRequiredView(obj, R.id.comprobante_transferencia, "field 'pantallaComprobanteTransferencia'");
        transferenciasFragment.pantallaLegales = finder.findRequiredView(obj, R.id.legales, "field 'pantallaLegales'");
        transferenciasFragment.transferenciasView = finder.findRequiredView(obj, R.id.idTransferenciasView, "field 'transferenciasView'");
        transferenciasFragment.comprobanteTransferencia = finder.findRequiredView(obj, R.id.comprobanteTransferencia, "field 'comprobanteTransferencia'");
        transferenciasFragment.svComprobante = finder.findRequiredView(obj, R.id.svComprobante, "field 'svComprobante'");
        transferenciasFragment.txtSelectedCurrency = (TextView) finder.findRequiredView(obj, R.id.importeSeleccionado, "field 'txtSelectedCurrency'");
        transferenciasFragment.selectorImporteRLL = (RelativeLayout) finder.findRequiredView(obj, R.id.selectorImporte, "field 'selectorImporteRLL'");
        transferenciasFragment.imgSelectedCurrency = (ImageView) finder.findRequiredView(obj, R.id.imgImporteSeleccionado, "field 'imgSelectedCurrency'");
        View findRequiredView = finder.findRequiredView(obj, R.id.editTextImporte, "field 'etImporte' and method 'focusChangeAmount'");
        transferenciasFragment.etImporte = (NumericEditTextWithPrefixAccesibility) findRequiredView;
        findRequiredView.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                transferenciasFragment.focusChangeAmount(z);
            }
        });
        View findRequiredView2 = finder.findRequiredView(obj, R.id.editTextReferenciaConcepto, "field 'etReferenciaConcepto' and method 'focusChangeConcept'");
        transferenciasFragment.etReferenciaConcepto = (EditText) findRequiredView2;
        findRequiredView2.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                transferenciasFragment.focusChangeConcept(z);
            }
        });
        transferenciasFragment.textViewSeleccionar = (TextView) finder.findRequiredView(obj, R.id.textViewSeleccionar, "field 'textViewSeleccionar'");
        transferenciasFragment.linearLayoutTitularDivider = finder.findRequiredView(obj, R.id.linearLayoutTitularDivider, "field 'linearLayoutTitularDivider'");
        transferenciasFragment.linearLayoutTitular = finder.findRequiredView(obj, R.id.linearLayoutTitular, "field 'linearLayoutTitular'");
        transferenciasFragment.linearLayoutAlias = finder.findRequiredView(obj, R.id.linearLayoutAlias, "field 'linearLayoutAlias'");
        transferenciasFragment.linearLayoutTipoCuenta = finder.findRequiredView(obj, R.id.linearLayoutTipoCuenta, "field 'linearLayoutTipoCuenta'");
        transferenciasFragment.linearLayoutTipo = finder.findRequiredView(obj, R.id.linearLayoutTipo, "field 'linearLayoutTipo'");
        transferenciasFragment.linearLayoutNumero = finder.findRequiredView(obj, R.id.linearLayoutNumero, "field 'linearLayoutNumero'");
        transferenciasFragment.linearLayoutCUIT = finder.findRequiredView(obj, R.id.linearLayoutCUIT, "field 'linearLayoutCUIT'");
        transferenciasFragment.linearLayoutBancoDestino = finder.findRequiredView(obj, R.id.linearLayoutBancoDestino, "field 'linearLayoutBancoDestino'");
        transferenciasFragment.linearLayoutCBU = finder.findRequiredView(obj, R.id.linearLayoutCBU, "field 'linearLayoutCBU'");
        transferenciasFragment.listaDestinatarios = (ListView) finder.findRequiredView(obj, R.id.listAgendaDestinatarios, "field 'listaDestinatarios'");
        transferenciasFragment.arrowright = (ImageView) finder.findRequiredView(obj, R.id.arrow_right, "field 'arrowright'");
    }

    public static void reset(TransferenciasFragment transferenciasFragment) {
        transferenciasFragment.mControlPager = null;
        transferenciasFragment.pantallaTransferencias = null;
        transferenciasFragment.pantallaLimitesHorarios = null;
        transferenciasFragment.pantallaConfirmacionTransferencia = null;
        transferenciasFragment.pantallaAgendaDestinatarios = null;
        transferenciasFragment.pantallaTerminosCondiciones = null;
        transferenciasFragment.pantallaComprobanteTransferencia = null;
        transferenciasFragment.pantallaLegales = null;
        transferenciasFragment.transferenciasView = null;
        transferenciasFragment.comprobanteTransferencia = null;
        transferenciasFragment.svComprobante = null;
        transferenciasFragment.txtSelectedCurrency = null;
        transferenciasFragment.selectorImporteRLL = null;
        transferenciasFragment.imgSelectedCurrency = null;
        transferenciasFragment.etImporte = null;
        transferenciasFragment.etReferenciaConcepto = null;
        transferenciasFragment.textViewSeleccionar = null;
        transferenciasFragment.linearLayoutTitularDivider = null;
        transferenciasFragment.linearLayoutTitular = null;
        transferenciasFragment.linearLayoutAlias = null;
        transferenciasFragment.linearLayoutTipoCuenta = null;
        transferenciasFragment.linearLayoutTipo = null;
        transferenciasFragment.linearLayoutNumero = null;
        transferenciasFragment.linearLayoutCUIT = null;
        transferenciasFragment.linearLayoutBancoDestino = null;
        transferenciasFragment.linearLayoutCBU = null;
        transferenciasFragment.listaDestinatarios = null;
        transferenciasFragment.arrowright = null;
    }
}
