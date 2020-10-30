package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.module.payments.commons.TypeDebt;

public class RecargaCelularesFragment extends PagosFragment {
    public static PagosFragment getInstance() {
        return PagosFragment.getInstance(TypeDebt.RC, R.layout.phone_top_up_fragment);
    }
}
