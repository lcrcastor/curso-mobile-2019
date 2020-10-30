package ar.com.santander.rio.mbanking.app.ui.activities.titulosvalores;

import ar.com.santander.rio.mbanking.app.base.IBasePresenter;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Titulo;
import java.util.LinkedHashMap;
import java.util.List;

public interface TitulosValoresContract {

    public interface Presenter extends IBasePresenter<View> {
        void changeCuenta(int i);

        void getRecyclerData(String str);

        void getTitulosValores(String str, int i, int i2, String str2);
    }

    public interface View extends IBaseView {
        void changeStateSelectorAccount(Boolean bool);

        void dismissProgressIndicator();

        void errorResult();

        void okResult();

        void setErrorTitulosValores();

        void setLeyenda(String str, String str2);

        void setPartialErrorMsg(String str, int i);

        void setRecyclerData(LinkedHashMap<String, List<Titulo>> linkedHashMap);

        void setTitulosValores(Cuenta cuenta);

        void showProgressIndicator(String str);

        void unknowError();
    }
}
