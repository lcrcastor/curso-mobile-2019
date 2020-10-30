package ar.com.santander.rio.mbanking.app.module.seguros;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants.TipoAlta;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetCotizacionCompraProtegidaEvent;
import ar.com.santander.rio.mbanking.services.events.GetCotizacionSeguroMovilEvent;
import ar.com.santander.rio.mbanking.services.events.GetFamiliasObjetosEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SegurosBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizacionCompraProtegida;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizacionSeguroMovil;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetPreguntasFamilia;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ContratarSeguroPresenter extends BasePresenter<ContratarSeguroView> {
    private Context a;
    private String b;
    private SegurosBean c;

    public ContratarSeguroPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.a = context;
    }

    public void onCreatePage(String str, SegurosBean segurosBean) {
        this.b = str;
        this.c = segurosBean;
        ((ContratarSeguroView) getBaseView()).showOnBoarding();
    }

    public void contratarSeguroMovil() {
        if (this.b.equals(TipoAlta.DISPOSITIVO_ASEGURADO) || this.b.equals(TipoAlta.SEGURO_CARTERA)) {
            ((ContratarSeguroView) getBaseView()).gotoSeguroMovil(this.b, this.c, new CotizacionBean());
        } else {
            obtenerCotizacionSeguroMovil();
        }
    }

    public void obtenerCotizacionSeguroMovil() {
        ((ContratarSeguroView) getBaseView()).showProgressIndicator(VGetCotizacionSeguroMovil.nameService);
        this.mDataManager.getCotizacionSeguroMovil();
    }

    public void goToContratarSeguroMovilSinCotizar() {
        ((ContratarSeguroView) getBaseView()).goToContratarSeguroMovilSinCotizar();
    }

    @Subscribe
    public void onGetCotizacionSeguroMovil(GetCotizacionSeguroMovilEvent getCotizacionSeguroMovilEvent) {
        ((ContratarSeguroView) getBaseView()).dismissProgressIndicator();
        final GetCotizacionSeguroMovilEvent getCotizacionSeguroMovilEvent2 = getCotizacionSeguroMovilEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.SEGUROS, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                ContratarSeguroPresenter.this.a(getCotizacionSeguroMovilEvent2);
            }
        };
        r1.handleWSResponse(getCotizacionSeguroMovilEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetCotizacionSeguroMovilEvent getCotizacionSeguroMovilEvent) {
        ((ContratarSeguroView) getBaseView()).gotoSeguroMovil(this.b, this.c, getCotizacionSeguroMovilEvent.getResponse().getGetCotizacionSeguroMovilBodyResponseBean().getCotizacion());
    }

    public void obtenerCotizacionCompraProtegida() {
        ((ContratarSeguroView) getBaseView()).showProgressIndicator(VGetCotizacionCompraProtegida.nameService);
        this.mDataManager.getCotizacionCompraProtegida();
    }

    public void obtenerFamiliaObjetos() {
        ((ContratarSeguroView) getBaseView()).showProgressIndicator(VGetCotizacionCompraProtegida.nameService);
        this.mDataManager.getFamiliasObjetos();
    }

    public void obtenerPreguntasObjetos(String str) {
        ((ContratarSeguroView) getBaseView()).showProgressIndicator(VGetPreguntasFamilia.nameService);
        this.mDataManager.getPreguntasFamilia(str);
    }

    public void showProgress() {
        ((ContratarSeguroView) getBaseView()).showProgressIndicator(VGetCotizacionCompraProtegida.nameService);
    }

    public void closeProgress() {
        ((ContratarSeguroView) getBaseView()).dismissProgressIndicator();
    }

    @Subscribe
    public void onGetFamiliaObjetos(GetFamiliasObjetosEvent getFamiliasObjetosEvent) {
        ((ContratarSeguroView) getBaseView()).dismissProgressIndicator();
        final GetFamiliasObjetosEvent getFamiliasObjetosEvent2 = getFamiliasObjetosEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.SEGUROS, getBaseView(), this.a.getString(R.string.ID_4057_SEGUROS_LBL_SOL_SGRO), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                ContratarSeguroPresenter.this.a(getFamiliasObjetosEvent2);
            }
        };
        r1.handleWSResponse(getFamiliasObjetosEvent);
    }

    @Subscribe
    public void onGetCotizacionCompraProtegida(GetCotizacionCompraProtegidaEvent getCotizacionCompraProtegidaEvent) {
        ((ContratarSeguroView) getBaseView()).dismissProgressIndicator();
        final GetCotizacionCompraProtegidaEvent getCotizacionCompraProtegidaEvent2 = getCotizacionCompraProtegidaEvent;
        AnonymousClass3 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.SEGUROS, getBaseView(), this.a.getString(R.string.ID_4057_SEGUROS_LBL_SOL_SGRO), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                ContratarSeguroPresenter.this.a(getCotizacionCompraProtegidaEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes4Error(WebServiceEvent webServiceEvent) {
                ((ContratarSeguroView) ContratarSeguroPresenter.this.getBaseView()).showError(this.mErrorEvent.getMessageToShow(), R.drawable.sin_tenencia, this.mContext.getString(R.string.ID_4057_SEGUROS_LBL_SOL_SGRO));
            }
        };
        r1.handleWSResponse(getCotizacionCompraProtegidaEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetFamiliasObjetosEvent getFamiliasObjetosEvent) {
        ((ContratarSeguroView) getBaseView()).gotoFamiliaObjeto(getFamiliasObjetosEvent.getResponse().getFamiliasObjetosBodyResponseBean);
    }

    /* access modifiers changed from: private */
    public void a(GetCotizacionCompraProtegidaEvent getCotizacionCompraProtegidaEvent) {
        ((ContratarSeguroView) getBaseView()).gotoCompraProtegida(getCotizacionCompraProtegidaEvent.getResponse().getGetCotizacionCompraProtegidaBodyResponseBean().getCotizacion());
    }

    public void goToCompraProtegidaTarjetasYaAseguradas() {
        ((ContratarSeguroView) getBaseView()).gotoCompraProtegida(null);
    }
}
