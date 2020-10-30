package ar.com.santander.rio.mbanking.app.module.debin.abmdebin;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultarTitularCuentaBodyResponseBean;

public interface GenerarDebinView extends IBaseView {
    Context getContext();

    void setGenerarDebinView(ConsultarTitularCuentaBodyResponseBean consultarTitularCuentaBodyResponseBean);
}
