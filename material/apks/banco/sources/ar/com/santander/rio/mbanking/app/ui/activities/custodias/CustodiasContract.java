package ar.com.santander.rio.mbanking.app.ui.activities.custodias;

import ar.com.santander.rio.mbanking.app.base.IBasePresenter;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Custodia;
import java.util.List;

public interface CustodiasContract {

    public interface Presenter extends IBasePresenter<View> {
        void getCustodia(String str, int i, int i2, String str2);

        void getRecyclerData(String str);
    }

    public interface View extends IBaseView {
        void dismissProgressIndicator();

        void errorResult();

        void okResult();

        void setCustodia(Cuenta cuenta);

        void setErrorCustodias();

        void setLeyenda(String str, String str2);

        void setPartialErrorMsg(String str, int i);

        void setRecyclerData(List<Custodia> list);

        void showProgressIndicator(String str);

        void unknowError();
    }
}
