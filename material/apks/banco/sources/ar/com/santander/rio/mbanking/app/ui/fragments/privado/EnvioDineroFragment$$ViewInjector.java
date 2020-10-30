package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.CustomSpinner;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class EnvioDineroFragment$$ViewInjector {
    public static void inject(Finder finder, final EnvioDineroFragment envioDineroFragment, Object obj) {
        View findRequiredView = finder.findRequiredView(obj, R.id.F12_00_csp_selectorCuentas_vgSelectorAccount, "field 'lbl_cambiarCuenta' and method 'seleccionarCuenta'");
        envioDineroFragment.lbl_cambiarCuenta = (CustomSpinner) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                envioDineroFragment.y();
            }
        });
        envioDineroFragment.idCuenta = (TextView) finder.findRequiredView(obj, R.id.F12_00_lbl_selectorCuentas_idCuenta, "field 'idCuenta'");
        envioDineroFragment.selectorCuentas = (LinearLayout) finder.findRequiredView(obj, R.id.F12_00_lnl_selectorCuentas, "field 'selectorCuentas'");
        envioDineroFragment.cuentaSaldoValue = (AmountView) finder.findRequiredView(obj, R.id.F12_00_lbl_data_saldoCuenta, "field 'cuentaSaldoValue'");
        envioDineroFragment.btn_continuar = (Button) finder.findRequiredView(obj, R.id.F12_00_btn_continuar, "field 'btn_continuar'");
        envioDineroFragment.lbl_limiteDiario = (TextView) finder.findRequiredView(obj, R.id.F12_00_lbl_limiteDiario, "field 'lbl_limiteDiario'");
        envioDineroFragment.img_botonAyudaLimiteDiario = (ImageView) finder.findRequiredView(obj, R.id.F12_00_img_limiteDiario, "field 'img_botonAyudaLimiteDiario'");
        envioDineroFragment.envioDineroView = finder.findRequiredView(obj, R.id.idEnvioDineroView, "field 'envioDineroView'");
    }

    public static void reset(EnvioDineroFragment envioDineroFragment) {
        envioDineroFragment.lbl_cambiarCuenta = null;
        envioDineroFragment.idCuenta = null;
        envioDineroFragment.selectorCuentas = null;
        envioDineroFragment.cuentaSaldoValue = null;
        envioDineroFragment.btn_continuar = null;
        envioDineroFragment.lbl_limiteDiario = null;
        envioDineroFragment.img_botonAyudaLimiteDiario = null;
        envioDineroFragment.envioDineroView = null;
    }
}
