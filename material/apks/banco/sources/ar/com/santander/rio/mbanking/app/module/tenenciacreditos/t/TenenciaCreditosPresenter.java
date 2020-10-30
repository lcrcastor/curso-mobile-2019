package ar.com.santander.rio.mbanking.app.module.tenenciacreditos.t;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.services.events.ConfirmarPagoEvent;
import ar.com.santander.rio.mbanking.services.events.GetDetalleCuotaTenenciaCreditoEvent;
import ar.com.santander.rio.mbanking.services.events.GetProximasCuotasCreditoEvent;
import ar.com.santander.rio.mbanking.services.events.GetTenenciaCreditosEvent;
import java.util.Date;

public interface TenenciaCreditosPresenter {
    void backPagePressed(int i);

    String getExpirationDate(String str);

    String getNumComprobante();

    void nextPage();

    void onAccountSelected(String str);

    void onBtnCancelConfirmLongTermDepositClicked();

    void onBtnConstituirClicked();

    void onBtnContinueInCreateClicked();

    void onBtnReceiptClicked();

    void onChangeCurrencyClicked();

    void onChangeCurrencySelected(String str);

    void onConfirmarPagoCuotasOK(ConfirmarPagoEvent confirmarPagoEvent);

    void onConfirmarPagoResponse(ConfirmarPagoEvent confirmarPagoEvent, BaseActivity baseActivity, String str, String str2);

    void onCurrencySelected(String str);

    void onExpiredActionClicked();

    void onExpiredActionSelected(String str);

    void onExpiredDateSelected(Date date);

    void onGetDetalleCuotaResponseOk(GetDetalleCuotaTenenciaCreditoEvent getDetalleCuotaTenenciaCreditoEvent);

    void onGetDetalleCuotaTenenciaCreditoResponseOK(GetDetalleCuotaTenenciaCreditoEvent getDetalleCuotaTenenciaCreditoEvent);

    void onGoToRatesClicked(String str);

    void onGoToSolicitudCreditos();

    void onLoad();

    void onPauseEvent();

    void onProximasCuotasOK(GetProximasCuotasCreditoEvent getProximasCuotasCreditoEvent);

    void onRedButtonClicked();

    void onSelectorAccountClicked();

    void onSelectorCurrencyClicked();

    void onSelectorExpiredDateClicked();

    void onTenenciasResponse(GetTenenciaCreditosEvent getTenenciaCreditosEvent, BaseActivity baseActivity);

    void setAccount();

    void setListeners();
}
