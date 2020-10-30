package ar.com.santander.rio.mbanking.app.module.payments.commons;

import android.app.Activity;
import ar.com.santander.rio.mbanking.app.ui.adapters.BasePaymentAdapter;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.CnsDeudaRecargaEvent;
import ar.com.santander.rio.mbanking.services.events.RecargaCelularesEvent;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;

public interface PaymentPresenter {
    void backPagePressed(int i);

    void btnConfirmClicked();

    Activity getActivity();

    BasePaymentAdapter getPaymentAdapter();

    void onAccountSelected(String str);

    void onAmountSelected(String str);

    void onBtnNegativeConfirmPaymentClicked();

    void onBtnPossitiveClicked(IDataManager iDataManager);

    void onBtnPrepareClicked();

    void onItemListPaymentServicesClicked(DatosDeudaBean datosDeudaBean);

    void onPauseEvent();

    void onReceiptPaymentClicked();

    void onResumeEvent();

    void onSelectorAccountClicked();

    void onSelectorAmountClicked();

    void onShareReceipt();

    void onShowPageList();

    void onUpdateView();

    void responseCnsDeudaRecargaEvent(CnsDeudaRecargaEvent cnsDeudaRecargaEvent);

    void responsePagoService(RecargaCelularesEvent recargaCelularesEvent);

    void sendRequestCnsDeuda(IDataManager iDataManager);

    void setAnalyticManager(AnalyticsManager analyticsManager);
}
