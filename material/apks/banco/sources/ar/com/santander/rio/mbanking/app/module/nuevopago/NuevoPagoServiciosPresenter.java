package ar.com.santander.rio.mbanking.app.module.nuevopago;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.module.payments.commons.TypeDebt;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.CnsDeudaEvent;
import ar.com.santander.rio.mbanking.services.events.CnsEmpresaEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VCndDeuda;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class NuevoPagoServiciosPresenter extends BasePresenter<NuevoPagoServiciosView> {
    /* access modifiers changed from: private */
    public boolean a;
    /* access modifiers changed from: private */
    public String b = "";

    public NuevoPagoServiciosPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void cnsDeuda() {
        ((NuevoPagoServiciosView) getBaseView()).showProgressIndicator(VCndDeuda.nameService);
        this.mDataManager.getCnsDeuda(new CnsDeudaBodyRequestBean(TypeDebt.PS.name()));
    }

    @Subscribe
    public void onCnsEmpresa(CnsEmpresaEvent cnsEmpresaEvent) {
        final CnsEmpresaEvent cnsEmpresaEvent2 = cnsEmpresaEvent;
        AnonymousClass1 r0 = new BaseWSResponseHandler(((NuevoPagoServiciosView) getBaseView()).getContext(), TypeOption.INITIAL_VIEW, FragmentConstants.PAGO_SERVICIOS, getBaseView(), (BaseActivity) ((NuevoPagoServiciosView) getBaseView()).getContext()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                try {
                    if (NuevoPagoServiciosPresenter.this.a) {
                        NuevoPagoServiciosPresenter.this.b = "";
                        ((NuevoPagoServiciosView) NuevoPagoServiciosPresenter.this.getBaseView()).dismissProgressIndicator();
                        CnsEmpresaBodyResponseBean cnsEmpresaBodyResponseBean = cnsEmpresaEvent2.getResponse().cnsEmpresaBodyResponseBean;
                        List arrayList = new ArrayList();
                        if (cnsEmpresaBodyResponseBean.cuentas != null) {
                            arrayList = cnsEmpresaEvent2.getResponse().cnsEmpresaBodyResponseBean.cuentas.lstCuentaDeudas;
                        }
                        CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa = cnsEmpresaBodyResponseBean.datosEmpresa;
                        DatosDeudaBean datosDeudaBean = (DatosDeudaBean) cnsEmpresaBodyResponseBean.datosDeudas.lstDatosDeudaBean.get(0);
                        if (cnsEmpresaDatosEmpresa.empServ.equalsIgnoreCase("SUBE")) {
                            datosDeudaBean.datosEmpresa = cnsEmpresaDatosEmpresa;
                            datosDeudaBean.empServ = cnsEmpresaDatosEmpresa.empServ;
                            datosDeudaBean.empDescr = cnsEmpresaDatosEmpresa.empDescr;
                            ((NuevoPagoServiciosView) NuevoPagoServiciosPresenter.this.getBaseView()).showRecargaActivity(arrayList, cnsEmpresaDatosEmpresa, datosDeudaBean);
                        } else {
                            NuevoPagoServiciosPresenter.this.onPrepararPago(datosDeudaBean);
                        }
                    }
                    ((NuevoPagoServiciosView) NuevoPagoServiciosPresenter.this.getBaseView()).setResultCnsEmpresa(Boolean.valueOf(true), Html.fromHtml(cnsEmpresaEvent2.getResponse().cnsEmpresaBodyResponseBean.leyendaAyuda));
                } catch (Exception unused) {
                }
            }

            /* access modifiers changed from: protected */
            public void commonAllErrorsBeforeProcess(WebServiceEvent webServiceEvent) {
                if (NuevoPagoServiciosPresenter.this.a) {
                    ((NuevoPagoServiciosView) NuevoPagoServiciosPresenter.this.getBaseView()).dismissProgressIndicator();
                }
            }
        };
        r0.handleWSResponse(cnsEmpresaEvent);
    }

    @Subscribe
    public void onCnsDeuda(CnsDeudaEvent cnsDeudaEvent) {
        final CnsDeudaEvent cnsDeudaEvent2 = cnsDeudaEvent;
        AnonymousClass2 r0 = new BaseWSResponseHandler(((NuevoPagoServiciosView) getBaseView()).getContext(), TypeOption.INITIAL_VIEW, FragmentConstants.PAGO_SERVICIOS, getBaseView(), ((NuevoPagoServiciosView) getBaseView()).getTitle(), (BaseActivity) ((NuevoPagoServiciosView) getBaseView()).getContext()) {
            public void onOk() {
                NuevoPagoServiciosPresenter.this.a(cnsDeudaEvent2);
            }

            /* access modifiers changed from: protected */
            public void commonAllErrorsBeforeProcess(WebServiceEvent webServiceEvent) {
                ((NuevoPagoServiciosView) NuevoPagoServiciosPresenter.this.getBaseView()).dismissProgressIndicator();
            }
        };
        r0.handleWSResponse(cnsDeudaEvent);
    }

    /* access modifiers changed from: private */
    public void a(CnsDeudaEvent cnsDeudaEvent) {
        try {
            if (TextUtils.isEmpty(this.b)) {
                ((NuevoPagoServiciosView) getBaseView()).dismissProgressIndicator();
                if (Integer.valueOf(cnsDeudaEvent.getResponse().cnsDeudaBodyResponseBean.datosDeudas.cantDeudas).intValue() > 0) {
                    ((NuevoPagoServiciosView) getBaseView()).setAccountList(cnsDeudaEvent.getResponse().cnsDeudaBodyResponseBean.cuentasDeudasBean.getCuentas());
                    ((NuevoPagoServiciosView) getBaseView()).showPaymentsList(cnsDeudaEvent.getResponse().cnsDeudaBodyResponseBean.datosDeudas.lstDatosDeudaBean);
                } else {
                    ((NuevoPagoServiciosView) getBaseView()).showNoPayments();
                }
                ((NuevoPagoServiciosView) getBaseView()).showOnBoarding();
                return;
            }
            ((NuevoPagoServiciosView) getBaseView()).setAccountList(cnsDeudaEvent.getResponse().cnsDeudaBodyResponseBean.cuentasDeudasBean.getCuentas());
            cnsEmpresa(this.b);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "cnsDeudaResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    public void showHomeScreen(Boolean bool, String str) {
        cnsDeuda();
        if (!TextUtils.isEmpty(str)) {
            this.b = str;
        } else if (bool.booleanValue()) {
            a();
        }
    }

    private void a() {
        this.a = false;
        this.mDataManager.cnsEmpresa("");
    }

    public void cnsEmpresa(String str) {
        this.a = true;
        this.mDataManager.cnsEmpresa(str);
    }

    public void onPrepararPago(DatosDeudaBean datosDeudaBean) {
        try {
            String str = datosDeudaBean.datosEmpresa.tipoPago;
            char c = 65535;
            int hashCode = str.hashCode();
            if (hashCode != 83) {
                switch (hashCode) {
                    case 49:
                        if (str.equals("1")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 50:
                        if (str.equals("2")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 51:
                        if (str.equals("3")) {
                            c = 0;
                            break;
                        }
                        break;
                }
            } else if (str.equals("S")) {
                c = 3;
            }
            switch (c) {
                case 0:
                    ((NuevoPagoServiciosView) getBaseView()).showPrepararPagoConFactura(datosDeudaBean);
                    return;
                case 1:
                    ((NuevoPagoServiciosView) getBaseView()).showPrepararPagoSinFactura(datosDeudaBean, NuevoPagoServiciosConstants.ORIGEN_AGENDA);
                    return;
                case 2:
                    ((NuevoPagoServiciosView) getBaseView()).showPrepararPagoAfip(datosDeudaBean);
                    return;
                case 3:
                    ((NuevoPagoServiciosView) getBaseView()).showPrepararPagoPrepago(datosDeudaBean);
                    return;
                default:
                    return;
            }
        } catch (Exception unused) {
        }
    }
}
