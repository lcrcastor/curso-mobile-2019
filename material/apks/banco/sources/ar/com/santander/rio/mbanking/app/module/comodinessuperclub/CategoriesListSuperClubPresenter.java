package ar.com.santander.rio.mbanking.app.module.comodinessuperclub;

import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetCategoriasSuperClubEvent;
import ar.com.santander.rio.mbanking.services.events.GetCuponesCategorySuperClubEvent;
import ar.com.santander.rio.mbanking.services.events.GetPuntosSuperClubEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCuponesSuperClub;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetPuntosSuperClub;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class CategoriesListSuperClubPresenter extends BasePresenter<CategoriesListSuperClubView> {
    private final int a = 99;
    private final int b = 0;
    private final int c = -1;
    private int d = 99;
    private int e = 99;
    /* access modifiers changed from: private */
    public CategoriaSuperClubBean f;
    protected String mAuxIdAtributo;

    public CategoriesListSuperClubPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void getPuntosSuperClub() {
        this.mDataManager.getPuntosSuperClub();
    }

    @Subscribe
    public void onGetPuntosSuperClub(GetPuntosSuperClubEvent getPuntosSuperClubEvent) {
        if (this.e == 99 || this.e == 0) {
            final GetPuntosSuperClubEvent getPuntosSuperClubEvent2 = getPuntosSuperClubEvent;
            AnonymousClass1 r1 = new BaseWSResponseHandler(((CategoriesListSuperClubView) getBaseView()).getContext(), TypeOption.INITIAL_VIEW, FragmentConstants.COMODINES_SUPERCLUB, getBaseView(), (BaseActivity) ((CategoriesListSuperClubView) getBaseView()).getContext()) {
                public void onOk() {
                    CategoriesListSuperClubPresenter.this.a(getPuntosSuperClubEvent2);
                }

                public void onRes4Error(WebServiceEvent webServiceEvent) {
                    CategoriesListSuperClubPresenter.this.b(getPuntosSuperClubEvent2);
                }

                public void commonAllErrorsAfterProcess(WebServiceEvent webServiceEvent) {
                    CategoriesListSuperClubPresenter.this.c(getPuntosSuperClubEvent2);
                }
            };
            r1.handleWSResponse(getPuntosSuperClubEvent);
        }
    }

    /* access modifiers changed from: private */
    public void a(GetPuntosSuperClubEvent getPuntosSuperClubEvent) {
        try {
            this.d = 0;
            ((CategoriesListSuperClubView) getBaseView()).showCategoriesList(getPuntosSuperClubEvent.getResponse().getPuntosBodyResponseBean.puntosSuperClub);
            if (this.e == 0) {
                ((CategoriesListSuperClubView) getBaseView()).dismissProgressIndicator();
                ((CategoriesListSuperClubView) getBaseView()).makeVisibleHomeScreen();
                ((CategoriesListSuperClubView) getBaseView()).showOnBoarding();
            }
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "getPuntosSuperClubResultOk: ", e2);
            e2.fillInStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void b(GetPuntosSuperClubEvent getPuntosSuperClubEvent) {
        try {
            this.d = -1;
            ((CategoriesListSuperClubView) getBaseView()).dismissProgressIndicator();
            ((CategoriesListSuperClubView) getBaseView()).showSadError(getPuntosSuperClubEvent.getMessageToShow());
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "getPuntosSuperClubResult4: ", e2);
            e2.fillInStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void c(GetPuntosSuperClubEvent getPuntosSuperClubEvent) {
        try {
            this.d = -1;
            ((CategoriesListSuperClubView) getBaseView()).dismissProgressIndicator();
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "getPuntosSuperClubCommonErrorsResult: ", e2);
            e2.fillInStackTrace();
        }
    }

    public void getCategoriasSuperClub() {
        this.mDataManager.getCategoriasSuperClub();
    }

    @Subscribe
    public void onGetCategoriasSuperClub(GetCategoriasSuperClubEvent getCategoriasSuperClubEvent) {
        if (this.d == 99 || this.d == 0) {
            final GetCategoriasSuperClubEvent getCategoriasSuperClubEvent2 = getCategoriasSuperClubEvent;
            AnonymousClass2 r1 = new BaseWSResponseHandler(((CategoriesListSuperClubView) getBaseView()).getContext(), TypeOption.INITIAL_VIEW, FragmentConstants.COMODINES_SUPERCLUB, getBaseView(), (BaseActivity) ((CategoriesListSuperClubView) getBaseView()).getContext()) {
                public void onOk() {
                    CategoriesListSuperClubPresenter.this.a(getCategoriasSuperClubEvent2);
                }

                public void onRes4Error(WebServiceEvent webServiceEvent) {
                    CategoriesListSuperClubPresenter.this.b(getCategoriasSuperClubEvent2);
                }

                public void commonAllErrorsAfterProcess(WebServiceEvent webServiceEvent) {
                    CategoriesListSuperClubPresenter.this.c(getCategoriasSuperClubEvent2);
                }
            };
            r1.handleWSResponse(getCategoriasSuperClubEvent);
        }
    }

    /* access modifiers changed from: private */
    public void a(GetCategoriasSuperClubEvent getCategoriasSuperClubEvent) {
        try {
            this.e = 0;
            ((CategoriesListSuperClubView) getBaseView()).showCategoriesList(getCategoriasSuperClubEvent.getResponse().getCategoriasBodyResponseBean.categorias.categoria);
            if (this.d == 0) {
                ((CategoriesListSuperClubView) getBaseView()).dismissProgressIndicator();
                ((CategoriesListSuperClubView) getBaseView()).makeVisibleHomeScreen();
                ((CategoriesListSuperClubView) getBaseView()).showOnBoarding();
            }
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "getCategoriasSuperClubResultOk: ", e2);
            e2.fillInStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void b(GetCategoriasSuperClubEvent getCategoriasSuperClubEvent) {
        try {
            this.e = -1;
            ((CategoriesListSuperClubView) getBaseView()).dismissProgressIndicator();
            ((CategoriesListSuperClubView) getBaseView()).showSadError(getCategoriasSuperClubEvent.getMessageToShow());
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "getCategoriasSuperClubResult4: ", e2);
            e2.fillInStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void c(GetCategoriasSuperClubEvent getCategoriasSuperClubEvent) {
        try {
            this.e = -1;
            ((CategoriesListSuperClubView) getBaseView()).dismissProgressIndicator();
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "getCategoriasSuperClubCommonErrorsResult: ", e2);
            e2.fillInStackTrace();
        }
    }

    public void getPuntosYCategorias() {
        ((CategoriesListSuperClubView) getBaseView()).showProgressIndicator(VGetPuntosSuperClub.nameService);
        this.d = 99;
        this.e = 99;
        getPuntosSuperClub();
        getCategoriasSuperClub();
    }

    public void getCuponesSuperClub(String str, String str2, String str3) {
        ((CategoriesListSuperClubView) getBaseView()).showProgressIndicator(VGetCuponesSuperClub.nameService);
        this.mDataManager.getCuponesCategorySuperClub(str, Html.fromHtml(str2).toString(), str3);
    }

    @Subscribe
    public void onGetCuponesSuperClub(GetCuponesCategorySuperClubEvent getCuponesCategorySuperClubEvent) {
        ((CategoriesListSuperClubView) getBaseView()).dismissProgressIndicator();
        final GetCuponesCategorySuperClubEvent getCuponesCategorySuperClubEvent2 = getCuponesCategorySuperClubEvent;
        AnonymousClass3 r1 = new BaseWSResponseHandler(((CategoriesListSuperClubView) getBaseView()).getContext(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.COMODINES_SUPERCLUB, getBaseView(), (BaseActivity) ((CategoriesListSuperClubView) getBaseView()).getContext()) {
            public void onOk() {
                CategoriesListSuperClubPresenter.this.a(getCuponesCategorySuperClubEvent2);
            }
        };
        r1.handleWSResponse(getCuponesCategorySuperClubEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetCuponesCategorySuperClubEvent getCuponesCategorySuperClubEvent) {
        try {
            if (TextUtils.isEmpty(this.mAuxIdAtributo)) {
                ((CategoriesListSuperClubView) getBaseView()).showComodinesList(getCuponesCategorySuperClubEvent.getResponse().getCuponesBodyResponseBean.cupones.cupon);
            } else if (getCuponesCategorySuperClubEvent.getResponse().getCuponesBodyResponseBean.hasStores().booleanValue()) {
                ((CategoriesListSuperClubView) getBaseView()).showComodinesList(getCuponesCategorySuperClubEvent.getResponse().getCuponesBodyResponseBean.cupones.cupon, getCuponesCategorySuperClubEvent.getResponse().getCuponesBodyResponseBean.localesAdheridos);
            } else {
                ((CategoriesListSuperClubView) getBaseView()).showComodinesList(getCuponesCategorySuperClubEvent.getResponse().getCuponesBodyResponseBean.cupones.cupon, null);
            }
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "getCuponesSuperClubResultOk: ", e2);
            e2.fillInStackTrace();
        }
    }

    public void onCategorySelected(CategoriaSuperClubBean categoriaSuperClubBean) {
        this.f = categoriaSuperClubBean;
        this.mAuxIdAtributo = categoriaSuperClubBean.idAtributo;
        if (categoriaSuperClubBean.hasSubcategories().booleanValue()) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    ((CategoriesListSuperClubView) CategoriesListSuperClubPresenter.this.getBaseView()).showSubcategoriesList(CategoriesListSuperClubPresenter.this.f.categorias.categoria);
                }
            }, 200);
        } else {
            getCuponesSuperClub(categoriaSuperClubBean.idClase, Html.fromHtml(categoriaSuperClubBean.nombre).toString(), categoriaSuperClubBean.idAtributo);
        }
    }
}
