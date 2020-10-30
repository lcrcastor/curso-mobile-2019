package ar.com.santander.rio.mbanking.app.module.seguros;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SegurosBean;

public interface ContratarSeguroView extends IBaseView {
    void goToContratarSeguroMovilSinCotizar();

    void gotoCompraProtegida(CotizacionBean cotizacionBean);

    void gotoFamiliaObjeto(GetFamiliasObjetosBodyResponseBean getFamiliasObjetosBodyResponseBean);

    void gotoSeguroMovil(String str, SegurosBean segurosBean, CotizacionBean cotizacionBean);

    void showError(String str, int i, String str2);

    void showOnBoarding();

    void verifyBatery();
}
