package ar.com.santander.rio.mbanking.app.ui.interfaces;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroAccidenteBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteBodyResponseBean;

public interface CotizacionSeguroAccidenteInt {
    void configActionBarDefault();

    void initialize();

    void showComprobanteSeguroAccidente(ContratarSeguroAccidenteBean contratarSeguroAccidenteBean, String str);

    void showCotizacionSeguroAccidente(GetCotizacionSeguroAccidenteBodyResponseBean getCotizacionSeguroAccidenteBodyResponseBean);
}
