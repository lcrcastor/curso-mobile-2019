package ar.com.santander.rio.mbanking.app.module.recargaSube;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.services.events.GetRecargasEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.versions.VRecargas;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.List;

public class ObtenerTarjetasRecargaSubePresenter extends BasePresenter<ObtenerTarjetasRecargaSubeView> {
    protected Context mContext;
    protected SettingsManager mSettingsManager;

    public ObtenerTarjetasRecargaSubePresenter(Bus bus, IDataManager iDataManager, SettingsManager settingsManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
        this.mSettingsManager = settingsManager;
    }

    public void getTarjetasSubeData(String str, String str2, String str3, String str4, String str5) {
        ((ObtenerTarjetasRecargaSubeView) getBaseView()).showProgressIndicator(VRecargas.nameService);
        this.mDataManager.getRecargas(str, str2, str3, str4, str5);
    }

    @Subscribe
    public void onGetRecargas(GetRecargasEvent getRecargasEvent) {
        ((ObtenerTarjetasRecargaSubeView) getBaseView()).dismissProgressIndicator();
        final GetRecargasEvent getRecargasEvent2 = getRecargasEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.INTERMDIATE_VIEW, FragmentConstants.SEGUROS, getBaseView(), "RECARGA_SUBE", (BaseActivity) this.mContext) {
            public void onOk() {
                ObtenerTarjetasRecargaSubePresenter.this.a(getRecargasEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes4Error(WebServiceEvent webServiceEvent) {
                ObtenerTarjetasRecargaSubePresenter.this.a(webServiceEvent);
            }
        };
        r1.handleWSResponse(getRecargasEvent);
    }

    /* access modifiers changed from: private */
    public void a(WebServiceEvent webServiceEvent) {
        ((ObtenerTarjetasRecargaSubeView) getBaseView()).callErrorScreen(webServiceEvent.getErrorBodyBean().resTitle, webServiceEvent.getErrorBodyBean().resDesc);
    }

    /* access modifiers changed from: private */
    public void a(GetRecargasEvent getRecargasEvent) {
        List listaCuenta = getRecargasEvent.getResponse().getRecargasBodyResponseBean.getListaCuenta();
        List listaRecargas = getRecargasEvent.getResponse().getRecargasBodyResponseBean.getListaRecargas();
        List listaValores = getRecargasEvent.getResponse().getRecargasBodyResponseBean.getListaValores();
        String sesionUsuario = getRecargasEvent.getResponse().getRecargasBodyResponseBean.getSesionUsuario();
        String titulo = getRecargasEvent.getResponse().getRecargasBodyResponseBean.getTitulo();
        String descripcion = getRecargasEvent.getResponse().getRecargasBodyResponseBean.getDescripcion();
        if (listaCuenta == null || listaRecargas == null || listaValores == null) {
            ((ObtenerTarjetasRecargaSubeView) getBaseView()).goToRegistrarSube(titulo, descripcion);
        } else {
            ((ObtenerTarjetasRecargaSubeView) getBaseView()).getTarjetas(listaCuenta, listaRecargas, listaValores, sesionUsuario, titulo, descripcion);
        }
    }
}
