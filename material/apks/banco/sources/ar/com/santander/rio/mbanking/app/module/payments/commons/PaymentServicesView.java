package ar.com.santander.rio.mbanking.app.module.payments.commons;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import ar.com.santander.rio.mbanking.app.ui.adapters.BasePaymentAdapter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LinkSeguroBean;
import java.util.ArrayList;

public interface PaymentServicesView {
    void addViewFormConfirmPayment(View view);

    void addViewFormPreparePayment(View view);

    void addViewReceiptBottom(View view);

    void addViewReceiptTop(View view);

    void cleanFormPreparePayment();

    void cleanViewReceiptPayment();

    void cleanViewTableConfirmPayment();

    void closeDialogLoading();

    void errorEmptyAccount();

    Context getActContext();

    int getCurrentIndexViewPage();

    View getCurrentViewPageShow();

    IDataManager getDataManager();

    int getIndexViewPage(View view);

    BasePaymentAdapter getPaymentServicesAdapter();

    ViewGroup getRowSelectorAmount();

    String getSelectorAccount();

    String getSelectorAmountValue();

    SessionManager getSessionManager();

    ViewGroup getVgWrapperReceiptBottomPayment();

    ViewGroup getVgWrapperReceiptTopPayment();

    ViewGroup getViewFormPreparePayment();

    View getViewPageFromIndex(int i);

    View getViewSelectorAccount();

    View getViewSelectorAmount();

    View getvPageConfirmPaymentServices();

    View getvPagePreparePaymentServices();

    View getvPageReceiptPaymentServices();

    View getvPageTablePaymentServices();

    void gotoPage(int i);

    void hideViewLegend();

    boolean isVisibleAmountEditable();

    void loadBarReturn();

    void loadBarShare();

    void onBackPressed();

    void onDispatchEventsError(WebServiceEvent webServiceEvent);

    void onShowDialogConfirmPayment(String str, String str2);

    void onShowDialogError(String str, String str2);

    void onTopPageConfirm();

    void onTopPagePrepare();

    void onTopPageReceipt();

    void reloadFragment(TypeDebt typeDebt);

    void restartMainActionBar();

    void restoreSelectorAccount(String str);

    void restoreSelectorAmount(String str);

    void setAdapterToListPaymentServices(BasePaymentAdapter basePaymentAdapter);

    void setFocusAndShowKeyboard(EditText editText);

    void setLabelButtonConfirm(int i);

    void setLabelSelectorAccount(String str);

    void setLabelSelectorAmount(String str);

    void setListenerClickSelectorAccount();

    void setListenerClickSelectorAmount();

    void setNextPageAnimation();

    void setPreviusPageAnimation();

    void setSelectorValue(String str);

    void setTextLegendBottom(String str);

    void setTextLegendTop(String str);

    void setTitlePage(View view, String str);

    void showActionShareReceipt(String str, String str2);

    void showDialogLoading();

    void showDialogSelector(int i, ArrayList<String> arrayList, String str, String str2);

    void showDialogSelectorAccount(ArrayList<String> arrayList, String str);

    void showLink(LinkSeguroBean linkSeguroBean, CuentaDebitoBean cuentaDebitoBean, String str);

    void showViewLegend();
}
