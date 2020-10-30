package ar.com.santander.rio.mbanking.app.module.recargaSube;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.module.payments.commons.TypeDebt;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.services.events.CnsDeudaEvent;
import ar.com.santander.rio.mbanking.services.events.CnsEmpresaEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class RegistrarTarjetaRecargaSubePresenter extends BasePresenter<RegistrarTarjetaRecargaSubeView> {
    private static String a = "SUBE";
    protected boolean backFromWSError = false;
    protected Context mContext;
    protected SettingsManager mSettingsManager;

    /* access modifiers changed from: private */
    public void a(CnsDeudaEvent cnsDeudaEvent) {
    }

    public RegistrarTarjetaRecargaSubePresenter(Bus bus, IDataManager iDataManager, SettingsManager settingsManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
        this.mSettingsManager = settingsManager;
    }

    public void callRecargaSubeWs() {
        ((RegistrarTarjetaRecargaSubeView) this.mBaseView).showProgressIndicator("");
        a();
        onSubeCnsEmpresaSelected();
    }

    private void a() {
        this.mDataManager.getCnsDeuda(new CnsDeudaBodyRequestBean(TypeDebt.PS.name()));
    }

    /* access modifiers changed from: protected */
    public void cnsEmpresa(String str) {
        this.mDataManager.cnsEmpresa(str);
    }

    public void onSubeCnsEmpresaSelected() {
        cnsEmpresa(a);
    }

    @Subscribe
    public void onCnsDeuda(CnsDeudaEvent cnsDeudaEvent) {
        final CnsDeudaEvent cnsDeudaEvent2 = cnsDeudaEvent;
        AnonymousClass1 r0 = new BaseWSResponseHandler(this.mContext, TypeOption.INITIAL_VIEW, FragmentConstants.PAGO_SERVICIOS, getBaseView(), "Recarga SUBE", (BaseActivity) this.mContext) {
            public void onOk() {
                RegistrarTarjetaRecargaSubePresenter.this.a(cnsDeudaEvent2);
            }
        };
        r0.handleWSResponse(cnsDeudaEvent);
    }

    @Subscribe
    public void onCnsEmpresa(CnsEmpresaEvent cnsEmpresaEvent) {
        ((RegistrarTarjetaRecargaSubeView) this.mBaseView).dismissProgressIndicator();
        final CnsEmpresaEvent cnsEmpresaEvent2 = cnsEmpresaEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.INTERMDIATE_VIEW, FragmentConstants.PAGO_SERVICIOS, getBaseView(), (BaseActivity) this.mContext) {
            public void onOk() {
                RegistrarTarjetaRecargaSubePresenter.this.a(cnsEmpresaEvent2);
            }

            public void commonAllErrorsBeforeProcess(WebServiceEvent webServiceEvent) {
                ((RegistrarTarjetaRecargaSubeView) RegistrarTarjetaRecargaSubePresenter.this.getBaseView()).dismissProgressIndicator();
                RegistrarTarjetaRecargaSubePresenter.this.backFromWSError = true;
            }
        };
        r1.handleWSResponse(cnsEmpresaEvent);
    }

    /* access modifiers changed from: private */
    public void a(CnsEmpresaEvent cnsEmpresaEvent) {
        CnsEmpresaBodyResponseBean cnsEmpresaBodyResponseBean = cnsEmpresaEvent.getResponse().cnsEmpresaBodyResponseBean;
        List arrayList = new ArrayList();
        if (cnsEmpresaBodyResponseBean.cuentas != null) {
            arrayList = cnsEmpresaEvent.getResponse().cnsEmpresaBodyResponseBean.cuentas.lstCuentaDeudas;
        }
        CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa = cnsEmpresaBodyResponseBean.datosEmpresa;
        DatosDeudaBean datosDeudaBean = (DatosDeudaBean) cnsEmpresaBodyResponseBean.datosDeudas.lstDatosDeudaBean.get(0);
        if (String.valueOf(cnsEmpresaDatosEmpresa.tipoPago).equalsIgnoreCase("3") || (cnsEmpresaDatosEmpresa.tipoPago.equalsIgnoreCase("1") && !String.valueOf(datosDeudaBean.validaciones.tipoId).equalsIgnoreCase("2"))) {
            ((RegistrarTarjetaRecargaSubeView) getBaseView()).goToTarjetaSubeRegisterFlow(arrayList, cnsEmpresaDatosEmpresa, datosDeudaBean);
        }
    }
}
