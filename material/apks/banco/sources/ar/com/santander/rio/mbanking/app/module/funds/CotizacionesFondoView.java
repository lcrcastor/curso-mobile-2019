package ar.com.santander.rio.mbanking.app.module.funds;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.InformacionFondoBean;

public interface CotizacionesFondoView extends IBaseView {
    void displayCotizacionesFiltro(String str);

    void displayCotizacionesTodosTipos();

    void displayCotizacionesUltimoAno();

    void displayCotizacionesUltimoDia();

    void displayCotizacionesUltimos30();

    void displayCotizacionesUltimos90();

    void displayCotizacionesValorCuota();

    void gotoInformacionFondo(InformacionFondoBean informacionFondoBean, FondoBean fondoBean);

    void sortCotizacionesAscendente();

    void sortCotizacionesDescendente();
}
