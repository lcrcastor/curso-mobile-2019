package ar.com.santander.rio.mbanking.app.ui.activities;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import butterknife.ButterKnife.Finder;

public class SubcategoriesListSuperClubActivity$$ViewInjector {
    public static void inject(Finder finder, SubcategoriesListSuperClubActivity subcategoriesListSuperClubActivity, Object obj) {
        subcategoriesListSuperClubActivity.screenSuperClub = (RelativeLayout) finder.findRequiredView(obj, R.id.F20_00_LNL, "field 'screenSuperClub'");
        subcategoriesListSuperClubActivity.homeSuperClub = (LinearLayout) finder.findRequiredView(obj, R.id.F20_00_LNL_HOME, "field 'homeSuperClub'");
        subcategoriesListSuperClubActivity.txtPoints = (TextView) finder.findRequiredView(obj, R.id.F20_00_LBL_DATA_POINTS, "field 'txtPoints'");
        subcategoriesListSuperClubActivity.txtSearch = (ClearableEditText) finder.findRequiredView(obj, R.id.F20_00_INP_SEARCH, "field 'txtSearch'");
        subcategoriesListSuperClubActivity.lstData = (RecyclerView) finder.findRequiredView(obj, R.id.F20_00_LST_DATA, "field 'lstData'");
        subcategoriesListSuperClubActivity.lnlSearch = (RelativeLayout) finder.findRequiredView(obj, R.id.F20_00_LNL_SEARCH, "field 'lnlSearch'");
        subcategoriesListSuperClubActivity.lstSearch = (RecyclerView) finder.findRequiredView(obj, R.id.F20_00_LST_SEARCH, "field 'lstSearch'");
        subcategoriesListSuperClubActivity.headersuperclub = (RelativeLayout) finder.findRequiredView(obj, R.id.F20_00_RLL_HEADER, "field 'headersuperclub'");
    }

    public static void reset(SubcategoriesListSuperClubActivity subcategoriesListSuperClubActivity) {
        subcategoriesListSuperClubActivity.screenSuperClub = null;
        subcategoriesListSuperClubActivity.homeSuperClub = null;
        subcategoriesListSuperClubActivity.txtPoints = null;
        subcategoriesListSuperClubActivity.txtSearch = null;
        subcategoriesListSuperClubActivity.lstData = null;
        subcategoriesListSuperClubActivity.lnlSearch = null;
        subcategoriesListSuperClubActivity.lstSearch = null;
        subcategoriesListSuperClubActivity.headersuperclub = null;
    }
}
