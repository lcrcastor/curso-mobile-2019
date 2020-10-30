package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAmountAcc;
import ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalytics;
import ar.com.santander.rio.mbanking.app.module.accounts.details.DetailsTransactionPresenter;
import ar.com.santander.rio.mbanking.app.module.accounts.details.DetailsTransactionPresenterImpl;
import ar.com.santander.rio.mbanking.app.module.accounts.details.DetailsTransactionView;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransactionResponseBean;
import ar.com.santander.rio.mbanking.view.AmountView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import javax.inject.Inject;

public class DetallesMovimientoFragment extends BaseFragment implements DetailsTransactionView {
    private TransactionResponseBean a;
    private DetailsTransactionPresenter b;
    private Cuenta c;
    @Inject
    public AccountAnalytics mAccountAnalytics;
    @InjectView(2131365838)
    public ViewGroup tableDetailsTransactions;
    @InjectView(2131366313)
    public AmountView vAmount;
    @InjectView(2131366322)
    public TextView vDataAccount;
    @InjectView(2131366323)
    public TextView vDateDetailsTransaction;
    @InjectView(2131366325)
    public TextView vDescription;

    public Fragment getFragment() {
        return this;
    }

    public static DetallesMovimientoFragment getInstance(Cuenta cuenta, TransactionResponseBean transactionResponseBean) {
        DetallesMovimientoFragment detallesMovimientoFragment = new DetallesMovimientoFragment();
        detallesMovimientoFragment.c = cuenta;
        detallesMovimientoFragment.a = transactionResponseBean;
        detallesMovimientoFragment.b = new DetailsTransactionPresenterImpl(detallesMovimientoFragment);
        return detallesMovimientoFragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.detalles_movimiento_fragment, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        this.mAccountAnalytics.registerScreenDetailsTransaction();
        this.b.setDataTransaction(this.c, this.a);
        return inflate;
    }

    public void setDetailsTransaction(ViewGroup viewGroup) {
        if (this.tableDetailsTransactions != null) {
            this.tableDetailsTransactions.addView(viewGroup);
        }
    }

    public void setAmountTransaction(CAmount cAmount) {
        if (this.vAmount != null) {
            this.vAmount.setColorAmount(cAmount.isAmountPossite());
            this.vAmount.setAmount(cAmount.getAmount());
            this.vAmount.setCElementAcc(new CAmountAcc());
        }
    }

    public void setDescriptionTransaction(String str) {
        if (this.vDescription != null) {
            this.vDescription.setText(str);
            this.vDescription.setContentDescription(str != null ? str.toLowerCase() : "");
        }
    }

    public void setDateTransaction(String str) {
        if (this.vDateDetailsTransaction != null) {
            this.vDateDetailsTransaction.setText(str);
            this.vDateDetailsTransaction.setContentDescription(str);
        }
    }

    public void setDataAccount(String str) {
        if (this.vDataAccount != null) {
            this.vDataAccount.setText(str);
            this.vDataAccount.setContentDescription(str != null ? str.toLowerCase() : "");
        }
    }

    public void onBackPressed() {
        this.b.onBackPressed();
    }

    public Context getActContext() {
        return getActivity();
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
