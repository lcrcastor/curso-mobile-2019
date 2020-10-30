package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.CustomSpinner;
import ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class ExtraccionSinTarjetaFragment$$ViewInjector {
    public static void inject(Finder finder, final ExtraccionSinTarjetaFragment extraccionSinTarjetaFragment, Object obj) {
        extraccionSinTarjetaFragment.idCuenta = (TextView) finder.findRequiredView(obj, R.id.F19_00_lbl_selectorCuentas_idCuenta, "field 'idCuenta'");
        extraccionSinTarjetaFragment.selectorCuentas = (LinearLayout) finder.findRequiredView(obj, R.id.F19_00_lnl_selectorCuentas, "field 'selectorCuentas'");
        extraccionSinTarjetaFragment.inp_importe = (NumericEditTextWithPrefixAccesibility) finder.findRequiredView(obj, R.id.F19_00_inp_importe, "field 'inp_importe'");
        extraccionSinTarjetaFragment.cuentaSaldoValue = (AmountView) finder.findRequiredView(obj, R.id.F19_00_lbl_data_saldoCuenta, "field 'cuentaSaldoValue'");
        extraccionSinTarjetaFragment.lbl_limiteDiario = (TextView) finder.findRequiredView(obj, R.id.F19_00_lbl_limiteDiario, "field 'lbl_limiteDiario'");
        extraccionSinTarjetaFragment.img_botonAyudaLimiteDiario = (ImageView) finder.findRequiredView(obj, R.id.F19_00_img_limiteDiario, "field 'img_botonAyudaLimiteDiario'");
        View findRequiredView = finder.findRequiredView(obj, R.id.F19_00_csp_selectorCuentas_vgSelectorAccount, "field 'lblselectorcuentas' and method 'seleccionarCuenta'");
        extraccionSinTarjetaFragment.lblselectorcuentas = (CustomSpinner) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                extraccionSinTarjetaFragment.y();
            }
        });
        extraccionSinTarjetaFragment.lblsaldocuenta = (TextView) finder.findRequiredView(obj, R.id.F19_00_lbl_saldoCuenta, "field 'lblsaldocuenta'");
        extraccionSinTarjetaFragment.btn_continuar = (Button) finder.findRequiredView(obj, R.id.F19_00_btn_continuar, "field 'btn_continuar'");
        extraccionSinTarjetaFragment.extraccionSinTarjetaView = finder.findRequiredView(obj, R.id.idExtraccionSinTarjetaView, "field 'extraccionSinTarjetaView'");
    }

    public static void reset(ExtraccionSinTarjetaFragment extraccionSinTarjetaFragment) {
        extraccionSinTarjetaFragment.idCuenta = null;
        extraccionSinTarjetaFragment.selectorCuentas = null;
        extraccionSinTarjetaFragment.inp_importe = null;
        extraccionSinTarjetaFragment.cuentaSaldoValue = null;
        extraccionSinTarjetaFragment.lbl_limiteDiario = null;
        extraccionSinTarjetaFragment.img_botonAyudaLimiteDiario = null;
        extraccionSinTarjetaFragment.lblselectorcuentas = null;
        extraccionSinTarjetaFragment.lblsaldocuenta = null;
        extraccionSinTarjetaFragment.btn_continuar = null;
        extraccionSinTarjetaFragment.extraccionSinTarjetaView = null;
    }
}
