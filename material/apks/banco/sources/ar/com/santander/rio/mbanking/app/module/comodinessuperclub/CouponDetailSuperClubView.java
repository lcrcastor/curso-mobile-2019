package ar.com.santander.rio.mbanking.app.module.comodinessuperclub;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaSuperClubBean;
import java.util.ArrayList;

public interface CouponDetailSuperClubView extends IBaseView {
    void showComodinDetail(String str, String str2, String str3, String str4, String str5, String str6);

    void showComodinReceipt(String str, String str2, ArrayList<LeyendaSuperClubBean> arrayList);

    void showCouponDetail();
}
