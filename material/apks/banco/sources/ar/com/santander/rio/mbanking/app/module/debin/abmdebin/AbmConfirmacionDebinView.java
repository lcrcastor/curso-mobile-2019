package ar.com.santander.rio.mbanking.app.module.debin.abmdebin;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinCompradorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinVendedorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DetalleDebinBean;

public interface AbmConfirmacionDebinView extends IBaseView {
    AnalyticsManager getAnalyticsManager();

    Context getContext();

    void gotoComprobanteAbmDebin(AbmDebinVendedorBodyResponseBean abmDebinVendedorBodyResponseBean, AbmDebinCompradorBodyResponseBean abmDebinCompradorBodyResponseBean);

    void setConfirmarDebinView(DetalleDebinBean detalleDebinBean);
}
