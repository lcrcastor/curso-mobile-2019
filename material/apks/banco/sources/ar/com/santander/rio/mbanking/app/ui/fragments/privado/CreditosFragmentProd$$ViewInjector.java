package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class CreditosFragmentProd$$ViewInjector {
    public static void inject(Finder finder, final CreditosFragmentProd creditosFragmentProd, Object obj) {
        creditosFragmentProd.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.vgCreditos, "field 'mControlPager'");
        creditosFragmentProd.vgWrapperSimulacion = (ViewGroup) finder.findRequiredView(obj, R.id.vgWrapperSimulacion, "field 'vgWrapperSimulacion'");
        creditosFragmentProd.creditosView = (LinearLayout) finder.findRequiredView(obj, R.id.idCreditosView, "field 'creditosView'");
        View findRequiredView = finder.findRequiredView(obj, R.id.f111creditoscontinuar, "field 'continuar' and method 'onCreditoContinuar'");
        creditosFragmentProd.continuar = (Button) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                creditosFragmentProd.y();
            }
        });
        View findRequiredView2 = finder.findRequiredView(obj, R.id.f110creditosconfirmar, "field 'confirmar' and method 'onCreditoConfirmar'");
        creditosFragmentProd.confirmar = (Button) findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                creditosFragmentProd.z();
            }
        });
        View findRequiredView3 = finder.findRequiredView(obj, R.id.f112creditosircuentas, "field 'irCuentas' and method 'onCreditoIrCuentas'");
        creditosFragmentProd.irCuentas = (Button) findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                creditosFragmentProd.A();
            }
        });
        creditosFragmentProd.listCreditos = (ListView) finder.findRequiredView(obj, R.id.listCreditos, "field 'listCreditos'");
        creditosFragmentProd.pantallaInicial = finder.findRequiredView(obj, R.id.pantallaInicial, "field 'pantallaInicial'");
        creditosFragmentProd.pantallaSimulacion = finder.findRequiredView(obj, R.id.pantallaSimulacion, "field 'pantallaSimulacion'");
        creditosFragmentProd.pantallaConfirmacion = finder.findRequiredView(obj, R.id.pantallaConfirmacion, "field 'pantallaConfirmacion'");
        creditosFragmentProd.pantallaComprobante = finder.findRequiredView(obj, R.id.pantallaComprobante, "field 'pantallaComprobante'");
        creditosFragmentProd.viewComprobanteCreditos = finder.findRequiredView(obj, R.id.viewComprobanteCreditos, "field 'viewComprobanteCreditos'");
        finder.findRequiredView(obj, R.id.f84creditopage3tyc, "method 'onVerTerminos'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                creditosFragmentProd.B();
            }
        });
    }

    public static void reset(CreditosFragmentProd creditosFragmentProd) {
        creditosFragmentProd.mControlPager = null;
        creditosFragmentProd.vgWrapperSimulacion = null;
        creditosFragmentProd.creditosView = null;
        creditosFragmentProd.continuar = null;
        creditosFragmentProd.confirmar = null;
        creditosFragmentProd.irCuentas = null;
        creditosFragmentProd.listCreditos = null;
        creditosFragmentProd.pantallaInicial = null;
        creditosFragmentProd.pantallaSimulacion = null;
        creditosFragmentProd.pantallaConfirmacion = null;
        creditosFragmentProd.pantallaComprobante = null;
        creditosFragmentProd.viewComprobanteCreditos = null;
    }
}
