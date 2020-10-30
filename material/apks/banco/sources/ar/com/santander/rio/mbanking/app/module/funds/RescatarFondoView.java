package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaOperativaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RescatarFondoBodyResponseBean;
import java.util.List;

public interface RescatarFondoView extends IBaseView {
    AnalyticsManager getAnalyticsManager();

    Context getContext();

    List<CuentaOperativaBean> getCuentasValidas(List<CuentaOperativaBean> list);

    String getDestinoDeCuentaOpEnLista(String str);

    SessionManager getSessionManager();

    void gotoConfirmacion(RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean);

    void gotoFueraHorarioFondo(String str);

    boolean isValidDataRescatar();

    void setRescatarFondoView();

    void setSelectedAccount(CuentaFondosBean cuentaFondosBean);

    void setSelectedAmountTypeCuotapartes();

    void setSelectedAmountTypeOtro();

    void setSelectedAmountTypeTotal();

    void setSelectedDestinationAccount(Cuenta cuenta);

    void setSelectedFondo(FondoBean fondoBean);
}
