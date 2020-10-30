package ar.com.santander.rio.mbanking.app.module.accounts.details;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import ar.com.santander.rio.mbanking.app.commons.CAmount;

public interface DetailsTransactionView {
    Context getActContext();

    Fragment getFragment();

    void setAmountTransaction(CAmount cAmount);

    void setDataAccount(String str);

    void setDateTransaction(String str);

    void setDescriptionTransaction(String str);

    void setDetailsTransaction(ViewGroup viewGroup);
}
