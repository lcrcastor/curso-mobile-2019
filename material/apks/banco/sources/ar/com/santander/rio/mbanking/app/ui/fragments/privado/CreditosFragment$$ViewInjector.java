package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class CreditosFragment$$ViewInjector {
    public static void inject(Finder finder, CreditosFragment creditosFragment, Object obj) {
        creditosFragment.leyendaPrestamoUvaConfirmar = (TextView) finder.findRequiredView(obj, R.id.creditoPage3LeyendaPrestamoUva, "field 'leyendaPrestamoUvaConfirmar'");
        creditosFragment.leyendaPrestamoUvaComprobante = (TextView) finder.findRequiredView(obj, R.id.creditoPage4LeyendaPrestamoUva, "field 'leyendaPrestamoUvaComprobante'");
        creditosFragment.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.vgCreditos, "field 'mControlPager'");
        creditosFragment.vgWrapperSimulacion = (ViewGroup) finder.findRequiredView(obj, R.id.vgWrapperSimulacion, "field 'vgWrapperSimulacion'");
        creditosFragment.creditosView = (LinearLayout) finder.findRequiredView(obj, R.id.idCreditosView, "field 'creditosView'");
        creditosFragment.continuar = (Button) finder.findRequiredView(obj, R.id.f111creditoscontinuar, "field 'continuar'");
        creditosFragment.confirmar = (Button) finder.findRequiredView(obj, R.id.f110creditosconfirmar, "field 'confirmar'");
        creditosFragment.irCuentas = (Button) finder.findRequiredView(obj, R.id.f112creditosircuentas, "field 'irCuentas'");
        creditosFragment.listCreditos = (ListView) finder.findRequiredView(obj, R.id.listCreditos, "field 'listCreditos'");
        creditosFragment.pantallaInicial = finder.findRequiredView(obj, R.id.pantallaInicial, "field 'pantallaInicial'");
        creditosFragment.pantallaSimulacion = finder.findRequiredView(obj, R.id.pantallaSimulacion, "field 'pantallaSimulacion'");
        creditosFragment.pantallaConfirmacion = finder.findRequiredView(obj, R.id.pantallaConfirmacion, "field 'pantallaConfirmacion'");
        creditosFragment.pantallaComprobante = finder.findRequiredView(obj, R.id.pantallaComprobante, "field 'pantallaComprobante'");
        creditosFragment.viewComprobanteCreditos = finder.findRequiredView(obj, R.id.viewComprobanteCreditos, "field 'viewComprobanteCreditos'");
    }

    public static void reset(CreditosFragment creditosFragment) {
        creditosFragment.leyendaPrestamoUvaConfirmar = null;
        creditosFragment.leyendaPrestamoUvaComprobante = null;
        creditosFragment.mControlPager = null;
        creditosFragment.vgWrapperSimulacion = null;
        creditosFragment.creditosView = null;
        creditosFragment.continuar = null;
        creditosFragment.confirmar = null;
        creditosFragment.irCuentas = null;
        creditosFragment.listCreditos = null;
        creditosFragment.pantallaInicial = null;
        creditosFragment.pantallaSimulacion = null;
        creditosFragment.pantallaConfirmacion = null;
        creditosFragment.pantallaComprobante = null;
        creditosFragment.viewComprobanteCreditos = null;
    }
}
