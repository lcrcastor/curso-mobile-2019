package ar.com.santander.rio.mbanking.app.module.tenenciacreditos.t;

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
import ar.com.santander.rio.mbanking.services.model.ProximasCuotasModel.ProximasCuotas;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.CategoriaCredito;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.CuentaOperativa;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosCredito;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.Listaleyendas;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTenenciasDatosPFBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarPagoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleCuotaTenenciaCreditoBodyResponseBean;
import java.util.ArrayList;

public interface TenenciaCreditosView extends StepsView {
    void addBlockBodyErrorConfirmarPagoRes4(String str);

    void addBlockBodyErrorConfirmarPagoRes8(String str, String str2);

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

    void configConfirmUI();

    void configDetailUI(GetDetalleCuotaTenenciaCreditoBodyResponseBean getDetalleCuotaTenenciaCreditoBodyResponseBean, CategoriaCredito categoriaCredito, DatosCredito datosCredito);

    void configPaymentUI(CuentaOperativa cuentaOperativa);

    void configReceiptUI(ConfirmarPagoBodyResponseBean confirmarPagoBodyResponseBean);

    void disableAccountSelector();

    void enableAccountSelector();

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

    View getPageDetail();

    View getPageList();

    View getPagePayment();

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

    ViewGroup getWrapperDataReceipt();

    void hideLegendConfirm();

    void hideLegendReceipt();

    boolean isDialogLoadingVisible();

    boolean isForgetShareReceipt();

    void loadBarBackMenu();

    void loadBarClose();

    void loadBarCloseAndReturn();

    void loadBarReturn();

    void loadBarShare();

    void onClickShowActionShareReceipt();

    void onDispatchEventsError(WebServiceEvent webServiceEvent);

    void onShowDialog(String str, String str2, String str3);

    void onShowDialogConfirmPayment(String str, String str2);

    void onShowError(String str, String str2);

    void onShowErrorRes1(WebServiceEvent webServiceEvent);

    void onTopPageConfirm();

    void onTopPageCreate();

    void onTopPageReceipt();

    void reloadFragment();

    void rememberShareReceipt();

    void removeBlockBodyPageConfirm();

    void removeBlockBodyPageCreate();

    void removeBlockBodyPageDetail();

    void removeBlockBodyPageList();

    void removeBlockBodyPageReceipt();

    void restartMainActionBar();

    void restoreSelectorCurrency();

    void setCountDetailCuot(String str);

    void setCountDetailCuot2(String str, String str2);

    void setEventChangedValueTerm();

    void setEventLostFocusTerm();

    void setLabelSelectorAccount(String str, String str2);

    void setLabelSelectorCurrency(String str);

    void setLegendConfirm(String str);

    void setLegendReceipt(String str);

    void setListaLeyendas(Listaleyendas listaleyendas);

    void setListenerClickChangeCurrency();

    void setListenerClickExpiredAction();

    void setListenerClickExpiredDate();

    void setListenerClickGoToCreateNewAction();

    void setListenerClickGoToRatesAction();

    void setListenerClickRowAccount();

    void setListenerClickRowCurrency();

    void setListenerPickerExpiredDate();

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

    void startListaProximasCuotasActivity(ProximasCuotas proximasCuotas);
}
