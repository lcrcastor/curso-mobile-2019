package ar.com.santander.rio.mbanking.app.module.comodinessuperclub;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalesAdheridosSuperClub;
import java.util.List;

public interface SubcategoriesListSuperClubView extends ListSuperClubView {
    void showComodinesList(List<CuponSuperClubBean> list, LocalesAdheridosSuperClub localesAdheridosSuperClub);

    void showSubcategoriesList();
}
