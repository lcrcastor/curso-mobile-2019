package ar.com.santander.rio.mbanking.app.module.buySellDollars;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;

public class ReceiptBuySellDollarsPresenter extends BasePresenter<ReceiptBuySellDollarsView> {
    public ReceiptBuySellDollarsPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void showReceipt() {
        ((ReceiptBuySellDollarsView) getBaseView()).showReceipt();
    }
}
