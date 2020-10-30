package ar.com.santander.rio.mbanking.app.module.seguros;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetFamiliasObjetosEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizacionCompraProtegida;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetPreguntasFamilia;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class SeguroFamiliaObjetosPresenter extends BasePresenter<SeguroFamiliaObjetosView> {
    private Context a;

    public SeguroFamiliaObjetosPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.a = context;
    }

    public void onCreatePage() {
        ((SeguroFamiliaObjetosView) getBaseView()).showOnBoarding();
    }

    public void obtenerFamiliaObjetos() {
        ((SeguroFamiliaObjetosView) getBaseView()).showProgressIndicator(VGetCotizacionCompraProtegida.nameService);
        this.mDataManager.getFamiliasObjetos();
    }

    public void obtenerPreguntasObjetos(String str) {
        ((SeguroFamiliaObjetosView) getBaseView()).showProgressIndicator(VGetPreguntasFamilia.nameService);
        this.mDataManager.getPreguntasFamilia(str);
    }

    @Subscribe
    public void onGetFamiliaObjetos(GetFamiliasObjetosEvent getFamiliasObjetosEvent) {
        ((SeguroFamiliaObjetosView) getBaseView()).dismissProgressIndicator();
        final GetFamiliasObjetosEvent getFamiliasObjetosEvent2 = getFamiliasObjetosEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.SEGUROS, getBaseView(), this.a.getString(R.string.ID_4057_SEGUROS_LBL_SOL_SGRO), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                SeguroFamiliaObjetosPresenter.this.a(getFamiliasObjetosEvent2);
            }
        };
        r1.handleWSResponse(getFamiliasObjetosEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetFamiliasObjetosEvent getFamiliasObjetosEvent) {
        ((SeguroFamiliaObjetosView) getBaseView()).gotoFamiliaObjeto(getFamiliasObjetosEvent.getResponse().getFamiliasObjetosBodyResponseBean);
    }
}
