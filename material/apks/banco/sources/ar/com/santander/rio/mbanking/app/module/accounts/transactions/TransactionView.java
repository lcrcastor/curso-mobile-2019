package ar.com.santander.rio.mbanking.app.module.accounts.transactions;

import android.content.Context;
import android.text.Spanned;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransactionResponseBean;
import java.util.List;

public interface TransactionView {
    void cleanTransactions();

    Context getActContext();

    void hideViewLoading();

    void onDispatchEventsError(WebServiceEvent webServiceEvent);

    void setMessageTransactions(Spanned spanned);

    void setMessageTransactions7DaysVisibility();

    void setTransactionsAccount(List<TransactionResponseBean> list);

    void setVisibilityWrapperMessageTransactions(int i);

    void showAlert(String str, String str2);

    void showViewLoading();

    void updateLabelFilters(String str);
}
