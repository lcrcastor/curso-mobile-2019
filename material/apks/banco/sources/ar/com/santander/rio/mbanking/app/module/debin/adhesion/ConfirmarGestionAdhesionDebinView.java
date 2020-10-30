package ar.com.santander.rio.mbanking.app.module.debin.adhesion;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmAdhesionVendedorBodyResponseBean;

public interface ConfirmarGestionAdhesionDebinView extends IBaseView {
    AnalyticsManager getAnalyticsManager();

    Context getContext();

    void gotoComprobanteGestionAdhesionDebinView(AbmAdhesionVendedorBodyResponseBean abmAdhesionVendedorBodyResponseBean, Integer num);

    void setConfirmarGestionCuentaView();

    void setStatusACuenta(Integer num);
}
