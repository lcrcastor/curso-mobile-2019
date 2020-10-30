package ar.com.santander.rio.mbanking.app.module.debin.adhesion;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.ConsultarAdhesionVendedorEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaCuentasVendedorBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetConsultarAdhesionVendedor;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class GestionAdhesionDebinPresenter extends BasePresenter<GestionAdhesionDebinView> {
    private Context a;

    public GestionAdhesionDebinPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus);
        this.mDataManager = iDataManager;
        this.a = context;
    }

    public void onCreatePage() {
        ((GestionAdhesionDebinView) getBaseView()).setListaCuentasView();
    }

    public void onCuentaSelected() {
        ((GestionAdhesionDebinView) getBaseView()).gotoConfirmarGestionCuenta();
    }

    public void consultarAdhesionVendedor() {
        ((GestionAdhesionDebinView) getBaseView()).showProgressIndicator(VGetConsultarAdhesionVendedor.nameService);
        this.mDataManager.consultarAdhesionVendedor();
    }

    @Subscribe
    public void onConsultarAdhesionVendedor(ConsultarAdhesionVendedorEvent consultarAdhesionVendedorEvent) {
        ((GestionAdhesionDebinView) getBaseView()).dismissProgressIndicator();
        final ConsultarAdhesionVendedorEvent consultarAdhesionVendedorEvent2 = consultarAdhesionVendedorEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.DEBIN, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                GestionAdhesionDebinPresenter.this.a(consultarAdhesionVendedorEvent2);
            }
        };
        r1.handleWSResponse(consultarAdhesionVendedorEvent);
    }

    /* access modifiers changed from: private */
    public void a(ConsultarAdhesionVendedorEvent consultarAdhesionVendedorEvent) {
        try {
            if (!consultarAdhesionVendedorEvent.getResponse().getConsultarAdhesionVendedorBodyResponseBean().getListaCuentasVendedorBean().getCuentaVendedor().isEmpty()) {
                ((GestionAdhesionDebinView) getBaseView()).setCuentasAdheridas(consultarAdhesionVendedorEvent.getResponse().getConsultarAdhesionVendedorBodyResponseBean().getListaCuentasVendedorBean());
            } else {
                ((GestionAdhesionDebinView) getBaseView()).setCuentasAdheridas(new ListaCuentasVendedorBean());
            }
            ((GestionAdhesionDebinView) getBaseView()).setListaCuentasView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
