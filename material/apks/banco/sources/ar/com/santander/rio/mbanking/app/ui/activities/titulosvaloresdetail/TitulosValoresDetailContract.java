package ar.com.santander.rio.mbanking.app.ui.activities.titulosvaloresdetail;

import ar.com.santander.rio.mbanking.app.base.IBasePresenter;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Titulo;
import java.util.List;

public interface TitulosValoresDetailContract {

    public interface Presenter extends IBasePresenter<View> {
        void getTitulo(Titulo titulo);
    }

    public interface View extends IBaseView {
        void setHeaderData(String str, String str2, String str3);

        void setRecyclerViewData(List<String> list, List<TitulosValoresViewElements> list2);
    }
}
