package ar.com.santander.rio.mbanking.app.module.seguros;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LinkSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SegurosBean;

public interface TenenciaSegurosView extends IBaseView {
    void goToContratarSeguro(SegurosBean segurosBean, String str);

    void goToContratarSeguroMovil(CotizacionBean cotizacionBean);

    void goToContratarSeguroMovilSinCotizar();

    void goToDetalleSeguro(String str, SeguroBean seguroBean, String str2);

    void setBackgroundVisibleInit();

    void setTenenciaSegurosView(Boolean bool, SegurosBean segurosBean, LinkSeguroBean linkSeguroBean);
}
