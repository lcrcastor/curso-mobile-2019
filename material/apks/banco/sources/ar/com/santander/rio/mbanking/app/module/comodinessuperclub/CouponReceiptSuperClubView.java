package ar.com.santander.rio.mbanking.app.module.comodinessuperclub;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaSuperClubBean;
import java.util.ArrayList;

public interface CouponReceiptSuperClubView extends IBaseView {
    void configureOptionsShare();

    void configureOptionsShare(int i);

    void showComodinReceipt();

    void showComodinReceipt(String str, String str2, String str3, String str4, ArrayList<LeyendaSuperClubBean> arrayList);
}
