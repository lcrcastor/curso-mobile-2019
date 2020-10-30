package ar.com.santander.rio.mbanking.app.module.buySellDollars;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.CompraVentaDolarResponseBean;

public interface ConfirmationBuySellDollarsView extends IBaseView {
    void displayReceipt();

    void exchangeRateValidationFailed();

    void offHoursValidationFailed();

    void onCompraVentaDolar(CompraVentaDolarResponseBean compraVentaDolarResponseBean);
}
