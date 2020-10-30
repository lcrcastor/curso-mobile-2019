package ar.com.santander.rio.mbanking.app.module.accounts.transactions;

import android.content.Intent;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.module.accounts.ConstantsTransactions;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.DetailsTransactionActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.MovCtasEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.general.FilterTransactionsAccount;
import ar.com.santander.rio.mbanking.services.soap.beans.MovCtasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovCtasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovCtasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransactionResponseBean;
import ar.com.santander.rio.mbanking.utils.UtilAccesibility;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.Utils;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

public class TransactionPresenterImpl implements TransactionPresenter {
    private static String c = "+000000000000.00";
    private static String d = "+999999999999.99";
    private final Integer a = Integer.valueOf(1);
    private final Integer b = Integer.valueOf(1);
    private Cuenta e;
    private IDataManager f;
    private SessionManager g;
    private TransactionView h;
    private MovCtasBodyRequestBean i;
    private MovCtasBodyResponseBean j;

    public void onResume() {
    }

    public TransactionPresenterImpl(TransactionView transactionView) {
        this.h = transactionView;
    }

    public void onLoadTransactions(IDataManager iDataManager, Cuenta cuenta, SessionManager sessionManager) {
        if (cuenta == null) {
            a(this.h.getActContext().getString(R.string.TEXT_ERROR_TRANSACTIONS));
            return;
        }
        this.e = cuenta;
        this.f = iDataManager;
        this.g = sessionManager;
        this.i = new MovCtasBodyRequestBean();
        c();
        FilterTransactionsAccount filterTransactionsAccount = new FilterTransactionsAccount(DateTime.now().toString(Constants.FORMAT_DATE_WS_1), DateTime.now().toString(Constants.FORMAT_DATE_WS_1), this.h.getActContext().getString(R.string.ID96_ACCOUNTS_CHANGEACC_ACT_MOV1));
        a(filterTransactionsAccount);
        this.h.updateLabelFilters(filterTransactionsAccount.label);
        a();
    }

    private void a() {
        this.f.getMovCtas(this.i);
    }

    public void onResponseTransactions(MovCtasEvent movCtasEvent) {
        this.h.hideViewLoading();
        a(movCtasEvent);
    }

    private void a(MovCtasEvent movCtasEvent) {
        if (movCtasEvent == null) {
            this.h.onDispatchEventsError(movCtasEvent);
        } else if (TypeResult.OK.equals(movCtasEvent.getResult())) {
            a(movCtasEvent.getResponse());
        } else {
            a(movCtasEvent, movCtasEvent.getResult(), movCtasEvent.getTitleToShow(), movCtasEvent.getMessageToShow());
            UtilAccesibility.sendAccessibilityEvent(this.h.getActContext(), movCtasEvent.getMessageToShow());
        }
    }

    private void a(MovCtasResponseBean movCtasResponseBean) {
        if (movCtasResponseBean != null) {
            try {
                a(movCtasResponseBean.movCtasBodyResponseBean);
                List<TransactionResponseBean> list = movCtasResponseBean.movCtasBodyResponseBean.transactionsResponseBean.lstTranstactionsResponseBeans;
                if (movCtasResponseBean.movCtasBodyResponseBean.cantMovMostrar == null || Integer.parseInt(movCtasResponseBean.movCtasBodyResponseBean.cantMovMostrar) <= 0 || list == null || list.size() <= 0) {
                    a((Spanned) new SpannableString(this.h.getActContext().getString(R.string.TEXT_EMPTY_TRANSACTIONS)));
                } else {
                    a(movCtasResponseBean.movCtasBodyResponseBean.transactionsResponseBean.lstTranstactionsResponseBeans);
                }
            } catch (Exception unused) {
                a(this.h.getActContext().getString(R.string.TEXT_ERROR_TRANSACTIONS));
            }
        } else {
            a(this.h.getActContext().getString(R.string.TEXT_ERROR_TRANSACTIONS));
        }
    }

    private void a(List<TransactionResponseBean> list) {
        this.h.setVisibilityWrapperMessageTransactions(8);
        this.h.setTransactionsAccount(list);
        if (!d()) {
            b();
        }
    }

    private void b() {
        this.h.setTransactionsAccount(new ArrayList());
    }

    private void a(MovCtasBodyResponseBean movCtasBodyResponseBean) {
        this.j = movCtasBodyResponseBean;
    }

    public void onTransactionClicked(TransactionResponseBean transactionResponseBean) {
        try {
            Intent intent = new Intent((SantanderRioMainActivity) this.h.getActContext(), DetailsTransactionActivity.class);
            intent.putExtra(DetailsTransactionActivity.INDEX_ACCOUNT, new Gson().toJson((Object) this.e));
            intent.putExtra(DetailsTransactionActivity.INDEX_TRANSACTION_BEAN, new Gson().toJson((Object) transactionResponseBean));
            ((SantanderRioMainActivity) this.h.getActContext()).startNewActivity(intent);
        } catch (Exception e2) {
            Log.e("@dev", "Excepcion:", e2);
        }
    }

