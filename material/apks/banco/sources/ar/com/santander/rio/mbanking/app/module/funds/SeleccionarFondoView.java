package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;

public interface SeleccionarFondoView extends IBaseView {
    Context getContext();

    void gotoNextFlow(FondoBean fondoBean);

    void setSelectedAccount(CuentaFondosBean cuentaFondosBean);
}
