package ar.com.santander.rio.mbanking.app.module.comodinessuperclub;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponSuperClubBean;
import java.util.List;

public interface CouponsListSuperClubView extends IBaseView {
    void showComodinDetail(CuponSuperClubBean cuponSuperClubBean);

    void showComodinesList();

    void showComodinesList(String str, String str2, String str3, List<CuponSuperClubBean> list);
}