    private void c() {
        if (this.i == null) {
            this.i = new MovCtasBodyRequestBean();
        }
        this.i.accountRequestBean = new AccountRequestBean(this.e.getTipo(), this.e.getNroSuc(), this.e.getNumero());
    }

    public void onNextTransactionsAccounts() {
        try {
            if (d()) {
                a(this.a);
                a();
                return;
            }
            b();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private boolean d() {
        try {
            if (Integer.parseInt(this.i.nroPag) < Integer.parseInt(this.j.cantPagTotal)) {
                return true;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return false;
    }

    private void a(Integer num) {
        Integer valueOf = Integer.valueOf(Integer.valueOf(Integer.parseInt(this.i.nroPag)).intValue() + num.intValue());
        this.i.nroPag = valueOf.toString();
    }

    public void applyFilter(FilterTransactionsAccount filterTransactionsAccount) {
        try {
            this.h.showViewLoading();
            this.h.updateLabelFilters(filterTransactionsAccount.label);
            this.h.setVisibilityWrapperMessageTransactions(8);
            this.h.cleanTransactions();
            a(filterTransactionsAccount);
            a();
        } catch (Exception unused) {
            this.h.hideViewLoading();
            b();
        }
    }

    private void a(FilterTransactionsAccount filterTransactionsAccount) {
        if (filterTransactionsAccount.fromDate != null) {
            this.i.fechaMovDesde = filterTransactionsAccount.fromDate;
        } else {
            this.i.fechaMovDesde = DateTime.now().toString(Constants.FORMAT_DATE_WS_1);
        }
        if (filterTransactionsAccount.toDate != null) {
            this.i.fechaMovHasta = filterTransactionsAccount.toDate;
        } else {
            this.i.fechaMovHasta = this.i.fechaMovDesde;
        }
        if (filterTransactionsAccount.fromAmount != null) {
            this.i.importeDesde = filterTransactionsAccount.fromAmount;
        } else {
            this.i.importeDesde = c;
        }
        if (filterTransactionsAccount.currency != null) {
            this.i.moneda = filterTransactionsAccount.currency;
        } else if (!(this.g == null || this.e == null)) {
            this.i.moneda = UtilAccount.getCurrencyOfAccount(this.g, this.e) == Constants.SYMBOL_CURRENCY_PESOS ? Constants.SYMBOL_CURRENCY_PESOS_STR : Constants.SYMBOL_CURRENCY_DOLLAR_STR;
        }
        if (filterTransactionsAccount.toAmount != null) {
            this.i.importeHasta = filterTransactionsAccount.toAmount;
        } else {
            this.i.importeHasta = d;
        }
        if (filterTransactionsAccount.typeAmount != null) {
            this.i.tipoCons = filterTransactionsAccount.typeAmount.value;
        } else {
            this.i.tipoCons = ConstantsTransactions.getTypeAmountDefault(this.h.getActContext()).value;
        }
        if (filterTransactionsAccount.typeTransaction != null) {
            this.i.tipoMov = filterTransactionsAccount.typeTransaction.value;
        } else {
            this.i.tipoMov = ConstantsTransactions.getTypeTransactionDefault(this.h.getActContext()).value;
        }
        if (this.e.isBancaPrivada() && !TextUtils.isEmpty(filterTransactionsAccount.advancedSearch)) {
            this.i.tipoCons = filterTransactionsAccount.advancedSearch;
        } else if (this.e.isBancaPrivada()) {
            this.i.tipoCons = "";
        }
        this.i.nroPag = this.b.toString();
        this.i.idTrx = null;
    }

    private void a(Spanned spanned) {
        this.h.setMessageTransactions(spanned);
        this.h.setMessageTransactions7DaysVisibility();
        this.h.setVisibilityWrapperMessageTransactions(0);
        this.h.cleanTransactions();
    }

    private void a(String str) {
        this.h.setVisibilityWrapperMessageTransactions(0);
        this.h.setMessageTransactions(new SpannableString(str));
        this.h.cleanTransactions();
    }

    private void a(MovCtasEvent movCtasEvent, TypeResult typeResult, String str, String str2) {
        b();
        if (TypeResult.BEAN_ERROR_RES_1.equals(typeResult) || TypeResult.BEAN_ERROR_RES_2.equals(typeResult)) {
            a("");
            this.h.showAlert(str, str2);
        } else if (TypeResult.BEAN_ERROR_RES_3.equals(typeResult)) {
            a("");
            this.h.showAlert(str, str2);
        } else if (TypeResult.BEAN_ERROR_RES_4.equals(typeResult)) {
            a(Html.fromHtml(Utils.formatIsbanHTMLCode(str2)));
        } else {
            this.h.onDispatchEventsError(movCtasEvent);
        }
    }
}
