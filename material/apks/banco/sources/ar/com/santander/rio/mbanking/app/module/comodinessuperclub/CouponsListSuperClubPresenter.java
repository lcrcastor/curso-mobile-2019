package ar.com.santander.rio.mbanking.app.module.comodinessuperclub;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponSuperClubBean;
import com.squareup.otto.Bus;

public class CouponsListSuperClubPresenter extends BasePresenter<CouponsListSuperClubView> {
    public CouponsListSuperClubPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void showComodinesList() {
        ((CouponsListSuperClubView) getBaseView()).showComodinesList();
    }

    public void onComodinSelected(CuponSuperClubBean cuponSuperClubBean) {
        ((CouponsListSuperClubView) getBaseView()).showComodinDetail(cuponSuperClubBean);
    }
}
