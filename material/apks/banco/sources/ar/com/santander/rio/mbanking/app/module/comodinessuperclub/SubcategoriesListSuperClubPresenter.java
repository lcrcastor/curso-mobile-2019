package ar.com.santander.rio.mbanking.app.module.comodinessuperclub;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetCuponesSubcategorySuperClubEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCuponesSuperClub;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class SubcategoriesListSuperClubPresenter extends BasePresenter<SubcategoriesListSuperClubView> {
    protected Context mContext;

    public SubcategoriesListSuperClubPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
    }

    public void getCuponesSuperClub(String str, String str2, String str3) {
        ((SubcategoriesListSuperClubView) this.mBaseView).showProgressIndicator(VGetCuponesSuperClub.nameService);
        this.mDataManager.getCuponesSubcategorySuperClub(str, Html.fromHtml(str2).toString(), str3);
    }

    @Subscribe
    public void onGetCuponesSuperClub(GetCuponesSubcategorySuperClubEvent getCuponesSubcategorySuperClubEvent) {
        ((SubcategoriesListSuperClubView) getBaseView()).dismissProgressIndicator();
        final GetCuponesSubcategorySuperClubEvent getCuponesSubcategorySuperClubEvent2 = getCuponesSubcategorySuperClubEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.INTERMDIATE_VIEW, FragmentConstants.COMODINES_SUPERCLUB, getBaseView(), (BaseActivity) this.mContext) {
            public void onOk() {
                SubcategoriesListSuperClubPresenter.this.a(getCuponesSubcategorySuperClubEvent2);
            }
        };
        r1.handleWSResponse(getCuponesSubcategorySuperClubEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetCuponesSubcategorySuperClubEvent getCuponesSubcategorySuperClubEvent) {
        try {
            ((SubcategoriesListSuperClubView) getBaseView()).showComodinesList(getCuponesSubcategorySuperClubEvent.getResponse().getCuponesBodyResponseBean.cupones.cupon, getCuponesSubcategorySuperClubEvent.getResponse().getCuponesBodyResponseBean.localesAdheridos);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onGetCuponesSuperClubResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    public void onSubcategorySelected(CategoriaSuperClubBean categoriaSuperClubBean) {
        getCuponesSuperClub(categoriaSuperClubBean.idClase, categoriaSuperClubBean.nombre, categoriaSuperClubBean.idAtributo);
    }

    public void showSubcategoriesList() {
        ((SubcategoriesListSuperClubView) getBaseView()).showSubcategoriesList();
    }
}
