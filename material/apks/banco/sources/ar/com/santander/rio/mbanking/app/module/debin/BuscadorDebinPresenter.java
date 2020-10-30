package ar.com.santander.rio.mbanking.app.module.debin;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GetDebinesBusquedaAvanzadaEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDebinesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDebines;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;

public class BuscadorDebinPresenter extends BasePresenter<BuscadorDebinView> {
    private Context a;
    private SessionManager b;

    public BuscadorDebinPresenter(Bus bus, IDataManager iDataManager, Context context, SessionManager sessionManager) {
        super(bus, iDataManager);
        this.a = context;
        this.b = sessionManager;
    }

    public void onCreatePage() {
        ((BuscadorDebinView) getBaseView()).setBuscadorView();
    }

    public void filtrarDebines(String str, String str2, String str3, String str4) {
        ((BuscadorDebinView) getBaseView()).showProgressIndicator(VGetDebines.nameService);
        this.mDataManager.getDebinesBusquedaAvanzada(str, str2, str3, str4, "");
    }

    @Subscribe
    public void onGetDebinesBusquedaAvanzada(GetDebinesBusquedaAvanzadaEvent getDebinesBusquedaAvanzadaEvent) {
        ((BuscadorDebinView) getBaseView()).dismissProgressIndicator();
        final GetDebinesBusquedaAvanzadaEvent getDebinesBusquedaAvanzadaEvent2 = getDebinesBusquedaAvanzadaEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.DEBIN, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                BuscadorDebinPresenter.this.a(getDebinesBusquedaAvanzadaEvent2);
            }
        };
        r1.handleWSResponse(getDebinesBusquedaAvanzadaEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetDebinesBusquedaAvanzadaEvent getDebinesBusquedaAvanzadaEvent) {
        try {
            GetDebinesBodyResponseBean debinesBodyResponseBean = getDebinesBusquedaAvanzadaEvent.getResponse().getDebinesBodyResponseBean();
            if (!debinesBodyResponseBean.getListaDebinesBean().getListDebinesBean().isEmpty()) {
                ((BuscadorDebinView) getBaseView()).setResultados((ArrayList) debinesBodyResponseBean.getListaDebinesBean().getListDebinesBean(), debinesBodyResponseBean.getSiguientePagina(), "");
            } else {
                ((BuscadorDebinView) getBaseView()).setResultados(new ArrayList(), debinesBodyResponseBean.getSiguientePagina(), debinesBodyResponseBean.resDesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
