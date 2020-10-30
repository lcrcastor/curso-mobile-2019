package ar.com.santander.rio.mbanking.app.module.seguros;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GetOcupacionesEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetOcupacionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetOcupaciones;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ContratacionSeguroPresenter extends BasePresenter<ContratacionSeguroView> {
    private Context a;
    private String b;

    public ContratacionSeguroPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(PlanSeguroBean planSeguroBean) {
        ((ContratacionSeguroView) getBaseView()).setContratacionSeguroView(planSeguroBean);
    }

    public void getOcupaciones(Context context, String str, GetOcupacionesBodyResponseBean getOcupacionesBodyResponseBean, SessionManager sessionManager) {
        this.a = context;
        this.b = str;
        if (getOcupacionesBodyResponseBean != null) {
            ((ContratacionSeguroView) getBaseView()).showSeleccionarOcupacion(getOcupacionesBodyResponseBean, this.b);
        } else if (sessionManager.getListaOcupaciones() == null || sessionManager.getListaOcupaciones().getOcupaciones().getListOcupaciones().isEmpty()) {
            ((ContratacionSeguroView) getBaseView()).showProgressIndicator(VGetOcupaciones.nameService);
            this.mDataManager.getOcupaciones();
        } else {
            ((ContratacionSeguroView) getBaseView()).showSeleccionarOcupacion(sessionManager.getListaOcupaciones(), this.b);
        }
    }

    @Subscribe
    public void onGetSeleccionarOcupacion(GetOcupacionesEvent getOcupacionesEvent) {
        ((ContratacionSeguroView) getBaseView()).dismissProgressIndicator();
        final GetOcupacionesEvent getOcupacionesEvent2 = getOcupacionesEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.SEGUROS, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                ContratacionSeguroPresenter.this.onGetSeleccionarOcupacionOK(getOcupacionesEvent2);
            }
        };
        r1.handleWSResponse(getOcupacionesEvent);
    }

    public void onGetSeleccionarOcupacionOK(GetOcupacionesEvent getOcupacionesEvent) {
        ((ContratacionSeguroView) getBaseView()).showSeleccionarOcupacion(getOcupacionesEvent.getResponse().getGetOcupacionesBodyResponseBean(), this.b);
    }
}
