package ar.com.santander.rio.mbanking.app.module.funds;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovimientoFondosBean;

public interface DetalleMovimientoFondoView extends IBaseView {
    void setDetalleMovimientoFondoView(MovimientoFondosBean movimientoFondosBean);
}
