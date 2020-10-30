package ar.com.santander.rio.mbanking.app.module.comodinessuperclub;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaSuperClubBean;
import java.util.List;

public interface ListSuperClubView extends IBaseView {
    void configureListDataAdapter(List<CategoriaSuperClubBean> list);

    void showList(String str);

    void showList(List<CategoriaSuperClubBean> list);

    void showMatchesList(List<CategoriaSuperClubBean> list);
}
