package ar.com.santander.rio.mbanking.app.module.buySellDollars;

import ar.com.santander.rio.mbanking.app.base.IBaseView;

public interface ReceiptBuySellDollarsView extends IBaseView {
    void configureOptionsShare();

    void configureOptionsShare(int i);

    void showReceipt();
}
