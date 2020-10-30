package ar.com.santander.rio.mbanking.app.module.comodinessuperclub;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;

public class CouponReceiptSuperClubPresenter extends BasePresenter<CouponReceiptSuperClubView> {
    public CouponReceiptSuperClubPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void showComodinReceipt() {
        ((CouponReceiptSuperClubView) getBaseView()).showComodinReceipt();
    }
}
