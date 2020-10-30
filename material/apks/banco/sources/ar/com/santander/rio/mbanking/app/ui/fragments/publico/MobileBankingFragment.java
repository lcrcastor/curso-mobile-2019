package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;

public class MobileBankingFragment extends BaseFragment {
    public void onBackPressed() {
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return getActivity().getLayoutInflater().inflate(R.layout.mobile_banking_fragment, viewGroup, false);
    }
}
