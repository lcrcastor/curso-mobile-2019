package ar.com.santander.rio.mbanking.app.module.funds;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.InformacionFondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesAdmFondos;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovCtasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovimientoFondosBean;
import java.util.List;

public interface SeleccionarInformacionFondoView extends IBaseView {
    void gotoCotizacionesFondos(List<CategoriaFondosBean> list, LegalesFondosBean legalesFondosBean);

    void gotoInfoAdmFondos(ListaFondosBean listaFondosBean, LegalesAdmFondos legalesAdmFondos, String str);

    void gotoInformacionFondo(InformacionFondoBean informacionFondoBean, FondoBean fondoBean);

    void gotoSuscribirFondo(MovCtasBodyResponseBean movCtasBodyResponseBean, FondoBean fondoBean);

    void gotoTransferirFondo(FondoBean fondoBean);

    void gotoUltimosMovimientosFondo(FondoBean fondoBean, List<MovimientoFondosBean> list);
}
