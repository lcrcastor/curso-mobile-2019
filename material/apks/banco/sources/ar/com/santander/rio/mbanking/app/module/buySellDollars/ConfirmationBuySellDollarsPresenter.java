package ar.com.santander.rio.mbanking.app.module.buySellDollars;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.CompraVentaDolarEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolaresCuentaBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VCompraVentaDolar;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ConfirmationBuySellDollarsPresenter extends BasePresenter<ConfirmationBuySellDollarsView> {
    protected Context mContext;

    public ConfirmationBuySellDollarsPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
    }

    public void performBuyingOperation(String str, String str2, String str3, String str4, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2) {
        a("1", str, str2, str3, str4, compraVentaDolaresCuentaBean, compraVentaDolaresCuentaBean2);
    }

    public void performSellingOperation(String str, String str2, String str3, String str4, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2) {
        a("2", str, str2, str3, str4, compraVentaDolaresCuentaBean, compraVentaDolaresCuentaBean2);
    }

    private void a(String str, String str2, String str3, String str4, String str5, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2) {
        ((ConfirmationBuySellDollarsView) this.mBaseView).showProgressIndicator(VCompraVentaDolar.nameService);
        this.mDataManager.compraVentaDolar(str, str2, str3, str4, str5, compraVentaDolaresCuentaBean, compraVentaDolaresCuentaBean2);
    }

    @Subscribe
    public void onDoCompraVentaDolar(CompraVentaDolarEvent compraVentaDolarEvent) {
        ((ConfirmationBuySellDollarsView) getBaseView()).dismissProgressIndicator();
        final CompraVentaDolarEvent compraVentaDolarEvent2 = compraVentaDolarEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.COMPRAVENTA_DOLARES, getBaseView(), (BaseActivity) this.mContext) {
            public void onOk() {
                ConfirmationBuySellDollarsPresenter.this.a(compraVentaDolarEvent2);
            }
        };
        r1.handleWSResponse(compraVentaDolarEvent);
    }

    /* access modifiers changed from: private */
    public void a(CompraVentaDolarEvent compraVentaDolarEvent) {
        try {
            ((ConfirmationBuySellDollarsView) getBaseView()).onCompraVentaDolar(compraVentaDolarEvent.getResponse());
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onDoCompraVentaDolarResultOk: ", e);
            e.fillInStackTrace();
        }
    }
}
