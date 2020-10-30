package ar.com.santander.rio.mbanking.app.module.nuevopago;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.services.events.CnsEmpresaEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VCnsEmpresa;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class BuscarEmpresaNuevoPagoPresenter extends BasePresenter<BuscarEmpresaNuevoPagoView> {
    protected boolean backFromWSError = false;
    protected Context mContext;
    protected SettingsManager mSettingsManager;

    public BuscarEmpresaNuevoPagoPresenter(Bus bus, IDataManager iDataManager, SettingsManager settingsManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
        this.mSettingsManager = settingsManager;
        this.backFromWSError = false;
    }

    public void cnsEmpresa() {
        cnsEmpresa("");
    }

    /* access modifiers changed from: protected */
    public void cnsEmpresa(String str) {
        ((BuscarEmpresaNuevoPagoView) getBaseView()).showProgressIndicator(VCnsEmpresa.nameService);
        this.mDataManager.cnsEmpresa(str);
    }

    @Subscribe
    public void onCnsEmpresa(CnsEmpresaEvent cnsEmpresaEvent) {
        ((BuscarEmpresaNuevoPagoView) getBaseView()).dismissProgressIndicator();
        final CnsEmpresaEvent cnsEmpresaEvent2 = cnsEmpresaEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.INTERMDIATE_VIEW, FragmentConstants.PAGO_SERVICIOS, getBaseView(), (BaseActivity) this.mContext) {
            public void onOk() {
                BuscarEmpresaNuevoPagoPresenter.this.a(cnsEmpresaEvent2);
            }

            public void commonAllErrorsBeforeProcess(WebServiceEvent webServiceEvent) {
                BuscarEmpresaNuevoPagoPresenter.this.backFromWSError = true;
            }
        };
        r1.handleWSResponse(cnsEmpresaEvent);
    }

    /* access modifiers changed from: private */
    public void a(CnsEmpresaEvent cnsEmpresaEvent) {
        try {
            if (cnsEmpresaEvent.getResponse().cnsEmpresaBodyResponseBean.listaEmpresa != null) {
                ((BuscarEmpresaNuevoPagoView) getBaseView()).showFindCompany(cnsEmpresaEvent.getResponse().cnsEmpresaBodyResponseBean.listaEmpresa.getLstCnsEmpresaDatosEmpresa());
                ((BuscarEmpresaNuevoPagoView) getBaseView()).setTxtDescription(Html.fromHtml(cnsEmpresaEvent.getResponse().cnsEmpresaBodyResponseBean.leyendaAyuda));
                return;
            }
            CnsEmpresaBodyResponseBean cnsEmpresaBodyResponseBean = cnsEmpresaEvent.getResponse().cnsEmpresaBodyResponseBean;
            List arrayList = new ArrayList();
            if (cnsEmpresaBodyResponseBean.cuentas != null) {
                arrayList = cnsEmpresaEvent.getResponse().cnsEmpresaBodyResponseBean.cuentas.lstCuentaDeudas;
            }
            CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa = cnsEmpresaBodyResponseBean.datosEmpresa;
            DatosDeudaBean datosDeudaBean = (DatosDeudaBean) cnsEmpresaBodyResponseBean.datosDeudas.lstDatosDeudaBean.get(0);
            if (cnsEmpresaDatosEmpresa.empServ.equalsIgnoreCase("SUBE")) {
                ((BuscarEmpresaNuevoPagoView) getBaseView()).showPhoneAddPayment(arrayList, cnsEmpresaDatosEmpresa, datosDeudaBean);
                return;
            }
            if (!String.valueOf(cnsEmpresaDatosEmpresa.tipoPago).equalsIgnoreCase("3")) {
                if (!cnsEmpresaDatosEmpresa.tipoPago.equalsIgnoreCase("1") || String.valueOf(datosDeudaBean.validaciones.tipoId).equalsIgnoreCase("2")) {
                    if (String.valueOf(cnsEmpresaDatosEmpresa.tipoPago).equalsIgnoreCase("1") && String.valueOf(datosDeudaBean.validaciones.tipoId).equalsIgnoreCase("2")) {
                        ((BuscarEmpresaNuevoPagoView) getBaseView()).showPhoneAddPayment(arrayList, cnsEmpresaDatosEmpresa, datosDeudaBean);
                        return;
                    } else if (String.valueOf(cnsEmpresaDatosEmpresa.tipoPago).equalsIgnoreCase("2")) {
                        ((BuscarEmpresaNuevoPagoView) getBaseView()).showAfipAddPayment(arrayList, cnsEmpresaDatosEmpresa, datosDeudaBean);
                        return;
                    } else {
                        return;
                    }
                }
            }
            ((BuscarEmpresaNuevoPagoView) getBaseView()).showElectronicAddPayment(arrayList, cnsEmpresaDatosEmpresa, datosDeudaBean);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onCnsEmpresaResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    public boolean isBackFromWSError() {
        return this.backFromWSError;
    }

    public void showFindCompany() {
        cnsEmpresa();
    }

    public void onCompanySelected(CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa) {
        cnsEmpresa(cnsEmpresaDatosEmpresa.empServ);
    }
}
