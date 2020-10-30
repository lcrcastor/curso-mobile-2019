package ar.com.santander.rio.mbanking.app.module.seguros;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetCotizacionSeguroMovilEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizacionSeguroMovil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class SeguroMovilPresenter extends BasePresenter<SeguroMovilView> {
    private Context a;

    public SeguroMovilPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.a = context;
    }

    public void onCreatePage() {
        ((SeguroMovilView) getBaseView()).setContratarSeguroMovilView();
    }

    public void obtenerCotizacionSeguroMovil(Context context) {
        this.a = context;
        ((SeguroMovilView) getBaseView()).showProgressIndicator(VGetCotizacionSeguroMovil.nameService);
        this.mDataManager.getCotizacionSeguroMovil();
    }

    @Subscribe
    public void onGetCotizacionSeguroMovil(GetCotizacionSeguroMovilEvent getCotizacionSeguroMovilEvent) {
        ((SeguroMovilView) getBaseView()).dismissProgressIndicator();
        final GetCotizacionSeguroMovilEvent getCotizacionSeguroMovilEvent2 = getCotizacionSeguroMovilEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.SEGUROS, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                SeguroMovilPresenter.this.a(getCotizacionSeguroMovilEvent2);
            }
        };
        r1.handleWSResponse(getCotizacionSeguroMovilEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetCotizacionSeguroMovilEvent getCotizacionSeguroMovilEvent) {
        ((SeguroMovilView) getBaseView()).goToSeleccionarCobertura(getCotizacionSeguroMovilEvent.getResponse().getGetCotizacionSeguroMovilBodyResponseBean().getCotizacion());
    }
}
