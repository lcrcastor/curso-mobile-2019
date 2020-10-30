package ar.com.santander.rio.mbanking.app.module.longtermdeposit;

import ar.com.santander.rio.mbanking.services.events.CnsTasasPFTEvent;
import ar.com.santander.rio.mbanking.services.events.CnsTenenciasEvent;
import ar.com.santander.rio.mbanking.services.events.ConstPlazoFijoEvent;
import java.util.Date;

public interface LongTermDepositPresenter {
    void backPagePressed(int i);

    String getExpirationDate(String str);

    String getNumComprobante();

    void onAccountSelected(String str);

    void onBtnCancelConfirmLongTermDepositClicked();

    void onBtnConstituirClicked();

    void onBtnContinueInCreateClicked();

    void onBtnReceiptClicked();

    void onChangeCurrencyClicked();

    void onChangeCurrencySelected(String str);

    void onCnsTasasPFTEventResponseOk(CnsTasasPFTEvent cnsTasasPFTEvent);

    void onCnsTenenciasResponse(CnsTenenciasEvent cnsTenenciasEvent);

    void onConstPlazoFijoResponseOk(ConstPlazoFijoEvent constPlazoFijoEvent);

    void onCurrencySelected(String str);

    void onExpiredActionClicked();

    void onExpiredActionSelected(String str);

    void onExpiredDateSelected(Date date);

    void onGoToCreateNewClicked();

    void onGoToRatesClicked(String str);

    void onLoad();

    void onPauseEvent();

    void onSelectorAccountClicked();

    void onSelectorCurrencyClicked();

    void onSelectorExpiredDateClicked();

    void sendConstituirLongTerm();

    void setListeners();

    void updateMainView(String str);
}
