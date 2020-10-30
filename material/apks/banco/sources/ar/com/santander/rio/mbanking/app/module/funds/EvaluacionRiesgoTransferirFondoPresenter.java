package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.TransferirFondoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.TransferirFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AlertaEvaluacionRiesgoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SimularTransferenciaFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferirFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VTransferirFondo;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class EvaluacionRiesgoTransferirFondoPresenter extends BasePresenter<EvaluacionRiesgoTransferirFondoView> {
    public EvaluacionRiesgoTransferirFondoPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean, String str, String str2) {
        ((EvaluacionRiesgoTransferirFondoView) getBaseView()).setEvaluacionRiesgoTransferenciaView(alertaEvaluacionRiesgoBean, str, str2);
    }

    public Context getContext() {
        return ((EvaluacionRiesgoTransferirFondoView) getBaseView()).getContext();
    }

    public void callTransferirFondo(SimularTransferenciaFondoBodyResponseBean simularTransferenciaFondoBodyResponseBean, CuentaFondosBean cuentaFondosBean, String str, String str2) {
        ((EvaluacionRiesgoTransferirFondoView) getBaseView()).showProgressIndicator(VTransferirFondo.nameService);
        String str3 = "0";
        if (simularTransferenciaFondoBodyResponseBean.getMonedaDestino().equalsIgnoreCase("2") || UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(simularTransferenciaFondoBodyResponseBean.getMoneda()).toString()).equalsIgnoreCase(Constants.SYMBOL_CURRENCY_DOLAR)) {
            str3 = "2";
        }
        this.mDataManager.transferirFondo(simularTransferenciaFondoBodyResponseBean.getImporteTransferir(), str3, simularTransferenciaFondoBodyResponseBean.getIdFondoOrigen(), simularTransferenciaFondoBodyResponseBean.getIdFondoDestino(), cuentaFondosBean.getTipoCuenta(), cuentaFondosBean.getSucursalCuenta(), cuentaFondosBean.getNumero(), "1", str2, simularTransferenciaFondoBodyResponseBean.getComisionOrigen(), simularTransferenciaFondoBodyResponseBean.getComisionDestino(), "", "1");
    }

    @Subscribe
    public void transferirFondo(TransferirFondoEvent transferirFondoEvent) {
        ((EvaluacionRiesgoTransferirFondoView) getBaseView()).dismissProgressIndicator();
        final TransferirFondoEvent transferirFondoEvent2 = transferirFondoEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(getContext(), TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) getContext()) {
            public void onOk() {
                EvaluacionRiesgoTransferirFondoPresenter.this.a(((TransferirFondoResponseBean) transferirFondoEvent2.getBeanResponse()).getTransferirFondoBodyResponseBean());
            }
        };
        r1.handleWSResponse(transferirFondoEvent);
    }

    /* access modifiers changed from: private */
    public void a(TransferirFondoBodyResponseBean transferirFondoBodyResponseBean) {
        try {
            ((EvaluacionRiesgoTransferirFondoView) getBaseView()).gotoComprobante(transferirFondoBodyResponseBean);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "transferirFondoResultOk: ", e);
            e.fillInStackTrace();
        }
    }
}
