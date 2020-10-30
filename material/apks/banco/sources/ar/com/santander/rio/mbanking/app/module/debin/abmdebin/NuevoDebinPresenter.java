package ar.com.santander.rio.mbanking.app.module.debin.abmdebin;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.AbmDebinVendedorEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultarTitularCuentaEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DetalleDebinBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetAbmDebinVendedor;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetConsultarTitularCuenta;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class NuevoDebinPresenter extends BasePresenter<NuevoDebinView> {
    public NuevoDebinPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage() {
        ((NuevoDebinView) getBaseView()).setNuevoDebinView();
    }

    public Context getContext() {
        return ((NuevoDebinView) getBaseView()).getContext();
    }

    public void consultarTitular(String str, String str2) {
        ((NuevoDebinView) getBaseView()).showProgressIndicator(VGetConsultarTitularCuenta.nameService);
        this.mDataManager.consultarTitularCuenta(str, str2);
    }

    @Subscribe
    public void onGetConsultarTitular(ConsultarTitularCuentaEvent consultarTitularCuentaEvent) {
        ((NuevoDebinView) getBaseView()).dismissProgressIndicator();
        ((NuevoDebinView) getBaseView()).setLayoutSinCuentasVisible();
        final ConsultarTitularCuentaEvent consultarTitularCuentaEvent2 = consultarTitularCuentaEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(getContext(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.DEBIN, getBaseView(), (BaseActivity) getContext()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                NuevoDebinPresenter.this.a(consultarTitularCuentaEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes4Error() {
                NuevoDebinPresenter.this.b(consultarTitularCuentaEvent2);
            }
        };
        r1.handleWSResponse(consultarTitularCuentaEvent);
    }

    /* access modifiers changed from: private */
    public void a(ConsultarTitularCuentaEvent consultarTitularCuentaEvent) {
        try {
            ((NuevoDebinView) getBaseView()).setGenerarDebinView(consultarTitularCuentaEvent.getResponse().getConsultarTitularCuentaBodyResponseBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void b(ConsultarTitularCuentaEvent consultarTitularCuentaEvent) {
        try {
            ((NuevoDebinView) getBaseView()).setDebinRes4View(consultarTitularCuentaEvent.getMessageToShow(), Boolean.valueOf(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearDebin(String str, DetalleDebinBean detalleDebinBean) {
        ((NuevoDebinView) getBaseView()).showProgressIndicator(VGetAbmDebinVendedor.nameService);
        this.mDataManager.abmDebinVendedor(str, detalleDebinBean, null, (BaseActivity) getContext(), true);
    }

    @Subscribe
    public void onGetcrearDebin(AbmDebinVendedorEvent abmDebinVendedorEvent) {
        ((NuevoDebinView) getBaseView()).dismissProgressIndicator();
        final AbmDebinVendedorEvent abmDebinVendedorEvent2 = abmDebinVendedorEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(getContext(), TypeOption.NO_TRANSACTIONAL_FINAL_VIEW, FragmentConstants.DEBIN, getBaseView(), (BaseActivity) getContext()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                NuevoDebinPresenter.this.a(abmDebinVendedorEvent2);
            }
        };
        r1.handleWSResponse(abmDebinVendedorEvent);
    }

    /* access modifiers changed from: private */
    public void a(AbmDebinVendedorEvent abmDebinVendedorEvent) {
        try {
            ((NuevoDebinView) getBaseView()).setComprobanteDebinView(abmDebinVendedorEvent.getResponse().getAbmDebinVendedorBodyResponseBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
