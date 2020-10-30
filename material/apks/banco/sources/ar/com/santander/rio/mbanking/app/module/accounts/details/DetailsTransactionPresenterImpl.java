package ar.com.santander.rio.mbanking.app.module.accounts.details;

import android.content.Context;
import android.view.ViewGroup;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.module.accounts.RowViewCreatorAccounts;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransactionResponseBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.tables.BaseColumn.TypeStyle;
import ar.com.santander.rio.mbanking.view.tables.TableView;

public class DetailsTransactionPresenterImpl implements DetailsTransactionPresenter {
    private DetailsTransactionView a;

    public DetailsTransactionPresenterImpl(DetailsTransactionView detailsTransactionView) {
        this.a = detailsTransactionView;
    }

    public void setDataTransaction(Cuenta cuenta, TransactionResponseBean transactionResponseBean) {
        this.a.setAmountTransaction(a(transactionResponseBean.importe, cuenta.esCuentaEnDolares()));
        this.a.setDescriptionTransaction(transactionResponseBean.descripcionLarga);
        this.a.setDetailsTransaction(a(transactionResponseBean, cuenta));
        this.a.setDateTransaction(UtilDate.getDateWithNameMonth(transactionResponseBean.fecha, Constants.FORMAT_DATE_WS_1));
    }

    private CAmount a(String str, boolean z) {
        CAmount cAmount = new CAmount(str);
        cAmount.setSymbolCurrencyDollarOrPeso(z);
        return cAmount;
    }

    private ViewGroup a(TransactionResponseBean transactionResponseBean, Cuenta cuenta) {
        TableView tableView = new TableView(this.a.getActContext());
        tableView.addRowView(RowViewCreatorAccounts.getRowAccount(this.a.getActContext(), CAccounts.getTypeAccount(cuenta.getTipo()).split(UtilsCuentas.SEPARAOR2)[0], UtilAccount.getAccountFormat(cuenta.getNroSuc(), cuenta.getNumero()), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreatorAccounts.getRowDate(this.a.getActContext(), UtilDate.getDateFormat(transactionResponseBean.fecha, Constants.FORMAT_DATE_WS_1), TypeStyle.TYPE_STYLE_DATA_VALUE));
        Context actContext = this.a.getActContext();
        StringBuilder sb = new StringBuilder();
        sb.append(transactionResponseBean.sucursal);
        sb.append("-");
        sb.append(transactionResponseBean.nombreSucursal);
        tableView.addRowView(RowViewCreatorAccounts.getRowSucOrigen(actContext, sb.toString(), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreatorAccounts.getRowReference(this.a.getActContext(), transactionResponseBean.nroComprobante, TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreatorAccounts.getRowAmount(this.a.getActContext(), CAmount.getInstance(transactionResponseBean.importe).getAmount(), TypeStyle.TYPE_STYLE_DATA_VALUE));
        tableView.addRowView(RowViewCreatorAccounts.getRowCurrency(this.a.getActContext(), UtilCurrency.getCurrencyFromValue(this.a.getActContext(), cuenta.esCuentaEnDolares()), TypeStyle.TYPE_STYLE_DATA_VALUE));
        return tableView;
    }

    public void onBackPressed() {
        ((SantanderRioMainActivity) this.a.getActContext()).backLastFragment();
    }
}
