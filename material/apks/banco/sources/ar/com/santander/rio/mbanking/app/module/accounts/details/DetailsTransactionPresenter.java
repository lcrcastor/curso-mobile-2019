package ar.com.santander.rio.mbanking.app.module.accounts.details;

import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransactionResponseBean;

public interface DetailsTransactionPresenter {
    void onBackPressed();

    void setDataTransaction(Cuenta cuenta, TransactionResponseBean transactionResponseBean);
}
