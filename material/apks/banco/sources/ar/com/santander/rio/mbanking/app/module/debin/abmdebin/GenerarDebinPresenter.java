package ar.com.santander.rio.mbanking.app.module.debin.abmdebin;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.ConsultarTitularCuentaEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetConsultarTitularCuenta;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class GenerarDebinPresenter extends BasePresenter<GenerarDebinView> {
    public void onCreatePage() {
    }

    public GenerarDebinPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void consultarTitular(String str, String str2) {
        ((GenerarDebinView) getBaseView()).showProgressIndicator(VGetConsultarTitularCuenta.nameService);
        this.mDataManager.consultarTitularCuenta(str, str2);
    }

    @Subscribe
    public void onGetConsultarTitular(ConsultarTitularCuentaEvent consultarTitularCuentaEvent) {
        ((GenerarDebinView) getBaseView()).dismissProgressIndicator();
        final ConsultarTitularCuentaEvent consultarTitularCuentaEvent2 = consultarTitularCuentaEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(((GenerarDebinView) getBaseView()).getContext(), TypeOption.INITIAL_VIEW, FragmentConstants.DEBIN, getBaseView(), (BaseActivity) ((GenerarDebinView) getBaseView()).getContext()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                GenerarDebinPresenter.this.a(consultarTitularCuentaEvent2);
            }
        };
        r1.handleWSResponse(consultarTitularCuentaEvent);
    }

    /* access modifiers changed from: private */
    public void a(ConsultarTitularCuentaEvent consultarTitularCuentaEvent) {
        try {
            ((GenerarDebinView) getBaseView()).setGenerarDebinView(consultarTitularCuentaEvent.getResponse().getConsultarTitularCuentaBodyResponseBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
