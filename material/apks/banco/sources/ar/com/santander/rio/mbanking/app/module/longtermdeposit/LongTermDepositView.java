package ar.com.santander.rio.mbanking.app.module.longtermdeposit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.app.commons.StepsView;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTenenciasDatosPFBean;
import java.util.ArrayList;

public interface LongTermDepositView extends StepsView {
    void addBlockBodyPageConfirm(View view);

    void addBlockBodyPageCreate(View view);

    void addBlockBodyPageDetail(CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean, String str);

    void addBlockBodyPageListErrorDevolucionPlazosFijos(String str);

    void addBlockBodyPageListPlazosFijosExistentes(View view);

    void addBlockBodyPageListSinPlazosFijos();

    void addBlockBodyPageRates(View view);

    void addBlockBodyPageReceipt(View view);

    void closeDialogLoading();

    void configActionShareReceipt(String str, String str2);

    void errorEmptyAccount();

    Context getActContext();

    AnalyticsManager getAnalyticsManager();

    Button getBtnConstituirInConfirm();

    Button getBtnContinueInCreate();

    Button getBtnReturnInReceipt();

    int getCurrentIndexViewPage();

    IDataManager getDataManager();

    TextView getLblMontoMinimoRates();

    View getPageConfirm();

    View getPageCreate();

    View getPageDetail();

    View getPageList();

    View getPageRates();

    View getPageReceipt();

    View getRowSelectorAccount();

    TextView getSelectorCurrency();

    SessionManager getSessionManager();

    String getValueAmount();

    String getValueChangeCurrencyAction();

    String getValueCurrency();

    String getValueExpiredAction();

    String getValueExpiredDate();

    String getValueTerm();

    View getViewPageFromIndex(int i);

    View getViewSelectorAccount();

    TextView getViewTerm();

    ViewGroup getWrapperDataConfirm();

    ViewGroup getWrapperDataCreate();

    ViewGroup getWrapperDataRates();

    ViewGroup getWrapperDataReceipt();

    void hideLegendConfirm();

    void hideLegendReceipt();

    boolean isForgetShareReceipt();

    void loadBarClose();

    void loadBarReturn();

    void loadBarShare();

    void onClickShowActionShareReceipt();

    void onDispatchEventsError(WebServiceEvent webServiceEvent);

    void onShowDialog(String str, String str2, String str3);

    void onShowDialogConfirmPayment(String str, String str2);

    void onShowError(String str, String str2);

    void onTopPageConfirm();

    void onTopPageCreate();

    void onTopPageReceipt();

    void reloadFragment();

    void rememberShareReceipt();

    void removeBlockBodyPageConfirm();

    void removeBlockBodyPageCreate();

    void removeBlockBodyPageDetail();

    void removeBlockBodyPageList();

    void removeBlockBodyPageRates();

    void removeBlockBodyPageReceipt();

    void restartMainActionBar();

    void restoreSelectorCurrency();

    void setEventChangedValueTerm();

    void setEventLostFocusTerm();

    void setLabelSelectorAccount(String str);

    void setLabelSelectorCurrency(String str);

    void setLegendConfirm(String str);

    void setLegendReceipt(String str);

    void setListenerClickCambiarTipoCuentas();

    void setListenerClickChangeCurrency();

    void setListenerClickExpiredAction();

    void setListenerClickExpiredDate();

    void setListenerClickGoToCreateNewAction();

    void setListenerClickGoToRatesAction();

    void setListenerClickRowAccount();

    void setListenerClickRowCurrency();

    void setListenerPickerExpiredDate();

    void setTasas();

    void setTermValue(String str);

    void setTitleLayout(View view, String str);

    void setValueChangeCurrencyAction(String str);

    void setValueExpiredAction(String str);

    void setValueExpiredDate(String str);

    void showDialogDate(String str, String str2);

    void showDialogLoading();

    void showDialogSelector(String str, String str2, ArrayList<String> arrayList, String str3);

    void showLegendConfirm();

    void showLegendReceipt();
}
