package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import butterknife.ButterKnife.Finder;

public class CategoriesListSuperClubFragment$$ViewInjector {
    public static void inject(Finder finder, CategoriesListSuperClubFragment categoriesListSuperClubFragment, Object obj) {
        categoriesListSuperClubFragment.screenSuperClub = (RelativeLayout) finder.findRequiredView(obj, R.id.F20_00_LNL, "field 'screenSuperClub'");
        categoriesListSuperClubFragment.homeSuperClub = (LinearLayout) finder.findRequiredView(obj, R.id.F20_00_LNL_HOME, "field 'homeSuperClub'");
        categoriesListSuperClubFragment.txtPoints = (TextView) finder.findRequiredView(obj, R.id.F20_00_LBL_DATA_POINTS, "field 'txtPoints'");
        categoriesListSuperClubFragment.txtSearch = (ClearableEditText) finder.findRequiredView(obj, R.id.F20_00_INP_SEARCH, "field 'txtSearch'");
        categoriesListSuperClubFragment.lstData = (RecyclerView) finder.findRequiredView(obj, R.id.F20_00_LST_DATA, "field 'lstData'");
        categoriesListSuperClubFragment.lnlSearch = (RelativeLayout) finder.findRequiredView(obj, R.id.F20_00_LNL_SEARCH, "field 'lnlSearch'");
        categoriesListSuperClubFragment.lstSearch = (RecyclerView) finder.findRequiredView(obj, R.id.F20_00_LST_SEARCH, "field 'lstSearch'");
        categoriesListSuperClubFragment.headersuperclub = (RelativeLayout) finder.findRequiredView(obj, R.id.F20_00_RLL_HEADER, "field 'headersuperclub'");
    }

    public static void reset(CategoriesListSuperClubFragment categoriesListSuperClubFragment) {
        categoriesListSuperClubFragment.screenSuperClub = null;
        categoriesListSuperClubFragment.homeSuperClub = null;
        categoriesListSuperClubFragment.txtPoints = null;
        categoriesListSuperClubFragment.txtSearch = null;
        categoriesListSuperClubFragment.lstData = null;
        categoriesListSuperClubFragment.lnlSearch = null;
        categoriesListSuperClubFragment.lstSearch = null;
        categoriesListSuperClubFragment.headersuperclub = null;
    }
}
