package ar.com.santander.rio.mbanking.app.module.comodinessuperclub;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalesAdheridosSuperClub;
import java.util.List;

public interface CategoriesListSuperClubView extends ListSuperClubView {
    Context getContext();

    void makeVisibleHomeScreen();

    void showCategoriesList(String str);

    void showCategoriesList(List<CategoriaSuperClubBean> list);

    void showComodinesList(List<CuponSuperClubBean> list);

    void showComodinesList(List<CuponSuperClubBean> list, LocalesAdheridosSuperClub localesAdheridosSuperClub);

    void showOnBoarding();

    void showSadError(String str);

    void showSubcategoriesList(List<CategoriaSuperClubBean> list);
}
