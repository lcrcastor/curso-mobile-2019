package ar.com.santander.rio.mbanking.app.module.funds;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovimientoFondosBean;
import java.util.Date;
import java.util.List;

public interface BusquedaAvanzadaMovimientoFondosView extends IBaseView {
    void gotoMovimiento(List<MovimientoFondosBean> list);

    void setBusquedaAvanzadaView();

    void setFechaDesde(Date date);

    void setFechaHasta(Date date);
}
