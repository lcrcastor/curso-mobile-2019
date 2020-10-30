package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.module.payments.commons.TypeDebt;

public class PagosServiciosFragment extends PagosFragment {
    public static PagosFragment getInstance() {
        return PagosFragment.getInstance(TypeDebt.PS, R.layout.pago_servicios_fragment);
    }
}
