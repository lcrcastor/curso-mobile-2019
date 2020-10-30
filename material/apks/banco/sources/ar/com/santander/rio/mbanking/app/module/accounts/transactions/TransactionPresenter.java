package ar.com.santander.rio.mbanking.app.module.accounts.transactions;

import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.MovCtasEvent;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.general.FilterTransactionsAccount;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransactionResponseBean;

public interface TransactionPresenter {
    void applyFilter(FilterTransactionsAccount filterTransactionsAccount);

    void onLoadTransactions(IDataManager iDataManager, Cuenta cuenta, SessionManager sessionManager);

    void onNextTransactionsAccounts();

    void onResponseTransactions(MovCtasEvent movCtasEvent);

    void onResume();

    void onTransactionClicked(TransactionResponseBean transactionResponseBean);
}
